/*
 * New Order JavaScript client
 *
 * $Id: NewOrder.js,v 1.0 20012/10/15 15:09:37 Meenakshi  $
 *
 *
 * This code is based on JS used in NewOrder.jsp.
 * All JS code written in NewOrder.jsp is moved to this JS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
/*
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[040]    ROHIT VERMA       05-Nov-12	CSR00-07843		7 Core Products-->
<!--[083]  	Manisha Garg	02-Feb-13	00-05422		Change Order after validate coming out to Home page rather than showing Lines tab like in New Order defect no 52 -->

<!--[084]  	Manisha Garg	02-Feb-13	00-05422		sr no refreshed defect no 36-->
<!--[041]    Vijay             08-Feb-13                    cancel buttone should not be display first time  -->
<!--[042]    Vijay             12-Feb-13                selected page's tab contain  a diffrent color than non-selected pages Tabs. In this way selected Tab contain uniqeness.-->
<!--[043]    ROHIT VERMA       21-Feb-13	CSR00-07480		Arbor Development for LSI Mapping & Linking GUI-->
//[15032013016] Rampratap 14-03-13 added for count tolal line items selected.
<!--[15032013012]   Vijay      14-March-13              'Order Validated Sucessfully' Alert's image replaced by Tick mark. And this alert would be fade out automatically.  -->
<!--[044]	 Neelesh		24-May-13	CSR00-08463     For Addition of Category -->
<!--[045]	 Neelesh		06-Jun-13	TRNG22032013037     For Mulitple Opportunity  -->
<!--[046]	 Sumit Arora	29-Aug-13	20130807-00-09286 iB2B - opportunity selection Mandatory for GB Order  -->
<!--[047]	 Rohit Verma	10-Jul-13	CSR00-9912	     BFR5-Parallel Upgrade LSI Validation-->
<!--[048]	 Rohit Verma	26-Nov-13	CSR00-9463	     BFR1-Validation to input Advance Payment details at line item level for Channel's Order-->
<!--[049]	 Santosh	19-Dec-13	  	     Added condition for NewOrder and DraftOrder -->

<!--[050]    Gunjan Singla  12-Dec-2013                 added action mapping for ordercanellationreason.jsp and added ajax call to cancelorderreason-->
<!-- [051] 	VIPIN SAHARIA 29-Apr-2014 		If condition added -- not to call validatePODetail(x) method if Role Name is PM or SED -->
     [052]    Gunjan Singla  05-Mar-2014                  Added for cancellation reason
     [053] VIPIN SAHARIA 17-07-2014 Added logic to get charge details for Service - Sales charges validation for DC hardware products.
     [054] VIPIN SAHARIA 05-09-2014	Added logic to show alert while clicking SendToCOPC after SED Approval
     [055] Sadananda Behera 1-09-2014  added new  columan service_cancel_date,service_cancel_reson,service_ cancelBy,service_cancel_remarks for new order 
     [056]   Neha Maheshwari    added service segment     NORTH STAR ACCOUNT TAGGING
     [057]   Gunjan Singla      23-12-2014                Parallel Upgrade-Multiple LSI selection
     [058]  Raveendra           Partial service validation
     [060]   Gunjan Singla   4-Mar-15     20150225-R1-021111    LSI and Service No Search required in Lines Tab
<!-- [061]	VIPIN SAHARIA 13-May-2015 20150403-R2-021204 Project Satyapan Validating mandatory nature of fields while saving Main/Contact/PO Tabs -->
<!-- [062]  Priya Gupta  19-Jun-2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 2 (BFR 4) ld clause in IB2B -->
<!--[063] VIPIN SAHARIA 11th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Reverting MBIC CC mapping as per L3-VCS mapping -->
	[065]	Gunjan Singla	24 Nov 2015		M2M:validations on validate order button 
	[067]   Gunjan Singla   26 Oct 2016     CBR-ANG bandwidth correction in iB2B and M6
*/
	//==========[ start clep ]=======================

var clepState=0;
var ORDER_CREATION_SOURCE = 2;//CLEP
//==========[ end clep ]========================
var reasonForCancelService='';
var checkedServiceNumber=-1;
 var fileTypeIdN=0;
 var sentMethod=0;
 var child_count=0;
var flag=0;
var counter = 1;

//PAGING-SERVICE-LINE-14-10-2012
//var countService=1;


var isServiceReadOnly =0;
var OrderNoTM6=0,OrderNoBilling=0;
var serviceTypeId;
var selectedServiceName;
var parrentArray = new Array();
var childArray = new Array();
	
	var roleFeild;
	var contactTabFieldList;
	var poDetailTabFieldList;
	var linesTabFieldList;
	var mainTabFieldList;
	var btnselectservice="0";
	var btnAttributes="0";
	var btnAttLabelValues="0";
	var serviceSummaryList;
	var isGamNFormulaAttachedFlag=0;

	
	var treeNodes;
	var employeeId = gb_empID;
	var roleName = gb_roleID;
	var userId = gb_userID;

function noBack() 
	{ 
		window.history.forward(1); 
	}

function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}


function getTip_DD(obj,value,objName)
{	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('searchForm');
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}
function fetchFeildList(tabName)
	{
			var myForm=document.getElementById('searchForm')
			//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			//var sessionid ='<%=request.getSession().getId() %>';
			
			roleFeild = jsonrpc.processes.getFieldValidation(sessionId, tabName);
			
			mainTabFieldList = roleFeild.list[0];
			contactTabFieldList = roleFeild.list[1];
			poDetailTabFieldList = roleFeild.list[2];
			linesTabFieldList = roleFeild.list[3];
			serviceSummaryList = roleFeild.list[4];
			checkServiceButton(linesTabFieldList);
			
			initFieldValidation();
			
			initButtonFieldValidation();
			
	}
	function fnCheckContactAll()
{
  
	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
	    conatctTab = document.getElementById('tableContact');
	    addressTab = document.getElementById('tableAddress');
        var rowlen= conatctTab.rows.length;
         if(rowlen==1)
			 {
			 alert("please add one row to delete");
			 document.getElementById('SelectAllChckContact').checked=false;
			 return false;
			 }
        
        var chkLength1=document.forms[0].chkSelectContactDetail.length;
	    
		if(document.getElementById('SelectAllChckContact').checked==true)
		{
			        if(chkLength1==undefined)
	        
	                {
		                 if(document.forms[0].chkSelectContactDetail.disabled==false)
			             {
		          
		                    document.forms[0].chkSelectContactDetail.checked=true;
		                  
		                   
		                 }
	                 
			             
	          
                  }
          
      else
      {    
	         for (i =0; i<chkLength1; i++)
			   { 
		     
			     if(myForm.chkSelectContactDetail[i].disabled==false)
			     
					 {
					        myForm.chkSelectContactDetail[i].checked=true ;
					     
					      
					}      
		   
		      }
	}
	
	 
	         
	
}	
	
	
		   


	else
	   {
	           if(chkLength1==undefined)
        
          {
	                 if(document.forms[0].chkSelectContactDetail.disabled==false)
		             {
	          
	                    document.forms[0].chkSelectContactDetail.checked=false;
	                    
	                 
	                 }
	                 
	             
	          
          }
          
      else
      {    
         for (i =0; i<chkLength1; i++)
		   { 
		     
		     if(myForm.chkSelectContactDetail[i].disabled==false)
		     
				 {
				        myForm.chkSelectContactDetail[i].checked=false ;
				       
				      
				 }       
		   
		   }
	}
	
		       
}
	
	


	
}


function fnCheckPOAll()
{
  
	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
	    POTab = document.getElementById('tablePO');
        var rowlen= POTab.rows.length;
         if(rowlen==1)
			 {
			 alert("please add one row to delete");
			 document.getElementById('SelectAllPOChck').checked=false;
			 return false;
			 }
        
        var chkLength1=document.forms[0].chkSelectPODetail.length;
	    
		if(document.getElementById('SelectAllPOChck').checked==true)
		{
			        if(chkLength1==undefined)
	        
	                {
		                 if(document.forms[0].chkSelectPODetail.disabled==false)
			             {
			                
			             
			                    document.forms[0].chkSelectPODetail.checked=true;
			                    counter++;
		                     
		                   
		                 }
		                 
		          
	                 
			             
	          
                  }
          
      else
      {    
	         for (i =0; i<chkLength1; i++)
			   { 
		     
			     if(myForm.chkSelectPODetail[i].disabled==false)
			     
					 {
					        myForm.chkSelectPODetail[i].checked=true ;
					           counter++;
					      
					} 
					
					       
		              
		   
		      }
	}
	
	      if(counter==1)
		 
		         {
					alert("No PO Available for Selection");
					document.getElementById('SelectAllPOChck').checked=false;
					return false;         
		         }
	
	 
	         
	
}	
	
	else
	   {
	           if(chkLength1==undefined)
        
          {
	                 if(document.forms[0].chkSelectPODetail.disabled==false)
		             {
	          
	                    document.forms[0].chkSelectPODetail.checked=false;
	                       counter++;
	                 
	                 }
	                 
	             
	          
          }
          
      else
      {    
         for (i =0; i<chkLength1; i++)
		   { 
		     
		     if(myForm.chkSelectPODetail[i].disabled==false)
		     
				 {
				        myForm.chkSelectPODetail[i].checked=false ;
				          counter++;
				      
				 }       
		   
		   }
	}
	
	     if(counter==1)
		 
		         {
					alert("No PO Available for Selection");
					document.getElementById('SelectAllPOChck').checked=false;
					return false;                  
		         }
	
		       
}
	
	


	
}
	
	
	
	
function fnCheckAll()
{

	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
     	//var sessionid ='<%=request.getSession().getId() %>';
     	var jsData1 =              			
	   	{
			poNumber:document.getElementById('poNumber').value,
			sessionid:sessionId
		};

    	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");

    	var test1 = jsonrpc.processes.poulateServiceList(jsData1);
    	var serviceCount=test1.list.length;

		if(serviceCount==0)
			{
				alert("Please add atleast one service");
				document.getElementById('SelectAllChck').checked=false;
				return false;
			}

	var chkLengthser=document.forms[0].chkService.length;
     
	if(chkLengthser==undefined)
	{


		if(document.forms[0].chkService.checked==true)
		{
			checkedCounter++;
			
			if(!(document.forms[0].chk_spId))
			{
			
				alert("please add atleast one product");
				document.getElementById('SelectAllChck').checked=false;
				return; 

	}		}


}	
	else
	{
		for(i=0;i<chkLengthser;i++)
	   	{
			if(myForm.chkService[i].checked==true)
			{

				checkedCounter++;
				if(!(document.forms[0].chk_spId))
			{
						alert("please add atleast one product");
						document.getElementById('SelectAllChck').checked=false;
						return; 

					}
		}
	}

}				
	if(checkedCounter==1)
	{
		alert('Please Select Service Type!!');
		document.getElementById('SelectAllChck').checked=false;
		return false;
	}		




	   var chkLength1=document.forms[0].chk_spId.length;

		if(document.getElementById('SelectAllChck').checked==true)
		{
			        if(chkLength1==undefined)

	                {
		                 if(document.forms[0].chk_spId.disabled==false)
			             {

		                    document.forms[0].chk_spId.checked=true;


		                 }



                  }

      else
      {    
	         for (i =0; i<chkLength1; i++)
			   { 

			     if(myForm.chk_spId[i].disabled==false)

					 {
					        myForm.chk_spId[i].checked=true ;


					}      

		      }
	}




}	





	else
	   {
	           if(chkLength1==undefined)

          {
	                 if(document.forms[0].chk_spId.disabled==false)
		             {

	                    document.forms[0].chk_spId.checked=false;


	                 }



          }

      else
      {    
         for (i =0; i<chkLength1; i++)
		   { 

		     if(myForm.chk_spId[i].disabled==false)

				 {
				        myForm.chk_spId[i].checked=false ;


				 }       

		   }
	}


	}

}

	
	function initButtonFieldValidation()
	{
			var myForm=document.getElementById('searchForm');

			//----------Contact Tab-------------------------------			
			var ctrlArry = new Array();
			ctrlArry[0] =  myForm.btnAddContact;
			ctrlArry[1] =  myForm.btnDeleteContactDetail;
			fieldRoleMappingValidation(contactTabFieldList,ctrlArry);
			//----------Contact Tab-------------------------------		
			
			//----------PO Detail Tab-------------------------------			
			ctrlArry = new Array();
			ctrlArry[0] =  myForm.btnAddPODetail;
			ctrlArry[1] =  myForm.btnDeletePoDetail;
			fieldRoleMappingValidation(poDetailTabFieldList,ctrlArry);
			//----------PO Detail Tab-------------------------------		

			//----------Lines Tab-------------------------------			
			for (iField = 0 ; iField < linesTabFieldList.list.length; iField++)
    		{
    			if(linesTabFieldList.list[iField].isReadOnly=="1" && linesTabFieldList.list[iField].feildName=="cancelservice")
    				document.getElementById('cancelservice').style.visibility='hidden';
    			if(linesTabFieldList.list[iField].isReadOnly=="1" && linesTabFieldList.list[iField].feildName=="validateorder")
    				document.getElementById('validateorder').style.visibility='hidden';
    			if(linesTabFieldList.list[iField].isReadOnly=="1" && linesTabFieldList.list[iField].feildName=="productcatelog")
    				document.getElementById('productcatelog').style.visibility='hidden';
    				
    		}
    		ctrlArry = new Array();
			ctrlArry[0] =  myForm.btnAddService;
			
			//fieldRoleMappingValidation(linesTabFieldList,ctrlArry);
			//----------Lines Tab-------------------------------		
			
				
		
	}
	
	function checkServiceButton(linesTabFieldList)
	{
		for (iField = 0 ; iField <  linesTabFieldList.list.length; iField++)
		{
			if(linesTabFieldList.list[iField].feildName=="btnselectservice")
				btnselectservice = linesTabFieldList.list[iField].isReadOnly
			if(linesTabFieldList.list[iField].feildName=="btnAttributes")	
				btnAttributes = linesTabFieldList.list[iField].isReadOnly
			if(linesTabFieldList.list[iField].feildName=="btnAttLabelValues")	
				btnAttLabelValues = linesTabFieldList.list[iField].isReadOnly
		}
	}
	
	function initFieldValidation()
	{

			var myForm=document.getElementById('searchForm')


			//----------Main Tab-------------------------------			
			var ctrlArry = new Array();
			ctrlArry[0] =  myForm.AttributeVal_1;
			ctrlArry[1] =  myForm.AttributeVal_2;
			ctrlArry[2] =  myForm.AttributeVal_3;
			ctrlArry[3] =  myForm.AttributeVal_4;
			ctrlArry[4] =  myForm.AttributeVal_5;
			ctrlArry[5] =  myForm.AttributeVal_6;
			ctrlArry[6] =  myForm.AttributeVal_7;
			ctrlArry[7] =  myForm.AttributeVal_8;
			ctrlArry[8] =  myForm.AttributeVal_9;
			ctrlArry[9] =  myForm.AttributeVal_10;
			//[047] Start
			ctrlArry[10] =  myForm.AttributeVal_11;
			//[047] End
			fieldRoleMappingValidation(mainTabFieldList,ctrlArry);
			//----------Main Tab-------------------------------			
			
			//----------Contact Tab-------------------------------			
			ctrlArry = new Array();
			ctrlArry[0] =  myForm.contactType;
			ctrlArry[1] =  myForm.salutationName;
			ctrlArry[2] =  myForm.firstName;
			ctrlArry[3] =  myForm.lastName;
			ctrlArry[4] =  myForm.cntEmail;
			ctrlArry[5] =  myForm.contactCell;
			ctrlArry[6] =  myForm.contactFax;
			ctrlArry[7] =  myForm.address1;
			ctrlArry[8] =  myForm.address2;
			ctrlArry[9] =  myForm.address3;
			ctrlArry[10] =  myForm.cityName;
			ctrlArry[11] =  myForm.stateName;
			ctrlArry[12] =  myForm.countyName;
			ctrlArry[13] =  myForm.pinNo;
			fieldRoleMappingValidation(contactTabFieldList,ctrlArry);
			//----------Contact Tab-------------------------------			
		
			//----------PO Detail Tab-------------------------------
			ctrlArry = new Array();	
			ctrlArry[0] =  myForm.custPoDetailNo;
			//<!-- [003]	Start -->
			ctrlArry[1] = myForm.custPoDate;
			//<!-- [003]	End -->		
			ctrlArry[2] =  myForm.poDetailNo;
			ctrlArry[3] =  myForm.poDate;
			ctrlArry[4] =  myForm.poReceiveDate;
			ctrlArry[5] =  myForm.contractStartDate;
			ctrlArry[6] =  myForm.contractEndDate;
			ctrlArry[7] =  myForm.periodsInMonths;
			ctrlArry[8] =  myForm.totalPoAmt;
			ctrlArry[9] =  myForm.entityId;
			ctrlArry[10] =  myForm.defaultPO;
			ctrlArry[11] =  myForm.poIssueBy;
			ctrlArry[12] =  myForm.poRemarks;
			ctrlArry[13] =  myForm.poEmailId;
			ctrlArry[14] =  myForm.poDemoContractPeriod;
			//[062]
			ctrlArry[15] =  myForm.ldClause;
			fieldRoleMappingValidation(poDetailTabFieldList,ctrlArry);
			//----------Lines Tab-------------------------------	
			
			/*	ctrlArry = new Array();			
			alert(myForm.txtServiceNo.value)
			ctrlArry[0] =  myForm.txtServiceNo;
			ctrlArry[1] =  myForm.txtServiceName;
			ctrlArry[2] =  myForm.txtServiceSubTypeName;
			//fieldRoleMappingValidation(linesTabFieldList,ctrlArry);
		ctrlArry[3] =  myForm.serviceDetailID;
			ctrlArry[4] =  myForm.parentServiceProductId;
			
           		//----------Billing Info -------
			ctrlArry[5] =  myForm.txtPODetailNo;
			ctrlArry[6] =  myForm.txtBillingPODate;
			ctrlArry[7] =  myForm.txtCntrtMonths;
			ctrlArry[8] =  myForm.txtBillingAC;			
			ctrlArry[9] =  myForm.txtBillingCP;
			ctrlArry[10] =  myForm.txtCur;
			ctrlArry[11] =  myForm.txtEntity;
			ctrlArry[12] =  myForm.txtBillingMode;
			ctrlArry[13] =  myForm.txtBillingformat;
			ctrlArry[14] =  myForm.txtLicenceCo;
			ctrlArry[15] =  myForm.txtBillingType;
			ctrlArry[16] =  myForm.txtTaxation;
			ctrlArry[17] =  myForm.txtCmtPeriod;
			ctrlArry[18] =  myForm.txtBillingLvl;
			ctrlArry[19] =  myForm.txtPenaltyClause;
			  		//----------Billing Address -------
			ctrlArry[20] =  myForm.bbPrimaryBCP;
				//------------Service Location Details----------------
			ctrlArry[21] =  myForm.ddPrimaryLocType;
			ctrlArry[22] =  myForm.ddSecondaryLocType;
			
			
			//------------Charges Details --------------
			ctrlArry[23] =  myForm.ddCType;
			ctrlArry[24] =  myForm.txtCName;
			ctrlArry[25] =  myForm.txtCPeriod;
			ctrlArry[26] =  myForm.txtCTAmt;
			ctrlArry[27] =  myForm.txtCFrequency;
			ctrlArry[28] =  myForm.txtCFreqAmt;
			ctrlArry[29] =  myForm.txtCStartDate;
			ctrlArry[30] =  myForm.txtCEndDate;
			
			//------------Hardware Details --------------
			ctrlArry[31] =  myForm.txtStore;
			ctrlArry[32] =  myForm.txtHtype;
			ctrlArry[33] =  myForm.txtform;
			ctrlArry[34] =  myForm.txtTSale;
			ctrlArry[35] =  myForm.txtNSale;*/

			
			//fieldRoleMappingValidation(linesTabFieldList,ctrlArry);	
			
				


	}	
	
	function fieldRoleMappingValidation(lstFieldList,ctrlArry)
	{
			var myForm=document.getElementById('searchForm')
			
			for (iField = 0 ; iField < lstFieldList.list.length; iField++)
    		{
	 			for(iCtrl=0;iCtrl< ctrlArry.length;iCtrl++)
				{					
					  if(ctrlArry[iCtrl].name==undefined)
					  {
						  for(ictrlName=0;ictrlName<ctrlArry[iCtrl].length;ictrlName++)
						  {					  
						   ctrl = ctrlArry[iCtrl];
						   ctrlName = ctrl[ictrlName].name;				
							if(ctrlName==lstFieldList.list[iField].feildName)
							{
							  	
								if(lstFieldList.list[iField].isReadOnly=="1")
								{
									
									if(ctrl[ictrlName].type=="select-one")
										ctrl[ictrlName].disabled  = true;
									else
									if(ctrl[ictrlName].type=="button")
									{
										ctrl[ictrlName].disabled="disabled";
									}	
									else
									    ctrl[ictrlName].readOnly  = true;		
								}
								else
								{
									if(ctrl[ictrlName].type=="select-one")
										ctrl[ictrlName].disabled  = false;
									else
									    ctrl[ictrlName].readOnly  = false;		
								}
								ctrl[ictrlName].isRequired = lstFieldList.list[iField].isMand;
								ctrl[ictrlName].Disp = lstFieldList.list[iField].fieldLabel;
								//---[012]---Start
								if(ctrl[ictrlName].isRequired=="0")
									ctrl[ictrlName].className = "inputBorder2";
								else
								ctrl[ictrlName].className = "inputBorder1";
								//---[012]---End
							}
						  }
					  }
					  else	  
					  {
					    ctrlName = ctrlArry[iCtrl].name;
						if(ctrlName==lstFieldList.list[iField].feildName)
						{
							if(lstFieldList.list[iField].isReadOnly=="1")
							{
								if(ctrlArry[iCtrl].type=="select-one")
									ctrlArry[iCtrl].disabled  = true;
								else
								if(ctrlArry[iCtrl].type=="button")
									ctrlArry[iCtrl].disabled="disabled";
								else
								    ctrlArry[iCtrl].readOnly  = true;		
								
							}
							else
							{
								if(ctrlArry[iCtrl].type=="select-one")
									ctrlArry[iCtrl].disabled  = false;
								else
								    ctrlArry[iCtrl].readOnly  = false;		
								
							}
							ctrlArry[iCtrl].isRequired = lstFieldList.list[iField].isMand;
							//---[012]---Start
							if(ctrlArry[iCtrl].isRequired=="0")
								ctrlArry[iCtrl].className = "inputBorder2";
							else
								ctrlArry[iCtrl].className = "inputBorder1";
							//---[012]---End
							ctrlArry[iCtrl].Disp = lstFieldList.list[iField].fieldLabel;
						}
					  }						
				}	
			}
	}




function goToHome()
{
//Meenakshi : commented entire form submit. Made a dummy form and called action
	/*var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	showLayer();
	myForm.submit(); */
	
		var goToHomeForm=createDummyForm(gb_path + "/defaultAction.do");
		attachHiddenField(goToHomeForm,"method","goToHomeAfterClosing");
		showLayer();
		goToHomeForm.submit();
}

//--[042]--start--//
function selectCurrentTab(currentTab){
	 var li1 = document.getElementById("li1");
	 var li2 = document.getElementById("li2");
	 var li3 = document.getElementById("li3");
	 var li5 = document.getElementById("li5");
	 var li6 = document.getElementById("li6");
	 if(li1 !=null)
	    li1.className="";
	 if(li2 !=null)
	    li2.className="";
	 if(li3 !=null)
	    li3.className="";      
	 if(li5 !=null)
	    li5.className="";   
	 if(li6 !=null)
	    li6.className="";   
	 document.getElementById(currentTab).className = "current";    
}
//--[042]--end--//

//--[042]--start--//
function changeTab(currentTab,tabVal,view1,view2,view3,view4,view5)
{
	getRoleWiseFieldInformation(currentTab);
	selectCurrentTab(currentTab);
	//--[042]--end--//
	if(tabVal==3)
	{
		enableDisablePOCheckbox();
	}
	
	document.getElementById('hdnTabVal').value=tabVal;
	//document.getElementById('hdnTabValchannelmaster').value=tabVal;
	document.getElementById('Page_1').style.display=view1;
	document.getElementById('Page_2').style.display=view2;
	document.getElementById('Page_3').style.display=view3;
	document.getElementById('Page_5').style.display=view4;	
	if(document.getElementById('hdnTabVal').value=="5" || document.getElementById('hdnTabVal').value=="6" /*|| document.getElementById('hdnTabValchannelmaster').value=="5" || document.getElementById('hdnTabValValchannelmaster').value=="6"*/)
	{
		document.getElementById('saveIcon').style.display="none";
	}
	else
	{
		if(OrderNoTM6==OrderNoBilling && OrderNoTM6 != 0)
		{
			document.getElementById('saveIcon').style.display="none";
		}
		else
		{
				//added for clep states in change tab to hide save button
			if(clepState !=1 && clepState != 2 && clepState !=3 && clepState !=4){
				document.getElementById('saveIcon').style.display="block";
			}
		}
	}
	// below code is used for display approval.jsp page in edit mode through work queue approval: by anil
	//var modeValue="<%= request.getParameter("modeName") %>";
	  var modeValue= gb_ModeValue;
	
	//Commented below condition due to production Issue of not Displaying Approval Tab After Rejection
	//if(modeValue=="editON")
	//{
		document.getElementById('Page_6').style.display=view5;
		document.getElementById('divSelectCurrencyId').style.display='none';
		
	//}
	//else
	//{
		//document.getElementById('Page_6').style.display="none";	
		//document.getElementById('divSelectCurrencyId').style.display='block';
	//}
	//End By Saurabh
	if(tabVal==1)
	{
		if(modeValue=="editON")
		{
			document.getElementById('divSelectCurrencyId').style.display='none';
		}
		else
		{
			document.getElementById('divSelectCurrencyId').style.display='block';
		}
	}
	else
	{
		document.getElementById('divSelectCurrencyId').style.display='none';
	}
}
function getRoleWiseFieldInformation(currentTab){
	if(currentTab == 'li1')
		fetchFeildList('Main');
	else if(currentTab == 'li2')	
		fetchFeildList('Contact');
	else if(currentTab == 'li3')	
		fetchFeildList('PO Details');
	else if(currentTab == 'li5'){
		fetchFeildList('Product Catelog');
		DrawTable('SERVICENO','SERVICELINETABLE');
	}
}
//these functions is used for work queue approval page
function deleteNote(searchtaskNotesId,taskId,status)
{
	if (confirm("Are you sure you want to delete this note")) 
	{
    	
	var jsData =			
    {
		taskNoteId:searchtaskNotesId
	};
	
	   //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
	    var retVal = jsonrpc.processes.DeleteNotes(jsData);
	    alert(retVal.msgOut);
	    showNotes(taskId,unescape(status));
    }
    
}
function serviceProductAttribute(ctrlNo) 
{
	document.getElementById('hdnserviceid').value = document.getElementById('txtServiceNo'+ctrlNo).value;
	var serviceNo=document.getElementById('txtServiceNo'+ctrlNo).value;
	var serviceTypeId=document.getElementById('txtServiceTypeID'+ctrlNo).value;
	var path = gb_path +"/NewOrderAction.do?method=getToProductAttDetail&hdnserviceid="+serviceNo+'&hdnserviceTypeId='+serviceTypeId;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
}
/*function ViewServiceTree1()
{
		var modeValue=gb_ModeValue;
		var myForm=document.getElementById('searchForm');
		myForm.action=gb_path+"/NewOrderAction.do?method=getServiceTreeview&modeName="+ modeValue;
		showLayer();
		myForm.submit();
}*/

function fnViewProductCatelog(url)
{
	
	var roleId = gb_roleID;
	//var modeValue="<%= request.getParameter("modeName")%>";
	var modeValue= gb_ModeValue;
	var path = gb_path + "/NewOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=2"+"&modeName="+ modeValue+"&roleId="+roleId+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value;
	window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px");
}

function getTree(treeView,nextNode,nextLabel,nextUrl, commercial)
{
	try{
		var getNode="";
		var getNextNode="";
		var str="";
		var i;
		str =str+"<li><span class='folder'><input type='radio' commercial='" + commercial + "' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
				
		for(i=0;i<treeView.lstTreeItems.list.length;i++)
		{
			if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
			{
			 	parrentArray[i]=treeView.lstTreeItems.list[i].serviceProductParentId;
		 		childArray[i]=treeView.lstTreeItems.list[i].serviceProductChildId;
			 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL, treeView.lstTreeItems.list[i].isCommercial)+"</ul>";
			 	
			}
		}
		str=str+"</li>";
		return str;
	}catch(e)
	{
		alert('error code 17: '+ e.message);
	}
}
/* function TreeNode(nextNode,nextUrl , nextLabel)
{
	if(unescape(nextUrl)=="")
	{
	//	iCountTreeNode=2;
		alert('Total Products in ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode));
	}	
	else
	{
		//iCountTreeNode=0;	
		alert('Total Products in ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode));
		fnViewProductCatelog(nextUrl);
	}	
} */
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
//Saurabh
function fncServiceTreeView()
{
	try{
	var newCounter=1;
	var chkLength=document.forms[0].chkService.length;
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			if(document.forms[0].txtServiceNo1.value=="")
			{
				alert("Pls. Enter Service");
				return false;
			}
			else
			{
				document.getElementById('browser').innerHTML= "";
			    var jsData = 
			    {
			    	serviceId:document.forms[0].txtServiceNo1.value,
			    	orderNumber:document.getElementById('poNumber').value
			    };
			  	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				 var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
				 
				 //////
				 treeNodes = treeView;
				var url ="";
				var nodeText ="";
				var parentNodeId;
				var commercial = "";
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	commercial = treeView.lstTreeItems.list[i].isCommercial;
					 	break;
					}
				}
				 
				//alert(document.forms[0].txtServiceNo1.value); 
				 
				var treeString=getTree(treeView,parentNodeId,nodeText,"", commercial);

			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});
				 ////////////////
				 
				 /*
				var treeString="";
			  	for(var j=0;j<treeView.lstTreeItems.list.length;j++)
			  	{
					treeString = treeString + "<li><span class='file'><a href='#' onclick=fnViewProductCatelog(\'"+ escape(treeView.lstTreeItems.list[j].treeViewURL) +"\');>"+ treeView.lstTreeItems.list[j].treeViewText + "</a></span></li>"    		
			   	}	
			 	var branches = $("<li><span class='folder'>" + document.forms[0].txtServiceName1.value + "</span><ul>" + 
			 		treeString +
			 		"</ul></li>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});*/
			}
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chkService[i].checked==true)
			{
				if(document.getElementById('txtServiceNo'+newCounter).value=="")
				{
					alert("Pls. Enter Service For " + newCounter + " Element.");
					return false;
				}
				else
				{
					document.getElementById('browser').innerHTML= "";
				    var jsData = 
				    {
				    	serviceId:document.getElementById('txtServiceNo'+newCounter).value,
				    	orderNumber:document.getElementById('poNumber').value
				    };
				  	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
					 var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
					 
					 /////
					 treeNodes = treeView;
						var treeString="";
						var url ="";
						var nodeText ="";
						var parentNodeId;
						var commercial="";
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
							 	commercial = treeView.lstTreeItems.list[i].isCommercial;
							 	break;
							}
						}
						 
						//alert(document.forms[0].txtServiceNo1.value); 
						 
						var treeString=getTree(treeView,parentNodeId,nodeText,"",commercial);
		
					 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;
					 
					 /////////
					 /*
					var treeString="";
				  	for(var j=0;j<treeView.lstTreeItems.list.length;j++)
				  	{
						treeString = treeString + "<li><span class='file'><a href='#' onclick=fnViewProductCatelog(\'"+ treeView.lstTreeItems.list[j].treeViewURL +"\');>"+ treeView.lstTreeItems.list[j].treeViewText + "</a></span></li>"    		
				   	}	
				 	var branches = $("<li><span class='folder'>" + document.getElementById('txtServiceName'+newCounter).value + "</span><ul>" + 
				 		treeString +
				 		"</ul></li>").appendTo("#browser");
				 	$("#browser").treeview({
				 		add: branches
				 	});
					break;*/
				}
			}
		}
	}
	}catch(e)
	{
		alert('error code 19: '+ e.message);
	}
}
//-------[020] Start
//function wrriten for publishing in edit mode through work queue approval page: by anil
function fnPublish(){
//raghu
	//var sessionid ='<%=request.getSession().getId() %>';
    var orderNo=document.getElementById('poNumber').value;
    //var sessionid ='<%=request.getSession().getId() %>';
	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    //var errorMessege = jsonrpc.processes.validateSassProduct(orderNo,sessionid);
   // if(errorMessege != 'SUCCESS')
   // {
   //  alert(errorMessege);
   //  return false;
    //}
    //raghu
	
  // var test = jsonrpc.processes.validateFeild(orderNo,sessionid);
  // if(test != 'SUCCESS')
  // {
   //  alert(test);
 //    return false;
  // }
  
  //end raghu
	//[250511AKS]Start
    //start [058] commented
	/*validateOrder('1');
	var isValidatePO =	document.getElementById("isValidatePO").value;	
	//if Validate successfully
	if(isValidatePO==0)
	{
		return false;
	}*/
	//end [058]
	//[250511AKS]End
		
		 document.getElementById('roleID').value = gb_roleID;
		 document.getElementById('empID').value=gb_empID;
	// called by Saurabh for partial publish view
	var path = gb_path + "/NewOrderAction.do?method=displayPartailPublish&poNumber="+ document.getElementById('poNumber').value;
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	
}
//function wrriten for publishing in edit mode through work queue approval page: by anil
// name changed by saurabh to view partial publish  
function publishAfterViewPartialPublish(serviceList,publishList){
	var poNum=document.searchForm.poNumber.value;
	var myDummyForm=createDummyForm(gb_path + "/viewOrderAction.do");
	attachHiddenField(myDummyForm,"methodName","fnPublishOrder");
	attachHiddenField(myDummyForm,"publishChangeOrd","NEW");
	attachHiddenField(myDummyForm,"PONum",poNum);
	attachHiddenField(myDummyForm,"serviceList",serviceList);
	attachHiddenField(myDummyForm,"publishList",publishList);
	attachHiddenField(myDummyForm,"roleid",gb_roleID);
	showLayer();
	myDummyForm.submit();
	/*	
	//alert("Data of Order Entry will be inserted in Fx billing engine.");
	var myForm=document.getElementById('searchForm');
    var poNum=document.searchForm.poNumber.value;
    //alert(poNum);
	var url = "<%=request.getContextPath()%>/viewOrderAction.do?methodName=fnPublishOrder&publishChangeOrd=NEW";
	url = url + "&PONum="+poNum;
	myForm.action=url;
	showLayer();
	myForm.submit();*/
}
//-------[020] End
//function wrriten for billing trigger in edit mode through work queue approval page: by anil

