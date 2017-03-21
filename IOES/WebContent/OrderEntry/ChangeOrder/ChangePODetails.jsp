<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]    SAURABH SINGH  16-Feb      00-05422        Creating and validating Customer PO Date No.-->
<!--[077]    ASHUTOSH SINGH  01-DEC-11  00-05422        Changes in PO AMOUNT(Decimal validation) -->
<!--[078]    Raveendra        30-02-2015                Disable Total PO Amount for PM and SED ROLE -->
<!--[079]    Priya Gupta     24-06-2015              Added new column ldclause                                               -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<!--Start [078]-->
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>

<%

HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

%>

<!--End [078]-->
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>




<table border="0" cellpadding="0" cellspacing="0"  width="93%"> 
	<tr>
		<td valign="top" width="100%">
			<fieldset class="border1">
				<legend > <b>PO Details</b> </legend>				
				
				<table border="1" cellspacing="0" cellpadding="0"  width="100%" id='selectPOtable'>
				   <tr style="font-weight: bold;">
			              <td width="100%">Select All
					       
					        <input type="checkbox" name="SelectAllPOChck" id="SelectAllPOChck" onclick="javascript:fnCheckPOAll();"/>
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
                                		<!-- <td class='inner col3 head1'>
                                			Entered PO Date 
                                			(DD/MM/YYYY)
                                		</td> -->
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
                                		<!-- [079] starts -->
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
                                		<!--  <td class='inner col3'></td>-->		
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
                                		<td class='inner col4'></td>
                                		<!-- [079] -->
                                	    <td class='inner col4'></td>
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
											<input name="chkSelectPODetail" id="chkSelectPODetail" type="checkbox" value="<%=count-1 %>" onclick="unCheckedPOMaster();">
									</logic:equal>
									<logic:notEqual  name="poDetailID" property="noofuses" value="0">
											 	<input name="chkSelectPODetail" id="chkSelectPODetail" type="checkbox" value="<%=count-1 %>" disabled="true" onclick="unCheckedPOMaster();">
									</logic:notEqual>
									</td>
									<td class='inner col4'>
										<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" width="50px" name="custPoDetailNo" isRequired="0" maxlength="100" id="custPoDetailNo<%=count-1 %>" class="inputBorder1" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'CustomerPODetailNo')};" value="<bean:write name="poDetailID" property="custPoDetailNo"/>"/>
										</td>
								   <!-- [001]	Start -->
								  	<td class='inner col3'>
								    	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  isRequired="0" name="custPoDate"  size=12 id="custPoDate<%=count-1 %>" class="inputBorder4" value="<bean:write name="poDetailID" property="custPoDate"/>" readonly="true" onblur='if(this.value.length > 0){return checkdate(this)};' />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].custPoDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
								    </td>
								    <!-- [001]	End -->
								   <td class='inner col3'>
								   <input type="hidden" name="contactId" id="contactId<%=count-1 %>" class="inputBorder1" value="<bean:write name="poDetailID" property="contactId"/>"  />
								   <input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="12" name="poDetailNo" id="poDetailNo<%=count-1 %>" class="inputDisabled" value="<bean:write name="poDetailID" property="poDetailNo"/>" readonly="true"/>
								   <input type="hidden" isRequired="0" name="poDate"  id="poDate<%=count-1 %>" value="<bean:write name="poDetailID" property="poDate" />" />
								   </td>
								   
								    <!-- <td class='inner col3'>
								    	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" width="100px" size=12 isRequired="0" name="poDate"  id="poDate<%=count-1 %>" class="inputBorder1" value="<bean:write name="poDetailID" property="poDate"/>" readonly="true" onblur='if(this.value.length > 0){return checkdate(this)};'/>&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].poDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
								    </td> -->
								    
								    <logic:equal value="1" name="poDetailID" property="defaultPO">
								     <td class='inner col1'>
								     	<input type="radio" isRequired="0" size=2 name="defaultPO" id="defaultPO<%=count-1 %>"  checked="checked" />
								     	 <input type="hidden" name="isdefault" isRequired="0" id = "isdefault<%=count-1 %>" value="1"/>
								     	</td>
								     </logic:equal>
								     <logic:notEqual  value="1" name="poDetailID"  property="defaultPO">
								     <td class='inner col1'>
								     	<input type="radio" isRequired="0" name="defaultPO" id="defaultPO<%=count-1 %>" width="50px" />
								     	 <input type="hidden" name="isdefault" isRequired="0" id = "isdefault<%=count-1 %>" value="0"/>
								     	</td>
								     </logic:notEqual>
								     <td class='inner col5' nowrap="nowrap">
								     <input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="entity" isRequired="0" id="entity<%=count-1 %>" class="inputBorder1" value="<bean:write name="poDetailID" property="entity"/>" style="width:225px; float:left;" readonly="readonly" >
								     <!-- Commented 
								     <div class="searchBg1"><a href="#" onclick="return popEntitySelection(<%=count-1 %>)">...</a></div>
								     -->
								     <input type="hidden" name="entityId" isRequired="0" id = "entityId<%=count-1 %>"  value="<bean:write name="poDetailID" property="entityID"/>" />
									  </td>
								   <td class='inner col4'>
								   <!--Start [078]
								     	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="20" isRequired="0" name="totalPoAmt" maxlength="15" id="totalPoAmt<%=count-1 %>" class="inputBorder2" onblur="if(this.value.length > 0){return checknumber(this)}" value="<bean:write name="poDetailID" property="totalPoAmt"/>"  /></td>
								    --> 
								     <input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="20" isRequired="0" name="totalPoAmt" maxlength="15" id="totalPoAmt<%=count-1 %>" class="inputBorder2" onblur="if(this.value.length > 0){return checknumber(this)}" value="<bean:write name="poDetailID" property="totalPoAmt"/>" <% if((AppConstants.PM_ROLE).equals(objUserDto.getUserRoleId())||(AppConstants.SED_ROLE).equals(objUserDto.getUserRoleId())){ %> disabled="disabled" <%} %> /></td>
								     <!-- End [078] -->
								     <td class='inner col3'>
								      	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="contractStartDate" size=12 isRequired="0" id="contractStartDate<%=count-1 %>" class="inputBorder2" value="<bean:write name="poDetailID" property="contractStartDate"/>" readonly="true" onblur='if(this.value.length > 0){return checkdate(this)};' />
								    <font size="1">
											<a href="javascript:show_calendar(searchForm[0].contractStartDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
								    </td >
								     <td class='inner col3'>
								    	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" size=12 name="contractEndDate" id="contractEndDate<%=count-1 %>" class="inputDisabled" value="<bean:write name="poDetailID" property="contractEndDate"/>" readonly="true"/>
								     </td>
								     <td class='inner col2'>
								     	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" style="width:50px;" isRequired="0" name="periodsInMonths" maxlength="5" id="periodsInMonths<%=count-1 %>" class="inputBorder1" onblur="if( trim(this.value).length>0){return checkdigits(this)};" value="<bean:write name="poDetailID" property="periodsInMonths"/>" /></td>
								    <td class='inner col4'>
								    	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size=12 isRequired="0" name="poReceiveDate" id="poReceiveDate<%=count-1 %>" class="inputBorder1" value="<bean:write name="poDetailID" property="poReceiveDate"/>" readonly="true" onblur='if(this.value.length > 0){return checkdate(this)};' />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].poReceiveDate<%=count -1%>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
								    </td>
								     <td class='inner col4'>
								     	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0"  name="poIssueBy" maxlength="100" id="poIssueBy<%=count-1 %>" class="inputBorder2" value="<bean:write name="poDetailID" property="poIssueBy" />" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,100)) { return ValidateTextField(this,path,'IssuedBy')}};"/></td>
								     <td class='inner col4'>
								     	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" name="poRemarks" maxlength="1000" id="poRemarks<%=count-1 %>" class="inputBorder2" value="<bean:write name="poDetailID" property="poRemarks"/>" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,1000)) { return ValidateTextField(this,path,'Remarks')}};"/></td>
								     <td class='inner col4'>
								     	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" name="poEmailId" maxlength="50" id="poEmailId<%=count-1%>" class="inputBorder2" value="<bean:write name="poDetailID" property="poEmailId"/>" onblur="if( trim(this.value).length>0){ return emailValidate(this)};"/></td>
								     <td class='inner col4'>
								     	<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" name="poDemoContractPeriod" maxlength="5" readonly="readonly" id="poDemoContractPeriod<%=count-1 %>" class="inputBorder2" value="<bean:write name="poDetailID" property="poDemoContractPeriod"/>" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
								     <!-- [079] starts -->
								     <td class='inner col4'>
										<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" width="50px" name="ldClause" isRequired="0" maxlength="255" id="ldClause<%=count-1 %>" class="inputBorder1" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Ld Clause')};" value="<bean:write name="poDetailID" property="ldClause"/>"/>
									 </td> 
									 <!-- [079] end -->
								  </tr>
				              </logic:iterate>
				              </logic:notEmpty>
				             </logic:present>
				              <logic:empty name="listPoDetails" >
				                    <html:hidden property="poUpdateType" value="1"/>
									<tr id="row<%=count %>">
										<td class='inner col1 toprow'>		
											<input name="chkSelectPODetail" id="chkSelectPODetail" type="checkbox" value="<%=count-1 %>" onclick="unCheckedPOMaster();">
										</td>
											<td class='inner col4'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder1" isRequired="0" width="50px" maxlength="100" name="custPoDetailNo" id="custPoDetailNo<%=count-1%>" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'CustomerPODetailNo')};"></td>
										<!-- [001]	Start -->
								   		<td class='inner col3'>	 
								   			<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  isRequired="0" class="inputBorder1" size=12 name="custPoDate"  id="custPoDate<%=count-1 %>" value="<%=AppUtility.getTodayDate() %>" readonly="true" onblur='if(this.value.length > 0){return checkdate(this)};'/>&nbsp;<font size="1">
											<a href="javascript:show_calendar(searchForm[0].custPoDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
								    	</td>
								    	<!-- [001]	End -->
										<td class='inner col3'>
										<input type="hidden" class='inputBorder1' isRequired="0" id="contactId<%=count-1 %>" name="contactId" value="<%=count-1%>" >
										<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputDisabled" isRequired="0" size="12" id="poDetailNo<%=count-1 %>" name="poDetailNo" readonly = "true">
										<input type="hidden" isRequired="0" name="poDate" id="poDate<%=count-1 %>" value="<%=AppUtility.getTodayDate() %>" >
										</td>
										
										<!-- <td class='inner col3'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" size=12 class="inputBorder1" name="poDate" id="poDate<%=count-1 %>" value="<%=AppUtility.getTodayDate() %>" readonly="true" onblur='if(this.value.length > 0){return checkdate(this)};' />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].poDate<%=count-1 %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
										
										</td> -->
										
								     	<td class='inner col1'>
								     		<input type="radio" size=2 name="defaultPO" isRequired="0" id="defaultPO<%=count-1 %>" />
								     		 <input type="hidden" name="isdefault" isRequired="0" id = "isdefault<%=count-1 %>" value="0"/>
								     		</td>
										
										<td class='inner col5' nowrap="nowrap">
										<select id="entityId(<%=count-1 %>)" style="width:225px; float:left;" isRequired="0" width="100px" name="entityId" onmouseover='getTip_DD(this,this.selectedIndex,this.name)'>
									  			<option value="0" >Select Legal Entity</option>									  			
									  	</select>									    
										</td>
										<td class='inner col4'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder1" isRequired="0" size="20" maxlength="15" name="totalPoAmt" id="totalPoAmt<%=count-1%>" onblur="if(this.value.length > 0){return checknumber(this)}"></td>
											
										<td class='inner col3'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size=12 class="inputBorder2" isRequired="0" name="contractStartDate" id="contractStartDate<%=count-1%>" readonly="true" onblur='if(this.value.length > 0){return checkdate(this)};' />
										<font size="1">
											<a href="javascript:show_calendar(searchForm[0].contractStartDate<%=count-1%>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
										</td>
										<td class='inner col3'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size=12 isRequired="0" class="inputDisabled" name="contractEndDate" id="contractEndDate<%=count-1%>" readonly="true">
										</td>
										<td class='inner col2'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" style="width:50px;" isRequired="0" class="inputBorder1" maxlength="5" name="periodsInMonths" id="periodsInMonths<%=count-1%>" onblur="if( trim(this.value).length>0){return checkdigits(this)};"></td>
										<td class='inner col4'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size=12 isRequired="0" class="inputBorder1" name="poReceiveDate" id="poReceiveDate<%=count-1%>" readonly="true" onblur='if(this.value.length > 0){return checkdate(this)};' />&nbsp;<font size="1"><a href="javascript:show_calendar(searchForm[0].poReceiveDate<%=count-1%>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
										</td>
										<td class='inner col4'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" class="inputBorder2" maxlength="100" name="poIssueBy" id="poIssueBy<%=count-1%>" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,100)) { return ValidateTextField(this,path,'IssuedBy')}};"></td>
										<td class='inner col4'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" class="inputBorder1" maxlength="1000" name="poRemarks" id="poRemarks<%=count-1%>" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,1000)) { return ValidateTextField(this,path,'Remarks')}};"></td>
										<td class='inner col4'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" class="inputBorder1" maxlength="50" name="poEmailId" id="poEmailId<%=count-1%>" onblur="if( trim(this.value).length>0){ return emailValidate(this)};"></td>
										<td class='inner col4'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" isRequired="0" class="inputBorder1" maxlength="5" readonly="readonly" name="poDemoContractPeriod" id="poDemoContractPeriod<%=count-1%>" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></td>
									    <!-- [079] starts -->
								        <td class='inner col4'>
											<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder1" isRequired="0" width="50px" maxlength="255" name="ldClause" id="ldClause<%=count-1%>" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Ld Clause')};"></td>
										<!-- [079]	end -->
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
						    <input type="button" name="btnAddPODetail" value="Add More PO" onClick="AddPO()">
						    <input type="button" name="btnDeletePoDetail" value="Delete PO Detail" onClick="DeletePO()">
							<input type="hidden" value="<%=count%>" name="hdnPOCount">
							<input type="hidden" value = "<bean:write name = "newOrderBean" property = "accountID"/>"name="hdnPOAccount">
						</td>
					</tr>
				</table>
		</fieldset>
		</td>
	</tr>	
</table>


