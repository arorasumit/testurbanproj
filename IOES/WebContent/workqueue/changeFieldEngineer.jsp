<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet"> 
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet"> 
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<title>Edit</title>
</head>

<style type="text/css">
    .button {
	  width: 59px;
	  height: 19px;
	  padding: 0px;
	  box-sizing: border-box;
	  border: 4;
	  background: #A9A9A9;
}
</style>

<script>
 var editFEList = [];
 var counter = 0;
function showFEDraftData(){
    try{
    	
    	editFEList =[];
		editFEList  = window.dialogArguments.listofFEwithIdforEdit;
	    if(editFEList != []){	
		 for(var key in editFEList) {
		    if (typeof editFEList[key] !== "function") {
    			//alert(editFEList[key].valTextBox);
    			if(editFEList[key].valTextBox!==undefined){
    				counter = editFEList[key].counter;
    				counter++
    				//alert('editFEList[key].status'+editFEList[key].status)
    				if(editFEList[key].seId != ''){ // hide remove button 
        				//alert('alerttttt:'+editFEList[key].hiddenValTextBox)
    					if(editFEList[key].status=='1'){
        					if(editFEList[key].hiddenValTextBox==undefined){
        					   $('#tableTxtBox').append('<tr id="rowId'+editFEList[key].idTextBox+'"><td align="center"><input type="checkbox" checked name ="txtBoxFE" id ="'+editFEList[key].idTextBox+'" /></td><td align="center"> <input type="text" value="'+editFEList[key].valTextBox+'" onblur="validateTxtFieldforScriptInjection(this)" maxlength="100" name ="txtBoxFE"  seId="'+editFEList[key].seId+'" style="width: 200px;" id ="'+editFEList[key].idTextBox+'" /> </td> <td align="center"> <input type="hidden" value="'+editFEList[key].valTextBox+'" name ="txtBoxFE" id ="idHiddenFE" /> </td> </tr>')
            				}else{
    						   $('#tableTxtBox').append('<tr id="rowId'+editFEList[key].idTextBox+'"><td align="center"><input type="checkbox" checked name ="txtBoxFE" id ="'+editFEList[key].idTextBox+'" /></td><td align="center"> <input type="text" value="'+editFEList[key].valTextBox+'" onblur="validateTxtFieldforScriptInjection(this)" maxlength="100" name ="txtBoxFE"  seId="'+editFEList[key].seId+'" style="width: 200px;" id ="'+editFEList[key].idTextBox+'" /> </td> <td align="center"> <input type="hidden" value="'+editFEList[key].hiddenValTextBox+'" name ="txtBoxFE" id ="idHiddenFE" /> </td> </tr>')
            				}	
	    				}else{
		    				if(editFEList[key].hiddenValTextBox==undefined){
		    				   $('#tableTxtBox').append('<tr id="rowId'+editFEList[key].idTextBox+'"><td align="center"><input type="checkbox" name ="txtBoxFE" id ="'+editFEList[key].idTextBox+'" /></td><td align="center"> <input type="text" value="'+editFEList[key].valTextBox+'" onblur="validateTxtFieldforScriptInjection(this)" style="width: 200px;" maxlength="100" name ="txtBoxFE"  seId="'+editFEList[key].seId+'" id ="'+editFEList[key].idTextBox+'" /> </td>  <td align="center"> <input type="hidden" value="'+editFEList[key].valTextBox+'" name ="txtBoxFE" id ="idHiddenFE" /> </td> </tr>')
			    			}else{
							   $('#tableTxtBox').append('<tr id="rowId'+editFEList[key].idTextBox+'"><td align="center"><input type="checkbox" name ="txtBoxFE" id ="'+editFEList[key].idTextBox+'" /></td><td align="center"> <input type="text" value="'+editFEList[key].valTextBox+'" onblur="validateTxtFieldforScriptInjection(this)" style="width: 200px;" maxlength="100" name ="txtBoxFE"  seId="'+editFEList[key].seId+'" id ="'+editFEList[key].idTextBox+'" /> </td>  <td align="center"> <input type="hidden" value="'+editFEList[key].hiddenValTextBox+'" name ="txtBoxFE" id ="idHiddenFE" /> </td> </tr>')
			    			}    				
	    				}
    				}else{ // show remove button
		   				if(editFEList[key].status=='1'){
		   					$('#tableTxtBox').append('<tr id="rowId'+editFEList[key].idTextBox+'"><td align="center"><input type="checkbox" checked name ="txtBoxFE" id ="'+editFEList[key].idTextBox+'" /></td><td align="center"> <input type="text" onblur="validateTxtFieldforScriptInjection(this)" maxlength="100" style="width: 200px;" value="'+editFEList[key].valTextBox+'" name ="txtBoxFE"  seId="'+editFEList[key].seId+'" id ="'+editFEList[key].idTextBox+'" /> </td> <td align="center"> <input type="button" onclick="removeTextBox('+editFEList[key].idTextBox+');" value="Remove" name ="txtBoxRemoveButton" class=button id ="txtBoxRemoveButton" /> </td><td align="center"> <input type="hidden" value="" name ="txtBoxFE" id ="idHiddenFE" /> </td></tr>')	
		   				}else{
							$('#tableTxtBox').append('<tr id="rowId'+editFEList[key].idTextBox+'"><td align="center"><input type="checkbox" name ="txtBoxFE" id ="'+editFEList[key].idTextBox+'" /></td><td align="center"> <input type="text" onblur="validateTxtFieldforScriptInjection(this)" maxlength="100" style="width: 200px;" value="'+editFEList[key].valTextBox+'" name ="txtBoxFE"  seId="'+editFEList[key].seId+'" id ="'+editFEList[key].idTextBox+'" /> </td> <td align="center"> <input type="button" onclick="removeTextBox('+editFEList[key].idTextBox+');" value="Remove" class=button name ="txtBoxRemoveButton" id ="txtBoxRemoveButton" /> </td><td align="center"> <input type="hidden" value="" name ="txtBoxFE" id ="idHiddenFE" /> </td></tr>')    				
		   				}					
    				}
    			}
    	 	}
    	 	 document.getElementById("clickDoneButtonId").disabled  = false;
		}
		//window.dialogArguments.document.getElementById('addList').value = [] ;
		//window.dialogArguments.addList=[];
		//counter = editFEList[key].counter;
		editFEList=[];
		//alert(counter);
        }
    	
    	
    	/* editFEList =[];
		editFEList  = window.dialogArguments.listofFEwithIdforEdit;
	    if(editFEList != []){	
		 for(var key in editFEList) {
		    if (typeof editFEList[key] !== "function") {
    			alert(editFEList[key].valTextBox);
    			if(editFEList[key].valTextBox!==undefined){
    				$('#tableTxtBox').append('<tr id="rowId'+editFEList[key].idTextBox+'"><td align="center"> <input type="text" value="'+editFEList[key].valTextBox+'" name ="txtBoxFE"  seId="'+editFEList[key].seId+'" id ="'+editFEList[key].idTextBox+'" /> </td> <td align="center"> <input type="button" onclick="removeTextBox('+editFEList[key].idTextBox+');" value="Click to Remove Text Box" name ="txtBoxRemoveButton" id ="txtBoxRemoveButton" /> </td></tr>')
    				counter++
    			}
    	 	}
    	 	 document.getElementById("clickDoneButtonId").disabled  = false;
		}
		//window.dialogArguments.document.getElementById('addList').value = [] ;
		//window.dialogArguments.addList=[];
		counter = editFEList[key].counter;
		editFEList=[];
		alert(counter);
     } */    
    }catch(error){
    		//alert(error.message)
          }
    } 
	
	function validateTxtFieldforScriptInjection(objFieldName){
		if( trim(objFieldName.value).length>0){
			return ValidateTextField(objFieldName,'<%=request.getContextPath()%>', 'FSE name')
		};
	}
	
	function addTextBoxOnClick(){
		//$('#tableTxtBox').append('<tr id="rowId'+counter+'"><td><input type="checkbox" checked name ="txtBoxFE" id ="'+counter+'"/></td><td align="center"> <input type="text" name ="txtBoxFE" onblur="validateTxtFieldforScriptInjection(this)" maxlength="100"  style="width: 200px;" seId="" id ="'+counter+'" /> </td><td align="center"> <input type="hidden" name ="txtBoxFE"  seId="" id ="idHiddenFE" /> </td> <td align="center"> <input type="button" onclick="removeTextBox('+counter+');" value="Remove" class=button name ="txtBoxRemoveButton" id ="txtBoxRemoveButton" /> </td> </tr>')
		$('#tableTxtBox').append('<tr id="rowId'+counter+'"><td><input type="checkbox" checked name ="txtBoxFE" id ="'+counter+'"/></td><td align="center"> <input type="text" name ="txtBoxFE" onblur="validateTxtFieldforScriptInjection(this)" maxlength="100"  style="width: 200px;" seId="" id ="'+counter+'" /> </td><td align="center"> <input type="button" onclick="removeTextBox('+counter+');" value="Remove" class=button name ="txtBoxRemoveButton" id ="txtBoxRemoveButton" /> </td><td align="center"> <input type="hidden" name ="txtBoxFE"  seId="" id ="idHiddenFE" /></td></tr>')
		counter++;
		document.getElementById("clickDoneButtonId").disabled  = false;
    }
	
