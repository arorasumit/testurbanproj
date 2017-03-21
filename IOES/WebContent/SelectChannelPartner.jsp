
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Channel Partner</title>
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
var callerWindowObj = dialogArguments;	
var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
var custSegNameList = jsonrpc.processes.getCustLOBNamebyRespId(callerWindowObj.respId); // new change end moment 
var selectedChannelPartner = {};
var custSegmentList=[];
var feildEngineerListForPartner = [];
function getInfoAndUpdate() 
{
	var callerWindowObj = dialogArguments;	
		
	    if($('input[type=radio]:checked').length == 0)
	    {
	        alert("Please select Channel Partner");         
	      //  document.getElementById("#chkId"+partnerId).focus();         
	        return false;     
	    }
	    else
	    	{
	    	var cpFieldEng = document.getElementById("fieldengineerId"+selectedChannelPartner.partnerId).value;
			var cpFieldEngID = document.getElementById("fieldEngId"+selectedChannelPartner.partnerId).value;	
		
			var isDisabled = $('#fieldengineerId'+selectedChannelPartner.partnerId).is(":disabled")
				
		//	 if(isDisabled==false && (cpFieldEngID=="" || $.inArray($('#fieldengineerId'+selectedChannelPartner.partnerId).val(),feildEngineerListForPartner) <0))
			 if(isDisabled==false && (cpFieldEngID=="" || $.inArray(cpFieldEng.toUpperCase(),feildEngineerListForPartner) <0 )) 
			{
					alert('Please Select FSE');
					return false;
			}
			callerWindowObj.document.getElementById('partnerName').value = unescape(selectedChannelPartner.partnerName);
			callerWindowObj.document.getElementById('partnerCode').value = unescape(selectedChannelPartner.partnerCode);
			callerWindowObj.document.getElementById('partnerID').value = unescape(selectedChannelPartner.partnerId);
			callerWindowObj.document.getElementById('fieldEngineer').value = unescape(cpFieldEng);
			callerWindowObj.document.getElementById('fieldEngineerId').value = unescape(cpFieldEngID);
			window.close();
	    	
	    	}
}


