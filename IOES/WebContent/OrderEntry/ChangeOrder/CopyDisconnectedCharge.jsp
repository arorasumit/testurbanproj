<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Charge</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var serviceProductID=<%=request.getParameter("serviceProductID")%>;
var serviceId=<%=request.getParameter("serviceNo")%>;
var orderNo=<%=request.getParameter("orderNo")%>;
var frequency=<%=request.getParameter("frequency")%>;
var callerWindowObj = dialogArguments;
var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
var chargeId=0;
function getInfoAndUpdate(chargeDetails) 
{
	var counter = parseInt(callerWindowObj.document.getElementById('hdnChargeCount').value);
	
	var chargeId;
	var disconnectionTyped;
	var index;
	var chargestype;
	var rcActiveDateCrossed;
	var nrcActiveDateCrossed;
	var chargesEndDateLogic;
	var rcInactiveDateCrossed;
	
	var chDet = chargeDetails.split(",");
	if (chDet.length > 0){	
		chargeId = chDet[0]; //charge id
		disconnectionTyped = chDet[1]; //disconnection type
		
		/*Here getting the right index/counter means which the exact position on which the chrge is drwa.
		* For this purpose we compare the charge id with pkChargeId+counter. That counter will be the exact index where pkcharge id match 
		*/
		for(k=counter; k >=1; k--){
			if(callerWindowObj.document.getElementById('pkChargeId'+k) != undefined && callerWindowObj.document.getElementById('pkChargeId'+k).value == chargeId ){
				index = k ;//here setting exact counter on which position charge is exists 	
				break;
			}		
		}
		chargestype = chDet[3]; // charge type
		rcActiveDateCrossed = chDet[4]; 
		nrcActiveDateCrossed = chDet[5]; 
		chargesEndDateLogic = chDet[6]; 
		rcInactiveDateCrossed = chDet[7]; 
	}
	
	/*Here charge is going to disconnected. */
	callerWindowObj.disconnectCharge(chargeId,disconnectionTyped,index,chargestype,rcActiveDateCrossed,nrcActiveDateCrossed,chargesEndDateLogic,rcInactiveDateCrossed,0);

	/*Now a new charge is going to dispaly on screen */
	var status = callerWindowObj.AddCharge(2);
	counter = parseInt(callerWindowObj.document.getElementById('hdnChargeCount').value); //again getting the counter 
	callerWindowObj.document.getElementById('txtOBCharge'+counter).value=chargeId;
	
	/*Now getting the all details for disconnected charge so that we can set that details to new charge that is earlier  added */
    var chargeDetails = jsonrpc.processes.populateChargeForCopyDisconnection(chargeId);
    if(chargeDetails != null){
    	callerWindowObj.document.getElementById('ddCType'+counter).value = chargeDetails.chargeTypeID; //charge type
    	callerWindowObj.document.getElementById('chargeName'+counter).value = chargeDetails.chargeName; //charge name in text form
    	callerWindowObj.document.getElementById('hdnChargeName'+counter).value = chargeDetails.chargeNameID; //charge name value in int form
    	callerWindowObj.document.getElementById('txtCName'+counter).value = chargeDetails.chargeNameID; //charge id a hidden field
    	callerWindowObj.document.getElementById('txtCAnnotation'+counter).value = chargeDetails.chargeAnnotation; //charge annotation
    	//callerWindowObj.document.getElementById('txtTaxRate'+counter).value = chargeDetails.taxRate; //tax rate
    	callerWindowObj.document.getElementById('txtCPeriod'+counter).value = chargeDetails.chargePeriod; //charge period
    	callerWindowObj.document.getElementById('txtCTAmt'+counter).value = chargeDetails.chargeAmount; //Period Line amount
    	callerWindowObj.document.getElementById('txtCFrequency'+counter).value = chargeDetails.chargeFrequency; //Billing Frequency
    	callerWindowObj.document.getElementById('txtCFreqAmt'+counter).value = chargeDetails.chargeFrequencyAmt; //Billing Frequency amount
    	//calling those methods which should be executed after selection of Billing frequecy dropdown
    	//callerWindowObj.chkDifferentialChargeForFrequency(counter);
    	callerWindowObj.document.getElementById('txtPayment1'+counter).value = chargeDetails.paymentTerm1; //Payment term 1
    	callerWindowObj.document.getElementById('txtPayment2'+counter).value = chargeDetails.paymentTerm2; //Payment term 2
    	callerWindowObj.document.getElementById('txtPayment3'+counter).value = chargeDetails.paymentTerm3; //Payment term 3
    	callerWindowObj.document.getElementById('txtPayment4'+counter).value = chargeDetails.paymentTerm4; //Payment term 4

    	/*disabling all fields except 'charge amount' because of this charge is linked with disconnected charges and no modification required */	
    	callerWindowObj.document.getElementById('ddCType'+counter).disabled=true;
    	callerWindowObj.document.getElementById('chargeName'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtCAnnotation'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtTaxRate'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtCPeriod'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtCFrequency'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtCFreqAmt'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtCStartDate'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtStartDays'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtStartMonth'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtCEndDate'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtEndDays'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtEndMonth'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtAnnualCharge'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtChargeStartDate'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtChargeEndDate'+counter).disabled=true;
    	callerWindowObj.document.getElementById('chkexcludeCharges'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtLDDateClause'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtDelayedTimeInDays'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtLDPercentage'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtMaxPenaltyPercentage'+counter).disabled=true;

    	callerWindowObj.document.getElementById('txtPayment1'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtPayment2'+counter).disabled=true;
    	callerWindowObj.document.getElementById('txtPayment3'+counter).disabled=true; 
    	callerWindowObj.document.getElementById('txtPayment4'+counter).disabled=true; 
    	
		// calculating tax rate after disconnect and copy charge
    	callerWindowObj.getTaxRate(counter);
		
    	/* A new charge has been added on screen and also a disconnected charge has been linked so we need to set that disconnected charge into an array.
    	* The array is alreay maintaining in parent window which containing the all disconnected charges id which are linked with new charge
    	* For this purpose just call a method for adding new charge
    	*/
    	callerWindowObj.addDisconnectLinkedCharge(chargeId);
    }
   
}

function DrawChargeList()
{
	var str;
	var searchChargeId=document.getElementById('searchChargeId').value;
	var mytable = document.getElementById('tabChargeLinking');
	var iCountRows = mytable.rows.length;
	//alert('iCountRows '+iCountRows)
	 for (k = iCountRows-1; k >=2; k--)
	   {	
	       mytable.deleteRow(k);
	   } 
	
	if(searchChargeId !=""){
		chargeId=Number(document.getElementById('searchChargeId').value);
	}else{
		chargeId=0;
	}
	//Get the charge list those are disconnected and can be linked with new charge 
	//var chargeList = jsonrpc.processes.getDifferentialCharges(Number(chargeId),Number(serviceProductID),Number(serviceId),Number(orderNo),Number(frequency));
	var chargeList = jsonrpc.processes.getChargesForDisconnectAndCopy(Number(chargeId),Number(serviceProductID),Number(serviceId),Number(frequency));
	
	
	var insertedRowCount = 0;
	var counter=0;
	for (i = 0; i <  chargeList.list.length; i++){
		/*this condition implies that 
		 *	1->the charge is not linked yet so this charge should be display
		 *	2->the charge should not be disconnected 
		*/	
		counter++;
		//validateChargeBeforeDisplay() - this function will decide that charge would be display or not 
		//if(validateChargeBeforeDisplay(chargeList.list[i].chargeInfoID)){
			
			var end_date_logic="";
			
			if(chargeList.list[i].endDateLogic=='BTD'){
				end_date_logic=1;
			}
			else if(chargeList.list[i].endDateLogic=='TD'){
				end_date_logic=2;
			}else {
				end_date_logic=0;
			}						
			
			/*Creating check boxes on the basis of condition */
			if(chargeList.list[i].chargeType == 1  && chargeList.list[i].rcActiveDateCrossed == '1'){
				str="<input name='chk' id='chk' type='checkbox' value='"+ chargeList.list[i].chargeInfoID +",2,"+counter+","+chargeList.list[i].chargeType+","+chargeList.list[i].rcActiveDateCrossed+","+chargeList.list[i].nrcActiveDateCrossed+","+end_date_logic+","+chargeList.list[i].rcInactiveDateCrossed+"'/>";
			}else if (chargeList.list[i].chargeType == 2  && chargeList.list[i].nrcActiveDateCrossed == '1'){
				str="<input name='chk' id='chk' type='checkbox' value='"+ chargeList.list[i].chargeInfoID +",1,"+counter+","+chargeList.list[i].chargeType+","+chargeList.list[i].rcActiveDateCrossed+","+chargeList.list[i].nrcActiveDateCrossed+","+end_date_logic+","+chargeList.list[i].rcInactiveDateCrossed+"'/>";
			}else if(chargeList.list[i].chargeType == 1  && chargeList.list[i].rcActiveDateCrossed == '0'){
				if(chargeList.list[i].endDateLogic == 'TD'){
					str="<input name='chk' id='chk' type='checkbox' value='"+ chargeList.list[i].chargeInfoID +",2,"+counter+","+chargeList.list[i].chargeType+","+chargeList.list[i].rcActiveDateCrossed+","+chargeList.list[i].nrcActiveDateCrossed+","+end_date_logic+","+chargeList.list[i].rcInactiveDateCrossed+"'/>";
				}else if(chargeList.list[i].endDateLogic == 'BTD' && chargeList.list[i].rcInactiveDateCrossed == '0'){
					continue;
				}else if(chargeList.list[i].endDateLogic == 'BTD' && chargeList.list[i].rcInactiveDateCrossed == '1'){
					str="<input name='chk' id='chk' type='checkbox' value='"+ chargeList.list[i].chargeInfoID +",2,"+counter+","+chargeList.list[i].chargeType+","+chargeList.list[i].rcActiveDateCrossed+","+chargeList.list[i].nrcActiveDateCrossed+","+end_date_logic+","+chargeList.list[i].rcInactiveDateCrossed+"'/>";
				}
				
			}else if(chargeList.list[i].chargeType == 2  && chargeList.list[i].nrcActiveDateCrossed == '0'){
				continue;
			}
				
			
			insertedRowCount++;	
			var tblRow=document.getElementById('tabChargeLinking').insertRow();
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			//str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(chargeList.list[i].chargeInfoID) +"') />";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=chargeList.list[i].chargeInfoID;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=chargeList.list[i].chargeTypeName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=chargeList.list[i].chargeName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=chargeList.list[i].chargePeriod;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=chargeList.list[i].chargeAmount;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=chargeList.list[i].startDateLogic;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=chargeList.list[i].endDateLogic;
			CellContents = str;
			tblcol.innerHTML = CellContents;
		//}
		
	}
	if(chargeList.list.length == 0 || insertedRowCount == 0){
		var tblRow=document.getElementById('tabChargeLinking').insertRow();
	    var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 8;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
	else{
		var tblRow=document.getElementById('tabChargeLinking').insertRow();	
		 var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 8;
			str="<input type='button' id='ok' name='ok' value='Disconnect and Add' title='Click to add selected charges' onClick='addDisconnectedCharge()'>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
	}
	
}
function isBlankSearch()
{
	var searchChargeId=document.getElementById("searchChargeId");
	if(searchChargeId.value.length>0){
		if(checkdigits(searchChargeId)==false){
			return false;
		}
	}
	DrawChargeList();	  
}

function validateChargeBeforeDisplay(disChargeId){
	//get the charges those are selected 
	var linkedCharge = callerWindowObj.selectedLinkedCharge();
	var validate = false;
	
	//Get the charge list those are already linked 
	var alreadyLinkedChargeList = jsonrpc.processes.getAlreadyLinkedCharge(Number(serviceProductID));
	
	var alreadyLnkChr="0";
	for(j=0; j < alreadyLinkedChargeList.list.length; j++){
		alreadyLnkChr = alreadyLnkChr+"_"+alreadyLinkedChargeList.list[j].differentialCharge;
	}
	if(linkedCharge.indexOf(disChargeId) == -1 && alreadyLnkChr.indexOf(disChargeId) == -1 ){
		validate =  true;
	}
    return  validate ; 
}

function searchDisconnectedCharge(){
	isBlankSearch();
}

function addDisconnectedCharge(){
	var isChargeSelected = false;
	var obPKCharge = document.getElementsByName('chk');
	
	if(obPKCharge.length > 0){
		for(i=0; i< obPKCharge.length; i++){
			if(obPKCharge[i].checked == true){
				isChargeSelected = true;
				break;
			}
		}
	}
	
	if(isChargeSelected){
		if(window.confirm(" The selected charge will get disconnected and new charge will be added. Do you want to continue ?")){
			for(i = 0; i < obPKCharge.length; i++){
				if(obPKCharge[i].checked == true){
					getInfoAndUpdate(obPKCharge[i].value);
				}
			}
		}
		window.close();
		
	}else{
		alert("Please select at lease one charge!");
		return;
	}
}

</script>
</head>
<body onload="DrawChargeList()">
	<fieldset class="border1">
		<legend> <b>Charge List</b> </legend>
		<table width="100%"  border="1" id="tabChargeLinking" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		   <tr class="grayBg">
				<td width="10%" align="center" colspan="8">
					<div style="float: left;"><strong>Input Charge</strong><input type="text" name="searchChargeId" id="searchChargeId" value="" onkeydown="if (event.keyCode == 13)  return searchDisconnectedCharge();" 
						class="inputBorder1" maxlength="10" /></div>
					<div class="searchBg" onclick="isBlankSearch()"  style="margin-right:10px;"><a href="#">Go</a></div>
				</td>
			</tr>
			<tr class="grayBg">
				<td width="5%" align="center">Select</td>
				<td width="15%" align="left">PK Charge Id</td>
				<td width="15%" align="left">Charge Type</td>
				<td width="20%" align="left">Charge Name</td>
				<td width="7%" align="left">Charge Period</td>
				<td width="8%" align="left">Amount</td>
				<td width="15%" align="left">Start Date Logic</td>
				<td width="15%" align="left">End Date Logic</td>
			</tr>
		</table>
	</fieldset>
</body>
</html>