function fnBillingTrigger3(chiWindow)
{
              // alert(2);
               chiWindow.close();
               fnBillingTrigger();
}
function fnBillingTrigger()
{
	poNum=document.searchForm.poNumber.value;
	var url = gb_path+"/viewOrderAction.do?methodName=fnBillingTrigger";
	{
	var da=new Date();
		url = url + "&orderNo="+poNum+"&"+da;
		//features="scrollbars=yes,resizable=yes,width=800,height=540,left=100,top=100";
		//window.open(url,'xyz',features);
		window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}
//function wrriten for submit task action in edit mode through work queue approval page: by anil
function submitParent()
{

	var myForm=document.getElementById('searchForm');
	var modeValue="editON";
	poNumber=document.getElementById('poNumber').value;
	//myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+poNumber+"&modeName="+modeValue;
	myForm.action=gb_path+"/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}
function popService(counter) 
{
	poNumber=document.getElementById('poNumber').value;
	//vijay start	
		var totalServiceMessage = jsonrpc.processes.totalServiceCountCheck(poNumber,1);
		if(totalServiceMessage!= '' ){
			alert(totalServiceMessage);
			return false;
		}
	//vijay end	
	var path = gb_path+"/selectservice.do?method=fetchServiceType&hdnServiceCounter="+ counter +"&orderNo="+poNumber+"&roleID="+gb_roleID+"";
   	window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:650px");
}

function validateSource(){
	var sName=document.getElementById('sourceName').value;
		if(sName == "CRMORDER"){
			document.searchForm.txtquotesNo.value="";		
			document.searchForm.quoteNo.readOnly=true;
			document.getElementById('txtQuoteNo').style.display='block';  
			document.getElementById('cboQuoteNo').style.display='none';			
		}
		if(sName == "CRMQUOTE"){	
			document.searchForm.quoteNo.readOnly=false;		
			document.getElementById('txtQuoteNo').style.display='none';  
			document.getElementById('cboQuoteNo').style.display='block';			
		}
		if(sName == "0"){	
			document.searchForm.txtquotesNo.value="";		
			document.searchForm.quoteNo.readOnly=true;		
			document.getElementById('txtQuoteNo').style.display='block';  
			document.getElementById('cboQuoteNo').style.display='none';			
		}
}

function popitup(url){
		var poNumber=document.getElementById('poNumber').value;	    	
	    var stage = document.getElementById("stageName").value;
	if(url=="closeorder"){		
		//var myForm=document.getElementById('searchForm');
		//myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
		var myDummyForm=createDummyForm(gb_path+"/defaultAction.do");
		attachHiddenField(myDummyForm,"method","goToHomeAfterClosing");
		showLayer();
		myDummyForm.submit();
	}
	if(url=="SelectOpportunity")
	{
	
		
		var orderNo = 	document.getElementById('poNumber').value;
		var accountId = document.getElementById('accountID').value;		
		var stageName =	document.getElementById('stageName').value;
		
		var path = gb_path+"/NewOrderAction.do?method=fetchOpportunity&orderNo="+orderNo+"&accountID="+accountId+"&stageName="+stageName;		
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:720px");
		
	}if(url=="SelectAccount"){
		 $("#selectAccountDialog").dialog({
       	 	height: 530,
       	 	width: 900,
       	 	minHeight:530,
            autoOpen: false,
        	modal: true,
        	closeOnEscape: true,
        	title: "Select Account",
        	close: function() { 
				jQuery.noConflict();
				$("#selectAccountDialog").empty();
			}
    	});
    	$("#selectAccountDialog").load(gb_path+"/NewOrderAction.do?method=fetchAccount");
		$('#selectAccountDialog').dialog('open');
		return;
	}if(url=="SelectCurrency"){
		var path = gb_path+"/NewOrderAction.do?method=fetchCurrency";		
		window.showModalDialog(path,window,"status:false;dialogWidth:750px;dialogHeight:450px");
		return;
	}if(url=="SelectSource"){
		var path = gb_path+"/NewOrderAction.do?method=fetchSource";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		var sName=document.getElementById('sourceName').value;
		if(sName == "CRMORDER"){
			document.getElementById('txtQuoteNo').style.display='block';  
			document.getElementById('cboQuoteNo').style.display='none';
		}else{			
			document.getElementById('txtQuoteNo').style.display='none';  
			document.getElementById('cboQuoteNo').style.display='block';			
		}
		return;
	}
	if(url=="SelectChannelPartner"){
		var path = gb_path+"/NewOrderAction.do?method=fetchChannelPartner";		
		window.showModalDialog(path,window,"status:false;dialogWidth:1150px;dialogHeight:500px");		
			//document.getElementById('txtQuoteNo').style.display='none';  
			//document.getElementById('cboQuoteNo').style.display='block';			
		
		return;
	}
		
	
	
	if(url=="FileAttachment"){//add [006]
	    if(document.getElementById('accountID').value=="" || document.getElementById('poNumber').value==""){
	    	alert('Please Select an Account or Order No Before Attaching a File');	    	
	    	return false;
	    }else{
	    	var width  = 700;
 			var height = 300;
 			var left   = (screen.width  - width)/2;
 			var top    = (screen.height - height)/2;
 			var params = 'width='+width+', height='+height;
 				params += ', top='+top+', left='+left;
 				params += ', directories=no';
 				params += ', location=no';
 				params += ', menubar=no';
 				params += ', resizable=no';
 				params += ', scrollbars=yes';
 				params += ', status=no';
 				params += ', toolbar=no';
	    	
	    	var accountNumber=document.getElementById('accNo').value;
	    	var orderNumber=document.getElementById('poNumber').value;
			var path = gb_path+"/NewOrderAction.do?method=goToFileAttachmentPage&orderNo="+orderNumber+"&accountNo="+accountNumber+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod;	
			window.open(path,"FileAttachmentWindow",params); 						
		}
		return;
	}
	//end [006]
		//Rakshika : code Start
	if(url=="UploadedFile")
	{
	    if(document.getElementById('accountID').value=="" || document.getElementById('poNumber').value=="")
	    {
	    	alert('Please Select an Account or Order No Before Dowloading a File');	    	
	    	return false;
	    }
	    else
	    {
	    	var width  = 1000;
 			var height = 600;
 			var left   = (screen.width  - width)/2;
 			var top    = (screen.height - height)/2;
 			var params = 'width='+width+', height='+height;
 				params += ', top='+top+', left='+left;
 				params += ', directories=no';
 				params += ', location=no';
 				params += ', menubar=no';
 				params += ', resizable=no';
 				params += ', scrollbars=yes';
 				params += ', status=no';
 				params += ', toolbar=no';
 				params += ', status=yes';
	    	
	    	var accountNumber=document.getElementById('accountID').value;
	    	var orderNumber=document.getElementById('poNumber').value;
	    	var crmAccountNo=document.getElementById('crmAccountNo').value;
	    	
			var path = gb_path+"/NewOrderAction.do?method=goToDownloadedFilePage&orderNo="+orderNumber+"&accountNo="+accountNumber+"&crmAccountNo="+crmAccountNo+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod;	
			window.open(path,"FileDownloadWindow",params); 						
		}
		return;
	}
	//Rakshika : code end
	//if (window.focus) {window.focus()}
	//return false;
	
	//Start : Anil for GAM
	if(url=="selectGAM")
	{	
		//lawkush Changed for disabling GAM icon and no user function on onClick when  main tab is not saved
			document.getElementById('hdnShowAttachIcon').value=gb_AttachIconAttribute;	
			if(document.getElementById('hdnShowAttachIcon').value=="NO")
			{
				return false;
			}
		//lawkush Changed for disabling GAM icon and no user function on onClick when  main tab is not saved
	    var quoteNo=document.getElementById('txtquotesNo').value;	    				    			   	
	    var accountNumber=document.getElementById('accNo').value;
	    var orderNumber=document.getElementById('poNumber').value;	    	
	    var stage = document.getElementById("stageName").value;
	    var createdByRole = document.getElementById("createdByRole").value;
	    
	
	    var isInView=0;	
	    if(!((stage=="AM" && createdByRole=="1") || (stage=="New" && createdByRole=="1") || (stage=="PM" && createdByRole=="2") || (stage=="COPC" && createdByRole=="3") || (stage=="SED" && createdByRole=="4")))
	    {
	    isInView=111;	
	    }
		var path = gb_path+"/NewOrderAction.do?method=goToViewGAM&orderNo="+orderNumber+"&accountNo="+accountNumber+"&quoteNo="+quoteNo+"&isInView="+isInView+"&stage="+stage;									
		window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:550px"); 	    
		//window.open(path);
		return;
	}
	//End : Anil for GAM
	
	if(url=="approvalHistory")
	{
		var path = gb_path+"/NewOrderAction.do?method=fetchApprovalOrderHistory&poNumber="+ poNumber +"&stage="+stage+"";		
		window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px"); 	 
		return;   
	}	
	

}
//below popup method is used for Copying Contact details of previous 10 orders of same account
function popContact()
{
	var accountNumber=document.getElementById('crmAccountNo').value;
	var poNumber=document.getElementById('poNumber').value;	
	    
	var path = gb_path+"/NewOrderAction.do?method=fetchContact&poNumber="+ poNumber +"&accountNumber="+accountNumber;		
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:530px"); 	
	/*
	
		 $("#selectAccountDialog").dialog({
       	 	height: 530,
       	 	width: 900,
            autoOpen: false,
        	modal: true,
        	closeOnEscape: true,
        	title: "Select Account",
        	close: function() { 
				jQuery.noConflict();
				$("#selectAccountDialog").empty();
			}
    	});
    	$("#selectAccountDialog").load(gb_path+"/NewOrderAction.do?method=fetchContact&poNumber="+ poNumber +"&accountNumber="+accountNumber);
		$('#selectAccountDialog').dialog('open');
		return;
	
	*/
}

function cellValidation(abc)
{	
	/*if(abc.value.length != 10)
		{
			alert("Cell no. should be 10 digit");			
			abc.focus();
			return false;
		} */
	if(abc.value.length > 0) 
	{
		
		if(checknumber(abc)==false)
		{
			abc.value='';
			abc.focus();
			return false;
		
		}
	}
	
}

function popContactType(count) 
{
     conatctTab = document.getElementById('tableContact');
	 addressTab = document.getElementById('tableAddress');
     var rowlen= conatctTab.rows.length;
	var path = gb_path+"/NewOrderAction.do?method=fetchContactType&counter="+count;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
 }
 function popSalutationList(count) 
{
	
	var path = gb_path+"/NewOrderAction.do?method=fetchSalutationList&counter="+count;			
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
 }
 function popCountryList(count) 
{  		
	var stateCode=document.getElementById("stateCode"+count).value;	
	if(stateCode=="")
   	{
    	alert("Please insert state first!!");
    	return false;     
   	}
	var path = gb_path+"/NewOrderAction.do?method=fetchCountryList&counter="+count+"&stateCode="+stateCode;		
	window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:500px");
 }
 function popEntitySelection(count) 
{
	var path = gb_path+"/NewOrderAction.do?method=getEntityList&counter="+count;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	//window.open(path);
 }
 function popStateList(count) 
{   	
   //var countyCode=document.getElementById("countyCode").value;
   var cityCode=document.getElementById("cityCode"+count).value;   
   if(cityCode=="")
   {
    alert("Please insert city first!!");
    return false;
     
   }
	//var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchStateList&counter="+count+"&countyCode="+countyCode;	
	var path = gb_path+"/NewOrderAction.do?method=fetchStateList&counter="+count+"&cityCode="+cityCode;	
	window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:500px");
}
//below method changed from document.getElementById to both byId and by Form to handle single row if previous rows are deleted
function autoFill(count)
{
	var myForm=document.getElementById('searchForm');
	var controlCityName;
	var controlCityCode;
	var controlStateName;
	var controlStateCode;
	var controlCountryName;
	var controlCountryCode;
	var controlPinNo;
	var controlPinCode;
	
    if(myForm.salutationName.length==undefined)
    {
    	controlCityName=myForm.cityName;
		controlCityCode=myForm.cityCode;
		controlStateName=myForm.stateName;
		controlStateCode=myForm.stateCode;
		controlCountryName=myForm.countyName;
		controlCountryCode=myForm.countyCode;
		controlPinNo=myForm.pinNo;
		controlPinCode=myForm.pinCode;
    }
 	else
 	{
 		controlCityName=document.getElementById('cityName'+count);
 		controlCityCode=document.getElementById('cityCode'+count);
 		controlStateName=document.getElementById('stateName'+count);
		controlStateCode=document.getElementById('stateCode'+count);
		controlCountryName=document.getElementById('countyName'+count);
		controlCountryCode=document.getElementById('countyCode'+count);
		controlPinNo=document.getElementById('pinNo'+count);
		controlPinCode=document.getElementById('pinCode'+count);
	}

	var string1 = controlCityName.value;
	var flag = string1.search('%');
 
 	if(flag != -1)	
 	{
 		if(controlCityName.value == "%")
 		{
 			popCityList(count);
 		}
 	
 		else
 		{	
 		
 		var jsData =
		{								
			cityName:controlCityName.value
		};				  
		var lstService = jsonrpc.processes.searchCityName2(jsData);
	
		if(lstService.list.length == 1)
 		{
 			controlCityName.value=lstService.list[0].cityName;
 			controlCityCode.value=lstService.list[0].cityCode;
 			
 			if($('#hdnCheckTab').val()=="0")
 			{
	 			controlPinNo.value="";
		 		controlPinCode.value="";
 			}
 				
 		
	 		if(controlCityCode.value != "")
	 		{ 		
		 		var jsData1 =
				{				
					cityCode:controlCityCode.value				
				};			
				var service2 = jsonrpc.processes.searchStateName1(jsData1);
				if(service2.list.length == 1)
		 		{
		 		controlStateName.value=service2.list[0].stateName;
		 		controlStateCode.value=service2.list[0].stateCode;
		 		
		 		}
		 		if(service2.list.length == 0)
		 		{
		 		controlStateName.value="";
		 		controlStateCode.value="";
		 		controlCountryName.value="";
		 		controlCountryCode.value="";
		 		controlPinNo.value="";
		 		controlPinCode.value="";	 		
		 		}
			}
 	
 			if(controlStateCode.value != "")
	 		{ 		
	 			var jsData1 =
				{				
					stateCode:controlStateCode.value				
				};			
				var service3 = jsonrpc.processes.searchCountryName1(jsData1);
				if(service3.list.length == 1)
		 		{
		 		controlCountryName.value=service3.list[0].countyName;
		 		controlCountryCode.value=service3.list[0].countyCode;
		 		
		 		}
		 		if(service3.list.length == 0)
		 		{
		 		controlCountryName.value="";
		 		controlCountryCode.value="";
		 		
		 		}
			}
		
					 		
 		}
 			else if(lstService.list.length == 0)
 			{
 					alert('NO RECORD FOUND');
 					controlCityName.value="";
 					controlCityCode.value="";
 					controlStateName.value="";
		 			controlStateCode.value="";
		 			controlCountryName.value="";
		 			controlCountryCode.value="";
		 			controlPinNo.value="";
		 			controlPinCode.value="";	 			 
 					return false;
 			}
		 		else
		 		{
		 		popCityList(count);
		 		}
 		}
 	}
 	else
 	{
 		if(string1==""){
 				controlStateName.value = "";
				controlStateCode.value = "";
				controlCityCode.value = "";
				controlCountryName.value = "";
				controlCountryCode.value = "";
				controlPinNo.value="";
		 		controlPinCode.value="";	 		
	 			
 		}
 		else
 		{
 		
 			var jsData =
			{								
				cityName:controlCityName.value
			};				  
			//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			var lstService = jsonrpc.processes.searchCityName1(jsData);
		 	if(lstService.list.length == 0)
		 	{
		 		alert('No record found');
		 		controlStateName.value = "";
				controlStateCode.value = "";
				controlCityCode.value = "";
				controlCountryName.value = "";
				controlCountryCode.value = "";
		 		controlCityName.value = "" ;
		 		controlPinNo.value="";
		 		controlPinCode.value="";	 		
		 		return false;
		 	}
		 	if(lstService.list.length == 1)
		 	{
		 		controlCityName.value=lstService.list[0].cityName;
		 		controlCityCode.value=lstService.list[0].cityCode;
		 		if($('#hdnCheckTab').val()=="0")
 				{
	 				controlPinNo.value="";
		 			controlPinCode.value="";
 				}
		 	
			 	if(controlCityCode.value != "")
			 	{ 		
		 		var jsData1 =
					{				
						cityCode:controlCityCode.value				
					};			
				var service2 = jsonrpc.processes.searchStateName1(jsData1);
				if(service2.list.length == 1)
		 		{
		 		controlStateName.value=service2.list[0].stateName;
		 		controlStateCode.value=service2.list[0].stateCode;
		 		
		 		}
		 		if(service2.list.length == 0)
		 		{
		 		controlStateName.value="";
		 		controlStateCode.value="";
		 		controlCountryName.value="";
		 		controlCountryCode.value="";
		 		controlPinNo.value="";
		 		controlPinCode.value="";	 		
		 		}
			}
 	
		 	if(controlStateCode.value != "")
		 	{ 		
		 		var jsData1 =
				{				
					stateCode:controlStateCode.value				
				};			
				var service3 = jsonrpc.processes.searchCountryName1(jsData1);
				if(service3.list.length == 1)
		 		{
		 			controlCountryName.value=service3.list[0].countyName;
		 			controlCountryCode.value=service3.list[0].countyCode;
		 		}
		 		if(service3.list.length == 0)
		 		{
		 			controlCountryName.value="";
		 			controlCountryCode.value="";
		 		}
			}
		}	
	 }					
  }
}
 function popCityList(count) 
{
  
  /*var stateCode=document.getElementById("stateCode").value;
   if(stateCode=="")
   {
    alert("Please insert State first!!");
    return false;
     
   }*/
	var path =gb_path+"/NewOrderAction.do?method=fetchCityList&counter="+count;		
	window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:500px");
 }
 //Start[021]
 //---[012]---Start
 function DateValidation(rfsdate , feildName )
   {
   
   var count=document.getElementById('attCount').value;
  
		for(i=1;i<=count;i++)
		{
			
			
   				if(document.getElementById('IsRequired_'+i).value=="1")
   				{
   					
  					
   					 if(rfsdate!="")
   	  					{
   	  					    if(feildName=='Date CAF Filled By Customer')
   	  					    {
    						if(dateCompare(rfsdate,currentDate)==1)
								{			
									alert(feildName + " ! Cannot be future Date!!!");
									return false;
								} 
	    				}
	  				 }
					 else
	 					{
	 						
		  					if(document.forms[0].AttributeVal_1.value=="")
		  						{
		   							alert("Please enter RFS Date!!!");
		   							document.forms[0].AttributeVal_1.focus();//[038]           05-06-12
		   							return false;
		   						}
		  					else if(document.forms[0].AttributeVal_7.value=="")
		   						{
		   						alert("Please enter Date CAF Filled By Customer!!!");
		   						document.forms[0].AttributeVal_7.focus();//[038]         05-06-12
		   						return false;
		   						}
	 					 }
				}
	 			else
				 {
	 				return true;
	 		     } 
         }		
			
  }	
  //---[012]---End
  //End[021]
  //---[016] Start
  
  function popPinList(count) 
{
  
  var cityCode=document.getElementById("cityCode"+count).value;
  var stateCode=document.getElementById("stateCode"+count).value;
  
 if(cityCode == "")
   {
    alert("Please insert City first!!");
    return false;
     
   }
 if(stateCode=="")
   {
    alert("Please insert State!!");
    return false;
     
   }
   var path = gb_path+"/NewOrderAction.do?method=fetchPinList&counter="+count+"&cityCode="+cityCode+"&stateCode="+stateCode;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
 }
 //---[016] End
function validate(){

    var flag=0;
	var modeValue=gb_ModeValue;
	var	newFormOpened;
	
	var stage = document.getElementById("stageName").value;
	//	[045]	Start
	var orderNoValid = document.getElementById("poNumber").value;
	var chkIsDemo = document.getElementById('chkIsDemo');
	        
	
	if((stage == 'New' && orderNoValid != 0 && orderNoValid!= '') && (!chkIsDemo.checked)){
		var segment = document.getElementById("lob").value;
		var sourceName = document.getElementById("sourceName").value;		
		if(segment == 'AES Corporate' && sourceName == 'CRMORDER'){
			alert('Please Select Opportunity.');
			return false;
		}
	}	
	//	[045]	End
	//Started by Saurabh for SED role check
	if(stage=="SED" || stage=="Partial Publish" || stage=="Billing Trigger"){
		alert("You are not authorised to use this functionality");
		return false;
	}
	//End By Saurabh
	
	if(document.getElementById('hdnTabVal').value==1 /*|| document.getElementById('hdnTabValchannelmaster').value==1*/){
		/*//[007] Start
		//if((document.getElementById('regionID').value=="0") )
		//{
		//	alert("Please Select  Region!!");
		//	return false;
		//}
		//if((document.getElementById('zoneID').value=="0") )
		//{
		//	alert("Please Select  Zone!!");
		//	return false;
		//}
		//[007] End
		//Start[014]*/
		var projectMan = document.getElementById('projectManager');
		var accNo = document.getElementById('accNo');
		var accName = document.getElementById('accountName');
		var accMan = document.getElementById('accountManager');
		var lob = document.getElementById('lob');
		if(accNo.value=="" || accName.value==""	|| accMan.value=="" || projectMan.value=="" || lob.value==""){
			alert("Please select an A/C No!!");
			document.getElementById('crmAccNo').focus();//[038]      05-06-12
			return false;
		}

		if(projectMan.value=='0'){
			alert("Please select Project Mgr!!");
			projectMan.focus();
			return false;
		}
		
	//[049] Start
		try{
			if(document.getElementById('shortCode').value != undefined) {
				if(document.getElementById('shortCode').value == "null" || trim(document.getElementById('shortCode').value) ==""){	     	
				alert("Short code not present with this Account!!");			
				return false;
				}	
			}
		}
		catch(error){
			if (document.getElementById('m6ShortCode').value != undefined )
		 	{
				if(document.getElementById('m6ShortCode').value == "null" || trim(document.getElementById('m6ShortCode').value) ==""){	     	
					alert("Short code not present with this Account!!");			
					return false;
				}	
			}
		}	
	//[049] End	
		//End [014]
		var orderType = document.getElementById('orderType');
		if(orderType.value==""){
			alert("Please input Order Type!!");
			orderType.focus();//[038]          05-06-12
			return false;
		}
		var orderDate = document.getElementById('orderDate');
		if(orderDate.value==""){
			alert("Please input Order Date!!");
			orderDate.focus();//[038]           05-06-12
			return false;
		}
		var sourceName = document.getElementById('sourceName');
		if(sourceName.value==0){
			alert("Please select Source!!");
			sourceName.focus();//[038]          05-06-12
			return false;
		}
		var curShortCode = document.getElementById('curShortCode');
		if(curShortCode.value==""){
			alert("Please select Currency!!");
			document.getElementById('curShortCode').focus();//[038]         05-06-12
			return false;
		}else{
			var flag = (curShortCode.value).search('%');
			if(flag != -1){
				alert("Please enter Right Currency") ;
				return false;
			}else if(autoFillCurrency()==false){
					return false;
			}
		}		
	
		/*if(document.getElementById('opportunityId').value=="0")
		{				
			alert("Please select Opportunity Id!!");
			document.getElementById('opportunityId').focus();
			return false;
		}
		//document.getElementById('txtquotesNo').value ="0";
		if(document.getElementById('sourceName').value=="CRMORDER"){
			//if(document.getElementById('txtquotesNo').value=="") 
			//{
				//alert("Please Input Quote Number!!");
				//return false;
			//}
		}
		else*/
		var quoteNo = document.getElementById('quoteNo');
		if(sourceName.value!="CRMORDER"){	
		//Start[014]		
			if(quoteNo.value=="0"){				
				alert("Please select Quote No!!");
				quoteNo.focus();
				return false;
			}
		//End[014]
		}
		var zone = document.getElementById('zone');
		if(zone.value==""){
			alert("Please Select Zone!!");
			zone.focus();
			return false;
		}
		var chkIsDemo = document.getElementById('chkIsDemo');
		var noOfDaysForDemo = document.getElementById('noOfDaysForDemo');
		if(chkIsDemo.checked){
			if(noOfDaysForDemo.value==""){
				alert("Please Input No of Days for Demo Order");
				noOfDaysForDemo.focus();
				return false;
			}
		}
	//Start[023]	
		if(document.getElementById('isUrgent').value=='on'){
			document.searchForm.isUrgent.value=1;
		}else{
			document.searchForm.isUrgent.value=1;
		}
	//End[023]	
		var countAttributes=document.getElementById('attCount').value;
		for(i=1;i<=countAttributes;i++){
			var hdnAttributeExpectedValue = document.getElementById('hdnAttributeExpectedValue_'+i);
			var attributeVal = document.getElementById('AttributeVal_'+i);
			var hdnAttributeName = document.getElementById('hdnAttributeName_'+i);
			if(hdnAttributeExpectedValue.value=="datetime"){
				if(DateValidation(attributeVal.value , hdnAttributeName.value)==false){
					return false;
				}					
				if(document.forms[0].AttributeVal_1.value==""){
					alert("Please enter RFS Date!!!");
					document.forms[0].AttributeVal_1.focus();//[038]           05-06-12
					return false;
				}
				//[040] Start
				/*if("document.forms[0].AttributeVal_"+i+".value"=="document.forms[0].AttributeVal_1.value") 
		  		{
					if(dateCompare(document.forms[0].AttributeVal_1.value,currentDate)=="-1")
					{			
						alert("PO RFS Date should not be Previous Date!!!");
					} 
				}*/
				//[040] End	
			}
			//---[012]---Start
			var isRequired = document.getElementById('IsRequired_'+i);
			isRequired.value=attributeVal.isRequired;
			//---[012]---Start
			//Start[021]
			if(hdnAttributeExpectedValue.value=="YN"){
				if(attributeVal.value=="" && isRequired.value=="1"){
					alert("Please input Extended Attributes Details!!");
					attributeVal.focus();
					return false;
				}
			}
			if(hdnAttributeExpectedValue.value=="NUMERIC"){
				
				if(attributeVal.value=="" && isRequired.value=="1"){
					alert("Please Input Phone no!!");
					attributeVal.focus();
					return false;
				}
			}
			if(hdnAttributeExpectedValue.value=="email"){
				if(attributeVal.value=="" && attributeVal.value=="1"){
					alert("Please input ActMgr EmailID !!");
					attributeVal.focus();
					return false;
				}
			}
			//End[021]	
			//---[014]--End
				/*if(document.getElementById('Mandatory_'+i).value=="Y")
			{ 
				if(document.getElementById('AttributeVal_'+i).value=="")
				{
					alert("Please Input Extended Attributes Details!!");
					document.getElementById('AttributeVal_'+i).focus();
					return false;
				}
			} */
		
			if(hdnAttributeExpectedValue.value=="VARCHAR" || hdnAttributeExpectedValue.value=="varchar" ){
			
				if(document.getElementById('AttributeVal_'+i).value!="")
				{
					if(ValidateTextField(document.getElementById('AttributeVal_'+i) ,path,'')==false){
						document.getElementById('AttributeVal_'+i).focus();
						return false;
					}
				}
			}
			
			if(hdnAttributeExpectedValue.value=="NUMERIC" || hdnAttributeExpectedValue.value=="numeric" ){
				
				if(document.getElementById('AttributeVal_'+i).value!="")
				{
					
					if(checkdigits(document.getElementById('AttributeVal_'+i))==false){	
						document.getElementById('AttributeVal_'+i).focus();
						return false;
					}
					
				}
			}	
			
		}
		//[061] Start PROJECT SATYAPAN
		var status = ispTagValidation();
		if(status != true)
			return false;
		//[061] End PROJECT SATYAPAN
		
		var status = channelPartnerTagValidation();
    	if(status != true)
    		return false;
		
	    flag=2;
		var myForm=document.getElementById('searchForm');
	    myForm.action=gb_path+"/NewOrderAction.do?method=insertUpdateMain&modeName="+modeValue+"&flag="+flag+"&newFormOpened=Contact";
	 
	    showLayer();
		myForm.submit();
	
		document.getElementById('attachIcon').style.display="block";
		document.getElementById('saveIcon').style.display="block";
	}
	else if(document.getElementById('hdnTabVal').value ==3  /*||   document.getElementById('hdnTabValchannelmaster').value ==3*/)
	{
	    	var myForm=document.getElementById('searchForm');
	    	var poTable = document.getElementById('tablePO');
	    	var poSelectionList ="";
	    	var poDate1=myForm.poDate.value;
	    	var poReceiveDate=myForm.poReceiveDate.value;
	    	var rowCounter = 1;
			 if(poTable.rows.length==1)
			 {
				alert('Atleast 1 row should be added in PO Detail');
				return false; 	
			 }
	    	
	    		   if(poTable.rows[1].style.display=='none')
	    	           {
		     		      poTable.deleteRow(1); 
		     		   }
		    
	    	else
	    	{
		    	for (i = 0 ; i < poTable.rows.length - 1; i++,rowCounter++)
		        {
		          if(poTable.rows.length==1)
		          {
			     	  if(myForm.chkSelectPODetail.checked==true)
			     	  {
			     	  	poTable.deleteRow(1); 
			     	  }
		          }
		          else
		          {	
			     	  if(poTable.rows[rowCounter].style.display=='none')
			     	  {
			     	  	poTable.deleteRow(rowCounter); 
			     	  	i=-1;
			     	  	rowCounter=0;
			     	  }
		     	  }	
		     	}
	     	}
			var ischkd= false;
			for (i = 1; i <= poTable.rows.length - 1; i++) {
			
			if(document.forms[0].defaultPO.length==undefined)
			{
			   
				if(document.getElementsByName('defaultPO')[0].checked==true){
				 
					poSelectionList= poSelectionList + 1 + ',';
					ischkd = true;
				}else {
					poSelectionList = poSelectionList+ 0 + ',';
				}
			}
		else
		{
				if(document.getElementsByName('defaultPO')[i-1].checked==true){
				 
					poSelectionList= poSelectionList + 1 + ',';
					ischkd = true;
				}else {
					poSelectionList = poSelectionList+ 0 + ',';
				}
				
			}
			}
			if(ischkd == false){
			alert('Choose Default PO for the Order');
			return false; 	
			}
			
			myForm.selectedPODetails.value= poSelectionList;
			
			//[061] Start PROJECT SATYAPAN
			var status = ispTagValidation();
			if(status != true)
				return false;
			//[061] End PROJECT SATYAPAN
			
			var status = channelPartnerTagValidation();
	    	if(status != true)
	    		return false;
			
	    var val = validatePoDetails();
	    if (val == true)
	    {
	        flag=5;
	    	var poNumber=document.getElementById('poNumber').value;	    	
	    	myForm.action=gb_path+"/NewOrderAction.do?method=insertUpdatePODetails&modeName="+modeValue+"&orderNo="+poNumber+"&flag="+flag+"&newFormOpened=Product Catelog";
	    	showLayer();
			myForm.submit();
			document.getElementById('attachIcon').style.display="block";
			document.getElementById('saveIcon').style.display="block";
	    }
	    else
	    {
	    	return false;
	    }
	}
	else if(document.getElementById('hdnTabVal').value==2 /*|| document.getElementById('hdnTabValchannelmaster').value==2*/){
		var countContact=document.getElementById('hdnContactCount').value;
		var countAddress=document.getElementById('hdnAddressCount').value;
		if(countContact != countAddress ){
			alert("Contact And Address Rows Should be Equal");
			return false;
		}
        var myForm=document.getElementById('searchForm');
    	var contactTable = document.getElementById('tableContact');
    	var addressTable = document.getElementById('tableAddress');
    	var rowCounter = 1;
    	var rowlen=contactTable.rows.length;
    	if(rowlen==1){
			alert("please enter one row");
			return false;
	    }else{
			/*Puneet Commenting for performance tuning
			for (i = 1 ; i <= contactTable.rows.length - 1; i++,rowCounter++){
				if(contactTable.rows.length==1){
					if(contactTable.rows[1].style.display=='none'){
						contactTable.deleteRow(1); 
				     	addressTable.deleteRow(1);
				     	rowCounter=0;
				     	i=-1;
			    	}
				}else{	
					if(contactTable.rows[rowCounter].style.display=='none'){
			     	  	contactTable.deleteRow(rowCounter); 
			     	  	addressTable.deleteRow(rowCounter); 
			     	  	i=0;
			     	  	rowCounter=0;
					}
				}	
			}*/
			if(contactTable.rows[1].style.display=='none'){
						contactTable.deleteRow(1); 
				     	addressTable.deleteRow(1);
	    	}else{
	    		for (i = 1 ; i <= contactTable.rows.length - 1; i++,rowCounter++){
					if(contactTable.rows[rowCounter].style.display=='none'){
			     	  	contactTable.deleteRow(rowCounter); 
			     	  	addressTable.deleteRow(rowCounter); 
					}
				}
			}
		}
	    /*Puneet Commenting as it is duplicate code
	    if(contactTable.rows.length ==1){
		   	alert('Please first add contact & address details !!');
			return false;
	    }*/
    	//[061] Start PROJECT SATYAPAN
    	var status = ispTagValidation();
    	if(status != true)
    		return false;
    	//[061] End PROJECT SATYAPAN
    	
    	var status = channelPartnerTagValidation();
    	if(status != true)
    		return false;
    	
		counter = parseInt(countContact);
		var i;
		var val = validateContactDetails();
	    if (val == true){
	        flag=3;
	    	var poNumber=document.getElementById('poNumber').value;	    	
	    	myForm.action=gb_path+"/NewOrderAction.do?method=insertContactTabDetail&modeName="+modeValue+"&orderNo="+poNumber+"&flag="+flag+"&newFormOpened=PO Details";
	    	showLayer();
	    
			myForm.submit();
			document.getElementById('attachIcon').style.display="block";
			document.getElementById('saveIcon').style.display="block";
//			newFormOpened='Product Catelog';
	    }else{
	    	return false;
	    }
	}
}

function AddPO() 
{
	var account = parseInt(document.getElementById('accNo').value);
	var counter= parseInt(document.getElementById('hdnPOCount').value);
	document.getElementById('hdnPOCount').value = counter+1;
	var str;
	var rowlen = document.getElementById('tablePO').rows.length;
	var tblRow=document.getElementById('tablePO').insertRow();
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col1 toprow";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='chkSelectPODetail' id='chkSelectPODetail"+counter+"' type='checkbox' onclick='unCheckedPOMaster();' >";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' isRequired='1' onfocus='populatePODemoContractPeriod("+counter+")' name='custPoDetailNo' maxlength='50' id='custPoDetailNo"+counter+"' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Cust Po Detail Number')};\">";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	// [003]	Start 
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' class='inputBorder1' showcal='date' name='custPoDate' isRequired='1' id='custPoDate"+counter+"' value='"+ currentDate +"' readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};' >&nbsp;<font size='1'><a href=\"javascript:show_calendar(searchForm[0].custPoDate"+ counter +");\" class=\"borderTabCal\"><img src= '/IOES/images/cal.gif' border='0px' alt='Loading...'></a></font>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	// [001]	End 
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col2";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' size='12' readonly='true' class='inputDisabled' name='poDetailNo' id='poDetailNo"+counter+"'>";
		str = str + "<input type='hidden' name='poDate' isRequired='0' id='poDate"+counter+"' value='"+ currentDate +"' >";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
		/*var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' class='inputBorder1' showcal='date'  name='poDate' isRequired='0' id='poDate"+counter+"' value='"+ currentDate +"' readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};' >&nbsp;<font size='1'><a href=\"javascript:show_calendar(searchForm[0].poDate"+ counter +");\" class=\"borderTabCal\"><img src= '/IOES/images/cal.gif' border='0px' alt='Loading...'></a></font>";
	CellContents = str;
		tblcol.innerHTML = CellContents;*/		
	
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col1";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='radio' name='defaultPO' isRequired='1' class='inputBorder1' id='defaultPO"+counter+"'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<select name='entityId' id='entityId"+counter+"' style='width:225px; float:left;' width='100px' onmouseover='getTip_DD(this,this.selectedIndex,this.id)'><option value='0'>Select Legal Entity</option></select>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' size=20 type='text' class='inputBorder1' isRequired='1' name='totalPoAmt' maxlength='15' id='totalPoAmt"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' class='inputBorder2' isRequired='0' showcal='date' name='contractStartDate' id='contractStartDate"+counter+"' readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};'>&nbsp;<font size='1'><a href=\"javascript:show_calendar(searchForm[0].contractStartDate"+ counter +");\" class=\"borderTabCal\"><img src= '/IOES/images/cal.gif' border='0px' alt='Select Date'></a></font>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputDisabled' size='12' isRequired='0' showcal='date' name='contractEndDate' id='contractEndDate"+counter+"' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' style='width:50px;' isRequired='0' name='periodsInMonths' maxlength='5' id='periodsInMonths"+counter+"'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col3";
	str ="<input onmouseover='getTip(value)' showcal='date' onmouseout='UnTip()' type='text' class='inputBorder1' style='width:90px;'  isRequired='1' name='poReceiveDate' id='poReceiveDate"+counter+"' readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};' >&nbsp;<font size='1'><a href=\"javascript:show_calendar(searchForm[0].poReceiveDate"+ counter +");\" class=\"borderTabCal\"><img src= '/IOES/images/cal.gif' border='0px' alt='Loading...'></a></font>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2' name='poIssueBy' isRequired='0' maxlength='100' id='poIssueBy"+counter+"' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'IssuedBy')};\">";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2' name='poRemarks' maxlength='1000' isRequired='0' id='poRemarks"+counter+"' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Remarks')};\">";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2' name='poEmailId' maxlength='50' isRequired='0' id='poEmailId"+counter+"' onBlur='if( trim(this.value).length>0){ return emailValidate(this)};'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2' readonly='true' name='poDemoContractPeriod' isRequired='0' maxlength='5' id='poDemoContractPeriod"+counter+"' onBlur=\"if( trim(this.value).length>0){return checkdigits(this)};\">";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//[0062] start
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' isRequired='1' name='ldClause' maxlength='50' id='ldClause"+counter+"' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Ld Clause')};\">";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//[0062] end
		//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
		var lstEntity = jsonrpc.processes.getEntity1();
		var tr, td, i, j, oneRecord;  		   			
	    for(j=0;j<lstEntity.list.length;j++)
    	{        	
    		var combo = document.getElementById('entityId'+counter);	    			
	    	var opt = document.createElement("option");
	   		opt.text = lstEntity.list[j].entityName;
			opt.value = lstEntity.list[j].entityId;
			opt.title = lstEntity.list[j].entityName;
			try 
			{	
				combo.add(opt, null); //Standard				
			}
			catch(error) 
			{								
				combo.add(opt); // IE only
			}
    	}	
	
	
	var ctrlArry = new Array();
	var myForm=document.getElementById('searchForm')
	
	if(rowlen==1)
	{
		ctrlArry[0] =  myForm.custPoDetailNo;
		ctrlArry[1] = myForm.custPoDate;
		ctrlArry[2] =  myForm.poDetailNo;
			//ctrlArry[3] =  myForm.poDate;
			ctrlArry[3] =  myForm.poReceiveDate;
			ctrlArry[4] =  myForm.contractStartDate;
			ctrlArry[5] =  myForm.contractEndDate;
			ctrlArry[6] =  myForm.periodsInMonths;
			ctrlArry[7] =  myForm.totalPoAmt;
			ctrlArry[8] =  myForm.entityId;
			ctrlArry[9] =  myForm.defaultPO;
			ctrlArry[10] =  myForm.poIssueBy;
			ctrlArry[11] =  myForm.poRemarks;
			ctrlArry[12] =  myForm.poEmailId;
			ctrlArry[13] =  myForm.poDemoContractPeriod;
	
	}
	
	else
	{
		ctrlArry[0] =  myForm.custPoDetailNo[rowlen-1];
		ctrlArry[1] = myForm.custPoDate[rowlen-1];
		ctrlArry[2] =  myForm.poDetailNo[rowlen-1];
			//ctrlArry[3] =  myForm.poDate[rowlen-1];
			ctrlArry[3] =  myForm.poReceiveDate[rowlen-1];
			ctrlArry[4] =  myForm.contractStartDate[rowlen-1];
			ctrlArry[5] =  myForm.contractEndDate[rowlen-1];
			ctrlArry[6] =  myForm.periodsInMonths[rowlen-1];
			ctrlArry[7] =  myForm.totalPoAmt[rowlen-1];
			ctrlArry[8] =  myForm.entityId[rowlen-1];
			ctrlArry[9] =  myForm.defaultPO[rowlen-1];
			ctrlArry[10] =  myForm.poIssueBy[rowlen-1];
			ctrlArry[11] =  myForm.poRemarks[rowlen-1];
			ctrlArry[12] =  myForm.poEmailId[rowlen-1];
			ctrlArry[13] =  myForm.poDemoContractPeriod[rowlen-1];
			//[062]
			ctrlArry[14] =  myForm.ldClause[rowlen-1];
	}
	
	fieldRoleMappingValidation(poDetailTabFieldList,ctrlArry);
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' class='inputBorder1' name='contactId' id='contactId"+counter+"' value='0'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	setShowCal();
}

