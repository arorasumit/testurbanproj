<!-- [001] Nagarjuna  10/3/2014 Pm Welcome mail edition jsp -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<html:html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/npd/CSS/style.css">

<script type="text/javascript" src="js/utility.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>

<title>Welcome Mail </title>

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
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
    
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<script language="javascript" type="text/javascript" >
var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
path='<%=request.getContextPath()%>';

function getTip_DD(obj,value,objName)
	{	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('searchForm');
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
	}
	
	function goToHome()
	{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	//showLayer();
	document.forms[0].submit();
	}
	//cancelling 
 function cancelMailDetails()
 {
 		document.getElementById("searchForm").reset();
 		window.history.back();
 	 
 }

function Execute()
	{
	
	
/*		if(ValidateFields()==false)
		  {
				return false;
		  }
*/		
		
	 	doLoad();
		
	}
	
	
function countChars(countfrom,displayto) {

  var len = document.getElementById(countfrom).value.length;
  
  document.getElementById(displayto).innerHTML = len;
}
function doLoad()
{

var mailbody = jsonrpc.processes.getPMWelcomeMail();
document.getElementById("data1").value=mailbody.list[0].mailBody;
document.getElementById("data2").value=mailbody.list[0].mailBodyPart2;
document.getElementById("data3").value=mailbody.list[0].mailBodyPart3;
document.getElementById("data4").value=mailbody.list[0].mailBodyPart4;
document.getElementById("data5").value=mailbody.list[0].mailBodyPart5;
}
function submitData()
{
var body1 = document.getElementById("data1").value;
var body2 = document.getElementById("data2").value;
var body3 = document.getElementById("data3").value;
var body4 = document.getElementById("data4").value;
var body5 = document.getElementById("data5").value;

var jsData =
 	{
mailBody:document.getElementById("data1").value,
mailBodyPart2:document.getElementById("data2").value,
mailBodyPart3:document.getElementById("data3").value,
mailBodyPart4:document.getElementById("data4").value,
mailBodyPart5:document.getElementById("data5").value
 	
 	};
 	if( (body1  != null) || (body2  != null) || (body3  != null) || (body4  != null) || (body5  != null) )
 	{
 	var mail3 = jsonrpc.processes.updatePMWelcomeMail(jsData);
 	 alert("data updation status:"+mail3);
 	 }else{
 	 alert("Please insert some data");
 	 }
}


