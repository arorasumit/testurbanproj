/*

<!--[018]    Vijay             12-Feb-13                selected page's tab contain  a diffrent color than non-selected pages Tabs. In this way selected Tab contain uniqeness.-->
[019]        Gunjan            28-Apr-14                appended roleid for charge disconnection button on change view product catelog page
[020] Sadananda Behera         01-09-14                 service_cancel_Date,servic_cancel_reson,service_cancelby,service_cancel_remarks for view order
[0111]   Gunjan Singla          9-Mar-15     20150225-R1-021111    LSI and Service No Search required in Lines Tab
[021] Pankaj Thakur              3-jun-2015     correct the code in drawServiceLineTable() method for next/prev button on Line tab  
*/
var fileTypeIdN=0;
var sentMethod=0;
function getTip(value){	
	Tip("<Table border=0> "+ value +"   </table>");	
}
function UnTip(){
	return false;
}

function getTip_DD(obj,value,objName){	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('productCatelog');
	SelectedValue=document.getElementById(objName).options[selIndex].text;	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}

	var counter = 1;
	var countService=1;
	var currentDate = gb_currentDate;
	var isServiceReadOnly =0;
	var OrderNoTM6=0,OrderNoBilling=0;
	var serviceTypeId;
	var selectedServiceName;
	var roleFeild;
	var contactTabFieldList;
	var poDetailTabFieldList;
	var linesTabFieldList;
	var mainTabFieldList;
	var btnselectservice="0";
	var btnAttributes="0";
	var btnAttLabelValues="0";
	var serviceSummaryList;
	var parrentArray = new Array();
	var childArray = new Array();
	
