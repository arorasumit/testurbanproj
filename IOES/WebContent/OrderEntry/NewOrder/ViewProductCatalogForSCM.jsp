<!-- [001] Vipin Saharia 26-Dec-2013 Added SCM Repush Button -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.forms.ComponentsDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>PR Attributes View</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="gifs/treeview.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./css/chargeTableFreeze.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" type="text/javascript">
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>

var testresult;
var roleWiseSectionDetail = null;
var serviceid = "<%= request.getParameter("ServiceID")%>";
var path="<%=request.getContextPath()%>";
var pathM6 = "<%=request.getContextPath()%>";
var logicalSIno = "<%=request.getAttribute("LogicalSI")%>";
var serviceNo="<%=request.getParameter("ServiceNO")%>";
var isView = "<%=request.getParameter("isView")%>";
var PAGE_SIZE_CHARGE_LINE="<%=objUserDto.getPageSizeChargeLines()%>";
var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
var productName="<%=request.getParameter("ProductName")%>";
var serviceName="<%=request.getParameter("service_Name")%>";
var serviceDetailID="<%=request.getParameter("serviceDetailID")%>";
var serviceProductID="<%=request.getParameter("ServiceProductID")%>";
var poNumber="<%=request.getParameter("POnum")%>";
var servicesID="<%=request.getParameter("serviceNo")%>";
var sessionid ="<%=request.getSession().getId() %>";
var roleId="<%=request.getParameter("roleId")%>";
var requestContextPath = "<%=request.getContextPath()%>";
var hiddenPrSerSummary="<%=request.getParameter("hdnnPrServiceSummary")%>";
var hiddenPrLineinfo="<%=request.getParameter("hdnnPrLineInfo")%>";
serviceTypeId=serviceid;
var serviceNos =  servicesID;
var gb_exceptionMessage="<%=Messages.getMessageValue("javascriptExceptionMessage")%>";
var gb_exceptionMessageShow = "<%=Messages.getMessageValue("javascriptExceptionShow")%>";
var roleName = "<%=objUserDto.getUserRoleId() %>";
var userId = "<%=objUserDto.getUserId() %>";
var counter = 1;
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
var gbPrevBillFormatIndexflag=0;
var billinglists = null; 
var vPrevBillFormat=0;
var vPrevBillFormatValue;
var vPrevCustPoNo;
var vPrevCustPoValue;
var gbDefaultPOId='';
var callerWindowObj = dialogArguments;
var editSolutionChangeOldProduct=0;
var hdnChangeTypeId = 0;
var poDateVal;
var hdnLineItemName="";
var attMasterIdList = "365,175,370,194,207,264,287,302,317,3214,669,674,679,685,689,748,1292,1296,1300,1304,1308,3181,3192,1553,1557,1561,1565,1639,1644,2923,2927,2931,2915,2919,2303,3034,3036,3042,2588,2591,2594,2597,2600,3046,2768,2772,2776,2780,2784,2788,2792,2808,2988,5404,5432,5464";
var ctindex=1;
var chargelists=0;
//===================[ start clep ]=============================
var clepState=0;
var clep_arrEnableDisableFlags=new Array();
var changeSubTypeID=0;
var totalExtraChrgeAmount=0;
var ORDER_CREATION_SOURCE = 2;//CLEP
//===================[ end clep ]===============================
var componentCheckCount=0;
var attFLI_PO_DisableValue;
var upperSectionSelectedTR=null;
var treeNodes;
var global_firstNode;
var initialSPID = serviceProductID;
var initialServiceNo = serviceNo;
var hook=false;
var destAttID=new Array();
var jsonrpc1 = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
//flag for whether details are fetched or not
var serviceSummaryFetched = false;
var billingDetailsFetched = false;
var hardwareDetailsFetched = false;
var locationDetailsFetched = false;
var componentDetailsFetched = false;
var serviceSummaryIndValChanged = false;
var serviceSummaryIndVal = null;
var isUsageCheckValueChanged = false;
var chargeRedValueChanged = false;
var scmLineDetailsFetched = false;
var selectCircleUnit=null;
var accountNumScm="<%=request.getParameter("accountID")%>";
var pr_Value=0;
var isPrReuseFlag=0;
var accountId=0;
var saveScmPageflage=false;
var notSaveFlag=false;
var pathPrReuse = "<%=request.getContextPath()%>/NewOrderAction.do?method=getPRDetails";
var prNumber;
var prNu;
var pr_Value1=null;
var pr_viewValue=false;
var prReuseUpadteViewFlag=0;
var attDescKey=null;
var selectedCircle=null;
var deleteScmLineIdArray = [];
var selectedCircleInView;
var savekeyPressScm=false;
var hiddenServiceID=0;
var hiddenChangeServiceId=0;
var isServiceCancle=0;
</script>
</head>
<script src="js/jquery-latest.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/productCatelogUtility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/enableDisableOrderEntryUtility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/gen_validatorv31.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/arborValidation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ArrayProperties.js"></script>
<script type="text/javascript" src="js/Customisation.js"></script>
<script type="text/javascript" src="js/j1.js"></script>
<script type="text/javascript" src="js/nostree.js"></script>
<script type="text/javascript" src="js/tree_nodes.js"></script>
<script type="text/javascript" src="js/tree_format.js"></script>
<script type="text/javascript" src="js/Tokenizer.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet"> 
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script> 
<script type="text/javascript" src="js/ViewProductCatalogForScm.js"></script>
<script type="text/javascript" src="js/viewProductCatalogue.js"></script>
<script type="text/javascript" src="js/ProductCatalogueASUtility.js"></script>
<body onload="getServiceId();getParameter();getIsPublished();drawViewChargeTable1()">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
	<html:form action="/NewOrderAction" styleId="productCatelog" method="post">
	<bean:define id="formBean" name="newOrderBean"></bean:define>
		<input type="hidden" id="hdnservicetypeid" name="hdnservicetypeid"/>
		<input type="hidden" id="hdnServiceDetailID" name="hdnServiceDetailID"/>
		<input type="hidden" id="accountNo" name="accountNo" value="<%=request.getParameter("accountID")%>"/>
		<input type="hidden" id="paramCheck" name="paramCheck" value=""/>
		<DIV class="head">PR Attributes View</DIV>
		<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
		
		<td width="80%" valign="top">
		<fieldset class="border1" >
			<legend> <b>PR Attributes</b> </legend>
			<div class="scroll" style="height:80%" align="right">
				<table  border="1" cellspacing="0" cellpadding="0" align="center" width="90%" >
					<tr  align="center">
						<td colspan="0" align="left" >
							<label class="hintanchor"><b>Service No:</b><span id="id_span_ServiceNo"><%=request.getParameter("serviceNo")%></span></label>
						</td>
						<td colspan="0" align="left" >
							<label class="hintanchor"><b>Service Name:</b><span id="id_span_ServiceName"><%=request.getParameter("service_Name")%></span></label>
						</td>
						<td colspan="0" align="left" >							
							<label class="hintanchor" id="lblServiceProductId"><b>LineItem No:</b><%=request.getParameter("ServiceProductID") %></label>
						</td>
					</tr>
				</table>
				<table  border="1" cellspacing="0" cellpadding="0" align="center" width="90%" >
					<tr align="center" >
						<td colspan="1" align="right" style="font-size:11px">
							Line Item
						</td>
						<td colspan="1" align="left" style="font-size:11px">
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" value="" name="txtProductName" style="float:left;"/>
							
					<div style="float: left"><img src="gifs/top/4.gif" onClick="saveProductCatalogForScm();" style="display: block;" id="saveImageScm" title="Save Product Information"></div>
					<logic:equal name="formBean" property="isDisplayRepushBtn" value="1">
					<input type="button" value="Repush" onmouseover="getTip(value)" onmouseout="UnTip()" name="btnRepush" id="btnRepush" onClick="repushXML();">
					</logic:equal>
					<input type="button" value="PR Reuse" name="btnprReuse" id="btnprReuse" onClick="showPR()">
					<input type="button" onclick="createNewPr()" value="Create New PR" name="creatNewPr" id="creatNewPr">
				
				</td>
				
				<!-- [001] Start -->
							
							<td id="scmRepush" ></td>
							<td id="prReuse" ></td>
							<!-- [001] End -->						
							</tr>

						<tr align="center">
						<td colspan="1" align="left" style="font-size:11px;" >
							<input type="hidden" readonly="readonly" value="" name="hdnPrServiceSummary"  id="hdnPrServiceSummary"/>
							<input type="hidden" readonly="readonly" value="" name="hdnBillingInfo" id="hdnBillingInfo"/>
							<input type="hidden" readonly="readonly" value="" name="hdnPrLineInfo" id="hdnPrLineInfo"/>
							<input type="hidden" readonly="readonly" value="" name="IsPrReuse" id="IsPrReuse"/>
							<input type="hidden" readonly="readonly" value="" name="serviceIDD" id="serviceIDD"/>
							<input type="hidden" readonly="readonly" value="" name="changeServiceID" id="changeServiceID"/>
							<input type="hidden" readonly="readonly" value="<%=request.getParameter("ServiceProductID") %>" name="hdnServiceProductID"/>
							<input type="hidden" readonly="readonly" value="<%=request.getParameter("ServiceID") %>" name="hdnServiceID"/>
							<input type="hidden" readonly="readonly" value="<%=request.getParameter("ServiceNO")%>" name="hdnServiceno"/>
							<input type="hidden" readonly="readonly" value="<%=request.getParameter("ProductName")%>" name="hdnServiceProductName"/>
							<input type="hidden" readonly="readonly" value="viewproductctlg" name="hdnpagename"/>
							<input type="hidden" value="" name="hdnISFLEFLAG" id="hdnISFLEFLAG"/>
							<input type="hidden" value="0" name="hdnConfigValue" id="hdnConfigValue"/>
							<input type="hidden" value="0" name="hdnFxRedirectionSPID" id="hdnFxRedirectionSPID"/>
							<input type="hidden" value="1" name="accountNoFlag" id="accountNoFlag"/>
							<input type="hidden" value="" name="hdnNotSaveFlag" id="hdnNotSaveFlag"/>
							<input type="hidden" id="createNewpr" name="createNewpr" value=""/>
							<input type="hidden" id="hdnprValue" name="hdnprValue" value=""/>
							
						</td>
						</tr>
					</tr>
				
							

					<tr align="center">
					
						<td colspan="2">
							<jsp:include flush="true" page="ViewServiceSummaryForScm.jsp"></jsp:include>
						</td>
						<input type="hidden" name="hdnSeriveAttCounter" value="">
					</tr>
					
					
					<tr align="center">
					
						<td colspan="2">
							<jsp:include flush="true" page="viewScmLine.jsp"></jsp:include> 
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
<script type="text/javascript">
gb_sessionid = sessionid;
  function getServiceId()
{
isServiceCancle=getServiceStatus(servicesID);

savekeyPressScm=true;
document.getElementById('txtProductName').value=productName;
document.getElementById('creatNewPr').disabled=true;
disableChangeView();
if(isPrReuseFlag==1)
{
	document.getElementById('creatNewPr').disabled=false;
}
 disablePrReuse();
}  
 
	//========================================================================================================
</script>
</html>
