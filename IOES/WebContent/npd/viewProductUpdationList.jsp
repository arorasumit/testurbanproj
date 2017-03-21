<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.ProductCreationBean"%>
<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
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
function searchProject()
{
	var errorString="";
	if(document.getElementById("npdCategoryId").value==-1&&document.getElementById("prdCategoryId").value==-1&&document.getElementById("projectId").value==-1&&(document.getElementById("projectName").value==null||trim(document.getElementById("projectName").value)==""))
	{
	errorString+="Please enter atleast 1 search criteria"+"\n";
	}
	
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	document.productCreationBean.action="<c:out value='${sessionScope.MenuContextPath}' />/createProduct.do?method=SearchProduct";
	document.productCreationBean.submit();  
	}
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function showDetails(var1) {
	if(var1=='createProject')
	{
	document.productCreationBean.action="<c:out value='${sessionScope.MenuContextPath}' />/createProduct.do?method=initProduct";
	document.productCreationBean.submit();  
	
	}
	if(var1=='searchProject')
	{
	document.productCreationBean.action="<c:out value='${sessionScope.MenuContextPath}' />/createProduct.do?method=initSearchProduct";
	document.productCreationBean.projectId.value='';
	document.productCreationBean.prdCategoryId.value='';
	document.productCreationBean.npdCategoryId.value='';
	document.productCreationBean.projectName.value='';		
	document.productCreationBean.submit();  
	
	}
	if(var1=='updateProject')
	{
	document.productCreationBean.action="<c:out value='${sessionScope.MenuContextPath}' />/createProduct.do?method=initUpdateProject";
	document.productCreationBean.submit();  
	
	}

}
function viewPlanVersion(var_projectId)
{
	var da=new Date();
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var_projectId = jsonrpc.processes.encryptString(var_projectId);	
 	windowURL="<c:out value='${sessionScope.MenuContextPath}' />/attachEditProjectPlan.do?method=viewProductPlanVersions&projectId="+var_projectId+"&"+da.getTime();
	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	window.open(windowURL,"Versions",windowDefault);
}

function productUpdate(val,var_projectId)
{
	var methodName;		
	if(val=='updateProduct')
	{
		methodName="initUpdateProduct";
	}
	if(val=='viewUpdateRequest')
	{	
		methodName="viewUpdateProduct";	
	}
	var da=new Date();
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var_projectId = jsonrpc.processes.encryptString(var_projectId);	
 	windowURL="<c:out value='${sessionScope.MenuContextPath}' />/createProduct.do?method="+methodName+"&projectId="+var_projectId+"&"+da.getTime();
 	//windowDefault="resizable=yes;dialogWidth:700px;dialogHeight:400px;center:yes;resizable:yes;status:no;";
	//window.showModalDialog(windowURL,window,windowDefault);
 	
	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	window.open(windowURL,"Versions",windowDefault);
}

/*
function productUpdate(val,var_projectId)
{
		myform=document.createElement("form");
		document.body.appendChild(myform);
		myform.action="<%=request.getContextPath()%>/createProduct.do";
		
		var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name","method");
		if(val=='updateProduct')
		{
			input.setAttribute("value", "initUpdateProduct");
		}
		if(val=='viewUpdateRequest')
		{	
			input.setAttribute("value", "viewUpdateProduct");
		}
		myform.appendChild(input);
		
		var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name","projectId");
		input.setAttribute("value", var_projectId);
		myform.appendChild(input);
		
		myform.submit();
}
*/
function showProjectDetails(var1)
 {
		var da=new Date();
 		windowURL="<c:out value='${sessionScope.MenuContextPath}' />/createProduct.do?method=initUpdateProject&projectId="+var1+"&"+da.getTime();
		windowDefault="resizable=yes;dialogWidth:700px;dialogHeight:400px;center:yes;resizable:yes;status:no;";
		window.showModalDialog(windowURL,window,windowDefault);
		return searchProject();
}
function searchSubmit()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	if(checkForm()==false)
	{
		return ;
	}
	showLayer();
	myform.submit();
}

function fromChildPagingSubmit()
{
	myform=document.getElementById('searchForm');
//	myform.elements["pagingSorting.pageNumber"].value=val;
	//showLayer();
	myform.submit();
}
function checkForm()
{
	/*if(trim(document.getElementById("id_projectId_search").value)!='')
	{
		if(!isPositiveIntegerGreaterThan0(document.getElementById("id_projectId_search").value))
		{
		alert("Please Enter a Positive No. for Project Id");
		return false;
		}
	}
	*/
	if(CheckNumeric(document.getElementById("id_projectId_search"),"Project ID")==false)
	{
		//return false;
	}
	if(ValidateTextField(document.getElementById("id_projectName_search"),'<%=request.getContextPath()%>',"Search Field-Project Name")==false)
	{
		//return false;
	}

	return true;
}
function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;
	
	if(checkForm()==false)
	{
		return ;
	}
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=1;
	if(myform.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myform.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myform.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myform.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myform.elements["pagingSorting.sortByColumn"].value=sortBy;
		myform.elements["pagingSorting.sortByOrder"].value="decrement";
	}
	if(checkForm()==false)
	{
		return ;
	}
	//showLayer();
	myform.submit();
}