function getRoleWiseFieldInformation(currentTab){
	if(currentTab == 'li1')
		fetchFeildList('Main');
	else if(currentTab == 'li2')	
		fetchFeildList('Contact');
	else if(currentTab == 'li3')	
		fetchFeildList('PO Details');
	else if(currentTab == 'li5')	
	{
		fetchFeildList('Product Catelog');
		DrawTable('SERVICENO','SERVICELINETABLE');
	}
}
function fetchFeildList(tabName){	
		var myForm=document.getElementById('searchForm');
		var sessionid =gb_sessionid;
		roleFeild = jsonrpc.processes.getFieldValidation(sessionid,tabName);
		
		mainTabFieldList = roleFeild.list[0];
		contactTabFieldList = roleFeild.list[1];
		poDetailTabFieldList = roleFeild.list[2];
		linesTabFieldList = roleFeild.list[3];
		serviceSummaryList = roleFeild.list[4];
		checkServiceButton(linesTabFieldList);
		initFieldValidation();
		initButtonFieldValidation();
}
	
	function initButtonFieldValidation()
	{
			var myForm=document.getElementById('searchForm');
			var ctrlArry = new Array();
			ctrlArry[0] =  myForm.btnAddContact;
			ctrlArry[1] =  myForm.btnDeleteContactDetail;
			fieldRoleMappingValidation(contactTabFieldList,ctrlArry);
			ctrlArry = new Array();
			ctrlArry[0] =  myForm.btnAddPODetail;
			ctrlArry[1] =  myForm.btnDeletePoDetail;
			fieldRoleMappingValidation(poDetailTabFieldList,ctrlArry);
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
			var myForm=document.getElementById('searchForm');
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
			ctrlArry[9] =  myForm.entity;
			ctrlArry[10] =  myForm.defaultPO;
			ctrlArry[11] =  myForm.poIssueBy;
			ctrlArry[12] =  myForm.poRemarks;
			ctrlArry[13] =  myForm.poEmailId;
			ctrlArry[14] =  myForm.poDemoContractPeriod;
			fieldRoleMappingValidation(poDetailTabFieldList,ctrlArry);
			//----------Lines Tab-------------------------------		
	}	
	
	function fieldRoleMappingValidation(lstFieldList,ctrlArry)
	{
			var myForm=document.getElementById('searchForm');
			
			for (iField = 0 ; iField < lstFieldList.list.length; iField++)
    		{
	 			for(iCtrl=0;iCtrl< ctrlArry.length;iCtrl++)
				{
					if(ctrlArry[iCtrl] != null)
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
	}
function fnCheckContactAll(){
	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
	    conatctTab = document.getElementById('tableContact');
	    addressTab = document.getElementById('tableAddress');
        var rowlen= conatctTab.rows.length;
         if(rowlen==1){
			 alert("please add one row to delete");
			 document.getElementById('SelectAllChckContact').checked=false;
			 return false;
		}
        
        var chkLength1=document.forms[0].chkSelectContactDetail.length;
	    
		if(document.getElementById('SelectAllChckContact').checked==true){
				if(chkLength1==undefined){
					if(document.forms[0].chkSelectContactDetail.disabled==false){
		                    document.forms[0].chkSelectContactDetail.checked=true;
		                 }
                  }
    	  else{    
		         for (i =0; i<chkLength1; i++){ 
		          	if(myForm.chkSelectContactDetail[i].disabled==false){
					        myForm.chkSelectContactDetail[i].checked=true ;
					}      
		      }
	}
}	
	else{
	           if(chkLength1==undefined){
	                 if(document.forms[0].chkSelectContactDetail.disabled==false){
	                    document.forms[0].chkSelectContactDetail.checked=false;
	                 }
	          }
      else{    
         for (i =0; i<chkLength1; i++){ 
		     if(myForm.chkSelectContactDetail[i].disabled==false){
				        myForm.chkSelectContactDetail[i].checked=false ;
				 }       
		   }
	}
}
}
function fnCheckPOAll(){
	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
	    POTab = document.getElementById('tablePO');
        var rowlen= POTab.rows.length;
         if(rowlen==1){
			 alert("please add one row to delete");
			 document.getElementById('SelectAllPOChck').checked=false;
			 return false;
		}
        var chkLength1=document.forms[0].chkSelectPODetail.length;
		if(document.getElementById('SelectAllPOChck').checked==true){
			        if(chkLength1==undefined){
		                 if(document.forms[0].chkSelectPODetail.disabled==false){
			                    document.forms[0].chkSelectPODetail.checked=true;
			                    counter++;
		                 }
                  }
      else{    
	         for (i =0; i<chkLength1; i++){ 
			     if(myForm.chkSelectPODetail[i].disabled==false){
					        myForm.chkSelectPODetail[i].checked=true ;
					           counter++;
					} 
		      }
	}
	      if(counter==1){
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
function goToHome()
{
	var gotoHomeForm=createDummyForm(gb_path+"/defaultAction.do");
	attachHiddenField(gotoHomeForm,"method","goToHomeAfterClosing");
	gotoHomeForm.submit();
}

//--[018]--start--//
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
//--[018]--end--//

	 //--[018]--start--//
function changeTab(currentTab,tabVal,view1,view2,view3,view4,view5)
{
	    getRoleWiseFieldInformation(currentTab);
		selectCurrentTab(currentTab);
	//--[018]--end--//
	document.getElementById('hdnTabVal').value=tabVal;
	document.getElementById('Page_1').style.display=view1;
	document.getElementById('Page_2').style.display=view2;
	document.getElementById('Page_3').style.display=view3;
	document.getElementById('Page_5').style.display=view4;	
	if(document.getElementById('hdnTabVal')!=null)
	{
			if(document.getElementById('copyProductIcon')!=null){
				document.getElementById('copyProductIcon').style.display="none";
			}
			else{
				if(OrderNoTM6==OrderNoBilling){
					document.getElementById('saveIcon').style.display="none";
					//lawkush Start
					document.getElementById('refreshAccountNo').style.display="none";	
					//lawkush End
				}
				else{
					document.getElementById('saveIcon').style.display="block";
					//lawkush Start
					document.getElementById('refreshAccountNo').style.display="block";	
					//lawkush End
				}
			}
		}	
	
	// below code is used for display approval.jsp page in edit mode through work queue approval: by anil
	var modeValue=gb_modeName;
	
	//Commented below condition due to production Issue of not Displaying Approval Tab After Rejection
	//if(modeValue=="viewMode")
	//{
		document.getElementById('Page_6').style.display=view5;
	//}
	//else
	//{
	//	document.getElementById('Page_6').style.display="none";	
	//}
	//End By Saurabh
}
function fnViewProductCatelog(url)
{
	var orderType=document.getElementById('orderType').value;
	if(orderType=="New" || orderType=="NEW")
	{
		var roleId = gb_roleID;
		var modeValue=gb_modeName;
		var path = gb_path+"/NewOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=2"+"&modeName="+ modeValue+"&roleId="+roleId+"&isView=1"+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value;
		window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
	else
	{
		var hdnChangeTypeId=document.getElementById('hdnChangeTypeId').value;
		var hdnSubChangeTypeId=document.getElementById('hdnSubchangeTypeId').value;
		var changeOrderNo=document.getElementById('poNumber').value;
		//Added By Ashutosh
		var modeValue=gb_modeName;
		//[019]
		var path = gb_path+"/ChangeOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=1"+"&hdnChangeTypeId="+hdnChangeTypeId+"&changeOrderNo="+changeOrderNo+"&hdnSubChangeTypeId="+hdnSubChangeTypeId+"&modeName="+ modeValue+"&subChangeTypeId="+ document.getElementById('subChangeTypeId').value+"&isView=1"+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value+"&roleId="+gb_roleID;
		window.showModalDialog(path,window,"status:false;dialogWidth:2000px;dialogHeight:2000px");
	}
} 
var treeNodes;
function getTree(treeView,nextNode,nextLabel,nextUrl)
{
	
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	//str = "<li><span class='folder'><a href='#' onclick=fnViewProductCatelog(\'"+ escape(nextUrl) +"\');>"+ nextLabel + "</a></span>"	
	str = "<li><span class='folder'><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
//		 	alert(treeView.lstTreeItems.list[i].treeViewURL)
		 	
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL)+"</ul>";
		 	
		}
	}
	str=str+"</li>";
	//alert('AT the End '+ nextNode)
	return str;
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
//Saurabh
function fncServiceTreeView()
{
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
				
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	break;
					}
				}
				 
				//alert(document.forms[0].txtServiceNo1.value); 
				 
				var treeString=getTree(treeView,parentNodeId,nodeText,"");
				document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});
			}
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm");
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
						
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
							 	break;
							}
						}
						//alert(document.forms[0].txtServiceNo1.value); 
						var treeString=getTree(treeView,parentNodeId,nodeText,"");
						document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
					 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;
				}
			}
		}
	}
	
}
function fnBillingTrigger()
{
	//return false;
	poNum=document.searchForm.poNumber.value;
	var mode='view';
	
	var url = gb_path+"/viewOrderAction.do?methodName=fnBillingTrigger";
	{
	var da=new Date();
		url = url + "&orderNo="+poNum+"&"+da+"&mode="+mode;
		//features="scrollbars=yes,resizable=yes,width=800,height=540,left=100,top=100";
		//window.open(url,'xyz',features);
		window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}
//function wrriten for submit task action in edit mode through work queue approval page: by anil
function submitParent()
{
	var modeValue="editON";
	poNumber=document.getElementById('poNumber').value;
	//Meenakshi : commented entire form submit. Made a dummy form and called action
	var incompleteNewOrderForm=createDummyForm(gb_path+"/NewOrderAction.do");
	attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder&orderNo");
	attachHiddenField(incompleteNewOrderForm,"orderNo",poNumber);
	attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
	incompleteNewOrderForm.submit();
}
function popitup(url){
	var poNumber=document.getElementById('poNumber').value;	    	
	    var stage = document.getElementById("stageName").value;
	if(url=="closeorder")
	{
		var myDummyForm=createDummyForm(gb_path+"/defaultAction.do");
		attachHiddenField(myDummyForm,"method","goToHomeAfterClosing");
		myDummyForm.submit();
	}
	if(url=="SelectOpportunity")
	{
		
		var orderNo = 	document.getElementById('poNumber').value;
		var accountId = document.getElementById('accountID').value;				
		var path = gb_path+"/NewOrderAction.do?method=fetchOpportunity&orderNo="+orderNo+"&accountID="+accountId+"&stageName=view";	
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:720px");
		
	}
	//add [006]
	if(url=="FileAttachment")
	{
	//lawkush Start Commented
	// return false;
	//lawkush End Commented
	    if(document.getElementById('accountID').value=="" || document.getElementById('poNumber').value=="")
	    {
	    	alert('Please Select an Account or Order No Before Attaching a File');	    	
	    	return false;
	    }
	    else
	    {
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
	    	//lawkush Start
	    	var crmAccountNo=document.getElementById('crmAccountNo').value;
	    	//lawkush End
			var path = gb_path+"/NewOrderAction.do?method=goToDownloadedFilePage&orderNo="+orderNumber+"&accountNo="+accountNumber+"&crmAccountNo="+crmAccountNo+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod;    //+"&isViewOrder=1";	
			window.open(path,"FileDownloadWindow",params); 						
		}
	}
	//Rakshika : code end
	//if (window.focus) {window.focus()}
	//return false;
	if(url=="approvalHistory")
	{
		var path = gb_path+"/NewOrderAction.do?method=fetchApprovalOrderHistory&poNumber="+ poNumber +"&stage="+stage+"";		
		window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px"); 	    
	}	
}
  //---[016] Start
  
  function popPinList(count) 
{
  
  var stateCode=document.getElementById("stateCode").value;
  var cityCode=document.getElementById("cityCode").value;
  
 if(cityCode=="")
   {
    alert("Please Insert City First!!");
    return false;
     
   }
   var path = gb_path+"/NewOrderAction.do?method=fetchPinList&counter="+count+"&stateCode="+stateCode+"&cityCode="+cityCode;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
 }
function AddPO() 
{return false;}
function AddContact()
{ return false;}
function ViewServiceTree()
{
		var modeValue=gb_modeName;
		var myForm=document.getElementById('searchForm');
		//[250511AKS]Start
		var poNumber=document.getElementById('poNumber').value;
		//[250511AKS]End
		myForm.action=gb_path+"/NewOrderAction.do?method=getServiceTreeview&modeName="+ modeValue+"&orderNo="+poNumber;
		
		myForm.submit();
}

//PAGING-SERVICE-LINE-14-10-2012
	function drawServiceLineTable(){
		
		var pagenumber=document.getElementById("txtPageNumber").value;
		var newPageNumber = document.getElementById("id_paging_goToPage").value;
		//checking if rows already exist, then do not create the table again
		serviceTable = document.getElementById('ServiceList');
	  	
		var iCountRows = serviceTable.rows.length;
		//[0111] start --commented code as paging is not working properly due to this 
	   	/*if(iCountRows>0){
	   		if((null != pagenumber || pagenumber != "") && 
				pagenumber == newPageNumber){
				return;
	  		}
	   	}*/
			
		for (i = 0; i <  iCountRows; i++)
	    {
			serviceTable.deleteRow(0);
	    }
		//[0111] end
		pageRecords=PAGE_SIZE_SERVICE_LINE;
		var countService=1;
		var i, j, oneRecord;
		var test;
		
		mytable = document.getElementById('contentscrollService');
	  	mytable.innerHTML="";
		
	   //-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
	   	document.getElementById("txtPageNumber").value= pageNumber;
	   	document.getElementById("id_paging_goToPage").value = pageNumber
	   	sync();  
	   //[0111]  added searchLSI and searchServiceNo 
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
		//jsonrpc = new JSONRpcClient("<=request.getContextPath()%>" + "/JSON-RPC");
	    test = jsonrpc.processes.poulateServiceListWithPaging(jsData);    
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
	    if(test.list.length > 0){
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
		}
		//-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
		iTotalLength=test.list.length;	
		if(iTotalLength !=0){
			iNoOfPages = test.list[0].maxPageNo;
		}
	    else{     
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
	     //lawkush Start
		var index_var=startRecordId;	   
		//lawkush End
		var tableDataStart;
		var tableDataEnd = "</td>";
		var tableRowStart;
		var tableRowEnd = "</tr>";
		var tableCol;
		var tableStr = "<table  cellpadding='0' cellspacing='0' class='content1' id='ServiceList'>";
		var tableData;
	    
	    for (i = 0 ; i < iTotalLength; i++){
	    	tableRowStart = "<tr class='" + colors[parseInt(i)%2] + "'>";
	    	tableStr = tableStr + tableRowStart;			
			//lawkush Start
			tableDataStart = "<td width='1%' align='center' vAlign='top' class='inner colSrNo'>";		
			tableData="<label>" +index_var+ "</label>";
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			//lawkush End
			tableDataStart = "<td width='4%' align='left' vAlign='top' class='inner colRadio'>";				
			serviceTypeId=test.list[i].serviceTypeId;
			tableData="<input tabIndex='-1' name='chkService' id='chkService"+i+"' type='radio' value='"+ test.list[i].serviceId +"'  onclick=fncServiceTreeView(serviceTypeId)>";
			tableData = tableData+ "<input type = 'hidden' id = 'isServicePublished' value = '"+ test.list[i].isPublished +"'>";
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			tableDataStart = "<td width='5%' align='center' vAlign='top' class='inner colCheckBox' disabled='true'>";
			tableData="<input tabIndex='-1' name='chkDeleteService' id='chkDeleteService"+i+"' value='"+ test.list[i].serviceId +"'  type='checkbox' onclick='allServiceCheckNone()'>";	
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			tableDataStart = "<td width='8%' align='center' vAlign='top' class='inner colPublished'>";
			//Started By Saurabh to make user see whether service is published or not
			if(test.list[i].isPublished==0)
				tableData="<input name='chk' id='chk"+i+"' value='No' class='inputBorder5' type='text' readonly='true' size='5'/>";
			else
				tableData="<input name='chk' id='chk"+i+"' value='Yes' class='inputBorder5' type='text' readonly='true' size='5'/>";
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			//End By Saurabh 
			//Started By Saurabh to make user see whether service is dummy or not
			tableDataStart = "<td width='5%' align='center' vAlign='top' class='inner colDummy'>";
			if(test.list[i].isDummy==0)
				tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='dummy' id='dummy"+i+"' value=' - ' class='inputBorder5' type='text' readonly='true' size='5'/>";
			else
				tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='dummy' id='dummy"+i+"' value='Yes' class='inputBorder5' type='text' readonly='true' size='5'/>";
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			//End By Saurabh
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
			if(btnAttributes=="0")
				tableData="<div class='searchBg1'><a href='#' onclick=popServiceAttribute('ServiceAttributes',"+ 
							countService +",'"+ test.list[i].serviceId +"')>...</a></div><input name='servAttrEntered"+
							countService+"' value='"+test.list[i].servAttrEntered+"' type='hidden' >";
			else
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
			tableData="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:120%;' name='InitiatedTo"+
							countService+"' id='InitiatedTo"+countService+"' value='"+ 
							test.list[i].initiated_to +"' class='inputBorder5' type='text' readonly='true'>";
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			// added by manisha initiated to field o2c end
			
			
			
			
			

			//[020]
			
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
			
			//[020]
			
			
			
			
			
			
			if(countService>0){	
				document.getElementById('selectAllID').disabled=false;
			}
			countService++;		
			//lawkush start
			index_var++;
			//lawkush End
			checkedServiceNumber=gb_CheckedServiceNoParam;		
			if(i==checkedServiceNumber)
			{
				document.getElementById("chkService"+i).checked=true;
				fncServiceTreeView(serviceTypeId);
			}
			tableStr = tableStr + tableRowEnd;
	    }
	    tableStr = tableStr + "</table>";
	    mytable.innerHTML = tableStr;
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
				if(count != 0 && count %2 == 0)
					abc=abc+"<br>( M6 Failed : " +iM6FailedCount+ " )";
				else
					abc=abc+" ( M6 Failed : " +iM6FailedCount+ " )";
				count++;
			}		
			if(iM6SuccessCount>0){
				if(count != 0 && count %2 == 0)
					abc=abc+"<br>( M6 Success : " +iM6SuccessCount+ " )";
				else
					abc=abc+" ( M6 Success : " +iM6SuccessCount+ " )";	
				count++;		
			}		abc = abc + ")</b>";	
			document.getElementById('lblServicetype').innerHTML = abc;
		}
		return false;	
		//-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
}

