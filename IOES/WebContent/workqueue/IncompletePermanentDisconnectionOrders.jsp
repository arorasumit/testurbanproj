<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Manisha Garg	17-Aug-12	00-05422		Draft Auto Create Permanent Disconnection Orders -->
<!--[002]    Priya Gupta	31-Jul-15                   Add a new column Order Source and enable/disable the checkbox if the orders are repeating-->
<!--[003]    Priya Gupta	31-Mar-16	Order approval twice in PD orders. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.beans.DefaultBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="com.ibm.ioes.forms.DisconnectOrderDto"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html:html>
<head>
<title>Draft-Permanent Disconnection Order</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<link href="./gifs/freezeStyle.css" type="text/css" rel="stylesheet">
<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<script language="javascript" src="js/calendar.js"></script>
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


        table.main
        {
            width: 100%;        
            table-layout: fixed;
        }
        table.root
        {
            table-layout: fixed;
        }
        table.content1
        {
            table-layout: fixed;
            width: 100%;    
            height: 60%;       
        }
        table.head1
        {
            table-layout: fixed;
            width: 100%;
        }
        table.frozen
        {
            table-layout: fixed;
        }
        
        div.horizontal-scroll
        {
            width: 100%;
            height: 22px;
            overflow: hidden;
            overflow-x: scroll;
            border: solid 1px #666;
        }
        div.horizontal-scroll div
        {
            width: 2400px;
            height: 1px;
        }
        div.vertical-scroll
        {
            height: 100%;
            width: 100%;
            overflow: hidden;
            overflow-y: scroll;
            border: solid 1px #666;
        }
        div.vertical-scroll div
        {
            height:400px;
            width: 1px;
        }
        td.inner
        {
            border-left: 1px solid #666;
            border-bottom: 1px solid #666;
            padding: 0px;
            height: 30px;
        }
        td.frozencol
        {
            border-right: 1px double #666;
            width: 1px;
        }
        td.col1
        {           
            width: 50px;
        }
        .col4
        {            
            width:175px;
        }
        .col3
        {            
            width:130px;
        }
        .col5
        {            
            width:250px;
        }
        td.bottomcol
        {
            /*border-bottom: 1px solid #666;*/
        }
        .col2, .col6, .col7, .col8, .col9, .col10
        {
            width: 100px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
        }
        td.head1
        {
            /*border-bottom: 1px solid #666;*/
            background-color: #CCCC99;
            border-top: 1px solid #666;
        }
        .rightcol
        {
            border-right: 1px solid #666;
        }
        .toprow
        {
            border-top: 1px;
        }
        div.root
        {
            margin-left: 0px;
            overflow: hidden;
            width: 100%;
            height: 26px;
            border-bottom: 1px solid #666;
        }
        div.frozen
        {
            overflow: hidden;
            width: 100%; /*border-bottom: 1px solid #666;*/
            height: 200px;
        }
        div.divhead
        {
            overflow: hidden;            
            width: 100%;
            border-left: 1px solid #666;
            border-right: 1px solid #666; /*border-bottom: 0px solid #666;*/
            border-bottom: 1px solid #666;
        }
        div.content1
        {
            overflow: hidden;
            width: 100%;
            height: 50%;
            border-left: 1px solid #666;
            border-right: 1px solid #666; /*border-bottom: 1px solid #666;*/
        }
        td.tablefrozencolumn
        {
            width: 1px;
            border-right: 3px solid #666;
        }
        td.tablecontent
        {
            width: 100%;
        }
        td.tableverticalscroll
        {
            width: 24px;
        }
        div.ff-fill
        {
            height: 23px;
            width: 23px;
            background-color: #ccc;
            border-right: 1px solid #666;
            border-bottom: 1px solid #666;
        }
</style>
<script language="javascript" type="text/javascript">
path='<%=request.getContextPath()%>';
        function reposHead(e) {
            var h = document.getElementById('headscroll');
            h.scrollLeft = e.scrollLeft;
            var f = document.getElementById('divfrozen');
            f.scrollTop = e.scrollTop;
        }
        function reposHorizontal(e) {
            var h = document.getElementById('headscroll');
            var c = document.getElementById('contentscroll');
            h.scrollLeft = e.scrollLeft;
            c.scrollLeft = e.scrollLeft;
            
        }
        function reposVertical(e) {
            var h = document.getElementById('divfrozen');
            var c = document.getElementById('contentscroll');
            h.scrollTop = e.scrollTop;
            c.scrollTop = e.scrollTop;
           
        }

