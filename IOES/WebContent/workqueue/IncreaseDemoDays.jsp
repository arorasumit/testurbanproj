<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Shorya	        23-sep-2013   cust billing   update demo end date-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.util.HashMap"%>

<html:html>
<head>
<title>Increase Demo Days</title>
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
            width: 2200px;
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
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	showLayer();
	document.forms[0].submit();
}


function searchSubmit()
{
	
	
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	if(isBlankForm()==false){		
		return false;
	}
	
	myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=updateDemoDays";
	showLayer();
	myForm.submit();
	
}
function sortSubmit(sortBy)
{
	myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
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
     	myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=updateDemoDays";
		showLayer();
		myForm.submit();
	
}
function pagingSubmit(val)
{
	var myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<%=request.getContextPath()%>/NewOrderAction.do?method=updateDemoDays";
	showLayer();
	myform.submit();
}

function clearFields()
{	
	var myForm=document.getElementById('searchForm');
	myForm.logicalSINo.value='' ; 	
	myForm.searchfromDate.value='';
	myForm.searchToDate.value='';	

}


function fnCheckOrderAll()
	{
	  	var myForm=document.getElementById('searchForm');
 		var str="";
 		var checkedCounter1=0;
	   	if(document.forms[0].chkSelectLogicalSi)
	    {
	    	var chkLength=document.forms[0].chkSelectLogicalSi.length;
	    	if(document.getElementById('chkAllOrder').checked==true)
			 {
			 	if(chkLength==undefined)
	                {
		              if(document.forms[0].chkSelectLogicalSi.disabled==false)
			           {
		                    document.forms[0].chkSelectLogicalSi.checked=true;
		               }
                  	}
          
      			else
      			{    
	         		for (i =0; i<chkLength; i++)
			  		 { 
		     			if(myForm.chkSelectLogicalSi[i].disabled==false)
					 	{
					        myForm.chkSelectLogicalSi[i].checked=true ;
						}      
		      		}
				}
			}	

		else
	   	{
	   	  if(chkLength==undefined)
        	{
	           if(document.forms[0].chkSelectLogicalSi.disabled==false)
		          {
	   				document.forms[0].chkSelectLogicalSi.checked=false;
	              }
          }
          
      	else
      	{    
         	for (i =0; i<chkLength; i++)
		   	{ 
		     if(myForm.chkSelectLogicalSi[i].disabled==false)
				 {
				     myForm.chkSelectLogicalSi[i].checked=false ;
				 }       
		   }
		}
     }
     
   }
   
  else
   {
   	 alert("No Data exist");
   	 document.getElementById('chkAllOrder').checked=false;
   	 return false;
   } 
     
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var logicalsi=myForm.logicalSINo.value;
	var fromdate=myForm.searchfromDate.value;
	var todate=myForm.searchToDate.value;
	if((fromdate == null & todate == null) 
		|| ( trim(fromdate)=="" & trim(todate) == "")){
		alert("Please enter From Demo Start Date and  To Demo Start Date!");
		return false;
		}
	else {
			if(document.getElementById('logicalSINo').value == "0")
			{
				alert('please enter value greater than 0 in LSI');					
				myForm.logicalSINo.value = "";	
				myForm.logicalSINo.focus();						
				return false;         
			}
			
			
			if(document.getElementById('logicalSINo').value.length>0)
			{
					if(checkdigits(document.getElementById('logicalSINo'))==false){		
							return false;
						}
			   
			}
			
			if((fromdate == null || trim(fromdate)== "" ) && (todate != null && trim(todate) != "" ))
				{
					alert("Please enter From DemoStartDate ");
					return false;
				}
			else if((todate == null || trim(todate)== "" ) && (fromdate != null && trim(fromdate) != "" ))
				{
					alert("Please enter To  DemoStartDate ");
					return false;
				}
				
			if(dateCompare(fromdate,todate)==1)
					{			
						alert("From DemoStartDate cannot be greater than To DemoStartDate");
						return false;
					}
	}				
			
	
	return true;	 

}

function exportToExcel()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';

	if(!isBlankForm()){
		return;
	} 
	else {
			myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=updateDemoDays&inExcel=true";
		    showLayerExcel();
		    //showLayer();
		    myForm.submit();
	}
}

