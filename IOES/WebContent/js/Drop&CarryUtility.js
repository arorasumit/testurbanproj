/*-------------------------------------Shubhranshu,12-oct-2016----------------------------------------*/
			$(document).ready(function()
		{														
				if(viewMode==1 || orderType=='Change')
				{
					$('#ddServiceFlavour').attr("disabled",true);
					$('#ddCircuitType').attr("disabled",true);
					$('#heclink').attr("disabled",true);	
					$('#txtHeadEndCode').val("");
					$('#btn_DnC').attr("disabled",true);					
				}			
																										
						/*Change Event Handlers*/
					$('#ddServiceFlavour').change(function()
						{					
							var selectedServiceFlavour=$(this).val();
									if(selectedServiceFlavour=='PP')
										{
											$('#ddCircuitType').val(0);
											$('#ddCircuitType').attr("disabled",true);
											$('#txtHeadEndCode').val("");
											$('#heclink').attr("disabled",true);	
											$('#btn_DnC').attr("disabled",false);
											$("#alertText").css("visibility","hidden");
										}
									else
										{
											$('#ddCircuitType').attr("disabled",false);									
										}
					   });
			
					//CircuitTypeDD
					$('#ddCircuitType').change(function()
					{
						/*alert('here in handler for ckttype');*/
						var selectedCircuitType=$(this).val(); //alert(selectedCircuitType);
						
						if(selectedCircuitType=='HE')
							{
							$("#alertText").css("visibility","visible");
							$("#alertText").css("font-size","large");
							}
						else
							{
							$("#alertText").css("visibility","hidden");
							}
						
						if(selectedCircuitType=='DROP')
							{
							$('#heclink').attr("disabled",false);
							}
						else
							{
							$('#heclink').attr("disabled",true);	
							$('#txtHeadEndCode').val("");
							}
					});
											
						/*$("#HeadEndCodeDialog").dialog().on("dialogopen",function(event, ui){
							
							$(".ui-dialog-titlebar").find("button").eq(0).remove(); // To remove default close button
						});  */
	     			
					// Event Handler Code Ends 
					
					if(viewMode=='null' && orderType=='New')
					{
						$('#ddServiceFlavour').attr("disabled",false);	
					}	
						getDropAndCarryData(serviceId);
					
						createHeadEndCodeDialog();				
			});
			
		function createHeadEndCodeDialog()
		{
			//
			 $("#HeadEndCodeDialog").dialog({			    	 	
		    	 	width: 800,
		    	 	minHeight:400,
		         	autoOpen: false,
		     		modal:true,			     		
		     		closeOnEscape: false,
		     		resizable:false,		     
		     		title: "SelectHeadEndCode",		     			       
		     		buttons:
		     			{
		     			Close : function (){
		     				$(this).dialog("close");
		     				$(this).empty();
		     											}
		     			},
		     		open:function(){		     			
		     			 $("[aria-labelledby='ui-dialog-title-HeadEndCodeDialog']").find("a").remove() ;		     			
		     		}
		     			
		 	});				 
			 //
		}
		
		// To Open HeadEndCode Dialog
		function selectHeadEndCode()
		{	//alert(path+param);
			 $("#HeadEndCodeDialog").load(path+param);						
				$('#HeadEndCodeDialog').dialog('open');
			};

		// 
		function getDropAndCarryData(srvId)
		{					
				var test=jsonrpc.processes.getDropAndCarryDataForCC(srvId);
				
					if(test.list.length>0)
						{							
							var srvFlavor=test.list[0].serviceFlavor; //alert(srvFlavor);
							var cktType=test.list[0].circuitType;//alert(cktType);
							var headEndCode=test.list[0].headEndCode;//alert(headEndCode);
															
									$('#ddServiceFlavour').val(trim(srvFlavor));
									$('#ddCircuitType').val(trim(cktType));
									$('#txtHeadEndCode').val(trim(headEndCode));
						}		
										
							var selectedSrvFlavor=$('#ddServiceFlavour').val();
							var selectedCktType=$('#ddCircuitType').val();			
									
										if(viewMode=='null' && orderType=='New')
										{ 
												if(selectedSrvFlavor=='DC')
												{
												$('#ddCircuitType').attr('disabled',false);
												}
											else
												{
												$('#ddCircuitType').attr('disabled',true);
												}
												
												if(selectedCktType=='DROP')
												{
												$('#heclink').attr("disabled",false);
												}
												else
												{
												$('#heclink').attr("disabled",true);
												}
												
												if(selectedCktType=='HE')
												{												
												$("#alertText").css({"visibility":"visible","font-size":"large"});
												}
											else
												{
												$("#alertText").css("visibility","hidden");
												}	
										}															
					return 0 ;
	}
				
	// On Save Button Click
		function saveOptions()
			{
				var valSrvFlavour=$('#ddServiceFlavour').val(); 
				
				var valCktType=$('#ddCircuitType').val(); 
				
				var valHeadEndCode=$('#txtHeadEndCode').val();
				
				if(valCktType==0)
					{valCktType=' ';}
				
				if(valSrvFlavour=='DC')
					{
					if(valCktType==0)
						{
						alert('Please Select Circuit Type !!');
						return false;
						}
					}
				var jsData={
						serviceFlavor:valSrvFlavour,
						circuitType:valCktType,
						headEndCode:valHeadEndCode	,
						serviceId:serviceId
				};
					var res=jsonrpc.processes.saveDropNCarryData(jsData);
					if(res==1)
						{
						alert('Data Saved Successfully !!');
						}
					else
						{
						alert('Data Not Saved !!');
						}	
					/*jQuery.noConflict();*/					
					$('#dropNCarryDialog').dialog('close');
					 //$('#dropNCarryDialog').empty();
					 
			}
			
			