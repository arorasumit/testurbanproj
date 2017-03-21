/*TAG NAME			RESOURCE		DATE		DESCRIPTION
PagingSorting.js	ANIL KUMAR		22-03-2011	This file is created for paging and sorting with javascript and Ajax[json]
PagingSorting.js	LAWKUSH		06-05-2011	modification according different paging sorting 
[001]				Rohit Verma	 18-feb-13	Modification in DrawTable fuction for Sorting in LSIMapping Interface	
[002]				Rohit Verma	 1-Mar-13	Modification in DrawTable fuction for Sorting in Cancel Hardware Line Item Interface	*/
//-----------------------------Global variables are declared-------------------
var iNoOfPages = 0;
var iFirstRow = 0; 
var iLastRow = 0;
var iTotalLength = 0;

var pageRecords = 10;
var startRecordId=1;
var endRecordId=parseInt(pageRecords)-1;
var recordCount=0;
var maxPageNumber=1;
var sortByColumn='CRMACCOUNTNO';
var sortByOrder='ASC';
var pageNumber=1;
var var_alphabet=null;
//------------------------------------------------------------------------------
/*function	:sync()
	Purpose	:To create start index and end index and send these values to the database
*/
function sync()
{				

	startRecordId = 1+parseInt((parseInt(pageNumber)-1)*parseInt(pageRecords));		
		
	endRecordId = parseInt(startRecordId)+parseInt(pageRecords)-1;		
	
}
//end :	sync()


/*function NextPageLsiCancellation(var_sortByColumn,var_pageName)
{	
    //validateAndMaintainListOnSelectionUtility()
    if ( validateAndMaintainListOnSelectionUtility()!=false ) {
    	sortByColumn=var_sortByColumn;
    	if(pageNumber<iNoOfPages)
    	{
    		pageNumber++;		
    	}
    	DrawTable(sortByColumn,var_pageName);
    	showCheckedServices()
	}
	
}
//end:NextPage()

function	:LastPage()
purpose		:to navigate last page

function LastPageLsiCancellation(var_sortByColumn,var_pageName)
{	
   // validateAndMaintainListOnSelectionUtility()
    if ( validateAndMaintainListOnSelectionUtility()!=false ) {
		sortByColumn=var_sortByColumn;
		pageNumber=iNoOfPages;	
		DrawTable(sortByColumn,var_pageName);
		showCheckedServices()
    }
}
//end:LastPage()

function	:FirstPage()
Purpose		:To navigate first page

function FirstPageLsiCancellation(var_sortByColumn,var_pageName)
{		
	    //validateAndMaintainListOnSelectionUtility()
	    if ( validateAndMaintainListOnSelectionUtility()!=false ) {
			sortByColumn=var_sortByColumn;
			pageNumber=1;
			DrawTable(sortByColumn,var_pageName);
			showCheckedServices()
	     }
}
//end:FirstPage()

function	:PrevPage()
Purpose		:To navigate previous page

function PrevPageLsiCancellation(var_sortByColumn,var_pageName)
{	
	//validateAndMaintainListOnSelectionUtility()
    if ( validateAndMaintainListOnSelectionUtility()!=false ) {
		sortByColumn=var_sortByColumn;
		if(pageNumber>1)
		{
			pageNumber=pageNumber-1;
		}
		DrawTable(sortByColumn,var_pageName);
		showCheckedServices()
    }
}
*///end:PrevPage()

/*function 	:NextPage()
Purpose		:To navigate next page
*/
function NextPage(var_sortByColumn,var_pageName)
{	
	sortByColumn=var_sortByColumn;
	if(pageNumber<iNoOfPages)
	{
		pageNumber++;		
	}
	DrawTable(sortByColumn,var_pageName);
}
//end:NextPage()

/*function	:LastPage()
purpose		:to navigate last page
*/
function LastPage(var_sortByColumn,var_pageName)
{	
	sortByColumn=var_sortByColumn;
	pageNumber=iNoOfPages;	
	DrawTable(sortByColumn,var_pageName);
}
//end:LastPage()

/*function	:FirstPage()
Purpose		:To navigate first page
*/
function FirstPage(var_sortByColumn,var_pageName)
{		
		sortByColumn=var_sortByColumn;
		pageNumber=1;
		DrawTable(sortByColumn,var_pageName);
}
//end:FirstPage()

