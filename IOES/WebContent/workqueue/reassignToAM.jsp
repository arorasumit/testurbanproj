<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Raveendra Kumar	25-Mar-15	20150225-R1-021110     Order transfer rights required on GUI for AM Role -->
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
<title>Reassign Order To Another AM</title>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
<style type="text/css">
    .opaqueLayer
    {
        display:none;
        position:absolute;
        top:0px;
        left:0px;
        opacity:0.5;
        filter:alpha(opacity=50);
        background-color: #000000;
        z-Index:1000;
        text-align:center;
        vertical-align:middle;
        padding:100px;
    }
    
</style>
<!-- added by manisha start -->

<%
			HashMap var_userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession var_sessionObj = (HttpSession) var_userHashMap.get(session.getId());
			UserInfoDto var_objUserDto  = (UserInfoDto)var_sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			String accountManagerName="";
			String description="";
			
			if (request.getParameter("accountManagerName") != null && !"".equals(request.getParameter("accountManagerName")))
			{
				accountManagerName=request.getParameter("accountManagerName");
					
			}
			
			if (request.getParameter("description") != null && !"".equals(request.getParameter("description")))
			{
				description=request.getParameter("description");
					
			}
			
%>

<!-- added by manisha end -->

<script language="javascript" type="text/javascript">
var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 

function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

// for auto suggest
function callOnBlur()
	{
		$(":text[ctrltype='dropdown']").each(function() {
				var thisEl = $(this);				
			$(thisEl).blur(function()
			{				
				
			});	
		});
	}	
