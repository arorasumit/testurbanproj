<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	4-Mar-11	00-05422		Created By and Modified by -->
<!--[002]	 Anil Kumar		3-Oct-11	CSR00-05422     Use Ctrl + S for Save  -->
<!--[003]	 Rohit Verma	3-Oct-11	CSR00-05422     Tool Tip  -->
<!--[004]	 ASHUTOSH		05-06-12	00-05422		Focus on field validation -->
<!--[HYPR22032013006]	 Anil Kumar		29-03-13	00-05422		Change reason text field replace by drop down,user can change this reason value -->
<!--[005]	 Rohit Verma				00-09112		Parallel Upgrade Order No\LSI input -->
<!--[006]	 Gunjan Singla			20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection -->
<!--[007] Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Parallel Upgrade LSI -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<title>ServiceAttributes</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/enableDisableOrderEntryUtility.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>

<!--  Shubhranshu--> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/getAutoSuggestUtility.js"></script> 
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
//added by anil for clep
	path="<%=request.getContextPath()%>";
	var ORDER_CREATION_SOURCE = 2;//CLEP
//end

var isView = <%=request.getParameter("isView1")%>;
var txtServiceTypeId=<%=request.getParameter("serviceTypeId")%>;
//Added By Ashutosh As On Date 03-Oct-2011
//==========[Start:[HYPR22032013006]]=========================
var selectedReasonForChangeId=0;
var selectReasonForChangeName="";
//==========[End:[HYPR22032013006]]===========================
function selectCustLogicalSI()
{
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchCustLogicalSI";		
	window.showModalDialog(path,window,"status:false;dialogWidth:400px;dialogHeight:530px");
}
function saveServiceAttributes()
{
	var frm=document.getElementById('serviceAttributes');
	var serviceId = frm.serviceID.value;
	var productId = document.getElementById("txtProduct").value;
	var subProductId = document.getElementById("txtSubProduct").value;
	var downtimeclause=document.getElementById("downtimecls").value;
	var cancelledOrderReference=document.getElementById("cancelledOrderReference").value;
	//[006] start
	//[005] Start
	var parallelUpgradeLSINo1=document.getElementById("parallelUpgradeLSINo1").value;
	var parallelUpgradeLSINo2=document.getElementById("parallelUpgradeLSINo2").value;
	var parallelUpgradeLSINo3=document.getElementById("parallelUpgradeLSINo3").value;
	
	//[005] End
	//[006] end
	var isBundle=0;	
	
	//[007] Start
	var parallelUpgradeLSIRequire=document.getElementById("parallelUpgradeLSIRequire");
	var remarksParallelUpgrade = document.getElementById("remarksParallelUpgrade");
	//[007] End
	if(txtServiceTypeId==381 || txtServiceTypeId==431 )  //added by Anoop for OVCC---[01]
	{
		if(document.getElementById("cmbBundle")!=null)
			isBundle=document.getElementById("cmbBundle").value;
	}
	var isPublished = 0;
	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");	
	
	serviceInactiveFlagCheck=jsonrpc.processes.serviceInactiveFlagCheck(serviceId);
	
	if(serviceInactiveFlagCheck == 1 || serviceInactiveFlagCheck == 2)
	{
		alert('Service cancelled , you cant save now !! ');
		return false;
	}
	
	newOrderStatusServiceId = jsonrpc.processes.newOrderStatusServiceId(serviceId);

	if(serviceId == newOrderStatusServiceId)
	{
		alert("Service already published");
		return false;
	}
	 var callerWindowObj = dialogArguments;
	var orderNo = callerWindowObj.document.getElementById('poNumber').value;
	
	<%
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
	%>
	
	var roleName = <%=objUserDto.getUserRoleId() %>;
	var userId = "<%=objUserDto.getUserId() %>";

	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	var shortCode = jsonrpc.processes.getShortCode(roleName);
	
	
	if(orderDetails.list.length>0)
	{
		stage=orderDetails.list[0].stageName;
		if(shortCode==stage)
		{
			//do nothing
		}
		else if(stage=="New" || stage=="NEW")
		{
			//do nothing
		}
		else if(stage=="Billing Trigger" && shortCode=="COPC")
		{
			alert("You are not authorised to save or update the values");
			return false;
			//do nothing
		}
		else if((stage=="Partial Publish" || stage=="Partial Initiated") && (shortCode=="COPC" || shortCode=="SED"))
		{
			//alert("You are not authorised to save or update the values");
			//return false;
			//do nothing
		}else if(stage=="Partial Initiated"){
			//do nothing
		}
		else
		{
			alert("You are not authorised to save or update the values");
			return false;
		}
	}
	//End By Saurabh
	
	var effStartDate = frm.effStartDate.value;
	/*var effEndDate = frm.effEndDate.value;
	if(dateCompare(effStartDate,effEndDate)==1)
	{			
		alert("Effective EndDate Cannot be less than Effective StartDate !!");
		return false;
	} 
	if(frm.effEndDate.value=="")
	{
		alert("Please Input Effective End Date!!");
		frm.effEndDate.focus();
		return false;
	}
	*/
	if(frm.effStartDate.value=="")
	{
		alert("Please input Effective Start Date!!");
		frm.effStartDate.focus();
		return false;
	}
	
	if(frm.rfsDate.value=="")
	{
		alert("Please input RFS Date!!");
		frm.rfsDate.focus();
		return false;
	}
	//shourya start 
	if(trim(document.getElementById('downtimecls').value) == "")
	{
	alert("Please input Downtime Clause!!");
	frm.downtimecls.focus();
	return false;
	}
	
	//shourya end 
	if(frm.custSINo.value=="")
	{
		alert("Please input Customer Logical SI No!!");
		frm.custSINo.focus();
		return false;
	}
	//Start [004]	 AS ON		05-Jun-12
	if(productId==0 )
	{
		alert("Please select Product!!!");
		document.getElementById("txtProduct").focus();
		return false;
	}
	if(subProductId==0)
	{
		alert("Please select SubProduct !!!");		
		document.getElementById("txtSubProduct").focus();
		return false;
	}
	//[007] Start
	
	if(parallelUpgradeLSIRequire.value==0 )
	{
		alert("Please select Change Order LSI No Required!!!");
		parallelUpgradeLSIRequire.focus();
		return false;
	}else if(parallelUpgradeLSIRequire.value == 1 && parallelUpgradeLSINo1 == ""){
		alert("Please input Change Order LSI No");
		document.getElementById("parallelUpgradeLSINo1").focus();
		return false;
	}else if(parallelUpgradeLSIRequire.value == 2 && remarksParallelUpgrade.value == ""){
		alert("Please input Change Order LSI No Remarks");
		remarksParallelUpgrade.focus();
		return false;
	}
	
	
	
	//[007] End
	//End [004]	 	AS ON	05-Jun-12
	//====================[Start:[HYPR22032013006]]====================================
			//Shubhranshu
		if(callerWindowObj.document.getElementById('orderType').value=='Change')
		{
				if(($("#reasonforchange").val()=="" )||($("#txtChangeReason").val()=="-1") || ($("#txtChangeReason").val()=="")){
					//alert($("#txtChangeReason").val());
					alert("Please select reason for change!!");
					return false;
				}		
					selectedReasonForChangeId=$("#txtChangeReason").val();  //document.getElementById("changeReason").value;
					selectReasonForChangeName=$("#reasonforchange").val();			
																																																																											
					var effRRDate =frm.valeffectiveDate.value;
					if(effRRDate=="")
						{
						alert("Please Input RR Effective Date/ Effective Date  !!");
							return false;
						}
																																				
			//Shubhranshu
		}
		
		if( trim(document.getElementById('remarks').value).length>0)
		{
			 if(ValidateTextField(document.getElementById('remarks'),path,'Remarks')==false)
			 {
			 	return false;
			 }
		}
		
		if(trim(document.getElementById('downtimecls').value).length>0)
		{
			 if(ValidateTextField(document.getElementById('downtimecls'),path,'DownTimeClause')==false)
			 {
			 	return false;
			 }
		}
		if( trim(document.getElementById('cancelledOrderReference').value).length>0)
		{
			 if(ValidateTextField(document.getElementById('cancelledOrderReference'),path,'Cancel Order Reference')==false)
			 {
			 	return false;
			 }
		}
				
	//====================[End:[HYPR22032013006]]======================================	
	//[005] Start
	var jsData =
	{
		serviceId:frm.serviceID.value,
		effStartDate:frm.effStartDate.value,
		rfsDate:frm.rfsDate.value,
		customer_logicalSINumber:frm.custSINo.value,
		attRemarks:frm.attRemarks.value,
		updateType:frm.updateType.value,
		serviceTypeId:productId,
		serviceSubtypeId:subProductId,
		isPublished:isPublished,
		isAttach:isBundle,
		orderType:callerWindowObj.document.getElementById('orderType').value,
		stdReasonId:selectedReasonForChangeId,
		stdReasonName:selectReasonForChangeName,
		cancelledOrderReference:cancelledOrderReference,
		//[006] start
		parallelUpgradeLSINo1:parallelUpgradeLSINo1,
		parallelUpgradeLSINo2:parallelUpgradeLSINo2,
		parallelUpgradeLSINo3:parallelUpgradeLSINo3,
		//[006] end
		downtimeClause:downtimeclause,
		//[007] Start
		parallelUpgradeLSIRequire:parallelUpgradeLSIRequire.value,
		remarksParallelUpgrade:remarksParallelUpgrade.value,
		
		effRRDate:frm.valeffectiveDate.value//Shubhranshu
		
		//[007] End
	};
	//[005] End
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	//[001] Start
	//var lstService = jsonrpc.processes.updateServiceAttribute(jsData);
	var sessionId='<%=request.getSession().getId() %>';
	var lstService = jsonrpc.processes.validateAndSaveServiceAttribute(jsData,sessionId);
	//[001] End
	var returnMsg="";
	//if(!(parallelUpgradeLSINo1=="" && parallelUpgradeLSINo2=="" && parallelUpgradeLSINo3=="")){
			if(lstService.list.length==1){
				
						if(lstService.list[0].validationFlag==1){
							
							alert(lstService.list[0].statusMsg+"\nData has not been saved");
						}else{
								if(lstService.list[0].statusFlag==0){
						 			returnMsg+=lstService.list[0].statusMsg+" for parallel upgrade LSI "+lstService.list[0].logicalSINumber;
									}
							if(returnMsg != "")
							alert(returnMsg+"\nData has not been saved");
						}
				
			}else if(lstService.list.length>1){
				var returnMsg="";
				for (var i=0;i<lstService.list.length;i++){
					if(lstService.list[i].validationFlag==1){
						
						returnMsg+=lstService.list[i].statusMsg;
						
					}
					else{
						
						 		if(lstService.list[i].statusFlag==0){
						 			returnMsg+=lstService.list[i].statusMsg+" for parallel upgrade LSI "+lstService.list[i].logicalSINumber+"\n";
									}	
						}
					
				}
				if(returnMsg != "")
				alert(returnMsg+"\nData has not been saved");
			}
	//}

	if(lstService.list[0].executeFlag==1){
		alert("Data saved successfully");
    	//save into parent window the edit status
	    	var callerWindowObj = dialogArguments;
	   	  counterVal=callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;
	   	  var nam="servAttrEntered"+counterVal;
   		 callerWindowObj.document.getElementById(nam).value = '1';
   		//callerWindowObj.drawTable();
   		var flag=5;
   		
   		//PAGING-SERVICE-LINE-14-10-2012
   		//callerWindowObj.countService=1;
    	//callerWindowObj.drawTable();
    	window.close();
    }	
  
	//UpdateParentInterface(lstService.serviceId,serviceTypeName,selectedText.options[selectedText.selectedIndex].text);
}

		//Shubhranshu