function popServiceAttribute(url,cntr,serviceId)
{
	//logicalSINo=document.getElementById("txtServiceName"+id).value+"-"+document.NewOrder.txtAccountNo.value+"-"+serviceID
	var serviceId ;
		var ctvar;
	var serviceTypeId=document.getElementById("txtServiceTypeID"+cntr).value;
		if(document.getElementById('orderType').value=='Change')
			{
			ctvar=document.getElementById('HdnChangeTypeId').value;
			}
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
			var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&isView1=1"+"&serviceTypeId="+serviceTypeId;
				// Shubhranshu, For Change Order, URL is modified..
				if(document.getElementById('orderType').value=='Change')
					{
					var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&isView1=1"+"&serviceTypeId="+serviceTypeId+"&HdnChangeTypeID="+ctvar;
					}			
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			//url = url + "?txtServiceID="+cntr+"&serviceID="+serviceId;
				window.showModalDialog(path,window,"status:false;dialogWidth:1200px;dialogHeight:600px");
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
			var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&isView1=1"+"&serviceTypeId="+serviceTypeId;
				// Shubhranshu, For Change Order, URL is modified..
				if(document.getElementById('orderType').value=='Change')
					{
					var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&isView1=1"+"&serviceTypeId="+serviceTypeId+"&HdnChangeTypeID="+ctvar;
					}
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
			var path = gb_path+"/selectservice.do?method=goToPartialPublish&txtServiceID="+ cntr +"&serviceID="+serviceId+"";
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:350px");
		}
	}
}
//-------[020] End
function AddServiceLine()
{
	return false;	
}
//----------------------------------Service Type Tab----------------------Ends Here------------------

function SelectProductCatelog()
{
var modeValue=gb_modeName;

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
				var path = gb_path+"/NewOrderAction.do?method=displayProductCatelog&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accNo').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&modeName="+ modeValue+"&selectedServiceName="+selectedServiceName;
				window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
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
					var path = gb_path+"/NewOrderAction.do?method=displayProductCatelog&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accNo').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&modeName="+ modeValue+"&selectedServiceName="+selectedServiceName;
					window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
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
		var path = gb_path+"/NewOrderAction.do?method=getTProductAttDetail&hdnserviceid="+serviceNo+'&hdnserviceTypeId='+serviceTypeId+"&isView2=1";		
		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//window.open(path);
	 }
}

//-------------------------Validate PO----------------------------------------------
//Added One Parameter for calling same method from Action close and publish. 
function validateOrder(isActionOrPublish)
{
	return false;	
}

//-------------------------Validate PO----------------------------------------------


