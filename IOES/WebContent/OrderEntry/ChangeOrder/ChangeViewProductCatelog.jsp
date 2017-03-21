<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	8-Feb-11	00-05422		Charge Name not displaying more than 2 values -->
<!--[002]	 Lawkush 	12-June-12	    CSR00-05422		For making service  summary attributes editable or non editable according to change type , subchange type and SUBCHANGETYPE_NETWORK_CHANGE_EDITABLE flag  in database -->
<!--[003]   Raghu       19-Jun-12	CSR00-05422		    Changes for line item display as in red colour if charge already exist in that line item -->
<!--[004]	 Raghu	    13-July-2012				changes for dispatch contact name for hardware details in-->
<!--[004.1]	 Rohit Verma	7-Sep-2012		Service Flavour	Modifications for IS_Mandatory for Dropdown Values-->
<!-- [101010]  Rampratap      16-11-2012                  Length may not be more than 25 character -->
<!-- [202020] Rampratap 19-02-2013 Dispatch address couldn't be enabled to edit in any change type of order as it was enbled and was being saved successfuly at ib2b end but when published new address was not reflecting at M6 -->
<!-- [303030] Rampratap 19-02-2013 hdnChangeTypeId == 1 added -->
 
<!-- [505050]   Rampratap  01-Mar-2013 changed regarding dispatch address LST no -->
<!--[005]	 SAURABH	    03-Feb-2013				showing LOV field blank on change of other LOV-->
<!--[006]	 LAWKUSH	    07-Feb-2013				Service Location Customer Address like search Hypercare Point 5-->
<!--[009]	 VIJAY	    27-Feb-2013				 Increase the area's length of 'Service location Details'.-->
<!--[010]	 Rohit Verma    1-Apr-2013	 00-07480 	Validation for not allowing 95p as Service Flavour and no other Lic Company apart from 402-95p BAL-->
<!-- [010] Anil Kumar 20-Mar-2013 Bypass PO Level check for foreign legal entity for arbor migration data -->
<!--[011]	 Neelesh		24-May-13	CSR00-08463     For Addition of Revenue Circle  -->
<!--[012]	 Kalpana		13-JUN-13	CBR-CSR-20130404-XX-08714     For M6 Editable fields ,added isServiceSummReadonly to make the fields readonly and disable  if flag=1  -->
<!--[013]	 Santosh Srivastava  added a method on 16 November for advance payment details
<!-- [TRNG22032013018] SAURABH 22-JUL-13			Service Level Navigation on Product Catelog i.e. Next and Prev Option for Navigation and Tree should be refershed accordingly -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.forms.ComponentsDto"%>
<html>
<head>
<title>Change View ProductCatalog</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="gifs/treeview.css" type="text/css" rel="stylesheet">
<link href="./css/chargeTableFreeze.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
</head>
<script src="js/jquery-latest.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/productCatelogUtility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/enableDisableOrderEntryUtility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/gen_validatorv31.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="js/viewProductCatalogue.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/arborValidation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ArrayProperties.js"></script>
<script type="text/javascript" src="js/Customisation.js"></script>
<script type="text/javascript" src="js/j1.js"></script>
<script type="text/javascript" src="js/nostree.js"></script>
<script type="text/javascript" src="js/tree_nodes.js"></script>
<script type="text/javascript" src="js/tree_format.js"></script>
<script type="text/javascript" src="js/Tokenizer.js"></script>
<script type="text/javascript" src="js/ProductCatalogueASUtility.js"></script>
<script type="text/javascript" src="js/ChangeViewProductCatalogue.js"></script>
<script type="text/javascript">
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
var roleWiseSectionDetail = null;
var serviceNo='<%=request.getParameter("ServiceNO")%>';
var servicesID='<%=request.getParameter("ServiceNO")%>';
var ctindex = 1;
var counter = 1;
var serviceid = "<%= request.getParameter("ServiceID")%>";
var factorVal;
var srvDetailId;
var serviceTypeId;
var gbchargeTypeIds;
var gbchargeTypeValues;
var gbchargeTypeCached=0;
var gbfrequencyId;
var gbfrequencyName;
var gbfrequencyCached=0;
var factorVal;
var gbDefaultPOId='';
var callerWindowObj = dialogArguments;
var contractVal;
var poDateVal;
var neworderno=callerWindowObj.document.getElementById('poNumber').value;//Added By Ashutosh for resolving Product Issues
var path="<%=request.getContextPath()%>";
var pathM6="<%=request.getContextPath()%>";
var gb_path="<%=request.getContextPath()%>";
var attMasterIdList = "365,175,370,194,207,264,287,302,317,3214,669,674,679,685,689,748,1292,1296,1300,1304,1308,3181,3192,1553,1557,1561,1565,1639,1644,2923,2927,2931,2915,2919,2303,3034,3036,3042,2588,2591,2594,2597,2600,3046,2768,2772,2776,2780,2784,2788,2792,2808,2988,5404,5432,5464";
var gbPrevBillFormatIndexflag=0;
var vPrevBillFormat=0;
var vPrevBillFormatValue;
var vPrevCustPoNo=0;
var chkIndexForRadio = 0;
var selectedIndexToBeChecked=0;
var logicalSIno = <%=request.getAttribute("LogicalSI")%>;
var isPublised = callerWindowObj.document.getElementById('isServicePublished').value;
var editSolutionChangeOldProduct=0;
var isOldLineItem_RateRenewal=0;
var poNoForChking=0;
var billinglists = null;
var entityNo;
var licCoNo;
var storeName;
var hdnLineItemName="";
var isView = '<%=request.getAttribute("isView")%>';
var chargelists=0;
var serviceProductID='<%=request.getParameter("ServiceProductID")%>';
//===================[ start clep ]=============================
var clepState=0;
var clep_arrEnableDisableFlags=new Array();
var totalExtraChrgeAmount=0;
var ORDER_CREATION_SOURCE = 2;//CLEP
//===================[ end clep ]===============================
var isBillinglevelDisabled=0;
var PAGE_SIZE_CHARGE_LINE="<%=objUserDto.getPageSizeChargeLines()%>";
var guiAlertForConfig;
var orderStage='';
var attFLI_PO_DisableValue;
var productName='<%=request.getParameter("ProductName")%>';
var serviceName='<%=request.getParameter("ServiceName")%>';
var serviceDetailID='<%=request.getParameter("ServiceTypeID")%>';
var poNumber='<%=request.getParameter("POnum")%>';
var hdnChangeTypeId='<%=request.getParameter("hdnChangeTypeId")%>';
var changeOrderNo='<%=request.getParameter("changeOrderNo")%>';
var serviceSummaryList;
var linesTabFieldList;
var changeSubTypeID  ;
var fxChargeRedirectionTypeCumulative="";
<%	HashMap var_userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession var_sessionObj = (HttpSession) var_userHashMap.get(session.getId());
%>

