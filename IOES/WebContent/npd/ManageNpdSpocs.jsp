<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
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
<script type="text/javascript" src="js/inputColor.js"></script>

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
<script type="text/javascript">
function loadLevelList(objId,roleLevel1)
{
	var appContextPath = '<%=request.getContextPath()%>';
	var obj = document.getElementById(objId);
	try
	{	/*
	var role = new Array(document.getElementById("roleId").length);
	var counter=0;

		for(x=0;x<document.getElementById("roleId").length;x++)
		{
				if(document.getElementById("roleId").options[x].selected)
				{
				role[counter]=document.getElementById("roleId").options[x].value;
				counter++;
				}
		}
			getUnMappedRoles();
	var userReplacedRoleId = document.getElementById("userReplacedRoleIds").value;

	if(document.getElementById("employeeId").value!=-1&&userReplacedRoleId=="")
	{
	document.getElementById("buttonClicked").value="S";
	}
	
		
	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
    	//For populating Level 1 List
    	result = jsonrpc.processes.getRolesForEscalation(role,roleLevel1);
    			
		
		
		var selectedVal=obj.value;
		
		for(i=obj.options.length-1;i>=0 ;i--)
		{
			obj.options[i] = null;
		}
		obj.options[0] = new Option("Select", "-1");
		if( result !=null && result.list.length > 0)
		{
			//alert("List Exists");
			for(i=0;i < result.list.length;i++)
			{
				var name = result.list[i].rolename;
				var id=result.list[i].roleid;
				obj.options[i+1] = new Option(name,id);
				
			}//for
		}//if result
		
	    	obj.value=selectedVal;//
	    	if(obj.value=="")
	    	{
	    		obj.value=document.forms[0].originalLevel1Id.value;//
	    		if(obj.value=="")
	    		{
	    		obj.value="-1";
	    		}
	    	}*/
	    	//load level1 employee list
			//fillEmployees(obj.value,'level1EmployeeId');
	    	//loadLevel2List('level2Id','');

    	
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

function loadLevel2List(objId,roleLevel1)
{
	var appContextPath = '<%=request.getContextPath()%>';
	var obj = document.getElementById(objId);
	try
	{	/*
	var role = new Array(document.getElementById("roleId").length);
	var counter=0;

		for(x=0;x<document.getElementById("roleId").length;x++)
		{
				if(document.getElementById("roleId").options[x].selected)
				{
				role[counter]=document.getElementById("roleId").options[x].value;
				counter++;
				}
		}
			getUnMappedRoles();
	var userReplacedRoleId = document.getElementById("userReplacedRoleIds").value;

	if(document.getElementById("employeeId").value!=-1&&userReplacedRoleId=="")
	{
	document.getElementById("buttonClicked").value="S";
	}
	
		
	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
    	
    	//For populating Level 2 List
       	roleLevel1= document.getElementById("level1Id").value;
    	result = jsonrpc.processes.getRolesForEscalation(role,roleLevel1);
    	
    			

		
		var selectedVal=obj.value;
		
		for(i=obj.options.length-1;i>=0 ;i--)
		{
			obj.options[i] = null;
		}
		obj.options[0] = new Option("Select", "-1");
		if( result !=null && result.list.length > 0)
		{
			//alert("List Exists");
			for(i=0;i < result.list.length;i++)
			{
				var name = result.list[i].rolename;
				var id=result.list[i].roleid;
				obj.options[i+1] = new Option(name,id);
				
			}//for
		}//if result
		
		
    	
  	    	obj.value=selectedVal;//
  	    	if(obj.value=="")
	    	{
	    		obj.value=document.forms[0].originalLevel2Id.value;//
	    		if(obj.value=="")
	    		{
	    			obj.value="-1";
	    		}
	    	}*/
	    	//fillEmployees(obj.value,'level2EmployeeId');
	    
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}
function fillEmployees(roleId,objid)
{
	var appContextPath = '<%=request.getContextPath()%>';

	
	try
	{	
		var obj = document.getElementById(objid);
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
			result = jsonrpc.processes.getUsersForARoleID_NpdSpocs(roleId,document.getElementById('npdempid').value);
			var prevValue=obj.value;
			for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
			if( result !=null && result.list.length > 0)
			{
				for(i=0;i < result.list.length;i++)
				{
				var name = result.list[i].empname;
				var id=result.list[i].npdempid;
				obj.options[i+1] = new Option(name,id);
				
				}//for
			}//if result
			obj.value=prevValue;
			if(obj.value=="")
			{
				obj.value="-1";
			}
	}
	catch(e) {
		//alert(e);
		return false;
	}
}
function loadEmployeeDetails(empid)
{
	document.userNpdSpocs.action="<c:out value='${sessionScope.MenuContextPath}' />/manageNpdSpocs.do?method=viewNpdSpocs";
	showLayer();
	document.userNpdSpocs.submit();  
}


function saveRoleandEscalationLevel()
{
	//searchReplacement();
	try
	{
	var errorString="";
	var selectedRole=0;
	if(document.getElementById("employeeId").value==-1)
	{
		errorString+="Please Select Employee"+"\n";
	}
	/*for(x=0;x<document.getElementById("roleId").length;x++)
	{
		if(document.getElementById("roleId").options[x].selected)
		{
			selectedRole++;			
		}
	}

	if(selectedRole<1)
	{
		errorString+="Please Select atleast 1 Role \n";
	}
	else if(selectedRole>document.getElementById("roleId").length-2)
	{
		errorString+="Please Select upto "+(document.getElementById("roleId").length-2)+" Roles \n";
	}*/

	if(document.getElementById('escalation').checked==false)
	{
		if(document.getElementById("level1Id").value==-1)
		{
			errorString+="Please Select Level 1"+"\n";
		}
		
		if(document.getElementById("level2Id").value==-1)
		{
			errorString+="Please Select Level 2"+"\n";
		}
		
		if(document.getElementById("level1EmployeeId").value==-1)
		{
			errorString+="Please Select Level 1 Employee"+"\n";
		}
		
		if(document.getElementById("level2EmployeeId").value==-1)
		{
			errorString+="Please Select Level 2 Employee"+"\n";
		}
	}
	
	getUnMappedRoles();
	var userReplacedRoleId = document.getElementById("userReplacedRoleIds").value;
	var selectedUsers='';
	var previous = document.getElementById("previousRoleId").value

	if(document.getElementById("employeeId").value!=-1&&userReplacedRoleId!="")
	{
		var unmappedRoleIDs = new Array();
		unmappedRoleIDs = userReplacedRoleId.split(',');
		if(userReplacedRoleId!=""&&document.getElementById("buttonClicked").value!="R")
		{
			alert('Please Replace Employees For Roles');
			return false;	
		}
		
		var appContextPath = '<%=request.getContextPath()%>';
		jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
		
		for(x=0;x<unmappedRoleIDs.length;x++)
		{
			result = jsonrpc.processes.getUsersForARoleID(unmappedRoleIDs[x]); 
			if(result !=null && result.list.length > 0)
			{
				var roleName = result.list[0].tmRoles.rolename;
				selectedUsers=selectedUsers+document.getElementById(roleName).value+",";
				if(document.getElementById(roleName).value==-1)
				{
					errorString+="Please Select "+roleName+"\n";
				}
			}
		}		
	}	
	if(errorString!="")
	{
		alert(errorString);
		return false;
	}
	else
	{
	if(selectedUsers.length>0)
	{
		selectedUsers =selectedUsers.substring(0,selectedUsers.length-1);
	}	
	if(document.getElementById("selectedRoleId").length > 0)
	{
		for(x=0;x<document.getElementById("selectedRoleId").length;x++)
		{
			document.getElementById("selectedRoleId").options[x].selected=true;
		}
	}
		document.userNpdSpocs.action="<c:out value='${sessionScope.MenuContextPath}' />/manageNpdSpocs.do?method=Save&selectedUsers="+selectedUsers;
		showLayer();
		return true;
		//document.userNpdSpocs.submit();
	}
	}catch(e)
	{
		//throw(e);
		return false;
	}
}

function getUnMappedRoles()
{
	var selectedRole=0;
	var currentRoleIds = new Array();
	for(x=0;x<document.getElementById("selectedRoleId").length;x++)
		{
				//if(document.getElementById("roleId").options[x].selected)
				//{
				currentRoleIds[selectedRole] = document.getElementById("selectedRoleId").options[x].value;
				selectedRole++;			
				//}
		}
//alert(1);
	var previous = document.getElementById("previousRoleId").value;
	if(previous!="")
	{//alert(2);
		var previousRoleIds = new Array();
		var previousUnmappedRoleIds = new Array();
		previousRoleIds = previous.split(',');
		var counter=0;
		var unMappedRoles=0;
		for(x=0;x<previousRoleIds.length;x++)
			{
				for(y=0;y<currentRoleIds.length;y++)
				{
					if(previousRoleIds[x]==currentRoleIds[y])
						{
						counter++;
						}
				}
				if(counter==0)
				{
				previousUnmappedRoleIds[unMappedRoles]=previousRoleIds[x];
				unMappedRoles++;
				}
			counter =0;
	
			}
		
				
				if(previousUnmappedRoleIds.length>=0)
				{
				var userReplacedRoleIds = '';
				for(z=0;z<previousUnmappedRoleIds.length;z++)
					{
						userReplacedRoleIds = userReplacedRoleIds+previousUnmappedRoleIds[z]+",";
										
					}
					userReplacedRoleIds=userReplacedRoleIds.substring(0,userReplacedRoleIds.length-1);
					document.getElementById("userReplacedRoleIds").value = userReplacedRoleIds;
					//alert("3:"+userReplacedRoleIds);
					return false;
				}
				//alert("4:"+document.getElementById("userReplacedRoleIds").value);
			
	}	
	return false;
}
function addOption1()
{
	myform=document.getElementById('searchForm');

	totaloption=document.getElementById("unselectedRoleId").length;
	for(i=0;i<totaloption;i++)
	{
		if(document.getElementById("unselectedRoleId").options[i].selected)
	 	{
			optionValue=document.getElementById("unselectedRoleId").options[i].value
			optionLabelTxt=document.getElementById("unselectedRoleId").options[i].text
			optionTxt=optionLabelTxt
			newoption= new Option(optionTxt,optionValue); 
			document.getElementById("selectedRoleId").options[document.getElementById("selectedRoleId").length]=newoption;
		}
	}
	for(i=totaloption-1;i>=0;i--)
	{
		if(document.getElementById("unselectedRoleId").options[i].selected)
 		{
 			document.getElementById("unselectedRoleId").options[i]=null
  		}
	}
}

function removeOption1()
{
	myform=document.getElementById('searchForm');
	
	totaloption=document.getElementById("selectedRoleId").length;
	for(i=0;i<totaloption;i++)
	{
		if(document.getElementById("selectedRoleId").options[i].selected)
		{
			optionValue=(document.getElementById("selectedRoleId").options[i].value);//.split("~~~+++")[1];
			optionLabelTxt=document.getElementById("selectedRoleId").options[i].text;
			//optionLabelTxt=optionLabelTxt.substring(optionLabelTxt.indexOf("-")+1,  optionLabelTxt.length)
			optionTxt=optionLabelTxt; //(document.getElementById("optionalId").options[i].value).split("~~~+++")[2]
			newoption= new Option(optionTxt,optionValue);
			document.getElementById("unselectedRoleId").options[document.getElementById("unselectedRoleId").length]=newoption;
		}
	}
	
	for(i=totaloption-1;i>=0;i--)
	{
		if(document.getElementById("selectedRoleId").options[i].selected)
		{
			document.getElementById("selectedRoleId").options[i]=null
		}
	}
}


function searchReplacement()
{
	try
	{
	var errorString="";
	var selectedRole=0;
	if(document.getElementById("employeeId").value==-1)
	{
		errorString+="Please Select Employee"+"\n";
	}
	
	var currentRoleIds = new Array();
	for(x=0;x<document.getElementById("selectedRoleId").length;x++)
		{
				//if(document.getElementById("selectedRoleId").options[x].selected)
				{
				currentRoleIds[selectedRole] = document.getElementById("selectedRoleId").options[x].value;
				selectedRole++;			
				}
		}

	/*if(selectedRole<1)
		{
		errorString+="Please Select atleast 1 Role \n";
		}
		else if(selectedRole>document.getElementById("roleId").length-2)
		{
		errorString+="Please Select upto "+(document.getElementById("roleId").length-2)+" Roles \n";
		}*/
	document.getElementById("buttonClicked").value="R";
	var previous = document.getElementById("previousRoleId").value;
	if(previous!="")
	{
		var previousRoleIds = new Array();
		var previousUnmappedRoleIds = new Array();
		previousRoleIds = previous.split(',');
		var counter=0;
		var unMappedRoles=0;
		for(x=0;x<previousRoleIds.length;x++)
			{
				for(y=0;y<currentRoleIds.length;y++)
				{
					if(previousRoleIds[x]==currentRoleIds[y])
						{
						counter++;
						}
				}
				if(counter==0)
				{
				previousUnmappedRoleIds[unMappedRoles]=previousRoleIds[x];
				unMappedRoles++;
				}
			counter =0;
	
			}
		
		var appContextPath = '<%=request.getContextPath()%>';
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
			
				for(x=document.getElementById("replacementRolesList").rows.length-1;x>=0;x--)
					{
						document.getElementById("replacementRolesList").deleteRow(x);
					}
				if(previousUnmappedRoleIds.length>0)
				{
				var userReplacedRoleIds = '';
				var flag=0;
				for(z=0;z<previousUnmappedRoleIds.length;z++)
					{
						userReplacedRoleIds = userReplacedRoleIds+previousUnmappedRoleIds[z]+",";
						result = jsonrpc.processes.getUsersForARoleID(previousUnmappedRoleIds[z]); 
							
							if(result !=null && result.list.length > 0)
							{
								if(flag==0)
								{
								var newRow = document.all("replacementRolesList").insertRow();
								oCell = newRow.insertCell();
								oCell.innerHTML='<TH>&nbsp;</TH> ';


								var newRow = document.all("replacementRolesList").insertRow();
								oCell = newRow.insertCell();
								oCell.innerHTML='<TH>Select Employee for Replacement for tasks :</TH>';
								flag=1;
								}
								
								var roleName = result.list[0].tmRoles.rolename;
								var newRow = document.all("replacementRolesList").insertRow();
								oCell = newRow.insertCell();
								
								var	makingHtml ='<th width="100%" scope="row" ><div align="left"><b>'+roleName+'</b><font color="#990033">*</font></div></th><td >'
								+'<select id="'+roleName+'"style="width:140px"><option value="-1">Select</option>'
							for(i=0;i < result.list.length;i++)
								{		
								var employeeName=result.list[i].tmEmployee.empname;
								var employeeId=result.list[i].tmEmployee.npdempid;
									if(document.getElementById("npdempid").value!=employeeId)	
									{
										makingHtml=makingHtml+'<option value="'+employeeId+'">'+employeeName+'</option>'
									}					
											
								}
								oCell.innerHTML=makingHtml+	'</select></td>';
							}
							else
							{
							result1 = jsonrpc.processes.getRoleUsingRoleId(previousUnmappedRoleIds[z]);
								var roleName = result1.list[0].tmRoles.rolename;
								var newRow = document.all("replacementRolesList").insertRow();
								oCell = newRow.insertCell();
								var	makingHtml ='<th width="30%" scope="row"><div align="left"><b>'+roleName+'</b><font color="#990033">*</font></div></th><td width="70%">'
								+'<select id="'+roleName+'"style="width:140px"><option value="-1">Select</option>'
								oCell.innerHTML=makingHtml+	'</select></td>';
							}
							
					}
					userReplacedRoleIds=userReplacedRoleIds.substring(0,userReplacedRoleIds.length-1);
					document.getElementById("userReplacedRoleIds").value = userReplacedRoleIds;
					return false;
				}
				else
				{
					for(x=document.getElementById("replacementRolesList").rows.length-1;x>=0;x--)
					{
						document.getElementById("replacementRolesList").deleteRow(x);
					}
					var newRow = document.all("replacementRolesList").insertRow();
					oCell = newRow.insertCell();
					oCell.innerHTML='<td><font color="red"></font></td>';
					return false;
				}
				
		return false;	
	}	
	return false;
	}catch(e)
	{
		//alert(e);
		return false;
	}
}

function toggleEscalations(elem)
{
	if(elem.checked==true)
	{
		document.getElementById('level1Id').disabled=true;
		document.getElementById('level2Id').disabled=true;
		document.getElementById('level1EmployeeId').disabled=true;
		document.getElementById('level2EmployeeId').disabled=true;						
	}
	else
	{
		document.getElementById('level1Id').disabled=false;
		document.getElementById('level2Id').disabled=false;
		document.getElementById('level1EmployeeId').disabled=false;
		document.getElementById('level2EmployeeId').disabled=false;						
	}
}
function onBodyLoad()
{
	toggleEscalations(document.getElementById('escalation'));
}
</script>

</head>
<body onload="javascript:document.body.click();onBodyLoad();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait While Data Loads..</b></font>
    <br /><br /><br />
</div>
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
<html:form action="/manageNpdSpocs" method="post" styleId="searchForm">

<html:hidden property="typeOfEmployee"/>
<html:hidden property="npdempid" styleId="npdempid"/>
	<br>
	<table width="98%" border="0" cellpadding="0" cellspacing="0"
		class="border" align="center">
		<tr valign="middle" id="projectPlan">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Defining
					Roles and Escalation</td>
					<td><img src="npd/Images/tabRight.gif"></td>
					<td width="70%"><img src="npd/Images/zero.gif" width="15"
						height="1"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<table width="98%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr>

			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">
				<tr>
					<th width="30%" scope="row">
					<div align="left">Employee <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="employeeId"
						style="width:250px"
						onchange="javascript:loadEmployeeDetails(this.value);">
						<html:option value="-1">Select</html:option>
						<!--<logic:notEmpty name="userNpdSpocs" property="employeeList">
							<html:optionsCollection property="employeeList" label="empname"
								value="npdempid" />
						</logic:notEmpty>
					-->
					<logic:iterate id="assumptionBean_id" name="userNpdSpocs" property="employeeList" indexId="index1">
						<html:option value="${assumptionBean_id.hrms_employee_number}">
							<c:out 	value="${assumptionBean_id.hrms_full_name}(${assumptionBean_id.hrms_email})" />
						</html:option>
					</logic:iterate>
					</html:select></td>
				</tr>
				<html:hidden property="previousRoleId" />
				<html:hidden property="userReplacedRoleIds" />
				<html:hidden property="buttonClicked" />

				<tr>
					<th valign="top" scope="row">
					<div align="left">Roles <font color="#990033">*</font></div>
					</th>
					<td>
					<table><tr>
					<td>
					<html:select property="unselectedRoleId" multiple=""
						style="width:250px;height:150px">
						<logic:notEmpty name="userNpdSpocs" property="unselectedRoles">
							<html:optionsCollection property="unselectedRoles" label="rolename"
								value="roleid" />
						</logic:notEmpty>
					</html:select>
					</td>
					<td style="vertical-align: middle">
					
					<input type="button" value="&gt;&gt;&gt;" onclick="addOption1();searchReplacement();"/><BR><BR>
					<input type="button" value="&lt;&lt;&lt;" onclick="removeOption1();searchReplacement();" />
					</td>
					<td>
					<html:select property="selectedRoleId" multiple="" style="width:250px;height:150px">
						<logic:notEmpty name="userNpdSpocs" property="selectedRoles">
							<html:optionsCollection property="selectedRoles" label="rolename"
								value="roleid" />
						</logic:notEmpty>
					</html:select>
					</td>
					
					</tr></table>
					
					
					
					</td>
				</tr>
				<tr><td colspan="2"> &nbsp;<html:checkbox property="escalation" value="1" 
										styleId="escalation" onclick="javascript:toggleEscalations(this)"
										onkeypress="javascript:toggleEscalations(this)"/>&nbsp;
										Make Escalation Level Non-Mandatory for the selected Employee
</td></tr>
				<tr>
					<td colspan="2">
						<table width="98%" border="0" class="tabledata" cellpadding="0"
												cellspacing="0" align="center">
							<tr>
								<th valign="top" scope="row">
								<div align="left">Level 1 <font color="#990033">*</font></div>
								</th>
								<td><html:select property="level1Id" style="width:250px"
									onchange="javascript:fillEmployees(this.value,'level1EmployeeId')">
									<html:option value="-1">Select</html:option>
									<logic:notEmpty name="userNpdSpocs" property="level1List">
										<html:optionsCollection property="level1List" label="rolename"
											value="roleid" />
									</logic:notEmpty>
								</html:select>
								<input type="hidden" name="originalLevel1Id" value="<bean:write name='userNpdSpocs' property='level1Id' />">
								</td>
								<th valign="top" scope="row">
								<div align="left">Employee<font color="#990033">*</font></div>
								</th>
								<td><html:select property="level1EmployeeId" style="width:250px">
									<html:option value="-1">Select</html:option>
									<logic:notEmpty name="userNpdSpocs" property="level1EmployeeList">
										<html:optionsCollection property="level1EmployeeList" label="empname"
											value="npdempid" />
									</logic:notEmpty>
								</html:select>
								<input type="hidden" name="originalLevel1EmployeeId" value="<bean:write name='userNpdSpocs' property='level1EmployeeId' />">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="98%" border="0" class="tabledata" cellpadding="0"
												cellspacing="0" align="center">
							<tr>
								<th valign="top" scope="row">
								<div align="left">Level 2 <font color="#990033">*</font></div>
								</th>
								<td><html:select property="level2Id" style="width:250px" onchange="fillEmployees(this.value,'level2EmployeeId')">
									<html:option value="-1">Select</html:option>
									<logic:notEmpty name="userNpdSpocs" property="level1List">
										<html:optionsCollection property="level1List" label="rolename"
											value="roleid" />
									</logic:notEmpty>
								</html:select>
								<input type="hidden" name="originalLevel2Id" value="<bean:write name='userNpdSpocs' property='level2Id' />">
								</td>
								<th valign="top" scope="row">
								<div align="left">Employee<font color="#990033">*</font></div>
								</th>
								<td><html:select property="level2EmployeeId" style="width:250px">
									<html:option value="-1">Select</html:option>
									<logic:notEmpty name="userNpdSpocs" property="level2EmployeeList">
										<html:optionsCollection property="level2EmployeeList" label="empname"
											value="npdempid" />
									</logic:notEmpty>
								</html:select>
								<input type="hidden" name="originalLevel2EmployeeId" value="<bean:write name='userNpdSpocs' property='level2EmployeeId' />">
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tbody id='replacementRolesList' style="width: 100%" >
				
				</tbody>


				<tr align="center">
					<th colspan="2" scope="row">
					<table width="100%" border="0">

						<tr>
							<td align="center"><html:submit property="method"
								onclick="javascript:return saveRoleandEscalationLevel();">
								<bean:message key="submit.saveNPDSpocs" bundle="ButtonResources" />
							</html:submit></td>


						</tr>
					</table>
				</tr>


				<br>

				<logic:messagesPresent>
					<table width="50%" align="center">
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><font color="red" face="Verdana" size="2"><html:messages
								id="message">
								<li><bean:write name="message" /></li>
							</html:messages></font></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
				</logic:messagesPresent>
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
			</table>
			</td>
		</tr>
	</table>
	<!-- input color -->
	<script type="text/javascript">
 // initInputHighlightScript();
</script>

</html:form>
</div>
</BODY>
</html:html>