var treeNodes;
function getTree(treeView,nextNode,nextLabel,nextUrl,serviceDetailId)
{
	
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	str = "<li><span class='folder'><input type='radio' id='rad_spId"+nextNode+ "' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >";	
	if(global_firstNode!=nextNode)
	{
	
		str=str+"<input type='checkbox' id='chk_spId'"+nextNode+"' name='chk_spId' value='"+nextNode+"'>";
		str=str+"<input type='hidden' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input type='hidden' name='serviceDetailId' value='"+nextNode+"'>"+ nextLabel + "</span>";
		
	}
	else 
	{
	str=str+"<input type='hidden' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input type='hidden' name='serviceDetailId' value='"+nextNode+"'>"+ nextLabel + "</span>";
	
	}
	
//	str = "<li><span class='folder'><input type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><input type='checkbox' name='chk_spId' value='"+nextNode+"'><input type='mmm' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input type='mmm' name='serviceDetailId' value='"+nextNode+"'>"+ nextLabel + "</span>"	
	//str = "<li><span ><input type='checkbox' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><a >"+ nextLabel + "</a></span>"	
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
//		 	alert(treeView.lstTreeItems.list[i].treeViewURL)
		 	
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL,treeView.lstTreeItems.list[i].serviceChildId)+"</ul>";
		 	
		}
	}
	str=str+"</li>";
	//alert('AT the End '+ nextNode)
	return str;
}
//var iCountTreeNode=0;	
var global_currTreeNode;

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
	//jsonrpc = new JSONRpcClient("<=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populateZoneList(jsData);    
    if(document.getElementById('zone_1')==null)
    {
	    for(j=0;j<test.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test.list[j].zoneName;
			option.value = test.list[j].zoneId;
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

function TreeNode(nextNode,nextUrl , nextLabel)
{
	global_currTreeNode=nextNode;
	var oTokens = unescape(nextLabel).tokenize('(',' ',true);
	productName=oTokens[0];
	document.getElementById('txtLineItemName').value=unescape(productName);
	document.getElementById('txtLineItemNo').value=nextNode;
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

var global_firstNode;
function fncServiceTreeView(serviceTypeId)
{
if(document.getElementById('orderType').value == 'New')
{

document.getElementById('hdnservicetypeid').value=serviceTypeId;
//alert("fncServiceTreeView : hdnservicetypeid :  " + document.getElementById('hdnservicetypeid').value);
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
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	serviceDetailId = treeView.lstTreeItems.list[i].serviceChildId;
					 	break;
					}
				}
				 global_firstNode=parentNodeId;
				//alert(document.forms[0].txtServiceNo1.value); 
				 
				var treeString=getTree(treeView,parentNodeId,nodeText,"",serviceDetailId);
				document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});
			}
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm")
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
						
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
							 	break;
							}
						}
						global_firstNode=parentNodeId; 
						//alert(document.forms[0].txtServiceNo1.value); 
						 
						var treeString=getTree(treeView,parentNodeId,nodeText,"");
						document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
					 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;
					}
				}
			}
		}
	}
}
else
{
if(document.getElementById('HdnChangeTypeID').value ==3)
	{
		if(document.getElementById('disconnectionDiv')!=null)
		{
	 //start [021] 
				// document.getElementById('suspendDiv').style.display="none";
				// document.getElementById('resumeDiv').style.display="none";
				 //document.getElementById('disconnectionDiv').style.display="block";
				  //end [021] 
		}
		if(document.getElementById('addChildNodeIcon')!=null)
		{
		 //start [021] 
				document.getElementById('addChildNodeIcon').style.display='none';
				//document.getElementById('suspendDiv').style.display="none";
				//document.getElementById('resumeDiv').style.display="none";
				//document.getElementById('disconnectionDiv').style.display="none";
				 //end [021] 
		}
	
	}
	document.getElementById('hdnservicetypeid').value=serviceTypeId;
	var poNumber=document.getElementById('poNumber').value;
	var newCounter=1;
	var chkLength=document.forms[0].chkService.length;
	if(chkLength==undefined)
	{
	
		if(document.forms[0].chkService.checked==true)
		{
		//Start[019]
	  	    var subChangeTypeId=0;
			if(document.getElementById('hdnSubChangeTypeID').value!="undefined")
				{
				subChangeTypeId=document.getElementById("hdnSubChangeTypeID").value
				}
			
			document.getElementById('browser').innerHTML= "";
			    var jsData = 
			    {
			    	serviceId:document.forms[0].txtServiceNo1.value,
					poNumber:poNumber,
					changeTypeId:document.getElementById('HdnChangeTypeID').value,
					subChangeTypeId:subChangeTypeId
			    };
			    //End[019]
			  	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				var treeView = jsonrpc.processes.ViewServiceTreeAfterDisconnection(jsData);
				 document.getElementById('hdnserviceid').value = document.forms[0].txtServiceNo1.value;
				 selectedServiceName = document.forms[0].txtServiceName1.value;
				
				treeNodes = treeView;
				var url ="";
				var nodeText ="";
				var parentNodeId;
				var isDisconnected =0;
				var changeSubtypeID = 0;
				var billingFormat=0;
				var isAdditionalNode=0;
				var serviceDetailId=0;
				var isChargePresent =0;
				//Start[048]
				var createdInServiceId=0;
				var guiServiceId=0;
				//Start By Saurabh
				//to show root line as disconnected when every child is disconnected
				var totalLineCount=0;
				var totalLineDisconnected=0;
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId!="0")
					{
						totalLineCount++;
						if(treeView.lstTreeItems.list[i].isChildDisconnected==1)
						{
							totalLineDisconnected++;
						}
					}	
				}
				if(totalLineCount==totalLineDisconnected)
				{
					isDisconnected=1;
					changeSubtypeID=3;
				}
				//End By Saurabh
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					//serviceDetailId = treeView.lstTreeItems.list[i].serviceProductChildId;
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	isChargePresent = treeView.lstTreeItems.list[i].isChargePresent;
					 	createdInServiceId=treeView.lstTreeItems.list[i].serviceId;
					 	//serviceDetailId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	break;
					}
						
					
				}
				global_firstNode=parentNodeId;	 
				guiServiceId=document.getElementById('hdnserviceid').value; 
				var treeString=getTree(treeView,parentNodeId,nodeText,"",isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,isChargePresent,createdInServiceId,guiServiceId);
				document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});


			  
			}
		}
		else
		{
			for(i=1,j=0;i<=chkLength;i++,newCounter++,j++)
				{
				//Start[019]	
					
					if(document.forms[0].chkService[j].checked==true)
					{
						var subChangeTypeId=0;
						if(document.getElementById('hdnSubChangeTypeID').value!="undefined")
						{
							subChangeTypeId=document.getElementById('hdnSubChangeTypeID').value
						}
						
						document.getElementById('browser').innerHTML= "";
					    var jsData = 
					    { 
					    	serviceId:document.getElementById('txtServiceNo'+ i).value,
							//logicalSINo:document.getElementById('logicalSINo'+ i).value,
							logicalSINo:document.getElementById('txtLogicalSINumber'+ i).value,
							poNumber:poNumber,
							changeTypeId:document.getElementById('HdnChangeTypeID').value,
							subChangeTypeId:subChangeTypeId
					    };
					 //End[019]
					  	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
						 var treeView = jsonrpc.processes.ViewServiceTreeAfterDisconnection(jsData);
						 treeNodes = treeView;
						  document.getElementById('hdnserviceid').value = document.getElementById('txtServiceNo'+ i).value;
						  selectedServiceName = document.getElementById('txtServiceName'+ i).value;
						var treeString="";
						var url ="";
						var nodeText ="";
						var parentNodeId;
						var isDisconnected = 0;
						var changeSubtypeID = 0;
						var billingFormat = 0;
						var serviceDetailId = 0;
						var isAdditionalNode = 0;
						var createdInServiceId=0;
						var guiServiceId=0;
						//Start By Saurabh
						//to show root line as disconnected when every child is disconnected
						var totalLineCount=0;
						var totalLineDisconnected=0;
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId!="0")
							{
								totalLineCount++;
								if(treeView.lstTreeItems.list[i].isChildDisconnected==1)
								{
									totalLineDisconnected++;
								}
							}	
						}
						if(totalLineCount==totalLineDisconnected)
						{
							isDisconnected=1;
							changeSubtypeID=3;
						}
						//End By Saurabh
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							//serviceDetailId = treeView.lstTreeItems.list[i].serviceProductChildId;
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
							 	isChargePresent = treeView.lstTreeItems.list[i].isChargePresent;
							 	createdInServiceId=treeView.lstTreeItems.list[i].serviceId;
							 	//serviceDetailId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	break;
							}
								
						}
						 global_firstNode=parentNodeId;
						//alert(document.forms[0].txtServiceNo1.value); 
						 guiServiceId=document.getElementById('hdnserviceid').value; 

						var treeString=getTree(treeView,parentNodeId,nodeText,"",isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,isChargePresent,createdInServiceId,guiServiceId);
						document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
		//End[048]
					 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;	
					}
					
				}
			
		}
	}
	var selectedNode=document.getElementById('hdnServiceProductId').value;
	if(selectedNode!="")
	{
		//document.getElementById('rad_spId'+selectedNode).checked=true;
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

function getTree(treeView,nextNode,nextLabel,nextUrl,isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,isCharge,createdInServiceId,guiServiceId)
{
//Start[048]


	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	
	if(isDisconnected == 1 && (changeSubtypeID ==3 ||  changeSubtypeID ==4 || changeSubtypeID ==15 ||  changeSubtypeID ==16) )
	{	
	   if(isCharge=="1")
			str = "<li><span ><img src='/IOES/gifs/top/30.gif' title='Disconnected Product'> <input type='radio' name='rdo1' id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span ><img src='/IOES/gifs/top/30.gif' title='Disconnected Product'> <input type='radio' name='rdo1' id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	} 
	else if (isDisconnected == 0 && billingFormat !=3 && changeSubtypeID==3)
	{
		if(isCharge=="1")
			str = "<li><span > <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span > <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	}
	 else if(isDisconnected == 0 && billingFormat ==3 && (changeSubtypeID==3 ||  changeSubtypeID ==4 || changeSubtypeID==15 ||  changeSubtypeID ==16)) 
	{
		if(isCharge=="1")	
			str = "<li><span ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
		else
			str = "<li><span ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	}
	else {
		if (isAdditionalNode == 1 && changeSubtypeID ==0 ) {
			
		str = "<li><span ><img src='/IOES/gifs/top/star.gif' title='Additional Product'> <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
		}
		else if(changeSubtypeID == 10 || changeSubtypeID == 19)
		{						
			
			 if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Shifted Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Shifted Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
		}
		else if(changeSubtypeID == 9 || changeSubtypeID == 18)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
			  {
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Downgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Downgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel  +"</span>"	
			}
			else
			{
			if(isCharge=="1")
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Downgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Downgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel  +"</span>"	
				
			}		
		}
		 //start [021] 
		else if(changeSubtypeID == 8 || changeSubtypeID == 17 )
		{
		    if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			if(isCharge=="1")
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Upgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Upgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else{
				if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Upgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
			else
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Upgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			
		}
		
		else if(changeSubtypeID == 12)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
		    if(isCharge=="1")
		    	str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Regularize Product'> <input id='rad_spId"+nextNode+ "' type='radio' disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
				    	str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Regularize Product'> <input id='rad_spId"+nextNode+ "' type='radio' disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			 if(isCharge=="1")
				    	str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Regularize Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
		    	str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Regularize Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
				
			}	
		
		}
		 //end [021] 
		else if(changeSubtypeID == 6 || changeSubtypeID == 13)
		{
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/Suspend.jpg' title='Suspended Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"
			else
				str = "<li><span ><img src='/IOES/gifs/top/Suspend.jpg' title='Suspended Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"
			
		}
		else if(changeSubtypeID == 7 || changeSubtypeID == 14)
		{
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/resume.jpg' title='Resumed Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"		
			else
				str = "<li><span ><img src='/IOES/gifs/top/resume.jpg' title='Resumed Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"		
				
		}
		else if(changeSubtypeID == 20)
		{
			if(billingFormat==3  && createdInServiceId!=guiServiceId )
		    {
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Network Change Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Network Change Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			if(isCharge=="1")
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Network Change Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Network Change Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			
			}
		}
		else if(changeSubtypeID == 5)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId )
		    {
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Rate Renewed Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
						str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Rate Renewed Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			if(isCharge=="1")
						str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Rate Renewed Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Rate Renewed Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
				
			}			
		} 
		else {		
			if(isCharge=="1")
				str = "<li><span class='folder'><input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' > <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"		
			else
				str = "<li><span class='folder'><input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' > <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"		
				
		}
	}
	//str = "<li><span ><input type='checkbox' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><a >"+ nextLabel + "</a></span>"	
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		isDisconnected = treeView.lstTreeItems.list[i].isChildDisconnected;
		changeSubtypeID = treeView.lstTreeItems.list[i].changeSubTypeID;
		billingFormat = treeView.lstTreeItems.list[i].billFormat;
		isAdditionalNode = treeView.lstTreeItems.list[i].isAdditionalNode;
		createdInServiceId=treeView.lstTreeItems.list[i].serviceId;
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{	 	
			parrentArray[i]=treeView.lstTreeItems.list[i].serviceProductParentId;
	 		childArray[i]=treeView.lstTreeItems.list[i].serviceProductChildId;
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL,isDisconnected,changeSubtypeID,billingFormat,treeView.lstTreeItems.list[i].isAdditionalNode,treeView.lstTreeItems.list[i].isChargePresent,createdInServiceId,guiServiceId)+"</ul>";
		 	
		}
	}
	str=str+"</li>";
	//alert('AT the End '+ nextNode)
	return str;
	//End[048]
}
//------------------------------------------Conatct Tab------------------------------


function DeleteContact()
  {return false;
}
function DeletePO()
  {
	return false;
}
function cancelService()
{
return false;
}

function showNoOfDays()
{
	if(document.getElementById('chkIsDemo').checked==true)
		document.getElementById('dvNoOfDays').style.visibility='visible'
	else
		document.getElementById('dvNoOfDays').style.visibility='hidden'	
}

//FUNCTION IS USED FOR SHOW POPUP WINDOW FOR TASK_ACTION IN EDIT MODE
function popTaskAction(id,ownerId,isaccountManager) 
{
	return false;
}
//FUNCTION IS USED FOR SHOW POPUP FOR TASK_NOTES IN EDIT MODE
function showNotes(taskId,status)
{
	//return false;commenetd by kalpana to show notes in view order , bug id 15032013033  
	
   var sessionId=gb_sessionid;	
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
	

    //jsonrpc = new JSONRpcClient("<=request.getContextPath()%>" + "/JSON-RPC");
    
    var retVal = jsonrpc.processes.ViewNotes(jsData,sessionId);
    
    
    for (i = 0 ; i < retVal.list.length; i++)
    {
		var str;
		var tblRow=document.getElementById('tabNotes').insertRow();
				
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].notesType
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].notesMeaning
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].createdBy
		CellContents = str;
		tblcol.innerHTML = CellContents;

		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].createdDate
		CellContents = str;
		tblcol.innerHTML = CellContents;

	
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="<div class='deleteBg1'><a href='#' onclick=deleteNote("+ retVal.list[i].taskNoteId +","+ retVal.list[i].taskID +",'"+ escape(status) +"')>...</a></div>";
						
		var ownerId = gb_roleID;

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
		CellContents = "No Notes Found";
		tblcol.innerHTML = CellContents;
	}
}
function popTaskStatus(id,ownername) 
{
	return false;
	
}
function fnChargeSummary()
{
	//return false;
	poNum=document.searchForm.poNumber.value;
	var d=new Date();
	var url = gb_path+"/viewOrderAction.do?methodName=fnChargeSummary"+"&"+d.getTime();
	{url = url + "&PONum="+document.searchForm.poNumber.value;
	window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}

function populatePODemoContractPeriod(count){
 	var noOfDays = document.getElementById('noOfDaysForDemo').value;
 	if (noOfDays!=""){
 		document.getElementById('poDemoContractPeriod'+count).value=noOfDays //-Puneet- document.getElementById('noOfDaysForDemo').value;
 	}
}

	var isCtrl = false; 
	document.onkeyup=function(){ 
		if(event.keyCode == 17) isCtrl=false; 
	}

	document.onkeydown=function(e){
		var stage;
		var orderType;
		var modeValue="editON";
		var roleName =gb_roleID;
		var userId = gb_sessionid;
		if(event.keyCode == 17) isCtrl=true; 
		if(event.keyCode == 122 && isCtrl == false){
	  	var orderNo=document.getElementById('poNumber');
	  	if(orderNo.value == "" || orderNo.value == null){	  	 //Puneet document.getElementById('poNumber').value== "" || document.getElementById('poNumber').value == null)
			event.keyCode = 0;
			event.returnValue = false;
			return false;
		}
	  	
		//var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");			
		var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo.value,roleName,userId);
	  	event.keyCode = 0;
		event.returnValue = false;
		
	  	if(orderDetails.list.length>0){
			if(orderNo.value == orderDetails.list[0].orderNumber){
				var goToNewOrderForm=createDummyForm(gb_path+"/defaultNewOrderAction.do");
				attachHiddenField(goToNewOrderForm,"method","goToNewOrder");
				goToNewOrderForm.submit();
			}
		}	
		else{
			orderNo.readOnly=false;
			orderNo.value="";
			orderNo.styleClass="inputBorder2";
			orderNo.focus();
			event.keyCode = 0;
			event.returnValue = false;
		}	
	}
 }