function fnUpdateDemoDays()
{
		var myForm=document.getElementById('searchForm');
 		var str="";
 		var checkedCounter1=0;
 		var logicalsiList='';
 		var ordernoList='';
		var noofdaysList='';
		var demoenddatelist='';
		var demodayslist='';
		var newValue;
		var oldValue;	
	   	if(document.forms[0].chkSelectLogicalSi)
	    {
	    	var chkLength=document.forms[0].chkSelectLogicalSi.length;
	    	if(chkLength==undefined)
	 			{
	 			
		   			if(document.forms[0].chkSelectLogicalSi.checked==true)
		   			{
		   				checkedCounter1++;
		   				newValue=Number(document.getElementById('noofdays_'+1).value);
		    			oldValue=Number(document.getElementById('noofdaysold_'+1).value);
		     			if( newValue>oldValue)
		    				{
				   				if(logicalsiList=="")
					     	   	{
					     	     
					     	     	logicalsiList=document.forms[0].chkSelectLogicalSi.value;
					     	    }
				     	       else
					     	    {
					     	       logicalsiList=logicalsiList + "," +document.forms[0].chkSelectLogicalSi.value;
					     	    }
					     	    
					     	    if(ordernoList=="")
					     	    {
					     	    	ordernoList=document.getElementById('orderno_'+1).value;
					     	    	
					     	    }
					     	    else
					     	    {
					     	    	ordernoList=ordernoList+ "," + document.getElementById('orderno_'+1).value;
					     	    }
					     	    
					     	    if(noofdaysList=="")
					     	    {
					     	    	noofdaysList=document.getElementById('noofdays_'+1).value-document.getElementById('noofdaysold_'+1).value;
					     	    	
					     	    }
					     	    else
					     	    {
					     	    	noofdaysList=noofdaysList+ "," + (document.getElementById('noofdays_'+1).value-document.getElementById('noofdaysold_'+1).value);
					     	    }
					     	    if(demodayslist=="")
					     	    {
					     	    	demodayslist=document.getElementById('noofdays_'+1).value;
					     	    	
					     	    }
					     	    else
					     	    {
					     	    	demodayslist=demodayslist+ "," + document.getElementById('noofdays_'+1).value;
					     	    }
					     	    
					     	    if(demoenddatelist=='')
					     	    {
					     	    	demoenddatelist=document.getElementById('demoenddate_'+1).value;
					     	    }
					     	    else
					     	    {
					     	    	demoenddatelist=demoenddatelist + "," + document.getElementById('demoenddate_'+1).value;
					     	    }
					     	    	
					     	 }
					    else
			     			{
			     					alert('Enter value should be greater than Old Value');
			     					return false;
			     			} 	    
		       			
		       		}
		     	}
		     
			else
			{
				for(i=0;i<chkLength;i++)
				{
		     		if(myForm.chkSelectLogicalSi[i].checked==true)
		     		{
		     			checkedCounter1++
		    			newValue=Number(document.getElementById('noofdays_'+(i+1)).value);
		    			oldValue=Number(document.getElementById('noofdaysold_'+(i+1)).value);
		     			if( newValue>oldValue)
		    				{
				     	     	if(logicalsiList=="")
				     	   		{
				     	     		logicalsiList=myForm.chkSelectLogicalSi[i].value;
				     	    	}
				     	     	else
				     	     	{
				     	       		logicalsiList=logicalsiList + "," +myForm.chkSelectLogicalSi[i].value;
				     	     	}
				     	     	if(ordernoList=="")
					     	    {
					     	    	ordernoList=document.getElementById('orderno_'+(i+1)).value;
					     	    }
					     	    else
					     	    {
					     	    	ordernoList=ordernoList+ "," + document.getElementById('orderno_'+(i+1)).value;
					     	    }
					     	    
				     	     	if(noofdaysList=="")
					     	    {
					     	    	noofdaysList=(document.getElementById('noofdays_'+(i+1)).value - document.getElementById('noofdaysold_'+(i+1)).value) ;
					     	    }
					     	    else
					     	    {
					     	    	noofdaysList=noofdaysList+ "," + (document.getElementById('noofdays_'+(i+1)).value - document.getElementById('noofdaysold_'+(i+1)).value);
					     	    }
					     	    
					     	    if(demodayslist=="")
					     	    {
					     	    	demodayslist=document.getElementById('noofdays_'+(i+1)).value ;
					     	    	
					     	    }
					     	    else
					     	    {
					     	    	demodayslist=demodayslist+ "," + document.getElementById('noofdays_'+(i+1)).value ;
					     	    }
					     	    
					     	    if(demoenddatelist=='')
					     	    {
					     	    	demoenddatelist=document.getElementById('demoenddate_'+(i+1)).value;
					     	    }
					     	    else
					     	    {
					     	    	demoenddatelist=demoenddatelist + "," + document.getElementById('demoenddate_'+(i+1)).value;
					     	    }
					     	    	
		     				}
		     				
		     			 else
			     			{
			     					alert('Enter value should be greater than current value');
			     					return false;
			     			}
		     		}		
		     	}	
			}
			
			if(checkedCounter1==0)
			{
				alert("please Select  logical Si No");
				return false;
			}
			
			
									
				     	     		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
								    var retVal = jsonrpc.processes.fnupdatedemoenddate(logicalsiList,noofdaysList,demoenddatelist,ordernoList,demodayslist);
								    alert(retVal);
								   	myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=updateDemoDays";
									showLayer();
									myForm.submit();
	}
	
 	else
 		{
 			alert("No Data exist");
 			return false;
 		}	
}





