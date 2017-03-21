//*************************************************************************************************************************************
	/*Created By: Ashutosh Singh
	  Date:		  04-Jun-2012
	  Purpose:	  For Copy Service Product in New and Change Order.
	*/
//**************************************************************************************************************************************


//for Copy Product in new and Change Order
//[15032013017] Rampratap 14-03-13 added for count tolal line items selected.
//[001] Saurabh Singh 	Code done for fixing defect MASDB00194472
//[002] Vipin Saharia	Code to chk if PO present while copying lines in change order (for PM n SED)

function copyServiceProductForNewAndChangeOrder(path,sessionId,orderType) 
{	
	
    var stage = document.getElementById('stageName').value;
    if(stage=="Partial Publish" || stage=="Published" || stage=="Billing trigger")
    {
    	alert("You are not authorised to use this functionality");
    	return false;
    } 
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
	var local_isServiceSelected = 0;
	var chkLength=document.forms[0].chkService.length;
	
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
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
		alert("Please Select Radio Button");
		return false;
	}	
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
	//Meenakshi: Commenting condition to check player befor copying screen. as Screen can be added without Player.
	// Implemented in CBR_20120806-XX-07984
	/*if(local_serviceDetailId1 == 346)
	{
		var jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		var countPlayerInService = jsonrpc.processes.fnDmxCountPlayerInService(ServiceId_dmxvalidation);
		if(countPlayerInService.list[0] == 0 )
		{
			alert('Please add DigitalSignage-HWPlayer first !!');
			return false;
	 	}
	}*/
	//[002] START VIPIN
	if(document.getElementById('orderType').value=="Change" &&(gb_roleID==2 || gb_roleID==4)){ //VIPIN
    	var copyAllowed = chkIsPoDetailsPresent(local_PrdCatelogValue);
    	if(copyAllowed==false){
    		alert("You cannot copy selected product as no PO selected for this line");
    		return false;
    	}
    }
	//[002] END VIPIN
	var orderNo=document.getElementById('poNumber').value;
	try
	{
		var jsData =			
	    {
		  serviceProductID:local_PrdCatelogValue,
		  copyProductValues:document.forms[0].txtCopyProduct.value,		  
		  poNumber:orderNo
		};
	    jsonrpc = new JSONRpcClient(path + "/JSON-RPC");     	
		result = jsonrpc.processes.copyServiceProductForNewAndChangeOrder(jsData,sessionId);
		if(result !=null)
		{
			alert(result.message);
			if(result.messageId=='ENTRY_COPIED')
			{
				//refresh tree for new and change
				if(orderType=='Change')
					ServiceTreeViewAfterDisconnection(document.getElementById('hdnservicetypeid').value);
				else
					fncServiceTreeView();	
			}			
		}
	}
	catch(e)
	{
		alert("Error : "+e)
		//alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}
//[15032013017] Start
function countitems(path,sessionId,orderType)
{
var local_isServiceSelected = 0;
var local_PrdCatelogValue;
var local_serviceDetailId1;
var local_isPrdCatelogSelected = 0;
//============= for change order
	if(orderType=='Change')
	{
	if(document.forms[0].chkService==undefined || document.forms[0].chkService == null)
	{
		alert("No Service Present");
		return false;
	}
	if(document.forms[0].rdo1==null)
	{
		alert("Please Select a service !!");
		return false;
	}
	if(document.forms[0].rdo1==undefined)
	{
		alert("Please Select a service !!");
		return false;
	}
	
	chkLength=document.forms[0].rdo1.length;
	//alert("chkLength :"+chkLength);
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
			if(document.forms[0].rdo1[i].checked==true)
			{
			local_isPrdCatelogSelected = 1;
				//alert("local_isPrdCatelogSelected  :"+local_isPrdCatelogSelected );
				break;
			}
		}
	}
	
	if(local_isPrdCatelogSelected==0)
	{
	alert("Please Select a line item");
		return false;
	}
	}
	//============== change order ended
	// =========== for view order start====
	else if(orderType=='View')
	{
var chkLength;
if(document.forms[0].chkService==undefined)
	{
		alert("No Service Present");
		return false;
	}
	else if (document.forms[0].chkService!=undefined && document.forms[0].chkService.length==undefined)
	{
			if(document.forms[0].chkService.checked==true)
			{
			local_isServiceSelected = 1;
				
			}
	}
	else if (document.forms[0].chkService!=undefined && document.forms[0].chkService.length!=undefined)
	{
	chkLength=document.forms[0].chkService.length;
	for(i=0;i<chkLength;i++)
		{
			var myform=document.getElementById("searchForm");
			if(document.forms[0].chkService[i].checked==true)
			{
				local_isServiceSelected = 1;
				break;
			}
		}
	}
	
	if(local_isServiceSelected==0)
	{
		alert("Please Select A Service and then line item");
		return false;
	}
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
			if(document.forms[0].rdo1[i].checked==true)
			{
				local_isPrdCatelogSelected = 1;
				break;
			}
		}
	}
	if(local_isPrdCatelogSelected==0)
	{
		alert("Please Select a line item");
		return false;
	}
	
	}
	//========= view order ended
	//====== for new order start
	else{
	var chkLength;
	if(document.forms[0].chkService==undefined)
		{
		alert("No Service Present");
		return false;
		}
	else if (document.forms[0].chkService!=undefined && document.forms[0].chkService.length==undefined)
	{
			if(document.forms[0].chkService.checked==true)
			{
			local_isServiceSelected = 1;
				
			}
	}
	else if (document.forms[0].chkService!=undefined && document.forms[0].chkService.length!=undefined)
	{
	chkLength=document.forms[0].chkService.length;
	for(i=0;i<chkLength;i++)
		{
			var myform=document.getElementById("searchForm");
			if(document.forms[0].chkService[i].checked==true)
			{
			local_isServiceSelected = 1;
				break;
			}
		}
	}
	if(local_isServiceSelected==0)
	{
		alert("Please Select A Service and then line item");
		return false;
	}
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
			if(document.forms[0].rdo1[i].checked==true)
			{
				local_isPrdCatelogSelected = 1;
				break;
			}
		}
	}
	if(local_isPrdCatelogSelected==0)
	{
		alert("Please Select a line item");
		return false;
	}
	
	}
	local_PrdCatelogValue=global_currTreeNode;
	var orderNo=document.getElementById('poNumber').value;
	
	try
	{
		var jsData =			
	    {
		  serviceProductID:local_PrdCatelogValue,
		  //serviceDetailID:local_serviceDetailId1,
		  poNumber:orderNo,
		  orderType:orderType
		};
	    jsonrpc = new JSONRpcClient(path + "/JSON-RPC");     	
		result = jsonrpc.processes.countSelectedLineItems(jsData,sessionId);
		if(result !=null)
		{
			alert(result.message);
		}
	}
	catch(e)
	{
		alert("Error : "+e)
	}
	//[15032013017] END
}
function copyServiceProductForNewOrder(path,sessionId,orderType)
{
		var myform=document.getElementById("searchForm");
	    var stage = document.getElementById('stageName').value;
	    if(stage=="Partial Publish" || stage=="Published" || stage=="Billing trigger"){
	    	alert("You are not authorised to use this functionality");
	    	return false;
	    } 
		if(document.forms[0].chkService==undefined){
			alert("Please Add a Service!!");
			return false;
		}
		if(document.forms[0].txtCopyProduct.value == ""){
		    alert("Please Insert values for Product Copy!!");
			return false;
		}
		if(!checkdigits(document.forms[0].txtCopyProduct)){
		  return false;
		}
		var isServiceSelected = 0;
		var chkLength=document.forms[0].chkService.length;
		if(chkLength==undefined){
			if(document.forms[0].chkService.checked==true){
				if(document.getElementById('chk'+0).value=="Yes"){
			   		alert("You Cannot Copy LineItem of Already Published Service");
			   		return false;
				}
				if(document.getElementById('serviceStatus'+1).value=="CancelAndCopy" || document.getElementById('serviceStatus'+1).value=="Cancelled in CRM"){
	   				alert("You Cannot Copy LineItem of Already Cancelled Service");
	   				return false;
				}			
				isServiceSelected = 1;
			}
		}else{
			for(i=0;i<chkLength;i++){
				if(myform.chkService[i].checked==true){
					if(document.getElementById('chk'+i).value=="Yes"){
			   			alert("You Cannot Copy LineItem of Already Published Service");
			   			return false;
					}
					if(document.getElementById('serviceStatus'+(i+1)).value=="CancelAndCopy" || document.getElementById('serviceStatus'+(i+1)).value=="Cancelled in CRM"){
	   					alert("You Cannot Copy LineItem of Already Cancelled Service");
	   					return false;
					}
					isServiceSelected = 1;
					break;
				}
			}
		}
		if(isServiceSelected==0)
		{
			alert("Please Select A Service and then product catelog");
			return false;
		}
		if(document.forms[0].chk_spId==undefined)
		{
			alert("No Product Catelog Present!!");
			return false;
		}
		var productStr="";
		var chkLength=document.forms[0].chk_spId.length;
		var checkedCounter=0;
		if(chkLength==undefined){
			// Start [001]
			if(document.forms[0].chk_spId.checked==true){
				for(var j=1;j<childArray.length;j++)
				{
					if(document.forms[0].chk_spId.value==childArray[j])
					{
						if((document.getElementById('chk_spId'+parrentArray[j]) == null) ||
							((document.getElementById('chk_spId'+parrentArray[j]) != null) && (document.getElementById('chk_spId'+parrentArray[j]).checked==false))){
							checkedCounter++;
							if(productStr==""){
								productStr=document.forms[0].chk_spId.value;
							}else{
								productStr=productStr + "," +document.forms[0].chk_spId.value;
							}
						}
					}
				}
			}
			// End [001]
			if(checkedCounter==0){
				alert('Please select line item(s)');
				return false;
			}else if(checkedCounter==1){
				if(document.forms[0].txtCopyProduct.value.length>2){
					alert("No. of copies should be less than 100");
				    return false;
				}
				if(document.forms[0].txtCopyProduct.value == 0){
				    alert("No of copies should be greater than 0");
					return false;
				} 
			}else if(checkedCounter>1 && checkedCounter<11){
				if(document.forms[0].txtCopyProduct.value > 10){
					alert("No. of copies should be less than or Equal to 10");
				    return false;
				}
				if(document.forms[0].txtCopyProduct.value == 0){
				    alert("No of copies should be greater than 0");
					return false;
				} 
			}else{
				alert('You cannot select more than 10 line for copying ')
				return false;
			}
			var jsData =			
		    {
				spIdString:productStr,
				copyProductValues:document.forms[0].txtCopyProduct.value,		  
				poNumber:Number(document.getElementById('poNumber').value)
			};
			var result = jsonrpc.processes.copyServiceProductForNewOrder(jsData,sessionId);
			if(result !=null){
				alert(result.message);
				if(result.messageId=='ENTRY_COPIED'){
					fncServiceTreeView();
					return false;
				}			
			}
		}else{
			//Start [001]
			for(i=0;i<document.forms[0].chk_spId.length;i++){
				if(myform.chk_spId[i].checked==true){
					for(var j=1;j<childArray.length;j++)
					{
						if(myform.chk_spId[i].value==childArray[j])
						{
							if((document.getElementById('chk_spId'+parrentArray[j]) == null) ||
								((document.getElementById('chk_spId'+parrentArray[j]) != null) && (document.getElementById('chk_spId'+parrentArray[j]).checked==false))){
								checkedCounter++;
					     	   	if(productStr==""){
					     	   		productStr=myform.chk_spId[i].value;
					     	    }else{
					     	    	productStr=productStr + "," +myform.chk_spId[i].value;
					     	    }
							}
						}
					}
				}
			}
			//End [001]
			if(checkedCounter==0){
				alert('Please select line item(s)');
				return false;
			}else if(checkedCounter==1){
				if(document.forms[0].txtCopyProduct.value.length>2){
					alert("No. of copies should be less than 100");
				    return false;
				}
				if(document.forms[0].txtCopyProduct.value == 0){
				    alert("No of copies should be greater than 0");
					return false;
				} 
			}else if(checkedCounter>1 && checkedCounter<11){//
				if(document.forms[0].txtCopyProduct.value > 10){
					alert("No. of copies should be less than or Equal to 10");
				    return false;
				}
				if(document.forms[0].txtCopyProduct.value == 0){
				    alert("No of copies should be greater than 0");
					return false;
				} 
			}else{
				alert('You cannot select more than 10 line for copying ')
				return false;
			}
			var jsData =			
		    {
				spIdString:productStr,
				copyProductValues:document.forms[0].txtCopyProduct.value,		  
				poNumber:Number(document.getElementById('poNumber').value)
			};
			var result = jsonrpc.processes.copyServiceProductForNewOrder(jsData,sessionId);
			if(result !=null){
				alert(result.message);
				if(result.messageId=='ENTRY_COPIED'){
					fncServiceTreeView();
					return false;
				}			
			}
		}
	

}
function copyProductCommercialCheck(){
	var roleWiseSectionDetail = getRoleWiseSectionDetails(gb_roleID);
    var commercialNotAllowed = checkRoleWiseCommercialNotAllowed(roleWiseSectionDetail);
    if(commercialNotAllowed){
    	var commercialLineToBeAdded =  checkServiceTreeContainsCommercialLineForCopyProduct("browser");
    	if(commercialLineToBeAdded){
    		alert("You are not authorized to Copy commercial line");
    		return false;
    	}
    }
    return true;
}

