<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.ReferenceDocBean;"%>
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


<script type="text/javascript">

function saveReferenceDoc()
{
	var counter=0;
	myform=document.getElementById('searchForm');
	
	if(document.getElementById("attachment").value==null||document.getElementById("attachment").value=="")
	{
		alert("Please enter Attachment");
		return false;
	}
	
	if(document.getElementById("attachment").value!=null&&document.getElementById("attachment").value!="")
	{
		if (isValidFileExtension(document.getElementById("attachment").value)==false) 
		{
			return false;                                    
		}
	}
	
	if(document.getElementById("refDocName").value==null||trim(document.getElementById("refDocName").value)=="")
	{
		alert("Please enter Reference Doc Name");
		return false;
	}
	
	if(ValidateTextField(myform.refDocName,'<%=request.getContextPath()%>',"Document Name")==false)
	{
		return false;
	}
	
	if(document.referenceDocBean.referenceDocId!=null)
	{
		for(x=0;x<document.referenceDocBean.referenceDocId.length;x++)
		{
			if(document.referenceDocBean.referenceDocId[x].checked)
			{
				counter++;
			}
		}
		if(counter>1)
		{
			alert("Please select only 1 document");
			return false;
		}
		if(counter==0&&document.referenceDocBean.referenceDocId.length!=undefined)
		{
			document.referenceDocBean.referenceDocId.value=null;
		}
		
	}
	document.referenceDocBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageReferenceDoc.do?method=Add/Update Doc";
	showLayer();
	return true;
}

function trim(stringToTrim) 
{
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function downloadFile(var1)
{
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var1 = jsonrpc.processes.encryptString(var1);	
	document.referenceDocBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageReferenceDoc.do?method=downloadFile&refDocId="+var1;
	document.referenceDocBean.submit();	
}

function assignValue(ref){
	if(document.referenceDocBean.referenceDocId.length == undefined)
	{
		document.referenceDocBean.referenceDocId.value = ref;
	}
	else
	{
		document.referenceDocBean.referenceDocId[0].value = ref;
	}
		
}

function initializePage()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(myform.rfDocSearch!="")
	{
		if(ValidateTextField(myform.rfDocSearch,'<%=request.getContextPath()%>',"Document Name")==false)
		{
			return false;
		}
	}
	document.referenceDocBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageReferenceDoc.do?method=viewReferenceDoc";
	showLayer();
	document.referenceDocBean.submit();			
}