function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	showLayer();
	document.forms[0].submit();
}

function fnViewOrder(orderNo)
{
		var modeValue="viewMode";
    	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
		window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
}
function searchSubmit()
{
	
	var myForm=document.getElementById('searchForm');
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	if(isBlankForm()==false){		
		return false;
	}
	
	myForm.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompletePDOrders";
	showLayer();
	myForm.submit();
	
}
function sortSubmit(sortBy)
{
	myForm=document.getElementById('searchForm');
	myForm.elements["pagingSorting.pageNumber"].value=1;
	if(myForm.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myForm.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myForm.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myForm.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myForm.elements["pagingSorting.sortByColumn"].value=sortBy;
		myForm.elements["pagingSorting.sortByOrder"].value="decrement";
	}	
     	myForm.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompletePDOrders";
		showLayer();
		myForm.submit();
	
}
function pagingSubmit(val)
{
	var myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompletePDOrders";
	showLayer();
	myform.submit();
}

function clearFields()
{	
	var myForm=document.getElementById('searchForm');
	myForm.searchCRMOrder.value='' ; 	
	myForm.searchAccountNo.value='' ; 
	myForm.searchAccountName.value='' ;
	myForm.searchLSI.value='' ;	
	myForm.searchSRNO.value='' ;	
	myForm.searchfromDate.value='';
	myForm.searchToDate.value='';	
	myForm.searchfromdisc_date.value='';
	myForm.searchTodisc_date.value='';

}

function fnApproveOrder()
{		
			var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		var myForm=document.getElementById('searchForm');
 		var str="";
		//[003]
 	   	var modifiedDateLists = [];
 		var checkedCounter1=0;
 		//[003]
 		modifiedDateList={"javaClass": "java.util.ArrayList","list": []};
	   	if(document.forms[0].chkSelectOrder)
	    {
	    	var chkLength=document.forms[0].chkSelectOrder.length;
	    	if(chkLength==undefined)
	 			{
	 			
		   			if(document.forms[0].chkSelectOrder.checked==true)
		   			{
		   				checkedCounter1++;
		   				if(str=="")
			     	   	{
			     	     
			     	     	str=str+document.forms[0].chkSelectOrder.value;
			     	     	//[003]
			     	     	modifiedDateLists.push(document.forms[0].modifiedDate.value);
			 			}
		     	     	else
			     	     {
			     	           str=str + "," +document.forms[0].chkSelectOrder.value;
			     	           //[003] 
			     	           modifiedDateLists.push(document.forms[0].modifiedDate.value);
			     	     }
		       			
		       		}
		     	}
		     
			else
			{
			
				for(i=0;i<document.forms[0].chkSelectOrder.length;i++)
				{
				
		     		if(myForm.chkSelectOrder[i].checked==true)
		     		{
		     			checkedCounter1++
		     	     	if(str=="")
		     	   		{
		     	     	str=myForm.chkSelectOrder[i].value;
		     	     	//[003]
		     	     	modifiedDateLists.push(myForm.modifiedDate[i].value);
		    
		     	    	}
		     	     	else
		     	     	{
		     	       		str=str + "," +myForm.chkSelectOrder[i].value;
		     	       		//[003]
		     	       	    modifiedDateLists.push(myForm.modifiedDate[i].value);
		     	     	}
		     		}
		     	}	
			}
			//[003]
			modifiedDateList={"javaClass": "java.util.ArrayList",
					"list": modifiedDateLists };
			//Shubhranshu, Parallel Upgrade Validation
			var arrayOrdrNumber= getAsArray(str); 
				var validationFailedOrderList=jsonrpc.processes.validatePDOrderForParallelUpgrade(arrayOrdrNumber);
				if(validationFailedOrderList.list.length!=0)
					{
						var i, msg1="LSI Nos. ",msg2="",msg3="in OrderNos. ",msg4="",msg5=" cannot be published ";
							var msg6="because these LSIs are not mapped as Parallel Upgrade in a New Order !! \n ";
								for(i=0;i<validationFailedOrderList.list.length;i++)
									{
										msg2=msg2+validationFailedOrderList.list[i].logical_si_no+", ";
									msg4=msg4+validationFailedOrderList.list[i].orderNo+",";
								}
							alert(msg1+msg2+msg3+msg4+msg5+msg6);
						return false;					
					}
				//return false; 
			//Shubhranshu, Parallel Upgrade Validation
			if(str!="")
			{
			
				<%-- var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); --%>	
				var sessionid ='<%=request.getSession().getId() %>';	
				//[003]
			        var status = jsonrpc.processes.fnapprovePDOrders(str,sessionid,modifiedDateList);	
				var successcount=0;
				var falcount=0;
				//[003]
				var doubleapprovecount =0;
				var msg="";
				var str1="";
				var str3="";
				//[003]
				var doubleapproveorderlist= [];
				
				if(status.approveorderstatuslist!=null && status.approveorderstatuslist.list.length>0)
				{
					for(i=0;i<status.approveorderstatuslist.list.length;i++)
						{
				
							if(status.approveorderstatuslist.list[i].approvestatus=='Success')
							{
								successcount=successcount+1;
							}
							else if(status.approveorderstatuslist.list[i].approvestatus=='DoubleApproval')
							{
								doubleapproveorderlist.push(status.approveorderstatuslist.list[i].orderNumber);
							     	doubleapprovecount=doubleapprovecount+1;
								
							} 
							else
							{
								if(str1=="")
								{
								str1=(status.approveorderstatuslist.list[i].orderNumber);
								
								falcount=falcount+1;
								}
								else
								{
								str3=(status.approveorderstatuslist.list[i].orderNumber);
						     	str1=str1 + ',' + str3;
								falcount=falcount+1;
								}
							}
						
						 }
				 } 
				 	if(successcount>0)
			 	   		{
			 	   			msg="Approve Order Result :" + "\n" +successcount+"- Order Approved Successfully";
				   		} 
		        
			   		if(falcount>0)
						{
							msg=msg + "\n"+ falcount+"- Order failed to Approve" +"\n" +"Following are the Order Numbers that have Failed" +str1;
						}
						if(doubleapprovecount>0)
						{
							msg=msg + "\n"+ doubleapprovecount+"- Following Orders have failed because action has been taken already: " +doubleapproveorderlist;
						}
				 		alert(msg);
				 		myForm.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompletePDOrders";
				 		showLayer();
						myForm.submit();
				
			}
			if(checkedCounter1==0)
			{
				alert("please Select Order");
				return false;
			}
	}
	
 	else
 		{
 			alert("No Order exist");
 			return false;
 		}	
}

