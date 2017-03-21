//for textarea count
/*
[15032013012]   Vijay      14-March-13         A Generic method is written that display a msg with title and a tick mark. This msg would be fade out after 1.5 second 
[002] 		Gunjan Singla  18-March-14         Added a method ValidateRemarks() for not allowing special characters
[003]       pankaj thakur  26-nov-14           Added a method validateFileName() to allowed certain speical characters which are availabe in table field
[004]	VIPIN SAHARIA 13-May-2015 20150403-R2-021204 Project Satyapan Validating mandatory nature of fields while updating ISP tagging fields
[005]   RAHUL TANDON  20150508-R2-021322 SalesForce Opportunity number in iB2B
*/
function KeyCheck()
{
   var KeyID = event.keyCode;
	var t = window.event.srcElement.type;
	if( KeyID==8 && !((t =='text' || t =='textarea' || t =='file')&& window.event.srcElement.readOnly!=true ))
	{
		return false;
	}
}
function textAreaMax(maxLength)
{
	var taObj=event.srcElement;
	if (taObj.value.length>maxLength*1) 
	{
		alert("Maximum limit allowed is "+maxLength+" characters.");
		taObj.value=taObj.value.substring(0,maxLength*1);	
	}
}
function isNumberKey(evt)
{
       var charCode = (evt.which) ? evt.which : event.keyCode
       if (charCode > 31 && (charCode < 48 || charCode > 57))
          return false;
       return true;
}
function emailValidate(val) {
   var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
   if(reg.test(val) == false) {
      return false;
   }
   return true;
}
function emailValidate1(val) {
	//alert(val.value);
   var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
   //var reg = /^([a-zA-z0-9\.\_\-]+\@[a-zA-z0-9\.\_\-]+\.[a-zA-z0-9]$/;
   //alert(reg.test(val.value));
   if(reg.test(val.value) == false) {
   	  alert("Invalid Email Format. Please correct and submit again.");
   	  val.focus();
   	  testresult=false;      
   }
   else
   {
   	testresult=true;  
   }
   //alert(testresult);
   return testresult;
}
function phoneNoValidate(val) {
	
   var reg =/^([0-9]+)$/;
   if(reg.test(val) == false) {
      return false;
   }
   return true;
}

//Format of strings : dd/mm/yyyy
function dateDifference(val1,val2)
{
	//format: dd/MM/yyyy
	str1=new String(val1);
	y=str1.substring(6);
	m=str1.substring(3,5);
	d=str1.substring(0,2);
	
	var date1=new Date(Number(y),Number(m)-1,Number(d));

	str2=new String(val2);
	y=str2.substring(6);
	m=str2.substring(3,5);
	d=str2.substring(0,2);
	var date2=new Date(Number(y),Number(m)-1,Number(d));
	
	var one_day=1000*60*60*24;
	
	var diff=Math.ceil((date1.getTime()-date2.getTime())/(one_day));

	return diff;
}

function dateCompare(val1,val2)
{
	//format: dd/MM/yyyy
	str1=new String(val1);
	y=str1.substring(6);
	m=str1.substring(3,5);
	d=str1.substring(0,2);
	date1=y+m+d;

	str2=new String(val2);
	y=str2.substring(6);
	m=str2.substring(3,5);
	d=str2.substring(0,2);
	date2=y+m+d;
	
	var date11 = new Date(val1);
	var date22 = new Date(val2);

	var diffDays=date11.getDate()-date22.getDate();

	if(date1>date2)
	 {
		return 1;
	 }
	 else if(date1==date2)
	 {
	 	return 0;
	 }
	 else if(date1<date2)
	 {
	 	return -1;
	 }
}

function isEmpty(val)
{
	if(trim(val)=='')
	{	
		return true;
	}
	else
	{
		return false;
	}
}
function isNotSelected(val)
{
	
	if(val=='-1')
	{
		return true;
	}
	else
	{
		return false;
	}
}

