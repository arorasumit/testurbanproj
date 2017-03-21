<!--[001]	 Vishwa			04-Nov-11	00-05422		Add More Service id  Style is set to none to display from viewing  -->
<!--[002]    Gunjan Singla    4-Mar-15  20150225-R1-021111    LSI and Service No Search required in Lines Tab -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.ArrayList;"%>
<link href="./gifs/freezeStyle_chargeInfo1.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
var freezeRow=1; //change to row to freeze at
var freezeCol=3; //change to column to freeze at
var myRow=freezeRow;
var myCol=freezeCol;
var speed=100; //timeout speed
var myTable;
var noRows;
var myCells,ID,myCells1;
function setUp(){

	if(!myTable)
	{	
		myTable=document.getElementById("ServiceList");
	}
	if(!myTable.rows[0])
		return false;
 	myCells = myTable.rows[0].cells.length;
 	//alert('setup myCells = '+myCells);
 	//if(myTable.rows[2].cells.length != null)
 	//myCells2 = myTable.rows[2].cells.length;
	noRows=myTable.rows.length;
	//alert('setup noRows = '+noRows);
	//alert('myCells - '+myCells);
	//alert('myCells1 - '+myCells2);
	//alert('noRows - '+noRows);

	for( var x = 0; x < myTable.rows[0].cells.length; x++ ) {
		colWdth=myTable.rows[0].cells[x].offsetWidth;
		myTable.rows[0].cells[x].setAttribute("width",colWdth-4);

	}
}
function right(up){
	if(up){window.clearTimeout(ID);return;}
	if(!myTable){setUp();}
	//alert('myCol = '+myCol);
	//alert('myCells = '+myCells);
	//alert('noRows = '+noRows);
	if(myCol<(myCells)){
		for( var x = 0; x < noRows; x++ ) {
			myTable.rows[x].cells[myCol].style.display="";
		}
		if(myCol >freezeCol){myCol--;}
		ID = window.setTimeout('right()',speed);
	}
}
function right1(up){
	if(up){window.clearTimeout(ID);return;}
	if(!myTable){setUp();}

	if(myCol<(myCells)){
		for( var x = 0; x < noRows; x++ ) {
			myTable.rows[x].cells[myCol].style.display="";
		}
		if(myCol >freezeCol){myCol--;}
		ID = window.setTimeout('right1()',speed);
	}
}
function left(up){
	if(up){window.clearTimeout(ID);return;}
	if(!myTable){setUp();}

	if(myCol<(myCells-1)){
		for( var x = 0; x < noRows; x++ ) {
			myTable.rows[x].cells[myCol].style.display="none";
		}
		myCol++
		ID = window.setTimeout('left()',speed);

	}
}

function down(up){
	if(up){window.clearTimeout(ID);return;}
	setUp();

	if(myRow<(noRows-1)){
			if(myTable.rows[myRow]!=null)
			myTable.rows[myRow].style.display="none";
			myRow++	;

			ID = window.setTimeout('down()',speed);
	}
}

function upp(up){
	if(up){window.clearTimeout(ID);return;}
	setUp();
	if(myRow<noRows){
		//alert('noRows_upp -'+noRows)
		if(myTable.rows[myRow]!=null)
		myTable.rows[myRow].style.display="";
		if(myRow >freezeRow){myRow--;}
		ID = window.setTimeout('upp()',speed);
	}
}

function handle(delta) {
        if (delta <0)
        {
        		down();
                down(1);
        		
        }        
        else
        {
        		upp();
                upp(1);
        }        
}
 
function wheel(event){
        var delta = 0;
        if (!event) event = window.event;
        if (event.wheelDelta) {
                delta = event.wheelDelta/120; 
                if (window.opera) delta = -delta;
        } else if (event.detail) {
                delta = -event.detail/3;
        }
        if (delta)
                handle(delta);
}
 
/* Initialization code. */
if (window.addEventListener)
        window.addEventListener('DOMMouseScroll', wheel, false);
window.onmousewheel = document.onmousewheel = wheel;
function AddServiceLine1()
{
	right1();
	window.setTimeout('right1(1)',3000);
	AddServiceLine();
}

function refreshTree(){
	document.getElementById('browser').innerHTML= "";
}
//[002] start
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
//[002] end
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
                        <table border="1" cellpadding="0" cellspacing="0" class='head1' height="5%">
                              <tr>
                              
                                <td class='innerHead colRadio head1'>
                                    Select To Add LineItem
                                </td>
                                <td class='innerHead colDummy head1'>
                                	Dummy
                                </td>
                                <!-- [002] start -->
                                <td class='innerHead colServiceNo head1'>
                                    Service No  <input type="text" id="txtServiceNo" onKeyPress="onKeyPressServiceSearch(event)">
                                </td>
                                <!-- [002] end -->
                                <td class='innerHead colServiceType head1'>
                                    Service Type
                                </td>
                                <!-- [002] start -->
                                <td class='innerHead colLogicalSI head1'>
                                    Logical SI No <input type="text" id="txtLsiNo" onKeyPress="onKeyPressServiceSearch(event)">
                                </td>
                               
                                <!-- [002] end -->
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
						<input type="button" title="Add More Service" id="btnAddMoreService" name="btnAddContact" value="Add More Service" onClick="AddServiceLine1()" disabled="true">
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