function checkPartialInitiatedOrPartialPublish(){
	var stage = document.getElementById("stageName").value;
	//alert("stage: " + stage);
	if(stage == 'Partial Initiated' || stage == 'Partial Publish')	
		return false;
	else
		return true;
}
// added by manisha for o2c start
function disableServiceLines(sectionParentId,gbroleid){
	$("#" +sectionParentId).find("tr[initTo!="+gbroleid+"][initTo!=0][ispublished=0]").each(function(){
		//var idNo = (this.id.split("_")[1])-1;
		//$("#" + sectionParentId).find("table tr:eq(" + idNo + ")").find("[type!='hidden']").attr("disabled", true);
		$(this).find( "a,  [type='checkbox']" ).attr("disabled", true);
	});
	
	//disabling the published lines
	$("#" +sectionParentId).find("tr[ispublished!=0]").each(function(){
		//var idNo = (this.id.split("_")[1])-1;
		//$("#" + sectionParentId).find("table tr:eq(" + idNo + ")").find("[type!='hidden']").attr("disabled", true);
		$(this).find( "a,  [type='checkbox']" ).attr("disabled", true);
	});
}	
//added by manisha for o2c end	
/**
 * enabling & disabling options based on roleID for O2C drop2
 * @param initiatedTo
 * @param roleID
 * @author Anoop Tiwari
 * @returns void
 *  
 */