function fncancelOrder()
{
		var myForm=document.getElementById('searchForm');
 		var str="";
		//[003]
 		var modifiedDateLists = [];
 		var checkedCounter1=0;
		//[003]
 		modifiedDateList={"javaClass": "java.util.ArrayList","list": []};
	    if(document.forms[0].chkSelectOrder)
	    {
	    	var chkLength=document.forms[0].chkSelectOrder.length;
	    	if(chkLength==undefined)
	 			{
	 			
		   			if(document.forms[0].chkSelectOrder.checked==true)
		   			{
		   				checkedCounter1++;
		   				if(str=="")
			     	   	{
			     	     
			     	     	str=str=document.forms[0].chkSelectOrder.value;
					//[003]
			     	     	modifiedDateLists.push(document.forms[0].modifiedDate.value);
			     	    }
		     	     	else
			     	     {
			     	           str=str + "," +document.forms[0].chkSelectOrder.value;
					   //[003]
			     	          modifiedDateLists.push(document.forms[0].modifiedDate.value);
			     	     }
		       			
		       		}
		     	}
		     
			else
			{
			
				for(i=0;i<document.forms[0].chkSelectOrder.length;i++)
				{
				
		     		if(myForm.chkSelectOrder[i].checked==true)
		     		{
		     			checkedCounter1++
		     	     	if(str=="")
		     	   		{
		     	     		str=myForm.chkSelectOrder[i].value;
					//[003]
		     	     		modifiedDateLists.push(myForm.modifiedDate[i].value);
		    			}
		     	     	else
		     	     	{
		     	       		str=str + "," +myForm.chkSelectOrder[i].value;
					//[003]
		     	       		modifiedDateLists.push(myForm.modifiedDate[i].value);
		     	     	}
		     		}
		     	}	
			}
			//[003]
	    	        modifiedDateList={"javaClass": "java.util.ArrayList",
					"list": modifiedDateLists };
			if(str!="")
			{
				var commit_flag='N';
				var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
				var sessionid ='<%=request.getSession().getId() %>';	
				//[003]		
				var status = jsonrpc.processes.cancelPDOrders(str,sessionid,commit_flag,modifiedDateList);	
				var msg="";
				var successcount=0;
				var falcount=0;
				//[003]
				var doublecancelcount=0;
				var str1="";
				var str3="";
				//[003]
				var doublecancelorderlist=[];
				var temp_doublecancelorderlist="";
				
				if(status.cancelorderstatuslist!=null && status.cancelorderstatuslist.list.length>0)
				{
					for(i=0;i<status.cancelorderstatuslist.list.length;i++)
						{
				
							if(status.cancelorderstatuslist.list[i].cancelorderstatus=='Success')
							{
								successcount=successcount+1;
							}
							//[003]
							else if(status.cancelorderstatuslist.list[i].cancelorderstatus=='DoubleCancelled')
							{
									doublecancelorderlist.push(status.cancelorderstatuslist.list[i].orderNumber);
							     		doublecancelcount=doublecancelcount+1;
							}
							else
							{
								if(str1=="")
								{
								str1=(status.cancelorderstatuslist.list[i].orderNumber);
								falcount=falcount+1;
								}
								else
								{
								str3=(status.cancelorderstatuslist.list[i].orderNumber);
						     	str1=str1 + ',' + str3;
								falcount=falcount+1;
								}
							}
						
						 }
				 } 
				 	if(successcount>0)
			 	   		{
			 	   			msg="Cancel Order Result :" + "\n" +successcount+"- Order Cancelled Successfully";
				   		} 
		        
			   		if(falcount>0)
						{
					   		msg=msg + "\n"+ falcount+"- Order failed to Cancel" +"\n" +"Following are the Order Numbers that have Failed" +str1;
				   	}
					if(doublecancelcount>0)
			 		{
			 				msg=msg + "\n"+ doublecancelcount+"- Following Orders have failed because action has been taken already" +doublecancelorderlist;
			 		}
				 
				 		alert(msg);
				 		myForm.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompletePDOrders";
				 		showLayer();
						myForm.submit();
				
			}
			if(checkedCounter1==0)
			{
				alert("please Select Order");
				return false;
			}
	}
	
 	else
 		{
 			alert("No Order exist");
 			return false;
 		}	
}

