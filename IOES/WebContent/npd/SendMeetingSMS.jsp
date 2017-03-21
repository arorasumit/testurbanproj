<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/ajax.js"></script>
<script type="text/javascript" src="js/context-menu.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<script language="javascript" src="js/utility.js"></script>
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

<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>


<script type="text/javascript"><!--
function loadOptionalList(objId)
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var mandatory = new Array(document.getElementById("mandatoryId").length);
	var counter=0;

		for(x=0;x<document.getElementById("mandatoryId").length;x++)
		{
				if(document.getElementById("mandatoryId").options[x].selected)
				{
				mandatory[counter]=document.getElementById("mandatoryId").options[x].value;
				counter++;
				}
		}
		
	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
    	//For Optional List
    	result = jsonrpc.processes.getUsersForOptionalList(mandatory,document.getElementById("productId").value);
    	    			
		var obj = document.getElementById(objId);
		
		for(i=obj.options.length-1;i>=0 ;i--)
		{
			obj.options[i] = null;
		}
		if( result !=null && result.list.length > 0)
		{
			//alert("List Exists");
			for(i=0;i < result.list.length;i++)
			{
				var name = result.list[i].empname;
				var id=result.list[i].npdempid;
				obj.options[i] = new Option(name,id);
				
			}//for
			

//			If()
		}//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}
function performAction(val)
{
	myform1=document.getElementById("searchForm");
	setFormBean(myform1);
	if(val=='sendSMS')
	{
		var myForm=document.getElementById('searchForm');
		if(checkForm()==false)
		{
			return false;
		}
		myForm.method.value='saveUpdate';
		//showLayer(); 
		myForm.submit();
	}
}
function checkForm()
{
	if(document.getElementById("productId").value==-1 || document.getElementById("productId").value=="")
	{
		alert("Please Select Product");
		return false;
	}
	if(document.getElementById("meetingId").value==-1 || document.getElementById("meetingId").value=="")
	{
		alert("Please Select Meeting");
		return false;
	}
	if(document.getElementById("smsMessage").value==null||trim(document.getElementById("smsMessage").value)=="")
	{
		alert("Please enter Message");
		return false;
	}
	if(ValidateTextField(document.getElementById("smsMessage"),'<%=request.getContextPath()%>',"Message")==false)
	{
		return false;
	}
	if(document.getElementById("smsMessage").value.length>255)
	{
		alert('Message Cannot Be Greater Than 255 characters');
		return false;
	}
	var selectedContacts=0;
	for(x=0;x<document.getElementById("smsContacts").length;x++)
	{
		if(document.getElementById("smsContacts").options[x].selected)
		{
			selectedContacts++;
		}
	}
		
	if(selectedContacts < 1)
	{
		alert("Please Select from Contact List");
		return false;
	}
	
	return true;	
}
function loadMeetingList()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
		var prodId=document.getElementById("productId").value;

	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
    	//For Optional List
    	result = jsonrpc.processes.getMeetingsOfProduct(prodId);
    	    			
		var obj = document.getElementById('meetingId');
		
		for(i=obj.options.length-1;i>0 ;i--)
		{
			obj.options[i] = null;
		}
		if( result !=null && result.list.length > 0)
		{
			//alert("List Exists");
			for(i=0;i < result.list.length;i++)
			{
				var name = result.list[i].subject;
				var id=result.list[i].meetingid;
				obj.options[i+1] = new Option(name,id);
				
			}//for
//			If()
		}//if result
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

