
 
<!--[001]	 LAWKUSH 		06-MAY-11	00-05422		Setting Paging ans sorting -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.FieldAttibuteDTO"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.utilities.Utility"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="com.ibm.ioes.forms.ChannelPartnerDto"%>
<html>
<head>
<title>channel partner</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet"> 
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/NewOrder.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<head>
<title>FSE</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet"> 
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<script language="javascript" type="text/javascript">
 
 
 
 	 var counter = 0;   
	 var addedFEList = [];
	 
	 
function clickCancelButton(){
 		//window.dialogArguments.addList = [] ;
   		addedFEList=[];
     	window.close();
}	 
	       
function removeTextBox(id){
		   			 //alert('$(#txtBoxFEHidden).value'+$("#txtBoxFE").value)
        			//$("#txtBoxFE" + counter).remove();
        //alert(' text box number:'+id+'will be removed')
        $("#rowId" +id).remove();
       				// $(parent).remove();
      				 //$(this).closest("tr").remove();
        //counter--;
        //alert('now text box available is '+counter)
        if(counter==0)
		  {
            document.getElementById("clickDoneButtonId").disabled  = true;
   		  } 
}      
      
function addTextBoxOnClick(){
	//alert('test')
	//$('#tableTxtBox tr:not(:first)').remove();
	counter++;
	$('#tableTxtBox').append('<tr id="rowId'+counter+'"><td align="center"> <input type="text" style="width: 200px;" onblur="validateTxtFieldforScriptInjection(this)" name ="txtBoxFE" maxlength="100" id ="'+counter+'" /> </td> <td align="left"> <input type="button" class=button onclick="removeTextBox('+counter+');" value="Remove" name ="txtBoxRemoveButton" id ="txtBoxRemoveButton" /> </td> </tr>')
	//counter++;
	document.getElementById("clickDoneButtonId").disabled  = false;
}

function validateTxtFieldforScriptInjection(objFieldName){
	if( trim(objFieldName.value).length>0){
		return ValidateTextField(objFieldName,'<%=request.getContextPath()%>', 'FSE name')
	};
}

function clickDoneButton(){
	//$('#tableTxtBox').find('tr').each(function () {
	        //var row = $(this);
	           var closePopUp=1;
	           var addedFEListTemp=[];
		       $('input[type=text]').each(function(i, item) {
					
					//alert('id'+$(item).attr('id'))
					//alert('val'+$(item).val())		       		
		       		var obTextBoxFE={
	   						idTextBox:$(item).attr('id'),
	   						valTextBox:$.trim($(item).val()),
	   						counter:counter
		       		}
//			        var fieldEng =  $(item).val();
			        //alert(grade);
			        if($.trim($(item).val())==''){
			        	addedFEListTemp=[];
						alert('Text box should not be left blank. Either remove it or input FSE');
						closePopUp=0;	
						return false;	
			        }
			        if($.trim($(item).val()).toUpperCase() in addedFEListTemp || $.trim($(item).val()).toLowerCase() in addedFEListTemp) {
			        	  //alert('inside ')
						  //delete myList[serviceval];	
						  //alert('obj with key:'+serviceval+'deleted from myList')
						  addedFEListTemp=[];
						  alert('FSE should not be duplicate')
						  //alert('Engineer')
						  for(var key in addedFEListTemp) {
						    if (typeof addedFEListTemp[key] !== "function") {
						    	//arraylistAddFE.push(addList[key])\
						    	//alert('inside reoveeeeeeeeeeeeeeee')
								addedFEListTemp[i]=[];
							    //i=i+1;
				    	 	}
						   }
						   addedFEListTemp=[];
						   
						  // alert(addedFEList.length())
						  closePopUp=0;
						  return false;	
					  } else{
					  	   //alert(' wrong ')
					  	   closePopUp=1
					  	   addedFEListTemp[$.trim($(item).val())] = obTextBoxFE ;		
					  	   //addedFEList.push(fieldEng);
					  	   //alert('text box value added in list is:'+$(item).val())
					  }
					  
					  /* for(var key in addedFEList) {
					  	if(addedFEList.hasOwnProperty(key)) {
							 alert('inside ')					  	
					  	}
					  }
					   */
			        //alert('text box value added in list:'+fieldEng)
			        //addedFEList.push(fieldEng);
		       });
		       
		       if(closePopUp==1){
		       		//window.dialogArguments.document.getElementById('addList').value = addedFEList ;
		       		window.dialogArguments.addList = [] ;
		       		addedFEList=addedFEListTemp;
			   		window.dialogArguments.addList = addedFEList ;
			   		addedFEList=[];
    		     	window.close();
		       }
		       if(closePopUp==0){
		       		addedFEList=[];
		       }
}

    function showFEDraftData(){
    try{
    	addedFEList=[];
		addedFEList = window.dialogArguments.addList;
	    if(addedFEList != []){	
		 for(var key in addedFEList) {
		    //if(addedFEList.hasOwnProperty(key)) {
		    	//$('#tableTxtBox').append('<tr id="rowId'+addedFEList[key].idTextBox+'"><td align="center"> <input type="text" value="'+addedFEList[key].valTextBox+'" name ="txtBoxFE" id ="'+addedFEList[key].idTextBox+'" /> </td> <td align="center"> <input type="button" onclick="removeTextBox('+addedFEList[key].idTextBox+');" value="Click to Remove Text Box" name ="txtBoxRemoveButton" id ="txtBoxRemoveButton" /> </td> </tr>')
		    //}
		    if (typeof addedFEList[key] !== "function") {
    			//alert(addedFEList[key]);
    			$('#tableTxtBox').append('<tr id="rowId'+addedFEList[key].idTextBox+'"><td align="center"> <input type="text" style="width: 200px;" onblur="validateTxtFieldforScriptInjection(this)" maxlength="100" value="'+addedFEList[key].valTextBox+'" name ="txtBoxFE" id ="'+addedFEList[key].idTextBox+'" /> </td> <td align="center"> <input type="button" class=button onclick="removeTextBox('+addedFEList[key].idTextBox+');" value="Remove" name ="txtBoxRemoveButton" id ="txtBoxRemoveButton" /> </td> </tr>')
    	 	}
    	 	 document.getElementById("clickDoneButtonId").disabled  = false;
		}
		//window.dialogArguments.document.getElementById('addList').value = [] ;
		//window.dialogArguments.addList=[];
		counter = addedFEList[key].counter;
		addedFEList=[];
		//alert(counter);
     }    
	    }catch(error){
	    		//alert(error.message)
	    }
   } 
</script>
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
<body onload="showFEDraftData()">
<center>
<div class="head"> <span>ADD FSE</span></div>
<html:form action="/NewOrderAction" styleId="addFE" onsubmit="return false;" method="post">
	<table width="100%"  border="1" align="center" class="tab2" >
			   <tr align="center">
				 <td>
					 <!-- input type="button" id="txtAddFieldEngineer" align="center" value="Add Field Engineer" name="txtAddFieldEngineer" onclick="txtAddFieldEngineer();"  maxlength="200" style="width:27%"//  -->
					 <input type="button" value="Add FSE"  id="addTextBoxOnClickId" onclick="addTextBoxOnClick();" />
				 </td>
				 <!-- <td>
					 <input type="button" style="width:40px;height:20px;" value="test load" id="addTeClickId" onclick="showFEDraftData();" />
				 </td>  -->
			   </tr>
			  
			   <table class="tab2" style="overflow:auto;" width="60%" border="1" align="center" id='tableTxtBox' style="vertical-align: top">
				 <tr>
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
<script type="text/javascript">
	
</script>
</html>