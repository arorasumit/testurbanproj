/**Shubhranshu **/
function getAutoSuggestUtility(thisEl)
{    
	var callType = null;         
    var prodCatType = null;    
    var callerWindowObj=dialogArguments;
    var focusEnabled=true;
  
    /*Use these methods if needed in future.*/
  //getCallType(thisEl);//getProdCatType(thisEl);
	
	if(thisEl.is(":disabled")){
		return;
	}
	var param=callerWindowObj.document.getElementById("crmAccNo").value+"_"+callerWindowObj.document.getElementById("hdnSubChangeTypeId").value;
	param=encodeURIComponent(param);
	
   	thisEl.autocomplete({
   		source: function(request, response){	  
	             	$.ajax({
           		url: path + "/NewOrderAction.do?method=populateServiceAttMasterValue&searchval="+request.term+"&attributefor="+thisEl.attr("retval")+"&sourceType=" +prodCatType+ "&param="+param+ "&callType="+callType+"&parntAttId="+thisEl.attr("parntAttId"),
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
                    };
               		}));
               },async:false,
               error: function(XMLHttpRequest, textStatus, errorThrown) {
                   alert(errorThrown);
               }
           });
       },select: function (event, ui){                                	                                                           
    	   if(ui.item.value=="-1"){
    		   setValuesForNoRecord(thisEl);
           }else{
        	   setValuesForFetchedRecord(thisEl, ui);
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
			if(ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1")
			{
  				var value2,label2,addParam;
			 	var validateData=$.ajax({
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
					setValuesForNoRecord(thisEl);
				}else{
					setValuesForFetchedRecordForChange(thisEl, ui, value2, label2);
				}
			}else{
				value2 = ui.item.value;
				label2 = ui.item.label;
				if(value2=="-1" ||label2=="-No Record Found-"){
					setASValuesForNoRecord(thisEl);
				}else{
					setValuesForFetchedRecordForChange(thisEl, ui, value2, label2);
				}
					
			}																
		},								
       minLength: 0
   });
}

function setValuesForNoRecord(thisEl)
{
	if (thisEl.attr("retval")=="AUTOSUGGESTCHANGEREASON")  // Shubhranshu , 31-5-16
	{
		$(thisEl).val("");
	$("#txtChangeReason").val("0"); //alert("ha ha ha !!");
	}
	return ;
}

function setValuesForFetchedRecord(thisEl,ui)
{
	//Shubhranshu, 31-5-16
	if (thisEl.attr("retval")=="AUTOSUGGESTCHANGEREASON"){
 		if(ui.item.value != $("#txtChangeReason").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtChangeReason").val(ui.item.value); 
 		}
 	}
	return;
}


function setValuesForFetchedRecordForChange(thisEl, ui, value2, label2)
{				// Shubhranshu , 31-5-16
			if (thisEl.attr("retval")=="AUTOSUGGESTCHANGEREASON")
			{
				if(value2 != $("txtChangeReason").val()){
				$(thisEl).val(label2);
				$("txtChangeReason").val(value2);
			}
		}
	return;	
}

/********************************************************************************************************************/

/*function attachCSSOnAS(item){
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
*/
/*
$(document).ready(function()
		{	
	var datelabel=$('#myTable tr:first').find("td").eq(0) ; // ServiceNproduct.jsp
	
	var datelabel2= $('#dateinputrow').find("td").eq(0) ;  // ServiceAttributes.jsp
				
			if(changeTypeId==5)
				{
				datelabel.text("RR Effective Date "); 
				datelabel2.text("RR Effective Date ");
			    }
			else
				{	datelabel.text(" Effective Date "); 
					datelabel2.text(" Effective Date ");
				}
*/	
			/*	$('#effectiveDate').change(function()
						{
				
				alert($(this).val());
				});*/
				
				//attachCSSOnAS($("[ctrltype='dropdownlink']")); // to set image to autosuggest link
/***************************************************************************************************************************************/