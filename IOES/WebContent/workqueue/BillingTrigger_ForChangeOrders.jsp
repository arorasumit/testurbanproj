<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Manisha Garg	22-jun-11	00-05422		Select All Functionality-->
<!--[002]	 Manisha Garg	22-jun-11	00-05422	`	Search Logical SI-->
<!--[003]	 Manisha Garg	23-jun-11	00-05422		Search Customer Logical SI-->
<!--[004]	 Manisha Garg	23-jun-11	00-05422		Copy LOC Date-->
<!--[005]	 Manisha Garg	24-jun-11	00-05422		ADD SOME NEW COLUMNS-->
<!--[006]	 Manisha Garg	24-jul-11	00-05422		Changes in Trigger Billing Function for Auto Billing-->
<!--[007]	 Manisha Garg	24-aug-11	00-05422		To Get Details-->
<!--[008]	 Manisha Garg	24-aug-11	00-05422		To Get Component Details-->
<!--[009]	 Manisha Garg	24-aug-11	00-05422		Component Header-->
<!--[010]	 Manisha Garg	24-aug-11	00-05422		Charge Header-->
<!--[011]	 Manisha Garg	24-aug-11	00-05422		Trigger Billing Button-->
<!--[013]  	 Sandeep Aggarwal 28-sep-11                 Loc Rec Date 	-->
<!--[014]  		Lawkush  30-MAR-13  BUG ID TRNG22032013005 fixed	-->
<!--[015]	 Rohit Verma 	30-MAR-13	IT-09112  		BUG ID TRNG22032013005 fixed	-->
<!--[016]	 Rohit Verma 	05-SEP-13	IT-09112  		PO Expiry Date and Billing Trigger Date	-->
<!--[017]	 Gunjan Singla	22-Jan-15  20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection -->
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
<%@page import="com.ibm.ioes.beans.ViewOrderDto"%>
<%@page import="com.ibm.ioes.utilities.Utility"%>
<%@page import="com.ibm.ioes.beans.BillingTriggerValidation"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.forms.ViewOrderFormBean"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>:: Billing Trigger Details</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fxUtility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/enableDisableOrderEntryUtility.js"></script>
<!-- 001 START --><script type="text/javascript" src="${pageContext.request.contextPath}/js/BillingTrigger.js"></script><!-- 001 END -->

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
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
<script language="javascript" type="text/javascript">
window.name="BILLINGTRIGGER_FORCHANGE";
<!-- Java Script Code Will Comes Here-->
//004 start

//007 start


//007 end
   var gb_serviceProductID;
	var gb_serviceId;
	var path="<%=request.getContextPath()%>";
	
	<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
	%>
	var PAGE_SIZE_CHARGES=<%=objUserDto.getPageSizeBTCharges()%>;
	
var enableDisableFlag=<%= request.getAttribute("billingTriggerEnableFlag")%>;
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
function fnBillingTrigger3(chiWindow)
{
              // alert(2);
               chiWindow.close();
             
}

function buttonsDisableInViewMode()
{
	var viewMode;
	<% ViewOrderDto modeDto=(ViewOrderDto)request.getAttribute("orderData");
		if(modeDto.getMode()!=null && "view".equals(modeDto.getMode())){%>
		
		document.getElementById("fnBillingTrigger4").disabled=true;
		document.getElementById("saveIcon").disabled=true;
		document.getElementById("copy_loc_date").disabled=true;
		viewMode="viewMode";
	<%}%>
	
	if(enableDisableFlag==0)
	{
		document.getElementById("fnBillingTrigger4").disabled=true;
		document.getElementById("saveIcon").disabled=true;
		document.getElementById("copy_loc_date").disabled=true;
		document.getElementById("copy_loc_No").disabled=true;
	}
	//----------------[Start;Purpose:Disable Billing Trriger Button for CLEP Order;Date:04-Sep-2012]-----------
	if(viewMode!="viewMode"){
		//start :CLEP Enable Disable Order Entry		
			var orderNo=document.frmBillingTrigger.orderNo.value;			
			var stateid=getOrderStateforClep(orderNo,path);					
			orderEntryEnableDisable('BILLING_TRRIGER',stateid, null);
		//end clep
	}
	//----------------[End;Purpose:Disable Billing Trriger Button for CLEP Order;Date:04-Sep-2012]-----------
	getAutoSuggest();	
	AttachCSStoAutoSuggestButton();
}

function savelocdata()
{
    var flag,i,index_lp,strBillingTrigger,dataChanged,locList,checkbox_status;

	flag=0;
	strBillingTrigger="";
	dataChanged="";
	var line_prop=new Array();
	index_lp=0;
	var myList=[];
    var locNo_status;
	var Inp_locDate_Status;
	 var btDoneBy=-1;
	locList={"javaClass": "java.util.ArrayList",
										"list": []
					  					};
	var Inp_locRecDate_Status;			  					
					  					 
	locNo_status="";
	Inp_locDate_Status="";
	var toadd;
	var counter=0;
	checkbox_status="";
	
	
	for(i=1;i<=document.frmBillingTrigger.itemServiceCount.value;i++)
	{
	 
		if(document.frmBillingTrigger("chkService"+i).checked==true)
		{
		     checkbox_status="checked";
		
			ServiceProductId=document.frmBillingTrigger("hdnSID"+i).value;
			LOCNo=document.frmBillingTrigger("LOCno"+i).value;
			LOCdate=document.frmBillingTrigger("LOCdate"+i).value;
			BillingTriggerDate=document.frmBillingTrigger("BTdate"+i).value;
			LocReceiveDate=document.frmBillingTrigger("LocRecDate"+i).value;
			if(ServiceProductId=="")
			{
				ServiceProductId="0";
			}
			
			
		
			
			if(strBillingTrigger=="")
			{
				strBillingTrigger=ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate+"~"+LocReceiveDate+"~"+"abc";
				dataChanged="1";
			}
			else
			{
				strBillingTrigger=strBillingTrigger+"@@"+ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate+"~"+LocReceiveDate+"~"+"abc";
				dataChanged=dataChanged+"@@"+"1";
			}
			flag=1;
			
			
		}
		
	//006 start	
	
		if(document.frmBillingTrigger("isAutoBilling"+i).value==1)
					    
			{
			   
			    
			    if(document.frmBillingTrigger["LOCNO_enableDisableFlag"+i].value=="allow" && document.frmBillingTrigger("LOCno"+i).value!="")
			      {
			        
			          toadd="true";
			          locno_status="edited";
			          
			      }
			      
			      else
			        {
			           locno_status="";
			           
			        }
					    
				if(document.frmBillingTrigger["LOCDATE_enableDisableFlag"+i].value=="allow" && document.frmBillingTrigger("LOCdate"+i).value!="")
			      {
			          toadd="true";
			          Inp_locDate_Status="edited";
			      }
			      
			      else
			      {
			         Inp_locDate_Status="";
			         
			      }
			      
			      
			      if(document.frmBillingTrigger["LRD_enableDisableFlag"+i].value=="allow" && document.frmBillingTrigger("LocRecDate"+i).value!="")
			      {
			          toadd="true";
			          Inp_locRecDate_Status="edited";
			      }
			      
			      else
			      {
			         Inp_locRecDate_Status="";
			         
			      }
			      
			      
			      
			      if(locno_status=="edited" || Inp_locDate_Status=="edited" || Inp_locRecDate_Status=="edited")
			      
			      {
			      //[014]  start
			             var ob={"javaClass": "com.ibm.ioes.beans.ViewOrderDto",
							              serviceProductID:document.frmBillingTrigger("hdnSID"+i).value,
										  locNo:document.frmBillingTrigger("LOCno"+i).value,
										  locDate:document.frmBillingTrigger("LOCdate"+i).value,
										  locRecDate:document.frmBillingTrigger("LocRecDate"+i).value,
										  locNo_Status:locno_status,
										  locDate_Status:Inp_locDate_Status,
										  locRecDate_Status:Inp_locRecDate_Status,
										  btDoneBy:btDoneBy
											
					       		};
			                          myList[counter]=ob;
			                           locList={"javaClass": "java.util.ArrayList",
										"list": myList
					  					};								       
					               counter++;
					  				flag=1;
			      
			      }     
					  	      
			}		       
								  
	}
	
	
					      
					      
	if(flag ==0)
	{
		alert("Please Select atleast One Line Item before Saving!!");
		return false;
	}
	
		var billingOrderNo;
		//document.forms[0].strBillingTrigger.value=strBillingTrigger;
		//document.forms[0].dataChanged.value=dataChanged;
		billingOrderNo=document.frmBillingTrigger.orderNo.value;
	
	

	fnUpdateLocData(strBillingTrigger,billingOrderNo,dataChanged,locList,checkbox_status);
	

}



	

function fnUpdateLocData(val1,val2,dataChanged,locList,checkbox_status)
    {

      
		var jsonrpc;
		var test;
		var result_list;
		var result_list_view_order_dto;
		var msg="";
		var myForm=document.getElementById('frmBillingTrigger');
        document.getElementById('LOCNO').value=trim(document.getElementById('LOCNO').value);
	

	try
	{
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
		//[014]  start
		var sessionid ='<%=request.getSession().getId() %>';
		//[014]  end
		
		var jsData =			
	    {
		  orderNo:val2,
		  billingTriggerString:val1,
		  dataChanged:dataChanged,
		  locArrayList:locList,
		  checkbox_status:checkbox_status,
		  sessionid:sessionid
		 
		};
	
		test = jsonrpc.processes.fnUpdateLocdetails(jsData);
	if(test.biling_status!=null)
	{
	   if(test.biling_status=='success')
	   {
	   
	   alert("Data Saved Successfully.");
//	   myForm.submit();
			//myForm.elements["pagingSorting.pageNumber"].value=val;	
			var myDummyForm=createBTScreenDummyForm();
			showLayer();
			myDummyForm.submit();
	   
	   }
	   
	   if(test.biling_status=='failure')
	   {
	   
	   alert("Failed to Save");
	   }
	
	}
	   
	    
	 //006 end   
	    //dialogArguments.fnBillingTrigger5(window);
	}
	catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
//		dialogArguments.fnBillingTrigger5(window);
		return;
	}	
		




}
function createBTScreenDummyForm()
{
	var myForm=document.getElementById('frmBillingTrigger');	
	var myDummyForm=createDummyForm("<%=request.getContextPath()%>/viewOrderAction.do");
	attachHiddenField(myDummyForm,"methodName","fnBillingTriggerInfx");
	attachHiddenField(myDummyForm,"orderNo",myForm.orderNo.value);
//	attachHiddenField(myDummyForm,"mode",myForm.mode.value);
	attachHiddenField(myDummyForm,"searchLSI",myForm.searchLSI.value);
	attachHiddenField(myDummyForm,"searchCustomerLSI",myForm.searchCustomerLSI.value);
	attachHiddenField(myDummyForm,"searchLineTriggerStatus",myForm.searchLineTriggerStatus.value);	
	attachHiddenField(myDummyForm,"pagingSorting.pageNumber",myForm.elements["pagingSorting.pageNumber"].value);	
	myDummyForm.target="BILLINGTRIGGER_FORCHANGE";
	return myDummyForm;
}
function fnGetComponentsDetails( serviceProductID,path,serviceId)
{
	var i;
	var j;
	var jsonrpc ;
	var counter;
	var test;
	try
	{
	
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		var jsData =			
	    {
		   serviceProductID:serviceProductID,
		  serviceId:serviceId,
		  orderNo:document.frmBillingTrigger.orderNo.value
		  
		};

		test = jsonrpc.processes.fnGetComponentsDetails(jsData);

		//var rowCount = document.getElementById("tableComponents").getElementsByTagName("TR").length;
		var rowCount = document.getElementById("tableComponents").rows.length
		if(test.list.length > 0 )
		{
			document.getElementById('Components').style.display='block';
			//document.getElementById('Charges').style.display='block';
		}
		for(j=rowCount-1;j>0;j--)
		{
			document.getElementById('tableComponents').deleteRow(j);
		}
	
			for (i = 0 ; i < test.list.length; i++,counter++)
			    {
			
			    
			    var str;
			    var tblRow=document.getElementById('tableComponents').insertRow();
				
					fnInsertData(tblRow,'txtcomponentID',setBlankIfNull(test.list[i].componentID));							
					fnInsertData(tblRow,'txtcomponentName',setBlankIfNull(test.list[i].componentName));		
					fnInsertData(tblRow,'txtPackageID',setBlankIfNull(test.list[i].packageID));		
					fnInsertData(tblRow,'txtPackageName',setBlankIfNull(test.list[i].packageName));							
					fnInsertData(tblRow,'txtcomponentStatus',setBlankIfNull(test.list[i].componentStatus));						
					fnInsertData(tblRow,'txtComponentCreationOrderNo',setBlankIfNull(test.list[i].createdInOrderNo));	
					fnInsertData(tblRow,'txtComponentEndOrderNo',setBlankIfNull(test.list[i].disconnectedInOrderNo));	
				
				// FOR RATE RENEWAL, SOLUTION CHANGE : CAPTURE DISCONNECTION DATE. 
				// ONLY WHEN COMPONENT IS INDIVIDUALLY DISCONNECTED IN CURRENT ORDER AND BT IS NOT YET DONE
					str = "&nbsp;";
					// commented by manisha start cust bill exp bfr 14
					/*if(test.list[i].disconnectedInCurrentService=='Y' && test.list[i].isLineDisconnected!='1')
					{
					
					
						if(test.list[i].disconnectionType== 'CLOSE')
						{
							if(test.list[i].billingTriggerStatus!=20 && enableDisableFlag==1)
							{
							   var componentid=test.list[i].component_ID;
								str = "<img src='<%=request.getContextPath()%>/gifs/disconnect.gif' name='btnCDisconnect"+i+"' border='0px' style='vertical-align: bottom;' onclick='javascript:fnDisconnectionDate("+componentid+",2);'>";
							}
							else
							{
								//str = "<img src='<%=request.getContextPath()%>/gifs/disconnect.gif' name='btnCDisconnect"+i+"' border='0px' style='vertical-align: bottom;' >";							
								str="&nbsp;";
							}
						}
						else
						{
							//do nothing
						}
					}*/ 
					// end
					
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
				// END


					fnInsertData(tblRow,'txtComponentStartLogic',setBlankIfNull(test.list[i].startLogic));					
					fnInsertData(tblRow,'txtComponentStartDays',setBlankIfNull(test.list[i].startDateDays));
					fnInsertData(tblRow,'txtComponentStartMonths',setBlankIfNull(test.list[i].startDateMonth));					
					fnInsertData(tblRow,'txtComponentStartDate',setBlankIfNull(test.list[i].startDate));	
					
					fnInsertData(tblRow,'txtComponentEndLogic',setBlankIfNull(test.list[i].endLogic));	
					fnInsertData(tblRow,'txtComponentEndDays',setBlankIfNull(test.list[i].endDateDays));
					fnInsertData(tblRow,'txtComponentEndMonths',setBlankIfNull(test.list[i].endDateMonth));
					fnInsertData(tblRow,'txtComponentEndDate',setBlankIfNull(test.list[i].disconnectDate));	
					
					fnInsertData(tblRow,'txtCompnentStartTokenNo',setBlankIfNull(test.list[i].fxTokenNo));			
					//Start fx status
					fnInsertData(tblRow,'txtCompnentStartFxStatus',setBlankIfNull(test.list[i].fxStartStatus));	
					//Start fx No
					fnInsertData(tblRow,'txtCompnentStartFxNo',setBlankIfNull(test.list[i].fxStartNo));	
					//End token No
					fnInsertData(tblRow,'txtCompnentEndTokenNo',setBlankIfNull(test.list[i].endTokenNo));	
					//End Fx status
					fnInsertData(tblRow,'txtCompnentEndFxStatus',setBlankIfNull(test.list[i].endFxStatus));	
					//End Fx No
					fnInsertData(tblRow,'txtCompnentEndFxNo',setBlankIfNull(test.list[i].endFxNo));	

					fnInsertData(tblRow,'txtCompnentFxStartStatus',setBlankIfNull(test.list[i].startStatus));	
					
					fnInsertData(tblRow,'txtCompnentFxEndStatus',setBlankIfNull(test.list[i].endStatus));	

					fnInsertData(tblRow,'txtcomponentInfoID',setBlankIfNull(test.list[i].component_ID));
															
					fnInsertData(tblRow,'txtComponentInstanceID',setBlankIfNull(test.list[i].componentInstanceID));
					//***Added by Ashutosh for package instance id and service
					fnInsertData(tblRow,'txtPackageInstId',setBlankIfNull(test.list[i].packageInstId));	
					fnInsertData(tblRow,'txtPackageInstIdServ',setBlankIfNull(test.list[i].packageInstIdServ));
					
		}
	}
	catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}
 //008 end
 

//007 start
function fnGetDetails( serviceProductID,path,/*productBillType,*/serviceId,isUsage)
{
	 gb_serviceProductID=serviceProductID;
	 gb_serviceId=serviceId;
	/*	if(productBillType == 'FLAT')
		{
			document.getElementById('Components').style.display='none';
			document.getElementById('Charges').style.display='block';
			//fnGetChargeDetails( serviceProductID,path,serviceId)
			FirstPage('CHARGE_ID','BillingTriggerForCharges');
		} else if(productBillType == 'USAGE')
		{
			document.getElementById('Components').style.display='block';
			document.getElementById('Charges').style.display='none';
			fnGetComponentsDetails( serviceProductID,path,serviceId);
		}*/
		
	if(isUsage!=1)
	{
		document.getElementById('Components').style.display='none';
		document.getElementById('Charges').style.display='block';
		//fnGetChargeDetails( serviceProductID,path,serviceId)
		FirstPage('CHARGE_ID','BillingTriggerForCharges');
	}
	else
	{	document.getElementById('Components').style.display='block';
		document.getElementById('Charges').style.display='none';
		fnGetComponentsDetails( serviceProductID,path,serviceId);
	}
}

//007 end



function fnDisconnectionDate(id,type)
{
         // type = 1 for Charges
         // type= 2 for components
      
          var url = "<%=request.getContextPath()%>/viewOrderAction.do?methodName=ViewDisconnectionDatePage";
          url = url + "&ID="+id + "&Type="+type;
          window.showModalDialog(url,window,"status:false;dialogWidth:250px;dialogHeight:250px");
}
function copyLoc_Date()
  {
  
     var i;
    var myForm=document.getElementById('frmBillingTrigger');		
    
    var copy_loc_date=document.getElementById('CopyLOCdate').value;
  
  var counter=1;

  
  if(copy_loc_date!="")
   {
   

         if(document.forms[0].chkService)
         
         {
          	
        
            var chkLength_loc_date=document.forms[0].chkService.length;
            
              
	         if(chkLength_loc_date==undefined)
	         
	         {
	         	if(document.frmBillingTrigger["LOCDATE_enableDisableFlag1"].value=='allow')
	         	{
		         	
		         	document.frmBillingTrigger["LOCDATE1"].value=copy_loc_date;
		         	counter++;
	         	}
	         	if(document.frmBillingTrigger["LRD_enableDisableFlag1"].value=='allow')
	         	{
		         	
		         	document.frmBillingTrigger["LocRecDate1"].value=copy_loc_date;
		         	counter++;
	         	}
	         	if(document.frmBillingTrigger["BTDATE_enableDisableFlag1"].value=='allow')
	         	{
		         	
		           document.frmBillingTrigger["BDATE1"].value=copy_loc_date;
		         	counter++;
	         	}
	             /*if((document.frmBillingTrigger["isAutoBilling1"].value==1) && (document.frmBillingTrigger["LOCDATE_enableDisableFlag1"].value=='allow' ||  document.frmBillingTrigger["LRD_enableDisableFlag1"].value=='allow'))
	             {
	                 document.forms[0].LOCDATE.value=copy_loc_date;
	                 document.forms[0].LocRecDate.value=copy_loc_date;
	                counter++;
	             }
	             
	             else if(document.forms[0].chkService.disabled==false && document.frmBillingTrigger["LOCDATE_enableDisableFlag1"].value=='allow'&& document.frmBillingTrigger["LRD_enableDisableFlag1"].value=='allow'&& document.frmBillingTrigger["BTDATE_enableDisableFlag1"].value=='allow')
	             {
	                 document.forms[0].LOCDATE.value=copy_loc_date;
	                 document.forms[0].LocRecDate.value=copy_loc_date;
	                 document.forms[0].BDATE.value=copy_loc_date;
	                  counter++;
	             }*/
	             
	            
	         }
         
	         else
	         
	         {
	         		
		           for (i=0; i<chkLength_loc_date; i++)
				   { 
				 		if(document.frmBillingTrigger["LOCDATE_enableDisableFlag"+(Number(i)+1)].value=='allow')
				 		{
				 			
				 			document.frmBillingTrigger["LOCDATE"+(Number(i)+1)].value=copy_loc_date;
				 			counter++;
				 		}
				 		if(document.frmBillingTrigger["LRD_enableDisableFlag"+(Number(i)+1)].value=='allow')
				 		{
				 		  
				 		    document.frmBillingTrigger["LocRecDate"+(Number(i)+1)].value=copy_loc_date;
				 		    counter++;
				 		}
				 		if(document.frmBillingTrigger["BTDATE_enableDisableFlag"+(Number(i)+1)].value=='allow')
				 		{
				 		   
				 		    document.frmBillingTrigger["BDATE"+(Number(i)+1)].value=copy_loc_date;
				 		    counter++;
				 		}
				        /* if((document.frmBillingTrigger["isAutoBilling"+(Number(i)+1)].value==1) && (document.frmBillingTrigger["LOCDATE_enableDisableFlag"+(Number(i)+1)].value=='allow' || document.frmBillingTrigger["LRD_enableDisableFlag"+(Number(i)+1)].value=='allow'))
				         {
				        
				             myForm.LOCDATE[i].value=copy_loc_date;
				             myForm.LocRecDate[i].value=copy_loc_date;
				            counter++;
				         }
				         
				        else if(myForm.chkService[i].disabled==false && document.frmBillingTrigger["LOCDATE_enableDisableFlag"+(Number(i)+1)].value=='allow'&& document.frmBillingTrigger["LRD_enableDisableFlag"+(Number(i)+1)].value=='allow'&& document.frmBillingTrigger["BTDATE_enableDisableFlag"+(Number(i)+1)].value=='allow')
				         {
				        
				             myForm.LOCDATE[i].value=copy_loc_date;
				             myForm.LocRecDate[i].value=copy_loc_date;
				             myForm.BDATE[i].value=copy_loc_date;
				             counter++;
				         }*/
				       
							   
				   }
	         }
	         
	         
	         if(counter==1)
	         {
				alert("LOC Date Not Copied");         
	         }
	         
       }
       
     
         
       
   }
  else
  
	  {
	       alert("Please Enter LOC_DATE to Copy");
	  }

  }

//004 end

function copy_Loc_No()
  {
  
     var i;
    var myForm=document.getElementById('frmBillingTrigger');		
    
    var copy_loc_No=document.getElementById('CopyLOCNo').value;
  
  var counter=1;

  
  if(copy_loc_No!="")
   {
   

         if(document.forms[0].chkService)
         
         {
         
        
            var chkLength_loc_No=document.forms[0].LOCNO.length;
            
              
	         if(chkLength_loc_No==undefined)
	         
	         {
	             if((document.frmBillingTrigger["isAutoBilling1"].value==1 && document.frmBillingTrigger["LOCNO_enableDisableFlag1"].value=='allow') || (document.forms[0].chkService.disabled==false && document.frmBillingTrigger["LOCNO_enableDisableFlag1"].value=='allow'))
	             {
	                 document.forms[0].LOCNO.value=copy_loc_No;
	                
	                  counter++;
	             }
	             
	            
	         }
         
	         else
	         
	         {
	         		
		           for (i=0; i<chkLength_loc_No; i++)
				   { 
				 
				         if((document.frmBillingTrigger["isAutoBilling"+(Number(i)+1)].value==1 && document.frmBillingTrigger["LOCNO_enableDisableFlag"+(Number(i)+1)].value=='allow') || (myForm.chkService[i].disabled==false && document.frmBillingTrigger["LOCNO_enableDisableFlag"+(Number(i)+1)].value=='allow'))
				         {
				        
				             myForm.LOCNO[i].value=copy_loc_No;
				              counter++;
				         }
				       
							   
				   }
	         }
	         
	         
	         if(counter==1)
	         {
				alert("LOC No Not Copied");         
	         }
	         
       }
       
     
         
       
   }
  else
  
	  {
	       alert("Please Enter No to Copy");
	  }

  }



//002 START
function fnSearchLogicalSI()
    {
    
            var myForm=document.getElementById('frmBillingTrigger');				
			//showLayer();
			//myForm.submit();
			myForm.elements["pagingSorting.pageNumber"].value=1;	
			var myDummyForm=createBTScreenDummyForm();
			showLayer();
			myDummyForm.submit();
			
			
    
    }
    //002 END
    
 //008 start
var chargesSectionSelectedTR=null;
 function rowBackgroundChange()
 {
 	var current = window.event.srcElement;
	while ( (current = current.parentElement)  && current.tagName !="TR");
	current.style.backgroundColor = "blue";
	if(chargesSectionSelectedTR!=null)
	{	chargesSectionSelectedTR.style.backgroundColor = "white";
	}
	chargesSectionSelectedTR=current;
   
 }
 var upperSectionSelectedTR=null;
 function changeUpperBackground()
 {
    var current = window.event.srcElement;
 	while ( (current = current.parentElement)  && current.tagName !="TR");
	current.style.backgroundColor = "blue";
	if(upperSectionSelectedTR!=null)
	{	upperSectionSelectedTR.style.backgroundColor = "white";
	}
	upperSectionSelectedTR=current;
 }
    
function fnGetChargeDetails()
{
	var i;
	var j;
	var jsonrpc ;
	var counter;
	var test;
	var serviceProductId = gb_serviceProductID;
	var serviceId = gb_serviceId;
	pageRecords=PAGE_SIZE_CHARGES;
	try
	{
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		document.getElementById('txtPageNumber').value= pageNumber;
		pageRecords=<%=objUserDto.getPageSizeBTCharges()%>;
		sync();

		var jsData_Paging =			
	    {
		    startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			pageRecords:pageRecords,
			sortByOrder:sortByOrder
		  
		};
		var jsData =			
	    {
		  serviceProductID:gb_serviceProductID,
		  serviceId:gb_serviceId
		};
		var pagingRequired=1;
		test = jsonrpc.processes.fnGetChargesAllOrders(jsData_Paging,jsData,pagingRequired);
		var rowCount = document.getElementById("tableCharges").rows.length;
		iTotalLength=test.list.length;	
		if(iTotalLength !=0)
		{
		iNoOfPages = test.list[0].maxPageNo;
		}
        else
		{     
	        iNoOfPages=1;
		}
	 document.getElementById('txtMaxPageNo').value=iNoOfPages;	
     var pagenumber=document.getElementById('txtPageNumber').value;
	 var MaxPageNo=document.getElementById('txtMaxPageNo').value;

   if(pageNumber==1 && MaxPageNo==1)
	 {
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;
	 }
	 
	 if(pageNumber==1 && MaxPageNo!=1)
	 {
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=false;
	     document.getElementById('next').disabled=false;
	 }
	 
	if(pageNumber==MaxPageNo && pageNumber != 1)
	 {
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;
	     document.getElementById('first').disabled=false;
	     document.getElementById('prev').disabled=false;
	 
	 }
	 if(pageNumber!=MaxPageNo && pageNumber != 1)
	 {
	     document.getElementById('last').disabled=false;
	     document.getElementById('next').disabled=false;
	     document.getElementById('first').disabled=false;
	     document.getElementById('prev').disabled=false;
	 }
		for(j=rowCount-1;j>0;j--)
		{
			document.getElementById('tableCharges').deleteRow(j);
		}
			for (i = 0 ; i < test.list.length; i++,counter++)
			    {
			    var str;
			    var tblRow=document.getElementById('tableCharges').insertRow();
				//tblRow.id = 'id_'+i;
				tblRow.onclick=function(){rowBackgroundChange()};
					//chargetype
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCName'  value='"+test.list[i].chargeType+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					//chargename
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCName'  value='"+test.list[i].chargeName+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					//chargeperiod
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCperiod'  value='"+test.list[i].chargePeriod+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					//chargeamount
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCamount'  value='"+test.list[i].chargeAmt+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
				
					
					
					//CHARGESTATUS
					fnInsertData(tblRow,'txtCstatus',test.list[i].chargeStatus);
				
					//START_ORDER_NO
					fnInsertData(tblRow,'txtCstartOrderNo',test.list[i].chargeCreatedOnOrder);
				
					//END_ORDER_NO
					fnInsertData(tblRow,'txtCendOrderNo',setBlankIfNull(test.list[i].chargeEndedOnOrder));
					
					//if charges are disconnected in current order for noncurrent order's nondisconnetced line
					//show disconnect button , till BT is not done , after that show in disabled form.
					str = "&nbsp;";
					// commented by manisha start cust bill exp bfr 14
					/*if(test.list[i].isChargeDisconnectedInCurrentOrder=='Y' && test.list[i].isLineDisconnected!='1')
					{
					
					
						if(test.list[i].disconnectionType==2 || test.list[i].disconnectionType==3)
						{
							if(test.list[i].billingTriggerStatus!=20 && enableDisableFlag==1)
							{
							   var chargeids=test.list[i].chargeId;
								str = "<img src='<%=request.getContextPath()%>/gifs/disconnect.gif' name='btnCDisconnect"+i+"' border='0px' style='vertical-align: bottom;' onclick='javascript:fnDisconnectionDate("+chargeids+",1)'>";
							}
							else
							{
								str = "<img src='<%=request.getContextPath()%>/gifs/disconnect.gif' name='btnCDisconnect"+i+"' border='0px' style='vertical-align: bottom;' >";							
							}
						}
						// test.list[i].isdifferentialchargeflag!=1 ADDED BY MANISHA END
						else
						{
							//do nothing
						}
					}*/
					// end
					
					
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					CellContents = str;
					tblcol.innerHTML = CellContents;
				    
					//chargefrequency
					fnInsertData(tblRow,'txtCbillPeriod',setBlankIfNull(test.list[i].billPeriod));
					//chargefrequencyamount
					
					
					fnInsertData(tblRow,'txtCfrequencyAmt',setBlankIfNull(test.list[i].chargefrequencyamount));
					//startdatelogic
					fnInsertData(tblRow,'txtstartdatelogic',setBlankIfNull(test.list[i].startdatelogic));
					//start_date_days
					fnInsertData(tblRow,'txtstartdatedays',setBlankIfNull(test.list[i].start_date_days));					
					//start_date_month
					fnInsertData(tblRow,'txtstartdatemonth',setBlankIfNull(test.list[i].start_date_month));				
					//start_date
					fnInsertData(tblRow,'txtCstartdate',setBlankIfNull(test.list[i].chargeStartDate_String));							
					//ENDDATELOGIC
					fnInsertData(tblRow,'txtenddatelogic',setBlankIfNull(test.list[i].enddatelogic));
					//END_DATE_DAYS
					fnInsertData(tblRow,'txtenddatedays',setBlankIfNull(test.list[i].end_date_days));					
					//end_date_month
					fnInsertData(tblRow,'txtenddatemonth',setBlankIfNull(test.list[i].end_date_month));				
					//end date
					fnInsertData(tblRow,'txtCenddate',setBlankIfNull(test.list[i].chargeEndDate_String));
					//Annual Rate
					fnInsertData(tblRow,'txtCannualRate',setBlankIfNull(test.list[i].annualRate));
					//Annotation
					fnInsertData(tblRow,'txtCannotation',setBlankIfNull(test.list[i].annotation));
					//Start token No
					fnInsertData(tblRow,'txtCstartTokenNo',setBlankIfNull(test.list[i].startTokenNo));			
					//Start fx status
					fnInsertData(tblRow,'txtCstartFxStatus',setBlankIfNull(test.list[i].startFxStatus));	
					//Start fx No
					fnInsertData(tblRow,'txtCstartFxNo',setBlankIfNull(test.list[i].startFxNo));	
					//End token No
					fnInsertData(tblRow,'txtCendTokenNo',setBlankIfNull(test.list[i].endTokenNo));	
					//End Fx status
					fnInsertData(tblRow,'txtCendFxStatus',setBlankIfNull(test.list[i].endFxStatus));	
					//End Fx No
					fnInsertData(tblRow,'txtCendFxNo',setBlankIfNull(test.list[i].endFxNo));	
					//Charge Start Status
					fnInsertData(tblRow,'txtCchargeStartStatus',setBlankIfNull(test.list[i].chargeStartStatus));	
					//Charge End Status
					fnInsertData(tblRow,'txtCchargeEndStatus',setBlankIfNull(test.list[i].chargeEndStatus));	
					//Frequency
					fnInsertData(tblRow,'txtCfrequency',setBlankIfNull(test.list[i].chargefrequency));	
					//Charge Id
					fnInsertData(tblRow,'txtCchargeId',setBlankIfNull(test.list[i].chargeId));	
					//Fx View Id
					fnInsertData(tblRow,'txtCfxViewId',setBlankIfNull(test.list[i].fxViewId));	
								
					continue;
			//CHARGESTATUS
			
				var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					tblcol.nowrap="nowrap";
					tblcol.width="150px";
					//str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100;' name='txtchargestatus'  value='"+test.list[i].chargeStatus+"' readonly='true'>";
					str=test.list[i].chargeStatus+"";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					
					
					//fxstatus
					
					
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					tblcol.nowrap="nowrap";
					tblcol.width="150px";
					//str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtfxstatus'  value='"+test.list[i].fxStatus+"' readonly='true'>";
					str=test.list[i].fxStatus+"";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					
					//FX_NO
					
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtfxno'  value='"+test.list[i].fxno+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
			
			///CHARGE_CREATEDON
			
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtchargecreatedon'  value='"+test.list[i].charge_createdon+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					///////CHARGE_EndedON//////////////
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtchargeendedon'  value='"+test.list[i].charge_endon+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
		}
	}
	catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
		throw e;
	}
}

function fnInsertData(tblRow,labelName,dataValue)
{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='"+labelName+"'  value='"+dataValue+"' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
}

function changeVal(i)
{
	if(document.frmBillingTrigger("chkService"+i).checked==true)
	{
		if(document.frmBillingTrigger["LOCNO_enableDisableFlag"+i].value=='allow')
		{
			document.frmBillingTrigger("LOCno"+i).disabled=false;
		}
		
		if(document.frmBillingTrigger["LOCDATE_enableDisableFlag"+i].value=='allow')
		{
			document.frmBillingTrigger["LOCDATE_Calendar"+i].style.display='block';
		}
		
		
		if(document.frmBillingTrigger["LRD_enableDisableFlag"+i].value=='allow')
		{
				document.frmBillingTrigger["LRD_Calendar"+i].style.display='block';
		}
		
	
		if(document.frmBillingTrigger["BTDATE_enableDisableFlag"+i].value=='allow')
		{
				document.frmBillingTrigger["BTDATE_Calendar"+i].style.display='block';
		}
		
		
		
		
		//document.frmBillingTrigger("LOCno"+i).readonly=false;
		//document.frmBillingTrigger("LOCdate"+i).readonly=false;
		//document.frmBillingTrigger("BTdate"+i).readonly=false;
	}
	else
	{
		document.frmBillingTrigger("LOCno"+i).disabled=true;
		if(document.frmBillingTrigger["LOCDATE_enableDisableFlag"+i].value=='allow')
		{
			document.frmBillingTrigger["LOCDATE_Calendar"+i].style.display='none';
		}
		if(document.frmBillingTrigger["BTDATE_enableDisableFlag"+i].value=='allow')
		{
			document.frmBillingTrigger["BTDATE_Calendar"+i].style.display='none';
		}
		if(document.frmBillingTrigger["LRD_enableDisableFlag"+i].value=='allow')
		{
			document.frmBillingTrigger["LRD_Calendar"+i].style.display='none';
		}
	}
	return false;
}

function fnBillingTriggerSubmit()
{

     
    var flag,i,index_lp,strBillingTrigger,dataChanged,locList,checkbox_status;
	//[017] start
	/* //[015] Start
	var billingOrderNo;
	billingOrderNo=document.frmBillingTrigger.orderNo.value;
	var stages='BT';
	jsonrpc = new JSONRpcClient(path + "/JSON-RPC")
	var parallelUpgradeValidation = jsonrpc.processes.parallelUpgradeValidation(billingOrderNo,stages);
	if(parallelUpgradeValidation != '')
	{
		alert(parallelUpgradeValidation);
		return false;
	}
	//[015] End */
	//[017] end
	flag=0;
	strBillingTrigger="";
	dataChanged="";
	var line_prop=new Array();
	index_lp=0;
	var myList=[];
    var locNo_status;
	var Inp_locDate_Status;
	var Inp_locRecDate_Status;
	locList={"javaClass": "java.util.ArrayList","list": []};
	locNo_status="";
	Inp_locDate_Status="";
	var toadd;
	var counter=0;
	checkbox_status="";
	var confirmation=0;
	 var btDoneBy=-1;
	
	for(i=1;i<=document.frmBillingTrigger.itemServiceCount.value;i++)
	{
	 
		if(document.frmBillingTrigger("chkService"+i).checked==true)
		{
		     checkbox_status="checked";
		
			ServiceProductId=document.frmBillingTrigger("hdnSID"+i).value;
			LOCNo=document.frmBillingTrigger("LOCno"+i).value;
			LOCdate=document.frmBillingTrigger("LOCdate"+i).value;
			BillingTriggerDate=document.frmBillingTrigger("BTdate"+i).value;
			LocRecDate = document.frmBillingTrigger("LocRecDate"+i).value;
			
			pmprovdate = document.frmBillingTrigger("taskDate"+i).value;
			siid = document.frmBillingTrigger("siid"+i).value;
			challenno = document.frmBillingTrigger("challenno"+i).value;
			challendate = document.frmBillingTrigger("challendate"+i).value;
			servicetype = document.frmBillingTrigger("servicetype"+i).value;
			sendToM6 = document.frmBillingTrigger("hdnSendToM6"+i).value;
			
			if(ServiceProductId=="")
			{
				ServiceProductId="0";
			}
			
		
			
			if(servicetype=="Service" || servicetype=="Bandwidth")
			{
			
				
				
				if(pmprovdate=="" || siid=="")
					{
					
						
					
						alert("billing trigger can not be done for Product Number :" + ServiceProductId+"as few of the fields are blank-SIID,PM Provisioning Date");
						return false;
					
					}
					
			}
			
			
			if(servicetype=="Hardware")
			{
			     if(sendToM6==1 && (pmprovdate=="" || siid=="" || challenno=="" || challendate==""))
					{
					
						alert("billing trigger can not be done for Product Number :" + ServiceProductId+"as few of the fields are blank-SIID,PM Provisioning Date,Challen No,Challen Date");
						return false;
					}else if(sendToM6!=1 && (pmprovdate=="" || siid=="" ))
					{
						alert("billing trigger can not be done for Product Number :" + ServiceProductId+"as few of the fields are blank-SIID,PM Provisioning Date.");
						return false;
					}
					
			}
			
			if(document.frmBillingTrigger("isAutoBilling"+i).value!=2)
			{	
			if(LOCNo=="")
			{
			alert("Please Enter LOC No for Product Number : "+ ServiceProductId);
			return false;
			}
			if(LOCdate=="")
			{
			alert("Please Enter LOCDate for Product Number : " + ServiceProductId);
			return false;
			}
		
			if(LocRecDate=="")
			{
			alert("Please Enter Rec. Date for Product Number : " + ServiceProductId);
			return false;
				}
				if(BillingTriggerDate=="")
				{
					alert("Please Enter BillingTriggerDate for Product Number : " + ServiceProductId);
					return false;
				}
			}
		
			if(strBillingTrigger=="")
			{
				strBillingTrigger=ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate+"~"+LocRecDate+"~1";
				dataChanged="1";
			}
			else
			{
				strBillingTrigger=strBillingTrigger+"@@"+ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate+"~"+LocRecDate+"~1";
				dataChanged=dataChanged+"@@"+"1";
			}
			flag=1;
			
			line_prop[index_lp]=null;//document.frmBillingTrigger("line_prop"+i).value;
			index_lp++;

		
	//006 start	
	
			if(document.frmBillingTrigger("isAutoBilling"+i).value==1)
					    
			{
			   
			    
			    if(document.frmBillingTrigger["LOCNO_enableDisableFlag"+i].value=="allow" && document.frmBillingTrigger("LOCno"+i).value!="")
			      {
			        
			          toadd="true";
			          locno_status="edited";
			          
			      }
			      
			      else
			        {
			           locno_status="";
			           
			        }
					    
				if(document.frmBillingTrigger["LOCDATE_enableDisableFlag"+i].value=="allow" && document.frmBillingTrigger("LOCdate"+i).value!="")
			      {
			          toadd="true";
			          Inp_locDate_Status="edited";
			      }
			      
			      else
			      {
			         Inp_locDate_Status="";
			         
			      }
			      
			       if(document.frmBillingTrigger["LRD_enableDisableFlag"+i].value=="allow" && document.frmBillingTrigger("LocRecDate"+i).value!="")
			      {
			          toadd="true";
			          Inp_locRecDate_Status="edited";
			      }
			      
			      else
			      {
			         Inp_locRecDate_Status="";
			         
			      }
			      
			      
			      
			    if(locno_status=="edited" || Inp_locDate_Status=="edited" || Inp_locRecDate_Status=="edited")
			      
			      {
			      //[014]  Start
			             var ob={"javaClass": "com.ibm.ioes.beans.ViewOrderDto",
							              serviceProductID:document.frmBillingTrigger("hdnSID"+i).value,
										  locNo:document.frmBillingTrigger("LOCno"+i).value,
										  locDate:document.frmBillingTrigger("LOCdate"+i).value,
										  locRecDate:document.frmBillingTrigger("LocRecDate"+i).value,
										  locNo_Status:locno_status,
										  locDate_Status:Inp_locDate_Status,
										  locRecDate_Status:Inp_locRecDate_Status,
										  btDoneBy:btDoneBy
											
					       		};
			                          myList[counter]=ob;
                locList={"javaClass": "java.util.ArrayList","list": myList};								       
                counter++;
					  				flag=1;
			      
			      }     
					  	      
			}		       
								  
		}
	
	}
	
					      
					      
	if(flag ==0)
	{
		alert("Please Select atleast One Line Item before Selecting Billing Trigger!!");
		return false;
	}
	
		var billingOrderNo;
		//document.forms[0].strBillingTrigger.value=strBillingTrigger;
		//document.forms[0].dataChanged.value=dataChanged;
		billingOrderNo=document.frmBillingTrigger.orderNo.value;
	
	

	fnSetBillingTriggerData(strBillingTrigger,billingOrderNo,dataChanged,line_prop,locList,checkbox_status,confirmation);
	

}



	

function fnSetBillingTriggerData(val1,val2,dataChanged,line_prop,locList,checkbox_status,confirmation)
    {

      
		var jsonrpc;
		var test;
		var result_list;
		var result_list_view_order_dto;
		var msg="";
		var myForm=document.getElementById('frmBillingTrigger');
        document.getElementById('LOCNO').value=trim(document.getElementById('LOCNO').value);
	

	try
	{
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		//[014]  start
		var sessionid ='<%=request.getSession().getId() %>';
		//[014]  end
		var jsData =			
	    {
		  orderNo:val2,
		  billingTriggerString:val1,
		  dataChanged:dataChanged,
		  line_prop:line_prop,
		  locArrayList:locList,
		  checkbox_status:checkbox_status,
		  billingTriggerConfirmed:confirmation,
		  sessionid:sessionid
		};
		document.getElementById('fnBillingTrigger4').disabled=true;
		test = jsonrpc.processes.fnBillingTriggerSubmitForChangeOrders(jsData);
		
		
		if(test!=null)
		{	
			if(test.ifAnyFailValidation=='FAILURE' && test.billingTriggerProcess=='validation')
			{
				/*result_list_view_order_dto = test.arrViewOrderDto;
				
				var j;
				var msg ="";
				for(j=0;j<result_list_view_order_dto.list.length;j++)
				{
						
					if(result_list_view_order_dto.list[j].dateValidationStatus =='FAIL')
					{
						msg = msg+"\n"+"Billing Trigger Date can not be less than : "+result_list_view_order_dto.list[j].accountActiveDate+" for Line No :"+result_list_view_order_dto.list[j].serviceProductID;
					}
				
				}*/
				result_list_view_order_dto = test.billingTriggerValidationErrors;
				var j;
				var msg ="";
				for(j=0;j<result_list_view_order_dto.list.length;j++)
				{
					msg = msg+"\n"+result_list_view_order_dto.list[j];
				}
				
				alert(msg);
				document.getElementById('fnBillingTrigger4').disabled=false;
				
				//returning as validation failure
				return;
			}else if(test.ifAnyBillingTriggerConfirmationRequired=='1' && test.billingTriggerProcess=='validation')
			{
				if(window.confirm('Do you want to continue'))
				{
					fnBillingTriggerSubmit();
				}
				else
				{
					return;
				}
			}
		}
	
	    var successcount=0;
		var falcount=0;
		var successinsert=0;
		var failuretoinsert=0;
		var i;
		var str1="";
		var str3;
		var locfailure_result="";
		var locsuccess_result="";
		var locfailure_start;
		var locsuccess_start;
		
		 if(test.billingTriggerResult!=null && test.billingTriggerResult.list.length>0)
			   {
			         
						for(i=0;i<test.billingTriggerResult.list.length;i++)
						{
				
							if(test.billingTriggerResult.list[i].billingTriggerStatus=='20')
							{
								successcount=successcount+1;
							}
							else
							{
							
						 str3=(test.billingTriggerResult.list[i].serviceProductID);
						     str1=str1 + ',' + str3;
							falcount=falcount+1;
							}
						
						 }
			  }
	  
	  
				if(test.successfullyLOCDataInserted!=null && test.successfullyLOCDataInserted.list.length>0)
				{
				    
	                  for(i=0;i<test.successfullyLOCDataInserted.list.length;i++)
	                   {
	                         locsuccess_start= test.successfullyLOCDataInserted.list[i].serviceProductID;
	                         locsuccess_result=locsuccess_result+','+locsuccess_start;
	                         successinsert=successinsert+1;
	                   }                  
	                    			
				   
				   
				}  
				
				
				if(test.failuetoInsertLocData!=null && test.failuetoInsertLocData.list.length>0)
				{
				
	                  for(i=0;i<test.failuetoInsertLocData.list.length;i++)
	                   {
	                         locfailure_start= test.failuetoInsertLocData.list[i].serviceProductID;
	                         locfailure_result=locfailure_result+','+locfailure_start;
	                         failuretoinsert=failuretoinsert+1;
	                   }                  
	                    			
				   
				   
				}  
				
				if(str1.length>0)
			  
					{
						str1=str1.substring(1);
					}
		
	 	   if(successcount>0)
	 	   {
		          msg="Billing Trigger Result :" + "\n" +   "Request Submitted Successfully for "+successcount+" Products";
		   } 
		        
		   if(falcount>0)
			{
			   msg=msg + "\n" +" Request Submission Failed for - "+falcount+". \nFollowing are the Product Numbers that have Failed - " + str1;
		    }
		     
		if(successinsert>0)
		{
		     msg=msg + "\n" +" LOC DATA Inserted for - "+successinsert+" products";
		
		}	
		
		if(failuretoinsert>0)
		{
		     msg=msg + "\n" +" Loc Data Failed To Insert  for - "+failuretoinsert+" products. \nFollowing are the Product Numbers that have Failed - " + locfailure;
		}	
				
				
		
		
		alert(msg);
//		showLayer();
//	    myForm.submit();
			//myForm.elements["pagingSorting.pageNumber"].value=val;	
			var myDummyForm=createBTScreenDummyForm();
			showLayer();
			myDummyForm.submit();
	    
	 //006 end   
	    //dialogArguments.fnBillingTrigger5(window);
	}
	catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
//		dialogArguments.fnBillingTrigger5(window);
		return;
	}	
		



		
 /*if(test!=null)
		{
		alert("null");
		var result_list=test.billingTriggerResult;
			alert("ins");
			//alert(result_list.list.length);
			//alert('Action Performed. ');
		}
		var successcount=0;
		var falcount=0;
		var i;
		var str1="";
		var str3;
		var result_list;
		
for(i=0;i<result_list.list.length;i++)
		{
		alert("billing");
			if(result_list.list[i].billingTriggerStatus=='30')
			{
		successcount=successcount+1;
			}
			else
			{
			
		 str3=(result_list.list[i].serviceProductID);
		     str1=str1 + ',' + str3;
			falcount=falcount+1;
			}
		
		}*/
				
//dialogArguments.document.fnBillingTrigger();
//document.searchForm.action='viewOrderAction.do?methodName=fnBillingTriggerSubmit&BillingTrigger='+val1+'&orderNo='+val2;
//	document.searchForm.submit();
}

function pagingSubmit(val)
{
	myForm=document.getElementById('frmBillingTrigger');
	myForm.elements["pagingSorting.pageNumber"].value=val;
	var myDummyForm=createBTScreenDummyForm();
	showLayer();
	myDummyForm.submit();
}
function getDropDownCLSI()
{
	$("#searchCustomerLSI").autocomplete( "search", "" );
	$("#searchCustomerLSI").focus();

	
}
function getDropDownLSI()
{
	$("#searchLSI").autocomplete( "search", "" );
	$("#searchLSI").focus();

	
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
		    hideothers();
		    callOnBlur();
  }
function hideothers()
	{
		$(":text[ctrltype='dropdown']").each(function() {
				var thisEl = $(this);				
			$(thisEl).autocomplete('widget').css('z-index', 100);
		});
	}
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
		var orderNumber = document.getElementById("orderNo").value;
				       		         	         
	         $(":text[ctrltype='dropdown']").each(function() {
				var thisEl = $(this);																				
	        	thisEl.autocomplete({
	    			   
			            source: function(request, response) 
			            {
			            			            																	
	                	$.ajax({
							url: "<%=request.getContextPath()%>/NewOrderAction.do?method=populateServiceAttMasterValue&searchval="+request.term+"&attributefor="+thisEl.attr("retval")+"&sourceType=NEW&serviceId="+0+"&SPID=0&accountId="+document.getElementById('orderNo').value,
		                    data: "{ edition: '" + request.term + "',targetctrl: 'test' }",
		                    dataType: "json",
		                    type: "Get",
		                    selectFirst:true,		                   
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
                                     	if(thisEl.attr("retval")=="AUTOSUGGESTCLSI")
                                     	{
                                     		$(thisEl).val("");	
											$("#searchCustomerLSI").val("0");
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTLSI")
                                     	{
                                     		$(thisEl).val("");	
											$("#searchLSI").val("0");
                                     	}
                                     }
                                     else
                                     {
                                     	if(thisEl.attr("retval")=="AUTOSUGGESTCLSI")
                                     	{
                                     		$("#searchCustomerLSI").val(ui.item.value);
		                                    $(thisEl).val(ui.item.label);
		                                    //FetchBillingDetails();
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTLSI")
                                     	{
                                     		$("#searchLSI").val(ui.item.value);
		                                    $(thisEl).val(ui.item.label);
		                                    //FetchBillingDetails();
                                     	}
                                     
	                                      
                                     }
                                     
           return false;
       },
	   change: function( event, ui ) 
							{																													
								if(ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1"|| $("#searchCustomerLSI").val()=="0")
								{
										                				
	                				var value2,label2;
	                			 var validateData=$.ajax({
													url: "<%=request.getContextPath()%>/NewOrderAction.do?method=populateServiceAttMasterValue&searchval="+request.term+"&attributefor="+thisEl.attr("retval")+"&sourceType=NEW&serviceId="+0+"&SPID=0&accountId="+document.getElementById('orderNo').value,
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
									if(value2=="-1" ||label2=="-No Record Found-"){
										if(thisEl.attr("retval")=="AUTOSUGGESTCLSI")
                                     	{
                                     		$(thisEl).val("");
											$("#searchCustomerLSI").val("0");	
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTLSI"){
											$(thisEl).val("");
											$("#searchLSI").val("0");		
										}
									}else{
										if(thisEl.attr("retval")=="AUTOSUGGESTCLSI")
                                     	{
                                     		$(thisEl).val(label2);
											$("#searchCustomerLSI").val(value2);
											//FetchBillingDetails();
                                     	}
                                      	else if(thisEl.attr("retval")=="AUTOSUGGESTLSI"){
											$(thisEl).val(label2);
											$("searchLSI").val(value2);
										}
									}				                				    			
																
								}else{	
								
									if(thisEl.attr("retval")=="AUTOSUGGESTCLSI")
                                     	{
                                     	//
                                     	}
                                     	
									
								}																
							},								
	            minLength: 0
	        });
			});							
	}
	
var path="<%=request.getContextPath()%>";
function showAccountUpdateStatus()
{
	var orderNo=document.frmBillingTrigger.orderNo.value;
	var url = "<%=request.getContextPath()%>/viewOrderAction.do?methodName=ViewAccountUpdateStatusPage&orderNo="+orderNo;
    window.showModalDialog(url,window,"status:false;dialogWidth:520px;dialogHeight:300px");
}
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="buttonsDisableInViewMode()">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/viewOrderAction" styleId="frmBillingTrigger" method="post" target="BILLINGTRIGGER_FORCHANGE">
		<bean:define id="formBean" name="ViewOrderLogicalFormBean"></bean:define>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>

<DIV class="head"> Billing Trigger Details</div>
<fieldset class="border1">
<legend> <b>Billing Trigger Details</b></legend>
<input type="hidden" name="methodName" value="fnBillingTriggerInfx"/>
<div class="scroll">
	<table  border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
	<tr>
	
		<td width="10%">
		<!-- 004 start -->
		 	LOC Date
		</td>
		<td width="15%">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>	
				 <td>
				 	<input onmouseover='getTip(value)' onmouseout='UnTip()' size="10" type="text" showcal="date" name="CopyLOCdate" id="CopyLOCdate" class="inputBorder1" style="width:120px;" onblur="if(this.value.length > 0){return checkdate(this)}">
				 </td>
				 <td>
			  		<a href="javascript:show_calendar(frmBillingTrigger.CopyLOCdate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" style="vertical-align: bottom;">
				 	</a>
				 </td>
		 	</tr>
	 		</table>
	 	</td>
		<td width="20%">
			<div class="searchBg"><a href id="copy_loc_date" onClick="copyLoc_Date()">Copy LOC Date</a></div>
			<!-- 004 end -->
		</td>
		
		<td width="10%">
	 <input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="15" name="CopyLOCNo" maxlength="50" id="CopyLOCNo" class="inputBorder1" style="width:120px;" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Copy LOC No')};">
	 	
	 
	 	</td>
		<td width="20%">
			<div class="searchBg"><a href id="copy_loc_No" onClick="copy_Loc_No()">Copy LOC No</a></div>
			<!-- 004 end -->
		</td>
		<!--  <td width="10%">
			<div class="searchBg"><a href="#">Pre Impl. Disconn.</a></div>
		</td>-->
		<td width="45%">
			<table border="0" cellspacing="0" cellpadding="0" align="right">
				<tr>
				
					<td width="4%" align="left">
					
					<img src="gifs/top/4.gif" onClick="savelocdata()" style="display: block" id="saveIcon"  alt="Save" title="Save Information">
					</td>
				
				
					<!-- 002 START -->
					<td>
				<div class="searchBg"><a href id="Query" onClick="fnSearchLogicalSI()">Query</a></div>
					</td>
						<!-- 002 END -->
					<td  >
					<div class="searchBg"><a href id="Query" onClick="showAccountUpdateStatus()">Account Update Status</a></div>
					</td>
				</tr>
			</table>
		</td>
		<td >
					<html:select property="searchLineTriggerStatus" styleId="searchLineTriggerStatus">
												<html:option value="SHOWALL">Show All</html:option>
												<html:option value="ACCOUNTCREATIONPENDING">Account Creation Pending</html:option>
												<html:option value="BILLINGTRIGGERREADY">Billing Trigger Ready</html:option>
												<html:option value="BILLINGTRIGGERDONE">Billing Trigger Done</html:option>												
					</html:select>
		</td>
		<!--<td width="10%">
			<div class="searchBg"><a href="#">Pre Impl. Disconn.</a></div>
		</td> -->
	</tr> 
	<tr>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	</tr>
	</table>
	<table width="100%">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((ViewOrderFormBean)formBean).getPagingSorting().getPageNumber());
							String 	maxPageNumber =String.valueOf(((ViewOrderFormBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="../paging.jsp">
							<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
							<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
						</jsp:include>
					</td>
				</tr>
	</table>
	<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
		<tr>
			<td width="70%">
				<!-- <div style="height:200px;" class="scroll"> -->
				<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr>
					  <td align="center">&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					   <td>&nbsp;</td>
					  <td colspan="2" align="center" nowrap>------------- Order Line ----------------------</td>
					  <td align="center" nowrap>&nbsp;</td>
					  <td nowrap>&nbsp;</td>
					  <td nowrap>&nbsp;</td>
					  <td colspan="2" align="center" nowrap>--------------Child Billing Account-------------------- </td>
				  </tr>
					<tr>
					<td>Select</td>
					  <td align="center">Select</td>
						<td align="center">PM Provisioning Dated</td>
						<td align="center">LOC No</td>
						<td align="center">LOC Date</td>
						<td align="center">Rec.Date</td><!-- Field added by sandeep on 28-sep -->
						<td align="center">Billing Trigger Date</td>
						
						<td align="center">Number</td>
					     <!--  <td align="center">Remarks</td>-->
						<td align="center" width="5%">Line Item Name</td>

						<td nowrap align="center">Logical SI No </td>
						<td nowrap align="center">Cust. Logical SI No </td>
						<td nowrap align="center">SI ID </td>
						<td nowrap align="center">A/c No </td>
						<td nowrap align="center">FX Status</td>
						<!-- 005 START -->
						<td align="center">Fx Token No</td>
						<td align="center">Fx Response</td>
						<td align="center">Fx Status</td>
						<td align="center">Line Status</td>
						<td nowrap align="center">Fx Service Update Status</td>
						<td align="center">Challen No</td>
						<td align="center">Challen Date</td>
						
						<!-- 005 END -->
						<td  align="center">
							<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
								<tr><td colspan="6" align="center" width="100%">Charge Redirection</td></tr>
								<tr>
									<td width="25%">Redirected LSI</td>
									<td colspan="2" align="center" width="45%">
										<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
											<tr><td colspan="2" align="center" width="100%">Redirected A/c</td></tr>
											<tr>
												<td width="50%">Internal</td>
												<td width="50%">External</td>						
											</tr>					
										</table>
									</td>
									<td width="5%">Status</td>						
									<td width="25%">Status Description</td>									
								</tr>					
							</table>
						</td>
						<td  align="center">
								<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
									<tr><td colspan="2" align="center" width="100%">Cumulative</td></tr>
									<tr>
										<td width="50%">Status</td>						
										<td width="50%">Status Description</td>	
									</tr>
								</table>
						</td>
						<td  align="center">	
						Billing Trigger Done By
						</td>
						<!-- [016] Start -->
						<td  align="center">	
						Billing Trigger Action Date
						</td>
						<td  align="center">	
						PO Expiry Date
						</td>
						<!-- [016] End -->
					</tr>
					<!-- 001 START -->
					<tr>
					<td>Select All</td>
					<td>
					<input type="checkbox" name="SelectAllChck" id="SelectAllChck" onclick="javascript:fnCheckAll();"/>
					</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>

				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
					
				<td>
				
						<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:32%;float:left;" name="searchLSI" ctrltype='dropdown' retval='AUTOSUGGESTLSI' id="searchLSI" class="dropdownMargin"><a id="lsiAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownLSI()">Show</a>								
										</td>
									
				<td>
				
				<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:32%;float:left;" name="searchCustomerLSI" ctrltype='dropdown' retval='AUTOSUGGESTCLSI' id="searchCustomerLSI" class="dropdownMargin"><a id="clsiAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownCLSI()">Show</a>								
								
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
					
					
					</tr>
						<!-- 001 END -->
					<%int strcount=0;%>
					<logic:present name="selectServiceDetailsList" scope="request">
						<logic:empty name="selectServiceDetailsList">
						<tr align="center">
							<td colspan="12">
								<b>No Products Available for Billing Trigger</b>
							</td>
						</tr>
						</logic:empty>
					<logic:notEmpty name="selectServiceDetailsList">



					<logic:iterate name="selectServiceDetailsList" id="QueueList" indexId="recordId" type="com.ibm.ioes.beans.ViewOrderDto">
					<bean:define id="billingTriggerValidation" name="QueueList" property="billingTriggerAllowDenyLogic" type="BillingTriggerValidation"></bean:define>
					<bean:define id="remarks" value=""/>

					<%
						String classType=null;
						if(recordId.intValue() % 2 == 0)
						{
						classType="normal";
						}
						else
						{
						classType="lightBg";				
						}	
						strcount++;					
					%>	
					<tr class="<%=classType%>" onclick="changeUpperBackground()">
						<td>
							
						<input type="hidden" name="hdnNoOfComponents" value=<bean:write name='QueueList' property='noOfComponents'/>>	
						
						<input type="radio" name="chargerow" onclick="javascript:fnGetDetails('<bean:write name='QueueList' property='lineNumber'/>',path,'<bean:write name='QueueList' property='serviceId'/>','<bean:write name='QueueList' property='isUsage'/>')">
						
						
						</td>
						<td align="center">
							<input type="checkbox" id="chkService" name="chkService<%=strcount%>" value="1" onClick="changeVal('<%=strcount%>')" class="<%=billingTriggerValidation.getChkBoxEnable_HTMLClass() %>" style="background-color: <%=billingTriggerValidation.getChkBoxColorCode() %>"  <%=billingTriggerValidation.getChkBoxEnable_HTMLAttribute() %> title="<%=billingTriggerValidation.getCheckBoxMessage()%>">
							<input type="hidden" name="hdnSID<%=strcount%>" value='<bean:write name="QueueList" property="lineNumber"/>'>
							<input type="hidden" name="hdnSendToM6<%=strcount%>" value='<bean:write name="QueueList" property="sendToM6"/>'>							
							
						</td>
						<td nowrap>
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputborder2" readonly="readonly" size="15" name="taskDate<%=strcount%>" value='<bean:write name="QueueList" property="pmProvisioningDate"/>'>
						</td>
						<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" id="LOCNO" maxlength="50" size="10" class="<%=billingTriggerValidation.getLocNoForEdit_HTMLClass() %>" name="LOCno<%=strcount%>" <%=billingTriggerValidation.getLocNoForEdit_HTMLAttribute() %> value='<bean:write name="QueueList" property="locNo"/>' onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'LOC No')};" >
							<input type="hidden" id="LOCNO_enableDisableFlag" name="LOCNO_enableDisableFlag<%=strcount%>"  value='<%=billingTriggerValidation.getLocNoForEdit() %>' >
						</td>
						<td nowrap>
						<table border="0" cellspacing="0" cellpadding="0">
						 <tr>
						    <td>
							   <input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" showcal="date" id="LOCDATE<%=strcount%>" size="12" class="<%=billingTriggerValidation.getLocDateForEdit_HTMLClass() %>" name="LOCdate<%=strcount%>" style="width:110px;float:left;"  value='<bean:write name="QueueList" property="locDate"/>' onblur="if(this.value.length > 0){return checkdate(this)}" readonly="" >
							</td>
							<td>
								<logic:equal name="billingTriggerValidation" property="locDateForEdit" value="allow">
								<logic:equal name="QueueList" property="isAutoBilling" value="1">
									<img src="<%=request.getContextPath()%>/images/cal.gif" name="LOCDATE_Calendar<%=strcount%>" border="0px" style="vertical-align: bottom;"  onClick="javascript:show_calendar(frmBillingTrigger.LOCdate<%=strcount%>);">
								</logic:equal>
								<logic:notEqual name="QueueList" property="isAutoBilling" value="1">
								<img src="<%=request.getContextPath()%>/images/cal.gif" name="LOCDATE_Calendar<%=strcount%>" border="0px" style="vertical-align: bottom; display: none" onClick="javascript:show_calendar(frmBillingTrigger.LOCdate<%=strcount%>);">
								</logic:notEqual>
								</logic:equal>
								
							</td>
						</tr>
					  </table>
							
							<input type="hidden" id="LOCDATE_enableDisableFlag" showcal="date" name="LOCDATE_enableDisableFlag<%=strcount%>"  value='<%=billingTriggerValidation.getLocDateForEdit() %>' >
						</td>
						
						<td nowrap width="4%">
						<table border="0" cellspacing="0" cellpadding="0">
						 <tr>
						    <td>
							   <input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" showcal="date" id="LocRecDate<%=strcount%>" size="12" class="<%=billingTriggerValidation.getLRDForEdit_HTMLClass() %>" style="width:110px;float:left;" name="LocRecDate<%=strcount%>"  value='<bean:write name="QueueList" property="locRecDate"/>' onblur="if(this.value.length > 0){return checkdate(this)}" readonly="">
							</td>
							<td>
								<logic:equal name="billingTriggerValidation" property="locRecDateForEdit" value="allow">
									<logic:equal name="QueueList" property="isAutoBilling" value="1">
									<img src="<%=request.getContextPath()%>/images/cal.gif" name="LRD_Calendar<%=strcount%>" border="0px" style="vertical-align: bottom;"  onClick="javascript:show_calendar(frmBillingTrigger.LocRecDate<%=strcount%>);">
									</logic:equal>
								<logic:notEqual name="QueueList" property="isAutoBilling" value="1">
								<img src="<%=request.getContextPath()%>/images/cal.gif" name="LRD_Calendar<%=strcount%>" border="0px" style="vertical-align: bottom;display: none;"  onClick="javascript:show_calendar(frmBillingTrigger.LocRecDate<%=strcount%>);">
								</logic:notEqual>
								</logic:equal>
								
							</td>
						  </tr>
					  </table>
							<input type="hidden" id="LRD_enableDisableFlag" showcal="date" name="LRD_enableDisableFlag<%=strcount%>"  value='<%=billingTriggerValidation.getLocRecDateForEdit() %>' >
						</td> 
						
					   <td nowrap>
						<table border="0" cellspacing="0" cellpadding="0">
						 <tr>
						    <td>
							  <input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" ctrlindex="<%=strcount%>" showcal="date" id="BDATE<%=strcount%>" size="12" class="<%=billingTriggerValidation.getBtdForEdit_HTMLClass() %>" style="width:110px;float:left;" name="BTdate<%=strcount%>"  value='<bean:write name="QueueList" property="billingTriggerDate"/>' readonly="" onblur="if(this.value.length > 0){return checkdate(this)}">
							</td>
							<td>
								<logic:equal name="billingTriggerValidation" property="btdForEdit" value="allow">
									<img src="<%=request.getContextPath()%>/images/cal.gif" name="BTDATE_Calendar<%=strcount%>" border="0px" style="vertical-align: bottom;display: none;"  onClick="javascript:show_calendar(frmBillingTrigger.BTdate<%=strcount%>);">
								</logic:equal>
							</td>
						 </tr>
					 </table>
							<input type="hidden" id="BTDATE_enableDisableFlag" showcal="date" name="BTDATE_enableDisableFlag<%=strcount%>"  value='<%=billingTriggerValidation.getBtdForEdit() %>' >
						</td> 
						<!--[013]-->
						
						<td nowrap width="4%">
							<!--<input type="text" value="<%=billingTriggerValidation.getHelperRemarks() %>">  -->
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" name="SID<%=strcount%>" value='<bean:write name="QueueList" property="lineNumber"/>'></td>
							<!-- <td nowrap width="15%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" class="inputborder2" name="remarks<%=strcount%>" value='<bean:write name="QueueList" property="remarks"/>'></td> -->
							
						<td nowrap width="15%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" class="inputborder2" name="Sdesp<%=strcount%>" value='<bean:write name="QueueList" property="lineName"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" name="logicalSI<%=strcount%>" value='<bean:write name="QueueList" property="logicalSino"/>'></td>
						<td nowrap  width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" name="CustlogicalSI<%=strcount%>" value='<bean:write name="QueueList" property="custLogicalSino"/>'></td>
						<td nowrap width="10%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" id="siid" name="siid<%=strcount%>" value='<bean:write name="QueueList" property="siid"/>'></td>
						<td nowrap width="10%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fx_ACCOUNT_EXTERNAL_ID"/>'></td>
						<td nowrap ><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fxStatus"/>'></td>
						<!-- 005 START -->
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fx_Token_no"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fx_Response"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="5" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fx_status"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="line_status"/>'></td>
						<td nowrap width="7%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="15" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fxServiceUpdateStatus"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" id="challenno" name="challenno<%=strcount%>" value='<bean:write name="QueueList" property="challen_No"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" id="challendate" name="challendate<%=strcount%>" value='<bean:write name="QueueList" property="challen_date"/>'></td>
						<td  nowrap width="7%">
							<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >								
								<tr>
									<td width="25%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" id="redirectedLSI" name="redirectedLSI<%=strcount%>" value='<bean:write name="QueueList" property="redirectedLSI"/>'></td>
									<td colspan="2" align="center" width="45%">
										<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >											
											<tr>
												<td width="50%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" id="redirectInternalAc" name="redirectInternalAc<%=strcount%>" value='<bean:write name="QueueList" property="redirectInternalAc"/>'></td>
												<td width="50%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" id="redirectExternalAc" name="redirectExternalAc<%=strcount%>" value='<bean:write name="QueueList" property="redirectExternalAc"/>'></td>						
											</tr>					
										</table>
									</td>
									<td width="5%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="5" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="redirect_status"/>'></td>						
									<td width="25%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="5" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="redirect_status_desc"/>'></td>									
								</tr>					
							</table>
						</td>
						<td  nowrap width="7%">
								<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >									
									<tr>
										<td width="50%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="5" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="cumulative_status"/>'></td>						
										<td width="50%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="5" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="cumulative_status_desc"/>'></td>	
									</tr>
								</table>
						</td>
						<!-- btDoneBy added By Start [014]   -->
						<td nowrap ><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="btDoneByName"/>'></td>
						<!-- btDoneBy added By  Start [014]   -->
						<!--[016] Start-->
						<td nowrap ><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="btDoneDate"/>'></td>
						<td nowrap ><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="poExpiryDate"/>'></td>
						<!--[016] End  -->
						<td nowrap><input type="hidden" class="inputborder2" size="15" readonly="readonly" name="isAutoBilling<%=strcount%>" value='<bean:write name="QueueList" property="isAutoBilling"/>'>
						<td nowrap><input type="hidden" class="inputborder2" size="15" readonly="readonly" id="servicetype" name="servicetype<%=strcount%>" value='<bean:write name="QueueList" property="serviceType"/>'>
						
						</td>
						<!-- 005 END -->
						
					</tr>
										
					</logic:iterate>	
					</logic:notEmpty>
					</logic:present>
				</table>
			</td>
		</tr>
	</table>
	</div>
	</fieldset>
	<!-- defect no 20032013004 changed by manisha start-->
	
	<div class="searchBg" align="right"  style="margin-left:400px;"><a href id="fnBillingTrigger4"  onClick="fnBillingTriggerSubmit()">Trigger Billing</a></div>
	<!-- defect no 20032013004 changed by manisha end-->
	
	<!--  010 start -->
	<fieldset class="border1"  id="Charges" style="display:none;">
	<legend scrolling="yes"> <b>Charge Details</b> </legend>
	<!-- Raghu: start Paging code-->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>

		<td align="center"><a href="#" id= "first" onclick="FirstPage('CHARGE_ID','BillingTriggerForCharges')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('CHARGE_ID','BillingTriggerForCharges')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('CHARGE_ID','BillingTriggerForCharges')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('CHARGE_ID','BillingTriggerForCharges')">Last</a></td>
	</tr>
	</table>
<!-- End Paging code-->
	<div class="scroll">
	<table id="tableCharges"  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
		<tr>
		
			<td align="center">Type</td>
			<td align="center">Name</td>
			<td align="center">Charge Period Month </td>
			<td align="center">Total Amount </td>
			<td align="center">Charge Status </td>
			<td align="center">Charge Created on Order No </td>
			<td align="center">Charge Ended on Order No </td>
			<td align="center">&nbsp;</td>
			<td align="center">Bill Period </td>
			<td align="center">Frequency Amount </td>
			
			<td colspan="4" align="center">
				<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr><td colspan="4" align="center">---Start Date---</td></tr>
					<tr>
						<td width="25%">Logic</td>
						<td width="25%">Days</td>
						<td width="25%">Months</td>
						<td width="25%">Charge Start Dt</td>
					</tr>					
				</table>
			</td>
			
			<td colspan="4" align="center">
				<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr><td colspan="4" align="center">---End Date---</td></tr>
					<tr>
						<td width="25%">Logic</td>
						<td width="25%">Days</td>
						<td width="25%">Months</td>
						<td width="25%">Charge End Dt</td>
					</tr>					
				</table>
			</td>
			<td align="center">Annual Rate</td>
			<td align="center">Annotation</td>			
			<td colspan="3" align="center">
				<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr><td colspan="3" align="center">---Start Details---</td></tr>
					<tr>
						<td width="33%">Token No</td>
						<td width="33%">Fx Status</td>
						<td width="34%">Fx No</td>
					</tr>					
				</table>
			</td>
			<td colspan="3" align="center">
				<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr><td colspan="3" align="center">---End Details---</td></tr>
					<tr>
						<td width="33%">Token No</td>
						<td width="33%">Fx Status</td>
						<td width="34%">Fx No</td>
					</tr>					
				</table>
			</td>			
			<td align="center">Charge Start Status</td>
			<td align="center">Charge End Status</td>			
			
			<td nowrap align="center">Frequency</td>
			<td nowrap align="center">Charge Id</td>
			<td nowrap align="center">FX View Id</td>			
<!-- 			<td nowrap>Charge Status</td>
			<td nowrap>FX Status(,Disonnection Status)</td>
			<td>Charge Created On</td>
			<td>Charge Ended On</td> -->
		</tr>

	</table>
	</div>
</fieldset>
<!--  010 end -->

<!--  009 start -->
<fieldset class="border1" id="Components" style="display: none;"><legend scrolling="yes">
	<b>Components Details</b> </legend>
	<div class="scroll">
	<table id="tableComponents" border="1" cellspacing="0" cellpadding="0"
		align="center" width="100%">
		<tr>

			<td>Components ID</td>
			<td>Components Name</td>
			<td>Package ID</td>
			<td>Package Name</td>
			<td>Components Status</td>
			<td>Component Creation Order No</td>		
			<td>Component End Order No</td>	
			<td align="center">&nbsp;</td>			
			<td colspan="4" align="center">
				<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr><td colspan="4" align="center">---Start Date---</td></tr>
					<tr>
						<td width="25%">Logic</td>
						<td width="25%">Days</td>
						<td width="25%">Months</td>
						<td width="25%">Component Start Date</td>
					</tr>					
				</table>
			</td>						
			<td colspan="4" align="center">
				<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr><td colspan="4" align="center">---End Date---</td></tr>
					<tr>
						<td width="25%">Logic</td>
						<td width="25%">Days</td>
						<td width="25%">Months</td>
						<td width="25%">Component End Date</td>
					</tr>					
				</table>
			</td>			
			<td colspan="3" align="center">
				<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr><td colspan="3" align="center">---Start Details---</td></tr>
					<tr>
						<td width="33%">Token No</td>
						<td width="33%">Fx Status</td>
						<td width="34%">Fx No</td>
					</tr>					
				</table>
			</td>
			<td colspan="3" align="center">
				<table   border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr><td colspan="3" align="center">---End Details---</td></tr>
					<tr>
						<td width="33%">Token No</td>
						<td width="33%">Fx Status</td>
						<td width="34%">Fx No</td>
					</tr>					
				</table>
			</td>
			<td align="center">Component Start Status</td>
			<td align="center">Component End Status</td>						
			<td>IB2B Component Instance ID</td>
			<td>FX Component Instance ID</td>
			<td>Package Instance ID</td>
			<td>Package Instance ID Serv</td>
								
				
		
		</tr>

	</table>
	</div>
	</fieldset>
<!--  009 end -->

<!--  011 start -->
			<table border="0" cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td>
						<input type="hidden" name="itemServiceCount" value="<%=strcount%>">
 					    <input type="hidden" name="orderNo" value='<bean:write name="ViewOrderLogicalFormBean" property="billingOrderNo"/>'>
				    <html:hidden name="ViewOrderLogicalFormBean" property="strBillingTrigger"/>
 					</td>
				</tr>
			</table>
	<!--  011 end -->	
</html:form>
</body>
</html>

