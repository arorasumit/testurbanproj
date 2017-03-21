<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Manisha Garg	03-Feb-13	CSR00-05422     page updated by manisha : defect no 98-->
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
<html>
<head>
<title>Access Matrix iB2B</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet"> 
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<!-- added by manisha start -->

<%
			HashMap var_userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession var_sessionObj = (HttpSession) var_userHashMap.get(session.getId());
			UserInfoDto var_objUserDto  = (UserInfoDto)var_sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>

<!-- added by manisha end -->

<script language="javascript" type="text/javascript">
jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
var gbOriginalCustomerSegment ='';
var clickonsearch;
function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

 
function getAutoSuggest() 
{
  $(":text[ctrltype='dropdown']").each(function() {
	var thisEl = $(this);																				
	thisEl.autocomplete({
		   
    source: function(request, response) 
            {
        	$.ajax({	
				url: "<%=request.getContextPath()%>/NewOrderAction.do?method=accessMatrixiB2B&searchval="+request.term+"&attributefor="+thisEl.attr("retval"),
                data: "{ edition: '" + request.term + "',targetctrl: 'test' }",
                dataType: "json",
                type: "Get",
                contentType: "application/json; charset=utf-8",
                dataFilter: function(data) {  return data; },
            	success: function(data) {	                        
                    response($.map(data.glossary, function(item) {
                        return {
								value: item.value,
                                label: item.label 
                        }
                    }))
            },async:false,
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
    },select: function (event, ui)
                    { 
                         if(ui.item.value=="-1")
                         {
                         	if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                         	{
                         		$(thisEl).val("");	
								$("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val("0");
                         	}
                         	else if(thisEl.attr("retval")=="AUTOSUGGESTACCESSMATRIX")
                         	{
                         		$(thisEl).val("");	
								$("#bbPrimaryPMID").val("0");
                         	} 
                         }
                         else
                         {
                           if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                         	{
                                $("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val(ui.item.value);
                                 $(thisEl).val(ui.item.label);
                         	}
                         	else if(thisEl.attr("retval")=="AUTOSUGGESTACCESSMATRIX")
                         	{
                         		$("#bbPrimaryPMID").val(ui.item.value);
                                $(thisEl).val(ui.item.label);
                               // alert(ui.item.label)
                               // alert($(thisEl).val(ui.item.label))
                               // alert($("#bbPrimaryPMID").val(ui.item.value))
                                /* //alert('clicked om select')
                                var customerSegmnetForUpdate=jsonrpc.processes.getMappedCustomerSegmentWithUserId(ui.item.value);
                                if(customerSegmnetForUpdate!=null){
                                	$("#bbcustomersegmentForUserName").val(customerSegmnetForUpdate.list[0].customerSegmentId);
                                }else{
                                    $("#bbcustomersegmentForUserName").val('');
                                } */
                                var customerSegmentDropDown = document.getElementById('custSegment');
                   				$("#Update").attr("disabled", true);
                                customerSegmentDropDown.selectedIndex=0;
                         	}
                         }
                         
 return false;	
},focus: function (event, ui) {
						
					   $("#bbPrimaryPMID").val(ui.item.value);
                       $(thisEl).val(ui.item.label);
                       var customerSegmentDropDown = document.getElementById('custSegment');
                   	   $("#Update").attr("disabled", true);
                       customerSegmentDropDown.selectedIndex=0;
                       return true;
                    },
change: function( event, ui ) 
				{
					var myForm=document.getElementById('searchForm');
					var bbPrimaryPMID=myForm.bbPrimaryPMID.value;
					var projectManager=myForm.projectManager.value;
					
					if((ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1"|| $("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val()=="0" ) && (projectManager.length>1) )
					{					
        		     var value2,label2;
        			 var validateData=$.ajax({
        			 					
										url: "<%=request.getContextPath()%>/NewOrderAction.do?method=accessMatrixiB2B&searchval="+thisEl.val()+"&attributefor="+thisEl.attr("retval"),
		                    			data: "{ edition: '" + thisEl.val() + "',targetctrl: 'test' }",
		                    			dataType: "json",
		                    			type: "Get",
		                    			contentType: "application/json; charset=utf-8",
		                    			dataFilter: function(data) {  return data; },
	                    				success: function(data) {
	                    					value2=data.glossary[0].value;
	                    					label2=data.glossary[0].label;
	                    				},async:false,
	                    				error: function(XMLHttpRequest, textStatus, errorThrown) {
	                        				alert(errorThrown);
	                    				}
	                				});	
	                			
	                	if(document.getElementById('clearhideen').value==1)
	                	{	
	                		value2=0;
	                		label2="";
	                		document.getElementById('clearhideen').value=0;
	                	
	                	}							                				  				   
						if(value2=="-1" ||label2=="-No Record Found-"){
						   if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                         	{	
                         		$(thisEl).val("");
								$("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val("0");
                         	}
                         	else if(thisEl.attr("retval")=="AUTOSUGGESTACCESSMATRIX")
                         	{	
                         		$(thisEl).val("");
								$("#bbPrimaryPMID").val("0");	
                         	} 
						}else{
								if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                         			{
                                 		$(thisEl).val(label2);
										$("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val(value2);		                                    
								    }
							else if(thisEl.attr("retval")=="AUTOSUGGESTACCESSMATRIX")
                             	{	
                             		$(thisEl).val(label2);
									$("#bbPrimaryPMID").val(value2);
                             	} 		
						}				                				    			
					}														
				},								
    minLength:2
	});
});							
}	
 
function getAutoSuggestForUserId() 
{
  $(":text[ctrltype='dropdownforuserid']").each(function() {
	var thisEl = $(this);																				
	thisEl.autocomplete({
		   
    source: function(request, response) 
            {                    
            
        	$.ajax({	
        	
				url: "<%=request.getContextPath()%>/NewOrderAction.do?method=accessMatrixiB2BForUserId&searchval="+request.term+"&attributefor="+thisEl.attr("retval"),
                data: "{ edition: '" + request.term + "',targetctrl: 'test' }",
                dataType: "json",
                type: "Get",
                contentType: "application/json; charset=utf-8",
                dataFilter: function(data) {  return data; },
            	success: function(data) {	                        
                    response($.map(data.glossary, function(item) {
                        return {
								value: item.value,
                                label: item.label 
                        }
                    }))
            },async:false,
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
    },select: function (event, ui)
                    { 
                    
                         if(ui.item.value=="-1")
                         {
                         	if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                         	{
                         		$(thisEl).val("");	
								$("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val("0");
                         	}
                         	else if(thisEl.attr("retval")=="AUTOSUGGESTACCESSMATRIX")
                         	{
                         		$(thisEl).val("");	
								$("#bbPrimaryPMIDForUserId").val("0");
                         	} 
                         }
                         else
                         {
                           if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                         	{
                                $("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val(ui.item.value);
                                 $(thisEl).val(ui.item.label);
                         	}
                         	else if(thisEl.attr("retval")=="AUTOSUGGESTACCESSMATRIX")
                         	{
                         		$("#bbPrimaryPMIDForUserId").val(ui.item.value);
                                $(thisEl).val(ui.item.label);
                                
                                 $("#Update").attr("disabled", true);
                                 var customerSegmentDropDown = document.getElementById('custSegment');
                                 customerSegmentDropDown.selectedIndex=0;
                         	}
                         }
                         
	 return false;
	},focus: function (event, ui) {
						
					   $("#bbPrimaryPMIDForUserId").val(ui.item.value);
                       $(thisEl).val(ui.item.label);
                       $("#Update").attr("disabled", true);
                       var customerSegmentDropDown = document.getElementById('custSegment');
                       customerSegmentDropDown.selectedIndex=0;
                       return true;
                    },
    change: function( event, ui ) 
				{
					var myForm=document.getElementById('searchForm');
					var bbPrimaryPMIDForUserId=myForm.bbPrimaryPMIDForUserId.value;
					var projectManager=myForm.projectManagerForUserId.value;
					
					
					if((ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1"|| $("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val()=="0" ) && (projectManager.length>1))
					{										                		
					
        		     var value2,label2;
        			 var validateData=$.ajax({
										url: "<%=request.getContextPath()%>/NewOrderAction.do?method=accessMatrixiB2BForUserId&searchval="+thisEl.val()+"&attributefor="+thisEl.attr("retval"),
		                    			data: "{ edition: '" + thisEl.val() + "',targetctrl: 'test' }",
		                    			dataType: "json",
		                    			type: "Get",
		                    			contentType: "application/json; charset=utf-8",
		                    			dataFilter: function(data) {  return data; },
	                    				success: function(data) {
	                    					value2=data.glossary[0].value;
	                    					label2=data.glossary[0].label;
	                    				},async:false,
	                    				error: function(XMLHttpRequest, textStatus, errorThrown) {
	                        				alert(errorThrown);
	                    				}
	                				});	
	                			
	                	if(document.getElementById('clearhideen').value==1)
	                	{
	                		value2=0;
	                		label2="";
	                		document.getElementById('clearhideen').value=0;
	                	
	                	}							                				  				   
						if(value2=="-1" ||label2=="-No Record Found-"){
						   if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                         	{
                         		$(thisEl).val("");
								$("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val("0");
                         	}
                         	else if(thisEl.attr("retval")=="AUTOSUGGESTACCESSMATRIX")
                         	{
                         		$(thisEl).val("");
								$("#bbPrimaryPMIDForUserId").val("0");	
                         	} 
						}else{
								if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                         			{
                                 		$(thisEl).val(label2);
										$("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val(value2);		                                    
								    }
							else if(thisEl.attr("retval")=="AUTOSUGGESTACCESSMATRIX")
                             	{
                             		$(thisEl).val(label2);
									$("#bbPrimaryPMIDForUserId").val(value2);
                             	} 		
						}				                				    			
					}														
				},								
    minLength:2
	});
});							
}
	
function getAllCustomerSegment(){
	   var myForm=document.getElementById('searchForm');
	   var custSeg = jsonrpc.processes.getAllCustomerSegmentList();
	   var combomain =myForm.custSegment;
	   var iCountRows = combomain.length;
	
	   for (var i = 1; i <  iCountRows; i++)
	   {
		   combomain.remove(i);
	   }
	 
	 for(var j=0;j<custSeg.list.length;j++)
     {
		
	       var optionmain = document.createElement("option");
	       
	        optionmain.text = custSeg.list[j].cus_segment;
	   		optionmain.title = custSeg.list[j].cus_segment;				
			optionmain.value = custSeg.list[j].customerSegmentId;
			try 
			{
				combomain.add(optionmain, null); 
				
			}
			catch(error) 
			{
				combomain.add(optionmain); 
				
			} 
			
			
    	}
	 var custSegm='<%=request.getAttribute("CustSegment")%>';	
	 if(custSegm !='null' && custSegm !=''){
			
		    combomain.value=custSegm;
		   }			
}
	
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}

function checkForm()
{
	return true;
}

function disableUserNameTextBox(){
	//$("input[ctrltype='dropdown']").attr('readonly', false);
	//$("input[ctrltype='dropdownforuserid']").attr('readonly', true);
	$("input[ctrltype='dropdown']").addClass('input-disabledfortextbox')
	$("input[ctrltype='dropdownforuserid']").removeClass('input-disabledfortextbox')
	//$("input[ctrltype='dropdownforuserid']").focus();	
	var myForm=document.getElementById('searchForm');
	myForm.projectManager.value = "";
	myForm.bbPrimaryPMID.value = ""; 	
}

function disableUserIdTextBox(){
	//$("input[ctrltype='dropdownforuserid']").attr('readonly', false);
	//$("input[ctrltype='dropdown']").attr('readonly', true);
	$("input[ctrltype='dropdownforuserid']").addClass('input-disabledfortextbox')
	$("input[ctrltype='dropdown']").removeClass('input-disabledfortextbox')
	//$("input[ctrltype='dropdown']").focus();
	var myForm=document.getElementById('searchForm');
	myForm.projectManagerForUserId.value = "";	
	myForm.bbPrimaryPMIDForUserId.value = "";	 
}

function searchClick()
{  
   // ClickOnSearch=1;	
	var retVal=null; 
	var myForm=document.getElementById('searchForm');
	var bbPrimaryPMID=myForm.bbPrimaryPMID.value;
	var projectManager=myForm.projectManager.value;
	var projectManagerForUserId=myForm.projectManagerForUserId.value;
	var bbPrimaryPMIDForUserId=myForm.bbPrimaryPMIDForUserId.value;
	var custSegment=myForm.custSegment.value;
	//alert("custSegment" +custSegment)
	//var customerSegment=myForm.customerSegment.value;
	//alert(customerSegment)
	//alert(bbPrimaryPMID+'            '+projectManager+'   '+ bbPrimaryPMIDForUserId +'   '+projectManagerForUserId)
	if(((bbPrimaryPMID==null || bbPrimaryPMID=="" || projectManager<1  || projectManager==null  || projectManager=="" || bbPrimaryPMID<1) && (bbPrimaryPMIDForUserId==null || bbPrimaryPMIDForUserId=="" || projectManagerForUserId<1  || projectManagerForUserId==null  || projectManagerForUserId=="" || bbPrimaryPMIDForUserId<1))) {
		alert("User name/User id shouldn't be blank");
		$('#tblRoleList tr:not(:first)').remove();
		myForm.bbPrimaryPMID.value = "";
		myForm.projectManager.value = "";
		return false;		
	} else
	{	
		var jsData=document.getElementById('bbPrimaryPMID').value
		var jsDataForUserId=document.getElementById('bbPrimaryPMIDForUserId').value
		//alert(jsData +' ---- '+jsDataForUserId)
		if (!(jsData==null || jsData=='' || jsData=='undefined')){
/* 			retVal = jsonrpc.processes.GetUserAccessList(jsData);		
 */
 			retValForAccessListAndSegment = jsonrpc.processes.GetUserAccessListAndSegment(jsData);	
			retVal = retValForAccessListAndSegment.getUserAccessList;
 		}
		
		if(!(jsDataForUserId==null || jsDataForUserId=='' || jsDataForUserId=='undefined')){
		    retValForAccessListAndSegment = jsonrpc.processes.GetUserAccessListAndSegment(jsDataForUserId);	
			retVal = retValForAccessListAndSegment.getUserAccessList;
		}
		    
		    $('#tblRoleList tr:not(:first)').remove();
		    for (i = 0,iSlNo=1 ; i < retVal.list.length; i++,iSlNo++)
		    {	
		    	if(retVal.list[i].isRoleAssigned==1)
		    		$('#tblRoleList').append('<tr><td align="center"> '+ iSlNo + ' </td><td align="center"> '+ retVal.list[i].roleName + ' </td><td align="center"> <input type="checkbox" name ="chkIsAssigned" id ="chkIsAssigned" checked=checked roleid='+ retVal.list[i].roleId +' /> </td><td align="center"> <input type="hidden" name="isRoleAssigned"  id="isRoleAssigned" isRoleAssigned='+ retVal.list[i].isRoleAssigned +'></td></tr>')  
		    	else
		    	    $('#tblRoleList').append('<tr><td align="center"> '+ iSlNo + ' </td><td align="center"> '+ retVal.list[i].roleName + ' </td><td align="center"> <input type="checkbox" name ="chkIsAssigned" id ="chkIsAssigned" roleid='+ retVal.list[i].roleId +' /> </td><td align="center"> <input type="hidden" name="isRoleAssigned"  id="isRoleAssigned" isRoleAssigned='+ retVal.list[i].isRoleAssigned +'></td></tr>')    
		    }
		// for cust seg
				   
		    	gbOriginalCustomerSegment = retValForAccessListAndSegment.getMappedCustomerSegmentWithUserId
		    	if(gbOriginalCustomerSegment.list.length>0){
			    	//alert('ssssssssssss   '+gbOriginalCustomerSegment.list[0].cus_segment+' ---'+ gbOriginalCustomerSegment.list[0].customerSegmentId) 
			    	var customerSegmentDropDown = document.getElementById('custSegment');
			    	selectItemByValue(customerSegmentDropDown, gbOriginalCustomerSegment.list[0].customerSegmentId);
		    	}else{
		    		gbOriginalCustomerSegment='';
		    	}
		// for cust seg	    
	}
		$("#Update").removeAttr("disabled");
}

	function selectItemByValue(elmnt, value){
	    for(var i=0; i < elmnt.options.length; i++)
	    {
	      if(elmnt.options[i].value == value)
	        elmnt.selectedIndex = i;
	    }
	  }

	function updateClick()
	{   
		var myForm=document.getElementById('searchForm');
		var bbPrimaryPMID=myForm.bbPrimaryPMID.value;
		var projectManager=myForm.projectManager.value;
		var projectManagerForUserId=myForm.projectManagerForUserId.value;
		var bbPrimaryPMIDForUserId=myForm.bbPrimaryPMIDForUserId.value;
		var customerSegmentDropDown = document.getElementById('custSegment');
		//alert('customerSegmentDropDown.selectedIndex  '+		customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].value);
		//alert('selected cust segment val '+customerSegmentDropDown.selectedIndex)
		//alert(bbPrimaryPMID+'            '+projectManager)
		if((customerSegmentDropDown.selectedIndex == 0)||((bbPrimaryPMID==null || bbPrimaryPMID=="" || projectManager<1  || projectManager==null  || projectManager=="" || bbPrimaryPMID<1) && (bbPrimaryPMIDForUserId==null || bbPrimaryPMIDForUserId=="" || projectManagerForUserId<1  || projectManagerForUserId==null  || projectManagerForUserId=="" || bbPrimaryPMIDForUserId<1))) {
			alert("Please input user name/user id or customer segment");
			clickonsearch=1;
			//$('#tblRoleList tr:not(:first)').remove();
		//	myForm.bbPrimaryPMID.value = "";
			var flag=2;
		//	myForm.projectManager.value = "";
			//myForm.bbPrimaryPMIDForUserId.value = "";
		//	myForm.projectManagerForUserId.value = "";
			
			
			return false;
		} 
		
		//alert(customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].value);
		//alert(customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].text);
		
		var empid='';
		var isRoleAssigned;
		var assignedList = [];
	    var nonAssignedList = [];
	    var nonAssignedListRoleName = [];
	    var assignedListRoleName= [];
		var userName = "<%=var_objUserDto.getUserName() %>";
        var userId = "<%=var_objUserDto.getUserId() %>";
        	        //alert(userName+'----' + userId)
		$('#tblRoleList').find('tr').each(function () {
	        var row = $(this);
	        
	         if ((row.find('input[type="checkbox"]').is(':checked')==false) && (row.find('input[type="hidden"]').attr('isRoleAssigned')!= 0)){
		        if(row.find('input[type="checkbox"]').attr('roleid')!=undefined){	
		       	//	alert('assign to non-assign '+row.find('input[type="checkbox"]').not(':checked')+ '   '+row.find('input[type="hidden"]').attr('isRoleAssigned'))
		            nonAssignedList.push(row.find('input[type="checkbox"]').attr('roleid'))	;
		       		nonAssignedListRoleName.push(row.find('td:eq(1)').text());
		        }
		    }
	        
	        if ((row.find('input[type="checkbox"]').is(':checked')==true) && (row.find('input[type="hidden"]').attr('isRoleAssigned')!= 1)){
		        if(row.find('input[type="checkbox"]').attr('roleid')!=undefined){
		        	//alert('non-assign to assign   '+row.find('input[type="hidden"]').attr('isRoleAssigned'))
				    assignedList.push(row.find('input[type="checkbox"]').attr('roleid'));
				    assignedListRoleName.push(row.find('td:eq(1)').text());
		        }
		    }
	    });	
       // alert('gbOriginalCustomerSegmentgbOriginalCustomerSegmentgbOriginalCustomerSegment'+gbOriginalCustomerSegment)
        if(gbOriginalCustomerSegment!='' && !((!(gbOriginalCustomerSegment.list[0]=='undefined')) && gbOriginalCustomerSegment.list.length>0 
				&& gbOriginalCustomerSegment.list[0].customerSegmentId != customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].value && customerSegmentDropDown.selectedIndex != 0) &&
					((!(assignedList.length >0 || nonAssignedList.length >0 ) ))){
		        	alert('No changes has been made');
	            return false;
	        }
	        
	        /* if(!((!(gbOriginalCustomerSegment.list[0]=='undefined')) && gbOriginalCustomerSegment.list.length>0 
				&& gbOriginalCustomerSegment.list[0].customerSegmentId != customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].value && customerSegmentDropDown.selectedIndex != 0) &&
					((!(assignedList.length >0 || nonAssignedList.length >0 ) ))){
				
				alert()
		    } */
		    
       // alert(document.getElementById('bbPrimaryPMID').value)
        if(document.getElementById('bbPrimaryPMID').value!=''){
        	empid=document.getElementById('bbPrimaryPMID').value
        }
        if(document.getElementById('bbPrimaryPMIDForUserId').value!=''){
            empid=document.getElementById('bbPrimaryPMIDForUserId').value
        }
        var customerSegmentId=null;
        var customerSegmentName=null;
        //alert('---gbOriginalCustomerSegment-'+gbOriginalCustomerSegment+'--' +'gbOriginalCustomerSegment.list[0]-'+gbOriginalCustomerSegment.list[0])
       if(gbOriginalCustomerSegment!=''){
	        if((!(gbOriginalCustomerSegment.list[0]=='undefined')) && gbOriginalCustomerSegment.list.length>0 && gbOriginalCustomerSegment.list[0].customerSegmentId != customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].value && customerSegmentDropDown.selectedIndex != 0){
			 	 customerSegmentId=customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].value;
			 	 customerSegmentName=customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].text;
			 	 
			 	 //alert('customerSegmentId111 ------------------'+customerSegmentId)
			 	 //alert('customerSegmenanme111 ------------------'+customerSegmentName)
			}else{
				//alert(gbOriginalCustomerSegment.list[0].customerSegmentId != customerSegmentDropDown.selectedIndex +'&&&&&'+ customerSegmentDropDown.selectedIndex != 0);
				customerSegmentId=-1;
				customerSegmentName='-1';
				//alert('customerSegmentId -----------------is '+customerSegmentId)
			}
		}
		
		if(customerSegmentDropDown.selectedIndex != 0 && gbOriginalCustomerSegment==''){
				 customerSegmentId=customerSegmentDropDown.options[customerSegmentDropDown.selectedIndex].value;
			 	 customerSegmentName=customerSegmentDropDown.options[customerSegmentId].text;
			 	// alert('gbOriginalCustomerSegmentblank==customerSegmentId ------------------'+customerSegmentId)
			 	// alert('gbOriginalCustomerSegmentblank==customerSegmentIdsdssdfdsfsdfdfds ------------------'+customerSegmentName)
		}
		
		var oldCustSegmentName='';
		//alert('---gbOriginalCustomerSegment-'+gbOriginalCustomerSegment+'--' +'gbOriginalCustomerSegment.list[0]-'+gbOriginalCustomerSegment.list[0])
		if(gbOriginalCustomerSegment!=''){
				//alert('enterrrrrrrrrrrrrrrr'+'gbOriginalCustomerSegment.list[0]='+gbOriginalCustomerSegment.list[0]+'gbOriginalCustomerSegment.list[0].customerSegmentId='+gbOriginalCustomerSegment.list[0].customerSegmentId)
			if((((gbOriginalCustomerSegment.list[0]!='undefined'))) && (gbOriginalCustomerSegment.list[0].customerSegmentId!=null || gbOriginalCustomerSegment.list[0].customerSegmentId!='')){
	            //oldCustSegmentName= customerSegmentDropDown.gbOriginalCustomerSegment.list[0].customerSegmentId.customerSegmentId.text;    
	         	for(var i=0; i < customerSegmentDropDown.options.length; i++)
				    {
				      if(customerSegmentDropDown.options[i].value == gbOriginalCustomerSegment.list[0].customerSegmentId)
				          oldCustSegmentName= customerSegmentDropDown.options[i].text;    
				    }  
	            //oldCustSegmentName= customerSegmentDropDown.options[gbOriginalCustomerSegment.list[0].customerSegmentId].text;    
	            //alert('oldCustSegmentName-------------  '+oldCustSegmentName)
	        }	
	     }
	        
        if ((assignedList.length >0 || nonAssignedList.length >0 ) || customerSegmentId!=-1){
        	var jsData =			
			    {   
			        assignedList:assignedList,
			        nonAssignedList:nonAssignedList,
				    empId:empid,
					modifiedByUserName:userName,			    
				    modifiedByUserId:userId,
				    assignedListRoleName:assignedListRoleName,
				    nonAssignedListRoleName:nonAssignedListRoleName,
				    customerSegmentId:customerSegmentId,
				    cus_segment:customerSegmentName,
				    oldCustSegmentName:oldCustSegmentName
				};
		   //alert('updateRolesOnSubmitupdateRolesOnSubmitupdateRolesOnSubmit')
           var retVal = jsonrpc.processes.updateRolesOnSubmit(jsData);
          // alert('retValretVal--'+retVal+'----')
           if(retVal=="success")
        	   alert('Data updated successfully')
           else if(retVal=="failure")
        	   alert('Data updation failed')
           
           searchClick()
        }
    }

function clearFields()
{
	document.getElementById('projectManager').value='';
	document.getElementById('bbPrimaryPMID').value='';
 	document.getElementById('projectManagerForUserId').value='';
	document.getElementById('bbPrimaryPMIDForUserId').value='';
 	document.getElementById('clearhideen').value=1;
}


path='<%=request.getContextPath()%>';
</script>
<body onload="getAutoSuggest();getAutoSuggestForUserId();getAllCustomerSegment()" style="vertical-align: top;">
<html:form action="/NewOrderAction"  enctype="text/plain" styleId="searchForm" onsubmit="return false;" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
		
	<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%" style="vertical-align: top">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head" style="width:100%"> <span>iB2B User Access Matrix</span> </div>
		<fieldset  style="width:70%" border="1" align="center" class="border2">
			<legend><b>Search</b></legend>
				<table border="0" cellspacing="5"  align="center" cellpadding="0" style="margin-top:7px" style="vertical-align: top" >
					<tr>
						
						<td align="right" style="font-size:9px">User Id:</td>
						<td align="left">
							<input type="text" styleClass="inputBorder2" style="width:150px;float:left;" name="projectManagerForUserId" ctrltype='dropdownforuserid' retval='AUTOSUGGESTACCESSMATRIX' id="projectManagerForUserId" onclick="disableUserNameTextBox()">							
								<input type="hidden" name="bbPrimaryPMIDForUserId"  id="bbPrimaryPMIDForUserId" isrequired="1">
								<input type="hidden" name="bbcustomersegmentForUserId"  id="bbcustomersegmentForUserId" >
						</td>
						<td align="right" style="font-size:9px"> </td>
						<td align="right" style="font-size:9px">User Name:</td>
						<td align="left">
							<input type="text" styleClass="inputBorder2" style="width:150px;float:left;" name="projectManager" ctrltype='dropdown' retval='AUTOSUGGESTACCESSMATRIX' id="projectManager" onclick="disableUserIdTextBox()"><!-- <a id="PMAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownPM()">Show</a> -->								
								<input type="hidden" name="bbPrimaryPMID"  id="bbPrimaryPMID" isrequired="1">
								<input type="hidden" name="bbcustomersegmentForUserName"  id="bbcustomersegmentForUserName" >
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
							<input type="hidden" name="clearhideen"  id="clearhideen" value="0" isrequired="1">
						</td>
						<td><input type="hidden" styleClass="inputBorder2" style="width:200px;float:left;" id="projectmgrid" name="projectmgrid" value="0"></td>
						
						
						
						<!-- <td type=drop width="30px"> </td> -->
						<td>     
						<html:select property="custSegment"  style="width:190px;"  styleId="customerSegment">
						<html:option  value="0">Select Customer Segment </html:option>
						</html:select>
						</td>			
						
						
						<td align="right" width="70px"><div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div></td>
						<td width="30px"/>
						<td align="right" width="70px"><div class="searchBg" style="margin-right:10px;"><a href="#" id="Update" title="Update" onClick="updateClick();">Update</a></div></td>
					</tr>
					<tr height="5px">
					 <td colspan="15" align="center"><B><font color="red"></font></td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" style="width:70%">
			<legend> <b>User Role Mapping</b></legend>
			<br/>
			
			<table class="tab2" style="overflow:auto;" width="60%" border="1" align="center" id='tblRoleList' style="vertical-align: top">
				<tr>
					<td align="center" style="font-size:9px" width="10%"><b>Sl.No.</b></td>	
					<td align="center" style="font-size:9px" width="65%"><b>
							Role Name
					</b></td>
					<td align="center" style="font-size:9px" width="35%"><b>Role Mapped</b></td>
					
				</tr>
				</table>
		</fieldset>
		
	</html:form>
</body>
</html>
  