</script>

</head>
<body onload="document.body.click()">
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
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Update Project Details</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
				<td align="right" style="padding-right:10px;">
					<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
				</td>
					
			</tr>
		</table>
		</td>
	</tr>
</table>
								<%String []colors=new String[]{"redBg","alterRedBg"}; %>
<html:form action="/createProduct" method="post" styleId="searchForm">
	<bean:define id="formBean" name="productCreationBean"></bean:define>
	<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
	<input type="hidden" name="method" value="initSearchProductUpdation"/>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>	
	
	<table width="100%" border="1" class="tabledata" cellpadding="3" cellspacing="1" align="center">
		<tr>
			
							<td colspan="11" class="tabledata" >
								  <bean:define id="pagingBean" name="formBean"></bean:define>
									<%String  currPageNumber =String.valueOf(((ProductCreationBean)formBean).getPagingSorting().getPageNumber());
										String  maxPageNumber =String.valueOf(((ProductCreationBean)formBean).getPagingSorting().getMaxPageNumber());
									%>
									<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
										<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
										<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
									</jsp:include>
							</td>
						</tr>	
	</table>
					<div style="overflow:scroll;height:370px;width:100%" class="scroll">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0" id="tbl1" class="tabledata" style="display:block" align="center">						
						<tr class="rptHeader">
							<td>ProjectId</td>
							<td>ProjectName</td>
							<td>Priority of Launch</td>
							<td>Product Brief</td>
							<td>Target Market</td>
							<td>Product Category</td>
							<td>ChangeOrder Category</td>							
							<td>Airtel Potential</td>
							<td>Total Market potential</td>
							<td>Capex Requirement</td>
							<td>Update</td>							

						</tr>
						<tr class="rptHeader">
							<th width="1%"  >
								<html:text property="searchProjectId" maxlength="8" size="5"  styleId="id_projectId_search"/>
							</th>
							<th width="5%"  >
								<html:text property="searchProjectName" maxlength="20" styleId="id_projectName_search"></html:text>
							</th>
							<th width="5%"  >&nbsp;</th>
							<th width="15%"  >&nbsp;</th>
							<th width="5%"  >&nbsp;</th>
							<th width="5%"  >
							<html:select property="searchProductCategoryId">
									<html:option value="-1">--</html:option>
									<logic:notEmpty name="formBean" property="prdCategoryList">
										<html:optionsCollection property="prdCategoryList" label="prodcatdesc"
											value="prodcatid" />
									</logic:notEmpty>
							  </html:select>
							</th>
							<th width="5%"  >
							<html:select property="searchNpdCategoryId">
									<html:option value="-1">--</html:option>
									<logic:notEmpty name="formBean" property="npdCategoryList">
										<html:optionsCollection property="npdCategoryList" label="npdcatdesc"
											value="npdcatid" />
									</logic:notEmpty>
							  </html:select>
							</th>							
							<th width="10%"  >&nbsp;</th>
							<th width="10%"  >&nbsp;</th>
							<th width="5%"  >&nbsp;</th>
							<th width="5%"  >&nbsp;</th>

						</tr>
						<logic:empty name="formBean" property="filteredProjectList">
							<tr>
								<td colspan="12" align="center">No Project Found</td>
							</tr>
						</logic:empty>
						<logic:notEmpty name="formBean" property="filteredProjectList">

						<logic:iterate id="row"	name="formBean" property="filteredProjectList" indexId="index1">
								<tr class="<%=colors[index1.intValue()%2]%>">
									<td>
									<bean:write name='row' property='projectid' /></td>
									<td>
									<bean:write name='row' property='projectName' /></td>
									<td><bean:write name='row'
										property='launchPriority' /></td>
									<td><bean:write name='row'
										property='productBrief' /></td>
									<td><bean:write name='row'
										property='targetMkt' /></td>
									<td>
										<bean:write name="row" property="prdCategoryName"/>
										</td>
									<td>
										<bean:write name="row" property="npdCategoryName"/>
										</td>
									<td><bean:write name='row'
										property='airtelPotential' /></td>
									<td><bean:write name='row'
										property='totalMktPotential' /></td>
									<td><bean:write name='row'
										property='capexReq' /></td>
									
									<td>
									
										<logic:equal name="row" property="productUpdationFlag" value="0">
											<a href="#" onclick="productUpdate('updateProduct','<bean:write name='row' property='projectid' />');">
													Update Product
											</a>
										</logic:equal>
										<logic:notEqual name="row" property="productUpdationFlag" value="0">
												<a href="#" onclick="productUpdate('viewUpdateRequest','<bean:write name='row' property='projectid' />');">
														View Update Request
												</a>
										</logic:notEqual>	
									</td>
									
								</tr>
						</logic:iterate>
						</logic:notEmpty>
					</table>
					</div>
</html:form>

</div>
</BODY>


</html:html>