function getAutoSuggest() 
	{
		
				       		         	         
	         $(":text[ctrltype='dropdown']").each(function() {
				var thisEl = $(this);																				
	        	thisEl.autocomplete({
	    			   
			            source: function(request, response) 
			            {
			            			            																	
	                	$.ajax({
							url: "<%=request.getContextPath()%>/NewOrderAction.do?method=reassignToAM&searchval="+request.term+"&attributefor="+thisEl.attr("retval"),
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
                                     	if(thisEl.attr("retval")=="AUTOSUGGESTAMASSIGNED")
                                     	{
                                     		$(thisEl).val("");	
											$("#bbPrimaryAMIDAssigned_"+thisEl.attr("counterval")).val("0");
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTAM")
                                     	{
                                     		$(thisEl).val("");	
											$("#bbPrimaryAMID").val("0");
											  
                                     	} 
                                     	    
                                     }
                                     else
                                     {
                                     	
                                       if(thisEl.attr("retval")=="AUTOSUGGESTAMASSIGNED")
                                     	{
		                                    $("#bbPrimaryAMIDAssigned_"+thisEl.attr("counterval")).val(ui.item.value);
		                                     $(thisEl).val(ui.item.label);
                                     	}
                                     	
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTAM")
                                     	{
                                     		$("#bbPrimaryAMID").val(ui.item.value);
		                                    $(thisEl).val(ui.item.label);
                                     	}
                                     	
		                            
                                     }
                                     
                                     
           return false;
       },
	   change: function( event, ui ) 
							{
								
																																	
								if(ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1"|| $("#bbPrimaryAMIDAssigned_"+thisEl.attr("counterval")).val()=="0" )
								{										                				
	                				var value2,label2;
	                				
	                			 var validateData=$.ajax({
													url: "<%=request.getContextPath()%>/NewOrderAction.do?method=reassignToAM&searchval="+thisEl.val()+"&attributefor="+thisEl.attr("retval"),
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
										
                                     		
									   if(thisEl.attr("retval")=="AUTOSUGGESTAMASSIGNED")
                                     	{
                                     		$(thisEl).val("");
											$("#bbPrimaryAMIDAssigned_"+thisEl.attr("counterval")).val("0");
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTAM")
                                     	{
                                     		$$(thisEl).val("");
											$("#bbPrimaryAMID").val("0");	
                                     	} 
                                     	
                                     	
									}else{
											if(thisEl.attr("retval")=="AUTOSUGGESTAMASSIGNED")
                                     			{
										
		                                     		$(thisEl).val(label2);
													$("#bbPrimaryAMIDAssigned_"+thisEl.attr("counterval")).val(value2);		                                    
											    }
										else if(thisEl.attr("retval")=="AUTOSUGGESTAM")
	                                     	{
	                                     		$(thisEl).val(label2);
												$("#bbPrimaryAMID").val(value2);
	                                     	} 		
								
									}				                				    			
																
								}														
							},								
	            minLength: 0
	        });
			});							
	}
	
	

function AttachCSStoAutoSuggestButton()
	{
			$("[ctrltype='dropdownlink']").each(function()
			 {
				var thisEl = $(this);
				thisEl.attr( "tabIndex", -1 )
					.attr( "title", "Show All Items" )
					.button({
						icons: {
							primary: "ui-icon-triangle-1-s"
						},
						text: false
					})
					.removeClass( "ui-corner-all" )
					.addClass( "ui-corner-right ui-button-icon" )
		    });	
		   // callOnBlur();
  }
function getDropDownAM()
	{
		$("#accountManager").autocomplete( "search", "" );
		$("#accountManager").focus();
	}
	
function getDropDownAMAssigned(counter_val)
	{
		$("#accountManagerAssigned_"+counter_val).autocomplete( "search", "" );
		$("#accountManagerAssigned_"+counter_val).focus();
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

function popitup(url) 
{
	if(url=="SelectAccount")
	{
		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccount_ofrreassignam";		
		//	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchAccount";		
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
	}
	return false;
}

function pagingSubmit(val)
{
	var myForm=document.getElementById('searchForm');
    myForm.elements["pagingSorting.pageNumber"].value=val;
	orderNo=myForm.orderNo.value;
	var employeeid = "<%=var_objUserDto.getEmployeeId() %>";
	var description=myForm.bbPrimaryAMID.value;
    var arr=new Array();
    arr=description.split(":"); 
	accountManager=arr[0];
	accountManagerName=myForm.accountManager.value;
	crmAccountNo=myForm.crmAccountNo.value;
	if((crmAccountNo==null || trim(crmAccountNo)=="" ) && ( orderNo == null || trim(orderNo)=="")&& (accountManager==0 || trim(accountManager)=="" ) ){
		alert("Please enter atleast one search criteria!");
		return false;
	} 
	var incompletemyForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do?method=getOrderListForPendingWithAM");
	attachHiddenField(incompletemyForm,"orderNo",orderNo);
	attachHiddenField(incompletemyForm,"crmAccountNo",crmAccountNo);
	attachHiddenField(incompletemyForm,"accountManager",accountManager);
	attachHiddenField(incompletemyForm,"Employeeid",employeeid);
	attachHiddenField(incompletemyForm,"accountManagerName",accountManagerName);
	attachHiddenField(incompletemyForm,"description",description);
	attachHiddenField(incompletemyForm,"pagingSorting.pageNumber",val);
	incompletemyForm.method = "POST";
	incompletemyForm.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	myform.elements["pagingSorting.pageNumber"].value=1;
	if(myform.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myform.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myform.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myform.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myform.elements["pagingSorting.sortByColumn"].value=sortBy;
		myform.elements["pagingSorting.sortByOrder"].value="decrement";
	}
	myform.method.value='getOrderListForPendingWithAM';	 
	showLayer();
	myform.submit();
}

function searchClick()
{
	var myForm=document.getElementById('searchForm');
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	orderNo=myForm.orderNo.value;
	var employeeid = "<%=var_objUserDto.getEmployeeId() %>";
	var description=myForm.bbPrimaryAMID.value;
    var arr=new Array();
    arr=description.split(":"); 
	accountManager=arr[0];
	accountManagerName=myForm.accountManager.value
	crmAccountNo=myForm.crmAccountNo.value;
	if((crmAccountNo==null || trim(crmAccountNo)=="" ) && ( orderNo == null || trim(orderNo)=="")&& (accountManager==0 || trim(accountManager)=="" ) ){
		alert("Please enter atleast one search criteria!");
		return false;
	} 
	var incompletemyForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do?method=getOrderListForPendingWithAM");
	attachHiddenField(incompletemyForm,"orderNo",orderNo);
	attachHiddenField(incompletemyForm,"crmAccountNo",crmAccountNo);
	attachHiddenField(incompletemyForm,"accountManager",accountManager);
	attachHiddenField(incompletemyForm,"Employeeid",employeeid);
	attachHiddenField(incompletemyForm,"accountManagerName",accountManagerName);
	attachHiddenField(incompletemyForm,"description",description);
	incompletemyForm.method = "POST";
	incompletemyForm.submit();
}


function clearFields()
{
	
	var myForm=document.getElementById('searchForm');
	myForm.crmAccountNo.value='' ; 
	myForm.orderNo.value='' ; 
	myForm.accountManager.value='' ; 
	myForm.bbPrimaryAMID.value=0;
	document.getElementById('clearhideen').value=1;
		
}
function submitReassignRequest()
{
		var myForm=document.getElementById('searchForm');
		accountManager= "<%= request.getParameter("accountManager")%>";
		orderNo=myForm.orderNo.value;
		accountManagerName=myForm.accountManager.value;
		var description=myForm.bbPrimaryAMID.value;
		crmAccountNo=myForm.crmAccountNo.value;
		var orderTable = document.getElementById('tblOrderList');
		var chkLength1=document.forms[0].chkSelectOrderNo.length;
		var orderNoList='';
		var reassignamidList='';
		var reassignamnameList='';
		var employeeid = "<%=var_objUserDto.getEmployeeId() %>";
		var actmgridlist='';
		var lastmgremailList='';
		var lastmgridList='';
		var selectam=0;
		if(chkLength1==undefined)
		{
			if(document.forms[0].chkSelectOrderNo.checked==true)
			{
				if(document.getElementById('bbPrimaryAMIDAssigned_'+1).value!='')
				{
					orderNoList = document.forms[0].chkSelectOrderNo.value;
					reassignamidList = document.getElementById('bbPrimaryAMIDAssigned_'+1).value;
					reassignamnameList = document.getElementById('accountManagerAssigned_'+1).value;
					actmgridlist = document.getElementById('actmgremailid'+1).value;
					lastmgremailList=document.getElementById('actmgremailid'+1).value;
					if(document.getElementById('actmgrid'+1).value=='')
					{
						lastmgridList=0;
					}
					else
					{
							lastmgridList=document.getElementById('actmgrid'+1).value;
					}
					if(document.getElementById('actmgremailid'+1).value=='')
					{
						lastmgremailList='';
					}
					else
					{
							lastmgremailList=document.getElementById('actmgremailid'+1).value;
					}
				}
				else
				{
					alert('Please Enter Value in Reassign To AM for OrderNo :' + document.forms[0].chkSelectOrderNo.value );
					return false;
				}
			}
			else
			{
				alert('Please Select Order for Updation');
				return false;
			}
			
		}
		else
		{
			for(i=0;i<chkLength1;i++)
		    {
		    	if(document.forms[0].chkSelectOrderNo[i].checked==true)
		    	{
		    		selectam++;
		    		if(document.getElementById('bbPrimaryAMIDAssigned_'+(i+1)).value!='')
		    		{
		    			if(orderNoList == '')
		    				orderNoList = orderNoList+document.forms[0].chkSelectOrderNo[i].value;
		    		 	else
		    				orderNoList = orderNoList+',' +document.forms[0].chkSelectOrderNo[i].value;
		    			
		    			if(reassignamidList == '')
		    			{
		    				reassignamidList = reassignamidList + document.getElementById('bbPrimaryAMIDAssigned_'+(i+1)).value;
		    				actmgridlist = actmgridlist+document.getElementById('actmgremailid'+(i+1)).value;
				    		if(document.getElementById('actmgrid'+(i+1)).value=='')
							{
								lastmgridList=lastmgridList +  0;
							}
							else
							{
									lastmgridList=lastmgridList  + document.getElementById('actmgrid'+(i+1)).value;
							}
							lastmgremailList=lastmgremailList + document.getElementById('actmgremailid'+(i+1)).value;
							reassignamnameList =reassignamnameList+ document.getElementById('accountManagerAssigned_'+(i+1)).value;
							
		    			}
		    			else
		    			{
		    				reassignamidList = reassignamidList +',' + document.getElementById('bbPrimaryAMIDAssigned_'+(i+1)).value;
		    				actmgridlist = actmgridlist+','+document.getElementById('actmgremailid'+(i+1)).value;
							if(document.getElementById('actmgrid'+(i+1)).value=='')
							{
								lastmgridList=lastmgridList + ',' + 0;
							}
							else
							{
									lastmgridList=lastmgridList + ',' + document.getElementById('actmgrid'+(i+1)).value;
							}
							lastmgremailList=lastmgremailList+ ',' + document.getElementById('actmgremailid'+(i+1)).value;
							reassignamnameList =reassignamnameList+ ','+ document.getElementById('accountManagerAssigned_'+(i+1)).value;
		    			}
		    	 }
		    	 else
		    	 {
		    	 	alert('Please Enter Value in Reassign To AM for OrderNo : ' + document.forms[0].chkSelectOrderNo[i].value )
		    	 	return false;
		    	 	
		    	 } 
		     }
		  }
		  if(selectam==0)
		  {
		  	alert('Please Select Order for Updation');
		  	return false;
		  }
		  
		}
	    var retVal = jsonrpc.processes.reassignAM(reassignamidList,reassignamnameList,orderNoList,employeeid,lastmgridList,lastmgremailList,actmgridlist);
	    alert(retVal);
	    if(retVal.search("Following")<0){
	    	var incompletemyForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do?method=getOrderListForPendingWithAM");
	    	attachHiddenField(incompletemyForm,"orderNo",orderNo);
	    	attachHiddenField(incompletemyForm,"crmAccountNo",crmAccountNo);
	    	attachHiddenField(incompletemyForm,"accountManager",accountManager);
	    	attachHiddenField(incompletemyForm,"Employeeid",employeeid);
	    	attachHiddenField(incompletemyForm,"accountManagerName",accountManagerName);
	    	attachHiddenField(incompletemyForm,"description",description);
	    	incompletemyForm.method = "POST";
	    	incompletemyForm.submit();
	    	
	    }
	    
}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="getAutoSuggest();AttachCSStoAutoSuggestButton()" style="vertical-align: top;">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<html:form action="/NewOrderAction"  styleId="searchForm" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<input type="hidden" name="fromPageSubmit" value="1"/>		
<html:hidden property="pagingSorting.sortByColumn"/>
<html:hidden property="pagingSorting.sortByOrder"/>
<html:hidden property="pagingSorting.pageNumber"/>	
<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%" style="vertical-align: top">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>REASSIGN ORDER TO ANOTHER AM</span> </div>
		<fieldset width="100%" border="1" align="center" class="border2">
			<legend><b>Search</b></legend>
				<table border="0" cellspacing="0"  align="center" cellpadding="0" style="margin-top:7px" style="vertical-align: top" >
					<tr>
						<td align=right style="font-size:9px"> A/C No:</td>
						<td align="left">
							<html:text  property="crmAccountNo" styleClass="inputBorder2" style="width:60px;float:left;" readonly="true" ></html:text>
							<div class="searchBg1"><a href="#" onClick="return popitup('SelectAccount')">...</a></div> 
						</td>
						<td width="10px"/>
						
						<td align="right" style="font-size:9px">Order Number:</td>
						<td align="left">
							<html:text  property="orderNo" styleClass="inputBorder2" style="width:50px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="right" style="font-size:9px">AM:</td>
						<td align="left">
							<input type="text" styleClass="inputBorder2" style="width:150px;float:left;" name="accountManager" value="<%=accountManagerName %>" ctrltype='dropdown' retval='AUTOSUGGESTAM' id="accountManager" ><a id="AMAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownAM()">Show</a>								
								<input type="hidden" name="bbPrimaryAMID"  id="bbPrimaryAMID" isrequired="1" value="<%=description %>">
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
							<input type="hidden" name="clearhideen"  id="clearhideen" value="0" isrequired="1">
						</td>
						<td><input type="hidden" styleClass="inputBorder2" style="width:200px;float:left;" id="accountmgrid" name="accountmgrid" value="0"></td>
						
						<td width="30px"/>
						<td align="right" width="70px"><div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div></td>
						
					</tr>
					<tr height="5px">
					 <td colspan="15" align="center"><B><font color="red">Orders containing AM in the workflow with pending Approval by AM and Orders Containing in Draft Mode Will be Searched</font></td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
					<legend> <b>Order List</b></legend>
			<br/>
			<table width="100%">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((NewOrderBean)formBean).getPagingSorting().getPageNumber());
						if(currPageNumber.equals("0"))
							currPageNumber="1";
						String 	maxPageNumber =String.valueOf(((NewOrderBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="../paging.jsp">
							<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
							<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
						</jsp:include>
					</td>
				</tr>
			</table>
			<table border="0" cellpadding="0" cellspacing="0" class='main'>
				<tr>
					<td class='tablefrozencolumn'>
	                   <div id='divroot' class='root'>
	                       <table border="0" cellpadding="0" cellspacing="0" width="100%" class='root'>
	                           <tr>
	                               <td class='inner frozencol colwidth head1 '> 
	                               </td>
	                           </tr>
	                       </table>
	                   </div>
	                   <div id='divfrozen' class='frozen'>
	                       <table border="0" cellpadding="0" cellspacing="0" width="100%" class='frozen' >
								<tr>
	                               <td class='inner frozencol toprow'>
	                               </td>
	                           </tr>                            
	                       </table>
	                   </div>
	               	</td>
					<td class='tablecontent'>
						<div id='headscroll' class='divhead'>
							<table border="0" cellpadding="0" cellspacing="0" class='head1'>
								<tr>
								    <td align="center" class='inner col1 head1' ><b>Select</b></td>
									<td align="center" class='inner col2 head1' ><b>Order No</b></td>
									<td align="center" class='inner col3 head1' ><b>Initial AM</b></td>
								    <td align="center" class='inner col3 head1' ><b>Last Changed AM</b></td>
								    <td align="center" class='inner col3 head1' ><b>Reassign To AM</b></td>
									<td align="center" class='inner col3 head1' ><b>Last Changed By</b></td>
							  	</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'style="height: ">
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tableOrderListForPendingWithAM'>		
								<logic:present name="displayOrderListForPendingWithAM" scope="request">
									<logic:notEmpty  name="displayOrderListForPendingWithAM" scope="request"> 					
										<logic:iterate id="row" name="displayOrderListForPendingWithAM" indexId="recordId">
											<%
												String classType=null;
											 int count = ((recordId.intValue()) + 1);
												if(recordId.intValue() % 2 == 0)
												{
												classType="lightBg";
												}
												else
												{
												classType="normal";				
												}	
											%>				
											<tr class="<%=classType%>">
											  <td align="center" class='inner col1'><input name="chkSelectOrderNo" id="chkSelectOrderNo" type="checkbox" value="<bean:write  name="row" property="orderNo"/>">&nbsp;</td>
								                <td align="center" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="initial_am"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="last_am"/>&nbsp;</td>
												<td  class='inner col3'>
												<table><tr><td><input type="text" styleClass="inputBorder2" style="width:200px;float:left;" name="accountManagerAssigned" counterval='<%=count%>' ctrltype='dropdown' retval='AUTOSUGGESTAMASSIGNED' id="accountManagerAssigned_<%=count%>"></td><td><a id="AMAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownAMAssigned(<%=count%>)">Show</a></td></tr></table>
																														
									<input type="hidden" name="bbPrimaryAMAssigned"  id="bbPrimaryAMIDAssigned_<%=count%>" isrequired="1">
								  &nbsp;</td>
												<td align="justify" class='inner col3'><bean:write  name="row" property="last_changed_by"/>&nbsp;
												<input type="hidden" name="actmgremailid" id="actmgremailid<%=count%>"  value="<bean:write  name="row" property="acmgrEmail"/>">
								<input name="actmgremailid" id="actmgremailid<%=count%>" type="hidden" value="<bean:write  name="row" property="accountmgremail"/>">
								<input name="actmgrid" id="actmgrid<%=count%>" type="hidden" value="<bean:write  name="row" property="accountManager"/>">
							
												</td>
										   </tr>
										</logic:iterate>
									</logic:notEmpty>
									<logic:empty  name="displayOrderListForPendingWithAM">
					   <tr align="center" >
						 <td  align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
					</logic:empty>	
								</logic:present>
								
												
							</table>
						</div>
					</td>
					<td class='tableverticalscroll' rowspan="2">
						<div class='vertical-scroll' onscroll='reposVertical(this);'>
	                        <div></div>
	                    </div>
	                    <div class='ff-fill'>
	                    </div>		
					</td>
				</tr>
				<tr>
	               	<td colspan="3">
	                   	<div class='horizontal-scrollOrderListForPendingWithAM' onscroll='reposHorizontal(this);'>
	                       	<div></div>
	                   	</div>
	               	</td>
				</tr>
				
			</table>
			<!-- End Scrolling and Freezing of OrderNewReport-->	
			<table align="center"><tr>
								<td  align="center">
									<input type="button"	name="SubmitReassignRequest" value="Reassign" onClick="submitReassignRequest()">
								</td>	
						</tr></table>
		</fieldset>
	</html:form>
</body>
</html>
