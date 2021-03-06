<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ANIL KUMAR	    24-feb-2011 				set hardware dispatch address fields as 0 if default dispatch address not available -->
<!--[002]	 Raghu	    13-July-2012				Code change for allowing Maxlength from backend for service summary section		UAT of 7 Product-->
<!--[003]	 Raghu	    13-July-2012				changes for dispatch contact name for hardware details in-->
<!--[004.1]	 Rohit Verma	7-Sep-2012		Service Flavour	Modifications for IS_Mandatory for Dropdown Values-->
<!-- [101010]  Rampratap      16-11-2012                  Length may not be more than 25 character -->
<!-- [202020]   Rampratap  01-Mar-2013 changed regarding dispatch address LST no -->
<!--[005]	 SAURABH	    03-Feb-2013				showing LOV field blank on change of other LOV-->
<!--[006]	 LAWKUSH	    07-Feb-2013				Service Location Customer Address like search Hypercare Point 5-->
<!--[008]	 MANISHA GARG    08-Feb-2013			On changing different hardware product from product catlog , warranty logic list is getting appended with same values-->
<!--[010]	 Rohit Verma    1-Apr-2013	 00-07480 	Validation for not allowing 95p as Service Flavour and no other Lic Company apart from 402-95p BAL-->
<!-- [009] Anil Kumar 20-Mar-2013 Bypass PO Level check for foreign legal entity for arbor migration data -->
<!--[011]	 Neelesh		24-May-13	CSR00-08463     For Addition of Revenue Circle  -->
<!--[012]	 Rohit Verma	 						    Customer Billing Experence--Making Payment Term editable for hardware and Service-BFR13-->




<!--[012]	 Kalpana		13-JUN-13	CBR-CSR-20130404-XX-08714     For M6 Editable fields ,added isServiceSummReadonly to make the fields readonly and disable  if flag=1 -->

<!--[013]	 Neelesh		05-Jul-13	Sevice and Billing Summary do no Collapse on saving  -->
<!--[017] 	 Rohit Verma    07-Dec-13 	CSR-09463	Advance Payment Icon-->
<!-- [019] 	 Vipin Saharia	27-Aug-14	GDB Lic Comp. segregation changed if condition for new licence company-->
<!-- [020] 	 Vipin Saharia	28-Jan-15	added logic for setting default value to dropdown from ATTDEFAULTVAL Column-->
<!-- [021]  Gunjan Singla     29-Apr-2015   20150202-R2-021037    Suppression of creation of new billable account in case of change order -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>    
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.forms.ComponentsDto"%>
<html>
<head>
<title>ProductCatalog</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/productCatelogUtility.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="js/gen_validatorv31.js"></script>
<script type="text/javascript" src="js/Customisation.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="js/ProductCatelogUpdate.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/arborValidation.js"></script>
<script language="javascript" type="text/javascript">
//POC POINT:Added by Ashutosh
var poNumber=<%=request.getParameter("poNumber")%>;
var serviceID_Product = <%=request.getAttribute("serviceID")%>;
//By Deepak kumar for Third Party
var sessionid ='<%=request.getSession().getId() %>';
var serviceNameForScm = "<%= request.getParameter("selectedServiceName")%>";
var isView='null';
var productName1;
var callerWindowObj = dialogArguments;
var roleName = callerWindowObj.gb_roleID;
//end Deepak kumar
var serviceProductID=0;
var lineType = "NEW";
var fxChargeIDConst = {
		'-101':-1	
	};
function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

function checkchargevalidation()
{

var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	if(chkChargeLine.length>0)
	{
		document.getElementById("txtPODetailNo").selectedIndex=vPrevCustPoNo;	
		alert('please delete All the Charges first');
	  	return false;
	}

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
var serviceid = "<%= request.getParameter("serviceTypeID")%>";
jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
var treeViewForProduct = jsonrpc.processes.ViewDropDownTree(serviceid);
var pathM6 = "<%=request.getContextPath()%>";
var attMasterIdList = "365,175,370,194,207,264,287,302,317,3214,669,674,679,685,689,748,1292,1296,1300,1304,1308,3181,3192,1553,1557,1561,1565,1639,1644,2923,2927,2931,2915,2919,2303,3034,3036,3042,2588,2591,2594,2597,2600,3046,2768,2772,2776,2780,2784,2788,2792,2808,2988,5404,5432,5464";
var nextnodedd;

function getTreeNodeCountForDropDown(treeViewForProduct,nextNode)
{
	var i;
	var localCount=1;
	
	for(i=0;i<treeViewForProduct.list.length;i++)
	{
		if(nextNode==treeViewForProduct.list[i].serviceChildId)
		{
			localCount = localCount + getTreeNodeCountForDropDown(treeViewForProduct,treeViewForProduct.list[i].serviceParentId);
		}
	}
	return localCount;
}


function getTreeForDropDown(nextnode1)
{
	var strval = getTreeForDropDownValue(treeViewForProduct,nextnode1)	
}

function setPopulateStdReason(taxationValue)
{
	if(taxationValue==2)
	{
		populateReasonForChange();
		document.getElementById("changeReason").disabled=false;
		document.getElementById("changeReason").className = "inputBorder1";
	}else
	{
		 var combo = document.getElementById("changeReason");	
		 removeAllOptions(combo);
		 document.getElementById("changeReason").disabled=true;
		 document.getElementById("changeReason").className = "inputDisabled";
	}
	var i;
	var counter = (document.getElementById('tableCharges').rows.length)-1;
	for(i=2; i<=counter;i++)
	{
		getTaxRate(i);
	}
}		
function populateReasonForChange()
{	
	var tr, td, i, j, oneRecord;
    var test;
    var interFaceStdReason=1;
   var combo = document.getElementById("changeReason");	
   var iCountRows = combo.length;
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(i);
   }
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var stdReason = jsonrpc.processes.populateReasonForChange(interFaceStdReason);
	    for(j=0;j<stdReason.list.length;j++)
	    {
	    	var option = document.createElement("option");	    	
	   		option.text = stdReason.list[j].reasonName;
	   		option.title = stdReason.list[j].reasonName;
			option.value = stdReason.list[j].reasonID;
			try 
			{
			combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }    
}

function getTreeForDropDownValue(treeViewForProduct,nextnodedd)
{
	
	var str="";
	var icountDash= 0;
	var sDash = "";
	icountDash = getTreeNodeCountForDropDown(treeViewForProduct,nextnodedd)
	sDash = "";
	if(icountDash!=1)
	{
		for(j=0;j<icountDash*10;j++)
			sDash = sDash + " ";
		sDash = sDash + " ";
	}
	var service = document.getElementById('serviceDetailID');
	
	if(service==null)
	{
		alert('No Line Item Exists for Rate Renewal In This Service');
		return false
	}	
	for(var j=0;j< service.options.length;j++)
	{
		if(service.options[j].value == nextnodedd && nextnodedd != "0")
		{
			service.options[j].text = sDash + service.options[j].text;
			if(icountDash==2)
				service.options[j].style.color = "red";
			if(icountDash==3)
				service.options[j].style.color = "blue";
			if(icountDash==4)
				service.options[j].style.color = "green";
		}	
	}
	
	for(var i=0;i<treeViewForProduct.list.length;i++)
	{
		if(nextnodedd==treeViewForProduct.list[i].serviceParentId)
		{						
			str = str + getTreeForDropDownValue(treeViewForProduct,treeViewForProduct.list[i].serviceChildId);
	    }
	}	
	return str;
}

var counter = 1;
var factorVal;
var contractVal;
var chargeVal;
var poDateVal;
var serviceTypeId;
var isServiceReadOnly;
var changeTypeId;
var gbPrevBillFormatIndexflag=0;
var vPrevBillFormat=0;
var vPrevCustPoNo=0;
var logicalSIno = <%=request.getAttribute("LogicalSI")%>;
var editSolutionChangeOldProduct=0;
var hdnChangeTypeId = 0;
var arborLSIPopupCounter=0;
var attFLI_PO_DisableValue;
var roleWiseSectionDetail = null;
function getPrevBillFormat(var_billformat)
{									
	vPrevBillFormat=var_billformat;													
}


function getPrevCustPoNo(var_custpono)
{		
	vPrevCustPoNo=var_custpono;				
}

function setBillingLevelNo(billingLevel){

	if(billingLevel == 2) {
		document.getElementById('txtBillingLevelNo').value = document.getElementById('txtPODetailNo').value;
	} else if (billingLevel == 1)
	{
		document.getElementById('txtBillingLevelNo').value = 0;
	}else if (billingLevel == 3)
	{
		document.getElementById('txtBillingLevelNo').value = logicalSIno;
	}
}
function calculateTotalAmount()
{		
		//alert('attMasterIdList = '+attMasterIdList);	
		if(document.getElementById('txtBillingformat').value == 3 && document.getElementById('hdnServiceSummary').value == 1 && document.getElementById('hdnChargeInfo').value == 1)
		{
			var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
			var attributeID=new Array();
			//var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
			var totalAmount = 0.0;		
			/*for(i=0;i<chkChargeLine.length;i++)
			{
				var chkboxCharge=chkChargeLine[i];
				var index=chkboxCharge.value;
				totalAmount = parseFloat(totalAmount) + parseFloat(document.getElementById("txtCTAmt"+index).value);
				
			}*/	
			totalAmount = Number(document.getElementById('totalLineAmount').value);
			var values = attMasterIdList.split(",");
			//alert('values = '+values);
			if (values.length > 0) 
			{	
				for (var i = 0; i < values.length; i++) 
				{
				 //alert('att id described value = '+values[i]);
				 	for(j=1;j<=countAttributes;j++)
				 	{ //alert('DB att value = '+document.getElementById('hdnProdAttVal_'+j).value)
					  if(document.getElementById('prodExpVal_'+j).value != "DROPDOWN" && document.getElementById('prodExpVal_'+j).value != "LOV" && values[i] == document.getElementById('hdnProdAttVal_'+j).value)
						{							
									document.getElementById('prodAttVal_'+j).value = totalAmount;							
						}
					}
				}
			}
		}	
}

function checkTotalAmount()
{
		var chkChargeLine;
		var totalCPeriod=0;
		chkChargeLine=document.getElementsByName('chkSelectChargeDetail')
			for(i=0;i<chkChargeLine.length;i++)
			{
				var chkboxCharge=chkChargeLine[i];
				var index=chkboxCharge.value;
				if(document.getElementById("ddCType"+index).value==1)
				{
					if(document.getElementById("txtCEndDate"+index).value=="BTD")
					{
						if(parseInt(document.getElementById("txtEndMonth"+index).value) < parseInt(document.getElementById("txtStartMonth"+index).value))
						{	
						    var lineno=index-1;
							alert('End Date Month Should be greater than Start Date Month IN Line No:' + lineno);
							return false;
				        }
					}
				 }  
				totalCPeriod =parseFloat(totalCPeriod) + parseFloat(document.getElementById("txtCPeriod"+index).value);
				if(parseFloat(document.getElementById("txtCPeriod"+index).value)>parseFloat(document.getElementById("txtCntrtMonths").value))
				{
					alert('Charge Period should not be greater than Contract Period');
					return false;
				}
			}		
}

	function initFieldValidation()
	{
 			var myForm=document.getElementById('productCatelog');
			var ctrlArry = new Array();
			
           	ctrlArry[0] =  myForm.txtPoId;
			ctrlArry[1] =  myForm.txtPODetailNo;
			ctrlArry[2] =  myForm.txtBillingPODate;
			ctrlArry[3] =  myForm.txtCntrtMonths;
			ctrlArry[4] =  myForm.txtBillingAC;			
			ctrlArry[5] =  myForm.txtBillingCP;
			ctrlArry[6] =  myForm.txtCur;
			ctrlArry[7] =  myForm.txtEntity;
			ctrlArry[8] =  myForm.txtBillingMode;
			ctrlArry[9] =  myForm.txtBillingformat;
			ctrlArry[10] =  myForm.txtLicenceCo;
			ctrlArry[11] =  myForm.txtBillingLevelNo;
			ctrlArry[12] =  myForm.txtNoticePeriod;
			ctrlArry[13] =  myForm.txtBillingType;
			ctrlArry[14] =  myForm.txtTaxation;
			ctrlArry[15] =  myForm.txtCmtPeriod;
			ctrlArry[16] =  myForm.txtBillingLvl;
			ctrlArry[17] =  myForm.txtPenaltyClause;
			ctrlArry[18] =  myForm.bbPrimaryBCP;
			ctrlArry[19] =  myForm.ddPrimaryLocType;
			ctrlArry[20] =  myForm.ddSecondaryLocType;
			ctrlArry[21] =  myForm.ddPrimaryBCP;
			ctrlArry[22] =  myForm.ddSecondaryBCP;
			ctrlArry[23] =  myForm.ddPNLocation;
			ctrlArry[24] =  myForm.ddSNLocation;
			ctrlArry[25] =  myForm.txtFAddress;
			ctrlArry[26] =  myForm.txtToAddress;
			ctrlArry[27] =  myForm.txtStore;
			ctrlArry[28] =  myForm.txtHtype;
			ctrlArry[29] =  myForm.txtform;
			ctrlArry[30] =  myForm.txtTSale;
			ctrlArry[31] =  myForm.txtNSale;
			ctrlArry[32] =  myForm.txtdispatchAddress;
			ctrlArry[33] =  myForm.txtStartDateLogic;
			ctrlArry[34] =  myForm.txtEndDateLogic;
			ctrlArry[35] =  myForm.txtStartDate;
			ctrlArry[36] =  myForm.txtEndDate;
			ctrlArry[37] =  myForm.txtInterestRate;
			ctrlArry[38] =  myForm.txtPrincipalAmount;
			ctrlArry[39] =  myForm.txtHStartMonth;
			ctrlArry[40] =  myForm.txtHStartDays; 
			ctrlArry[41] =  myForm.txtHEndMonth;
			ctrlArry[42] =  myForm.txtHEndDays; 
			ctrlArry[43] =  myForm.txtHExtMonth;
			ctrlArry[44] =  myForm.txtHExtDays; 
			ctrlArry[45] =  myForm.txtHExtDate; 
			//[003]start 
			ctrlArry[46] =  myForm.txtDispatchContactName;
			//[003]end
			//WARRANTY CLAUSE ADDED BY MANISHA START
			ctrlArry[47] =  myForm.txtWarrantClause;
			//WARRANTY CLAUSE ADDED BY MANISHA END
			//bcp details for services ADDED BY MANISHA START
			ctrlArry[48] =  myForm.bbPrimaryBCPService;
			//bcp details for services ADDED BY MANISHA end
			//satyapan By nagarjuna
			ctrlArry[49] =  myForm.txtOSPTagging;
			ctrlArry[50] =  myForm.txtOSPRegNo;
			ctrlArry[51] =  myForm.txtOSPRegDate;
			//satyapan By Nagarjuna
			
			var callerWindowObj = dialogArguments;
			fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry);
	}
	function setServiceSummaryAttributes()
	{
		var callerWindowObj = dialogArguments;

		if(callerWindowObj.serviceSummaryList.list[0].feildName=="ServiceSummary"){
			if(callerWindowObj.serviceSummaryList.list[0].isReadOnly=="1")
			{
					isServiceReadOnly="1";
			}
		}
	}
	function fieldRoleMappingValidation(lstFieldList,ctrlArry)
	{  
	     	var myForm=document.getElementById('productCatelog')
			for (iField = 0 ; iField < lstFieldList.list.length; iField++)
    		{  
	 			for(iCtrl=0;iCtrl< ctrlArry.length;iCtrl++)
				{				
					  if(ctrlArry[iCtrl].name==undefined)
					  {
						  for(ictrlName=0;ictrlName<ctrlArry[iCtrl].length;ictrlName++)
						  {					  
						   ctrl = ctrlArry[iCtrl];
						   ctrlName = ctrl[ictrlName].name;	
							if(ctrlName==lstFieldList.list[iField].feildName)
							{
								if(lstFieldList.list[iField].isReadOnly=="1")
								{								
									if(ctrl[ictrlName].type=="select-one")
										ctrl[ictrlName].disabled  = true;
									else
									if(ctrl[ictrlName].type=="button")
									{
										ctrl[ictrlName].disabled="disabled";
									}	
									else
									    ctrl[ictrlName].readOnly  = true;		
								}
								else
								{ 			
									if(ctrl[ictrlName].type=="select-one")
										ctrl[ictrlName].disabled  = false;
									else
									    ctrl[ictrlName].readOnly  = false;		
								}
								ctrl[ictrlName].isRequired = lstFieldList.list[iField].isMand;
								if(ctrlArry[iCtrl].isRequired=="0")
									ctrlArry[iCtrl].className = "inputBorder2";
							else								
									ctrlArry[iCtrl].className = "inputBorder1";
								ctrl[ictrlName].Disp = lstFieldList.list[iField].fieldLabel;
							}
						  }
					  }
					  else	  
					  { 
					    ctrlName = ctrlArry[iCtrl].name;
						if(ctrlName==lstFieldList.list[iField].feildName)
						{
							if(lstFieldList.list[iField].isReadOnly=="1")
							{	
								if(ctrlArry[iCtrl].type=="select-one")
									ctrlArry[iCtrl].disabled  = true;
								else
								if(ctrlArry[iCtrl].type=="button")
									ctrlArry[iCtrl].disabled="disabled";
								else
								    ctrlArry[iCtrl].readOnly  = true;		
							}
							else
							{
								if(ctrlArry[iCtrl].type=="select-one")
									ctrlArry[iCtrl].disabled  = false;
								else
								    ctrlArry[iCtrl].readOnly  = false;		
						
							}
							ctrlArry[iCtrl].isRequired = lstFieldList.list[iField].isMand;
							if(ctrlArry[iCtrl].isRequired=="0")
									ctrlArry[iCtrl].className = "inputBorder2";
							else								
									ctrlArry[iCtrl].className = "inputBorder1";
							ctrlArry[iCtrl].Disp = lstFieldList.list[iField].fieldLabel;
						}
					  }						
				}	
			}
	}

function setPAandIR()
{
	var myLocalForm = document.getElementById('productCatelog');
	if(document.getElementById("txtTSale").value==2){		
		document.getElementById("txtPrincipalAmount").disabled=false;
		document.getElementById("txtInterestRate").disabled=false;
	}
	else
	{
		document.getElementById("txtPrincipalAmount").disabled=true;
		document.getElementById("txtInterestRate").disabled=true;
	}
	
}
function removeAllOptions(selectbox)
{
var i;
for(i=selectbox.options.length-1;i>0;i--)
{
selectbox.remove(i);
}
}

function setBillingMode()
{
	var billFormat=document.getElementById("txtBillingformat").value;
	if(billFormat == 3)
	{
		document.getElementById("txtBillingMode").selectedIndex=2 //Setting Index For Automatic
  	}
	else if(billFormat == 1 || billFormat == 2)
	{
   		document.getElementById("txtBillingMode").selectedIndex=1 //Setting Index For Manual
	}
	else if(billFormat == 21)
	{
   		document.getElementById("txtBillingMode").selectedIndex=2 //Setting Index For Automatic
	}
    else
	{
   		document.getElementById("txtBillingMode").selectedIndex=0; //Select
  	}
}

function setTypeofSale(){
gbPrevBillFormatIndexflag=1;
	if(document.getElementById('hdnHardwareInfo').value==1)
	{		
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		if(chkChargeLine.length>0)
		{			
				alert('Please delete all charges line first');						
				document.getElementById("txtBillingformat").selectedIndex=vPrevBillFormat;			
				return false;
		}
	}
    setBillingMode();
    var myLocalForm = document.getElementById('productCatelog');
	var billFormat=document.getElementById("txtBillingformat").value;
	if(billFormat!=2 && billFormat!=3)
	{
		return false;
	}
	    var comboSaleType = document.getElementById("txtTSale");	    
	    removeAllOptions(document.getElementById("txtTSale"));
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        var saleType = 0;
        saleType= jsonrpc.processes.typeOfSale(billFormat);
       
        for(j=0;j<saleType.list.length;j++)
	    { 
	    	var option = document.createElement("option");
	   		option.text = saleType.list[j].saleTypeName;
			option.value = saleType.list[j].saleTypeId;
			option.title = saleType.list[j].saleTypeName;
			try 
			{
				comboSaleType.add(option, null); //Standard
			}
			catch(error) 
			{
				comboSaleType.add(option); // IE only
			}
	    }
	     document.getElementById("txtTSale").selectedIndex=0;	
}