function clickDoneButton(){
	//$('#tableTxtBox').find('tr').each(function () {
	        //var row = $(this);
	        var closePopUp=1;
	         //if ((row.find('input[type="text"]').is(':checked')==false) && (row.find('input[type="hidden"]').attr('isRoleAssigned')!= 0)){
		        //if(row.find('input[type="checkbox"]').attr('roleid')!=undefined){	
		       	//	alert('assign to non-assign '+row.find('input[type="checkbox"]').not(':checked')+ '   '+row.find('input[type="hidden"]').attr('isRoleAssigned'))
		            //nonAssignedList.push(row.find('input[type="checkbox"]').attr('roleid'))	;
		       		//nonAssignedListRoleName.push(row.find('td:eq(1)').text());
		        //}
		       // alert('row.find(td:eq(1)).text()'+row.find('td:eq(0)').text()+'tddd')
		      // alert(' $(this)'+ $(this));
		       // addedFEList.push(row.find('td:eq(1)').text());
		       var status;
		       $('input[type=text]').each(function(i, item) {
					if($(this).closest("tr").find('input[type="checkbox"]').is(':checked')==true){
						status=1
					}else{
						status=0
					}
					//alert('id'+$(item).attr('id'))
					//alert('val'+$(item).val())
					//alert('status'+status)	
					//alert('hidden'+$(this).closest("tr").find('input[type="hidden"]').val())	;       		
		       		var obTextBoxFE={
	   						idTextBox:$(item).attr('id'),
	   						valTextBox:$.trim($(item).val()),
	   						counter:counter,
	   						seId:$(item).attr('seId'),
	   						status:status,
	   						hiddenValTextBox:$(this).closest("tr").find('input[type="hidden"]').val()
		       		}
		       		//$(this).closest("tr").find('input[type="checkbox"]').is(':checked')
		       		
		       		//var trid = $(this).closest("tr");// table row ID 
		       		//$(trid).find('input[type=checkbox]').attr('id');
		       		//alert('tridtrid'+trid)
		       		//alert('dsada:::::'+$(trid).find('input[type="checkbox"]').is(':checked'));
					//var fieldEng =  $(item).val();
			        //alert(grade);
			        if($.trim($(item).val())==''){
						alert('FSE should not be left blank. Either remove it or input FSE')
						editFEList = [];	
						closePopUp=0;	
						return false;		         		
			        }
			        if($.trim($(item).val()).toUpperCase() in editFEList || $.trim($(item).val()).toLowerCase() in editFEList) {
			        	  //alert('inside ')
						  //delete myList[serviceval];	
						  //alert('obj with key:'+serviceval+'deleted from myList')
						  alert('You cannot enter duplicate FSE');
						  editFEList =[] ;		
						  closePopUp=0;
						  return false;
					  } else{
					  	   closePopUp=1
					  	   editFEList[$.trim($(item).val())] = obTextBoxFE ;		
					  	   //editFEList.push(fieldEng);
					  	   //alert('text box value added in list is:'+$(item).val())
					  }
					  
					  /* for(var key in editFEList) {
					  	if(editFEList.hasOwnProperty(key)) {
							 alert('inside ')					  	
					  	}
					  }
					   */
			        //alert('text box value added in list:'+fieldEng)
			        //editFEList.push(fieldEng);
		       });
		       
		       if(closePopUp==1){
		       		//window.dialogArguments.document.getElementById('addList').value = editFEList ;
		       		window.dialogArguments.listofFEwithIdforEdit = [] ;
			   		window.dialogArguments.listofFEwithIdforEdit = editFEList ;
			   		editFEList=[];
    		     	window.close();
		       }
}