function getMonth(str)
{
	if(str=='Jan')
	{
		return '01';
	}else if(str=='Feb')
	{
		return '02';
	}else if(str=='Mar')
	{
		return '03';
	}else if(str=='Apr')
	{
		return '04';
	}else if(str=='May')
	{
		return '05';
	}else if(str=='Jun')
	{
		return '06';
	}else if(str=='Jul')
	{
		return '07';
	}else if(str=='Aug')
	{
		return '08';
	}else if(str=='Sep')
	{
		return '09';
	}else if(str=='Oct')
	{
		return '10';
	}else if(str=='Nov')
	{
		return '11';
	}else if(str=='Dec')
	{
		return '12';
	} 

}

function  isValidFileExtension(data)      
{
   data = data.replace(/^\s|\s$/g, "");
   if (data.match(/([^\/\\]+)\.((jpg)|(gif)|(jpeg)|(zip)|(ppt)|(pptx)|(doc)|(xls)|(xlsx)|(bmp)|(tif)|(tiff)|(png)|(docx)|(txt))$/i) )
   {
	  return true;  
   }
   else
   {
		alert("Selected Attachment is of Wrong Type");
	    return false ;				
   }
}
function createDummyForm(actionUrl)
{
	myform=document.createElement("form");
	document.body.appendChild(myform);
	myform.action=actionUrl;
	return myform;
}
function attachHiddenField(p_form,p_field_name,p_field_value)
{
	p_form.appendChild(getHiddenField(p_field_name,p_field_value));
}
function getHiddenField(varName,varValue)
{
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name",varName);
	input.setAttribute("value", varValue);
	return input;
}

function CheckNumeric(txt,label)
{
	if (isNaN(txt.value) == true)
	{
		alert("Sorry! Please Enter only Numeric Values! in " + label);
		txt.focus();
		return false;
	}	
	
	if (parseInt(txt.value)==0 || parseInt(txt.value)< 0)
	{
		alert("Sorry! Please Enter Value Greater Than 0! in " + label);
		txt.focus();
		return false;
	}
	
	if ((label=="Project ID") || (label=="Airtel Potential") || (label=="Capex Requirement") || (label=="Total Market Potential")||(label=="Account No"))
	{
		var strTextEntered="";	
		strTextEntered=txt.value;
		if (strTextEntered.indexOf(".")!=-1) 
		{
			alert("Decimal Values are Not Allowed in " + label +"!!!");
			return false;
		}
	}
	if(txt.value != "")
	{
		var iChars = "!$%^&*()+=[]\\\';,/{}|\":<>?";
		var arr=new Array();
		arr=iChars.split(",");
		for (var i = 0; i < txt.value.length; i++)
		{
	  		if (iChars.indexOf(txt.value.charAt(i)) != -1) 
	  		{
		  		alert ("Only AlphaNumeric Character are allowed in " + label +"!!!");
				txt.focus();	
				return false;
	  		}
	  	}
	}	  
}

//OPS01042013001 added by manisha start 
function ValidateTextField(fieldname,path,label)
{

	try
	{	
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		var iChars;
		iChars=jsonrpc.processes.getSpecialCharContact();
		
		var arr=new Array();
		arr=iChars.split(",");
		var flag=1;
		for (var i = 0; i < fieldname.value.length; i++) 
		{
			j=0;
			flag=1;
			for(j=0;j<arr.length;j++)
			{
				if (fieldname.value.charAt(i).charCodeAt(0) < 65 || fieldname.value.charAt(i).charCodeAt(0) >90)
				{
					if (fieldname.value.charAt(i).charCodeAt(0) < 97 || fieldname.value.charAt(i).charCodeAt(0) >122)	
					{
						if (fieldname.value.charAt(i).charCodeAt(0) < 48 || fieldname.value.charAt(i).charCodeAt(0) >57)	
						{
							    if (fieldname.value.charAt(i).charCodeAt(0) == arr[j]) 
							    {
							    	flag=0;
							    }
							
						}
						else
							flag=0;
					}
					else
						flag=0;
				}
				else
					flag=0; 
				
				 if(flag==1 && j==arr.length-1)
				 {
				 		
						alert("Sorry!  Following `~._- Special Characters not allowed in " + label +"!!!");
						fieldname.value = "";
						fieldname.focus();
						return false;				 
				}
			}
		}
	}
	catch(e) {
		alert('error code 172: '+ e.message);
		return false;
	}
}