function validatePoDetails()
 {
 
	var i;
	var count = 1;
	var tabData = document.getElementById('tablePO');

 	for(i=1; i <= tabData.rows.length - 1; i++)
 	{	
 	//---[012]---Start
 			if(document.getElementsByName('custPoDetailNo')[i-1].value=="" && document.getElementsByName('custPoDetailNo')[i-1].isRequired=="1")
 			{
 				alert("Please enter Customer PO Detail Number");
 				document.getElementsByName('custPoDetailNo')[i-1].focus();
 				return false;
 				break;
 			}
 			if(document.getElementsByName('custPoDetailNo')[i-1].value!="" || document.getElementsByName('custPoDetailNo')[i-1].value!=null )
			{
				 if(ValidateTextField(document.getElementsByName('custPoDetailNo')[i-1],path,'CustomerPODetailNo')==false)
				 {
				 	document.getElementsByName('custPoDetailNo')[i-1].focus();
				 	return false;
				 	break;
				 }
			}
 			if(document.getElementsByName('entityId')[i-1].value=="0")
 			{
 				alert("Please select Legal Entity");
 				document.getElementsByName('entityId')[i-1].focus();
 				return false;
 				break;
 			}
 			// [003]	Start 
 			if(document.getElementsByName('custPoDate')[i-1].value=="" && document.getElementsByName('custPoDate')[i-1].isRequired=="1")
 			{
 				alert("Please enter Customer PO Date");
 				document.getElementsByName('custPoDate')[i-1].focus();
 				return false;
 				break;
 			}
			//[038]   start        05-06-12
 			if(document.getElementsByName('totalPoAmt')[i-1].value=="" && document.getElementsByName('totalPoAmt')[i-1].isRequired=="1")
 			{
				alert("Please enter Total PO Amount");
 				document.getElementsByName('totalPoAmt')[i-1].focus();
				return false;
				break;
				
			}if(document.getElementsByName('periodsInMonths')[i-1].value=="" && document.getElementsByName('periodsInMonths')[i-1].isRequired=="1")
			{
				alert("Please enter Period in Month");
				document.getElementsByName('periodsInMonths')[i-1].focus();
				return false;
				break;
			}
			if(document.getElementsByName('periodsInMonths')[i-1].value=="0" && document.getElementsByName('periodsInMonths')[i-1].isRequired=="1")
 			{
 				alert(document.getElementsByName('periodsInMonths')[i-1].Disp + 'Cannot be 0');
 				document.getElementsByName('periodsInMonths')[i-1].focus();
 				return false;
 				break;
 			}
 			
 			else
			{
				if(checknumber(document.getElementsByName('periodsInMonths')[i-1])==false)
				{
					document.getElementsByName('periodsInMonths')[i-1].focus();
					return false;
				
				}
			}

		if(document.getElementsByName('poReceiveDate')[i-1].value=="" && document.getElementsByName('poReceiveDate')[i-1].isRequired=="1")
			{
				alert("Please enter Po Receive Date");
				document.getElementsByName('poReceiveDate')[i-1].focus();
 				return false;
 				break;
 			}
			//[038]    END        05-06-12
 			// [003]	End 
 			//---[012]---End
			/*if(document.getElementsByName('totalPoAmt')[i-1].value=="0" && document.getElementsByName('totalPoAmt')[i-1].isRequired=="1")
 			{
 				alert(document.getElementsByName('totalPoAmt')[i-1].Disp + 'Cannot be 0');
 				document.getElementsByName('totalPoAmt')[i-1].focus();
 				return false;
 				break;
 			}*/
 			
 			//lawkush
 			//Start[021]
 			if(document.getElementsByName('poIssueBy')[i-1].value=="" && document.getElementsByName('poIssueBy')[i-1].isRequired=="1")
 			{
 				alert("Please enter Issued By");
 				document.getElementsByName('poIssueBy')[i-1].focus();
 				return false;
 				break;
 			}
 			if(document.getElementsByName('poIssueBy')[i-1].value!="" || document.getElementsByName('poIssueBy')[i-1].value!=null )
			{
				 if(ValidateTextField(document.getElementsByName('poIssueBy')[i-1],path,'Po IssueBy')==false)
				 {
				 	document.getElementsByName('poIssueBy')[i-1].focus();
				 	return false;
				 	break;
				 }
			}
		
 			if(document.getElementsByName('poRemarks')[i-1].value=="" && document.getElementsByName('poRemarks')[i-1].isRequired=="1")
 			{
 				alert("Please enter PO Remarks");
 				document.getElementsByName('poRemarks')[i-1].focus();
 				return false;
 				break;
 			}
 			
 			if(document.getElementsByName('poRemarks')[i-1].value!="" || document.getElementsByName('poRemarks')[i-1].value!=null )
			{
				 if(ValidateTextField(document.getElementsByName('poRemarks')[i-1],path,'Po Remarks')==false)
				 {
				 	document.getElementsByName('poRemarks')[i-1].focus();
				 	return false;
				 	break;
				 }
			}
 			if(document.getElementsByName('poEmailId')[i-1].value=="" && document.getElementsByName('poEmailId')[i-1].isRequired=="1")
 			{
 				alert("Please enter Email Id");
 				document.getElementsByName('poEmailId')[i-1].focus();
 				return false;
 				break;
 			}
 			if(document.getElementsByName('poDemoContractPeriod')[i-1].value=="" && document.getElementsByName('poDemoContractPeriod')[i-1].isRequired=="1")
 			{
 				alert("Please enter Demo Contract Period(Days)");
 				document.getElementsByName('poDemoContractPeriod')[i-1].focus();
 				return false;
 				break;
 			}
 			//End[021]
 			//lawkush
			
			var valFrom=document.getElementsByName('contractStartDate')[i-1].value;
			
			if(valFrom != ""){
				fnCalculateEndDate(i);
			}
			var valTo=document.getElementsByName('contractEndDate')[i-1].value;
			var podate = document.getElementsByName('poDate')[i-1].value;
			// [003]	Start 
			var custPoDate = document.getElementsByName('custPoDate')[i-1].value;
			// [003]	End 
			var poRecieveDate=document.getElementsByName('poReceiveDate')[i-1].value;
			
			if(dateCompare(podate,currentDate)==1)
			{			
				alert("PO Date Cannot be greater than Current Date");
				return false;
			}
			// [003]	Start 
			if(dateCompare(currentDate,custPoDate)==1)
			{			
				//alert("Customer PO Date Cannot be less than Current Date");
				//return false;
			}
			// [003]	End 
			if(dateCompare(poRecieveDate,currentDate)==1)
			{			
				alert("PO Recieve Date Cannot be greater than Current Date");
				return false;
			}
			
			/*if(dateCompare(valFrom,valTo)==1)
			{			
				alert("Contract Start Date Cannot be greater than Contract End Date");
				return false;
			}*/
			//Start[021]
			if(document.getElementsByName('poReceiveDate')[i-1].value!="")
			{
				if(dateCompare(custPoDate,poRecieveDate)==1)
				{			
					alert("PO Receive Date can't be less than Customer PO Date!!!");
					return false;
				} 
				
			}
		  //End[021]
		  //lawkush
           
		  //[062] start
			if(document.getElementsByName('ldClause')[i-1].value=="" && document.getElementsByName('ldClause')[i-1].isRequired=="1")
			{
				    alert("Please enter Ld Clause");
				    document.getElementsByName('ldClause')[i-1].focus();
				    return false;
				    break;
			}
			if(document.getElementsByName('ldClause')[i-1].value!="" || document.getElementsByName('ldClause')[i-1].value!=null )
			{
				 if(ValidateTextField(document.getElementsByName('ldClause')[i-1],path,'Ld Clause')==false)
				 {
					 document.getElementsByName('ldClause')[i-1].focus();
					 return false;
					 break;
				 }
			}
//[062] end
		 	
		
			
	//008 add		
		var check="";
		var j;
		
		var jsData=
			{
			   custPoDetailNo:document.getElementsByName('custPoDetailNo')[i-1].value,
			
	 			custPoDate:document.getElementsByName('custPoDate')[i-1].value,
	 			poNumber:document.getElementById('poNumber').value
	 			};
			
					var pono=document.getElementsByName('custPoDetailNo')[i-1].value;
	 			
			// vr ajsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			
		     test=jsonrpc.processes.validatePoDetailNONDATE_FORNEW(jsData);
				
		var count=0;
		  for(j=0;j<test.list.length;j++)
		{

			if(test.list[j].count1>0)
			{
			var ponumber1=test.list[j].ponum;
		
		   var podate=document.getElementsByName('custPoDate')[i-1].value;
		   count++;
		 }
		 if(check=="")
			{
			check=test.list[j].ponum;
			}
			else
			{
			check=check + "," + test.list[j].ponum ;
			}
		
		 
		 
		}
		if(count>0)
		{
		 if(!window.confirm("Customer PODetail Number  : " + pono  + " and   Customer PO Date   :  "  + podate +   "   already exist  for OrderNo: " + check +		  '\n'+		    " Do You Want To Continue " ))
		 {
		 return false;
		 }
		}
	
	//008 end			
				
			
						
 		var jsData = 
 			{
 				custPoDetailNo:document.getElementsByName('custPoDetailNo')[i-1].value,
	 			custPoDate:document.getElementsByName('custPoDate')[i-1].value,
	 			poDate:document.getElementsByName('poDate')[i-1].value,
	 			poReceiveDate:document.getElementsByName('poReceiveDate')[i-1].value,
	 			contractStartDate:document.getElementsByName('contractStartDate')[i-1].value,
	 			contractEndDate:document.getElementsByName('contractEndDate')[i-1].value,
	 			periodsInMonths:document.getElementsByName('periodsInMonths')[i-1].value,
	 			totalPoAmt:document.getElementsByName('totalPoAmt')[i-1].value,
	 			entityId:document.getElementsByName('entityId')[i-1].value,
	 			//[062]
	 			ldClause:document.getElementsByName('ldClause')[i-1].value,
	 			isReqCustPoDetailNo:document.getElementsByName('custPoDetailNo')[i-1].isRequired,
	 			isReqCustPoDate:document.getElementsByName('custPoDate')[i-1].isRequired,
	 			isReqPODate:document.getElementsByName('poDate')[i-1].isRequired,
	 			isReqPORcvDt:document.getElementsByName('poReceiveDate')[i-1].isRequired,
	 			isReqcontractStartDate:document.getElementsByName('contractStartDate')[i-1].isRequired,
	 			isReqContractEndDate:document.getElementsByName('contractEndDate')[i-1].isRequired,
	 			isReqPeriodsInMonths:document.getElementsByName('periodsInMonths')[i-1].isRequired,
	 		        isReqTotalPoAmt:document.getElementsByName('totalPoAmt')[i-1].isRequired,
	 			isReqEntity:document.getElementsByName('entityId')[i-1].isRequired,
	 			//[062]
	 			isReqLdClause:document.getElementsByName('ldClause')[i-1].isRequired
		   };
		   count++;
			//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			var error = jsonrpc.processes.validatePoDetails(jsData);
		
			if(error != "") {
			alert(error);
			return false;
			
 		} 
	 }
 	return true;
 }
 
function validateContactDetails(){
	var i;
	var count = 1;
	var myForm=document.getElementById('searchForm');
	var contactTable = document.getElementById('tableContact');
	var addressTable = document.getElementById('tableAddress');
	//var counter = document.getElementById('chkSelectContactDetail').value;
	var jsData = new Array();
	for(i=0;i<contactTable.rows.length-1;i++){
   		//---[012]---Start
	    if(myForm.salutationName.length==undefined){
	    	var counter = myForm.chkSelectContactDetail.value;
			if(trim(myForm.contactType.value) == '' && myForm.contactType.isRequired=="1"){
				alert("Please enter Contact Type on line no."+(i+1));
				myForm.contactType.focus();
				return false;
			}else{
			 	var flag = (myForm.contactType.value).search('%');
				if(flag != -1){
					alert("Please enter Right Contact Type on line no."+(i+1)) ;
					return false;
				}else{
					if(autoFillContactType(counter)==false){
						return false;
					}
				}
			}
			if(trim(myForm.salutationName.value) == '' && myForm.salutationName.isRequired=="1"){
				alert("Please enter Salutation on line no."+(i+1)) ;
				myForm.salutationName.focus();
				return false;
			}else{
			 	var flag = (myForm.salutationName.value).search('%');
				if(flag != -1){
			 		alert("Please enter Right Salutation Name on line no."+(i+1)) ;
					return false;
			 	}else{	
					if(autoFillSalutation(counter)==false){
						return false;
					}
				}
			}			
			if(trim(myForm.firstName.value) == '' && myForm.firstName.isRequired=="1"){
				alert("Please enter First Name on line no."+(i+1));
				myForm.firstName.focus();
				return false;
			}
			
			if(trim(myForm.firstName.value)!="" || trim(myForm.firstName.value)!=null )
			{
				 if(ValidateTextField(myForm.firstName,path,'First Name')==false)
				 {
				 	myForm.firstName.focus();
				 	return false;
				 	break;
				 }
			}
			
			if(trim(myForm.lastName.value) == '' && myForm.lastName.isRequired=="1"){
				alert("Please enter Last Name on line no."+(i+1));
				myForm.lastName.focus();
				return false;
			}
			if(trim(myForm.lastName.value)!="" || trim(myForm.lastName.value)!=null )
			{
				 if(ValidateTextField(myForm.lastName,path,'Last Name')==false)
				 {
				 	myForm.lastName.focus();
				 	return false;
				 	break;
				 }
			}
			if(trim(myForm.cntEmail.value) == '' && myForm.cntEmail.isRequired=="1"){
				alert("Please enter Email on line no."+(i+1));
				myForm.cntEmail.focus();
				return false;
			}if(trim(myForm.contactCell.value) == '' && myForm.contactCell.isRequired=="1" ){
				alert("Please enter Cell No on line no."+(i+1));
				myForm.contactCell.focus();
				return false;
			}if(trim(myForm.contactFax.value) == '' && myForm.contactFax.isRequired=="1"){
				alert("Please enter Fax on line no."+(i+1));
				myForm.contactFax.focus();
				return false;
			}
			//==================================================================			
			//-------------------Address Tab Validation-------------------------
			//==================================================================
			if(trim(myForm.address1.value) == '' && myForm.address1.isRequired=="1"){
				alert("Please enter Address1 on line no."+(i+1));
				myForm.address1.focus();
				return false;
			}
			if(trim(myForm.address1.value)!="" || trim(myForm.address1.value)!=null )
			{
				 if(ValidateTextField(myForm.address1,path,'Address1')==false)
				 {
				 	myForm.address1.focus();
				 	return false;
				 	break;
				 }
			}
			if(trim(myForm.address2.value) == '' && myForm.address2.isRequired=="1"){
				alert("Please enter Address2 line no."+(i+1)) ;
				myForm.address2.focus();
				return false;
			}
			
			if(trim(myForm.address2.value)!="" || trim(myForm.address2.value)!=null )
			{
				 if(ValidateTextField(myForm.address2,path,'Address2')==false)
				 {
				 	myForm.address2.focus();
				 	return false;
				 	break;
				 }
			}
			if(trim(myForm.address3.value) == '' && myForm.address3.isRequired=="1"){
				alert("Please enter Address3 on line no."+(i+1)) ;
				myForm.address3.focus();
				return false;
			}
			
			if(trim(myForm.address3.value)!="" || trim(myForm.address3.value)!=null )
			{
				 if(ValidateTextField(myForm.address3,path,'Address3')==false)
				 {
				 	myForm.address3.focus();
				 	return false;
				 	break;
				 }
			}
			if(trim(myForm.cityName.value) == '' && myForm.cityName.isRequired=="1"){
				alert("Please enter City Name on line no."+(i+1)) ;
				myForm.cityName.focus();
				return false;
			}else{
			 	var flag = (myForm.cityName.value).search('%');
				if(flag != -1){
					alert("Please enter Right City Name on line no."+(i+1)) ;
					return false;
				}else
				{
					$('#hdnCheckTab').val(1);				 	
					if(autoFill(counter)==false){
						return false;
					}
				}
			}
			if(trim(myForm.stateName.value) == '' && myForm.stateName.isRequired=="1"){
					alert("Please enter State Name on line no."+(i+1)) ;
					myForm.stateName.focus();
					return false;
			}
			if(trim(myForm.countyName.value) == '' && myForm.countyName.isRequired=="1"){
					alert("Please enter Country Name on line no."+(i+1)) ;
					myForm.countyName.focus();
					return false;
			}				
			if(trim(myForm.pinNo.value) == '' && myForm.pinNo.isRequired=="1"){
					alert("Please enter Pin on line no."+(i+1)) ;
					myForm.pinNo.focus();
					return false;
			}
			//==================================================================			
			//-------------------Address Tab Validation-------------------------
			//==================================================================
	    }else{
	    	var counter = myForm.chkSelectContactDetail[i].value;
			if(trim(myForm.contactType[i].value) == '' 
				&& document.getElementsByName('contactType')[i].isRequired=="1"){
				alert("Please enter Contact Type on line no."+(i+1)) ;
				myForm.contactType[i].focus();
				return false;
			}else{
				var flag = (myForm.contactType[i].value).search('%');
				if(flag != -1){
					alert("Please enter Right Contact Type on line no."+(i+1)) ;
					return false;
				}else{	
					if(autoFillContactType(counter)==false){
						return false;
					}
				}
			}	  
			if(trim(myForm.salutationName[i].value) == '' 
				&& document.getElementsByName('salutationName')[i].isRequired=="1"){
				alert("Please enter Salutation on line no."+(i+1)) ;
				myForm.salutationName[i].focus();
				return false;
			}else{
				 var flag = (myForm.salutationName[i].value).search('%');
				if(flag != -1){
					alert("Please enter Right Salutation Name on line no."+(i+1)) ;
					return false;
				}else{	
					if(autoFillSalutation(counter)==false){
						return false;
					}
				}
			}	  
			if(trim(myForm.firstName[i].value) == '' 
				&& document.getElementsByName('firstName')[i].isRequired=="1"){
				alert("Please enter First Name on line no."+(i+1)) ;
				myForm.firstName[i].focus();
				return false;
			}
			if(trim(myForm.firstName[i].value) != ''  || trim(myForm.firstName[i].value) != null)
			{
				if(ValidateTextField(myForm.firstName[i],path,'First Name')==false)
				 {
				 	myForm.firstName[i].focus();
				 	return false;
				 	break;
				 }
			}
			
			if(trim(myForm.lastName[i].value) == '' 
				&& document.getElementsByName('lastName')[i].isRequired=="1"){
				alert("Please enter Last Name on line no."+(i+1)) ;
				myForm.lastName[i].focus();
				return false;
			}
			if(trim(myForm.lastName[i].value) != ''  || trim(myForm.lastName[i].value) != null)
			{
				if(ValidateTextField(myForm.lastName[i],path,'Last Name')==false)
				 {
				 	myForm.lastName[i].focus();
				 	return false;
				 	break;
				 }
			}
			if(trim(myForm.cntEmail[i].value) == '' 
				&& document.getElementsByName('cntEmail')[i].isRequired=="1"){
				alert("Please enter Email on line no."+(i+1)) ;
				myForm.cntEmail[i].focus();
				return false;
			}
			if(trim(myForm.contactCell[i].value) == '' 
				&& document.getElementsByName('contactCell')[i].isRequired=="1"){
				alert("Please enter Cell No on line no."+(i+1)) ;
				myForm.contactCell[i].focus();
				return false;
			}
			if(trim(myForm.contactFax[i].value) == '' 
				&& document.getElementsByName('contactFax')[i].isRequired=="1"){
				alert("Please enter Fax on line no."+(i+1)) ;
				myForm.contactFax[i].focus();
				return false;
			}
			//==================================================================			
			//-------------------Address Tab Validation-------------------------
			//==================================================================
			if(trim(myForm.address1[i].value) == '' 
				&& document.getElementsByName('address1')[i].isRequired=="1"){
				alert("Please enter Address1 on line no."+(i+1)) ;
				myForm.address1[i].focus();
				return false;
			}
			if(trim(myForm.address1[i].value) != ''  || trim(myForm.address1[i].value) != null)
			{
				if(ValidateTextField(myForm.address1[i],path,'Address1')==false)
				 {
				 	myForm.address1[i].focus();
				 	return false;
				 	break;
				 }
			}
			if(trim(myForm.address2[i].value) == '' 
				&& document.getElementsByName('address2')[i].isRequired=="1"){
				alert("Please enter Address2 line no."+(i+1)) ;
				myForm.address2[i].focus();
				return false;
			}
			if(trim(myForm.address2[i].value) != ''  || trim(myForm.address2[i].value) != null)
			{
				if(ValidateTextField(myForm.address2[i],path,'Address2')==false)
				 {
				 	myForm.address2[i].focus();
				 	return false;
				 	break;
				 }
			}
			if(trim(myForm.address3[i].value) == '' 
				&& document.getElementsByName('address3')[i].isRequired=="1"){
					alert("Please enter Address3 on line no."+(i+1)) ;
					myForm.address3[i].focus();
					return false;
			}
			
			if(trim(myForm.address3[i].value) != ''  || trim(myForm.address3[i].value) != null)
			{
				if(ValidateTextField(myForm.address3[i],path,'Address3')==false)
				 {
				 	myForm.address3[i].focus();
				 	return false;
				 	break;
				 }
			}
			if(trim(myForm.cityName[i].value) == '' 
				&& document.getElementsByName('cityName')[i].isRequired=="1"){
					alert("Please enter City Name on line no."+(i+1)) ;
					myForm.cityName[i].focus();
					return false;
			}else{
				 var flag = (myForm.cityName[i].value).search('%');
				if(flag != -1){
			 		alert("Please enter Right City Name on line no."+(i+1)) ;
					return false;
				}else
					{
					$('#hdnCheckTab').val(1);		
					if(autoFill(counter)==false){
						return false;
					}
				}
			}	  
			if(trim(myForm.stateName[i].value) == '' 
				&& document.getElementsByName('stateName')[i].isRequired=="1"){
					alert("Please enter State Name on line no."+(i+1)) ;
					myForm.stateName[i].focus();
					return false;
			}
			if(trim(myForm.countyName[i].value) == '' 
				&& document.getElementsByName('countyName')[i].isRequired=="1"){
					alert("Please enter Country Name on line no."+(i+1)) ;
					myForm.countyName[i].focus();
					return false;
			}
			if(trim(myForm.pinNo[i].value) == '' 
				&& document.getElementsByName('pinNo')[i].isRequired=="1"){
					alert("Please enter Pin on line no."+(i+1));
					myForm.pinNo[i].focus();
					return false;
			}
			// End[012]
			//==================================================================			
			//-------------------Address Tab Validation-------------------------
			//==================================================================
			
			document.getElementsByName('contactFax')[i].isRequired="0"; 		        
			jsData[i] ={
	 			contactType:document.getElementsByName('contactType')[i].value,
	 			salutationName:document.getElementsByName('salutationName')[i].value,
	 			firstName:document.getElementsByName('firstName')[i].value,
	 		    lastName:document.getElementsByName('lastName')[i].value,
	 			cntEmail:document.getElementsByName('cntEmail')[i].value,
	 			contactCell:document.getElementsByName('contactCell')[i].value,
	 			contactFax:document.getElementsByName('contactFax')[i].value,
	 		    address1:document.getElementsByName('address1')[i].value,
	 			address2:document.getElementsByName('address2')[i].value,
	 			address3:document.getElementsByName('address3')[i].value,
	 		    cityName:document.getElementsByName('cityName')[i].value,
	 			stateName:document.getElementsByName('stateName')[i].value,
	 			countyName:document.getElementsByName('countyName')[i].value,
	 			pinNo:document.getElementsByName('pinNo')[i].value,
	 			isReqContactType:document.getElementsByName('contactType')[i].isRequired,
				isReqSaluation:document.getElementsByName('salutationName')[i].isRequired,
				isReqFirstName:document.getElementsByName('firstName')[i].isRequired,
				isReqLastName:document.getElementsByName('lastName')[i].isRequired,
				isReqCntEmail:document.getElementsByName('cntEmail')[i].isRequired,
				isReqContactCell:document.getElementsByName('contactCell')[i].isRequired,
				isReqContactFax:document.getElementsByName('contactFax')[i].isRequired,
				isReqAddress1:document.getElementsByName('address1')[i].isRequired,
				isReqAddress2:document.getElementsByName('address2')[i].isRequired,
				isReqAddress3:document.getElementsByName('address3')[i].isRequired,
				isReqCityName:document.getElementsByName('cityName')[i].isRequired,
				isReqStateName:document.getElementsByName('stateName')[i].isRequired,
				isReqCountyName:document.getElementsByName('countyName')[i].isRequired,
				isReqPinNo:document.getElementsByName('pinNo')[i].isRequired,
				count:count
		   };		   
			//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");	
			/*var error = jsonrpc.processes.validateContactDetails(jsData,count);			
			if(error != "") {
			alert(error);
			return false;*/
 			count++;
		}
	}	
	/*for(i=1; i <= contactTable.rows.length-1; i++){		
	}*/
	var error = jsonrpc.processes.validateContactDetails(jsData);			
	if(error != ""){
		alert(error);
		return false;
	}	
 	return true;
 }
function validateRoleField()
{
			var myForm=document.getElementById('searchForm')
			for (iField = 0 ; iField < roleFeild.list.length; iField++)
    		{
    			alert(roleFeild.list[iField].feildName);
	 			for(iCtrl=0;iCtrl<myForm.elements.length;iCtrl++)
				{
					var ctrlName=myForm.elements[iCtrl].name;
					if(ctrlName==roleFeild.list[iField].feildName)
					{
						if(roleFeild.list[iField].isReadOnly=="1")
						{
							myForm.elements[iCtrl].value  = "HHH";
						}	
						else
							myForm.elements[iCtrl].readOnly  = false;
							
						myForm.elements[iCtrl].Mand = roleFeild.list[iField].isMand;
					}
				}	
			}		
}


function unCheckedContactMaster()
{
	
	var myForm=document.getElementById('searchForm');
	var chkLength1=document.forms[0].chkSelectContactDetail.length;
	var counter=0;
	
		if(chkLength1==undefined)
		{
				if(document.forms[0].chkSelectContactDetail.checked==false)
				{
					document.getElementById("SelectAllChckContact").checked=false;
				}
				
				if(document.forms[0].chkSelectContactDetail.checked==true)
				{
					document.getElementById("SelectAllChckContact").checked=true;
				}
		}
	
	
		else
	      { 
	      	   
	         for (i =0; i<chkLength1; i++)
			   { 
			     
			     if(myForm.chkSelectContactDetail[i].checked==false)
			     
					 {
					       document.getElementById("SelectAllChckContact").checked=false ;
					 }
					 
				if(myForm.chkSelectContactDetail[i].checked==true)
			     
					 {
					       counter++;
					 }
			   if(counter==chkLength1)
			   
			   {
			   		  document.getElementById("SelectAllChckContact").checked=true ;
					          
			   }
		}
	}
}

function unCheckedSelectAllPO()
{
	
	if(!(document.forms[0].chkSelectPODetail))
	{
		document.getElementById("SelectAllPOChck").checked=false;
	}
	
}


function unCheckedSelectAllContact()
{
	
	if(!(document.forms[0].chkSelectContactDetail))
	{
		document.getElementById("SelectAllChckContact").checked=false;
	}
	
}



function unCheckedPOMaster()
{
	
	var myForm=document.getElementById('searchForm');
	var chkLength1=document.forms[0].chkSelectPODetail.length;
	var counter=0;
		if(chkLength1==undefined)
		{
				if(document.forms[0].chkSelectPODetail.checked==false)
				{
					document.getElementById("SelectAllPOChck").checked=false;
				}
				
				if(document.forms[0].chkSelectPODetail.checked==true)
				{
					document.getElementById("SelectAllPOChck").checked=true;
				}
		}
	
	
		else
	      {    
	         for (i =0; i<chkLength1; i++)
			   { 
			     
			     if(myForm.chkSelectPODetail[i].checked==false)
			     
					 {
					       document.getElementById("SelectAllPOChck").checked=false ;
					 }
					 
					 if(myForm.chkSelectPODetail[i].disabled==true || myForm.chkSelectPODetail[i].checked==true)
			     
					 {
					       counter++;
					 }
					   if(counter==chkLength1)
					   
					   {
					   		  document.getElementById("SelectAllPOChck").checked=true ;
							          
					   }       
			   }
		}

}


function AddContact()
{
    // var counter = "";
    var iCountConatctRow=0;
    var counter= parseInt(document.getElementById('hdnContactCount').value);
	document.getElementById('hdnContactCount').value = counter+1;
	iCountConatctRow = document.getElementById('tableContact').rows.length;
	var str;
	var rowlen=document.getElementById('tableContact').rows.length;
	
	var tblRow=document.getElementById('tableContact').insertRow();
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' class='inputBorder4' name='abc' id='abc' value='0'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;

	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' class='inputBorder1' name='contactId' id='contactId "+counter+"' value='0'><input onmouseover='getTip(value)' onmouseout='UnTip()' name='chkSelectContactDetail' id='chkSelectContactDetail"+counter+"' value='"+ counter +"' type='checkbox' onclick='unCheckedContactMaster();'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' name='contactType' isRequired=0 style='width:90px; float:left;' id='contactType"+counter+"' onchange='autoFillContactType("+counter+")' onkeydown='if (event.keyCode == 13)  return autoFillContactType("+counter+");'   ><input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' name='contactTypeId' id='contactTypeId"+counter+"'  ><div class='searchBg1'><a href='#' onClick='popContactType("+ counter +")'>...</a></div>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' name='salutationName' id='salutationName"+counter+"' isRequired='0'  onchange='autoFillSalutation("+counter+")' onkeydown='if (event.keyCode == 13)  return autoFillSalutation("+counter+");'   style='width:50px; float:left;'  };\"><input type='hidden' name='salutationId"+counter+"' id='salutationId' readonly='readonly' ><div class='searchBg1'><a href='#' onClick='popSalutationList("+ counter +")'>...</a></div>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' name='firstName' maxlength='100' style='width:100px; float:left;' isRequired=0 id='firstName' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'First Name')};\">"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' name='lastName' maxlength='100' style='width:100px; float:left;' isRequired=0 id='lastName' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Last Name')};\" >"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2' name='cntEmail' maxlength='255' style='width:100px; float:left;' isRequired=0 id='cntEmail' onBlur='if(this.value.length > 0){return emailValidate(this)}'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1'  style='width:90px; float:left;' name='contactCell' maxlength='50' isRequired=0 id='contactCell' onBlur='cellValidation(this);'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2'  style='width:90px; float:left;' name='contactFax' maxlength='15' isRequired=0 id='contactFax' onBlur='if(this.value.length > 0){return checknumber(this)}'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	    AddAddress(counter);
	   
	
	
	 	var rowCounter = 1;
   	 	var idCounter = 1;
	 	var conatctTab = document.getElementById('tableContact');
	 	var addressTab = document.getElementById('tableAddress');
	 
		var iSlNo=1;
		for (var row=1; row<conatctTab.rows.length;row++) 
		{
			
			if(conatctTab.rows[row].style.display != 'none')
			{
	      		var celsContactTab = conatctTab.rows[row].getElementsByTagName('td');
	      		celsContactTab[0].innerHTML=iSlNo;
	      		var celAddressTab = addressTab.rows[row].getElementsByTagName('td');
	      		celAddressTab[0].innerHTML=iSlNo;
	      		iSlNo++;
      		}
      		else
      		{
	      		var celsContactTab = conatctTab.rows[row].getElementsByTagName('td');
	      		celsContactTab[0].innerHTML="X"+row;
	      		var celAddressTab = addressTab.rows[row].getElementsByTagName('td');
	      		celAddressTab[0].innerHTML="X"+row;
      		}
      		
      	}	
	         var ctrlArry = new Array();
			var myForm=document.getElementById('searchForm')
			
			ctrlArry = new Array();
			
			if(rowlen==1)
			{
				ctrlArry[0] =  myForm.contactType;
				ctrlArry[1] =  myForm.salutationName;
				ctrlArry[2] =  myForm.firstName;
				ctrlArry[3] =  myForm.lastName;
				ctrlArry[4] =  myForm.cntEmail;
				ctrlArry[5] =  myForm.contactCell;
				ctrlArry[6] =  myForm.contactFax;
				ctrlArry[7] =  myForm.address1;
				ctrlArry[8] =  myForm.address2;
				ctrlArry[9] =  myForm.address3;
				ctrlArry[10] =  myForm.cityName;
				ctrlArry[11] =  myForm.stateName;
				ctrlArry[12] =  myForm.countyName;
				ctrlArry[13] =  myForm.pinNo;
			
			}
			
		else
		{	
			ctrlArry[0] =  myForm.contactType[rowlen-1];
			ctrlArry[1] =  myForm.salutationName[rowlen-1];
			ctrlArry[2] =  myForm.firstName[rowlen-1];
			ctrlArry[3] =  myForm.lastName[rowlen-1];
			ctrlArry[4] =  myForm.cntEmail[rowlen-1];
			ctrlArry[5] =  myForm.contactCell[rowlen-1];
			ctrlArry[6] =  myForm.contactFax[rowlen-1];
			ctrlArry[7] =  myForm.address1[rowlen-1];
			ctrlArry[8] =  myForm.address2[rowlen-1];
			ctrlArry[9] =  myForm.address3[rowlen-1];
			ctrlArry[10] =  myForm.cityName[rowlen-1];
			ctrlArry[11] =  myForm.stateName[rowlen-1];
			ctrlArry[12] =  myForm.countyName[rowlen-1];
			ctrlArry[13] =  myForm.pinNo[rowlen-1];
		//---[021]--End
		
	 }	
			fieldRoleMappingValidation(contactTabFieldList,ctrlArry);
		
	
}
//class type changed by lawkush
function AddAddress(counter)
{
    var iCountConatctRow=0;
  	var rowlen=document.getElementById('tableContact').rows.length;
	var str;
	var tblRow=document.getElementById('tableAddress').insertRow();
	iCountAddressRow = document.getElementById('tableAddress').rows.length-1;
	var counter= parseInt(document.getElementById('hdnAddressCount').value);
	document.getElementById('hdnAddressCount').value = counter+1;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input type='hidden' class='inputBorder4' name='abc' id='abc' value='0'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input type='hidden' class='inputBorder1' name='addID' id='addID' value='0'><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' name='address1' style='width:100px; float:left;' isRequired=0 id='address1' maxlength='255'  onBlur= \"if( trim(this.value).length>0){return ValidateTextField(this,path,'Address1')};\">"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2'  name='address2' style='width:100px; float:left;' isRequired=0 id='address2' maxlength='255' onBlur= \"if( trim(this.value).length>0){return ValidateTextField(this,path,'Address2')};\">"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2'  name='address3' maxlength='255' style='width:100px; float:left;' isRequired=0 id='address3' onBlur= \"if( trim(this.value).length>0){return ValidateTextField(this,path,'Address3')};\">"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1'  name='cityName' isRequired=0 id='cityName"+counter+"'  style='width:90px; float:left;' readonly='readonly' onchange='autoFill("+counter+")' onkeydown='if (event.keyCode == 13)  return autoFill("+counter+");'><input type='hidden' name='cityCode' id='cityCode"+counter+"'  readonly='readonly' ><div class='searchBg1'><a href='#' onClick='popCityList("+ counter +")'>...</a></div>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1'   name='stateName' isRequired=0 id='stateName"+counter+"'  style='width:90px; float:left;' readonly='readonly' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'State')};\"><input type='hidden' name='stateCode' id='stateCode"+counter+"'  readonly='readonly' ><div class='searchBg1'><a href='#' onClick='popStateList("+ counter +")'>...</a></div>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1'  style='width:90px; float:left;' name='pinNo' maxlength='15' isRequired=0 id='pinNo"+counter+"' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Pin')};\"><input type='hidden' name='pinCode' id='pinCode"+counter+"'  readonly='readonly' ><div class='searchBg1'><a href='#' onClick='popPinList("+ counter +")'>...</a></div>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' name='countyName' isRequired=0 id='countyName"+counter+"'  style='width:90px; float:left;' readonly='readonly' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Country')};\"><input type='hidden' name='countyCode' id='countyCode"+counter+"'  readonly='readonly' ><div class='searchBg1'><a href='#' onClick='popCountryList("+ counter +")'>...</a></div>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' value='0' name='hdnAddess' id='hdnAddess'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
}


//----------------------------------Service Type Tab----------------------Starts Here------------------

//014 start
function fnDeletePro(){
	try{
	//added for clep to prevent onclick activity
	if(document.getElementById('deleteProductDiv').disabled==true){
		return false;
	}else{
     //Added By Saurabh for Role Check
     //Start
     var stage = document.getElementById('stageName').value;
     if(stage=="Partial Publish" || stage=="Billing trigger"){
     	alert("You are not authorised to use this functionality");
     	return false;
     }
     //End
     //Puneet for commercial product check before deletion
     var roleWiseSectionDetail = getRoleWiseSectionDetails(gb_roleID);
     var commercialNotAllowed = checkRoleWiseCommercialNotAllowed(roleWiseSectionDetail);
     if(commercialNotAllowed){
    	var commercialLineToBeDeleted =  checkServiceTreeContainsCommercialLine("browser");
    	if(commercialLineToBeDeleted){
    		alert("You are not authorized to delete commercial line");
    		return false;
    	}
     }
     
     var myform=document.getElementById("searchForm");
     var checkedCounter=1;
     var checkedCounter1=1;
     var serviceNo=0;
     var jsData1 ={
    	poNumber:document.getElementById('poNumber').value
     };
    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    var test1 = jsonrpc.processes.poulateServiceList(jsData1);
	var errorMsg = "";; 
	var count = 0;
	var serviceCount=test1.list.length;
	if(serviceCount==0){
		alert("Please add atleast one service");
		return false;
	}
	var chkLength1=document.forms[0].chkService.length;
	if(chkLength1==undefined){
		//Added By Saurabh to restrict users from deleting product whose service has been already published
		if(document.getElementById('chk'+0).value=="Yes"){
		   	alert("Service already published \n Products cannot be deleted");
		   	return false;
		}
		if(document.getElementById('serviceStatus'+1).value=="CancelAndCopy" || document.getElementById('serviceStatus'+1).value=="Cancelled in CRM"){
			alert("Service already cancelled \n Products cannot be deleted");
			return false;
		}
		//End By Saurabh 
		if(document.forms[0].chkService.checked==true){
			checkedCounter++;
			serviceNo=myform.chkService.value;
		}
	}else{
		for(i=0;i<chkLength1;i++){
			if(myform.chkService[i].checked==true){
				//Added By Saurabh to restrict users from deleting product whose service has been already published
				if(document.getElementById('chk'+i).checked==true){
		   			alert("Service already published \n Products cannot be deleted");
		   			return false;
				}
				if(document.getElementById('serviceStatus'+(i+1)).value=="CancelAndCopy" || document.getElementById('serviceStatus'+(i+1)).value=="Cancelled in CRM"){
					alert("Service already cancelled \n Products cannot be deleted");
					return false;
				}
				//End By Saurabh
				checkedCounter++;
				serviceNo=myform.chkService[i].value;
			}
		}
	}			
	if(checkedCounter==1){
		alert('Please Select Service Type!!');
		return false;
	}
	/*for(i=1;i<=serviceCount;i++)
	{
	 var cat=test1.list[0].productCatelogEntered;
       if(cat==0)
		{
		alert("please add atleast one product");
		return; 
		}
    }*/
    var chkProduct=document.getElementById('chk_spId');
    var str="";
    var chkLength=document.forms[0].chk_spId.length;
  
    
    if(chkLength==undefined){
	   if(document.forms[0].chk_spId.checked==true){
		   checkedCounter1++;
		   if(str==""){
			   str=document.forms[0].chk_spId.value;
		   }else{
			   str=str + "," +document.forms[0].chk_spId.value;
   	       }
		   //myform.action="<%=request.getContextPath()%>/NewOrderAction.do?method=DeleteProducts&hdnslno="+str;
		   //myform.submit();
	       //alert("Files Deleted Successfully");
	       var jsData =	{
	    	   serviceId1:str,
	    	   orderNumber:document.getElementById('poNumber').value
			};
			//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
		    //Added By Saurabh for Partial Publish Check
		    //Start By Saurabh
			newOrderStatusServiceId = jsonrpc.processes.newOrderStatusServiceId(serviceNo);
			if(serviceNo == newOrderStatusServiceId){
				alert("Service already published \n Products Cannot be deleted");
				return false;
			}
			//End By Saurabh
			var answer = confirm(" Are you sure to delete line item " + str)
			if(answer){
				//Do Nothing
			}else{
				return false;
			}
			//Start Dummy Line Delete Validation		
			var results;
			var jsData1 ={
				serviceIdString:serviceNo,
				productIdString:str,
				orderNumber:document.getElementById('poNumber').value,
				remarks:"Delete Product"
			};
			results = jsonrpc.processes.validateVCSServicesBeforeCancel(jsData1);
			if(results!="" && results!=null){
				alert(results);
				return false;
			}
			//End Dummy Line Delete Validation
			result = jsonrpc.processes.DeleteProducts(jsData);
			//serviceTab.deleteRow(1); 
			if(result.msgOut != "" && result.msgOut != null){
				//alert(result.msgOut);
		   		alert("Row(s) deleted successfully");
		   		fncServiceTreeView();
		  		setDummyStatusIfPossible('-');
		    }else{
				alert("Row(s) not deleted");
				return false;
			}
	   	}
    }else{
	    for(i=0;i<document.forms[0].chk_spId.length;i++){
	    	if(myform.chk_spId[i].checked==true){
	     	     checkedCounter1++;
	     	     if(str==""){
		     	     str=myform.chk_spId[i].value;
	     	     }else{
	     	    	 str=str + "," +myform.chk_spId[i].value;
	     	     }
		     }
	    }	
		    /*for(i=0;i<document.forms[0].chk_spId.length;i++)
		{
		     if(myform.chk_spId[i].checked==true)
		     	{
		     	     checkedCounter1++;*/
	    var jsData ={
	    		serviceId1:str
		};
		//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
		//Added By Saurabh for Partial Publish Check
    	//Start By Saurabh
		newOrderStatusServiceId = jsonrpc.processes.newOrderStatusServiceId(serviceNo);
		if(serviceNo == newOrderStatusServiceId){
			alert("Service already published \n Products Cannot be deleted");
			return false;
		}
		//End By Saurabh
		//Added by Deepak  to give the alert when user select radio button to delete the product
		else if(str==""){
			alert("Please Select a Product to delete");
			return false;
		}
		//end Deepak
		var answer = confirm(" Are you sure to delete line item " + str)
		if(answer){
			//Do Nothing
		}else{
			return false;
		}
		//Start Dummy Line Delete Validation		
		var results;
		var jsData1 ={
			serviceIdString:serviceNo,
			productIdString:str,
			orderNumber:document.getElementById('poNumber').value,
			remarks:"Delete Product"
		};
		results = jsonrpc.processes.validateVCSServicesBeforeCancel(jsData1);
		if(results!="" && results!=null){
			alert(vcsResults);
			return false;
		}
		//Start Dummy Line Delete Validation
		result = jsonrpc.processes.DeleteProducts(jsData);
		if(result.msgOut != "" && result.msgOut != null){
			//alert(result.msgOut);
			alert("Row(s) deleted successfully");
			fncServiceTreeView(); 
		    setDummyStatusIfPossible('-');
		}else{
    		alert("Row(s) not deleted");
    		return false;
		}
		//myform.action="<%=request.getContextPath()%>/NewOrderAction.do?method=DeleteProducts&hdnslno="+str;
		// myform.submit();
	    //alert("Files Deleted Successfully");
	}
    if(checkedCounter1==1){
    	alert("please select a product to delete");
    }
	}//end CLEP else
	}catch(e){
		alert('error code 50: '+ e.message);
	}
}
//014 end