var gb_exceptionMessage="<%=Messages.getMessageValue("javascriptExceptionMessage")%>";
var gb_exceptionMessageShow = "<%=Messages.getMessageValue("javascriptExceptionShow")%>";
var roleName = <%=objUserDto.getUserRoleId() %>;
var userId = "<%=objUserDto.getUserId() %>";
var roleId = <%=objUserDto.getUserRoleId() %>;
var userIdNo = "<%=objUserDto.getUserId() %>";
var initialSPID = serviceProductID;
var initialServiceNo = serviceNo;
var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
var sessionid ='<%=request.getSession().getId() %>';
var jsonrpc1 = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
var vPrevCustPoValue;
//Puneet for performance tuning
var additionalNodeFlag = null;
//flag for whether details are fetched or not
var serviceSummaryFetched = false;
var billingDetailsFetched = false;
var hardwareDetailsFetched = false;
var locationDetailsFetched = false;
var componentDetailsFetched = false;
var serviceSummaryIndValChanged  = false;
var serviceSummaryIndVal = null;
var isUsageCheckValueChanged = false;
var chargeRedValueChanged = false;
//added by Deepak
var hdnnPrServiceSummary=1;
var hdnnPrLineInfo=1;
var accountNo;
var viewScmButtonFlag=1;//Added by Deepak Kumar for Third Party
//end Deepak
var fxChargeIDConst = {
	'-101':-1	
};