function getServicelineCount(){
		var tabServiceLineCount = document.getElementById('sist').rows.length-1;
		var tabService = document.getElementById('ServiceList');
		var iNewServiceCount = 0;
		var iM6ProcessingServiceCount = 0;
		var iBillingTriggerServiceCount = 0;
		var iBillingTriggerEndServiceCount = 0;
		var iM6CancelledServiceCount = 0;
		var iCRMCancelledServiceCount = 0;
		var iCancelandCopyServiceCount = 0;
		var iM6FailedCount = 0;
		var iM6SuccessCount = 0;

		for(var iCount=0;iCount<tabServiceLineCount;iCount++){
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
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="CancelAndCopy")
				iCancelandCopyServiceCount++;
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="M6 Failed")
				iM6FailedCount++;
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="M6 Success")
				iM6SuccessCount++;
		}
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
			if(count != 0 && count %2 == 0)
				abc=abc+"<br>( M6 Failed : " +iM6FailedCount+ " )";
			else
				abc=abc+" ( M6 Failed : " +iM6FailedCount+ " )";
			count++;
		}		
		if(iM6SuccessCount>0){
			if(count != 0 && count %2 == 0)
				abc=abc+"<br>( M6 Success : " +iM6SuccessCount+ " )";
			else
				abc=abc+" ( M6 Success : " +iM6SuccessCount+ " )";	
			count++;		
		}			
		abc = abc + ")</b>";
		//CellContents = abc;
		document.getElementById('lblServicetype').innerHTML = abc;
}

function refreshTransaction(){
	return false;
}
function fnExportChargeSummary(){
	var myForm=document.getElementById('searchForm');
	myForm.action=gb_path+"/viewOrderAction.do?methodName=fnExportChargeSummary&PONum="+document.searchForm.poNumber.value;
	myForm.submit();
}
	
	function setShowCal(){
		$(":text[showcal='date']").each(function() {
			var thisEl = $(this).attr("id");
			$("#"+thisEl ).datepicker({dateFormat: 'dd/mm/yy'});
		});
	}
	
function insertUpdate2DProductValues(){
	var transactionStatus ;
	transactionStatus = jsonrpc.processes.insertUpdate2DProductValues();
	alert(transactionStatus);
}
function selectAll(){
	return false;
	}
//Start Saurabh for viewing cancelled Hardware line in that order
function fnViewCancelledHardwareLineDetails()
{
	var path = gb_path+"/NewOrderAction.do?method=DisplayCancelledhardwareLineDetails";
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:400px");
}/*

<!--[018]    Vijay             12-Feb-13                selected page's tab contain  a diffrent color than non-selected pages Tabs. In this way selected Tab contain uniqeness.-->
*/
var fileTypeIdN=0;
var sentMethod=0;
function getTip(value){	
	Tip("<Table border=0> "+ value +"   </table>");	
}
function UnTip(){
	return false;
}

function getTip_DD(obj,value,objName){	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('productCatelog');
	SelectedValue=document.getElementById(objName).options[selIndex].text;	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}

	var counter = 1;
	var countService=1;
	var currentDate = gb_currentDate;
	var isServiceReadOnly =0;
	var OrderNoTM6=0,OrderNoBilling=0;
	var serviceTypeId;
	var selectedServiceName;
	var roleFeild;
	var contactTabFieldList;
	var poDetailTabFieldList;
	var linesTabFieldList;
	var mainTabFieldList;
	var btnselectservice="0";
	var btnAttributes="0";
	var btnAttLabelValues="0";
	var serviceSummaryList;
	var parrentArray = new Array();
	var childArray = new Array();
	