function ViewServiceTree(flag){
	var modeValue=gb_ModeValue;
	var myForm=document.getElementById('searchForm');
	//[250511AKS]Start
	var poNumber=document.getElementById('poNumber').value;
	//[250511AKS]End
	/*myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=getServiceTreeview&modeName="+ modeValue+"&orderNo="+poNumber+"&flag="+flag+"&checkedServiceNumber="+checkedServiceNumber;
	showLayer();
	myForm.submit();*/
	var initialForm = document.getElementById('searchForm');
	var myDummyForm=createDummyForm(gb_path+"/NewOrderAction.do");
	attachHiddenField(myDummyForm,"method","getServiceTreeview");
	attachHiddenField(myDummyForm,"modeName",modeValue);
	attachHiddenField(myDummyForm,"orderNo",poNumber);
	attachHiddenField(myDummyForm,"flag",flag);
	attachHiddenField(myDummyForm,"checkedServiceNumber",checkedServiceNumber);		
	attachHiddenField(myDummyForm,"poNumber",poNumber);				
	if(initialForm.m6Status!=null){
		attachHiddenField(myDummyForm,"m6Status",initialForm.m6Status.value);				
	}
	if(initialForm.stageName!=null){
		attachHiddenField(myDummyForm,"stageName",initialForm.stageName.value);				
	}
	//alert(initialForm.m6Status);
	showLayer();
	myDummyForm.submit();
}

//PAGING-SERVICE-LINE-14-10-2012
function drawServiceLineTable()
{
   //------------Set Page Size for Service Line Table Paging Start --------------
   	pageRecords=PAGE_SIZE_SERVICE_LINE;
   //------------Set Page Size for Service Line Table Paging End ----------------
   var countService=1;
   var i;//Puneet Commenting as these var are not usedtr, td, i, j, oneRecord;
   
   /*Puneet Commenting to directly set the inner html blank 
   mytable = document.getElementById('ServiceList');
  
   var iCountRows = mytable.rows.length;
   for (i = 0; i <  iCountRows; i++)
   {
       mytable.deleteRow(0);
   }*/
   
   	mytable = document.getElementById('contentscrollService');
  	mytable.innerHTML="";
   	//-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
   	document.getElementById('txtPageNumber').value= pageNumber;
   	sync();  
   	//lawkush Start
	var index_var=0;
	index_var=startRecordId;
	//lawkush End
	//[060] added searchLSI and searchServiceNo
    var jsData =			
    {
    	startIndex:startRecordId,
		endIndex:endRecordId,
		sortBycolumn:sortByColumn,
		sortByOrder:sortByOrder,
		pageRecords:pageRecords,		
		poNumber:Number(document.getElementById('poNumber').value),
		searchLSI:Number(document.getElementById('txtLsiNo').value),
		searchServiceNo:Number(document.getElementById('txtServiceNo').value)
		
	};
	//-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------

	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    var test = jsonrpc.processes.poulateServiceListWithPaging(jsData);   
    
   	var tabServiceLineCount;
	var iNewServiceCount;
	var iM6ProcessingServiceCount;
	var iBillingTriggerServiceCount;
	var iBillingTriggerEndServiceCount;
	var iM6CancelledServiceCount;
	var iCRMCancelledServiceCount;
	var iCancelandCopyServiceCount;
	var iM6FailedCount = 0;
	var iM6SuccessCount = 0;
	iTotalLength=test.list.length;
    if(iTotalLength > 0){
		tabServiceLineCount=test.list[0].total_services;
		iNewServiceCount=test.list[0].total_new_services;
		iM6ProcessingServiceCount=test.list[0].total_m6_processing;
		iBillingTriggerServiceCount=test.list[0].total_billing_trigger;
		iBillingTriggerEndServiceCount=test.list[0].total_billing_trigger_end;
		iM6CancelledServiceCount=test.list[0].total_cancel_m6;
		iCRMCancelledServiceCount=test.list[0].total_cancel_crm;
		iCancelandCopyServiceCount=test.list[0].total_cancel_copy;
		iM6FailedCount=test.list[0].total_m6_failed;
		iM6SuccessCount=test.list[0].total_m6_success;
	}/*else{    commented by [060]
		return;
	}
	*/
	//-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
	if(iTotalLength !=0){
		iNoOfPages = test.list[0].maxPageNo;
	}else{     
        iNoOfPages=1;
	}
		
	document.getElementById('txtMaxPageNo').value=iNoOfPages;	
	
	if(iTotalLength > 0){
		document.getElementById('serviceLineTabNavigatorId').style.display='block';
	}else{
		document.getElementById('serviceLineTabNavigatorId').style.display='none';		
	}
	//-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
	var colors=new Array("normal","lightBg");
	var tableDataStart;
	var tableDataEnd = "</td>";
	var tableRowStart;
	var tableRowEnd = "</tr>";
	//var tableCol;
	var tableStr = "<table  cellpadding='0' cellspacing='0' class='content1' id='ServiceList'>";
	var tableData;
	var selServTypeID = null;
	var selServiceNum = null;
    for (i = 0 ; i < iTotalLength; i++){
		tableRowStart = "<tr class='" + colors[parseInt(i)%2] + "' initTo='" + test.list[i].role_id + "' ispublished='" + test.list[i].isPublished + "'>";
    	tableStr = tableStr + tableRowStart;
		tableDataStart = "<td width='1%' align='center' vAlign='top' class='inner colSrNo'>";		
		tableData="<label>" +index_var+ "</label>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
	
		tableDataStart = "<td width='4%' align='left' vAlign='top' class='inner colRadio'>";				
		tableData="<input tabIndex='-1' name='chkService' id='chkService"+i+"' type='radio' value='"+ test.list[i].serviceId +"'  onclick='fncServiceTreeView("+test.list[i].serviceTypeId+");fncServicePresent("+test.list[i].role_id+","+gb_roleID+","+test.list[i].isPublished+")'>";
		//tableData = tableData+ "<input type = 'hidden' id = 'isServicePublished' value = '"+ test.list[i].isPublished +"'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		tableDataStart = "<td width='5%' align='center' vAlign='top' class='inner colCheckBox'>";
		tableData="<input tabIndex='-1' name='chkDeleteService' id='chkDeleteService"+i+"' value='"+ test.list[i].serviceId +"'  type='checkbox' onclick='allServiceCheckNone()'>";	
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colPublished'>";
		//Started By Saurabh to make user see whether service is published or not
		if(test.list[i].isPublished==0)
			tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='chk' id='chk"+i+"' value='No' class='inputBorder5' type='text' readonly='true' size='5'/>";
		else
			tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='chk' id='chk"+i+"' value='Yes' class='inputBorder5' type='text' readonly='true' size='5'/>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		tableDataStart = "<td width='5%' align='center' vAlign='top' class='inner colDummy'>";
		if(test.list[i].isDummy==0)
			tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='dummy' id='dummy"+i+"' value=' - ' class='inputBorder5' type='text' readonly='true' size='5'/>";
		else
			tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='dummy' id='dummy"+i+"' value='Yes' class='inputBorder5' type='text' readonly='true' size='5'/>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;

		tableDataStart = "<td width='8%' align='left' vAlign='top' class='inner colServiceNo'>";
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtServiceNo"+countService+"' id='txtServiceNo"+countService+"' value='"+ test.list[i].serviceId +"' class='inputBorder5' type='text' readonly='true' size='5'> <input name='txtServiceTypeID"+countService+"' value='"+ test.list[i].serviceTypeId +"' type='hidden' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		tableDataStart = "<td width='29%' align='center' vAlign='top' class='inner colServiceType'>";
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:96%;' name='txtServiceName"+
						countService+"' value='"+ test.list[i].serviceTypeName +"' class='inputBorder5' type='text' readonly='true'> <input name='txtServiceTypeID"+
						countService+"' value='"+ test.list[i].serviceTypeId +"' type='hidden' readonly='true'> <input id='hdnIsServicecancelled"+countService+"' value='"+ 
						test.list[i].isServiceActive +"' type='hidden' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;	
		
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colLogicalSI'>";
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:90%;' name='txtLogicalSINumber"+
						countService+"' value='"+ test.list[i].logicalSINumber +"' class='inputBorder5' type='text' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;	
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colLogicalSI'>";
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtCustomer_logicalSINumber"+
						countService+"' value='"+ test.list[i].customer_logicalSINumber +"' class='inputBorder5' type='text' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colAttribute'>";
		if(btnAttributes=="0"){
			var order_creation_source = document.getElementById('hdnOrderCreationSourceId').value;
			if(order_creation_source=="2"){		
				tableData="&nbsp;";
			}else{
				tableData="<div class='searchBg1'><a href='#' onclick=popServiceAttribute('ServiceAttributes',"+ 
						countService +",'"+ test.list[i].serviceId +"')>...</a></div><input name='servAttrEntered"+
						countService+"' value='"+test.list[i].servAttrEntered+"' type='hidden' >";
			}
		}else
			tableData="&nbsp;"
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colAttribute'>";
		if(btnAttLabelValues=="0")
			tableData="<div class='searchBg1'><a href='#' onclick=serviceProductAttribute("+ countService +")>...</a></div><input onmouseover='getTip(value)' onmouseout='UnTip()' name='prdAttrEntered"+countService+"' value='"+test.list[i].prdAttrEntered+"' type='hidden' > <input onmouseover='getTip(value)' onmouseout='UnTip()' name='productEntered"+countService+"' value='"+test.list[i].productCatelogEntered+"' type='hidden' >";
		else
			tableData="&nbsp;";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colServiceStatus'>";				
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:120%;' name='serviceStatus"+
						countService+"' id='serviceStatus"+countService+"' value='"+ 
						test.list[i].serviceStatus +"' class='inputBorder5' type='text' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		//adding one more column for display demo flag. 
		tableDataStart = "<td width='9%' align='center' vAlign='top' class='inner colPublished'>";
		if(test.list[i].isDemo==0)
			tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='dmo' id='dmo"+i+"' value='No' class='inputBorder5' type='text' readonly='true' size='5'/>";
		else
			tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='dmo' id='dmo"+i+"' value='Yes' class='inputBorder5' type='text' readonly='true' size='5'/>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		//end of display demo flag
		// added by manisha initiated to field o2c start
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colInitiatedTo'>";				
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:120%;' name='initiatedTo"+
						countService+"' id='initiatedTo"+countService+"' value='"+ 
						test.list[i].initiated_to +"' class='inputBorder5' type='text' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		/*tableData="<input name='hdnroleId"+
		countService+"' id='hdnroleId"+countService+"' value='"+ 
		test.list[i].role_id +"' type='hidden'>";*/
		// added by manisha initiated to field o2c end
		
		
		
		
		//[055]
		
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner calcancelDate'>";
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:120%;' name='serviceCancelDate"+
						countService+"' id='serviceCancelDate"+countService+"' value='"+ 
						test.list[i].service_cancl_date +"' class='inputBorder5' type='text' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colCancellationreson'>";
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:120%;' name='serviceCancelReson"+
						countService+"' id='serviceCancelReson"+countService+"' value='"+ 
						test.list[i].serv_cancel_reson +"' class='inputBorder5' type='text' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colCancelledBy'>";
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:120%;' name='serviceCancelledBy"+
						countService+"' id='serviceCancelledBy"+countService+"' value='"+ 
						test.list[i].service_cancelledby +"' class='inputBorder5' type='text' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		
		tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colCancelremarks'>";
		tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:120%;' name='serviceCancelRemarks"+
						countService+"' id='serviceCancelRemarks"+countService+"' value='"+ 
						test.list[i].serv_cancel_remarks +"' class='inputBorder5' type='text' readonly='true'>";
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
		//[055]
		
		
		if(countService>0){	
			document.getElementById('selectAllID').disabled=false;
		}
		countService++;		
		//lawkush start
		index_var++;
		//lawkush End
		checkedServiceNumber=gb_CheckedServiceNoParam;		
		if(i==checkedServiceNumber){
			selServTypeID = i+1;
			selServiceNum = serviceTypeId;
		}
		tableStr = tableStr + tableRowEnd;		
    }
    tableStr = tableStr + "</table>";
    mytable.innerHTML = tableStr;
	if(null != selServTypeID && null != selServiceNum){
		document.getElementById("chkService"+selServiceNum).checked=true;
		fncServiceTreeView(selServTypeID);
	}
    //-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
    var pagenumber=document.getElementById('txtPageNumber').value;
	var MaxPageNo=document.getElementById('txtMaxPageNo').value;
	if(pagenumber && MaxPageNo==1)
	 {	
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;		
	 }	 
	 if(pagenumber==1 && MaxPageNo!=1)
	 {
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=false;
	     document.getElementById('next').disabled=false;	 
	 }	 
	 if(pagenumber==MaxPageNo && pagenumber!=1)	  
	 {
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;
	     document.getElementById('first').disabled=false;
	     document.getElementById('prev').disabled=false;	 
	 }	 
	 if(pagenumber!=MaxPageNo && pagenumber!=1)	  
	 {
	     document.getElementById('last').disabled=false;
	     document.getElementById('next').disabled=false;
	     document.getElementById('first').disabled=false;
	     document.getElementById('prev').disabled=false;	 
	 }
			
	if(iTotalLength==0)		
	{									
		document.getElementById('first').disabled=true;
     	document.getElementById('prev').disabled=true;
     	document.getElementById('last').disabled=true;
     	document.getElementById('next').disabled=true;	     				
	}
	if(test.list.length > 0){
		var abc = "<b> Service Type (Total No Of Service : "+tabServiceLineCount;
		//Puneet added for display of count status
		var count = 0;
		if(iNewServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( NEW : " +iNewServiceCount+ " )";
			else
				abc=abc+" ( NEW : " +iNewServiceCount+ " )";
			count++;		
		}	
		if(iM6ProcessingServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Processing In M6 : " +iM6ProcessingServiceCount+ " )";
			else
				abc=abc+" ( Processing In M6 : " +iM6ProcessingServiceCount+ " )";
			count++;
		}
		if(iBillingTriggerServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Ready To Billing Trigger : " +iBillingTriggerServiceCount+ " )";
			else
				abc=abc+" ( Ready To Billing Trigger : " +iBillingTriggerServiceCount+ " )";
			count++;		
		}	
		if(iBillingTriggerEndServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Billing Trigger Ends : " +iBillingTriggerEndServiceCount+ " )";
			else
				abc=abc+" ( Billing Trigger Ends : " +iBillingTriggerEndServiceCount+ " )";
			count++;	
		}	
		if(iM6CancelledServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Cancelled in M6 : " +iM6CancelledServiceCount+ " )";
			else
				abc=abc+" ( Cancelled in M6 : " +iM6CancelledServiceCount+ " )";
			count++;		
		}	
		if(iCRMCancelledServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Cancelled in CRM : " +iCRMCancelledServiceCount+ " )";
			else	
				abc=abc+" ( Cancelled in CRM : " +iCRMCancelledServiceCount+ " )";
			count++;	
		}	
		if(iCancelandCopyServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( CancelAndCopy : " +iCancelandCopyServiceCount+ " )";
			else
				abc=abc+" ( CancelAndCopy : " +iCancelandCopyServiceCount+ " )";
			count++;	
		}
		if(iM6FailedCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( M6 Failed : " +iM6FailedCount+ " )";
			else
				abc=abc+" ( M6 Failed : " +iM6FailedCount+ " )";
			count++;	
		}
		if(iM6SuccessCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( M6 Success : " +iM6SuccessCount+ " )";
			else
				abc=abc+" ( M6 Success : " +iM6SuccessCount+ " )";
			count++;	
		}
		abc = abc + ")</b>";	
		document.getElementById('lblServicetype').innerHTML = abc;
	}
	//--vijay--if service is 0 then display it// 
	else{
		var abc = "<b> Service Type (Total No Of Service : 0";
		abc = abc+" ( NEW : 0 )";
		abc = abc + ")</b>";
		document.getElementById('lblServicetype').innerHTML = abc;
	}
	//--end of code--//
	disableServiceLines('contentscrollService',gb_roleID);
	return false;	
	//-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------

}
function popServiceAttribute(url,cntr,serviceId)
{
	//logicalSINo=document.getElementById("txtServiceName"+id).value+"-"+document.NewOrder.txtAccountNo.value+"-"+serviceID
	var serviceId ;
	var serviceTypeId=document.getElementById("txtServiceTypeID"+cntr).value;
	if(cntr==0)
	{
		serviceId = document.getElementById("txtServiceNo").value
		
		if(document.getElementById("txtServiceName").value=="")
		{
			alert("Please Select A service Type");
			return false;
		}
		else
		{
			var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&serviceTypeId="+serviceTypeId;
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			//url = url + "?txtServiceID="+cntr+"&serviceID="+serviceId;
			window.showModalDialog(path,window,'status:false;dialogWidth:1200px;dialogHeight:600px');
		}
		
	}
	else
	{
		serviceId = document.getElementById("txtServiceNo" + cntr).value
		if(document.getElementById("txtServiceName"+cntr).value=="")
		{
			alert("Please Select A service Type");
			return false;
		}
		else
		{
			var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&serviceTypeId="+serviceTypeId;
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			window.showModalDialog(path,window,"status:false;dialogWidth:1200px;dialogHeight:600px");
		}
	}
}
//-------[020] Start
function popPartialPublish(url,cntr,serviceId)
{
	//logicalSINo=document.getElementById("txtServiceName"+id).value+"-"+document.NewOrder.txtAccountNo.value+"-"+serviceID
	var serviceId ;
	if(cntr==0)
	{
		serviceId = document.getElementById("txtServiceNo").value
		
		if(document.getElementById("txtServiceName").value=="")
		{
			alert("Please Select A service Type");
			return false;
		}
		else
		{
			var path = gb_path+"/selectservice.do?method=goToPartialPublish&txtServiceID="+ cntr +"&serviceID="+serviceId+"";
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:350px");
		}
		
	}
	else
	{
		serviceId = document.getElementById("txtServiceNo" + cntr).value
		if(document.getElementById("txtServiceName"+cntr).value=="")
		{
			alert("Please Select A service Type");
			return false;
		}
		else
		{
			var path =gb_path+"/selectservice.do?method=goToPartialPublish&txtServiceID="+ cntr +"&serviceID="+serviceId+"";
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:350px");
		}
	}
}

function getServicelineCount()
{
		//var tabServiceLineCount = document.getElementById('ServiceList').rows.length-1;
		//--vijay--//
		var tabServiceLineCount = document.getElementById('ServiceList').rows.length;
		var tabService = document.getElementById('ServiceList');
		var iNewServiceCount = 0;
		var iM6ProcessingServiceCount = 0;
		var iBillingTriggerServiceCount = 0;
		var iBillingTriggerEndServiceCount = 0;
		var iM6CancelledServiceCount = 0;
		var iCRMCancelledServiceCount = 0;
		var iCancelandCopyServiceCount = 0;
		var iWaitingForL3MPLSServiceCount = 0;
		var iWaitingForISPServiceCount = 0;
		var iAccountCreationCount = 0;
		var iM6FailedCount = 0;
		var iM6SuccessCount = 0;		
		for(var iCount=0;iCount<tabServiceLineCount;iCount++)
		{
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="NEW")
			iNewServiceCount++;
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="Processing In M6")
			iM6ProcessingServiceCount++;
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="Ready To Billing Trigger")
			iBillingTriggerServiceCount++;
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="Billing Trigger Ends")
			iBillingTriggerEndServiceCount++;
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="Cancelled in M6")
			iM6CancelledServiceCount++;
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="Cancelled in CRM")
				iCRMCancelledServiceCount++;
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="CancelAndCopy")
				iCancelandCopyServiceCount++;
			if((document.getElementById('serviceStatus'+(iCount+1)).value).substring(0,19)=="Waiting For L3 MPLS")
				iWaitingForL3MPLSServiceCount++
			if((document.getElementById('serviceStatus'+(iCount+1)).value).substring(0,15)=="Waiting For ISP")
				iWaitingForISPServiceCount++
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="FX Account Creation")
				iAccountCreationCount++;
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="M6 Failed")
				iM6FailedCount++;
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="M6 Success")
				iM6SuccessCount++;	
		}
		
		var abc = "<b> Service Type (Total No Of Service : "+tabServiceLineCount;
		var count = 0;
		if(iNewServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( NEW : " +iNewServiceCount+ " )";
			else
				abc=abc+" ( NEW : " +iNewServiceCount+ " )";
			count++;	
		}
		if(iM6ProcessingServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Processing In M6 : " +iM6ProcessingServiceCount+ " )";
			else
				abc=abc+" ( Processing In M6 : " +iM6ProcessingServiceCount+ " )";
			count++;	
		}
		if(iBillingTriggerServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Ready To Billing Trigger : " +iBillingTriggerServiceCount+ " )";
			else
				abc=abc+" ( Ready To Billing Trigger : " +iBillingTriggerServiceCount+ " )";
			count++;	
		}
		if(iBillingTriggerEndServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Billing Trigger Ends : " +iBillingTriggerEndServiceCount+ " )";
			else
				abc=abc+" ( Billing Trigger Ends : " +iBillingTriggerEndServiceCount+ " )";
			count++;	
		}
		if(iM6CancelledServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Cancelled in M6 : " +iM6CancelledServiceCount+ " )";
			else
				abc=abc+" ( Cancelled in M6 : " +iM6CancelledServiceCount+ " )";
			count++;	
		}
		if(iCRMCancelledServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Cancelled in CRM : " +iCRMCancelledServiceCount+ " )";
			else
				abc=abc+" ( Cancelled in CRM : " +iCRMCancelledServiceCount+ " )";
			count++;	
		}
		if(iCancelandCopyServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( CancelAndCopy : " +iCancelandCopyServiceCount+ " )";
			else
				abc=abc+" ( CancelAndCopy : " +iCancelandCopyServiceCount+ " )";
			count++;		
		}
		if(iWaitingForL3MPLSServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Waiting For L3 MPLS M6 End : " +iWaitingForL3MPLSServiceCount+ " )";
			else
				abc=abc+" ( Waiting For L3 MPLS M6 End : " +iWaitingForL3MPLSServiceCount+ " )";
			count++;		
		}
		if(iWaitingForISPServiceCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( Waiting For ISP M6 End : " +iWaitingForISPServiceCount+ " )";
			else
				abc=abc+"( Waiting For ISP M6 End : " +iWaitingForISPServiceCount+ " )";
			count++;		
		}
		if(iAccountCreationCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( FX Account Creation : " +iAccountCreationCount+ " )";
			else
				abc=abc+" ( FX Account Creation : " +iAccountCreationCount+ " )";
			count++;		
		}
		if(iM6FailedCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( M6 Failed : " +iM6FailedCount+ " )";
			else
				abc=abc+" ( M6 Failed : " +iM6FailedCount+ " )";
			count++;		
		}
		if(iM6SuccessCount>0){
			if(count != 0 && count%2 == 0)
				abc=abc+"<br>( M6 Success : " +iM6SuccessCount+ " )";	
			else
				abc=abc+" ( M6 Success : " +iM6SuccessCount+ " )";
			count++;			
		}
		abc = abc + ")</b>";
		//CellContents = abc;
		document.getElementById('lblServicetype').innerHTML = abc;
		
}
//-------[020] End
function AddServiceLine()
{
	var str;
	var stage = document.getElementById("stageName").value;
	if(stage=="SED" || stage=="Partial Publish") 
	{
		alert("You are not authorised to add more sevices");
		return false;
	}
	
	var tblRow=document.getElementById('ServiceList').insertRow();
			
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='chkService' id='chkService' type='radio' value='' onclick=fncServiceTreeView()>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input name='chkDeleteService' id='chkDeleteService"+countService+"' class='inputBorder5' type='text' readonly='true' size='5' onclick='allServiceCheckNone()' > ";	
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	//Started By Saurabh to make user see whether service is published or not
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' name='chk' id='chk"+countService+"' class='inputBorder5' type='text' readonly='true' size='5'/>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//End By Saurabh 
	
	//Started By Saurabh to make user see whether service is dummy or not
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' name='dummy' id='dummy"+countService+"' class='inputBorder5' type='text' readonly='true' size='5'/>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//End By Saurabh 
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtServiceNo"+countService+"' id='txtServiceNo"+countService+"' class='inputBorder5' type='text' readonly='true' size='5'>";
//	str="<label name='txtServiceNo"+countService+"'>&nbsp;</label>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:96%;' id='txtServiceName"+countService+"' name='txtServiceName"+countService+"' class='inputBorder5' type='text' readonly='true'> <input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceTypeID"+countService+"' type='hidden' readonly='true'>";
//	str="<label name='txtServiceName"+countService+"'>&nbsp;</label><input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceTypeID"+countService+"' type='hidden' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	

	//var tblcol=tblRow.insertCell();
	//tblcol.align = "center";
	//tblcol.vAlign="top";
	//str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtServiceSubTypeName"+countService+"' class='inputBorder5' type='text' readonly='true'>";
//	str="<label name='txtServiceSubTypeName"+countService+"'>&nbsp;</label>";
	//CellContents = str;
	//tblcol.innerHTML = CellContents;

	var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtLogicalSINumber"+countService+"' width = '10%' class='inputBorder5' type='text' readonly='true'>";
//	str="<label name='txtLogicalSINumber"+countService+"'>&nbsp;</label>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtCustomer_logicalSINumber"+countService+"' width = '10%' class='inputBorder5' type='text' readonly='true'>";
//	str="<label name='txtCustomer_logicalSINumber"+countService+"'>&nbsp;</label>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	
	if(btnselectservice=="0")
		str="<div class='searchBg1'><a href='#' title='Select Service' id='a"+countService+"' onclick=popService("+ countService +")>...</a></div>";
	else
		str="&nbsp;";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	
	
	if(btnAttributes=="0")
		str="<div class='searchBg1'><a href='#' title='Select ServiceAttribute'  onclick=popServiceAttribute('ServiceAttributes',"+ countService +",'')>...</a></div><input onmouseover='getTip(value)' onmouseout='UnTip()' name='servAttrEntered"+countService+"' value='0' type='hidden' >";
	else
		str="&nbsp;";	
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//counter++;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	if(btnAttLabelValues=="0")
		str="<div  class='searchBg1'><a href='#' title='Select ServiceProductAttribute'  onclick=serviceProductAttribute("+ countService +")>...</a></div><input onmouseover='getTip(value)' onmouseout='UnTip()' name='prdAttrEntered"+countService+"' value='0' type='hidden' > <input onmouseover='getTip(value)' onmouseout='UnTip()' name='productEntered"+countService+"' value='0' type='hidden' >";
	else
		str="&nbsp;";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//-------[020] Start
	//var tblcol=tblRow.insertCell();
	//tblcol.align = "center";
	//tblcol.vAlign="top";
	//if(btnAttributes=="0")
		//str="<div class='searchBg1'><a href='#' onclick=popPartialPublish('ServiceAttributes',"+ countService +",'')>...</a></div><input onmouseover='getTip(value)' onmouseout='UnTip()' name='servAttrEntered"+countService+"' value='0' type='hidden' >";
	//else
		///str="&nbsp;";	
	//CellContents = str;
	//tblcol.innerHTML = CellContents;
	//-------[020] End
	
	popService(countService);
	countService++;
	if(countService>1)
	{
	// selectAllID
	document.getElementById('selectAllID').disabled=false;
	}
}
//----------------------------------Service Type Tab----------------------Ends Here------------------

function SelectProductCatelog()
{
var modeValue=gb_ModeValue;

	//Added By Saurabh for Role Check
    //Start
    var stage = document.getElementById('stageName').value;
    if(stage=="Billing trigger" || stage=="CANCELLED")
    {
    	alert("You are not authorised to use this functionality");
    	return false;
    }
    //End
	
	if(document.forms[0].chkService==undefined)
	{
		alert("Please Add a Service!!");
		return false;
	}
	var newCounter=1;
	var chkLength=document.forms[0].chkService.length;
	chkTest = 0;
	chkVal =0;
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			if(document.forms[0].txtServiceNo1.value=="" || document.forms[0].txtServiceNo1.value== null)
			{
				chkVal=1;
			}
			else
			{
				document.getElementById('hdnCurrentTempCounter').value=newCounter;
				
				//Added By Saurabh To Restrict Users to create product for a service which is already published
				if(document.getElementById('chk'+0).value=="Yes") 
				{
					alert("Service already published \n No Product can be added");				
					return false;
				}
				if(document.getElementById('hdnIsServicecancelled'+1).value==1) 
				{
					alert("Service is Cancelled. No New Product can be added");				
					return false;
				}
				//End By Saurabh
				var selectedProductId = jsonrpc.processes.getSelectedProductId(document.getElementById('txtServiceNo'+newCounter).value)
				if(selectedProductId == 0)
				{
					alert("Please Select the Product Name for the selected Service in Attributes Section");
					return false;
				}
				var path = gb_path+"/NewOrderAction.do?method=displayProductCatelog&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accNo').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&modeName="+ modeValue+"&LogicalSI="+  document.getElementById('txtLogicalSINumber'+newCounter).value+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value+"&productId="+selectedProductId;
				window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px");
				//window.open(path);
				chkTest = 1;
				chkVal=0;
			}
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chkService[i].checked==true)
			{
				if(document.getElementById('txtServiceNo'+newCounter).value=="")
				{
					chkVal=1;
					break;
				}
				else
				{
					document.getElementById('hdnCurrentTempCounter').value=newCounter;
					
					//Added By Saurabh To Restrict Users to create product for a service which is already published
					if(document.getElementById('chk'+i).value=="Yes")
					{
						alert("Service already published \n No Product can be added");
						return false;
					}
					var j= i+1;
				//	alert(document.getElementById('hdnIsServicecancelled'+j).value + '  index : ' + i);
					if(document.getElementById('hdnIsServicecancelled'+j).value==1) 
					{
					alert("Service is Cancelled. No New Product can be added");				
						return false;
					}
					//End By Saurabh
					var selectedProductId = jsonrpc.processes.getSelectedProductId(document.getElementById('txtServiceNo'+newCounter).value)
					if(selectedProductId == 0)
					{
						alert("Please Select the Product Name for the selected Service in Attributes Section");
						return false;
					}
					//alert('txtLogicalSINumber : ' +  document.getElementById('txtLogicalSINumber'+newCounter).value);
					var path = gb_path+"/NewOrderAction.do?method=displayProductCatelog&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accNo').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&modeName="+ modeValue+"&LogicalSI="+  document.getElementById('txtLogicalSINumber'+newCounter).value+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value+"&productId="+selectedProductId;
					window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px");
					chkTest = 1;
					chkVal=0;
					break;
				}
			}
			else
			{
				chkTest = 0;
				chkVal=0;
			}
		}
	}
	
	if (chkTest == 0)
	{
		alert("Please Select a Service before Selecting Product Catelog!!");
		return false;
	}
	
	if(chkVal!=0)
	{
		alert("Please Select A Service Before Viewing Product Catelog");
		return false;
	}
}
function serviceProductAttribute(ctrlNo) 
{
      if(document.getElementById('txtServiceNo'+ctrlNo).value == "")
      {
        alert('Please Select Service First');
      }
      else
      {
		document.getElementById('hdnserviceid').value = document.getElementById('txtServiceNo'+ctrlNo).value;
		document.getElementById('hdnCurrentTempCounter').value=ctrlNo;
		var serviceNo=document.getElementById('txtServiceNo'+ctrlNo).value;
		var serviceTypeId=document.getElementById('txtServiceTypeID'+ctrlNo).value;
		var d=new Date();
		var path = gb_path+"/NewOrderAction.do?method=getTProductAttDetail&hdnserviceid="+serviceNo+'&hdnserviceTypeId='+serviceTypeId+"&"+d.getTime();		
		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//window.open(path);
	 }
}


//-------------------------Po Detail----------------------------------------------
function getCurrentDate(){
	/*var less2Date="<%=AppUtility.getTodayDate()%>";
	var valFrom=document.getElementById("demo1").value;
	var valTo=document.getElementById("demo2").value;
	if(valFrom==null || valFrom==""||valTo==null || valTo=="")
	{
		alert("Please Enter both From Date and To Date.");
		return false;
	}*/
}	