function checkCustomerSegment(custSegId,custSegmentList)
{
	
    var count = custSegmentList.length;
    for(var i=0;i<count;i++)
    {
        if(custSegmentList[i]==custSegId){
        	
        	return true;
        	}
    }
    return false;
}

 function convertCustSegListtoCommaSepName(){
		//custSegNameList=custSegNameList.substring(0, custSegNameList.length()-1);
		$('#channelPartnerTaggingAppend').append( custSegNameList + ' )');
		//return document.write(custSegNameList);
 }
   
  function PopulateFieldEngineer(custSegId,partnerId,partnerName,partnerCode)
  {
	 // document.getElementById('fieldengineerId'+partnerId).disabled = true;
	 	
	 var status=checkCustomerSegment(custSegId,custSegmentList);
	 	
	 	var availableTags = [];
	    var objFeildEng = {};
	    selectedChannelPartner.custSegId = custSegId;
	    selectedChannelPartner.partnerId = partnerId;
	    selectedChannelPartner.partnerCode = partnerCode;
	    selectedChannelPartner.partnerName = partnerName;


	    
	   if(status==true)
	    {
	    
	    var lstFeDetails = jsonrpc.processes.getFieldEngineerList(partnerId);
	 	 
	    for(j=0;j<lstFeDetails.list.length;j++)
  		{        	
	    	objFeildEng = {}; 
  			var combo = document.getElementById('fieldengineerId'+partnerId);	    			
  			objFeildEng.label = lstFeDetails.list[j].fieldEngineer;
  			objFeildEng.value = lstFeDetails.list[j].fieldEngineerId;
  			availableTags.push(objFeildEng);
  			feildEngineerListForPartner.push((lstFeDetails.list[j].fieldEngineer).toUpperCase());
  	    }	
	    selectedChannelPartner.fieldEngList = availableTags;
	    
	     
	    $( "#fieldengineerId"+partnerId).autocomplete({
	    	minLength:0,
            source: function(request, response) {
            var results = $.ui.autocomplete.filter(availableTags, request.term);
            if (results.length > 0) {
                response($.map(results, function (item) {
                    return {
                        label: item.label,
                        value: item.value
                    };
                }))
            } else {
                response([{ value:"", label: 'No results found.'}]);
            }      
         
            },
            
        focus: function (event, ui) {
        	 $( "#fieldengineerId"+partnerId  ).val( ui.item.label );
            return false;
        },
        select: function (event, ui) {
        	$( "#fieldengineerId"+partnerId ).val(ui .item.label );
	           $( "#fieldEngId"+partnerId ).val( ui.item.value );	   
            return false;
        },
        change: function(event, ui) {
        	 if (!ui.item) {
                  $(this).val('');
                  return false;
                }
            }
            });
	   
	     
  	}  
	   
	   else
		   {
		  // $("#fieldengineerId"+partnerId).prop('disabled', true);
		   document.getElementById('fieldengineerId'+partnerId).disabled = true;
			   
		   }
	   
  }
  
  function confirmPartnerId(fid)
	  {
		if  ($('input[type=radio]:checked').length == 0)
			{
			alert("Please select Channel Partner first"); 
			document.getElementById(fid).value= "";
		        return false;  
		        
		    }
		else
		{		
			var fid1 = parseInt(fid.match(/[0-9]+/)[0], 10);
			var radioId = parseInt(('chkId'+selectedChannelPartner.partnerId).match(/[0-9]+/)[0], 10);
			
			if(radioId!==fid1)
			{
			alert("Please select Channel Partner first");
			document.getElementById(fid).value= "";
			 return false;
			}
		}	
						
			return true;
	  }
  
  function getEligibleCutSegmforFeId()
  {	
  	  convertCustSegListtoCommaSepName();
      var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
      var getEligibleCustSegmforFeTemp = jsonrpc.processes.getEligibleCustSegmforFeId();	
      for (var i=0;i<getEligibleCustSegmforFeTemp.list.length;i++){
    		custSegmentList.push(getEligibleCustSegmforFeTemp.list[i]);
      }
      DrawTable('PARTNER_NAME','SELECTCHANNELPARTNER');
  }
  
 
 function DrawChannelPatnerType(){	 
 	  var str;
 	  
 	  var callerWindowObj = dialogArguments;
 	  var crmAccountNo=callerWindowObj.document.getElementById('crmAccountNo').value ;
 	  
 	  var frm=document.getElementById('partnerTypes');
 	  var mytable = document.getElementById('tabChannelPartnerType');	
 
 	  var iCountRows = mytable.rows.length;
   
 	   for (i = 1; i <  iCountRows; i++)
 	   {
 	       mytable.deleteRow(1);
 	   }   	  
		
 	   //mytable.innerHTML = "";
 	   document.getElementById('txtPageNumber').value= pageNumber;
 	   sync();	
 	  
 	   var jsData =
 			{
 				startIndex:startRecordId,
 				endIndex:endRecordId,
 				sortBycolumn:sortByColumn,
 				sortByOrder:sortByOrder,
 				channelPartnerName:document.getElementById('txtChannelPartnerName').value,
 				channelpartnerCode:document.getElementById('txtChannelPartnerCode').value,
 				crmAccountNo:callerWindowObj.document.getElementById('crmAccountNo').value 
 			};
 				  
 	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
 	
 	 var lstChannelPartner = jsonrpc.processes.getChannelPartnerList(jsData);
 	
 	var v_iTotalLength=lstChannelPartner.list.length;	
 	
 	if(v_iTotalLength !=0)
 	{
 		var v_iNoOfPages = lstChannelPartner.list[0].maxPageNo;
 		iNoOfPages=v_iNoOfPages;
 	}
 	 else
 	{     
 	    v_iNoOfPages=1;
 	}
 	
 	
 	document.getElementById('txtMaxPageNo').value=v_iNoOfPages;				
 	var colors=new Array("normal","lightBg");
 	
 <%-- 	var counter = <%=request.getAttribute("count")%>; --%>
 	
 
 	var isFEEnabled = false;
 	
 	for (i = 0; i <  v_iTotalLength; i++){
 	 	var colorValue=parseInt(i)%2;	 
 	  	
 	   	var tblRow=document.getElementById('tabChannelPartnerType').insertRow();
 		tblRow.className=colors[colorValue];
 		var tblcol=tblRow.insertCell();
 		tblcol.align = "center";
 		tblcol.vAlign="top";

 	     /* //str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstChannelPartner.list[i].channelPartnerId) +"','"+ escape(lstChannelPartner.list[i].channelPartnerCtgryName) +"','"+ escape(lstChannelPartner.list[i].channelPartnerCode) +"') />"; */
 	
 	    str="<input name=chk id='chkId"+lstChannelPartner.list[i].channelPartnerId+"'  type=radio onclick=PopulateFieldEngineer('"+lstChannelPartner.list[i].cust_Segment_Id+"','"+lstChannelPartner.list[i].channelPartnerId+"','"+escape(lstChannelPartner.list[i].channelPartnerName)+"','"+escape(lstChannelPartner.list[i].channelpartnerCode)+"') />";
 		CellContents = str;
 		tblcol.innerHTML = CellContents;
 		
 		tblcol=tblRow.insertCell();
 		tblcol.align = "left";
 		tblcol.vAlign="top";
 		str=lstChannelPartner.list[i].channelPartnerName;
 		CellContents = str;
 		tblcol.innerHTML = CellContents;
 		
 		tblcol=tblRow.insertCell();
 		tblcol.align = "left";
 		tblcol.vAlign="top";
 		str=lstChannelPartner.list[i].channelpartnerCode;
 		CellContents = str;
 		tblcol.innerHTML = CellContents;
 		
 		tblcol=tblRow.insertCell();
 		tblcol.align = "left";
 		tblcol.vAlign="top";
 		str=lstChannelPartner.list[i].lob;
 		CellContents = str;
 		tblcol.innerHTML = CellContents;
 		
 		if($.inArray(lstChannelPartner.list[i].cust_Segment_Id, custSegmentList) > 0)
 	 		str="<input type='text' class='inputBorder1' ctrltype='dropdownforchannelpartnerid' retval='AUTOSUGGESTFIELDENGINEER' name='fieldEng' id='fieldengineerId"+lstChannelPartner.list[i].channelPartnerId+"' style='width:200px; float:left;' width='100px' onkeyup='confirmPartnerId(id)' />"+"<input type='hidden' name='fieldEngId' id='fieldEngId"+lstChannelPartner.list[i].channelPartnerId+"'/>";
 		
 	 		else
 	 		str="<input type='text' disabled style='background-color:#D3D3D3;'ctrltype='dropdownforchannelpartnerid' retval='AUTOSUGGESTFIELDENGINEER' name='fieldEng' id='fieldengineerId"+lstChannelPartner.list[i].channelPartnerId+"' style='width:200px; float:left;' width='100px' />"+"<input type='hidden' name='fieldEngId' id='fieldEngId"+lstChannelPartner.list[i].channelPartnerId+"'/>";

 		var tblcol=tblRow.insertCell();
 		tblcol.align = "left";
 		tblcol.vAlign="top";
 		CellContents = str;
 		tblcol.innerHTML = CellContents; 
 		
 	 }

 	var pagenumber=document.getElementById('txtPageNumber').value;
 	var MaxPageNo=document.getElementById('txtMaxPageNo').value;
	
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
 	
 	if(v_iTotalLength== 0)
 	{
 		var tblRow=document.getElementById('tabChannelPartnerType').insertRow();
 	    tblcol=tblRow.insertCell();
 		tblcol.align = "center";
 		tblcol.vAlign="top";
 		tblcol.colSpan = 3;
 		str='NO RECORD FOUND'
 		CellContents = str;
 		tblcol.innerHTML = CellContents;
 	}	
 		return false;
 }
 //
 
 