function removeTextBox(id){
		   			 //alert('$(#txtBoxFEHidden).value'+$("#txtBoxFE").value)
        			//$("#txtBoxFE" + counter).remove();
        //alert(' text box number:'+id+'will be removed')
        $("#rowId" +id).remove();
       				// $(parent).remove();
      				 //$(this).closest("tr").remove();
       // counter--;
        //alert('now text box available is '+counter)
        if(counter==0)
		  {
            document.getElementById("clickDoneButtonId").disabled  = true;
   		  } 
} 

function clickCancelButton(){
        //window.dialogArguments.listofFEwithIdforEdit = [] ;
   		editFEList =[];
     	window.close();
} 

</script>

<body onload="showFEDraftData()">
<center>
<div class="head"> <span>EDIT FSE</span></div>
<html:form action="/NewOrderAction" styleId="editFE" onsubmit="return false;" method="post">
	<table width="100%"  border="1" align="center" class="tab2" >
			   <tr align="center">
				 <td>
					 <!-- input type="button" id="txtAddFieldEngineer" align="center" value="Add Field Engineer" name="txtAddFieldEngineer" onclick="txtAddFieldEngineer();"  maxlength="200" style="width:27%"//  -->
					 <input type="button" value="Click to Add FSE" id="addTextBoxOnClickId" onclick="addTextBoxOnClick();" />
				 </td>
				 <!-- <td>
					 <input type="button" value="test load" id="addTeClickId" onclick="showFEDraftData();" />
				 </td> -->
			   </tr>
			  
			   <table class="tab2" style="overflow:auto;" width="60%" border="1" align="center" id='tableTxtBox' style="vertical-align: top">
				 <tr>
					<td align="center" style="font-size:9px" width="10%"><b>Active</b></td>
					<td align="center" style="font-size:9px" width="10%"><b>FSE</b></td>	
				 </tr>
			   </table>
			   <table>
			   		<tr><td height=30 colspan=1></td></tr>
			   		<tr>
			   			<td>
			   			  <input type="button" id="clickDoneButtonId" disabled value="Attach FSE"	id="clickDoneButton" onclick="clickDoneButton();" />
			   			</td>
			   			<td>
			   			  <input type="button" id="clickCancelButtonId" value="Cancel"	id="clickCancelButton" onclick="clickCancelButton();" />
			   			</td>
			   		</tr>
			   </table>
	</table>
</html:form>
</center>
</body>
</html>