//-------------------------Validate PO----------------------------------------------
//Added One Parameter for calling same method from Action close and publish. 
//Added Three Parameter for validating only required services - O2C Drop2 - Service Level Approval
function validateOrder(isActionOrPublish,allServicesSelected,selectedServiceList){
	
	//Logging Total Time Taken by Validate action
		var var_start_validation_time=new Date();
		var var_alert_time_taken_answer=0;
	//Logging Total Time Taken by Validate action
	
	//alert('allServicesSelected '+allServicesSelected);
	//alert('selectedServiceList '+selectedServiceList);
	
	//valiation for demodays if isDemo is selected //mahalakshmi start
	var form =document.getElementById('searchForm');
	if(form.chkIsDemo.checked==true)
		{
		var demoDaysInput=form.noOfDaysForDemo.value;
		if(demoDaysInput==""|| Number(demoDaysInput)==0)
			{
				alert('No Of Days cannot be blank or zero if IsDemo is checked !!');
				form.noOfDaysForDemo.focus();
				return false;
			}
		else
		{		
		  if(checkdigits(form.noOfDaysForDemo)==false)
			  	return false;		 
		}
		
		}
	//mahalakshmi end
	
	//Start [058]
	var orderNo=document.getElementById('poNumber').value;
	//End [058]
	if(allServicesSelected==null || allServicesSelected==undefined){
		allServicesSelected=true;
	}
	if(selectedServiceList==null || selectedServiceList==undefined){
		selectedServiceList='';
	}
	//Start [058]
	var serviceForValidate = jsonrpc.processes.getServiceForValidate(orderNo,sessionId,allServicesSelected,selectedServiceList);
	//End [058]
		//added by Anil for clep
		if(clepState ==1 || clepState == 2 || clepState ==3 || clepState ==4){
			document.getElementById('isValidatePO').value=1;
			return false;
		}else{
			gamNFormulaAttachedWithOrder();
			if(isGamNFormulaAttachedFlag==0 || isGamNFormulaAttachedFlag==-1){
				return false;
			}
			// [063] 
			/*validateMBIC_To_CC();
			if(isValidaMBIC_MAP_WITH_CC==false)
				return false;*/
			validateVCS_BridgeServiceBundled();
			if(isVCS_MAP_WITH_L3MPLS==false)
				return false;
			//START POC POINTS As On Date 29-09-2012
			var stage = document.getElementById('stageName').value;
			if(stage=="COPC" || stage=="SED" || stage=="Partial Publish"){
				getComponentLineItem();
			if(isComponentAddedInLineItem==false)
				return false;
			}
			getMultipleDummyServices();
			if(isMultipleDummyServicesInOrder==false)
				return false;
			//END
		
			//	opportunity validation
			var orderNoValid = document.getElementById("poNumber").value;
			var chkIsDemo = document.getElementById('chkIsDemo');
			
			if((stage == 'New' && orderNoValid != 0 && orderNoValid!= '') && (!chkIsDemo.checked)){
				var segment = document.getElementById("lob").value;
				var sourceName = document.getElementById("sourceName").value;		
				if(segment == 'AES Corporate' && sourceName == 'CRMORDER'){
					alert('Please Select Opportunity.');
					return false;
				}
			}
			//opportunity validation
		
		    var finalErrorMessege="";
			var validated=0;
			var myform=document.getElementById("searchForm");
			// var orderNo=document.getElementById('poNumber').value; commented and define above by ravi
			
			
			var validateServiceLevelAttribute=jsonrpc.processes.checkServiceAttributePresent(orderNo,gb_roleID);
			if(validateServiceLevelAttribute != '' &&  validateServiceLevelAttribute!=null){
				if(selectedServiceList!=''){
					var returnString  = getSelectedServiceFromList(selectedServiceList,validateServiceLevelAttribute);
					if(returnString!='')
						finalErrorMessege= finalErrorMessege +"*"+ " Please fill mandatory service level attributes of following serviceId(s)  "+returnString +"\n";
				}
				else
					finalErrorMessege= finalErrorMessege +"*"+ " Please fill mandatory service level attributes of following serviceId(s)  "+validateServiceLevelAttribute +"\n";
			}
			//[057] start
			//[047] start
			/*var stages='Publish';
			var parallelUpgradeValidation = jsonrpc.processes.parallelUpgradeValidation(orderNo,stages);
			if(parallelUpgradeValidation != '')
			{
				alert(parallelUpgradeValidation);
				return false;
			}
			*///[047] end
			//[057] end 
			
			//[048] start
			if((document.getElementById('lob').value=="Channel") || (document.getElementById('lob').value=="SMB"))
			{
				if(document.getElementById('hdnOrderCreationSource').value!="2")
				{
					var validateAdvancePaymentDetails = jsonrpc.processes.validateAdvancePaymentDetails(orderNo);
					if(validateAdvancePaymentDetails != '')
					{
						alert(validateAdvancePaymentDetails);
						return false;
					}
				}
			}
			
			//[065] start
			
				var lst=jsonrpc.processes.validateM2M(orderNo);
				if(lst.code==1){
						alert("M2M Service can only be added in M2M Service Segment Account.Follwing are invalid M2M services: "+lst.msg);
						return false;
					}
				
				//[065] end
			
			//[048] end
			var signageGlobalCount = jsonrpc.processes.signageGlobalCount(orderNo);
			if(signageGlobalCount > 1){
				alert('Only one Signage Global service is allowed ! ');
				return false;
			}
			//Meenakshi : Adding DMX Specific Validation of checking mandatory line items
			//Meenakshi : Added for CBR_20120806-XX-07984
			var result = jsonrpc.processes.dmxMandatoryValidation(orderNo);
			if(result!='')
			{
				finalErrorMessege = finalErrorMessege +"*"+ result+"\n";
			}
			var test = jsonrpc.processes.validateFeild(orderNo,sessionId);
		   	if(test != 'SUCCESS')
		   	{
		    	finalErrorMessege= finalErrorMessege +"*"+ test +"\n";
			}
		    
			var jsData11 =			
		    {
				poNumber:document.getElementById('poNumber').value
			};
		    
		
			//Check to validate mpls limitations
			var mplsValidationCheck_ServiceNos = jsonrpc.processes.checkMplsValidationForOrder(orderNo);
			if(mplsValidationCheck_ServiceNos !=null && mplsValidationCheck_ServiceNos !='')
			{
				alert(mplsValidationCheck_ServiceNos);
				return false;
			}
			
			var strServiceAttrMsg = jsonrpc.processes.getUpdateData1(jsData11);
			if(strServiceAttrMsg!='' &&  strServiceAttrMsg!=null){
				if(selectedServiceList!=''){
					var returnString  = getSelectedServiceFromList(selectedServiceList,strServiceAttrMsg);
					if(returnString!='')
						finalErrorMessege= finalErrorMessege +"*"+ "Please Enter Rfs Date/Product/Subproduct/Change Order LSI No Required for ServiceId(s) "+returnString +"\n";
				}
				else
					finalErrorMessege= finalErrorMessege +"*"+ "Please Enter Rfs Date/Product/Subproduct/Change Order LSI No Required for ServiceId(s) "+strServiceAttrMsg +"\n";
			}
			//[067] drop & carry validation start
			// DnC , Validation , Modified By Shubhranshu, 10-11-2016
			var msg=jsonrpc.processes.validateDropAndCarry(orderNo,stage);
			//alert(msg.sqlMsg);
			if(trim(msg.sqlMsg)!='')
				alert(msg.sqlMsg);
			if(Number(msg.sqlMsgCode)<0 )
			{
			return false;
			}	
			//[067] drop & carry validation end 
			var flagfnDmxValidation = false;
			var flaglsiCheckForSignageValidation = false;
			var flagBillingLevel = false;
			var i=0;
			
			var test1 = jsonrpc.processes.poulateServiceList(jsData11);
		    var serviceCount1=test1.list.length;
		    var errorMsg = "";; 
			var count = 0;
		    if(serviceCount1==0)
			{
				alert("Please add atleast one service");
				return false;
			}
		    
		    for(j=0;j<test1.list.length;j++)
		    {
		    	/*
		    	 * Service should be active and if Stage is new all service will be validated,
		    	 * else only those services will be validated which are present in current owner bin
		    	 * If method called from action approval , then only selected services will be validated
		    	 * if all services are selected , then all will be validated
		    	 */
		    	if(((allServicesSelected==false && isCurrentServiceSelected(selectedServiceList,test1.list[j].serviceId)==true) || allServicesSelected==true) &&
		    			(test1.list[j].isServiceActive == 0) && ((stage=='New') || (stage != 'New' && test1.list[j].initiatedTo==gb_roleID))){
		    		
			    	if((test1.list[j].serviceTypeId == 202 || test1.list[j].serviceTypeId == 362)&& flaglsiCheckForSignageValidation == false)
			    	{
			    		var serviceIDChild = test1.list[j].serviceId;	     	
			    		var lsiCheckForSignageValidation = jsonrpc.processes.lsiCheckForSignageValidation(serviceIDChild);
			    		if(lsiCheckForSignageValidation == 2)
			    		{
		            		alert('Please select a valid LSI for '+serviceIDChild+' in service product attributes.');
		            		return false;
			    		}
			    		flaglsiCheckForSignageValidation = true;
			    	}
		    	
					var serviceId=test1.list[j].serviceId;
					var jsData12 =			
					{
						serviceId:serviceId
					};
				
					if(test1.list[j].serviceTypeId == 4 || test1.list[j].serviceTypeId == 21  || test1.list[j].serviceTypeId == 141
						|| test1.list[j].serviceTypeId == 81  || test1.list[j].serviceTypeId == 181  || test1.list[j].serviceTypeId == 221
						|| test1.list[j].serviceTypeId == 321 || test1.list[j].serviceTypeId == 281)
					{
						//Meenakshi : added to chk that harware should have atleast one child
						isHardwareValid = jsonrpc.processes.getDataForHardware(jsData12);
						if(isHardwareValid ==1){
							alert("Please Add Child For Hardware in Service : " + serviceId);
							return;
						}
						//Meenakshi : END
					}
				
					
					//Added By Saurabh for billing level check in product catelog
					var iBillingLevel=j+1;
					if(flagBillingLevel==false)
					{
						var billingLevelCheck_ServiceNos = jsonrpc.processes.checkBillingLevelForOrder(orderNo);
						if(billingLevelCheck_ServiceNos!=null && billingLevelCheck_ServiceNos!='')
						{
							var var_before_alert_popup=new Date().getTime();//Record time before alert popup (Logging for total taken time in validation action )
							var answer = confirm('Different billing level are selected for \n different line items of Service No(s): '+ billingLevelCheck_ServiceNos +' \n Do You Want To  Continue');
							var var_after_alert_popup=new Date().getTime();//Record time after alert popup (Logging for total taken time in validation action )
							if(answer)
							{
								var_alert_time_taken_answer=var_alert_time_taken_answer+(var_after_alert_popup-var_before_alert_popup);//total time taken for answer of alert (Logging for total taken time in validation action )
								//do nothing
							}
							else
							{
								return false;
							}
						}
						flagBillingLevel=true;
					}
				
					var serviceIdNo=test1.list[iBillingLevel-1].serviceId;
					//End By Saurabh
				
					//PAGING-SERVICE-LINE-14-10-2012
					if(test1.list[iBillingLevel-1].productCatelogEntered==0 && test1.list[iBillingLevel-1].isServiceActive == 0)
					{
						errorMsg= errorMsg +"*"+ "Please Input Product Attribute for Service No: "+serviceIdNo +"\n";
						validated=1;
					}
					if(test1.list[iBillingLevel-1].productCatelogEntered==0 && test1.list[iBillingLevel-1].isServiceActive == 0)
					{
						errorMsg=errorMsg +"*"+ "Please Input at least one Product for Service No: "+serviceIdNo +"\n";
						validated=1;
					}
					//PAGING-SERVICE-LINE-14-10-2012
			    }
		    }
		    var licProtest=jsonrpc.processes.validateProductNameLicCompMap(orderNo);
		    for(j=0;j<licProtest.list.length;j++)
		    {    
		    	finalErrorMessege= finalErrorMessege +"*"+"Product Name doesn't match with the Licence Co. for Line Item: "+licProtest.list[j] +"\n" ;
		    	validated=1;
		    }
		
			// added by manisha to validate tax rate start
		   	var orderNo=document.getElementById('poNumber').value;
			var errormsg1 = jsonrpc.processes.validateTaxRate(orderNo);
			if(errormsg1!="")
			{
				alert(errormsg1);
				return false;
			}
			// end
		
			//Service -sales validation
			//[053] START VIPIN
		    var outcome=doValidateDCHWChargeAndUpdateFlag();
		    if(outcome.returnStatus==-1){
		    	alert(outcome.message);
		    	return false;
		    }
		    //[053] End VIPIN
			var jsData = 
			{
				poNumber:document.getElementById('poNumber').value
			};
			//[051] START
			if(gb_roleID !=PM_ROLE && gb_roleID != SED_ROLE){
				result = jsonrpc.processes.ValidatePODetail(jsData);
				if(result!="")
				{ 
					errorMsg=errorMsg + result+"\n";
					count = count+1;
					validated=1;
				}
			}
			// [051] END If Condition
		    //[008]	Start
		    if (validated!=1)
		    {	
				var result;
				//Client Side for Service Summary
				var roleId=gb_roleID;
				var modeValue=gb_ModeValue;
				if(modeValue=="editON")
				{
					//do nothing
				}
				else
				{
					var jsData = 
					{
						poNumber:document.getElementById('poNumber').value
					};
					result = jsonrpc.processes.ValidateOrder(jsData);
					if(result.msgOut == "PO Validated Successfully" || result.msgOut == "PO Validated Successfully and Mail Send Successfully") 
				  	{
					  	document.getElementById('divSelectCurrencyId').style.display='none';	
					}
					else
					{
				    	errorMsg=errorMsg + result.msgOut;
				    	document.getElementById('divSelectCurrencyId').style.display='block';
									
					}
				}
			}
		    //[008] End
		
			if(errorMsg.length >0 || finalErrorMessege != "")
			{
				if(errorMsg.length >0)
					finalErrorMessege= finalErrorMessege +"*"+errorMsg ;
				alert(finalErrorMessege);
				//add by [058] updateStatus method to refresh the parent page
				updateStatus(0);
				return false;
			} 
			else 
			{
				var modeValue=gb_ModeValue;
				if(modeValue=="editON")
				{
					//[250511AKS]Start
					if(isActionOrPublish==1)
					{				
						document.getElementById('isValidatePO').value=1;
						return false;
					}
					//[250511AKS]End
					//[010] Start
					updateStatus(1);
					//--[15032013012]--start--//
					
					try{
						var var_end_validation_time=new Date().getTime();//Record time after end validation (Logging for total taken time in validation action )
						var var_total_time_taken=(var_end_validation_time-var_start_validation_time.getTime())-var_alert_time_taken_answer;//Record time total time (Logging for total taken time in validation action )
					
						loggTotalTimeTaken(orderNo,var_start_validation_time,var_total_time_taken,'VALIDATE');
					}catch(e){
						alert('Logging of validation recoding time failed!!');
					}
					fadeOutAlert('Order Validate','Order Validated Successfully.');
					/*
				 	 *followng function would be executed after 2 second. In this way we are providing
				 	 * to fadeout facility for above alert 'Order Validated Successfully' and prevent the overlapping 
				 	 * from other alerts or dialogs. 
				 	 */
				 	 
			 	 	document.getElementById('divSelectCurrencyId').style.display='none';
					attachWorkflow();
						
					//commented below code attachworkflow not call under timer
					//setTimeout(function() {
						//document.getElementById('divSelectCurrencyId').style.display='none';
						//attachWorkflow();
					//}, 2000);
					
					//--[15032013012]--end--//	
					
					//redirectAmForApproval();
					return false;
					//[010] End
				}
				else
				{
					//[250511AKS]Start	
					if(isActionOrPublish==1)
					{				
						document.getElementById('isValidatePO').value=1;
						return false;
					}
					//[250511AKS]End
					updateStatus(1);
				//--[15032013012]--start--//
					
					try{
						var var_end_validation_time=new Date().getTime();//Record time after end validation (Logging for total taken time in validation action )
						var var_total_time_taken=(var_end_validation_time-var_start_validation_time.getTime())-var_alert_time_taken_answer;//Record time total time (Logging for total taken time in validation action )
					
						loggTotalTimeTaken(orderNo,var_start_validation_time,var_total_time_taken,'VALIDATE');
					}catch(e){
						alert('Logging of validation recoding time failed!!');
					}
					fadeOutAlert('Order Validate','Order Validated Successfully.');
					/*
				 	 *followng function would be executed after 2 second. In this way we are providing
				 	 * to fadeout facility for above alert 'Order Validated Successfully' and prevent the overlapping 
				 	 * from other alerts or dialogs. 
				 	 */
				 	 
				 	 document.getElementById('divSelectCurrencyId').style.display='none';
				 	 attachWorkflow();
				 	 
				 	 //commented below code attachworkflow not call under timer
						//setTimeout(function() {
						//document.getElementById('divSelectCurrencyId').style.display='none';
						//attachWorkflow()}, 2000);
						
					//--[15032013012]--end--//					
					
					//redirectAmForApproval();
		 		}
		  		showLayer();
			}
		}//End CLEP else
}

function loggTotalTimeTaken(orderno,start_time,total_elapsed_time,actiontype){
	jsonrpc.processes.loggTotalTimeTaken(orderno,start_time,total_elapsed_time,actiontype);
}

function isCurrentServiceSelected(serviceList,currentService)
{
	//var isSelected = false;
	var str_array = serviceList.split(',');
	for(var i = 0; i < str_array.length; i++)
	{
		// Trim the excess whitespace.
		str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
	   
		if(str_array[i]==currentService){
			//isSelected=true;
			return true;
		}
	}
	return false;
}


function getSelectedServiceFromList(serviceListFromApproval,serviceListFromResult)
{
	var returnString='';
	var str_array_approval = serviceListFromApproval.split(',');//list of service that are selected from approval window for partial initiation
	var str_array_result = serviceListFromResult.split(',');//list of service that are coming from validation result
	for(var j = 0; j < str_array_approval.length; j++)
	{
		// Trim the excess whitespace.
		str_array_approval[j] = str_array_approval[j].replace(/^\s*/, "").replace(/\s*$/, "");
	   
		for(var i = 0; i < str_array_result.length; i++)
		{
			// Trim the excess whitespace.
			str_array_result[i] = str_array_result[i].replace(/^\s*/, "").replace(/\s*$/, "");
		   
			if(str_array_result[i]==str_array_approval[j]){
				if(returnString=='')
					returnString=str_array_result[i];
				else	
					returnString=returnString+','+str_array_result[i];
				break;
			}
		}
	}
	return returnString;
}


//Added by Saurabh for updating status against Tpomaster valid or incomplete 
function updateStatus (value){
	var orderNo = document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    var validInvalid = jsonrpc.processes.updateStatus(orderNo,value);
	var flag=5;
    ViewServiceTree(flag);
	return true;
}
//End By Saurabh

//-------------------------Validate PO----------------------------------------------

//[SUMIT007]
// isCharge Variable Added for Color Coding