function loadPartnerCodeonSource(/* partnerId,partnerName , partnerCode */)
  {
	    var availableTags = [];
	    var objFeildEng = {};
	    /* selectedChannelPartner.partnerId = partnerId;
	    selectedChannelPartner.partnerCode = partnerCode;
	    selectedChannelPartner.partnerName = partnerName; */
	 	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	    var lstFeDetails = jsonrpc.processes.getPartnerCode(callerWindowObj.document.getElementById('crmAccountNo').value);
	 	 
	     for(j=0;j<lstFeDetails.list.length;j++)
  		{        	
	    	objFeildEng = {}; 
  			var combo = document.getElementById('txtChannelPartnerCode');	    			
  			objFeildEng.label = lstFeDetails.list[j].channelpartnerCode;
  			objFeildEng.value = lstFeDetails.list[j].channelpartnerCode;
  			availableTags.push(objFeildEng);
  	    }	
	    	     
	     $( "#txtChannelPartnerCode" ).autocomplete({
	         minLength: 0,
	         source: availableTags,
	         focus: function( event, ui ) {
	           $( "#txtChannelPartnerCode"  ).val( ui.item.label );
	           return false;
	         },
	         select: function( event, ui ) 
	         {
	           $( "#txtChannelPartnerCode" ).val( ui.item.label );
	           //$( "#autoSuggestTextFieldIdHidden" ).val( ui.item.value );	           
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
  
  function loadPartnerNameonSource(/* partnerId,partnerName , partnerCode */)
  {
	    var availableTags = [];
	    var objPartnerName = {};
	    /* selectedChannelPartner.partnerId = partnerId;
	    selectedChannelPartner.partnerCode = partnerCode;
	    selectedChannelPartner.partnerName = partnerName; */
	 	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	    var lstPartnerDetails = jsonrpc.processes.getPartnerNameList(callerWindowObj.document.getElementById('crmAccountNo').value);
	     for(j=0;j<lstPartnerDetails.list.length;j++)
  		{        	
	    	objPartnerName = {}; 
  			var combo = document.getElementById('txtChannelPartnerName');	    			
  			objPartnerName.label = lstPartnerDetails.list[j].channelPartnerName;
  			objPartnerName.value = lstPartnerDetails.list[j].channelPartnerName;
  			availableTags.push(objPartnerName);
  	    }	
	    	     
	     $( "#txtChannelPartnerName" ).autocomplete({
	         minLength: 0,
	         source: availableTags,
	         focus: function( event, ui ) {
	           $( "#txtChannelPartnerName"  ).val( ui.item.label );
	           return false;
	         },
	         select: function( event, ui ) 
	         {
	           $( "#txtChannelPartnerName" ).val( ui.item.label );
	           //$( "#txtChannelPartnerName" ).val( ui.item.value );	           
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
 
 function clearFields()
 {
 	document.getElementById('txtChannelPartnerName').value="";
 	document.getElementById('txtChannelPartnerCode').value="";	
 }
 var path='<%=request.getContextPath()%>';
 function isBlankFields()
 {
 	var channelPartnerName=document.getElementById('txtChannelPartnerName').value;
 	var channelPartnerCode=document.getElementById('txtChannelPartnerCode').value;
 		
 		var searchFlag=1;
 	
 		if( trim(document.getElementById('txtChannelPartnerName').value).length>0)
 		{
 			if(ValidateTextField(document.getElementById('txtChannelPartnerName'),path,'Channel Partner Name')==false) 
 			{
 				searchFlag=0;
 				return false;
 			}
 		}
 		
 		if( trim(document.getElementById('txtChannelPartnerCode').value).length>0)
 		{	
 			if(ValidateTextField(document.getElementById('txtChannelPartnerCode'),path,'Channel Partner Code')==false) 
 			{
 				searchFlag=0;
 				return false;
 			}
 		}
 		if(searchFlag==1)
 		{
 			searchFromList('PARTNER_NAME','SELECTCHANNELPARTNER');
 		}
 }

 
 
 path='<%=request.getContextPath()%>';
</script>
</head>
<body onload="getEligibleCutSegmforFeId();loadPartnerCodeonSource();loadPartnerNameonSource();" style="vertical-align: top;">

<center>
<html:form action="/NewOrderAction" styleId="partnerTypes" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- <td align="center"><div align="center" class="head"> <span id=channelPartnerTaggingAppend>Channel Partner Tagging ( LOB Name : </span></div></td> -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
		<tr>
			<td align="center"><a href="#" id= "first" onclick="FirstPage('PARTNER_NAME','SELECTCHANNELPARTNER')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('PARTNER_NAME','SELECTCHANNELPARTNER')">Next</a></td>
			<td align="center">
				<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
				<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
				
		</td>
		<td><div class='searchBg' onclick='getInfoAndUpdate()'><a href='#'>OK</a></div></td>
		
			<td align="center"><a href="#" id="prev" onclick="PrevPage('PARTNER_NAME','SELECTCHANNELPARTNER')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('PARTNER_NAME','SELECTCHANNELPARTNER')">Last</a></td>
		</tr>
	</table>			
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Partner Name:</strong><input type="text" class="inputBorder2" maxlength="200" id="txtChannelPartnerName" name="channelPartnerName"  onkeydown="if (event.keyCode == 13)  return isBlankFields();"   >
					</div></td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Partner Code:</strong><input type="text" class="inputBorder2" maxlength="200" id="txtChannelPartnerCode" name="channelPartnerCode"  onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>		
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
				</tr>
	</table>
			<table width="100%"  border="1" id="tabChannelPartnerType" align="center" cellpadding="0" cellspacing="0" class="tab2" >			   
				<tr class="grayBg">
				    <td width="2%" align="center">Select
				    <input type="hidden" name="hdnRadioBtn" id="hdnRadioBtn" />
				    </td>
					<td width="8%" align="left">
						<a href="#" onclick="sortOrder('PARTNER_NAME','SELECTCHANNELPARTNER')">Partner Name</a>				
					</td>
					<td width="6%" align="left">
						<a href="#" onclick="sortOrder('PARTNER_NAME','SELECTCHANNELPARTNER')">Partner Code</a>					
					</td>
					<td width="2%" align="left">
						LOB Name				
					</td>
					<td width="6%" align="left">
						FSE				
					</td>
					
					
				</tr>
			</table>		
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="20%" >&nbsp;</td>
			<td width="20%" colspan="3">&nbsp;</td>
		</tr>
	</table>	
	<input type="hidden" id="hdnCustomerSegment" />
</html:form>
</center>
</body>
<script type="text/javascript">
	
</script>
</html>


	