function fncServicePresent(initiatedTo,roleID,isPublished){
	 //alert("ini"+initiatedTo+"role"+roleID);
	var objOrderType = document.getElementById('orderType').value;
	 if((initiatedTo!=0 && initiatedTo!=roleID) || isPublished==1){
		$("#idLinking").attr('disabled', true);
		$("#idCopyChargeImg").attr('disabled', true);
		$("#idLinking").attr('disabled', true);
		$("#productcatelog").attr('disabled', true);
		$("#cancelServiceDiv").attr('disabled', true);
		$("#idCountLineItemsImg").attr('disabled', true);
		$("#idCancelAndCopyImg").attr('disabled', true);
		$("#idDeleteServiceImg").attr('disabled', true);
		$("#cancelServiceDiv").attr('disabled', true);
		$("#deleteProductDiv").attr('disabled', true);
		$("#CopyOrderImg").attr('disabled', true);
		$("#productCatetogIcon").attr('disabled', true);
		$("#idDeleteServiceDiv").attr('disabled', true);
		$("#cancelServiceDiv").attr('disabled', true);
		$("#addChildNodeIcon").attr('disabled', true);
		$("#copyProductIcon").attr('disabled', true);
		$("#idCountLineItemsImg").attr('disabled', true);
		$("#deleteProductDiv").attr('disabled', true);
		if(objOrderType=='Change'){
			document.getElementById('linesTabButtons').style.display="none";
		}
	 }else{	
		 	$("#idLinking").attr('disabled', false);
			$("#idCopyChargeImg").attr('disabled', false);
			$("#idLinking").attr('disabled', false);
			$("#productcatelog").attr('disabled', false);
			$("#cancelServiceDiv").attr('disabled', false);
			$("#idCountLineItemsImg").attr('disabled', false);
			$("#idCancelAndCopyImg").attr('disabled', false);
			$("#idDeleteServiceImg").attr('disabled', false);
			$("#cancelServiceDiv").attr('disabled', false);
			$("#deleteProductDiv").attr('disabled', false);
			$("#CopyOrderImg").attr('disabled', false);
			$("#productCatetogIcon").attr('disabled', false);
			$("#idDeleteServiceDiv").attr('disabled', false);
			$("#cancelServiceDiv").attr('disabled', false);
			$("#addChildNodeIcon").attr('disabled', false);
			$("#copyProductIcon").attr('disabled', false);
			$("#idCountLineItemsImg").attr('disabled', false);
			$("#deleteProductDiv").attr('disabled', false);
			if(objOrderType=='Change'){
				document.getElementById('linesTabButtons').style.display="block";
			}
	}
}
function enableDisablePartialInitiatorTable(action){
	if(action==2){
		document.getElementById('partialInitiatorTable').style.display='none';
		document.getElementById('serviceTable').style.display='none';
		
	}else if(action==1){
		if(rolePartialInitiator==true || rolePartialInitiator=='true'){
		    document.getElementById('partialInitiatorTable').style.display='block';
		    document.getElementById('serviceTable').style.display='block';
		}
	}
}
//[002] START
function chkIsPoDetailsPresent(spId){
	jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
	var result=-1;
	try{
		result = jsonrpc.processes.chkIsPoDetailsPresent(spId);
		if(result==0 || result==-1)
			return false;
	}catch(e){	
   			alert("exception :  "+ e);
	}
	return true;
}
//[002] END