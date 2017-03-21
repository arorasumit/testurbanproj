//[002]	 Gunjan	    13-Jan-14	  	     Added JSONrpc call to checkCopiedNode for alert is not coming when order is validated without saving copied line item
// [003] VIPIN SAHARIA 29-Apr-2014 If condition added -- not to call validatePODetail(x) method if Role Name is PM or SED 
//[004]    Gunjan      5-Mar-14            for cancellation reason
//[005]	VIPIN SAHARIA 17-07-2014 Added logic to get charge details for Service - Sales charges validation for DC hardware products.
//[006]   Neha Maheshwari    added service segment    NORTH STAR ACCOUNT TAGGING
//[007]   Raveendra           20-01-2015               Partial service validation
//[008]  Gunjan Singla   9-Mar-15     20150225-R1-021111    LSI and Service No Search required in Lines Tab
//[009]	VIPIN SAHARIA 13-May-2015 20150403-R2-021204 Project Satyapan Validating mandatory nature of fields while saving Main/Contact/PO Tabs 
//[0010] Gunjan Singla  28-May-15   SR1712831    User unable to cancel Order pending at AM
//[0011] Raveendra 06-july-2015 20141219-R2-020936-Billing Efficiency Program_drop2  Auto Billing Trigger
//[0012] Priya Gupta    24-Jun-15 CBR_20141219-R2-020936-Billing Efficiency Program Drop 2 Added ldclause column
//[013] VIPIN SAHARIA 11th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Reverting MBIC CC mapping as per L3-VCS mapping
//[014] RAHUL TANDON 3-Sept-2015 CBR_20150603-R2-021385-Charge End Date and Short Billing : Alert on order validate if order has charges which have charges which can be re-disconnect

//[015] Pankaj Thakur  9-Sep-15     comment the attachWorkflowForSelectedProject()  and  merge the code in attachWorkflow.jsp
//[0016] Gunjan Singla  24-Nov-15    M2M: validations on validate order button
//[0018] Priya Gupta CSR-20160301-XX-022139 -Auto billing for RR orders & other Enhancements
//[0019] Priya Gupta CSR-2016XXXX-XX-000000  - Differential Linking Enable in Solution Change Orders
function getTip(value){	
	Tip("<Table border=0> "+ value +"   </table>");	
}

function getTip_DD(obj,value,objName){
	try{	
		var SelectedValue;
		var selIndex = value;
		var myForm=document.getElementById('searchForm');
		SelectedValue=document.getElementById(objName).options[selIndex].text;	
		var combo = document.getElementById(objName);
		combo.title = SelectedValue;
	}catch(e){
		alert('error code 601: '+ e.message);
	}
}

//[039] End
//Added by Ashutosh For SubChangeType
function selectOrderForChange(){
	try{
	    var path = contPath + "/ChangeOrderAction.do?method=fetchOrderForChange";		
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:600px");
	}catch(e){
		alert('error code 602: '+ e.message);
	}
}
function demoValidation(obj)
{
	
	try{
		var myForm=document.getElementById('searchForm');
		if(myForm.noOfDaysForDemo.value == 0)
		//if(document.forms[0].noOfDaysForDemo.value == 0)
		{
		    alert("No of days should be greater than 0");	    	
		    myForm.noOfDaysForDemo.value = "";
			myForm.noOfDaysForDemo.focus();	
			return false;
		} 
		else 
		{
			if(!checkdigits(obj))
			{
			  return false;
			}
		}
	}catch(e)
	{
		alert('error code 88: '+ e.message);
	}
}
function fnCheckContactAll(){
	try{
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
	}catch(e)
	{
		alert('error code 603: '+ e.message);
	}
}

function unCheckedSelectAllPO()
{
	try{
		if(!(document.forms[0].chkSelectPODetail))
		{
			document.getElementById("SelectAllPOChck").checked=false;
		}
	}catch(e)
	{
		alert('error code 604: '+ e.message);
	}
}

function unCheckedSelectAllContact()
{
	try{
		if(!(document.forms[0].chkSelectContactDetail))
		{
			document.getElementById("SelectAllChckContact").checked=false;
		}
	}catch(e)
	{
		alert('error code 605: '+ e.message);
	}
}
//[013] START
//Start : MBIC
/*function ccMBICMap(serviceId,serviceTypeId)
{
	try{
		var jsData = 
	    {
	    	serviceId:serviceId,
	    	orderNumber:document.getElementById('poNumber').value,
	    	serviceTypeId:serviceTypeId
	    };
     	var mbic_cc_serviceId_retun = jsonrpc.processes.selectCC_MBIC_Maping(jsData);
     	return mbic_cc_serviceId_retun;
	}catch(e)
	{
		alert('error code 606: '+ e.message);
	}
}*/
//END : MBIC
//Start : VCS
//[013] END
function justUnmappedVCSDeletionCheck(l3mplsServiceId,orderNo)
{
	try{	
     	var result = jsonrpc.processes.justUnmappedVCSDeletionCheck(l3mplsServiceId,orderNo);
     	return result;
    }catch(e)
	{
		alert('error code 607: '+ e.message);
	}
}
//End VCS

function fnDeleteService()
{	
	if(document.getElementById('deleteProductDiv').disabled==true){
		return false;
	}
	try{
    	//added for clep,it is mpp order so passing this functionality
    	if(clepState==1 || clepState==2 || clepState==3 || clepState==4){	
    		return false;
    	}
     	var myform=document.getElementById("searchForm");
     	var checkedCounter=1;
     	var newCounter=1;
     	var i;
     	var stage = document.getElementById('stageName').value;
     	
     	//[0010] condition "stage=="COPC" " added 
    	if(stage=="SED" || stage=="PM" || stage=="Billing trigger" || stage=="COPC" )
    	{
    		alert("You are not authorised to use this functionality");
    		return false;
    	}
    	var index_var=0;
		index_var=startRecordId;
    	var jsData1 =			
	    {
	    	startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			sortByOrder:sortByOrder,
			pageRecords:pageRecords,		
			poNumber:Number(document.getElementById('poNumber').value)
			
		};
	    var test1 = jsonrpc.processes.poulateSolutionChangeResults(jsData1);
           var errorMsg = "";
  		var count = 0;
		var serviceCount=test1.list.length;
		if(serviceCount==0)
		{
			alert("Please add atleast one service");
			return false;
		}
    	var chkLength=document.forms[0].chkService.length;
     	if(chkLength==undefined)
		{
			if(document.forms[0].chkService.checked==true)
			{
		    	checkedCounter++;
		      	if(document.getElementById('chk'+0).value=="Yes") 
				{
				 	alert("Service already published cannot be cancelled");
				   	return false;
				}
		    	if(document.getElementById('txtsubChangeTypeId'+0).value==0){
		    		alert("You Can't Delete the Service, Please Cancel It To Remove From The Order");
		    		return false;
		    	}
		   		else
		        {
			    	var jsData = 
			    	{
			    		serviceId:myform.chkService.value,
			    		orderNumber:document.getElementById('poNumber').value
			    	};
			     	var result = jsonrpc.processes.DeleteService(jsData);
		       		alert(result.msgOut);
		       		ViewServiceTree(5);
				}       
			}
		}
     	else
		{
			for(i=0;i<chkLength;i++,newCounter++)
			{
		     	if(myform.chkService[i].checked==true)
				{
			       	checkedCounter++;
					if(document.getElementById('chk'+i).value=="Yes") 
				    {
				    	alert("Service already published cannot be cancelled");
				    	return false;
				    }
			       	if(document.getElementById('txtsubChangeTypeId'+i).value==0)
			       	{
		              	alert("This is a current service You cant Delete It please cancel It");
		              	return false;
					}
		          	else
		          	{
		          	//[013] START
		          		//MBIC CC  and VCS L3MPLS Mapping validation on deletion in Change Order.
		          		/*if(document.getElementById('HdnChangeTypeID').value ==3 || document.getElementById('HdnChangeTypeID').value ==2)
		          		{
		          			var serviceTypeId=document.getElementById('txtServiceTypeID'+newCounter).value;
		          			//VCS L3MPLS Mapping validation on deletion
		          			if(document.getElementById('HdnChangeTypeID').value ==2 && serviceTypeId==141)//for L3 MPLS Service deletion Check
		          			{
		          				var results=justUnmappedVCSDeletionCheck(myform.chkService[i].value,document.getElementById('poNumber').value);
		          				if(results!='')
		          				{
		          					alert(''+results);
		          					return false;
		          				}
		          			}
				          	var mbic_serviceId='';
				          	var messageString='';
				          	//MBIC CC Mapping validation on Deletion 
				          	if(serviceTypeId==181 || serviceTypeId==413)
				          	{
				          		mbic_serviceId=ccMBICMap(myform.chkService[i].value,serviceTypeId);
				               	if(mbic_serviceId!='')
						        {
						        	if(serviceTypeId==181) 
						        		messageString=" MBIC Services No:"+mbic_serviceId+" is Also Get Deleted. Do You Want To Continue";
						        	else 
						        		messageString=" Do You Want To Also Delete Clear Channel Service No :"+mbic_serviceId;
					        		if(confirm(messageString))
					        		{
							        	var jsData2 = 
									    {
									    	serviceId:mbic_serviceId,
									    	orderNumber:document.getElementById('poNumber').value
									    };
								     	var result1 = jsonrpc.processes.DeleteService(jsData2);
								    }
								    else if(serviceTypeId==181)
								    {
								    	return false;
								    }
								    else if(serviceTypeId==413)
								    {
								    	if(!confirm("Do You Want To Continue To Delete MBIC Service"))
					        			{
					        				return false;
					        			}
								    }
						        }
				        	}
				        }*/ //[013] END	        	
					    var jsData = 
					    {
					    	serviceId:myform.chkService[i].value,
					    	orderNumber:document.getElementById('poNumber').value
					    };
				  		//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
						var result = jsonrpc.processes.DeleteService(jsData);
				       	alert(result.msgOut);
				       	ViewServiceTree(5);
					}
				}
			}
		}		
  		if(checkedCounter==1){
    		alert("Please Select a Service Type");
   		}
	}catch(e)
	{
		alert('error code 608: '+ e.message);
	}
}
//047 end

function cancelService()
{
	try{
		 var roleWiseSectionDetail = getRoleWiseSectionDetails(gb_roleID);
	     var commercialNotAllowed = checkRoleWiseCommercialNotAllowed(roleWiseSectionDetail);
	     if(commercialNotAllowed){
	    	var commercialLineToBeDeleted =  checkServiceTreeContainsCommercialLine("browser");
	    	if(commercialLineToBeDeleted){
	    		alert("You are not authorized to Cancel commercial line");
	    		return false;
	    	}
	     }
		//added for clep,it is mpp order so passing this functionality
		if(clepState==1 || clepState==2 || clepState==3 || clepState==4){	
			return false;
		}
		//Cancel Lookup services after validation and Maintain History for cancel services and display Cancel service on screen
		if(document.getElementById('HdnChangeTypeID').value ==1)
		{
			cancelLookUpServices();		
		}
		else
		{
			cancelAddedServices();
		}
	}catch(e)
	{
		alert('error code 609: '+ e.message);
	}	
}
//Created By Ashutosh For Cancel Lookup Sercvices as On Date 20-Oct-2011
function cancelLookUpServices()
{
	try{
		if(document.forms[0].chkService)
    	{	
    	    var stage = document.getElementById('stageName').value;
    	    if(stage=="Service Cancelled")
	    	{	   
	    	alert("Service already cancelled !!")
	    	}	
			var newCounter=1;
			var chkLength=document.forms[0].chkService.length;
			if(chkLength==undefined)
			{
				if(document.forms[0].chkService.checked==true)
				{					
					var serviceId = document.getElementById("txtServiceNo"+newCounter).value;
					//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");			
					var status = jsonrpc.processes.cancelLookUpServices(serviceId);	
					//var myform=document.getElementById("searchForm")
					if(status == '1')
					{
						alert('Service Cancelled Successfully');
						countService=1;
    					//drawTable();
						DrawTable('SERVICENO','SERVICELINETABLE');
					}else
					{
						alert("Some error Occur");
					}
				}else
				{
					alert("Please Select Service !!");
					return false;
				}
			}
			else
			{	var flag=0;
				for(i=0;i<chkLength;i++,newCounter++)
				{
					var myform=document.getElementById("searchForm")
					if(myform.chkService[i].checked==true)
					{
						if(confirm("Are you sure you want to delete this service"))
						{
							var serviceId = document.getElementById("txtServiceNo"+newCounter).value;
							//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");			
							var status = jsonrpc.processes.cancelLookUpServices(serviceId);	
							//var myform=document.getElementById("searchForm")
							if(status == '1')
							{
								alert('Service Cancelled Successfully');
								countService=1;
	    						//drawTable();
								DrawTable('SERVICENO','SERVICELINETABLE');
							}else
							{
								alert("Some error Occur");
							}
						}	
						flag=1;
					}
				}
				if(flag==0)
				{
					alert("Please Select Service !!");
					return false;
				}
			}
		}
		else
		{
			alert("Please Select a Service!!");
			return false;
		}
	}catch(e)
	{
		alert('error code 610: '+ e.message);
	}
}
//Created By Ashutosh For Cancel new Sercvices as On Date 20-Oct-2011
function cancelAddedServices()
{
	try{
 	var isCancelAndCopy=0;
 	var newCounter=1;
    var stage = document.getElementById('stageName').value;
    var myform=document.getElementById("searchForm");
    if(stage=="SED" || stage=="Billing trigger")
    {
    	alert("You are not authorised to use this functionality");
    	return false;
    }
  
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
		         //Added By Saurabh to restrict user from cancelling services which are already published
		         if(document.getElementById('chk'+0).value=="Yes") 
				 {
				 	alert("Service already published cannot be cancelled");
				   	return false;
				 }
				  //End by Saurabh
				
		         if(document.getElementById('txtsubChangeTypeId'+0).value!=0){
		        	 //[0010] message changed by Gunjan
				    //alert("This is a current service You cant cancel It please delete It");
		        	 alert("You are not authorised to use this functionality");
				    return false;
		       }
		
				 serviceNo=myform.chkService.value;
								 var result;
				if(confirm("Are you sure you want to delete this service"))
   				{
				   if(serviceNo!="")
				    {
				    	var path = contPath + "/NewOrderAction.do?method=serviceCancelReason&isCancelAndCopy="+isCancelAndCopy;
						window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
				       
				    }
				  else
				    {
				      serviceTab.deleteRow(1); 
				    }
				}      
			}
		}
		else
		{
			for(i=0;i<chkLength;i++,newCounter++)
			{
				
			    if(myform.chkService[i] != null && myform.chkService[i].checked == true)
				{
				    //Added By Saurabh to restrict user from cancelling services which are already published
				    if(document.getElementById('chk'+i).value=="Yes") 
				    {
				    	alert("Service already published cannot be cancelled");
				    	return false;
				    }
				    //End By Saurabh
				    
				     if(document.getElementById('txtsubChangeTypeId'+i).value!=0){
				     //[0010] message changed by Gunjan
		             // alert("You Can't Cancel The Service, Please Delete It To Remove From The Order");
				    	 alert("You are not authorised to use this functionality");
		              return false;
		               }
				     serviceNo=myform.chkService[i].value;
				   					var result;
				
					if(confirm("Are you sure you want to delete this service"))
   					{
					   if(serviceNo!="")
					    {
					     
					    	var path = contPath + "/NewOrderAction.do?method=serviceCancelReason&isCancelAndCopy="+isCancelAndCopy;
							window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
					    
					    }
					    else
					    { 
					      serviceTab.deleteRow((i+1)); 
					    }
					}
				}
			}
		}
	
	}
	else
	{
		alert("Please Select a Service!!");
		return false;
	}
	}catch(e)
	{
		alert('error code 611: '+ e.message);
	}
}





function CancellationOfService(reason,iscancelCopy)
{
	try{
//	var sessionid ='<%=request.getSession().getId() %>';
    serviceTab = document.getElementById('ServiceList');
    var myform=document.getElementById("searchForm");
    var newCounter=1;
    flag=5;
    if(document.forms[0].chkService){
		var chkLength=document.forms[0].chkService.length;
		if(chkLength==undefined){
			if(myform.chkService.checked==true){   
		         serviceNo=myform.chkService.value;
		         var result;
		         var jsData ={
					  serviceId:serviceNo,
					  //sessionid:sessionid,
					  cancelServiceReason:reason
					};
				    //jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
				    if(serviceNo!=""){
				       result = jsonrpc.processes.DeleteServiceDetails(jsData);
				       if(result==null){
				       		//start [023]					
							var myForm=document.getElementById('searchForm');				
							myForm.action=contPath + "/defaultAction.do?method=invalidsessoion";
							myForm.submit();
							alert("Session has been expired!!");		
							window.close();			
							//end [023]
				       }
				       serviceTab.deleteRow(1); 
				       
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
				    {
					  serviceId:serviceNo,
					 // sessionid:sessionid,
					  cancelServiceReason:reason
					};
				    //jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
				    if(serviceNo!=""){	
				      result = jsonrpc.processes.DeleteServiceDetails(jsData);
				       if(result==null)
				       {
				       		//start [023]					
							var myForm=document.getElementById('searchForm');				
							myForm.action=contPath + "/defaultAction.do?method=invalidsessoion";
							myForm.submit();
							alert("Session has been expired!!");		
							window.close();			
							//end [023]
				       }
				       serviceTab.deleteRow((i+1)); 
				       
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
				      serviceTab.deleteRow((i+1)); 
				    }
				}
			}
		}
		//rohit
		countService=1;
    	//drawTable();
		DrawTable('SERVICENO','SERVICELINETABLE');
	}
	else
	{
		alert("Please Select a Service!!");
		return false;
	}
	}catch(e)
	{
		alert('error code 612: '+ e.message);
	}
}
//047 end

function fnCheckPOAll()
{
	try{
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
		}catch(e)
	{
		alert('error code 613: '+ e.message);
	}
	
}
function fnCheckAll()
{
	try{
	    var i;
	    var checkedCounter=1;
        var myForm=document.getElementById('searchForm');		
	    var counter=1;
     	var jsData1 =              			
	   	{
			poNumber:document.getElementById('poNumber').value,
			sessionid:sessionid
		};
    	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
				}			
			}
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
		}else{
			if(chkLength1==undefined){
				if(document.forms[0].chk_spId.disabled==false){
		        	document.forms[0].chk_spId.checked=false;
		    	}
	  		}else{    
	         	for (i =0; i<chkLength1; i++){ 
			     	if(myForm.chk_spId[i].disabled==false){
					 	myForm.chk_spId[i].checked=false ;
					}       
				}
			}
		}
	}catch(e){
		alert('error code 614: '+ e.message);
	}
}

//[00044] Start
function getSubChangeType()
{
	try{   
    	 var i, j ;
     	var ChangeTypeList;
     	//alert("COMBO :"+document.getElementById("subChangeTypeId"))
     	var combo = document.getElementById("subChangeTypeId");
     	var iCountRows = combo.length;
	 	// alert("iCountRows :"+iCountRows)
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }	
	    //alert("HdnChangeTypeId :"+document.getElementById('changeTypeId').selectedIndex);
	 	var changeTypeId=Number(document.getElementById('changeTypeId').value);
	 	try{
	    //jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    ChangeTypeList = jsonrpc.processes.populateChangeType(changeTypeId);
	    }
	    catch(e)
	    {
	    	alert("exception     " + e);
	    }
	    for(j=0;j<ChangeTypeList.list.length;j++)
	    {
	    	
		   	var option = document.createElement("option");
		  		option.text = ChangeTypeList.list[j].subChangeTypeName;
		  		option.value = ChangeTypeList.list[j].subChangeTypeId;
		  		option.title = ChangeTypeList.list[j].subChangeTypeName;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	    
	    /*
		 * Vijay.
		 * Calling a method that set the default style/value of attribute
		 */
	    defaultAttributeValue();
	}catch(e)
	{
		alert('error code 615: '+ e.message);
	}
}
//[00044] End	
	function fetchFeildList(){
		try{
			var myForm=document.getElementById('searchForm')
			//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			//[001] Start
			//var changeTypeId = document.getElementById('HdnChangeTypeID').value;
			var changeTypeId;
			//[001] End
			//alert("changeTypeId : " + changeTypeId);
			// setting changeTypeId = 0 as thre proc now needs subChangeType 
			//which would be available on latter satge.
			changeTypeId = 0;
			roleFeild = jsonrpc.processes.getFieldValidationForChangeOrder(sessionid,changeTypeId);
			mainTabFieldList = roleFeild.list[0];
			contactTabFieldList = roleFeild.list[1];
			poDetailTabFieldList = roleFeild.list[2];
			linesTabFieldList = roleFeild.list[3];
			serviceSummaryList = roleFeild.list[4];
			checkServiceButton(linesTabFieldList);
			initFieldValidation();
			initButtonFieldValidation();
	}catch(e){
		alert('error code 616: '+ e.message);
	}
}
	//----[006]---Start
	function cancelOrder()
	{
		try{
		// var answer = confirm("Are you sure you want to cancel this order ?")
		
	// [090]
	
				var path = gb_path+"/ChangeOrderAction.do?method=orderCancelReason";
				window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:300px");
				//[004]
				//var cancelreason=document.getElementById('cancelReason').value;
		    	var answer=document.getElementById('answer').value;
		    // var poNumber=document.getElementById('poNumber').value;
		    	// [004]
		    	if(answer)
				{
			    var jsDataOrder =			
			    {
				  	orderNumber:document.getElementById('poNumber').value,
					cancelReason:document.getElementById('cancelReason').value,
					cancelReasonId:document.getElementById('cancelReasonDD').value
				};
			//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");			
			/*Vijay
			 *check order is belong to clep or not. If this order is clep order 
			 *then clep related cancel method is going to call because in this msg a 
			 * a milestone will send to clep.
			*/
			var order_creation_source = document.getElementById('hdnOrderCreationSourceId').value;	
			var status='';
			if(order_creation_source==ORDER_CREATION_SOURCE){
		
				var commit_flag='N';
				status = jsonrpc.processes.cancelOrderForClep(jsDataOrder,sessionId,commit_flag);	
			}
			else{
				var commit_flag='Y';
				status = jsonrpc.processes.cancelOrder(jsDataOrder,sessionId,commit_flag);		
			}
			//vijay end
			var myform=document.getElementById("searchForm")
			
			if(status == '1')
			{
				 alert('Order Cancelled Successfully');
				 myform.action=contPath + "/defaultDraftChangeOrdAction.do?method=viewIncompleteChangeOrder";
				 showLayer();
				 myform.submit();
			}
		}
		}catch(e)
	{
		alert('error code 617: '+ e.message);
	}
	}
	//----[006]---End
	
	
	function initButtonFieldValidation()
	{
		try{
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
			}catch(e)
			{
				alert('error code 618: '+ e.message);
			}
				
		
	}
	
	function checkServiceButton(linesTabFieldList)
	{
		try{
		for (iField = 0 ; iField <  linesTabFieldList.list.length; iField++)
		{
			if(linesTabFieldList.list[iField].feildName=="btnselectservice")
				btnselectservice = linesTabFieldList.list[iField].isReadOnly
			if(linesTabFieldList.list[iField].feildName=="btnAttributes")	
				btnAttributes = linesTabFieldList.list[iField].isReadOnly
			if(linesTabFieldList.list[iField].feildName=="btnAttLabelValues")	
				btnAttLabelValues = linesTabFieldList.list[iField].isReadOnly
		}
		}catch(e)
	{
		alert('error code 619: '+ e.message);
	}
	}
	
	function initFieldValidation()
	{
		try{
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
			//[078] start
			ctrlArry[7] =  myForm.AttributeVal_8;
			ctrlArry[8] =  myForm.AttributeVal_9;
			ctrlArry[9] =  myForm.AttributeVal_10;
			ctrlArry[10] =  myForm.AttributeVal_11;
			ctrlArry[11] =  myForm.AttributeVal_12;
			ctrlArry[12] =  myForm.AttributeVal_13;
			ctrlArry[13] =  myForm.AttributeVal_14;
			ctrlArry[14] =  myForm.AttributeVal_15;
			ctrlArry[15] =  myForm.AttributeVal_16;
			ctrlArry[16] =  myForm.AttributeVal_17;
			ctrlArry[17] =  myForm.AttributeVal_18;
			//[078] end
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
			//[0012]
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
			
		}catch(e)
		{
			alert('error code 620: '+ e.message);
		}		


	}	
	
	function fieldRoleMappingValidation(lstFieldList,ctrlArry)
	{
		try{
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
								//start [012]
								if(ctrl[ictrlName].isRequired=="0")
									ctrl[ictrlName].className = "inputBorder2";
								else
									ctrl[ictrlName].className = "inputBorder1";
									//end [012]
								ctrl[ictrlName].Disp = lstFieldList.list[iField].fieldLabel;
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
							//start [012]
							if(ctrlArry[iCtrl].isRequired=="0")
								ctrlArry[iCtrl].className = "inputBorder2";
							else
								ctrlArry[iCtrl].className = "inputBorder1";
							//end [012]
							ctrlArry[iCtrl].Disp = lstFieldList.list[iField].fieldLabel;
						}
					  }
					//Start[020]  
					
					
					if(document.getElementById('HdnChangeTypeID')!=null)
					{
						//Vijay add condition for demo-regularize order 
						if(document.getElementById('HdnChangeTypeID').value ==5  
							|| (document.getElementById('HdnChangeTypeID').value ==4 && document.getElementById("hdnSubchangeType").value==12))
						{
							var counter = document.getElementById('tablePO').rows.length;
							var i=0;
							for(i=1;i<=counter-1;i++)
							{
								document.getElementsByName('contractStartDate')[i-1].isRequired="1";
								document.getElementsByName('contractEndDate')[i-1].isRequired="1";
								document.getElementsByName('contractStartDate')[i-1].className="inputBorder1";
								document.getElementsByName('contractEndDate')[i-1].className="inputBorder1";
							}	
						}
					}
					//End[020] 						
				}	
			}
		}catch(e)
		{
			alert('error code 621: '+ e.message);
		}
	}
	function popTaskAction(id,ownerId) 
{
	try{
		var frm=document.getElementById('searchForm');
		frm.searchTaskId.value = id;
		frm.searchTaskOwnerId.value = ownerId;
		var path = contPath + "/viewOrderAction.do?methodName=TaskAction&orderNo="+orderNo+"&role="+gb_roleID;
	   	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
 	}catch(e)
	{
		alert('error code 622: '+ e.message);
	}
}


