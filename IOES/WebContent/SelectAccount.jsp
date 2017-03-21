<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	4-Feb-11	00-05422		Sending ShortCode on New Order -->
<!--[002]	 ROHIT VERMA	18-Feb-11	00-05422		Fetching Region and Zone AGainst an Account -->
<!--[003]	 ANIL KUMAR 	23-msr-11	00-05422		Setting Paging ans sorting in select account popup window-->
<!--[004]	 LAWKUSH 		06-MAY-11	00-05422		Setting Paging ans sorting -->
<!--[006]	 ANIL KUMAR		23-June-11	00-05422     	Show popup for view bcpdetails  -->
<!--[007]	 LAWKUSH 		17-OCT-11	00-05422		For making Project Manager   dropdown into textbox -->
<!--[008]	 Neelesh		24-May-13	CSR00-08463     For Addition of Category  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Account</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script src="js/jquery-latest.js"></script>	
<script language="javascript" type="text/javascript">
// getting global JSON object
jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
//Rakshika : Code Start
var flag=0;
//Rakshika : Code ends
var IS_FOUND_ACCOUNT_CRM=-1;
var isFromNewOrder=0;
//[001] Start
function getInfoAndUpdate(accID,accName,phno,LOB,ACMgr,PJTMgr,SPFirstName,SPLastName,SPPhNo,SPEmail,Region,regionId,Zone,acmgrPhno,acmgrEmail,m6ShortCode,crmAccountId,collectionMgr,osp,circle,ProjectMgrId,category,groupName,serviceSegment) 
//[001] End
{
	var source="<%=request.getParameter("source")%>";
	if(source=="bulkupload"){
		//do nothing for onclick on account from source of bulkuplaod
	}else{
	var callerWindowObj = document.searchForm;
	callerWindowObj.accountID.value = unescape(accID);
	callerWindowObj.crmAccountNo.value = unescape(crmAccountId);
	callerWindowObj.accountName.value = unescape(accName);
	callerWindowObj.lob.value = unescape(LOB);
	callerWindowObj.accountManager.value = unescape(ACMgr);
	callerWindowObj.projectManagerID.value=ProjectMgrId;
  	callerWindowObj.projectManager.value=unescape(PJTMgr);
	callerWindowObj.AttributeVal_2.value = unescape(acmgrPhno);
	callerWindowObj.AttributeVal_3.value = unescape(acmgrEmail);
	callerWindowObj.region.value = unescape(Region);
	callerWindowObj.regionId.value=unescape(regionId);
	callerWindowObj.shortCode.value=unescape(m6ShortCode);
	callerWindowObj.collectionMgr.value=unescape(collectionMgr);
	callerWindowObj.osp.value = unescape(osp);
	callerWindowObj.circle.value=unescape(circle);//added on 9-jan-2013, circle work
	callerWindowObj.category.value=unescape(category);
	callerWindowObj.groupName.value=unescape(groupName);
	callerWindowObj.serviceSegment.value=unescape(serviceSegment);
	fillZoneCombo1(regionId, callerWindowObj.zone);
	flag=1;
	jQuery.noConflict();
	//fnGetQuoteNo();
	//fnGetOpportunityId();
	$("#selectAccountDialog").dialog('close');
	$("#selectAccountDialog").empty();
	//window.close();
	}
}