var treeNodes;
function getTree(treeView,nextNode,nextLabel,nextUrl,serviceDetailId,isCharge, commercial)
{
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	var strCharges  =  escape("Charges Infor For Products");
		str = "<li ><span class='folder' ><input onmouseover='getTip(value)' onmouseout='UnTip()' id='rad_spId"+nextNode+"' type='radio' commercial = '" + commercial + "' name='rdo1' onclick=TreeNode("+ serviceDetailId +","+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >";	
		
	if(global_firstNode!=nextNode)
	{
	
	 child_count++;
	 
		str=str+"<input onmouseover='getTip(value)' onmouseout='UnTip()' type='checkbox' id='chk_spId"+nextNode+"' name='chk_spId' value='"+nextNode+"' onclick=checkUncheck("+ nextNode +")>";
		
		if(isCharge=="1")
			str=str+"<input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' name='hdnserviceDetailId'  value='"+serviceDetailId+"'><input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' name='serviceDetailId' value='"+nextNode+"'><font style='color:#FF0000'>"+ nextLabel + "</font></span>";
		else
			str=str+"<input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' name='serviceDetailId' value='"+nextNode+"'>"+ nextLabel + "</span>";
	}
	else 
	{
	str=str+"<input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' name='serviceDetailId' value='"+nextNode+"'><font style='color:#999900'>"+ nextLabel + "</font></span>";
	
	}
	
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
		 	parrentArray[i]=treeView.lstTreeItems.list[i].serviceProductParentId;
	 		childArray[i]=treeView.lstTreeItems.list[i].serviceProductChildId;
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL,treeView.lstTreeItems.list[i].serviceChildId,treeView.lstTreeItems.list[i].isChargePresent, treeView.lstTreeItems.list[i].isCommercial)+"</ul>";
		}
	}
	str=str+"</li>";
	return str;
}
var global_currTreeNode;
var serviceDetailId1;
//Fetch Zone List acording to Region
function fnGetZoneList()
{
	var regionId=document.getElementById("region").value;	
	var tr, td, i, j, oneRecord;
    var test;
    var combo = document.getElementById("zone");	
   var iCountRows = combo.length;
  
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(i);
   }
	
    var jsData =			
    {
		regionId:regionId
	};
	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    test = jsonrpc.processes.populateZoneList(jsData);    
    if(document.getElementById('zone_1')==null)
    {
	    for(j=0;j<test.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test.list[j].zoneName;
			option.value = test.list[j].zoneId;
			option.title = test.list[j].zoneName;
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
    else
    {
       var selectedZone = document.getElementById('zone_1').value;
	    for(j=0;j<test.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test.list[j].zoneName;
			option.value = test.list[j].zoneId;
			option.title = test.list[j].zoneName;
			try 
			{
			combo.add(option, null); //Standard
			
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	    combo.value = selectedZone;
	    }       
}

function TreeNode(serviceDetailId,nextNode,nextUrl , nextLabel)
{
		 //TRNG22032013026 added by manisha start
		var newCounter=1;
		var chkLength=document.forms[0].chkService.length;
		if(chkLength==undefined)
		{
			if(document.forms[0].chkService.checked==true)
			{
				document.getElementById('hdnCurrentTempCounter').value=newCounter;
			}
		}		
						
		else
		{
			for(i=0;i<chkLength;i++,newCounter++)
			{
				var myform=document.getElementById("searchForm")
				if(myform.chkService[i].checked==true)
				{
					
						document.getElementById('hdnCurrentTempCounter').value=newCounter;
				}
			}
		}				
		//TRNG22032013026 added by manisha end		
	global_currTreeNode=nextNode;
	var oTokens = unescape(nextLabel).tokenize('(',' ',true);
	productName=oTokens[0];
	document.getElementById('txtLineItemName').value=unescape(productName);
	document.getElementById('txtLineItemNo').value=nextNode;
	serviceDetailId1=serviceDetailId;
	document.forms[0].SPIDUrlforVPC.value = unescape(nextUrl);
	
	if(unescape(nextUrl)=="")
	{
		//iCountTreeNode=2;
		//alert('Total Products in service level ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode));
	}	
	else
	{
		//iCountTreeNode=0;	
		//alert('Total Products in product level ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode));
		if(window.confirm("Are you want to view the product details?"))
		{
			fnViewProductCatelog(nextUrl);
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


/*function getTreeNodeCount(treeView,nextNode)
{
	
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
		
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
			iCountTreeNode = iCountTreeNode + 1;
		 	return getTreeNodeCount(treeView,treeView.lstTreeItems.list[i].serviceProductChildId)
		}
	}
	iCountTreeNode = iCountTreeNode + 1;
	return iCountTreeNode;
}*/

var global_firstNode;
function fncServiceTreeView(serviceTypeId)
{
		var chkLength=document.forms[0].chkService.length;
		for(i=0;i<chkLength;i++)
		{
			//TODO, inspite of checking hidden variable can be passed directly
			if(document.getElementById("chkService"+i).checked==true)
			{
			checkedServiceNumber=i;
			}
		}
	//Check service is publish or Unpublish
	var checkedCounter=1;
	var myform=document.getElementById("searchForm");
		//var chkLength=document.forms[0].chkService.length;
	if(document.getElementById('deleteProductDiv') != null)
	{
     if(chkLength==undefined)
	 {
		if(document.forms[0].chkService.checked==true)
		{
		    checkedCounter++;
		    if(document.getElementById('chk'+0).value=="Yes") 
			{
				document.getElementById('deleteProductDiv').style.display="none";
				document.getElementById('cancelServiceDiv').style.display="none";
				document.getElementById('validateOrderDiv').style.display="none";
				document.getElementById('productcatelog').style.visibility='hidden';				
			}
			else
			{
				document.getElementById('deleteProductDiv').style.display="block";
				document.getElementById('cancelServiceDiv').style.display="block";
				document.getElementById('validateOrderDiv').style.display="block";
				document.getElementById('productcatelog').style.visibility='visible';
			}		
		 }
	  }	
      else
	  {
		for(i=0;i<chkLength;i++)
		{		
		     if(myform.chkService[i].checked==true)
			{
			   checkedCounter++;
			   if(document.getElementById('chk'+i).value=="Yes") 
			    {	
			        document.getElementById('deleteProductDiv').style.display="none";
				    document.getElementById('cancelServiceDiv').style.display="none";
				    document.getElementById('validateOrderDiv').style.display="none";
				    document.getElementById('productcatelog').style.visibility='hidden';	
			    }
			    else
				{
					document.getElementById('deleteProductDiv').style.display="block";
					document.getElementById('cancelServiceDiv').style.display="block";
					document.getElementById('validateOrderDiv').style.display="block";
					document.getElementById('productcatelog').style.visibility='visible';
				}
			 }
		 }
	  }
	}
	document.getElementById('hdnservicetypeid').value=serviceTypeId;
	var newCounter=1;
	var chkLength=document.forms[0].chkService.length;
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			if(document.forms[0].txtServiceNo1.value=="")
			{
				alert("Pls. Enter Service");
				return false;
			}
			else
			{
				document.getElementById('browser').innerHTML= "";
			    var jsData = 
			    {
			    	serviceId:document.forms[0].txtServiceNo1.value,
			    	orderNumber:document.getElementById('poNumber').value
			    };
			  	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
				treeNodes = treeView;
				 document.getElementById('hdnserviceid').value = document.forms[0].txtServiceNo1.value;
				selectedServiceName = document.forms[0].txtServiceName1.value;
				var url ="";
				var nodeText ="";
				var parentNodeId;
				var serviceDetailId;
				var commercial="";
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	serviceDetailId = treeView.lstTreeItems.list[i].serviceChildId;
					 	commercial = treeView.lstTreeItems.list[i].isCommercial;
					 	break;
					}
				}
				 global_firstNode=parentNodeId;
				child_count=0;
				var treeString=getTree(treeView,parentNodeId,nodeText,"",serviceDetailId, commercial);
				if(child_count==0)
				{
				var str="&nbsp;<b>Select All</b>&nbsp;<input onmouseover='getTip(value)' onmouseout='UnTip()' type='checkbox' name='SelectAllChck' id='SelectAllChck' disabled=true />";
				}
				else
				{
               	var str="&nbsp;<b>Select All</b>&nbsp;<input onmouseover='getTip(value)' onmouseout='UnTip()' type='checkbox' name='SelectAllChck' id='SelectAllChck' onclick='fnCheckAll();'/>";
                }
                document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
			 	var branches = $(str + "<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});
			


			  	/*for(var j=0;j<treeView.lstTreeItems.list.length;j++)
			  	{
					treeString = treeString + "<li><span class='file'><a href='#' onclick=fnViewProductCatelog(\'"+ escape(treeView.lstTreeItems.list[j].treeViewURL) +"\');>"+ treeView.lstTreeItems.list[j].treeViewText + "</a></span></li>"    		
			   	}	
			 	var branches = $("<li><span class='folder'>" + document.forms[0].txtServiceName1.value + "</span><ul>" + 
			 		treeString +
			 		"</ul></li>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});*/
			}
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm");
			if(myform.chkService[i] != null && myform.chkService[i].checked == true)
			{
			  if (document.getElementById('txtServiceNo' + newCounter) != null)
				{
					if(document.getElementById('txtServiceNo'+newCounter).value=="")
					{
						alert("Pls. Enter Service For " + newCounter + " Element.");
						return false;
					}
					else
					{
						document.getElementById('browser').innerHTML= "";
					    var jsData = 
					    {
					    	serviceId:document.getElementById('txtServiceNo'+newCounter).value,
					    	orderNumber:document.getElementById('poNumber').value
					    };
					  	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
						 var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
						 treeNodes = treeView;
						 
						 selectedServiceName = document.getElementById('txtServiceName'+newCounter).value;
						 document.getElementById('hdnserviceid').value = document.getElementById('txtServiceNo'+newCounter).value;
						var treeString="";
						var url ="";
						var nodeText ="";
						var parentNodeId;
						var commercial = ""; 
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;	
							 	serviceDetailId = treeView.lstTreeItems.list[i].serviceChildId;
							 	commercial = treeView.lstTreeItems.list[i].isCommercial;
							 	break;
							}
						}
						global_firstNode=parentNodeId; 
						child_count=0;
						
						var treeString=getTree(treeView,parentNodeId,nodeText,"",serviceDetailId, commercial);
						if(child_count==0)
						{
						var str="&nbsp;<b>Select All</b>&nbsp;<input onmouseover='getTip(value)' onmouseout='UnTip()' type='checkbox' name='SelectAllChck' id='SelectAllChck' disabled=true />";
						}
						else
						{
						var str="&nbsp;<b>Select All</b>&nbsp;<input onmouseover='getTip(value)' onmouseout='UnTip()' type='checkbox' name='SelectAllChck' id='SelectAllChck' onclick='fnCheckAll();'/>";
						
						}
						document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
							 
					 	var branches = $(str+"<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;
					}
				}
			}
		}
	}
	var selectedNode=document.getElementById('hdnServiceProductId').value;
	if(selectedNode!="")
	{
		document.getElementById('rad_spId'+selectedNode).checked=true;
		var hiddenLineItemName=document.getElementById('hdnServiceProductName').value;
		if(hiddenLineItemName!="")
		{
			var oTokens = unescape(hiddenLineItemName).tokenize('(',' ',true);
			document.getElementById('txtLineItemName').value=unescape(oTokens[0]);
		}	
		document.getElementById('txtLineItemNo').value=selectedNode;
		document.getElementById('hdnServiceProductId').value="";
	}
}

//------------------------------------------Conatct Tab------------------------------

function DeleteContact()
  {
  	 var result;
	 conatctTab = document.getElementById('tableContact');
	 addressTab = document.getElementById('tableAddress');
     var rowlen= conatctTab.rows.length;
	 flag=2;
   	 var rowCounter = 1;
   	 var idCounter = 1;
	 var myform=document.getElementById("searchForm");

	 var delRows = "";
	 var contactID="";
	 var addressID="";
	 var countDelete=1;
	 var contactID="";
	 var addressID="";
	 var rownos="";
	 if(rowlen==1)
	 {
	 	alert("No Row to Delete");  // 083
	 }
	 if(rowlen==2)
	 {
		 	if(document.forms[0].chkSelectContactDetail.checked==true)
		 	{
		 	   		countDelete++;
			 	   if(myform.contactId[0].value != "0")
		  			{
			 	  		var result1;
			 	  		var answer = confirm("Do You Want To Continue Deleting Contact Line")
						if(answer)
						{
							//continue deleting
						}
						else
						{
							return false;
						}
				  		//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				  		
				  		  var jsData =			 
							{
							    contactIDList:myform.contactId[0].value,
							    poNumber:document.forms[0].poNumber.value
							};
					  	   result1 = jsonrpc.processes.getCountContactNumber(jsData);
				  	
					    if(result1>0)
						{
							   var jsData =			
									     {
									          contactIDList:myform.contactId[0].value,
									          addressIDList:myform.addID[0].value
									     };
											result=  jsonrpc.processes.DeleteContactDetailRows(jsData);
											conatctTab.deleteRow();
	    									addressTab.deleteRow();
	    									calculatesino();  //084 start
	    									unCheckedSelectAllContact();
	    									
						}
					    else
						{
							
							 alert("You cannot delete all saved Contact & Address details (of Row: 1)");
							return false;
						}
			        }
			        
			    else
			    {
			    	conatctTab.deleteRow();
	    			addressTab.deleteRow();
	    			calculatesino(); //084 start
	    			unCheckedSelectAllContact();
			    }    
	 	}
	 	
	 	else {
	       	   alert('Please Select One Row To Delete ');
	       		return false;
	         }
  }
	 
	 else
	 {
	 	 
	   for(var j=1;j<rowlen;j++)
		{
	 	  	if(myform.chkSelectContactDetail[(j-1)].checked==true)
	     	{
	     	    countDelete++;
	     	    if(myform.contactId[j-1].value!="0" )
                 {
		   			if(contactID=="")
		    		{
		    			contactID = myform.contactId[(j-1)].value;
		    			addressID = myform.addID[(j-1)].value;
		    		}	
		    		else
		    		{
		    			contactID = contactID + "," + myform.contactId[(j-1)].value;
		    			addressID = addressID + "," + myform.addID[(j-1)].value;
		    		}	
	         }
	    }  
	}
	       if(countDelete==1 && rowlen>1)
	       {
	       			alert('Please Select One Row To Delete ');
	       			return false;
	       }
			if(contactID != "")
		    {	   
			    	var answer = confirm("Do You Want To Continue Deleting Contact Line")
					if(answer)
					{
						//continue deleting
					}
					else
					{
						return false;
					}
				   //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			  		var jsData =			 
					{
					   contactIDList:contactID,
					    poNumber:document.forms[0].poNumber.value
					};
			  	   result1 = jsonrpc.processes.getCountContactNumber(jsData);
				    if(result1>0)
				    {		
						var jsData =			
							     {
							          contactIDList:contactID,
							          addressIDList:addressID
							     };
									result=  jsonrpc.processes.DeleteContactDetailRows(jsData);
									 	for(var j=rowlen-1;j>0;j--)
									  		{
									  	
									    		if(myform.chkSelectContactDetail[(j-1)].checked==true)
									     			{
									
													document.getElementById('tableContact').deleteRow(j);
				   		            				document.getElementById('tableAddress').deleteRow(j);
				   		            				calculatesino();  //084 start
				   		            				unCheckedSelectAllContact();
		   		            						}
		   		            				}	
		   		    }
		   		          
		   		   else
		   		     {
		   		     		for(var j=1;j<rowlen;j++)
							{
		   		     		
			   		     		if((myform.chkSelectContactDetail[(j-1)].checked==true)&& (myform.contactId[j-1].value!="0"))
								  {
									  	if(rownos=="")
									  	{
									  		rownos=j;
									  	}
									  	else
									  	{
									  
											rownos =  rownos + "," + j;
										}	
													
			   		              }
		   		     		}
		   		     			
		   		     	
			   		       alert("You cannot delete all saved Contact & Address details (of Row Nos :"+ rownos + ").  Please unselect any saved row");
			   		        return false;
		   		     } 
	   		 } 
	   		      
		   	   else
		   	   {  
		   	  	    for(var j=rowlen-1;j>0;j--)
					 {
			           if(myform.chkSelectContactDetail[(j-1)].checked==true)
			     		{
							document.getElementById('tableContact').deleteRow(j);
 		            		document.getElementById('tableAddress').deleteRow(j);
 		            		calculatesino(); //084 start
 		            		unCheckedSelectAllContact();
	            		}
	            	}		 
		       } 
	}
	
		
	   
}

function getTreeNodeCountForDropDown(treeViewForProduct,nextNode)
{
	var i;
	var localCount=1;
	for(i=0;i<treeViewForProduct.list.length;i++)
	{
		if(nextNode==treeViewForProduct.list[i].serviceChildId)
		{
			localCount = localCount + getTreeNodeCountForDropDown(treeViewForProduct,treeViewForProduct.list[i].serviceParentId);
		}
	}
	//localCount = localCount +1;
	return localCount;
}



function getTreeForDropDown(nextnode1)
{
		
	var strval = getTreeForDropDownValue(treeViewForProduct,nextnode1)	
	document.getElementById('txtddvalue11').value = strval;
	alert(strval);
	

//    var combo = document.getElementById("tsttree");	
//    var iCountRows = combo.length;  
//   	for (i = 1; i <  iCountRows; i++)
//   	{
//       alert(combo.items[i].value);
//   	}
	
	
}

var strdd ="";

function getTreeForDropDownValue111(treeViewForProduct,nextnodedd)
{
	
	var str="";
	var icountDash= 0;
	var sDash = "";
	icountDash = getTreeNodeCountForDropDown(treeViewForProduct,nextnodedd)
	sDash = "";
	for(j=0;j<icountDash*10;j++)
		sDash = sDash + " ";
	sDash = sDash + " ";
	str = str + "<option>" + sDash + nextnodedd + "</option>" ;

	    var combo = document.getElementById("tsttree");
    	var option = document.createElement("option");
  		option.text = sDash + nextnodedd;
		option.value = nextnodedd;
	try 
	{
		combo.add(option, null); //Standard	
	}
	catch(error) 
	{
		combo.add(option); // IE only
	}
	
	
	for(var i=0;i<treeViewForProduct.list.length;i++)
	{
		if(nextnodedd==treeViewForProduct.list[i].serviceParentId)
		{						
			str = str + getTreeForDropDownValue(treeViewForProduct,treeViewForProduct.list[i].serviceChildId);
	    }
	}	
	return str;
}

function getTreeForDropDownValue(treeViewForProduct,nextnodedd)
{
	
	var str="";
	var icountDash= 0;
	var sDash = "";
	icountDash = getTreeNodeCountForDropDown(treeViewForProduct,nextnodedd)
	sDash = "";
	for(j=0;j<icountDash*10;j++)
		sDash = sDash + " ";
	sDash = sDash + " ";
	
	var service = document.getElementById('serviceDetailID');
	for(var j=0;j< service.options.length;j++)
	{
		if(service.options[i].value == nextnodedd)
			service.options[i].text = sDash + service.options[i].text;
	}
	
	
	for(var i=0;i<treeViewForProduct.list.length;i++)
	{
		if(nextnodedd==treeViewForProduct.list[i].serviceParentId)
		{						
			str = str + getTreeForDropDownValue(treeViewForProduct,treeViewForProduct.list[i].serviceChildId);
	    }
	}	
	return str;
}

////////////////////////////////////////////////////////////



//038 start

function DeletePO()
  {
     var result;
	 POTab = document.getElementById('tablePO');
	 var podetailno="";
	 flag=3;
     var rowlen= POTab.rows.length;
     var k;
   	 var rowCounter = 1;
   	 var idCounter = 1;
	 var myform=document.getElementById("searchForm");
	 var delRows = "";
	 var countDelete=1;
	 var POID="";
	 var result3=0;
	 var rownos="";
	 if(rowlen==1)
	 {
	 alert("please add one row to delete");
	 return false;
	 }
	  var chkLength=document.forms[0].chkSelectPODetail.length;
	  
	  if(chkLength==undefined)
	   {
	 	if(document.forms[0].chkSelectPODetail.checked==true)
	 	{
	 		countDelete++;
	 		var dpochecked=document.forms[0].defaultPO.checked;
		    if(dpochecked==true)
			{
				alert('You cant delete PO Line with Default Po');
				return false;
		    }
	    	if(document.forms[0].poDetailNo.value == "")
	  		{
	  		
	  		   POTab.deleteRow();
	  		   unCheckedSelectAllPO();
	  		}
	    	else
	    	{  
	 	  		var result1;
	 	  		var answer = confirm("Do You Want To Continue Deleting PO Line")
				if(answer)
				{
					//continue deleting
				}
				else
				{
					return false;
				}
		  		//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
		  		
		  		var jsData =			 
		   		{
		    		poSearchDetailNumber:document.forms[0].poDetailNo.value,
		    		poNumber:document.forms[0].poNumber.value
		  		};
		  		result1 = jsonrpc.processes.getCountPODetailNumber(jsData);
		  	
			    if(result1>0)
				{
				   		
							    var jsData =			 
					   		{
					    		poSearchDetailNumber:document.forms[0].poDetailNo.value
					  		};
				  		     
						     delRows=jsonrpc.processes.DeletePODetailRows(jsData);
						     POTab.deleteRow();
						     unCheckedSelectAllPO();
				 }
				
			    else
				{
					alert('you cannot delete all saved PO Lines');
					return false;
				}
	        }
	 	}
	 	
	 	else
	 		{
		       	   alert('Please Select One Row To Delete ');
		       		return false;
	       	}
	}

else
	 {
	   for(k=0;k<document.forms[0].chkSelectPODetail.length;k++)
		{  
	 	  	if(myform.chkSelectPODetail[k].checked==true)
	     	{
	     		countDelete++;
	     		var dpochecked=myform.defaultPO[k].checked;
			    if(dpochecked==true)
				{
					alert('You cant delete PO Line with Default Po');
					return false;
			    }
	     	    
	     	     if(myform.poDetailNo[k].value!="" )
                 {
	               	if(podetailno=="")
		               	{
			    			podetailno =myform.poDetailNo[k].value;
			    		}	
	    			else
			    		{
			    			podetailno = podetailno + "," + myform.poDetailNo[k].value;
			    		}	
	        	}
	     	}	    
	   }
	
	       if(countDelete==1 && rowlen>1)
		       {
		       		alert('Please Select One Row To Delete ');
		       		return false;
		       }
		       
		 if(podetailno != "")
		    {	   
			    	var answer = confirm("Do You Want To Continue Deleting PO Line")
					if(answer)
					{
						//continue deleting
					}
					else
					{
						return false;
					}
				   //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			  		var jsData =			 
					{
					     	poSearchDetailNumber:podetailno,
					     	poNumber:document.forms[0].poNumber.value
					};
			  	   result1 = jsonrpc.processes.getCountPODetailNumber(jsData);
			  	
				    if(result1>0)
				    {		
						var jsData =			
							     {
							          poSearchDetailNumber:podetailno
							     };
									result=  jsonrpc.processes.DeletePODetailRows(jsData);
									for(var j=rowlen-1;j>0;j--)
									  		{
									  	
									    		if(myform.chkSelectPODetail[(j-1)].checked==true)
									     			{
									
													document.getElementById('tablePO').deleteRow(j);
													unCheckedSelectAllPO();
				   		            				
		   		            						}
		   		            				}
		   		    }
		   		          
		   		 else
		   		     {
		   		     		for(var j=1;j<rowlen;j++)
							{
		   		     		
			   		     		if((myform.chkSelectPODetail[(j-1)].checked==true)&& (myform.poDetailNo[j-1].value!=""))
								  {
		   		     		
			   		     		
										if(rownos=="")
									  	{
									  		rownos=j;
									  	}
									  	else
									  	{
									  
											rownos =  rownos + "," + j;
										}
													
			   		              }
		   		     		}
		   		     
			   		     alert("you cannot delete all saved PO details (of Row Nos :"+ rownos + "). Please unselect any saved row  ");
			   		        return false;
		   		     } 
	   		   } 
	   		      
	   		 else
	   		 {
				 for(var j=rowlen-1;j>0;j--)
					{
					  if(myform.chkSelectPODetail[(j-1)].checked==true)
						{
							document.getElementById('tablePO').deleteRow(j);
							unCheckedSelectAllPO();
				   		            				
		   		        }
		   		   }
			 }	           
		}
		
	}
	
	
//038 end	




//------------------------------------------Conatct Tab------------------------------
//------------------------------------------Cancel Service------------------------------
function cancelService(isCancelAndCopy)
{
	try{
	//added by anil for clep prvent cancelservice activity onclick 
    if(document.getElementById('cancelServiceDiv').disabled==true){
    	return false
    }else{
	//Start [030]
	/*var isCancelAndCopy is passed to check whether it is Cancel Service
	or cancelAndCopy Functionality*/
	//End [030]
	
	//Added By Saurabh for Role Check
    //Start
    var stage = document.getElementById('stageName').value;
   
    var loggedRoleName =gb_roleID;
    if(isCancelAndCopy==0)
    {
	    if(stage=="Partial Publish" || stage=="Billing trigger")
	    {
	    	alert("You are not authorised to use this functionality");
	    	return false;
	    }
	    var roleWiseSectionDetail = getRoleWiseSectionDetails(gb_roleID);
	     var commercialNotAllowed = checkRoleWiseCommercialNotAllowed(roleWiseSectionDetail);
	     if(commercialNotAllowed){
	    	 /*
	    	  * By vijay commenting old method and using new version
	    	  */
	    	//var commercialLineToBeDeleted =  checkServiceTreeContainsCommercialLine("browser");
	    	var commercialLineToBeDeleted =  checkServiceTreeContainsAnyCommercialLine("browser");
	    	if(commercialLineToBeDeleted){
	    		alert("You are not authorized to Cancel this Service");
	    		return false;
	    	}
	     }
	}
	else if(isCancelAndCopy==1)
	{
		if(loggedRoleName!=4)
    	{
    		alert('Only SED Can Use This Functionality');
    		return false;
    	}
    } 
    //End
    serviceTab = document.getElementById('ServiceList');
    var myform=document.getElementById("searchForm");
    var newCounter=1;
    if(document.forms[0].chkService)
    {
		var chkLength=document.forms[0].chkService.length;
		var checkedCounter=1;
		if(chkLength!=undefined)
		{
			for(i=0;i<chkLength;i++)
				{
					if(myform.chkService[i].checked==true)
					{
					  checkedCounter++;
					   serviceNo=myform.chkService[i].value;
					}
				}
				if(checkedCounter==1)
				{
				  alert('Please Select Service Type!!');
				  return false;
				}
		 }
		if(chkLength==undefined)
		{
			if(myform.chkService.checked==true)
			{
			  checkedCounter++;
			   serviceNo=myform.chkService.value;
			}
		 if(checkedCounter==1)
			{
			  alert('Please Select Service Type!!');
			  return false;
			}
		}
		//Start VCS and L3 MPLS Validation		
	 	var vcsResults;
		var jsData =			
	    {
		  serviceId:serviceNo,
		  orderNumber:document.getElementById('poNumber').value,
		   remarks:"Cancel"
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");	
		vcsResults = jsonrpc.processes.validateVCSServicesBeforeCancel(jsData);
		if(vcsResults!="" && vcsResults!=null)
		{
			alert(vcsResults);
			return false;
		}
		//End VCS Validation		
		if(chkLength==undefined)
		{
			if(myform.chkService.checked==true)
		     {   
		         //Added By Saurabh to restrict user from cancelling services which are already published
			         if(document.getElementById('chk'+0).value=="Yes") 
					 {
					 	alert("Service already published cannot be cancelled");
					   	return false;
					 }
					 if(document.getElementById('serviceStatus'+1).value=="CancelAndCopy" || document.getElementById('serviceStatus'+1).value=="Cancelled in CRM")
			   		{
						alert("Service already cancelled");
						return false;
					}
				 //End by Saurabh
		         serviceNo=myform.chkService.value;
		         //[052]
		         if(serviceNo!="")
				  {
				    	var path = gb_path+"/NewOrderAction.do?method=serviceCancelReason&isCancelAndCopy="+isCancelAndCopy;
						window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:300px");
				 var result;
					var jsData =			
				    {
					  serviceId:serviceNo
					};
					var btn_click=document.getElementById('buttonClicked').value;
					if(btn_click==1){
					if(confirm("Are you sure you want to cancel this service"))
   					{
					    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
					    
						//Added By Saurabh for Partial Publish Check
			    			//Start By Saurabh
						newOrderStatusServiceId = jsonrpc.processes.newOrderStatusServiceId(serviceNo);
							if(serviceNo == newOrderStatusServiceId)
							{
								alert("Service already published cannot be cancelled");
								return false;
							}
					//[0052]
					CancellationOfService(reasonForCancelService,isCancelAndCopy);
   					return false;
					//End By Saurabh
					   // if(serviceNo!="")
					    //{
					    	//var path = gb_path+"/NewOrderAction.do?method=serviceCancelReason&isCancelAndCopy="+isCancelAndCopy;
							//window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
					       /*result = jsonrpc.processes.DeleteServiceDetails(jsData);
					       if(result==null)
					       {
					       		
								var myForm=document.getElementById('searchForm');				
								myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
								myForm.submit();
								alert("Session has been expired!!");		
								window.close();			
								
					       }
					       serviceTab.deleteRow(1); 
					       
					       if(result.msgOut != "" && result.msgOut != null)
					       {
					       		alert(result.msgOut);
					       }
					       else
					       {
					       		alert("Row(s) deleted successfully");
					       } */
					    }
					  else
					    {
					      //serviceTab.deleteRow(1); 
					    } 
				}
			}
		}
		}
		else
		{
			for(i=0;i<chkLength;i++)
			{
				var myform=document.getElementById("searchForm")
				
				if(myform.chkService[i] != null && myform.chkService[i].checked == true)
				{
				    //Added By Saurabh to restrict user from cancelling services which are already published
					    if(document.getElementById('chk'+i).value=="Yes") 
					    {
					    	alert("Service already published cannot be cancelled");
					    	return false;
					    }
					    if(document.getElementById('serviceStatus'+(i+1)).value=="CancelAndCopy" || document.getElementById('serviceStatus'+(i+1)).value=="Cancelled in CRM")
				   		{
							alert("Service already cancelled");
							return false;
						}
				    //End By Saurabh
				    
				    serviceNo=myform.chkService[i].value;
					var result;
					//[052]
					 if(serviceNo!="")
					    {
					     
					    	var path = gb_path+"/NewOrderAction.do?method=serviceCancelReason&isCancelAndCopy="+isCancelAndCopy;
							window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:300px");
					
					var jsData =			
				    {
					  serviceId:serviceNo
					};
					var btn_click=document.getElementById('buttonClicked').value;
					if(btn_click==1){
				    if(confirm("Are you sure you want to cancel this service"))
   					{
					    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
					    
					    //Added By Saurabh for Partial Publish Check
			    		//Start By Saurabh
						newOrderStatusServiceId = jsonrpc.processes.newOrderStatusServiceId(serviceNo);
							if(serviceNo == newOrderStatusServiceId)
							{
								alert("Service already published cannot be cancelled");
								return false;
							}
					    //End By Saurabh
					    		//[0052]
							CancellationOfService(reasonForCancelService,isCancelAndCopy);
							return false;
							//if(serviceNo!="")
					    //{
					     
					    	//var path = gb_path+"/NewOrderAction.do?method=serviceCancelReason&isCancelAndCopy="+isCancelAndCopy;
							//window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
					    	
					    	//CancellationOfService(jsData);
					      //result = jsonrpc.processes.DeleteServiceDetails(jsData);
					      // if(result==null)
					     //  {
					       		//start [023]					
								//var myForm=document.getElementById('searchForm');				
								//myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
								//myForm.submit();
								//alert("Session has been expired!!");		
								//window.close();			
								//end [023]
					     //  }
					      // serviceTab.deleteRow((i+1)); 
					       
					      // if(result.msgOut != "" && result.msgOut != null)
					     //  {
					      // 		alert(result.msgOut);
					      // }
					     //  else
					      // {
					       //		alert("Row(s) deleted successfully");
					      // }
					    }
					    else
					    { 
					      //serviceTab.deleteRow((i+1)); 
					    }
					}
					}
				}
			}
		}
		//rohit
		//ViewServiceTree(flag);
	}
	else
	{
		alert("Please Select a Service!!");
		return false;
	}
	}//End CLEP else
	}catch(e)
	{
		alert('error code 72: '+ e.message);
	}
}

//start Ramana
function deleteService(isCancelAndCopy)
{
	serviceTab = document.getElementById('ServiceList');
    var myform=document.getElementById("searchForm");	
	var checkedCounter=0;
	var serviceId1;
	var serviceNo;
	var str = "";
	if(document.forms[0].chkDeleteService==undefined)
		var serviceLength=0
	else
		var serviceLength=document.forms[0].chkDeleteService.length;
	var stage = document.getElementById('stageName').value;
	if(isCancelAndCopy==0)
    {
		if((stage != "New") && (stage !="AM"))
	    {
	    	alert("You are not authorised to use this functionality");
	    	return false;
	    }
	}    
	if(serviceLength==0)
	{
		alert("Please Add Atleast One service"); 
		return false;
	}			
	
	else if(serviceLength==undefined)
	{
		if(document.getElementById('serviceStatus'+1)==null)
		{
			alert("Please Add Service First"); 
			return false;
		}
		if(myform.chkDeleteService.checked==true)
		{
			serviceNo=myform.chkService.value;
			if(document.getElementById('serviceStatus'+1).value=="CancelAndCopy" || document.getElementById('serviceStatus'+1).value=="Cancelled in CRM")
   			{
   				if(isCancelAndCopy==1)
   				{
   					alert("You Cannot Cancel Already Cancelled Service");
   					return false;
   				}
   				// [15032013016] START
   				else if (isCancelAndCopy==0 && (stage =="New" || stage =="AM" ))
   				{
   				//alert("Deleting the service ");
   				}
   				//[15032013016] END
   				else
   				{
   					alert("You Cannot Delete Already Cancelled Service");
   					return false;
   				}
			}
			if(document.getElementById('chk'+0).value=="Yes")
			{
				if(isCancelAndCopy==1)
   				{
					alert("Published Service Cannot Be Cancelled")
					return false;
				}
   				else
   				{
   					alert("Published Service Cannot Be Deleted");
   					return false;
   				}
			}
			if(isCancelAndCopy==1)
			{
				//if(confirm("Are you sure you want to cancel this service"))
   				//{
					cancelAndCopyWithServiceString(serviceNo,1,1);
					return false;
				//}	
			}
			var jsData =			
			{
				serviceId1:serviceNo,
				orderNumber:document.getElementById('poNumber').value
			};
   			if(confirm("Are you sure you want to delete this service"))
   			{
	   			//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				result = jsonrpc.processes.DeleteServices(jsData);
				       
				if(result.msgOut != "" && result.msgOut != null)
				{
					alert(result.msgOut);
					//PAGING-SERVICE-LINE-14-10-2012
					if(iNoOfPages==pageNumber){
						if(result.totalRecord==iTotalLength){
							pageNumber=pageNumber-1;
						}
					}	
					countService=1;
					document.getElementById('browser').innerHTML= "";					
					document.getElementById("lblServiceDetail").innerHTML = "" ;					
					//drawTable();	
					DrawTable('SERVICENO','SERVICELINETABLE');							       		
				}
			}	
		}
		else
		{
			alert("Please Select A Service First By checking In Checkbox");
			return false;
		}
	}
	else
	{
		for(i=0;i<serviceLength;i++)
		{
			if(myform.chkDeleteService[i].checked==true)
	     	{    		 
				checkedCounter++;
				if(document.getElementById('serviceStatus'+(i+1)).value=="CancelAndCopy" || document.getElementById('serviceStatus'+(i+1)).value=="Cancelled in CRM")
     			{
     				if(isCancelAndCopy==1)
	   				{
	   					alert("You Cannot Cancel Already Cancelled Service");
	   					return false;
	   				}
	   				// [15032013016] START
	   				else if (isCancelAndCopy==0 && (stage =="New" || stage =="AM" ))
	   				{
	   				//alert("Deleting the service ");
	   				}
	   				//[15032013016] END
	   				else
	   				{
	   					alert("You Cannot Delete Already Cancelled Service");
	   					return false;
	   				}
				}
				if(document.getElementById('chk'+i).value=="Yes")
				{
					if(isCancelAndCopy==1)
	   				{
						alert("Published Service Cannot Be Cancelled")
						return false;
					}
	   				else
	   				{
	   					alert("Published Service Cannot Be Deleted");
	   					return false;
	   				}
				}
				if(str=="")
				{
					str=myform.chkService[i].value;
				}
				else
				{
					str=str + "," +myform.chkService[i].value;
				}
			}
		}
	}		
	//return false;
	if(serviceLength>1)
	{
		if(checkedCounter==0)
		{
			alert("Please Select A Service First By checking In Checkbox");
			return false;
		}	
		
		if(checkedCounter>0)
		{	
			if(isCancelAndCopy==1)
			{
				//if(confirm("Are you sure you want to cancel this service"))
   				//{
					cancelAndCopyWithServiceString(str,checkedCounter,serviceLength);
					return false;
				//}	
			}
			
			var jsData =			
			{
				serviceId1:str,
				orderNumber:document.getElementById('poNumber').value
			};
			
			if(confirm("Are you sure you want to delete this service"))
   			{
				//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				result = jsonrpc.processes.DeleteServices(jsData);
			       
				if(result.msgOut != "" && result.msgOut != null)
				{
					alert(result.msgOut);
					
					//PAGING-SERVICE-LINE-14-10-2012
					if(iNoOfPages==pageNumber){
						if(result.totalRecord==iTotalLength){
							pageNumber=pageNumber-1;
						}
					}	
					countService=1;
					document.getElementById('browser').innerHTML= "";					
					document.getElementById("lblServiceDetail").innerHTML = "" ;					
					//drawTable();	
					DrawTable('SERVICENO','SERVICELINETABLE');			       		
				}
			}	
		}	
	}
	
	
	if(serviceTab.rows.length==1)
	{
		document.getElementById('selectAllID').checked=false;
		document.getElementById('selectAllID').disabled=true;
	}		
}
//end Ramana

function CancellationOfService(reason,isCancelAndCopy)
{	
//	var sessionid ='<%=request.getSession().getId() %>';
    serviceTab = document.getElementById('ServiceList');
    var myform=document.getElementById("searchForm");
    var newCounter=1;
    var stage = document.getElementById('stageName').value;
     var result;
    flag=5;

    //Start[030]
    if(isCancelAndCopy==1)
    {
    	cancelAndCopy(reason);
    	return false;
    }
    //End[030]
    if(document.forms[0].chkService)
    {
		var chkLength=document.forms[0].chkService.length;
		if(chkLength==undefined)
		{
			if(myform.chkService.checked==true)
		     {   
		         serviceNo=myform.chkService.value;
				//[052]
					var jsData =			
				    {
					  serviceId:serviceNo,
					  //sessionid:sessionid,
					  cancelServiceReason:reason,
					  orderNumber:document.getElementById('poNumber').value,
					  cancelReasonId:document.getElementById('cancelReasonDD').value
					};
					
				    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				  
				    if(serviceNo!="")
				    {
				       result = jsonrpc.processes.DeleteServiceDetails(jsData,sessionId);
				       //Meenakshi: commenting unreachable code
				      /* if(result==null)
				       {
				       		//start [023]					
							var myForm=document.getElementById('searchForm');				
							myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
							myForm.submit();
							alert("Session has been expired!!");		
							window.close();			
							//end [023]
				       }*/
				        // 035 start
				       //serviceTab.deleteRow(0); 
				       // 035 end
				       
				       if(result.msgOut != "" && result.msgOut != null)
				       {
				       		alert(result.msgOut);
				       }
				       else
				       {
				       		alert("Row(s) deleted successfully");
				       }
				    }
				  else
				    {
				       // 035 start
				       //serviceTab.deleteRow(0); 
				       // 035 end
				    }  
			}
		}
		else
		{
			for(i=0;i<chkLength;i++)
			{
				var myform=document.getElementById("searchForm")
				
				if(myform.chkService[i] != null && myform.chkService[i].checked == true)
				{
				    
				    serviceNo=myform.chkService[i].value;
					var result;
					//[052]
					var jsData =			
				    {
					  serviceId:serviceNo,
					 // sessionid:sessionid,
					  cancelServiceReason:reason,
					  orderNumber:document.getElementById('poNumber').value,
					  cancelReasonId:document.getElementById('cancelReasonDD').value
					};
				     //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				    
				  
				    if(serviceNo!="")
				    {	
				      result = jsonrpc.processes.DeleteServiceDetails(jsData,sessionId);
				      //Meenakshi: commenting unreachable code
				     /*  if(result==null)
				       {
				       		//start [023]					
							var myForm=document.getElementById('searchForm');				
							myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
							myForm.submit();
							alert("Session has been expired!!");		
							window.close();			
							//end [023]
				       } */
				       // 035 start
				       //serviceTab.deleteRow((i)); 
				       // 035 end
				       
				       if(result.msgOut != "" && result.msgOut != null)
				       {
				       		alert(result.msgOut);
				       }
				       else
				       {
				       		alert("Row(s) deleted successfully");
				       }
				    }
				    else
				    { 
				       // 035 start
				       //serviceTab.deleteRow((i)); 
				       // 035 end
				    }
				}
			}
		}
		//rohit
		countService=1;
		//PAGING-SERVICE-LINE-14-10-2012
		//drawTable();
		DrawTable('SERVICENO','SERVICELINETABLE');
	}
	else
	{
		alert("Please Select a Service!!");
		return false;
	}

}

function downLoadMaster() {
	var myForm=document.getElementById('searchForm');
	//myForm.productID.value = global_currTreeNode
	//alert(global_currTreeNode);
	myForm.action=gb_path+"/NewOrderAction.do?method=downloadMasters";
	showLayer();
	myForm.submit();


}
function copyServiceProduct() 
{
	//Added By Saurabh for Role Check
    //Start
    var stage = document.getElementById('stageName').value;
    if(stage=="SED" || stage=="Partial Publish" || stage=="Billing trigger")
    {
    	alert("You are not authorised to use this functionality");
    	return false;
    }
    //End
	
	if(document.forms[0].chkService==undefined)
	{
		alert("Please Add a Service!!");
		return false;
	}
	
	if(document.forms[0].txtCopyProduct.value == "")
	{
	    alert("Please Insert values for Product Copy!!");
		return false;
	}
	if(!checkdigits(document.forms[0].txtCopyProduct))
	{
	  return false;
	}
	if(document.forms[0].txtCopyProduct.value.length>2)
	{
		alert("No. of copies should be less than 100");
	    return false;
	}
	    
	if(document.forms[0].txtCopyProduct.value == 0)
	{
	    alert("No of copies should be greater than 0");
		return false;
	} 
	/*else if (document.forms[0].txtCopyProduct.value > 20)
	{
		alert("No of copies should be less than 20");
		return false;
	}*/
	var local_isServiceSelected = 0;
	var chkLength=document.forms[0].chkService.length;
	
	
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			//Started By Saurabh to restrict user from copying product for the services which are already published
			if(document.getElementById('chk'+0).value=="Yes") 
			{
		   		alert("You Cannot Copy LineItem of Already Published Service");
		   		return false;
			}
			if(document.getElementById('serviceStatus'+1).value=="CancelAndCopy" || document.getElementById('serviceStatus'+1).value=="Cancelled in CRM")
   			{
   				alert("You Cannot Copy LineItem of Already Cancelled Service");
   				return false;
			}
			//Started By Saurabh
			local_isServiceSelected = 1;
		}
	}
	else
	{
		for(i=0;i<chkLength;i++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chkService[i].checked==true)
			{
				//Started By Saurabh to restrict user from copying product for the services which are already published
				if(document.getElementById('chk'+i).value=="Yes") 
				{
		   			alert("You Cannot Copy LineItem of Already Published Service");
		   			return false;
				}
				if(document.getElementById('serviceStatus'+(i+1)).value=="CancelAndCopy" || document.getElementById('serviceStatus'+(i+1)).value=="Cancelled in CRM")
   				{
   					alert("You Cannot Copy LineItem of Already Cancelled Service");
   					return false;
				}
				//Started By Saurabh
				local_isServiceSelected = 1;
				break;
			}
		}
	}
	
	if(local_isServiceSelected==0)
	{
		alert("Please Select A Service and then product catelog");
		return false;
	}
	
	//test for selection of prdCatelog
	if(document.forms[0].rdo1==undefined)
	{
		alert("No Product Catelog Present!!");
		return false;
	}
	var local_isPrdCatelogSelected = 0;
	var local_PrdCatelogValue;
	var local_serviceDetailId1;
	chkLength=document.forms[0].rdo1.length;
	
	
	if(chkLength==undefined)
	{
		if(document.forms[0].rdo1.checked==true)
		{
			local_isPrdCatelogSelected = 1;
		}
	}
	else
	{
		for(i=0;i<chkLength;i++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.rdo1[i].checked==true)
			{
				local_isPrdCatelogSelected = 1;
				break;
			}
		}
	}
	
	if(local_isPrdCatelogSelected==0)
	{
		alert("Please Select Product catelog");
		return false;
	}
	/////////////////////
	//test whther product is of last level
	//var isLastLevel = 0;
	
	//var global_currTreeNode;
	//var global_currTreeLabel;
	//var global_currTreeUrl;

	//compute whther last level or not
	//isLastLevel = isLastLevelorNot();
	//if(isLastLevel == 0)
	//{
		//alert('Product not of last level.Only Last Level Products Can be Copied.');
		//return false;
	//}
	var ServiceId_dmxvalidation;
	var newCounter=1;
	var chkLength=document.forms[0].chkService.length;
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			ServiceId_dmxvalidation=document.forms[0].txtServiceNo1.value
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chkService[i].checked==true)
			{
			 ServiceId_dmxvalidation=document.getElementById('txtServiceNo'+newCounter).value;
				
			}
		}	
	}
	local_PrdCatelogValue=global_currTreeNode;
	local_serviceDetailId1=serviceDetailId1;
	
	if(local_serviceDetailId1 == 345)
	     {
			   //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			  var countPlayerInService = jsonrpc.processes.fnDmxCountPlayerInService(ServiceId_dmxvalidation);
			  if(countPlayerInService.list[0] >= 1 )
			  {
			   alert('You can not add more than one player!!');
			   return false;
			  }
		 }
		 if(local_serviceDetailId1 == 346)
	     {
			   //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			  var countPlayerInService = jsonrpc.processes.fnDmxCountPlayerInService(ServiceId_dmxvalidation);
			  if(countPlayerInService.list[0] == 0 )
			  {
			   alert('Please add DigitalSignage-HWPlayer first !!');
			   return false;
			  }
		 }
		 //Start[033]
		if(local_serviceDetailId1 ==521 || local_serviceDetailId1 ==526)
		{
			var jsData =			
    		{
    		serviceId:ServiceId_dmxvalidation
			};
			 //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			var MplsLineCount = jsonrpc.processes.getMplsLineCount(jsData);
			if(local_serviceDetailId1 ==521)
			{
				if(MplsLineCount.flexiCount >= 1)
				{
					alert("You Cannot have more than one FlexiConnect Line Item In Service No "+ ServiceId_dmxvalidation);
					return false;
				}
			}
			if(local_serviceDetailId1 ==526)
			{	
				if(MplsLineCount.siteBasedCount >= 1)
				{
					alert("You Cannot have more than one SiteBased Line Item In Service No "+ ServiceId_dmxvalidation);
					return false;
				}
			}	
		} 
		//End[033] 
	
	
	
	try{
	var jsData =			
	    {
		  serviceProductID:local_PrdCatelogValue,
		  copyProductValues:document.forms[0].txtCopyProduct.value
		};
    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
	result = jsonrpc.processes.copyServiceProduct(jsData);
	if(result !=null)
	{
		alert(result.message);
		
        //top.consoleRef=window.open('','myconsole','width=300,height=250')
        //top.consoleRef.document.writeln(result.message);
		 
		if(result.messageId=='ENTRY_COPIED')
		{
			//refresh tree 
			fncServiceTreeView();
		}
		//else
		//{
			//alert('Entry Coping Failed. Please Try Again.');
		//}
	}
	}
	catch(e)
	{
		alert(gb_ExceptionMessage);
		showJavascriptException(e,gb_ExceptionShow);
	}
}
// [013] Start 
function copyServiceCharge() 
{
	try{
	//Added By Saurabh for Role Check
    //Start
	var userRoleName =gb_roleID;
    var stage = document.getElementById('stageName').value;
    if(stage=="SED" || stage=="Partial Publish" || stage=="Billing trigger" || userRoleName==PM_ROLE)
    {
    	alert("You are not authorised to use this functionality");
    	return false;
    }
    //End
	
	if(document.forms[0].chkService==undefined)
	{
		alert("Please Add a Service!!");
		return false;
	}
	var modeValue=gb_ModeValue;

	if(document.forms[0].chkService==undefined)
	{
		alert("Please Add a Service!!");
		return false;
	}
	var newCounter=1;
	var chkLength=document.forms[0].chkService.length;
	chkTest = 0;
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			
		//Added By Saurabh to restrict user from copying charge for those services which are already published
		if(document.getElementById('chk'+0).value=="Yes") 
		{
			alert("Service already published \n No Charges can be copied");
			return false;
		}
		if(document.getElementById('serviceStatus'+1).value=="CancelAndCopy" || document.getElementById('serviceStatus'+1).value=="Cancelled in CRM")
   		{
			alert("Service already cancelled \n No Charges can be copied");
			return false;
		}
		//End By Saurabh
				
				document.getElementById('hdnCurrentTempCounter').value=newCounter;
				var path = gb_path+"/NewOrderAction.do?method=displayCopyCharge&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accNo').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&modeName="+ modeValue;
				window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:500px");
				chkTest = 1;
			
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chkService[i].checked==true)
			{
				
				//Added By Saurabh to restrict user from copying charge for those services which are already published
				if(document.getElementById('chk'+i).value=="Yes") 
				{
					alert("Service already published \n No Charges can be copied");
					return false;
				}
				if(document.getElementById('serviceStatus'+(i+1)).value=="CancelAndCopy" || document.getElementById('serviceStatus'+(i+1)).value=="Cancelled in CRM")
		   		{
					alert("Service already cancelled \n No Charges can be copied");
					return false;
				}
				//End By Saurabh
				
				if(document.getElementById('txtServiceNo'+newCounter).value=="")
				{
					break;
				}
				else
				{
					document.getElementById('hdnCurrentTempCounter').value=newCounter;
					var path = gb_path+"/NewOrderAction.do?method=displayCopyCharge&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accNo').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&modeName="+ modeValue;
					window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:500px");
					chkTest = 1;
					break;
				}
			}
			else
			{
				chkTest = 0;
				
			}
		}
	}
	
	if (chkTest == 0)
	{
		alert("Please Select a Service before Selecting Copy Charge!!");
		return false;
	}
	
	}catch(e)
	{
		alert('error code 77: '+ e.message);
	}

}	
// [013] Ends 
function fnCalculateEndDate(val){

	
	var period=document.getElementsByName('periodsInMonths')[val-1].value;
	var startDate=document.getElementsByName('contractStartDate')[val-1].value;
	
	if(startDate == null || startDate=="") {
	alert("Please enter start date");
	document.getElementsByName('contractStartDate')[val-1].focus();
	return;
	}
	if(period == null || period=="") {
	alert("Please enter period in months");
	document.getElementsByName('periodsInMonths')[val-1].focus();
	return;
	}
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
	document.getElementsByName('contractEndDate')[val-1].value=new String(endDate);
	
return;

}
function DownLoadTemplateProductCatelog()
{
	if(document.forms[0].chk_spId==null)
	{
		alert("No Product Present for Download.");
		return false;
	}
	
	var chkLength=document.forms[0].chk_spId.length;
	if(chkLength==undefined)
	{
		if(document.forms[0].chk_spId.checked==true)
		{
			document.hdnSelectedServiceDetailId.value=document.forms[0].hdnserviceDetailId[1];
		}
		else
		{
			alert("No Product Selected.");
			return false;
		}
	}
	else
	{
		var ischecked=0;
		var prevTypeId='';
		for(i=0;i<chkLength;i++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chk_spId[i].checked==true)
			{
				ischecked=1;
				myform.hdnSelectedServiceDetailId.value=myform.hdnserviceDetailId[i+1].value;
				if(prevTypeId=='')
				{
					prevTypeId=myform.hdnserviceDetailId[i+1].value;
				}
				else if(prevTypeId!=myform.hdnserviceDetailId[i+1].value)
				{
					alert('You Can not select Different types products!!');
					return false;
				}
				
			}
		}
		if(ischecked==0)
		{
			alert("No Product Selected.");
			return false;
		}
	}
    var myform=document.getElementById("searchForm")
    myform.action=gb_path+"/NewOrderAction.do?method=downloadTemplateExcelForPrdCatelog";
    showLayer();
    myform.submit();
}

function uploadFile(){

	
		var myForm=document.getElementById('searchForm');
		
		//validation
		
		if(document.getElementById("id_attachment").value==null || document.getElementById("id_attachment").value=="")
		{
			alert("Please Select Template file");
			return false;  
		}else
		{
				var data=document.getElementById("id_attachment").value;
				data = data.replace(/^\s|\s$/g, "");
			   if (data.match(/([^\/\\]+)\.((xls))$/i) )
			   {
				  //do nothing 
			   }
			   else
			   {
					alert("Selected Attachment is of Wrong Type");
				    return false ;				
			   }
		}
		
		myForm.action=gb_path+"/NewOrderAction.do?method=uploadPlanExcel";
		showLayer();
		myForm.submit();
}

