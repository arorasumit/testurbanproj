<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 LawKush		10-Feb-11	CSR00-05422     JSP for Generating report for orders pending in billing and hardware.-->
<!-- [001]	Start -->
<!-- [TRNG21052013004]    Vijay        19 June   - create freez header -->
<!-- [002] Vipin Saharia added SeviceNumber and Currency Column -->
<!-- [003] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title> Pending Order And Bill-HW Report  </title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<!-- [TRNG21052013004] start -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
<!-- [TRNG21052013004] end -->
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
<script language="javascript" type="text/javascript"><!--

var counter = 1;

var userId = '<%=request.getAttribute("userId")%>';
var interfaceId = '<%=request.getParameter("interfaceId")%>';
var actionType;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}


function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='pendingOrderBill';	
	if(isBlankForm()==false)
	{
		return ;
	}
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//myform.reset();
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
	myform.method.value='pendingOrderBill';	
	showLayer();
	myform.submit();
}


function searchClick()
{
	myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	
	
	var searchName= '<%= AppConstants.ACTION_SEARCH %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:searchName
		
	};		
    
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	
	if(!isBlankForm()){
		
		return;
	} 
	else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=pendingOrderBill";
		showLayer();
		myForm.submit(); 
	}
}

function exportToExcel()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';
		
	var excelName= '<%= AppConstants.ACTION_EXCEL %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:excelName
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	
	if(!isBlankForm()){
		return;
	} 
	else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=pendingOrderBill&inExcel=true";
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.orderType.value='' ;	
	
	myForm.fromAccountNo.value='' ;
	myForm.toAccountNo.value='' ;
	myForm.fromOrderDate.value='' ;
	myForm.toOrderDate.value='' ;
	myForm.fromCrmOrderid.value='' ;
	myForm.toCrmOrderid.value='' ;
	myForm.partyName.value='' ;
	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var orderType=myForm.orderType.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	var toAccountNo=myForm.toAccountNo.value;
	var fromOrderDate=myForm.fromOrderDate.value;
	var toOrderDate=myForm.toOrderDate.value;
	var fromCrmOrderid=myForm.fromCrmOrderid.value;
	var toCrmOrderid=myForm.toCrmOrderid.value;
	var partyName=myForm.partyName.value;
			
	    	 
	     	 	 	if((fromAccountNo == 0  ) && (toAccountNo !=0))
		  	  		{
						alert("Please enter From Account number");
						return false;
		   	 		}	
					if ((fromAccountNo != 0  ) && (toAccountNo == 0))
		   		 	{
						alert("Please enter To Account number");
						return false;
		   		 	}
		   		 	
		   		 	if((fromCrmOrderid == 0  ) && (toCrmOrderid !=0))
		  	  		{
						alert("Please enter From Order number");
						return false;
		   	 		}	
					if ((fromCrmOrderid != 0  ) && (toCrmOrderid == 0))
		   		 	{
						alert("Please enter To Order number");
						return false;
		   		 	}
		   		 	
		   		 	if(fromAccountNo>toAccountNo)
					{
					alert("From Account No. cannot be greater than To Account No");
					return false;
					}
					if(fromCrmOrderid>toCrmOrderid)
					{
					alert("from Crm Order id cannot be greater than to Crm Order id");
					return false;
					}
		   		 	
		   		 		
		   		/* if((fromOrderDate == null || trim(fromOrderDate)== "" ) && (toOrderDate != null && trim(toOrderDate) != "" ))
					{
						alert("Please enter From order Date");
						return false;
					}
				  	if ((fromOrderDate != null || trim(fromOrderDate)!= "" ) && (toOrderDate == null && trim(toOrderDate) == "" ))
					{
					alert("Please enter To order Date");
					return false;
					}
					
					
		
				if(dateCompare(fromOrderDate,toOrderDate)==1)
					{			
					alert("From order Date cannot be greater than To Order Date");
					return false;
					}*/
				if((fromOrderDate == null || trim(fromOrderDate)== "") && (toOrderDate == null || trim(toOrderDate) == "" ))  
					{
						alert("Please enter From and To COPC Approved Date");
						return false;
					}
				if(fromOrderDate == null || trim(fromOrderDate)== "" ) 
					{
						alert("Please enter From COPC Approved Date");
						return false;
					}
				  	if (toOrderDate == null || trim(toOrderDate)== "" ) 
					{
					alert("Please enter To COPC Approved Date");
					return false;
					}
					
					
		
				if(dateCompare(fromOrderDate,toOrderDate)==1)
					{			
					alert("From COPC Approved Date cannot be greater than To COPC Approved Date");
					return false;
					}
					
	
		
		return true;
	
}
function downloadDump(formId,fileName)
{
	myForm=document.getElementById(formId);
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=getDumpFile&fileName="+fileName;
//	showLayer();
		
	var dumpname = '<%= AppConstants.ACTION_DUMP %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:dumpname
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	
	myForm.submit(); 
}
path='<%=request.getContextPath()%>';
--></script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<html:hidden property="reportProcessForLimit"/>
        <html:hidden property="reportCurrentCount"/>
        <html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>		
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span><span> PENDING ORDER AND BILL-HW</span> </span> 
			</td>
			<logic:present name="formBean"property="isDumpAvailable">
				<logic:equal name="formBean" property="isDumpAvailable" value="Y">
					<td align="right">
					<span style="text-align: right;">
					<a  href="#" title="Download Dump" onClick="javascript:downloadDump('searchForm','<bean:write name="formBean" property="dumpFileName"/>');">
						<font color="white">Download Dump</font>
					</a>&nbsp;&nbsp;
					</span>
					</td>
				</logic:equal>
			</logic:present>
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
					<td width="90px"/>
						<td align="right" style="font-size:9px"> Order Type:</td>
						<td align="left" nowrap>
							<html:select property="orderType"  style="width:90px;float:left;" >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
							
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From Account No.:</td>
						<td nowrap><html:text  property="fromAccountNo"  style="width:75px;" onblur="if((trim(this.value)).length>0) {return checkdigits(this)};" ></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">To Account No.:</td>
						<td nowrap><html:text  property="toAccountNo"  style="width:75px;" onblur="if((trim(this.value)).length>0) {return checkdigits(this)};" ></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From COPC Approved Date:</td>
						<td nowrap><html:text  property="fromOrderDate" styleId="fromdate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromOrderDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">To COPC Approved Date:</td>
						<td nowrap><html:text  property="toOrderDate" styleId="todate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toOrderDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						</tr>
						<tr>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From CRM id:</td>
						<td nowrap><html:text  property="fromCrmOrderid"  style="width:75px;" onblur="if((trim(this.value)).length>0) {return checkdigits(this)};"></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">To CRM id:</td>
						<td nowrap><html:text  property="toCrmOrderid"  style="width:75px;" onblur="if((trim(this.value)).length>0) {return checkdigits(this)};"></html:text>
						</td>
						
						 <td width="10px"/>
						<td align="right" style="font-size:9px">Party</td>
						<td align="left" nowrap>
							<html:text  property="partyName"  style="width:90px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'partyName')};"></html:text>
						</td>
						
						<td width="10px"/>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td width="10px"/>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td width="10px"/>
													<td align="left" ><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Pending Order Bill</b></legend>
			<br/>
			<table width="100%">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((ReportsBean)formBean).getPagingSorting().getPageNumber());
							String 	maxPageNumber =String.valueOf(((ReportsBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="../paging.jsp">
							<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
							<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
						</jsp:include>
					</td>
				</tr>
			</table>
			
			<!-- Start Scrolling and Freezing of PENDING ORDER AND BILL-HW   -->
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
							
					<td align="center" class='inner col2 head1'><b>Vertical  </b></td>
					<td align="center" class='inner col2 head1'><b>Customer Segment  </b></td>
					<td align="center" class='inner col2 head1'><b>Account Category  </b></td>
					<td align="center" class='inner col2 head1'><b>Region Name  </b></td>
					<td align="center" class='inner col3 head1'><b>Account Mgr</b></td>
					<td align="center" class='inner col3 head1'><b>Project Mgr<b></td>
		            <td align="center" class='inner col2 head1'><b>Account Number  </b></td>
					<td align="center" class='inner col2 head1'><b>Copc Approved Date  </b></td>
					<td align="center" class='inner col2 head1'><b>Crm Order id  </b></td>
					<td align="center" class='inner col2 head1'><b>Po Number  </b></td>
					<td align="center" class='inner col3 head1'><b>Service Name  </b></td>
					<td align="center" class='inner col2 head1'><b>
						<logic:present name="pendingOrderBill" scope="request">
							<logic:notEmpty  name="pendingOrderBill" scope="request"> 
								<a href="#" onclick="sortSubmit('LOGICAL_SI_NO')">Logical Circuit Id</a>
							</logic:notEmpty>
							<logic:empty  name="pendingOrderBill" scope="request">
								Logical Circuit Id
							</logic:empty>
						</logic:present>
					</b></td>
					<td align="center" class='inner col3 head1'><b>Line Name</b></td>
					<td align="center" class='inner col2 head1'><b>Line Item Amount</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Charge Start Date  </b></td>
					<td align="center" class='inner col2 head1'><b>Challen No</b></td>
					<td align="center" class='inner col2 head1'><b>Challen Date  </b></td>
					<td align="center" class='inner col3 head1'><b>Party  </b></td>
					<td align="center" class='inner col2 head1'><b>Order Type  </b></td>							
					<!-- [020] start -->	
					<td align="center" class='inner col3 head1'><b>Service_Number  </b></td>
					<td align="center" class='inner col2 head1'><b>Currency  </b></td>
					<!-- [020] end -->						

				</tr>
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>		
				<logic:present name="pendingOrderBill" scope="request">
					<logic:notEmpty  name="pendingOrderBill" scope="request"> 					
						<logic:iterate id="row" name="pendingOrderBill" indexId="recordIdt">
							<%
								String classType=null;
								if(recordIdt.intValue() % 2 == 0)
								{
								classType="lightBg";
								}
								else
								{
								classType="normal";				
								}	
							%>				
							<tr class="<%=classType%>">
                                <td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
                                <td align="left" class='inner col2'><bean:write  name="row" property="cus_segment"/>&nbsp;</td>
                                <td align="left" class='inner col2'><bean:write  name="row" property="act_category"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="actmngname"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="prjmngname"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="accountID"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="poNumber"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="linename"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="chargeAmount_String"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="startDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="challenno"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="challendate"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderType"/>&nbsp;</td>
								<!-- [020] start -->
								<td align="left" class='inner col3'><bean:write  name="row" property="serviceNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="currency"/>&nbsp;</td>
								<!-- [020] end -->
								
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
		              	<div class='horizontal-scrollpendingorderandbillinghw' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of PENDING ORDER AND BILL-HW   -->
					<logic:empty  name="pendingOrderBill">
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
		</fieldset>
	</html:form>
</body>
</html>
<!-- [001]	End -->