function textCounter(field,field2,maxlimit)
{
 var countfield = document.getElementById(field2);
 if ( field.value.length > maxlimit ) {
  field.value = field.value.substring( 0, maxlimit );
  return false;
 } else {
  countfield.value = maxlimit - field.value.length;
 }
}

 function ValidateTextFieldSpecialchar(fieldname,path,label){
 //alert("N");
	if(fieldname.value != "")
	{
	var fieldvalue=fieldname.value;
	
	
	var pattern = /[a-zA-Z0-9~`!@#$%^&*()_+-={}|:;<>,.?\/\n\t \x22 \x27 ]+$/ ;
	
	if ( !fieldvalue.match(pattern))
		{
				alert ("Special Characters are not allowed in " + label +"!!!");
		  		//fieldname.value = fieldname.value.replace(/[^\x20-\x7E\n\p{Sc}\n\r.]+/g, '');
		  		fieldname.focus();	
				return false;
		}
		
	}	
	return true;
	 
 }
 

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


</script>
</head>
<body onload="javascript:doLoad();" >



<html:form action="/reportsAction" method="post" enctype="multipart/form-data" styleId="searchForm" >
<bean:define id="formBean" name="reportsBean"></bean:define>
	<div><table width="100%"  border="0" cellspacing="0" cellpadding="0" >
  		<tr height="25">
  		<td background="gifs/bg-header-main.jpg" width="50%" align="left"><img src="gifs/logo-inner.jpg">&nbsp;</td>
		<td background="gifs/bg-header-main.jpg" width="50%" align="right">&nbsp;<font face="verdana" size="4" color="white"><b><i>iB2B-Integrated Order Entry Management System</i></b></font></td>
		</tr>
		</table></div>
	
	<div><img src="./gifs/top/home.gif" onClick="goToHome();"></img></div>
	<div class="head"> <span><b>Welcome Letter </b><p align="right"><input type="button" value="Update" onclick="submitData();"><input type="button" value="Cancel"	onclick="Execute();" /></p></span> </div>
	
<DIV style='width: 800px; word-wrap: break-word; margin-left:90px; margin-right:50px;'>
<fieldset style='padding-left:20px; padding-right:20px; background-color:#FFFFE0; border-color:#F00; border-style: solid;'>
<legend ></legend>
<TABLE width=100%><tr><td></td><td style='text-align: center; '> 
<font size="3"><B >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <U>Welcome Letter</U></B></font> </td><td align="right"><img src="./gifs/welcomeletterlogo.jpg"></td></tr><TR> </TR>
<TR> </TR><TR><TD style='align: left; '><B>Ref:</B>&nbsp;SystemGeneratedValue</TD><TD></TD>
<TD><p  style='text-align:right;'><B>Date:</B>&nbsp;SystemGeneratedValue</p></TD></TR>
</table>
<br>Dear Mr,Order Entry Contact<BR>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>

</tr>
			
<tr>
			<td width="40%" valign="top">
			


			<table border="0" cellspacing="0" cellpadding="1"
				style="margin-top:7px " width="50%">
				


				
				<tr>
					<td height="29" width="21%" valign="middle"></td>
					<td align="center" height="29" width="79%">
					<textarea id="data1" cols="100" rows="3"
						onkeyup="textCounter(this,'counter1',488); ValidateTextFieldSpecialchar(this,path,'data1');" onkeydown="countChars('data1','charcount1');" onmouseout="countChars('data1','charcount1');"   ></textarea>
						<input disabled  maxlength="4" size="4" value="488" id="counter1"></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="29" width="21%" valign="middle"></td>
					<td align="center" height="29" width="79%"><textarea id="data2" cols="100" rows="3"
						onkeyup="textCounter(this,'counter2',488); ValidateTextFieldSpecialchar(this,path,'data2');" onkeydown="countChars('data2','charcount1');" onmouseout="countChars('data2','charcount1');"   ></textarea>
						<input disabled  maxlength="4" size="4" value="488" id="counter2"></td>
				
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="29" width="21%" valign="middle"></td>
					<td align="center" height="29" width="79%"><textarea id="data3" cols="100" rows="3"
						onkeyup="textCounter(this,'counter3',488); ValidateTextFieldSpecialchar(this,path,'data3');" onkeydown="countChars('data3','charcount1');" onmouseout="countChars('data3','charcount1');"    ></textarea>
						<input disabled  maxlength="5" size="5" value="488" id="counter3"></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr><td height="29" width="21%" valign="middle"></td><td align="center" height="29" width="79%">
									<TABLE border="1" width="100%"><TR font-size: 15px bgcolor=#F7F7E7><TD>Name:</TD><TD>SystemGeneratedValue</TD></TR>
									<TR font-size: 15px bgcolor=#F7F7E7><TD width="30%">Address:</TD><TD width="70%">SystemGeneratedValue</TD></TR>
									<TR font-size: 15px bgcolor=#F7F7E7><TD width="30%">Email:</TD><TD width="70%">SystemGeneratedValue</TD></TR>
									<TR font-size: 15px bgcolor=#F7F7E7><TD width="30%">Contact:</TD><TD width="70%">SystemGeneratedValue</TD></TR>
									<TR font-size: 15px bgcolor=#F7F7E7><TD width="30%">FAX:</TD><TD width="70%">SystemGeneratedValue</TD></TR></TABLE></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="29" width="21%" valign="middle"></td>
					<td align="center" height="29" width="79%"><textarea id="data4" cols="100" rows="3"
						onkeyup="textCounter(this,'counter4',488); ValidateTextFieldSpecialchar(this,path,'data4')" onkeydown="countChars('data4','charcount1');" onmouseout="countChars('data4','charcount1');"   ></textarea>
						<input disabled  maxlength="5" size="5" value="488" id="counter4"></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr><td height="29" width="21%" valign="middle"></td><td align="center" height="29" width="79%">
								<TABLE border="1" width="100%">
									<TR font-size:16px bgcolor=#F7F7E7><TH>Escalation Levels</TH><TH>Name</TH><TH>Email</TH><TH>Contact No</TH></TR>
									<TR font-size: 15px bgcolor=#F7F7E7><TD width="25%">Escalation Level1</TD><TD width="25%">SystemGeneratedValue </TD><TD width="25%">SystemGeneratedValue</TD><TD width="25%">SystemGeneratedValue</TD></TR>
									<TR font-size: 15px bgcolor=#F7F7E7><TD width="25%">Escalation Level2</TD><TD width="25%">SystemGeneratedValue </TD><TD width="25%">SystemGeneratedValue</TD><TD width="25%">SystemGeneratedValue</TD></TR>
									<TR font-size: 15px bgcolor=#F7F7E7><TD width="25%">Escalation Level3</TD><TD width="25%">SystemGeneratedValue </TD><TD width="25%">SystemGeneratedValue</TD><TD width="25%">SystemGeneratedValue</TD></TR></TABLE>
						</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="29" width="21%" valign="middle"></td>
					<td align="center" height="29" width="79%"><textarea id="data5" cols="100" rows="3"
						onkeyup="textCounter(this,'counter5',488); ValidateTextFieldSpecialchar(this,path,'data5');" onkeydown="countChars('data5','charcount1');" onmouseout="countChars('data5','charcount1');"    ></textarea>
						<input disabled  maxlength="5" size="5" value="488" id="counter5"></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr><td height="29" width="21%" valign="middle"></td><td align="left" height="29" width="79%">Best Regards</td></tr>
				<tr><td height="29" width="21%" valign="middle"></td><td align="left" height="29" width="79%">Bharti Airtel Ltd.,</td></tr>
				<!-- <tr>
					<td width="10%"><input type="hidden" id="rowId2" /></td>
					<td width="30%" align="center">
					<td width="25%" align="right"><input type="button" value="Update" onclick="submitData();"></td>
					<td width="25%" align="left">	<input type="button" value="Cancel"	onclick="Execute();" /></td>

				</tr> -->

			</table>
			
			</td>
		</tr>
	</table>
</fieldset></DIV>
									
									
	


</html:form>
</BODY>


</html:html>