function uploadFile_Page(){
	var path1 = gb_path+"/NewOrderAction.do?method=gotoUploadPage";
   	window.showModalDialog(path1,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	//var myForm=document.getElementById('searchForm');
	//myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=gotoUploadPage";
	//myForm.submit();
}
function fnGetQuoteNo(){
	//Puneet - checking if source is CRMORder, ,then simply return
	var sName=document.getElementById('sourceName').value;
	var accountID=document.getElementById('accountID').value;
	if(sName == "CRMORDER" || "" == accountID)
		return;
	var test;
    var combo = document.getElementById("quoteNo");	
    combo.innerHTML = "";
    /*var iCountRows = combo.length;  
   	for (i = 1; i <  iCountRows; i++){
       combo.remove(1);
   	}*/
   	var jsData ={
		accountID:Number(accountID)
	};
	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    test = jsonrpc.processes.populateQuoteNoList(jsData);  
    for(j=0;j<test.list.length;j++){
    	var option = document.createElement("option");
		option.text = test.list[j].quoteNo;
		option.value = test.list[j].quoteNo;
		option.title = test.list[j].quoteNo;
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
	}
	if(test.list.length<=0){
		var option = document.createElement("option");
		option.text = "Select";
		option.value = "0";
		option.title = "Select";
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
	}	
	/*var x=document.getElementById("quoteNo");   
	x.selectedIndex = 0;*/
}

function showNoOfDays(){
	if(document.getElementById('chkIsDemo').checked==true)
		document.getElementById('dvNoOfDays').style.visibility='visible';
	else
		document.getElementById('dvNoOfDays').style.visibility='hidden'	
}

//FUNCTION IS USED FOR SHOW POPUP WINDOW FOR TASK_ACTION IN EDIT MODE
function popTaskAction(id,ownerId,isaccountManager,isPmPresent) 
{
	var orderNo = document.getElementById('poNumber').value;
	try{
		var frm=document.getElementById('searchForm');
		frm.searchTaskId.value = id;
		frm.searchTaskOwnerId.value = ownerId;
		if(isaccountManager==1)
		{
			if(isPmPresent==1){
				document.getElementById('hdnprojectmgrid').value = document.getElementById('projectManagerAssigned').value;
				var path = gb_path+"/viewOrderAction.do?methodName=TaskAction&orderNo="+orderNo+"&role="+gb_roleID;
			}else{
				//vijay. comment following code because of now dynamic workflow has been implemented for CLEP order
				/*var order_creation_source = document.getElementById('hdnOrderCreationSourceId').value;	
				if(order_creation_source==ORDER_CREATION_SOURCE){
					document.getElementById('hdnprojectmgrid').value = document.getElementById('projectManagerAssigned').value;	
				}else{	*/		 
					document.getElementById('hdnprojectmgrid').value = 0;
				// }
				var path = gb_path+"/viewOrderAction.do?methodName=TaskAction&orderNo="+orderNo+"&role="+gb_roleID;
			}		
		}
		else
		{
			document.getElementById('hdnprojectmgrid').value = 0;
			var path = gb_path+"/viewOrderAction.do?methodName=TaskAction&orderNo="+orderNo+"&role="+gb_roleID;
		}
		//[00055] Start (Changed By Ashutosh)		
	   	window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px");
	 	//[00055] End
	}catch(e)
	{
		alert('error code 84: '+ e.message);
	}
}
//FUNCTION IS USED FOR SHOW POPUP FOR TASK_NOTES IN EDIT MODE
function showNotes(taskId,status)
{

   //var sessionId='<%=request.getSession().getId() %>';	
   mytable = document.getElementById('tabNotes');
  
   var iCountRows = mytable.rows.length;
  
   for (i = 1; i <  iCountRows; i++)
   {
       mytable.deleteRow(1);
   }   
	var jsData =			
    {
		taskID:taskId
	};
	

    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    
    var retVal = jsonrpc.processes.ViewNotes(jsData,sessionId);
    
    
    for (i = 0 ; i < retVal.list.length; i++)
    {
		var str;
		var tblRow=document.getElementById('tabNotes').insertRow();
				
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.title=retVal.list[i].notesType;
		str=retVal.list[i].notesType
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.title=retVal.list[i].notesMeaning;
		str=retVal.list[i].notesMeaning
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.title=retVal.list[i].createdBy;
		str=retVal.list[i].createdBy
		CellContents = str;
		tblcol.innerHTML = CellContents;

		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.title=retVal.list[i].createdDate;
		str=retVal.list[i].createdDate
		CellContents = str;
		tblcol.innerHTML = CellContents;

	
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="<div class='deleteBg1'><a href='#' onclick=deleteNote("+ retVal.list[i].taskNoteId +","+ retVal.list[i].taskID +",'"+ escape(status) +"')>...</a></div>";
						
		var ownerId =gb_roleID;

		if(status=="Open" && ownerId ==retVal.list[i].ownerId)
			CellContents = str;
		else
			CellContents = "";	
		tblcol.innerHTML = CellContents;

	}	
	
	if(retVal.list.length==0)
	{
		var tblRow=document.getElementById('tabNotes').insertRow();
				
		var tblcol=tblRow.insertCell();
		tblcol.align = "Center";
		tblcol.vAlign="top";
		tblcol.colSpan ="5";
		tblcol.title ="No Notes Found";
		CellContents = "No Notes Found";
		tblcol.innerHTML = CellContents;
	}
}
function popTaskStatus(id,ownername) 
{
	
	var frm=document.getElementById('searchForm');
	frm.searchTaskId.value = id;
	frm.searchTaskOwner.value = ownername;
	
	var path = gb_path+"/viewOrderAction.do?methodName=fetchNotes&taskId="+id;
   	window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:350px");
 	//window.open(path);
}
function fnChargeSummary()
{
	poNum=document.searchForm.poNumber.value;
	var d=new Date();
	var url = gb_path+"/viewOrderAction.do?methodName=fnChargeSummary"+"&"+d.getTime();
	{
		//alert(document.searchForm.poNumber.value);
		url = url + "&PONum="+document.searchForm.poNumber.value;
		//features="scrollbars=yes,resizable=yes,width=800,height=540,left=100,top=100";
		//window.open(url,'xyz',features);
		window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}
//----[022]---Start
function demoValidation(obj)
{
	var myForm=document.getElementById('searchForm');
	if(myForm.noOfDaysForDemo.value == 0)
	//if(document.forms[0].noOfDaysForDemo.value == 0)
	{
	    alert("No of days should be greater than 0");	    	
	    myForm.noOfDaysForDemo.value = "";
		myForm.noOfDaysForDemo.focus();	
	} 
	else 
	{
		checkdigits(obj);
	}
}
//----[022]---End
//----[011]---Start
function cancelOrder()
{
	//var answer = confirm("Are you sure you want to cancel this order ?")
	//if(answer)
	//{
			//[050]

			var path = gb_path+"/NewOrderAction.do?method=orderCancelReason";
			window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:300px");
			var cancelreason=document.getElementById('cancelReason').value;
			var poNumber=document.getElementById('poNumber').value;
			var cancelreasonDD=document.getElementById('cancelReasonDD').value;
	    
	    	
		//Start Validation of Dummy Lsi b4 Cancel Order	
	 	var results;
	 	 var answer=document.getElementById('answer').value;

	 	
	 	if(answer){
		var jsData =			
	    {
		  serviceIdString:"",
		  orderNumber:document.getElementById('poNumber').value,
		  remarks:"Cancel Order"
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");	
		results = jsonrpc.processes.validateVCSServicesBeforeCancel(jsData);
		if(results!="" && results!=null)
		{
			alert(results);
			return false;
		}
		//End Validation of Dummy Lsi b4 Cancel Order
		//var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");			
		
		/*Vijay
		 *check order is belong to clep or not. If this order is clep order 
		 *then clep related cancel method is going to call because in this msg a 
		 * a milestone will send to clep.
		*/
		var order_creation_source = document.getElementById('hdnOrderCreationSourceId').value;	
		var status='';
		//[052]
			var jsDataOrder =			
		    {
			  
				orderNumber:document.getElementById('poNumber').value,
			  cancelReason:document.getElementById('cancelReason').value,
			  cancelReasonId:document.getElementById('cancelReasonDD').value
			};
		
		if(order_creation_source==ORDER_CREATION_SOURCE){
			var commit_flag='N';
			status = jsonrpc.processes.cancelOrderForClep(jsDataOrder,sessionId,commit_flag);	
		}
		else{
			var commit_flag='N';
			status = jsonrpc.processes.cancelOrder(jsDataOrder,sessionId,commit_flag);	
		}
			//Vijay end		
		var myform=document.getElementById("searchForm")
		
		if(status == '1')
		{
			 alert('Order Cancelled Successfully');
			 //Meenakshi : commented entire form submit. created a dummy form and submitted it.
			 /*myform.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompleteOrder";
			 showLayer();
			 myform.submit(); */
			var viewIncompleteOrderForm=createDummyForm(gb_path+"/defaultDraftNewOrdAction.do");
			attachHiddenField(viewIncompleteOrderForm,"method","viewIncompleteOrder");
			showLayer();
			viewIncompleteOrderForm.submit();
		}
	}
}
//----[011]---End
 /*function fnBillingTriggerEnable()
 {
    poNumber=document.getElementById('poNumber').value;
	var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");			
	var status = jsonrpc.processes.checkM6Status(poNumber);	
	if(status == '1')
	{
	document.getElementById("billingTriggerID").disabled=false;
	alert('Status Updated!!');
	}
	else
	{
	 document.getElementById("billingTriggerID").disabled=true;
	 alert('No Status ForUpdate!!');
	}
			
 }*/

//add by anil for display message successfully published

//add by anil for fetch Order no from TM6_NEWORDER_STATUS for check publish status
function checkPublishStatus()
{
	
	OrderNoBilling=document.getElementById('poNumber').value;
	var stage=document.getElementById("stageName").value;
	
	var orderStatus = document.getElementById("orderStatusValue");
	//var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");			
	OrderNoTM6 = jsonrpc.processes.checkPublishStatus(OrderNoBilling);
	var userId = gb_userID;
	var serviceCount = jsonrpc.processes.checkPublishServiceStatus(OrderNoBilling,userId);
	//--[041]--//
	//cancel button should be not be display when order entry page open first time. 
	//So, here adding an other condition that prevent to display cancel button.
	//if(serviceCount==OrderNoBilling)
	
	/*if(OrderNoBilling !=null && OrderNoBilling != "" && OrderNoBilling != 0 && (serviceCount==OrderNoBilling))
	{
		//document.getElementById('saveIcon').style.display="none";	
		document.getElementById('cancelIcon').style.display="block";	
	}*/
	
	/*By Vijay: Some above code is commented regarding 'Cancel' button displaying functionality.
	  following 'if block' added to enable cancel button while order's stage is 'New'
	  or 'AM' and login role by '1'(AM role). 
	  In other words, order can be cancel only if order is in AM's bin.
	*/  
	 
	if(OrderNoBilling !=null && OrderNoBilling != "" && OrderNoBilling != 0)
	{
		if((stage=="AM" || stage=="New") && (roleName=="1"))
		{
			document.getElementById('cancelIcon').style.display="block";
		}
	}
	
	if(document.getElementById('Page_5')!=null)
	{
		if(document.getElementById('Page_5').style.display=="block")
		{
			document.getElementById('saveIcon').style.display="none";
		
		}
	}
	
	if(stage=="PM" ||stage=="COPC" ||stage=="SED"|| stage=="Billing Trigger" || stage=="Partial Initiated")
	{
			
		document.getElementById('btnAddMoreService').style.display="none";	
	}
	else
	{
		
		if(document.getElementById('btnAddMoreService') != null)
		{
			document.getElementById('btnAddMoreService').style.display="block";	
		}
	}
	//add [005]
	document.getElementById('hdnShowAttachIcon').value=gb_AttachIconAttribute;	
	if(document.getElementById('hdnShowAttachIcon').value=="NO")
	{
		document.getElementById('attachIcon').style.display="none";
		//lawkush Changed for disabling GAM icon when  main tab is not saved
		document.getElementById('addGam').style.display="block";
		document.getElementById('addGam').src="gifs/top/6.1.gif";
		document.getElementById('addGam').alt="";
		document.getElementById('addGam').title="";
		//lawkush Changed for disabling GAM icon when  main tab is not saved
		
	}
	else
	{
		document.getElementById('attachIcon').style.display="block";
		document.getElementById('addGam').style.display="block";
	}
	//end [005]
}

//Added By Saurabh for checking whether all services are published or not
function checkAllPublished(){
	var serviceLength = document.forms[0].chkService.length;
	var chk=0;
	for(var i=0;i<serviceLength;i++){
		if(document.getElementById('chk'+i).value=="No"){
			alert("Selected Service of the Order Published Successfully");				
			return false;
		}else{
			chk++;			
		}
	}
	if(chk==serviceLength)
	{
		alert("Complete Order Published Successfully");	
		//goToHome();
		//openInViewOrder();//done to open the order after publishing as told by Rohit 20-feb-12
		return false;
	}

	if(serviceLength==undefined)
	{
		
		if(document.getElementById('chk'+0).value=="Yes")
		{
			alert("Complete Order Published Successfully");	
			openInViewOrder();
			return false;
		}
	}
}
//End By Saurabh

//[014] Start
function populatePODemoContractPeriod(count)
{
 	var noOfDays = document.getElementById('noOfDaysForDemo').value;
 	if (noOfDays!="")
 	{
 		document.getElementById('poDemoContractPeriod'+count).value=document.getElementById('noOfDaysForDemo').value;
 	}
}
//[014] End

// -------[017] Start
function CopyOrder() 
{
	try{
			//To Check whether BCP Address is present agsinst the account by Vishwa on 10-Sep-13 START
			var accountID=document.getElementById('accountID').value;
			var userRoleName =gb_roleID;
			if(userRoleName==PM_ROLE)
				{
				alert("You are not authorised to use this functionality");
				return false;
				}
			var test1 = jsonrpc.processes.countBCPWithDispatch(accountID);
			if (test1==0)
					{	
						alert("Please create Billing and Dispatch address in CRM and Sync in iB2B");
					}
			//To Check whether BCP Address is present agsinst the account by Vishwa on 10-Sep-13 END
				else
					{	
						//Started By Saurabh for Role check of SED
					    
						var stage = document.getElementById("stageName").value;
						if(userRoleName==4  ||  stage=="SED" || stage=="Billing Trigger" || stage=="Partial Publish")
						{
							alert("You are not authorised to use this functionality");
							return false;
						}
						//End By Saurabh
						var path = gb_path+"/NewOrderAction.do?method=getOrderToBeCopied";
						window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:600px");
					}
	}catch(e)
	{
		alert('error code 93: '+ e.message);
	}
}
// -------[017] End

var isCtrl = false; 
document.onkeyup=function(){ 
			if(event.keyCode == 17) isCtrl=false; 
		}

/*var roleName = gb_roleID;
var userId = gb_userID;
var employeeId = gb_empID;*/

document.onkeydown=function(e){
	var stage;
	var orderType;
	var accessMode;
	var createdBy;
	var modeValue="editON";
	
	//[037]Start
	//To disable backspace key doing Back on browser 
	if(KeyCheck()==false) return false; 
	//[037] End
	
	if(event.keyCode == 17) isCtrl=true; 
	if(event.keyCode == 122 && isCtrl == true) 
	{
		isCtrl=false;
		
		if(document.getElementById('readOnlyPO')!=null)
		{
			var orderNo=document.getElementById('readOnlyPO').value;
			
			if( trim(document.getElementById('readOnlyPO').value).length>0)
			{
				if(checkdigits(document.getElementById('readOnlyPO'))==false)
				{
					return false;
				}	
			}
						
			if(document.getElementById('readOnlyPO').value== "" || document.getElementById('readOnlyPO').value == null)
			{
				orderNo=0;
			}
		}
		else
		{
			var orderNo=document.getElementById('poNumber').value;
			
			if( trim(document.getElementById('poNumber').value).length>0)
			{
				if(checkdigits(document.getElementById('poNumber'))==false)
				{
					return false;
				}	
			}
						
			if(document.getElementById('poNumber').value== "" || document.getElementById('poNumber').value == null)
			{
				orderNo=0;
			}
		}	
		//var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");			
		
		var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
		var shortCode = jsonrpc.processes.getShortCode(roleName);
		
		if(orderDetails.list.length==0)
		{
			alert('Order Does Not Exist \n Please Try Valid Order No');
			return false;
		}
		else if(orderDetails.list.length>0)
		{
			stage=orderDetails.list[0].stageName;
			orderType=orderDetails.list[0].orderType;
			orderNo=orderDetails.list[0].orderNumber;
			accessMode=orderDetails.list[0].mode;
			createdBy=orderDetails.list[0].createdBy;
			//lawkush Start
			 var userIDLogged=orderDetails.list[0].userID;
			 var  roleNameLogged=orderDetails.list[0].roleName;			 
		     var userNameLogged=orderDetails.list[0].userName;
			//lawkush End
		}
		//lawkush Start
		if(userIDLogged!=null && userIDLogged!='' )
		      {
		      		if(userIDLogged.toUpperCase()!=userId.toUpperCase())
		      		{
		      
				      		if(!confirm("User "+userNameLogged+" [User ID "+userIDLogged+ "]  has already Logged in,"+'\n'+" Do you continue viewing in View Order"))
				      		{
				      			return false;
				      		}
		      		}
		      		
		      }
		//lawkush End
		
		if(orderType=="New" && ( stage=="New" || stage=="NEW"))
		{
			var viewIncompleteNewOrder=createDummyForm(gb_path+"/NewOrderAction.do");
			if(shortCode=="AM")
			{
				if(createdBy==employeeId)
				{
					
					/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
					document.forms[0].submit(); */
					attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
					attachHiddenField(viewIncompleteNewOrder,"draft","1");
					attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);										
//					attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder&draft=1&orderNo="+orderNo);
					viewIncompleteNewOrder.submit();
				}
				else
				{
				
					var modeValue="viewMode";
					/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					document.forms[0].submit(); */
					attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
					attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
					attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
					viewIncompleteNewOrder.submit();
				}
			}
			else
			{
			
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
      			document.forms[0].submit();*/
      			attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
				attachHiddenField(viewIncompleteNewOrder,"draft","1");
				attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
				viewIncompleteNewOrder.submit();
      		}	
		}
		else if(orderType=="New" && (stage=="AM" ||stage=="PM" ||stage=="COPC" ||stage=="SED" ||stage=="Partial Publish" ||stage=="Published" ||stage=="Completed" ||stage=="M6_CANCELLED"  || stage == "CANCELLED"))
		{
			var viewIncompleteNewOrder=createDummyForm(gb_path+"/NewOrderAction.do");
			if(shortCode==stage)
			{
				//Added By Saurabh To Check AM or PM Roled Id
				if(stage=="AM")
				{
					if(createdBy==employeeId)
					{
					
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit(); */
						attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
						attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
						attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
						viewIncompleteNewOrder.submit();
					}
					else
					{
					
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
						attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
						attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
						viewIncompleteNewOrder.submit();
					}
				}
				else if(stage=="PM")
				{
					if(accessMode=="Write")
					{
					
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
						attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
						attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
						viewIncompleteNewOrder.submit();
					}
					else if(accessMode=="Read")
					{
						
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
						attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
						attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
						viewIncompleteNewOrder.submit();
					}
				}
				else
				{
				
					/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					document.forms[0].submit();*/
					attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
					attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
					attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
					viewIncompleteNewOrder.submit();
				}	
				//End By Saurabh
			}
			else
			{
			
				var modeValue="viewMode";
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
				attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
				attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
				viewIncompleteNewOrder.submit();
			}
		}
		else if(orderType=="New" && stage=="Billing Trigger")
		{
			var viewIncompleteNewOrder=createDummyForm(gb_path+"/NewOrderAction.do");
			if(shortCode="COPC")
			{
				
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
	      		document.forms[0].submit(); */
	      		attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
				attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
				attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
				viewIncompleteNewOrder.submit();
			}
			else
			{
				
				var modeValue="viewMode";
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
				attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
				attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
				viewIncompleteNewOrder.submit();
	      	}
		}
		else if(orderType=="Change" && ( stage=="New" || stage=="NEW"))
		{
			var viewIncompleteChangeOrder=createDummyForm(gb_path+"/ChangeOrderAction.do");
			if(shortCode=="AM")
			{
				if(createdBy==employeeId)
				{
					
					/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
					document.forms[0].submit();*/
					attachHiddenField(viewIncompleteChangeOrder,"method","incompleteOrder");
					attachHiddenField(viewIncompleteChangeOrder,"draft","1");
					attachHiddenField(viewIncompleteChangeOrder,"orderNo",orderNo);
					viewIncompleteChangeOrder.submit();
				}
				else
				{
				
					var modeValue="viewMode";
					/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					document.forms[0].submit();*/
					attachHiddenField(viewIncompleteChangeOrder,"method","incompleteOrder");
					attachHiddenField(viewIncompleteChangeOrder,"modeName",modeValue);
					attachHiddenField(viewIncompleteChangeOrder,"orderNo",orderNo);
					viewIncompleteChangeOrder.submit();
				}
			}
			else
			{
				/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
      			document.forms[0].submit();*/
      			attachHiddenField(viewIncompleteChangeOrder,"method","incompleteOrder");
				attachHiddenField(viewIncompleteChangeOrder,"draft","1");
				attachHiddenField(viewIncompleteChangeOrder,"orderNo",orderNo);
				viewIncompleteChangeOrder.submit();
      		}	
		}
		else if(orderType=="Change" && (stage=="AM" ||stage=="PM" ||stage=="COPC" ||stage=="SED" ||stage=="Partial Publish" ||stage=="Published" ||stage=="Completed" ||stage=="M6_CANCELLED"  || stage == "CANCELLED"))
		{
			var viewIncompleteChangeOrder=createDummyForm(gb_path+"/ChangeOrderAction.do");
			if(shortCode==stage)
			{
				//Added By Saurabh To Check AM or PM Roled Id
				if(stage=="AM")
				{
					if(createdBy==employeeId)
					{
						/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit(); */
						attachHiddenField(viewIncompleteChangeOrder,"method","incompleteOrder");
						attachHiddenField(viewIncompleteChangeOrder,"modeName",modeValue);
						attachHiddenField(viewIncompleteChangeOrder,"orderNo",orderNo);
						viewIncompleteChangeOrder.submit();
					}
					else
					{
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit(); */
						var viewIncompleteNewOrder=createDummyForm(gb_path+"/NewOrderAction.do");
						attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
						attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
						attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
						viewIncompleteNewOrder.submit();
					}
				}
				else if(stage=="PM")
				{
					if(accessMode=="Write")
					{
						/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(viewIncompleteChangeOrder,"method","incompleteOrder");
						attachHiddenField(viewIncompleteChangeOrder,"modeName",modeValue);
						attachHiddenField(viewIncompleteChangeOrder,"orderNo",orderNo);
						viewIncompleteChangeOrder.submit();
					}
					else if(accessMode=="Read")
					{
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						var viewIncompleteNewOrder=createDummyForm(gb_path+"/NewOrderAction.do");
						attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
						attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
						attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
						viewIncompleteNewOrder.submit();
					}
				}
				else
				{
					/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					document.forms[0].submit();*/
					var viewIncompleteChangeOrder=createDummyForm(gb_path+"/ChangeOrderAction.do");
					attachHiddenField(viewIncompleteChangeOrder,"method","incompleteOrder");
					attachHiddenField(viewIncompleteChangeOrder,"modeName",modeValue);
					attachHiddenField(viewIncompleteChangeOrder,"orderNo",orderNo);
					viewIncompleteChangeOrder.submit();
				}
				//End By Saurabh
			}
			else
			{
				var modeValue="viewMode";
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				var viewIncompleteNewOrder=createDummyForm(gb_path+"/NewOrderAction.do");
				attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
				attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
				attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
				viewIncompleteNewOrder.submit();
			}
			
		}
		else if(orderType=="Change" && stage=="Billing Trigger")
		{
			
			if(shortCode=="COPC")
			{
				/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				var viewIncompleteChangeOrder=createDummyForm(gb_path+"/ChangeOrderAction.do");
				attachHiddenField(viewIncompleteChangeOrder,"method","incompleteOrder");
				attachHiddenField(viewIncompleteChangeOrder,"modeName",modeValue);
				attachHiddenField(viewIncompleteChangeOrder,"orderNo",orderNo);
				viewIncompleteChangeOrder.submit();
			}
			else
			{
				var modeValue="viewMode";
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				var viewIncompleteNewOrder=createDummyForm(gb_path+"/NewOrderAction.do");
				attachHiddenField(viewIncompleteNewOrder,"method","incompleteOrder");
				attachHiddenField(viewIncompleteNewOrder,"modeName",modeValue);
				attachHiddenField(viewIncompleteNewOrder,"orderNo",orderNo);
				viewIncompleteNewOrder.submit();
			}
		}	
		isCtrl=false;
		return false; 
	} 
	else if(event.keyCode == 122 && isCtrl == false)
	{
	  	if(document.getElementById('readOnlyPO')!=null)
	  	{
		  	if(document.getElementById('readOnlyPO').value== "" || document.getElementById('readOnlyPO').value == null )
			{
				document.getElementById("readOnlyPO").readOnly=false;
				document.getElementById("readOnlyPO").value="";
				document.getElementById("readOnlyPO").styleClass="inputBorder1";
				document.getElementById("readOnlyPO").focus();
				event.keyCode = 0;
				event.returnValue = false;
				return false;
			}
		  	
		  	var orderNo=document.getElementById('readOnlyPO').value;
		}
		else
		{
			if(document.getElementById('poNumber').value== "" || document.getElementById('poNumber').value == null )
			{
				document.getElementById('poNumber').readOnly=false;
				document.getElementById("poNumber").value="";
				document.getElementById("poNumber").styleClass="inputBorder1";
				document.getElementById("poNumber").focus();
				event.keyCode = 0;
				event.returnValue = false;
				return false;
			}
		  	
		  	var orderNo=document.getElementById('poNumber').value;
		}
		//var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");			
		var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	  	event.keyCode = 0;
		event.returnValue = false;
	  	if(orderDetails.list.length>0)
		{
			if(orderNo == orderDetails.list[0].orderNumber)
			{
				/*document.forms[0].action="./defaultNewOrderAction.do?method=goToNewOrder";
				document.forms[0].submit(); */
				var goToNewOrderForm=createDummyForm(gb_path+"/defaultNewOrderAction.do");
				attachHiddenField(goToNewOrderForm,"method","goToNewOrder");
				goToNewOrderForm.submit();
			}
		}	
		else
		{
			document.getElementById("readOnlyPO").readOnly=false;
			document.getElementById("readOnlyPO").value="";
			document.getElementById("readOnlyPO").styleClass="inputBorder1";
			document.getElementById("readOnlyPO").focus();
			event.keyCode = 0;
			event.returnValue = false;
		}	
	}
	else
	if (event.ctrlKey && event.keyCode == 83) {		
				//stop ctrl S for CLEP orders
				var order_creation_source = document.getElementById('hdnOrderCreationSourceId').value;					
				if(order_creation_source==ORDER_CREATION_SOURCE)
	  			{
	  				return false;	
	  			}else{
					event.keyCode=4; 
					validate();
					document.getElementById("saveIcon").tabIndex = -1;
					if(document.getElementById("saveIcon") != null)
					{
						if(document.getElementById('saveIcon').style.display=="block")
							document.getElementById("saveIcon").focus();
					}
				}						   				   						
   }   
	
 }

 function checkpage(){
 //Puneet - changed the tab opening logic
    var flag3=gb_FlagParam;
    var stage = document.getElementById("stageName").value;
    if(flag3==2){
	  /*document.getElementById('hdnTabVal').value=2;
	  document.getElementById('Page_1').style.display="none";
	  document.getElementById('Page_2').style.display="block";
	  //--[042]--start--//
	  selectCurrentTab('li2');
	  //--[042]--end--//*/
	  changeTab("li2",2,'none','block','none', 'none','none');
	}else if(flag3==3){
		/*enableDisablePOCheckbox();
		document.getElementById('hdnTabVal').value=3;
		document.getElementById('Page_1').style.display="none";
		document.getElementById('Page_2').style.display="none";
		document.getElementById('Page_3').style.display="block";
		//--[042]--start--//
	  	selectCurrentTab('li3');
	  //--[042]--end--//*/
	  changeTab("li3",3,'none','none', 'block', 'none','none');
	}else if(flag3==5){
		/*document.getElementById('hdnTabVal').value=5;
		document.getElementById('Page_1').style.display="none";
		document.getElementById('Page_2').style.display="none";
		document.getElementById('Page_3').style.display="none";
		document.getElementById('Page_5').style.display="block";
	  	//--[042]--start--//
		selectCurrentTab('li5');
		//--[042]--end--//*/
		changeTab("li5",5,'none','none', 'none','block','none');
	}
    //	[045]	Start
       	
    if(document.getElementById('poNumber').value >0){    	
    	document.getElementById('attachOpportunity').style.display="block";    	
    	if(stage == 'New' || stage =='Valid'){
  			document.getElementById('viewOpp').style.display="none";
  			document.getElementById('selOpp').style.display="block";
  		}else{
  			document.getElementById('viewOpp').style.display="block";
  			document.getElementById('selOpp').style.display="none";
  		}
  	}
 	//	[045]	End
}

 function fnSendToCOPC(){
		try{
		if(checkPartialInitiatedOrPartialPublish()){
			var stage = document.getElementById("stageName").value;
			var loggedUserRoleName = gb_roleID;
			if(loggedUserRoleName == 4 || loggedUserRoleName == 2){
				//[054] Start VIPIN
				if(loggedUserRoleName==4)
					if(checkServicePublishReadyCount()>0){
						alert("This functionality is not available as some of services are ready to publish");
						return false;
					}
				//[054] End VIPIN
			}else{
				alert("You are not allowed to use this functionality");
				return false;
			}
			var path =gb_path+"/NewOrderAction.do?method=goToCOPCPageFromSEDRole";
		   	window.showModalDialog(path,window,"status:false;dialogWidth:550px;dialogHeight:200px");
		}
		else{
			alert("You are not allowed to use SendToCOPC at this stage.");
			return false;
		}
		}catch(e){
			alert('error code 96: '+ e.message);
		}
	}

//end[027]
//start hideSelectCurrency() add by anil for hide select currency after successful validate
function hideSelectCurrency()
{
	var modeValue=gb_ModeValue;
	
	if(modeValue=="editON")
	{
		document.getElementById('divSelectCurrencyId').style.display='none';
	}
	else
	{		
		document.getElementById('divSelectCurrencyId').style.display='block';		
	}
}
//end hideSelectCurrency()

//Start[030]
function cancelAndCopy(reason)
{
	//var sessionid ='<%=request.getSession().getId() %>';	
    serviceTab = document.getElementById('ServiceList');
    var myform=document.getElementById("searchForm");
    var newCounter=1;
    flag=5;
  	
    if(document.forms[0].chkService)
    {
		var chkLength=document.forms[0].chkService.length;
		if(chkLength==undefined)
		{
			if(myform.chkService.checked==true)
		     {   
		         serviceNo=myform.chkService.value;
			       //[052]
				 var result;
					var jsData =			
				    { roleId:gb_roleID,	
					  serviceId:serviceNo,
					  cancelServiceReason:reason,					  
						  orderNumber:document.getElementById('poNumber').value,

						  cancelReasonId:document.getElementById('cancelReasonDD').value
					};
				    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				    if(serviceNo!="")
				    {
				       result = jsonrpc.processes.cancelAndCopy(jsData);
				       //Meenakshi : commenting unreachable code
				       /*if(result==null)
				       {
				       		//start [023]					
							var myForm=document.getElementById('searchForm');				
							myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
							myForm.submit();
							alert("Session has been expired!!");		
							window.close();			
							//end [023]
				       }*/
				       serviceTab.deleteRow(1); 
				       
				       /*if(result.msgOut != "" && result.msgOut != null)
				       {
				       		alert(result.msgOut);
				       }*/
				       if(result.orderNumber != 0)
				       {
				       		if(confirm("Order Created Successfully with ORDERNO="+result.orderNumber+" For Cancelled Service !!Do you want to view?"))
							{
							//Meenakshi : commented entire form submit. Made a dummy form and called action
								/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+result.orderNumber;
		      					document.forms[0].submit();*/
		      				var goToIncompleteOrderForm=createDummyForm(gb_path+"/NewOrderAction.do");
							attachHiddenField(goToIncompleteOrderForm,"method","incompleteOrder");
							attachHiddenField(goToIncompleteOrderForm,"draft","1");
							attachHiddenField(goToIncompleteOrderForm,"orderNo",result.orderNumber);
							goToIncompleteOrderForm.submit();
		      					
		      				}
				       }
				    }
				  else
				    {
				      serviceTab.deleteRow(1); 
				    }  
			}
		}
		else
		{
			for(i=0;i<chkLength;i++)
			{
				var myform=document.getElementById("searchForm")
				
				if(myform.chkService[i] != null && myform.chkService[i].checked == true)
				{
				    
				    serviceNo=myform.chkService[i].value;
					var result;
					var jsData =			
				    {roleId:gb_roleID,	
					  serviceId:serviceNo,
					  cancelServiceReason:reason,					  
						  orderNumber:document.getElementById('poNumber').value,
						  published_by_empid:Number(gb_empID)
					};
				    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				    
				  
				    if(serviceNo!="")
				    {	
				      result = jsonrpc.processes.cancelAndCopy(jsData);
				     //Meenakshi : commenting unreachable code
				       /*if(result==null)
				       {
				       		//start [023]					
							var myForm=document.getElementById('searchForm');				
							myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
							myForm.submit();
							alert("Session has been expired!!");		
							window.close();			
							//end [023]
				       } */
				       serviceTab.deleteRow((i+1)); 
				       
				       if(result.orderNumber != 0)
				       {
				       		if(confirm("Order Created Successfully with ORDERNO="+result.orderNumber+" For Cancelled Service !!Do you want to view?"))
							{
								//Meenakshi : commented entire form submit. Made a dummy form and called action
								/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+result.orderNumber;
		      					document.forms[0].submit(); */
		      				var goToIncompleteOrderForm=createDummyForm(gb_path+"/NewOrderAction.do");
							attachHiddenField(goToIncompleteOrderForm,"method","incompleteOrder");
							attachHiddenField(goToIncompleteOrderForm,"draft","1");
							attachHiddenField(goToIncompleteOrderForm,"orderNo",result.orderNumber);
							goToIncompleteOrderForm.submit();
		      					
		      					
		      				}
				       }
				    }
				    else
				    { 
				      serviceTab.deleteRow((i+1)); 
				    }
				}
			}
		}
		//ViewServiceTree(flag);
	}
	else
	{
		alert("Please Select a Service!!");
		return false;
	}
}
//End[030]
//Start[030]
function fnViewCancelAndCopyReport(serviceId,isNew)
{
	var poNumber=document.getElementById('poNumber').value;
	//var myForm=document.getElementById('searchForm');				
	//myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=DisplayCancalCopyReport&poNumber="+poNumber+"&serviceId="+serviceId;
	//myForm.submit();
	var path = gb_path+"/reportsAction.do?method=DisplayCancalCopyReport&poNumber="+poNumber+"&serviceId="+serviceId+"&isNew="+isNew;
	window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:900px");
}
//End[030]
//Start[030]
function cancelAndCopyWithServiceString(serviceIdString,checkedCounter,serviceLength)
{
	loggedUserRole=gb_roleID;
	if(loggedUserRole!=4)
	{
		alert("You Are Not Authorised To use This Functionality");
		return false;
	}
		//Start VCS and L3 MPLS Validation		
	 	var vcsResults;
		var jsData =			
	    {
		  serviceIdString:serviceIdString,
		  orderNumber:document.getElementById('poNumber').value,
		  remarks:"Cancel and Copy"
		  
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");	
		vcsResults = jsonrpc.processes.validateVCSServicesBeforeCancel(jsData);
		if(vcsResults!="" && vcsResults!=null)
		{
			alert(vcsResults);
			return false;
		}
	//End VCS Validation
	
	var path = gb_path+"/NewOrderAction.do?method=serviceCancelReason&isCancelAndCopy="+2+"&serviceIdString="+serviceIdString;
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:300px");
		//[052]
		var btn_click=document.getElementById('buttonClicked').value;
		if(btn_click==1){
			if(confirm("Are you sure you want to cancel this service"))
			{
				cancelAndCopyService(reasonForCancelService,serviceIdString,checkedCounter,serviceLength);
			}
			else{
				return false;
			}
		}
}
//End[030]
//Start[030]
function cancelAndCopyService(reason,serviceIdString,checkedCounter,serviceLength)
{
	//[052]
	
	if(reason == null)
	reason="";
	try
	{
		var jsData =			
	    { roleId:gb_roleID,	
		  serviceIdString:serviceIdString,
		  cancelServiceReason:reason,		  
		  orderNumber:document.getElementById('poNumber').value,
		  published_by_empid:Number(gb_empID),
		  cancelReasonId:document.getElementById('cancelReasonDD').value
		};
	    //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
		result = jsonrpc.processes.cancelAndCopy(jsData);
		if(result.orderNumber != 0)
		{
			if(confirm("Order Created Successfully with ORDERNO="+result.orderNumber+" For Cancelled Service !!Do you want to view?"))
			{
				var orderNo=result.orderNumber;  
				//Meenakshi : commented entire form submit. Made a dummy form and called action			
      			/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
		      	document.forms[0].submit();*/
		      	var goToIncompleteOrder=createDummyForm(gb_path +"/NewOrderAction.do");
				attachHiddenField(goToIncompleteOrder,"method","incompleteOrder");
				attachHiddenField(goToIncompleteOrder,"draft","1");
				attachHiddenField(goToIncompleteOrder,"orderNo",orderNo);
				goToIncompleteOrder.submit();
			}
			else
			{
				if(checkedCounter==serviceLength)
				{
					alert('Since You Have Selected All The Services for Cancellation  So The Order Is Also Cancelled \n HEnce You Are Being Redirected To Home Page');
					goToHome();
					return false;
				}
				else if(result.is_partial_initiated==1){
					alert('Since You Have Selected All Partial Initiated Services for Cancellation, So The Order send to previuos user \n Hence You Are Being Redirected To Home Page');
					goToHome();
					return false;
				}
				else
				{
					countService=1;
					document.getElementById('browser').innerHTML= "";					
					document.getElementById("lblServiceDetail").innerHTML = "" ;
					DrawTable('SERVICENO','SERVICELINETABLE');
				}
			}	
		}
		else if(result.msgOut!="" || result.msgOut!=null)
		{
			alert(result.msgOut);
			return false;
		}
		else
		{
			alert('Some Error Occured. Please Try After Some Time');
			return false;
		}
	}
	catch(e)
	{
		alert(e);
	}
}
function legalEntityLoading()
{
	//alert(document.getElementById('hdnTabVal').value);
	//alert(document.getElementById('Page_3').style.display);
	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
		  		
		  		var jsData =			 
		   		{
		    		poNumber:document.forms[0].poNumber.value
		  		};
		  		result1 = jsonrpc.processes.getPoExistency(jsData);
		  	
			    if(result1>0)
				{
					return false;
				}
	if(document.getElementById('hdnTabVal').value==5)
	{
		return false;
	}
	//if(document.getElementById('hdnTabVal').value==3 || (document.getElementById('Page_3').style.display == 'none' && document.getElementById('Page_2').style.display == 'block') || (document.getElementById('Page_3').style.display == 'none' && document.getElementById('Page_5').style.display == 'block'))
	if(document.getElementById('hdnTabVal').value==3 || document.getElementById('Page_3').style.display == 'none')
	{		
		//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");	
		var lstEntity = jsonrpc.processes.getEntity1();
		var tr, td, i, j, oneRecord;  		   			
	    for(j=0;j<lstEntity.list.length;j++)
    	{      
   	  	 if(document.getElementsByName('entityId')[0] == '[object]')
   	  	  {
    		var combo = document.getElementsByName('entityId')[0];	    		
	    	var opt = document.createElement("option");
	   		opt.text = lstEntity.list[j].entityName;
			opt.value = lstEntity.list[j].entityId;	
			opt.title = lstEntity.list[j].entityName;		  		
			try 
			{				
				combo.add(opt, null); //Standard				
			}
			catch(error) 
			{								
				combo.add(opt); // IE only
				
			}			
    	 }
    	}
	}
}

function  populateAccountWith()
	{
			document.getElementById("channelPartnerName").value="";
			document.getElementById("channelPartnerId").value="";
			document.getElementById("channelpartnerCode").value="";
			document.getElementById("fieldEngineer").value="";
			document.getElementById("fieldEngineerId").value="";
	
	
	
			if(document.getElementById('crmAccNo').value.length > 0)
			{						
				if(accountValidation(document.getElementById('crmAccNo'),'Account No')==false)
				{
					return false;
				}
			}
						
			var sortByColumn='CRMACCOUNTNO';
			var sortByOrder='ASC';
			var string1 = document.getElementById('crmAccNo').value;
		 	var flag = string1.search('%');
		 	if(flag != -1)	
		 	{
			 	var jsData =
				{
					startIndex:startRecordId,
					endIndex:endRecordId,
					sortBycolumn:sortByColumn,
					sortByOrder:sortByOrder,
					accountIDString:document.getElementById('crmAccNo').value
				};
				
				//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				var lstAccount = jsonrpc.processes.getAccountDetailsWithSorting(jsData);
					if(lstAccount.list.length==1)
						{
						document.searchForm.accountID.value = lstAccount.list[0].accountID;
						document.searchForm.crmAccountNo.value = lstAccount.list[0].crmAccountId;
						document.searchForm.accountName.value = lstAccount.list[0].accountName;
						//document.searchForm.accphoneNo.value = lstAccount.list[0].accphoneNo;
						document.searchForm.lob.value = lstAccount.list[0].lob;
						//[056] Start
						document.searchForm.serviceSegment.value = lstAccount.list[0].serviceSegment;
						//[056] End
						document.searchForm.accountManager.value =lstAccount.list[0].accountManager;
						document.searchForm.AttributeVal_2.value = lstAccount.list[0].acmgrPhno;
						document.searchForm.AttributeVal_3.value = lstAccount.list[0].acmgrEmail;
						document.searchForm.region.value = lstAccount.list[0].region;
						document.searchForm.regionId.value=lstAccount.list[0].regionIdNew;
						document.searchForm.shortCode.value=lstAccount.list[0].m6ShortCode;
						document.searchForm.collectionMgr.value=lstAccount.list[0].collectionMgr;
						document.searchForm.osp.value = lstAccount.list[0].osp;
						document.searchForm.circle.value = lstAccount.list[0].circle;//added on 9-jan-2013, circle work
						document.searchForm.category.value = lstAccount.list[0].category;//[044]
						document.searchForm.projectManager.value = lstAccount.list[0].projectManager;
						document.searchForm.projectManagerID.value = lstAccount.list[0].projectManagerID;
						document.searchForm.groupName.value = lstAccount.list[0].groupName;
						
						//fillProjectManagerCombo(lstAccount.list[0].accountID);
						fillZoneCombo(lstAccount.list[0].regionIdNew);
					//	fillChannelPartnerMaster()
						//document.searchForm.crmAccountNo.className="inputDisabled";
						//document.searchForm.crmAccountNo.readOnly='readOnly';
						//document.getElementById('divSelectAccount').style.display="none";
						fnGetQuoteNo();
						fnGetOpportunityId();
						return false;
						
						}
						else
				 		{
				 		popitup('SelectAccount');
				 		//fillChannelPartnerMaster()
				 		}				
		 	}
		 	else
		 	{
		 		if(string1=="")
		 		{
		 			document.searchForm.crmAccountNo.value = "";
					document.searchForm.accountID.value = "";
					document.searchForm.accountName.value = "";
					//document.searchForm.accphoneNo.value = "";
					document.searchForm.lob.value = "";
                    //[056] Start
					document.searchForm.serviceSegment.value = "";
                    //[056] End
					document.searchForm.accountManager.value = "";
					document.searchForm.projectManager.value = "";
					document.searchForm.AttributeVal_2.value = "";
					document.searchForm.AttributeVal_3.value = "";
					document.searchForm.region.value = "";
					document.searchForm.zone.value = "";
					document.searchForm.shortCode.value="";
					document.searchForm.collectionMgr.value="";
					document.searchForm.osp.value = "";
					document.searchForm.circle.value = "";//added on 9-jan-2013, circle work
					document.searchForm.category.value = "";// [044]
					document.searchForm.groupName.value = "";
					var comboZone = document.searchForm.zone;
				    var iCountRows = comboZone.length;
				    for (i = 1; i <  iCountRows; i++)
				    {
				    	comboZone.remove(1);
				    }
					return false;
		 		}
		 		else
		 		{
				 	var jsData =
					{								
						accountIDString:document.getElementById('crmAccNo').value
					};				  
					//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
					var lstAccount = jsonrpc.processes.getAccountDetails(jsData);
					if(lstAccount !=null){
					if(lstAccount.list.length == 0)
					{
						//if accountno not found in ib2b then it will serach at crm and if found then it downloaded to ib2b db
						var crmAccountNo=document.getElementById('crmAccNo').value;
						fetchAccountBCPDispatchCRM(crmAccountNo);																	 		
						return false;
					}
					if(lstAccount.list.length==1)
					{
						document.searchForm.accountID.value = lstAccount.list[0].accountID;
						document.searchForm.crmAccountNo.value = lstAccount.list[0].crmAccountId;
						document.searchForm.accountName.value = lstAccount.list[0].accountName;
						//document.searchForm.accphoneNo.value = lstAccount.list[0].accphoneNo;
                        //[056] Start
						document.searchForm.serviceSegment.value = lstAccount.list[0].serviceSegment;
                        //[056] End
						document.searchForm.lob.value = lstAccount.list[0].lob;
						document.searchForm.accountManager.value =lstAccount.list[0].accountManager;
						document.searchForm.AttributeVal_2.value = lstAccount.list[0].acmgrPhno;
						document.searchForm.AttributeVal_3.value = lstAccount.list[0].acmgrEmail;
						document.searchForm.region.value = lstAccount.list[0].region;
						document.searchForm.regionId.value=lstAccount.list[0].regionIdNew;
						document.searchForm.shortCode.value=lstAccount.list[0].m6ShortCode;
						document.searchForm.collectionMgr.value=lstAccount.list[0].collectionMgr;
						document.searchForm.osp.value = lstAccount.list[0].osp;
						document.searchForm.circle.value = lstAccount.list[0].circle;//added on 9-jan-2013, circle work
							document.searchForm.category.value = lstAccount.list[0].category;//[044]
						document.searchForm.projectManager.value = lstAccount.list[0].projectManager;
						document.searchForm.projectManagerID.value = lstAccount.list[0].projectManagerID;
							document.searchForm.groupName.value = lstAccount.list[0].groupName;

						fillZoneCombo(lstAccount.list[0].regionIdNew);
						
					//	fillChannelPartnerMaster()
							//fnGetQuoteNo();
							//fnGetOpportunityId();								
						}
					
					}else{
						alert("Error[-1212]:Some error has occurred!!");
						return false;
					}
				}
		 	}		 				 
	}
function fillZoneCombo(regionId)
{
	     var tr, td, i, j, oneRecord;
	     var zoneList;
	     var combo = document.searchForm.zone;
	     var iCountRows = combo.length;
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
		//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
	    zoneList = jsonrpc.processes.populateZoneList(regionId);
	    for(j=0;j<zoneList.list.length;j++)
	    {
		   	var option = document.createElement("option");
		  	option.text = zoneList.list[j].zoneName;
			option.value = zoneList.list[j].zoneId;
			option.title = zoneList.list[j].zoneName;
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
function fillProjectManagerCombo(accID)
{
	     var tr, td, i, j, oneRecord;
	     var projectManagerNameList;
		//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
		var accId=unescape(accID);
		projectManagerNameList = jsonrpc.processes.getProjectManagerNameList(accId);
	   document.searchForm.projectManagerID.value=projectManagerNameList.list[0].projectManagerID;
	   document.searchForm.projectManager.value=projectManagerNameList.list[0].projectManager;
}
function fnOpenNewOrder()
{
	//Meenakshi : commented entire form submit. Made a dummy form and called action
	/*var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultNewOrderAction.do?method=goToNewOrder";
	showLayer();
	myForm.submit();*/
		var goToNewOrderForm =createDummyForm(gb_path +"/defaultNewOrderAction.do");
		attachHiddenField(goToNewOrderForm,"method","goToNewOrder");
		showLayer();
		goToNewOrderForm.submit();
}
//End[030]
var hook=true;
window.onbeforeunload = function() 
{
    if (hook)
    {
    	//Start[036]
    	var orderNo=document.getElementById('poNumber').value;
		 //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
	    var isClosed = jsonrpc.processes.closeOrderAlreadyOpened(orderNo,userId,roleName);
	    //End[036]
	     
	    if ((window.event.clientX < 0) || (window.event.clientY < 0) || (window.event.clientX < -80))
	    {
	    {
	      return "This will cause losing any unsaved data ! "
	    } 
	    }
	} 
}
function unhook() 
{
     hook=false;
}

//Start[031]
function redirectAmForApproval()
{
	var stage;
	var orderType;
	var accessMode;
	var createdBy;
	var modeValue="editON";
	
	var orderNo=document.getElementById('poNumber').value;
		
	//var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");			
	
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	var shortCode = jsonrpc.processes.getShortCode(roleName);
	
	if(orderDetails.list.length==0)
	{
		alert('Order Does Not Exist \n Please Try Valid Order No');
		return false;
	}
	else if(orderDetails.list.length>0)
	{
		stage=orderDetails.list[0].stageName;
		orderType=orderDetails.list[0].orderType;
		orderNo=orderDetails.list[0].orderNumber;
		accessMode=orderDetails.list[0].mode;
		createdBy=orderDetails.list[0].createdBy;
	}
	
	if(orderType=="New" && stage=="AM")
	{
		if(shortCode==stage)
		{
			if(createdBy==employeeId)
			{
				
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				
				var initialForm = document.forms[0];
				var myDummyForm=createDummyForm(gb_path +"/NewOrderAction.do");
				attachHiddenField(myDummyForm,"method","incompleteOrder");
				attachHiddenField(myDummyForm,"orderNo",orderNo);
				attachHiddenField(myDummyForm,"modeName",modeValue);
				if(initialForm.m6Status!=null)
				{
					attachHiddenField(myDummyForm,"m6Status",initialForm.m6Status.value);				
				}
				//alert(initialForm.m6Status);
				
				showLayer();
				myDummyForm.submit();
			}
		}	
	}
}
//End[031]


/*function fillChannelPartnerMaster()
{
	try{
		// alert('fillChannelPartnerMaster'+document.getElementById('channelPartnerCtgry1').value)
		var orderNo=document.getElementById('poNumber').value;
	     var tr, td, i, j, oneRecord;
	     var channelPartnerMasterList;
	     // var combo = document.searchForm.channelPartnerCtgry;
	     var combo =document.getElementById("channelPartnerCtgry");	
	     
	     var iCountRows = combo.length;
	   //  alert("iCountRows"+iCountRows)
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	   // alert('orderNoorderNoorderNo  :'+orderNo)
	    
	    channelPartnerMasterListvar list = jsonrpc.processes.populateChannelPartnerList(orderNo);
	    channelPartnerMasterList=list.list[0]
	    var channelPartnerId;
	    if(orderNo==undefined || orderNo==0){	    	
	    	 orderNo=''
	    	 channelPartnerId=0;
	    }else{
	    	 channelPartnerId=list.list[1].list[0].PARTNER_ID;
	    }
	    	
	    //var channelPartnerId=channelPartnerMasterListOther.PARTNER_ID;
	    if(document.getElementById('channelPartnerCtgry1').value=='' && channelPartnerId==0)
	    {
	    	//alert("no val  hid val"+document.getElementById('channelPartnerCtgry1'))
			    for(j=0;j<channelPartnerMasterList.list.length;j++)
			    {
				   	var option = document.createElement("option");
				  	option.text = channelPartnerMasterList.list[j].PARTNER_NAME;
					option.value = channelPartnerMasterList.list[j].PARTNER_ID;
					option.title = channelPartnerMasterList.list[j].PARTNER_NAME;
					try 
					{
						combo.add(option, null); //Standard
						//alert('adding' + option.text)
					}
					catch(error) 
					{
						//alert('adding' + option.text)
						combo.add(option); // IE only
					}
			    }			    
	    }	    
			    
	    else
	    {
	    	//alert("with val --  hid val"+document.getElementById('channelPartnerCtgry1'))
	        var selectedCategory = channelPartnerId;
	    	//alert("selectedCategory"+selectedCategory)
		    for(j=0;j<channelPartnerMasterList.list.length;j++)
		    {
		    	var option = document.createElement("option");
		   		option.text = channelPartnerMasterList.list[j].PARTNER_NAME;
				option.value = channelPartnerMasterList.list[j].PARTNER_ID;
				option.title = channelPartnerMasterList.list[j].PARTNER_NAME;
				try 
				{
				combo.add(option, null); //Standard
		
				}
				catch(error) 
				{
					combo.add(option); // IE only
				}
		    }
		    combo.value = selectedCategory;
		    document.getElementById("channelMasterTagging").value=1;
		    }       
	    
		}catch(e)
	{
		alert('error code 701: method name fillChannelPartnerMaster of neworder.js '+ e.message);
	}
}*/



//Start[032]
function fnGetOpportunityId(){
	var test;
	var combo = document.getElementById("opportunityId");	
	var accountID=document.getElementById('accountID').value;
	if("" ==  accountID)
		return;
	var sourceName=document.getElementById('sourceName').value;
	var quoteNo=document.getElementById('quoteNo').value;
	/*Puneet - commenting 
	if(sourceName=='CRMQUOTE'){
		if(quoteNo!=0){
			//accountID=0;	
		}
	}else{
		quoteNo=0;
	}*/	
	if(sourceName!='CRMQUOTE'){
		quoteNo=0;
	}
	/*var iCountRows = combo.length;  
	for (i = 1; i <  iCountRows; i++){
		combo.remove(1);
	}*/
	combo.innerHTML="";
	var jsData = {
		accountID:Number(accountID),
		quoteNo:quoteNo
	};
	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    test = jsonrpc.processes.fetchOpportunityIdList(jsData);
    for(j=0;j<test.list.length;j++){
		var option = document.createElement("option");
		option.text = test.list[j].opportunityId;
		option.value = test.list[j].opportunityId;
		option.title = test.list[j].opportunityId;
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
	}
	//var x=document.getElementById("opportunityId"); 
	if(test.list.length<=0){
		var option = document.createElement("option");
		option.text = "Select";
		option.value = "0";
		option.title = "Select";
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
		/*Puneet - commenting
		if(test.list.length>0){
			combo1.selectedIndex = 1;
		}else
			combo1.selectedIndex = 0;*/
	}/*else{
		combo1.selectedIndex = 0;
	}*/ 
	
}
//End [032]

function openInViewOrder()
{	
	
	var orderNo=document.getElementById('poNumber').value;
	 //jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
    var isClosed = jsonrpc.processes.closeOrderAlreadyOpened(orderNo,userId,roleName);
	//End[036]
	
	/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+document.getElementById('poNumber').value+"&modeName=viewMode";
	document.forms[0].submit();
	*/
	var initialForm = document.forms[0];
	var myDummyForm=createDummyForm(gb_path +"/NewOrderAction.do");
	attachHiddenField(myDummyForm,"method","incompleteOrder");
	attachHiddenField(myDummyForm,"orderNo",orderNo);
	attachHiddenField(myDummyForm,"modeName","viewMode");
	if(initialForm.m6Status!=null)
	{
		attachHiddenField(myDummyForm,"m6Status",initialForm.m6Status.value);				
	}
	//alert(initialForm.m6Status);
	showLayer();
	myDummyForm.submit();
}
function openOrderAfterApproval(){
	try{
		var orderNo=document.getElementById('poNumber').value;
		var initialForm = document.forms[0];
		var myDummyForm=createDummyForm(gb_path +"/NewOrderAction.do");
		attachHiddenField(myDummyForm,"method","incompleteOrder");
		attachHiddenField(myDummyForm,"orderNo",orderNo);
		attachHiddenField(myDummyForm,"modeName","editON");
		if(initialForm.m6Status!=null){
			attachHiddenField(myDummyForm,"m6Status",initialForm.m6Status.value);				
		}
		showLayer();
		myDummyForm.submit();
	}catch(e){
		alert('error code 111: '+ e.message);
	}
}
// 034 start
function checkstage()
{
	
 var stage = document.getElementById('stageName').value;
  if(stage=='CANCELLED')
						 {
						 		alert('Since You Have Selected All The Services for Cancellation  So The Order Is Also Cancelled \n HEnce You Are Being Redirected To Home Page');
								goToHome();
								return false;
						 
						 }

}

//034 end

function validateMBIC_To_CC()
{
	isValidaMBIC_MAP_WITH_CC=true;
	var orderNo=document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	var ccLSIMapWithMBIC=jsonrpc.processes.validateMBIC_To_CC(orderNo);	
	if(ccLSIMapWithMBIC!='')  
	{
		alert(""+ccLSIMapWithMBIC);
		isValidaMBIC_MAP_WITH_CC=false;
		return false;
	}
}
function validateVCS_BridgeServiceBundled()
{
	isVCS_MAP_WITH_L3MPLS=true;
	var orderNo=document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	var VCS_MAP_WITH_L3MPLS=jsonrpc.processes.validateVCS_BridgeServiceBundled(orderNo);	
	if(VCS_MAP_WITH_L3MPLS!='')  
	{
		alert(""+VCS_MAP_WITH_L3MPLS);
		isVCS_MAP_WITH_L3MPLS=false;
		return false;
	}
}
//START POC POINTS As On Date 29-09-2012
function getComponentLineItem()
{
	isComponentAddedInLineItem=true;
	var orderNo=document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	var Results=jsonrpc.processes.isComponentAdded(orderNo);	
	if(Results!='')  
	{
		alert(""+Results);
		isComponentAddedInLineItem=false;
		return false;
	}
}

function getMultipleDummyServices()
{
	isMultipleDummyServicesInOrder=true;
	var orderNo=document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	var Results=jsonrpc.processes.isMultipleDummyServices(orderNo);	
	if(Results!='')  
	{
		if(confirm(""+Results+" \n Do You Want to Continue.."))
			isMultipleDummyServicesInOrder=true;
		else
			isMultipleDummyServicesInOrder=false;
		//return false;
	}
}
//END POC POINTS As On Date 29-09-2012
function gamNFormulaAttachedWithOrder(){
	 var orderNo=document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC"); 
	isGamNFormulaAttachedFlag=jsonrpc.processes.isGamNFormulaAttachedWithOrder(orderNo);	
		
		// Added condtion for Order and Formula validation by Lawkush
		
			if(isGamNFormulaAttachedFlag==0)  
			{
			alert("Please attach GAM Formula with This Order");
			return false;
			}
			if(isGamNFormulaAttachedFlag==-1)  
			{
				alert("Please attach GAM with This Order");
				return false;
			}
			
	// Added condtion for Order and Formula validation by Lawkush


}
//below method changed from document.getElementById to both byId and by Form to handle single row if previous rows are deleted
function autoFillSalutation(counter)
{	
 	var myForm=document.getElementById('searchForm');
	var contactTable = document.getElementById('tableContact');
    var controlSalutationName;
    var controlSalutationId;
	if(myForm.salutationName.length==undefined)
    {
		controlSalutationName=myForm.salutationName;
		controlSalutationId=myForm.salutationId;
	}
	else
	{
		controlSalutationName=document.getElementById('salutationName'+counter);
		controlSalutationId=document.getElementById('salutationId'+counter);
	}
    
   	/*if(ValidateTextField(controlSalutationName,path,'Salutation Name')==false)
	{
		return false;
	}*/
 
 	//var string1 = document.getElementById('salutationName'+counter).value;
	var string1 = controlSalutationName.value;
	
 	var flag = string1.search('%');
 	if(flag != -1)	
 	{
 		var jsData =
			{								
				salutationName:document.getElementById('salutationName'+counter).value
			};				  
		//jsonrpc1 = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
		var lstService = jsonrpc.processes.searchSalutationName(jsData);
		if(lstService.list.length == 1)
 		{
	 		controlSalutationName.value=lstService.list[0].salutationName;
	 		controlSalutationId.value=lstService.list[0].salutationId;
	 		return true;
 		}
 		else if(lstService.list.length==0)
		{
			alert('No record found');
		    controlSalutationName.value = "";
			controlSalutationId.value = "";
		    return false;
	   	}	
		else
		{
			popSalutationList(counter);
		}
 	}
 	else
 	{
 		var jsData =
		{								
			salutationName:controlSalutationName.value
		};				  
		var lstService = jsonrpc.processes.populateSalutation(jsData);
		if(lstService.list.length == 0)
		{
			alert('No record found');
			controlSalutationName.value = "";
			controlSalutationId.value = "";
			return false;
		}
		if(lstService.list.length == 1)
		{
			controlSalutationName.value=lstService.list[0].salutationName;
	 		controlSalutationId.value=lstService.list[0].salutationId;
	 		return true;
		}
	}
}
//below method changed from document.getElementById to both byId and by Form to handle single row if previous rows are deleted
function autoFillContactType(counter)
 {
 
	var myForm=document.getElementById('searchForm');
	var contactTable = document.getElementById('tableContact');
	var controlContactType;
	var controlContactTypeId;
    if(myForm.salutationName.length==undefined)
    {
    	controlContactType=myForm.contactType;
    	controlContactTypeId=myForm.contactTypeId;
    }
 	else
 	{
 		controlContactType=document.getElementById('contactType'+counter);
 		controlContactTypeId=document.getElementById('contactTypeId'+counter);
	}
 
 	/*if(ValidateTextField(controlContactType,path,'Contact Name')==false)
	{
		return false;
	}*/
 	var string1 = controlContactType.value;
	
 	var flag = string1.search('%');
 	if(flag != -1)	
 	{
 		var jsData =
		{								
			contactTypeName:controlContactType.value
		};				  
		var lstService = jsonrpc.processes.searchContactType(jsData);
		if(lstService.list.length == 1)
 		{
 			controlContactType.value=lstService.list[0].contactTypeName;
 			controlContactTypeId.value=lstService.list[0].contactTypeId;
 			return true;
 		}
 		else if(lstService.list.length == 0)
 		{
 			alert('No record found');
 			controlContactType.value = "";
			controlContactTypeId.value = "";
 			return false;
 		}
 		else
 		{
 			popContactType(counter);
 		}
 	}
 	else
 	{
 		var jsData =
		{								
			contactTypeName:controlContactType.value
		};				  
		var lstService = jsonrpc.processes.getContactTypeDetail(jsData);
	 	if(lstService.list.length == 0)
	 	{
	 		alert('No record found');
	 		controlContactType.value = "";
			controlContactTypeId.value = "";
 			return false;
	 	}
	 	if(lstService.list.length == 1)
	 	{
	 		controlContactType.value=lstService.list[0].contactTypeName;
 			controlContactTypeId.value=lstService.list[0].contactTypeId;
 			return true;
	 	}
	}
}

function  autoFillCurrency()
	{
		
				if( trim(document.getElementById('curShortCode').value).length>0)
					{
						if(ValidateTextField(document.getElementById('curShortCode'),path,'Currency Code')==false)
						{
							return false;
						}
					}
				
	
			var sortByColumn='curShortCode';
			var sortByOrder='ASC';
			var string1 = document.getElementById('curShortCode').value;
		 	var flag = string1.search('%');
		 	if(flag != -1)	
		 	{
			 	var jsData =
				{
					startIndex:startRecordId,
					endIndex:endRecordId,
					sortBycolumn:sortByColumn,
					sortByOrder:sortByOrder,
					// currencyName:document.getElementById('txtCurrencyName').value,
					currencyCode:document.getElementById('curShortCode').value
				};
				
				//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				var lstCurrency = jsonrpc.processes.currencyType(jsData);
					if(lstCurrency.list.length==1)
						{
						document.getElementById('currencyID').value = lstCurrency.list[0].currencyID;
						document.getElementById('curShortCode').value = lstCurrency.list[0].currencyCode;
						return true;
						}
						
					else if(lstCurrency.list.length==0)
						{
								alert('No record found');
								document.getElementById('curShortCode').value = "";
								document.getElementById('currencyID').value = "";
								return false;
						
						}	
						
						
						else
				 		{
				 		popitup('SelectCurrency');
				 		return true;
				 		
				 		}
				
		 	}
		 	else
		 	{
		 	
				 	var jsData =
					{								
						currencyCode:document.getElementById('curShortCode').value
					};				  
						//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
						var lstCurrency = jsonrpc.processes.getCurrency(jsData);
						 if(lstCurrency.list.length == 0)
						 {
						 		alert('No record found');
								document.getElementById('curShortCode').value = "";
								document.getElementById('currencyID').value = "";
								return false;
						 }
						 if(lstCurrency.list.length==1)
								{
									document.getElementById('currencyID').value = lstCurrency.list[0].currencyID;
									document.getElementById('curShortCode').value  = lstCurrency.list[0].currencyCode;
									return true;
								}
		 	}
		 	

			 
	}
	
	
//start :CLEP Enable Disable Order Entry
function enableDisableCLEP(){
	var orderNo=document.getElementById('poNumber').value;	
	var stateid=getOrderStateforClep(orderNo,path);		
	clepState=stateid;	
	orderEntryEnableDisable('NEWORDER',stateid, null);
}
//end clep
//Added by anil to prevent any action after success aproval from aproval window
function showOpqChangeLayer()
{
	showLayer();	
}

//lawkush start
 
 function selectAll()
{
	if(document.getElementById('selectAllID').checked)
	{
		CheckAll(true);
	}else{
		CheckAll(false);
	}
}

function CheckAll(chkbox)
{
	var i=0;
	while(eval(document.getElementById('chkDeleteService'+i)))
	{
			if(document.getElementById('chkDeleteService'+i).disabled==false)
			{
		eval(document.getElementById('chkDeleteService'+i)).checked=chkbox;
			}	
		i=i+1;
	}
}
 
function allProductCheckBoxNone()
{
	document.getElementById('SelectAllChck').checked=false;
}
function allServiceCheckNone()
{
	document.getElementById('selectAllID').checked=false;
}
function allContactCheckNone()
{
	document.getElementById('SelectAllChckContact').checked=false;
}
function allPOCheckNone()
{
	document.getElementById('SelectAllPOChck').checked=false;
}
 


 //lawkush end
 
 function enableDisablePOCheckbox()
{
	var myForm=document.getElementById('searchForm');
	var poLength=document.forms[0].chkSelectPODetail.length;
	var poNumber = document.getElementById('poNumber').value;
//	var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");	
	var poList = jsonrpc.processes.getPOListForOrder(poNumber);
	var orderPOList=poList.list.length;
	var counter=0;
	if(poLength==undefined)
	{
		for (i =0; i<orderPOList; i++)
		{
			if(document.forms[0].poDetailNo.value==poList.list[i].podetailID)
			{
				document.forms[0].chkSelectPODetail.disabled=true;
			}
			else
				document.forms[0].chkSelectPODetail.disabled=false;
		}
	}		
	else
	{
		for (j =0; j<poLength; j++)
		{counter=0;
			for (i =0; i<orderPOList; i++)
			{
				if(counter==0)
				{
					if(myForm.poDetailNo[j].value==poList.list[i].podetailID)
					{
						myForm.chkSelectPODetail[j].disabled=true;
						counter++;
					}
				}	
			}
			if(counter==0)
			{
				myForm.chkSelectPODetail[j].disabled=false;
			}		
		}
	}		
}
//------------- Fetch CRM account on selection of account -----------------
function fetchAccountBCPDispatchCRM(crmAccountNo)
{	
		$.ajax({
			    url: gb_path +"/NewOrderAction.do?method=fetchCRMAccountBCPDispatch&accountno="+crmAccountNo,
	                    dataType: "text",
	                    type: "Get",
	                    contentType: "application/json; charset=utf-8",
	                    dataFilter: function(data) { return data; },
	                    success: handleGetAccount, 
						beforeSend: function(){
							$("#imgLoadingId").show();	
							$("#crmAccNo").attr('disabled', 'disabled');
							$("#divSelectAccount").hide();	
							},
						complete: function(){
							$("#imgLoadingId").hide();	
							$("#crmAccNo").attr('disabled', '');	
							$("#divSelectAccount").show();
							} ,
						async:true,
	                    error: function(XMLHttpRequest, textStatus, errorThrown) {
	                        alert(errorThrown);
	                    }	                    
	                });	                		                
}
function handleGetAccount(data){
	
	var crmActNo=document.getElementById('crmAccNo').value;
	if(data == "1")	
	{
            var jsData =
			{								
				accountIDString:crmActNo
			};				  
			//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
			var listAccountInfo = jsonrpc.processes.getAccountDetails(jsData);
			
		      document.searchForm.accountID.value = listAccountInfo.list[0].accountID;
			  document.searchForm.crmAccountNo.value = listAccountInfo.list[0].crmAccountId;
			  document.searchForm.accountName.value = listAccountInfo.list[0].accountName;
			  //document.searchForm.accphoneNo.value = listAccountInfo.list[0].accphoneNo;
              //[056] Start
			  document.searchForm.serviceSegment.value = listAccountInfo.list[0].serviceSegment;
              //[056] End
			  document.searchForm.lob.value = listAccountInfo.list[0].lob;
			  document.searchForm.accountManager.value =listAccountInfo.list[0].accountManager;
			  document.searchForm.AttributeVal_2.value = listAccountInfo.list[0].acmgrPhno;
			  document.searchForm.AttributeVal_3.value = listAccountInfo.list[0].acmgrEmail;
			  document.searchForm.region.value = listAccountInfo.list[0].region;
			  document.searchForm.regionId.value=listAccountInfo.list[0].regionIdNew;
			  document.searchForm.shortCode.value=listAccountInfo.list[0].m6ShortCode;
			  document.searchForm.collectionMgr.value=listAccountInfo.list[0].collectionMgr;
			  document.searchForm.osp.value = listAccountInfo.list[0].osp;
			  document.searchForm.circle.value = listAccountInfo.list[0].circle;//added on 9-jan-2013, circle work
			  document.searchForm.projectManager.value = listAccountInfo.list[0].projectManager;
			  document.searchForm.projectManagerID.value = listAccountInfo.list[0].projectManagerID;
			  document.searchForm.category.value = listAccountInfo.list[0].category;
			  //fillProjectManagerCombo(listAccountInfo.list[0].accountID);
			  fillZoneCombo(listAccountInfo.list[0].regionIdNew);
			//  fillChannelPartnerMaster()
			  //fnGetQuoteNo();
			  //fnGetOpportunityId();			  	  								
			  return false;       
	}else{
		clearAccountRelatedFields();
		alert(data);		
	}	   
		
}

function clearAccountRelatedFields(){
	
	document.searchForm.crmAccountNo.value = "";
	document.searchForm.accountID.value = "";
	document.searchForm.accountName.value = "";
	//document.searchForm.accphoneNo.value = "";
    //[056] Start
	document.searchForm.serviceSegment.value = "";
    //[056] End
	document.searchForm.lob.value = "";
	document.searchForm.accountManager.value = "";
	document.searchForm.projectManager.value = "";
	document.searchForm.AttributeVal_2.value = "";
	document.searchForm.AttributeVal_3.value = "";
	document.searchForm.region.value = "";
	document.searchForm.zone.value = "";
	document.searchForm.shortCode.value="";
	document.searchForm.collectionMgr.value="";
	document.searchForm.osp.value = "";		
	document.searchForm.circle.value = "";//added on 9-jan-2013, circle work
	document.searchForm.category.value = "";
		document.searchForm.groupName.value = "";		
	var comboZone = document.searchForm.zone;
				    var iCountRows = comboZone.length;
				    for (i = 1; i <  iCountRows; i++)
				    {
				    	comboZone.remove(1);
				    }
}
//-------------------------------------------------------------------------
//Added By Saurabh to check uncheck line item
//Start
function checkUncheck(nextNode)
{	
	document.getElementById('SelectAllChck').checked=false;
	if(document.getElementById('chk_spId'+nextNode).checked==true)
	{
		checkChildLineItem(nextNode);
	}
	else
	{
		uncheckParentLineItem(nextNode);
	}		
}
// This below function is called on check of a line item to check line item 
// which are child to this and there childs and so on
function checkChildLineItem(SPID)
{
	for(var i=1;i<parrentArray.length;i++)
	{
		if(SPID==parrentArray[i])
		{
			if(document.getElementById('chk_spId'+childArray[i]) != null)
			{
				document.getElementById('chk_spId'+childArray[i]).checked=true;
				checkChildLineItem(childArray[i]);
			}
		}
	}
}
// This below function is called on unCheck of a line item to check line item 
// which are parent to this and there parent and so on
function uncheckParentLineItem(SPID)
{
	for(var j=1;j<childArray.length;j++)
		{
			if(SPID==childArray[j])
			{
				if(document.getElementById('chk_spId'+parrentArray[j]) != null)
				{
					document.getElementById('chk_spId'+parrentArray[j]).checked=false;
					uncheckParentLineItem(parrentArray[j]);
				}
			}
		}
}
//End
//------------------------------------[ CRM Account Refreshing Start ]-------------------------------
function refreshTransaction(sourceType)
{
	if(document.getElementById('crmAccNo').value.length > 0)
	{
		if(accountValidation(document.getElementById('crmAccNo'),'Account No')==false)
		{				
			return false;
		}			  			  	   
	}else{
		alert('Please enter Account No!!');
		document.getElementById('crmAccNo').focus();
		return false;
	}
	//jQuery.noConflict();
	if(sourceType=="ACCOUNT"){
		refreshAccountBCPDispatchCRM();
	}else{
		//refreshBCPDispatchCRM();
	}
}
function handleRefreshAccount(data){
	if(data=="1"){
		alert("Account synced successfully!!");
		var crmActNo=document.getElementById('crmAccNo').value;
		var orderNo=document.getElementById('poNumber').value;
		
		var jsData =
		{								
			accountIDString:crmActNo
		};	
		var listAccountInfo = jsonrpc.processes.getAccountDetails(jsData);
		
	      document.searchForm.accountID.value = listAccountInfo.list[0].accountID;
		  document.searchForm.crmAccountNo.value = listAccountInfo.list[0].crmAccountId;
		  document.searchForm.accountName.value = listAccountInfo.list[0].accountName;	
          //[056] Start
		  document.searchForm.serviceSegment.value = listAccountInfo.list[0].serviceSegment;
          //[056] End
		  document.searchForm.lob.value = listAccountInfo.list[0].lob;
		  document.searchForm.accountManager.value =listAccountInfo.list[0].accountManager;
		  document.searchForm.AttributeVal_2.value = listAccountInfo.list[0].acmgrPhno;
		  document.searchForm.AttributeVal_3.value = listAccountInfo.list[0].acmgrEmail;
		  document.searchForm.region.value = listAccountInfo.list[0].region;		  
		  if(orderNo=="" || orderNo=="0"){
			 document.searchForm.shortCode.value=listAccountInfo.list[0].m6ShortCode;
			 document.searchForm.regionId.value=listAccountInfo.list[0].regionIdNew;
		  }else{
			  document.searchForm.m6ShortCode.value=listAccountInfo.list[0].m6ShortCode;
		  }
		  document.searchForm.collectionMgr.value=listAccountInfo.list[0].collectionMgr;
		  document.searchForm.osp.value = listAccountInfo.list[0].osp;
		  document.searchForm.circle.value = listAccountInfo.list[0].circle;//added on 9-jan-2013, circle work
		  document.searchForm.projectManager.value = listAccountInfo.list[0].projectManager;
		  document.searchForm.projectManagerID.value = listAccountInfo.list[0].projectManagerID;
		  document.searchForm.category.value = listAccountInfo.list[0].category;
			  document.searchForm.groupName.value = listAccountInfo.list[0].groupName;
		  //fillProjectManagerCombo(listAccountInfo.list[0].accountID);
		  if(orderNo==""||orderNo=="0"){
			  fillZoneCombo(listAccountInfo.list[0].regionIdNew);
		  }
		  if(orderNo==""||orderNo=="0"){
			//  fillChannelPartnerMaster()
		  }
		  //fnGetQuoteNo();
		  //fnGetOpportunityId();			  	  								
		  return false; 
	}else{
		alert(data);
	}	
}
function handleRefreshBCPDispatch(data){
	if(data=="1"){
		alert("BCP and Dispatch Address synced successfully!!");
	}else{
		alert(data);
	}	
}
function refreshAccountBCPDispatchCRM()
{	
	var crmAccountNo=document.getElementById('crmAccNo').value;
		$.ajax({
			    url: gb_path +"/NewOrderAction.do?method=refreshCRMAccountBCPDispatch&accountno="+crmAccountNo,
	                    dataType: "text",
	                    type: "Get",
	                    contentType: "application/json; charset=utf-8",
	                    dataFilter: function(data) { return data; },
	                    success: handleRefreshAccount, 
						beforeSend: function(){
							$("#imgLoadingId").show();	
							$("#crmAccNo").attr('disabled', 'disabled');	
							$("#divSelectAccount").hide();														
							},
						complete: function(){
							$("#imgLoadingId").hide();	
							$("#crmAccNo").attr('disabled', '');
							$("#divSelectAccount").show();
							} ,
						async:true,
	                    error: function(XMLHttpRequest, textStatus, errorThrown) {
	                        alert(errorThrown);
	                    }	                    
	                });	                		                
}
//------------------------------------[ CRM Account Refreshing End ]-------------------------------
function fnExportChargeSummary()
{
	var myForm=document.getElementById('searchForm');
	myForm.action=gb_path+"/viewOrderAction.do?methodName=fnExportChargeSummary&PONum="+document.searchForm.poNumber.value;
	myForm.submit();
}
//------------[ New Order Workflow Start ]-------------------------------------------
function attachWorkflow(){
   // jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var workFlowAttachDetails = jsonrpc.processes.getChangeOrderSubTypeAttached(document.getElementById('poNumber').value);
	var subtypelist=workFlowAttachDetails.workFlowList;
	if(subtypelist.list.length!=0 && subtypelist.list[0].ib2bWorkflowAttachedId!=null){	
		//[022]
		var poNumber1=document.getElementById('poNumber').value;		
		var jsData2 ={
				poNumber:poNumber1			
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var check_workflow_already_attached = jsonrpc.processes.reattachworkflowcheck(jsData2);
		//[022]
		if(check_workflow_already_attached == 0){
			if(subtypelist.list.length > 1){
				var changeOrderNo=document.getElementById('poNumber').value;
				//var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=1"+"&hdnChangeTypeId="+hdnChangeTypeId+"&changeOrderNo="+changeOrderNo;
				var path = gb_path+"/ChangeOrderAction.do?method=attachWorklow&changeOrderNo="+changeOrderNo+"&roleName="+gb_roleID;
				window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
			}else{	
				var jsData1 ={
					poNumber:document.getElementById('poNumber').value,
					projectWorkflowId:subtypelist.list[0].ib2bWorkflowAttachedId,
					roleId:gb_roleID
				};
				    //jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
				    var test1 = jsonrpc.processes.attachworkflowForChangeOrder(jsData1);
			    
			    	if(test1==1)
			    	{
			    		alert('Workflow inserted successfully');
			    		ViewServiceTree(6);
			    	}
			}
		}
	}
	else
	{
		alert('No Workflow exsists for selected order.\n Order Validation Failed');
		return false;
	}
	
}
/*function attachWorkflowForSelectedProject()
{

		var jsData1 =			
	    {
			poNumber:document.getElementById('poNumber').value,
			projectWorkflowId:document.getElementById('hdnworkflowid').value,
			roleId:gb_roleID
		};
		    //jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		    var test1 = jsonrpc.processes.attachworkflowForChangeOrder(jsData1);
	    
	    	if(test1==1)
	    	{
	    		alert('Workflow inserted successfully');	    		
	    	}
	
}
*///------------[ New Order Workflow End ]---------------------------------------------

function setDummyStatusIfPossible(str)
{
	var chkLength=document.forms[0].chkService.length;
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			document.getElementById("dummy").value=str;
		}
	}
	else
	{
		for(var i=0;i<chkLength;i++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chkService[i].checked==true)
			{
				document.getElementById("dummy"+i).value=str;
				break;
			}
		}
	}
}

//084 start 
function calculatesino()
{

 	 var conatctTab = document.getElementById('tableContact');
	 var addressTab = document.getElementById('tableAddress');
	  var iSlNo=1;
		for (var row=1; row<conatctTab.rows.length;row++) 
		{
			
			if(conatctTab.rows[row].style.display != 'none')
			{
	      		var celsContactTab = conatctTab.rows[row].getElementsByTagName('td');
	      		celsContactTab[0].innerHTML=iSlNo;
	      		var celAddressTab = addressTab.rows[row].getElementsByTagName('td');
	      		celAddressTab[0].innerHTML=iSlNo;
	      		iSlNo++;
      		}
      		else
      		{
	      		var celsContactTab = conatctTab.rows[row].getElementsByTagName('td');
	      		celsContactTab[0].innerHTML="X"+row;
	      		var celAddressTab = addressTab.rows[row].getElementsByTagName('td');
	      		celAddressTab[0].innerHTML="X"+row;
      		}
      		
      	}	


}

//[043] Start
function viewLSILinking()
{
	//var myForm=document.getElementById('searchForm');
	//myForm.action=gb_path+"/reportsAction.do?method=viewLSIMapping";
	//myForm.submit();
	var width  = 1500;
	var height = 1500;
	var left   = 10;
	var top    = 10;
	var params = 'width='+width+', height='+height;
 				params += ', top='+top+', left='+left;
 				params += ', directories=no';
 				params += ', location=no';
 				params += ', menubar=no';
 				params += ', resizable=no';
 				params += ', scrollbars=yes';
 				params += ', status=no';
 				params += ', toolbar=no';
	var path = gb_path+"/reportsAction.do?method=viewLSIMapping";	
	window.open(path,"LSIMapping",params);
}
//[043] End
//084 end 
//Start Saurabh for keeping 2D product Values of Ib2b in sync with CRM
function insertUpdate2DProductValues()
{
	var transactionStatus ;
	transactionStatus = jsonrpc.processes.insertUpdate2DProductValues();
	alert(transactionStatus);
}
//Method to set the tab fields based on the form opened
function setFormFields(newFormOpened){
	if(null != newFormOpened && !(""==newFormOpened)){
		fetchFeildList(newFormOpened);
		if('Product Catelog' == newFormOpened)
			DrawTable('SERVICENO','SERVICELINETABLE');
	}	
	newFormOpened = "";	
	
}
//End
//Start Saurabh for viewing cancelled Hardware line in that order
function fnViewCancelledHardwareLineDetails()
{
	var path = gb_path+"/NewOrderAction.do?method=DisplayCancelledhardwareLineDetails";
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:400px");
}
//End
//Start [048]
function  fnUpdateOpportunity()
{
	try{
	var updationStatus ;
	var accountId=0;
	accountId=document.getElementById('accNo').value;
	var crmAccNo=document.getElementById('crmAccNo').value;
	//alert("accountId  >> "+accountId);
	updationStatus = jsonrpc.processes.refreshOpportunity(accountId);
	if(updationStatus==-1){
		alert("Opportunity Id Values Insertion failed for Account No. "+crmAccNo);
		}
	else if(updationStatus==1){
		alert("Opportunity Id  Synced with CRM for Account No. "+crmAccNo);
		}
	else if(updationStatus==-2){
		alert("No Opportunity Id Present in CRM with Account No. "+crmAccNo);
		}
	else if(updationStatus==-3){
		alert(" Failed to connect with CRM Database ");
		}
	}catch(e)
	{
		alert('error code 196: '+ e.message);
	}
}
//[054] Start VIPIN
function checkServicePublishReadyCount(){
	var count=jsonrpc.processes.getServicePublishReadyCount(document.getElementById('poNumber').value);
	return count;
}
//[054] END VIPIN

//[060] start
function searchLineTab(var_sortByColumn,var_pageName){
	
	document.getElementById("id_paging_goToPage").value="";
	pageNumber=1;
	DrawTable(var_sortByColumn,var_pageName);
}
//[060] end

//[061] Start PROJECT SATYAPAN
function ispTagValidation(){
	var ispTagging = document.getElementById("ispTagging");
	var ocs = document.getElementById('hdnOrderCreationSourceId').value;
	//Validation will run only for non-clep orders selected ISP tagging values as "YES".
	if(ispTagging !=undefined && ispTagging.selectedIndex=="1" && ocs!=ORDER_CREATION_SOURCE){
		var ispLicCtgry = document.getElementById("ispLicCtgry");
		var ispLicDate = document.getElementById("ispLicDate");
		var ispLicNo = document.getElementById("ispLicNo");
		if(ispLicCtgry !=undefined && ispLicCtgry.selectedIndex == "0"){
			alert("Please select ISP license category");
			ispLicCtgry.focus();
			return false;
		}
		if(ispLicDate !=undefined && ispLicDate.value == ""){
			alert("Please enter ISP license date");
			ispLicDate.focus();
			return false;
		}
		if(ispLicNo !=undefined && ispLicNo.value == ""){
			alert("Please enter ISP license no.");
			ispLicNo.focus();
			return false;
		}
	}
	return true;
}
//[061] End PROJECT SATYAPAN

//Start channel partner
function channelPartnerTagValidation(){
	var channelMasterTagging = document.getElementById("channelMasterTagging");
	var ocs = document.getElementById('hdnOrderCreationSourceId').value;
	//Validation will run only for non-clep orders selected channelMasterTagging values as "YES".
	if(channelMasterTagging !=undefined && channelMasterTagging.selectedIndex=="1" && ocs!=ORDER_CREATION_SOURCE){
		
		var channelPartnerName = document.getElementById("channelPartnerName");
	
		if(channelPartnerName.value==""){
			alert("Please Select Channel Partner!!");
			channelPartnerName.focus();
			return false;
		}
		
	}
	return true;
}


function  clearChannelPartner()
{
	//document.getElementById("channelMasterTagging").selectedIndex="0";
	document.getElementById("channelPartnerName").value="";
	document.getElementById("channelPartnerId").value="";
	document.getElementById("channelpartnerCode").value="";
	document.getElementById("fieldEngineer").value="";
	document.getElementById("fieldEngineerId").value="";	

	/*var channelMasterTagging = document.getElementById("channelMasterTagging");
	if(channelMasterTagging.selectedIndex=="0"){
	
	document.getElementById('divChannelPartner').style.visibility ="hidden";
	document.getElementById('channelPartnerCtgryName').style.visibility ="hidden";
	document.getElementById('channelPartnerCtgry').style.visibility ="hidden";
	document.getElementById('channelPartnerCode').style.visibility ="hidden";
	document.getElementById('fieldEngineer').style.visibility ="hidden";
	document.getElementById('fieldEngineerId').style.visibility ="hidden";
	document.getElementById('fieldEngineerlbl').style.visibility ="hidden";
	document.getElementById('channelPartnerlbl').style.visibility ="hidden";
	}*/
	
}

function hideChannelPartner(){
	
	var channelMasterTagging = document.getElementById("channelMasterTagging");
	if(channelMasterTagging.selectedIndex=="0"){
		//document.getElementById('divChannelPartner').style.visibility ="hidden";
		document.getElementById('channelPartnerName').style.visibility ="hidden";
		document.getElementById('channelPartnerId').style.visibility ="hidden";
		document.getElementById('channelpartnerCode').style.visibility ="hidden";
		document.getElementById('fieldEngineer').style.visibility ="hidden";
		document.getElementById('fieldEngineerId').style.visibility ="hidden";
		document.getElementById('fieldEngineerlbl').style.visibility ="hidden";
		document.getElementById('channelPartnerlbl').style.visibility ="hidden";
	}
}
	


//pankaj end channel partner