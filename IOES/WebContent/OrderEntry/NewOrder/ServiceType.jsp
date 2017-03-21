<!--Tag Name  Resource Name   Date		CSR No			       Description 
	[001]    Gunjan Singla    4-Mar-15  20150225-R1-021111    LSI and Service No Search required in Lines Tab
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.ArrayList;"%>

       
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
            height: 221px;
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
            width: 3000px;
            height: 1px;
        }
        
         div.horizontal-scrollService
        {
            width: 100%;
            height: 22px;
            overflow: hidden;
            overflow-x: scroll;
            border: solid 1px #666;
        }
        div.horizontal-scrollService div
        {
            width: 1600px;
            height: 1px;
        }
        
        div.vertical-scroll
        {
            height: 227px;
            width: 100%;
            overflow: hidden;
            overflow-y: scroll;
            border: solid 1px #666;
        }
        
        div.vertical-scroll div
        {
            height:1700px;
            width: 1px;
        }
        
          div.vertical-scrollService
        {
            height: 290px;
            width: 100%;
            overflow: hidden;
            overflow-y: scroll;
            border: solid 1px #666;
        }
        
        div.vertical-scrollService div
        {
            height:1700px;
            width: 1px;
        }
        
        
        td.inner
        {
            border-left: 1px solid #666;
            border-bottom: 1px solid #666;
            padding: 0px;
            height: 30px;
        }
        td.innerHead
        {
            border-left: 1px solid #666;
            border-bottom: 1px solid #666;
            padding: 0px;
            height: 90px;
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
        
          .colSrNo
          {
            width: 40px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           .colRadio
          {
            width: 52px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           .colCheckBox
          {
            width: 48px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           .colDummy
          {
            width: 59px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
         .colPublished
          {
            width: 59px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
          .colServiceNo
          {
            width: 90px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           
           .colServiceType
          {
            width: 150px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
            .colLogicalSI
          {
            width: 90px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
          .colAttribute
          {
            width: 70px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
           }
           
           .colServiceStatus
          {
            width: 120px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }

	  .colIsDemo
          {
            width: 60px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
           }
	   	
            .colInitiatedTo
          {
            width: 90px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           
             .colCancellationreson
          {
            width: 120px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           
             .colCancelledBy
          {
            width: 120px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           
             .colCancelremarks
          {
            width: 120px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           
             .calcancelDate
          {
            width: 120px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
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
            height: 200px;
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
                

<script type="text/javascript">


		

function AddServiceLine1()
{
	if(gb_modeName=="viewMode")
	{
		return false
	}
	else
	{
		popService(0);
	}
	//PAGING-SERVICE-LINE-14-10-2012
	//AddServiceLine();
	
}
//PAGING-SERVICE-LINE-14-10-2012
function refreshTree(){
	document.getElementById('browser').innerHTML= "";
}

function reposHead(e) {
            var h = document.getElementById('headscroll');
            h.scrollLeft = e.scrollLeft;
            var f = document.getElementById('divfrozen');
            f.scrollTop = e.scrollTop;
        }
        function reposHeadService(e) {
            var h = document.getElementById('headscrollService');
            h.scrollLeft = e.scrollLeft;
            var f = document.getElementById('divfrozenService');
            f.scrollTop = e.scrollTop;
        }
        function reposHorizontal(e) {
            var h = document.getElementById('headscroll');
            var c = document.getElementById('contentscroll');
            h.scrollLeft = e.scrollLeft;
            c.scrollLeft = e.scrollLeft;
            
        }
        function reposHorizontalService(e) {
            var h = document.getElementById('headscrollService');
            var c = document.getElementById('contentscrollService');
            h.scrollLeft = e.scrollLeft;
            c.scrollLeft = e.scrollLeft;
            
        }
        
        function reposVertical(e) {
            var h = document.getElementById('divfrozen');
            var c = document.getElementById('contentscroll');
            h.scrollTop = e.scrollTop;
            c.scrollTop = e.scrollTop;
           
        }
        function reposVerticalService(e) {
            var h = document.getElementById('divfrozenService');
            var c = document.getElementById('contentscrollService');
            h.scrollTop = e.scrollTop;
            c.scrollTop = e.scrollTop;
           
        }
        //[001] start
        function onKeyPressServiceSearch(key_event)
        {
            if (key_event.keyCode==13) 
            {
            	validateAndProcessServiceSearch();
            }      
        }
        
        function validateAndProcessServiceSearch(){
        	
        	
        	var logicalSiNumber=document.getElementById('txtLsiNo').value;
        	var serviceNo=document.getElementById('txtServiceNo').value;
        	var searchFlag=1;
        	if( trim(logicalSiNumber).length>0)
        	{ 		
        		if(checkdigitsServiceSearch(document.getElementById('txtLsiNo'),'Logical SI No'))
        			{
        			searchFlag=1;
        			}
        			else
        			{
        			 searchFlag=0;
        			}	  		  		  	  		  	 	 
        	}	
        	if( trim(serviceNo).length>0)
        	{ 		
        		if(checkdigitsServiceSearch(document.getElementById('txtServiceNo'),'Service No'))
        			{
        			searchFlag=1;
        			}
        			else
        			{
        			 searchFlag=0;
        			}	  		  		  	  		  	 	 
        	}
        	 if(searchFlag==1){
        		searchLineTab('SERVICENO','SERVICELINETABLE');
        	} 
        }
      //[001] end
       
</script>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%" valign="top">
			<fieldset class="border1" >
				<legend id="lblServicetype"> <b>Service Type</b> </legend>
				<!----------------------------------------- [ PAGING-SERVICE-LINE-14-10-2012 ]------------------------------------------------------------------- -->
				<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" id="serviceLineTabNavigatorId">	
				<tr>
					<td align="center"><a href="#" id= "first" onclick="refreshTree();FirstPage('SERVICENO','SERVICELINETABLE')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="refreshTree();NextPage('SERVICENO','SERVICELINETABLE')">Next</a></td>
					<td align="center">
						<input type="text" id="id_paging_goToPage" class="inputBorder2" size="2" maxlength="4" >
								<a href="#" onclick="refreshTree();goToPageSubmit('SERVICENO','SERVICELINETABLE')">GO </a>
						<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
						<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
					</td>
					<td align="center"><a href="#" id="prev" onclick="refreshTree();PrevPage('SERVICENO','SERVICELINETABLE')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="refreshTree();LastPage('SERVICENO','SERVICELINETABLE')">Last</a></td>
				</tr>
				</table>
				<!----------------------------------------- [ PAGING-SERVICE-LINE-14-10-2012 ]------------------------------------------------------------------- -->
				
				<!----------------------------------------- [ service-type-06-11-2012 ]------------------------------------------------------------------- -->
				
				<table border="1" cellpadding="0" cellspacing="0" class='main' id="tabParentService" >        
            <tr>
                <td class='tablefrozencolumn'>
                    <div id='divrootService' class='root'>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='root'>
                            <tr>
                                <td class='inner frozencol colwidth head1'>
                                    
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div id='divfrozenService' class='frozen'>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='frozen'>
                            <tr>
                                <td class='inner frozencol toprow'>
                                    
                                </td>
                            </tr>                            
                        </table>
                    </div>
                </td>
                <td class='tablecontent' valign="top">
                    <div id='headscrollService' class='divhead' style="vertical-align:top">                               	
                        <table border="0" cellpadding="0" cellspacing="0" class='head1' height="5%">
                              <tr>
                                <td class='innerHead colSrNo head1'>
									Sr. No.
                                </td>
                                <td class='innerHead colRadio head1'>
                                    Select To Add LineItem
                                </td>
                                <td class='innerHead colCheckBox head1' >
                                    Select To Delete Service<input type="checkbox" id="selectAllID" name="selectAllID"  onclick="selectAll()" disabled="disabled">
                                </td>
                                <td class='innerHead colPublished head1'>
                                    Published
                                </td>
                                <td class='innerHead colDummy head1'>
                                	Dummy
                                </td>
                                <!-- [001] start -->
                                <td class='innerHead colServiceNo head1'>
                                    Service No<input type="text" id="txtServiceNo" onKeyPress="onKeyPressServiceSearch(event)">
                                </td>
                                <!-- [001] end -->
                                <td class='innerHead colServiceType head1'>
                                    Service Type
                                </td>
                                <!-- [001] start -->
                                <td class='innerHead colLogicalSI head1'>
                                    Logical SI No<input type="text" id="txtLsiNo" onKeyPress="onKeyPressServiceSearch(event)">
                                </td>
                                <!-- [001] end -->
                                <td class='innerHead colLogicalSI head1'>
                                    Customer Logical SI No
                                </td>
                                <td class='innerHead colAttribute head1' id="ServiceAttHeader" style="display:block">
                                    Attributes
                                </td>
                                <td class='innerHead colAttribute head1'>
                                    Attribute Label Value
                                </td>
								<td class='innerHead colServiceStatus head1'>
                                    Service Status
                                </td>
				 <td class='innerHead colIsDemo head1'>
                                    Is Demo
                                </td>
				
                                <!-- added by manisha o2c start -->
                                <td class='innerHead colInitiatedTo head1'>
                                    Bin
                                </td>
                                  <!-- added by manisha o2c end -->


                                 <!-- added by Sadananda IB2B start -->
                                <td class='innerHead calcancelDate head1'>
                                 Service Cancellation Date
                                </td>
                                <td class='innerHead colCancellationreson head1'>
                                 Service Cancellation Reason 
                                </td>
                                <td class='innerHead colCancelledBy head1'>
                                  Service Cancelled By
                                </td>
                                <td class='innerHead colCancelremarks head1'>
                                  Service Cancel Remarks
                                </td>
                                  <!-- added by Sadananda IB2B end -->


                            </tr>  
							<input type="hidden" name="hdnServiceCount" value="1">							
                        </table>
                    </div>
                    <div id='contentscrollService' class='content1' onscroll='reposHeadService(this);'>
                        <table border="0" cellpadding="0" cellspacing="0" class='content1' id='ServiceList'>                        	
                                                      
                                                                            
                        </table>
                    </div>
                </td>
                <td class='tableverticalscroll' rowspan="2">
                    <div class='vertical-scrollService' onscroll='reposVerticalService(this);'>
                        <div>
                        </div>
                    </div>
                    <div class='ff-fill'>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <div class='horizontal-scrollService' onscroll='reposHorizontalService(this);'>
                        <div>
                        </div>
                    </div>
                </td>
            </tr>
          <tr>
						<td colspan="12" align="left" width="505">
						<input type="button" title="Add More Service" id="btnAddMoreService" name="btnAddContact" value="Add More Service" onClick="AddServiceLine1()">
						</td>
					</tr>
        </table>
				
				<!----------------------------------------- [ service-type-06-11-2012 ]------------------------------------------------------------------- -->
				
				
				
				
				
				
							
			</fieldset>
		</td>
	</tr>
	<tr>
		<td>
		<fieldset class="border1" >
		<table width="100%"  border="1" cellspacing="0" cellpadding="0">
			<tr>
				<td><b>LineItem Name</b></td>
				<td><input id="txtLineItemName" readonly="readonly" /></td>
				<td><b>LineItem No</b></td>
				<td><input id="txtLineItemNo" readonly="readonly" /></td>
			</tr>
		</table>
		</fieldset>
		</td>
	</tr>
</table>