function popTaskActionChangeOrder(id,ownerId){
	try{
		var orderNo = document.getElementById('poNumber').value;
		var frm=document.getElementById('searchForm');
		frm.searchTaskId.value = id;
		frm.searchTaskOwnerId.value = ownerId;
		var path = contPath + "/viewOrderAction.do?methodName=ChangeOrderTaskAction&orderNo="+orderNo+"&role="+gb_roleID;
		//[00055] Start (Changed By Ashutosh)	
	   	window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px");
	 	//[00055] End
 	}catch(e)
	{
		alert('error code 623: '+ e.message);
	}
}


function submitParent()
{
	try{
		var myForm=document.getElementById('searchForm');
		var modeValue="editON";
		poNumber=document.getElementById('poNumber').value;
		myForm.action=contPath + "/ChangeOrderAction.do?method=incompleteOrder&orderNo="+poNumber+"&modeName="+modeValue;
		showLayer();
		myForm.submit();
	}catch(e)
	{
		alert('error code 624: '+ e.message);
	}
}

function deleteNote(searchtaskNotesId,taskId,status)
{
	try{
		if (confirm("Are you sure you want to delete this note")) 
		{
			var jsData =			
	    	{
				taskNoteId:searchtaskNotesId
			};
		    //jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		    var retVal = jsonrpc.processes.DeleteNotes(jsData);
		    alert(retVal.msgOut);
		    showNotes(taskId,unescape(status));
	    }
    }catch(e)
	{
		alert('error code 625: '+ e.message);
	}
}

function showNotes(taskId,status)
{
	try{
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
    	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	var retVal = jsonrpc.processes.ViewNotes(jsData,sessionId);
    	for (i = 0 ; i < retVal.list.length; i++)
    	{
			var str;
			var tblRow=document.getElementById('tabNotes').insertRow();
				
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.title=retVal.list[i].notesType
			str=retVal.list[i].notesType
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.title=retVal.list[i].notesMeaning
			str=retVal.list[i].notesMeaning
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.title=retVal.list[i].createdBy
			str=retVal.list[i].createdBy
			CellContents = str;
			tblcol.innerHTML = CellContents;
	
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.title=retVal.list[i].createdDate
			str=retVal.list[i].createdDate
			CellContents = str;
			tblcol.innerHTML = CellContents;
	
		
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str="<div class='deleteBg1'><a href='#' onclick=deleteNote("+ retVal.list[i].taskNoteId +","+ retVal.list[i].taskID +",'"+ escape(status) +"')>...</a></div>";
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
	}catch(e)
	{
		alert('error code 626: '+ e.message);
	}
}

function popTaskStatus(id,ownername) 
{
	try{
		var frm=document.getElementById('searchForm');
		frm.searchTaskId.value = id;
		frm.searchTaskOwner.value = ownername;
		
		var path = contPath + "/viewOrderAction.do?methodName=fetchNotes&taskId="+id;
	   	window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:350px");
	 	//window.open(path);
 	}catch(e)
	{
		alert('error code 627: '+ e.message);
	}
}
function goToHome()
{
	try{
		var myForm=document.getElementById('searchForm');
		myForm.action=contPath + "/defaultAction.do?method=goToHomeAfterClosing";
		showLayer();
		myForm.submit();
	}catch(e)
	{
		alert('error code 628: '+ e.message);
	}
}

//--[084]--start--//
function selectCurrentTab(currentTab){
	try{
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
	}catch(e)
	{
		alert('error code 629: '+ e.message);
	}
}
//--[084]--end--//

//--[084]--start--//
function changeTab(currentTab,tabVal,view1,view2,view3,view4,view5)
{
	try{
		fetchFeildList();	
	selectCurrentTab(currentTab);
	//--[084]--end--//
	if(tabVal==3)
	{
		enableDisablePOCheckbox();
	}
	
	document.getElementById('hdnTabVal').value=tabVal;
	document.getElementById('Page_1').style.display=view1;
	document.getElementById('Page_2').style.display=view2;
	document.getElementById('Page_3').style.display=view3;
	document.getElementById('Page_5').style.display=view4;
	
	if(document.getElementById('hdnTabVal').value=="5" ){
		DrawTable('SERVICENO','SERVICELINETABLE');
		document.getElementById('saveIcon').style.display="none";
	}else if(document.getElementById('hdnTabVal').value=="6")
		document.getElementById('saveIcon').style.display="none";
	else{
		if(OrderNoBilling==OrderNoTM6){
			document.getElementById('saveIcon').style.display="none";
		}else{
			//added for clep states in change tab to hide save button
			if(clepState !=1 && clepState != 2 && clepState !=3 && clepState !=4){
				document.getElementById('saveIcon').style.display="block";
			}
		}
	}

	//Commented below condition due to production Issue of not Displaying Approval Tab After Rejection
	//if(modeValue=="editON")
	//{
		document.getElementById('Page_6').style.display = view5;
	//}
	//else
	//{		
		//document.getElementById('Page_6').style.display="none";	
	//}
	//End By Saurabh
	if(tabVal==1){
		if(modeValue=="editON"){
			document.getElementById('divSelectCurrencyId').style.display='none';
		}else{
			document.getElementById('divSelectCurrencyId').style.display='block';
		}
	}
	else{
		document.getElementById('divSelectCurrencyId').style.display='none';
	}
	}catch(e){
		alert('error code 630: '+ e.message);
	}
}

function popService(counter) 
{
	try{
	poNumber=document.getElementById('poNumber').value;
	var path = contPath + "/selectservice.do?method=fetchServiceType&hdnServiceCounter="+ counter +"&orderNo="+poNumber+"";
   	window.showModalDialog(path,window,"status:false;dialogWidth:650px;dialogHeight:650px");
   	}catch(e)
	{
		alert('error code 631: '+ e.message);
	}
}

function validateSource()
{
	try{
		var sName=document.getElementById('sourceName').value;
		if(sName == "CRMORDER")
		{
			document.searchForm.txtquotesNo.value="";		
			document.searchForm.quoteNo.readOnly=true;
			document.getElementById('txtQuoteNo').style.display='block';  
			document.getElementById('cboQuoteNo').style.display='none';			
		}
		if(sName == "CRMQUOTE")
		{	
			document.searchForm.quoteNo.readOnly=false;		
			document.getElementById('txtQuoteNo').style.display='none';  
			document.getElementById('cboQuoteNo').style.display='block';			
		}
		if(sName == "0")
		{	
			document.searchForm.txtquotesNo.value="";		
			document.searchForm.quoteNo.readOnly=true;		
			document.getElementById('txtQuoteNo').style.display='block';  
			document.getElementById('cboQuoteNo').style.display='none';			
		}
		}catch(e)
	{
		alert('error code 632: '+ e.message);
	}
}

function popitup(url) 
{
	try{
		var poNumber=document.getElementById('poNumber').value;	    	
	    var stage = document.getElementById("stageName").value;
	if(url=="closeorder")
	{		
		var myForm=document.getElementById('searchForm');
		myForm.action=contPath + "/defaultAction.do?method=goToHomeAfterClosing";
		showLayer();
		myForm.submit();
	}

	if(url=="SelectAccount"){
		/*var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=fetchAccount";		
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:520px");
		fnGetQuoteNo();
		fnGetOpportunityId();*/
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
    	$("#selectAccountDialog").load(contPath + "/ChangeOrderAction.do?method=fetchAccount");
		$('#selectAccountDialog').dialog('open');
		return;
	}
	if(url=="SelectCurrency")
	{
		var path = contPath + "/ChangeOrderAction.do?method=fetchCurrency";		
		window.showModalDialog(path,window,"status:false;dialogWidth:750px;dialogHeight:450px");
	}	
	//start[088]
	
	//pankaj channel partner
	
	if(url=="SelectChannelPartner"){
		var path = gb_path+"/ChangeOrderAction.do?method=fetchChannelPartner";		
		window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:500px");		
			//document.getElementById('txtQuoteNo').style.display='none';  
			//document.getElementById('cboQuoteNo').style.display='block';			
		
		return;
	}
	
	
	//end 
	
	if(url=="SelectOpportunity")
	{
	
	
		
		var orderNo = 	document.getElementById('poNumber').value;
		var accountId = document.getElementById('accountID').value;	
		var stageName =	document.getElementById('stageName').value;	
			
		var path = contPath + "/ChangeOrderAction.do?method=fetchOpportunity&orderNo="+orderNo+"&accountID="+accountId+"&stageName="+stageName;
		
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:720px");
	
	}
	//end [088]	
	//start [005]
	if(url=="FileAttachment")
	{
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
 				params += ', scrollbars=no';
 				params += ', status=no';
 				params += ', toolbar=no';
	    	
	    	var accountNumber=document.getElementById('accNo').value;
	    	var orderNumber=document.getElementById('poNumber').value;
			var path = contPath + "/NewOrderAction.do?method=goToFileAttachmentPage&orderNo="+orderNumber+"&accountNo="+accountNumber+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod;	
			window.open(path,"FileAttachmentWindow",params); 						
		}
	}
	
	//lawkush Start
	
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
	    	
			var path = contPath + "/NewOrderAction.do?method=goToDownloadedFilePage&orderNo="+orderNumber+"&accountNo="+accountNumber+"&crmAccountNo="+crmAccountNo+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod;	
			window.open(path,"FileDownloadWindow",params); 						
		}
	}
	
	
	//lawkush End
	
	//end [005]
	//if (window.focus) {window.focus()}
	//return false;
	
		if(url=="selectGAM")
			{	
				//lawkush Changed for disabling GAM icon and no user function on onClick when  main tab is not saved
				document.getElementById('hdnShowAttachIcon').value=showAttachIcon;	
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
				var path = contPath + "/NewOrderAction.do?method=goToViewGAM&orderNo="+orderNumber+"&accountNo="+accountNumber+"&quoteNo="+quoteNo+"&isInView="+isInView+"&stage="+stage;									
			  	window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:550px"); 	    
	
			}
			
			if(url=="approvalHistory")
				{
					var path = contPath + "/NewOrderAction.do?method=fetchApprovalOrderHistory&poNumber="+ poNumber +"&stage="+stage+"";		
					window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1500px"); 	    
				}	
	
	}catch(e)
	{
		alert('error code 633: '+ e.message);
	}
}
//below popup method is used for Copying Contact details of previous 10 orders of same account
function popContact()
{
	try{
	var accountNumber=document.getElementById('crmAccountNo').value;
	var poNumber=document.getElementById('poNumber').value;	
	    
	var path = contPath + "/NewOrderAction.do?method=fetchContact&poNumber="+ poNumber +"&accountNumber="+accountNumber;		
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
	}catch(e)
	{
		alert('error code 634: '+ e.message);
	}
}

function cellValidation(abc)
{	
	try{
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
	}catch(e)
	{
		alert('error code 635: '+ e.message);
	}
}

function popContactType(count) 
{
	try{
		var path = contPath + "/ChangeOrderAction.do?method=fetchContactType&counter="+count;		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}catch(e)
	{
		alert('error code 636: '+ e.message);
	}
}
 function popSalutationList(count) 
{
	try{
		var path = contPath + "/ChangeOrderAction.do?method=fetchSalutationList&counter="+count;			
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}catch(e)
	{
		alert('error code 637: '+ e.message);
	}
 }
 function popCountryList(count) 
{  	
	try{	
	var stateCode=document.getElementById("stateCode"+count).value;
	if(stateCode=="")
   	{
    	alert("Please insert state first!!");
    	return false;     
   	}
	//var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=fetchCountryList&counter="+count+"&stateCode="+stateCode;		
	var path = contPath + "/NewOrderAction.do?method=fetchCountryList&counter="+count+"&stateCode="+stateCode;
	window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:500px");
	}catch(e)
	{
		alert('error code 638: '+ e.message);
	}
 }
//below method changed from document.getElementById to both byId and by Form to handle single row if previous rows are deleted
function autoFill(count)
{
	try{
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
		//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
	
			return true;		 		
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
		 		{//alert('popCityList');
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
			//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
  }catch(e)
	{
		alert('error code 639: '+ e.message);
	}
}
 
 
 function popStateList(count) 
{ 
	try{  	
   //var countyCode=document.getElementById("countyCode").value;
  var cityCode=document.getElementById("cityCode"+count).value;   
   if(cityCode=="")
   {
    alert("Please insert city first!!");
    return false;
     
   }
	//var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=fetchStateList&counter="+count+"&cityCode="+cityCode;	
	var path = contPath + "/NewOrderAction.do?method=fetchStateList&counter="+count+"&cityCode="+cityCode;
	window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:500px");
	}catch(e)
	{
		alert('error code 640: '+ e.message);
	}
 }
function popCityList(count) 
{
  try{
		var path = contPath + "/NewOrderAction.do?method=fetchCityList&counter="+count;		
		window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:500px");
	}catch(e)
	{
		alert('error code 641: '+ e.message);
	}
}
function popEntitySelection(count) 
{
	try{
	var path = contPath + "/NewOrderAction.do?method=getEntityList&counter="+count;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}catch(e)
	{
		alert('error code 642: '+ e.message);
	}
 }
 //Start[016]
 // start [012]
function DateValidation(rfsdate ,feildName)
   {
   	try{
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
	   							document.forms[0].AttributeVal_1.focus();//[038]        05-June-12
	   							return false
	   						}
	  					else if(document.forms[0].AttributeVal_7.value=="")
	   						{
	   						alert("Please enter Date CAF Filled By Customer!!!");
	   						document.forms[0].AttributeVal_7.focus();//[038]        05-June-12
	   						return false
	   						}
	 					 }
				}
	 			else
				 {
	 				return true;
	 		     } 
         }	
         }catch(e)
	{
		alert('error code 643: '+ e.message);
	}	
			
  }	
  //end[012]
  //End[016]
  //[015] Start
  
  function popPinList(count) 
{
  try{
  var cityCode=document.getElementById("cityCode"+count).value;
  var stateCode=document.getElementById("stateCode"+count).value;
   
 if(cityCode=="")
   {
    alert("Please Insert City First!!");
    return false;
     
   }
if(stateCode=="")
   {
    alert("Please insert State!!");
    return false;
     
   }
   var path = contPath + "/NewOrderAction.do?method=fetchPinList&counter="+count+"&cityCode="+cityCode+"&stateCode="+stateCode;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}catch(e)
	{
		alert('error code 644: '+ e.message);
	}
 }
 //[015] End