function loadMeetingDetail()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
		var meetId=document.getElementById("meetingId").value;

	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
    	//For Optional List
    	result = jsonrpc.processes.getMeetingDetailById(meetId);
    	
    	var bean=result;
    	
    	if(bean!=null)
    	{
    			document.getElementById("locationId").value=bean.location.locationName;
    			document.getElementById("subject").value=bean.subject;    			
    			document.getElementById("startTime").value=bean.starttimeString;  
    			document.getElementById("endTime").value=bean.endtimeString;  
    			document.getElementById("date").value=bean.meetingdateString;      
    			
    			var obj = document.getElementById('smsContacts');
				var meettype=bean.tmMeetings.meetingtype;

				if(new String(meettype).toLowerCase()=="cft")
				{
					document.getElementById('meetingTypecft').checked=true;
				}			
				if(new String(meettype).toLowerCase()=="npsc")
				{
					document.getElementById('meetingTypenpsc').checked=true;				
				}			
				for(i=obj.options.length-1;i>0 ;i--)
				{
					obj.options[i] = null;
				}			

				if( bean.attendies.list !=null && bean.attendies.list.length > 0)
				{
					//alert("List Exists" + bean.attendies.length);
					for(i=0;i < bean.attendies.list.length;i++)
					{
						var name = bean.attendies.list[i].employee.empname;
						var id=bean.attendies.list[i].employee.mobileNo;
						obj.options[i] = new Option(name,id);
						
					}//for
		//			If()
				}			    			    			
    	}
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}


function loadMandatoryList()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var productIds = new Array(document.getElementById("productId").length);
	var counter=0;

		for(x=0;x<document.getElementById("productId").length;x++)
		{
				if(document.getElementById("productId").options[x].selected)
				{
				productIds[counter]=document.getElementById("productId").options[x].value;
				counter++;
				}
		}
		
	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
    	//For Optional List
    	result = jsonrpc.processes.getUsersForMandatoryList(productIds);
    	    			
		var obj = document.getElementById('mandatoryId');
		
		for(i=obj.options.length-1;i>=0 ;i--)
		{
			obj.options[i] = null;
		}
		if( result !=null && result.list.length > 0)
		{
			//alert("List Exists");
			for(i=0;i < result.list.length;i++)
			{
				var name = result.list[i].empname;
				var id=result.list[i].npdempid;
				obj.options[i] = new Option(name,id);
				
			}//for
			

//			If()
		}//if result
		
		
		loadOptionalList('optionalId');
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

