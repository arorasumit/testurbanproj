//Tag Name Resource Name  Date		CSR No			Description
//[001]	 MANISHA GARG    15-Mar-2013 				Service att validation
//[002]	 MANISHA GARG    15-Mar-2013 				Defect No 19032013001 Service att validation  
//[128]  Raveendra   16-02-2015  20150203-R1-021049  advance payment refund process  
//[003]  Gunjan Singla   4-Mar-15     20150225-R1-021111    LSI and Service No Search required in Lines Tab

function checknumber_max2decimal_charges_section(obj,alertgive)
{
	try{
		var x = obj.value
		var anum=/(^\d+$)|(^\d+\.$)|(^\d+\.\d$)|(^\d+\.\d\d$)/
		if (anum.test(x))
			testresult=true
		else{	
			if(alertgive==1){
				alert("Please input a valid number (with max of 2 decimal places)!")
			}
			testresult=false
			obj.focus();
		}
		return (testresult)
	}catch(e){
		alert('error code 354: '+ e.message);
		return false;
	}
}

function checknumber_charges_section(obj){
		var x = obj.val();
		var anum=/(^\d+$)|(^\d+\.$)|(^\d+\.\d$)|(^\d+\.\d\d$)/
		if (anum.test(x))
			testresult=true;
		else{
			var dotIndex = x.indexOf(".");
			if( dotIndex != -1 && (x.slice(dotIndex, x.length)).length<=2 && x.split(".").length != 2 )
				alert("Please input a valid number (with max of 2 decimal places)!");
			testresult=false;
			obj.focus();
		}
		return testresult;
}

function check_intermediate_number(obj)
{
	try{
		var x = obj.value
		var anum=/(^\d+$)|(^\d+\.\d+$)|(^\d+\.$)/
		if (anum.test(x))
			testresult=true
		else
		{
			alert("Please input a valid number!")
			testresult=false
			obj.value = "";
			// Commenting on focus as it is creating infinite loop
			//obj.focus();
		}
		return (testresult)
	}catch(e)
	{
		alert('error code 355: '+ e.message);
		return false;
	}
}
function checknumber(obj)
{
	try{
		if(obj.zeroValueAllowed=='N' && obj.value==0)
		{
		alert("0 is not allowed ");
		obj.value='';
		return false;
		}
		var x = obj.value
		var anum=/(^\d+$)|(^\d+\.\d+$)/
		if (anum.test(x))
			testresult=true
		else
		{
			alert("Please input a valid number!")
			testresult=false
				// Meenakshi : uncommenting and setting value of control to "" , so as the non numbers are cleared from Text box
			obj.value = "";
			obj.focus();
		}
		return (testresult)
	}catch(e)
	{
		alert('error code 356: '+ e.message);
		return false;
	}
}

function checkdigits(obj)
{
	try{
		var x = obj.value
		var anum=/(^\d+$)|(^\d+\d+$)/
		if (anum.test(x))
			testresult=true
		else
		{
			alert("Please input only digits!")
			testresult=false
			obj.value = "";
			obj.focus();
		}
		return (testresult)
	}catch(e)
	{
		alert('error code 357: '+ e.message);
		return false;
	}
}

function checkdigitsValidation(obj)
{
	var x = obj.value
	var anum=/(^\d+$)|(^\d+\d+$)/
	if (anum.test(x))
		testresult=true
	else if(x.match(/^-\d+$/))//negative number
	{	
		alert("Negative Number Not Allowed!")
		testresult=false
		obj.value = "";
		obj.focus();
          
	}
	else if(x.match(/^[0-9]*(\.[0-9]*)+$/))//Decimal number
	{	
		alert("Decimal Number Not Allowed!")
		testresult=false
		obj.value = "";
		obj.focus();
          
	}	
	else
	{
		alert("Please Input only Natural Number!")
		testresult=false
		obj.value = "";
		obj.focus();
	}
	return (testresult)
}
function countChar(countCharFor, displayCountedChar)
{
	formcontent = countCharFor.value
	displayCountedChar.value=formcontent.length
}

function isBlank(ctrl , label)
{
	if(document.getElementById(ctrl).value=="") 
	{
		alert(label + ' Cannot be left blank');
		document.getElementById(ctrl).focus();
		return false;
	}
}


function checkrequired(which)
{
	var pass=true
	if (document.images)
	{
		for (i=0;i<which.length;i++)
		{
			var tempobj=which.elements[i]
			if (tempobj.name.substring(0,8)=="required")
			{
				if (((tempobj.type=="text"||tempobj.type=="textarea")&&tempobj.value=='')||(tempobj.type.toString().charAt(0)=="s"&&tempobj.selectedIndex==-1))
				{
					pass=false
					break
				}
			}
		}
	}
	if (!pass)
	{
		alert("One or more of the required elements are not completed. Please complete them, then submit again!")
		return false
	}
	else
	return true
}

function checkdate(input)
{
	var validformat=/^\d{2}\/\d{2}\/\d{4}$/ //Basic check for format validity
	var returnval=false
	if (!validformat.test(input.value))
	{
		alert("Invalid Date Format. Please Enter Date In Correct Format(DD/MM/YYYY) and submit again.")
		//input.value='';
		}
	else
	{ //Detailed check for valid date ranges
		var monthfield=input.value.split("/")[1]
		var dayfield=input.value.split("/")[0]
		var yearfield=input.value.split("/")[2]
		var dayobj = new Date(yearfield, monthfield-1, dayfield)
		if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
			alert("Invalid Day, Month, or Year range detected. Please correct and submit again.")
		else
		returnval=true
	}
	if (returnval==false) 
	{
		input.select();
		input.focus();
	}
	return returnval
}

