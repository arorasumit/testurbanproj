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
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="com.ibm.ioes.forms.ChannelPartnerDto"%>
<html>
<head>
<title>Channel Partner</title>
<link href="gifs/style.css" tyisipe="text/css" rel="stylesheet">
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

<style type="text/css">
    .header{ margin:2px; padding:1px 0px 1px 1px; color:#ffffff;}
</style>

<!-- added by manisha start -->

<%
			HashMap var_userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession var_sessionObj = (HttpSession) var_userHashMap.get(session.getId());
			UserInfoDto var_objUserDto  = (UserInfoDto)var_sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>

<!-- added by manisha end -->


<script language="javascript" type="text/javascript">

//var gb_path='<%=request.getContextPath()%>';
var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
var sessionid ='<%=request.getSession().getId() %>';
var addList= [];
var listofFEwithIdforEdit= [];
var getEligibleCustSegmforFeId= [];
var custSegId = jsonrpc.processes.getCustSegbyRespId("<%=var_objUserDto.getRespId() %>");
var custSegNameList = jsonrpc.processes.getCustSegNamebyRespId("<%=var_objUserDto.getRespId() %>"); // new change end moment 
var respId="<%=var_objUserDto.getRespId() %>";

function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}

function convertCustSegListtoCommaSepName(){
	//custSegNameList=custSegNameList.substring(0, custSegNameList.length()-1);
	$('#channelPartnerAppend').append( custSegNameList + ' )');
	//return document.write(custSegNameList);
}

function showFEButton()
{
	 var row = document.getElementById('selectFEId');
     row.style.display = '';
}

function hideFEButton()
{
	 var row = document.getElementById('selectFEId');
     row.style.display = 'none';
 	 $('#autoSuggestTextFieldIdTD').hide();
 	 $('#autoSuggestTextFieldIdTDLabel').hide();
}

function showAddHeader()
{
	 var row = document.getElementById('addHeader');
     row.style.display = '';
     var editHeader = document.getElementById('editHeader');
     editHeader.style.display = 'none';
}

function showEditHeader()
{
	 var row = document.getElementById('addHeader');
     row.style.display = 'none';
     var editHeader = document.getElementById('editHeader');
     editHeader.style.display = '';
}

function getEligibleCutSegmforFeId()
{	
	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
    var getEligibleCustSegmforFeTemp = jsonrpc.processes.getEligibleCustSegmforFeId();	
    for (var i=0;i<getEligibleCustSegmforFeTemp.list.length;i++){
  		getEligibleCustSegmforFeId[getEligibleCustSegmforFeTemp.list[i]] = getEligibleCustSegmforFeTemp.list[i]
    }
}

function addNewChannelPartner(){
	//alert('test')
	if(globalEditListisNotEmpty(addList)){
		var addNewPartnerWithoutSaving = confirm(" All unsaved data will be lost. Click OK to continue");
		if (addNewPartnerWithoutSaving == true) {
		   addList = [];
		} else {
		   return false;
		} 
	}
	
	if(globalEditListisNotEmpty(listofFEwithIdforEdit)){
		var addNewPartnerWithoutSaving = confirm(" All unsaved data will be lost. Click OK to continue");
		if (addNewPartnerWithoutSaving == true) {
		    listofFEwithIdforEdit = [];
		} else {
		    return false;
		} 
	}
	
	var txtChannelPartnerName,txtChannelPartnerCode,txtChannelPartnerStatus,txtChannelPartnerId;
	document.getElementById('txtChannelPartnerName').value='';
	document.getElementById('txtChannelPartnerCode').value='';
	document.getElementById('txtChannelPartnerStatus').checked=true;
	document.getElementById('txtChannelPartnerId').value='';
	addList= [];
	listofFEwithIdforEdit= [];
	
	//document.getElementById('addList').value=[];
	showAddHeader();
	//document.getElementById('custSegment').disabled=false;
	//hideFEButton();
	//drawAllCustomerSegment();
	//document.getElementById('custSegment').value = 0;
}

function clickOnPopUptoDisplayFE(partnerId){
	/* try{ */
		
			popitup('DisplayFieldEngineer',partnerId);
			/* var path = gb_path+"/NewOrderAction.do?method=displayFieldEngineer&partnerId="+partnerId;		
			window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:500px");		
			return; */
		/* } */
	/* catch(e)
	{
		alert('error code in NewOrder.jsp function clickOnPopUptoChangeFE: '+ e.message);
    } */
}


/* if(url=="SelectCurrency"){
	var path = gb_path+"/NewOrderAction.do?method=fetchCurrency";		
	window.showModalDialog(path,window,"status:false;dialogWidth:750px;dialogHeight:450px");
	return;
} */

function popitup(url,partnerId) 
{
	try{
		if(url=="DisplayFieldEngineer")
		{
			var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=displayFieldEngineer&partnerId="+partnerId;		
			window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:600px");
		}
		if(url=="AddFieldEngineer")
		{
			var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=addFieldEngineer";		
			window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:600px");
			//alert(addList.length)
		}
		if(url=="ChangeFieldEngineer")
		{
			var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=changeFieldEngineer&partnerId="+partnerId;		
			window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:600px");
		}
	}catch(e)
	{
		alert(e.message);
		//alert('error code in channelPartnerMasteriB2B.jsp function clickOnPopUptoChangeFE: '+ e.message);
    }
}

