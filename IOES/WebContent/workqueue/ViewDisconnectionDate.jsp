<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.forms.ViewOrderFormBean"%>
<%@page import="com.ibm.ioes.beans.ViewOrderDto"%>
<%@page import="com.ibm.ioes.utilities.Utility"%>

<html:html>

<head>
<title>ViewDisconnectionDate</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="gifs/treeview.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

   function fnSaveDate()
    {
    	 var type =(<%=request.getParameter("Type")%>); 
    	 if(type == 1)   // i.e Charges
    	 {
    	 	fnSaveDateForCharges();
    	 }
    	 else  // for components
    	 {
    	 	fnSaveDateForComponents();
    	 }
    }
   function fnSaveDateForCharges()
    {
    	 var chargeid=(<%=request.getParameter("ID")%>);   
         var discDate=document.getElementById('disconnectionDate').value;
          var test;
          var callerWindowObj = dialogArguments;
      
         
         try
	          {
	
					jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
					var jsData =			
				    {
					chargeInfoId:chargeid
					 
					  
					};
					test = jsonrpc.processes.fnGetDisconnectionDateData(jsData);
					if(test!=null)
					
					  		{
					  
							     for(i=0;i<test.list.length;i++)
					                    
					                 {
					                 
					                    if(test.list[i].enddatelogic=='TD')
					                    
					                      {
					                      
					                             
					                        var a=dateCompare(discDate,test.list[i].billing_Active_date);
					                         if(a>=0)
					                          
					                           {
					                           
					                              var jsData =			
															    {
																chargeInfoId:chargeid,
																disconnectiondate:discDate
																 
																  
																};
																jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
																result = jsonrpc.processes.fnInsertDisconnectionDate(jsData);
																if(result!=null)
																 {
																		    for(i=0;i<result.list.length;i++)
																		     {
							                    
							              
																		  
																		            var msg=result.list[i].msgOut;
																		            alert(msg);
																		            return false;
																		            window.close();
																		            
																		     }                         
					                            
					                                                  }
					                                              }
					                                              
					                                                
					                           
							                    else
							                    
							                      {
							                      
							                          alert("Disconnection Date should be Greater Than equal to Active Date :" + test.list[i].billing_Active_date);
							                          return false;
							                    
							                      }       
					                           
					                      
					                      }
					                      
					                    if(test.list[i].enddatelogic=='BTD')
					                    
					                      { 
					                      
					                       
					                      
					                                  var a=dateCompare(discDate,test.list[i].billing_Active_date);
					                                  var b=dateCompare(discDate,test.list[i].billing_End_Date);
					                                  
					                                   if(a>=0 && b<=0)
					                                   {
					                        
					                               var jsData =			
															    {
																chargeInfoId:chargeid,
																disconnectiondate:discDate
																 
																  
																};
																jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
																result = jsonrpc.processes.fnInsertDisconnectionDate(jsData);
																if(result!=null)
																 {
																     
																  
																            var msg=result.list[0].msgOut;
																            alert(msg);
																            //return false;
																            window.close();
																                              
					                            
					                                                  }
					                           
					                           }
					                           
					                        else
					                        
					                        {
					                              alert("Disconnection Date should be Greater Than equal to Active Date: " + test.list[i].billing_Active_date + "and less than to Inactive date: " + test.list[i].billing_End_Date);
							                          return false;
							                    
					                        
					                        
					                        }   
					                      
					                 
					                 }
					                    
					   
					   
					  } 
					
					
					
               }
               
          }     
               
               catch(e)
	           {
						alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
						showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
						throw e;
			    }
    
    }


function fnSaveDateForComponents()
{
			
			var componentid=(<%=request.getParameter("ID")%>);   
         	var discDate=document.getElementById('disconnectionDate').value;
         	var startEndDateList;
          	var callerWindowObj = dialogArguments;
      		var compStartLogic,compEndLogic;
         
         try
	          {
	
					jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
					startEndDateList = jsonrpc.processes.fnGetValidationDataForComponents(componentid);
					if(startEndDateList!=null && startEndDateList.length !=0)					
					 {      					 	
					 			var a=dateCompare(discDate,startEndDateList.list[0]);//start date comparing
					 			var var_endDate=startEndDateList.list[1];
					            //
					  			if(var_endDate==null){
					  				if(a>=0)
					             	{
					               		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
										result = jsonrpc.processes.fnInsertDisconnectionDateForComponent(discDate,componentid);
										
								if(result!=null)
								 {
								    alert(result);
						            window.close();
                                  }
                                  else
                                  {
                                  	alert("Some Error has occured. Please re-try.");
                                  	 return false;
                                  }
					            } else
							                    
			                      {
			                      
			                          alert("Disconnection Date should be Greater Than equal to Component Active Date :" + startEndDateList.list[0]);
			                          return false;
			                      	} 
					  			}else{					  				
						  			var b=dateCompare(discDate,startEndDateList.list[1]);//end date comparing						  			
					  				if(a>=0 && b<=0)
					             	{
					               		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
										result = jsonrpc.processes.fnInsertDisconnectionDateForComponent(discDate,componentid);
										
										if(result!=null)
								 		{
								    		alert(result);
						            		window.close();
                                  		}
                                  		else
                                  		{
                                  			alert("Some Error has occured. Please re-try.");
                                  	 		return false;
                                  		}
					            	}else
			                      	{
			                          alert("Disconnection Date should be between Component active Date: " + startEndDateList.list[0]+" and Component Initial Inactive Date: "+startEndDateList.list[1]);
			                          return false;
			                      	} 
			                      }       
						}
          	}     
               
            catch(e)
         	{
				alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
				showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
				throw e;
    		}
 
}



</script>
</head>


<body>
<html:form action="/viewOrderAction" styleId="frmdisconnectionDatePage" method="post">
<fieldset class="border1">
<legend> <b>Disconnection Date</b></legend>
<!-- start [003] -->
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center">Disconnection Date</td>
			<td align="center">
			<input type="text" size="12" maxlength="12" isRequired="0" name="disconnectionDate"  id="disconnectionDate" class="inputBorder4"/>
								<font size="1">
													<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" style="vertical-align: bottom;" onclick="javascript:show_calendar(frmdisconnectionDatePage.disconnectionDate);">
									</font></td>
	</tr>
	
	<tr>
	
	<td>
	  						<div class="searchBg"><a href id="fnBillingTrigger4" onClick="fnSaveDate()">Save Date</a></div>
	
	</td>
	</tr>
	
	</table>	  
	
</html:form>

</body>

</html:html>