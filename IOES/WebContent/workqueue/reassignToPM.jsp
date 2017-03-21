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
<title>Reassign Order To Another PM</title>
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
							url: "<%=request.getContextPath()%>/NewOrderAction.do?method=reassignToPM&searchval="+request.term+"&attributefor="+thisEl.attr("retval"),
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
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTPM")
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
                                     	
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTPM")
                                     	{
                                     		$("#bbPrimaryPMID").val(ui.item.value);
		                                    $(thisEl).val(ui.item.label);
                                     	}
                                     	
		                            
                                     }
                                     
                                     
           return false;
       },
	   change: function( event, ui ) 
							{
								
																																	
								if(ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1"|| $("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val()=="0" )
								{										                				
	                				var value2,label2;
	                				
	                			 var validateData=$.ajax({
													url: "<%=request.getContextPath()%>/NewOrderAction.do?method=reassignToPM&searchval="+thisEl.val()+"&attributefor="+thisEl.attr("retval"),
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
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTPM")
                                     	{
                                     		$$(thisEl).val("");
											$("#bbPrimaryPMID").val("0");	
                                     	} 
                                     	
                                     	
									}else{
											if(thisEl.attr("retval")=="AUTOSUGGESTPMASSIGNED")
                                     			{
										
		                                     		$(thisEl).val(label2);
													$("#bbPrimaryPMIDAssigned_"+thisEl.attr("counterval")).val(value2);		                                    
											    }
										else if(thisEl.attr("retval")=="AUTOSUGGESTPM")
	                                     	{
	                                     		$(thisEl).val(label2);
												$("#bbPrimaryPMID").val(value2);
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
function getDropDownPM()
	{
		$("#projectManager").autocomplete( "search", "" );
		$("#projectManager").focus();
	}
	
function getDropDownPMAssigned(counter_val)
	{
		$("#projectManagerAssigned_"+counter_val).autocomplete( "search", "" );
		$("#projectManagerAssigned_"+counter_val).focus();
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
		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccount_ofrreassignpm";		
		//	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchAccount";		
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
	}
	return false;
}

function searchClick()
{
	var myForm=document.getElementById('searchForm');
	orderNo=myForm.orderNo.value;
	var employeeid = "<%=var_objUserDto.getEmployeeId() %>";
	var description=myForm.bbPrimaryPMID.value;
    var arr=new Array();
    arr=description.split(":"); 
	projectManager=arr[0];
	crmAccountNo=myForm.crmAccountNo.value;
	if((crmAccountNo==null || trim(crmAccountNo)=="" ) && ( orderNo == null || trim(orderNo)=="")&& (projectManager==0 || trim(projectManager)=="" ) ){
		alert("Please enter atleast one search criteria!");
		return false;
	} 
	myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=getOrderListForPendingWithPM&orderNo="+orderNo+"&crmAccountNo="+crmAccountNo+"&projectManager="+projectManager+"&Employeeid="+employeeid;
	myForm.submit();
}


function clearFields()
{
	
	var myForm=document.getElementById('searchForm');
	myForm.crmAccountNo.value='' ; 
	myForm.orderNo.value='' ; 
	myForm.projectManager.value='' ; 
	myForm.bbPrimaryPMID.value=0;
	document.getElementById('clearhideen').value=1;
		
}
function submitReassignRequest()
{
		var myForm=document.getElementById('searchForm');
		projectManager= "<%= request.getParameter("projectManager")%>";
		orderNo=myForm.orderNo.value;
		crmAccountNo=myForm.crmAccountNo.value;
		var orderTable = document.getElementById('tblOrderList');
		var chkLength1=document.forms[0].chkSelectOrderNo.length;
		var orderNoList='';
		var reassignpmidList='';
		var reassignpmnameList='';
		var id = "<%=var_objUserDto.getEmployeeId() %>";
		var actmgridlist='';
		var lastmgremailList='';
		var lastmgridList='';
		var selectpm=0;
		if(chkLength1==undefined)
		{
			if(document.forms[0].chkSelectOrderNo.checked==true)
			{
				if(document.getElementById('bbPrimaryPMIDAssigned_'+1).value!='')
				{
					orderNoList = document.forms[0].chkSelectOrderNo.value;
					reassignpmidList = document.getElementById('bbPrimaryPMIDAssigned_'+1).value;
					reassignpmnameList = document.getElementById('projectManagerAssigned_'+1).value;
					actmgridlist = document.getElementById('actmgremailid'+1).value;
					lastmgremailList=document.getElementById('prjmgremailid'+1).value;
					if(document.getElementById('prjmgrid'+1).value=='')
					{
						lastmgridList=0;
					}
					else
					{
							lastmgridList=document.getElementById('prjmgrid'+1).value;
					}
					if(document.getElementById('prjmgremailid'+1).value=='')
					{
						lastmgremailList='';
					}
					else
					{
							lastmgremailList=document.getElementById('prjmgremailid'+1).value;
					}
				}
				else
				{
					alert('Please Enter Value in Reassign To PM for OrderNo :' + document.forms[0].chkSelectOrderNo.value );
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
		    		selectpm++;
		    		if(document.getElementById('bbPrimaryPMIDAssigned_'+(i+1)).value!='')
		    		{
		    			if(orderNoList == '')
		    				orderNoList = orderNoList+document.forms[0].chkSelectOrderNo[i].value;
		    		 	else
		    				orderNoList = orderNoList+',' +document.forms[0].chkSelectOrderNo[i].value;
		    			
		    			if(reassignpmidList == '')
		    			{
		    				reassignpmidList = reassignpmidList + document.getElementById('bbPrimaryPMIDAssigned_'+(i+1)).value;
		    				actmgridlist = actmgridlist+document.getElementById('actmgremailid'+(i+1)).value;
				    		if(document.getElementById('prjmgrid'+(i+1)).value=='')
							{
								lastmgridList=lastmgridList +  0;
							}
							else
							{
									lastmgridList=lastmgridList  + document.getElementById('prjmgrid'+(i+1)).value;
							}
							lastmgremailList=lastmgremailList + document.getElementById('prjmgremailid'+(i+1)).value;
							reassignpmnameList =reassignpmnameList+ document.getElementById('projectManagerAssigned_'+(i+1)).value;
							
		    			}
		    			else
		    			{
		    				reassignpmidList = reassignpmidList +',' + document.getElementById('bbPrimaryPMIDAssigned_'+(i+1)).value;
		    				actmgridlist = actmgridlist+','+document.getElementById('actmgremailid'+(i+1)).value;
							if(document.getElementById('prjmgrid'+(i+1)).value=='')
							{
								lastmgridList=lastmgridList + ',' + 0;
							}
							else
							{
									lastmgridList=lastmgridList + ',' + document.getElementById('prjmgrid'+(i+1)).value;
							}
							lastmgremailList=lastmgremailList+ ',' + document.getElementById('prjmgremailid'+(i+1)).value;
							reassignpmnameList =reassignpmnameList+ ','+ document.getElementById('projectManagerAssigned_'+(i+1)).value;
		    			}
		    	 }
		    	 else
		    	 {
		    	 	alert('Please Enter Value in Reassign To PM for OrderNo : ' + document.forms[0].chkSelectOrderNo[i].value )
		    	 	return false;
		    	 	
		    	 } 
		     }
		  }
		  if(selectpm==0)
		  {
		  	alert('Please Select Order for Updation');
		  	return false;
		  }
		  
		}
	  	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    var retVal = jsonrpc.processes.reassignPM(reassignpmidList,reassignpmnameList,orderNoList,id,lastmgridList,lastmgremailList,actmgridlist);
	    alert(retVal);
	    myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=getOrderListForPendingWithPM&orderNo="+orderNo+"&crmAccountNo="+crmAccountNo+"&projectManager="+projectManager+"&Employeeid="+id;
		myForm.submit();
	    
}

path='<%=request.getContextPath()%>';
</script>
<body onload="getAutoSuggest();AttachCSStoAutoSuggestButton()" style="vertical-align: top;">
<html:form action="/NewOrderAction"  enctype="text/plain" styleId="searchForm" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
		
		
		
	
	
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%" style="vertical-align: top">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>REASSIGN ORDER TO ANOTHER PM</span> </div>
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
						<td align="right" style="font-size:9px">PM:</td>
						<td align="left">
							<input type="text" styleClass="inputBorder2" style="width:150px;float:left;" name="projectManager" ctrltype='dropdown' retval='AUTOSUGGESTPM' id="projectManager" ><a id="PMAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownPM()">Show</a>								
								<input type="hidden" name="bbPrimaryPMID"  id="bbPrimaryPMID" isrequired="1">
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
							<input type="hidden" name="clearhideen"  id="clearhideen" value="0" isrequired="1">
						</td>
						<td><input type="hidden" styleClass="inputBorder2" style="width:200px;float:left;" id="projectmgrid" name="projectmgrid" value="0"></td>
						
						<td width="30px"/>
						<td align="right" width="70px"><div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div></td>
						
					</tr>
					<tr height="5px">
					 <td colspan="15" align="center"><B><font color="red">Orders containing PM in the workflow and not Approved by PM Will be Searched</font></td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Order List</b></legend>
			<br/>
			
			<table class="tab2" style="overflow:auto;" width="99%" border="1" align="center" id='tblOrderList' style="vertical-align: top">
				<tr>
					<td align="center" style="font-size:9px"></td>	
					<td align="center" style="font-size:9px"><b>
						
							Order No
					</b></td>
					<td align="center" style="font-size:9px" width="300px"><b>
						
							Initial PM
					</b></td>
					<td align="center" style="font-size:9px" width="300px"><b>
						
							Last Changed PM
					</b></td>

					<td align="center" style="font-size:9px" width="285"><b>Reassign To PM</b></td>
					<!-- added by manisha start -->
					<td align="center" style="font-size:9px" width="426"><b>Last Changed By</b></td>
					<!-- added by manisha end -->
				</tr>
				
				<logic:present name="orderList" scope="request">
					<logic:notEmpty  name="orderList" scope="request"> 					
						<logic:iterate id="row" name="orderList" indexId="recordId">
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
										
							<tr id="row<%=count %>" class="<%=classType%>">
								<td width="1"><input name="chkSelectOrderNo" id="chkSelectOrderNo" type="checkbox" value="<bean:write  name="row" property="orderNo"/>">
								<td align="center" width="300px" style="font-size:9px"><bean:write  name="row" property="orderNo"/></td>
								<td align="center" width="300px" style="font-size:11px"><bean:write  name="row" property="initial_pm"/></td>
								<td align="center" style="font-size:11px"><bean:write  name="row" property="last_pm"/></td>
								<td><input type="text" styleClass="inputBorder2" style="width:200px;float:left;" name="projectManagerAssigned" counterval='<%=count%>' ctrltype='dropdown' retval='AUTOSUGGESTPMASSIGNED' id="projectManagerAssigned_<%=count%>" ><a id="PMAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownPMAssigned(<%=count%>)">Show</a>								
									<input type="hidden" name="bbPrimaryPMAssigned"  id="bbPrimaryPMIDAssigned_<%=count%>" isrequired="1">
								</td>
								<!-- added by manisha start -->
								<td align="center" style="font-size:11px"><bean:write  name="row" property="last_changed_by"/>
								
								<input type="hidden" name="actmgremailid" id="actmgremailid<%=count%>"  value="<bean:write  name="row" property="acmgrEmail"/>">
								<input name="prjmgremailid" id="prjmgremailid<%=count%>" type="hidden" value="<bean:write  name="row" property="projectmgremail"/>">
								<input name="prjmgrid" id="prjmgrid<%=count%>" type="hidden" value="<bean:write  name="row" property="projectManager"/>">
								</td>
								
								<!-- added by manisha end -->
							</tr>
							
						</logic:iterate>
						<tr>
								<td colspan="17" align="center">
									<input type="button"	name="SubmitReassignRequest" value="Reassign" onClick="submitReassignRequest()">
								</td>	
						</tr>
					</logic:notEmpty>
					<logic:empty  name="orderList">
		   			<tr align="center" >
			 			<td colspan="17" align="center"><B><font color="red">No Record Found</font></td>
		   			</tr>
				</logic:empty>	
		</logic:present>				
		</table>
		</fieldset>
	</html:form>
</body>
</html>