path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/NewOrderAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="newOrderBean"></bean:define>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
	      <html:hidden property="toExcel"/>
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left" style="font-size:12px;"><span>Increase Demo Days</span>  
			</td>
			</tr></table>
					</div>
		<div border="1" class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
				<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
		</div>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Search</b></legend>
				<table border="0"  align="center" style="margin-top:7px">
					<tr>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From Demo Start Date:</td>
							<td><html:text  property="searchfromDate" styleId="searchfromDate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.searchfromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
							</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;" >To Demo Start Date:</td>
						<td ><html:text  property="searchToDate" styleId="searchToDate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						  <font size="1">
								<a href="javascript:show_calendar(searchForm.searchToDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px;" width="39">Logical SI No</td>
							<td nowrap width="94">
							<html:text  property="logicalSINo" styleId="logicalSINo" maxlength="20" styleClass="inputBorder1" style="width:75px;"></html:text>
						</td>
						<td align="left" width="40">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
							</td>
							<td align="left" width="40">
							 <a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
						</td>
						
						<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel"  onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Increase Demo Days</b></legend>
			<br/>
			<table width="100%">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((NewOrderBean)formBean).getPagingSorting().getPageNumber());
							String 	maxPageNumber =String.valueOf(((NewOrderBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="../paging.jsp">
							<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
							<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
						</jsp:include>
					</td>
				</tr>
			</table>
			
			<!-- Start Scrolling and Freezing of PERFORMANCE SUMMARY REPORT   -->
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
	                	  <td>Select All<input name="chkAllOrder" id="chkAllOrder"  type="checkbox" onclick="fnCheckOrderAll()"> </td>
	                	  </tr>
								<tr>
									<td align="center" class='inner col3 head1'><b>Select</b></td>	
									<td align="center" class='inner col3 head1'><b>Logical Si No</b></td>	
									<td align="center" class='inner col3 head1'><b>OrderNo</b></td>
									<td align="center" class='inner col3 head1'><b>Order Type</b></td>										
									<td align="center" class='inner col3 head1'><b>AccountNo</b></td>
									<td align="center" class='inner col3 head1'><b>AccountName</b></td>
									<td align="center" class='inner col3 head1'><b>Demo Start Date</b></td>
									<td align="center" class='inner col3 head1'><b>Demo End Date</b></td>	
									<td align="center" class='inner col3 head1'><b>Demo Days</b></td>	
									
					
							</tr>
							</table>
					 	 </div> 	
					 	 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
		        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
		        			
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
											<td class='inner col3' align="center"><input name="chkSelectLogicalSi" id="chkSelectLogicalSi" value="<bean:write name="row" property="logicalsi" />" type="checkbox"></td>
											<td align="center" class='inner col3 '><bean:write  name="row" property="logicalsi"/>&nbsp;</td>
											<td align="center" class='inner col3 '><bean:write  name="row" property="orderNo"/><input type="hidden" id="orderNo_<%=count%>" value="<bean:write  name="row" property="orderNo"/>"></td>
											<td align="center" class='inner col3'><bean:write  name="row" property="order_types"/>&nbsp;</td>
											<td align="center" class='inner col3 '><bean:write  name="row" property="crmAccountNo"/>&nbsp;</td>
											<td align="center" class='inner col3'><bean:write  name="row" property="accountName"/>&nbsp;</td>
											<td align="center" class='inner col3'><bean:write  name="row" property="demoStartDate"/>&nbsp;</td>
											<td align="center" class='inner col3'><bean:write  name="row" property="demoEndDate"/>&nbsp;
											<input type="hidden" id="demoenddate_<%=count%>" value="<bean:write  name="row" property="demoEndDate"/>">
											</td>
											<td align="center" class='inner col3'><input type="text" id="noofdays_<%=count%>" value="<bean:write  name="row" property="demoDays"/>" maxlength="4" onBlur="if(this.value.length > 0){return checkdigits(this)}"  >
											<input type="hidden" id="noofdaysold_<%=count%>" value="<bean:write  name="row" property="demoDays"/>">
											</td>
										
						
						
							</tr>
						</logic:iterate>
					</logic:notEmpty>
			   	</table>
	     		
					<logic:empty  name="orderList">
					   <% String fromPageSubmit=request.getParameter("fromPageSubmit");
							if(("1").equalsIgnoreCase(fromPageSubmit)){%>
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
						<%}%>
					</logic:empty>	
				</logic:present>						
			</table>
		 </div>
	   </td>
    </tr>			   
</table>
	     <table border="1" cellspacing="0" cellpadding="0" align="left" width="100%" id='selectPOtable'>	
					<tr>
						<td colspan="8" align="center">
						    <input type="button" name="btnApproveOrderDetail" style="font-style: normal;" value="Update Days" onClick="fnUpdateDemoDays()">
						   
						    
							
						</td>
					</tr>
				</table>
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html:html>
