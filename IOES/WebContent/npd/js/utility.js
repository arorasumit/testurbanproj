//for textarea count

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
	
	if(label=="Capex Requirement")
	{
		if (parseInt(txt.value)< 0)
		{
			alert("Sorry! Please Enter Value Greater Than 0! in " + label);
			txt.focus();
			return false;
		}
	}
	else
	{
		if (parseInt(txt.value)==0 || parseInt(txt.value)< 0)
		{
			alert("Sorry! Please Enter Value Greater Than 0! in " + label);
			txt.focus();
			return false;
		}
	}
	
	if ((label=="Project ID") || (label=="Airtel Potential") || (label=="Capex Requirement") || (label=="Total Market Potential"))
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

function ValidateTextField(fieldname,path,label)
{

	try
	{	
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		var iChars;
		iChars=jsonrpc.processes.getSpecialChar();
		
		var arr=new Array();
		arr=iChars.split(",");
		for (var i = 0; i < fieldname.value.length; i++) 
		{
			for(j=0;j<arr.length;j++)
			{
				if (fieldname.value.charAt(i).charCodeAt(0) == arr[j]) 
				{
					alert("Sorry! Special Characters not allowed in " + label +"!!!");
					fieldname.focus();
					return false;
				}
			}
		}
	}
	catch(e) {
		alert(e);
		return false;
	}
}

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