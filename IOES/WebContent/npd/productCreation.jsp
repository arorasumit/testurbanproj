<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Anil Kumar		3-Oct-11	CSR00-05422     Use Ctrl + S for Save  -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<title>ChangeOrder Workflow</title>
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

<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>

<script type="text/javascript">
function goToHome()
{
	var myForm=document.getElementById('productform');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
// to check whether stage already exists in the database.

function checkForDuplicateStage()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var stageName =trim(document.getElementById("stage").value);
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	
		//For checking whether duplicate stage exists.
    	result = jsonrpc.processes.checkDuplicateStage(stageName,document.getElementById("npdCategoryId").value);
    
		if( result !=null)
		{
			if(result)
			{
			alert("This stage already exists");
			return false;
			}
		}//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}
// to check whether Task already exists in the database.

function checkForDuplicateTask()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var taskName =trim(document.getElementById("task").value);
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	
		//For checking whether duplicate stage exists.
    	result = jsonrpc.processes.checkDuplicateTask(taskName,document.getElementById("stageId").value);
    
		if( result !=null)
		{
			if(result)
			{
			alert("This Task already exists");
			return false;
			}
		}//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}
function getTemplateList(objId)
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
			var obj = document.getElementById(objId);
		if(document.getElementById("attachment").checked)
		{
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
    		
    		//For populating Template List
    		result = jsonrpc.processes.getTemplateDocList();
    				
			for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
			if( result !=null && result.list.length > 0)
			{
				for(i=0;i < result.list.length;i++)
				{
				var name = result.list[i].refdocname;
				var id=result.list[i].refdocid;
				obj.options[i+1] = new Option(name,id);
				
				}//for
			}//if result
		}
		else
		{
		for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
		}
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

function loadEmployeeDetails(empid)
{
	document.userNpdSpocs.action="./manageNpdSpocs.do?method=viewMasterPlan";
	document.userNpdSpocs.submit();  
	
}


function saveStage()
{
	var errorString="";
	if(document.getElementById("npdCategoryId").value==-1)
	{
	errorString+="Please Select Change Order Category"+"\n";
	}
	if(document.getElementById("stage").value==null||trim(document.getElementById("stage").value)=="")
	{
	errorString+="Please enter Stage"+"\n";
	}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	return checkForDuplicateStage();
	return true;
	}
}



function saveProject()
{
	myform=document.getElementById("productform");
	setFormBean(myform);
	var errorString="";

	if(document.getElementById("projectName").value==null||trim(document.getElementById("projectName").value)=="")
	{
	errorString+="Please enter Project Name"+"\n";
	}
	if(ValidateTextField(document.getElementById("projectName"),'<%=request.getContextPath()%>',"ProjectName")==false)
	{
		return false;
	}
	if(isNotSelected(document.getElementById('npdCategoryId').value))
	{
		errorString+="Please Select ChangeOrder Category"+"\n";
	
	}

	
	if(document.getElementById("productBrief").value==null||trim(document.getElementById("productBrief").value)=="")
	{
		errorString+="Please enter Product Brief     "+"\n";
	
	}
	if(ValidateTextField(document.getElementById("productBrief"),'<%=request.getContextPath()%>',"Product Brief")==false)
	{
		return false;
	}	
	else if(document.getElementById("productBrief").value.length>500)
	{
		errorString+="Product Brief Cannot Be Greater Than 500 characters   "+"\n";
	}
	if(errorString!="")
	{
		alert(errorString);
		return false;
	}
	else
	{
	//return checkForDuplicateTask();
	//special charactres check
	
		
	showLayer();
	return true;
	}
}
function checkStartDate(date)
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
	var today=new Date();
	if(fromDate.getYear()==today.getYear() && fromDate.getMonth()==today.getMonth() && fromDate.getDay()==today.getDay() )
	{
		return true;
	}
	else
	{
		if(fromDate-today<0)
			return true;
		else return false;
	}
	/*return 	fromDate-today;												
	if(fromDate-today<0)
	{
	retvalue = false;
	return retvalue;
	}
	else
	{
	return retvalue;
	}*/
}
function getTaskListForACategory(objId,var1)
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var counter=0;
		
	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
    	
		//For populating Stage and Task hyperlinks for updation.
    	result = jsonrpc.processes.getTaskListForACategory(null,var1); 
    				for(x=document.getElementById("stageAndTaskList").rows.length-1;x>=0;x--)
					{
						document.getElementById("stageAndTaskList").deleteRow(x);
					}
			if( result !=null && result.list.length > 0)
			{
				var stageid = '';
				
				for(i=0;i < result.list.length;i++)
				{
					if(stageid!=result.list[i].stage.stageid)
					{//creating Stage row
					var newRow = document.all("stageAndTaskList").insertRow();
				oCell = newRow.insertCell();
				oCell.innerHTML='<b>'+result.list[i].stage.stagename+'<b><br>';
								
					}
					//creating Task as hyperlink
				var newRow = document.all("stageAndTaskList").insertRow();
				oCell = newRow.insertCell();
				oCell.innerHTML='<td>&nbsp;&nbsp;&nbsp;<a href="" onclick="return getTaskDetails('+result.list[i].taskid+');">'
				+result.list[i].taskname+'</a>&nbsp;&nbsp;<input type="text" style="width:14px" value="'+result.list[i].planneddurationdays+'"/></td>';
				
				stageid =result.list[i].stage.stageid;					
				}//for
			}//if result
			else
			{
				var newRow = document.all("stageAndTaskList").insertRow();
				oCell = newRow.insertCell();
				oCell.innerHTML='<td><font color="red">No Task for ChangeOrder the selected Category</font></td>';
			}
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function showDetails(var1) {
	if(var1=='createProject')
	{
	document.productCreationBean.action="./createProduct.do?method=initProduct";
	document.productCreationBean.submit();  
	
	}
	if(var1=='searchProject')
	{

		myform=document.createElement("form");
		document.body.appendChild(myform);
		myform.action="<%=request.getContextPath()%>/createProduct.do?method=initSearchProduct";
		var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name","method");
		input.setAttribute("value", "initSearchProduct");
		myform.appendChild(input);
		myform.submit();
	
//	document.productCreationBean.action="./createProduct.do?method=initSearchProduct";
//	document.productCreationBean.projectId.value='';
//	document.productCreationBean.prdCategoryId.value='';
//	document.productCreationBean.npdCategoryId.value='';
//	document.productCreationBean.projectName.value='';		
//	document.productCreationBean.submit();  
	
	}
	if(var1=='updateProject')
	{
	document.productCreationBean.action="./createProduct.do?method=initProduct";
	document.productCreationBean.submit();  
	
	}

}
function attachProjectPlan(projectId)
{
	myform=document.createElement("form");
	document.body.appendChild(myform);
	myform.action="<%=request.getContextPath()%>/attachEditProjectPlan.do";
	
	myform.appendChild(getHiddenField("method","viewEditTasks"));
	myform.appendChild(getHiddenField("projectId",'<bean:write name="productCreationBean" property="projectId"/>'));
	myform.appendChild(getHiddenField("projectWorkflowId",'<bean:write name="productCreationBean" property="projectWorkflowId"/>'));				
	myform.appendChild(getHiddenField("attachMode",'<%=AppConstants.ATTACH_NEW%>'));					
	
	myform.submit();
}