/*function	:PrevPage()
Purpose		:To navigate previous page
*/
function PrevPage(var_sortByColumn,var_pageName)
{	
	sortByColumn=var_sortByColumn;
	if(pageNumber>1)
	{
		pageNumber=pageNumber-1;
	}
	DrawTable(sortByColumn,var_pageName);
}
//end:PrevPage()

/*function	:sortOrder()
Purpose		:To sorting in order ascending or descending with column(table fields)
*/
function sortOrder(var_sortByColumn,var_pageName)
{	
	sortByColumn=var_sortByColumn;
	if(sortByOrder=='ASC')
	{
		sortByOrder='DESC';
	}
	else
	{
		sortByOrder='ASC';
	}
	DrawTable(sortByColumn,var_pageName);
}
//end:sortOrder(sortBy)

/*function 	:searchAccountList()
Purpose		:To search particular records.
*/
function searchFromList(var_sortByColumn,var_pageName)
{	
	    var_alphabet=null;
		sortByColumn=var_sortByColumn;
		pageNumber=1;
		DrawTable(sortByColumn,var_pageName);
}
//end	:searchFromList()

/*function DrawTable()
Purpose	:This function is created for draw table where populated the list
*/
function DrawTable(var_orderByCol,var_pageName)
{
	sortByColumn=var_orderByCol;
	if(var_pageName.toUpperCase()=='SELECTACCOUNT')
	{
		DrawAccountListTable();
	}
	
	if(var_pageName.toUpperCase()=='SELECTACCOUNT_FORSITRANSFER')
	{
		DrawAccountTable();
	}
	
	
	if(var_pageName.toUpperCase()=='SELECTPARTY')
	{
		DrawPartyTable();
	}
	if(var_pageName.toUpperCase()=='SELECTLOGICALSINO_FORCURRENCYCHANGE')
	{
				
		DrawLogicalType_CurrencyChange();
	}
	if(var_pageName.toUpperCase()=='SELECTOPPORTUNITY')
	{		
		DrawOpportunityType();
	}		
	if(var_pageName.toUpperCase()=='SELECTCURRENCY')
	{		
		DrawCurrencyType();
	}
	
	//pankaj start
	if(var_pageName.toUpperCase()=='SELECTCHANNELPARTNER')
	{		
	//	alert('DrawTable() channel partner in pagingsorting.js');
		DrawChannelPatnerType();
	}
	//pankaj end
	
	if(var_pageName.toUpperCase()=='SELECTCOUNTRY')
	{
		DrawCountryList();
	}
	if(var_pageName.toUpperCase()=='SELECTSTATE')
	{		
		DrawStateList();
	}
	if(var_pageName.toUpperCase()=='SELECTCITY')
	{		
		DrawCityList();
	}
	if(var_pageName.toUpperCase()=='SELECTSERVICETYPE')
	{		
		DrawServiceTypeTable();
	}
	if(var_pageName.toUpperCase()=='SELECTLOGICAL_SI_NO')
	{		
		DrawLogicalType();
	}
	if(var_pageName.toUpperCase()=='SELECTPOPLOCATION')
	{	
		DrawPopLocType();
	}
	if(var_pageName.toUpperCase()=='VIEWBCPDETAILS')
	{	
		DrawBCPListTable();
	}
	if(var_pageName.toUpperCase()=='QUERYFORMLIST')
	{	
		DrawQueryFormTable();
	}
	//added by Ashutosh As On Date: 3-Sep-2011
	if(var_pageName.toUpperCase()=='SELECTLOGICALSINO')
	{
		DrawCustLogicalSITable();
	}
		if(var_pageName.toUpperCase()=='SELECTCOMPONENT')
	{		
		DrawComponentList();
	}
	
	if(var_pageName.toUpperCase()=='SELECTPACKAGE')
	{		
		DrawPackageList();
	}
	
	if(var_pageName.toUpperCase()=='SELECTLOGICALSI_FORSITRANSFER')
	{		
		DrawLogicalType();
	}
	
	if(var_pageName.toUpperCase()=='SELECTBCP_FORSITRANSFER')
	{		
		DrawBCPDetailTable();
	}
	if(var_pageName.toUpperCase()=='SELECTDISPATCH_FORSITRANSFER')
	{		
		DrawDispatchTable();
	}
	if(var_pageName.toUpperCase()=='SELECTACCOUNTFORBULKUPLOAD')
	{		
		DrawAccountBULKListTable();
	}
	if(var_pageName.toUpperCase()=='SELECTLSIFORBULKUPLOAD')
	{		
		DrawLSIBULKListTable();
	}
	if(var_pageName.toUpperCase()=='SELECTGAM')
	{		
		DrawAllGAMList('0');
	}
	if(var_pageName =='BillingTriggerForCharges')
	{		
		fnGetChargeDetails();
	}
	if(var_pageName =='LSICANCELLATION')
	{		
		getAndShowTableData();
	}
	if(var_pageName.toUpperCase()=='SELECTAPPROVAL')
	{		
		DrawApprovalList();
	}
	
	if(var_pageName.toUpperCase()=='SELECTCOPYORDER')
	{		
		populateService();
	}
	if(var_pageName.toUpperCase()=='SERVICELINETABLE'){					
		drawServiceLineTable();
	}
	
	//[001] Start
	if(var_pageName.toUpperCase()=='LSIMAPPING'){					
		DrawLSIMapping();
	}
	//[001] End
	if(var_pageName.toUpperCase()=='SELECTARBORLSI')
	{		
		DrawArborLSIListTable();
	}
	if(var_pageName.toUpperCase()=='VIEWCHARGETABLE'){						
		drawViewChargeTable();
	}	
	if(var_pageName.toUpperCase()=='CHANGEVIEWCHARGETABLE'){						
		drawChangeViewChargeTable();
	}		
	//[002] Start
	if(var_pageName.toUpperCase()=='CANCEL_HARDWARE_LINEITEM'){					
		DrawHardwareDetailsTable();
	}
	//[002] End
	if(var_pageName.toUpperCase()=='SELECTCONTACT'){					
		DrawContactListTable();
	}
	//vijay start 
	if(var_pageName.toUpperCase()=='SELECTPARTIALPUBLISH'){					
		DrawPartialPublishTable();
	}
	//Start Deepak for PrSearch dialog
	if(var_pageName.toUpperCase()=='SERVICEID')
		{		
			DrawPrSearchTable();
		}
	//end Deepak
	//vijay end
	if(var_pageName.toUpperCase()=='SELECTSERVICES'){
		drawApproveServicesTable();
	}
	if(var_pageName.toUpperCase()=='STANDARDREASON'){
		DrawStandardReasonListTable();
	}
	if(var_pageName.toUpperCase()=='VIEWDISPATCHADDRESS'){
		DrawDispatchListTable();
	}
	if(var_pageName.toUpperCase()=='CHANNELPARTNER'){
		DrawChannelPartnerListTable();
	}
	
	//Shubhranshu,DnC
	if(var_pageName.toUpperCase()=='SELECTHEADENDLSI')
		{
			DrawHeadEndLsiListTable();	
		}
}
function searchLocation(var_alphabet1)
     {	
     	var_alphabet=var_alphabet1;
		pageNumber=1;
		var_pageName='SELECTPOPLOCATION';
		sortBycolumn='LOCATION_NAME';
		DrawPopLocType();
     }
/*function 	:goToPageSubmit()
Purpose		:To search records of particular page.
*/
function goToPageSubmit(var_sortByColumn,var_pageName)
{
	var maxPages=document.getElementById('txtMaxPageNo').value;
	var temp=document.getElementById('id_paging_goToPage');
	
	if(temp.value!=null && temp.value!="")
	{
		var str=new String(temp.value);
	
		var reg =/^([0-9]+)$/;
	    if(reg.test(temp.value) == false) {
	   		alert("Only Numeric Digits are Allowed in Go To Page Field");
	   		temp.focus();
	    	return false;
	   	}
	   	if(Number(temp.value)==0)
	   	{
	   		alert("Go To Page Number Cannot Be Less Than 1.");
	   		temp.focus();
	   		return false;
	   	}
	   	if(Number(temp.value)>Number(maxPages))
	   	{
	   		alert("Go To Page Number Cannot Be Greater Than Total No of Pages.");
	   		temp.focus();
	   		return false;
	   	}
	   	pageNumber=temp.value;//seting required page no
	   	sortByColumn=var_sortByColumn;
	   	DrawTable(sortByColumn,var_pageName);
	}
	else
	{
		alert("Please Enter Page Number");
		temp.focus();
		return false;
	}
}     