function emailValidate(obj) 
{
	
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var x = obj.value
	if(reg.test(x) == false) 
	{
		alert("Invalid Email Format. Please correct and submit again.")
		testresult=false
		obj.value = "";
		obj.focus();
	}
	testresult=true;
	return (testresult);
}

function YN(obj) 
{
	var x = obj.value
	if((x == "Y") || (x == "N") || (x == "y") || (x == "n"))
	{
		testresult=true;
	}
	else
	{
		alert("Invalid Data Inserted. Please correct and submit again(Y/N).")
		testresult=false
		obj.value = "";
		obj.focus();
	}
	
	return (testresult);
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

function isNull(val){return(val==null);}

function isBlank(ctrl , label)
{
	if(trim(document.getElementById(ctrl).value)=="") 
	{
		alert(label + ' Cannot be left blank');
		document.getElementById(ctrl).focus();
		return false;
	}
}

function selectDropdown(ctrl , label)
{
	if(document.getElementById(ctrl).value==0) 
	{
		alert('Please Select ' + label + '!!');
		document.getElementById(ctrl).focus();
		return false;
	}
}

function selectAutoSuggest(ctrl , label, textCtrl){
	if(document.getElementById(textCtrl).isRequired=="1" && !($("#"+textCtrl).is(':disabled')) && document.getElementById(ctrl).value==0){
		alert('Please Select ' + label + '!!');
		if($("#"+textCtrl).is(':visible')){
			$("#"+textCtrl).focus();
		}
		return false;
	}
}

function selectDropdown(ctrl , label,noSelectionValue)
{
	if(document.getElementById(ctrl).value==noSelectionValue || document.getElementById(ctrl).value==null || trim(document.getElementById(ctrl).value)=='') 
	{
		alert('Please Select ' + label + '!!');
		document.getElementById(ctrl).focus();
		return false;
	}
}

function checkTextAreaLength(obj,len)
{
	if(obj.value.length>len)
	{
		alert("Maxlength allowed is "+len+". Extra charaters are trimed.")
		testresult=false
		obj.value = obj.value.substring(0,len);
		obj.focus();
	}
	else
	{
		testresult=true
	}
	return (testresult)
}


//lawkush Start
function dateDifference(val1,val2)
{
	var date11 = new Date(formatDate(val1));
	var date22 = new Date(formatDate(val2));

	var timeDiff = Math.abs(date22.getTime() - date11.getTime());
	var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
	if(diffDays>90)
	{
		return 1;
	
	} 
	


}
function formatDate (input) {
  var datePart = input.match(/\d+/g);
  
  	day = datePart[0];
	month = datePart[1];
	year = datePart[2];

  return month+'/'+day+'/'+year;
}

//lawkush End

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
//Meenakshi : 
//Adding a new Email Validate Method for Email validation on PC
// Since Ctrl+S is not allowed on PC, we can set field blank.
function validateEmail(obj) 
{
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var x = obj.value
	if(reg.test(x) == false) 
	{
		alert("Invalid Email Format. Please correct and submit again.")
		testresult=false
		//obj.value = "";
		obj.focus();
	}
	testresult=true;
	return (testresult);
}

// added by manisha for service summary validation

function fproduct_att_validation(objname,objvalue,objexpvalue,objvalidation,attServiceSummMand)
{
	// 002 START
	if(attServiceSummMand=='1')
	{
		if(objvalue!='' && objvalue!=null)
		{
			switch(objvalidation){
				case 'MULTIPLE_64':
				
					if(objvalue==0 || objvalue%64!=0)
					{
						alert(objname +  '  should be multiple of 64');
						return false;
					
					}
				}
		}				
	}
	else
		{
			if(objvalue!='' && objvalue!=null)
			{
				switch(objvalidation){
					case 'MULTIPLE_64':
					
						if(objvalue%64!=0)
						{
							alert(objname +  '  should be multiple of 64');
							return false;
						
						}
					}
			}				
		}
	// 002 END	
		
	return true;

}

//Start [128]
function checkConfirmField(id1, reId1, label, reLabel){
	
	
    var id = document.getElementById(id1);
    var reId = document.getElementById(reId1);
  
    
    
    if(id.value == reId.value){
       
     return true;
    }else{
      alert("The "+reLabel+" doesn’t match with the "+label);
     
      return false;
       
    }
	
	
}
// End [128]
//[003] start
function checkdigitsServiceSearch(obj,label)
{
	try{
		var x = obj.value;
		var anum=/(^\d+$)|(^\d+\d+$)/;
		if (anum.test(x))
			testresult=true;
		else
		{
			alert("Please input only digits in "+label+"!");
			testresult=false;
			obj.value = "";
			obj.focus();
		}
		return (testresult);
	}catch(e)
	
	{
		alert('error code 736:' +e.message);
		return false;
	}
}

//[003] end
//ravi
function CheckMinLength(fieldname){
	
	if(fieldname.value.length < 10){
		
		alert("Please input atleast 10 charactors!");
		
		fieldname.focus();
		
	 return false;
	
	}
	return true;
	 
 }
//Gunjan Start
function checkdecimal(obj)
{
	try{
		var x = obj.value
		//var anum=/(^\d+$)|(^\d*\.\d{1,4})$)/
			var anum=/(^\d+$)|(^\d*\.$)|(^\d*\.\d$)|(^\d*\.\d\d$)|(^\d*\.\d\d\d$)|(^\d*\.\d\d\d\d$)/
			if (anum.test(x))
			testresult=true
		else
		{
			alert("Please input a valid number (with max of 4 decimal places)!")
			testresult=false
			obj.value = "";
			obj.focus();
		}
		return (testresult)
	}catch(e)
	{
		alert('error code 600');
		return false;
	}
}

//Gunjan End