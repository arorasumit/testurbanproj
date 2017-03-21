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
	var mandatory = new Array(document.getElementById("selectedMandatoryId").length);
	var counter=0;
	var optional = new Array(document.getElementById("selectedOptionalId").length);
		for(x=0;x<document.getElementById("selectedMandatoryId").length;x++)
		{
				//if(document.getElementById("selectedMandatoryId").options[x].selected)
				//{
				mandatory[counter]=document.getElementById("selectedMandatoryId").options[x].value;
				counter++;
				//}
		}
		
		for(x=0;x<document.getElementById("selectedOptionalId").length;x++)
		{
				//if(document.getElementById("selectedMandatoryId").options[x].selected)
				//{
				mandatory[counter]=document.getElementById("selectedOptionalId").options[x].value;
				counter++;
				//}
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

function loadMandatoryListChange()
{
	var objId='mandatoryId';
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var mandatory = new Array(document.getElementById("selectedMandatoryId").length);
	var counter=0;
	var optional = new Array(document.getElementById("selectedOptionalId").length);
	for(x=0;x<document.getElementById("selectedMandatoryId").length;x++)
	{
			//if(document.getElementById("selectedMandatoryId").options[x].selected)
			//{
			mandatory[counter]=document.getElementById("selectedMandatoryId").options[x].value;
			counter++;
			//}
	}
	
	for(x=0;x<document.getElementById("selectedOptionalId").length;x++)
	{
			//if(document.getElementById("selectedMandatoryId").options[x].selected)
			//{
			mandatory[counter]=document.getElementById("selectedOptionalId").options[x].value;
			counter++;
			//}
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
			//If()
		}//if result
		document.getElementById('selectedMandatoryId').value=null;
		loadOptionalList('optionalId');
	}
	catch(e) 
	{
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
	if(document.getElementById("selectedMandatoryId").length < 1)
	{
		alert("Please Select from Mandatory List");
		return false;
	}
	else
	{
		for(x=0;x<document.getElementById("selectedMandatoryId").length;x++)
		{
			document.getElementById("selectedMandatoryId").options[x].selected=true;
		}
	}
		
	if(document.getElementById("selectedOptionalId").length > 0)
	{
		for(x=0;x<document.getElementById("selectedOptionalId").length;x++)
		{
			document.getElementById("selectedOptionalId").options[x].selected=true;
		}
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
 
function addOption1()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	totaloption=document.getElementById("mandatoryId").length;
	for(i=0;i<totaloption;i++)
	{
		if(document.getElementById("mandatoryId").options[i].selected)
	 	{
			optionValue=document.getElementById("mandatoryId").options[i].value
			optionLabelTxt=document.getElementById("mandatoryId").options[i].text
			optionTxt=optionLabelTxt
			newoption= new Option(optionTxt,optionValue); 
			document.getElementById("selectedMandatoryId").options[document.getElementById("selectedMandatoryId").length]=newoption;
		}
	}
	for(i=totaloption-1;i>=0;i--)
	{
		if(document.getElementById("mandatoryId").options[i].selected)
 		{
 			document.getElementById("mandatoryId").options[i]=null
  		}
	}
}

function removeOption1()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	totaloption=document.getElementById("selectedMandatoryId").length;
	for(i=0;i<totaloption;i++)
	{
		if(document.getElementById("selectedMandatoryId").options[i].selected)
		{
			optionValue=(document.getElementById("selectedMandatoryId").options[i].value).split("~~~+++")[1];
			optionLabelTxt=document.getElementById("selectedMandatoryId").options[i].text
			optionLabelTxt=optionLabelTxt.substring(optionLabelTxt.indexOf("-")+1,  optionLabelTxt.length)
			optionTxt=optionLabelTxt //(document.getElementById("optionalId").options[i].value).split("~~~+++")[2]
			newoption= new Option(optionTxt,optionValue);
			document.getElementById("mandatoryId").options[document.getElementById("mandatoryId").length]=newoption;
		}
	}
	
	for(i=totaloption-1;i>=0;i--)
	{
		if(document.getElementById("selectedMandatoryId").options[i].selected)
		{
			document.getElementById("selectedMandatoryId").options[i]=null
		}
	}
}

function addOption2()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	totaloption=document.getElementById("optionalId").length;
	for(i=0;i<totaloption;i++)
	{
		if(document.getElementById("optionalId").options[i].selected)
	 	{
			optionValue=document.getElementById("optionalId").options[i].value
			optionLabelTxt=document.getElementById("optionalId").options[i].text
			optionTxt=optionLabelTxt
			newoption= new Option(optionTxt,optionValue); 
			document.getElementById("selectedOptionalId").options[document.getElementById("selectedOptionalId").length]=newoption;
		}
	}
	for(i=totaloption-1;i>=0;i--)
	{
		if(document.getElementById("optionalId").options[i].selected)
 		{
 			document.getElementById("optionalId").options[i]=null
  		}
	}
}