function validate()
{
	var	newFormOpened;
	
	//[001] Start
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
	//[001] End	
	
	
	
   try{
	//	[088]	Start
	if(document.getElementById("subChangeTypeName")){
	
		var stage = document.getElementById("stageName").value;	
		var orderNoValid = document.getElementById("poNumber").value;
		var subChangeTypeName = document.getElementById("subChangeTypeName").value;	
			     
			
			
		if(stage == 'New' && orderNoValid != 0 && orderNoValid!= ''){
			if(subChangeTypeName == 'Upgrade-Solution Change' || subChangeTypeName == 'Shifting-Solution Change'){
				var segment = document.getElementById("lob").value;
				var sourceName = document.getElementById("sourceName").value;		
				if(segment == 'AES Corporate' && sourceName == 'CRMORDER'){
					alert('Please Select Opportunity.');
					return false;
				}
			}
		}	
	}
	//	[088]	End
	if(document.getElementById('hdnTabVal').value==1)
	{
		//[004]	Start
		//if((document.getElementById('regionID').value=="0") )
		//{
			//alert("Please Select  Region!!");
			//return false;
		//}
		//if((document.getElementById('zoneID').value=="0") )
		//{
			//alert("Please Select  Zone!!");
			//return false;
		//}
		//[004] End
		//alert(document.getElementById('changeTypeId').value);
	//Start[016]	
	
	if((document.getElementById('accountID').value=="") || (document.getElementById('accountName').value=="") || (document.getElementById('accountManager').value=="") || (document.getElementById('projectManager').value=="") || (document.getElementById('lob').value==""))
		{
			alert("Please Select An Account No!!");
			document.getElementById('crmAccNo').focus();//[038]        05-June-12
			return false;
		}
	
		if(document.getElementById('projectManager').value=='0') 
		{
			alert("Please Select Project Manager Name!!");
			document.getElementById('projectManager').focus();
			return false;
		}
		
		//End[016]
		//[090] Start
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
	//[090] End	
		
		
		/*if(document.getElementById('shortCode').value == "null" || trim(document.getElementById('shortCode').value) ==""){	     	
			alert("Short code not present with this Account!!");			
			return false;
		}		
		*/
		
		if(document.getElementById('orderType').value=="") 
		{
			alert("Please Input Order Type!!");
			document.getElementById('orderType').focus();//[038]        05-June-12
			return false;
		}
		
		if(document.getElementById('orderDate').value=="") 
		{
			alert("Please Input Order Date!!");
			document.getElementById('orderDate').focus();//[038]        05-June-12
			return false;
		}
		
		if(document.getElementById('sourceName').value==0) 
		{
			alert("Please Select Source!!");
			document.getElementById('sourceName').focus();//[038]        05-June-12
			return false;
		}
		
		if(document.getElementById('curShortCode').value=="") 
		{
			alert("Please select Currency!!");
			document.getElementById('curShortCode').focus();//[038]         05-06-12
			return false;
		}
		else
			{
				var flag = (document.getElementById('curShortCode').value).search('%');
				if(flag != -1)	
					{
					 	alert("Please enter Right Currency") ;
						return false;
					}
				else
				  {	
						   if(autoFillCurrency()==false)
								{
									return false;
								}
				  }
			}	
		
		/*if(document.getElementById('opportunityId').value=="0")
		{				
			alert("Please select Opportunity Id!!");
			document.getElementById('opportunityId').focus();
			return false;
		}*/
		
		//document.getElementById('txtquotesNo').value ="0";
		if(document.getElementById('sourceName').value=="CRMORDER")
		{
			//if(document.getElementById('txtquotesNo').value=="") 
			//{
				//alert("Please Input Quote Number!!");
				//return false;
			//}
		}
		else
		{
		//Start[016]			
			if(document.getElementById('quoteNo').value=="0")
			{				
				alert("Please Select QuoteNo!!");
				document.getElementById('quoteNo').focus();
				return false;
			}
		}
		
		if(document.getElementById('zone').value == "")
		{
		alert("Please select zone");
		document.getElementById('zone').focus();
		return false;
		}
		
		if(document.getElementById('changeTypeID').value == "")
		{
		alert("Please enter change type");
		document.getElementById('changeTypeID').focus();
		return false;
		}		
		if(document.getElementById('subChangeTypeId').value == 0)
		{
		alert("Please enter sub change type");
		document.getElementById('subChangeTypeId').focus();
		return false;
		}
		if(document.getElementById('isUrgent').value=='on')
		{
		document.searchForm.isUrgent.value=1;
		}
		else
		{
		document.searchForm.isUrgent.value=1;
		}
		var demotype = document.getElementById('isDemoOrder').value;
		var noOfDaysForDemo = document.getElementById('noOfDaysForDemo');
		if(demotype=='ON'){
			
			if(document.getElementById('changeTypeID').value!=3 && document.getElementById('subChangeTypeId').value!=12)
				{
					if(noOfDaysForDemo.value==""){
						alert("Please Input No of Days for Demo Order");
						noOfDaysForDemo.focus();
						return false;
					}
					if(noOfDaysForDemo.value == 0)
					{
					    alert("No of days should be greater than 0");	    	
					    noOfDaysForDemo.value = "";
						noOfDaysForDemo.focus();	
						return false;
					} 
				}	
			}
		if(document.forms[0].noOfDaysForDemo.value !="")
		{
			if(!checkdigits(document.forms[0].noOfDaysForDemo))
			{
			  return false;
			}
		}
		var countAttributes=document.getElementById('attCount').value;
		
		for(i=1;i<=countAttributes;i++)
		{
		
		    if(document.getElementById('hdnAttributeExpectedValue_'+i).value=="datetime")
			{
					if(DateValidation(document.getElementById('AttributeVal_'+i).value , document.getElementById('hdnAttributeName_'+i).value)==false)
					{
					return false;
					}
					//[080] Start
					/*if("document.forms[0].AttributeVal_"+i+".value"=="document.forms[0].AttributeVal_1.value") 
			  		{
						if(dateCompare(document.forms[0].AttributeVal_1.value,currentDate)=="-1")
						{			
							alert("PO RFS Date should not be Previous Date!!!");
						} 
		  			}*/
		  			//[080] End
					
			}
			// Start[012]
			document.getElementById('IsRequired_'+i).value=document.getElementById('AttributeVal_'+i).isRequired;
			// End[012]
			if(document.getElementById('hdnAttributeExpectedValue_'+i).value=="YN")
				{
				if(document.getElementById('AttributeVal_'+i).value=="" && 	(document.getElementById('IsRequired_'+i).value="1"))
					{
					alert("Please Input Extended Attributes Details!!");
					document.getElementById('AttributeVal_'+i).focus();
					return false;
					
					}
				}
			
			if(document.getElementById('hdnAttributeExpectedValue_'+i).value=="NUMERIC")
				{
				if(document.getElementById('AttributeVal_'+i).value=="" && 	(document.getElementById('IsRequired_'+i).value=="1"))
					{
					alert("Please Input Phone no!!");
					document.getElementById('AttributeVal_'+i).focus();
					return false;
					
					}
				}
				
			if(document.getElementById('hdnAttributeExpectedValue_'+i).value=="email")
				{
				if(document.getElementById('AttributeVal_'+i).value=="" && 	(document.getElementById('IsRequired_'+i).value=="1"))
					{
					alert("Please Input Email id !!");
					document.getElementById('AttributeVal_'+i).focus();
					return false;
					
					}
				}
			// End[016]
			/*if(document.getElementById('Mandatory_'+i).value=="Y")
			{ 
				if(document.getElementById('AttributeVal_'+i).value=="")
				{
					alert("Please Input Extended Attributes Details!!");
					document.getElementById('AttributeVal_'+i).focus();
					return false;
				}
			}*/
			
			if(document.getElementById('hdnAttributeExpectedValue_'+i).value=="VARCHAR" || document.getElementById('hdnAttributeExpectedValue_'+i).value=="varchar"){
			
				if(document.getElementById('AttributeVal_'+i).value!="")
				{
					if(ValidateTextField(document.getElementById('AttributeVal_'+i) ,path,'')==false){
						document.getElementById('AttributeVal_'+i).focus();
						return false;
					}
				}
			}		
			
			if(document.getElementById('hdnAttributeExpectedValue_'+i).value=="NUMERIC" ||document.getElementById('hdnAttributeExpectedValue_'+i).value=="numeric" ){
				if(document.getElementById('AttributeVal_'+i).value!="")
				{
					if(checkdigits(document.getElementById('AttributeVal_'+i))==false){	
						document.getElementById('AttributeVal_'+i).focus();
						return false;
					}
				}
			}	
		}
		//[009] Start PROJECT SATYAPAN
		var status = ispTagValidation();
		if(status != true)
			return false;
		//[009] End PROJECT SATYAPAN
		
		//pankaj
		var status = channelPartnerTagValidation();
		if(status != true)
			return false;
		
		//pankaj channel partner
		
		//[00044] start
		var subChangeTypeId=document.getElementById('subChangeTypeId').value;		
		//var subChangeTypeId=6;
		//[00044] End
		 flag=2;
		var myForm=document.getElementById('searchForm');
		myForm.action=contPath + "/ChangeOrderAction.do?method=insertUpdateMain&modeName="+modeValue+"&subChangeTypeId="+subChangeTypeId+"&flag="+flag+"&newFormOpened=Contact";
		showLayer();
		myForm.submit();
		document.getElementById('attachIcon').style.display="block";
		document.getElementById('saveIcon').style.display="block";
	}
	else if(document.getElementById('hdnTabVal').value==3)
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
	    	var chkLength = myForm.chkSelectPODetail.length;
				    	
	    	if(chkLength==undefined)
	    	{
	    		if(myForm.chkSelectPODetail.checked==true)
	    		{
	    		   if(poTable.rows[1].style.display=='none')
	    	           {
		     		poTable.deleteRow(1); 
		     	   }	
		     	}	
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
			
			
	    	if(poTable.rows.length==1)
	    	{
	    		alert('Atleast 1 row should be added in PO Detail')
	    		return false;
	    	}
	    	//[009] Start PROJECT SATYAPAN
	    	var status = ispTagValidation();
	    	if(status != true)
	    		return false;
	    	//[009] End PROJECT SATYAPAN
	    	
	    	//pankaj
			var status = channelPartnerTagValidation();
			if(status != true)
				return false;
			
			//pankaj channel partner
	    	
	    var val = validatePoDetails();
	    if (val == true)
	    {
	        flag=5;
	    	var poNumber=document.getElementById('poNumber').value;
	    	myForm.action=contPath + "/ChangeOrderAction.do?method=insertUpdatePODetails&modeName="+modeValue+"&orderNo="+poNumber+"&flag="+flag+"&newFormOpened=Product Catelog";
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
	else if(document.getElementById('hdnTabVal').value==2)
	{
	
		var countContact=document.getElementById('hdnContactCount').value;
		var countAddress=document.getElementById('hdnAddressCount').value;
		if(countContact != countAddress )
		{
		  alert("Contact And Address Rows Should be Equal");
		  return false;
		}
		
	        var myForm=document.getElementById('searchForm');
	    	var contactTable = document.getElementById('tableContact');
	    	var addressTable = document.getElementById('tableAddress');
	    	var rowlen=contactTable.rows.length;
	    	var rowCounter = 1;
	    	if(rowlen==1)
	    	{
	    	
	    	alert("please enter one row");
	    	
	    	}
	    	
	    	else
	    	{
		    	for (i = 1 ; i <= contactTable.rows.length - 1; i++,rowCounter++)
		        {
		          if(contactTable.rows.length==1)
		          {
			     	  if(contactTable.rows[1].style.display=='none')
			     	  {
			     	  	
			     	  	contactTable.deleteRow(1); 
			     	  	addressTable.deleteRow(1);
			     	  	rowCounter=0;
			     	  	i=-1;
			     	  }
		          }
		          else
		          {	
			     	  if(contactTable.rows[rowCounter].style.display=='none')
			     	  {
			     	    
			     	  	contactTable.deleteRow(rowCounter); 
			     	  	addressTable.deleteRow(rowCounter); 
			     	  	i=0;
			     	  	rowCounter=0;
			     	  }
		     	  }	
		     	}
	     	}
	     	if(contactTable.rows.length ==1)
	     	{
	     	alert('Please first add contact & address details !!');
	     	 return false;
	     	}
	   counter = parseInt(document.getElementById('hdnContactCount').value);
	   var i;
	 //[009] Start PROJECT SATYAPAN
		var status = ispTagValidation();
		if(status != true)
			return false;
		//[009] End PROJECT SATYAPAN
		
		//pankaj
		var status = channelPartnerTagValidation();
		if(status != true)
			return false;
		
		//pankaj channel partner

	 var val = validateContactDetails();
	    if (val == true)
	    {
	         flag=3;
	    	var poNumber=document.getElementById('poNumber').value;
	    	myForm.action=contPath + "/ChangeOrderAction.do?method=insertContactTabDetail&modeName="+modeValue+"&orderNo="+poNumber+"&flag="+flag+"&newFormOpened=PO Details";
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
	
	}catch(e)
	{
		alert('error code 645: '+ e.message);
	}
}

function AddPO() 
{
	try{
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
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' isRequired='1'  name='custPoDetailNo' maxlength='50' id='custPoDetailNo"+counter+"'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	// [003]	Start 
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' class='inputBorder1' showcal='date' name='custPoDate' isRequired='1' id='custPoDate"+counter+"' value='"+ currentDate +"' readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};' >&nbsp;<font size='1'><a href=\"javascript:show_calendar(searchForm[0].custPoDate"+ counter +");\" class=\"borderTabCal\"><img src='" + contPath + "/images/cal.gif' border='0px' alt='Loading...'></a></font>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	// [001]	End 
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col2";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' size='12' readonly='true' class='inputDisabled' name='poDetailNo' id='poDetailNo"+counter+"'>";
	str =str +"<input type='hidden' name='poDate' isRequired='0' id='poDate"+counter+"' value='"+ currentDate +"'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	/*var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' class='inputBorder1' showcal='date'  name='poDate' isRequired='0' id='poDate"+counter+"' value='"+ currentDate +"' readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};' >&nbsp;<font size='1'><a href=\"javascript:show_calendar(searchForm[0].poDate"+ counter +");\" class=\"borderTabCal\"><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...'></a></font>";
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
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' class='inputBorder2' isRequired='0' showcal='date' name='contractStartDate' id='contractStartDate"+counter+"' readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};'>&nbsp;<font size='1'><a href=\"javascript:show_calendar(searchForm[0].contractStartDate"+ counter +");\" class=\"borderTabCal\"><img src='" + contPath + "/images/cal.gif' border='0px' alt='Select Date'></a></font>";
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
	str ="<input onmouseover='getTip(value)' showcal='date' onmouseout='UnTip()' type='text' class='inputBorder1' style='width:90px;'  isRequired='1' name='poReceiveDate' id='poReceiveDate"+counter+"' readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};' >&nbsp;<font size='1'><a href=\"javascript:show_calendar(searchForm[0].poReceiveDate"+ counter +");\" class=\"borderTabCal\"><img src='" + contPath + "/images/cal.gif' border='0px' alt='Loading...'></a></font>";
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
	
	//[0012] starts
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="inner col4";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1' isRequired='1'  name='ldClause' maxlength='200' id='ldClause"+counter+"' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'LdClause')};\">";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//[0012] end
	
		//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
		var lstEntity = jsonrpc.processes.getEntity1();
		var tr, td, i, j, oneRecord;  		   			
	    for(j=0;j<lstEntity.list.length;j++)
    	{        	
    		var combo = document.getElementById('entityId'+counter);	    			
	    	var opt = document.createElement("option");
	   		opt.text = lstEntity.list[j].entityName;
			opt.value = lstEntity.list[j].entityId;
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
		//[0012]
		ctrlArry[14] =  myForm.ldClause;
	
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
		//[0012]
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
	}catch(e)
	{
		alert('error code 646: '+ e.message);
	}
}

function validatePoDetails()
 {
 	try{
	var i;
	var count = 1;
	var tabData = document.getElementById('tablePO');
	
 	for(i=1; i <= tabData.rows.length - 1; i++)
 	{	
 			if(document.getElementsByName('custPoDetailNo')[i-1].value=="" && document.getElementsByName('custPoDetailNo')[i-1].isRequired=="1")
 			{
 				alert("Please enter Customer PO Detail No");
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
 		//Start[019]
 				if(document.getElementsByName('entityId')[i-1].value=="0")
 			{
 				alert('Please Select a Valid Legal Entity');
 				document.getElementsByName('entityId')[i-1].focus();
 				return false;
 				break;
 			}
 			//End[019]
 			//<!-- [003]	Start -->
 			if(document.getElementsByName('custPoDate')[i-1].value=="" && document.getElementsByName('custPoDate')[i-1].isRequired=="1")
 			{
 				alert("Please Enter Customer PO Date");
 				document.getElementsByName('custPoDate')[i-1].focus();
 				return false;
 				break;
 			}
 			//<!-- [003]	End -->
			//[038]   Start     05-June-12
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
			//[038]   End     05-June-12
 			/*if(document.getElementsByName('totalPoAmt')[i-1].value=="0" && document.getElementsByName('totalPoAmt')[i-1].isRequired=="1")
 			{
 				alert(document.getElementsByName('totalPoAmt')[i-1].Disp + 'Cannot be 0');
 				document.getElementsByName('totalPoAmt')[i-1].focus();
 				return false;
 				break;
 			}
			*/
 			
//lawkush
 			//Start[016]
 			if(document.getElementsByName('poIssueBy')[i-1].value=="" && document.getElementsByName('poIssueBy')[i-1].isRequired=="1")
 			{
 				alert("Please Enter PO Issued By");
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
 				alert("Please Enter PO Remarks");
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
 				alert("Please Enter PO Email Id");
 				document.getElementsByName('poEmailId')[i-1].focus();
 				return false;
 				break;
 			}
 			if(document.getElementsByName('poDemoContractPeriod')[i-1].value=="" && document.getElementsByName('poDemoContractPeriod')[i-1].isRequired=="1")
 			{
 				alert("Please Enter PO Demo Contract Period");
 				document.getElementsByName('poDemoContractPeriod')[i-1].focus();
 				return false;
 				break;
 			}
 			//End[016]
 			//lawkush
 			
			// End[012]
			var valFrom=document.getElementsByName('contractStartDate')[i-1].value;
			
			if(valFrom != ""){
				fnCalculateEndDate(i);
			}
			var valTo=document.getElementsByName('contractEndDate')[i-1].value;
			var podate = document.getElementsByName('poDate')[i-1].value;
			//<!-- [003]	Start -->
			var custPoDate = document.getElementsByName('custPoDate')[i-1].value;
			//<!-- [003]	End -->
			var poRecieveDate=document.getElementsByName('poReceiveDate')[i-1].value;
			
			
			if(dateCompare(podate,currentDate)==1)
			{			
				alert("PO Date Cannot be greater than Current Date");
				return false;
			}
			//<!-- [003]	Start -->
			if(dateCompare(currentDate,custPoDate)==1)
			{			
				//alert("Customer PO Date Cannot be less than Current Date");
				//return false;
			}
			//Start[016]
			if(dateCompare(poRecieveDate,currentDate)==1)
			{			
				alert("Customer PO Date Cannot be greater than Current Date");
				return false;
			}
			if(document.getElementsByName('poReceiveDate')[i-1].value!="")
			{
				if(dateCompare(custPoDate,poRecieveDate)==1)
				{			
					alert("PO Receive Date can't be less than Customer PO Date!!!");
					return false;
				} 
				
			}
			//End[016]
			//<!-- [003]	End -->
			
			// [0012] starts
 			if(document.getElementsByName('ldClause')[i-1].value=="" && document.getElementsByName('ldClause')[i-1].isRequired=="1")
 			{
 				
 				alert("Please enter Ld Clause");
 				document.getElementsByName('ldClause')[i-1].focus();
 				return false;
 				break;
 			}
 			if(document.getElementsByName('ldClause')[i-1].value!="" || document.getElementsByName('ldClause')[i-1].value!=null )
			{
				 if(ValidateTextField(document.getElementsByName('ldClause')[i-1],path,'LdClause')==false)
				 {
				 	document.getElementsByName('ldClause')[i-1].focus();
				 	return false;
				 	break;
				 }
			}
 			//[0012] end
			
			/*if(dateCompare(valFrom,valTo)==1)
			{			
				alert("Contract Start Date Cannot be greater than Contract End Date");
				return false;
			}*/
			
			//009 start
			var j;
			var test;
			var check="";
			var jsData=
			{
			   custPoDetailNo:document.getElementsByName('custPoDetailNo')[i-1].value,
			
	 		    custPoDate:document.getElementsByName('custPoDate')[i-1].value,
	 		    poNumber:document.getElementById('poNumber').value
	 			};
	 			
	 			var pono=document.getElementsByName('custPoDetailNo')[i-1].value;
	 			
			//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			
		     test=jsonrpc.processes.validatePoDetailNONDATE_FORCHANGE(jsData);
			
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
	
		
			
				//009 end
						
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
	 			//[0012]
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
	 			//[0012]
	 			isReqLdClause:document.getElementsByName('ldClause')[i-1].isRequired
		   };
		   count++;
			//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var error = jsonrpc.processes.validatePoDetails(jsData);
			if(error != "") {
			alert(error);
			return false;
			
 		} 
	 }
 	return true;
 	}catch(e)
	{
		alert('error code 647: '+ e.message);
	}
 }
 
  function validateContactDetails()
 {
 	try{
	var i;
	var count = 1;
	       var myForm=document.getElementById('searchForm');
	    	var contactTable = document.getElementById('tableContact');
	    	var addressTable = document.getElementById('tableAddress');
	var jsData = new Array();
	for(i=0;i<contactTable.rows.length-1;i++)
     {
   
	    if(myForm.salutationName.length==undefined)
	    {
			// Start[012]
			var counter = myForm.chkSelectContactDetail.value;
			if(trim(myForm.contactType.value) == '' && myForm.contactType.isRequired=="1")
			{
				alert("Please enter Contact Type on line no."+(i+1)) ;
				myForm.contactType.focus();
				return false;
			}
			
			else
			{
			 	var flag = (myForm.contactType.value).search('%');
				 if(flag != -1)	
				 	{
				 		alert("Please enter Right Contact Type on line no."+(i+1)) ;
						return false;
				 	}
				 else
				 {	
				 	
					   if(autoFillContactType(counter)==false)
							{
								return false;
							}
				}
			}
			
			if(trim(myForm.salutationName.value) == '' && myForm.salutationName.isRequired=="1")			
			{
				alert("Please enter Salutation on line no."+(i+1)) ;
				myForm.salutationName.focus();
				return false;
			}
			else
			{
			 	var flag = (myForm.salutationName.value).search('%');
				 if(flag != -1)	
				 	{
				 		alert("Please enter Right Salutation Name on line no."+(i+1)) ;
						return false;
				 	}
				 else
				 {	
				 	
					   if(autoFillSalutation(counter)==false)
							{
								return false;
							}
				}
			}
			if(trim(myForm.firstName.value) == '' && myForm.firstName.isRequired=="1")
			{
				alert("Please Enter First Name on line no."+(i+1)) ;
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
			if(trim(myForm.lastName.value) == '' && myForm.lastName.isRequired=="1")
			{
				alert("Please Enter Last Name on line no."+(i+1)) ;
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
			if(trim(myForm.cntEmail.value) == '' && myForm.cntEmail.isRequired=="1")
			{
				alert("Please Enter Email on line no."+(i+1)) ;
				myForm.cntEmail.focus();
				return false;
			}
			if(trim(myForm.contactCell.value) == '' && myForm.contactCell.isRequired=="1" )
			{
				alert("Please Enter Contact Cell on line no."+(i+1)) ;
				myForm.contactCell.focus();
				return false;
			}
			if(trim(myForm.contactFax.value) == '' && myForm.contactFax.isRequired=="1")
			{
				alert("Please Enter Contact Fax on line no."+(i+1)) ;
				myForm.contactFax.focus();
				return false;
			}
			//==================================================================			
			//-------------------Address Tab Validation-------------------------
			//==================================================================
			
				if(trim(myForm.address1.value) == '' && myForm.address1.isRequired=="1")
				{
					alert("Please Enter Address1 on line no."+(i+1)) ;
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
				if(trim(myForm.address2.value) == '' && myForm.address2.isRequired=="1")
				{
					alert("Please Enter Address2 line no."+(i+1)) ;
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
			
				if(trim(myForm.address3.value) == '' && myForm.address3.isRequired=="1")
				{
					alert("Please Enter Address3 on line no."+(i+1)) ;
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
				if(trim(myForm.cityName.value) == '' && myForm.cityName.isRequired=="1")
				{
					alert("Please enter City Name on line no."+(i+1)) ;
					myForm.cityName.focus();
					return false;
				}
				else
				{
				 	var flag = (myForm.cityName.value).search('%');
					 if(flag != -1)	
					 	{
					 		alert("Please enter Right City Name on line no."+(i+1)) ;
							return false;
					 	}
					 else
					 {	
					 	   $('#hdnCheckTab').val(1);	
						   if(autoFill(counter)==false)
								{
									return false;
								}
					}
				}			
				if(trim(myForm.stateName.value) == '' && myForm.stateName.isRequired=="1")
				{
					alert("Please Enter State Name line no."+(i+1)) ;
					myForm.stateName.focus();
					return false;
				}	
				if(trim(myForm.countyName.value) == '' && myForm.countyName.isRequired=="1")
				{
					alert("Please Enter County Name on line no."+(i+1)) ;
					myForm.countyName.focus();
					return false;
				}			
				if(trim(myForm.pinNo.value) == '' && myForm.pinNo.isRequired=="1")
				{
					alert("Please Enter Pin No on line no."+(i+1)) ;
					myForm.pinNo.focus();
					return false;
				}
			
			//==================================================================			
			//-------------------Address Tab Validation-------------------------
			//==================================================================
	    	
	    }
	    else
	    {
	    	
			var counter = myForm.chkSelectContactDetail[i].value;
			if(trim(myForm.contactType[i].value) == '' && document.getElementsByName('contactType')[i].isRequired=="1")
			{
				alert("Please enter Contact Type on line no."+(i+1)) ;
				myForm.contactType[i].focus();
				return false;
			}
			
			else
				{
				 	var flag = (myForm.contactType[i].value).search('%');
					 if(flag != -1)	
					 	{
					 		alert("Please enter Right Contact Type on line no."+(i+1)) ;
							return false;
					 	}
					 else
					 {	
					 	
						   if(autoFillContactType(counter)==false)
								{
									return false;
								}
					}
				}	  
			if(trim(myForm.salutationName[i].value) == '' && document.getElementsByName('salutationName')[i].isRequired=="1")
			{
				alert("Please enter Salutation on line no."+(i+1)) ;
				myForm.salutationName[i].focus();
				return false;
			}
			else
				{
				 	var flag = (myForm.salutationName[i].value).search('%');
					 if(flag != -1)	
					 	{
					 		alert("Please enter Right Salutation Name on line no."+(i+1)) ;
							return false;
					 	}
					 else
					 {	
					 	
						   if(autoFillSalutation(counter)==false)
								{
									return false;
								}
					}
				}
			if(trim(myForm.firstName[i].value) == '' && document.getElementsByName('firstName')[i].isRequired=="1") 
			{
				alert("Please Enter First Name on line no."+(i+1)) ;
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
			
			if(trim(myForm.lastName[i].value) == '' && document.getElementsByName('lastName')[i].isRequired=="1")
			{
				alert("Please Enter Last Name on line no."+(i+1)) ;
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
			if(trim(myForm.cntEmail[i].value) == '' && document.getElementsByName('cntEmail')[i].isRequired=="1")
			{
				alert("Please Enter Email on line no."+(i+1)) ;
				myForm.cntEmail[i].focus();
				return false;
			}
			if(trim(myForm.contactCell[i].value) == '' && document.getElementsByName('contactCell')[i].isRequired=="1")
			{
				alert("Please Enter Contact Cell on line no."+(i+1)) ;
				myForm.contactCell[i].focus();
				return false;
			}
			if(trim(myForm.contactFax[i].value) == '' && document.getElementsByName('contactFax')[i].isRequired=="1")
			{
				alert("Please Enter Contact Fax on line no."+(i+1)) ;
				myForm.contactFax[i].focus();
				return false;
			}
			
						//==================================================================			
			//-------------------Address Tab Validation-------------------------
			//==================================================================
			
				if(trim(myForm.address1[i].value) == '' && document.getElementsByName('address1')[i].isRequired=="1")
				{
					alert("Please Enter Address1 on line no."+(i+1)) ;
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
				if(trim(myForm.address2[i].value) == '' && document.getElementsByName('address2')[i].isRequired=="1")
				{
					alert("Please Enter Address2 line no."+(i+1)) ;
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
				if(trim(myForm.address3[i].value) == '' && document.getElementsByName('address3')[i].isRequired=="1")
				{
					alert("Please Enter Address3 on line no."+(i+1)) ;
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
				if(trim(myForm.cityName[i].value) == '' && document.getElementsByName('cityName')[i].isRequired=="1")
				{
					alert("Please enter City Name on line no."+(i+1)) ;
					myForm.cityName[i].focus();
					return false;
				}
				
				else
				{
				 	var flag = (myForm.cityName[i].value).search('%');
					 if(flag != -1)	
					 	{
					 		alert("Please enter Right City Name on line no."+(i+1)) ;
							return false;
					 	}
					 else
					 {	
					 	   $('#hdnCheckTab').val(1);	
						   if(autoFill(counter)==false)
								{
									return false;
								}
					}
				}		
				if(trim(myForm.stateName[i].value) == '' && document.getElementsByName('stateName')[i].isRequired=="1")
				{
					alert("Please Enter State Name line no."+(i+1)) ;
					myForm.stateName[i].focus();
					return false;
				}
				if(trim(myForm.countyName[i].value) == '' && document.getElementsByName('countyName')[i].isRequired=="1")
				{
					alert("Please Enter County Name on line no."+(i+1)) ;
					myForm.countyName[i].focus();
					return false;
				}
				if(trim(myForm.pinNo[i].value) == '' && document.getElementsByName('pinNo')[i].isRequired=="1")
				{
					alert("Please Enter Pin No on line no."+(i+1));
					myForm.pinNo[i].focus();
					return false;
				}
			// End[012]
			//==================================================================			
			//-------------------Address Tab Validation-------------------------
			//==================================================================
			
		}
		
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
	 			pinNo:document.getElementsByName('pinNo')[i].value
		   };
		   count++;
	}
 	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var error = jsonrpc.processes.validateContactDetails(jsData);
	if(error != "") {
		alert(error);
		return false;
	}		
	return true;	
	}catch(e)
	{
		alert('error code 648: '+ e.message);
	}	
 }
function validateRoleField()
{
	try{
			var myForm=document.getElementById('searchForm')
			for (iField = 0 ; iField < roleFeild.list.length; iField++)
    		{
    			//alert(roleFeild.list[iField].feildName);
	 			for(iCtrl=0;iCtrl<myForm.elements.length;iCtrl++)
				{
					var ctrlName=myForm.elements[iCtrl].name;
					if(ctrlName==roleFeild.list[iField].feildName)
					{
						if(roleFeild.list[iField].isReadOnly=="1")
						{
							myForm.elements[iCtrl].value  = "HHH";
							//alert(myForm.elements[iCtrl].name)
						}	
						else
							myForm.elements[iCtrl].readOnly  = false;
							
						myForm.elements[iCtrl].Mand = roleFeild.list[iField].isMand;
					}
				}	
			}		
		}catch(e)
	{
		alert('error code 649: '+ e.message);
	}	
		
}
function AddContact()
{
	try{
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
		}catch(e)
	{
		alert('error code 650: '+ e.message);
	}
	
}
//class type changed by lawkush
function AddAddress(counter)
{
	try{
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
	str="<input type='hidden' class='inputBorder1' name='addID' id='addID' value='0'><input onmouseover='getTip(value)' maxlength='255' onmouseout='UnTip()' type='text' class='inputBorder1' name='address1' style='width:100px; float:left;' isRequired=0 id='address1'  onBlur= \"if( trim(this.value).length>0){return ValidateTextField(this,path,'Address1')};\">"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2' maxlength='255'  name='address2' style='width:100px; float:left;' isRequired=0 id='address2' onBlur= \"if( trim(this.value).length>0){return ValidateTextField(this,path,'Address2')};\">"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder2'  name='address3' style='width:100px; float:left;' maxlength='255' isRequired=0 id='address3' onBlur= \"if( trim(this.value).length>0){return ValidateTextField(this,path,'Address3')};\">"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1'  name='cityName' isRequired=0 id='cityName"+counter+"'  style='width:90px; float:left;' readonly='readonly' onchange='autoFill("+counter+")' onkeydown='if (event.keyCode == 13)  return autoFill("+counter+");' ><input type='hidden' name='cityCode' id='cityCode"+counter+"'  readonly='readonly' ><div class='searchBg1'><a href='#' onClick='popCityList("+ counter +")'>...</a></div>"
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
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' class='inputBorder1'  style='width:90px; float:left;' name='pinNo' isRequired=0 id='pinNo"+counter+"' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Pin')};\"><input type='hidden' name='pinCode' id='pinCode"+counter+"'  readonly='readonly' ><div class='searchBg1'><a href='#' onClick='popPinList("+ counter +")'>...</a></div>"
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
	str="<input type='hidden' value='0' name='hdnAddess' id='hdnAddess'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}catch(e)
	{
		alert('error code 651: '+ e.message);
	}
}



//----------------------------------Service Type Tab----------------------Starts Here------------------

function fnDeletePro()
{   
	if(document.getElementById('deleteProductDiv').disabled==true){
		return false;
	}
	try{
		//added for clep to prevent onclick activity
		//added for clep states in change tab to hide save button
		if(clepState ==1 || clepState == 2 || clepState ==3 || clepState ==4){
			return false;
		}
		//Added By Saurabh for Role Check
		//Start
		var stage = document.getElementById('stageName').value;
		if(stage=="Partial Publish" || stage=="Billing trigger"){
			alert("You are not authorised to use this functionality");
			return false;
		}
     
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
		//End
		var myform=document.getElementById("searchForm");
		var checkedCounter=1;
		var checkedCounter1=1;
		var serviceNo=0;
		var index_var=0;
		index_var=startRecordId;
	    var jsData1 =			
	    {
	    	startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			sortByOrder:sortByOrder,
			pageRecords:pageRecords,		
			poNumber:Number(document.getElementById('poNumber').value)
			
		};
	    var test1=jsonrpc.processes.poulateSolutionChangeResults(jsData1);

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
		for(i=1;i<=serviceCount;i++)
		{
			var cat=test1.list[0].productCatelogEntered;
			if(cat==0 && document.forms[0].chkService.checked==true){
				alert("please add atleast one product");
				return; 
			}
		}
		var chkProduct=document.getElementById('chkSPID');
		var str="";
		var chkLength=document.forms[0].chkSPID.length;
		if(chkLength==undefined){
			if(document.forms[0].chkSPID.checked==true && document.forms[0].chkSPID.value!=global_firstNode){//If Checked Line is Parent Line, we will not delete
				alert('Please do not check Root Parent line, It will not be deleted');
				return false;
			}
			if(document.forms[0].chkSPID.checked==true){
				checkedCounter1++;
				if(str=="" ){
					str=document.forms[0].chkSPID.value;
				}else{
     	            str=str + "," +document.forms[0].chkSPID.value;
				}
				var jsData =			
				{
					serviceId1:str
				};
			    var answer = confirm("Do You Want To Continue Deleting Line Item " + str)
				if(answer){
					//Do Nothing
				}else{
					return false;
				}
			    result = jsonrpc.processes.DeleteProducts(jsData);
			    if(result.msgOut != "" && result.msgOut != null){
			    	alert("Product Line Deleted Successfully");				       		
			    	ServiceTreeViewAfterDisconnection(document.getElementById('hdnservicetypeid').value);
			    }
	       }
	  }else{  
		  for(i=0;i<document.forms[0].chkSPID.length;i++){   
			  if(myform.chkSPID[i].checked==true && myform.chkSPID[i].value==global_firstNode){//If Checked Line is Parent Line, we will not delete
				  alert('Please do not check Root Parent line, It will not be deleted');
				  return false;
			  }
			  if(myform.chkSPID[i].checked==true){
				  checkedCounter1++;
				  if(str==""){
					  str=myform.chkSPID[i].value;	     	    
				  }else{
					  str=str + "," +myform.chkSPID[i].value;
	     	      }
			  } 
		  }	
		  for(i=0;i<document.forms[0].chkSPID.length;i++){
		     if(myform.chkSPID[i].checked==true){
		         checkedCounter1++;
		         var jsData =			
				    {
					  serviceId1:str
					};
		         var answer = confirm("Do You Want To Continue Deleting Line Item " + str)
		         if(answer){
		        	 //Do Nothing
		         }else{
		        	 return false;
		         }
		         result = jsonrpc.processes.DeleteProducts(jsData);			    
		         if(result.msgOut != "" && result.msgOut != null){
	       			alert("Product Line Deleted Successfully");	
					ServiceTreeViewAfterDisconnection(document.getElementById('hdnservicetypeid').value);
		         }
		     }
		  }
	   }
	   if(checkedCounter1==1){
		   alert("please select a product to delete");
	   }
	}catch(e){
		alert('error code 652: '+ e.message);
	}
}
function ViewServiceTree(flag)
{
	try{
		var myForm=document.getElementById('searchForm');
		var poNumber=document.getElementById('poNumber').value;
		myForm.action=contPath + "/ChangeOrderAction.do?method=getServiceTreeview&modeName="+ modeValue+"&orderNo="+poNumber+"&flag="+flag+"&checkedServiceNumber="+checkedServiceNumber;
		showLayer();
		myForm.submit();
	}catch(e)
	{
		alert('error code 653: '+ e.message);
	}
}
//function drawTable()
function drawServiceLineTable(){
	/*if(serviceTableCreated)
		return;*/
	try{
		
		//------------Set Page Size for Service Line Table Paging Start --------------
	   	pageRecords=PAGE_SIZE_SERVICE_LINE;
	   //------------Set Page Size for Service Line Table Paging End ----------------
	   	
		var tr, td, i, j, oneRecord;
		var test;
		mytable = document.getElementById('ServiceList');
		
		var iCountRows = mytable.rows.length;
	  
	    for (i = 0; i <  iCountRows; i++)
	    {
	       mytable.deleteRow(0);
	    } 
	   //-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
	   	document.getElementById('txtPageNumber').value= pageNumber;
	   	sync();  
	   	//lawkush Start
		var index_var=0;
		index_var=startRecordId;
		//lawkush End
		//[008] added searchLSI and searchServiceNo
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
	    
	 
	    //[001] Start
	    document.getElementById('HdnChangeTypeID').value=document.getElementById('changeTypeID').value;
	
	    if(document.getElementById('HdnChangeTypeID')==null)
	    {
	
	    }
	    //[001] End
	    else
    {
		//Vijay. Remove demo order condition 
	    //if(document.getElementById('HdnChangeTypeID').value ==3 ||document.getElementById('HdnChangeTypeID').value ==4 )
	    if(document.getElementById('HdnChangeTypeID').value ==3)
	    {
	
	        test = jsonrpc.processes.poulateServiceListAfterDisconnection(jsData);
	    }
	    //Vijay add demo-regularize condition
	    else if(document.getElementById('HdnChangeTypeID').value ==2 || document.getElementById('HdnChangeTypeID').value ==5 || document.getElementById('HdnChangeTypeID').value ==1
	    	|| (document.getElementById('HdnChangeTypeID').value ==4 && document.getElementById('HdnSubChangeTypeID').value ==12))
	    {
	
	    	test = jsonrpc.processes.poulateSolutionChangeResults(jsData);
	    }
	    //[001] Start
	  //-------------------------[PagingSorting:Date:11-Oct-2012]----------------------------------
	    iTotalLength=test.list.length;
	    
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
		}/*else{     commented by[008] 
			return;
		}*/
	    
	    
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
		
	    if(test==undefined)
	    {

	    }
	    //[001] end
	    else
	    {
	    	
	    	countService = 1;
	
		    for (i = 0 ; i < test.list.length; i++)
		    {
				var str;
				var tblRow=document.getElementById('ServiceList').insertRow();
						
				var tblcol=tblRow.insertCell();
				tblRow.setAttribute("initTo", test.list[i].role_id );
				tblRow.setAttribute("ispublished", test.list[i].isPublished );
				tblcol.width = "10%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colRadio";
				serviceTypeId=test.list[i].serviceTypeId;
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='chkService' id='chkService"+i+"' type='radio' value='"+ test.list[i].serviceId +"'  onclick=ServiceTreeViewAfterDisconnection(serviceTypeId);fncServicePresent("+test.list[i].role_id+","+gb_roleID+")><input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtsubChangeTypeId' id='txtsubChangeTypeId"+i+"' value='"+test.list[i].subChangeTypeId+"' class='inputBorder5' type='hidden' readonly='true' size='5'/>";
			    str = str+ "<input type = 'hidden' id = 'isServicePublished' value = '"+ test.list[i].isPublished +"'>"
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				//Started By Saurabh to make user see whether service is dummy or not
				var tblcol=tblRow.insertCell();
				tblcol.width = "5%";
				tblcol.align = "center";
				tblcol.vAlign="top";
				tblcol.className="inner colDummy";
				if(test.list[i].isDummy==0)
					str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='dummy' id='dummy"+i+"' value=' - ' class='inputBorder1' type='text' readonly='true' size='5'/>";
				else
					str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='dummy' id='dummy"+i+"' value='Yes' class='inputBorder1' type='text' readonly='true' size='5'/>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				//End By Saurabh
												
				var tblcol=tblRow.insertCell();
				tblcol.width = "12%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colServiceNo";
				if(test.list[i].isPublished==0)
					str="<input name='chk' id='chk"+i+"' value='No' class='inputBorder5' type='hidden' readonly='true' size='5'/><input name='txtServiceNo"+countService+"' value='"+ test.list[i].serviceId +"' class='inputBorder1' type='text' readonly='true' size='5'> <input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceTypeID"+countService+"' value='"+ test.list[i].serviceTypeId +"' type='hidden' readonly='true'>";
				else
					str="<input name='chk' id='chk"+i+"' value='Yes' class='inputBorder5' type='hidden' readonly='true' size='5'/><input name='txtServiceNo"+countService+"' value='"+ test.list[i].serviceId +"' class='inputBorder1' type='text' readonly='true' size='5'> <input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceTypeID"+countService+"' value='"+ test.list[i].serviceTypeId +"' type='hidden' readonly='true'>";
				
				//str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceNo"+countService+"' value='"+ test.list[i].serviceId +"' class='inputBorder1' type='text' readonly='true' size='5'> <input name='txtServiceTypeID"+countService+"' value='"+ test.list[i].serviceTypeId +"' type='hidden' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "20%";
				tblcol.align = "center";
				tblcol.vAlign="top";
				tblcol.className="inner colServiceType";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceName"+countService+"' value='"+ test.list[i].serviceTypeName +"' class='inputBorder1' type='text' readonly='true'> <input name='txtServiceTypeID"+countService+"' value='"+ test.list[i].serviceTypeId +"' type='hidden' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "center";
				//tblcol.vAlign="top";
				//str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceSubTypeName"+countService+"' value='"+ test.list[i].serviceSubTypeName +"' class='inputBorder1' type='text' readonly='true'>";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
			
				//lawkush start
				var tblcol=tblRow.insertCell();
				tblcol.width = "6%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colLogicalSI";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtLogicalSINumber"+countService+"'  value='"+ test.list[i].logicalSINumber +"'  class='inputBorder1'  width = '10%' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "8%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colLogicalSI";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtCustomer_logicalSINumber"+countService+"' value='"+ test.list[i].customer_logicalSINumber +"'  class='inputBorder1'  width = '10%' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				//lawkush end
				
	
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "12%";
				tblcol.align = "center";
				tblcol.vAlign="top";
				tblcol.className="inner colAttribute";
				str="<div class='searchBg1'><a href='#' onclick=popServiceAttribute('ServiceAttributes',"+ countService +",'"+ test.list[i].serviceId +"')>...</a></div><input name='servAttrEntered"+countService+"' value='"+test.list[i].servAttrEntered+"' type='hidden' >";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "10%";
				tblcol.align = "center";
				tblcol.vAlign="top";
				tblcol.className="inner colAttribute";
				str="<div class='searchBg1'><a href='#' onclick=serviceProductAttribute("+ countService +")>...</a></div><input name='prdAttrEntered"+countService+"' value='"+test.list[i].prdAttrEntered+"' type='hidden' > <input name='productEntered"+countService+"' value='"+test.list[i].productCatelogEntered+"' type='hidden' >";
				str = str+ " <input id='txtLogicalSINumber"+countService+"' value='"+test.list[i].logicalSINo+"' type='hidden' >";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "10%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colServiceStatus";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:90%;' name='serviceStatus"+countService+"' value='"+ test.list[i].serviceStatus +"' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				//adding one more column for display demo flag. 
				var dm = "No";
				if(test.list[i].isDemo==1){
					dm="Yes";
				}
				var tblcol=tblRow.insertCell();
				tblcol.width = "10%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colIsDemo";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:90%;' name='dmo"+countService+"' value='"+ dm +"' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "10%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colServiceStatus";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:90%;' name='initiatedTo"+countService+"' value='"+ test.list[i].initiated_to +"' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "10%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner calcancelDate";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:90%;' name='serviceCanclDate"+countService+"' value='"+ test.list[i].service_cancl_date +"' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "10%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colCancellationreson";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:90%;' name='serviceCancelReson"+countService+"' value='"+ test.list[i].serv_cancel_reson +"' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "10%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colCancelledBy";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:90%;' name='serviceCancelledby"+countService+"' value='"+ test.list[i].service_cancelledby +"' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				var tblcol=tblRow.insertCell();
				tblcol.width = "10%";
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="inner colCancelremarks";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1'  style='width:90%;' name='serviceCancelRemarks"+countService+"' value='"+ test.list[i].serv_cancel_remarks +"' class='inputBorder5' type='text' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				
				document.getElementById('hdnSubchangeType').value = test.list[i].subChangeTypeId;
				
				countService++;
				if(i==checkedServiceNumber)
				{
				document.getElementById("chkService"+i).checked=true;
				ServiceTreeViewAfterDisconnection(serviceTypeId);
				}
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
						abc=abc+"<br>( CHANGE : " +iNewServiceCount+ " )";
					else
						abc=abc+" ( CHANGE : " +iNewServiceCount+ " )";
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
		}
	}
	if(document.getElementById('HdnChangeTypeID').value ==3 && document.getElementById('deleteProductDiv')!=null)
		document.getElementById('deleteProductDiv').style.display="none";
		//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
		/* if (document.getElementById('HdnChangeTypeID').value ==2 && document.getElementById("subChangeTypeId").value==10)
				{
				
				
				document.getElementById('productCatetogIcon').style.display="none";
				
				
				} 
		*/	
		
		//getServicelineCount();	
		disableServiceLines('contentscrollService',gb_roleID);
	}catch(e)
	{
		alert('error code 654: '+ e.message);
	}
	serviceTableCreated = true;
}

function popServiceAttribute(url,cntr,serviceId)
{
	try{
	var HdnChangeTypeID=document.getElementById("HdnChangeTypeID").value;
	//logicalSINo=document.getElementById("txtServiceName"+id).value+"-"+document.NewOrder.txtAccountNo.value+"-"+serviceID
	//Start: Added By Ashutosh for VCS
	var serviceTypeId=document.getElementById("txtServiceTypeID"+cntr).value;
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
			
			var path = contPath + "/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&HdnChangeTypeID="+HdnChangeTypeID+"&serviceTypeId="+serviceTypeId;
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
			var path = contPath + "/selectservice.do?method=goToServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"&HdnChangeTypeID="+HdnChangeTypeID+"&serviceTypeId="+serviceTypeId;
			document.getElementById('hdnCurrentTempCounter').value=cntr;
			window.showModalDialog(path,window,"status:false;dialogWidth:1200px;dialogHeight:600px");
		}
	}
	}catch(e)
	{
		alert('error code 655: '+ e.message);
	}
}



function AddServiceLine()
{
	try{
	var str;
	var tblRow=document.getElementById('ServiceList').insertRow();
			
	var tblcol=tblRow.insertCell();
	tblcol.width = "8%";
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input name='chkService' id='chkService' type='radio' value='' onclick=fncServiceTreeView()>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.width = "12%";
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' name='chk' id='chk"+countService+"' class='inputBorder5' type='hidden' readonly='true' size='5'/><input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceNo"+countService+"' class='inputBorder1' type='text' readonly='true' size='5'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//Started By Saurabh to make user see whether service is dummy or not
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' name='dummy' id='dummy"+countService+"' class='inputBorder5' type='text' readonly='true' size='5'/>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//End By Saurabh 
	
	var tblcol=tblRow.insertCell();
	tblcol.width = "20%";
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceName"+countService+"' class='inputBorder1' type='text' readonly='true'> <input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceTypeID"+countService+"' type='hidden' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	

	//var tblcol=tblRow.insertCell();
	//tblcol.align = "center";
	//tblcol.vAlign="top";
	//str="<input onmouseover='getTip(value)' onmouseout='UnTip()' name='txtServiceSubTypeName"+countService+"' class='inputBorder1' type='text' readonly='true'>";
	//CellContents = str;
	//tblcol.innerHTML = CellContents;

//lawkush start

	var tblcol=tblRow.insertCell();
	tblcol.width = "10%";
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtLogicalSINumber"+countService+"' width = '10%' class='inputBorder5' type='text' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.width = "10%";
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' style='width:90%;' name='txtCustomer_logicalSINumber"+countService+"' width = '10%' class='inputBorder5' type='text' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	//lawkush end
	var tblcol=tblRow.insertCell();
	tblcol.width = "10%";
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<div class='searchBg1'><a href='#' id='a"+countService+"' onclick=popService("+ countService +")>...</a></div>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.width = "10%";
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<div class='searchBg1'><a href='#' onclick=popServiceAttribute('ServiceAttributes',"+ countService +",'')>...</a></div><input name='servAttrEntered"+countService+"' value='0' type='hidden' >";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//counter++;
	
	var tblcol=tblRow.insertCell();
	tblcol.width = "10%";
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<div class='searchBg1'><a href='#' onclick=serviceProductAttribute("+ countService +")>...</a></div><input name='prdAttrEntered"+countService+"' value='0' type='hidden' > <input name='productEntered"+countService+"' value='0' type='hidden' >";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.width = "10%";
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' name='serviceCanclDate' id='serviceCanclDate"+countService+"' class='inputBorder5' type='text' readonly='true' size='5'/>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' name='serviceCancelReson' id='serviceCancelReson"+countService+"' class='inputBorder5' type='text' readonly='true' size='5'/>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' name='serviceCancelledby' id='serviceCancelledby"+countService+"' class='inputBorder5' type='text' readonly='true' size='5'/>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' tabIndex='-1' name='serviceCancelRemarks' id='serviceCancelRemarks"+countService+"' class='inputBorder5' type='text' readonly='true' size='5'/>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	
	popService(countService);
	countService++;
	}catch(e)
	{
		alert('error code 656: '+ e.message);
	}

}
//----------------------------------Service Type Tab----------------------Ends Here------------------

function SelectProductCatelog()
{
	try{
	if(  document.getElementById("HdnChangeTypeID").value==3 || document.getElementById("HdnChangeTypeID").value==1 || 
	(document.getElementById("HdnChangeTypeID").value==4 && (document.getElementById("hdnSubchangeType").value==12 || document.getElementById("hdnSubchangeType").value==13 || document.getElementById("hdnSubchangeType").value==14 || document.getElementById("hdnSubchangeType").value==15)))
	{
		return false;
	}
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
			if(document.forms[0].txtServiceNo1.value=="")
			{
				chkVal=1;
			}
			else
			{
				//Start[035]
				//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");	
			
				serviceInactiveFlagCheck=jsonrpc.processes.serviceInactiveFlagCheck(document.forms[0].txtServiceNo1.value);
				
				if(serviceInactiveFlagCheck == 1 || serviceInactiveFlagCheck == 2)
				{
					alert('Service cancelled , you cant save now !! ');
					return false;
				}
				//End[035]
				document.getElementById('hdnCurrentTempCounter').value=newCounter;
				var changeTypeId=   document.getElementById("HdnChangeTypeID").value;
				var selectedProductId = jsonrpc.processes.getSelectedProductId(document.getElementById('txtServiceNo'+newCounter).value)
				if(selectedProductId == 0)
				{
					alert("Please Select the Product Name for the selected Service in Attributes Section");
					return false;
				}
				var path = contPath + "/ChangeOrderAction.do?method=displayProductCatelog&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accountID').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&changeTypeID="+document.getElementById("HdnChangeTypeID").value+"&LogicalSI="+  document.getElementById('txtLogicalSINumber'+newCounter).value+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value+"&productId="+selectedProductId;
				window.showModalDialog(path,window,"status:false;dialogWidth:2000px;dialogHeight:2000px");
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
					//Start[035]
					//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");	
				
					serviceInactiveFlagCheck=jsonrpc.processes.serviceInactiveFlagCheck(document.getElementById('txtServiceNo'+newCounter).value);
					
					if(serviceInactiveFlagCheck == 1 || serviceInactiveFlagCheck == 2)
					{
						alert('Service cancelled , you cant save now !! ');
						return false;
					}
					//End[035]
					document.getElementById('hdnCurrentTempCounter').value=newCounter;
					var selectedProductId = jsonrpc.processes.getSelectedProductId(document.getElementById('txtServiceNo'+newCounter).value)
					if(selectedProductId == 0)
					{
						alert("Please Select the Product Name for the selected Service in Attributes Section");
						return false;
					}
					var path = contPath + "/ChangeOrderAction.do?method=displayProductCatelog&serviceID="+ document.getElementById('txtServiceNo'+newCounter).value+"&accountID="+ document.getElementById('accountID').value+"&orderDate="+ document.getElementById('orderDate').value+"&curShortCode="+ document.getElementById('curShortCode').value+"&poNumber="+ document.getElementById('poNumber').value+"&serviceTypeID="+document.getElementById('txtServiceTypeID'+newCounter).value+"&LogicalSI="+  document.getElementById('txtLogicalSINumber'+newCounter).value+"&changeTypeID="+document.getElementById("HdnChangeTypeID").value+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value+"&productId="+selectedProductId;
					window.showModalDialog(path,window,"status:false;dialogWidth:2000px;dialogHeight:2000px");
					//window.open(path);
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
	}catch(e)
	{
		alert('error code 657: '+ e.message);
	}
}
//----------------------------------Service and Product Tab---------------------
function serviceProductPopup() {
	try{
        var path = contPath + "/ChangeOrderAction.do?method=fetchServiceNProduct&roleID="+gb_roleID;		
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:600px");
	}catch(e){
		alert('error code 658: '+ e.message);
	}

}
function serviceProductAttribute(ctrlNo) 
{
	try{
	  var hdnSubChangeTypeID =document.getElementById('hdnSubChangeTypeID').value;	
	  var HdnChangeTypeID=document.getElementById("HdnChangeTypeID").value;
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
		var path = contPath + "/ChangeOrderAction.do?method=getTProductAttDetail&hdnserviceid="+serviceNo+'&hdnserviceTypeId='+serviceTypeId+'&HdnChangeTypeID='+HdnChangeTypeID+'&hdnSubChangeTypeID='+hdnSubChangeTypeID+"&"+d.getTime();		
		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//window.open(path);
	 }
	 }catch(e)
	{
		alert('error code 659: '+ e.message);
	}
}


//-------------------------Po Detail----------------------------------------------
function getCurrentDate()
{
	try{
		/*var less2Date="<%=AppUtility.getTodayDate()%>";
		var valFrom=document.getElementById("demo1").value;
		var valTo=document.getElementById("demo2").value;
		if(valFrom==null || valFrom==""||valTo==null || valTo=="")
		{
			alert("Please Enter both From Date and To Date.");
			return false;
		}*/
	}catch(e)
	{
		alert('error code 660: '+ e.message);
	}
}	

//038 start

function DeletePO()
  {
  	try{
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
		  		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		  		
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
					alert('you cant Delete all the PO Lines');
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
					alert('You cannot delete PO Line with Default Po');
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
				  // jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
		}catch(e)
		{
			alert('error code 661: '+ e.message);
		}
	}
	
	
//038 end	

function unCheckedContactMaster()
{
	try{
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
	}catch(e)
	{
		alert('error code 662: '+ e.message);
	}
}


function unCheckedPOMaster()
{
	try{
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
					 
					 if(myForm.chkSelectPODetail[i].checked==true)
			     
					 {
					       counter++;
					 }
					   if(counter==chkLength1)
					   
					   {
					   		  document.getElementById("SelectAllPOChck").checked=true ;
							          
					   }       
			   }
		}
	}catch(e)
	{
		alert('error code 663: '+ e.message);
	}
}


function attachWorkflow()
{
	try{
	
	   // jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		//[022]
		//[0010] Auto billing
		var poNumber1=document.getElementById('poNumber').value;		
		var jsData2 =			
		    {
				poNumber:poNumber1,
				roleId:gb_roleID
			};
			  //  jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			    var check_workflow_already_attached = jsonrpc.processes.reattachworkflowcheck(jsData2);
		//[022]
	if(check_workflow_already_attached == 0 )
	{	    
		var workFlowAttachDetails = jsonrpc.processes.getChangeOrderSubTypeAttached(document.getElementById('poNumber').value);

		if(workFlowAttachDetails.behaviour=="DEFAULT")
		{
			var subtypelist=workFlowAttachDetails.workFlowList;

			if(subtypelist.list.length!=0 && subtypelist.list[0].ib2bWorkflowAttachedId!=null)
			{	



				if(subtypelist.list.length > 1)
				{
					var changeOrderNo=document.getElementById('poNumber').value;
					//var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=1"+"&hdnChangeTypeId="+hdnChangeTypeId+"&changeOrderNo="+changeOrderNo;
					var path = contPath + "/ChangeOrderAction.do?method=attachWorklow&changeOrderNo="+changeOrderNo+"&roleName="+gb_roleID;;
					window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
				}	
				else
				{	
					var jsData1 =			
					{
							poNumber:document.getElementById('poNumber').value,
							projectWorkflowId:subtypelist.list[0].ib2bWorkflowAttachedId,
							roleId:gb_roleID
					};
					//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
					var test1 = jsonrpc.processes.attachworkflowForChangeOrder(jsData1);

					if(test1==1)
					{
						alert('Workflow inserted successfully');
					}
				}

			}
			else
			{
				alert('No Workflow exsists for selected order.\n Order Validation Failed');
				return false;
			}

		}else if(workFlowAttachDetails.behaviour=="PermanentDisconnectionSingleThenBulkApproval")
		{
			//Todo Reveendra : Chk Already inserted case
			var jsData1 =			
			{
					poNumber:document.getElementById('poNumber').value,
					permanentDisconnectionSingleThenBulkApproval:workFlowAttachDetails.behaviour,
					roleId:gb_roleID

			};
			//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			var test1 = jsonrpc.processes.attachworkflowForChangeOrder(jsData1);

			if(test1==1)
			{
				alert('Workflow inserted successfully');
			}
		}

	}
	}catch(e)
	{
		alert('error code 664: '+ e.message);
	}
}

/*function attachWorkflowForSelectedProject()
{
	try{
		var jsData1 =			
	    {
			poNumber:document.getElementById('poNumber').value,
			projectWorkflowId:document.getElementById('hdnworkflowid').value,
			roleId:gb_roleID
		};
		  //  jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		    var test1 = jsonrpc.processes.attachworkflowForChangeOrder(jsData1);
	    
	    	if(test1==1)
	    	{
	    		alert('Workflow inserted successfully');
	    	}
	}catch(e)
	{
		alert('error code 665: '+ e.message);
	}
}
*/






//Added Start [007]	add argumnet in validateOrder method
function validateOrder(isActionOrPublish,allServicesSelected,selectedServiceList){
	try{
	//alert('allServicesSelected '+allServicesSelected);
	//alert('selectedServiceList '+selectedServiceList);
		
		//Logging Total Time Taken by Validate action
			var var_start_validation_time=new Date();
			var var_alert_time_taken_answer=0;
		//Logging Total Time Taken by Validate action
			
	var orderNo=document.getElementById('poNumber').value;
	if(allServicesSelected==null || allServicesSelected==undefined){
		allServicesSelected=true;
	}
	if(selectedServiceList==null || selectedServiceList==undefined){
		selectedServiceList='';
	}


	var serviceForValidate = jsonrpc.processes.getServiceForValidate(orderNo,sessionId,allServicesSelected,selectedServiceList);

	//End [007]
	
	//Shubhranshu - ParallelUpgrade Validation
	var subChangeTypeId =document.getElementById('hdnSubChangeTypeId').value; 
	//alert(subChangeTypeId); alert(isActionOrPublish);
	if((subChangeTypeId==3 || subChangeTypeId==4) && (isActionOrPublish==2))
		{
		var serviceForValidationSplitted=serviceForValidate.split(",");
			var serviceForValidationArray=[];
				var i;
					for(i=0;i<serviceForValidationSplitted.length;i++)
						{
							serviceForValidationArray.push(serviceForValidationSplitted[i]);		
							}			
							var FailedValidationSerivceIdlist = jsonrpc.processes.validateParallelUpgradeForDisconnectionOrder(serviceForValidationArray); //		
								if(FailedValidationSerivceIdlist.list.length!=0)
									{		
									var i, msg1="LSI Nos. ",msg2="",msg3="of ServiceIds ",msg4="",msg5=" cannot be published ";
								var msg6="because these LSIs are not mapped as Parallel Upgrade in New Order Published Services!! \n ";
							for(i=0;i<FailedValidationSerivceIdlist.list.length;i++)
						{
					msg2=msg2+FailedValidationSerivceIdlist.list[i].logical_si_no+", ";
				msg4=msg4+FailedValidationSerivceIdlist.list[i].serviceId+",";
			  }
		alert(msg1+msg2+msg3+msg4+msg5+msg6);
	 return false;
}
	//Shubhranshu- Parallel Upgrade Validation
}
	//added for clep,it is mpp order so pass this functionality
	if(clepState==1 || clepState==2 || clepState==3 || clepState==4){
		document.getElementById('isValidatePO').value=1; //set value to 1 only for CLEP order case
		return false;
	}
	gamNFormulaAttachedWithOrder();
	if(isGamNFormulaAttachedFlag==0 || isGamNFormulaAttachedFlag==-1)
	{
	return false;
	}
	if(document.getElementById('HdnChangeTypeID').value==3)
	{
		validateVCS_L3MPLSForDisconnectOrder();
		if(isVCS_MAP_WITH_L3MPLS==false)return false;
		//[013] 
		//validateMBIC_ON_CC_Discoonnect();
		//if(isValidateMBIC_ON_CC_Discoonnect==false)return false;
	}
	//START POC POINTS As On Date 29-09-2012
	var stage = document.getElementById('stageName').value;
	if(stage=="COPC" || stage=="SED" || stage=="Partial Publish")
	{
		getComponentLineItem();
		if(isComponentAddedInLineItem==false)return false;
	}
	getMultipleDummyServices();
	if(isMultipleDummyServicesInOrder==false)return false;
	//END
	//Start validate opportunity 
	if(document.getElementById("subChangeTypeName")){
	
		var stage = document.getElementById("stageName").value;	
		var orderNoValid = document.getElementById("poNumber").value;
		var subChangeTypeName = document.getElementById("subChangeTypeName").value;	
			
		if(stage == 'New' && orderNoValid != 0 && orderNoValid!= ''){
			if(subChangeTypeName == 'Upgrade-Solution Change' || subChangeTypeName == 'Shifting-Solution Change'){
				var segment = document.getElementById("lob").value;
				var sourceName = document.getElementById("sourceName").value;		
				if(segment == 'AES Corporate' && sourceName == 'CRMORDER'){
					alert('Please Select Opportunity.');
					return false;
				}
			}
		}	
	}
	//End validate opportunity 

	var validated=0;
	var myform=document.getElementById("searchForm");
	var finalErrorMessege="";
	//[007]  commented and define starting in th fuction 
	//var orderNo = document.getElementById('poNumber').value;
	var changeTypeID = document.getElementById('HdnChangeTypeID').value;
	
	if(changeTypeID == 2){
		var validateServiceLevelAttribute=jsonrpc.processes.checkServiceAttributePresent(orderNo,gb_roleID);
		if(validateServiceLevelAttribute != '' &&  validateServiceLevelAttribute!=null){
			finalErrorMessege= finalErrorMessege +"*"+ " Please fill mandatory service level attributes of following serviceId(s)  "+validateServiceLevelAttribute +"\n";
		}
	}
	
	//[0016] start	
	
	var lst=jsonrpc.processes.validateM2M(orderNo);
	if(lst.code==1){
			alert("M2M Service can only be added in M2M Service Segment Account.Follwing are invalid M2M services: "+lst.msg);
			return false;
		}
	//[016] end
	//raghu
	//Ramana
	var signageGlobalCount = jsonrpc.processes.signageGlobalCount(orderNo);
	if(signageGlobalCount > 1)
	{
		alert('Only one Signage Global service is allowed ! ');
		return false;
	}
	//Meenakshi : Added for CBR_20120806-XX-07984
	//Meenakshi : Adding DMX Specific Validation of checking mandatory line items
	
	var result = jsonrpc.processes.dmxMandatoryValidation(orderNo);
	if(result!='')
	{
		finalErrorMessege = finalErrorMessege +"*"+ result+"\n";
	}
	//Ramana
	//-------[Start:086]-----------------------------------------------------------------
		var noFXSICompValidResult = jsonrpc.processes.validateNOFXSIComponent(orderNo);
		if(noFXSICompValidResult !="" && noFXSICompValidResult !=null){
			alert(noFXSICompValidResult);
			return;
		}
	//-------[End:086]-------------------------------------------------------------------
	var test = jsonrpc.processes.validateFeild(orderNo,sessionid);
   	if(test != 'SUCCESS')
   	{
    	finalErrorMessege= finalErrorMessege +"*"+ test +"\n";
	}
   	var errorMsg = "";; 
	var count = 0;
	var jsData11 =			
    {
		poNumber:document.getElementById('poNumber').value
	};
    var test1 = jsonrpc.processes.poulateServiceList(jsData11);
	var serviceCount=test1.list.length
	if(serviceCount==0)
	{
		alert("Please add atleast one service");
		return false;
	}
    for (i=0;i<test1.list.length;i++)
    {
		var serviceId=test1.list[i].serviceId;
		/*
    	 * Service should be active and if Stage is new, all service will be validated,
    	 * else, only those services will be validated, which are present in current owner's bin
    	 */
    	if((test1.list[i].isServiceActive == 0) && ((stage=='New') || (stage != 'New' && test1.list[i].initiatedTo==gb_roleID))){
			
    		if(test1.list[i].serviceTypeId == 202 || test1.list[i].serviceTypeId == 362){	
				var serviceIDChild = test1.list[i].serviceId;	     	
				var lsiCheckForSignageValidation = jsonrpc.processes.lsiCheckForSignageValidation(serviceIDChild);
				if(lsiCheckForSignageValidation == 2){
					alert('Please select a valid LSI for '+serviceIDChild+' in service product attributes.');
	   	     		return false;
	     		}
	   	    }	
			var jsData12 =			
			{
				serviceId:serviceId
			};
				
			//Meenakshi : added to chk that harware should have atleast one child
			isHardwareValid = jsonrpc.processes.getDataForHardware(jsData12);
			if(isHardwareValid ==1){
				alert("Please Add Child For Hardware in Service : " + serviceId);
				return;
			}
			//Meenakshi : END
			test11 = jsonrpc.processes.getUpdateData(jsData12);
			if(test11.list.length>0){
				// Added By Saurabh to check Partial Publish Data
				var serviceTypeId	=test11.list[0].serviceTypeId;
				var serviceSubtypeId=test11.list[0].serviceSubtypeId;
				var isPublished		=test11.list[0].isPublished;
			
				var rfs_date=test11.list[0].rfs_date;
				if(rfs_date==null){
				     finalErrorMessege= finalErrorMessege +"*"+ "Please Enter Rfs Date for ServiceId "+serviceId+"\n";
				}
				
				if((serviceTypeId == null || serviceSubtypeId == null || isPublished == null ) 
					||(serviceTypeId == 0 || serviceSubtypeId == 0 )){
					finalErrorMessege= finalErrorMessege +"*"+ "Please Enter Product Subproduct Details for ServiceId "+serviceId+"\n";
				}
			}else{
				finalErrorMessege= finalErrorMessege +"*"+ "Please Enter Product Subproduct Details for ServiceId "+serviceId+"\n";
			}

			//Sales Service Tax Validation and Updation on Success
			//[005] Start VIPIN
			var outcome=doValidateDCHWChargeAndUpdateFlag();
			if(outcome.returnStatus==-1){
		    	alert(outcome.message);
		    	return false;
		    }
		    //[005] End VIPIN
			//Added By Saurabh for comparing parent and child node(if present) for 17 parameters
			var serviceIdNo=test1.list[i].serviceId;
	    	var productList = jsonrpc.processes.populateProductList(serviceIdNo);
	    	var productCount=productList.list.length;
	    	if(productCount>0){
		    	for(j=0;j<productCount;j++){
		    		var productLineId=productList.list[j].serviceProductID;
		    		var isChildNode = productList.list[j].subLineItemLbl;//Getting additon node details
					if(isChildNode == 1){
						var compareResult = jsonrpc.processes.checkAdditionalNode(productLineId);
						if(compareResult == "NOTOK"){
							updateStatus(0);
							alert("Please Enter billing and hardware details different than the parent node for child node "+productLineId);
							return false;
						}
						else if(compareResult == "NOTSAVE")//[040] Start : Add by Ashutosh for Checking subnode Details are save or not
						{
							updateStatus(0);
							alert("Please First Save The Child Node : "+productLineId);
							return false;
						}else{
							var oldSubNodeID = compareResult.slice(16);
							var resultString = compareResult.slice(0,15);						
							if(resultString == "NOTOK_WITHCHILD" && oldSubNodeID!=""){
								updateStatus(0);
								alert("Please Enter billing and hardware details different for child node : "+productLineId+" than the existing Sub Node : "+oldSubNodeID );
								return false;							
							}					
						}//[040]End
					} 
					//gunjan[002]
					else{
						var compareResult1= jsonrpc.processes.checkCopiedNode(productLineId);
						if(compareResult1 == "NOTSAVE")
						{
							updateStatus(0);
							alert("Please First Save LineItem : "+productLineId);
							return false;
						}
					}
		    	}
		    }		
		    //End By Saurabh for comparing parent and child
		
		    //Added By Saurabh for billing level check in product catelog
		    var billingLevelCheck = jsonrpc.processes.checkBillingLevel(serviceIdNo);
		    for (var p=0;p<billingLevelCheck.list.length;p++){
		    	if(p>0){
		    		var c1=billingLevelCheck.list[p-1].billingLevelId;
		    		var c2=billingLevelCheck.list[p].billingLevelId;
		    		if(c1!=c2){
		    			
		    			var var_before_alert_popup=new Date().getTime();//Record time before alert popup (Logging for total taken time in validation action )
		    			var answer = confirm('Different billing level are selected for \n different line items of Service No: '+ serviceIdNo +' \n Do You Want To  Continue?')
		    			var var_after_alert_popup=new Date().getTime();//Record time after alert popup (Logging for total taken time in validation action )
						if(answer){
							
							var_alert_time_taken_answer=var_alert_time_taken_answer+(var_after_alert_popup-var_before_alert_popup);//total time taken for answer of alert (Logging for total taken time in validation action )
							break;
							//do nothing 
							//move to other validations
						}else{
							return false;
						}
		    		}
		    	}	    	
		    }
		    //End By Saurabh
		    
		    //By Saurabh to check MPLS Validations
			//Start[033]
			if(test1.list[i].serviceTypeId==141){
				var jsData =			
	    		{
	    			serviceId:serviceIdNo
				};
				var MplsLineCount = jsonrpc.processes.getMplsLineCount(jsData);
				if(MplsLineCount.flexiCount > 1){
					alert("You Cannot have more than one FlexiConnect Line Item In Service No "+ serviceIdNo);
					return false;
				}
				if(MplsLineCount.userBasedCount > 0 || MplsLineCount.siteBasedCount > 0){
					if(MplsLineCount.flexiCount == 0){
						alert("You Must Add Atleast One FlexiConnect Line Item In Service No "+ serviceIdNo);
						return false;
					}
				}
				if(MplsLineCount.flexiCount == 1){
					if(MplsLineCount.userBasedCount == 0 & MplsLineCount.siteBasedCount == 0){
						alert("You Should have Atleast One UserBased or SiteBased Line Item In Service No "+ serviceIdNo);
						return false;
					}
					if(MplsLineCount.siteBasedCount > 1){
						alert("You Cannot have more than one SiteBased Line Item In Service No "+ serviceIdNo);
						return false;
					}
				}	
			} 
			//End[033]		
    	}
	}
	//-------[020] End	
    // DnC , Validation , Modified By Shubhranshu, 10-11-2016
		var msg=jsonrpc.processes.validateDropAndCarry(orderNo,stage);
		if(trim(msg.sqlMsg)!='')
			alert(msg.sqlMsg);
		if(Number(msg.sqlMsgCode)<0 )
		{
		return false;
		}		
    // Ends
	
//---------------------------------------[Start:Anil Kumar::Date:08-07-2013]---------------------------
//below code:skip check service root level attribute mandatory for disconnetion and raterenal order	
//Vijay add condition for demo-regularize
if(document.getElementById('HdnChangeTypeID').value!=3 && document.getElementById('HdnChangeTypeID').value!=5
	&& (document.getElementById('HdnChangeTypeID').value!=4 && document.getElementById("hdnSubchangeType").value==12) ){	
	for(i=1;i<=serviceCount;i++)
	{
		if(document.getElementById('prdAttrEntered'+i).value==0)
		{
			errorMsg= errorMsg + "Please Input Product Attribute for Service No: "+document.getElementById('txtServiceNo'+i).value +"\n";
			validated=1;
		}
		if(document.getElementById('servAttrEntered'+i).value==0)
		{
		    
			errorMsg=errorMsg + "Please Input Service Attribute for Service No: "+document.getElementById('txtServiceNo'+i).value +"\n";
			validated=1;
		}
	}
}
var licProtest=jsonrpc.processes.validateProductNameLicCompMap(orderNo);
for(j=0;j<licProtest.list.length;j++)
{    
	finalErrorMessege= finalErrorMessege +"*"+"Product Name doesn't match with the Licence Co. for Line Item:"+licProtest.list[j] +"\n" ;
	validated=1;
}
//---------------------------------------[End:Anil Kumar::Date:08-07-2013]---------------------------

	// added by manisha to validate tax rate start
	var orderNo=document.getElementById('poNumber').value;
	var errormsg1 = jsonrpc.processes.validateTaxRate(orderNo);
	if(errormsg1!="")
	{
		alert(errormsg1);
		return false;
		
	}
	// added by manisha to validate tax rate end
	// added by manisha to validate tax rate start
	var orderNo=document.getElementById('poNumber').value;
	var status = jsonrpc.processes.validateDemoDays(orderNo);
	if(status==1)
	{
		alert('please enter Demo Days');
		return false;
		
	}
//  [0018] start
	if(isActionOrPublish==0 || isActionOrPublish==1)
	{
		var RR_subChangeTypeId=document.getElementById('hdnSubchangeType').value;
		var RR_orderNo = document.getElementById('poNumber').value;
		var lst = jsonrpc.processes.processForRRAutoTriggerValidation(RR_orderNo,RR_subChangeTypeId);
		if(lst.message == "Failed")
		{
			alert("Please upload backdated approval");
			return false;
		}
		else if(lst.message == "INTERNAL_ERROR")
		{
			alert("Some error has occured");
			return false;
		} 
	}
	// [0018] end 
	// added by manisha to validate tax rate end
	

	
		var jsData = 
					{
						poNumber:document.getElementById('poNumber').value,
						changeType:document.getElementById('HdnChangeTypeID').value,
						subChangeTypeId:document.getElementById('hdnSubchangeType').value
					};
	   // jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		// [003] START
		if(gb_roleID !=PM_ROLE && gb_roleID != SED_ROLE){
	    result = jsonrpc.processes.ValidatePODetail(jsData);
	    if(result!="")
	    {
	    	errorMsg=errorMsg + result+"\n";
			//count = count+1;
	    	validated=1;
	    	//alert(result);
	    }
		}
		
		// [003] END
		if(errorMsg.length >0 || finalErrorMessege != "")
	      {
		//alert(errorMsg);
			if(errorMsg.length >0)
				finalErrorMessege= finalErrorMessege +"*"+errorMsg ;
		alert(finalErrorMessege);
		updateStatus(0);
		return false;
	    } 
		 else {
		//--[15032013012]--start--// 	
		 	
			 //[014] start
		 	 giveAlertsOnValidate(orderNo) //if order has charges which can be re-disconnected(modify end date) then system has to give the alert
			 //[014] end
		 
			 
			 //added by [007]
			 if(isActionOrPublish==1 || isActionOrPublish==2)
				{				
					document.getElementById('isValidatePO').value=1;
					return false;
				}
			 //[007]
			 
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
		 	setTimeout(function() {
		 		attachWorkflow();
		 		updateStatus(1);
		 		document.getElementById('divSelectCurrencyId').style.display='none';
		 		redirectAmForApproval();
		 	}, 2000);
		 //--[15032013012]--end--// 	
			//myform.action="<%=request.getContextPath()%>/NewOrderAction.do?method=redirectToHome";
	    	// 083 END
		}
		showLayer();
		}catch(e)
	{
		alert('error code 666: '+ e.message);
	}
}

function loggTotalTimeTaken(orderno,start_time,total_elapsed_time,actiontype){
	jsonrpc.processes.loggTotalTimeTaken(orderno,start_time,total_elapsed_time,actiontype);
}

//Added by Saurabh for updating status against Tpomaster valid or incomplete 
function updateStatus (value)
{
	try{
	var orderNo = document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var validInvalid = jsonrpc.processes.updateStatus(orderNo,value);
    var flag=5;
    ViewServiceTree(flag);
	return true;
	}catch(e)
	{
		alert('error code 667: '+ e.message);
	}
}
//End By Saurabh

//-------------------------Validate PO----------------------------------------------

function fnViewProductCatelog(url)
{
	try{
	var hdnChangeTypeId=document.getElementById('hdnChangeTypeId').value;
	var hdnSubChangeTypeId=document.getElementById('hdnSubchangeType').value;
	var changeOrderNo=document.getElementById('poNumber').value;
	//Added By Ashutosh
	var path = contPath + "/ChangeOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=1"+"&hdnChangeTypeId="+hdnChangeTypeId+"&changeOrderNo="+changeOrderNo+"&hdnSubChangeTypeId="+hdnSubChangeTypeId+"&modeName="+ modeValue+"&subChangeTypeId="+ document.getElementById('subChangeTypeId').value+"&selectedServiceName="+selectedServiceName+"&crmAccountName="+ document.getElementById('crmAccNo').value+"&roleId="+gb_roleID;
	window.showModalDialog(path,window,"status:false;dialogWidth:2000px;dialogHeight:2000px");
	}catch(e)
	{
		alert('error code 668: '+ e.message);
	}
}

//[SUMIT007]
// isCharge Variable Added for Color Coding

var treeNodes;
function getTree(treeView,nextNode,nextLabel,nextUrl,isDisconnected,commercial,changeSubtypeID,billingFormat,isAdditionalNode,isCharge,createdInServiceId,guiServiceId,serviceDetailId)
{
	try{
	//Start[048]
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	
	if(isDisconnected == 1 && (changeSubtypeID ==3 ||  changeSubtypeID ==4 || changeSubtypeID ==15 ||  changeSubtypeID ==16) )
	{	
	   if(isCharge=="1")
			str = "<li><span ><img src='" + contPath + "/gifs/top/30.gif' title='Disconnected Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+serviceDetailId +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span ><img src='" + contPath + "/gifs/top/30.gif' title='Disconnected Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+serviceDetailId +"\') >"+ nextLabel + "</span>"	
			
	} 
	else if (isDisconnected == 0 && billingFormat !=3 && changeSubtypeID==3)
	{
		if(isCharge=="1")
			str = "<li><span > <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' ><input type='radio' name='rdo1' commercial='" + commercial + "'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+serviceDetailId +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span > <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' ><input type='radio' name='rdo1' commercial='" + commercial + "'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+serviceDetailId +"\') >"+ nextLabel + "</span>"	
			
	}
	 else if(isDisconnected == 0 && billingFormat ==3 && (changeSubtypeID==3 ||  changeSubtypeID ==4 || changeSubtypeID==15 ||  changeSubtypeID ==16)) 
	{
		if(isCharge=="1")	
			str = "<li><span ><input type='radio' name='rdo1' commercial='" + commercial + "'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+serviceDetailId +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"
		else
			str = "<li><span ><input type='radio' name='rdo1' commercial='" + commercial + "'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+serviceDetailId +"\') >"+ nextLabel + "</span>"	
			
	}
	else {
		if (isAdditionalNode == 1 && changeSubtypeID ==0 ) {
		//[041] start
		if(isCharge=="1")	
			str = "<li><span ><img src='" + contPath + "/gifs/top/star.gif' title='Additional Product'> <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  ><input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
		str = "<li><span ><img src='" + contPath + "/gifs/top/star.gif' title='Additional Product'> <input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  ><input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
		//[041] end 
		}
		else if(changeSubtypeID == 10 || changeSubtypeID == 19)
		{						
			
			 if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
		    //[041] start
		    if(isCharge=="1")
			str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Shifted Product'> <input type='radio' commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
			else
			str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Shifted Product'> <input type='radio' commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			if(isCharge=="1")
			str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Shifted Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"
			else
			str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Shifted Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			////[041] end
			}
		}
		else if(changeSubtypeID == 9 || changeSubtypeID == 18)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
			  {
			if(isCharge=="1")
				str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Downgraded Product'> <input type='radio' commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Downgraded Product'> <input type='radio' commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel  +"</span>"	
			}
			else
			{
			if(isCharge=="1")
					str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Downgraded Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
				str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Downgraded Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel  +"</span>"	
				
			}		
		}
		 //start [021] 
		else if(changeSubtypeID == 8 || changeSubtypeID == 17 )
		{
		    if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			if(isCharge=="1")
					str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Upgraded Product'> <input type='radio' commercial='" + commercial + "' disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Upgraded Product'> <input type='radio'commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else{
				if(isCharge=="1")
				str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Upgraded Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
			else
				str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Upgraded Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			
		}
		
		else if(changeSubtypeID == 12)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
		    if(isCharge=="1")
		    	str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Regularize Product'> <input type='radio' commercial='" + commercial + "' disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
				    	str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Regularize Product'> <input type='radio'commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			 if(isCharge=="1")
				    	str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Regularize Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
		    	str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Regularize Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
				
			}	
		
		}
		 //end [021] 
		else if(changeSubtypeID == 6 || changeSubtypeID == 13)
		{
			if(isCharge=="1")
				str = "<li><span ><img src='" + contPath + "/gifs/top/Suspend.jpg' title='Suspended Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"
			else
				str = "<li><span ><img src='" + contPath + "/gifs/top/Suspend.jpg' title='Suspended Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"
			
		}
		else if(changeSubtypeID == 7 || changeSubtypeID == 14)
		{
			if(isCharge=="1")
				str = "<li><span ><img src='" + contPath + "/gifs/top/resume.jpg' title='Resumed Product'> <input type='radio' name='rdo1' commercial='" + commercial + "'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"		
			else
				str = "<li><span ><img src='" + contPath + "/gifs/top/resume.jpg' title='Resumed Product'> <input type='radio' name='rdo1' commercial='" + commercial + "'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"		
				
		}
		else if(changeSubtypeID == 20)
		{
			if(billingFormat==3  && createdInServiceId!=guiServiceId )
		    {
			if(isCharge=="1")
				str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Network Change Product'> <input type='radio' commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Network Change Product'> <input type='radio' commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			if(isCharge=="1")
					str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Network Change Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
				str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Network Change Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			
			}
		}
		else if(changeSubtypeID == 5)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId )
		    {
			if(isCharge=="1")
				str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Rate Renewed Product'> <input type='radio' commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
						str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Rate Renewed Product'> <input type='radio' commercial='" + commercial + "'  disabled=true name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
			if(isCharge=="1")
						str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Rate Renewed Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
					else
				str = "<li><span ><img src='" + contPath + "/gifs/top/lock.gif' title='Rate Renewed Product'> <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
				
			}			
		} 
		else {		
			if(isCharge=="1")
				str = "<li><span class='folder'><input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' > <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"		
			else
				str = "<li><span class='folder'><input name='chkSPID' id='chkSPID"+nextNode+"' type='checkbox' value = "+ nextNode +"  onclick='checkUncheck("+ nextNode +")' > <input type='radio' commercial='" + commercial + "' name='rdo1'  id='rad_spId"+nextNode+ "' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(serviceDetailId) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"		
				
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
		//[041] start
		var guiServiceId=document.getElementById('hdnserviceid').value; 
		//[041] end
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{	 	
			parrentArray[i]=treeView.lstTreeItems.list[i].serviceProductParentId;
	 		childArray[i]=treeView.lstTreeItems.list[i].serviceProductChildId;
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL,isDisconnected,treeView.lstTreeItems.list[i].isCommercial,changeSubtypeID,billingFormat,treeView.lstTreeItems.list[i].isAdditionalNode,treeView.lstTreeItems.list[i].isChargePresent,createdInServiceId,guiServiceId,treeView.lstTreeItems.list[i].serviceChildId)+"</ul>";
		 	
		}
	}
	str=str+"</li>";
	return str;
	//End[048]
	}catch(e)
	{
		alert('error code 669: '+ e.message);
	}
}
//var iCountTreeNode=0;	
var global_currTreeNode;



//Fetch Zone List acording to Region
function fnGetZoneList()
{
	try{
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
//	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
	//[001] Start
  	if(document.getElementById("HdnChangeTypeID")==null)
  	{
  	
  	}
  	//[001] Start
	else if(document.getElementById("HdnChangeTypeID").value==4 && (document.getElementById("hdnSubchangeType").value==18 || document.getElementById("hdnSubchangeType").value==17 || document.getElementById("hdnSubchangeType").value==19) ){
   
   	//document.getElementById('addServiceLine').style.display="none";
   	//document.getElementById('CopyOrder').style.display="none";
   }
   }catch(e)
	{
		alert('error code 670: '+ e.message);
	}
} 

function TreeNode(nextNode,nextUrl , nextLabel,serviceDetailId,changeSubtypeID)
{
	try{
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
	var oTokens = unescape(nextLabel).tokenize('(',' ',true);
	productName=oTokens[0];
	document.getElementById('txtLineItemName').value=unescape(productName);
	document.getElementById('txtLineItemNo').value=nextNode;
	document.getElementById('hdnSubchangeType').value=changeSubtypeID;
	global_currTreeNode=nextNode;
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
	}catch(e)
	{
		alert('error code 671: '+ e.message);
	}	
}

function getTreeNodeCount(treeView,nextNode)
{
	try{
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
	}catch(e)
	{
		alert('error code 672: '+ e.message);
	}
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
function fncServiceTreeView()
{
	try{
	for(i=0;i<chkLength;i++)
		{
			if(document.getElementById("chkService"+i).checked==true)
			{
			checkedServiceNumber=i;
			}
		}
//document.getElementById('hdnservicetypeid').value=serviceTypeId;
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
			  	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
				var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
				
				document.getElementById('hdnserviceid').value = document.forms[0].txtServiceNo1.value;
				treeNodes = treeView;
				var url ="";
				var nodeText ="";
				var parentNodeId;
				var serviceDetailId;
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
				//alert(document.forms[0].txtServiceNo1.value); 
				 
				var treeString=getTree(treeView,parentNodeId,nodeText,"",0,commercial);
                document.getElementById("lblServiceDetail").innerHTML = "<b> Service Details (Total No of Nodes including parent : "  + getTreeNodeCount(treeView,parentNodeId) + ")</b>" ;
			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
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
					  //	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
						 var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
						  document.getElementById('hdnserviceid').value = document.getElementById('txtServiceNo'+newCounter).value;
						 treeNodes = treeView;
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
							 	commercial = treeView.lstTreeItems.list[i].isCommercial;
							 	break;
							}
						}
						global_firstNode=parentNodeId; 
						//alert(document.forms[0].txtServiceNo1.value); 
						 
						var treeString=getTree(treeView,parentNodeId,nodeText,"",0, commercial);
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
	}catch(e)
	{
		alert('error code 673: '+ e.message);
	}
}


//[014] start
function giveAlertsOnValidate(orderNo)
{
	 var jsData = {
			orderNo:orderNo,
			scenario:['REDISCONNECTION_NOTIFY','']
	};
	var result = jsonrpc.processes.fetchOrderAlerts(jsData);
	if(result.isAnyReDisconnectionAlert==1){
		alert(result.reDisconnectionAlertMsg);
	}	
}
//[014] end 

function ServiceTreeViewAfterDisconnection(serviceTypeId)
{
	try{
	var chkLength1=document.forms[0].chkService.length;
	if(chkLength1==undefined)
	{
	chkLength1=1;
	}
		for(i=0;i<chkLength1;i++)
		{
			if(document.getElementById("chkService"+i).checked==true)
			{
			checkedServiceNumber=i;
			}
		}
		
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
			if(document.getElementById('hdnSubchangeType').value!="undefined")
				{
				subChangeTypeId=document.getElementById('hdnSubchangeType').value
				}
			
			document.getElementById('browser').innerHTML= "";
			    var jsData = 
			    {
			    	serviceId:document.forms[0].txtServiceNo1.value,
					logicalSINo:document.forms[0].logicalSINo.value,
					poNumber:poNumber,
					changeTypeId:document.getElementById('HdnChangeTypeID').value,
					subChangeTypeId:subChangeTypeId
			    };
			    //End[019]
			  //	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
				var commercial = "";
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
					 	//[039] Start : Assign value in serviceDetailId for Copy Service product
					 	serviceDetailId = treeView.lstTreeItems.list[i].serviceChildId;
					 	commercial = treeView.lstTreeItems.list[i].isCommercial;
					 	///serviceDetailId1=serviceDetailId;
					 	//[039] End 
					 	break;
					}
						
					
				}
				global_firstNode=parentNodeId;	 
				guiServiceId=document.getElementById('hdnserviceid').value; 
				var treeString=getTree(treeView,parentNodeId,nodeText,"",isDisconnected,commercial,changeSubtypeID,billingFormat,isAdditionalNode,isChargePresent,createdInServiceId,guiServiceId,serviceDetailId);
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
						if(document.getElementById('hdnSubchangeType').value!="undefined")
						{
							subChangeTypeId=document.getElementById('hdnSubchangeType').value
						}
						
						document.getElementById('browser').innerHTML= "";
					    var jsData = 
					    {
					    	serviceId:document.getElementById('txtServiceNo'+ i).value,
							logicalSINo:document.forms[0].logicalSINo.value,
							poNumber:poNumber,
							changeTypeId:document.getElementById('HdnChangeTypeID').value,
							subChangeTypeId:subChangeTypeId
					    };
					 //End[019]
					  //	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
						 var treeView = jsonrpc.processes.ViewServiceTreeAfterDisconnection(jsData);
						 treeNodes = treeView;
						  document.getElementById('hdnserviceid').value = document.getElementById('txtServiceNo'+ i).value;
						selectedServiceName=document.getElementById('txtServiceName'+ i).value;
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
						var commercial = "";
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
							 	//[039] Start : Assign valu inserviceDetailId for Copy Service product
							 	serviceDetailId = treeView.lstTreeItems.list[i].serviceChildId;
							 	commercial = treeView.lstTreeItems.list[i].isCommercial;
					 			//serviceDetailId1=serviceDetailId;
					 			//[039] End 
							 	break;
							}
								
						}
						 global_firstNode=parentNodeId;
						//alert(document.forms[0].txtServiceNo1.value); 
						 guiServiceId=document.getElementById('hdnserviceid').value; 

						var treeString=getTree(treeView,parentNodeId,nodeText,"",isDisconnected,commercial,changeSubtypeID,billingFormat,isAdditionalNode,isChargePresent,createdInServiceId,guiServiceId,serviceDetailId);
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
	    var selectedNode=document.getElementById('hdnServiceProductId').value;
		if(selectedNode!="" && $("#rad_spId"+selectedNode).is(":visible"))
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
		}catch(e)
		{
			alert('error code 674: '+ e.message);
		}
	}
	