function saveMeetingSchedule()
{
	var errorString="";
	var selectedMandatory=0;
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(document.getElementById("productId").value==-1 || document.getElementById("productId").value=="")
	{
		alert("Please Select Product");
		return false;
	}
	if(document.getElementById("locationId").value==-1 || document.getElementById("locationId").value=="")
	{
		alert("Please Select Location");
		return false;
	}
	if(document.getElementById("subject").value==null||trim(document.getElementById("subject").value)=="")
	{
		alert("Please enter Subject");
		return false;
	}
	if(ValidateTextField(myform.subject,'<%=request.getContextPath()%>',"Subject/Agenda")==false)
	{
		return false;
	}
	
	
	if(document.getElementById("date").value==null||document.getElementById("date").value=="")
	{
		alert("Please enter Date of Meeting");
		return false;
	}
	else
	{
		if(!checkPastDate(new Date(),document.getElementById("date").value))
		{
			alert("Please enter Current/Future Date in Date of Meeting");
			return false;
		}
	}
	if(document.getElementById("startTime").value==null||document.getElementById("startTime").value=="")
	{
		alert("Please enter Start Time");
		return false;
	}
	if(document.getElementById("endTime").value==null||document.getElementById("endTime").value=="")
	{
		alert("Please enter End Time");
		return false;
	}
	for(x=0;x<document.getElementById("mandatoryId").length;x++)
	{
		if(document.getElementById("mandatoryId").options[x].selected)
		{
			selectedMandatory++;
		}
	}
		
	if(selectedMandatory < 1)
	{
		alert("Please Select from Mandatory List");
		return false;
	}
	//Commented as not required as per CBR
	if(document.getElementById("attachment").value==null||document.getElementById("attachment").value=="")
	{
		
	}
	else if (isValidFileExtension(document.getElementById("attachment").value)==false) 
	{
		return false;                                    
	}
		 
	if(document.getElementById("attachment1").value==null||document.getElementById("attachment1").value=="")
	{
	
	}
	else if (isValidFileExtension(document.getElementById("attachment").value)==false) 
	{
		return false;                                    
	}
	 if(document.getElementById("attachment2").value==null||document.getElementById("attachment2").value=="")
	{
	
	}
	else if (isValidFileExtension(document.getElementById("attachment").value)==false) 
	{
		return false;                                    
	}
	showLayer();
	return true;
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function checkPastDate(today,date)
{	 var retvalue = new Boolean(true);
	var monthArray = new Array('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
	var fromDateArray;
	var fromDate;
	fromDateArray = date.split("-");
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

															
	if(fromDate-today<0)
	{
	retvalue = false;
	return retvalue;
	}
	else
	{
	return retvalue;
	}
}
 

--></script>

</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<!--<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>-->
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Send SMS For Meeting</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<html:form action="/SmsForMeeting" method="post" styleId="searchForm"
	enctype="multipart/form-data" onsubmit="return saveMeetingSchedule();">


	<table width="98%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr>

			<td width="70%">
			<input type="hidden" name="method">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">
				<tr>
					<th width="30%" scope="row">
					<div align="left">Select Product:<font color="#990033">*</font></div>
					</th>
					<td width="70%">
					<html:select property="productId" style="width:400px"
						onchange="javascript:loadMeetingList();">
						<html:option value="-1">Select</html:option>
						<logic:notEmpty name="meetingBean" property="productList">
							<html:optionsCollection property="productList" label="CSV_id_name"
								value="projectid" />
						</logic:notEmpty>
					</html:select>
					</td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Select Meeting:<font color="#990033">*</font></div>
					</th>
					<td width="70%">
					<html:select property="meetingId" style="width:400px"
						onchange="javascript:loadMeetingDetail();">
						<html:option value="-1">Select</html:option>
					</html:select>
					</td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Meeting Type <font color="#990033">*</font></div>
					</th>
					<td width="70%">
					
					<html:radio property="meetingType" value="CFT"  styleId="meetingTypecft" >CFT</html:radio>
					<html:radio property="meetingType" value="NPSC" styleId="meetingTypenpsc" >NPSC</html:radio></td>

				</tr>
								
				<tr>
					<th width="30%" scope="row">
					<div align="left">Location:<font color="#990033">*</font></div>
					</th>
					<td width="70%">
					<html:text property="locationId" style="width:400px" readonly="true">
					</html:text>
					</td>

				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Subject/Agenda <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="subject" readonly="true"/></td>

				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Date of Meeting<font color="#990033">*</font></div>
					</th>
					<td><html:text property="date" readonly="true" /> </td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Start Time <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="startTime" maxlength="8"
						readonly="true" /> </td>

				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">End Time <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="endTime" maxlength="8"
						readonly="true" /> </td>

				</tr>



				
				<tr>
					<th width="30%" scope="row">
					<div align="left">Message</div>
					</th>
					<td width="70%"><html:textarea property="smsMessage" rows="4" cols="10" />
					</td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Contacts <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="smsContacts"
						multiple="" style="width:120px">
					</html:select></td>
				</tr>



				<tr align="center">
					<th colspan="2" scope="row">
					<table width="50%" border="0">
						<tr>
							<td><span id="firstButton" class="buttonSmall"
											onClick="performAction('sendSMS')">Send</span> 
							</td>
						</tr>
					</table>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<logic:messagesPresent message="true">
		<table width="50%" align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font color="red" face="Verdana" size="2"><html:messages
					id="message" message="true">
					<li><bean:write name="message" /></li>
				</html:messages></font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</logic:messagesPresent>
	<logic:present name="meetingBean" property="meetingId">
		<table width="50%" align="center">
			
			<tr>
				<td><font color="red" face="Verdana" size="2">
					<bean:write name="meetingBean" property="meetingMsg" />
				</font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</logic:present>
	<!-- input color -->
	<script type="text/javascript">
<!--
 // initInputHighlightScript();
//-->
</script>
</html:form>
</div>
</BODY>


</html:html>