function getRoleWiseFieldInformation(currentTab){
	if(currentTab == 'li1')
		fetchFeildList('Main');
	else if(currentTab == 'li2')	
		fetchFeildList('Contact');
	else if(currentTab == 'li3')	
		fetchFeildList('PO Details');
	else if(currentTab == 'li5')	
	{
		fetchFeildList('Product Catelog');
		DrawTable('SERVICENO','SERVICELINETABLE');
	}
}
function fetchFeildList(tabName){	
		var myForm=document.getElementById('searchForm');
		var sessionid =gb_sessionid;
		roleFeild = jsonrpc.processes.getFieldValidation(sessionid,tabName);
		
		mainTabFieldList = roleFeild.list[0];
		contactTabFieldList = roleFeild.list[1];
		poDetailTabFieldList = roleFeild.list[2];
		linesTabFieldList = roleFeild.list[3];
		serviceSummaryList = roleFeild.list[4];
		checkServiceButton(linesTabFieldList);
		initFieldValidation();
		initButtonFieldValidation();
}
	
	function initButtonFieldValidation()
	{
			var myForm=document.getElementById('searchForm');
			var ctrlArry = new Array();
			ctrlArry[0] =  myForm.btnAddContact;
			ctrlArry[1] =  myForm.btnDeleteContactDetail;
			fieldRoleMappingValidation(contactTabFieldList,ctrlArry);
			ctrlArry = new Array();
			ctrlArry[0] =  myForm.btnAddPODetail;
			ctrlArry[1] =  myForm.btnDeletePoDetail;
			fieldRoleMappingValidation(poDetailTabFieldList,ctrlArry);
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
			var myForm=document.getElementById('searchForm');
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
			ctrlArry[9] =  myForm.entity;
			ctrlArry[10] =  myForm.defaultPO;
			ctrlArry[11] =  myForm.poIssueBy;
			ctrlArry[12] =  myForm.poRemarks;
			ctrlArry[13] =  myForm.poEmailId;
			ctrlArry[14] =  myForm.poDemoContractPeriod;
			fieldRoleMappingValidation(poDetailTabFieldList,ctrlArry);
			//----------Lines Tab-------------------------------		
	}	
	
	function fieldRoleMappingValidation(lstFieldList,ctrlArry)
	{
			var myForm=document.getElementById('searchForm');
			
			for (iField = 0 ; iField < lstFieldList.list.length; iField++)
    		{
	 			for(iCtrl=0;iCtrl< ctrlArry.length;iCtrl++)
				{
					if(ctrlArry[iCtrl] != null)
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
	}
function fnCheckContactAll(){
	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
	    conatctTab = document.getElementById('tableContact');
	    addressTab = document.getElementById('tableAddress');
        var rowlen= conatctTab.rows.length;
         if(rowlen==1){
			 alert("please add one row to delete");
			 document.getElementById('SelectAllChckContact').checked=false;
			 return false;
		}
        
        var chkLength1=document.forms[0].chkSelectContactDetail.length;
	    
		if(document.getElementById('SelectAllChckContact').checked==true){
				if(chkLength1==undefined){
					if(document.forms[0].chkSelectContactDetail.disabled==false){
		                    document.forms[0].chkSelectContactDetail.checked=true;
		                 }
                  }
    	  else{    
		         for (i =0; i<chkLength1; i++){ 
		          	if(myForm.chkSelectContactDetail[i].disabled==false){
					        myForm.chkSelectContactDetail[i].checked=true ;
					}      
		      }
	}
}	
	else{
	           if(chkLength1==undefined){
	                 if(document.forms[0].chkSelectContactDetail.disabled==false){
	                    document.forms[0].chkSelectContactDetail.checked=false;
	                 }
	          }
      else{    
         for (i =0; i<chkLength1; i++){ 
		     if(myForm.chkSelectContactDetail[i].disabled==false){
				        myForm.chkSelectContactDetail[i].checked=false ;
				 }       
		   }
	}
}
}
function fnCheckPOAll(){
	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
	    POTab = document.getElementById('tablePO');
        var rowlen= POTab.rows.length;
         if(rowlen==1){
			 alert("please add one row to delete");
			 document.getElementById('SelectAllPOChck').checked=false;
			 return false;
		}
        var chkLength1=document.forms[0].chkSelectPODetail.length;
		if(document.getElementById('SelectAllPOChck').checked==true){
			        if(chkLength1==undefined){
		                 if(document.forms[0].chkSelectPODetail.disabled==false){
			                    document.forms[0].chkSelectPODetail.checked=true;
			                    counter++;
		                 }
                  }
      else{    
	         for (i =0; i<chkLength1; i++){ 
			     if(myForm.chkSelectPODetail[i].disabled==false){
					        myForm.chkSelectPODetail[i].checked=true ;
					           counter++;
					} 
		      }
	}
	      if(counter==1){
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
function goToHome()
{
	var gotoHomeForm=createDummyForm(gb_path+"/defaultAction.do");
	attachHiddenField(gotoHomeForm,"method","goToHomeAfterClosing");
	gotoHomeForm.submit();
}

//--[018]--start--//
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
//--[018]--end--//

	 //--[018]--start--//

function fnViewProductCatelog(url)
{
	var orderType=document.getElementById('orderType').value;
	if(orderType=="New" || orderType=="NEW")
	{
		var roleId = gb_roleID;
		var modeValue=gb_modeName;
		var path = gb_path+"/NewOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=2"+"&modeName="+ modeValue+"&roleId="+roleId+"&isView=1"+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value;
		window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
	else
	{
		var hdnChangeTypeId=document.getElementById('hdnChangeTypeId').value;
		var hdnSubChangeTypeId=document.getElementById('hdnSubchangeTypeId').value;
		var changeOrderNo=document.getElementById('poNumber').value;
		//Added By Ashutosh
		var modeValue=gb_modeName;
		//[019]
		var path = gb_path+"/ChangeOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=1"+"&hdnChangeTypeId="+hdnChangeTypeId+"&changeOrderNo="+changeOrderNo+"&hdnSubChangeTypeId="+hdnSubChangeTypeId+"&modeName="+ modeValue+"&subChangeTypeId="+ document.getElementById('subChangeTypeId').value+"&isView=1"+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value+"&roleId="+gb_roleID;
		window.showModalDialog(path,window,"status:false;dialogWidth:2000px;dialogHeight:2000px");
	}
} 
var treeNodes;
function getTree(treeView,nextNode,nextLabel,nextUrl)
{
	
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	//str = "<li><span class='folder'><a href='#' onclick=fnViewProductCatelog(\'"+ escape(nextUrl) +"\');>"+ nextLabel + "</a></span>"	
	str = "<li><span class='folder'><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
//		 	alert(treeView.lstTreeItems.list[i].treeViewURL)
		 	
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL)+"</ul>";
		 	
		}
	}
	str=str+"</li>";
	//alert('AT the End '+ nextNode)
	return str;
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
//Saurabh
function fncServiceTreeView()
{
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
				
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	break;
					}
				}
				 
				//alert(document.forms[0].txtServiceNo1.value); 
				 
				var treeString=getTree(treeView,parentNodeId,nodeText,"");
				document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});
			}
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm");
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
						
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
							 	break;
							}
						}
						//alert(document.forms[0].txtServiceNo1.value); 
						var treeString=getTree(treeView,parentNodeId,nodeText,"");
						document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
					 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;
				}
			}
		}
	}
	
}
function fnBillingTrigger()
{
	//return false;
	poNum=document.searchForm.poNumber.value;
	var mode='view';
	
	var url = gb_path+"/viewOrderAction.do?methodName=fnBillingTrigger";
	{
	var da=new Date();
		url = url + "&orderNo="+poNum+"&"+da+"&mode="+mode;
		//features="scrollbars=yes,resizable=yes,width=800,height=540,left=100,top=100";
		//window.open(url,'xyz',features);
		window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}
//function wrriten for submit task action in edit mode through work queue approval page: by anil
function submitParent()
{
	var modeValue="editON";
	poNumber=document.getElementById('poNumber').value;
	//Meenakshi : commented entire form submit. Made a dummy form and called action
	var incompleteNewOrderForm=createDummyForm(gb_path+"/NewOrderAction.do");
	attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder&orderNo");
	attachHiddenField(incompleteNewOrderForm,"orderNo",poNumber);
	attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
	incompleteNewOrderForm.submit();
}
function popitup(url){
	var poNumber=document.getElementById('poNumber').value;	    	
	    var stage = document.getElementById("stageName").value;
	if(url=="closeorder")
	{
		var myDummyForm=createDummyForm(gb_path+"/defaultAction.do");
		attachHiddenField(myDummyForm,"method","goToHomeAfterClosing");
		myDummyForm.submit();
	}
	if(url=="SelectOpportunity")
	{
		
		var orderNo = 	document.getElementById('poNumber').value;
		var accountId = document.getElementById('accountID').value;				
		var path = gb_path+"/NewOrderAction.do?method=fetchOpportunity&orderNo="+orderNo+"&accountID="+accountId+"&stageName=view";	
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:720px");
		
	}
	//add [006]
	if(url=="FileAttachment")
	{
	//lawkush Start Commented
	// return false;
	//lawkush End Commented
	    if(document.getElementById('accountID').value=="" || document.getElementById('poNumber').value=="")
	    {
	    	alert('Please Select an Account or Order No Before Attaching a File');	    	
	    	return false;
	    }
	    else
	    {
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
	    	//lawkush Start
	    	var crmAccountNo=document.getElementById('crmAccountNo').value;
	    	//lawkush End
			var path = gb_path+"/NewOrderAction.do?method=goToDownloadedFilePage&orderNo="+orderNumber+"&accountNo="+accountNumber+"&crmAccountNo="+crmAccountNo+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod;    //+"&isViewOrder=1";	
			window.open(path,"FileDownloadWindow",params); 						
		}
	}
	//Rakshika : code end
	//if (window.focus) {window.focus()}
	//return false;
	if(url=="approvalHistory")
	{
		var path = gb_path+"/NewOrderAction.do?method=fetchApprovalOrderHistory&poNumber="+ poNumber +"&stage="+stage+"";		
		window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px"); 	    
	}	
}
  //---[016] Start
  
  function popPinList(count) 
{
  
  var stateCode=document.getElementById("stateCode").value;
  var cityCode=document.getElementById("cityCode").value;
  
 if(cityCode=="")
   {
    alert("Please Insert City First!!");
    return false;
     
   }
   var path = gb_path+"/NewOrderAction.do?method=fetchPinList&counter="+count+"&stateCode="+stateCode+"&cityCode="+cityCode;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
 }
function AddPO() 
{return false;}
function AddContact()
{ return false;}
function ViewServiceTree()
{
		var modeValue=gb_modeName;
		var myForm=document.getElementById('searchForm');
		//[250511AKS]Start
		var poNumber=document.getElementById('poNumber').value;
		//[250511AKS]End
		myForm.action=gb_path+"/NewOrderAction.do?method=getServiceTreeview&modeName="+ modeValue+"&orderNo="+poNumber;
		
		myForm.submit();
}

function popServiceAttribute(url,cntr,serviceId)
{
	//logicalSINo=document.getElementById("txtServiceName"+id).value+"-"+document.NewOrder.txtAccountNo.value+"-"+serviceID
	var serviceId ;
	var ctvar;
	var serviceTypeId=document.getElementById("txtServiceTypeID"+cntr).value;
	if(document.getElementById('orderType').value=='Change')
		{
		ctvar=document.getElementById('HdnChangeTypeId').value;
		}
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
			var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&isView1=1"+"&serviceTypeId="+serviceTypeId;			
			
			// Shubhranshu, For Change Order, URL is modified..
			if(document.getElementById('orderType').value=='Change')
				{
				var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&isView1=1"+"&serviceTypeId="+serviceTypeId+"&HdnChangeTypeID="+ctvar;
				}			
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			//url = url + "?txtServiceID="+cntr+"&serviceID="+serviceId;
			window.showModalDialog(path,window,"status:false;dialogWidth:1200px;dialogHeight:600px");
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
			var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&isView1=1"+"&serviceTypeId="+serviceTypeId;
			
			// Shubhranshu, For Change Order, URL is modified..
			if(document.getElementById('orderType').value=='Change')
				{
				var path = gb_path+"/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&isView1=1"+"&serviceTypeId="+serviceTypeId+"&HdnChangeTypeID="+ctvar;
				}
			
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
			var path = gb_path+"/selectservice.do?method=goToPartialPublish&txtServiceID="+ cntr +"&serviceID="+serviceId+"";
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:350px");
		}
	}
}
//-------[020] End
function AddServiceLine()
{
	return false;	
}
//----------------------------------Service Type Tab----------------------Ends Here------------------

function SelectProductCatelog()
{
var modeValue=gb_modeName;

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
				var path = gb_path+"/NewOrderAction.do?method=displayProductCatelog&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accNo').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&modeName="+ modeValue+"&selectedServiceName="+selectedServiceName;
				window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
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
					var path = gb_path+"/NewOrderAction.do?method=displayProductCatelog&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accNo').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&modeName="+ modeValue+"&selectedServiceName="+selectedServiceName;
					window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
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
		var path = gb_path+"/NewOrderAction.do?method=getTProductAttDetail&hdnserviceid="+serviceNo+'&hdnserviceTypeId='+serviceTypeId+"&isView2=1";		
		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//window.open(path);
	 }
}

//-------------------------Validate PO----------------------------------------------
//Added One Parameter for calling same method from Action close and publish. 
function validateOrder(isActionOrPublish)
{
	return false;	
}

//-------------------------Validate PO----------------------------------------------