function attachCSSOnAS(item){
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
		
//Shubhranshu		
function updateEffectiveDateLabelForRROrders(l_changeTypeId)
{
	var datelabel2= $('#dateinputrow').find("td").eq(0) ;  // ServiceAttributes.jsp
	
		if(l_changeTypeId==5)
			{
			datelabel2.text("RR Effective Date "); 
			/* $('#valeffectiveDate').val(""); */
    				}
				else
			  {	
					datelabel2.text(" Effective Date "); 
	}
}
function fetchServiceAttribute(serviceID)
{
	var callerWindowObj = dialogArguments;
		if(callerWindowObj.document.getElementById('orderType').value=='Change')
		{
		 document.getElementById('c_lsi').style.display="none";
		 document.getElementById('dateinputrow').style.display="block";  // EffectiveRRDate field visible on in case of change order
		updateEffectiveDateLabelForRROrders(document.serviceAttributes.elements['HdnChangeTypeID'].value);
			// Shubhranshu 
		}
			//Shubhranshu
				attachCSSOnAS($("[ctrltype='dropdownlink']")); //it changes the css of autosuggest dropdown link
				
	
	/*var cntr=callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;
	
	if(callerWindowObj.document.getElementById('chk'+cntr).checked==true)
	{
		document.getElementById('imgSave').style.visibility="hidden";
	}
	else
	{
		document.getElementById('imgSave').style.visibility="visible";
	}*/
	
	/*if(callerWindowObj.document.getElementById('isBillingTriggerReady').value=="ENABLE")
	{
		document.getElementById('imgSave').style.visibility="hidden";
	}
	else
	{
		document.getElementById('imgSave').style.visibility="visible";
	}*/
	
	var frm=document.getElementById('serviceAttributes');
	var jsData =
	{
		serviceId:frm.serviceID.value
	};
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	//var maxSIno = jsonrpc.processes.getLogicalSiNo();
	var lstService = jsonrpc.processes.fetchServiceAttribute(jsData);
	var a=lstService.effStartDate;
	var listSI = lstService.customer_logicalSINumbers;
	//var comboSI = document.getElementById("custSINo");
	var rfsDate=lstService.rfsDate;
	//alert("rfsDate  "+lstService.rfsDate)
		
	 /*for(j=0;j<listSI.list.length;j++)
	    { 
	    
	    	var option = document.createElement("option");
	   		option.text = listSI.list[j];
			option.value = listSI.list[j];
			
			try 
			{
				comboSI.add(option, null); //Standard
			}
			catch(error) 
			{
				comboSI.add(option); // IE only
			}
	    }
	   */
	 
	     document.getElementById("custSINo").value=lstService.customer_logicalSINumber;	
	   
	//frm.logicalSINo.value=maxSIno;
	if(a=="")
	{
	  
		//frm.effStartDate.value=''
		//frm.effEndDate.value=''
		frm.attRemarks.value=''
		frm.serviceDate.value='DRAFT'	
		frm.updateType.value=0
		//frm.logicalSINo=comboSI
	}
	else
	{
		frm.effStartDate.value=lstService.effStartDate;
		frm.rfsDate.value=lstService.rfsDate;
		//frm.effEndDate.value=lstService.effEndDate
		//frm.logicalSINo=comboSI
		frm.attRemarks.value=lstService.attRemarks
		frm.serviceDate.value='New';
		frm.updateType.value=1;
		if(lstService.downtimeClause==null)
		{
			frm.downtimecls.value='';
		}
		else
		{
		
			frm.downtimecls.value=lstService.downtimeClause
		}
		//lawkush Start
		if(lstService.cancelledOrderReference==null )
		{
			frm.cancelledOrderReference.value='';		
		}
		else
		{
			frm.cancelledOrderReference.value=lstService.cancelledOrderReference;
		}
		//lawkush End
		
		//[007] Start
		frm.parallelUpgradeLSIRequire.value = lstService.parallelUpgradeLSIRequire;
		if(lstService.parallelUpgradeLSIRequire == 1){
			frm.parallelUpgradeLSINo1.className = "inputBorder1";
		}else{
			frm.remarksParallelUpgrade.className = "inputBorder1";
		}
		
		if(lstService.remarksParallelUpgrade == null){
			frm.remarksParallelUpgrade.value = "";
		}else{
			frm.remarksParallelUpgrade.value = lstService.remarksParallelUpgrade;
		}
		//[007] End
		
		//[006] start
		//frm.parallelUpgradeLSINo.value=lstService.parallelUpgradeLSI;
		frm.parallelUpgradeLSINo1.value=lstService.parallelUpgradeLSINo1;
		frm.parallelUpgradeLSINo2.value=lstService.parallelUpgradeLSINo2;
		frm.parallelUpgradeLSINo3.value=lstService.parallelUpgradeLSINo3;
		//[006] end
	}
	if(txtServiceTypeId==381 || txtServiceTypeId==431)//added by Anoop---[03]
	{
		var combo = document.getElementById("cmbBundle");
		combo.value = lstService.isAttach;	
	}
	//Added for Disabling VCS Bundled in All change order Except Solution Change
	var hdnChangeTypeId = frm.HdnChangeTypeID.value;
	if(hdnChangeTypeId==3 || hdnChangeTypeId==1 || hdnChangeTypeId==5)
	{
		if(document.getElementById("cmbBundle")!=null)
		{
			document.getElementById("cmbBundle").disabled=true;
		}
	}
    var callerWindowObj = dialogArguments;
	//[005] Start
	if(callerWindowObj.document.getElementById('orderType').value=='New')
	{
		//[006] start
		document.getElementById('lblParallelUpgradeLSINo1').style.display='block';
		document.getElementById('valueParallelUpgradeLSINo1').style.display='block';
		document.getElementById('lblParallelUpgradeLSINo2').style.display='block';
		document.getElementById('valueParallelUpgradeLSINo2').style.display='block';
		document.getElementById('lblParallelUpgradeLSINo3').style.display='block';
		document.getElementById('valueParallelUpgradeLSINo3').style.display='block';
		//[006] end
		//[007] Start
		document.getElementById('lblParallelUpgadeLSIRequire').style.display='block';
		document.getElementById('valueParallelUpgradeLSIRequire').style.display='block';
		document.getElementById('lblRemarksParallelUpgrade').style.display='block';
		document.getElementById('valueRemarksParallelUpgrade').style.display='block';
		//[007] End
	}
    //[005] End
}
/*Shubhranshu,DnC*/		
		function createDropNCarryDialog()
		{										
			 $("#dropNCarryDialog").dialog(
			{			    	 		
    	 		width: 1000,			    	 		
         		autoOpen: false,
     			modal:true,
     			resizable:false,
     			closeOnEscape: false,			     			
     			title: "DropAndCarry",
     			close: function() { 							
				$("#dropNCarryDialog").empty();
			}
 			}); 
			 
		}
		function openDropAndCarryDialog()
		{	
			var frm=document.getElementById('serviceAttributes');
			var callerWindow=dialogArguments;
			var orderType=dialogArguments.document.getElementById('orderType').value;  //alert(orderType);
			var orderNo = callerWindow.document.getElementById('poNumber').value;
			var srvId=frm.serviceID.value; //alert(srvId);								
			var rqParams="/NewOrderAction.do?method=dropNCarrySelection&orderType="+orderType+"&serviceId="+srvId+"&viewMode="+isView+"&ordNo="+orderNo;
				//alert(path+params);	
				//alert('opening DnC dialog');
				 
				$("#dropNCarryDialog").load(path+rqParams);
				$('#dropNCarryDialog').dialog('open');		
			 
							
		}
/*Shubhranshu*/
function getUpdateData()
{
	//Shubhranshu,DnC
	if(txtServiceTypeId==181)
	{
		document.getElementById('btnDnC').style.visibility='visible';
	}
	
		createDropNCarryDialog();
	//Shubhranshu,DnC
	var frm=document.getElementById('serviceAttributes');
		var jsData =			
		{
			serviceId:frm.serviceID.value
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
		test = jsonrpc.processes.getUpdateData(jsData);
		if(test.list.length>0)
		{
			document.getElementById("serviceTypeId_1").value=test.list[0].serviceTypeId;
			document.getElementById("serviceSubTypeId_1").value=test.list[0].serviceSubtypeId;
			productlist();
			subproductlist(2);
			
			
			//document.getElementById("isPublished").value=test.list[0].isPublished;
			//var combo2 = document.getElementById("txtIsPublish");
			//combo2.value=document.getElementById("isPublished").value;
			//shourya start
			
			//shourya end 
			 if (test.list[0].downtimeClause==null)
			 {
			 	document.getElementById("downtimecls").value='';
			 }
		 	else
			{
 				document.getElementById("downtimecls").value=test.list[0].downtimeClause;
 			}
		}
		else
		{
			productlist();
		}
		//============[ START CLEP ]==========================
			enableDisableCLEP();
		//============[ END CLEP]==========================
		
		
		//--added by Vijay--//
		//here fetching change reason
		var callerWindowObj = dialogArguments;
		if(callerWindowObj.document.getElementById('orderType').value=='Change')
		{
			//var changeReason;
			document.getElementById('lblChangeReason').style.display='block';
			document.getElementById('valueChangeReason').style.display='block';
		    
		    //======[Start:[HYPR22032013006]]=============================================
				//populateReasonForChange(); //
			//======[End:[HYPR22032013006]]===============================================			
			
			//Shubhranshu
			var jsdatalist=jsonrpc.processes.getChangeReasonNeffectiveDate(jsData);

			$("#reasonforchange").val(jsdatalist.lblChangeReason);
			$("#txtChangeReason").val(jsdatalist.valChangeReason);
			document.getElementById("valeffectiveDate").value=jsdatalist.effectiveDate;
			//Shubhranshu
		}
}
function productlist()  // To get line items with charges in them
{ 
	var tr, td, i, j, oneRecord;
    var test;
    var frm=document.getElementById('serviceAttributes');
 	var combo = document.getElementById("txtProduct");

    var iCountRows = combo.length;
	  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       combo.remove(1);
	   }
	   
	
	var jsData =			
		{
		serviceId:frm.serviceID.value
		};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	test = jsonrpc.processes.getProductAndId(jsData);
	
	for(j=0;j<test.list.length;j++)
    {
    	var combo = document.getElementById("txtProduct");
    	var option = document.createElement("option");
   		option.text = test.list[j].product_name_for_fx;
   		option.value = test.list[j].serviceTypeId;
   		option.title = test.list[j].product_name_for_fx;
		try 
		{
			combo.add(option, null); //Standard
		}
		catch(error) 
		{
			combo.add(option); // IE only
		}
    }
    if(document.getElementById('serviceTypeId_1')!=null)
    	combo.value = document.getElementById('serviceTypeId_1').value;
    
}
function subproductlist(flag) // To get line items with no charges in them
{ 
	var tr, td, i, j, oneRecord;
    var test;
    var serviceTypeId = document.getElementById("txtProduct").value;
 	var combo1= document.getElementById("txtSubProduct");
	document.getElementById("downtimecls").value= ' ' ; // Shourya
	   
	var iCountRows1 = combo1.length;
	  
	for (i = 1; i <  iCountRows1; i++)
   	{
       combo1.remove(1);
   	}
	
	if(document.getElementById("txtProduct").value==0)
 	{
 		return false;
 	}
	      
	var jsData =			
		{
		serviceTypeId:serviceTypeId
		};		
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	test = jsonrpc.processes.getSubProductAndId(jsData);
	
    for(j=0;j<test.list.length;j++)
   	{
    	var combo1 = document.getElementById("txtSubProduct");
    	var option = document.createElement("option");
   		option.text = test.list[j].serviceSubTypeName;
   		option.value = test.list[j].serviceSubtypeId;
   		option.title = test.list[j].serviceSubTypeName;
		try 
		{
			combo1.add(option, null); //Standard
		}
		catch(error) 
		{
			combo1.add(option); // IE only
		}
   	}
    if(flag==2 && document.getElementById('serviceSubTypeId_1')!=null)
    {
    	combo1.value = document.getElementById('serviceSubTypeId_1').value;
    }
}
//Shourya start 

var downtime;
function downtimeClause()
{
	var j;
	var product = document.getElementById("txtProduct").value;
	var subproduct = document.getElementById("txtSubProduct").value;
	document.getElementById("downtimecls").value= ' ' ; // Shourya
	if(document.getElementById("txtSubProduct").value==0)
	 	{
	 		return false;
	 	}
	var jsData =			
		{
		serviceId:product,
		serviceSubtypeId:subproduct
		};
	 downtime = jsonrpc.processes.getDowntimeClause(jsData);
	 document.getElementById("downtimecls").value = downtime.list[0].downtimeClause;

}
document.onkeydown=checkKeyPress;
function checkKeyPress()
{
	if (event.ctrlKey && event.keyCode == 83) {
		var order_creation_source;
		//stop ctrl S for CLEP orders
		var callerWindowObj = dialogArguments;
		if(isView !=1){
			order_creation_source = callerWindowObj.document.getElementById('hdnOrderCreationSourceId').value;				
		}
			
		if(order_creation_source==ORDER_CREATION_SOURCE || isView ==1 )
	  	{
	  		return false;	
	  	}else{
				event.keyCode=4; 
				saveServiceAttributes();
				document.getElementById("imgSave").tabIndex = -1;
				document.getElementById("imgSave").focus();		
		}		   				   						
   }   
}
//end[002]
//[003] Start
function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

function getTip_DD(obj,value,objName)
{	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('productCatelog');
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}
//[003] End
function disableAttributesInView()
{	
	//alert('isView = '+isView);
	var callerWindowObj = dialogArguments;
	var stage = callerWindowObj.document.getElementById('stageName').value;
	
	//var orderNo = callerWindowObj.document.getElementById('poNumber').value;
	//alert('orderNo = '+orderNo);
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");		
	//disableAttributesInViewModeFlag = jsonrpc.processes.disableAttributesInViewModeFlag(orderNo);
	//alert('disableAttributesInViewModeFlag = '+disableAttributesInViewModeFlag);

	//alert('isView = '+isView);
	//var callerWindowObj = dialogArguments;
	//var orderNo = callerWindowObj.document.getElementById('poNumber').value;
	//alert('orderNo = '+orderNo);
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");		
	//disableAttributesInViewModeFlag = jsonrpc.processes.disableAttributesInViewModeFlag(orderNo);
	//alert('disableAttributesInViewModeFlag = '+disableAttributesInViewModeFlag);
	//[006] start
	if(stage == 'AM' || stage == 'New'){
		
		enableParallelUpgradeFunctionality();
		//document.getElementById("parallelUpgradeLSINo1").disabled=false;
		//document.getElementById("parallelUpgradeLSINo2").disabled=false;
		//document.getElementById("parallelUpgradeLSINo3").disabled=false;
	}
	else
		{
		disableParallelUpgradeFunctionality();
		//document.getElementById("parallelUpgradeLSINo1").disabled=true;
		//document.getElementById("parallelUpgradeLSINo2").disabled=true;
		//document.getElementById("parallelUpgradeLSINo3").disabled=true;
		}
	//[006] end
	
	if(isView == 1 || stage == 'Published' || stage == 'Billing Trigger')
	{
		document.getElementById("custSINo").disabled=true;
		document.getElementById("imgSave").disabled=true;
		document.getElementById("effStartDate").disabled=true;
		document.getElementById("rfsDate").disabled=true;
		document.getElementById("downtimecls").disabled=true;
		document.getElementById("txtProduct").disabled=true;
		document.getElementById("txtSubProduct").disabled=true;
		document.getElementById("remarks").disabled=false;
		document.getElementById("remarks").readOnly=true;
		document.getElementById("custSINo1").disabled=true;
		//Shubhranshu
		document.getElementById("reasonforchange").disabled=true;
		document.getElementById("valeffectiveDate").disabled=true;
		 //[006] start
		//[005] Start
		//document.getElementById("parallelUpgradeLSINo1").disabled=true;
		//document.getElementById("parallelUpgradeLSINo2").disabled=true;
		//document.getElementById("parallelUpgradeLSINo3").disabled=true;
		//[005] End
		//[006] End
		if(document.getElementById("cmbBundle")!=null)
		{
			document.getElementById("cmbBundle").disabled=true;
		}
		//[007] Start
		disableParallelUpgradeFunctionality();
		//[007] End
	}
}

//start :CLEP Enable Disable Order Entry
function enableDisableCLEP(){
	var callerWindowObj = dialogArguments;
	var orderNo=callerWindowObj.document.getElementById('poNumber').value;	
	var stateid=getOrderStateforClep(orderNo,path);			
	orderEntryEnableDisable('SERVICEATTRIBUTE',stateid, null);
}
//end clep
function checkMappingWithL3Mpls(id)
{   var serviceTypeId= <%=request.getParameter("serviceTypeId")%>
	var val=id.value;
	var frm=document.getElementById('serviceAttributes');	
	var serviceId = frm.serviceID.value;	
	if(val==0)//For bundled Yes to No
	{
		/*var l3MplsListDetails = jsonrpc.processes.getL3MplsMappingCount(serviceId);
		if(l3MplsListDetails!='')
		{
			alert("Please First Remove The Mapping Of This VCS Bridge Service LSI from : "+l3MplsListDetails);
			document.getElementById("cmbBundle").value = 1;	
			return false;
		}*/
	}
	else 
	{	//for bundled No To Yes
		var hdnChangeTypeId = frm.HdnChangeTypeID.value;		
		if(hdnChangeTypeId=="null")//new order validation for dummy line item 
		{
			var isDummyLineAdded="isDummyLineAdded";			
			var isDummyLineItem = jsonrpc.processes.isVCS_LSIBundled(serviceId,isDummyLineAdded);
			if(isDummyLineItem==1)
			{   if(serviceTypeId==381)
				alert("You can not Bundled  VCS Service because Dummy Line Item are Added in the service.");
			    else if(serviceTypeId==431)
			    	alert("You can not Bundled  OVCC Service because Dummy Line Item are Added in the service.");
				document.getElementById("cmbBundle").value = 0;	
				return false;
			}
		}		
		else if(hdnChangeTypeId==3 || hdnChangeTypeId==1 || hdnChangeTypeId==5)
		{
			alert("Non Bundled VCS Bridge Service Can not Bundled in Change Order");
			document.getElementById("cmbBundle").value = 0;	
			return false;
		}
		else if(hdnChangeTypeId==2)
		{			
			var isPreviousOrderVCSLSIBundled="isPreviousOrderVCSLSIBundled";			
			var isPreviousOrderVCSLSIBundled = jsonrpc.processes.isVCS_LSIBundled(serviceId,isPreviousOrderVCSLSIBundled);
			if(isPreviousOrderVCSLSIBundled==0)
			{
				alert("Non Bundled VCS Bridge Service Can not Bundled in Change Order");
				document.getElementById("cmbBundle").value = 0;	
				return false;
			}
		}
	}
}
//====================[Start:[HYPR22032013006]]===============================
<%-- function populateReasonForChange()
{	
	var tr, td, i, j, oneRecord;
    var test;
    var interFaceStdReason=2;
   var combo = document.getElementById("changeReason");	
   var iCountRows = combo.length;
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(i);
   }
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var stdReason = jsonrpc.processes.populateReasonForChange(interFaceStdReason);
	
	    for(j=0;j<stdReason.list.length;j++)
	    {
	    	var option = document.createElement("option");	    	
	   		option.text = stdReason.list[j].reasonName;
	   		option.title = stdReason.list[j].reasonName;
			option.value = stdReason.list[j].reasonID;
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
 --%>
//[007] Start
//Function enableParallelUpgradeFunctionality() enables fields without making fields empty

function enableParallelUpgradeFunctionality(){
	
	var parallelUpgradeLSIRequire = document.getElementById("parallelUpgradeLSIRequire");
	var remarksParallelUpgrade = document.getElementById("remarksParallelUpgrade");
	var parallelUpgradeLSINo1 = document.getElementById("parallelUpgradeLSINo1");
	var parallelUpgradeLSINo2 = document.getElementById("parallelUpgradeLSINo2");
	var parallelUpgradeLSINo3 = document.getElementById("parallelUpgradeLSINo3");
	parallelUpgradeLSIRequire.disabled = false;
	if(parallelUpgradeLSIRequire.value == 0){
		parallelUpgradeLSINo1.disabled=true;
		parallelUpgradeLSINo2.disabled=true;
		parallelUpgradeLSINo3.disabled=true;
		remarksParallelUpgrade.disabled=true;
		parallelUpgradeLSINo1.className="inputBorder2";
		remarksParallelUpgrade.className="inputBorder2";
	}
	else if(parallelUpgradeLSIRequire.value == 1){
		parallelUpgradeLSINo1.disabled=false;
		parallelUpgradeLSINo2.disabled=false;
		parallelUpgradeLSINo3.disabled=false;
		parallelUpgradeLSINo1.className="inputBorder1";
		remarksParallelUpgrade.className="inputBorder2";
		remarksParallelUpgrade.disabled=true;
	}	
	else if(parallelUpgradeLSIRequire.value == 2){
		remarksParallelUpgrade.disabled=false;
		remarksParallelUpgrade.className="inputBorder1";
		parallelUpgradeLSINo1.className="inputBorder2";
		parallelUpgradeLSINo1.disabled=true;
		parallelUpgradeLSINo2.disabled=true;
		parallelUpgradeLSINo3.disabled=true;
		
	}
	//yre-no enable
	//call onUserChangeParallelUpgradeRequire
}

function disableParallelUpgradeFunctionality(){
	document.getElementById("parallelUpgradeLSIRequire").disabled = true;
	document.getElementById("remarksParallelUpgrade").disabled = true;
	document.getElementById("parallelUpgradeLSINo1").disabled = true;
	document.getElementById("parallelUpgradeLSINo2").disabled = true;
	document.getElementById("parallelUpgradeLSINo3").disabled = true;
	
}

//Function onUserChangeParallelUpgradeRequire() enables fields and makes disabled field empty

function onUserChangeParallelUpgradeRequire(parallelUpgradeLSIRequireValue){
	 
	var remarksParallelUpgrade = document.getElementById("remarksParallelUpgrade");
	var parallelUpgradeLSINo1 = document.getElementById("parallelUpgradeLSINo1");
	var parallelUpgradeLSINo2 = document.getElementById("parallelUpgradeLSINo2");
	var parallelUpgradeLSINo3 = document.getElementById("parallelUpgradeLSINo3");
	
	if(parallelUpgradeLSIRequireValue == 0){
		parallelUpgradeLSINo1.disabled=true;
		parallelUpgradeLSINo2.disabled=true;
		parallelUpgradeLSINo3.disabled=true;
		remarksParallelUpgrade.disabled=true;
		parallelUpgradeLSINo1.className="inputBorder2";
		remarksParallelUpgrade.className="inputBorder2";
	}
	else if(parallelUpgradeLSIRequireValue == 1){
		//alert("Done"+parallelUpgradeLSIRequireValue);
		parallelUpgradeLSINo1.disabled=false;
		parallelUpgradeLSINo2.disabled=false;
		parallelUpgradeLSINo3.disabled=false;
		parallelUpgradeLSINo1.className="inputBorder1";
		remarksParallelUpgrade.className="inputBorder2";
		//document.getElementById("parallelUpgradeLSINo1").style.backgroundColor = "#FFFF99";
		//document.getElementById("remarksParallelUpgrade").style.backgroundColor = "#FFFFFF";
		remarksParallelUpgrade.value= "";
		remarksParallelUpgrade.disabled=true;
	}	
	else if(parallelUpgradeLSIRequireValue == 2){
		remarksParallelUpgrade.disabled=false;
		remarksParallelUpgrade.className="inputBorder1";
		parallelUpgradeLSINo1.className="inputBorder2";
		//document.getElementById("remarksParallelUpgrade").style.backgroundColor = "#FFFF99";
		//document.getElementById("parallelUpgradeLSINo1").style.backgroundColor = "#FFFFFF";
		parallelUpgradeLSINo1.value= "";
		parallelUpgradeLSINo2.value= "";
		parallelUpgradeLSINo3.value= "";
		parallelUpgradeLSINo1.disabled=true;
		parallelUpgradeLSINo2.disabled=true;
		parallelUpgradeLSINo3.disabled=true;
		
	}
	
}
//[007] End
var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
//====================[End:[HYPR22032013006]]===============================
// Shubhranshu
function getChangeReason()
{	
	/* alert("Fetching auto-suggested Value !!  "); */
	getAutoSuggestUtility($("#reasonforchange"));
	$("#reasonforchange").autocomplete( "search", "" );
	$("#reasonforchange").focus();
}
</script>
<body onload="fetchServiceAttribute(<%=request.getParameter("serviceID")%>);getUpdateData();disableAttributesInView();">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
	<html:form action="/NewOrderAction" styleId="serviceAttributes" method="post">
		<fieldset class="border1" >
			<legend> <b>Service Details</b> </legend>
			<table  border="0" cellspacing="0" cellpadding="0" align="center" width="100%" >
				<tr>
					<td width="18%">Service Stage</td>
					<td width="14%"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" name="serviceDate" class="inputBorder1" readonly="readonly" style="width:120px;" value="DRAFT"></td>
					<td width="15%"> Customer Logical SI No</td>
					<td width="15%">
						   
						   <html:text onmouseover="getTip(value)" onmouseout="UnTip()" property="custSINo" styleId="custSINo" styleClass="inputBorder1" style="width:110px;float:left;" readonly="true"></html:text>
										<div class="searchBg1" id=c_lsi ><a href="#"  id="custSINo1" title="Select Cust Logical SI"  onClick="selectCustLogicalSI()">...</a></div> 
					</td>		
					<td width="41%" colspan="3"><img id="imgSave" src="gifs/top/4.gif" onClick="saveServiceAttributes()" style="visibility: visible"></td>        
				</tr>
				<tr>
					<td width="18%">Effective Start Date </td>
					<%SimpleDateFormat sdf_Date=new SimpleDateFormat("dd/MM/yyyy");%>
					<%
					String dt=sdf_Date.format(new Date(System.currentTimeMillis()));
					%>
					    <td width="14%">
						<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" showcal="date" class="inputBorder1" style="width:80px;" name="effStartDate" id="effStartDate" onBlur="if(this.value.length > 0){return checkdate(this)}"  value="<%=dt%>" size="46">	
						<a href="javascript:show_calendar(serviceAttributes.effStartDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."style="vertical-align: bottom"/></a>
					</td>
					
					<td width="15%"> RFS Date</td>
					<td width="15%"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" showcal="date" class="inputBorder1" style="width:80px;" name="rfsDate" id="rfsDate" onBlur="if(this.value.length > 0){return checkdate(this)}"   size="46">
				   <a href="javascript:show_calendar(serviceAttributes.rfsDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif"
				border="0px" alt="Loading..."
				style="vertical-align: bottom"></a></td>
					 
					
					<td width="41%" colspan="0"></td>        
				</tr>
				<tr>
				<td align="left" nowrap height="40" width="182">Product
				Name</td>
				<td align="left" nowrap height="40" width="143">
				<select onfocus="getTip_DD(this,this.selectedIndex,this.name)"
					name="txtProduct" id="txtProduct" onchange="subproductlist(1)"
					style="width:150px;float:left" class="dropdownMargin">
					<option value="0">Select a Product</option>
				</select>
				</td>
				<td align="left" nowrap height="40"  width="182">Sub Product
				Name</td>
				<td align="left" nowrap height="40" width="41">
				<select onfocus="getTip_DD(this,this.selectedIndex,this.name)"
					name="txtSubProduct" id="txtSubProduct" onchange="downtimeClause(this)"
					style="width:150px;float:left" class="dropdownMargin">
					<option value="0">Select a Sub Product</option>
				</select></td>
			</tr>
				<!--//added by vijay -->
			
			
			<!-- [007] Start-->
				<td id="lblParallelUpgadeLSIRequire" style="display:none; ">
				        Change Order LSI No Required
				</td>
				<td id = "valueParallelUpgradeLSIRequire" style = "display:none; ">
				          <select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="parallelUpgradeLSIRequire" style="width:90px"
						   class="dropdownMargin" id="parallelUpgradeLSIRequire"  onchange=onUserChangeParallelUpgradeRequire(this.value);>
						   <option value="0">Select</option>
						   <option value="1">Yes</option>
						   <option value="2">No</option>
						   </select>
				</td>
				
				<td id = "lblRemarksParallelUpgrade" style = "display:none;">
				        Change Order LSI No Remarks
				</td>
				<td id = "valueRemarksParallelUpgrade" style = "display:none;">
				     <input value = "" onmouseover = "getTip(value)" onmouseout = "UnTip()" type = "text" id = "remarksParallelUpgrade" name = "remarksParallelUpgrade" class = "inputBorder2" style = "width:120px" maxlength = "250">
				</td>
				<!-- [007] End-->
			<!--adding a lable of 'reason for change'  -->
			<tr>
				<!--[006] start -->
				<!--005] Start -->
				<td id="lblParallelUpgradeLSINo1" style="display:none;" nowrap height="40" >
						Change Order LSI No1
				</td>
				<td id="valueParallelUpgradeLSINo1" style="display:none; ">
					<input value="" onmouseover="getTip(value)" onmouseout="UnTip()"  type="text" id="parallelUpgradeLSINo1" name="parallelUpgradeLSINo" class="inputBorder2"  style="width:120px;" maxlength="188">
				</td>
				<!--005] End -->
				
				<td id="lblParallelUpgradeLSINo2" style="display:none;">
						Change Order LSI No2
				</td>
				<td id="valueParallelUpgradeLSINo2" style="display:none; ">
					<input value="" onmouseover="getTip(value)" onmouseout="UnTip()"  type="text" id="parallelUpgradeLSINo2" name="parallelUpgradeLSINo" class="inputBorder2"  style="width:120px;" maxlength="188" >
				</td>
				</tr>
				<tr>
				<td id="lblParallelUpgradeLSINo3" style="display:none;">
						Change Order LSI No3
				</td>
				
				<td id="valueParallelUpgradeLSINo3" style="display:none; ">
					<input value="" onmouseover="getTip(value)" onmouseout="UnTip()"  type="text" id="parallelUpgradeLSINo3" name="parallelUpgradeLSINo" class="inputBorder2"  style="width:120px;" maxlength="188" >
				</td>
				<!--[006] End -->
				<!--//added by vijay -->
				<!--adding a lable of 'reason for change'  -->
				<td id="lblChangeReason" style="display:none; " align="left" nowrap height="40" width="182" >
						Reason For Change
				</td>
				<td id="valueChangeReason" style="display:none; ">
						<!--<input onmouseover='getTip(value)' onmouseout='UnTip()' style="width:130px;" name="changeReason" class="inputDisabled" size="20" readonly="true" value=""/>-->
						<!--insert here a ctrltype dropdown -->
						<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:120px" isRequired="0"  name="reasonforchange" ctrltype='dropdown' retval='AUTOSUGGESTCHANGEREASON' id="reasonforchange" class="dropdownMargin" readonly><a id="reasonforchangeLinkId" name="reasonforchangeLinkId" ctrltype='dropdownlink' onclick="javascript:getChangeReason()">Show</a>
										<input type="hidden" value="" name="txtChangeReason" id="txtChangeReason" isrequired="0">
									<input type="hidden" name="hdnChangeReason">
						<!-- <select onfocus='getTip_DD(this,this.selectedIndex,this.name)' name="changeReason" style="width:150px" id="changeReason" class="dropdownMargin" >
						<option value="0">Select Reason for Change</option>
						</select> -->
				</td>
			</tr>
				<!-- end of code -->
				
			<%
			String txtServiceTypeId=request.getParameter("serviceTypeId");			
			if(("381").equals(txtServiceTypeId) ||("431").equals(txtServiceTypeId) )  //added by Anoop for OVCC---[02]
			{
			 %>
			<tr>
				<td align="left" nowrap height="40" width="182">Bundle</td>
				<td align="left" nowrap height="40" width="143">
					<select onfocus="getTip_DD(this,this.selectedIndex,this.name)"
						name="cmbBundle" id="cmbBundle" onchange="checkMappingWithL3Mpls(this)"
						style="width:150px;float:left" class="dropdownMargin" >
						<option value="1">Yes</option>
						<option value="0">No</option>						
					</select>
				</td>
			</tr>
			<%} %>
			<!-- Cancelled Order/Service Reference lawkush Start -->
				<tr>
					<td width="18%">Cancelled Order/Service Reference</td>
					
		<!--shourya DownTime Clause start  -->			
						<td width="20%"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" id="cancelledOrderReference" name="cancelledOrderReference" class="inputBorder2"  style="width:120px;" ></td>
					<td align="left" nowrap height="40" width="182">DownTime Clause</td>
				<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder1" name="downtimecls"
				id="downtimecls" style="width:150px;float:left" size="134" maxlength="100"></td>
		<!--shourya end   -->	
		<!--Shubhranshu, Start,ClearChannel:DropNCarry  -->
		<td>
		</td>
		<td>
		<input type=button id="btnDnC" value="Drop&Carry" style="visibility: hidden" onclick="openDropAndCarryDialog()">
		<input type="hidden" value="0" id="hdnBtnCnt">
		</td>
		<!--Shubhranshu, End,ClearChannel:DropNCarry  -->
				</tr>
				<!-- Cancelled Order/Service Reference lawkush End -->
				<tr>
					<td width="18%">Remarks</td>
					<td colspan="4">
						<textarea onmouseover="getTip(value)" id="remarks" onmouseout="UnTip()" type="text" class="inputBorder2" style="width:750px;float:left" maxlength="1000" rows="5" name="attRemarks"></textarea>
						<input type="hidden" value="" name="updateType">
					</td>   
					<input type="hidden" name="serviceID" value="<%=request.getParameter("serviceID") %>">
					<input type="hidden" name="HdnChangeTypeID" value="<%=request.getParameter("HdnChangeTypeID") %>">
					<input type="hidden" id="serviceTypeId_1">
					<input type="hidden" id="serviceSubTypeId_1">
					<input type="hidden" id="isPublished">
				</tr>
				<tr><td colspan="4" align="left"><B><font color="red">* This Product/Sub-Product will be printed on all Billing Reports</font></B></td></tr>
				&nbsp;
				&nbsp;
			
				<%-- <%SimpleDateFormat sdf_Date=new SimpleDateFormat("dd/MM/yyyy");%>
					<%
					String dt=sdf_Date.format(new Date(System.currentTimeMillis()));
					%> --%>
				<tr id="dateinputrow" style="display: none;">
				<td id="lbleffectiveDate" name="lbleffectiveDate" align="left">Effective Date</td>
				<td align="left"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" showcal="date" class="inputBorder1" style="width:80px;" name="valeffectiveDate" id="valeffectiveDate" value="<%=dt %>" onBlur="if(this.value.length > 0){return checkdate(this)}" size="50">
				   <a href="javascript:show_calendar(serviceAttributes.valeffectiveDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif"
				border="0px" alt="Loading..."
				style="vertical-align: bottom"></a></td>
				</tr>
			</table>
		</fieldset>

	</html:form>
	<!--DnC,Dialog,Shubhranshu -->
		<div id = 'dropNCarryDialog'></div>				
</body>
</html>
					
