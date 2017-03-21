<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<title>SCM Line Details</title>
<script>
var sendToScm=1;

function getFinancialYear(var_counter)
{
var new_opt_one = document.createElement("option");
var new_opt_two = document.createElement("option");
new_opt_one.value = currentYearFormat();
new_opt_one.innerHTML =currentYearFormat();
new_opt_two.value = previousYearFormat();
new_opt_two.innerHTML =previousYearFormat();
document.getElementById("financeYear"+var_counter).appendChild(new_opt_two);
document.getElementById("financeYear"+var_counter).appendChild(new_opt_one);
}
function previousYearFormat()
{
var now = new Date();
var thisYear = now.getFullYear();
var disYr=thisYear-2000;
var lastYear = parseInt(now.getFullYear()) - 1;
var lstYr=lastYear-2000;
return lstYr+"-"+disYr;
}
function currentYearFormat()
{
var now = new Date();
var thisYear = now.getFullYear();
var disYr=thisYear-2000;
var nextYear = parseInt(now.getFullYear())+1;
var nxtYr=nextYear-2000;
return disYr+"-"+nxtYr;
}
function enableBudgetHead(var_counter)
{
document.getElementById('txtBudget'+var_counter).disabled = false;
} 
function getBudgetHeadAop2(var_counter1,aop_Id){
		 var aop1_Id=aop_Id;
		
		try
		{
		getBudget=new Array();
		getAop2Id=new Array();
	  
		BudgetHeadComb=jsonrpc.processes.populateBudgetHeadAop2(aop1_Id);
		document.getElementById("txtBudget"+var_counter1).options.length = 0;
		for(var z=0;z<BudgetHeadComb.list.length;z++)
		{
		 getBudget[z]=BudgetHeadComb.list[z].budgetHead2;
		 getAop2Id[z]=BudgetHeadComb.list[z].aop2_Id;
		}
			var local_typeCombo = document.getElementById("txtBudget"+var_counter1);
	        var cvalue=1;
	        for(i=0;i< BudgetHeadComb.list.length;i++)
	        {
	        	var option = document.createElement("option");
	    		option.text = getBudget[i];
		    	option.value=getAop2Id[i];
		    	option.title = getBudget[i];
				try 
				{
					local_typeCombo.add(option, null); //Standard
				}	 
			    catch(error) 
			    {
					local_typeCombo.add(option); // IE only
			    }
	         }
         }
         catch(e)
		 	{
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

function AddCharge(){

	var idCounter = parseInt(document.getElementById('hdnChargeCount').value)+1
	
	var counter = parseInt(document.getElementById('hdnChargeCount').value)+1;
	document.getElementById('hdnChargeCount').value = counter;
	var tblRow=document.getElementById('tableCharges').insertRow();	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col1 toprow";
	str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail' type='checkbox' value='"+idCounter+"'  onclick='allChargesCheckNone()'  >";
	str=str+"<input type='hidden' name='hdnChargeId' id='hdnChargeId"+idCounter+"'  value=''>";
	str=str+"<input type='hidden' name='hdnDeleteChargeId' id='hdnDeleteChargeId"+idCounter+"'  value=''>";
	CellContents = str;
	tblcol.innerHTML = CellContents;


//code for itemCode column
    var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col2";
	//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtItemCode' id='txtItemCode"+idCounter+"' isRequired='0' Disp=' class='dropdownMargin'><option value='-1'>Select Name</option></select>";
	str="<input type='text' value='' onmouseover='getTip(value)'  countervalItem='"+ idCounter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='itemName' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTITEMCODE' id='itemName" + idCounter + "'><a name='chargeNameLink' id='chargeNameLink" + idCounter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownItemCode(" + idCounter + ")'>Show</a>";
	str=str+"<input type='hidden' name='txtItemCode' id='txtItemCode"+idCounter+"' value='-1' countervalItem='"+ idCounter +"'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	 


//Code for charge vaule column

    var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col10";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' name='txtValue' id='txtValue"+idCounter+"' maxlength='10' onblur='if(this.value.length > 0){return checknumberSCM(this)}' >";
	CellContents = str;
	tblcol.innerHTML = CellContents;	
	 
//Code for quantity column

    var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col10";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()'  type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' name='txtQuantity' id='txtQuantity"+idCounter+"' maxlength='10' onkeyup='if(this.value.length > 0){integersOnly(this)}' >";
	CellContents = str;
	tblcol.innerHTML = CellContents;
 
 //code for fifth column (Delivery location)
  
    var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col5";
	//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtDelLocId' id='txtDelLocId"+idCounter+"' isRequired='0' Disp='' class='dropdownMargin'><option value='-1'>Select Name</option></select>";
	str="<input type='text'  value='' onmouseover='getTip(value)'  countervalDel='"+ idCounter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='delLocation' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTDELLOCATION' id='delLocation" + idCounter + "'><a name='chargeNameLink' id='chargeNameLink" + idCounter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownDelLocation(" + idCounter + ")'>Show</a>";
	str=str+"<input type='hidden' name='txtDelLocId' id='txtDelLocId"+idCounter+"' value='-1' countervalDel='"+ idCounter +"'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
 
 //code for Sixth column (Subinventory)
  
    var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col6";
	//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtSubInvent' id='txtSubInvent"+idCounter+"' isRequired='0' Disp='' class='dropdownMargin'><option value='-1'>Select Name</option></select>";
	str="<input type='text'  value='' onmouseover='getTip(value)'  countervalSub='"+ idCounter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='subInvent' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTSUBINVENTORY' id='subInvent" + idCounter + "'><a name='chargeNameLink' id='chargeNameLink" + idCounter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownSubInventory(" + idCounter + ")'>Show</a>";
	str=str+"<input type='hidden' name='txtSubInvent' id='txtSubInvent"+idCounter+"' value='-1' countervalSub='"+ idCounter +"'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//code for budgethead1
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col7";
    //str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtBudgetHead' id='txtBudgetHead"+idCounter+"' isRequired='0' Disp='' onchange='getBudgetHeadAop2("+idCounter+",this.value);enableBudgetHead("+idCounter+",this)' class='dropdownMargin'><option value='-1'>Select Name</option></select>";
    str="<input type='text'  value='' onmouseover='getTip(value)'  countervalBud='"+ idCounter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='budgHead' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTBUDGHEAD'  id='budgHead" + idCounter + "'><a name='chargeNameLink' id='chargeNameLink" + idCounter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownBudgetHead(" + idCounter + ")'>Show</a>";
	str=str+"<input type='hidden' name='txtBudgetHead' id='txtBudgetHead"+idCounter+"' value='-1' countervalBud='"+ idCounter +"'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//code for budgethaed2
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col8";
	str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtBudget' id='txtBudget"+idCounter+"' isRequired='0' Disp=''disabled='true' class='dropdownMargin'><option value='-1'>Select Budget Head2</option></select>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//code for financial year
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col9";
	str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='financeYear' id='financeYear"+idCounter+"' isRequired='0' Disp='' class='dropdownMargin'><option value='-1'>Select Year</option></select>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	// code for PO Number
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col10";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' readonly='true' name='txtPoNumber' id='txtPoNumber"+idCounter+"' maxlength='340'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	// code for PO date
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col10";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' readonly='true' name='txtPoDate' id='txtPoDate"+idCounter+"' maxlength='340'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	// Code for Po Amount
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col10";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' readonly='true' name='txtPoAmount' id='txtPoAmount"+idCounter+"' maxlength='340'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	// code for IsActive
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col10";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' readonly='true' name='txtIsActive' id='txtIsActive"+idCounter+"' maxlength='340'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	// Code for Scm Message
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col10";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' readonly='true' name='txtScmMessage' id='txtScmMessage"+idCounter+"' maxlength='340'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	if(idCounter>=0)
	{
		document.getElementById('SelectAllChckCharges').disabled=false;
	}


	getFinancialYear(counter);
 	AttachCSStoAutoSuggestButton();
}

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
function allChargesCheckNone()
{
	document.getElementById('SelectAllChckCharges').checked=false;
}
 function fnCheckChargesAll()
{
 var myForm=document.getElementById('searchForm');
	 
	    var counter=1;
	    chargesTab = document.getElementById('tableCharges');
        var rowlen= chargesTab.rows.length;
         if(rowlen==0)
			 {
			 
			 document.getElementById('SelectAllChckCharges').checked=false;
			 document.getElementById('SelectAllChckCharges').disabled=true;
			 return false;
			 }
         var chkLength1=document.forms[0].chkSelectChargeDetail.length;
		if(document.getElementById('SelectAllChckCharges').checked==true)
		{
			        if(chkLength1==undefined)
	        
	                {
		               
		                    document.forms[0].chkSelectChargeDetail.checked=true;
		                
					}
          
			      else
			        {    
				         for (i =0; i<chkLength1; i++)
						   { 
								myForm.chkSelectChargeDetail[i].checked=true ;
					       }
				    }
	
	 
	         
	
		}	
	else
	   {
	           if(chkLength1==undefined)
        
		          {
			          document.forms[0].chkSelectChargeDetail.checked=false;
		          }
			      else
			      {    
			         for (i =0; i<chkLength1; i++)
					   { 
							        myForm.chkSelectChargeDetail[i].checked=false ;
						
					   }
				  }
	
		       
		}
	
	


	
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
function hideothers()
{
	$(":text[ctrltype='dropdown']").each(function() {
			var thisEl = $(this);				
		$(thisEl).autocomplete('widget').css('z-index', 100);
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
	    hideothers();
	    callOnBlur();
}
</script>
</head>
<body>
<fieldset class="border1" style="width:100%;" id="scmLine">
<div align="left"><b>SCM Line Details:</b></div>	

		<table border="1" cellpadding="0" cellspacing="0" class='main' id="tblChargesDetails" >        
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
                <td class='tablecontent' valign="top">
                    <div id='headscroll' class='divhead' style="vertical-align:top">                               	
                        <table border="0" cellpadding="0" cellspacing="0" class='head1' height="4%">
				         <input type="hidden" name="hdnChargeCount" value="0"/>        
                              <tr>
                                <td class='inner col1 head1' align="center">
                                    Select <input type="checkbox" name="SelectAllChckCharges" id="SelectAllChckCharges" onclick="javascript:fnCheckAllScmLines();" disabled="disabled"/>
                                </td>
                                <td class='inner col2 head1' align="center">
                                     Item Code
                                </td>
                                <td class='inner col3 head1' align="center">
                                     Value
                                </td>
                                <td class='inner col10 head1' align="center">
                                    Quantity (TP Contract Tenure)
                                </td>
                                
                                <td class='inner col5 head1' align="center">
                                    Delivery Location
                                </td>
                                <td class='inner col6 head1' align="center">
                                     Sub Inventory
                                </td>
                                <td class='inner col7 head1' align="center">
                                     Budget Head1
                                </td>
                                <td class='inner col8 head1' align="center">
                                     Budget Head2
                                </td>
                                <td class='inner col9 head1' align="center">
                                     Financial Year
                                </td>
                                <td class='inner col10 head1' align="center">
                                     PO Number
                                </td>
                                <td class='inner col10 head1' align="center">
                                     PO Date
                                </td>
                                <td class='inner col10 head1' align="center">
                                     PO Amount
                                </td>
                                <td class='inner inner col10 head1' align="center">
                                     Is Active
                                </td>
                                <td class='inner inner col10 head1' align="center">
                                  SCM Message
                                </td>
                           </table>
                     </div>
                     </div>
                      <div id='contentscroll' class='content' onscroll='reposHead(this);'>
                         <table cellpadding='0' cellspacing='0' class='content' id='tableCharges'>                        	
                                                                            
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
           
			<tr>
				<td colspan="9" align="center">
					    <input type="button" name="btnAddCharges" id="btnAddCharges" value="Add SCM Line" onClick="AddCharge()">
					    <input type="button" name="btnDeletePoDetail" id="btnDeletePoDetail" value="Delete SCM Line" onClick="DeleteCharge()">
				</td>
			</tr>
     </table>
     
     </div>
</fieldset>
</body>
</html>