var treeNodes;
function getTree(treeView,nextNode,nextLabel,nextUrl,serviceDetailId)
{
	
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	str = "<li><span class='folder'><input type='radio' id='rad_spId"+nextNode+ "' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >";	
	if(global_firstNode!=nextNode)
	{
	
		str=str+"<input type='checkbox' id='chk_spId'"+nextNode+"' name='chk_spId' value='"+nextNode+"'>";
		str=str+"<input type='hidden' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input type='hidden' name='serviceDetailId' value='"+nextNode+"'>"+ nextLabel + "</span>";
		
	}
	else 
	{
	str=str+"<input type='hidden' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input type='hidden' name='serviceDetailId' value='"+nextNode+"'>"+ nextLabel + "</span>";
	
	}
	
//	str = "<li><span class='folder'><input type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><input type='checkbox' name='chk_spId' value='"+nextNode+"'><input type='mmm' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input type='mmm' name='serviceDetailId' value='"+nextNode+"'>"+ nextLabel + "</span>"	
	//str = "<li><span ><input type='checkbox' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><a >"+ nextLabel + "</a></span>"	
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
//		 	alert(treeView.lstTreeItems.list[i].treeViewURL)
		 	
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL,treeView.lstTreeItems.list[i].serviceChildId)+"</ul>";
		 	
		}
	}
	str=str+"</li>";
	//alert('AT the End '+ nextNode)
	return str;
}
//var iCountTreeNode=0;	
var global_currTreeNode;

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
	//jsonrpc = new JSONRpcClient("<=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populateZoneList(jsData);    
    if(document.getElementById('zone_1')==null)
    {
	    for(j=0;j<test.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test.list[j].zoneName;
			option.value = test.list[j].zoneId;
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

function TreeNode(nextNode,nextUrl , nextLabel)
{
	global_currTreeNode=nextNode;
	var oTokens = unescape(nextLabel).tokenize('(',' ',true);
	productName=oTokens[0];
	document.getElementById('txtLineItemName').value=unescape(productName);
	document.getElementById('txtLineItemNo').value=nextNode;
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

var global_firstNode;
function fncServiceTreeView(serviceTypeId)
{
if(document.getElementById('orderType').value == 'New')
{

document.getElementById('hdnservicetypeid').value=serviceTypeId;
//alert("fncServiceTreeView : hdnservicetypeid :  " + document.getElementById('hdnservicetypeid').value);
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
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	serviceDetailId = treeView.lstTreeItems.list[i].serviceChildId;
					 	break;
					}
				}
				 global_firstNode=parentNodeId;
				//alert(document.forms[0].txtServiceNo1.value); 
				 
				var treeString=getTree(treeView,parentNodeId,nodeText,"",serviceDetailId);
				document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});
			}
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm")
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
						
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
							 	break;
							}
						}
						global_firstNode=parentNodeId; 
						//alert(document.forms[0].txtServiceNo1.value); 
						 
						var treeString=getTree(treeView,parentNodeId,nodeText,"");
						document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
					 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;
					}
				}
			}
		}
	}
}
else
{
if(document.getElementById('HdnChangeTypeID').value ==3)
	{
		if(document.getElementById('disconnectionDiv')!=null)
		{
	 //start [021] 
				// document.getElementById('suspendDiv').style.display="none";
				// document.getElementById('resumeDiv').style.display="none";
				 //document.getElementById('disconnectionDiv').style.display="block";
				  //end [021] 
		}
		if(document.getElementById('addChildNodeIcon')!=null)
		{
		 //start [021] 
				document.getElementById('addChildNodeIcon').style.display='none';
				//document.getElementById('suspendDiv').style.display="none";
				//document.getElementById('resumeDiv').style.display="none";
				//document.getElementById('disconnectionDiv').style.display="none";
				 //end [021] 
		}
	
	}
	document.getElementById('hdnservicetypeid').value=serviceTypeId;
	var poNumber=document.getElementById('poNumber').value;
	var newCounter=1;
	var chkLength=document.forms[0].chkService.length;
	if(chkLength==undefined)
	{
	
		if(document.forms[0].chkService.checked==true)
		{
		//Start[019]
	  	    var subChangeTypeId=0;
			if(document.getElementById('hdnSubChangeTypeID').value!="undefined")
				{
				subChangeTypeId=document.getElementById("hdnSubChangeTypeID").value
				}
			
			document.getElementById('browser').innerHTML= "";
			    var jsData = 
			    {
			    	serviceId:document.forms[0].txtServiceNo1.value,
					poNumber:poNumber,
					changeTypeId:document.getElementById('HdnChangeTypeID').value,
					subChangeTypeId:subChangeTypeId
			    };
			    //End[019]
			  	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
				var treeView = jsonrpc.processes.ViewServiceTreeAfterDisconnection(jsData);
				 document.getElementById('hdnserviceid').value = document.forms[0].txtServiceNo1.value;
				 selectedServiceName = document.forms[0].txtServiceName1.value;
				
				treeNodes = treeView;
				var url ="";
				var nodeText ="";
				var parentNodeId;
				var isDisconnected =0;
				var changeSubtypeID = 0;
				var billingFormat=0;
				var isAdditionalNode=0;
				var serviceDetailId=0;
				var isChargePresent =0;
				//Start[048]
				var createdInServiceId=0;
				var guiServiceId=0;
				//Start By Saurabh
				//to show root line as disconnected when every child is disconnected
				var totalLineCount=0;
				var totalLineDisconnected=0;
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId!="0")
					{
						totalLineCount++;
						if(treeView.lstTreeItems.list[i].isChildDisconnected==1)
						{
							totalLineDisconnected++;
						}
					}	
				}
				if(totalLineCount==totalLineDisconnected)
				{
					isDisconnected=1;
					changeSubtypeID=3;
				}
				//End By Saurabh
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					//serviceDetailId = treeView.lstTreeItems.list[i].serviceProductChildId;
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	isChargePresent = treeView.lstTreeItems.list[i].isChargePresent;
					 	createdInServiceId=treeView.lstTreeItems.list[i].serviceId;
					 	//serviceDetailId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	break;
					}
						
					
				}
				global_firstNode=parentNodeId;	 
				guiServiceId=document.getElementById('hdnserviceid').value; 
				var treeString=getTree(treeView,parentNodeId,nodeText,"",isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,isChargePresent,createdInServiceId,guiServiceId);
				document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});


			  
			}
		}
		else
		{
			for(i=1,j=0;i<=chkLength;i++,newCounter++,j++)
				{
				//Start[019]	
					
					if(document.forms[0].chkService[j].checked==true)
					{
						var subChangeTypeId=0;
						if(document.getElementById('hdnSubChangeTypeID').value!="undefined")
						{
							subChangeTypeId=document.getElementById('hdnSubChangeTypeID').value
						}
						
						document.getElementById('browser').innerHTML= "";
					    var jsData = 
					    { 
					    	serviceId:document.getElementById('txtServiceNo'+ i).value,
							//logicalSINo:document.getElementById('logicalSINo'+ i).value,
							logicalSINo:document.getElementById('txtLogicalSINumber'+ i).value,
							poNumber:poNumber,
							changeTypeId:document.getElementById('HdnChangeTypeID').value,
							subChangeTypeId:subChangeTypeId
					    };
					 //End[019]
					  	//jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");
						 var treeView = jsonrpc.processes.ViewServiceTreeAfterDisconnection(jsData);
						 treeNodes = treeView;
						  document.getElementById('hdnserviceid').value = document.getElementById('txtServiceNo'+ i).value;
						  selectedServiceName = document.getElementById('txtServiceName'+ i).value;
						var treeString="";
						var url ="";
						var nodeText ="";
						var parentNodeId;
						var isDisconnected = 0;
						var changeSubtypeID = 0;
						var billingFormat = 0;
						var serviceDetailId = 0;
						var isAdditionalNode = 0;
						var createdInServiceId=0;
						var guiServiceId=0;
						//Start By Saurabh
						//to show root line as disconnected when every child is disconnected
						var totalLineCount=0;
						var totalLineDisconnected=0;
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId!="0")
							{
								totalLineCount++;
								if(treeView.lstTreeItems.list[i].isChildDisconnected==1)
								{
									totalLineDisconnected++;
								}
							}	
						}
						if(totalLineCount==totalLineDisconnected)
						{
							isDisconnected=1;
							changeSubtypeID=3;
						}
						//End By Saurabh
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							//serviceDetailId = treeView.lstTreeItems.list[i].serviceProductChildId;
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
							 	isChargePresent = treeView.lstTreeItems.list[i].isChargePresent;
							 	createdInServiceId=treeView.lstTreeItems.list[i].serviceId;
							 	//serviceDetailId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	break;
							}
								
						}
						 global_firstNode=parentNodeId;
						//alert(document.forms[0].txtServiceNo1.value); 
						 guiServiceId=document.getElementById('hdnserviceid').value; 

						var treeString=getTree(treeView,parentNodeId,nodeText,"",isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,isChargePresent,createdInServiceId,guiServiceId);
						document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
		//End[048]
					 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;	
					}
					
				}
			
		}
	}
	var selectedNode=document.getElementById('hdnServiceProductId').value;
	if(selectedNode!="")
	{
		//document.getElementById('rad_spId'+selectedNode).checked=true;
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

function getTree(treeView,nextNode,nextLabel,nextUrl,isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,isCharge,createdInServiceId,guiServiceId)
{
//Start[048]


	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	
	if(isDisconnected == 1 && (changeSubtypeID ==3 ||  changeSubtypeID ==4 || changeSubtypeID ==15 ||  changeSubtypeID ==16) )
	{	
	   if(isCharge=="1")
			str = "<li><span ><img src='/IOES/gifs/top/30.gif' title='Disconnected Product'> <input type='radio' name='rdo1' id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span ><img src='/IOES/gifs/top/30.gif' title='Disconnected Product'> <input type='radio' name='rdo1' id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	} 
	else if (isDisconnected == 0 && billingFormat !=3 && changeSubtypeID==3)
	{
		if(isCharge=="1")
			str = "<li><span > <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span > <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	}
	 else if(isDisconnected == 0 && billingFormat ==3 && (changeSubtypeID==3 ||  changeSubtypeID ==4 || changeSubtypeID==15 ||  changeSubtypeID ==16)) 
	{
		if(isCharge=="1")	
			str = "<li><span ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
		else
			str = "<li><span ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	}
	else {
		if (isAdditionalNode == 1 && changeSubtypeID ==0 ) {
			
		str = "<li><span ><img src='/IOES/gifs/top/star.gif' title='Additional Product'> <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  ><input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
		}
		else if(changeSubtypeID == 10 || changeSubtypeID == 19)
		{						
			
			 if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Shifted Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Shifted Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
		}
		else if(changeSubtypeID == 9 || changeSubtypeID == 18)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
			  {
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Downgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Downgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel  +"</span>"	
			}
			else
			{
			if(isCharge=="1")
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Downgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Downgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel  +"</span>"	
				
			}		
		}
		 //start [021] 
		else if(changeSubtypeID == 8 || changeSubtypeID == 17 )
		{
		    if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			if(isCharge=="1")
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Upgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Upgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else{
				if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Upgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
			else
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Upgraded Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			
		}
		
		else if(changeSubtypeID == 12)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
		    if(isCharge=="1")
		    	str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Regularize Product'> <input id='rad_spId"+nextNode+ "' type='radio' disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
				    	str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Regularize Product'> <input id='rad_spId"+nextNode+ "' type='radio' disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			 if(isCharge=="1")
				    	str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Regularize Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
		    	str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Regularize Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
				
			}	
		
		}
		 //end [021] 
		else if(changeSubtypeID == 6 || changeSubtypeID == 13)
		{
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/Suspend.jpg' title='Suspended Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"
			else
				str = "<li><span ><img src='/IOES/gifs/top/Suspend.jpg' title='Suspended Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"
			
		}
		else if(changeSubtypeID == 7 || changeSubtypeID == 14)
		{
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/resume.jpg' title='Resumed Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"		
			else
				str = "<li><span ><img src='/IOES/gifs/top/resume.jpg' title='Resumed Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"		
				
		}
		else if(changeSubtypeID == 20)
		{
			if(billingFormat==3  && createdInServiceId!=guiServiceId )
		    {
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Network Change Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Network Change Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			if(isCharge=="1")
					str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Network Change Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Network Change Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			
			}
		}
		else if(changeSubtypeID == 5)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId )
		    {
			if(isCharge=="1")
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Rate Renewed Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
						str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Rate Renewed Product'> <input id='rad_spId"+nextNode+ "' type='radio'  disabled=true name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			if(isCharge=="1")
						str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Rate Renewed Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
				str = "<li><span ><img src='/IOES/gifs/top/lock.gif' title='Rate Renewed Product'> <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
				
			}			
		} 
		else {		
			if(isCharge=="1")
				str = "<li><span class='folder'><input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' > <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"		
			else
				str = "<li><span class='folder'><input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' > <input id='rad_spId"+nextNode+ "' type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"		
				
		}
	}
	//str = "<li><span ><input type='checkbox' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><a >"+ nextLabel + "</a></span>"	
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		isDisconnected = treeView.lstTreeItems.list[i].isChildDisconnected;
		changeSubtypeID = treeView.lstTreeItems.list[i].changeSubTypeID;
		billingFormat = treeView.lstTreeItems.list[i].billFormat;
		isAdditionalNode = treeView.lstTreeItems.list[i].isAdditionalNode;
		createdInServiceId=treeView.lstTreeItems.list[i].serviceId;
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{	 	
			parrentArray[i]=treeView.lstTreeItems.list[i].serviceProductParentId;
	 		childArray[i]=treeView.lstTreeItems.list[i].serviceProductChildId;
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL,isDisconnected,changeSubtypeID,billingFormat,treeView.lstTreeItems.list[i].isAdditionalNode,treeView.lstTreeItems.list[i].isChargePresent,createdInServiceId,guiServiceId)+"</ul>";
		 	
		}
	}
	str=str+"</li>";
	//alert('AT the End '+ nextNode)
	return str;
	//End[048]
}
//------------------------------------------Conatct Tab------------------------------