//[003] start


function validateFileName(fieldname,path,label)
{
	 
	 try{	
			jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
			var iChars;
			iChars=jsonrpc.processes.getSpecialCharValue();
			
			
			var arr=new Array();
			arr=iChars.split(",");
			var flag=1;//specifies that filename is not correct
			for (var i = 0; i<fieldname.value.length; i++) 
			{
				//j=0;
				flag=1;
				for(j=0;j<arr.length;j++)
				{
					if (fieldname.value.charAt(i).charCodeAt(0) < 65 || fieldname.value.charAt(i).charCodeAt(0) >90)
					{
						if (fieldname.value.charAt(i).charCodeAt(0) < 97 || fieldname.value.charAt(i).charCodeAt(0) >122)	
						{
							if (fieldname.value.charAt(i).charCodeAt(0) < 48 || fieldname.value.charAt(i).charCodeAt(0) >57)	
							{
								    if (fieldname.value.charAt(i).charCodeAt(0)== arr[j]) 
								    {
								    	
								    	flag=0;
								    }
								
							}
							else{
								flag=0;
							}
						}
						else{
							flag=0;
						}
					}
				else{
					flag=0; 
				}
					 if(flag==1 && j==arr.length-1)
					 {
						
							alert("Sorry! Can't contain any of the following Special Characters \n  :*?/<>`~.-'\|  in " + label +" !!! ");
							fieldname.value = "";
							fieldname.focus();
							return false;				 
					}
				}
			}
		}
		catch(e) {
			alert('error code 200');
			return false;
		}
		return true;	
		
}
 
 //[003] end



// OPS01042013001 added by manisha end 

// OPS01042013001 added by manisha start 
function ValidateTextFieldM6(fieldname,pathM6)
{

	if(fieldname.zeroValueAllowed=='N'&& fieldname.value==0)
		{
		alert("0 is not allowed");
		fieldname.value='';
		return false;
		}
	try
	{	
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		var iChars;
		iChars=jsonrpc.processes.getSpecialCharContact();
		
		var arr=new Array();
		arr=iChars.split(",");
		var flag=1;
		for (var i = 0; i < fieldname.value.length; i++) 
		{
			j=0;
			flag=1;
			for(j=0;j<arr.length;j++)
			{
				if (fieldname.value.charAt(i).charCodeAt(0) < 65 || fieldname.value.charAt(i).charCodeAt(0) >90)
				{
					if (fieldname.value.charAt(i).charCodeAt(0) < 97 || fieldname.value.charAt(i).charCodeAt(0) >122)	
					{
						if (fieldname.value.charAt(i).charCodeAt(0) < 48 || fieldname.value.charAt(i).charCodeAt(0) >57)	
						{
						    if (fieldname.value.charAt(i).charCodeAt(0) == arr[j]) 
						    {
						    	flag=0;
						    }	
						   	
						}
						else
							flag=0;
					}
					else
						flag=0;
				}
				else
					flag=0; 
				
				 if(flag==1 && j==arr.length-1)
				 {
						alert("Sorry!  Following `~._: Special Characters not allowed !!!");
						//fieldname.value = "";
						fieldname.focus();
						return false;				 
				}
			}
		}
	}
	catch(e) {
		alert('error code 173: '+ e.message);
		return false;
	}
}