function fnCheckOrderAll()
	{
	  	var myForm=document.getElementById('searchForm');
 		var str="";
 		var checkedCounter1=0;
	   	if(document.forms[0].chkSelectOrder)
	    {
	    	var chkLength=document.forms[0].chkSelectOrder.length;
	    	if(document.getElementById('chkAllOrder').checked==true)
			 {
			 	if(chkLength==undefined)
	                {
		              if(document.forms[0].chkSelectOrder.disabled==false)
			           {
		                    document.forms[0].chkSelectOrder.checked=true;
		               }
                  	}
          
      			else
      			{    
	         		for (i =0; i<chkLength; i++)
			  		 { 
		     			if(myForm.chkSelectOrder[i].disabled==false)
					 	{
					        myForm.chkSelectOrder[i].checked=true ;
						}      
		      		}
				}
			}	

		else
	   	{
	   	  if(chkLength==undefined)
        	{
	           if(document.forms[0].chkSelectOrder.disabled==false)
		          {
	   				document.forms[0].chkSelectOrder.checked=false;
	              }
          }
          
      	else
      	{    
         	for (i =0; i<chkLength; i++)
		   	{ 
		     if(myForm.chkSelectOrder[i].disabled==false)
				 {
				     myForm.chkSelectOrder[i].checked=false ;
				 }       
		   }
		}
     }
     
   }
   
  else
   {
   	 alert('No Order To Select');
   	 document.getElementById('chkAllOrder').checked=false;
   	 return false;
   } 
     
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var crmOrderNo=myForm.searchCRMOrder.value;
	var accountNo=myForm.searchAccountNo.value;
	var accountName=myForm.searchAccountName.value;
	var logicalsi=myForm.searchLSI.value;
	var srno=myForm.searchSRNO.value;
	var fromdate=myForm.searchfromDate.value;
	var todate=myForm.searchToDate.value;
	var fromdisdate=myForm.searchfromdisc_date.value;
	var Todisdate=myForm.searchTodisc_date.value;
	if(	document.getElementById('searchCRMOrder').value == "0")
	{
		alert('please enter value greater than 0 in OrderNo');					
		myForm.searchCRMOrder.value = "";	
		myForm.searchCRMOrder.focus();						
		return false;           
	}
	if(document.getElementById('searchCRMOrder').value.length>0)
	{
			if(checkdigits(document.getElementById('searchCRMOrder'))==false){		
					return false;
				}
	   
	}
	if(	document.getElementById('searchAccountNo').value == "0")
	{
		alert('please enter value greater than 0 in Account Name');					
		myForm.searchAccountNo.value = "";	
		myForm.searchAccountNo.focus();						
		return false;         
	}
	
	if(document.getElementById('searchAccountNo').value.length>0)
	{
			if(checkdigits(document.getElementById('searchAccountNo'))==false){		
					return false;
				}
	   
	}
	
	if(	document.getElementById('searchLSI').value == "0")
	{
		alert('please enter value greater than 0 in LSI');					
		myForm.searchLSI.value = "";	
		myForm.searchLSI.focus();						
		return false;         
	}
	
	
	if(document.getElementById('searchLSI').value.length>0)
	{
			if(checkdigits(document.getElementById('searchLSI'))==false){		
					return false;
				}
	   
	}
	
	if(document.getElementById('searchAccountName').value.length>0)
	{
			if(ValidateTextField((document.getElementById('searchAccountName')),path,'Account Name')==false){		
					return false;
				}
	   
	}
	
	if(document.getElementById('searchSRNO').value.length>0)
	{
			if(ValidateTextField((document.getElementById('searchSRNO')),path,'SR No.')==false){		
					return false;
				}
	   
	}
	
	if((fromdate == null || trim(fromdate)== "" ) && (todate != null && trim(todate) != "" ))
		{
			alert("Please enter From SR Date ");
			return false;
		}
	else if((todate == null || trim(todate)== "" ) && (fromdate != null && trim(fromdate) != "" ))
		{
			alert("Please enter To SR Date ");
			return false;
		}
		
	if(dateCompare(fromdate,todate)==1)
			{			
				alert("From SR Date cannot be greater than To SR Date");
				return false;
			}
			
	if((fromdisdate == null || trim(fromdisdate)== "" ) && (Todisdate != null && trim(Todisdate) != "" ))
		{
			alert("Please enter From Disconnection Date ");
			return false;
		}
	else if((Todisdate == null || trim(Todisdate)== "" ) && (fromdisdate != null && trim(fromdisdate) != "" ))
		{
			alert("Please enter To Disconnection Date ");
			return false;
		}
		
	if(dateCompare(fromdisdate,Todisdate)==1)
			{			
				alert("From Disconnection Date cannot be greater than To Disconnection Date");
				return false;
			}		
			

	return true;	 

}