function getServiceAttributes()
{
   document.getElementById('lineItemServiceSummary').style.display='none';
   document.getElementById('BillingNChargeInfo').style.display='none';
   document.getElementById('BillingInfo').style.display='none';
   document.getElementById('serviceType').style.display='block';
   document.getElementById('ChargeDetails').style.display='none';
   document.getElementById('saveImage').style.display='block';
   document.getElementById('HardwareDetails').style.display='none';
   document.getElementById('ServiceLocation').style.display='none';
 document.getElementById('Components').style.display='none';
   document.getElementById('txtBCPAddressSuggestService').className = "dropdownMargin";
   document.getElementById('txtBCPAddressSuggestService').disabled=false;
    $("#txtBCPAddressSuggestService").autocomplete( "enable" );
		$("#bcpAutoSuggestServiceLinkId").show();

	//lawkush start
    // added condition show account name (party name) on productcatelog 
   var callerWindowObj = dialogArguments;
   var accountName=callerWindowObj.document.getElementById('accountName').value;
					
   //lawkush End

	if(document.getElementById('hdnComponentInfo').value==1){
		document.getElementById('Components').style.display='block';
		var compSec = new Array();
		compSec[0]="COMPONENT";
		compSec[1]="Components";
		if(null == roleWiseSectionDetail){
    		var callerWindowObj = dialogArguments;
    		var roleName = callerWindowObj.gb_roleID;
    		roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    	}
		enableDisableCommercialFields(compSec, roleWiseSectionDetail, lineType);
   }
   document.getElementById("serviceproductid").value='';
   
   if(document.getElementById('hdnServiceSummary').value==1)
   {
   		document.getElementById('lineItemServiceSummary').style.display='block';
		var tr, td, i, j, oneRecord;
		var test,test1;
		var mytable = document.getElementById('ServiceList');
		
		var iCountRows = mytable.rows.length;
		
		for (i = 0; i <  iCountRows; i++)
		{
		    mytable.deleteRow(0);
		}   
 
		var jsData =			
		{
			serviceDetailID:document.getElementById('serviceDetailID').value
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var sessionid ='<%=request.getSession().getId() %>';
		test = jsonrpc.processes.populateServiceAttributeList(jsData,sessionid);
		//Puneet fetching the service section commercial allowed for the role
	 	counter=1;
	 	document.getElementById('hdnSeriveAttCounter').value=test.list.length;
	 	var parentCounter=0;
  		for (i = 0 ; i < test.list.length; i++,counter++)
  		{
			var str;
			var tblRow=document.getElementById('ServiceList').insertRow();
									
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.width="50%"
			tblcol.vAlign="top";
			tblcol.style.fontSize="12px";
			tblcol.style.fontWeight="bold";
			if(test.list[i].prodAttributeLabel == 'B0.HW CircuitID')
			{
			str = "<label><center>label</center></label></br>";
			str=str + test.list[i].prodAttributeLabel;
			}
			else if(test.list[i].prodAttributeLabel == 'C1.HW Circuit ID')
			{
			str = "<label><center>label</center></label></br>";
			str=str + test.list[i].prodAttributeLabel;
			}
			else 
			str= test.list[i].prodAttributeLabel;
			CellContents = str;
			tblcol.innerHTML = CellContents;
		//START[13082012]AutoSuggest for BCP	
		
			if(test.list[i].prodExpectedValue=='DROPDOWN')
			{
			   var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(test.list[i].isServiceSummReadonly==1){
						str="<input type='hidden' isSummaryReq='1' class='dropdownMargin' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' isSummaryReq='1' style='width:235px' disabled='disabled' class='dropdownMargin1' readonly='true' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y'  readOnly='true' /><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";						
				}else{
					if(isServiceReadOnly=="1")
				{
			//[002]start
					if(test.list[i].isServiceSummMandatory==1)
						str="<input type='hidden' isSummaryReq='1' class='dropdownMargin' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' isSummaryReq='1' style='width:235px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y'  readOnly='true' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					else
						str="<input type='hidden' isSummaryReq='0' class='dropdownMargin1' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:235px' class='dropdownMargin1' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y'  readOnly='true' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				else
				if(test.list[i].isServiceSummMandatory==1)
					str="<input type='hidden' isSummaryReq='1' class='dropdownMargin' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' isSummaryReq='1' style='width:235px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y' readOnly='true' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				else
					str="<input type='hidden' isSummaryReq='0' class='dropdownMargin1'  id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:235px' class='dropdownMargin1' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y' readOnly='true' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
			}
			//[002]end
				str = str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				if(test.list[i].isParentAtt==1){
					parentCounter=counter;
				}else if(test.list[i].parntAttId!=0){
					parentCounter=parentCounter;//if child then set parent counter as parent counter
				}else{
					parentCounter=0;
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				for(j=0;j<test.list[i].serviceSummary.list.length;j++)
				{
					/*var combo = document.getElementById("prodAttVal_"+counter);
					var option = document.createElement("option");
					option.text = test.list[i].serviceSummary.list[j].serviceSummaryText;
					option.value = test.list[i].serviceSummary.list[j].serviceSummaryValues;
					option.title = test.list[i].serviceSummary.list[j].serviceSummaryText;
				try 
					{
						combo.add(option, null); //Standard
					}
					catch(error) 
					{
						combo.add(option); // IE only
					}
					*/
					var callerWindowObj = dialogArguments;
					var subChangeType = callerWindowObj.document.getElementById('hdnSubChangeTypeID').value;
					var ChangeType = callerWindowObj.document.getElementById('HdnChangeTypeID').value;										
					if(subChangeType == 8)
					{ 
						if(test.list[i].serviceSummary.list[j].serviceSummaryText == "Downgrade Order" || test.list[i].serviceSummary.list[j].serviceSummaryText == "Disconnection Order" || test.list[i].serviceSummary.list[j].serviceSummaryText == "New Order")
						{								
							combo.remove(2);
						}	
					}
					
					if(subChangeType == 8 && test.list[i].serviceSummary.list[j].serviceSummaryText == "Upgrade Order")	
					{
						combo.selectedIndex=1;						
					}	
					
					if(subChangeType == 9)
					{ 
						if(test.list[i].serviceSummary.list[j].serviceSummaryText == "Upgrade Order") 
							{								
								combo.remove(1);
						
							}	
						else 
						{
							if(test.list[i].serviceSummary.list[j].serviceSummaryText == "Disconnection Order" || test.list[i].serviceSummary.list[j].serviceSummaryText == "New Order")
								{
									combo.remove(2);
								}
						}	
					}
					
					if(subChangeType == 9 && test.list[i].serviceSummary.list[j].serviceSummaryText == "Downgrade Order")	
					{
						combo.selectedIndex=1;						
					}
					//[020] Start
					if(test.list[i].serviceSummary.list[j].serviceSummaryValues == test.list[i].defValue )
					{
						document.getElementById("autoSuggProdAttVal_"+counter).value=test.list[i].serviceSummary.list[j].serviceSummaryText;
						document.getElementById("prodAttVal_"+counter).value=test.list[i].serviceSummary.list[j].serviceSummaryValues;
					}
					//[020] End
				}	
			}//changes for clientside and serverside validation from database driven for maxlength in service summary section  by Raghu
			//START[13082012]AutoSuggest for BCP
			 //added LOV condition for autosuggestion in service summary section  by Lawkush Start
			else if(test.list[i].prodExpectedValue=='LOV')
			{
			
			   var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(test.list[i].isServiceSummReadonly==1){
						str="<input type='hidden' isSummaryReq='0' class='dropdownMargin1' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:235px' disabled='disabled' class='dropdownMargin1' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y' /><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";						
				}else{
				if(isServiceReadOnly=="1")
				{
				//[002]start
					if(test.list[i].isServiceSummMandatory==1)
					{
						str="<input type='hidden' isSummaryReq='1' class='dropdownMargin' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:235px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
					else
					{
						str="<input type='hidden' isSummaryReq='0' class='dropdownMargin1' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:235px' class='dropdownMargin1' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
				}
				else
				if(test.list[i].isServiceSummMandatory==1)
				{
					str="<input type='hidden' isSummaryReq='1' class='dropdownMargin' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:235px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				else
				{
					str="<input type='hidden' isSummaryReq='0' class='dropdownMargin1'  id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:235px' class='dropdownMargin1' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					//[002]end
				}
			}
				str = str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				if(test.list[i].isParentAtt==1){
					parentCounter=counter;
				}else if(test.list[i].parntAttId!=0){
					parentCounter=parentCounter;//if child then set parent counter as parent counter
				}else{
					parentCounter=0;
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
			}
			else if(test.list[i].prodExpectedValue=='LINK')
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.width="59%"
				tblcol.vAlign="top";
				if(test.list[i].isServiceSummMandatory==1)
				{
					str="<input onmouseover='getTip(value)' value='"+test.list[i].defValue+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder1' style='width:150px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				else
				{
					str="<input onmouseover='getTip(value)' value='"+test.list[i].defValue+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder2' style='width:150px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				
				}
				str = str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}	
			 //added LOV condition for autosuggestion in service summary section  by Lawkush End
			else if(test.list[i].prodExpectedValue == 'datetime')
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.width="50%"
				tblcol.vAlign="top";
				//strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"datetime\"){if(this.value.length > 0){return checkdate(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"email\"){if(this.value.length > 0){return  validateEmail(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"varchar\".toUpperCase()){if(this.value.length > 0){return ValidateTextFieldM6(this,pathM6)}}";
				strA="javascript: return false";
				if(test.list[i].isServiceSummReadonly==1){
						str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' disabled='disabled' class='dropdownMargin1'  value='"+test.list[i].defValue+"' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";						
				}else{
				if(isServiceReadOnly=="1")
				{
					str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true'  value='"+test.list[i].defValue+"' style='width:235px' class='inputBorder1' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				else if(test.list[i].isServiceSummMandatory==1)
				{
				    str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' value='' readOnly ='true' isSummaryReq='1' value='"+test.list[i].defValue+"' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar('forms[0].prodAttVal_" + counter + "');\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				else
				{
				    if(test.list[i].attServiceName=="SaaS")
					{
						str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' readOnly ='false'  value='"+test.list[i].defValue+"' style='width:235px' class='inputBorder2' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
					else
					{
						if(test.list[i].prodAttVal==null || test.list[i].prodAttVal=="0")
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' isSummaryReq='0'  value='' class='inputBorder2' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(forms[0].prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "' id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "' id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "'  id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_" + counter + "' id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						else							
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' isSummaryReq='0'  value='" + test.list[i].prodAttVal + "' class='inputBorder2' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(forms[0].prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "' id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "' id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "' id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_" + counter + "' id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
					}
				}
				str = str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			else{
			
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.width="50%"
				tblcol.vAlign="top";
				strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"datetime\".toUpperCase()){if(this.value.length > 0){return checkdate(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"email\".toUpperCase()){if(this.value.length > 0){return  validateEmail(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"varchar\".toUpperCase()){if(this.value.length > 0){return ValidateTextFieldM6(this,pathM6)}}";
				if(test.list[i].isServiceSummReadonly==1){
						str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true'  maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].defValue+"' style='width:235px' disabled='disabled' class='dropdownMargin1' readonly='true' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";	
				}else{
				if(isServiceReadOnly=="1")
				{
					str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true'  maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].defValue+"' style='width:235px' class='inputBorder1' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				else if(test.list[i].isServiceSummMandatory==1)
				{
				   if(test.list[i].prodAttributeID=="1041")
					  str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+test.list[i].defValue+"' dmxtotal='1' isSummaryReq='1' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				   else 
				   {
				   		if(test.list[i].prodAttributeID == "2470")
						{
							str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].defValue+"' dmxtotal='0' isSummaryReq='1' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				        }				   
						else if(test.list[i].prodAttributeID == "2476")
						{
							str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].defValue+"' dmxtotal='0' isSummaryReq='1' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				    	}
				    	//lawkush 
						//added condition show account name (party name) on productcatelog 
						//added attribute for cinema chain name and readonly also--kalpana
						else if (test.list[i].prodAttributeID == "5000179" || test.list[i].prodAttributeID == "5000183"  || test.list[i].prodAttributeID == "5000188" )
						{
							str="<input readonly='true' onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' value='" +accountName+"' onmouseout='UnTip()' type='text'  onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='1' dmxtotal='0' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onblur='"+ strA +"' zeroValueAllowed='Y' > <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "' id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "' id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "' id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_" + counter + "' id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					
						}							
						else
						{
				     		str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+test.list[i].defValue+"' dmxtotal='0' isSummaryReq='1' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				   		}
				   }
				}
				else
				{
				   if(test.list[i].prodAttributeID=="1041")
					 str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+test.list[i].defValue+"' dmxtotal='1' isSummaryReq='0' class='inputBorder2' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				   else
				     {
				     	if(test.list[i].prodAttributeID == "2470")
						{
							str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' maxlength='"+test.list[i].prodAttMaxLength+"' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' value='"+test.list[i].defValue+"' dmxtotal='0' isSummaryReq='0' class='inputBorder2' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				        }				   
						else if(test.list[i].prodAttributeID == "2476")
						{
							str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' maxlength='"+test.list[i].prodAttMaxLength+"' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' value='"+test.list[i].defValue+"' dmxtotal='0' isSummaryReq='0' class='inputBorder2' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				    	}
				    	//lawkush 
						//added condition show account name (party name) on productcatelog 
						//added attribute for cinema chain name and readonly also--kalpana
						else if (test.list[i].prodAttributeID == "5000179" || test.list[i].prodAttributeID == "5000183"  || test.list[i].prodAttributeID == "5000188" )
						{
						
								str="<input readonly='true' onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)'  onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  value='"+accountName+"'  dmxtotal='0' class='inputBorder2' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onblur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "' id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "' id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "' id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_" + counter + "' id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						
						}							
						else
						{
				     			str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' maxlength='"+test.list[i].prodAttMaxLength+"' type='text' value='"+test.list[i].defValue+"' dmxtotal='0' isSummaryReq='0' class='inputBorder2' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				   		}
				     }
				     //str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+test.list[i].defValue+"' dmxtotal='0' isSummaryReq='0' class='inputBorder2' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}//[002]end
			}
				str = str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			
			// added by manisha for service summary validation start defect no : 19032013001
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.width="50%"
				tblcol.vAlign="top";
				str="<input type='hidden' class='inputBorder2' name='txt_prod_attvalidation_" + counter + "' id='txt_prod_attvalidation_" + counter + "' value='"+test.list[i].for_validation+"' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				// added by manisha for service summary validation end
			
	    }
	      var serSec = new Array();
	serSec[0]="SERVICESUMMARY";
	serSec[1]="tblServiceSummary";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	enableDisableCommercialFields(serSec, roleWiseSectionDetail, lineType);
	    
	}
	if(document.getElementById('hdnChargeInfo').value==1)
    {
		document.getElementById('BillingNChargeInfo').style.display='block';
    	document.getElementById('ChargeDetails').style.display='block';
	}
	if(document.getElementById('hdnBillingInfo').value==1)
	{
   		document.getElementById('BillingNChargeInfo').style.display='block';
		document.getElementById('BillingInfo').style.display='block';
	     var tr, td, i, j, oneRecord;
	     var taxationList;
	     var combo = document.getElementById("txtTaxation");
	     var iCountRows = combo.length;
	  
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
	
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    taxationList = jsonrpc.processes.populateTaxationDetails();
	    for(j=0;j<taxationList.list.length;j++)
	    {
		   	var option = document.createElement("option");
		  		option.text = taxationList.list[j].taxationName;
			option.value = taxationList.list[j].taxationId;
			option.title = taxationList.list[j].taxationName;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	    
		loadBillingLevel();
		//[021] start
		setDefaultBillingLvlForChannel();
		//[021] end
		loadBillingFormat();

		 // *******************For Billing Mode Combo Filling by Ajax Start************************
	 	loadBillingMode();
	 	
	 // *******************For Billing Mode Combo Filling by Ajax End************************
		//START[13082012]AutoSuggest for BCP
	  	/*  var combo3 = document.getElementById("bbPrimaryBCP");
	    var iCountRows3 = combo3.length;
		var i;
 
	    for (i = 1; i <  iCountRows3; i++)
	    {
	       combo3.remove(1);
	    }
	    */
	    //END[13082012]AutoSuggest for BCP
	   	document.getElementById("changeReason").selectedIndex=0;
	   	document.getElementById("txtBillingMode").selectedIndex=0;
	   	document.getElementById("txtBillingLevelNo").value="0";
	   	document.getElementById("txtBAddress1").value="";
	   	document.getElementById("txtBAddress2").value="";
	   	document.getElementById("txtBAddress3").value="";
	   	document.getElementById("txtBAddress4").value="";
	   	document.getElementById("txtBCity").value="";
	   	document.getElementById("txtBState").value="";
	   	document.getElementById("txtBPincode").value="";
	   	document.getElementById("txtBCountry").value="";
	   	document.getElementById("txtBContactNo").value="";
	   	document.getElementById("txtBEmailId").value="";
	   	document.getElementById("designation").value="";
	   	document.getElementById("lst_No").value="";
	   	document.getElementById("lst_Date").value="";
	   	document.getElementById('txtBContactName').value = "";
	   	//011	Start
	   	document.getElementById('txtCircle').value = "";
	   	//011	End	   	
	   	//START[13082012]AutoSuggest for BCP
	    /*var jsData3 =			
	    {
			accountID:document.getElementById('txtBillingAC').value
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
        var test3 = jsonrpc.processes.populateBCP(jsData3);
          var j;
        for(j=0;j<test3.list.length;j++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = test3.list[j].bcpName;
			option.value = test3.list[j].bcpID;
			option.title = test3.list[j].bcpName;
			try 
			{
				combo3.add(option, null); //Standard
			}
			catch(error) 
			{
				combo3.add(option); // IE only
			}
	    }
	    if(test3.list.length==1)
	    {
	    	document.getElementById("bbPrimaryBCPID").selectedIndex=1;
	    	FetchBillingDetails();
	    }*/
	    //END[13082012]AutoSuggest for BCP
		var tr, td, i, j, oneRecord;
	     var billingTypeList;
	     var combo = document.getElementById("txtBillingType");
	     var iCountRows = combo.length;
	  
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
	
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    billingTypeList = jsonrpc.processes.populateBillingTypeDetails();
	    for(j=0;j<billingTypeList.list.length;j++)
	    {
	    	
		   	var option = document.createElement("option");
		  	option.text = billingTypeList.list[j].billingTypeName;
			option.value = billingTypeList.list[j].billingTypeId;
			option.title = billingTypeList.list[j].billingTypeName;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	      var tr, td, i, j, oneRecord;
	     var creditPeriodList;
	     var combo = document.getElementById("txtBillingCP");
	     var iCountRows = combo.length;
	  
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
	
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    creditPeriodList = jsonrpc.processes.getCreditPeriodDetails();
	    for(j=0;j<creditPeriodList.list.length;j++)
	    {
		   	var option = document.createElement("option");
		  	option.text = creditPeriodList.list[j].creditPeriodName;
			option.value = creditPeriodList.list[j].creditPeriodId;
			option.title = creditPeriodList.list[j].creditPeriodName;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
		document.getElementById('txtCmtPeriod').value="";
		document.getElementById('txtPenaltyClause').value="";
		
		    var test;
    var combo = document.getElementById("txtPODetailNo");
	contractVal=new Array();  
    chargeVal=new Array(); 
    poDateVal=new Array(); 
   var iCountRows = combo.length;
  
   for (i = iCountRows-1; i >=0; i--)
   {
       combo.remove(i);
   }
	<%String poNumber1=request.getParameter("poNumber");%>
	var poNumber1='<%=poNumber1%>';
    var jsData =			
    {
    	serviceDetailID:document.getElementById("serviceDetailID").value,
		poNumber:poNumber1
	};
	
	changeTypeId=("<%=request.getParameter("changeTypeID")%>");
	document.getElementById('hdnchangetypeid').value=changeTypeId;
	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populatePODetailsNoForProduct(jsData);
		    if(test.list.length==0)
		    {
		    var option = document.createElement("option");
		   		option.text="No Mapping Exists";
		   		option.title="No Mapping Exists";
		   		option.value = 0;
		   		try 
				{
					combo.add(option, null); //Standard
				}
				catch(error) 
				{
					combo.add(option); // IE only
				}
		    }
		   else
		   {
		   				var option = document.createElement("option");
					   		option.text = "Select Cust PO Detail No";
					   		option.title = "Select Cust PO Detail No";
		    				option.value = 0;
		    				try 
							{
								combo.add(option, null); //Standard
							}
							catch(error) 
							{
								combo.add(option); // IE only
							}
		    				
					    for(j=0;j<test.list.length;j++)
					    {
					    	var option = document.createElement("option");
					    	if(test.list[j].poRemarks==null ||test.list[j].poRemarks=="")					    	
								option.text = test.list[j].custPoDetailNo;
							else
					   		option.text = test.list[j].custPoDetailNo+"-"+test.list[j].poRemarks;
					   		option.title = test.list[j].custPoDetailNo+"-"+test.list[j].poRemarks;
							option.value = test.list[j].poDetailNo;
							chargeVal[j]= test.list[j].periodsInMonths;
							contractVal[j]= test.list[j].periodsInMonths;
							//added by Lawkush
							//poDateVal[j]= test.list[j].poDate;
							poDateVal[j]= test.list[j].custPoDate;
							
							try 
							{
								combo.add(option, null); //Standard
							}
							catch(error) 
							{
								combo.add(option); // IE only
							}
							
							if(test.list[j].defaultPO == 1)
							{
								gbDefaultPOId=test.list[j].poDetailNo;
								document.getElementById("txtPODetailNo").value=gbDefaultPOId;
								fnFetchEntity();
							}
						}
			}
  			var billingSec = new Array();
	billingSec[0]="BILLING";
	billingSec[1]="BillingInfo";
		
	var chargeSec = new Array();
	chargeSec[0]="CHARGE";
	chargeSec[1]="ChargeDetails";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	var sectiondisabled=enableDisableCommercialFields(billingSec, roleWiseSectionDetail, lineType);
	enableDisableCommercialFields(chargeSec, roleWiseSectionDetail, lineType);
	if(sectiondisabled==true){// added by manisha o2c start
		setDefaultValueInProductCatalog(billingSec[1], null, roleName, "Change", callerWindowObj.document.getElementById("accNo").value, document.getElementById('hdnHardwareInfo').value);
	}// added by manisha o2c end
	}else{	
		document.getElementById('txtBillingMode').value="0";
		document.getElementById('txtCmtPeriod').value=0;
		document.getElementById('txtPenaltyClause').value="0";
	}
	if(document.getElementById('hdnHardwareInfo').value==1)//if Hardware
    {
    	$("#txtBCPAddressSuggestService").attr('disabled', 'disabled');		
    	document.getElementById('txtBCPAddressSuggestService').className="inputDisabled";
		$("#bcpAutoSuggestServiceLinkId").hide();
    	document.getElementById('HardwareDetails').style.display='block';
    	document.getElementById('txtHAddress1').value="";
    	document.getElementById('txtHAddress2').value="";
    	document.getElementById('txtHAddress3').value="";
    	document.getElementById('txtHCityName').value="";
    	document.getElementById('txtHStateName').value="";
    	document.getElementById('txtHPincode').value="";
    	document.getElementById('txtHCountryName').value="";
    	document.getElementById('txtHPhnNo').value="";
		resetBCPAddress();
    	var combo1 = document.getElementById("txtStore");
    	
		var licenceCo=Number(document.getElementById("txtLicenceCo").value);
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
        test1 = jsonrpc.processes.populateStoreList(licenceCo);
          removeAllOptions(document.getElementById("txtStore"));  
        for(j=0;j<test1.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test1.list[j].storeName;
			option.value = test1.list[j].storeID;
			option.title = test1.list[j].storeName;
			try 
			{
				combo1.add(option, null); //Standard
			}
			catch(error) 
			{
				combo1.add(option); // IE only
			}
	    }
	    
	    var comboHType = document.getElementById("txtHtype");
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        var HType = jsonrpc.processes.populateHardwareType();
          removeAllOptions(document.getElementById("txtHtype"));  
        for(j=0;j<HType.list.length;j++)
	    {
	    	var option = document.createElement("option");	    	
	   		option.text = HType.list[j].hardwaretypeName;
			option.value = HType.list[j].hardwaretypeId;
			option.title = HType.list[j].hardwaretypeName;
			try 
			{
				comboHType.add(option, null); //Standard
			}
			catch(error) 
			{
				comboHType.add(option); // IE only
			}
	    }
	    document.getElementById("txtHtype").value = 0;
	    
	     document.getElementById("txtHtype").selectedIndex=0;	
	    
	    var comboForms = document.getElementById("txtform");
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        var formType = jsonrpc.processes.formAvailable();
		removeAllOptions(document.getElementById("txtform"));   
        for(j=0;j<formType.list.length;j++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = formType.list[j].formName;
			option.value = formType.list[j].formId;
			option.title = formType.list[j].formName;
			try 
			{
				comboForms.add(option, null); //Standard
			}
			catch(error) 
			{
				comboForms.add(option); // IE only
			}
	    }
	    
		document.getElementById("txtform").selectedIndex=0;	
	    var comboSaleNature = document.getElementById("txtNSale");
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        var saleNature = jsonrpc.processes.natureOfSale();
          removeAllOptions(document.getElementById("txtNSale"));   
        for(j=0;j<saleNature.list.length;j++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = saleNature.list[j].saleNatureName;
			option.value = saleNature.list[j].saleNatureId;
			option.title = saleNature.list[j].saleNatureName;
			try 
			{
				comboSaleNature.add(option, null); //Standard
			}
			catch(error) 
			{
				comboSaleNature.add(option); // IE only
			}
	    }
	    
		document.getElementById("txtNSale").selectedIndex=0;	
	    var comboSaleType = document.getElementById("txtTSale");
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        var saleType = jsonrpc.processes.typeOfSale(document.getElementById("txtBillingformat").value);
       
		removeAllOptions(document.getElementById("txtTSale"));         
        for(j=0;j<saleType.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = saleType.list[j].saleTypeName;
			option.value = saleType.list[j].saleTypeId;
			option.title = saleType.list[j].saleTypeName;
			try 
			{
				comboSaleType.add(option, null); //Standard
			}
			catch(error) 
			{
				comboSaleType.add(option); // IE only
			}
	    }
	    document.getElementById("txtTSale").selectedIndex=0;	
		var test;
		
	    var combo11 = document.getElementById("txtStartDateLogic");
	    
	    var combo22 = document.getElementById("txtEndDateLogic");
	    //008 start
	    
	    var iCountcombo11Rows = combo11.length;
	  
	    for (i = 1; i <  iCountcombo11Rows; i++)
	    {
	       combo11.remove(1);
	    }
	    
	    var iCountcombo22Rows = combo22.length;
	  
	    for (i = 1; i <  iCountcombo22Rows; i++)
	    {
	       combo22.remove(1);
	    }
	    
	    //008 end
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
        test = jsonrpc.processes.populateHDateLogic();
		
		for(j=0;j<test.list.length;j++)
	    {
	    	if(test.list[j].section=='START')
	    	{
			    	var option = document.createElement("option");
			    	option.text = test.list[j].dateLogicValue;
					option.value = test.list[j].dateLogicValue;	
					option.title = test.list[j].dateLogicValue;		
					try 
					{
				
					combo11.add(option, null); //Standard
					}
					catch(error) 
					{
					combo11.add(option); // IE only
					}
			}
			
			if(test.list[j].section=='END')
	    	{
			    	var option = document.createElement("option");
			    	option.text = test.list[j].dateLogicValue;
					option.value = test.list[j].dateLogicValue;	
					option.title = test.list[j].dateLogicValue;		
					try 
					{
				
					combo22.add(option, null); //Standard
					}
					catch(error) 
					{
					combo22.add(option); // IE only
					}
			}
	    }	   
	
	     /*var combo2 = document.getElementById("txtdispatchAddress");
	    document.getElementById("txtdispatchAddress").value=0;	     	   
	    var jsData2 =			
	    {
			accountID:document.getElementById('txtBillingAC').value
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
        test2 = jsonrpc.processes.populateDispatchCode(jsData2);
         
        for(j=0;j<test2.list.length;j++)
	    {
	    	
	    	var option = document.createElement("option");
	    	option.text = test2.list[j].dispatchAddressName;
			option.value = test2.list[j].dispatchAddressID;		
			option.title = test2.list[j].dispatchAddressName;				
			try 
			{
		
			combo2.add(option, null); //Standard
			}
			catch(error) 
			{
			combo2.add(option); // IE only
			}
			if (test2.list[j].defaultDispatchAddForAccID >0) {
				document.getElementById("txtdispatchAddress").value = test2.list[j].defaultDispatchAddForAccID;
			}
	    }
	    getDispatchAddress();*/	
	    document.getElementById('txtform').value="";
		document.getElementById('txtHtype').value="";
		document.getElementById('txtNSale').value="";
		document.getElementById('txtTSale').value="";
		var hardwareSec = new Array();
	hardwareSec[0]="HARDWARE";
	hardwareSec[1]="HardwareDetails";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	enableDisableCommercialFields(hardwareSec, roleWiseSectionDetail, lineType);
    }
    else
    {	
    	document.getElementById('txtform').value="0";
		document.getElementById('txtHtype').value="0";
		document.getElementById('txtNSale').value="0";
		document.getElementById('txtTSale').value="0";
		document.getElementById('txtStartDateLogic').value="0";
		document.getElementById('txtEndDateLogic').value="0";
		document.getElementById('txtStartDate').value="0";
		document.getElementById('txtEndDate').value="0";
    }
    
    	var callerWindowObj = dialogArguments;
   		var ctr=callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;
	   	
	 	serviceTypeId=callerWindowObj.document.getElementById('txtServiceTypeID'+ctr).value ;
	
	 	document.getElementById('hdnservicetypeid').value=serviceTypeId;
	 	
	if(document.getElementById('hdnLocationInfo').value==1)
	{
		document.getElementById('ServiceLocation').style.display='block';
    	document.getElementById('txtPAddress').value="";
    	document.getElementById('txtSAddress').value="";
    	document.getElementById('txtPAddress1').value="";
    	document.getElementById('txtPAddress2').value="";
    	document.getElementById('txtPAddress3').value="";
    	document.getElementById('txtPAddress4').value="";
    	document.getElementById('txtPCity').value="";
    	document.getElementById('txtPState').value="";
    	document.getElementById('txtPPincode').value="";
    	document.getElementById('txtPCountry').value="";
    	document.getElementById('txtSAddress1').value="";
    	document.getElementById('txtSAddress2').value="";
    	document.getElementById('txtSAddress3').value="";
    	document.getElementById('txtSAddress4').value="";
    	document.getElementById('txtSCity').value="";
    	document.getElementById('txtSState').value="";
    	document.getElementById('txtSPincode').value="";
    	document.getElementById('txtSCountry').value="";
    	
    	// Start[006]
    	
    	/*
    	
    	
	    var combo3 = document.getElementById("ddPrimaryBCP");
	    var iCountRows3 = combo3.length;
	    for (i = 1; i <  iCountRows3; i++)
	    {
	       combo3.remove(1);
	    }
	    var jsData3 =			
	    {
			accountID:document.getElementById('txtBillingAC').value
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
        var test3 = jsonrpc.processes.populateBCPWithDispatch(jsData3);
        for(j=0;j<test3.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test3.list[j].bcpName;
			option.value = test3.list[j].bcpID;
			option.title = test3.list[j].bcpName;
			try 
			{
				combo3.add(option, null); //Standard
			}
			catch(error) 
			{
				combo3.add(option); // IE only
			}
	    }
	    var combo4 = document.getElementById("ddSecondaryBCP");
	    var iCountRows2 = combo4.length;
  
	    for (i = 1; i <  iCountRows2; i++)
	    {
	       combo4.remove(1);
	    }
	    var jsData4 =			
	    {
			accountID:document.getElementById('txtBillingAC').value
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
        var test4 = jsonrpc.processes.populateBCPWithDispatch(jsData4);
         
        for(j=0;j<test4.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test4.list[j].bcpName;
			option.value = test4.list[j].bcpID;
			option.title = test4.list[j].bcpName;
			try 
			{
				combo4.add(option, null); //Standard
			}
			catch(error) 
			{
				combo4.add(option); // IE only
			}
	    }

	*/
	    // End[006]

 		if(serviceTypeId==4){
 		
	 	 document.getElementById('allLoc').style.display='block';
	   	 document.getElementById('onlyNetworkLoc').style.display='none';
	   	
	   	 if(document.getElementById("ddSNLocation").length>1)
	   	 {
		   document.getElementById("ddSNLocation").selectedIndex=1;	   	 
	   	 }

		  var jsData8 =			
		    {
				plocationCode:document.getElementById("ddSNLocation").value
			};
		   jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		 
	  	   var test8 = jsonrpc.processes.populateNSLocationAddress(jsData8);
	      
	       if(test8.list.length !=0){	    
		      document.getElementById('txtSAddress').value = test8.list[0].stxtSAddress;
		      }
	       document.getElementById('SecNetworkLocation').style.display='none';
	 	}
	 	else
	 	{ 	
	 	
	   	document.getElementById('allLoc').style.display='block';
	   	document.getElementById('onlyNetworkLoc').style.display='none';
	 	}
	 	var locSec = new Array();
	locSec[0]="SERVICELOCATION";
	locSec[1]="ServiceLocation";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	enableDisableCommercialFields(locSec, roleWiseSectionDetail, lineType);
	}
	
	document.getElementById("txtHtype").selectedIndex=0;
	document.getElementById("txtform").selectedIndex=0;
	document.getElementById("txtNSale").selectedIndex=0;
	document.getElementById("txtTSale").selectedIndex=0;
	//================================================================
	    //By Lawkush
	    //called getAutoSuggest() and AttachCSStoAutoSuggestButton() for autosuggestion
		getAutoSuggest();	
		AttachCSStoAutoSuggestButton();
	//================================================================
}
var gbDefaultPOId='';

function fnFetchEntity()
{
	deletedAllCharges();
	var combo = document.getElementById("txtEntity");
	if(document.getElementById('txtPODetailNo').value==0)
	{
		document.getElementById('txtCntrtMonths').value="";
	    var iCountRows = combo.length;
	   for (i = 1; i <  iCountRows; i++)
	   {
	       combo.remove(1);
	   }
	   document.getElementById('txtEntity').value=0;
	}
	else
	{
		var tr, td, i, j, oneRecord;
	    var test;
		   var iCountRows = combo.length;
		  
		   for (i = 1; i <  iCountRows; i++)
		   {
		       combo.remove(1);
		   }
	   
	    var jsData =			
	    {
			poDetailNo:document.getElementById("txtPODetailNo").value
		};
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    test = jsonrpc.processes.populateEntity(jsData);
	    for(j=0;j<test.list.length;j++)
	    {
	    	document.getElementById('hdnISFLEFLAG').value=test.list[j].isFLEFlag;
	    	var combo = document.getElementById("txtEntity");
	    	var option = document.createElement("option");
	   		option.text = test.list[j].entity;
			option.value = test.list[j].entityID;
			option.title = test.list[j].entity;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	    document.getElementById("txtEntity").selectedIndex = 1;
	    document.getElementById('txtCntrtMonths').value=contractVal[document.getElementById("txtPODetailNo").selectedIndex-1];
	    document.getElementById('txtBillingPODate').value=poDateVal[document.getElementById("txtPODetailNo").selectedIndex-1];
		fnFetchLicCompany();
	    if(document.getElementById('txtBillingLvl') != null )
		{
			setBillingLevelNo(document.getElementById('txtBillingLvl').value)
		}
	}
}

function fnFetchLicCompany()
{
	var tr, td, i, j, oneRecord;
    var test;
 
 	var combo = document.getElementById("txtLicenceCo");
 	
 	 <%String lproductId=request.getParameter("productId");%>	
 	var productID='<%=lproductId%>';
	  
 	var iCountRows = combo.length;
	  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       combo.remove(1);
	   }
   
    var jsData =			
    {
    	serviceDetailID:document.getElementById("serviceDetailID").value,
		entityID:document.getElementById("txtEntity").value,
		productId:productID
	};
	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

    test = jsonrpc.processes.populateLicCompany(jsData);
      
    for(j=0;j<test.list.length;j++)
    {
    	var combo = document.getElementById("txtLicenceCo");
    	var option = document.createElement("option");
   		option.text = test.list[j].licCompanyName;
		option.value = test.list[j].licCompanyID;
		option.title = test.list[j].licCompanyName;
		try 
		{
			combo.add(option, null); //Standard
		}
		catch(error) 
		{
			combo.add(option); // IE only
		}
    }
}
function fnHideAll()
{
	document.getElementById('lineItemServiceSummary').style.display='none';
	document.getElementById('BillingNChargeInfo').style.display='none';
   	document.getElementById('BillingInfo').style.display='none';
   	document.getElementById('serviceType').style.display='block';
   	document.getElementById('ChargeDetails').style.display='none';
   	document.getElementById('saveImage').style.display='block';
   	document.getElementById('HardwareDetails').style.display='none';
   	document.getElementById('ServiceLocation').style.display='none';
}
function fnViewProductCatelog(url)
{
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=2";
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
}

function fnshowOnFlags()
{
	var myLocalForm = document.getElementById('productCatelog');
	var varspan=document.getElementById('idparentPrdCatelog');
	if(myLocalForm.parentServiceProductId.value=="-1")
	{
		fnHideAll();
		varspan.style.display='none';
		return ;
	}
	var local_url=linkArray[myLocalForm.parentServiceProductId.selectedIndex];

	ht="<a href='#' onclick=\"fnViewProductCatelog('"+local_url+"')\">View Product Catalog<\/a>";

	varspan.innerHTML=ht;

	varspan.style.display='none';
	if(document.getElementById('hdnServiceSummary').value==1)
	{
   		document.getElementById('lineItemServiceSummary').style.display='block';
	}
	
	if(document.getElementById('hdnChargeInfo').value==1)
    {
		document.getElementById('BillingNChargeInfo').style.display='block';
    	document.getElementById('ChargeDetails').style.display='block';
	}
	if(document.getElementById('hdnHardwareInfo').value==1)//if Hardware
    {
    	document.getElementById('HardwareDetails').style.display='block';
    }
	if(document.getElementById('hdnLocationInfo').value==1)
	{
		document.getElementById('ServiceLocation').style.display='block';
    }
	if(document.getElementById('hdnBillingInfo').value==1)
	{
		document.getElementById('BillingNChargeInfo').style.display='block';
		document.getElementById('BillingInfo').style.display='block';
	}
	if(document.getElementById('hdnComponentInfo').value==1)
	{
		document.getElementById('Components').style.display='block';
	}
}

var linkArray;
<%String varserviceID=request.getParameter("serviceID");%>
var servicesID='<%=varserviceID%>';
var fxChargeRedirectionType="";
var fxChargeRedirectionTypeCumulative="";
function getParentServiceProduct()
{
    var serviceDetailID=document.getElementById("serviceDetailID").value;
	try
	{
		//--------To find config value based on servicedetailid and set config value on product catelog page----------
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var jsConfigData =			
		{
			serviceDetailID:document.getElementById('serviceDetailID').value,
			serviceId:servicesID
		};
		var configValueList=jsonrpc.processes.fetchConfigValue(jsConfigData);
		document.getElementById('hdnConfigValue').value=configValueList.configValue;	
		var totalLineItemAttached=configValueList.totalLineItemAttached;
		var maxLineItemAllow=configValueList.maxLineItemAllow;
		var isUsageValue=configValueList.isUsageAllow;
		var isDummy=configValueList.isDummy;
		var totalDummyLine=configValueList.dummyLineItem;
		var totalNonDummyLine=configValueList.nonDummyLineItem;
		fxChargeRedirectionType=configValueList.fxChargeRedirectionType;
		//start[009]
		attFLI_PO_DisableValue=configValueList.fli_po_disable;
		//end[009]
		fxChargeRedirectionTypeCumulative=configValueList.fxChargeRedirectionTypeCumulative;
		//-------------------------------------------------------------------------------------------------------------
		if(serviceDetailID==61 || serviceDetailID==81 || serviceDetailID==265 || serviceDetailID==142 || serviceDetailID==312 || serviceDetailID==669 || serviceDetailID==722 || serviceDetailID==381)
		{
			var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			var hardwareCount = jsonrpc.processes.getHardwareCount(servicesID,serviceDetailID);
			if(hardwareCount >= 1)
			{
				alert("You Cannot have More Than One Hardware For A Service");
				document.getElementById("serviceproductid").value='';
				return false;
			}
		}
		var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var jsData =			
    	{
    		serviceId:servicesID
		};
		var MplsLineCount = jsonrpc.processes.getMplsLineCount(jsData);
		if(serviceDetailID==521)
		{
			if(MplsLineCount.flexiCount >= 1)
			{
				alert("You Cannot have more than one FlexiConnect Line Item In Service No "+ servicesID);
				return false;
			}
		}
		else if(serviceDetailID==526)
		{	
			if(MplsLineCount.siteBasedCount >= 1)
			{
				alert("You Cannot have more than one SiteBased Line Item In Service No "+ servicesID);
				return false;
			}
			if(MplsLineCount.userBasedCount >= 1)
			{
				alert("You Cannot have SiteBased And UserBased Line Item In Service No "+ servicesID + "Together");
				return false;
			}
		}
		else if(serviceDetailID==522 || serviceDetailID==523 || serviceDetailID==524 || serviceDetailID==525)
		{	
			if(MplsLineCount.siteBasedCount >= 1 )
			{
				alert("You Cannot have SiteBased And UserBased Line Item In Service No "+ servicesID + "Together");
				return false;
			}
			
		}
		//-----------[ Configure Arbor Validation Start ]----------------------------------------------------												
		if(((maxLineItemAllow !=null)&&(maxLineItemAllow !=0)) && (totalLineItemAttached >= maxLineItemAllow))
		{
				alert("This Lineitem can not have more than "+maxLineItemAllow+" In Service No "+ servicesID);
				document.getElementById('saveImage').style.display='none';
				document.getElementById("serviceDetailID").value=0;
				return false;
		}
		if(isDummy==1 && totalNonDummyLine>0)
		{
			alert("You cannot select dummy Line Item with other Line Items");
			document.getElementById('saveImage').style.display='none';
			document.getElementById("serviceDetailID").value=0;
			return false;
		}
		else if(isDummy==0 && totalDummyLine>0)
		{
			alert("You cannot select LineItem with dummy Line Item already present")
			document.getElementById('saveImage').style.display='none';
			document.getElementById("serviceDetailID").value=0;
			return false;
		}		
		loadUsageCheck(isUsageValue, '');
		//-------------[ Configure Arbor Validation End ]----------------------------------------------------
	}
	catch(e)
	{		
		alert(e);					
	}	   
		  
	var myLocalForm = document.getElementById('productCatelog');
	if(myLocalForm.serviceDetailID.value=='0')
	{
		document.getElementById('idserviceProductTR').style.display='none';
   		fnHideAll();
   		var varspan=document.getElementById('idparentPrdCatelog');
   		varspan.style.display='none';
	}
	else
	{
		changehdnServiceType();
		getServiceAttributes();
		enableDisableRedirectionLSIIfPresent(fxChargeRedirectionType,'CPC',0,'');
		var jsData =			
	    {
			serviceDetailID:document.getElementById("serviceDetailID").value,
			serviceId:servicesID
		};
	
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    var local_Result = jsonrpc.processes.fetchParentServiceProduct(jsData);
	    if(local_Result!=null)
	    {
	    	var local_productlevel = local_Result.productlevel;
	    	if(local_productlevel == 1)
	    	{
	    		document.getElementById('idserviceProductTR').style.display='none';	
		    	var combo1 = document.getElementById("id_parentServiceProductId");
	    		for(i=combo1.options.length-1;i>=0 ;i--)
				{
					combo1.options[i] = null;
				}
			
				combo1.options[0] = new Option("First Level", local_Result.serviceProducts.list[0].serviceProductID);
	    	}
	    	else if(local_productlevel == 2)
	    	{
	    		fnHideAll();
				var varspan=document.getElementById('idparentPrdCatelog');
		   		varspan.style.display='none';
		   		
			   	document.getElementById('idserviceProductTR').style.display='block';	
		    	var combo1 = document.getElementById("id_parentServiceProductId");
				for(i=combo1.options.length-1;i>=0 ;i--)
				{
					combo1.options[i] = null;
				}
			
				combo1.options[0] = new Option("---Select---", "-1");
				linkArray = new Array();
				linkArray[0]='';
				if(local_Result.serviceProducts==null || local_Result.serviceProducts.list.length==0)
				{
					alert('No Parent Product Entered.');
					return false;
				}
		    	for(j=0;j<local_Result.serviceProducts.list.length;j++)
		    	{
			    	var name = local_Result.serviceProducts.list[j].serviceTypeDescription;
					var id=local_Result.serviceProducts.list[j].serviceProductID;
					combo1.options[j+1] = new Option(name+" ("+id+")",id);
					linkArray[j+1]=local_Result.serviceProducts.list[j].link;
		    	}
	    	}
	    }
	}
	selectPoId(document.getElementById('txtPODetailNo'));
}

function changehdnServiceType()
{
	var tr, td, i, j, oneRecord;
    var test9;
   
    var jsData =			
    {
		serviceDetailID:document.getElementById("serviceDetailID").value
	};

	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

    test9 = jsonrpc.processes.fetchAccessDetails(jsData);

    if(test9.list.length>0) //if logic added by saurabh
	{
	    document.getElementById('hdnServiceSummary').value = test9.list[0].serviceInfoValue;
	    document.getElementById('hdnBillingInfo').value = test9.list[0].billingInfoValue;
	    document.getElementById('hdnChargeInfo').value = test9.list[0].chargeInfoValue;
	    document.getElementById('hdnLocationInfo').value = test9.list[0].locationInfoValue;
	    document.getElementById('hdnHardwareInfo').value = test9.list[0].hardwareInfoValue;
	    document.getElementById('hdnComponentInfo').value = test9.list[0].componentInfoValue;
		document.getElementById("hdnServiceDetailID").value=document.getElementById("serviceDetailID").value
		document.getElementById("txtHdnServiceName").value=document.getElementById("hdnServiceDetailID").options[document.getElementById("hdnServiceDetailID").selectedIndex].text
	}
}

function getDispatchAddress()
{
	var tr, td, i, j, oneRecord;
    var test4;
     
    var jsData4 =			
    {
		dispatchAddressID:document.getElementById("txtdispatchAddress").value
	};
	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	if(document.getElementById("txtdispatchAddress").value != "0")
	{
    	test4 = jsonrpc.processes.populateDispatchAddress(jsData4);
      
    	if(test4.list.length>0) //if logic added by saurabh
    	{
	    	document.getElementById('txtHAddress1').value = test4.list[0].dispatchAddress1;
			document.getElementById('txtHAddress2').value = test4.list[0].dispatchAddress2;
			document.getElementById('txtHAddress3').value = test4.list[0].dispatchAddress3;
			document.getElementById('txtHCityName').value = test4.list[0].dispatchCityName;
			document.getElementById('txtHCountryName').value = test4.list[0].dispatchCountyName;
			document.getElementById('txtHPincode').value = test4.list[0].dispatchPinNo;
			document.getElementById('txtHPhnNo').value = test4.list[0].dispatchPhoneNo;
			document.getElementById('txtHStateName').value = test4.list[0].dispatchStateName;
			document.getElementById('txtHLstDate').value = test4.list[0].lstDate;
			//[202020]
			if(test4.list[0].lst_No!=0)
			{
			document.getElementById('txtHLstNo').value = test4.list[0].lst_No;
			}
			else
			{
			document.getElementById('txtHLstNo').value = '-';
			}
			//[202020]
			
		}
	}
	else
	{
		document.getElementById('txtHAddress1').value="0";
		document.getElementById('txtHAddress2').value="0";
		document.getElementById('txtHAddress3').value="0";
		document.getElementById('txtHCityName').value="0";
		document.getElementById('txtHCountryName').value="0";
		document.getElementById('txtHPincode').value="0";
		document.getElementById('txtHPhnNo').value="0";
		document.getElementById('txtHStateName').value="0";
		document.getElementById('txtHLstDate').value="0";
		document.getElementById('txtHLstNo').value="0";						
	}
}

function selectPrimaryLocation()
{
	var sendToM6Check;
	var serviceDetailId = document.getElementById('serviceDetailID').value;
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailId);
		
		
		if(sendToM6Check==0 && serviceid == 4)
		{
			if(document.getElementById('ddPrimaryLocType').value==0)
			{
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='none'; 
			}
			else if(document.getElementById('ddPrimaryLocType').value==1)
			{
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='block'; 
			}
			else
			{
				alert('Network location is not Allowed for this Line Item');
				document.getElementById('ddPrimaryLocType').value=0;
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='none'; 
			}		
		}
	if(sendToM6Check==1 && serviceid == 4)
	{
		if(document.getElementById('ddPrimaryLocType').value==0)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddPrimaryLocType').value==1)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}
		else
		{
			document.getElementById('PriNetworkPopLocation').style.display='block'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
			PopLocTypePopup('PRILOC');
		}
	}
	if(sendToM6Check==1 && serviceid != 4)
	{
		if(document.getElementById('ddPrimaryLocType').value==0)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddPrimaryLocType').value==1)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}
		else
		{
			document.getElementById('PriNetworkPopLocation').style.display='block'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
			PopLocTypePopup('PRILOC');
		}
	}
		if(sendToM6Check==0 && serviceid != 4)
		{
			if(document.getElementById('ddPrimaryLocType').value==0)
			{
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='none'; 
			}
			else if(document.getElementById('ddPrimaryLocType').value==1)
			{
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='block'; 
			}
			else
			{
				alert('Network location is not Allowed for this Line Item');
				document.getElementById('ddPrimaryLocType').value=0;
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='none'; 
			}		
		}
}

function selectSecondaryLocation()
{
		var sendToM6Check;
		var serviceDetailId = document.getElementById('serviceDetailID').value;
		var jsonrpc1 = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		sendToM6Check = jsonrpc1.processes.getM6LineItemCheck(serviceDetailId);
		
		if(sendToM6Check==0 && serviceid == 4)
		{
			if(document.getElementById('ddSecondaryLocType').value==0)
			{
				document.getElementById('SecNetworkLocation').style.display='none'; 
				document.getElementById('SecBCPLocation').style.display='none'; 
			}
			else if(document.getElementById('ddSecondaryLocType').value==1)
			{
				document.getElementById('SecNetworkLocation').style.display='none'; 
				document.getElementById('SecBCPLocation').style.display='block'; 
			}
			else if(document.getElementById('ddSecondaryLocType').value==2)
			{
				alert('Network location is not Allowed for this Line Item');
				document.getElementById('ddSecondaryLocType').value=0;
				document.getElementById('SecNetworkLocation').style.display='none'; 
				document.getElementById('SecBCPLocation').style.display='none'; 
			}			
		}

	if(sendToM6Check==1 && serviceid != 4)
	{
    if(document.getElementById('ddSecondaryLocType').value==0)
	{
		document.getElementById('SecNetworkLocation').style.display='none'; 
		document.getElementById('SecBCPLocation').style.display='none'; 
	}
	else if(document.getElementById('ddSecondaryLocType').value==1)
	{
		document.getElementById('SecNetworkLocation').style.display='none'; 
		document.getElementById('SecBCPLocation').style.display='block'; 
	}
	else
	{
		document.getElementById('SecNetworkLocation').style.display='block'; 
		document.getElementById('SecBCPLocation').style.display='none'; 
		PopLocTypePopup('SECLOC');
	}
  }
  if(sendToM6Check==1 && serviceid == 4)
  {  	  
			if(document.getElementById('ddSecondaryLocType').value==0)
			{
				document.getElementById('SecNetworkLocation').style.display='none'; 
				document.getElementById('SecBCPLocation').style.display='none'; 
			}
			else if(document.getElementById('ddSecondaryLocType').value==2)
			{
				document.getElementById('SecNetworkLocation').style.display='block'; 
				document.getElementById('SecBCPLocation').style.display='none'; 
			}
			else
			{
				alert('Customer location is not Allowed for this Line Item');
				document.getElementById('ddSecondaryLocType').value=0;
				document.getElementById('SecNetworkLocation').style.display='none'; 
				document.getElementById('SecBCPLocation').style.display='none'; 
			}	
  }
  
  if(sendToM6Check==0 && serviceid != 4)
		{
			if(document.getElementById('ddSecondaryLocType').value==0)
			{
				document.getElementById('SecNetworkLocation').style.display='none'; 
				document.getElementById('SecBCPLocation').style.display='none'; 
			}
			else if(document.getElementById('ddSecondaryLocType').value==1)
			{
				document.getElementById('SecNetworkLocation').style.display='none'; 
				document.getElementById('SecBCPLocation').style.display='block'; 
			}
			else if(document.getElementById('ddSecondaryLocType').value==2)
			{
				alert('Network location is not Allowed for this Line Item');
				document.getElementById('ddSecondaryLocType').value=0;
				document.getElementById('SecNetworkLocation').style.display='none'; 
				document.getElementById('SecBCPLocation').style.display='none'; 
			}			
			
		}
	
}

function FetchPriBCPDetails()
{
	var tr, td, i, j, oneRecord;
    var test5;
   	if(document.getElementById("ddPrimaryBCP").value != 0)
   	{
	    var jsData5 =			
	    {
			bcpID:document.getElementById("ddPrimaryBCP").value
		};
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    test5 = jsonrpc.processes.populateprmBCPDetails(jsData5);
		
	    if(test5.list.length>0) //if logic added by saurabh 
	    {
		    document.getElementById('txtPAddress1').value = test5.list[0].paddress1;
			document.getElementById('txtPAddress2').value = test5.list[0].paddress2;
			document.getElementById('txtPAddress3').value = test5.list[0].paddress3;
			document.getElementById('txtPAddress4').value = test5.list[0].paddress4;
			document.getElementById('txtPCity').value = test5.list[0].pcity;
			document.getElementById('txtPState').value = test5.list[0].pstate;
			document.getElementById('txtPPincode').value = test5.list[0].ppincode;
			document.getElementById('txtPCountry').value = test5.list[0].pcountry;
			document.getElementById('txtPContactNo').value = test5.list[0].pcontactNO;
			document.getElementById('txtPEmailId').value = test5.list[0].pemailId;
		}
	}
	else
	{
	    document.getElementById('txtPAddress1').value = "";
		document.getElementById('txtPAddress2').value = "";
		document.getElementById('txtPAddress3').value = "";
		document.getElementById('txtPAddress4').value = "";
		document.getElementById('txtPCity').value ="";
		document.getElementById('txtPState').value = "";
		document.getElementById('txtPPincode').value ="";
		document.getElementById('txtPCountry').value = "";
		document.getElementById('txtPContactNo').value = "";
		document.getElementById('txtPEmailId').value = "";
	}
	
}
function FetchSecBCPDetails()
{
	var tr, td, i, j, oneRecord;
    var test6;
   	if(document.getElementById("ddSecondaryBCP").value != 0)
   	{
	    var jsData6 =			
	    {
			bcpID:document.getElementById("ddSecondaryBCP").value
		};
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    test6 = jsonrpc.processes.populateBCPSecDetails(jsData6);
	
	    if(test6.list.length>0) //if logic added by saurabh
		{
		    document.getElementById('txtSAddress1').value = test6.list[0].saddress1;
			document.getElementById('txtSAddress2').value = test6.list[0].saddress2;
			document.getElementById('txtSAddress3').value = test6.list[0].saddress3;
			document.getElementById('txtSAddress4').value = test6.list[0].saddress4;
			document.getElementById('txtSCity').value = test6.list[0].scity;
			document.getElementById('txtSState').value = test6.list[0].sstate;
			document.getElementById('txtSPincode').value = test6.list[0].spincode;
			document.getElementById('txtSCountry').value = test6.list[0].scountry;
			document.getElementById('txtSContactNo').value = test6.list[0].scontactNO;
			document.getElementById('txtSEmailId').value = test6.list[0].semailId;
		}
	}
	else
	{
	    document.getElementById('txtSAddress1').value = "";
		document.getElementById('txtSAddress2').value = "";
		document.getElementById('txtSAddress3').value = "";
		document.getElementById('txtSAddress4').value = "";
		document.getElementById('txtSCity').value = "";
		document.getElementById('txtSState').value = "";
		document.getElementById('txtSPincode').value = "";
		document.getElementById('txtSCountry').value = "";
		document.getElementById('txtSContactNo').value = "";
		document.getElementById('txtSEmailId').value = "";
	}
}

function FetchPNLocationAddress()
{
 	if(document.getElementById("ddPNLocation").value != 0)
 	{
		var tr, td, i, j, oneRecord;
	    var test7;
	   
	    var jsData7 =			
	    {
			plocationCode:document.getElementById("ddPNLocation").value
		};
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    test7 = jsonrpc.processes.populateNPLocationAddress(jsData7);

    	if(test7.list.length>0) //if logic added by saurabh
	    {
    	document.getElementById('txtPAddress').value = test7.list[0].ptxtPAddress;
    	}
    }
    else
    { 
       document.getElementById('txtPAddress').value="";
    }
}

function FetchSNLocationAddress()
{
	if(document.getElementById("ddSNLocation").value != 0)
	{
		var tr, td, i, j, oneRecord;
	    var test8;
	   
	    var jsData8 =			
	    {
			plocationCode:document.getElementById("ddSNLocation").value
		};
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    test8 = jsonrpc.processes.populateNSLocationAddress(jsData8);
	
	    if(test8.list.length>0) //if logic added by saurabh
		{
	    	document.getElementById('txtSAddress').value = test8.list[0].stxtSAddress;
	    }
    }
    else
    { 
      document.getElementById('txtSAddress').value="";
    }
    
}

function saveProductCatelog()
{
	var componentsList;
		if(document.getElementById('serviceDetailID').value=="0")
	{
		alert("Please Select a Line Item");
		return false;
	}
	
		
//	[013]	Start
	var butSummaryFlag = false;
	var butBillFlag = false;
	var butLocFlag = false;
	var butHWFlag = false;
	var butWarranFlag = false;
//	[013]	End	
	
	//Added by Ashutosh
	<%String changeOrderNo=request.getParameter("poNumber");
	String changeTypeId=request.getParameter("changeTypeID");%>
	var changeOrderNo='<%=changeOrderNo%>';
	var changeTypeId='<%=changeTypeId%>';
	if(changeTypeId==null)
		changeTypeId=0;
	//alert("changeTypeId:::"+changeTypeId);
	<%String serviceID=request.getParameter("serviceID");%>
	//Client Side for Service Summary
	var servicesID='<%=serviceID%>';
	//RAGHU: START FOR UPDATION OF PRODUCT CATELOG PAGE
    var sessionId_for_update='<%=request.getSession().getId() %>';
   <%
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
	%>
	if(document.getElementById("serviceproductid").value != '' && document.getElementById("serviceproductid").value != '0')
	{
	  changeTypeId=0;
	var serviceproductID=document.getElementById("serviceproductid").value;
	//RAGHU: BELOW FUNCTION IS WRITTEN IN FILE ProductCatelogUpdate.js FOR UPDATION OF PRODUCT CATELOG PAGE 
	var changeType='CHANGE';
	saveProductCatelog_Update(sessionId_for_update,servicesID,serviceproductID,changeTypeId,changeType);
	return false;
	}

	
	if(selectDropdown("serviceDetailID","Product ID")==false)
	return false;
	
	if(document.getElementById("id_parentServiceProductId").value=="-1")
	{
		alert("Please Select Parent Product Id.");
		document.getElementById("id_parentServiceProductId").focus();
		return false;
	}
	if(document.getElementById("txtTaxation").value==2 && document.getElementById("changeReason").value==0)
	{
		alert("Please Select Standard Reason ");
		return false;
	}
	
	 var serviceDetailID=document.getElementById("serviceDetailID").value;
     
     var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
     if(serviceDetailID==61 || serviceDetailID==81 || serviceDetailID==265 || serviceDetailID==142 || serviceDetailID==312 || serviceDetailID==669 || serviceDetailID==722 || serviceDetailID==381)
	 {
	     var hardwareCount = jsonrpc.processes.getHardwareCount(servicesID,serviceDetailID);
	     if(hardwareCount >= 1)
	     {
	     	alert("You Cannot have More Than One Hardware For A Service");
	     	document.getElementById("serviceproductid").value='';
	     	return false;
	     }
	 }
	var jsData =			
    {
    	serviceId:servicesID
	};
	var MplsLineCount = jsonrpc.processes.getMplsLineCount(jsData);
	if(serviceDetailID==521)
	{
		if(MplsLineCount.flexiCount >= 1)
		{
			alert("You Cannot have more than one FlexiConnect Line Item In Service No "+ servicesID);
			return false;
		}
	}
	else if(serviceDetailID==526)
	{	
		if(MplsLineCount.siteBasedCount >= 1)
		{
			alert("You Cannot have more than one SiteBased Line Item In Service No "+ servicesID);
			return false;
		}
		if(MplsLineCount.userBasedCount >= 1)
		{
			alert("You Cannot have SiteBased And UserBased Line Item In Service No "+ servicesID + "Together");
			return false;
		}
	}
	else if(serviceDetailID==522 || serviceDetailID==523 || serviceDetailID==524 || serviceDetailID==525)
	{	
		if(MplsLineCount.siteBasedCount >= 1 )
		{
			alert("You Cannot have SiteBased And UserBased Line Item In Service No "+ servicesID + "Together");
			return false;
		}
		
	} 
	
			var eLogicalCircuitId="";
			var infraOderNo="";
			var metasolvCircuitId="";
			var linKageFlag="NEW"
	if(document.getElementById('hdnServiceSummary').value==1)
	{
		but=document.getElementById('btnServiceSummary');
		
		if(but.value=="+")
		{
			butSummaryFlag=true;
			show('tblServiceSummary',but);
		}
		var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
		var attributeID=new Array();
		var attributeVal=new Array();
		var attributeExpVal=new Array();
		var attributeName=new Array();
		var attributeMandatory=new Array();
		var attServiceSummMand = new Array();
		//[002]start
		var attributeMaxLength = new Array();
		var attfor_validation =new  Array();
		//[002]end
		// [101010] START
		var logicalCircuitIdLength=document.getElementById('eLogicalCircuitId').value.length;
		var infraOrderNoLength=document.getElementById('InfraOderNo').value.length;
		var metasolvCircuitIdLength=document.getElementById('metasolvCircuitId').value.length;
		if(logicalCircuitIdLength > 25)
		{
		alert("Logical Circuit ID can not be more than 25 char");
		document.getElementById('eLogicalCircuitId').focus();
		return false;
		}
		else if(infraOrderNoLength > 25)
		{
		alert("Infra Order No can not be more than 25 char");
		document.getElementById('InfraOderNo').focus();
		return false;
		}
		else if(metasolvCircuitIdLength > 25)
		{
		alert("Metasolv Circuit ID can not be more than 25 char");
		document.getElementById('metasolvCircuitId').focus();
		return false;
		}
		// [101010] END			
		for(i=1;i<=countAttributes;i++)
		{
			if(document.getElementById('prodExpVal_'+i).value == "DROPDOWN" || document.getElementById('prodExpVal_'+i).value == "LOV")
			{
				if(document.getElementById('prodAttMandatory_'+i).value=="Y" && document.getElementById('prodAttVal_'+i).value=="0" ) 
				{
					if(document.getElementById('prodAttVal_'+i).isSummaryReq==1)
					{
						alert("Please input values in Service Summary Section!!");
						//document.getElementById('prodAttVal_'+i).focus();
						//START[13082012]AutoSuggest for BCP
						document.getElementById('autoSuggProdAttVal_'+i).focus();
						//END[13082012]AutoSuggest for BCP
							return false;						
						
					}	
					else
					{
						attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
						attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
						attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
						attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
						attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
						attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
						//[002]start
						attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
						//[002]end
					}
				}
				else
				{
					attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
					attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
					attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
					attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
					attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
					attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
					//[002]start
					 attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
					//[002]end
					
				}
			}
			else
			{
				
				
				if(document.getElementById('prodAttMandatory_'+i).value=="Y" && document.getElementById('prodAttVal_'+i).value=="" ) 
				{
					if(document.getElementById('prodAttVal_'+i).isSummaryReq==1)
					{
						alert("Please input values in Service Summary Section!!");
						document.getElementById('prodAttVal_'+i).focus();
						return false;
					}	
					else
					{
						attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
						attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
						attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
						attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
						attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
						attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
						//[002]start
						attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
						//[002]end
					
					}
					
				}
				else
				{
					if(document.getElementById('hdnProdAttVal_'+i).value == 2470 && flagForDropdown == 2)
						{
							var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('prodAttVal_'+i).value);
    	       				if(flag == 1)
    	       				{
								alert('Please enter a valid LSI');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
						if(document.getElementById('hdnProdAttVal_'+i).value == 2476 && flagForDropdown == 1)
						{
							var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('prodAttVal_'+i).value);
    	       				if(flag == 1)
    	       				{
								alert('Please enter a valid LSI');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
					attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
					attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
					attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
					attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
					attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
					attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
					//[002]start
					attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
					//[002]end
					
				}
			}
			// added by manisha for service summary validation start defect no : 19032013001
			
			attfor_validation[i-1]=document.getElementById('txt_prod_attvalidation_'+i).value;
			
				if(fproduct_att_validation(attributeName[i-1],attributeVal[i-1],attributeExpVal[i-1],attfor_validation[i-1],attServiceSummMand[i-1])==false)
				{
					return false;
				
				}
			
			// end	
			
		}
		   eLogicalCircuitId=document.getElementById('eLogicalCircuitId').value;
			 infraOderNo=document.getElementById('infraOderNo').value;
			 metasolvCircuitId=document.getElementById('metasolvCircuitId').value;	
	
	}
	but=document.getElementById('btnServiceSummary');
		
		if(but.value=="-" && butSummaryFlag==true)
		{
			show('tblServiceSummary',but);
		}
	if(document.getElementById('hdnComponentInfo').value==1)
	{

		but=document.getElementById('btnComponentsDetails');
		var chkComponents=document.getElementsByName('chkComponents');
		if(but.value=="+")
		{
			show('tblComponentsDetails',but);
		}
		var myComponentsList=[];
		for(i=0;i<chkComponents.length;i++)
		{
			var chkboxComponent=chkComponents[i];
			var index=chkboxComponent.value;
			
			
			var objComponents={"javaClass": "com.ibm.ioes.forms.ComponentsDto",
						componentID:Number(document.getElementById("components"+index).value),
						packageID:Number(document.getElementById("packageID"+index).value),
						componentName:(document.getElementById("componentsName"+index).value),
					    packageName:(document.getElementById("packageName"+index).value),
						componentStatus:(document.getElementById("status"+index).value),
					    startDateLogic:(document.getElementById("txtCompStartDateLogic"+index).value),
						endDateLogic:(document.getElementById("txtCompEndDateLogic"+index).value),
						start_date:(document.getElementById("startdate"+index).value),
						end_date:(document.getElementById("enddate"+index).value),
						startDateDays:Number(document.getElementById("txtStartDays"+index).value),
						startDateMonth:Number(document.getElementById("txtStartMonth"+index).value),
						endDateDays:Number(document.getElementById("txtEndDays"+index).value),
						endDateMonth:Number(document.getElementById("txtEndMonth"+index).value),
						isReqComponentid:Number(document.getElementById('components'+index).isRequired),
						isReqComponentname:Number(document.getElementById('componentsName'+index).isRequired),
						isReqPackageid:Number(document.getElementById('packageID'+index).isRequired),
						isReqPackagename:Number(document.getElementById('packageName'+index).isRequired),
						isReqStatus:Number(document.getElementById('status'+index).isRequired),
						isReqStartdatelogic:Number(document.getElementById('txtCompStartDateLogic'+index).isRequired),
						isReqEnddateLogic:Number(document.getElementById('txtCompEndDateLogic'+index).isRequired),
						isReqStartDate:Number(document.getElementById('startdate'+index).isRequired),
						isReqEndDate:Number(document.getElementById('enddate'+index).isRequired)
						//activeDate:document.getElementById("activeDate"+index).value,
						//inactiveDate:document.getElementById("inactiveDate"+index).value
						};
			myComponentsList[i]=objComponents;
			
	//	
		}
		componentsList={"javaClass": "java.util.ArrayList",
				"list": myComponentsList
				};
	}
	if(document.getElementById('hdnBillingInfo').value==1)
	{
		if(selectDropdown("txtPODetailNo","Cust PO Detail No")==false)
		return false;
		
		if(selectDropdown("txtEntity","Entity")==false)
		return false;
		
		//[010] Start	
		if(document.getElementById('hdnConfigValue').value==1)
		{	//[019] Start
			if(document.getElementById('txtLicenceCo').value!=282 && document.getElementById('txtLicenceCo').value!=382)
			{
				alert("Invalid License Company!!For 95p Orders, You have to select 402-BAL 95P or 402-BAL 95P_GB License Company");
				return false;
			}	//[019] End
		}
		//[010] End	
	}
	
	if(document.getElementById('hdnLocationInfo').value==1)
	{
		
		if(selectDropdown("ddPrimaryLocType","Primary Location")==false)
		return false;
		
		if(document.getElementById('ddPrimaryLocType').value=="1")
		{
			if(selectDropdown("txtPriCustLocationSuggest","Primary BCP ID")==false)
			return false;
		}
		if(selectDropdown("ddSecondaryLocType","Secondary Location")==false)
		return false;
		
		if(document.getElementById('ddSecondaryLocType').value=="1")
		{
			if(selectDropdown("txtSecCustLocationSuggest","Secondary BCP ID")==false)
			return false;
		}
	}
	
	if(document.getElementById('hdnChargeInfo').value==1)
	{
		var myChargeList=[];
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var myList=[];
		var totalCPeriod=0;
		
		//lawkush start
		
		if(chkChargeLine.length>0)
		{
			document.getElementById('SelectAllChckCharges').disabled=false;
		}
		
		//lawkush end
		
		
		if(checkTotalAmount()==false)
		{
			return false;
		}
		
		for(i=0;i<chkChargeLine.length;i++)
		{
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			//[012] Start
			//if(document.getElementById('hdnHardwareInfo').value==1)
			//{	
			//[012] End
 				var payment11=document.getElementById('txtPayment1'+index).value;
 			
			var payment22=document.getElementById('txtPayment2'+index).value;
			
			var payment33=document.getElementById('txtPayment3'+index).value;
			
			var payment44=document.getElementById('txtPayment4'+index).value;
			
	if(payment11 == "" && payment22 == "" && payment33 == "" && payment44 == "")
		{
					//[012] Start
					document.getElementById('txtPayment1'+index).value=0;
					document.getElementById('txtPayment2'+index).value=0;
					document.getElementById('txtPayment3'+index).value=0;
					document.getElementById('txtPayment4'+index).value=0;  
					alert("Please input Payment Terms");
					return false;
					//[012] End
				}
				else
				{
					if (payment11 > 100 || payment22 > 100 || payment33 > 100 || payment44 > 100 )
					{
						alert('Sum of all payment terms should be equal to 100');
						/*[012] Start
						document.getElementById('txtPayment1'+index).value = "";
						document.getElementById('txtPayment2'+index).value = "";
						document.getElementById('txtPayment3'+index).value = "";
						document.getElementById('txtPayment4'+index).value = "";
						[012] End*/
						document.getElementById('txtPayment1'+index).focus();
						return false;
				
			}
			if (payment11 == "")
			{
				payment11=0;
			}
			if (payment22 == "")
			{
				payment22=0;
			}
			if (payment33 == "")
			{
				payment33=0;
			}
			if (payment44 == "")
			{
				payment44=0;
			}
			var payment = parseFloat(payment11) + parseFloat(payment22) + parseFloat(payment33) + parseFloat(payment44);
			
					if(payment != 100)
					{	
						alert('Sum of all payment terms should be equal to 100');
						document.getElementById('txtPayment1'+index).focus();
						return false;
					}	
					else
					{
						payment1=payment11;
						payment2=payment22;
						payment3=payment33;
						payment4=payment44;
					}
		 		}
		 	//[012] Start			
			//}
			//[012] End
			totalCPeriod = parseFloat(totalCPeriod) + parseFloat(document.getElementById("txtCPeriod"+index).value);
		}
		for(i=0;i<chkChargeLine.length;i++)
		{
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			var excludecharge=0;	
	                      
	      	if(document.getElementById('chkexcludeCharges'+index).checked==true)
			{
			   excludecharge=1;
			}
			else
			{
			    excludecharge=0;
			}
		
			var ob={"javaClass": "com.ibm.ioes.forms.ChargesDetailDto",
						chargeName:document.getElementById("txtCName"+index).value,
						chargePeriod:Number(document.getElementById("txtCPeriod"+index).value),
						chargeType:Number(document.getElementById("ddCType"+index).value),
						chargeAmount_String:document.getElementById("txtCTAmt"+index).value,
						chargeFrequency:document.getElementById("txtCFrequency"+index).value,
						chargeFrequencyAmt_String:document.getElementById("txtCFreqAmt"+index).value,
						startDateLogic:document.getElementById("txtCStartDate"+index).value,
						endDateLogic:document.getElementById("txtCEndDate"+index).value,
						startDateDays:Number(document.getElementById("txtStartDays"+index).value),
						startDateMonth:Number(document.getElementById("txtStartMonth"+index).value),
						endDateDays:Number(document.getElementById("txtEndDays"+index).value),
						endDateMonth:Number(document.getElementById("txtEndMonth"+index).value),
						excludecheck:Number(excludecharge),						
						isReqDdCType:Number(document.getElementById('ddCType'+index).isRequired),
						isReqTxtCName:Number(document.getElementById('txtCName'+index).isRequired),
						isReqTxtCPeriod:Number(document.getElementById('txtCPeriod'+index).isRequired),
						isReqTxtCTAmt:Number(document.getElementById('txtCTAmt'+index).isRequired),
						isReqTxtCFrequency:Number(document.getElementById('txtCFrequency'+index).isRequired),
						isReqTxtCFreqAmt:Number(document.getElementById('txtCFreqAmt'+index).isRequired),
						isReqTxtCStartDate:Number(document.getElementById('txtCStartDate'+index).isRequired),
						isReqTxtCEndDate:Number(document.getElementById('txtCEndDate'+index).isRequired),
						chargeAnnotation:document.getElementById("txtCAnnotation"+index).value,
						isReqTxtCAnnotation:Number(document.getElementById('txtCAnnotation'+index).isRequired),
						isReqTxtCStartDays:Number(document.getElementById("txtStartDays"+index).isRequired),
						isReqTxtCStartMonths:Number(document.getElementById("txtStartMonth"+index).isRequired),
						isReqTxtCEndDays:Number(document.getElementById("txtEndDays"+index).isRequired),
						isReqTxtCEndMonths:Number(document.getElementById("txtEndMonth"+index).isRequired),
						chargeRemarks:document.getElementById("txtRemarks"+index).value,
						paymentTerm1:Number(document.getElementById('txtPayment1'+index).value),
						paymentTerm2:Number(document.getElementById('txtPayment2'+index).value),
						paymentTerm3:Number(document.getElementById('txtPayment3'+index).value),
						paymentTerm4:Number(document.getElementById('txtPayment4'+index).value),
						lineItemName:document.getElementById("cmbLineItem"+index).value,
						subLineItemName:document.getElementById("cmbSubLineItem"+index).value,
						ldDateClause:document.getElementById("txtLDDateClause"+index).value,
						delayedTimeInDayes:Number(document.getElementById("txtDelayedTimeInDays"+index).value),
						ldPercentage:Number(document.getElementById("txtLDPercentage"+index).value),
						maxPercentage:Number(document.getElementById("txtMaxPenaltyPercentage"+index).value),
						isReqLineItem:Number(document.getElementById('cmbLineItem'+index).isRequired),
						isReqSubLineItem:Number(document.getElementById('cmbSubLineItem'+index).isRequired),
						taxRate:document.getElementById("txtTaxRate"+index).value,
						isReqTxtRemarks:Number(document.getElementById("txtRemarks"+index).isRequired)
						};
			myList[i]=ob;
		}

			var serviceDetID=document.getElementById('serviceDetailID').value;
			if(serviceDetID==342 || serviceDetID==344 )
			{
			   var result=  CheckMPLSWithEnterpriseSignage(attributeID,attributeVal);
			   if(result == false)
			    return;
			}
		var chargesList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
		var jsData =
		{
			prodAttID: attributeID,
			prodAttValue:attributeVal,
			serviceDetailID:document.getElementById('serviceDetailID').value,
			serviceProductID:0,
			serviceId:servicesID,
			podetailID:document.getElementById('txtPODetailNo').value   ,
			chargesDetails : chargesList
		};
    
		var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		try
		{
		    var retDto = jsonrpc.processes.validateCharges(jsData);
	    }
	    catch(e)
	    {
	    	alert("exception  :   "+ e );
		}
    }
    
    var chargesList={"javaClass": "java.util.ArrayList",
			"list": myList};
    var chargesDetailList;
    if(document.getElementById('hdnChargeInfo').value==1){
    	var myChargeList=[];
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var myList=[];
		for(i=0;i<chkChargeLine.length;i++)
		{
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			 var excludecharge=0;	
	                      
	      if(document.getElementById('chkexcludeCharges'+index).checked==true)
			{
			   excludecharge=1;
			}
			else
			{
			    excludecharge=0;
			}
			
			var ob={"javaClass": "com.ibm.ioes.forms.ChargesDetailDto",
						chargeName:document.getElementById("txtCName"+index).value,
						chargePeriod:Number(document.getElementById("txtCPeriod"+index).value),
						chargeType:Number(document.getElementById("ddCType"+index).value),
						chargeAmount_String:document.getElementById("txtCTAmt"+index).value,
						chargeFrequency:document.getElementById("txtCFrequency"+index).value,
						chargeFrequencyAmt_String:document.getElementById("txtCFreqAmt"+index).value,
						startDateLogic:document.getElementById("txtCStartDate"+index).value,
						endDateLogic:document.getElementById("txtCEndDate"+index).value,
						startDateDays:Number(document.getElementById("txtStartDays"+index).value),
						startDateMonth:Number(document.getElementById("txtStartMonth"+index).value),
						endDateDays:Number(document.getElementById("txtEndDays"+index).value),
						endDateMonth:Number(document.getElementById("txtEndMonth"+index).value),
						excludecheck:Number(excludecharge),						
						isReqDdCType:Number(document.getElementById('ddCType'+index).isRequired),
						isReqTxtCName:Number(document.getElementById('txtCName'+index).isRequired),
						isReqTxtCPeriod:Number(document.getElementById('txtCPeriod'+index).isRequired),
						isReqTxtCTAmt:Number(document.getElementById('txtCTAmt'+index).isRequired),
						isReqTxtCFrequency:Number(document.getElementById('txtCFrequency'+index).isRequired),
						isReqTxtCFreqAmt:Number(document.getElementById('txtCFreqAmt'+index).isRequired),
						isReqTxtCStartDate:Number(document.getElementById('txtCStartDate'+index).isRequired),
						isReqTxtCEndDate:Number(document.getElementById('txtCEndDate'+index).isRequired),
						chargeAnnotation:document.getElementById("txtCAnnotation"+index).value,
						isReqTxtCAnnotation:Number(document.getElementById('txtCAnnotation'+index).isRequired),
						chargeRemarks:document.getElementById("txtRemarks"+index).value,
						paymentTerm1:Number(document.getElementById('txtPayment1'+index).value),
						paymentTerm2:Number(document.getElementById('txtPayment2'+index).value),
						paymentTerm3:Number(document.getElementById('txtPayment3'+index).value),
						paymentTerm4:Number(document.getElementById('txtPayment4'+index).value),
						lineItemName:document.getElementById("cmbLineItem"+index).value,
						subLineItemName:document.getElementById("cmbSubLineItem"+index).value,
						ldDateClause:document.getElementById("txtLDDateClause"+index).value,
						delayedTimeInDayes:Number(document.getElementById("txtDelayedTimeInDays"+index).value),
						ldPercentage:Number(document.getElementById("txtLDPercentage"+index).value),
						maxPercentage:Number(document.getElementById("txtMaxPenaltyPercentage"+index).value),
						isReqLineItem:Number(document.getElementById('cmbLineItem'+index).isRequired),
						isReqSubLineItem:Number(document.getElementById('cmbSubLineItem'+index).isRequired),
						taxRate:document.getElementById("txtTaxRate"+index).value,
						isReqTxtRemarks:Number(document.getElementById("txtRemarks"+index).isRequired)
						};
			myList[i]=ob;
		}
		
		chargesDetailList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
    }
    
     var modeValue="<%= request.getParameter("modeName") %>";
  
    if(document.getElementById('hdnServiceSummary').value==1)
	{
			if(document.getElementById('hdnBillingInfo').value==1)
			{
		if(document.getElementById('txtPODetailNo').isRequired=="1"  && document.getElementById('txtPODetailNo').value == "0" )
			{
			alert("Please Enter Po detail no");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtPODetailNo').focus();
			return false;
			}
		else if(document.getElementById('txtPODetailNo').isRequired=="0"  && document.getElementById('txtPODetailNo').value == "0" )
			{
			document.getElementById('txtPODetailNo').value=0;
			}
		if(document.getElementById('txtBillingCP').isRequired=="1"  && document.getElementById('txtBillingCP').value == "0" )
			{
			alert("Please enter Credit Period");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingCP').focus();
			return false;
			}
		else if(document.getElementById('txtBillingCP').isRequired=="0"  && document.getElementById('txtBillingCP').value == "0" )
			{
			document.getElementById('txtBillingCP').value=0;
			}
		if(document.getElementById('txtEntity').isRequired=="1"  && document.getElementById('txtEntity').value == "0" )
		{
			alert("Please enter Legal Entity");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtEntity').focus();
			return false;
		}
		else if(document.getElementById('txtEntity').isRequired=="0"  && document.getElementById('txtEntity').value == "0" )
		{
			document.getElementById('txtEntity').value=0;
		}
		if(document.getElementById('txtBillingMode').isRequired=="1"  && document.getElementById('txtBillingMode').value == "0" )
		{
			alert("Please Enter billing mode");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingMode').focus();
			return false;
		}
		else if(document.getElementById('txtBillingMode').isRequired=="0"  && document.getElementById('txtBillingMode').value == "0" )
		{
			document.getElementById('txtBillingMode').value=0;
		}
		if(document.getElementById('txtBillingformat').isRequired=="1"  && document.getElementById('txtBillingformat').value == "0" )
		{
			alert("Please enter Bill Format");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingformat').focus();
			return false;
		}
		else if(document.getElementById('txtBillingformat').isRequired=="0"  && document.getElementById('txtBillingformat').value == "0" )
		{
			document.getElementById('txtBillingformat').value=0;
		}
		if(document.getElementById('txtLicenceCo').isRequired=="1"  && document.getElementById('txtLicenceCo').value == "0" )
		{
			alert("Please enter Licence Co");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtLicenceCo').focus();
			return false;
		}
		else if(document.getElementById('txtLicenceCo').isRequired=="0"  && document.getElementById('txtLicenceCo').value == "0" )
		{
			document.getElementById('txtBillingformat').value=0;
		}
		if(document.getElementById('txtNoticePeriod').isRequired=="1"  && document.getElementById('txtNoticePeriod').value == "" )
		{
			alert("Please enter Notice Period (months)");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtNoticePeriod').focus();
			return false;
		}
		else if(document.getElementById('txtNoticePeriod').isRequired=="0"  && document.getElementById('txtNoticePeriod').value == "" )
		{
			document.getElementById('txtNoticePeriod').value=0;
		}
		if(document.getElementById('txtBillingType').isRequired=="1"  && document.getElementById('txtBillingType').value == "0" )
		{
			alert("Please enter Billing Type");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingType').focus();
			return false;
		}
		else if(document.getElementById('txtBillingType').isRequired=="0"  && document.getElementById('txtBillingType').value == "0" )
		{
			document.getElementById('txtBillingType').value=0;
		}
		if(document.getElementById('txtTaxation').isRequired=="1"  && document.getElementById('txtTaxation').value == "0" )
		{
			alert("Please enter Taxation");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+")
			{
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('txtTaxation').focus();
			return false;
		}
		else if(document.getElementById('txtTaxation').isRequired=="0"  && document.getElementById('txtTaxation').value == "0" )
		{
			document.getElementById('txtTaxation').value=0;
		}
		if(document.getElementById('txtCmtPeriod').isRequired=="1"  && document.getElementById('txtCmtPeriod').value == "" )
		{
			alert("Please enter Commitment Period (Months)");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+")
			{
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('txtCmtPeriod').focus();
			return false;
		}
		else if(document.getElementById('txtCmtPeriod').isRequired=="0"  && document.getElementById('txtCmtPeriod').value == "" )
		{
			document.getElementById('txtCmtPeriod').value=0;
		}
		if(document.getElementById('txtBillingLvl').isRequired=="1"  && document.getElementById('txtBillingLvl').value == "0" )
		{
			alert("Please enter Billing Level");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+")
			{
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('txtBillingLvl').focus();
			return false;
		}
		else if(document.getElementById('txtBillingLvl').isRequired=="0"  && document.getElementById('txtBillingLvl').value == "0" )
		{
			document.getElementById('txtBillingLvl').value=0;
		}
		if(document.getElementById('txtBillingLevelNo').isRequired=="1"  && document.getElementById('txtBillingLevelNo').value == "" )
		{
			alert("Please enter Billing Level No");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+")
			{
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('txtBillingLevelNo').focus();
			return false;
		}
		else if(document.getElementById('txtBillingLevelNo').isRequired=="0"  && document.getElementById('txtBillingLevelNo').value == "" )
		{
			document.getElementById('txtBillingLevelNo').value=0;
		}
		if(document.getElementById('txtPenaltyClause').isRequired=="1"  && document.getElementById('txtPenaltyClause').value == "" )
		{
			alert("Please enter Penalty Clause");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+")
			{
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('txtPenaltyClause').focus();
			return false;
		}
		else if(document.getElementById('txtPenaltyClause').isRequired=="0"  && document.getElementById('txtPenaltyClause').value == "" )
		{
			document.getElementById('txtPenaltyClause').value=0;
		}
		//satyapan OSP
		if((document.getElementById('txtOSPRegNo').isRequired=="1" ||document.getElementById('txtOSPTagging').value==1 ) && (document.getElementById('txtOSPRegNo').value == "" || document.getElementById('txtOSPRegNo').value ==null) )
		{
			alert("Please enter OSPRegNO");
			document.getElementById('txtOSPRegNo').focus();
			return false;
		}
	  	else if(document.getElementById('txtOSPRegNo').isRequired=="0" && document.getElementById('txtOSPRegNo').value=="")
		{
			document.getElementById('txtOSPRegNo').value="";
		}	
		 
		if((document.getElementById('txtOSPRegDate').isRequired=="1" ||document.getElementById('txtOSPTagging').value==1 )  && (document.getElementById('txtOSPRegDate').value == "" || document.getElementById('txtOSPRegDate').value ==null) )
		{
			alert("Please enter OSPRegDate");
			
			document.getElementById('txtOSPRegDate').focus();
			return false;
		}
		else if(document.getElementById('txtOSPRegDate').isRequired=="0" && document.getElementById('txtOSPRegDate').value=="" )
		{
			document.getElementById('txtOSPRegDate').value="";
		}
		
		
		//END satyapan OSP
		//WARRANTY CLAUSE ADDED BY MANISHA START
		if(document.getElementById('txtWarrantClause').isRequired=="1"  && document.getElementById('txtWarrantClause').value == "" )
		{
			alert("Please enter Warranty Clause");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtWarrantClause').focus();
			return false;
		}
		else if(document.getElementById('txtWarrantClause').isRequired=="0"  && document.getElementById('txtWarrantClause').value == "" )
		{
			document.getElementById('txtWarrantClause').value=0;
		}	
		
		//WARRANTY CLAUSE ADDED BY MANISHA end
			//  bcp details for services ADDED BY MANISHA START
		if(document.getElementById('hdnHardwareInfo').value!=1)
		{
			if(document.getElementById('bbPrimaryBCPService').isRequired=="1"  && (document.getElementById('bbPrimaryBCPService').value == "" || document.getElementById('txtBCPAddressSuggestService').value=="") )
			{
				alert("Please enter Ship to Address");
				but=document.getElementById('btnBillingAndChargesInformation');
						if(but.value=="+")
						{
							show('tblBillingAndChargesInformation',but);
						}
				document.getElementById('txtBCPAddressSuggestService').focus();
				
				return false;
			}
			else if(document.getElementById('bbPrimaryBCPService').isRequired=="0"  && (document.getElementById('bbPrimaryBCPService').value == "" || document.getElementById('txtBCPAddressSuggestService').value=="") )
			{
				document.getElementById('txtBCPAddressSuggestService').value=0;
			}					 
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="-")
			{
				show('tblBillingAndChargesInformation',but);
			}
		}	
		//  bcp details for services ADDED BY MANISHA END
		if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && (document.getElementById('bbPrimaryBCP').value == "" || document.getElementById('txtBCPAddressSuggest').value=="")   )
		{
			alert("Please Enter   BCP details");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+")
			{
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('txtBCPAddressSuggest').focus();
			return false;
		}
		else if(document.getElementById('bbPrimaryBCP').isRequired=="0"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			document.getElementById('bbPrimaryBCP').value=0;
		}
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="-" && butBillFlag == true)
			{
				show('tblBillingAndChargesInformation',but);
			}
			//start[009]attFLI_PO_DisableValue
			if(document.getElementById('hdnISFLEFLAG').value==1  && document.getElementById('txtBillingLvl').value!=2)
			{
				if(!(document.getElementById('txtBillingLvl').value==3 && attFLI_PO_DisableValue =="Y"))
				{
					
					alert("Billing Level should be PO Level");
					but=document.getElementById('btnBillingAndChargesInformation');
						if(but.value=="+")
						{
							butBillFlag = true;
							show('tblBillingAndChargesInformation',but);
						}
					document.getElementById('txtBillingLvl').focus();
					return false;
				}
			}
		}	
	if(document.getElementById('hdnLocationInfo').value==1)
	{
		
		if(document.getElementById('ddPrimaryLocType').isRequired=="1"  && document.getElementById('ddPrimaryLocType').value == "0" )
		{
			alert("Please Enter Primary Location Type");
				but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}

			document.getElementById('ddPrimaryLocType').focus();
			return false;
		}
		else if(document.getElementById('ddPrimaryLocType').isRequired=="0"  && document.getElementById('ddPrimaryLocType').value == "0" )
		{
			document.getElementById('ddPrimaryLocType').value=0;
		}
		
		if(document.getElementById('ddSecondaryLocType').isRequired=="1"  && document.getElementById('ddSecondaryLocType').value == "0" )
		{

			alert("Please Enter Secondary Location Type");
			but=document.getElementById('btnServiceLocationDetails');
			if(but.value=="+")
			{
				butLocFlag = true;
				show('tblServiceLocationDetails',but);
			}

			document.getElementById('ddSecondaryLocType').focus();
			return false;
		}
		else if(document.getElementById('ddSecondaryLocType').isRequired=="0"  && document.getElementById('ddSecondaryLocType').value == "0" )
		{
			if(document.getElementById('ddSecondaryLocType').value == "" || document.getElementById('ddSecondaryLocType').value == "0" ){
			document.getElementById('ddSecondaryLocType').value="0";	
			}
			else
			{  if(serviceTypeId==4)
			 document.getElementById('ddSecondaryLocType').value="2";
			}
		}
		
		if(document.getElementById('ddPrimaryLocType').value=="1" && document.getElementById("ddPrimaryLocType").isRequired=="1")
		{
			if((document.getElementById("ddPrimaryBCP").value=="0" || document.getElementById('txtPriCustLocationSuggest').value == "0") && document.getElementById("ddPrimaryBCP").isRequired=="1")
			{
			alert("Please enter Primary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('txtPriCustLocationSuggest').focus();
			return false;
			}
			else if(document.getElementById('ddPrimaryBCP').isRequired=="0"  && (document.getElementById('ddPrimaryBCP').value == "0" || document.getElementById('txtPriCustLocationSuggest').value == "0" ))
			{
			document.getElementById('txtPriCustLocationSuggest').value=0;
			}
			
		}
		if(document.getElementById('ddPrimaryLocType').value=="2" && document.getElementById('ddPrimaryLocType').isRequired=="1")
		{
			if(document.getElementById('ddPNLocation').value=="0" && document.getElementById('ddPNLocation').isRequired=="1")
			{
			alert("Please enter Primary Location Network ");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			return false;
			}
			else if(document.getElementById('ddPNLocation').isRequired=="0"  && document.getElementById('ddPNLocation').value == "0" )
			{
			document.getElementById('ddPNLocation').value=0;
			}
		}
		else document.getElementById('ddPNLocation').value=0;
		
		if(document.getElementById('ddSecondaryLocType').value=="1" && document.getElementById('ddSecondaryLocType').isRequired=="1")
		{
			if((document.getElementById("ddSecondaryBCP").value=="0" || document.getElementById("txtSecCustLocationSuggest").value=="0") && document.getElementById("ddSecondaryBCP").isRequired=="1")
			{
			alert("Please enter Secondary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('txtSecCustLocationSuggest').focus();
			return false;
			}
			else if(document.getElementById('ddSecondaryBCP').isRequired=="0"  && (document.getElementById('ddSecondaryBCP').value == "0" || document.getElementById("txtSecCustLocationSuggest").value=="0"))
			{
			document.getElementById('txtSecCustLocationSuggest').value=0;
			}
		}
		if(document.getElementById('ddSecondaryLocType').value=="2" && document.getElementById('ddSecondaryLocType').isRequired=="1")
		{
			if(document.getElementById('ddSNLocation').value=="0" && document.getElementById('ddSNLocation').isRequired=="1")
			{
			alert("Please enter Secondary Location Network ");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			return false;
			}
			else if(document.getElementById('ddSNLocation').isRequired=="0"  && document.getElementById('ddSNLocation').value == "0" )
			{
			document.getElementById('ddSNLocation').value=0;
			}
		}
		else document.getElementById('ddSNLocation').value=0;
		if(document.getElementById('txtFAddress').isRequired=="1"  && document.getElementById('txtFAddress').value == "" )
		{
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('txtFAddress').focus();
		}
		else if(document.getElementById('txtFAddress').isRequired=="0"  && document.getElementById('txtFAddress').value == "" )
		{
			document.getElementById('txtFAddress').value=0;
		}
		if(document.getElementById('txtToAddress').isRequired=="1"  && document.getElementById('txtToAddress').value == "" )
		{
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('txtToAddress').focus();
		}
		else if(document.getElementById('txtToAddress').isRequired=="0"  && document.getElementById('txtToAddress').value == "" )
		{
			document.getElementById('txtToAddress').value=0;
		}
		but=document.getElementById('btnServiceLocationDetails');
		if(but.value=="-" && butLocFlag == true)
		{
			show('tblServiceLocationDetails',but);
		}
	}
	
	if(document.getElementById('hdnHardwareInfo').value==1)
	{
		if(document.getElementById('txtStore').isRequired=="1"  && document.getElementById('txtStore').value == "0" )
		{
			alert("Please enter Store");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+")
			{
				butHWFlag = true;
				show('tblHwDetails',but);
			}
			document.getElementById('txtStore').focus();
			return false;
		}
		else if(document.getElementById('txtStore').isRequired=="0"  && document.getElementById('txtStore').value == "0" )
		{
			document.getElementById('txtStore').value=0;
		}
		if((document.getElementById('txtHtype').value=="0" || document.getElementById('txtHtype').value=="")&& document.getElementById('txtHtype').isRequired=="1")
		{
			alert("Please enter Hardware Type");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtHtype').focus();
			return false;
		}
		else if((document.getElementById('txtHtype').value=="0" || document.getElementById('txtHtype').value=="")&& document.getElementById('txtHtype').isRequired=="0")
		{
			document.getElementById('txtHtype').value=0;
		}
		if((document.getElementById('txtform').value=="0" || document.getElementById('txtform').value=="")&&document.getElementById('txtform').isRequired=="1" )
		{
			alert("Please enter Form available");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtform').focus();
			return false;
		}
		else if((document.getElementById('txtform').value=="0" || document.getElementById('txtform').value=="")&&document.getElementById('txtform').isRequired=="0" )
		{
			document.getElementById('txtform').value=0;
		}
		if((document.getElementById('txtNSale').value=="0" || document.getElementById('txtNSale').value=="")&& document.getElementById('txtNSale').isRequired=="1" )
		{
			alert("Please enter Nature of sale");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtNSale').focus();
			return false;
		}
		else if((document.getElementById('txtNSale').value=="0" || document.getElementById('txtNSale').value=="")&& document.getElementById('txtNSale').isRequired=="0")
		{
			document.getElementById('txtNSale').value=0;
		}
		if(document.getElementById('txtTSale').isRequired=="1"  && document.getElementById('txtTSale').value == "0" )
		{

			alert("Please Enter Type of Sale");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}

			alert("Please enter Type of sale");

			document.getElementById('txtTSale').focus();
			return false;
		}
		else if(document.getElementById('txtTSale').isRequired=="0"  && document.getElementById('txtTSale').value == "0" )
		{
			document.getElementById('txtTSale').value=0;
		}
		but=document.getElementById('btnHwDetails');
					if(but.value=="-" && butHWFlag == true)
					{
						show('tblHwDetails',but);
					}
		if(document.getElementById('txtdispatchAddress').isRequired=="1"  && (document.getElementById('txtDispatchAddress').value == "" || document.getElementById('txtDispatchAddressSuggest').value=="" )  )
		{
			alert("Please enter Dispatch Address");
			but=document.getElementById('btnDispatchAddress');
				if(but.value=="+")
				{
					show('tblDispatchAddress',but);
				}
			document.getElementById('txtDispatchAddressSuggest').focus();
			return false;
		}
		else if(document.getElementById('txtdispatchAddress').isRequired=="0"  && document.getElementById('txtdispatchAddress').value == "0" )
		{
			document.getElementById('txtdispatchAddress').value=0;
		}
		but=document.getElementById('btnDispatchAddress');
				if(but.value=="-")
				{
					show('tblDispatchAddress',but);
				}
	if((document.getElementById('txtStartDateLogic').value != "0" || document.getElementById('txtEndDateLogic').value != "0" )
		||
		(document.getElementById('txtStartDateLogic').isRequired==1 || document.getElementById('txtEndDateLogic').isRequired==1 ||
			document.getElementById('txtHStartMonth').isRequired==1 || document.getElementById('txtHEndMonth').isRequired==1 ||
			document.getElementById('txtHStartDays').isRequired==1 || document.getElementById('txtHEndDays').isRequired==1 ||
			document.getElementById('txtHExtMonth').isRequired==1 || document.getElementById('txtHExtDays').isRequired==1
			)
		)
	{
		if( document.getElementById('txtStartDateLogic').value == "0" )
		{
			alert("Please enter Start Date Logic");
			but=document.getElementById('btnWarrentyDetails');
			if(but.value=="+")
			{
				butWarranFlag = true;
				show('tblWarrentyDetails',but);
			}
			document.getElementById('txtStartDateLogic').focus();
			return false;
		}
		
		if( document.getElementById('txtHStartMonth').value == "" )
		{
			alert("Please enter Start Month ");
			but=document.getElementById('btnWarrentyDetails');
			if(but.value=="+")
			{
				butWarranFlag = true;
				show('tblWarrentyDetails',but);
			}
			document.getElementById('txtHStartMonth').focus();
			return false;
		}
		
		if( document.getElementById('txtHStartDays').value == "" )
		{
			alert("Please enter Start Days ");
			but=document.getElementById('btnWarrentyDetails');
			if(but.value=="+")
			{
				butWarranFlag = true;
				show('tblWarrentyDetails',but);
			}
			document.getElementById('txtHStartDays').focus();
			return false;
		}
		
		if( document.getElementById('txtEndDateLogic').value == "0" )
		{
			alert("Please enter End Date Logic");
			but=document.getElementById('btnWarrentyDetails');
			if(but.value=="+")
			{
				butWarranFlag = true;
				show('tblWarrentyDetails',but);
			}
			document.getElementById('txtEndDateLogic').focus();
			return false;
		}
		
		
		
		if( document.getElementById('txtHEndMonth').value == "" )
		{
			alert("Please enter End Month ");
			but=document.getElementById('btnWarrentyDetails');
			if(but.value=="+")
			{
				butWarranFlag = true;
				show('tblWarrentyDetails',but);
			}
			document.getElementById('txtHEndMonth').focus();
			return false;
		}
		if( document.getElementById('txtHEndDays').value == "" )
		{
			alert("Please enter End Days ");
			but=document.getElementById('btnWarrentyDetails');
			if(but.value=="+")
			{
				butWarranFlag = true;
				show('tblWarrentyDetails',but);
			}
			document.getElementById('txtHEndDays').focus();
			return false;
		}
		if( document.getElementById('txtHExtMonth').value == "" )
		{
			alert("Please enter Extended Month");
			but=document.getElementById('btnWarrentyDetails');
			if(but.value=="+")
			{
				butWarranFlag = true;
				show('tblWarrentyDetails',but);
			}
			document.getElementById('txtHExtMonth').focus();
			return false;
		}
		if( document.getElementById('txtHExtDays').value == "" )
		{
			alert("Please enter Extended Days");
			but=document.getElementById('btnWarrentyDetails');
			if(but.value=="+")
			{
				butWarranFlag = true;
				show('tblWarrentyDetails',but);
			}
			document.getElementById('txtHExtDays').focus();
			return false;
		}
		
		
	}
	}
		if(serviceTypeId==4){
		
			 document.getElementById('ddSecondaryLocType').value=2;
			 }
			if(document.getElementById("chkSelectNfa").value=="on")
			 {
			 document.getElementById("chkSelectNfa").value=0
			 }	
	    var jsData =
		{
			prodAttID: attributeID,
			prodAttValue:attributeVal,
			prodAttExptdValue:attributeExpVal,
			prodAttName:attributeName,
			serviceSummaryMandatory:attServiceSummMand,
			prodAttMandatory:attributeMandatory,
			//[002]start
			prodAttriMaxLength:attributeMaxLength,	
			//[002]end				
			hdnSeriveAttCounter:countAttributes,
			serviceDetailID:Number(document.getElementById('serviceDetailID').value),
			serviceId:Number(servicesID),
			serviceTypeId:Number(serviceTypeId),
			podetailID:Number(document.getElementById('txtPODetailNo').value),
			accountID:Number(document.getElementById('txtBillingAC').value),
			creditPeriod:Number(document.getElementById('txtBillingCP').value),
			entityID:Number(document.getElementById('txtEntity').value),
			billingType:Number(document.getElementById('txtBillingType').value),
			billingMode:document.getElementById('txtBillingMode').value,
			//002 start
			noticePeriod:Number(document.getElementById('txtNoticePeriod').value),
			billingformat:document.getElementById('txtBillingformat').value,
			licenceCoID:Number(document.getElementById('txtLicenceCo').value),
			taxation:document.getElementById('txtTaxation').value,
			stdReasonId:document.getElementById('changeReason').value,
			commitmentPeriod:Number(document.getElementById('txtCmtPeriod').value),
			billingLevel:document.getElementById('txtBillingLvl').value,
			penaltyClause:document.getElementById('txtPenaltyClause').value,
			warrantyClause:document.getElementById('txtWarrantClause').value,
			//satyapan osp
			isOSPTagging:document.getElementById('txtOSPTagging').value,//satyapan osp
			txtOSPRegNo:document.getElementById('txtOSPRegNo').value,
			txtOSPRegDate:document.getElementById('txtOSPRegDate').value,
			//satyapan osp
			serviceType:document.getElementById('txtHdnServiceName').value,
			selectedDispatchID:Number(document.getElementById('txtdispatchAddress').value),
			//[003]start
			dispatchContactName:document.getElementById('txtDispatchContactName').value,
			//[003]end
			selectedStoreID:Number(document.getElementById('txtStore').value),
			formAvailable:document.getElementById('txtform').value,
			hardwareType:document.getElementById('txtHtype').value,
			saleNature:document.getElementById('txtNSale').value,
			saleType:document.getElementById('txtTSale').value,
			selectedPriLocType:Number(document.getElementById('ddPrimaryLocType').value),
			selectedSecLocType:Number(document.getElementById('ddSecondaryLocType').value),
			selectedPNLocation:Number(document.getElementById('ddPNLocation').value),
			selectedPrimaryBCP:Number(document.getElementById('ddPrimaryBCP').value),
			selectedSNLocation:Number(document.getElementById('ddSNLocation').value),
			selectedSecBCP:Number(document.getElementById('ddSecondaryBCP').value),
			billingLevelNo:Number(document.getElementById('txtBillingLevelNo').value),
			chargesDetails : chargesDetailList,
			serviceInfoValue:Number(document.getElementById('hdnServiceSummary').value),
			billingInfoValue:Number(document.getElementById('hdnBillingInfo').value),
			chargeInfoValue:Number(document.getElementById('hdnChargeInfo').value),
			locationInfoValue:Number(document.getElementById('hdnLocationInfo').value),
			hardwareInfoValue:Number(document.getElementById('hdnHardwareInfo').value),
			billingBCPId:document.getElementById('bbPrimaryBCPID').value,
			billingBCPIdService:document.getElementById('bbPrimaryBCPIDService').value,
			parentServiceProductId:document.getElementById('id_parentServiceProductId').value,
			startHWDateLogic:document.getElementById('txtStartDateLogic').value,
			endHWDateLogic:document.getElementById('txtEndDateLogic').value,
			startDate:document.getElementById('txtStartDate').value,
			endDate:document.getElementById('txtEndDate').value,
			fromLocation:document.getElementById('txtFAddress').value,
		    toLocation:document.getElementById('txtToAddress').value,
			isNfa:Number(document.getElementById('chkSelectNfa').value),
			isUsage:Number(document.getElementById('chkIsUsage').value),
			billingScenario:document.getElementById('txtBillingScenario').value,
			fxRedirectionSPID:Number(document.getElementById('hdnFxRedirectionSPID').value),
			fxRedirectionLSI:Number(document.getElementById('txtFxRedirectionLSI').value),
			
			logicalCircuitId:eLogicalCircuitId,
			infraOderNo:infraOderNo,
			metasolvCircuitId:metasolvCircuitId,
			linkageInfoFlag:linKageFlag,
		    txtStartMonth:Number(document.getElementById('txtHStartMonth').value),
			txtStartDays:Number(document.getElementById('txtHStartDays').value),
			txtEndMonth:Number(document.getElementById('txtHEndMonth').value),
			txtEndDays:Number(document.getElementById('txtHEndDays').value),
			txtExtMonth:Number(document.getElementById('txtHExtMonth').value),
			txtExtDays:Number(document.getElementById('txtHExtDays').value),
			txtExtDate:Number(document.getElementById('txtHExtDate').value),
		    
            isReqTxtPODetailNo:Number(document.getElementById('txtPODetailNo').isRequired),
			isReqTxtBillingAC:Number(document.getElementById('txtBillingAC').isRequired),
			isReqTxtBillingCP:Number(document.getElementById('txtBillingCP').isRequired),
			isReqTxtEntity:Number(document.getElementById('txtEntity').isRequired),
			isReqTxtBillingMode:Number(document.getElementById('txtBillingMode').isRequired),
			isReqTxtBillingformat:Number(document.getElementById('txtBillingformat').isRequired),
			isReqTxtLicenceCo:Number(document.getElementById('txtLicenceCo').isRequired),
			isReqTxtTaxation:Number(document.getElementById('txtTaxation').isRequired),
			isReqTxtCmtPeriod:Number(document.getElementById('txtCmtPeriod').isRequired),
			isReqTxtBillingLvl:Number(document.getElementById('txtBillingLvl').isRequired),
			isReqTxtPenaltyClause:Number(document.getElementById('txtPenaltyClause').isRequired),
			isReqTxtWarrantyClause:Number(document.getElementById('txtWarrantClause').isRequired),
			//satyapan OSP By nagarjuna
			isReqOSPTagging:Number(document.getElementById('txtOSPTagging').isRequired),//satyapan osp
			isReqOSPRegNo:Number(document.getElementById('txtOSPRegNo').isRequired),
			isReqOSPRegDate:Number(document.getElementById('txtOSPRegDate').isRequired),
			//satyapan OSP
			isReqSelectedDispatchID:Number(document.getElementById('txtdispatchAddress').isRequired),
			//[003]start
			isReqDispatchContactName:Number(document.getElementById('txtDispatchContactName').isRequired),
			//[003]end
			isReqDdPrimaryLocType:Number(document.getElementById('ddPrimaryLocType').isRequired),
			isReqDdSecondaryLocType:Number(document.getElementById('ddSecondaryLocType').isRequired),
			isReqSelectedPNLocation:Number(document.getElementById('ddPNLocation').isRequired),
			isReqSelectedPrimaryBCP:Number(document.getElementById('ddPrimaryBCP').isRequired),
			isReqSelectedSNLocation:Number(document.getElementById('ddSNLocation').isRequired),
			isReqSelectedSecBCP:Number(document.getElementById('ddSecondaryBCP').isRequired),
			
			isReqTxtStore:Number(document.getElementById('txtStore').isRequired),
			isReqTxtHtype:Number(document.getElementById('txtHtype').isRequired),
			isReqTxtform:Number(document.getElementById('txtform').isRequired),
			isReqTxtTSale:Number(document.getElementById('txtTSale').isRequired),
			isReqTxtNSale:Number(document.getElementById('txtNSale').isRequired),
			isReqTxtStore:Number(document.getElementById('txtStartDateLogic').isRequired),
			isReqTxtHtype:Number(document.getElementById('txtEndDateLogic').isRequired),
			isReqStartDate:Number(document.getElementById('txtStartDate').isRequired),
			isReqEndDate:Number(document.getElementById('txtEndDate').isRequired),
			isReqTxtFromLocation:Number(document.getElementById('txtFAddress').isRequired),
			isReqTxtToLocation:Number(document.getElementById('txtToAddress').isRequired), // by Saurabh for validation of to and from location
			poNumber:Number(changeOrderNo),
			changeTypeId:Number(changeTypeId),
			configValue:Number(document.getElementById('hdnConfigValue').value)
		};
	}
	else
	{
		if(document.getElementById('hdnBillingInfo').value==1)
		{
			if(document.getElementById('txtPODetailNo').isRequired=="1"  && document.getElementById('txtPODetailNo').value == "0" )
			{
				alert("Please Enter PO ID");
				but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+")
				{
					butBillFlag = true;
					show('tblBillingAndChargesInformation',but);
				}
			document.getElementById('txtPODetailNo').focus();
			return false;
			}
		else if(document.getElementById('txtPODetailNo').isRequired=="0"  && document.getElementById('txtPODetailNo').value == "0" )
			{
			document.getElementById('txtPODetailNo').value=0;
			}
		if(document.getElementById('txtBillingCP').isRequired=="1"  && document.getElementById('txtBillingCP').value == "0" )
			{
			alert("Please enter Credit Period");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingCP').focus();
			return false;
			}
		else if(document.getElementById('txtBillingCP').isRequired=="0"  && document.getElementById('txtBillingCP').value == "0" )
			{
			document.getElementById('txtBillingCP').value=0;
			}
		if(document.getElementById('txtEntity').isRequired=="1"  && document.getElementById('txtEntity').value == "0" )
		{
			alert("Please enter Legal Entity");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtEntity').focus();
			return false;
		}
		else if(document.getElementById('txtEntity').isRequired=="0"  && document.getElementById('txtEntity').value == "0" )
		{
			document.getElementById('txtEntity').value=0;
		}
		if(document.getElementById('txtBillingMode').isRequired=="1"  && document.getElementById('txtBillingMode').value == "0" )
		{
			alert("Please enter Billing Mode");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingMode').focus();
			return false;
		}
		else if(document.getElementById('txtBillingMode').isRequired=="0"  && document.getElementById('txtBillingMode').value == "0" )
		{
			document.getElementById('txtBillingMode').value=0;
		}
		if(document.getElementById('txtBillingformat').isRequired=="1"  && document.getElementById('txtBillingformat').value == "0" )
		{
			alert("Please enter Bill Format");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingformat').focus();
			return false;
		}
		else if(document.getElementById('txtBillingformat').isRequired=="0"  && document.getElementById('txtBillingformat').value == "0" )
		{
			document.getElementById('txtBillingformat').value=0;
		}
		if(document.getElementById('txtLicenceCo').isRequired=="1"  && document.getElementById('txtLicenceCo').value == "0" )
		{
			alert("Please enter Licence Co");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtLicenceCo').focus();
			return false;
		}
		else if(document.getElementById('txtLicenceCo').isRequired=="0"  && document.getElementById('txtLicenceCo').value == "0" )
		{
			document.getElementById('txtBillingformat').value=0;
		}
		if(document.getElementById('txtNoticePeriod').isRequired=="1"  && document.getElementById('txtNoticePeriod').value == "" )
		{
			alert("Please enter Notice Period (months)");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtNoticePeriod').focus();
			return false;
		}
		else if(document.getElementById('txtNoticePeriod').isRequired=="0"  && document.getElementById('txtNoticePeriod').value == "" )
		{
			document.getElementById('txtNoticePeriod').value=0;
		}
		if(document.getElementById('txtBillingType').isRequired=="1"  && document.getElementById('txtBillingType').value == "0" )
		{
			alert("Please enter Billing Type");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingType').focus();
			return false;
		}
		else if(document.getElementById('txtBillingType').isRequired=="0"  && document.getElementById('txtBillingType').value == "0" )
		{
			document.getElementById('txtBillingType').value=0;
		}
		if(document.getElementById('txtTaxation').isRequired=="1"  && document.getElementById('txtTaxation').value == "0" )
		{
			alert("Please enter Taxation");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtTaxation').focus();
			return false;
		}
		else if(document.getElementById('txtTaxation').isRequired=="0"  && document.getElementById('txtTaxation').value == "0" )
		{
			document.getElementById('txtTaxation').value=0;
		}
		if(document.getElementById('txtCmtPeriod').isRequired=="1"  && document.getElementById('txtCmtPeriod').value == "" )
		{
			alert("Please enter Commitment Period (Months)");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtCmtPeriod').focus();
			return false;
		}
		else if(document.getElementById('txtCmtPeriod').isRequired=="0"  && document.getElementById('txtCmtPeriod').value == "" )
		{
			document.getElementById('txtCmtPeriod').value=0;
		}
		if(document.getElementById('txtBillingLvl').isRequired=="1"  && document.getElementById('txtBillingLvl').value == "0" )
		{
			alert("Please enter Billing Level");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingLvl').focus();
			return false;
		}
		else if(document.getElementById('txtBillingLvl').isRequired=="0"  && document.getElementById('txtBillingLvl').value == "0" )
		{
			document.getElementById('txtBillingLvl').value=0;
		}
		if(document.getElementById('txtBillingLevelNo').isRequired=="1"  && document.getElementById('txtBillingLevelNo').value == "" )
		{
			alert("Please enter Billing Level No");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingLevelNo').focus();
			return false;
		}
		else if(document.getElementById('txtBillingLevelNo').isRequired=="0"  && document.getElementById('txtBillingLevelNo').value == "" )
		{
			document.getElementById('txtBillingLevelNo').value=0;
		}
		if(document.getElementById('txtPenaltyClause').isRequired=="1"  && document.getElementById('txtPenaltyClause').value == "" )
		{
			alert("Please Enter Penalty Clause ");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}

			document.getElementById('txtPenaltyClause').focus();
			return false;
		}
		else if(document.getElementById('txtPenaltyClause').isRequired=="0"  && document.getElementById('txtPenaltyClause').value == "" )
		{
			document.getElementById('txtPenaltyClause').value=0;
		}
		//satyapan OSP
		if((document.getElementById('txtOSPRegNo').isRequired=="1" ||document.getElementById('txtOSPTagging').value==1 ) && (document.getElementById('txtOSPRegNo').value == "" || document.getElementById('txtOSPRegNo').value ==null) )
		{
			alert("Please enter OSPRegNO");
			document.getElementById('txtOSPRegNo').focus();
			return false;
		}
	  	else if(document.getElementById('txtOSPRegNo').isRequired=="0" && document.getElementById('txtOSPRegNo').value=="")
		{
			document.getElementById('txtOSPRegNo').value="";
		}	
		 
		if((document.getElementById('txtOSPRegDate').isRequired=="1" ||document.getElementById('txtOSPTagging').value==1 )  && (document.getElementById('txtOSPRegDate').value == "" || document.getElementById('txtOSPRegDate').value ==null) )
		{
			alert("Please enter OSPRegDate");
			
			document.getElementById('txtOSPRegDate').focus();
			return false;
		}
		else if(document.getElementById('txtOSPRegDate').isRequired=="0" && document.getElementById('txtOSPRegDate').value=="" )
		{
			document.getElementById('txtOSPRegDate').value="";
		}
		
		
		//END satyapan OSP
		//WARRANTY CLAUSE ADDED BY MANISHA START
		if(document.getElementById('txtWarrantClause').isRequired=="1"  && document.getElementById('txtWarrantClause').value == "" )
		{
			alert("Please enter Warranty Clause");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtWarrantClause').focus();
			return false;
		}
		else if(document.getElementById('txtWarrantClause').isRequired=="0"  && document.getElementById('txtWarrantClause').value == "" )
		{
			document.getElementById('txtWarrantClause').value=0;
		}	
		
		//WARRANTY CLAUSE ADDED BY MANISHA end
			//  bcp details for services ADDED BY MANISHA START
		if(document.getElementById('hdnHardwareInfo').value!=1)
		{
			if(document.getElementById('bbPrimaryBCPService').isRequired=="1"  && (document.getElementById('bbPrimaryBCPService').value == "" || document.getElementById('txtBCPAddressSuggestService').value=="") )
			{
				alert("Please enter Ship to Address");
				but=document.getElementById('btnBillingAndChargesInformation');
						if(but.value=="+")
						{
							show('tblBillingAndChargesInformation',but);
						}
				document.getElementById('txtBCPAddressSuggestService').focus();
				
				return false;
			}
			else if(document.getElementById('bbPrimaryBCPService').isRequired=="0"  && (document.getElementById('bbPrimaryBCPService').value == "" || document.getElementById('txtBCPAddressSuggestService').value=="") )
			{
				document.getElementById('txtBCPAddressSuggestService').value=0;
			}					 
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="-")
			{
				show('tblBillingAndChargesInformation',but);
			}
		}	
		//  bcp details for services ADDED BY MANISHA END
		if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && (document.getElementById('bbPrimaryBCP').value == "" || document.getElementById('txtBCPAddressSuggest').value=="")  )
		{
			alert("Please enter   BCP details");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBCPAddressSuggest').focus();
			return false;
		}
		else if(document.getElementById('bbPrimaryBCP').isRequired=="0"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			document.getElementById('bbPrimaryBCP').value=0;
		}
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="-" && butBillFlag == true)
					{
						show('tblBillingAndChargesInformation',but);
					}
			//start[009]attFLI_PO_DisableValue					
			if(document.getElementById('hdnISFLEFLAG').value==1  && document.getElementById('txtBillingLvl').value!=2)
			{
				if(!(document.getElementById('txtBillingLvl').value==3 && attFLI_PO_DisableValue =="Y"))
				{
						
					alert("Billing Level should be PO Level");
					but=document.getElementById('btnBillingAndChargesInformation');
						if(but.value=="+")
						{
							butBillFlag = true;
							show('tblBillingAndChargesInformation',but);
						}
					document.getElementById('txtBillingLvl').focus();
					return false;
				}
			
			}
					
	}
	if(document.getElementById('hdnLocationInfo').value==1)
	{
		
		if(document.getElementById('ddPrimaryLocType').isRequired=="1"  && document.getElementById('ddPrimaryLocType').value == "0" )
		{
			alert("Please Enter Primary Location Type");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			alert("Please enter Primary Location Type");
			document.getElementById('ddPrimaryLocType').focus();
			return false;
		}
		else if(document.getElementById('ddPrimaryLocType').isRequired=="0"  && document.getElementById('ddPrimaryLocType').value == "0" )
		{
			document.getElementById('ddPrimaryLocType').value=0;
		}
		if(document.getElementById('ddSecondaryLocType').isRequired=="1"  && document.getElementById('ddSecondaryLocType').value == "0" )
		{
			alert("Please Enter Secondary Location Type");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}

			alert("Please enter Secondary Location Type");

			document.getElementById('ddSecondaryLocType').focus();
			return false;
		}
		else if(document.getElementById('ddSecondaryLocType').isRequired=="0"  && document.getElementById('ddSecondaryLocType').value == "0" )
		{
			if(document.getElementById('ddSecondaryLocType').value == "" || document.getElementById('ddSecondaryLocType').value == "0" ){
			document.getElementById('ddSecondaryLocType').value="0";	
			}
			else
			{  if(serviceTypeId==4)
			 document.getElementById('ddSecondaryLocType').value="2";
			}
		}
		
		if(document.getElementById('ddPrimaryLocType').value=="1" && document.getElementById("ddPrimaryLocType").isRequired=="1")
		{
			if(document.getElementById(("ddPrimaryBCP").value=="0" || document.getElementById('txtPriCustLocationSuggest').value == "0") && document.getElementById("ddPrimaryBCP").isRequired=="1")
			{
			alert("Please enter Primary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('txtPriCustLocationSuggest').focus();
			return false;
			}
			else if(document.getElementById('ddPrimaryBCP').isRequired=="0"  && ( document.getElementById('ddPrimaryBCP').value == "0" || document.getElementById('txtPriCustLocationSuggest').value == "0" ))
			{
			document.getElementById('txtPriCustLocationSuggest').value=0;
			}
			
		}
		if(document.getElementById('ddPrimaryLocType').value=="2" && document.getElementById('ddPrimaryLocType').isRequired=="1")
		{
			if(document.getElementById('ddPNLocation').value=="0" && document.getElementById('ddPNLocation').isRequired=="1")
			{
			alert("Please enter Primary Location Network ");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			return false;
			}
			else if(document.getElementById('ddPNLocation').isRequired=="0"  && document.getElementById('ddPNLocation').value == "0" )
			{
			document.getElementById('ddPNLocation').value=0;
			}
		}
		if(document.getElementById('ddSecondaryLocType').value=="1" && document.getElementById('ddSecondaryLocType').isRequired=="1")
		{
			if((document.getElementById("ddSecondaryBCP").value=="0" || document.getElementById("txtSecCustLocationSuggest").value=="0" ) && document.getElementById("ddSecondaryBCP").isRequired=="1")
			{
			alert("Please enter Secondary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('txtSecCustLocationSuggest').focus();
			return false;
			}
			else if(document.getElementById('ddSecondaryBCP').isRequired=="0"  && (document.getElementById('ddSecondaryBCP').value == "0" || document.getElementById("txtSecCustLocationSuggest").value=="0") )
			{
			document.getElementById('txtSecCustLocationSuggest').value=0;
			}
		}
		if(document.getElementById('ddSecondaryLocType').value=="2" && document.getElementById('ddSecondaryLocType').isRequired=="1")
		{
			if(document.getElementById('ddSNLocation').value=="0" && document.getElementById('ddSNLocation').isRequired=="1")
			{
			alert("Please enter Secondary Location Network ");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					show('tblServiceLocationDetails',but);
				}
			return false;
			}
			else if(document.getElementById('ddSNLocation').isRequired=="0"  && document.getElementById('ddSNLocation').value == "0" )
			{
			document.getElementById('ddSNLocation').value=0;
			}
		}
		
		if(document.getElementById('txtFAddress').isRequired=="1"  && document.getElementById('txtFAddress').value == "" )
		{
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('txtFAddress').focus();
		}
		else if(document.getElementById('txtFAddress').isRequired=="0"  && document.getElementById('txtFAddress').value == "" )
		{
			document.getElementById('txtFAddress').value=0;
		}
		if(document.getElementById('txtToAddress').isRequired=="1"  && document.getElementById('txtToAddress').value == "" )
		{
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('txtToAddress').focus();
		}
		else if(document.getElementById('txtToAddress').isRequired=="0"  && document.getElementById('txtToAddress').value == "" )
		{
			document.getElementById('txtToAddress').value=0;
		}
		but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="-" && butLocFlag == true)
				{
					show('tblServiceLocationDetails',but);
				}
	}
	if(document.getElementById('hdnHardwareInfo').value==1)
	{
		if(document.getElementById('txtStore').isRequired=="1"  && document.getElementById('txtStore').value == "0" )
		{
			alert("Please enter Store");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtStore').focus();
			return false;
		}
		else if(document.getElementById('txtStore').isRequired=="0"  && document.getElementById('txtStore').value == "0" )
		{
			document.getElementById('txtStore').value=0;
		}
		if(document.getElementById('txtHtype').value=="0" && document.getElementById('txtHtype').isRequired=="1")
		{
			alert("Please enter Hardware type");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtHtype').focus();
			return false;
		}
		else if(document.getElementById('txtHtype').value=="0" && document.getElementById('txtHtype').isRequired=="0")
		{
			document.getElementById('txtHtype').value=0;
		}
		if(document.getElementById('txtform').value=="0" &&document.getElementById('txtform').isRequired=="1" )
		{
			alert("Please enter Form available");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtform').focus();
			return false;
		}
		else if(document.getElementById('txtform').value=="0" &&document.getElementById('txtform').isRequired=="0" )
		{
			document.getElementById('txtform').value=0;
		}
		if(document.getElementById('txtNSale').value=="0" && document.getElementById('txtNSale').isRequired=="1" )
		{
			alert("Please enter Nature of sale");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtNSale').focus();
			return false;
		}
		else if(document.getElementById('txtNSale').value=="0" && document.getElementById('txtNSale').isRequired=="0")
		{
			document.getElementById('txtNSale').value=0;
		}
		if(document.getElementById('txtTSale').isRequired=="1"  && document.getElementById('txtTSale').value == "0" )
		{
			alert("Please Enter Type of Sale");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			alert("Please enter Type of sale");

			document.getElementById('txtTSale').focus();
			return false;
		}
		else if(document.getElementById('txtTSale').isRequired=="0"  && document.getElementById('txtTSale').value == "0" )
		{
			document.getElementById('txtTSale').value=0;
		}
		but=document.getElementById('btnHwDetails');
				if(but.value=="-" && butHWFlag == true)
				{
					show('tblHwDetails',but);
				}
		if(document.getElementById('txtdispatchAddress').isRequired=="1"  && (document.getElementById('txtDispatchAddress').value == "" || document.getElementById('txtDispatchAddressSuggest').value=="" )   )
		{
			alert("Please enter Dispatch Address");
			but=document.getElementById('btnDispatchAddress');
				if(but.value=="+")
				{
					show('tblDispatchAddress',but);
				}
			document.getElementById('txtDispatchAddressSuggest').focus();
			return false;
		}
		else if(document.getElementById('txtdispatchAddress').isRequired=="0"  && document.getElementById('txtdispatchAddress').value == "0" )
		{
			document.getElementById('txtdispatchAddress').value=0;
		}
		but=document.getElementById('btnDispatchAddress');
				if(but.value=="-")
				{
					show('tblDispatchAddress',but);
				}
	if((document.getElementById('txtStartDateLogic').value != "0" || document.getElementById('txtEndDateLogic').value != "0" )
		||
		(document.getElementById('txtStartDateLogic').isRequired==1 || document.getElementById('txtEndDateLogic').isRequired==1 ||
			document.getElementById('txtHStartMonth').isRequired==1 || document.getElementById('txtHEndMonth').isRequired==1 ||
			document.getElementById('txtHStartDays').isRequired==1 || document.getElementById('txtHEndDays').isRequired==1 ||
			document.getElementById('txtHExtMonth').isRequired==1 || document.getElementById('txtHExtDays').isRequired==1
			)
		)
	{
		if( document.getElementById('txtStartDateLogic').value == "0" )
		{
			alert("Please enter Start Date Logic");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtStartDateLogic').focus();
			return false;
		}
		
		if( document.getElementById('txtHStartMonth').value == "" )
		{
			alert("Please enter Start Month ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHStartMonth').focus();
			return false;
		}
		
		if( document.getElementById('txtHStartDays').value == "" )
		{
			alert("Please enter Start Days ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHStartDays').focus();
			return false;
		}
		
		if( document.getElementById('txtEndDateLogic').value == "0" )
		{
			alert("Please enter End Date Logic");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtEndDateLogic').focus();
			return false;
		}
		
		
		
		if( document.getElementById('txtHEndMonth').value == "" )
		{
			alert("Please enter End Month ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHEndMonth').focus();
			return false;
		}
		if( document.getElementById('txtHEndDays').value == "" )
		{
			alert("Please enter End Days ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHEndDays').focus();
			return false;
		}
		if( document.getElementById('txtHExtMonth').value == "" )
		{
			alert("Please enter Extended Month");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHExtMonth').focus();
			return false;
		}
		if( document.getElementById('txtHExtDays').value == "" )
		{
			alert("Please enter Extended Days");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHExtDays').focus();
			return false;
		}
		
		
	}
		but=document.getElementById('btnWarrentyDetails');
				if(but.value=="-" && butWarranFlag == true)
				{
					show('tblWarrentyDetails',but);
				}
	}
		if(document.getElementById("chkSelectNfa").value=="on")
			 {
			 document.getElementById("chkSelectNfa").value=0
			 }
		var jsData =
		{
			serviceDetailID:Number(document.getElementById('serviceDetailID').value),
			serviceId:Number(servicesID),
			serviceTypeId:Number(serviceTypeId),
			podetailID:Number(document.getElementById('txtPODetailNo').value),
			accountID:Number(document.getElementById('txtBillingAC').value),
			creditPeriod:Number(document.getElementById('txtBillingCP').value),
			entityID:Number(document.getElementById('txtEntity').value),
			billingType:Number(document.getElementById('txtBillingType').value),
			billingMode:document.getElementById('txtBillingMode').value,
			noticePeriod:Number(document.getElementById('txtNoticePeriod').value),
			billingformat:document.getElementById('txtBillingformat').value,
			licenceCoID:Number(document.getElementById('txtLicenceCo').value),
			taxation:document.getElementById('txtTaxation').value,
			stdReasonId:document.getElementById('changeReason').value,
			commitmentPeriod:Number(document.getElementById('txtCmtPeriod').value),
			billingLevel:document.getElementById('txtBillingLvl').value,
			penaltyClause:document.getElementById('txtPenaltyClause').value,
			warrantyClause:document.getElementById('txtWarrantClause').value,
			//satyapan osp
			isOSPTagging:document.getElementById('txtOSPTagging').value,//satyapan osp
			txtOSPRegNo:document.getElementById('txtOSPRegNo').value,
			txtOSPRegDate:document.getElementById('txtOSPRegDate').value,
			//satyapan osp
			serviceType:document.getElementById('txtHdnServiceName').value,
			selectedDispatchID:Number(document.getElementById('txtdispatchAddress').value),
			//[003] start
			dispatchContactName:document.getElementById('txtDispatchContactName').value,
			//[003] end
			selectedStoreID:document.getElementById('txtStore').value,
			formAvailable:document.getElementById('txtform').value,
			hardwareType:document.getElementById('txtHtype').value,
			saleNature:document.getElementById('txtNSale').value,
			saleType:document.getElementById('txtTSale').value,
			selectedPriLocType:Number(document.getElementById('ddPrimaryLocType').value),
			selectedSecLocType:Number(document.getElementById('ddSecondaryLocType').value),
			selectedPNLocation:Number(document.getElementById('ddPNLocation').value),
			selectedPrimaryBCP:Number(document.getElementById('ddPrimaryBCP').value),
			selectedSNLocation:Number(document.getElementById('ddSNLocation').value),
			selectedSecBCP:Number(document.getElementById('ddSecondaryBCP').value),
			billingLevelNo:Number(document.getElementById('txtBillingLevelNo').value),
			chargesDetails : chargesDetailList,
			serviceInfoValue:Number(document.getElementById('hdnServiceSummary').value),
			billingInfoValue:Number(document.getElementById('hdnBillingInfo').value),
			chargeInfoValue:Number(document.getElementById('hdnChargeInfo').value),
			locationInfoValue:Number(document.getElementById('hdnLocationInfo').value),
			hardwareInfoValue:Number(document.getElementById('hdnHardwareInfo').value),
			billingBCPId:document.getElementById('bbPrimaryBCPID').value,
			billingBCPIdService:document.getElementById('bbPrimaryBCPIDService').value,
			parentServiceProductId:document.getElementById('id_parentServiceProductId').value,
			startHWDateLogic:document.getElementById('txtStartDateLogic').value,
			endHWDateLogic:document.getElementById('txtEndDateLogic').value,
			fromLocation:document.getElementById('txtFAddress').value,
		    toLocation:document.getElementById('txtToAddress').value,
			startDate:document.getElementById('txtStartDate').value,
			endDate:document.getElementById('txtEndDate').value,
			isNfa:Number(document.getElementById('chkSelectNfa').value),
			isUsage:Number(document.getElementById('chkIsUsage').value),
			billingScenario:document.getElementById('txtBillingScenario').value,
			fxRedirectionSPID:Number(document.getElementById('hdnFxRedirectionSPID').value),
			fxRedirectionLSI:Number(document.getElementById('txtFxRedirectionLSI').value),
		    txtStartMonth:Number(document.getElementById('txtHStartMonth').value),
			txtStartDays:Number(document.getElementById('txtHStartDays').value),
			txtEndMonth:Number(document.getElementById('txtHEndMonth').value),
			txtEndDays:Number(document.getElementById('txtHEndDays').value),
			txtExtMonth:Number(document.getElementById('txtHExtMonth').value),
			txtExtDays:Number(document.getElementById('txtHExtDays').value),
			txtExtDate:document.getElementById('txtHExtDate').value,
		    isReqTxtPODetailNo:Number(document.getElementById('txtPODetailNo').isRequired),
			isReqTxtBillingAC:Number(document.getElementById('txtBillingAC').isRequired),
			isReqTxtBillingCP:Number(document.getElementById('txtBillingCP').isRequired),
			isReqTxtEntity:Number(document.getElementById('txtEntity').isRequired),
			isReqTxtBillingMode:Number(document.getElementById('txtBillingMode').isRequired),
			isReqTxtBillingformat:Number(document.getElementById('txtBillingformat').isRequired),
			isReqTxtLicenceCo:Number(document.getElementById('txtLicenceCo').isRequired),
			isReqTxtTaxation:Number(document.getElementById('txtTaxation').isRequired),
			isReqTxtCmtPeriod:Number(document.getElementById('txtCmtPeriod').isRequired),
			isReqTxtBillingLvl:Number(document.getElementById('txtBillingLvl').isRequired),
			isReqTxtPenaltyClause:Number(document.getElementById('txtPenaltyClause').isRequired),
			isReqTxtWarrantyClause:Number(document.getElementById('txtWarrantClause').isRequired),
			//satyapan OSP By nagarjuna
			isReqOSPTagging:Number(document.getElementById('txtOSPTagging').isRequired),//satyapan osp
			isReqOSPRegNo:Number(document.getElementById('txtOSPRegNo').isRequired),
			isReqOSPRegDate:Number(document.getElementById('txtOSPRegDate').isRequired),
			//Satyapan OSP By Nagarjuna
			isReqSelectedDispatchID:Number(document.getElementById('txtdispatchAddress').isRequired),
			//[003]start
			isReqDispatchContactName:Number(document.getElementById('txtDispatchContactName').isRequired),
			//[003]end
			isReqDdPrimaryLocType:Number(document.getElementById('ddPrimaryLocType').isRequired),
			isReqDdSecondaryLocType:Number(document.getElementById('ddSecondaryLocType').isRequired),
			isReqSelectedPNLocation:Number(document.getElementById('ddPNLocation').isRequired),
			isReqSelectedPrimaryBCP:Number(document.getElementById('ddPrimaryBCP').isRequired),
			isReqSelectedSNLocation:Number(document.getElementById('ddSNLocation').isRequired),
			isReqSelectedSecBCP:Number(document.getElementById('ddSecondaryBCP').isRequired),
			isReqTxtStore:Number(document.getElementById('txtStore').isRequired),
			isReqTxtHtype:Number(document.getElementById('txtHtype').isRequired),
			isReqTxtform:Number(document.getElementById('txtform').isRequired),
			isReqTxtTSale:Number(document.getElementById('txtTSale').isRequired),
			isReqTxtNSale:Number(document.getElementById('txtNSale').isRequired),
			isReqTxtStore:Number(document.getElementById('txtStartDateLogic').isRequired),
			isReqTxtHtype:Number(document.getElementById('txtEndDateLogic').isRequired),
			isReqTxtFromLocation:Number(document.getElementById('txtFAddress').isRequired),
			isReqTxtToLocation:Number(document.getElementById('txtToAddress').isRequired),
			poNumber:changeOrderNo,
			changeTypeId:Number(changeTypeId),
			componentDetails:componentsList,
			componentInfoValue:Number(document.getElementById('hdnComponentInfo').value),
			configValue:Number(document.getElementById('hdnConfigValue').value)
		};
	}
	/*if((document.getElementById('hdnServiceSummary').value==1) && attributeID != null)
	  {
	         var jsonrpc1 = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	       var productType = jsonrpc1.processes.checkProductServiceType(serviceDetID);
    	       if(productType=='Hardware')
    	       {
    	        var Billingformat=document.getElementById('txtBillingformat').value;
			    var result=  checkProductServiceType(attributeID,attributeVal,Billingformat);
			   if(result == false)
			    return;
			  }
	}*/
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	try
	{
    	var sessionId='<%=request.getSession().getId() %>';
    	var insertPcat = jsonrpc.processes.insertProductCatelog(jsData,sessionId);
    	
    	
    	    	document.getElementById("serviceproductid").value=insertPcat.serviceProductID;
    	if(document.getElementById('hdnComponentInfo').value==1)
    	{
   	       var dtoListComponentIds=insertPcat.listComponent;
			var chkComponents=document.getElementsByName('chkComponents');
			if(dtoListComponentIds!=null)
			{
				if(chkComponents.length > 0)
				{
					for(i=0;i<chkComponents.length;i++)
					{
			  		var chkboxComponent=chkComponents[i];
			  		var index=chkboxComponent.value;
			    	document.getElementById("component_infoID"+index).value = dtoListComponentIds.list[i];
					}
				}	
			}
		}
		if(document.getElementById('hdnChargeInfo').value==1)
		{
    	    var dtoListChargeInfoIds=insertPcat.listChargeInfoIdList;
	        var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	        if(dtoListChargeInfoIds!=null)
	        {
		        if(chkChargeLine.length > 0)
		        {
			    	for(i=0;i<chkChargeLine.length;i++)
					{
						var chkboxCharge=chkChargeLine[i];
						var index=chkboxCharge.value;
					    document.getElementById("hdnChargeId"+index).value = dtoListChargeInfoIds.list[i];
					}
				}
			}	
        }	
    }
    catch(e)
    {	alert("exception :  "+ e);
    }
    
    if(isNull(insertPcat.errors)==true)
    {
		var callerWindowObj = dialogArguments;
		counterVal=callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;
		var nam="productEntered"+counterVal;
		callerWindowObj.document.getElementById(nam).value = '1';
    	alert(insertPcat.msgOut);  
    	//var flag=5;
		callerWindowObj.ServiceTreeViewAfterDisconnection(serviceid);
		//callerWindowObj.countService=1;
    	//callerWindowObj.drawTable();	
    }	
    else
    {
    	alert(insertPcat.errors.list[0]);		
    	return false;
    }
}

function fnCalculateHardwareEndDate(startDate,period)
{		
	str1=new String(startDate);
	y=str1.substring(6);
	m=str1.substring(3,5);

	var totalMonths=Number(m)+Number(period);
	
	if(totalMonths<12)
	{
		str2=new String(startDate);
		y=str2.substring(6);
		m=totalMonths;
		d=str2.substring(0,2);
		var date2=new Date(Number(y),Number(m)-1,Number(d));	
	}else if(totalMonths>=12){
		str2=new String(startDate);
		y=str2.substring(6);
		m=totalMonths;
		d=str2.substring(0,2);
		var date2=new Date(Number(y),Number(m)-1,Number(d));
	}
	d2=date2.getDate();
	m2=date2.getMonth()+1;
	y2=date2.getYear();
	var endDate =d2 + "/" + m2 + "/" + y2 ;
	document.getElementById('txtEndDate').value=new String(endDate);
return;
}
function FetchBillingDetails()
{
	var tr, td, i, j, oneRecord;
    var test5;
   	if(document.getElementById("bbPrimaryBCP").value != "")
    {
	    var jsData5 =			
	    {
			bcpID:Number(document.getElementById("bbPrimaryBCP").value)
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    test5 = jsonrpc.processes.populateBCPDetails(jsData5);
		
	    if(test5.list.length>0) //if logic added by saurabh
	    {
		    document.getElementById('txtBAddress1').value = test5.list[0].baddress1;
			document.getElementById('txtBAddress2').value = test5.list[0].baddress2;
			document.getElementById('txtBAddress3').value = test5.list[0].baddress3;
			document.getElementById('txtBAddress4').value = test5.list[0].baddress4;
			document.getElementById('txtBCity').value = test5.list[0].bcity;
			document.getElementById('txtBState').value = test5.list[0].bstate;
			document.getElementById('txtBPincode').value = test5.list[0].bpincode;
			document.getElementById('txtBCountry').value = test5.list[0].bcountry;
			document.getElementById('txtBContactNo').value = test5.list[0].bcontactNo;
			document.getElementById('txtBEmailId').value = test5.list[0].bemailid;
			document.getElementById('designation').value = test5.list[0].designation;
			document.getElementById('lst_No').value = test5.list[0].lst_No;
			document.getElementById('lst_Date').value = test5.list[0].lstDate;
			document.getElementById('txtBContactName').value = test5.list[0].bcontactName;
			//011	Start
			document.getElementById('txtCircle').value = test5.list[0].revCircle;
			//011	End
		}
	}
	else
	{ 
	    document.getElementById('txtBAddress1').value = "";
		document.getElementById('txtBAddress2').value = "";
		document.getElementById('txtBAddress3').value = "";
		document.getElementById('txtBAddress4').value = "";
		document.getElementById('txtBCity').value = "";
		document.getElementById('txtBState').value = "";
		document.getElementById('txtBPincode').value = "";
		document.getElementById('txtBCountry').value = "";
		document.getElementById('txtBContactNo').value = "";
		document.getElementById('txtBEmailId').value = "";
		document.getElementById('designation').value = "";
		document.getElementById('lst_No').value = "";
		document.getElementById('lst_Date').value = "";
		document.getElementById('txtBContactName').value = "";
		//011	Start
		document.getElementById('txtCircle').value = "";
		//011	End
	}
}
//bcp details for services ADDED BY MANISHA START
function FetchBillingDetailsforService()
{
	var tr, td, i, j, oneRecord;
    var test5;
   	if(document.getElementById("bbPrimaryBCPService").value != "")
    {
    	var jsData5 =			
    	{
			bcpID:document.getElementById("bbPrimaryBCPService").value
		};
	
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

    	test5 = jsonrpc.processes.populateBCPDetails(jsData5);
	
		if(test5.list.length>0)
		{
		    document.getElementById('txtBAddress1Service').value = test5.list[0].baddress1;
			document.getElementById('txtBAddress2Service').value = test5.list[0].baddress2;
			document.getElementById('txtBAddress3Service').value = test5.list[0].baddress3;
			document.getElementById('txtBAddress4Service').value = test5.list[0].baddress4;
			document.getElementById('txtBCityService').value = test5.list[0].bcity;
			document.getElementById('txtBStateService').value = test5.list[0].bstate;
			document.getElementById('txtBPincodeService').value = test5.list[0].bpincode;
			document.getElementById('txtBCountryService').value = test5.list[0].bcountry;
			document.getElementById('txtBContactNoService').value = test5.list[0].bcontactNo;
			document.getElementById('txtBEmailIdService').value = test5.list[0].bemailid;
			document.getElementById('designationService').value = test5.list[0].designation;
			document.getElementById('lst_NoService').value = test5.list[0].lst_No;
			document.getElementById('lst_DateService').value = test5.list[0].lstDate;
			document.getElementById('txtBContactNameService').value = test5.list[0].bcontactName;
			//010	Start
			document.getElementById('txtCircleService').value = test5.list[0].revCircle;
			//010	End
		}
	}
	else
	{ 
	     	document.getElementById('txtBAddress1Service').value = "";
			document.getElementById('txtBAddress2Service').value = "";
			document.getElementById('txtBAddress3Service').value = "";
			document.getElementById('txtBAddress4Service').value = "";
			document.getElementById('txtBCityService').value = "";
			document.getElementById('txtBStateService').value = "";
			document.getElementById('txtBPincodeService').value ="";
			document.getElementById('txtBCountryService').value = "";
			document.getElementById('txtBContactNoService').value ="";
			document.getElementById('txtBEmailIdService').value = "";
			document.getElementById('designationService').value = "";
			document.getElementById('lst_NoService').value ="";
			document.getElementById('lst_DateService').value ="";
			document.getElementById('txtBContactNameService').value = "";
			//010	Start
			document.getElementById('txtCircleService').value = "";
			//010	End
	}
}

//bcp details for services ADDED BY MANISHA END
function FetchBillingLocationAddress()
{
	var tr, td, i, j, oneRecord;
    var test7;
   
    var jsData7 =			
    {
		plocationCode:document.getElementById("bbPNLocation").value
	};
	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

    test7 = jsonrpc.processes.populateNPLocationAddress(jsData7);

    document.getElementById('txtBAddress').value = test7.list[0].ptxtPAddress;
}

function show(tbl,btn)  
{
	if (btn.value=="-")
	{
		document.all.item(tbl).style.display="None";
		btn.value="+";
	}
	else
	{
		document.all.item(tbl).style.display="Inline";
		btn.value="-";
	}
}
function PopLocTypePopup(LOC) 
	{
		    if (LOC=='PRILOC')
		    {
		      document.getElementById("location").value='PRILOC';
		    }
		    else
		    {
		     document.getElementById("location").value='SECLOC';
		    }
			var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchNetworkPopLocation";		
			window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
	}
	function checkNfa()
	{
		if(document.getElementById("chkSelectNfa").checked==true)
		{
		document.getElementById("chkSelectNfa").value=1
		alert("You are converting this order as NFA");
		}
		else
		{
		document.getElementById("chkSelectNfa").value=0
		}
	}
document.onkeydown=checkKeyPress;
function checkKeyPress()
{
	if (event.ctrlKey && event.keyCode == 83) {						
				//alert('This functionality is not allowed for this page.Please click on the SAVE button to save!! ');
				//return false;				
				event.keyCode=4; 
				saveProductCatelog();
				document.getElementById("saveImage").tabIndex = -1;
				document.getElementById("saveImage").focus();					   				   						
   }   
}
var hook=true;
window.onbeforeunload = function() 
{
    if (hook) 
    if ((window.event.clientX < 0) || (window.event.clientY < 0) || (window.event.clientX < -80))
    {
    {
      return "This will cause losing any unsaved data ! "
    } 
    }   
}
function unhook() 
{
     hook=false;
}

function fnCheckForM6LineItem()
{
	var sendToM6Check;
	var serviceDetailId = document.getElementById('serviceDetailID').value;
	if(document.getElementById('ddPrimaryLocType').value==2)
	{
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailId);
		if(sendToM6Check==0)
		{
			alert('Network location is not Allowed for this Line Item')
			document.getElementById('ddPrimaryLocType').value=0;
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
			return false;
		}
	}
	if(document.getElementById('ddSecondaryLocType').value==2)
	{
		var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailId);
		if(sendToM6Check==0)
		{
			alert('Network location is not Allowed for this Line Item')
			document.getElementById('ddSecondaryLocType').value=0;
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none';
			return false;
		}
	}
}
var destAttID=new Array(); 
//lawkush Start

/*
Method fetched List of values for Dropdowns

*/
function fnGetDestLabelValue(attMstrId,fieldName)
{
	var labelValue;
	var textValue = fieldName.value;
	var select_list_field = fieldName; 
	var select_list_selected_index = select_list_field.selectedIndex;
	var attLabelvalue = select_list_field.options[select_list_selected_index].text;
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	var jsData =			
    {
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};
	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    labelValue = jsonrpc.processes.fillDropDownDependentlabel(jsData);
    if(labelValue.list.length>0)
	{
		for(var k=0;k<destAttID.length;k++)
		{
			destAttID.pop();
		}
		destAttID=new Array();
		for(var j = 0;j<labelValue.list.length;j++)
		{
			destAttID[j] = labelValue.list[j].destAttId;
			for(var i=1;i<=countAttributes;i++)
			{
				if(document.getElementById("hdnProdAttVal_"+i) != null)
				{
					if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[j])
					{
						//[004.1] Start
						var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc 
						if(depValue.indexOf("prodAttVal")!=-1)//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
						{
							var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
							document.getElementById("prodAttVal_"+i).value=document.getElementById("prodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
						}
						else
						{
							document.getElementById("prodAttVal_"+i).value=depValue;//Assigning value from DB
						}
						//Start [005]
						if(document.getElementById("autoSuggProdAttVal_"+i) != null)
						{
							document.getElementById("autoSuggProdAttVal_"+i).value='';
						}
						//End [005]
						//[004.1] End
						if(labelValue.list[j].isReadOnly == '1')
						{
							if(document.getElementById("prodAttVal_"+i).type=='select-one')
								document.getElementById("prodAttVal_"+i).disabled=true;
							else
								document.getElementById("prodAttVal_"+i).readOnly=true; 
							//added zeroValueAllowed control to check whether zero value is allowed on that control or not
							document.getElementById("prodAttVal_"+i).zeroValueAllowed='Y';
						}
						if(labelValue.list[j].isReadOnly == '0')
						{
							if(document.getElementById("prodAttVal_"+i).type=='select-one')
								document.getElementById("prodAttVal_"+i).disabled=false;
							else
								document.getElementById("prodAttVal_"+i).readOnly=false; 
							//added zeroValueAllowed control to check whether zero value is allowed on that control or not
							document.getElementById("prodAttVal_"+i).zeroValueAllowed='N';
						}
						//[004.1] Start
						if(labelValue.list[j].mandatory == '0')
						{
							document.getElementById("prodAttVal_"+i).isSummaryReq='0';
							document.getElementById("prodAttVal_"+i).className='inputBorder2';
						}
						if(labelValue.list[j].mandatory == '1')
						{
							document.getElementById("prodAttVal_"+i).isSummaryReq='1';
							document.getElementById("prodAttVal_"+i).className='inputBorder1';
						}
						//[004.1] End
					}
				}
			}	
		}
	}
}
/*
//lawkush
Method fetched List of values for auto suggestion 

*/
function fnGetDestLabelValueForLOV(attMstrId,fieldName)
{
	var labelValue;
	//var textValue = fieldName.value;	
	//var select_list_field = fieldName; 
	//var select_list_selected_index = select_list_field.selectedIndex;
	//var attLabelvalue = select_list_field.options[select_list_selected_index].text;
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	var attLabelvalue = fieldName;
	var jsData =			
    {
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};
	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    labelValue = jsonrpc.processes.fillDropDownDependentlabel(jsData);
    if(labelValue.list.length>0)
	{
		for(var k=0;k<destAttID.length;k++)
		{
			destAttID.pop();
		}
		destAttID=new Array();
		for(var j = 0;j<labelValue.list.length;j++)
		{
			destAttID[j] = labelValue.list[j].destAttId;
			for(var i=1;i<=countAttributes;i++)
			{
				if(document.getElementById("hdnProdAttVal_"+i) != null)
				{
					if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[j])
					{
						//[004.1] Start
						var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc 
						//destText Added By Kalpana to get Text for selectd Plan
						var destText=labelValue.list[j].destText;//Assigning Default TEXT Value from DB to destination attribute
						//end
						if(depValue.indexOf("prodAttVal")!=-1)//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
						{
							var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
							document.getElementById("prodAttVal_"+i).value=document.getElementById("prodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
						}
						else
						{
							document.getElementById("prodAttVal_"+i).value=depValue;//Assigning value from DB
							//set value in id autosuggest by kalpana
							if(document.getElementById("autoSuggProdAttVal_"+i) != null){
								if(destText!=null){
									document.getElementById("autoSuggProdAttVal_"+i).value=destText;
								}else{
									document.getElementById("autoSuggProdAttVal_"+i).value=depValue;
								}
							}
							//end
						}
						/*if(document.getElementById("autoSuggProdAttVal_"+i) != null)
						{
							document.getElementById("autoSuggProdAttVal_"+i).value='';
						}*/
						//[004.1] End
						if(labelValue.list[j].isReadOnly == '1')
						{
							if(document.getElementById("prodAttVal_"+i).type=='select-one')
								document.getElementById("prodAttVal_"+i).disabled=true;
							else
								document.getElementById("prodAttVal_"+i).readOnly=true; 
							//added zeroValueAllowed control to check whether zero value is allowed on that control or not
							document.getElementById("prodAttVal_"+i).zeroValueAllowed='Y';
							if(document.getElementById("autoSuggProdAttVal_"+i) != null)
							{
								document.getElementById("autoSuggProdAttVal_"+i).disabled=true;
								document.getElementById("autoSuggProdAttLink_"+i).style.display='none';															
							}
						}
						if(labelValue.list[j].isReadOnly == '0')
						{
							if(document.getElementById("prodAttVal_"+i).type=='select-one')
								document.getElementById("prodAttVal_"+i).disabled=false;
							else
								document.getElementById("prodAttVal_"+i).readOnly=false; 
							//added zeroValueAllowed control to check whether zero value is allowed on that control or not
							document.getElementById("prodAttVal_"+i).zeroValueAllowed='N';
							if(document.getElementById("autoSuggProdAttVal_"+i) != null)
							{
								document.getElementById("autoSuggProdAttVal_"+i).disabled=false;
								document.getElementById("autoSuggProdAttLink_"+i).style.display='block';															
							}
						}
						//[004.1] Start
						if(labelValue.list[j].mandatory == '0')
						{
							document.getElementById("prodAttVal_"+i).isSummaryReq='0';
							document.getElementById("prodAttVal_"+i).className='inputBorder2';
						}
						if(labelValue.list[j].mandatory == '1')
						{
							document.getElementById("prodAttVal_"+i).isSummaryReq='1';
							document.getElementById("prodAttVal_"+i).className='inputBorder1';
						}
						//[004.1] End
					}
				}
			}	
		}
	}
}
//lawkush End
//[004.1] Start
function copyTextValue(sourceValue)
{
	//alert(document.getElementById('hdnSeriveAttCounter').value)
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	for(var i=1;i<=countAttributes;i++)
	{
		if(document.getElementById("hdnProdAttVal_"+i).value==2109)
		{
			if((document.getElementById("prodAttVal_"+i).value != "1") && (document.getElementById("prodAttVal_"+i).value!="19") && (document.getElementById("prodAttVal_"+i).value!="0"))
			{
				document.getElementById("prodAttVal_18").value=document.getElementById("prodAttVal_4").value;
			}
		}
	}
}	
//[004.1] End
function popUpServiceSummary(attId,counter,popUpId)
{
	if(popUpId=='popLSINoForMBIC')
	{
		//popLSINoForMBIC(attId,counter) //No Need to call this method
		return false;
	}
	else if(popUpId=='popUpForVCSBridgeLSILookup')
	{
		popUpForVCSBridgeLSILookup(attId,counter)
	}
}
function popUpForArborLSILookup(attId,counter)
{
	var callerWindowObj = dialogArguments;
	var ib2bOrderNo = callerWindowObj.document.getElementById('poNumber').value;
	var serviceDetailID=document.getElementById("serviceDetailID").value;
	var configId=document.getElementById("hdnConfigValue").value;
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=arborLSILookup&fxChargeRedirectionTypeCumulative="+fxChargeRedirectionTypeCumulative;	
	path=path+"&logicalSIno="+logicalSIno+"&orderNo="+ib2bOrderNo+"&configId="+configId+"&serviceDetailID="+serviceDetailID+"&fxChargeRedirectionType="+fxChargeRedirectionType;
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:700px");
}
function popUpForVCSBridgeLSILookup(attMasterId,productCounter)
{
	var serviceProductID=0;
	var serviceDetailID=document.getElementById('serviceDetailID').value;
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchVCS_BridgeLSI&attMasterId="+attMasterId+"&ib2bOrderNo="+poNumber+"&servicesID="+servicesID+"&logicalSIno="+logicalSIno+"&productCounter="+productCounter+"&serviceProductID="+serviceProductID+"&serviceDetailID="+serviceDetailID;		
	window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
}
	//TRNG22032013026 added by manisha start
function viewserviceproductattributes() 
{
      if(serviceID_Product == "")
      {
        alert('Please Select Service First');
      }
      
      else
      {
      	callerWindowObj.document.getElementById('hdnserviceid').value=serviceID_Product;
		var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=getTProductAttDetail&hdnserviceid="+serviceID_Product+'&hdnserviceTypeId='+serviceid+"&isView2=1&pc=1";		
		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//window.open(path);
	 }
	 
}
	//TRNG22032013026 added by manisha end
//[017] Start	
function advPayment()
{
	if(document.getElementById('serviceproductid').value!="")
	{
		
		if(document.getElementById('hdnBillingInfo').value==0)
		{
			alert("User can't input Advance Payment Details as 17 Parameter for this line item doesn't get generated");
			return false;
		}
		else
		{
			var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=getAdvancePaymentDetails&orderNo="+poNumber+"&lineItemNo="+document.getElementById('serviceproductid').value+"&serviceNo="+serviceID_Product+"&logicalSIno="+logicalSIno+"&isView=null";
			window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
		}
	}
	else
	{
		alert("Please generate a Line item");
		return false
	}
}
function resetBCPAddress(){
		document.getElementById('txtBCPAddressSuggestService').value = "";
		document.getElementById('bbPrimaryBCPIDService').value = "0";
		document.getElementById('txtBAddress1Service').value = "";
		document.getElementById('txtBAddress2Service').value = "";
		document.getElementById('txtBAddress3Service').value = "";
		document.getElementById('txtBAddress4Service').value = "";
		document.getElementById('txtBCityService').value = "";
		document.getElementById('txtBStateService').value = "";
		document.getElementById('txtBPincodeService').value ="";
		document.getElementById('txtBCountryService').value = "";
		document.getElementById('txtBContactNoService').value ="";
		document.getElementById('txtBEmailIdService').value = "";
		document.getElementById('designationService').value = "";
		document.getElementById('lst_NoService').value ="";
		document.getElementById('lst_DateService').value ="";
		document.getElementById('txtBContactNameService').value = "";
		//010	Start
		document.getElementById('txtCircleService').value = "";
}
//[017] End
</script>
<body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/NewOrderAction" styleId="productCatelog"
	method="post">
	<bean:define id="formBean" name="newOrderBean"></bean:define>
	<input type="hidden" id="hdnservicetypeid" name="hdnservicetypeid" />
	<input type="hidden" id="hdnchangetypeid" name="hdnchangetypeid" />
	<DIV class="head">Product Catalog</DIV>
	<fieldset class="border1">
	<legend> <b>Product
	Catalog</b> </legend>
	<div class="scroll" style="height:100%">
	<table border="1" cellspacing="0" cellpadding="0" align="center"
		width="100%">
		<tr id="SCMBtnID" align="center">
			<td colspan="0" align="left"><label class="hintanchor"><b>Service
			No:</b><%=request.getParameter("serviceID")%></label></td>
			<td colspan="0" align="left"><label class="hintanchor"><b>Service
			Name:</b><%=request.getParameter("selectedServiceName")%></label></td>
			<td colspan="0" align="left"><label class="hintanchor"><b>LineItemID:</b></label><input
				type="text" readonly="readonly" id="serviceproductid"
				name="serviceproductid" /></td>
		</tr>
	</table>

	<table border="1" cellspacing="0" cellpadding="0" align="center"
		width="100%">
		<tr align="center">
			<td colspan="1" align="right" style="font-size:11px">Select a
			Line Item</td>
			<logic:notEmpty name="serviceTypeList" scope="request">
				<td colspan="1" align="left" class=""><html:select
					onmouseover='getTip_DD(this,this.selectedIndex,this.name)'
					property="serviceDetailID" style="width:400px;float:left;"
					onchange="scmButtonEnable();if(checkCommercialLineAllowed()){getParentServiceProduct();}else{document.getElementById('idserviceProductTR').style.display='none';fnHideAll();this.selectedIndex=0;}">
					<html:option value="0">Select a Line Item</html:option>
					<logic:iterate id="row1" name="serviceTypeList" indexId="index1">
						<html:option value="${row1.serviceDetailID}">
							<c:out value="${row1.serviceDetDescription}" />
						</html:option>
					</logic:iterate>
				</html:select>
				<div id="hiddendd" style="display:none"><html:select
					onmouseover='getTip_DD(this,this.selectedIndex,this.name)'
					property="hdnServiceDetailID" style="width:400px"
					onchange="getServiceAttributes()">
					<html:option value="0">Select a Product</html:option>
					<logic:iterate id="row1" name="serviceTypeList" indexId="index1">
						<html:option value="${row1.serviceDetailID}">
							<c:out value="${row1.serviceTypeDescription}" />
						</html:option>
					</logic:iterate>
				</html:select></div>
			<img src="gifs/top/4.gif" onClick="saveProductCatelog();"
					style="display: block; float: left" id="saveImage"
					title="Save Product Information">
						<div class='searchBg1' style="float: left"  align="right"><a href='#' title='View ServiceProductAttribute' style="float: left" onclick=viewserviceproductattributes()>...</a></div>
						<!--[017] Start-->
						<input type="button" value="Advance Payment Details" onClick="advPayment();" style="display: block;" id="coinImage" title="View Advance Payment Information"/>
						<!--[017] End-->	
					</logic:notEmpty>
					</td>
		</tr>
		<tr id="idserviceProductTR" style="display: none;">
			<td align="right" style="font-size:11px">Parent Product Id</td>
			<td align="left" style="font-size:11px"><html:select
				onmouseover='getTip_DD(this,this.selectedIndex,this.name)'
				property="parentServiceProductId"
				styleId="id_parentServiceProductId" style="width:240px"
				onchange="fnshowOnFlags();">

			</html:select> <span id="idparentPrdCatelog" style="display: none;"></span></td>
		</tr>
		<tr id="serviceType" style="display:none">
			<td colspan="1" align="right" style="font-size:11px">Service
			Type</td>
			<td colspan="1" align="left" style="font-size:11px;"><input
				onmouseover='getTip(value)' onmouseout='UnTip()' type="text"
				readonly="readonly" style="width:235px" value=""
				name="txtHdnServiceName" /> <input type="hidden" readonly="readonly"
				value="" name="hdnServiceSummary" /> <input type="hidden"
				readonly="readonly" value="" name="hdnBillingInfo" /> <input
				type="hidden" readonly="readonly" value="" name="hdnChargeInfo" /> <input
				type="hidden" readonly="readonly" value="" name="hdnLocationInfo" />
	<input type="hidden" value="" name="hdnISFLEFLAG" id="hdnISFLEFLAG"/>
				<input type="hidden" readonly="readonly" value=""
				name="hdnHardwareInfo" /> 
							<input type="hidden" readonly="readonly" value="" name="hdnComponentInfo"/>
							<input type="hidden" readonly="readonly" value="changeproductctlg" name="hdnpagename"/>
							<input type="hidden" value="0" name="hdnConfigValue" id="hdnConfigValue"/>
							<input type="hidden" value="0" name="hdnFxRedirectionSPID" id="hdnFxRedirectionSPID"/>
							<input type="hidden" name="accNo" id="accNo" value="">
						</td>
		</tr>
		<tr align="center">
			<td colspan="2" width="50%"><jsp:include flush="true"
				page="ChangeServiceSummary.jsp"></jsp:include></td>
			<input type="hidden" name="hdnSeriveAttCounter" value="">
		</tr>
		<tr align="center" id="BillingNChargeInfo" style="display:none">
			<td colspan="2" width="50%"><jsp:include flush="true"
				page="ChangeBillingNChargeInfo.jsp"></jsp:include></td>
		</tr>
		<tr align="center" id="HardwareDetails" style="display:none">
			<td colspan="2" width="50%"><jsp:include flush="true"
				page="ChangeHardwareDetails.jsp"></jsp:include></td>
		</tr>
		<tr align="center" id="ServiceLocation" style="display:none">
			<td colspan="2" width="50%"><jsp:include flush="true"
				page="ChangeServiceLocation.jsp"></jsp:include></td>
		</tr>

					<tr align="center" id="Components" style="display:none">
						<td colspan="2" width="50%">
							<jsp:include flush="true" page="ChangeComponents.jsp"></jsp:include>
						</td>
					</tr>
	</table>
	</div>
	</fieldset>
</html:form>
</body>
<script type="text/javascript">
	initFieldValidation();
	setServiceSummaryAttributes();
	getTreeForDropDown("0");
	
	//========================================================================================================
	
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
			            	var param=getParentVal(thisEl); 																
	                	$.ajax({
							url: "<%=request.getContextPath()%>/NewOrderAction.do?method=populateServiceAttMasterValue&searchval="+request.term+"&attributefor="+thisEl.attr("retval")+"&sourceType=CHANGE&serviceId="+servicesID+"&SPID=0&accountId="+document.getElementById('txtBillingAC').value+"&param="+param+"&parntAttId="+thisEl.attr("parntAttId"),
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
                                     	if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                     	{
                                     	$(thisEl).val("");	
											$("#bbPrimaryBCPID").val("0");
                                     	}
                                     		//bcp details for services ADDED BY MANISHA START
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                     	{
                                     		$(thisEl).val("");	
											$("#bbPrimaryBCPIDService").val("0");
                                     	}
                                     	//bcp details for services ADDED BY MANISHA end
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                     	{
                                     		$(thisEl).val("");	
											$("#txtdispatchAddress").val("0");
                                     	}
                                     	//Start  [006]
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                     	{
                                     		$(thisEl).val("");	
											$("#ddPrimaryBCP").val("0");
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                     	{
                                     		$(thisEl).val("");	
											$("#ddSecondaryBCP").val("0");
                                     	}
                                     	//End  [006]
                                     	else{
                                     		$(thisEl).val("");	
										$("#prodAttVal_"+thisEl.attr("counterval")).val("0");
										}
                                     }
                                     else
                                     {
                                     	if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                     	{
                                     		$("#bbPrimaryBCPID").val(ui.item.value);
		                                    $(thisEl).val(ui.item.label);
		                                    FetchBillingDetails();
                                     	}
                                     	//bcp details for services ADDED BY MANISHA START
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                     	{
                                     		$("#bbPrimaryBCPIDService").val(ui.item.value);
		                                    $(thisEl).val(ui.item.label);
		                                    FetchBillingDetailsforService();
                                     	}
                                     	//bcp details for services ADDED BY MANISHA end
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                     	{
                                     		$("#txtdispatchAddress").val(ui.item.value);
		                                    $(thisEl).val(ui.item.label);
		                                    getDispatchAddress();
                                     	}
                                     	//Start  [006]
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                     	{
                                     	$("#ddPrimaryBCP").val(ui.item.value);
		                                $(thisEl).val(ui.item.label);		                                 
										FetchPriBCPDetails();
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                     	{
                                     	$("#ddSecondaryBCP").val(ui.item.value);
		                                $(thisEl).val(ui.item.label);		                                 
										FetchSecBCPDetails();
                                     	}
                                     	//End  [006]
                                     	else{
						var l_AllowChangeaction=1;
						//after arbor merge
						if(thisEl.attr("configVal")!=null && thisEl.attr("configVal")!=0 && ui.item.value!=$("#prodAttVal_"+thisEl.attr("counterval")).val())
	                                     	{	l_AllowChangeaction=0;
	                                     	var compLength = 0;
				var chargeLength = 0;
				if(document.getElementById('hdnComponentInfo').value==1){
					compLength = document.getElementsByName('chkComponents').length;
				}
				if(document.getElementById('hdnChargeInfo').value==1){
				    chargeLength=document.getElementsByName('chkSelectChargeDetail').length;
				}
	                                     		if(loadUpdateConfigIfApplicable(thisEl.attr("retval"),ui.item.label,ui.item.value,'PC','', compLength, chargeLength)==true)
							{
								l_AllowChangeaction=1;
							}
						}
						//end: after arbor merge
						if(l_AllowChangeaction==1) 
						{
	                                     	 $("#prodAttVal_"+thisEl.attr("counterval")).val(ui.item.value);
		                                     $(thisEl).val(ui.item.label);
	        	                             //added to fetch dependent level values by lawkush  
	                	                      fnGetDestLabelValueForLOV(thisEl.attr("retval"),ui.item.label);
	                	                      if(thisEl.attr("isParentAtt")==1){
		                                    	resetChildAttr(thisEl);
		                                       }
						}
	                                     }
	                                      
                                     }
                                     
           return false;
       },
	   change: function( event, ui ) 
							{	
							
								var param=getParam(thisEl);									
								if(ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1"|| $("#prodAttVal_"+thisEl.attr("counterval")).val()=="0")
								{
									
	                				var value2,label2;
	                			 var validateData=$.ajax({
													url: "<%=request.getContextPath()%>/NewOrderAction.do?method=populateServiceAttMasterValueValidate&searchval="+thisEl.val()+"&attributefor="+thisEl.attr("retval")+"&sourceType=CHANGE&accountId="+document.getElementById('txtBillingAC').value+"&param="+param+"&parntAttId="+thisEl.attr("parntAttId"),
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
										if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                     	{
										$(thisEl).val("");
											$("#bbPrimaryBCPID").val("0");	
                                     	}
                                     	//bcp details for services ADDED BY MANISHA START
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                     	{
                                     		$(thisEl).val("");	
											$("#bbPrimaryBCPIDService").val("0");
                                     	}
                                     	//bcp details for services ADDED BY MANISHA end
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                     	{
                                     		$(thisEl).val("");	
											$("#txtdispatchAddress").val("0");
                                     	}
                                     	// Start  [006]
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                     	{
                                     		$(thisEl).val("");	
											$("#ddPrimaryBCP").val("0");
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                     	{
                                     		$(thisEl).val("");	
											$("#ddSecondaryBCP").val("0");
                                     	}
                                     	// End  [006]
                                     	else{
											$(thisEl).val("");
										$("#prodAttVal_"+thisEl.attr("counterval")).val("0");		
										}
									}else{
										setASValueOnChange(thisEl, value2, label2);
                                     	
									}				                				    			
																
								}else{	
								
									if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                     	{
                                     	//
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                     	{
                                     	//
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                     	{
                                     	}
                                     	//Start  [006]
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                     	{
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                     	{
                                     	}
                                     	//End  [006]
                                     	else
                                     	{
										 //added to fetch dependent level values by lawkush  								
										 fnGetDestLabelValueForLOV(thisEl.attr("retval"),ui.item.label);
										 	if(thisEl.attr("isParentAtt")==1){
		                                    	resetChildAttr(thisEl);
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
		    callOnBlur();
  }
  
  	function getDropDown(attrid)
	{
		
		$("#autoSuggProdAttVal_"+attrid).autocomplete( "search", "" );
		$("#autoSuggProdAttVal_"+attrid).focus();

	}
	//START[13082012]AutoSuggest for BCP
	function getDropDownBilling()
	{
	
		$("#txtBCPAddressSuggest").autocomplete( "search", "" );
		$("#txtBCPAddressSuggest").focus();
	}
	//END[13082012]AutoSuggest for BCP
	//========================================================================================================
	function getDropDownDispatch()
	{
		
		$("#txtDispatchAddressSuggest").autocomplete( "search", "" );
		$("#txtDispatchAddressSuggest").focus();

	}
	
	
	//Auto suggest Cust Location Start  [006]
	function getDropDownPriCustLocation()
	{
		
		$("#txtPriCustLocationSuggest").autocomplete( "search", "" );
		$("#txtPriCustLocationSuggest").focus();

	}
	
	function getDropDownSecCustLocation()
	{
		
		$("#txtSecCustLocationSuggest").autocomplete( "search", "" );
		$("#txtSecCustLocationSuggest").focus();

	}
	
		//bcp details for services ADDED BY MANISHA START
	function getDropDownBillingforService()
	{
		
		$("#txtBCPAddressSuggestService").autocomplete( "search", "" );
		$("#txtBCPAddressSuggestService").focus();

	}
	//bcp details for services ADDED BY MANISHA end
	
	//Auto suggest Cust Location End [006]
	
	//start Deepak Kumar for Third Party
	function OpenNewWindowSCM(){
    
	if(document.getElementById("serviceproductid").value == '' || document.getElementById("serviceproductid").value == '0')
	{
alert("Please Fill Product Catalog");
    }
else
   {
  
 	var ServiceProductID=document.getElementById("serviceproductid").value;
	var path1 = "<%=request.getContextPath()%>/NewOrderAction.do?method=getTProductLineAttmasterSCMForUpdate";
	path1=path1+"&ServiceProductID="+ServiceProductID+"&ServiceID="+servicesID+"&POnum="+poNumber+"&serviceDetailID="+document.getElementById('serviceDetailID').value+"&serviceNo="+serviceID_Product+"&service_Name="+serviceNameForScm+"&roleId="+roleName+"&ProductName="+productName1+"&hdnnPrServiceSummary="+1+"&hdnnPrLineInfo="+1+"&accountID="+document.getElementById('accNo').value+"&gb_sessionid="+sessionid+"&isView="+isView;
 	window.showModalDialog(path1,window,"status:false;dialogWidth:1200px;dialogHeight:800px");


   }
}

	function scmButtonEnable(){
			var myLocalForm = document.getElementById('productCatelog');
			var accountNum=document.getElementById('txtBillingAC').value;
			document.getElementById('accNo').value=accountNum;
			var dbList;
			var jsDataSerDetId=
			{
				serviceDetailID:document.getElementById('serviceDetailID').value
			};
			dbList=jsonrpc.processes.getServiceDetailId(jsDataSerDetId);
		
			
		 if(document.getElementById('serviceDetailID').value==dbList.serDetIdAskeyValue) 
	{ 
	 
			 productName1=dbList.productName;
	 var tblRow=document.getElementById('SCMBtnID');
		 	 				
			 	var tblcol=tblRow.insertCell();
			 	var str1 ="<input type='button'   onclick='OpenNewWindowSCM()' value='PR Attributes Details' id='scmBtn'/>";
				CellContents = str1;
				tblcol.innerHTML = CellContents;
	  
	           
			
			
	 
	}
	else{
	if(document.getElementById('scmBtn')!=null){
		document.getElementById('scmBtn').style.display='none';
	}

	}
		
	}
	function setScmButtonValue(disabledValue){
		document.getElementById("scmBtn").disabled=disabledValue;
		}
		
		// added by manisha o2c start
	function setASValueOnSelect(thisEl, value, label){
		if(thisEl.attr("retval")=="AUTOSUGGESTBCP"){
                       		$("#bbPrimaryBCPID").val(value);
		                    $(thisEl).val(label);
		                    FetchBillingDetails();
                        }else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE"){//bcp details for services ADDED BY MANISHA START
                           $("#bbPrimaryBCPIDService").val(value);
		                   $(thisEl).val(label);
		                   FetchBillingDetailsforService();
           				}else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH"){//bcp details for services ADDED BY MANISHA end
                           $("#txtdispatchAddress").val(value);
		                   $(thisEl).val(label);
		                   getDispatchAddress();
                        }else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC"){//Start[006]
                        	$("#ddPrimaryBCP").val(value);
		                    $(thisEl).val(label);		                                 
							FetchPriBCPDetails();
                        }else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC"){
                           	$("#ddSecondaryBCP").val(value);
		                    $(thisEl).val(label);		                                 
							FetchSecBCPDetails();
                        }else{//End[006]
							var l_AllowChangeaction=1;
							//after arbor merge
							if(thisEl.attr("configVal")!=null && thisEl.attr("configVal")!=0 
								&& value!=$("#prodAttVal_"+thisEl.attr("counterval")).val()){	
									l_AllowChangeaction=0;
	                                var compLength = 0;
									var chargeLength = 0;
								if(document.getElementById('hdnComponentInfo').value==1){
									compLength = document.getElementsByName('chkComponents').length;
								}
								if(document.getElementById('hdnChargeInfo').value==1){
			    					chargeLength=document.getElementsByName('chkSelectChargeDetail').length;
								}
								if(loadUpdateConfigIfApplicable(thisEl.attr("retval"),label,value,'PC','', compLength, chargeLength)==true){
									l_AllowChangeaction=1;
								}
							}//end: after arbor merge
						
							if(l_AllowChangeaction==1){
		                    	$("#prodAttVal_"+thisEl.attr("counterval")).val(value);
			                    $(thisEl).val(label);
		    	                //added to fetch dependent level values by lawkush  
		        	            fnGetDestLabelValueForLOV(thisEl.attr("retval"),label);
		        	             //Start [014]--to reset child LOV
		        	             if(thisEl.attr("isParentAtt")==1){
                                  	resetChildAttr(thisEl);
                                 }
                                 //END [014]
							}
	        		}
	}
	
	function setASValueOnChange(thisEl, value, label){
	if(thisEl.attr("retval")=="AUTOSUGGESTBCP"){
                        $(thisEl).val(label2);
						$("#bbPrimaryBCPID").val(value2);
						FetchBillingDetails();
                    }else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE"){
                        $(thisEl).val(label2);
						$("#bbPrimaryBCPIDService").val(value2);
						FetchBillingDetailsforService();
                    }else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH"){
                        $(thisEl).val(label2);
						$("#txtdispatchAddress").val(value2);
						getDispatchAddress();
				    }else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC"){
                        $(thisEl).val(label2);
						$("#ddPrimaryBCP").val(value2);
						FetchPriBCPDetails();
                     }else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC"){
                       $(thisEl).val(label2);
					   $("#ddSecondaryBCP").val(value2);
					   FetchSecBCPDetails();
                    }else{
					 $(thisEl).val(label2);
					 $("#prodAttVal_"+thisEl.attr("counterval")).val(value2);
					 //Start [014]--to reset child LOV
					 if(thisEl.attr("isParentAtt")==1){
                     	resetChildAttr(thisEl);
                      }
                      //END [014]
					}
			}// added by manisha o2c end

//end Deepak kumar
</script>

</html>