function removeOption2()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	totaloption=document.getElementById("selectedOptionalId").length;
	for(i=0;i<totaloption;i++)
	{
		if(document.getElementById("selectedOptionalId").options[i].selected)
		{
			optionValue=(document.getElementById("selectedOptionalId").options[i].value).split("~~~+++")[1];
			optionLabelTxt=document.getElementById("selectedOptionalId").options[i].text
			optionLabelTxt=optionLabelTxt.substring(optionLabelTxt.indexOf("-")+1,  optionLabelTxt.length)
			optionTxt=optionLabelTxt //(document.getElementById("optionalId").options[i].value).split("~~~+++")[2]
			newoption= new Option(optionTxt,optionValue);
			document.getElementById("optionalId").options[document.getElementById("optionalId").length]=newoption;
		}
	}
	
	for(i=totaloption-1;i>=0;i--)
	{
		if(document.getElementById("selectedOptionalId").options[i].selected)
		{
			document.getElementById("selectedOptionalId").options[i]=null
		}
	}
}

function removeMandataryOptionlist()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	totalMandatory=document.getElementById("selectedMandatoryId").length;
	totaloption=document.getElementById("selectedOptionalId").length;
	for(i=totalMandatory-1;i>=0;i--)
	{
		document.getElementById("selectedMandatoryId").options[i]=null
	}
	for(i=totaloption-1;i>=0;i--)
	{
		document.getElementById("selectedOptionalId").options[i]=null
	}
}
--></script>

</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
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
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Schedule
				Meeting For CFT & NPSC</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<html:form action="/manageMeetings" method="post" styleId="searchForm"
	enctype="multipart/form-data" onsubmit="return saveMeetingSchedule();">

	<table width="98%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr>

			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">

				<tr>
					<th width="30%" scope="row">
					<div align="left">Meeting Type <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:radio property="meetingType" value="CFT">CFT</html:radio>
					<html:radio property="meetingType" value="NPSC">NPSC</html:radio></td>

				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Product:<font color="#990033">*</font></div>
					</th>
					<td width="70%">
					<html:select property="productId" style="width:400px"
						onchange="javascript:loadMandatoryList();removeMandataryOptionlist();">
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
					<div align="left">Location:<font color="#990033">*</font></div>
					</th>
					<td width="70%">
					<html:select property="locationId" style="width:400px">
						<html:option value="-1">Select</html:option>
						<logic:notEmpty name="meetingBean" property="locationList">
							<html:optionsCollection property="locationList" label="locationName"
								value="locationId" />
						</logic:notEmpty>
					</html:select>
					</td>

				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Subject/Agenda <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="subject" maxlength="100" /></td>

				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Date of Meeting<font color="#990033">*</font></div>
					</th>
					<td><html:text property="date" readonly="true" /> <img
						src="npd/Images/cal.gif" width="16" height="16" border="0"
						alt="Pick a date" onclick="scwShow(scwID('date'),event);"></td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Start Time <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="startTime" maxlength="8" size="60"
						readonly="true" /> <IMG SRC="Images/timepicker.gif" BORDER="0"
						ALT="Pick a Time!" ONCLICK="selectTime(this,startTime)"
						STYLE="cursor:hand"></td>

				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">End Time <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="endTime" maxlength="8" size="60"
						readonly="true" /> <IMG SRC="Images/timepicker.gif" BORDER="0"
						ALT="Pick a Time!" ONCLICK="selectTime(this,endTime)"
						STYLE="cursor:hand"></td>

				</tr>



				<tr>
					<th width="30%" scope="row">
					<div align="left">MandatoryList <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="mandatoryId"
						multiple="" style="width:120px">
						<logic:notEmpty name="meetingBean" property="mandatoryList">
							<html:optionsCollection property="mandatoryList" label="empname"
								value="npdempid" />
						</logic:notEmpty>
					</html:select>
					<input type=button value="&gt;&gt;&gt;" onclick="addOption1();loadOptionalList('optionalId');">
					<input type=button value="&lt;&lt;&lt;" onclick="removeOption1();" height=25 border=0>
					<html:select property="selectedMandatoryId" multiple="" style="width:120px">
						<logic:notEmpty name="meetingBean" property="mandatoryList">
							<html:optionsCollection property="mandatoryList" label="empname"
								value="npdempid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">OptionalList</div>
					</th>
					<td width="70%"><html:select property="optionalId" multiple=""
						style="width:120px">
						<logic:notEmpty name="meetingBean" property="optionalList">
							<html:optionsCollection property="optionalList" label="empname"
								value="npdempid" />
						</logic:notEmpty>
					</html:select>
					<input type=button value="&gt;&gt;&gt;" onclick="addOption2();loadMandatoryListChange()">
					<input type=button value="&lt;&lt;&lt;" onclick="removeOption2();" height=25 border=0>
					<html:select property="selectedOptionalId" multiple="" style="width:120px">
						<logic:notEmpty name="meetingBean" property="optionalList">
							<html:optionsCollection property="optionalList" label="empname"
								value="npdempid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Attachment 1<font color="#990033"></font></div>
					</th>
					<td width="70%"><html:file property="attachment" size="20"
						onkeydown='this.blur()'></html:file></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Attachment 2<font color="#990033"></font></div>
					</th>
					<td width="70%"><html:file property="attachment1" size="20"
						onkeydown='this.blur()'></html:file></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Attachment 3<font color="#990033"></font></div>
					</th>
					<td width="70%"><html:file property="attachment2" size="20"
						onkeydown='this.blur()'></html:file></td>
				</tr>



				<tr align="center">
					<th colspan="2" scope="row">
					<table width="50%" border="0">
						<tr>
							<td><html:submit property="method">
								<bean:message key="submit.save" bundle="ButtonResources" />
							</html:submit></td>

						</tr>
					</table>
				</tr>
			</table>
			</td>
		</tr>
	</table>
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