function globalEditListisNotEmpty(obj) {
	for(var key in obj) {
		    if(typeof obj[key] !== "function") {
				return true;
    	 	}
		}
	return false;
}

function clearFields() {
	 $( "#autoSuggestTextFieldId" ).val( '' );
	 $( "#autoSuggestTextPartnerId" ).val( '');	
}

/* function clearFieldsPartner() {
	 $( "#autoSuggestTextPartnerId" ).val( '' );
	 $( "#autoSuggestTextPartnerIdHidden" ).val( '');	
} */

function search() {

	  searchFromList('PARTNER_NAME','CHANNELPARTNER');	
		
	  //DrawChannelPartnerListTable($( "#autoSuggestTextFieldId" ).val(),$( "#autoSuggestTextPartnerId" ).val());		  
	  /* /* if($( "#autoSuggestTextFieldId" ).val()=='' && $( "#autoSuggestTextFieldIdHidden" ).val()==''){
	  	alert('please input existing field engineer in search criteria');
	  	DrawChannelPartnerListTable('');
	  	return false;
	  } */
	  
	  /* if($( "#autoSuggestTextFieldId" ).val()!='' && $( "#autoSuggestTextFieldIdHidden" ).val()!=''){
			DrawChannelPartnerListTable($( "#autoSuggestTextFieldIdHidden" ).val(),'');		  
	  }else{
		  	if($( "#autoSuggestTextFieldId" ).val()!='' && $( "#autoSuggestTextFieldIdHidden" ).val()==''){
		  	    alert('please input existing field engineer in search criteria');
		  	}else{
		  	    DrawChannelPartnerListTable('','');
		  	}
	  }
	  
	  /* if($( "#autoSuggestTextFieldIdHidden" ).val()!=''){
		 DrawChannelPartnerListTable($( "#autoSuggestTextFieldIdHidden" ).val());		  
	  }else{
	  	 DrawChannelPartnerListTable('');	  
	  } */ /* */
}

/* function searchPartner() {
	  /* if($( "#autoSuggestTextPartnerId" ).val()!='' && $( "#autoSuggestTextPartnerIdHidden" ).val()!=''){
	  	
	  } */
		
	  /* if($( "#autoSuggestTextPartnerId" ).val()=='' && $( "#autoSuggestTextPartnerIdHidden" ).val()==''){
	  	alert('please input existing partner name in search criteria');
	  	DrawChannelPartnerListTable('','');
	  	return false;
	  } */
	  /* if($( "#autoSuggestTextPartnerId" ).val()!='' && $( "#autoSuggestTextPartnerIdHidden" ).val()!=''){
		DrawChannelPartnerListTable('',$( "#autoSuggestTextPartnerId" ).val());		  
	  }else{
	  	if($( "#autoSuggestTextPartnerId" ).val()!='' && $( "#autoSuggestTextPartnerIdHidden" ).val()==''){
		  	    alert('please input existing channel partner name in search criteria');
		  	    DrawChannelPartnerListTable('','');
		  	}else{
		  	    DrawChannelPartnerListTable('','');
		}
	  } */
	  
	 /*  if($( "#autoSuggestTextPartnerIdHidden" ).val()!=''){
		 DrawChannelPartnerListTable('',$( "#autoSuggestTextPartnerIdHidden" ).val());		  
	  }else{
	  	 DrawChannelPartnerListTable('','');	  
	  } */
/* }  */

function clickOnPopUptoChangeFE() {
	try{
		if(document.getElementById('txtChannelPartnerId').value==''){
			return popitup('AddFieldEngineer','');
			
		}else{
				if(globalEditListisNotEmpty(listofFEwithIdforEdit)){
					//alert(sdajk')
					return popitup('ChangeFieldEngineer',document.getElementById('txtChannelPartnerId').value);
				}else{
					//getting list of FE on basis of partner id
					var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
					//alert('tempListofFEwithIdforEdit'+tempListofFEwithIdforEdit);	
					var tempListofFEwithIdforEdit = jsonrpc.processes.getFEByPartnerId(document.getElementById('txtChannelPartnerId').value);	
					//alert('tempListofFEwithIdforEdit'+tempListofFEwithIdforEdit);	
						if(tempListofFEwithIdforEdit.list.length =='0'){
							
						}else{
							 for (i = 0; i < tempListofFEwithIdforEdit.list.length; i++)
					    		{	
						    		//alert('chann'+tempListofFEwithIdforEdit.list[i].fieldEngineer)
				   					    obTextBoxFEEdit={
					   						seId:tempListofFEwithIdforEdit.list[i].se_Id,
					   						idTextBox:i,
					   						valTextBox:tempListofFEwithIdforEdit.list[i].fieldEngineer,
					   						counter:i,
					   						status:tempListofFEwithIdforEdit.list[i].field_Engineer_Is_Active,
					   						hiddenValTextBox:tempListofFEwithIdforEdit.list[i].fieldEngineer
					       	    		}	
				   					tempListofFEwithIdforEdit[tempListofFEwithIdforEdit.list[i].fieldEngineer]=obTextBoxFEEdit;
					    		}
					    	listofFEwithIdforEdit = tempListofFEwithIdforEdit;
						}
						return popitup('ChangeFieldEngineer',document.getElementById('txtChannelPartnerId').value);
				}
		}
	}catch(e)
	{
		alert('error code 2: '+ e.message);
    }
}