/* Shubhranshu */
function getAsArray(valuestr)
{
	var splitAsArray= valuestr.split(",");
		var asIntegerArray=[];
			var i;	
				for(i=0; i<splitAsArray.length;i++)
				{
			asIntegerArray.push(splitAsArray[i]);
		}
	return asIntegerArray;
}
/* Shubhranshu */


</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/defaultDraftNewOrdAction" method="post" styleId="searchForm">
	<jsp:include page="../header.jsp" flush="true"/> 
	<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%" >
		<tr align="left">
			<td class="head">&nbsp;Draft-Permanent Disconnection Order</td>
		</tr>
		<tr valign="middle" id="newProduct">
			<td valign="bottom" align="center">
				<%String []colors=new String[]{"th","lightBg"}; %>
				<bean:define id="formBean" name="defaultBean"></bean:define>
				<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
				<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
				<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
				<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
				<table border="1" class="tab2" cellpadding="2" cellspacing="1" align="center" width="100%" >
					<tr>
						<td class="tab2" colspan="7">
							<bean:define id="pagingBean" name="formBean"></bean:define>
							<%String  currPageNumber =String.valueOf(((DefaultBean)formBean).getPagingSorting().getPageNumber());
							String  maxPageNumber =String.valueOf(((DefaultBean)formBean).getPagingSorting().getMaxPageNumber());%>
							<jsp:include flush="true" page="../paging.jsp">
								<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
								<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
							</jsp:include>
						</td>
						<td align="right" style="padding-right:10px;" colspan="3">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
							<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
							<img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img>
						</td>
					</tr>
				</table>
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
										<td class='inner col1 head1' align="center"><b>S.No</b></td>
										<td class='inner col3 head1' align="center"><b>Select</b></td>
										<td class='inner col3 head1' align="center"><b>Order No</b></td>
									
										<td class='inner col4 head1' align="center"><b>
										<logic:present name="formBean" property="PDorderList">
													<logic:notEmpty name="formBean" property="PDorderList">
														<a href="#" onclick="sortSubmit('DISCONNECTION_LOGIN_DATE')">From Disconnection Date</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="PDorderList">
													From Disconnection Date
												</logic:empty>
											</logic:present></b></td>
										<td class='inner col4 head1' colspan="2" align="center"><b>
										<logic:present name="formBean" property="PDorderList">
													<logic:notEmpty name="formBean" property="PDorderList">
														<a href="#" onclick="sortSubmit('DISCONNECTION_LOGIN_DATE')">To Disconnection Date</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="PDorderList">
													To Disconnection Date
												</logic:empty>
											</logic:present></b></td>
										<td class='inner col3 head1' align="center"><b>SR No.</b></td>
										<td class='inner col3 head1' align="center"><b>From SR Date</b></td>
										<td class='inner col3 head1' align="center"><b>To SR Date</b></td>
										<td class='inner col3 head1' align="center"><b>SR Raised By</b></td>
										<td class='inner col3 head1' align="center"><b>Account No</b></td>
										<td class='inner col5 head1' align="center"><b>Account Name</b></td>
										<td class='inner col3 head1' align="center"><b>Logical SI No</b></td>
										<td class='inner col3 head1' align="center"><b>Region</b></td>
										<td class='inner col5 head1' align="center"><b>Service Name</b></td>
										<!-- [002] -->
										<td class='inner col5 head1' align="center"><b>Order Source</b></td>
								</tr>
							</table>
	                	</div>                		 	                		
	                		  <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	                		 	<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablePO'>
								<tr class="th">
									<td class='inner col1' align="center">&nbsp;</td>
									<td class='inner col3'>Select All<input name="chkAllOrder" id="chkAllOrder"  type="checkbox" onclick="fnCheckOrderAll()"> </td>
									<td class='inner col3' align="center">
										<html:text property="searchCRMOrder" size="10"  maxlength="18" styleId="id_orderno_search"  
										onkeydown="if (event.keyCode == 13){searchSubmit()};"
										/>
										</td>
										
									
									<td class='inner col4' align="center">
										<html:text property="searchfromdisc_date" size="12" readonly="true"  styleId="disdateFrom" ></html:text>
										<font size="1">
											<a href="javascript:show_calendar(searchForm.searchfromdisc_date);" class="borderTabCal">
											<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
									</td>	
									<td class='inner col4' align="center">
										<html:text property="searchTodisc_date" size="12" readonly="true"  styleId="disdateTo" ></html:text>
										<font size="1">
											<a href="javascript:show_calendar(searchForm.searchTodisc_date);" class="borderTabCal">
											<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
									</td>
									<td class='inner col3' align="center">
										<html:text property="searchSRNO" size="10" maxlength="18" styleId="id_srno_search" 
										onkeydown="if (event.keyCode == 13){searchSubmit()};"></html:text>
									</td>
									<td class='inner col3' align="center">
										<html:text property="searchfromDate" size="10" readonly="true"  styleId="dateFrom" ></html:text>
										<font size="1">
											<a href="javascript:show_calendar(searchForm.searchfromDate);" class="borderTabCal">
											<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
									</td>	
									<td class='inner col3' align="center">
										<html:text property="searchToDate" size="10" readonly="true"  styleId="dateTo" ></html:text>
										<font size="1">
											<a href="javascript:show_calendar(searchForm.searchToDate);" class="borderTabCal">
											<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
									</td>
									<td class='inner col3' align="center">&nbsp;</td>
									<td class='inner col3' align="center">
										<html:text property="searchAccountNo" size="10" maxlength="18" styleId="id_accountno_search" 
										onkeydown="if (event.keyCode == 13){searchSubmit()};" 
										></html:text>
									</td>
									<td class='inner col5' align="center">
										<html:text property="searchAccountName" maxlength="100"  size="15" styleId="id_accountname_search" 
										onkeydown="if (event.keyCode == 13){searchSubmit()};" 
										></html:text>
									</td>
									<td class='inner col3' align="center">
										<html:text property="searchLSI" size="10" maxlength="18" styleId="id_lsi_search" 
										onkeydown="if (event.keyCode == 13){searchSubmit()};" 
										></html:text>
									</td>
									<td class='inner col3' align="center">&nbsp;</td>
									
									<td class='inner col5' align="center">&nbsp;</td>
									<!-- [002] -->
									<td class='inner col5' align="center">&nbsp;</td>
								</tr>
										<%int nSNo=0;%>
									<logic:empty name="formBean" property="PDorderList">
													<tr class="normal">
														<td colspan="10" align="center" style="text-align: center;color: red;font-size: small;font-weight: bold;">No Record Found</td>
													</tr>
												</logic:empty>
												<logic:notEmpty name="formBean" property="PDorderList">
													<logic:iterate id="row"	name="formBean" property="PDorderList" indexId="recordId">
													<%
														String classType=null;
														if(recordId.intValue() % 2 == 0)
														{
														classType="th";
														}
														else
														{
														classType="lightBg";				
														}	
														nSNo++;					
													%>	
													<tr class="<%=classType%>">
														<td class='inner col1' align="center"><%=nSNo%></td>
														<!-- [002] starts-->
												   <!-- <td class='inner col3' align="center"><input name="chkSelectOrder" id="chkSelectOrder" <%=nSNo%>" value="<bean:write name="row" property="searchCRMOrder" />" type="checkbox"></td>    -->
													    <logic:equal name="row" property="checkboxstatus" value="enable" >
                                                        	<td class='inner col3' align="center"><input name="chkSelectOrder" id="chkSelectOrder" <%=nSNo%>" value="<bean:write name="row" property="searchCRMOrder" />" type="checkbox"></td>
                                                        </logic:equal>
                                                        <logic:notEqual name="row" property="checkboxstatus" value="enable">
                                                        <td class='inner col3' align="center"><input name="chkSelectOrder" id="chkSelectOrder" disabled="true" <%=nSNo%>" value="<bean:write name="row" property="searchCRMOrder" />" type="checkbox"></td>
                                                        </logic:notEqual>
                                                        <!-- [002] ends-->
														<td class='inner col3' align="center">
															<a href="javascript:fnViewOrder('<bean:write name="row" property="searchCRMOrder" />');">
																	<font color="Brown">                       
																		<bean:write name="row" property="searchCRMOrder" />
																	</font>
															</a>
														</td>
													    
														<td class='inner col4' colspan="2" align="right"><bean:write name='row' property='search_dis_date' />
													  	<input type="hidden" name="modifiedDate" id="modifiedDate" value="<bean:write name="row" property='oldModifiedDate'/>" />&nbsp; </td>
														<td class='inner col3' align="center"><bean:write name='row' property='searchSRNO' />&nbsp;</td>
														<td class='inner col3' align="center"><bean:write name='row' property='srDate' />&nbsp;</td>
														<td class='inner col3' align="center"><bean:write name='row' property='srDate' />&nbsp;</td>
														<td class='inner col3' align="center"><bean:write name='row' property='sr_raised_by' />&nbsp;</td>
														<td class='inner col3' align="center"><bean:write name='row' property='searchAccountNo' />&nbsp;</td>
														<td class='inner col5' align="center"><bean:write name='row' property='searchAccountName' />&nbsp;</td>
														<td class='inner col3' align="center"><bean:write name='row' property='searchLSI' />&nbsp;</td>
														<td class='inner col3' align="center"><bean:write name='row' property='region' />&nbsp;</td>
														<td class='inner col5' align="center"><bean:write name='row' property='serviceTypeName' />&nbsp;</td>
														<!-- [002 -->
														<td class='inner col5' align="center"><bean:write name='row' property='orderSource' />&nbsp;</td>
													</tr>
													</logic:iterate>
												</logic:notEmpty>
															
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
						    <input type="button" name="btnApproveOrderDetail" style="font-style: normal;" value="Approve Order" onClick="fnApproveOrder()">
						    <input type="button" name="btnCancelOrder" style="font-style: normal;" value="Cancel Order" onClick="fncancelOrder()">
						    
							
						</td>
					</tr>
				</table>

</html:form>
</body>
</html:html>