//[013]	
function advPayment() 
{
	/*if(document.getElementById('hdnBillingInfo').value==0)
	{
		alert("User can't input Advance Payment Details as 17 Parameter for this line item doesn't get generated");
		return false;
	}
	else
	{
		if(hdnChangeTypeId==3 || hdnChangeTypeId==1 )
		{
			alert("Advance Payment Details can't updated for Disconnection Order or Network Change Order");
			return false;
		}
		else
		{*/
			var paths = path+"/NewOrderAction.do?method=getAdvancePaymentDetails&orderNo="+poNumber+"&lineItemNo="+serviceProductID+"&serviceNo="+serviceNo+"&logicalSIno="+logicalSIno+"&isView="+isView;
			window.showModalDialog(paths,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
		//}
//	}
}
//[013]	
// Start for Third Party(SCM) added by Deepak Kumar
var service_Name="<%=request.getParameter("selectedServiceName")%>";
function openUpdateWindowSCM()
{
	 accountNo=dialogArguments.document.getElementById("accNo").value;
	 
	
   	var path1 = "<%=request.getContextPath()%>/NewOrderAction.do?method=getTProductLineAttmasterSCMForUpdate";
    path1=path1+"&ServiceProductID="+serviceProductID+"&ServiceID="+serviceid+"&POnum="+poNumber+"&serviceDetailID="+serviceDetailID+"&serviceNo="+serviceNo+"&service_Name="+service_Name+"&roleId="+roleId+"&ProductName="+productName+"&hdnnPrServiceSummary="+hdnnPrServiceSummary+"&hdnnPrLineInfo="+hdnnPrLineInfo+"&accountID="+accountNo+"&gb_sessionid="+sessionid;
    window.showModalDialog(path1,window,"status:false;dialogWidth:1200px;dialogHeight:800px");

   
}
</script>
<body onload="fncServiceTreeView();getServiceAttributes();insertViewScMBtn()">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
	<html:form action="/NewOrderAction" styleId="productCatelog" method="post">
	<bean:define id="formBean" name="newOrderBean"></bean:define>
		<input type="hidden" id="hdnservicetypeid" name="hdnservicetypeid"/>
		<input type="hidden" id="hdnServiceDetailID" name="hdnServiceDetailID"/>
		<DIV class="head">Change View Product Catalog</DIV>
		<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
		<tr> 
		<td width="20%">
			<fieldset class="border1">
				<legend> <b>Service Details</b> </legend>
				<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center" style="color: blue;"><a href="#" id="prev" style="display: block;" onclick="treeViewPagingMethod('Prev')">Prev</a></td>
						<td align="center">
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" id="serviceId_goToPage" 
									class="inputBorder2" size="8" maxlength="10" onblur="if(this.value.length > 0){return checknumber(this)}">
							<a href="#" onclick="treeViewPagingMethod('GoToPage')" >GO </a>
						</td>
						<td align="center" style="color: blue;"><a href="#" id="next" style="display: block;" onclick="treeViewPagingMethod('Next')">Next</a></td>
					</tr>
				</table>
				<table width="100%"  border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<jsp:include flush="true" page="ChangeTreeView.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
		<td width="80%"  valign="top">
		<fieldset class="border1">
			<legend> <b>Change View Product Catalog</b> </legend>
			<div class="scroll" style="height:100%">
				<table  border="1" cellspacing="0" cellpadding="0" align="center" width="90%" >
					<tr id="scmBtn" align="center">
						<td colspan="0" align="left" >
							<label class="hintanchor"><b>Service No:</b><span id="id_span_ServiceNo"><%=request.getParameter("ServiceNO")%></span></label>
						</td>
						<td colspan="0" align="left" >
							<label class="hintanchor"><b>Service Name:</b><span id="id_span_ServiceName"><%=request.getParameter("selectedServiceName")%></span></label>
						</td>
						<td colspan="0" align="left" >							
							<label class="hintanchor" id="lblServiceProductId"><b>LineItem No:</b><%=request.getParameter("ServiceProductID") %></label>
						</td>						
					</tr>
				</table>
				<!--  [009] start -->
				<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
				<!--  [009] end -->
					<tr align="center">
						<td colspan="1" align="right" style="font-size:11px">
							Product Name
						</td>
						<td colspan="1" align="left" style="font-size:11px">
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" value="" id="txtProductName" name="txtProductName" style="float:left;"/>
					
							<div style="float: left"><img src="gifs/top/4.gif" onClick="saveProductCatelog();" style="display: block;" id="saveImage" title="Save Product Information"></div>					
							<div class='searchBg1' style="float: left"  align="right"><a href='#' title='View ServiceProductAttribute' style="float: left" onclick=viewserviceproductattributes()>...</a></div>				
							<!--[013] Start-->
							<input type="button" value="Advance Payment Details" onClick="advPayment();" style="display: block;" id="coinImage" title="View Advance Payment Information"/>
							<!--[013] End-->
						</td>
					</tr>
					<tr id="serviceType" style="display:none">
						<td colspan="1" align="right" style="font-size:11px">
							Service Type
						</td>
						<td colspan="1" align="left" style="font-size:11px;" >
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" style="width:235px" value="" id="txtHdnServiceName" name="txtHdnServiceName"/>
							<input type="hidden" readonly="readonly" value="" name="hdnServiceSummary"/>
							<input type="hidden" readonly="readonly" value="" name="hdnBillingInfo"/>
							<input type="hidden" readonly="readonly" value="" name="hdnChargeInfo"/>
							<input type="hidden" readonly="readonly" value="" name="hdnLocationInfo"/>
							<input type="hidden" readonly="readonly" value="" name="hdnHardwareInfo"/>
							<input type="hidden" readonly="readonly" value="" name="hdnComponentInfo" id="hdnComponentInfo"/>
							<input type="hidden" readonly="readonly" value="<%=request.getParameter("ServiceProductID") %>" name="hdnServiceProductID"/>
							<input type="hidden" readonly="readonly" value="<%=request.getParameter("ServiceID") %>" name="hdnServiceID"/>
							<input type="hidden" value="" name="hdnISFLEFLAG" id="hdnISFLEFLAG"/>
							<input type="hidden" value="0" name="hdnConfigValue" id="hdnConfigValue"/>
							<input type="hidden" value="0" name="hdnFxRedirectionSPID" id="hdnFxRedirectionSPID"/>
							<input type="hidden" name="accNo" id="accNo" value="">
							<input type="hidden" name="insertViewScmButton" id="insertViewScmButton" value="">
						</td>
					</tr>
					<tr align="center">
						<td colspan="2" width="50%">
							<jsp:include flush="true" page="ChangeviewServiceSummary.jsp"></jsp:include>
						</td>
						<input type="hidden" name="hdnSeriveAttCounter" value="">
					</tr>
					<tr align="center" id="BillingNChargeInfo" style="display:none">
					   <!--  [009] start -->
						<td colspan="2" width="70%">
						<!--  [009] end -->
							<jsp:include flush="true" page="ChangeViewBillingNChargeInfo.jsp"></jsp:include>
						</td>
					</tr>
					<tr align="center" id="ServiceLocation" style="display:none">
						<td colspan="2" width="50%">
							<jsp:include flush="true" page="ChangeviewServiceLocation.jsp"></jsp:include>
						</td>
					</tr>
					<tr align="center" id="HardwareDetails" style="display:none">
						<td colspan="2" width="50%">
							<jsp:include flush="true" page="ChangeviewHardwareDetails.jsp"></jsp:include>
						</td>
					</tr>
					
					<tr align="center" id="Components" style="display:none">
						<td colspan="2" width="50%">
							<jsp:include flush="true" page="changeViewComponents.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		</td>
		</tr>
		</table>
	</html:form>
</body>
</html>