//------------------------------------------Conatct Tab------------------------------



function DeleteContact()
  {
  	try{
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
	  		alert("No Row to Delete");
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
				  //		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
				  		
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
	    									calculatesino();
	    									unCheckedSelectAllContact();
						}
					    else
						{
							
							alert('you cannot delete all saved Contact and Address Details');
							return false;
						}
			        }
			        
			    else
			    {
			    	conatctTab.deleteRow();
	    			addressTab.deleteRow();
	    			calculatesino();
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
				 //  jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
				   		            				calculatesino();
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
 		            		calculatesino();
 		            		unCheckedSelectAllContact();
	            		}
	            	}		 
		       } 
	}
	
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
		if(countDelete==1 && rowlen>1)
		 {
		     alert("please select one row to delete");
		 }
	}catch(e)
	{
		alert('error code 675: '+ e.message);
	}
           
}
function copyServiceProduct() 
{
	try{
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
	
	var local_isServiceSelected = 0;
	var chkLength=document.forms[0].chkService.length;
	
	
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
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
	
	local_PrdCatelogValue=global_currTreeNode;
	try{
	var jsData =			
	    {
		  serviceProductID:local_PrdCatelogValue,
		  copyProductValues:document.forms[0].txtCopyProduct.value
		};
    //	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
			alert(javascriptExceptionMessage);
			showJavascriptException(e,javascriptExceptionShow);
		}
	}catch(e)
	{
		alert('error code 676: '+ e.message);
	}
}
function fnGetQuoteNo()
{
	try{
	var test;
    var combo = document.getElementById("quoteNo");	
    var accountID=document.getElementById('accountID').value;
    var iCountRows = combo.length;  
   	for (i = 1; i <  iCountRows; i++)
   	{
       combo.remove(1);
   	}
   	var jsData =			
    {
		accountID:accountID
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populateQuoteNoList(jsData);  
    for(j=0;j<test.list.length;j++)
	    {
	    	
		   	var option = document.createElement("option");
		  	option.text = test.list[j].quoteNo;
			option.value = test.list[j].quoteNo;
			option.title = test.list[j].quoteNo;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	   var x=document.getElementById("quoteNo");   
	   x.selectedIndex = 0;
	}catch(e)
	{
		alert('error code 677: '+ e.message);
	}
}
//<!-- [013] Ends -->
function fnCalculateEndDate(val){

	try{
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
	}catch(e)
	{
		alert('error code 678: '+ e.message);
	}
}
function DownLoadTemplateProductCatelog()
{
	try{
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
    myform.action=contPath + "/NewOrderAction.do?method=downloadTemplateExcelForPrdCatelog";
    showLayer();
    myform.submit();
    }catch(e)
	{
		alert('error code 679: '+ e.message);
	}
}

function uploadFile(){
	try{
	
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
		myForm.action=contPath + "/NewOrderAction.do?method=uploadPlanExcel";
		showLayer();
		myForm.submit();
	}catch(e)
	{
		alert('error code 680: '+ e.message);
	}
}

function uploadFile_Page(){
	try{
	var path1 = contPath + "/NewOrderAction.do?method=gotoUploadPage";
   	window.showModalDialog(path1,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	//var myForm=document.getElementById('searchForm');
	//myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=gotoUploadPage";
	//myForm.submit();
	}catch(e)
	{
		alert('error code 681: '+ e.message);
	}
}

function chkChangeData() {
	try{
	if(document.getElementById('HdnChangeTypeID').value != ""){
		fetchFeildList();
	}	
	}catch(e)
	{
		alert('error code 682: '+ e.message);
	}
}
//add by anil for publish order for change order
function fnPublish()
{
	try{
		//alert("Data of Order Entry will be inserted in Fx billing engine.");
		var myForm=document.getElementById('searchForm');
	    var poNum=document.searchForm.poNumber.value;
	    
	    var errorMsg = "" ; 
	    //Start [007]
	  /*  var selectedServiceList=jsonrpc.processes.getallServicesSelected(poNum,sessionId);	
		//alert(selectedServiceList+"   test "+" "+poNum+" "+sessionId);
		if(selectedServiceList!=''){
		var serviceForValidate = jsonrpc.processes.getServiceForValidate(poNum,selectedServiceList);
	    }*/
		//End [007]
		//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	    var oldCode=jsonrpc.processes.checkOldCodeActive();
		if(oldCode==1){
		var test = jsonrpc.processes.validateFeild(poNum,sessionid);
	   	if(test != 'SUCCESS')
	   	{
	     	alert(test);
	     	return false;
	   	}
    
		//var errorMsg = "" ; commented and define above [007]
		var jsData = 
					{
						poNumber:document.getElementById('poNumber').value,
						changeType:document.getElementById('HdnChangeTypeID').value,
						subChangeTypeId:document.getElementById('hdnSubchangeType').value
					};
	   // jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		// [003] START
		if(gb_roleID !=PM_ROLE && gb_roleID != SED_ROLE){
	    result = jsonrpc.processes.ValidatePODetail(jsData);
	    if(result!="")
	    {
	    	errorMsg=errorMsg + result+"\n";
			//count = count+1;
	    	//validated=1;
	    	//alert(result);
	    }
		}
		// [003] END
		}else{
			
			validateOrder("2", true, "");
			var isValidatePO =	document.getElementById("isValidatePO").value;	
			//if Validate successfully
			
			if(isValidatePO==0)
			{
				return false;
			}
			
			
			
		}
		
		var var_start_publish_part1=new Date();
		var orderNo=document.getElementById('poNumber').value;
		//-------[Start:086]-----------------------------------------------------------------
		var noFXSICompValidResult = jsonrpc.processes.validateNOFXSIComponent(document.getElementById('poNumber').value);
		if(noFXSICompValidResult !="" && noFXSICompValidResult !=null){
			alert(noFXSICompValidResult);
			return;
		}
		//-------[End:086]-------------------------------------------------------------------
		if(errorMsg.length >0 )
		{
			//alert(errorMsg);
			//finalErrorMessege= finalErrorMessege +"*"+errorMsg ;
			alert(errorMsg);
			updateStatus(0);
			return false;
		}
		else {
			updateStatus(1);
		}
		
		try{
   	 		var var_end_publish_part1=new Date();
			var total_time_elapsed=var_end_publish_part1.getTime()-var_start_publish_part1.getTime();
			loggTotalTimeTaken(orderNo,var_start_publish_part1,total_time_elapsed,'PUBLISH_PART1');
		}catch(e){alert('Logging of validation recoding time failed!!');}
		var url = contPath + "/viewOrderAction.do?methodName=fnPublishOrder&publishChangeOrd=CHANGE&roleid="+gb_roleID;
		url = url + "&PONum="+poNum;
		url = url + "&serviceList=''";
		url = url + "&publishList=''";
		myForm.action=url;
		showLayer();
		myForm.submit();
		}catch(e)
	{
		alert('error code 683: '+ e.message);
	}
}
//following function FNBILLINGTRIGGER5 AND FNBILLINGTRIGGER add by MANISHA for Billing Trigger change order//

function fnBillingTrigger5(chiWindow)
{
	try{
		// alert(2);
		chiWindow.close();
		fnBillingTrigger();
	}catch(e)
	{
		alert('error code 684: '+ e.message);
	}
}
function fnBillingTrigger()
{
	try{
	    poNum=document.searchForm.poNumber.value;
		var url = contPath + "/viewOrderAction.do?methodName=fnBillingTriggerInfx";
		{
		var da=new Date();
			url = url + "&orderNo="+poNum+"&"+da;
			features="scrollbars=yes,resizable=yes,width=800,height=540,left=100,top=100";
		//window.open(url,'xyz',features);
			window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
		}
	}catch(e)
	{
		alert('error code 685: '+ e.message);
	}
}

/*function fnBillingTriggerEnable()
 {
 	try{
    poNumber=document.getElementById('poNumber').value;
	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");			
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
	}catch(e)
	{
		alert('error code 686');
	}
			
 }*/
//following function add by anil for charge summary change order
function fnChargeSummary()
{
	try{
	poNum=document.searchForm.poNumber.value;
	var d=new Date();
	var url = contPath + "/viewOrderAction.do?methodName=fnChargeSummary"+"&"+d.getTime();
	{
		url = url + "&PONum="+poNum;
		//features="scrollbars=yes,resizable=yes,width=800,height=540,left=100,top=100";
		//window.open(url,'xyz',features);
		window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
	}catch(e)
	{
		alert('error code 687: '+ e.message);
	}
}
//add by anil for fetch Order no from TM6_NEWORDER_STATUS for check publish status
function checkPublishStatus()
{
	try{
	OrderNoBilling=document.getElementById('poNumber').value;
	
	//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");			
     OrderNoTM6 = jsonrpc.processes.checkPublishStatus(OrderNoBilling);
     
     if(document.getElementById('Page_5').style.display=="block")
     {
     	document.getElementById('saveIcon').style.display="none";
     
     }
	if(OrderNoBilling!=0 && (OrderNoTM6==OrderNoBilling))
	{	
		document.getElementById('saveIcon').style.display="none";
		document.getElementById('btnAddMoreService').style.display="none";	
		document.getElementById('linesTabButtons').style.display="none";
	}
	else
	{
	 	
		document.getElementById('btnAddMoreService').style.display="block";
		document.getElementById('linesTabButtons').style.display="block";	
	}
	//start [005]
	document.getElementById('hdnShowAttachIcon').value=showAttachIcon;//"<%= request.getAttribute("showAttachIcon")%>";	
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
	}catch(e)
	{
		alert('error code 689: '+ e.message);
	}
}

function cancelWorkflow()
{
	try{
		var myForm=document.getElementById('searchForm');
	   // var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");		
	 	var jsData =			
    	{
			poNumber:document.getElementById('poNumber').value
		};	
       var isCancelled = jsonrpc.processes.cancelWorkflow(jsData);
		
	   if(isCancelled==1)
	   {
	   	 alert('Workflow cancelled successfully and Sent to Account Manager');
	   	 myForm.action=contPath + "/defaultDraftChangeOrdAction.do?method=viewIncompleteChangeOrder";
		 showLayer();
		 myForm.submit();
	   }
	   else
	   {
	   	 alert('Error while Cancelling Workflow.');
	   	 return false;	
	   }			
	}catch(e)
	{
		alert('error code 690: '+ e.message);
	}
}

//Added By Ashutosh
//Start
function checkUncheck(nextNode)
{
	try{
		var chkLength=document.getElementsByName('chkSPID');	
		if(chkLength[0].checked == true){
			if(global_firstNode==chkLength[0].value){
				for(i=1;i<chkLength.length;i++){
					chkLength[i].checked=true;
				}
			}
		}else{
			if(chkLength.length!=null){	
				if(nextNode!=global_firstNode){
					chkLength.checked=true;
				}else{
					for(i=1;i<chkLength.length;i++){
						chkLength[i].checked=false;
					}
				}
			}else{					
				chkLength.checked=false;
			}
		}
		for(j=1;j<parrentArray.length-1;j++){	
			if(document.getElementById('chkSPID'+parrentArray[j])!=null){
				if(nextNode==parrentArray[j]){
					if(document.getElementById('chkSPID'+childArray[j]) != null && document.getElementById('chkSPID'+parrentArray[j]).checked==false){
						document.getElementById('chkSPID'+childArray[j]).checked=false;
					}else if(document.getElementById('chkSPID'+childArray[j])!= null && document.getElementById('chkSPID'+parrentArray[j]).checked==true){
						document.getElementById('chkSPID'+parrentArray[j]).checked=true;
						document.getElementById('chkSPID'+childArray[j]).checked=true;
					}
				}else{
					if(document.getElementById('chkSPID'+childArray[j]) != null &&	document.getElementById('chkSPID'+parrentArray[j]).checked==true){
						document.getElementById('chkSPID'+childArray[j]).checked=true;
					}
				}
			}
		}
	}catch(e){
		alert('error code 691: '+ e.message);
	}
}
//End
function disconnectProducts(){
	try{

	var chkLength=document.getElementsByName('chkSPID');
	var spidList='';	
	var countChked =0;
	for(i=0;i<chkLength.length;i++) 
		{
			if(chkLength[i].checked == true)
			{	
				countChked = countChked + 1;
			}
		}
	if(countChked == 0){
			alert('Please select one or more Products');
			return false;
		}
		else
		{
		for(i=0;i<chkLength.length;i++) 
		{
			if(chkLength[i].checked == true)
			{				
				if(chkLength[i].value!==global_firstNode)
				{
					if(spidList =='') {
						spidList = chkLength[i].value ;
					} else {
						spidList = spidList + ',' + chkLength[i].value ;
					}
					
				}
			
			}
		
		
		}
		
		if(spidList.length==0)
       	{
       		alert("All products are already Processed ");
       	
       		return false;
       	}
		var myForm=document.getElementById('searchForm');
	   // var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");		
	 
	if(confirm('Do you want to continue ?'))
		{
		
				
       var isDisconnected = jsonrpc.processes.disconnectProducts(spidList,document.getElementById('poNumber').value,document.getElementById('hdnSubchangeType').value,document.getElementById('hdnserviceid').value);
      
       if(isDisconnected == 0){
       	alert('Action Taken Successfully');
       	//alert('Product(s) Disconnected');
       //alert('hdnservicetypeid ' + 	document.getElementById('hdnservicetypeid').value );
       	ServiceTreeViewAfterDisconnection(document.getElementById('hdnservicetypeid').value);
       	//Meenakshi : Added for CBR_20120806-XX-07984
	//Meenakshi : Else If added tom handle DMX Validation
       } else if(isDisconnected == 5){
       				if(document.getElementById('hdnSubchangeType').value == 6 || document.getElementById('hdnSubchangeType').value == 13)
       				{
       					alert('Service Line Item Cannot be Suspended. Please Select Entire LSI for Suspension');
       				}
       				else
       				{
			       		alert('Service Line Item Cannot be Disconnected. Please Select Entire LSI for Disconnection');
			       	}
       		return false;
       }
       	//Meenakshi : End
       	else {
       	return false;
       }
      
      	}
		else
		{
		return false;
		}
       }
	}catch(e)
	{
		alert('error code 692: '+ e.message);
	}	
}
//[013] Start
//Add Child Node
 function checkpage()
 {
 	
  	var stage = document.getElementById("stageName").value;
    var orderNo=document.getElementById('poNumber').value;
   // var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
	var serviceCount = jsonrpc.processes.checkPublishServiceStatus(orderNo,userId);
	if(document.getElementById('isDemoOrder').value=='ON')
	 {
	   document.getElementById('chkIsDemo').checked ="checked";
	   document.getElementById('noOfDaysForDemo').className ="inputBorder1";
	   	
	 }
	//--[083]--//
	//cancel button should be not be display when order entry page open first time. 
	//So, here adding an other condition that prevent to display cancel button.
	//if(serviceCount==orderNo)
	
	/*
	   if(orderNo !=null && orderNo != "" && orderNo != 0 && (serviceCount==orderNo))
	   {
		 document.getElementById('cancelIcon').style.display="block";	
	   }
	*/
	
    /*By Vijay: Some above code is commented regarding 'Cancel' button displaying functionality.
	  following 'if block' added to enable cancel button while order's stage is 'New'
	  or 'AM' and login role by '1'(AM role). 
	  In other words, order can be cancel only if order is in AM's bin.
	*/  
	if(orderNo !=null && orderNo != "" && orderNo != 0)
	{
		if((stage=="AM" || stage=="New") && (roleName=="1"))
		{
			document.getElementById('cancelIcon').style.display="block";
		}
	}
  
    if(flag3==2)
	{
	  //document.getElementById('hdnTabVal').value=2;
	  //document.getElementById('Page_1').style.display="none";
	  //document.getElementById('Page_2').style.display="block";
	  //--[084]--start--//
	  //selectCurrentTab('li2');
	  //--[084]--end--//
	  changeTab('li2',2,'none','block','none', 'none','none');
	
	}
	
	if(flag3==3)
	{
	 
	  //document.getElementById('Page_1').style.display="none";
	  //document.getElementById('Page_2').style.display="none";
	  if(document.getElementById('HdnChangeTypeID').value==3 || document.getElementById('HdnChangeTypeID').value==1){
	  
		  /*document.getElementById('hdnTabVal').value=5;
	   document.getElementById('Page_3').style.display="none";
	   document.getElementById('Page_5').style.display="block";
	   //--[084]--start--//
	   selectCurrentTab('li5');*/
	   changeTab('li5',5,'none','none', 'none','block','none');
	   //--[084]--end--//
	  
	      }
	  else{
	  	enableDisablePOCheckbox();
	     document.getElementById('hdnTabVal').value=3;
	     /*document.getElementById('Page_3').style.display="block";
	     //--[084]--start--//
		 selectCurrentTab('li3');*/
	     changeTab('li3',3,'none','none', 'block', 'none','none');
		 //--[084]--end--//
	  
	  
	  }    
	  
	
	
	}
	
    
  if(flag3==5)
	{
	  /*document.getElementById('hdnTabVal').value=5;
	  document.getElementById('Page_1').style.display="none";
	  document.getElementById('Page_2').style.display="none";
	  document.getElementById('Page_3').style.display="none";
	  document.getElementById('Page_5').style.display="block";
	  //--[084]--start--//
	  selectCurrentTab('li5');*/
	  changeTab('li5',5,'none','none', 'none','block','none');
	  //--[084]--end--//
	}
	
//


	
	//Vijay. add condition for demo-regularize order
		if(document.getElementById('HdnChangeTypeID').value ==5
			|| (document.getElementById('HdnChangeTypeID').value ==4 && document.getElementById('HdnSubChangeTypeID').value ==12)){
			//document.getElementById('addServiceLine').style.display="none";
			//document.getElementById('CopyOrder').style.display="none";
			
			//document.getElementById('productCatetogIcon').style.display="none";			
			
				if(document.getElementById('suspendDiv')!=null){
					document.getElementById('suspendDiv').style.display="none";
				}
				if(document.getElementById('resumeDiv')!=null){
					document.getElementById('resumeDiv').style.display="none";
				}
				if(document.getElementById('disconnectionDiv')!=null){
					document.getElementById('disconnectionDiv').style.display="none";
				}	
		}
		
		else if ( document.getElementById('HdnChangeTypeID').value ==1)
		{
			
			//document.getElementById('addServiceLine').style.display="none";
			//document.getElementById('CopyOrder').style.display="none";
			if(document.getElementById('addChildNodeIcon')!=null)
			{
				document.getElementById('addChildNodeIcon').style.display="none";
			}
			if(document.getElementById('productCatetogIcon')!=null)
			{
				document.getElementById('productCatetogIcon').style.display="none";
			}
				document.getElementById('suspendDiv').style.display="none";
				document.getElementById('resumeDiv').style.display="none";
				document.getElementById('disconnectionDiv').style.display="none";
				document.getElementById('deleteProductDiv').style.display="none";
			//[039] Start : For Change Tpye Network Change
			if(document.getElementById('copyProductIcon')!=null)
			{
				document.getElementById('copyProductIcon').style.display="none";
				document.getElementById('txtCopyProduct').style.display="none";
			}
			//[039] End 	
							
		}
		
			else if (document.getElementById('HdnChangeTypeID').value ==3 )
		{
			
			//document.getElementById('addServiceLine').style.display="none";
			//document.getElementById('CopyOrder').style.display="none";
			
			
			if(document.getElementById('addChildNodeIcon')!=null)
			{
				document.getElementById('addChildNodeIcon').style.display="none";
			}
			if(document.getElementById('productCatetogIcon')!=null)
			{
				document.getElementById('productCatetogIcon').style.display="none";
			}
			//document.getElementById('copyProductIcon').style.display="none";
			if(document.getElementById("hdnSubchangeType").value==6 && document.getElementById('suspendDiv')!=null)
			{
				document.getElementById('suspendDiv').style.display="block";
				document.getElementById('resumeDiv').style.display="none";
				document.getElementById('disconnectionDiv').style.display="none";
			}
			if(document.getElementById("hdnSubchangeType").value==7 && document.getElementById('resumeDiv')!=null)
			{
				document.getElementById('suspendDiv').style.display="none";
				document.getElementById('resumeDiv').style.display="block";
				document.getElementById('disconnectionDiv').style.display="none";
			}
			if(document.getElementById("hdnSubchangeType").value==3 || document.getElementById("hdnSubchangeType").value==4)
			{ 
				if(document.getElementById('disconnectionDiv')!=null)
				{
				document.getElementById('suspendDiv').style.display="none";
				document.getElementById('resumeDiv').style.display="none";
				document.getElementById('disconnectionDiv').style.display="block";
				}
			}
			
			//[039] Start : For Change Type Disconnection
			if(document.getElementById('copyProductIcon')!=null)
			{
				document.getElementById('copyProductIcon').style.display="none";
				document.getElementById('txtCopyProduct').style.display="none";
			}
			//[039] End 
			
			
		}
		
		//Add condtion by vijay 
		if (document.getElementById('HdnChangeTypeID').value ==4){
			if(document.getElementById('productCatetogIcon') !=null ){
				document.getElementById('productCatetogIcon').style.display="none";
			}
			if (document.getElementById("hdnSubchangeType").value==12){
					document.getElementById('addChildNodeIcon').style.display="block";
			}
		}
		
		/*
		 * Vijay.
		 * Calling a method that set the default style/value of attribute
		 */
			defaultAttributeValue();		
			
			
		//Vijay. comment follwing code becoz now demo order treated as raterenewal order so 
		//this condtion merged with rate renewal case above
		
		
		 /* else if (document.getElementById('HdnChangeTypeID').value ==4)
		  {
 //start [021] 
		   		//document.getElementById('addServiceLine').style.display="none";
		   		//document.getElementById('CopyOrder').style.display="none";
				document.getElementById('addChildNodeIcon').style.display="none";
				document.getElementById('suspendDiv').style.display="none";
				document.getElementById('resumeDiv').style.display="none";
				document.getElementById('disconnectionDiv').style.display="none";
				document.getElementById('productCatetogIcon').style.display="none";
				//[039] Start : For Change Type Demo
				if(document.getElementById('copyProductIcon')!=null)
				{
					document.getElementById('copyProductIcon').style.display="none";
					document.getElementById('txtCopyProduct').style.display="none";
				}
				//[039] End 
		  
				if( document.getElementById("hdnSubchangeType").value==13  ) {
				document.getElementById('suspendDiv').style.display="block";
				} 
				
				else if (document.getElementById("hdnSubchangeType").value==14){
					document.getElementById('resumeDiv').style.display="block";
				 	
				}
				else if (document.getElementById("hdnSubchangeType").value==12){
					document.getElementById('addChildNodeIcon').style.display="block";
				 	
				}
				
				else if (document.getElementById("hdnSubchangeType").value==15 || document.getElementById("hdnSubchangeType").value==16){
					document.getElementById('disconnectionDiv').style.display="block";
				 	
				}
				
				else if (document.getElementById("hdnSubchangeType").value==18 || document.getElementById("hdnSubchangeType").value==17 || document.getElementById("hdnSubchangeType").value==19){
				 	//document.getElementById('CopyOrder').style.display="block";
				 	//document.getElementById('addServiceLine').style.display="block";
				 	document.getElementById('addChildNodeIcon').style.display="block";
				 	document.getElementById('productCatetogIcon').style.display="block";
				 	//[039] Start : For Change Type DEMO
					if(document.getElementById('copyProductIcon')!=null)
					{
						document.getElementById('copyProductIcon').style.display="block";
						document.getElementById('txtCopyProduct').style.display="block";
					}
					//[039] End 
				}
				//else if (document.getElementById("hdnSubchangeType").value==12){
				 	
				//}
				 //end [021] 
		
		}
		*/
		//Vijay. end of comment 
		
		//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
		/*else if (document.getElementById('HdnChangeTypeID').value ==2 && document.getElementById("subChangeTypeId").value==10)
		{
			document.getElementById('productCatetogIcon').style.display="none";
			//[039] Start : For Sub Change Type Shifting
			if(document.getElementById('copyProductIcon')!=null)
			{
				document.getElementById('copyProductIcon').style.display="none";
				document.getElementById('txtCopyProduct').style.display="none";
			}
			//[039] End 
				
		} 						
		*/
		


//  [088]	Start
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
//	[088]	End 
 }

function addChildNode()
{
	try{
    flag=5;
	if(document.getElementById('HdnChangeTypeID').value ==1)
	{
	return false;
	}
	var orderNo=document.getElementById('poNumber').value
	var serviceID;
	var serviceTypeID;
	if(document.forms[0].chkService==undefined)
	{
		alert("Please Add a Service!!");
		return false;
	}
	
	var local_isServiceSelected = 0;
	var chkLength=document.forms[0].chkService.length;
		
	newCounter=1;
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			local_isServiceSelected = 1;
			serviceID=document.getElementById('txtServiceNo'+newCounter).value;
			//Meenakshi : Change for sub node issue
			serviceTypeID=document.getElementById('txtServiceTypeID'+newCounter).value;
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chkService[i].checked==true)
			{
				local_isServiceSelected = 1;
				serviceID=document.getElementById('txtServiceNo'+newCounter).value;
				//Meenakshi : Change for sub node issue
				serviceTypeID=document.getElementById('txtServiceTypeID'+newCounter).value;
				break;
			}
		}
	}
	
	if(local_isServiceSelected==0)
	{
		alert("Please Select a Service");
		return false;
	}
	
	//test for selection of prdCatelog
	if(document.forms[0].rdo1==undefined)
	{
		alert("No Line Item Present!!");
		return false;
	}
	var local_isPrdCatelogSelected = 0;
	var local_PrdCatelogValue;
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
		alert("Please Select a LIne Item");
		return false;
	}
	
		//--------------------[Validate SubNode is Allow with LineItem or not Start]-----------------				
		var jsData1 =			
   		{   			
   			serviceProductID:Number(global_currTreeNode)   		
		};
	//	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var isAddSubNodeAllow = jsonrpc.processes.fetchAddSubNodeAllowStatus(jsData1);
		
		if(isAddSubNodeAllow=="N")
		{
			alert("You cannot add child node for this line item of service "+ serviceID);
			return false;
		}		
		//--------------------[Validate SubNode is Allow with LineItem or not End]-----------------	
		
	local_PrdCatelogValue=global_currTreeNode;
	try
	{
		var jsData =			
	    {
		  serviceProductID:local_PrdCatelogValue
		};
	   // jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		result = jsonrpc.processes.addChildProduct(jsData,orderNo,serviceID);
		if(result !=null)
		{
			alert(result.message);
	        //top.consoleRef=window.open('','myconsole','width=300,height=250')
	        //top.consoleRef.document.writeln(result.message);
			 
			if(result.messageId=='ENTRY_COPIED')
			{
				//refresh tree 
				//fncServiceTreeView();
				//ViewServiceTree(flag);
				countService=1;
    			//drawTable();
    			//Meenakshi : Change for sub node issue
    			ServiceTreeViewAfterDisconnection(serviceTypeID);
			}
			//else
			//{
				//alert('Entry Coping Failed. Please Try Again.');
			//}
			}
		}
		catch(e)
		{
			alert(javascriptExceptionMessage);
			showJavascriptException(e,javascriptExceptionShow);
		}
	}catch(e)
	{
		alert('error code 694: '+ e.message);
	}
}
//[013] End