function searchSubmit()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(myform.rfDocSearch!="")
	{
		if(ValidateTextField(myform.rfDocSearch,'<%=request.getContextPath()%>',"Document Name")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('referenceDocBean');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	if(myform.rfDocSearch!="")
	{
		if(ValidateTextField(myform.rfDocSearch,'<%=request.getContextPath()%>',"Document Name")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('referenceDocBean');
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<c:out value='${sessionScope.MenuContextPath}' />/manageReferenceDoc.do?method=viewReferenceDoc";
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	if(myform.rfDocSearch!="")
	{
		if(ValidateTextField(myform.rfDocSearch,'<%=request.getContextPath()%>',"Document Name")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('referenceDocBean');
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
	showLayer();
	myform.submit();
}

</script>

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
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Upload
				Reference Doc</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<html:form action="/manageReferenceDoc" method="post" enctype="multipart/form-data" onsubmit="return saveReferenceDoc();" styleId="searchForm">
	<table width="98%" border="2" class="tabledata" cellpadding="0"	cellspacing="0" align="center">
		<tr>
			<td width="70%">
				<table width="100%" border="0" class="tabledata" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<th width="30%" scope="row">
							<div align="left">Upload Reference Doc <font color="#990033">*</font></div>
						</th>
						<td width="70%"><html:file property="attachment" size="20" onkeydown='this.blur()'></html:file>&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<html:hidden property="pagingSorting.pageNumber" />
					<html:hidden property="pagingSorting.sortByColumn" />
					<html:hidden property="pagingSorting.sortByOrder" />
					<tr>
						<th width="30%" scope="row">
						<div align="left">Reference Doc Name <font color="#990033">*</font></div>
						</th>
						<td width="50%"><html:text property="refDocName" size="50" maxlength="35"
							style="width:50%"></html:text>&nbsp;&nbsp;&nbsp;<html:submit
							property="method">
							<bean:message key="submit.saveReferenceDoc"
								bundle="ButtonResources" />
						</html:submit></td>
	
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
				
										<div style="overflow:scroll;height:370px;width:100%" class="scroll">
											<table cellSpacing="1" cellPadding="4" width="100%" border="0" align="center">
												<tr height="20" valign="middle">
													<td width="5%" nowrap class="noScroll">&nbsp;</td>
													<td width="10%" nowrap class="noScroll">Document Name</td>
													<td width="16%" nowrap class="noScroll">Document Id</td>
													<td width="16%" nowrap class="noScroll">Created Date</td>
													<td width="10%" nowrap class="noScroll">created By</td>
												</tr>
												<tr height="20" valign="middle">
													<td width="5%"></td>
													<td width="10%"><html:text property="rfDocSearch" maxlength="30"/></td>
													<td width="16%"><img src="npd/Images/search.gif"
														onclick="initializePage();" /></td>
													<td width="16%"></td>
													<td width="10%"></td>
				
												</tr>
									
												<logic:notEmpty property="referenceDocList" name="referenceDocBean">
												<tr>
													<td colspan="15" class="tabledata"><bean:define id="pagingBean" name="referenceDocBean"></bean:define> 
														<%String currPageNumber = String
												 			.valueOf(((ReferenceDocBean) pagingBean).getPagingSorting()
												 			.getPageNumber());
												 			String maxPageNumber = String
												 			.valueOf(((ReferenceDocBean) pagingBean).getPagingSorting()
												 			.getMaxPageNumber());%> 
												 		<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
														<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
														<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
														</jsp:include>
													</td>
												</tr>
												<logic:iterate id="referenceDocBean_id" name="referenceDocBean"
													property="referenceDocList" indexId="index1">
													<c:if test="${index1%2==0}">
														<tr class="redBg">
															<td><html:radio property="referenceDocId"
																name="referenceDocBean"
																onclick="assignValue(${referenceDocBean_id.key.refdocid});"
																value="${referenceDocBean_id.key.refdocid}">
															</html:radio></td>
															<td><a href="#"
																onclick="downloadFile(<bean:write name="referenceDocBean_id" property="key.refdocid" />);"><c:out
																value="${referenceDocBean_id.key.refdocname}" /></a></td>
			
															<td><bean:write name="referenceDocBean_id"
																property="key.refdocid" /></td>
															<td><fmt:parseDate
																value="${referenceDocBean_id.key.createddate}" type="DATE"
																pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
																value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
																var="formatedDate" /><c:out value="${formatedDate}" /></td>
															<td><c:out
																value="${referenceDocBean_id.key.createdby.empname}" /></td>
														</tr>
													</c:if>
	
													<c:if test="${index1%2!=0}">
														<tr class="alterRedBg">
															<td><html:radio property="referenceDocId"
																name="referenceDocBean"
																onclick="assignValue(${referenceDocBean_id.key.refdocid});"
																value="${referenceDocBean_id.key.refdocid}">
															</html:radio></td>
															<td><a href="#"
																onclick="downloadFile(<bean:write name="referenceDocBean_id" property="key.refdocid" />);"><c:out
																value="${referenceDocBean_id.key.refdocname}" /></a></td>
															<td><bean:write name="referenceDocBean_id"
																property="key.refdocid" /></td>
															<td><fmt:parseDate
																value="${referenceDocBean_id.key.createddate}" type="DATE"
																pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
																value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
															var="formatedDate" /><c:out value="${formatedDate}" /></td>
														<td><c:out
															value="${referenceDocBean_id.key.createdby.empname}" /></td>
													</tr>
												</c:if>
											</logic:iterate>
									</logic:notEmpty>
									<logic:empty name="referenceDocBean" property="referenceDocList">
										<tr align="center">
											<td colspan="4"><font color="red">No Record Exists</font></td>
										</tr>
									</logic:empty>
								</table>
							</div>
						
</td>
</tr>
</table>


			<!-- input color --> <script type="text/javascript">
<!--
 // initInputHighlightScript();
//-->
</script> </html:form>
</div>
</BODY>


</html:html>
