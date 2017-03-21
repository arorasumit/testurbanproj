<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]    SAURABH SINGH  16-Feb      00-05422        Creating and validating Customer PO Date No.-->
<!--[002]    ROHIT VERMA    10-MAR      00-05422        Demo Days in PO Details-->
<!--[077]    ASHUTOSH SINGH  01-DEC-11  00-05422        Changes in PO AMOUNT(Decimal validation) -->
<!--[078]    Raveendra        30-02-2015                Disable Total PO Amount for PM and SED ROLE -->
<!--[079]    Priya Gupta    19-06-21015       Added ld clause column                                                     -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<!-- Start [078] -->
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%
HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

%>
<!-- End [078] -->
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>



<table border="0" cellpadding="0" cellspacing="0"  width="93%"> 
	<tr>
		<td valign="top" width="100%">
			<fieldset class="border1">
				<legend > <b>PO Details</b> </legend>				
				
				<table border="1" cellspacing="0" cellpadding="0"  width="100%" id='selectPOtable'>
				   <tr style="font-weight: bold;">
			              <td width="100%">Select All
					       
					        <input onmouseover="getTip(value)" onmouseout="UnTip()" type="checkbox" name="SelectAllPOChck" id="SelectAllPOChck" onclick="javascript:fnCheckPOAll();"/>
					         </td>
			              </tr>
			    </table>	
			    
			    <!-- Start Scrolling and Freezing of POTable -->
				<table border="0" cellpadding="0" cellspacing="0" class='main'>       
					<tr>
						<td class='tablefrozencolumn'>
	                    <div id='divroot' class='root'>
	                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='root'>
	                            <tr>
	                                <td class='inner frozencol colwidth head1'>
	                                    
	                                </td>
	                            </tr>
	                        </table>
	                    </div>
	                    <div id='divfrozen' class='frozen'>
	                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='frozen'>
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
	                		 	 	 	<td class='inner col1 head1'>
                                    		Select
                                		</td>
                                		<td class='inner col4 head1'>
                                			Customer PO Detail Number
                                		</td>
                                		<td class='inner col3 head1'>
                                			Customer PO Date
                                			(DD/MM/YYYY)
                                		</td>
                                		<td class='inner col3 head1'>
                                			PO ID
                                		</td>
                                		<!--<td class='inner col3 head1'>
                                			Entered PO Date 
                                			(DD/MM/YYYY)-->
                                		</td>
                                		<td class='inner col1 head1'>
                                			Default
                                		</td>
                                		<td class='inner col5 head1'>
                                			Legal Entity
                                		</td>
                                		<td class='inner col4 head1'>
                                			Total PO Amount
                                		</td>
                                		<td class='inner col3 head1'>
                                			Contract Start Date 
                                			(DD/MM/YYYY)
                                		</td>
                                		<td class='inner col3 head1'>
                                			Contract End Date
                                		</td>
                                		<td class='inner col2 head1'>
                                			Period In Months
                                		</td>
                                		<td class='inner col4 head1'>
                                			PO Recieve Date 
                                			(DD/MM/YYYY)
                                		</td>
                                		<td class='inner col4 head1'>
                                			Issued By
                                		</td>
                                		<td class='inner col4 head1'>
                                			PO Remarks
                                		</td>
                                		<td class='inner col4 head1'>
                                			Email Id
                                		</td>
                                		<td class='inner col4 head1'>
                                			Demo Contract Period
                                			(Days)
                                		</td>
                                		<!-- [079] start -->
                                		 <td class='inner col4 head1'>
                                			Ld Clause
                                		</td>  
                                		<!-- [079] end --> 
	                		 	 	 </tr>  
	                		 	</table>
	                		 </div>                		 	                		
	                		 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	                		 	<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablePO'>
	                		 	 	<%int count=1; %>		
									<tr style="display: none">
	                		 	 	 	<td class='inner col1'></td>
                                		<td class='inner col4'></td>
                                		<td class='inner col3'></td>                                			                                		
                                		<td class='inner col3'></td>                                			                                		
                                		<!--<td class='inner col3'></td>-->			                                		
                                		<td class='inner col1'></td>                                			                                		
                                		<td class='inner col5'></td>                                			                                		
                                		<td class='inner col4'></td>                                			                                		
                                		<td class='inner col3'></td>                                			                                		
                                		<td class='inner col3'></td>                                			                                		
                                		<td class='inner col2'></td>                                			                                		
                                		<td class='inner col4'></td>                                			                                		
                                		<td class='inner col4'></td>                                			                                		
                                		<td class='inner col4'></td>                                			                                		
                                		<td class='inner col4'></td>                                			                                		
                                		<td class='inner col4 '></td> 
                                		<!-- [079] -->
                                	    <td class='inner col4 '></td>               
                                	</tr> 	                		 	 	              		 	 	         		 	 
									<logic:present name="listPoDetails" scope="request">
		                      			<logic:notEmpty name="listPoDetails" >
		                      				<html:hidden property="poUpdateType" value="2"/>
		                      				<html:hidden property="entity" />		                     		                      
		                      			<logic:iterate id="poDetailID" name="listPoDetails" indexId="recordId" >
		                      			<%  count=((recordId.intValue())+1);%>
								   		<tr id="row<%=count %>" class='content1' onscroll='reposHead(this);'>
								  		<td class='inner col1 toprow'>
								   			<logic:equal name="poDetailID" property="noofuses" value="0">
												<input onmouseover="getTip(value)" onmouseout="UnTip()" name="chkSelectPODetail" id="chkSelectPODetail" type="checkbox" value="<%=count-1 %>" onclick="unCheckedPOMaster();" >
											</logic:equal>
											<logic:notEqual  name="poDetailID" property="noofuses" value="0">
											 	<input onmouseover="getTip(value)" onmouseout="UnTip()" name="chkSelectPODetail" id="chkSelectPODetail" type="checkbox" value="<%=count-1 %>" disabled="true" onclick="unCheckedPOMaster();">
											</logic:notEqual>
										</td>
									 <!-- [002]	Start -->
										<td class='inner col4'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" width="50px" name="custPoDetailNo" onfocus="populatePODemoContractPeriod(<%=count-1 %>)" isRequired="0" maxlength="100" id="custPoDetailNo<%=count-1 %>" class="inputBorder1" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'CustomerPODetailNo')};" value="<bean:write name="poDetailID" property="custPoDetailNo"/>"/>
										</td>
								     <!-- [002]	End -->
								   <!-- [001]	Start -->
								   <td class='inner col4'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="12" maxlength="12" name="custPoDate" isRequired="0" width="50px" id="custPoDate<%=count-1 %>"  value="<bean:write name="poDetailID" property="custPoDate"/>" readonly="true" onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].custPoDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
								  </td >
								    <!-- [001]	End -->
								  <td class='inner col3'>
								   	<input onmouseover="getTip(value)" onmouseout="UnTip()" type="hidden" name="contactId" id="contactId<%=count-1 %>" class="inputBorder1" value="<bean:write name="poDetailID" property="contactId"/>"  />
								   	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" name="poDetailNo" class="inputDisabled" size="12" id="poDetailNo<%=count-1 %>"  value="<bean:write name="poDetailID" property="poDetailNo"/>" readonly="true"/>
								  	<input type="hidden" isRequired="0" name="poDate"  id="poDate<%=count-1 %>"  value="<bean:write name="poDetailID" property="poDate"/>" />
								  </td>
								  <!-- <td class='inner col3'>
								   	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="12" maxlength="12" isRequired="0" name="poDate"  id="poDate<%=count-1 %>"  value="<bean:write name="poDetailID" property="poDate"/>" readonly="true" onblur="if(this.value.length > 0){return checkdate(this)}"/>&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].poDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
								  </td>		 -->						    
								  <logic:equal value="1" name="poDetailID"  property="defaultPO">
								  	<td class='inner col1'>
								  		<input onmouseover="getTip(value)" onmouseout="UnTip()" type="radio" isRequired="0" name="defaultPO" size='2' id="defaultPO<%=count-1 %>" width="50px" checked="checked" value="<%=count-1 %>" />
								  		 <input onmouseover="getTip(value)" onmouseout="UnTip()" type="hidden" name="isdefault" isRequired="0" id = "isdefault<%=count-1 %>" value="1"/>
								  		</td>
								  </logic:equal>
								  
								  <logic:notEqual  value="1" name="poDetailID"  property="defaultPO">
								     	<td class='inner col1'>
								     	<input onmouseover="getTip(value)" onmouseout="UnTip()" type="radio" isRequired="0" width="50px" name="defaultPO" id="defaultPO<%=count-1 %>" />
								     	 <input onmouseover="getTip(value)" onmouseout="UnTip()" type="hidden" name="isdefault" isRequired="0" id = "isdefault<%=count-1 %>" value="0"/>
								     	</td>
								  </logic:notEqual>
								  <td class='inner col5' nowrap="nowrap">
								     <input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)"  onmouseout="UnTip()" type="text" name="entity" id="entity<%=count-1 %>" class="inputBorder1"  isRequired="0" width="100px" value="<bean:write name="poDetailID" property="entity" />" style="width:225px; float:left;" readonly="readonly" >
								     <!-- Commented 
								     <div class="searchBg1"><a href="#" onclick="return popEntitySelection(<%=count -1%>)">...</a></div>
								      -->
								     <input onmouseover="getTip(value)" onmouseout="UnTip()" type="hidden" name="entityId" isRequired="0" id = "entityId<%=count-1 %>"  value="<bean:write name="poDetailID" property="entityID"/>" />
								  </td>
								  <td class='inner col4'>
								 <!--   	
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="20"  width="50px" name="totalPoAmt" isRequired="0" maxlength="15" id="totalPoAmt<%=count -1%>" onblur="if(this.value.length > 0){return checknumber(this)}" value="<bean:write name="poDetailID" property="totalPoAmt"/>" />
								 -->
								 	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="20"  width="50px" name="totalPoAmt" isRequired="0" maxlength="15" id="totalPoAmt<%=count -1%>" onblur="if(this.value.length > 0){return checknumber(this)}" value="<bean:write name="poDetailID" property="totalPoAmt"/>" <% if((AppConstants.PM_ROLE).equals(objUserDto.getUserRoleId())||(AppConstants.SED_ROLE).equals(objUserDto.getUserRoleId())){ %> disabled="disabled" <%} %> />
								  </td>
								  <td class='inner col3'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="12" maxlength="12" name="contractStartDate" isRequired="0" width="50px" id="contractStartDate<%=count-1 %>"  value="<bean:write name="poDetailID" property="contractStartDate"/>" readonly="true" onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].contractStartDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
								  </td >
								  <td class='inner col3'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputDisabled"  isRequired="0" name="contractEndDate" size="12" id="contractEndDate<%=count-1 %>"  value="<bean:write name="poDetailID" property="contractEndDate"/>" readonly="true"/>
								  </td>
								  <td class='inner col2'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  isRequired="0" style="width:50px;" maxlength="5" name="periodsInMonths" id="periodsInMonths<%=count-1%>" class="inputBorder4" onblur="if( trim(this.value).length>0){return checkdigits(this)};" value="<bean:write name="poDetailID" property="periodsInMonths"/>" />
								  </td>
								  <td class='inner col4'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="12" maxlength="12" name="poReceiveDate" isRequired="0" width="50px" id="poReceiveDate<%=count-1 %>"  value="<bean:write name="poDetailID" property="poReceiveDate"/>" readonly="true" onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].poReceiveDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
								  </td >
								  <td class='inner col4'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  name="poIssueBy" maxlength="100" width="50px" id="poIssueBy<%=count-1%>" class="inputBorder1" value="<bean:write name="poDetailID" property="poIssueBy" />" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,100)) { return ValidateTextField(this,path,'IssuedBy')}};"/>
								  </td>
								  <td class='inner col4'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  name="poRemarks" maxlength="1000" width="50px" id="poRemarks<%=count-1 %>" class="inputBorder1" value="<bean:write name="poDetailID" property="poRemarks"/>" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,1000)) { return ValidateTextField(this,path,'Remarks')}};"/>
								  </td>
								  <td class='inner col4'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  name="poEmailId"  maxlength="50" width="50px" id="poEmailId<%=count-1 %>" class="inputBorder1" value="<bean:write name="poDetailID" property="poEmailId"/>" onblur="if( trim(this.value).length>0){ return emailValidate(this)};"/>
								  </td>
								  <td class='inner col4'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  name="poDemoContractPeriod" isRequired="0"  width="50px" maxlength="5" readonly="readonly" id="poDemoContractPeriod<%=count-1%>" class="inputBorder1" value="<bean:write name="poDetailID" property="poDemoContractPeriod"/>" onfocus="populatePODemoContractPeriod(<%=count-1 %>)" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/>
								  </td>
								  <!--  [079] starts-->
								  <td class='inner col4'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" width="50px" name="ldClause" isRequired="0" maxlength="255" id="ldClause<%=count -1%>" class="inputBorder1" onblur="if(trim(this.value).length>0){return ValidateTextField(this,path,'Ld Clause')};" value="<bean:write name="poDetailID" property="ldClause"/>" />
								  </td>
								  <!--  [079] end --> 
								  </tr>
				              </logic:iterate>
				              </logic:notEmpty>
				             </logic:present>
				             <logic:empty name="listPoDetails" >
				                    <html:hidden property="poUpdateType" value="1"/>
									<tr id="row<%=count %>">
										<td class='inner col1 toprow'>										
											<input onmouseover="getTip(value)" onmouseout="UnTip()" name="chkSelectPODetail" id="chkSelectPODetail" type="checkbox" value="<%=count-1 %>" enabled="true" onclick="unCheckedPOMaster();">																																									
										</td>
										<!-- [002] Start -->
										<td class='inner col4'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  onfocus="populatePODemoContractPeriod(<%=count-1 %>)" isRequired="0" width="50px" maxlength="100" name="custPoDetailNo" id="custPoDetailNo<%=count-1%>" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'CustomerPODetailNo')};">
										</td>
										<!-- [002] End -->
										<!-- [001]	Start -->
								   		<td class='inner col4'>
								  	<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="12" maxlength="12" name="custPoDate" isRequired="0" width="50px" id="custPoDate<%=count-1 %>"  value="<%=AppUtility.getTodayDate() %>" readonly="true" onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].custPoDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
								  			</td >
								    	</td>
								    	<!-- [001]	End -->
										<td class='inner col3'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" type="hidden" class="inputDisabled" id="contactId<%=count-1 %>" name="contactId" value="<%=count-1%>" >
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  class="inputDisabled" Disp="" size="12" id="poDetailNo<%=count-1 %>" name="poDetailNo" readonly = "true">
											<input type="hidden" Disp="" name="poDate" id="poDate<%=count-1 %>" value="<%=AppUtility.getTodayDate() %>" />
										</td>
										<!-- <td class='inner col3'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  size="12" maxlength="12"  Disp="" width="50px"  name="poDate" id="poDate<%=count-1 %>" value="<%=AppUtility.getTodayDate() %>" readonly="true" onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].poDate<%=count-1%>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
											</font>	
										</td>	 -->								
								     	<td class='inner col1'>
								     		<input onmouseover="getTip(value)" onmouseout="UnTip()" type="radio"  name="defaultPO"  size='5' isRequired="0"  width="50px" id="defaultPO<%=count-1 %>" />
								     		<input onmouseover="getTip(value)" onmouseout="UnTip()" type="hidden" name="isdefault" isRequired="0" id = "isdefault<%=count-1 %>" value="0"/>
								     	</td>								    
										<td class='inner col5' nowrap="nowrap">
											<select id="entityId(<%=count-1 %>)" style="width:225px; float:left;" isRequired="0" width="100px" name="entityId" onmouseover='getTip_DD(this,this.selectedIndex,this.name)'>
									  			<option value="0" >Select Legal Entity</option>									  			
									  		</select>									    
										</td>									  
										<td class='inner col4'>
										
										<!-- Start [078] -->
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  isRequired="0" width="50px" size="20" maxlength="15" name="totalPoAmt" id="totalPoAmt<%=count-1%>"  onblur="if(this.value.length > 0){return checknumber(this)}" <% if((AppConstants.PM_ROLE).equals(objUserDto.getUserRoleId())||(AppConstants.SED_ROLE).equals(objUserDto.getUserRoleId())){ %> disabled="disabled" <%} %>>
									    <!-- End [078] -->
										</td>
										<td class='inner col3'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="12" maxlength="12" isRequired="0" width="50px" name="contractStartDate" id="contractStartDate<%=count-1%>" readonly="true" onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].contractStartDate<%=count-1%>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
											</font>
										</td>
										<td class='inner col3'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="12" maxlength="12" class="inputDisabled" isRequired="0" width="50px" name="contractEndDate" id="contractEndDate<%=count-1%>" readonly="true">
										</td>
										<td class='inner col2'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  isRequired="0" style="width:50px;" maxlength="5" name="periodsInMonths" id="periodsInMonths<%=count-1%>" onblur="if( trim(this.value).length>0){return checkdigits(this)};">
										</td>
										<td class='inner col4'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text" size="12" maxlength="12" isRequired="0" style="width:90px;" name="poReceiveDate" id="poReceiveDate<%=count-1%>" readonly="true" onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].poReceiveDate<%=count-1%>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
											</font>
										</td>
										<td class='inner col4'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"   width="50px" maxlength="100" name="poIssueBy" id="poIssueBy<%=count-1%>" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,100)) { return ValidateTextField(this,path,'IssuedBy')}};">
										</td>
										<td class='inner col4'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"   width="50px" maxlength="1000" name="poRemarks" id="poRemarks<%=count-1%>" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,1000)) { return ValidateTextField(this,path,'Remarks')}};">
										</td>
										<td class='inner col4'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"   width="50px" maxlength="50" name="poEmailId" id="poEmailId<%=count-1%>" onblur="if( trim(this.value).length>0){ return emailValidate(this)};">
										</td>									
										<td class='inner col4'>
											<input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" type="text"   onfocus="populatePODemoContractPeriod(<%=count-1 %>)" onselect="populatePODemoContractPeriod(<%=count-1 %>)" width="50px" maxlength="5" readonly="readonly" name="poDemoContractPeriod" id="poDemoContractPeriod<%=count-1%>" onblur="if( trim(this.value).length>0){ return checkdigits(this)};">											
										</td>
									<!--  [079] starts-->
								         <td class='inner col4'>
								  	        <input onmouseover="getTip(value)" onmouseout="UnTip()" onmouseover="getTip(value)" onmouseout="UnTip()" isRequired="0" type="text" width="50px" maxlength="255" name="ldClause"  id="ldClause<%=count -1%>" class="inputBorder1" onblur="if(trim(this.value).length>0){return ValidateTextField(this,path,'Ld Clause')};"/>
								        </td> 
								    <!--  [079] end -->
									</tr>
								</logic:empty>	
							</table>
						</div>
					</td>
	                	<td class='tableverticalscroll' rowspan="2">
                    		<div class='vertical-scroll' onscroll='reposVertical(this);'>
                        		<div>
                        		</div>
                    		</div>
                    		<div class='ff-fill'>
                    		</div>
                		</td>
					</tr>
					<tr>
	                	<td colspan="3">
	                    	<div class='horizontal-scroll' onscroll='reposHorizontal(this);'>
	                        	<div>
	                        	</div>
	                    	</div>
	                	</td>
            		</tr>
				</table>
				<!-- End Scrolling and Freezing of POTable -->
				
				<table border="1" cellspacing="0" cellpadding="0" align="left" width="100%" id='selectPOtable'>	
					<tr>
						<td colspan="8" align="center">
						    <input onmouseover="getTip(value)" onmouseout="UnTip()" type="button" name="btnAddPODetail" value="Add More PO" onClick="AddPO()">
						    <input onmouseover="getTip(value)" onmouseout="UnTip()" type="button" name="btnDeletePoDetail" value="Delete PO Detail" onClick="DeletePO()">
							<input onmouseover="getTip(value)" onmouseout="UnTip()" type="hidden" value="<%=count%>" name="hdnPOCount">
							<input onmouseover="getTip(value)" onmouseout="UnTip()" type="hidden" value = "<bean:write name = "newOrderBean" property = "accountID"/>"name="hdnPOAccount">
						</td>
					</tr>
				</table>
		</fieldset>
		</td>
	</tr>	
</table>