//Start [045]
function CopyOrder() 
{
	try{
		var stage = document.getElementById("stageName").value;
		if((UserRoleName==4 & stage!= "New" ) || stage=="SED" || stage=="Billing Trigger" || stage=="Partial Publish")
		{
			alert("You are not authorised to use this functionality");
			return false;
		}
		var path = contPath + "/ChangeOrderAction.do?method=getOrderToBeCopied";
	   	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:600px");
	}catch(e)
	{
		alert('error code 695: '+ e.message);
	}
}
//End [045]

var isCtrl = false; 
document.onkeyup=function(){ 
			if(event.keyCode == 17) isCtrl=false; 
		}
document.onkeydown=function(e){
	try{
	var stage;
	var orderType;
	var modeValue="editON";
	
	//[037]Start
	//To disable backspace key doing Back on browser 
	if(KeyCheck()==false) return false; 
	//[037] End
		
	if(event.keyCode == 17) isCtrl=true; 
	if(event.keyCode == 122 && isCtrl == true) 
	{
		isCtrl=false;
		return false; 
	} 
	else if(event.keyCode == 122 && isCtrl == false)
	{
	  	if(document.getElementById('poNumber').value== "" || document.getElementById('poNumber').value == null)
		{
			event.keyCode = 0;
			event.returnValue = false;
			return false;
		}
	  	
	  	var orderNo=document.getElementById('poNumber').value;
		//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
	  	alert("2CUserID " + userId + "roleName " + roleName);
		var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	  	event.keyCode = 0;
		event.returnValue = false;
		
	  	if(orderDetails.list.length>0)
		{
			if(orderNo == orderDetails.list[0].orderNumber)
			{
				document.forms[0].action="./defaultNewOrderAction.do?method=goToNewOrder";
				document.forms[0].submit();
			}
		}	
		else
		{
			document.getElementById("poNumber").readOnly=false;
			document.getElementById("poNumber").value="";
			document.getElementById("poNumber").styleClass="inputBorder2";
			document.getElementById("poNumber").focus();
			event.keyCode = 0;
			event.returnValue = false;
		}	
	}
	//start[046]
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
   }   //end[046]
   }catch(e)
	{
		alert('error code 696: '+ e.message);
	}
}