<%-- function drawAllCustomerSegment(){
	   var myForm = $('#searchForm')
	   var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	   var custSeg = jsonrpc.processes.getAllCustomerSegmentList();
	   var combomain = document.getElementById('custSegment'); 
	   var iCountRows = combomain.length;
	   
	   for (var i=combomain.options.length-1;i>=1;i--)
	   {
		   combomain.remove(i);
		   //combomain.options[i] = null;
	   }
	   $('#custSegment').children('option:not(:first)').remove();
	   //$("#custSegment").empty();
	   //document.getElementById("custSegment").innerHTML = "";
	  // combomain.add(optionmain); 
	  //$('#custSegment option').remove();
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
	<%--  var custSegm='<%=request.getAttribute("CustSegment")%>';	
	 if(custSegm !='null' && custSegm !=''){
		combomain.value=custSegm;
     }			 
} --%>

function DrawChannelPartnerListTable()
{
	var lstChannelPartnerDetails;	
	   try
	   {
		   var str;		   	  
		   var mytable = document.getElementById('tabViewChannelPartner');	
		   var iCountRows = mytable.rows.length;
		   for (i = 1; i <  iCountRows; i++)
		   {
		       mytable.deleteRow(1);
		   }   	
		   
	   		document.getElementById('txtPageNumber').value= pageNumber;
<%-- 			pageRecords=<%=objUserDto.getPageSizeLSICancellation()%>;
 --%>			
 				document.getElementById('id_paging_goToPage').value= pageNumber; 
 				//pageRecords=10;
 				sync();

		var jsData_Paging =			
	    {
		    startIndex:startRecordId,
			endIndex:endRecordId,
     		sortBycolumn:sortByColumn,
			pageRecords:pageRecords,
			sortByOrder:sortByOrder
		};
		
		  /*  document.getElementById('txtPageNumber').value= pageNumber;
		   document.getElementById('id_paging_goToPage').value= pageNumber; 
		   sync();	
		      	   	   
		   	var pagingDto=
				{
					startIndex:startRecordId,
					endIndex:endRecordId,
					sortBycolumn:sortByColumn,
					sortByOrder:sortByOrder
				};	   
		    var jsData =
				{
					pagingDto:pagingDto
				}; */
			var pagingRequired=1;			
			var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");						
			lstChannelPartnerDetails = jsonrpc.processes.fetchChannelPartner("<%=var_objUserDto.getRespId() %>",startRecordId,endRecordId,pagingRequired,pageRecords,$( "#autoSuggestTextFieldId" ).val(),$( "#autoSuggestTextPartnerId" ).val());							
			
			var v_iTotalLength=lstChannelPartnerDetails.list.length;	
			if(v_iTotalLength !=0)
			{
				var v_iNoOfPages = lstChannelPartnerDetails.list[0].maxPageNo;
				iNoOfPages=v_iNoOfPages;
			}
			 else
			{     
			    v_iNoOfPages=1;
			}
			
	        document.getElementById('txtMaxPageNo').value=v_iNoOfPages;	
			if(lstChannelPartnerDetails==null)
			{
				var tblRow=document.getElementById('tabViewChannelPartner').insertRow();
			    tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				tblcol.colSpan = 12;
				str='NO RECORD FOUND';
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
		
			iTotalLength=lstChannelPartnerDetails.list.length;	
			if(iTotalLength !=0)
			{
				iNoOfPages = lstChannelPartnerDetails.list[0].maxPageNo;
			}		
	        else
			{     
		        iNoOfPages=1;
			}
		
			document.getElementById('txtMaxPageNo').value=iNoOfPages;				
			var colors=new Array("normal","lightBg");
			var i;
			for (i = 0 ; i <iTotalLength; i++) 
			{		 		
				
		 		var colorValue=parseInt(i)%2;	 		  
				var tblRow=document.getElementById('tabViewChannelPartner').insertRow();
				tblRow.className=colors[colorValue];							
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=(i+1);
				str=str+"<input type='hidden' name='hdnChannelPartnerId' value='"+lstChannelPartnerDetails.list[i].channelPartnerCtgry+"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				/* str=str+"<input type="button" name="editID" id="editID" onclick="edit()">"; */
				str="<input type=button id=editID width=5 value=Edit  onClick=edit('"+escape(parseFloat(lstChannelPartnerDetails.list[i].channelPartnerCtgry))+"','"+escape(decodeURI(lstChannelPartnerDetails.list[i].channelPartnerCtgryName))+"','"+escape(decodeURI(lstChannelPartnerDetails.list[i].channelPartnerCode))+"','"+escape(decodeURI(lstChannelPartnerDetails.list[i].status))+"'); />";
				//str="<input name=delSelect"+iCountRows+" id=delSelect"+iCountRows+" type=checkbox onclick=delSelectCheck("+iCountRows+"); />";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str=lstChannelPartnerDetails.list[i].channelPartnerCtgryName;
				CellContents = str;
				tblcol.innerHTML = CellContents;

				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstChannelPartnerDetails.list[i].channelPartnerCode;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				/* tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				if(lstChannelPartnerDetails.list[i].custSegment==null || lstChannelPartnerDetails.list[i].custSegment==''){
					str='';
				}else{
					str=lstChannelPartnerDetails.list[i].custSegment;
				}
				CellContents = str;
				tblcol.innerHTML = CellContents; */

				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				if(isInArray(custSegId, getEligibleCustSegmforFeId)!=false){
					str="<div style=margin-left:35px;margin-top:5px; class=searchBg1  id=divSelectFEtoDisplay><a href=# id=divSelectFEtoDisplay title=Click to look attached FSE onClick=clickOnPopUptoDisplayFE('" +lstChannelPartnerDetails.list[i].channelPartnerCtgry+ "')>...</a></div>";	
				}else{
					str="&nbsp"
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(lstChannelPartnerDetails.list[i].status==1){
					str="Active";
				}else if(lstChannelPartnerDetails.list[i].status==0){
					str="Inactive";
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str=lstChannelPartnerDetails.list[i].currencyID;
				CellContents = str;
				tblcol.innerHTML = CellContents;

		}					
						
		var pagenumber=document.getElementById('txtPageNumber').value;
		var MaxPageNo=document.getElementById('txtMaxPageNo').value;
		if((pagenumber == 1) && (MaxPageNo==1))
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
			var tblRow=document.getElementById('tabViewChannelPartner').insertRow();
		    tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 12;
			str='NO RECORD FOUND';
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}	
		return false;
	}
	catch(e)
	{
		alert(e.message);		
	}
}
function edit(partnerId,partnerName,partnerCode,status/* ,custSegmentId */)
{
	if(globalEditListisNotEmpty(addList)){
		var addNewPartnerWithoutSaving = confirm(" All unsaved data will be lost .Click OK to continue");
		if (addNewPartnerWithoutSaving == true) {
		    addList = [];
		} else {
		    return false;
		}
	}
	if(globalEditListisNotEmpty(listofFEwithIdforEdit)){
		var addNewPartnerWithoutSaving = confirm(" All unsaved data will be lost .Click OK to continue");
		if (addNewPartnerWithoutSaving == true) {
		    listofFEwithIdforEdit = [];
		} else {
		    return false;
		}
	}
	
	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
					//alert('tempListofFEwithIdforEdit'+tempListofFEwithIdforEdit);	
					var tempListofFEwithIdforEdit = jsonrpc.processes.getFEByPartnerId(partnerId);	
					var tempListofFEwithIdforEditt=[];
					//alert('tempListofFEwithIdforEdit'+tempListofFEwithIdforEdit);	
						if(tempListofFEwithIdforEdit.list.length =='0'){
							
						}else{
							 for (i = 0; i < tempListofFEwithIdforEdit.list.length; i++)
					    		{	
				   					    obTextBoxFEEdit={
					   						seId:tempListofFEwithIdforEdit.list[i].se_Id,
					   						idTextBox:i,
					   						valTextBox:tempListofFEwithIdforEdit.list[i].fieldEngineer,
					   						counter:i,
					   						status:tempListofFEwithIdforEdit.list[i].field_Engineer_Is_Active,
					   						fieldEngineerHidden:tempListofFEwithIdforEdit.list[i].fieldEngineer
					       	    		}	
				   					tempListofFEwithIdforEditt[tempListofFEwithIdforEdit.list[i].se_Id]=obTextBoxFEEdit;
					    		}
						}
						listofFEwithIdforEdit = tempListofFEwithIdforEditt;
	//alert('edit')
	var txtChannelPartnerName,txtChannelPartnerCode,txtChannelPartnerStatus,txtChannelPartnerId;
	document.getElementById('txtChannelPartnerId').value=partnerId;
	document.getElementById('txtChannelPartnerName').value=decodeURI(partnerName);
	document.getElementById('txtChannelPartnerCode').value=decodeURI(partnerCode);
	document.getElementById('txtChannelPartnerCodeHidden').value=decodeURI(partnerCode);
	
	if(status=='1')
		document.getElementById('txtChannelPartnerStatus').checked=true;
	else
		document.getElementById('txtChannelPartnerStatus').checked=false;
	
	//document.getElementById('custSegment').value=decodeURI(custSegmentId);
	//disable DD
	//document.getElementById('custSegment').disabled=true;
	
	//showedit
	showEditHeader();
	
	//invisible FE button
	//hideFEButton();
	
	/* if(isInArray(custSegId, getEligibleCustSegmforFeId)!=false){
		showFEButton();
	}else{
		hideFEButton();
	}  */
}

function isInArray(value, array) {
 // alert(value)
  for (var i=0;i<value.list.length;i++){
  	for(var key in array) {
      if(array.hasOwnProperty(value.list[i])) {
    	  //return true;
      }else{
		return false;
      }
    }
  }
}


// we can disable FE button either user selects Channel or CB so we have option either we match cust id or cust text
function onLoadShowFEButtononeRespId() {
	   //alert('onchange drop down :'+currentSelection+':' )
	   var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	   var currentSelection = custSegId;
	   //var currentSelection = cusSeg ;
       if(isInArray(currentSelection, getEligibleCustSegmforFeId)!=false){// we have to confirm whther CB and channel customer segment has same ids in production db or not
      		//alert('onchange drop down in cb or channel :'+currentSelection ) // need toi make entry in appconstants
       		//$("#divSelectFE").blur();
       		
       		//document.getElementById("selectFEId").visible  = true;
       		/* $('#divSelectFE').prop("disabled",true); */   // prop is not working y ?
       		
      		showFEButton();
       }else{
       		hideFEButton();
       }
       convertCustSegListtoCommaSepName();
}

function loadFieldEngineeronSource(/* partnerId,partnerName , partnerCode */)
  {
	    var availableTags = [];
	    var objFeildEng = {};
	    /* selectedChannelPartner.partnerId = partnerId;
	    selectedChannelPartner.partnerCode = partnerCode;
	    selectedChannelPartner.partnerName = partnerName; */
	 	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	    var lstFeDetails = jsonrpc.processes.getAllFEList(respId);
	 	 
	     for(j=0;j<lstFeDetails.list.length;j++)
  		{        	
	    	objFeildEng = {}; 
  			var combo = document.getElementById('autoSuggestTextFieldId');	    			
  			objFeildEng.label = lstFeDetails.list[j].fieldEngineer;
  			objFeildEng.value = lstFeDetails.list[j].se_Id;
  			availableTags.push(objFeildEng);
  	    }	
	    	     
	     $( "#autoSuggestTextFieldId" ).autocomplete({
	         minLength: 0,
	         source: availableTags,
	         focus: function( event, ui ) {
	           $( "#autoSuggestTextFieldId"  ).val( ui.item.label );
	           return false;
	         },
	         select: function( event, ui ) 
	         {
	           $( "#autoSuggestTextFieldId" ).val( ui.item.label );
	           $( "#autoSuggestTextFieldIdHidden" ).val( ui.item.value );	           
	           return false;
	         },
	         change: function(event, ui) {
	        	 if (!ui.item) {
	                  /* $(this).val('');
	                  $( "#autoSuggestTextFieldIdHidden" ).val( '' );
	                   /* $( "#autoSuggestTextFieldId" ).val( '' );
	          		   $( "#autoSuggestTextFieldIdHidden" ).val( '' );	  */
	          		  // alert('please input existing field engineer in search criteria')
	          		  //  DrawChannelPartnerListTable('','');
	                  //return false; */
	                }
	            }
	       });
  }
  
  function loadFieldPartnerNameonSource(/* partnerId,partnerName , partnerCode */)
  {
	    var availableTags = [];
	    var objPartnerName = {};
	    /* selectedChannelPartner.partnerId = partnerId;
	    selectedChannelPartner.partnerCode = partnerCode;
	    selectedChannelPartner.partnerName = partnerName; */
	 	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	    var lstPartnerDetails = jsonrpc.processes.getAllPartnerNameList(respId);
	 	 
	     for(j=0;j<lstPartnerDetails.list.length;j++)
  		{        	
	    	objPartnerName = {}; 
  			var combo = document.getElementById('autoSuggestTextPartnerId');	    			
  			objPartnerName.label = lstPartnerDetails.list[j].channelPartnerName;
  			objPartnerName.value = lstPartnerDetails.list[j].channelPartnerName;
  			availableTags.push(objPartnerName);
  	    }	
	    	     
	     $( "#autoSuggestTextPartnerId" ).autocomplete({
	         minLength: 0,
	         source: availableTags,
	         focus: function( event, ui ) {
	           $( "#autoSuggestTextPartnerId"  ).val( ui.item.label );
	           return false;
	         },
	         select: function( event, ui ) 
	         {
	           $( "#autoSuggestTextPartnerId" ).val( ui.item.label );
	           $( "#autoSuggestTextPartnerIdHidden" ).val( ui.item.value );	           
	           return false;
	         },
	         change: function(event, ui) {
	        	 if (!ui.item) {
	                  /* $(this).val('');
	                  $( "#autoSuggestTextPartnerIdHidden" ).val( '' );
	                   /* $( "#autoSuggestTextFieldId" ).val( '' );
	          		   $( "#autoSuggestTextFieldIdHidden" ).val( '' );	  */
	          		   //	alert('please input existing partner name in search criteria')
	          		    //DrawChannelPartnerListTable('','');
	                //  return false; */
	                }
	            }
	       });
  }
  
function saveChannelPartner(){
	var empId = "<%=var_objUserDto.getEmployeeId() %>";
	var arraylistAddFE=[];
	var arraylistEditFE=[];
	var txtChannelPartnerName,txtChannelPartnerCode,txtChannelPartnerStatus,txtChannelPartnerId;
	txtChannelPartnerName=document.getElementById('txtChannelPartnerName');;
	txtChannelPartnerCode=document.getElementById('txtChannelPartnerCode');
	txtChannelPartnerCodeHidden=document.getElementById('txtChannelPartnerCodeHidden');
	txtChannelPartnerStatus=document.getElementById('txtChannelPartnerStatus');
	txtChannelPartnerId=document.getElementById('txtChannelPartnerId');	
	
	    if($.trim($("#txtChannelPartnerName").val())==''){
			alert("Channel partner name cannot be left blank");
			$('#txtChannelPartnerName').focus()
			return false;
		}
		if($.trim($("#txtChannelPartnerCode").val())==''){
			alert("Channel partner code cannot be left blank");
			$('#txtChannelPartnerCode').focus()
			return false;
	    }  
	if(txtChannelPartnerStatus.checked== false){
		txtChannelPartnerStatus=0; // if checkbox is selected // have assign 'on' text bcoz i was already using 'on' text through out the flow in java also 
	}else{
		txtChannelPartnerStatus=1;
	}
	
	/*  if((txtChannelPartnerId.value==""||txtChannelPartnerId.value==null) && document.getElementById('custSegmentId').value==0  ){
		alert('Please select customer segment')
		return false;
	} */ 
	if((!globalEditListisNotEmpty(listofFEwithIdforEdit) && !globalEditListisNotEmpty(addList)) &&  (isInArray(custSegId, getEligibleCustSegmforFeId)!=false)){
		alert('FSE is mandatory for this customer segment')
		return false;
	} 
	 if(txtChannelPartnerName.value.length > 200 || txtChannelPartnerCode.value.length > 200){
		alert('Length of channel partner name or code should be less than 200 characters');	
		return false;
	} 
	var jsData;
	if(txtChannelPartnerId.value==""||txtChannelPartnerId.value==null){//add
		var i=0;
		for(var key in addList) {
		    if (typeof addList[key] !== "function") {
		    	//arraylistAddFE.push(addList[key])
				arraylistAddFE[i]=addList[key].valTextBox;
			    i=i+1;
    	 	}
		}
		
		var addListGlobal={"javaClass": "java.util.ArrayList",
							"list": arraylistAddFE
						};
		 jsData =
				{		
						"javaClass": "com.ibm.ioes.forms.ChannelPartnerDto",
						channelPartnerName:txtChannelPartnerName.value,//partnername
						channelpartnerCode:$.trim($("#txtChannelPartnerCode").val()),
						channelPartnerId:txtChannelPartnerId.value,
						partner_Is_Active:txtChannelPartnerStatus,
						cust_Segment_Id:custSegId,
						addList:addListGlobal,
						empId:empId,
						respId:respId
				};
	}else{//edit
				/* var txtChannelPartnerCodeValue;
				if(txtChannelPartnerCodeHidden.value==txtChannelPartnerCode.value){
					txtChannelPartnerCodeValue='';
				}else{
					txtChannelPartnerCodeValue=txtChannelPartnerCode.value;
				} */
				var i=0;
				var jsdataTemp;
				var hiddenFe;
				
				for(var key in listofFEwithIdforEdit) {
				    if (typeof listofFEwithIdforEdit[key] !== "function") {
						//arraylistEditFE[i]=listofFEwithIdforEdit[key];
				    	//i=i+1;
				    	 if(listofFEwithIdforEdit[key].hiddenValTextBox==undefined){
				    	 	hiddenFe=listofFEwithIdforEdit[key].valTextBox
				    	 }else{
				    	 	hiddenFe=listofFEwithIdforEdit[key].hiddenValTextBox
				    	 }
				    	 jsdataTemp = 
								{	"javaClass": "com.ibm.ioes.forms.FieldEnginnerDto",
									fieldEngineer:listofFEwithIdforEdit[key].valTextBox,
									se_Id:listofFEwithIdforEdit[key].seId,
									field_Engineer_Is_Active:listofFEwithIdforEdit[key].status,
									fieldEngineerHidden:hiddenFe					
								};
						 arraylistEditFE.push(jsdataTemp);		
			   	 	}
				}
				
				/* var jsdataTemp = 
				{
					valTextBox:$(item).val(),
					seId:$(item).attr('seId'),
					status:status					
				}; */
				
				var editListGlobal={"javaClass": "java.util.ArrayList",
							"list": arraylistEditFE
				};		
				
				jsData =
				{
						channelPartnerName:txtChannelPartnerName.value,//partnername
						channelpartnerCode:$.trim($("#txtChannelPartnerCode").val()),
						channelpartnerCodeHidden:$.trim($("#txtChannelPartnerCodeHidden").val()),
						partner_Is_Active:txtChannelPartnerStatus,
						channelPartnerId:txtChannelPartnerId.value,
						editList:editListGlobal,
						empId:empId,
						cust_Segment_Id:custSegId,
						respId:respId
				};		
	}
				
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var status = jsonrpc.processes.saveChannelPartner(jsData);
	if(status=="ChannelNameMandatory"){
		alert("Channel partner name cannot be left blank");
		return false;
	}
	if(status=="ChannelNameSpecialCharacter"){
		alert(">,<,~ following characters are not allowed in channel partner name field");
		return false;
	}
	if(status=="ChannelNameMaxLength"){
		alert("Length of channel partner name should be lesser than 200 character");
		return false;
	}
	if(status=="ChannelCodeMandatory"){
		alert("Channel partner code cannot be left blank");
		return false;
	}
	if(status=="ChannelCodeSpecialCharacter"){
		alert(">,<,~ following characters are not allowed in channel partner code");
		return false;
	}
	if(status=="ChannelCodeMaxLength"){
		alert("Length of channel partner code should be lesser than 200 character");
		return false;
	}
	if(status=="channelPartnerCodeFailure"){
		alert("Channel partner code should be unique");	
		return false;
	}
	if(status=="FEFailure"){
		alert("FSE should be unique");	
		return false;
	}
	if(status=="success"){
		alert("Channel partner inserted/updated successfully");
		//clear();		
		DrawTable('PARTNER_NAME','CHANNELPARTNER');
		loadFieldEngineeronSource();
		loadFieldPartnerNameonSource();
		addList= [];
		listofFEwithIdforEdit= [];
		addNewChannelPartner();
		//addNewChannelPartner();
	}
	if(status=="otherFailure"){
		alert("Some error occured ");
		//clear();		
		//DrawTable('PARTNER_NAME','CHANNELPARTNER');		
	}
}




</script>
</head>
<body onLoad="onLoadShowFEButtononeRespId();showAddHeader();loadFieldEngineeronSource();loadFieldPartnerNameonSource();">
<td align="center"><div align="center" class="head"> <span id=channelPartnerAppend>Channel Partner Master ( </span></div></td>

<html:form action="/NewOrderAction"  enctype="text/plain" styleId="searchForm" onsubmit="return false;" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<table width="100%"  border="0"  cellpadding="0" cellspacing="0">	
		<tr>	
			<td width="5%" align="right" vAlign="top">
				<img src="gifs/top/home.gif" title="Home" onClick="goToHome();"></img>
			</td>			
		</tr>
</table>
<table width="45%" border="1"  id="tabNewStandardReason" align="center" cellpadding="2" cellspacing="2" class="tab2" >		
			<tr id='addHeader' class="head">
				<th colspan="2">Add Channel Partner</th>
			</tr>	
			<tr id='editHeader' class="head">
				<th colspan="2">Edit Channel Partner</th>
			</tr>				
			<tr>
				<td width="40%">
					Channel Partner Name
				</td>
				<td>	
					<input type="text" id="txtChannelPartnerName" name="txtChannelPartnerName" onblur="if( trim(this.value).length>0){return ValidateTextField(this,'<%=request.getContextPath()%>','Channel partner name ')};" class="inputBorder1" maxlength="200" style="width: 200px;">
				</td>
			</tr>
			
			<tr>
				<td width="40%">
					Channel Partner Code
				</td>
				<td>
					<input type="hidden" id="txtChannelPartnerCodeHidden" name="txtChannelPartnerCodeHidden" class="inputBorder1" maxlength="255" style="width:30%">
					<input type="text" id="txtChannelPartnerCode" name="txtChannelPartnerCode" onblur="if( trim(this.value).length>0){return ValidateTextField(this,'<%=request.getContextPath()%>','Channel partner code')};" class="inputBorder1" maxlength="200" style="width: 200px;">
					<input type="hidden" id="txtChannelPartnerId" name="txtChannelPartnerId" value="" class="inputBorder1" maxlength="255" style="width:30%">
					<input type="hidden" id="addList" name="addList" value="" class="inputBorder1" style="width:30%">
				</td>
			</tr>
			
			<tr>
				<td width="40%">
					Is Active
				</td>
				<td >
					<input type='checkbox' style="width:8%" name='txtChannelPartnerStatus' id='txtChannelPartnerStatus' checked >
					</input>
				</td>
			</tr>
			<tr>
				<!-- <td width="40%">
					Select Customer Segment
				</td> -->
				<%-- <td>     
					<html:select property="custSegment" onchange="onChangingDropDown(this.value)" style="border:1px #8D8D8D solid;width:140px;background:#FFFF99;margin-left:10px;font-family:Verdana, Arial, Helvetica, sans-serif;"   styleId="custSegmentId" >
					<html:option  value="0">Select Cust Segment</html:option>
					</html:select>
				</td> --%>
				
				<!-- <td colspan="2"  align="center">
					<input type="hidden" name="btnAddChannelPartner" value="Add New Channel Partner" width="5" onClick="addChannelPartner()">
				</td> -->
			</tr>	
			<tr id='selectFEId' >
				<td width="40%">
					Select FSE 
				</td>	
				<td>
					<div style="margin-left:10px;"   class="searchBg1"  onClick="clickOnPopUptoChangeFE()" id="divSelectFE"><a href="#" title="Select FE"  >...</a></div>
				</td>
			</tr>				
	</table>
	<table width="16%"  align="center" cellpadding="1" cellspacing="1"  valign="center"  class="header">
			<tr width="80%" >				
				<td colspan="1" align="left">
					<input type="button" name="addChannelPartner" value="Clear" onClick="addNewChannelPartner()">
					<input type="button" name="saveButton" value="Save Partner" onClick="saveChannelPartner()">
				</td>	
			</tr>				
	</table>	
<br/>

<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr class="head">
		<th colspan="4">Channel Partner Details</th>
	</tr>	
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
		<tr>
			<td align="center"><a href="#" id= "first" onclick="FirstPage('PARTNER_NAME','CHANNELPARTNER')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('PARTNER_NAME','CHANNELPARTNER')">Next</a></td>
			<td align="center"  class="last">Go To Page :
				<input type="text" class="inputNew" name="goToPageNumber" size="4" 
				maxlength="10" value="" id="id_paging_goToPage">
				<a href="#" onclick="goToPageSubmit('PARTNER_NAME','CHANNELPARTNER')">GO </a>
			</td>
			<td align="center">
				<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
				<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
			</td>
			<td align="center"><a href="#" id="prev" onclick="PrevPage('PARTNER_NAME','CHANNELPARTNER')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('PARTNER_NAME','CHANNELPARTNER')">Last</a>
			</td>
		</tr>
	</table>	
	<!-- <tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('PARTNER_NAME','CHANNELPARTNER')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('PARTNER_NAME','CHANNELPARTNER')">Next</a></td>
		<td align="center"  class="last">Go To Page :
			<input type="text" class="inputNew" name="goToPageNumber" size="4" 
				maxlength="10" value="" id="id_paging_goToPage">
				<a href="#" onclick="goToPageSubmit('PARTNER_NAME','CHANNELPARTNER')">GO </a>
		</td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('PARTNER_NAME','CHANNELPARTNER')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('PARTNER_NAME','CHANNELPARTNER')">Last</a></td>
	</tr> -->
	</table>	   		   
	
	<table width="80%"   align="center" cellpadding="0" cellspacing="0" class="tab2" >	
		
	<tr >
			<td align='center' width="222px" >
			<label>
			 <strong>
			 Search By Partner Name
			 <strong>
			</label>
			</td>
			<td >
				<input type="text"  maxlength="200" name="autoSuggestTextPartnerName" id="autoSuggestTextPartnerId">
				<input type="hidden" name="autoSuggestTextPartnerNameHidden" id="autoSuggestTextPartnerIdHidden">
			</td>
			<%-- <td  >
				<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFieldsPartner();"/>&nbsp;
				<!-- <a href="#"><img onclick="searchPartner();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp; -->
			</td> --%>
			
			<td id=autoSuggestTextFieldIdTDLabel align='center' width="235px" >
			<label>
			 <strong>
			 Search By FSE
			 <strong>
			</label>
			</td>
			<td id=autoSuggestTextFieldIdTD>
				<input type="text"  name="autoSuggestTextFieldName"  width="24" maxlength="100" id="autoSuggestTextFieldId">
				<input type="hidden" name="autoSuggestTextFieldNameHidden" id="autoSuggestTextFieldIdHidden">
			</td>
			<td  >
				<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
				<a href="#"><img onclick="search();" id="searchButtonId" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
			</td>
	</tr>
	</table>		
	
	<table width="100%" border="1"  id="tabViewChannelPartner" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
			
			<tr class="grayBg">
				<td width="5%%" align="center">
					S.No
				</td>
				<td width="5%" align="center">
					Edit
				</td>
				<td width="18%" align="center">
					Channel Partner Name
				</td>
				<td width="7%" align="center">
					Channel Partner Code
				</td>
				<!-- <td width="15%" align="center">
					Customer Segment
				</td> -->
				<td width="5%" align="center">
					FSE
				</td>
				<td width="7%" align="center">
					Status
				</td>	
				<td width="7%" align="center">
					Last Updated By
				</td>												
			</tr>											
	</table>
</html:form>
</body>
<script type="text/javascript">
	getEligibleCutSegmforFeId();
	DrawTable('PARTNER_NAME','CHANNELPARTNER');
	//drawAllCustomerSegment()
	
	$("#autoSuggestTextPartnerId").keyup(function(event){
    if(event.keyCode == 13){
        $("#searchButtonId").click();
    }
	});
	
	$("#autoSuggestTextFieldId").keyup(function(event){
    if(event.keyCode == 13){
        $("#searchButtonId").click();
    }
	});
	
	///* $(function () {
     //   $("#custSegment").change(function () {
     //      alert('onchange drop down :'+this.value)
     //   if(this.value!=Channel || this.value!=CB){
     //   	$("#divSelectFE").blur();
     ////   	$('#divSelectFE').prop("disabled",true);
      //  }
     //   });
    //});

</script>
</html>