function populateProductMaster()
{
				var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
				var result = jsonrpc.processes.populateProductHeader();
				
				for(j=0;j<result.list.length;j++)
				{
					var combo = document.getElementById('parentProduct');
					var option = document.createElement("option");
					option.text = result.list[j].productName;
					option.value = result.list[j].productId;
				try 
					{
						combo.add(option, null); //Standard
					}
					catch(error) 
					{
						combo.add(option); // IE only
					}
				}		
}

//start[001]
document.onkeydown=checkKeyPress;
function checkKeyPress()
{
	if (event.ctrlKey && event.keyCode == 83) {						
				event.keyCode=4;				
				saveProject();																
				var button = document.getElementById('saveproj');
				button.click();								
   }   
}
//end[001]
</script>

</head>
<body>
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	
</div>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
</table>
<div class="head"> <span>Change Workflow Creation</span> </div>
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
			<html:messages id="message" message="true"  property="detailsEntered">
				<b><bean:write name="message"/></b>
			</html:messages>
		</td>
	</tr>
</table>
<html:form action="/createProduct" method="post"
	onsubmit="return saveProject();" styleId="productform">
<logic:present name="enterProjectPlan">
	<table width="50%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr><td align="center">
		<html:messages id="message" message="true" property="detailsEntered">
			<b><bean:write name="message"/></b>
		</html:messages>
		</td></tr>
		<tr><td align="center"><div >
			Click to Attach &amp; Finalize Project Plan 
			<span id="attachProjectPlanButton" class="buttonLagre"
				onClick="attachProjectPlan('<bean:write name="productCreationBean" property="projectId"/>')">Attach</span>
				 </div>
		</td></tr>
	</table>
	
</logic:present>
<logic:notPresent name="enterProjectPlan">

	<table width="50%" border="2" class="tab2" cellpadding="0"
		cellspacing="0" align="center">
		
		<tr>
			<td width="100%" align="center">

			<table width="100%" border="0" class="tab2" cellpadding="0"
				cellspacing="0" align="center">
				<tr>
					<th width="30%" scope="row">
					<div align="left">Workflow Name <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="projectName"
						maxlength="100" style="width:300px" styleClass="inputBorder1"/></td>

				</tr>
				
													
				<tr>
					<th width="20%" scope="row">
					<div align="left">ChangeOrder Category <font color="#990033">*</font></div>
					</th>
					<td width="30%"><html:select property="npdCategoryId"
						style="width:300px" styleClass="inputBorder1">
						<html:option value="-1">Select</html:option>
						<logic:notEmpty name="productCreationBean"
							property="npdCategoryList">
							<html:optionsCollection property="npdCategoryList"
								label="npdcatdesc" value="npdcatid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>
				
				
				



				<tr>
					<th width="30%" scope="row">
					<div align="left">Workflow Brief <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:textarea property="productBrief"
						rows="6" cols="20" style="width:300px" styleClass="inputBorder1"/>
						
						<select  name='parentProduct' id='parentProduct'" class="inputBorder1"></select>
						</td>
				</tr>
				
				<tr>
					<td colspan="2">
						
					</td>
				</tr>

				<tr align="center">
					<th colspan="2" scope="row">
					 <!--<span
								id="saveButton" property="method" class="buttonLarge"
								onClick="return saveProject();">Save Project
					</span>-->					
					<table width="50%" border="0">
						<tr>
							<td><html:submit property="method" styleId="saveproj"
								onclick="return saveProject();">
								<bean:message key="submit.saveProject" bundle="ButtonResources" />
							</html:submit></td>

						</tr>
					</table>
				</tr>

			</table>
			</td>
		</tr>
	</table>

</logic:notPresent>
</html:form>
</div>

</BODY>
<script type="text/javascript">
	
populateProductMaster();
</script>
</html:html>