function DeleteContact()
  {return false;
}
function DeletePO()
  {
	return false;
}
function cancelService()
{
return false;
}

function showNoOfDays()
{
	if(document.getElementById('chkIsDemo').checked==true)
		document.getElementById('dvNoOfDays').style.visibility='visible'
	else
		document.getElementById('dvNoOfDays').style.visibility='hidden'	
}

//FUNCTION IS USED FOR SHOW POPUP WINDOW FOR TASK_ACTION IN EDIT MODE
function popTaskAction(id,ownerId,isaccountManager) 
{
	return false;
}
//FUNCTION IS USED FOR SHOW POPUP FOR TASK_NOTES IN EDIT MODE
function showNotes(taskId,status)
{
	//return false;commenetd by kalpana to show notes in view order , bug id 15032013033  
	
   var sessionId=gb_sessionid;	
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
	

    //jsonrpc = new JSONRpcClient("<=request.getContextPath()%>" + "/JSON-RPC");
    
    var retVal = jsonrpc.processes.ViewNotes(jsData,sessionId);
    
    
    for (i = 0 ; i < retVal.list.length; i++)
    {
		var str;
		var tblRow=document.getElementById('tabNotes').insertRow();
				
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].notesType
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].notesMeaning
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].createdBy
		CellContents = str;
		tblcol.innerHTML = CellContents;

		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].createdDate
		CellContents = str;
		tblcol.innerHTML = CellContents;

	
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="<div class='deleteBg1'><a href='#' onclick=deleteNote("+ retVal.list[i].taskNoteId +","+ retVal.list[i].taskID +",'"+ escape(status) +"')>...</a></div>";
						
		var ownerId = gb_roleID;

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
		CellContents = "No Notes Found";
		tblcol.innerHTML = CellContents;
	}
}
function popTaskStatus(id,ownername) 
{
	return false;
	
}
function fnChargeSummary()
{
	//return false;
	poNum=document.searchForm.poNumber.value;
	var d=new Date();
	var url = gb_path+"/viewOrderAction.do?methodName=fnChargeSummary"+"&"+d.getTime();
	{url = url + "&PONum="+document.searchForm.poNumber.value;
	window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}

function populatePODemoContractPeriod(count){
 	var noOfDays = document.getElementById('noOfDaysForDemo').value;
 	if (noOfDays!=""){
 		document.getElementById('poDemoContractPeriod'+count).value=noOfDays //-Puneet- document.getElementById('noOfDaysForDemo').value;
 	}
}

	var isCtrl = false; 
	document.onkeyup=function(){ 
		if(event.keyCode == 17) isCtrl=false; 
	}

	document.onkeydown=function(e){
		var stage;
		var orderType;
		var modeValue="editON";
		var roleName =gb_roleID;
		var userId = gb_sessionid;
		if(event.keyCode == 17) isCtrl=true; 
		if(event.keyCode == 122 && isCtrl == false){
	  	var orderNo=document.getElementById('poNumber');
	  	if(orderNo.value == "" || orderNo.value == null){	  	 //Puneet document.getElementById('poNumber').value== "" || document.getElementById('poNumber').value == null)
			event.keyCode = 0;
			event.returnValue = false;
			return false;
		}
	  	
		//var jsonrpc = new JSONRpcClient('<=request.getContextPath()%>' + "/JSON-RPC");			
		var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo.value,roleName,userId);
	  	event.keyCode = 0;
		event.returnValue = false;
		
	  	if(orderDetails.list.length>0){
			if(orderNo.value == orderDetails.list[0].orderNumber){
				var goToNewOrderForm=createDummyForm(gb_path+"/defaultNewOrderAction.do");
				attachHiddenField(goToNewOrderForm,"method","goToNewOrder");
				goToNewOrderForm.submit();
			}
		}	
		else{
			orderNo.readOnly=false;
			orderNo.value="";
			orderNo.styleClass="inputBorder2";
			orderNo.focus();
			event.keyCode = 0;
			event.returnValue = false;
		}	
	}
 }

function getServicelineCount(){
		var tabServiceLineCount = document.getElementById('sist').rows.length-1;
		var tabService = document.getElementById('ServiceList');
		var iNewServiceCount = 0;
		var iM6ProcessingServiceCount = 0;
		var iBillingTriggerServiceCount = 0;
		var iBillingTriggerEndServiceCount = 0;
		var iM6CancelledServiceCount = 0;
		var iCRMCancelledServiceCount = 0;
		var iCancelandCopyServiceCount = 0;
		var iM6FailedCount = 0;
		var iM6SuccessCount = 0;

		for(var iCount=0;iCount<tabServiceLineCount;iCount++){
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
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="CancelAndCopy")
				iCancelandCopyServiceCount++;
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="M6 Failed")
				iM6FailedCount++;
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="M6 Success")
				iM6SuccessCount++;
		}
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
			if(count != 0 && count %2 == 0)
				abc=abc+"<br>( M6 Failed : " +iM6FailedCount+ " )";
			else
				abc=abc+" ( M6 Failed : " +iM6FailedCount+ " )";
			count++;
		}		
		if(iM6SuccessCount>0){
			if(count != 0 && count %2 == 0)
				abc=abc+"<br>( M6 Success : " +iM6SuccessCount+ " )";
			else
				abc=abc+" ( M6 Success : " +iM6SuccessCount+ " )";	
			count++;		
		}			
		abc = abc + ")</b>";
		//CellContents = abc;
		document.getElementById('lblServicetype').innerHTML = abc;
}

function refreshTransaction(){
	return false;
}
function fnExportChargeSummary(){
	var myForm=document.getElementById('searchForm');
	myForm.action=gb_path+"/viewOrderAction.do?methodName=fnExportChargeSummary&PONum="+document.searchForm.poNumber.value;
	myForm.submit();
}
	
	function setShowCal(){
		$(":text[showcal='date']").each(function() {
			var thisEl = $(this).attr("id");
			$("#"+thisEl ).datepicker({dateFormat: 'dd/mm/yy'});
		});
	}
	
function insertUpdate2DProductValues(){
	var transactionStatus ;
	transactionStatus = jsonrpc.processes.insertUpdate2DProductValues();
	alert(transactionStatus);
}
function selectAll(){
	return false;
}	
//Start Saurabh for viewing cancelled Hardware line in that order
function fnViewCancelledHardwareLineDetails()
{
	var path = gb_path+"/NewOrderAction.do?method=DisplayCancelledhardwareLineDetails";
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:400px");
}
function popContact()
{
	return false;
}
function fnViewCancelAndCopyReport(serviceId,isNew)
{
	var poNumber=document.getElementById('poNumber').value;
	//var myForm=document.getElementById('searchForm');				
	//myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=DisplayCancalCopyReport&poNumber="+poNumber+"&serviceId="+serviceId;
	//myForm.submit();
	var path = gb_path+"/reportsAction.do?method=DisplayCancalCopyReport&poNumber="+poNumber+"&serviceId="+serviceId+"&isNew="+isNew;
	window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:900px");
	//return false;
}
function popContact()
{
	return false;
}
//[0111] start
function searchLineTab(var_sortByColumn,var_pageName){
	
	document.getElementById("id_paging_goToPage").value="";
	pageNumber=1;
	DrawTable(var_sortByColumn,var_pageName);
}
//[0111] end