//TODO Use jquery
function fillZoneCombo1(regionId, combo){
	//var callerWindowObj = dialogArguments;
	var tr, td, i, j, oneRecord;
	//var combo = callerWindowObj.document.searchForm.zone;
	var iCountRows = combo.length;
	for (i = 1; i <  iCountRows; i++){
	       combo.remove(1);
	}
	//combo.innerHTML="";
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	//var accId=unescape(accID);
	var zoneList = jsonrpc.processes.populateZoneList(regionId);
	//var optStr ="";
	for(j=0;j<zoneList.list.length;j++){
	   	//var option = callerWindowObj.document.createElement("option");
	   	var option = document.createElement("option");
	  	option.text = zoneList.list[j].zoneName;
		option.value = zoneList.list[j].zoneId;
		option.title = zoneList.list[j].zoneName;
		try {
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
		//optStr += '<option value="' + zoneList.list[j].zoneId +'">' + zoneList.list[j].zoneName + '</option>';
	}
	//combo.innerHTML=optStr;
}
function fillProjectManagerCombo(accID){
	//var callerWindowObj = dialogArguments.document.searchForm;
	var callerWindowObj = document.searchForm;
	var tr, td, i, j, oneRecord;
	var projectManagerNameList;
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var accId=unescape(accID);
	projectManagerNameList = jsonrpc.processes.getProjectManagerNameList(accId);
    callerWindowObj.projectManagerID.value=projectManagerNameList.list[0].projectManagerID;
    callerWindowObj.projectManager.value=projectManagerNameList.list[0].projectManager;
	//End[007]
}
//start [003]
//TODO Use jquery and use innerhtml to fill the datatable
function DrawAccountListTable(){
	var str;
	var frm=document.getElementById('accountFormId');
	//var mytable = document.getElementById('tabaccountType');	
	var mytable = document.getElementById('accountTypeTable');	
	/*var iCountRows = mytable.rows.length;
		
	for (i = 1; i <  iCountRows; i++){
		mytable.deleteRow(1);
	}*/   	
	mytable.innerHTML = "";
	    
	document.getElementById('selAccTxtPageNumber').value= pageNumber;
	sync();	   	   	   	   
	   
	var jsData ={
		startIndex:startRecordId,
		endIndex:endRecordId,
		sortBycolumn:sortByColumn,
		sortByOrder:sortByOrder,
		accountName:document.getElementById('txtAccountName').value,
		accountIDString:document.getElementById('txtAccountID').value,
		shortCode:document.getElementById('txtShortCode').value,
		osp:document.getElementById('txtOsp').value
	};
			
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstAccount = jsonrpc.processes.getAccountDetailsWithSorting(jsData);				
	iTotalLength=lstAccount.list.length;	
	if(iTotalLength !=0){
		iNoOfPages = lstAccount.list[0].maxPageNo;
	}else{     
	    iNoOfPages=1;
	}
	document.getElementById('selAccTxtMaxPageNo').value=iNoOfPages;
	var tableDataStart;
	var tableDataEnd = "</td>";
	var tableRowStart;
	var tableRowEnd = "</tr>";
	//var tableCol;
	var tableStr = "<table width='100%' border='1'  id='tabaccountType' align='center' cellpadding='0' cellspacing='0' class='tab2' >"
						+ "<tr class='grayBg'>"
						+ "<td width='2%' align='center'>Select</td>"
						+ "<td width='8%' align='center'>"
						+ "<a href='#' onclick=sortOrder('CRMACCOUNTNO','SELECTACCOUNT')>Account No</a>"
						+ "</td>"
						+ "<td width='8%' align='center'>"
						+ "<a href='#' onclick=sortOrder('M6shortCode','SELECTACCOUNT')>Short Code</a>"
						+ "</td>"
						+ "<td width='20%' align='center'>"
						+ "<a href='#' onclick=sortOrder('ACCOUNTNAME','SELECTACCOUNT')>Account Name</a>"
						+ "</td>"
						+ "<td width='20%' align='center'>"
						+ "<a href='#' onclick=sortOrder('OSP','SELECTACCOUNT')>OSP</a>"
						+ "</td>"
						+ "<td width='20%' align='center'>View BCP/Dispatch Details</td>"
						+ "</tr>";					
	var tableData;
	if(iTotalLength==0){
		tableRowStart = "<tr>";
	    tableStr = tableStr + tableRowStart;
			
		tableDataStart = "<td align='center' vAlign='top' colSpan = '3'>";		
		tableData='NO RECORD FOUND';
		tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		tableStr = tableStr + tableRowEnd + "</table>";
 	  	mytable.innerHTML = tableStr;	
		
		/*var tblRow=document.getElementById('tabaccountType').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;*/
		
		document.getElementById('first').disabled=true;
	    document.getElementById('prev').disabled=true;
	    document.getElementById('last').disabled=true;
	    document.getElementById('next').disabled=true;
	}else{
		var colors=new Array("normal","lightBg");
		for (i = 0 ; i <iTotalLength; i++){
			tableRowStart = "<tr class='" + colors[parseInt(i)%2] + "'>";
	    	tableStr = tableStr + tableRowStart;
			
			tableDataStart = "<td align='center' vAlign='top'>";		
				tableData="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+escape(lstAccount.list[i].accountID) + "','"+ escape(lstAccount.list[i].accountName) + "','"+ escape(lstAccount.list[i].accphoneNo) + "','" + escape(lstAccount.list[i].lob) + "','"+ escape(lstAccount.list[i].accountManager) + "','"+ escape(lstAccount.list[i].projectManager) + "','"+ escape(lstAccount.list[i].spFirstname) + "','"+ escape(lstAccount.list[i].spLastName) +	"','"+ escape(lstAccount.list[i].spLPhno) + "','"+ escape(lstAccount.list[i].spLEmail) + "','"+ escape(lstAccount.list[i].region) + "','"+ escape(lstAccount.list[i].regionIdNew) + "','"+ escape(lstAccount.list[i].zone) + "','"+ escape(lstAccount.list[i].acmgrPhno) + "','"+ escape(lstAccount.list[i].acmgrEmail) + "','"+escape(lstAccount.list[i].m6ShortCode) + "','"+ escape(lstAccount.list[i].crmAccountId) + "','"+ escape(lstAccount.list[i].collectionMgr) + "','" + escape(lstAccount.list[i].osp) +"','"+ escape(lstAccount.list[i].circle) +  "','"+ escape(lstAccount.list[i].projectManagerID) +  "','"+ escape(lstAccount.list[i].category) +  "','"+ escape(lstAccount.list[i].groupName) +  "','"+escape(lstAccount.list[i].serviceSegment)+"') />";
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
			tableDataStart = "<td align='center' vAlign='top'>";		
			tableData=lstAccount.list[i].crmAccountId;
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
			tableDataStart = "<td align='center' vAlign='top'>";		
			tableData=lstAccount.list[i].m6ShortCode;
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			
			tableDataStart = "<td align='center' vAlign='top'>";		
			tableData=lstAccount.list[i].accountName;
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			
			tableDataStart = "<td align='center' vAlign='top'>";		
			tableData=lstAccount.list[i].osp;
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
		
			tableDataStart = "<td align='center' vAlign='top'>";		
			tableData="<a href='#' onclick='viewBCPDetailsPopup("+lstAccount.list[i].accountID+")'>View BCP Details</a>";
			tableData=tableData+"&nbsp;|&nbsp;<a href='#' onclick='viewDispatchDetailsPopup("+lstAccount.list[i].accountID+")'>View Dispatch</a>";
			tableStr = tableStr + tableDataStart + tableData + tableDataEnd;
			
			tableStr = tableStr + tableRowEnd;		
				 		
	 	/*var colorValue=parseInt(i)%2;	 		  
		var tblRow=document.getElementById('tabaccountType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		//[001] Start
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+escape(lstAccount.list[i].accountID) + "','"+ escape(lstAccount.list[i].accountName) + "','"+ escape(lstAccount.list[i].accphoneNo) + "','" + escape(lstAccount.list[i].lob) + "','"+ escape(lstAccount.list[i].accountManager) + "','"+ escape(lstAccount.list[i].projectManager) + "','"+ escape(lstAccount.list[i].spFirstname) + "','"+ escape(lstAccount.list[i].spLastName) +	"','"+ escape(lstAccount.list[i].spLPhno) + "','"+ escape(lstAccount.list[i].spLEmail) + "','"+ escape(lstAccount.list[i].region) + "','"+ escape(lstAccount.list[i].regionIdNew) + "','"+ escape(lstAccount.list[i].zone) + "','"+ escape(lstAccount.list[i].acmgrPhno) + "','"+ escape(lstAccount.list[i].acmgrEmail) + "','"+escape(lstAccount.list[i].m6ShortCode) + "','"+ escape(lstAccount.list[i].crmAccountId) + "','"+ escape(lstAccount.list[i].collectionMgr) + "','" + escape(lstAccount.list[i].osp) +"','"+ escape(lstAccount.list[i].circle) +  "','"+ escape(lstAccount.list[i].projectManagerID) +  "','"+ escape(lstAccount.list[i].category) +  "') />";
		//[001] End
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].crmAccountId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].m6ShortCode;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].accountName;
		CellContents = str;
		tblcol.innerHTML = CellContents;	
		
		
		//LAWKUSH START
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].osp;
		CellContents = str;
		tblcol.innerHTML = CellContents;	
		
		//LAWKUSH END
		
		var accountid=lstAccount.list[i].accountID;
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<a href='#' onclick='viewBCPDetailsPopup("+accountid+")'>View BCP Details</a>";
		CellContents = str;
		tblcol.innerHTML = CellContents;*/						
		}
		tableStr = tableStr + "</table>";
 	  	mytable.innerHTML = tableStr;

		var pagenumber=document.getElementById('selAccTxtPageNumber').value;
		var MaxPageNo=document.getElementById('selAccTxtMaxPageNo').value;
		if(pagenumber && MaxPageNo==1){
			document.getElementById('first').disabled=true;
	     	document.getElementById('prev').disabled=true;
			document.getElementById('last').disabled=true;
	     	document.getElementById('next').disabled=true;
		}
		if(pagenumber==1 && MaxPageNo!=1){
			document.getElementById('first').disabled=true;
			document.getElementById('prev').disabled=true;
			document.getElementById('last').disabled=false;
			document.getElementById('next').disabled=false;
		}
		if(pagenumber==MaxPageNo && pagenumber!=1){
	   		document.getElementById('last').disabled=true;
	  		document.getElementById('next').disabled=true;
	   		document.getElementById('first').disabled=false;
		    document.getElementById('prev').disabled=false;
		}
	 	if(pagenumber!=MaxPageNo && pagenumber!=1){
	    	document.getElementById('last').disabled=false;
	    	document.getElementById('next').disabled=false;
	    	document.getElementById('first').disabled=false;
	    	document.getElementById('prev').disabled=false;
	 	}
	}
	return false;	
}

//TODO Use jquery
function clearFields(){
	document.getElementById('txtAccountName').value="";
	document.getElementById('txtShortCode').value="";
	document.getElementById('txtAccountID').value="";
	document.getElementById('txtOsp').selectedIndex=0;
}

function isBlankFields(){
	var path='<%=request.getContextPath()%>';
	var accountName=document.getElementById('txtAccountName');
	var shortCode=document.getElementById('txtShortCode').value;
	var accountID=document.getElementById('txtAccountID');
	var ospvalue=document.getElementById('txtOsp').value;
	if(accountName.value=="" && shortCode=="" && accountID.value=="" && ospvalue=='0'){
		alert("Please enter atleast one search creteria");
		return false;
	}
	if(accountID.value=="0"){
		alert("Value O is not allowed");
		return false;
	}
	var searchFlag=1;
	if(accountID.value.length > 0){
		if(accountValidation(accountID,'Account No')==false){
			searchFlag=0;
			return false;
		}			  			  	   
	}		
	if( trim(accountName.value).length>0){
		if(ValidateTextField(accountName,path,'Account Name')==false){
			searchFlag=0;
			return false;
		}
	}
	if( trim(shortCode).length>0){
		if(ValidateTextField(document.getElementById('txtShortCode'),path,'Short Code')==false){
			 searchFlag=0;
			 return false;
		}
	}
	if(searchFlag==1){
			searchFromList('CRMACCOUNTNO','SELECTACCOUNT');
	}
}

function onPressEnterSearch(key_event){
    if (key_event.keyCode==13){
     	isBlankFields();
    }      
}

//end [003]
//rakshika : Code start
function setDefaultValue(){
	var source="<%=request.getParameter("source")%>";
	if(source=="bulkupload"){
		//do nothing from source bulupload on close window
	}else{	
	var callerWindowObj = document.searchForm;
	if (flag==0 && callerWindowObj.accountID.value<=0){
		callerWindowObj.accountID.value = "";
		callerWindowObj.accountName.value = "";
		//callerWindowObj.accphoneNo.value = "";
		callerWindowObj.lob.value = "";
		callerWindowObj.accountManager.value = "";
		//callerWindowObj.projectManager.value = unescape(PJTMgr);
		//callerWindowObj.spFirstname.value = "";
		//callerWindowObj.spLastName.value = "";
		//callerWindowObj.spLPhno.value = "";
		//callerWindowObj.spLEmail.value = "";
		callerWindowObj.AttributeVal_2.value = "";
		callerWindowObj.AttributeVal_3.value = "";
		callerWindowObj.region.value = "";
		callerWindowObj.zone.value = "";
		callerWindowObj.shortCode.value="";
		callerWindowObj.osp.value = "";
	}
	//fillProjectManagerCombo(accID);
	//window.close();
}
}
//Rakshika : Code ends

//start[006]
function viewBCPDetailsPopup(accountid){
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=goToViewBCPDetailsPage&accountId="+accountid;		
	window.showModalDialog(path,window,"status:false;dialogWidth:1020px;dialogHeight:530px");
}

function viewDispatchDetailsPopup(accountid){
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=goToViewDispatchDetailsPage&accountId="+accountid;		
	window.showModalDialog(path,window,"status:false;dialogWidth:1020px;dialogHeight:530px");
}

function setValue(){   //called this function on onload 

	var source="<%=request.getParameter("source")%>";
	if(source=="bulkupload"){
		//donothing on load
	}else{
	isFromNewOrder=1;
	var count=(<%=request.getParameter("counter")%>);
	//var callerWindowObj = dialogArguments;
	document.getElementById('txtAccountID').value=document.getElementById('crmAccNo').value;
	if(document.getElementById('txtAccountID').value !="" && document.getElementById('txtAccountID').value !="%"){
		isBlankFields();
	}else{
		//DrawTable('CRMACCOUNTNO','SELECTACCOUNT');
	}
	
	if(document.getElementById('txtAccountID').value!=''){
		return false;
		}
	}
}
//end[006]

//------------- START::Fetch CRM account on selection of account -----------------
function fetchAccountBCPDispatchCRMAccountPage(crmAccountNo){	
		$.ajax({
			    url: "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchCRMAccountBCPDispatch&accountno="+crmAccountNo,
	                    dataType: "text",
	                    type: "Get",
	                    contentType: "application/json; charset=utf-8",
	                    dataFilter: function(data) { return data; },
	                    success: handleGetAccountAP, 
						beforeSend: function(){
							$("#imgLoadingId").show();	
							$("#imgSearchId").attr('disabled', 'disabled');		
							$("#txtAccountID").attr('disabled', 'disabled');	
							$("#txtOsp").attr('disabled', 'disabled');		
							$("#txtShortCode").attr('disabled', 'disabled');	
							$("#txtAccountName").attr('disabled', 'disabled');		
							},
						complete: function(){
							$("#imgLoadingId").hide();	
							$("#imgSearchId").attr('disabled', '');		
							$("#txtAccountID").attr('disabled', '');
							$("#txtOsp").attr('disabled', '');	
							$("#txtShortCode").attr('disabled', '');	
							$("#txtAccountName").attr('disabled', '');			
							} ,
						async:true,
	                    error: function(XMLHttpRequest, textStatus, errorThrown) {
	                        alert(errorThrown);
	                    }	                    
	                });	                		                
}
function handleGetAccountAP(data){		
	if(data == "1")	
	{	
		searchAccountCRM();
        IS_FOUND_ACCOUNT_CRM=val;	          					
		return false;       
	}else{
		alert(data);
	}
}
function refreshAccountBCPDispatchCRMAccountPage(){	
	var crmAccountNo=document.getElementById('txtAccountID').value;
		$.ajax({
			    url: "<%=request.getContextPath()%>/NewOrderAction.do?method=refreshCRMAccountBCPDispatch&accountno="+crmAccountNo,
	                    dataType: "text",
	                    type: "Get",
	                    contentType: "application/json; charset=utf-8",
	                    dataFilter: function(data) { return data; },
	                    success: handleRefreshAccountAP, 
						beforeSend: function(){
							$("#imgLoadingId").show();	
							$("#imgSearchId").attr('disabled', 'disabled');		
							$("#txtAccountID").attr('disabled', 'disabled');	
							$("#txtOsp").attr('disabled', 'disabled');	
							$("#txtShortCode").attr('disabled', 'disabled');	
							$("#txtAccountName").attr('disabled', 'disabled');									
							},
						complete: function(){
							$("#imgLoadingId").hide();		
							$("#imgSearchId").attr('disabled', '');		
							$("#txtAccountID").attr('disabled', '');				
							$("#txtOsp").attr('disabled', '');
							$("#txtShortCode").attr('disabled', '');
							$("#txtAccountName").attr('disabled', '');
							} ,
						async:true,
	                    error: function(XMLHttpRequest, textStatus, errorThrown) {
	                        alert(errorThrown);
	                    }	                    
	                });	                		                
}

function refreshTransactionAccountPage(sourceType){
	var accountID = document.getElementById('txtAccountID');
	if(accountID.value.length > 0){
		if(accountValidation(accountID,'Account No')==false){				
			return false;
		}			  			  	   
	}else{
		alert('Please enter Account No!!');
		accountID.focus();
		return false;
	}		 
	if(sourceType=="ACCOUNT"){
		refreshAccountBCPDispatchCRMAccountPage();
	}else{
		//refreshBCPDispatchCRMAccountPage();
	}
}

function handleRefreshAccountAP(data){
	var val=Number(data);
	if(data=="1"){
		alert("Account synced successfully!!");
		searchAccountCRM();		
	}else{		
		alert(data);
	}	
}
function handleRefreshBCPDispatchAP(data){
	if(data=="1"){
		alert("BCP and Dispatch Address synced successfully!!");
	}else{
		alert(data);
	}	
}
//------------- END::Fetch CRM account on selection of account -----------------

function searchAccountCRM(){
	isFromNewOrder=0;
	var accountName=document.getElementById('txtAccountName').value;
	var shortCode=document.getElementById('txtShortCode').value;
	var accountID=document.getElementById('txtAccountID');
	var searchFlag=1;		
	if(accountID.value.length > 0){
		if(accountValidation(accountID,'Account No')==false){
			searchFlag=0;
			return false;
		}			  			  	   
	}								
	if(searchFlag==1){
		searchFromList('CRMACCOUNTNO','SELECTACCOUNT');
	}						
}
//-------------------------------------------------------------------------
</script>
</head>
<body onunload="javascript:setDefaultValue();">
<div class="head"> <span>Account List </span> </div>
<center>
<html:form action="/NewOrderAction" styleId="accountFormId" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('CRMACCOUNTNO','SELECTACCOUNT')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('CRMACCOUNTNO','SELECTACCOUNT')">Next</a></td>
		<td align="center">
			<input type="text" id="selAccTxtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="selAccTxtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('CRMACCOUNTNO','SELECTACCOUNT')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('CRMACCOUNTNO','SELECTACCOUNT')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Account No:</strong><input type="text" id="txtAccountID" name="accountID" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" >										
					<img border="0" src="<%=request.getContextPath()%>/images/loading.gif" alt="Loading..." width="20" height="21"  id="imgLoadingId" style="display:none" />
					</div>
					</td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Short Code:</strong><input type="text" id="txtShortCode" name="accountName" class="inputBorder1"  maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Account Name:</strong><input type="text" id="txtAccountName" name="accountName" class="inputBorder1"  maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
					
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>OSP:</strong>
					
					<select  name="osp" id="txtOsp" class="dropdownMargin" isRequired="0" style="width:150px" onChange="isBlankFields();" >
												<option value="0" title="Select OSP">Select OSP</option>
												<option value="1" title="Yes">Yes</option>
												<option value="2" title="No">No</option>
									</select>
					
					
					</div></td>
					
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15" id="imgSearchId"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
					<a href="#"><img border="0"
					src="${pageContext.request.contextPath}/gifs/refresh_icon2.gif"
					alt="Sync Account with CRM" width="20" height="21" onclick="refreshTransactionAccountPage('ACCOUNT');" /></a>										
					</td>
				</tr>
	</table>
	<div id = "accountTypeTable">
	<table width="100%" border="1"  id="tabaccountType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('CRMACCOUNTNO','SELECTACCOUNT')">Account No</a>
				</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('M6shortCode','SELECTACCOUNT')">Short Code</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('ACCOUNTNAME','SELECTACCOUNT')">Account Name</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('OSP','SELECTACCOUNT')">OSP</a>
				</td>
				<td width="20%" align="center">View BCP/Dispatch Details</td>
			</tr>								
	</table>
	</div>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" >&nbsp;</td>
			<td width="20%" colspan="3">&nbsp;</td>
		</tr>
	</table>	
</html:form>
</center>
</body>
<script type="text/javascript">
	setValue()
	//DrawAccountType()	
	
	// <!-- end [003] -->
</script>
</html>