// OPS01042013001 added by manisha end
function compareTwoDates(date1,date2)
{	
	var retvalue = new Boolean(true);
	var monthArray = new Array('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
	var fromDateArray;
	var fromDate;
	var toDateArray;
	var toDate;
	fromDateArray = date1.split("-");
	fromDate=new Date();
	fromDate.setUTCFullYear(fromDateArray[2]);
	for (var j=0;j<12;j++)
	{
		if (monthArray[j]==fromDateArray[1])
		{
		fromDate.setUTCMonth(j);
		break;
		}
	}
	fromDate.setUTCDate(fromDateArray[0]);
	
	toDateArray = date2.split("-");
	toDate=new Date();
	toDate.setUTCFullYear(toDateArray[2]);
	for (var j=0;j<12;j++)
	{
		if (monthArray[j]==toDateArray[1])
		{
		toDate.setUTCMonth(j);
		break;
		}
	}
	toDate.setUTCDate(toDateArray[0]);
								
	if(fromDate-toDate>0)
	{
	retvalue = false;
	return retvalue;
	}
	else
	{
	return retvalue;
	}
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function chkDescriptionLength(remarks,label)
{
	
	if (remarks != '' && 
	trim(remarks) != '' && 
	trim(remarks).length >1000)
	{
		alert("Description cannot contain more then 1000 characters in "+ label +"!!!");
		return false;
	}
	return true;
}
 function isPositiveIntegerGreaterThan0(val)
{

    if(val==null)
    {
        return false;
    }
    if (val.length==0)
    {
        return false;
    }
    for (var i = 0; i < val.length; i++) 
    {
        var ch = val.charAt(i)
        
        if (ch < "0" || ch > "9")
        {
            return false
        }
    }
    if(Number(val)==0)
    {
    	return false;
    }
    return true
}
function DisableAndGenerateHidden(elem)
{
//alert(elem.name);
//alert(elem.value);
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", elem.name);
	input.setAttribute("value", elem.value);
	par=elem.parentNode;
	par.appendChild(input);
	elem.disabled=true;
}
function encrypt(var1,appContextPath) 
        { 
          	
try
	{	
		var encodedString = "";
		jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
		encodedString = jsonrpc.processes.encryptString(var1);
		return encodedString;	
	}
	catch(e) {
		alert(e);
	}
	
  } 
function setFormBean(formObject)
{
	for(i=0;i<formObject.elements.length;i++)
	{
		//alert("Elements : "+formObject.elements[i].value)
		var a=formObject.elements[i].type;
		if(a=='text' || a=='textarea')
		{
			formObject.elements[i].value = trim(formObject.elements[i].value);
		}
	}	
}	    
function showJavascriptException(e,flag)
{
	if(flag=='1')
	{
		alert(e);
	}
}



function setBlankIfNull(val)
{
	if (val==null)
		return "";	
	else	return val;
}    

function accountValidation(txt,label)
{
	if (isNaN(txt.value) == true && (label!="Salesforce Number"))
	{
		alert("Please Enter only Numeric Values! in " + label);
		txt.focus();
		return false;
	}	
	
	if (parseInt(txt.value)==0 || parseInt(txt.value)< 0)
	{		
		txt.value="";
		txt.focus();
		return false;
	}
	
	if ((label=="Account No"))
	{
		var strTextEntered="";	
		strTextEntered=txt.value;
		if (strTextEntered.indexOf(".")!=-1) 
		{
			alert("Decimal Values are Not Allowed in " + label +"!!!");
			return false;
		}
	}
	if(txt.value != "" && (label!="Salesforce Number"))
	{
		var iChars = "!$#@-_^&*()+=[]\\\';,/{}|\":<>?";
		var arr=new Array();
		arr=iChars.split(",");
		for (var i = 0; i < txt.value.length; i++)
		{
	  		if (iChars.indexOf(txt.value.charAt(i)) != -1) 
	  		{
		  		alert ("Special Characters are not allowed in " + label +"!!!");
				txt.focus();	
				return false;
	  		}
	  	}
	}	

}

function LoadToolTip()
	{
		var total = $('select option').length;
		var cur;
		for ( var i = 0; i < total; i++ )
		{
			cur = $('select option:eq(' + i + ')');
			cur.attr( 'title', cur.html() );
		}
	}	

//--[15032013012]--start--//
/*
	This method take two arguments first is title and second is msg.
	This method popup a alert without any button and aftter 1.5 second 
	it would be fade out.
*/
function fadeOutAlert(title,msg){
	$("BODY").append(
			  
			 '<div id="dialog1" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-draggable" style="background: white; height: 125px; width: 300px; top: 250px; left: 350px; display: block; z-index: 99999;" tabindex="-1" role="dialog" aria-describedby="dialog" aria-labelledby="ui-id-1">' +
			'<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" style="background: #C2F5BD;">'+
			'<span id="ui-id-1" class="ui-dialog-title" style="background: #C2F5BD;">'+ title+'</span>'+
			'</div>' +	 
			'<div id="dialog" class="ui-dialog-content ui-widget-content" style="background: #F7F7E7; width: auto; height:125px; min-height: 125px; max-height: 170px;  z-index: 99999">' +				 
			'<p style="padding:.8em;">'+
			'<img src="/IOES/images/confirmation.png" width="25" height="25" style="float: left; margin: 6px 7px 50px 0;" />' +
			'<br/><b>'+
			  msg +'</b></p>' +
			'</div>'+
			'</div>'); 
			
		setTimeout(function() {
                    $('#dialog1').fadeOut('fast');
                }, 1500);
                
                
}
//--[15032013012]--end--//

//var gb_reportMaxLimitMessage="Report contains more than 50000 records. Pls refine your search criteria.";
function maxLimitChecker(reportForm,ReportsExcelMaxSize,ReportsExcelMaxSizeMessage)
{
	if(reportForm.reportProcessForLimit.value=="1")
	{
		reportForm.reportProcessForLimit.value="0";
		var completeStr=ReportsExcelMaxSizeMessage.replace("{0}",ReportsExcelMaxSize);
		
		if(reportForm.isDumpAvailable!=null)
		{
		if(reportForm.isDumpAvailable.value=="Y")
			alert(completeStr+" or get the system generated csv dump.");
			else
		 alert(completeStr);
		 }
		 else
		  alert(completeStr);
	}
}

//[002]
 function ValidateRemarks(fieldname,path,label){
	if(fieldname.value != "")
	{
		var iChars = "!$#@-_^&*()+=[]\\\';,/{}|\":<>?";
		
		for (var i = 0; i < fieldname.value.length; i++)
		{
	  		if (iChars.indexOf(fieldname.value.charAt(i)) != -1) 
	  		{
		  		alert ("Special Characters are not allowed in " + label +"!!!");
		  		fieldname.focus();	
				return false;
	  		}
	  	}
	}	
	return true;
	 
 }
 //[004] Start PROJECT SATYAPAN
function checkIspTaggingMandate(chk){
	if(chk==1){
		
		document.getElementById("ispLicCtgry").style.background="#FFFF99";
		document.getElementById("ispLicDate").style.background="#FFFF99";
		document.getElementById("ispLicNo").style.background="#FFFF99";
	}else{
		
		document.getElementById("ispLicCtgry").style.background="#ffffff";
		document.getElementById("ispLicDate").style.background="#ffffff";
		document.getElementById("ispLicNo").style.background="#ffffff";
		// making dependent fields Blank
		document.getElementById("ispLicCtgry").selectedIndex="0";
		document.getElementById("ispLicDate").value="";
		document.getElementById("ispLicNo").value="";
	}
}
function chkIsISPChangeAllowed(ispTagField){
	var tabValue = document.getElementById("hdnTabVal").value;
	var ispTagging = document.getElementById("ispTagging");
	var ispLicCtgry = document.getElementById("ispLicCtgry");
	var ispLicDate = document.getElementById("ispLicDate");
	var ispLicNo = document.getElementById("ispLicNo");
	var dateIspLic= document.getElementById("dateIspLic");
	
	if(tabValue != undefined){
		if(tabValue == 5 ||tabValue == 6){
			alert("No change allowed in lines tab and approval tab");
			ispTagging.readOnly = true;
			ispLicCtgry.readOnly = true;
			ispLicDate.readOnly = true;
			dateIspLic.disabled=true;
			ispLicNo.readOnly = true;
			ispTagging.blur();
			ispLicCtgry.blur();
			ispLicDate.blur();
			dateIspLic.blur();
			ispLicNo.blur();
		}else{
			ispTagging.readOnly = false;
			ispLicCtgry.readOnly = false;
			ispLicDate.readOnly = false;
			dateIspLic.disabled=false;
			ispLicNo.readOnly = false;
			if(ispTagField==2){
				if(ispTagging.selectedIndex=="0"){
					alert("Please select ISP tagging field value first");
					ispLicCtgry.readOnly = true;
					ispLicDate.readOnly = true;
					dateIspLic.disabled=true;
					ispLicNo.readOnly = true;
					ispTagging.blur();
					ispLicCtgry.blur();
					ispLicDate.blur();
					dateIspLic.blur();
					ispLicNo.blur();
				}else{
					ispTagging.readOnly = false;
					ispLicCtgry.readOnly = false;
					ispLicDate.readOnly = false;
					dateIspLic.disabled=false;
					ispLicNo.readOnly = false;
				}
			}
		}
	}
	
}
//[004] End PROJECT SATYAPAN
//this function is used to change colour for OSPRegNo,OspRegDate based on value of OSP Taggimg
function checkOSPTaggingMandate(chk){
		if(chk==1){
		
			document.getElementById("txtOSPRegNo").disabled=false;
			document.getElementById("txtOSPRegDate").disabled=false;
			
			document.getElementById("txtOSPRegNo").className = "inputBorder1";
			document.getElementById("txtOSPRegDate").className = "inputBorder1";
			document.getElementById("txtOSPRegNo").isRequired="1";
			document.getElementById("txtOSPRegDate").isRequired="1";
		
		}else{
			
			document.getElementById("txtOSPRegNo").isRequired="0";
			document.getElementById("txtOSPRegDate").isRequired="0";
			
			document.getElementById("txtOSPRegNo").value="";
			document.getElementById("txtOSPRegDate").value="";
			document.getElementById("txtOSPRegNo").disabled=true;
			document.getElementById("txtOSPRegDate").disabled=true;
			
			document.getElementById("txtOSPRegNo").className = "inputBorder2";
			document.getElementById("txtOSPRegDate").className = "inputBorder2";
		
		}
	}

//pankaj Start for channelPartner

function channelPartnerEnabledAndDisabled(chk){
	//alert('chk'+chk)
	if(chk==1){
		
		document.getElementById("channelPartnerId").style.background="#FFFF99";
	//	document.getElementById('divChannelPartner').style.visibility ="visible";
		document.getElementById('channelPartnerName').style.visibility ="visible";
		document.getElementById('channelPartnerId').style.visibility ="visible";
		document.getElementById('channelpartnerCode').style.visibility ="visible";
		document.getElementById('fieldEngineer').style.visibility ="visible";
		document.getElementById('fieldEngineerId').style.visibility ="visible";
		document.getElementById('fieldEngineerlbl').style.visibility ="visible";
		document.getElementById('channelPartnerlbl').style.visibility ="visible";
	}else{
		
		document.getElementById("channelPartnerId").style.background="#ffffff";
		
		// making dependent fields Blank
		//document.getElementById("divChannelPartner").selectedIndex="1";
		//document.getElementById("channelPartnerCtgryName").value="";
		//document.getElementById("channelPartnerCtgry").value="";
		//document.getElementById("channelPartnerCode").value="";
		//document.getElementById("fieldEngineer").value="";
		//document.getElementById("fieldEngineerId").value="";
		
	//	document.getElementById('divChannelPartner').style.visibility ="hidden";
		document.getElementById('channelPartnerName').style.visibility ="hidden";
		document.getElementById('channelPartnerId').style.visibility ="hidden";
		document.getElementById('channelpartnerCode').style.visibility ="hidden";
		document.getElementById('fieldEngineer').style.visibility ="hidden";
		document.getElementById('fieldEngineerId').style.visibility ="hidden";
		document.getElementById('fieldEngineerlbl').style.visibility ="hidden";
		document.getElementById('channelPartnerlbl').style.visibility ="hidden";
			
	}
}


function chkIschannelPatnerTaggingAllowed(TagField){

	var tabValue = document.getElementById("hdnTabVal").value;
	var channelMasterTagging = document.getElementById("channelMasterTagging");
	var channelPartnerCtgry = document.getElementById("channelPartnerId");

		if(tabValue != undefined){
			if(tabValue == 5 ||tabValue == 6){
				alert("No change allowed in lines tab and approval tab");
				channelMasterTagging.readOnly = true;
				channelPartnerCtgry.readOnly = true;
				channelMasterTagging.blur();
				channelPartnerCtgry.blur();
				return false;
				
			}else{
				channelMasterTagging.readOnly = false;
				channelPartnerCtgry.readOnly = false;
				if(TagField==2){
					if(channelMasterTagging.selectedIndex=="0"){
					//	alert("Please Select YES from Channel Partner Tagging");
						channelPartnerCtgry.readOnly = true;
						channelMasterTagging.blur();
						channelPartnerCtgry.blur();
						return false;
						
					}else{
						channelMasterTagging.readOnly = false;
						channelPartnerCtgry.readOnly = false;
					}
				}
			}
		}
	
}




//[005] Start 20150508-R2-021322 SalesForce Opportunity number in iB2B
function checkchannelMasterTaggingMandate(chk){
	//alert('chk'+chk)
	if(chk==1){
		
		document.getElementById("channelPartnerCtgry").style.background="#FFFF99";

	}else{
		
		document.getElementById("channelPartnerCtgry").style.background="#ffffff";
		
		// making dependent fields Blank
		document.getElementById("channelPartnerCtgry").selectedIndex="0";
		document.getElementById("channelPartnerCtgry1").value="";
	}
}

function chkIschannelMasterTaggingAllowed(TagField){
	//var tabValue = document.getElementById("hdnTabValchannelmaster").value;
	var tabValue = document.getElementById("hdnTabVal").value;
	//var orderStage = document.getElementById('orderStageAnnotationName').value;
	var channelMasterTagging = document.getElementById("channelMasterTagging");
	var channelPartnerCtgry = document.getElementById("channelPartnerCtgry");
	//alert('orderStage-'+orderStage+'-orderStage')
	//alert('(orderStage!= New'+(orderStage!= 'New'))
	//alert('(orderStage!= AM'+( orderStage!= 'Pending With AM' ))

	if(tabValue != undefined){
		
		/*if ((orderStage== 'New' ) ||( orderStage == 'Pending With AM' )){*/
		if(tabValue != undefined){
			if(tabValue == 5 ||tabValue == 6){
				alert("No change allowed in lines tab and approval tab");
				channelMasterTagging.readOnly = true;
				channelPartnerCtgry.readOnly = true;
				
				channelMasterTagging.blur();
				channelPartnerCtgry.blur();
			}else{
				channelMasterTagging.readOnly = false;
				channelPartnerCtgry.readOnly = false;
				if(TagField==2){
					if(channelMasterTagging.selectedIndex=="0"){
						alert("Please select channel partner tagging field value first");
						channelPartnerCtgry.readOnly = true;
						channelMasterTagging.blur();
						channelPartnerCtgry.blur();
					}else{
						channelMasterTagging.readOnly = false;
						channelPartnerCtgry.readOnly = false;
					}
				}
			}
		}
	}
}


/*function onChangingChannelPartnerDropDown(){
	var channelPartnerCtgry = document.getElementById("channelPartnerCtgry");
	var orderStage = document.getElementById('orderStageAnnotationName').value;
	var tabValue = document.getElementById("hdnTabVal").value;
	//alert(channelPartnerCtgry.value)
	if(orderStage != undefined){		
		if ((orderStage== 'New' ) ||( orderStage == 'Pending With AM' )){			
			if(tabValue == 5 ||tabValue == 6){				
				if(channelPartnerCtgry.value!=''){
			         //alert("You can save channel partner in main , contact and PO tab")
			       }				
			}			
		}
	}
}*/

//[005] End 20150508-R2-021322 SalesForce Opportunity number in iB2B