//start hideSelectCurrency() add by anil for hide select currency after successful validate
function hideSelectCurrency()
{
	try{
		if(modeValue=="editON")
		{
			document.getElementById('divSelectCurrencyId').style.display='none';
		}
		else
		{
			document.getElementById('divSelectCurrencyId').style.display='block';
		}
		if((document.getElementById('hdnSubchangeType').value!=0))
		{
			document.getElementById('divSelectCurrencyId').style.display='none';
		}
	}catch(e)
	{
		alert('error code 697: '+ e.message);
	}
}
//end hideSelectCurrency()
function fnViewCancelAndCopyReport(a,b)
{
	return false;
}


function calculatesino()
{
	try{
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
	}catch(e)
	{
		alert('error code 698: '+ e.message);
	}
}

function legalEntityLoading()
{	
	try{
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		  		
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
	if(document.getElementById('hdnTabVal').value==3 || document.getElementById('Page_3').style.display == 'none')
	{
		//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
		var lstEntity = jsonrpc.processes.getEntity1();
		var tr, td, i, j, oneRecord;  		   			
	    for(j=0;j<lstEntity.list.length;j++)
    	{        	
    		var combo = document.getElementsByName('entityId')[0];	    		
	    	var opt = document.createElement("option");
	   		opt.text = lstEntity.list[j].entityName;	   		
			opt.value = lstEntity.list[j].entityId;			  		
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
	}catch(e)
	{
		alert('error code 699: '+ e.message);
	}
}
//HYPR03042013004-------Changes Made For PM Linking with Account
function  populateAccountWith()
{
	document.getElementById("channelPartnerName").value="";
	document.getElementById("channelPartnerId").value="";
	document.getElementById("channelpartnerCode").value="";
	document.getElementById("fieldEngineer").value="";
	document.getElementById("fieldEngineerId").value="";
	
	try{
	if(document.getElementById('crmAccNo').value.length > 0)
					{
		
					  if(ValidateTextField(document.getElementById('crmAccNo'),path,'Account No')==false)
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
				
				//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
				var lstAccount = jsonrpc.processes.getAccountDetailsWithSorting(jsData);
					if(lstAccount.list.length==1)
						{
						document.searchForm.accountID.value = lstAccount.list[0].accountID;
						document.searchForm.crmAccountNo.value = lstAccount.list[0].crmAccountId;
						document.searchForm.accountName.value = lstAccount.list[0].accountName;
						//document.searchForm.accphoneNo.value = lstAccount.list[0].accphoneNo;
						document.searchForm.lob.value = lstAccount.list[0].lob;
                        //[006] Start
						document.searchForm.serviceSegment.value = lstAccount.list[0].serviceSegment;
                        //[006] End
						document.searchForm.accountManager.value =lstAccount.list[0].accountManager;
						document.searchForm.AttributeVal_2.value = lstAccount.list[0].acmgrPhno;
						document.searchForm.AttributeVal_3.value = lstAccount.list[0].acmgrEmail;
						document.searchForm.region.value = lstAccount.list[0].region;
						document.searchForm.regionId.value=lstAccount.list[0].regionIdNew;
						document.searchForm.shortCode.value=lstAccount.list[0].m6ShortCode;
						document.searchForm.collectionMgr.value=lstAccount.list[0].collectionMgr;
						document.searchForm.osp.value = lstAccount.list[0].osp;
						document.searchForm.circle.value = lstAccount.list[0].circle;//added on 9-jan-2013,CIRCLE work
						document.searchForm.category.value = lstAccount.list[0].category;//[087]						
						
						document.searchForm.projectManager.value = lstAccount.list[0].projectManager;
						document.searchForm.projectManagerID.value = lstAccount.list[0].projectManagerID;
			  			document.searchForm.groupName.value = lstAccount.list[0].groupName;

						
						fillProjectManagerCombo(lstAccount.list[0].accountID);
						fillZoneCombo(lstAccount.list[0].regionIdNew);
					//	fillChannelPartnerMaster()
					
						//fnGetQuoteNo();
						//fnGetOpportunityId();
						return false;
						
						}
						else
				 		{
				 		popitup('SelectAccount');
				 	//	fillChannelPartnerMaster()
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
                    //[006] Start
					document.searchForm.serviceSegment.value = "";
                    //[006] End
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
					document.searchForm.category.value = "";//[087]
					document.searchForm.groupName.value = ""
					var comboZone = document.searchForm.zone;
				    var iCountRows = comboZone.length;
				    for (i = 1; i <  iCountRows; i++)
				    {
				    	comboZone.remove(1);
				    }
					return false;
		 		}else
				{				
				 	var jsData =
					{								
						accountIDString:document.getElementById('crmAccNo').value
					};				  
						//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
						var lstAccount = jsonrpc.processes.getAccountDetails(jsData);
						if(lstAccount !=null){
							if(lstAccount.list.length == 0)
							{
						 		alert('Account not found!Please sync account');
									document.searchForm.crmAccountNo.value = "";
									document.searchForm.accountID.value = "";
									document.searchForm.accountName.value = "";
									//document.searchForm.accphoneNo.value = "";
									document.searchForm.lob.value = "";
                                    //[006] Start
									document.searchForm.serviceSegment.value = "";
                                    //[006] End
									document.searchForm.accountManager.value = "";
									document.searchForm.projectManager.value = "";
									document.searchForm.AttributeVal_2.value = "";
									document.searchForm.AttributeVal_3.value = "";
									document.searchForm.region.value = "";
									document.searchForm.zone.value = "";
									document.searchForm.shortCode.value="";
									document.searchForm.collectionMgr.value="";
									document.searchForm.osp.value = "";
									document.searchForm.circle.value = "";//added on 9-jan-2013,CIRCLE work
									document.searchForm.category.value = "";//[087]
									document.searchForm.groupName.value = "";
									return false;
							}
							if(lstAccount.list.length==1)
							{
								document.searchForm.accountID.value = lstAccount.list[0].accountID;
								document.searchForm.crmAccountNo.value = lstAccount.list[0].crmAccountId;
								document.searchForm.accountName.value = lstAccount.list[0].accountName;
								//document.searchForm.accphoneNo.value = lstAccount.list[0].accphoneNo;
								document.searchForm.lob.value = lstAccount.list[0].lob;
                                //[006] Start
								document.searchForm.serviceSegment.value = lstAccount.list[0].serviceSegment;
                                //[006] End
								document.searchForm.accountManager.value =lstAccount.list[0].accountManager;
								document.searchForm.AttributeVal_2.value = lstAccount.list[0].acmgrPhno;
								document.searchForm.AttributeVal_3.value = lstAccount.list[0].acmgrEmail;
								document.searchForm.region.value = lstAccount.list[0].region;
								document.searchForm.regionId.value=lstAccount.list[0].regionIdNew;
								document.searchForm.shortCode.value=lstAccount.list[0].m6ShortCode;
								document.searchForm.collectionMgr.value=lstAccount.list[0].collectionMgr;
								document.searchForm.osp.value = lstAccount.list[0].osp;
								document.searchForm.circle.value = lstAccount.list[0].circle;//added on 9-jan-2013,CIRCLE work
								document.searchForm.category.value = lstAccount.list[0].category;//[087]									
								document.searchForm.projectManager.value = lstAccount.list[0].projectManager;
								document.searchForm.projectManagerID.value = lstAccount.list[0].projectManagerID;
			  					document.searchForm.groupName.value = lstAccount.list[0].groupName;
								
								fillZoneCombo(lstAccount.list[0].regionIdNew);
							//	fillChannelPartnerMaster()
								//fnGetQuoteNo();
								//fnGetOpportunityId();
											
							
								return false;
							}
						}
						else{
							alert("Error[-1212]:Some error has occurred!!");
							return false;
						}
				}
		 	}
	}catch(e)
	{
		alert('error code 700: '+ e.message);
	}
}
//HYPR03042013004-------Changes Made For PM Linking with Account	

function fillZoneCombo(regionId)
{
	try{
	     var tr, td, i, j, oneRecord;
	     var zoneList;
	     var combo = document.searchForm.zone;
	     var iCountRows = combo.length;
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
	}catch(e)
	{
		alert('error code 701: '+ e.message);
	}
}
function fillProjectManagerCombo(accID)
{
	try{
	    var tr, td, i, j, oneRecord;
	    var projectManagerNameList;
	//	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var accId=unescape(accID);
		projectManagerNameList = jsonrpc.processes.getProjectManagerNameList(accId);
	   	document.searchForm.projectManagerID.value=projectManagerNameList.list[0].projectManagerID;
	   	document.searchForm.projectManager.value=projectManagerNameList.list[0].projectManager;
	}catch(e)
	{
		alert('error code 702: '+ e.message);
	}
}
function fnOpenChangeOrder()
{
	try{
		var myForm=document.getElementById('searchForm');
		myForm.action=contPath + "/defaultChangeOrderAction.do?method=goToChangeOrder";
		showLayer();
		myForm.submit();
	}catch(e)
	{
		alert('error code 703: '+ e.message);
	}
}

var hook=true;
window.onbeforeunload = function() 
{
	try{
    if (hook)
    {
    	//Start[036]
    	var orderNo=document.getElementById('poNumber').value;
	//	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    var isClosed = jsonrpc.processes.closeOrderAlreadyOpened(orderNo,userId,roleName);
	    //End[036] 
	    if ((window.event.clientX < 0) || (window.event.clientY < 0) || (window.event.clientX < -80))
	    {
	    {
	      return "This will cause losing any unsaved data ! "
	    } 
	    }
	}
	}catch(e)
	{
		alert('error code 704: '+ e.message);
	}
}
function unhook() 
{
	try{
     hook=false;
     }catch(e)
	{
		alert('error code 705: '+ e.message);
	}
}

//Start[031]
function redirectAmForApproval()
{
	try{
	var stage;
	var orderType;
	var accessMode;
	var createdBy;
	var modeValue="editON";
	
	var orderNo=document.getElementById('poNumber').value;
		
	//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");			
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
	
	if(orderType=="Change" && stage=="AM")
	{
		if(shortCode==stage)
		{
			if(createdBy==employeeId)
			{
				document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();
			}
		}	
	}
	}catch(e)
	{
		alert('error code 706: '+ e.message);
	}
}
//End[031]

//Start[032]
function fnGetOpportunityId()
{
	try{
	var test;
	var combo = document.getElementById("opportunityId");	
	var accountID=document.getElementById('accountID').value;
	var sourceName=document.getElementById('sourceName').value;
	var quoteNo=document.getElementById('quoteNo').value;
	if(sourceName=='CRMQUOTE')
	{
		if(quoteNo!=0)
		{
			//accountID=0;	
		}
	}
	else
	{
		quoteNo=0;
	}	
	
	var iCountRows = combo.length;  
	for (i = 1; i <  iCountRows; i++)
	{
		combo.remove(1);
	}
	var jsData =			
	{
		accountID:Number(accountID),
		quoteNo:quoteNo
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.fetchOpportunityIdList(jsData);  
    for(j=0;j<test.list.length;j++)
	{
		var option = document.createElement("option");
		option.text = test.list[j].opportunityId;
		option.value = test.list[j].opportunityId;
		option.title = test.list[j].opportunityId;
		try 
		{
			combo.add(option, null); //Standard
		}
		catch(error) 
		{
			combo.add(option); // IE only
		}
	}
	var x=document.getElementById("opportunityId");   
	if(sourceName=='CRMQUOTE')
	{
		if(test.list.length>0)
		{
			x.selectedIndex = 1;
		}
		else
			x.selectedIndex = 0;
	}
	else
	{
		x.selectedIndex = 0;
	} 
	}catch(e)
	{
		alert('error code 707: '+ e.message);
	}
}
//End [032]

function openInViewOrder()
{
	try{
		//Start[036]
		var orderNo=document.getElementById('poNumber').value;
	//	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    var isClosed = jsonrpc.processes.closeOrderAlreadyOpened(orderNo,userId,roleName);
		//End[036]
		
		document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+document.getElementById('poNumber').value+"&modeName=viewMode";
		document.forms[0].submit();
	}catch(e)
	{
		alert('error code 708: '+ e.message);
	}
}
function openOrderAfterApproval(){	
	try{
		var orderNo=document.getElementById('poNumber').value;
	    var isClosed = jsonrpc.processes.closeOrderAlreadyOpened(orderNo,userId,roleName);
	    document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+document.getElementById('poNumber').value+"&modeName=editON";
		document.forms[0].submit();
	}catch(e){
		alert('error code 111: '+ e.message);
	}
}
//Validation for VCS and L3 MPLS Disconnection Order
function validateVCS_L3MPLSForDisconnectOrder()
{
	try{
	isVCS_MAP_WITH_L3MPLS=true;
	var orderNo=document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	var VCS_MAP_WITH_L3MPLS=jsonrpc.processes. validateVCS_L3MPLSForDisconnectOrder(orderNo);	
	if(VCS_MAP_WITH_L3MPLS!='')  
	{
		alert(""+VCS_MAP_WITH_L3MPLS);
		isVCS_MAP_WITH_L3MPLS=false;
		return false;
	}
	}catch(e)
	{
		alert('error code 709: '+ e.message);
	}
}
//[013] START
/*function validateMBIC_ON_CC_Discoonnect()
{
	try{
		isValidateMBIC_ON_CC_Discoonnect=true;
		var orderNo=document.getElementById('poNumber').value;
		//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
		var MBIC_MAP_WITH_CC=jsonrpc.processes. validateMBIC_ON_CC_Discoonnect(orderNo);
		if(MBIC_MAP_WITH_CC!='')  
		{
			alert(""+MBIC_MAP_WITH_CC);
			isValidateMBIC_ON_CC_Discoonnect=false;
			return false;
		}
	}catch(e)
	{
		alert('error code 710: '+ e.message);
	}
}*/

//[013] END

//START POC POINTS As On Date 29-09-2012
function getComponentLineItem()
{
	try{
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
	}catch(e)
	{
		alert('error code 711: '+ e.message);
	}
}
function getMultipleDummyServices()
{
	try{
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
	}catch(e)
	{
		alert('error code 712: '+ e.message);
	}
}
//END POC POINTS As On Date 29-09-2012
function gamNFormulaAttachedWithOrder()
{
	try{
	 var orderNo=document.getElementById('poNumber').value;
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
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
	}catch(e)
	{
		alert('error code 713: '+ e.message);
	}
}

//below method changed from document.getElementById to both byId and by Form to handle single row if previous rows are deleted
function autoFillSalutation(counter)
{	
	try{
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
				salutationName:controlSalutationName.value
			};				  
		//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
  }catch(e)
	{
		alert('error code 714: '+ e.message);
	}
 }
//below method changed from document.getElementById to both byId and by Form to handle single row if previous rows are deleted
function autoFillContactType(counter)
{
	try{
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
 
 /*	if(ValidateTextField(controlContactType,path,'Contact Name')==false)
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
		//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
  }catch(e)
	{
		alert('error code 715: '+ e.message);
	}
}


function  autoFillCurrency()
	{
		try{
	
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
				
				//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
						//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
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
		 	
		}catch(e)
		{
			alert('error code 716: '+ e.message);
		}
	}

function getServicelineCount()
{
	try{
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
		var iChangeServiceCount = 0;

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
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="Order Cancelled In M6")
			iM6CancelledServiceCount++;
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="Service Cancelled")
			iCRMCancelledServiceCount++;
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="CancelAndCopy")
			iCancelandCopyServiceCount++;
			
			if(document.getElementById('serviceStatus'+(iCount+1)).value=="CHANGE")
			iChangeServiceCount++;
			
		}
		var abc = "<b> Service Type (Total No Of Service : "+tabServiceLineCount;
		if(iNewServiceCount>0)
			abc=abc+" ( NEW : " +iNewServiceCount+ " )";
		if(iM6ProcessingServiceCount>0)
			abc=abc+" ( Processing In M6 : " +iM6ProcessingServiceCount+ " )";
		if(iBillingTriggerServiceCount>0)
			abc=abc+" ( Ready To Billing Trigger : " +iBillingTriggerServiceCount+ " )";
		if(iBillingTriggerEndServiceCount>0)
			abc=abc+" ( Billing Trigger Ends : " +iBillingTriggerEndServiceCount+ " )";
		if(iM6CancelledServiceCount>0)
			abc=abc+" ( Order Cancelled In M6 : " +iM6CancelledServiceCount+ " )";
		if(iCRMCancelledServiceCount>0)
			abc=abc+" ( Service Cancelled : " +iCRMCancelledServiceCount+ " )";
		if(iCancelandCopyServiceCount>0)
			abc=abc+" ( CancelAndCopy : " +iCancelandCopyServiceCount+ " )";
		if(iChangeServiceCount>0)
			abc=abc+" ( CHANGE : " +iChangeServiceCount+ " )";
					
		abc = abc + ")</b>";
		//CellContents = abc;
		//document.getElementById('lblServicetype').innerHTML = abc;
	}catch(e)
	{
		alert('error code 717: '+ e.message);
	}
}

//start :CLEP Enable Disable Order Entry
function enableDisableCLEP(){
	try{
		var orderNo=document.getElementById('poNumber').value;	
		var stateid=getOrderStateforClep(orderNo,path);		
		clepState=stateid;	
		orderEntryEnableDisable('CHANGEORDER',stateid, null);
	}catch(e)
	{
		alert('error code 718: '+ e.message);
	}
}
//end clep
//Added by anil to prevent any action after success aproval from aproval window
function showOpqChangeLayer()
{
	try{
		showLayer();
	}catch(e)
	{
		alert('error code 719: '+ e.message);
	}
}

function enableDisablePOCheckbox()
{
	try{
	var myForm=document.getElementById('searchForm');
	var poLength=document.forms[0].chkSelectPODetail.length;
	var poNumber = document.getElementById('poNumber').value;
	//var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
	var poList = jsonrpc.processes.getPOListForOrder(poNumber);
	var orderPOList=poList.list.length;
	var counter=0;
	if(poLength==undefined)
	{counter=0;
		for (i =0; i<orderPOList; i++)
		{
			if(counter==0)
			{
				if(document.forms[0].poDetailNo.value==poList.list[i].podetailID)
				{
					document.forms[0].chkSelectPODetail.disabled=true;
					counter++;
				}
			}
		}		
		if(counter==0)
		{
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
	}catch(e)
	{
		alert('error code 720: '+ e.message);
	}	
}
//------------------------------------[ CRM Account Refreshing Start ]-------------------------------
function refreshTransaction(sourceType)
{
	try{
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
		if(sourceType=="ACCOUNT"){
			refreshAccountBCPDispatchCRM();
		}else{
			//refreshBCPDispatchCRM();
		}
	}catch(e)
	{
		alert('error code 721: '+ e.message);
	}
}

function handleRefreshAccount(data){
	try{
	if(data=="1"){		
			alert("Account synced successfully!!");
			
			var crmActNo=document.getElementById('crmAccNo').value;
			var orderNo=document.getElementById('poNumber').value;
			
			var jsData =
			{								
				accountIDString:crmActNo
			};	
			//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var lstAccount = jsonrpc.processes.getAccountDetails(jsData);
			
		    document.searchForm.accountID.value = lstAccount.list[0].accountID;
			document.searchForm.crmAccountNo.value = lstAccount.list[0].crmAccountId;
			document.searchForm.accountName.value = lstAccount.list[0].accountName;
			//document.searchForm.accphoneNo.value = lstAccount.list[0].accphoneNo;
			document.searchForm.lob.value = lstAccount.list[0].lob;
            //[006] Start
			document.searchForm.serviceSegment.value = lstAccount.list[0].serviceSegment;
            //[006] End
			document.searchForm.accountManager.value =lstAccount.list[0].accountManager;
			document.searchForm.AttributeVal_2.value = lstAccount.list[0].acmgrPhno;
			document.searchForm.AttributeVal_3.value = lstAccount.list[0].acmgrEmail;
			document.searchForm.region.value = lstAccount.list[0].region;
			if(orderNo=="" || orderNo=="0"){
				document.searchForm.regionId.value=lstAccount.list[0].regionIdNew;
				document.searchForm.shortCode.value=lstAccount.list[0].m6ShortCode;
			}else{
				document.searchForm.m6ShortCode.value=lstAccount.list[0].m6ShortCode;
			}
			document.searchForm.collectionMgr.value=lstAccount.list[0].collectionMgr;
			document.searchForm.osp.value = lstAccount.list[0].osp;
			document.searchForm.circle.value = lstAccount.list[0].circle;//added on 9-jan-2013,CIRCLE work
			document.searchForm.category.value = lstAccount.list[0].category;//[087]									
			document.searchForm.projectManager.value = lstAccount.list[0].projectManager;
			document.searchForm.projectManagerID.value = lstAccount.list[0].projectManagerID;
			document.searchForm.groupName.value = lstAccount.list[0].groupName;
			if(orderNo==""||orderNo=="0"){								
				fillZoneCombo(lstAccount.list[0].regionIdNew);
			}
			if(orderNo==""||orderNo=="0"){
			//	fillChannelPartnerMaster()
			  }
			
		}else
		{		
			alert(data);
		}
	}catch(e)
	{
		alert('error code 722: '+ e.message);
	}	
}
function handleRefreshBCPDispatch(data){
	try{
		if(data=="1"){
			alert("BCP and Dispatch Address synced successfully!!");
		}else{
			alert(data);
		}	
	}catch(e)
	{
		alert('error code 734: '+ e.message);
	}	
}
function refreshAccountBCPDispatchCRM()
{	
	try{
		var crmAccountNo=document.getElementById('crmAccNo').value;
		$.ajax({
			    url: contPath + "/NewOrderAction.do?method=refreshCRMAccountBCPDispatch&accountno="+crmAccountNo,
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
	}catch(e)
	{
		alert('error code 723: '+ e.message);
	}               		                
}
//------------------------------------[ CRM Account Refreshing End ]-------------------------------
function fnExportChargeSummary()
{
	try{
		var myForm=document.getElementById('searchForm');
		myForm.action=contPath + "/viewOrderAction.do?methodName=fnExportChargeSummary"+"&PONum="+document.searchForm.poNumber.value;
		myForm.submit();
	}catch(e)
	{
		alert('error code 724: '+ e.message);
	}
}




        function reposHead(e) {
        	try{
	            var h = document.getElementById('headscroll');
	           	h.scrollLeft = e.scrollLeft;
	            var f = document.getElementById('divfrozen');
	            f.scrollTop = e.scrollTop;
            }catch(e)
			{
				alert('error code 725: '+ e.message);
			}
        }
        
         function reposHeadService(e) {
         	try{
	            var h = document.getElementById('headscrollService');
	            h.scrollLeft = e.scrollLeft;
	            var f = document.getElementById('divfrozenService');
	            f.scrollTop = e.scrollTop;
            }catch(e)
			{
				alert('error code 726: '+ e.message);
			}
        }
        
        function reposHorizontal(e) {
        	try{
	           	var h = document.getElementById('headscroll');
	            var c = document.getElementById('contentscroll');
	            h.scrollLeft = e.scrollLeft;
	            c.scrollLeft = e.scrollLeft;
            }catch(e)
			{
				alert('error code 727: '+ e.message);
			}
        }
        
         function reposHorizontalService(e) {
         	try{
	            var h = document.getElementById('headscrollService');
	            var c = document.getElementById('contentscrollService');
	            h.scrollLeft = e.scrollLeft;
	            c.scrollLeft = e.scrollLeft;
            }catch(e)
			{
				alert('error code 728: '+ e.message);
			}
        }
        
        function reposVertical(e) {
        	try{
	            var h = document.getElementById('divfrozen');
	            var c = document.getElementById('contentscroll');
	            h.scrollTop = e.scrollTop;
	            c.scrollTop = e.scrollTop;
           	}catch(e)
			{
				alert('error code 729: '+ e.message);
			}
        }
        
        function reposVerticalService(e) {
        	try{
	            var h = document.getElementById('divfrozenService');
	            var c = document.getElementById('contentscrollService');
	            h.scrollTop = e.scrollTop;
	            c.scrollTop = e.scrollTop;
           	}catch(e)
			{
				alert('error code 730: '+ e.message);
			}
        }

//[085] Start
function viewLSILinking()
{
	try{
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
		var path = contPath + "/reportsAction.do?method=viewLSIMapping";		
		window.open(path,"LSIMapping",params);
	}catch(e)
	{
		alert('error code 731: '+ e.message);
	}
}
//[085] End
        
//Start Saurabh for keeping 2D product Values of Ib2b in sync with CRM	
function insertUpdate2DProductValues()
{
	try{
		var transactionStatus ;
		transactionStatus = jsonrpc.processes.insertUpdate2DProductValues();
		alert(transactionStatus);
	}catch(e)
	{
		alert('error code 732: '+ e.message);
	}
}
//End
//Start Saurabh for viewing cancelled Hardware line in that order
function fnViewCancelledHardwareLineDetails()
{
	try{
	var path = contPath + "/NewOrderAction.do?method=DisplayCancelledhardwareLineDetails";	
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:400px");
	}catch(e)
	{
		alert('error code 733: '+ e.message);
	}
}
//End
//lawkush Start
function refreshOpportunity(updationStatus)
{
	try{
		//var updationStatus ;
		//var accountId=0;
		//accountId=document.getElementById('accNo').value;
		var crmAccNo=document.getElementById('crmAccNo').value;
		//alert("accountId  >> "+accountId);
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		//updationStatus = jsonrpc.processes.refreshOpportunity(accountId);
		if(updationStatus==-1){
			alert("Opportunity Id Values Insertion failed for Account No. "+crmAccNo);
			}
		else if(updationStatus==1){
			alert("Opportunity Id Synced with CRM for Account No. "+crmAccNo);
			}
		else if(updationStatus==-2){
			alert("No Opportunity Id Present in CRM with Account No. "+crmAccNo);
			}
		else if(updationStatus==-3){
			alert("Failed to connect with CRM Database ");
		}else{
		alert("Opportunity not updated");
	}
	}catch(e)
	{
		alert('error code 735: '+ e.message);
	}
		
}
function setShowCal()
{
	$(":text[showcal='date']").each(function() {
		var thisEl = $(this).attr("id");
	 $("#"+thisEl ).datepicker({dateFormat: 'dd/mm/yy',showOn: 'none', changeMonth: true,changeYear: true });
	});
}
//Method to set the tab fields based on the form opened
function setFormFields(newFormOpened)
{
	try{
		if(null != newFormOpened && !(""==newFormOpened)){
			fetchFeildList(newFormOpened);
			if('Product Catelog' == newFormOpened)
				DrawTable('SERVICENO','SERVICELINETABLE');
		}	
		newFormOpened = "";	
	}catch(e)
	{
		alert('error code 145: '+ e.message);
	}
}

function fnUpdateOpportunity()
{	
	try{
		var accountId=0;
		accountId=document.getElementById('accNo').value;
		$.ajax({
			    url: contPath + "/NewOrderAction.do?method=updateOpportunity&accountId="+accountId,
	                    dataType: "text",
	                    type: "Get",
	                    contentType: "application/json; charset=utf-8",
	                    dataFilter: function(data) { return data; },
	                    success: refreshOpportunity, 
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
	}catch(e)
	{
		alert('error code 736: '+ e.message);
	} 
}

//set the default value of attribute 
function defaultAttributeValue(){ 
	var attCount = document.getElementById('attCount').value
	var isDifferentialAttPosition = 0;
	var isDifferentialFound = false;
	
	//finding the position of isDifferential attribute
	for(i=attCount; i>=1; i--){
		// this attribute id is using for 'Is Differential'
		if(document.getElementById('hdnAttributeID_'+i).value =="39"){
			isDifferentialFound = true;
			isDifferentialAttPosition = i;
			break;
		}  
	}
	
	if(isDifferentialFound){
		//[0019]
		var isDifferentialEnable;
		try{
	    //	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			isDifferentialEnable = jsonrpc.processes.getIsDifferentialEnable(document.getElementById('HdnChangeTypeID').value,document.getElementById('changeTypeId').value);
	    }
	    catch(e){
	    	alert("exception     " + e);
	    }
		//if(document.getElementById('HdnChangeTypeID').value == 5 || document.getElementById('HdnChangeTypeID').value == 2 || document.getElementById('changeTypeId').value == 5 || document.getElementById('changeTypeId').value == 2){ //in rate revision and in solution change case
	    if(isDifferentialEnable=='Enable'){
    		document.getElementById('AttributeVal_'+isDifferentialAttPosition).className = "inputBorder1";
    		document.getElementById('AttributeVal_'+isDifferentialAttPosition).readOnly = false; // make this enable to edit
	    }
	    else{
    		document.getElementById('AttributeVal_'+isDifferentialAttPosition).className = "inputBorder2";
    		document.getElementById('AttributeVal_'+isDifferentialAttPosition).value = 'N';
    		document.getElementById('AttributeVal_'+isDifferentialAttPosition).readOnly = true; // make this readonly
	    }
	}
}
//[008] start
function searchLineTab(var_sortByColumn,var_pageName){
	
	document.getElementById("id_paging_goToPage").value="";
	pageNumber=1;
	DrawTable(var_sortByColumn,var_pageName);
}
//[008] end
//[009] Start PROJECT SATYAPAN
function ispTagValidation(){
	var ispTagging = document.getElementById("ispTagging");
	var ocs = document.getElementById('hdnOrderCreationSourceId').value;
	var ctId=document.getElementById('changeTypeID').value;
	//Validation will run only for non-clep & nondisconnection orders selected ISP tagging values as "YES".
	if((ispTagging !=undefined && ispTagging.selectedIndex=="1") && (ocs !=undefined && ocs!=ORDER_CREATION_SOURCE)){
		if(ctId !=undefined && ctId !=3){
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
	}
	return true;
}
//[009] End PROJECT SATYAPAN




function channelPartnerTagValidation(){
	var channelMasterTagging = document.getElementById("channelMasterTagging");
	var ocs = document.getElementById('hdnOrderCreationSourceId').value;
	var ctId=document.getElementById('changeTypeID').value;
	//Validation will run only for non-clep orders selected channelMasterTagging values as "YES".
	if(channelMasterTagging !=undefined && channelMasterTagging.selectedIndex=="1" && ocs!=ORDER_CREATION_SOURCE){
		if(ctId !=undefined){
		var channelPartnerCode = document.getElementById("channelpartnerCode");
		var channelPartnerName = document.getElementById("channelPartnerName");
		
		if(channelPartnerCode !=undefined && channelPartnerCode.value == ""){
			alert("Please Select Channel Partner!!");
			channelPartnerName.focus();
			return false;
			}
	
		}	
		
	}
	return true;
}


function  clearChannelPartner()
{
//	alert('2');
//	document.getElementById("divChannelPartner").selectedIndex="0";
	document.getElementById("channelPartnerName").value="";
	document.getElementById("channelPartnerId").value="";
	document.getElementById("channelpartnerCode").value="";
	document.getElementById("fieldEngineer").value="";
	document.getElementById("fieldEngineerId").value="";	

/*	var channelMasterTagging = document.getElementById("channelMasterTagging");
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
	//	document.getElementById('divChannelPartner').style.visibility ="hidden";
		document.getElementById('channelPartnerName').style.visibility ="hidden";
		document.getElementById('channelPartnerId').style.visibility ="hidden";
		document.getElementById('channelpartnerCode').style.visibility ="hidden";
		document.getElementById('fieldEngineer').style.visibility ="hidden";
		document.getElementById('fieldEngineerId').style.visibility ="hidden";
		document.getElementById('fieldEngineerlbl').style.visibility ="hidden";
		document.getElementById('channelPartnerlbl').style.visibility ="hidden";
	}
}
