	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"
		import="com.ibm.ioes.npd.hibernate.beans.MyToDoListDto"%>
	<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
	<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@taglib uri="http://struts.apache.org/tags-logic"
		prefix="logic"%>
			<%@page import="com.ibm.ioes.npd.beans.MyToDoListFormBean"%>
	<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<html:html>
	<head>
	<title>MyToDo List</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="GENERATOR" content="Rational Software Architect">
	<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
	<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<script type="text/javascript" src="js/chrome.js"></script>
	<script language="javascript" src="js/calendar.js"></script>
	<!-- <script language="javascript" src="js/sortable.js"></script> -->

	<script language="javascript" src="js/utility.js"></script>
	<script type="text/javascript" src="js/jsonrpc.js"></script>
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

<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
	<script type="text/javascript">
	
	function ValidateFields()
	{
		 myform=document.getElementById('searchForm');
		 
		 if(CheckNumeric(myform.seachprojectId,"Project ID")==false)
			return false;
		 if(ValidateTextField(myform.seachprojectId,'<%=request.getContextPath()%>',"Project Id")==false)
			return false;
		 if(ValidateTextField(myform.seachprojectName,'<%=request.getContextPath()%>',"Project Name")==false)
			return false;
		 if(ValidateTextField(myform.productcategory,'<%=request.getContextPath()%>',"Product Category")==false)
			return false;
		 if(ValidateTextField(myform.npscategory,'<%=request.getContextPath()%>',"ChangeOrder Category")==false)
			return false;
		 
		
	}
	
	function searchSubmit(falg)
	{
		if(ValidateFields()==false)
		  {
				return false;
		  }
		

		document.searchForm.action="<%=request.getContextPath()%>/MyToDoList.do?methodName=myToDoList";
		showLayer();
		myform.submit();
	}
	
	function pagingSubmit(val)
	{
		if(ValidateFields()==false)
		  {
				return false;
		  }
		
		myform=document.getElementById('searchForm');
		myform.pageNumber.value=val;
		document.searchForm.action="<%=request.getContextPath()%>/MyToDoList.do?methodName=myToDoList";
		showLayer();
		myform.submit();
	}
	
	
	function sortSubmit(sortBy)
	{
		if(ValidateFields()==false)
		  {
				return false;
		  }
		myform=document.getElementById('searchForm');
		myform.pageNumber.value=1;
		if(myform.sortBy.value==sortBy)
		{
			if(myform.sortByOrder.value=="decrement")
			{
				myform.sortByOrder.value="increment";
			}
			else
			{
				myform.sortByOrder.value="decrement";
			}
		}
		else
		{
			myform.sortBy.value=sortBy;
			myform.sortByOrder.value="decrement";
		}
		document.searchForm.action="<%=request.getContextPath()%>/MyToDoList.do?methodName=myToDoList";
		showLayer();
		myform.submit();
	}
	
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode
	   if (charCode > 31 && (charCode < 48 || charCode > 57))
	   {
		  return false;
	   }
	   return true;
	}
	
	function fnCheckAll()
	{
		var nCount = document.forms[0].chk.length;
		var i;
	
		if(nCount==undefined)
			{
				if(document.getElementById("chkAll").checked==true)
				   document.forms[0].chk.checked=true;
				else
				   document.forms[0].chk.checked=false;
			}
		else
		{	
			if(document.getElementById("chkAll").checked==true)
			{		
				for(i=0;i<nCount;i++)
				{
					document.forms[0].chk[i].checked=true;
				}
			}
			else
			{
				for(i=0;i<nCount;i++)
				{
					document.forms[0].chk[i].checked=false;
				}	
			}
		}
	}
	 
	function showMessage()
	{
		var msg='';
		<html:messages id="message" message="true"  >
			msg=msg+'\n'+'<bean:write name="message" />';
		</html:messages>
		if(msg!='') {
		alert(msg);
		}
	
	}
	
	function fnChange(elem)
	{
		//alert(elem.value);
		val=document.getElementById("rejectionAllowed"+elem).value;
//		alert(val);
		if(val=='1')
		{
			document.getElementById('rejectButton').style.display="block";
		}
		else
		{
			document.getElementById('rejectButton').style.display="none";			
		}
	}
	
	function fnAction(flag)
	{
		var nCount = document.forms[0].chk.length;
		var values="";
		var stages="";
		var projectId = "";
		var varmainprojectid = "";		
		
		var checkCount=0;
		var countValue=1;
		var searchprojectid = "";
		var checkCount = 0;
		//alert(nCount);
		
		
		if(ValidateFields()==false)
		  {
				return false;
		  }
		
		if(nCount==undefined)
		{
		  if(document.forms[0].chk.checked==true)
		  {	
			checkCount = 1;
			values=document.searchForm("taskId1").value;
			projectId=document.searchForm("projectId1").value;
			varmainprojectid=document.searchForm("mainprojectid1").value;			
			stages=document.searchForm("stageId1").value; 
			searchprojectid = document.searchForm("searchprojectid1").value; 
			isTaskMandatory = document.searchForm("isTaskMandatory1").value; 
		  }	
		}	
		else
		{
	
				if (nCount>=0)
			{
				for(i=0;i<nCount;i++)
				{
					if(document.forms[0].chk[i].checked==true)
					{
					    
						if (values=="")
						{
							values=document.searchForm("taskId"+countValue).value;
							projectId=document.searchForm("projectId"+countValue).value;
							varmainprojectid=document.searchForm("mainprojectid"+countValue).value;
							stages=document.searchForm("stageId"+countValue).value; 
							searchprojectid = document.searchForm("searchprojectid"+countValue).value; 
							isTaskMandatory = document.searchForm("isTaskMandatory"+countValue).value; 
							//alert(values);
						}
						else
						{			
							values=values +"," + document.searchForm("taskId"+countValue).value;
							stages=stages + "," + document.searchForm("stageId"+countValue).value; 
							projectId=document.searchForm("projectId"+countValue).value;
							varmainprojectid=document.searchForm("mainprojectid"+countValue).value;							
							searchprojectid = document.searchForm("searchprojectid"+countValue).value; 
							isTaskMandatory = document.searchForm("isTaskMandatory"+countValue).value; 
							//alert(values);
							
						}
						checkCount++;
					}		
					countValue++;
				}
			}
		}
		
		if(checkCount<=0)
		{
			alert("Please Select a Task!!");
			return false;
		}
		else
		if(checkCount>1)
		{
			alert("Only one task can be selected!!");
			return false;
		}
		
		
		
		if(flag=="1") //Approve task
		{
			//alert(values);
			values = encrypt(values,'<%=request.getContextPath()%>')
			projectId = encrypt(projectId,'<%=request.getContextPath()%>')
			stages = encrypt(stages,'<%=request.getContextPath()%>')
			isTaskMandatory = encrypt(isTaskMandatory,'<%=request.getContextPath()%>')
			var path = "<%=request.getContextPath()%>/MyToDoList.do?methodName=approveTask&taskId="+values+"&projectId="+projectId+"&stageId="+stages+"&isTaskMandatory="+isTaskMandatory ;	
			window.open(path,'Approve','scrollbars=yes,resizable=yes,width=500,height=400,left=280,top=250');
		}
		if(flag=="2") //RFI
		{
			values = encrypt(values,'<%=request.getContextPath()%>')
			projectId = encrypt(projectId,'<%=request.getContextPath()%>')
			stages = encrypt(stages,'<%=request.getContextPath()%>')
			searchprojectid = encrypt(searchprojectid,'<%=request.getContextPath()%>')

			var path = "<%=request.getContextPath()%>/MyToDoList.do?methodName=RFITask&taskId="+values+"&projectId="+projectId+"&stageId="+stages+"&searchprojectid="+searchprojectid;	
			window.open(path,'Approve','scrollbars=yes,resizable=yes,width=500,height=400,left=280,top=250');
		}
		if(flag=="3") //View Workflow
		{
			values = encrypt(values,'<%=request.getContextPath()%>')
			projectId = encrypt(projectId,'<%=request.getContextPath()%>')
			stages = encrypt(stages,'<%=request.getContextPath()%>')
			searchprojectid = encrypt(searchprojectid,'<%=request.getContextPath()%>')
		
			var path = "<%=request.getContextPath()%>/MyToDoList.do?methodName=viewrProjectPlanDetail&taskId="+values+"&projectId="+projectId+"&stageId="+stages+"&searchprojectid="+searchprojectid;	
			window.open(path,'ViewWorkFlow','scrollbars=yes,resizable=yes,width=500,height=400,left=280,top=250');
		}
		if(flag=="4") //Reject Task
		{
			values = encrypt(values,'<%=request.getContextPath()%>')
			projectId = encrypt(projectId,'<%=request.getContextPath()%>')
			stages = encrypt(stages,'<%=request.getContextPath()%>')
			searchprojectid = encrypt(searchprojectid,'<%=request.getContextPath()%>')
			varmainprojectid = encrypt(varmainprojectid,'<%=request.getContextPath()%>')
		
			var path = "<%=request.getContextPath()%>/MyToDoList.do?methodName=rejectTask&taskId="+values+"&projectId="+projectId+"&stageId="+stages+"&searchprojectid="+searchprojectid+"&mainprojectid="+varmainprojectid;	
			window.open(path,'ViewWorkFlow','scrollbars=yes,resizable=yes,width=500,height=400,left=280,top=250');
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
	
	function downloadFile(workflowid , taskid,docname)
	{

		document.searchForm("searchprojectworkflowid").value = workflowid;
		document.searchForm("searchtaskid").value = taskid;
		document.searchForm("fileName").value = docname;
		document.searchForm.action="<%=request.getContextPath()%>/MyToDoList.do?methodName=DownloadFile";
		document.searchForm.submit(); 
		
	}
	
	function viewChart(projectid)
	{
		da=new Date();
		var myForm=document.getElementById('searchForm');
		var_projectId=projectid
		windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
		var chil=window.open("","Assign"+da.getTime(),windowDefault);
		var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/projectStatusReport.do'>";
		var str1="<input type='hidden' name='method' value='viewChart'>";	
		var str2="<input type='hidden' name='searchProjectid' value='"+var_projectId+"'>";		
		var strL="</FORM></BODY></HTML>";			
	
		var str=strF+str1+str2+strL;
		chil.document.write(str);
		var chilForm=chil.document.childform;
		chilForm.submit();
	}
	
	</script>
	</head>
	<body >
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
	<html:form action="/MyToDoList" method="post" styleId="searchForm"
		enctype="multipart/form-data">
		<html:hidden property="sortBy" />
		<html:hidden property="sortByOrder" />
		<html:hidden property="param" />
		<html:hidden property="pageNumber" />
		<html:hidden property="requestId" />
		<html:hidden property="hiddenProjectId" />
		<html:hidden property="changeStatus" />
		<html:hidden property="changeStatusValue" />
		<html:hidden property="popUpSearch" />
		<html:hidden property="hiddenReturnFlag" />
		<html:hidden property="strNextTaskIds" />
		<html:hidden property="strProjectIds" />
	
		<html:hidden property="searchWorkflowid" />
		<html:hidden property="taskId" />
		<html:hidden property="stageId" />
		<html:hidden property="projectId" />
		<html:hidden property="searchprojectworkflowid" />
		<html:hidden property="searchtaskid" />
		<html:hidden property="fileName" />

	
	
	
	
		<input type="hidden" name="methodName" value="myToDoList" />
		<html:hidden property="buttonClicked" styleId="id_buttonClicked" />
		<table border="0" cellpadding="0" cellspacing="0" class="border"
			align="center" width="99%">
			<tr valign="middle" id="projectPlan">
				<td height="48" align="center" valign="bottom">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					align="center" class="borderTab">
					<tr>
						<td width="0" height="19"><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" height="19"
							width="26%">My To Do List</td>
						<td height="19" width="0"><img src="npd/Images/tabRight.gif"></td>
						<td align="left" height="19" width="772"><font size="1"></td>
						<td align="right" style="padding-right:10px;" width="166"
							height="19"><a href="#"><img onclick="searchSubmit();"
							src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;<a
							href="#"></a></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
	
	

				<bean:define id="pagingBean" name="myToDoListFormBean"></bean:define>
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="0" class="tabledata">
									<tr class="rptHeader">
										<td align="left" style="color:#FFFFFF"><bean:define
											id="currPageNumber" name="pagingBean" property="pageNumber" />
										<bean:define id="maxPageNumber" name="pagingBean"
											property="maxPageNumber" /> <logic:equal name="currPageNumber"
											value="1">FIRST</logic:equal> <logic:notEqual
											name="currPageNumber" value="1">
											<a href="#" onclick="pagingSubmit('1')" style="color:#FFFFFF">FIRST</a>
										</logic:notEqual> &nbsp;|&nbsp; <%
				if (Integer.parseInt((String) currPageNumber) < Integer
				.parseInt((String) maxPageNumber)) {
	 %>
										<a href="#"
											onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)+1%>')"
											style="color:#FFFFFF">NEXT </a> <%
	 } else {
	 %> NEXT <%
	 }
	 %>
										</td>
										<td align="center" style="color:#FFFFFF">Page <bean:write
											name="currPageNumber" /> of <bean:write name="maxPageNumber" /></td>
										<td align="right" style="color:#FFFFFF"><logic:equal
											name="currPageNumber" value="1">PREV</logic:equal> <logic:notEqual
											name="currPageNumber" value="1">
											<a href="#"
												onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)-1%>')"
												style="color:#FFFFFF">PREV </a>
										</logic:notEqual> &nbsp;|&nbsp; <%
				if (Integer.parseInt((String) currPageNumber) < Integer
				.parseInt((String) maxPageNumber)) {
	 %>
										<a href="#" onclick="pagingSubmit('<%=maxPageNumber %>')"
											style="color:#FFFFFF">LAST</a> <%
	 } else {
	 %> LAST <%
	 }
	 %>
										</td>
									</tr>
								</table>
								
					<div style="overflow:scroll;height:320px;width:100%" class="scroll">
	
								<table cellSpacing="1" width="100%" cellPadding="4" border="0" class="tabledata" id="TABLE1">
									<tr class="rptHeader">
										<td align="center">&nbsp;</td>
										<td align="center" >SNo.</td>
										<td align="center" nowrap="nowrap">
										<p><a href="#" 
											onclick="sortSubmit('projectId')">Project</a></p>
										<p><a href="#" 
											onclick="sortSubmit('projectId')"> Id</a></p>
										</td>
										<td align="center" nowrap="nowrap"><a href="#"
											 onclick="sortSubmit('projectName')">Project
										Name</a></td>
										<td align="center" nowrap="nowrap">
										<p><a href="#" 
											onclick="sortSubmit('npdCategory')">NPD </a></p>
										<p><a href="#" 
											onclick="sortSubmit('npdCategory')">Category</a></p>
										</td>
										<td align="center" nowrap="nowrap">
										<p><a href="#" 
											onclick="sortSubmit('productCategory')">Product </a></p>
										<p><a href="#" 
											onclick="sortSubmit('productCategory')">Category</a></p>
										</td>
			<td align="center" nowrap="nowrap"><a href="#"
											 onclick="sortSubmit('currentStage')">Current
										Stage</a></td>
										<td align="center" nowrap="nowrap"><a href="#"
											 onclick="sortSubmit('currentTask')">Current
										Task</a></td>
										<td align="center" nowrap="nowrap"><a href="#"
											 onclick="sortSubmit('duration')">Duration</a></td>
											 
										 <td align="center" nowrap="nowrap">Planned Start Date</td>
										<td align="center" nowrap="nowrap">Planned End Date</td>
										<td align="center" nowrap="nowrap">Actual Start Date</td>
										<td align="center" nowrap="nowrap">
										<p><a href="#" 
											onclick="sortSubmit('noofdayswithCurrentOwner')">No. Of </a></p>
										<p><a href="#" 
											onclick="sortSubmit('noofdayswithCurrentOwner')">days With
										<br>
										Current Owner</a></p>
										</td>
										<td align="center" nowrap="nowrap">
										<p><a href="#" 
											onclick="sortSubmit('noofdayswithCurrentOwner')">Task</a></p>
										<p><a href="#" 
											onclick="sortSubmit('noofdayswithCurrentOwner')"> Status</a></p>
										</td>
										<td align="center" nowrap="nowrap"><a href="#"
											
											onclick="sortSubmit('noofdayswithCurrentOwner')"> Reference <br>
										Document</a></td>
										<td align="center" nowrap="nowrap">
											View Charts
										</td>
								</tr>
	
									<tr class="rptHeader">
										<td align="center"><input name="chkAll" id="chkAll"
											value="0" style="visibility: hidden" onClick="javascript:fnCheckAll();" type="checkbox"
											class="none"></td>
										<td align="center">&nbsp;</td>
										<td align="center"><html:text property="seachprojectId"
											size="10" onkeypress="return isNumberKey(event)"></html:text>
										</td>
										<td align="center"><html:text property="seachprojectName"
											size="10"></html:text></td>
										<td align="center" nowrap="nowrap"><html:text
											property="npscategory" styleId="npscategory" size="13" /></td>
										<td align="center"><html:text property="productcategory"
				size="15"></html:text></td>
										<td align="center" nowrap="nowrap"></td>
										<td align="center"></td>
										<td align="center" nowrap="nowrap"></td>
										<td align="center" nowrap="nowrap"></td>
										<td align="center" nowrap="nowrap"></td>
										<td align="center" nowrap="nowrap"></td>
										<td align="center" nowrap="nowrap"></td>
										<td align="center" nowrap="nowrap"></td>
										<td align="center" nowrap="nowrap"></td>
										<td align="center" nowrap="nowrap"></td>
								</tr>
									<%
									int nSNo = 0;
									
									int newsno=1+(Integer.parseInt(((MyToDoListFormBean)pagingBean).getPageNumber())-1)*
										Integer.parseInt(((MyToDoListFormBean)pagingBean).getPageRecords());
									%>
									<logic:present name="accountList" scope="request">
										<logic:empty name="accountList">
											<tr class="alterRedBg" align="center" height="16">
					<td colspan="15" align="center" nowrap><B>No Record Found</td>
				</tr>
										</logic:empty>
										<logic:notEmpty name="accountList">
											<logic:iterate id="row" name="accountList" indexId="recordId">
												<%
															String classType = null;
															if (recordId.intValue() % 2 == 0) {
														classType = "redBg";
															} else {
														classType = "alterRedBg";
															}
															nSNo++;
												%>
												<tr class="<%=classType%>">
													<td><input id="chk1" name="chk" onclick="fnChange(<%=newsno%>)"
														value="<bean:write name="row" property="idCollection"/>"
														type="radio" class="none">
													<div style="display: none;"><input type="hidden"
														name="projectId<bean:write name="row" property="idCollection"/>"
														value="<bean:write name="row" property="projectId"/>">
													<input type="hidden"
														name="nextTaskId<bean:write name="row" property="idCollection"/>"
														value="<bean:write name="row" property="nextTaskId"/>">
													<input type="hidden"
														name="rejectionAllowed<%=nSNo%>"
														id="rejectionAllowed<%=nSNo%>"
														value="<bean:write name="row" property="rejectionAllowed"/>">
	
													</div>
	
													</td>
													<td align="center"><%=newsno%></td>
													<td align="center"><bean:write name="row" property="projectDetails.projectid" /> 
													<input type="hidden" name="mainprojectid<%=nSNo%>" id="mainprojectid<%=nSNo%>" value="<bean:write name="row" property="projectDetails.projectid"/>">													
													<input type="hidden" name="projectid<%=nSNo%>" id="projectid<%=nSNo%>" value="<bean:write name="row" property="projectDetails.projectworkflowid"/>">
													<input type="hidden" name="searchprojectid<%=nSNo%>" id="searchprojectid<%=nSNo%>"	value="<bean:write name="row" property="projectDetails.projectid"/>">
													<input type="hidden" name="taskId<%=nSNo%>" id="taskId<%=nSNo%>" value="<bean:write name="row" property="taskId"/>">
													<input type="hidden" name="stageId<%=nSNo%>" id="stageId<%=nSNo%>" value="<bean:write name="row" property="stageId"/>">
													<input type="hidden" name="isTaskMandatory<%=nSNo%>" id="isTaskMandatory<%=nSNo%>" value="<bean:write name="row" property="isTaskMandatory"/>">
	
													</td>
													<td><bean:write name="row"
														property="projectDetails.projectName" /></td>
													<td align="center"><bean:write name="row"
														property="projectDetails.npdCategory.npdcatdesc" /></td>
													<td align="center"><bean:write name="row"
														property="projectDetails.prdCategory.prodcatdesc" /></td>	
													<td align="center"><bean:write name="row"
														property="stageName" /></td>
													<td align="center"><bean:write name="row"
														property="taskName" /></td>
													<td align="center"><bean:write name="row"
														property="taskDuration" /></td>
													<td align="center"><bean:write name="row"
														property="taskstartdateString" /></td>
													<td align="center"><bean:write name="row"
														property="taskenddateString" /></td>
													<td align="center"><bean:write name="row"
														property="actualtaskstartdateString" /></td>
													<td align="center"><bean:write name="row"
														property="withCurrentOwner" /></td>
													<td align="center"><bean:write name="row"
														property="currentTaskStatus" /></td>
													<td align="center"><a href="#" onclick="downloadFile(<bean:write name="row" property="projectDetails.projectworkflowid" />,'<bean:write name="row" property="taskId" />','<bean:write name="row" property="docname" />');"><bean:write name="row"
														property="docname" /></td>
													<td align="center"><a href="#" onclick="viewChart(<bean:write name="row" property="projectDetails.projectid"/>);">View Charts</td>
											</tr>
											<%newsno=newsno+1;%>
											</logic:iterate>
										</logic:notEmpty>
									</logic:present>
								</table>
								</div>

								<table  width="39%" align="center" border="0"
									cellpadding="0" cellspacing="0">
									<tr>
										
											<td colspan="4">&nbsp;
												
											</td>
										
										
									</tr>
									<tr>
										<logic:notEmpty name="accountList">
											<td >
											<div class="buttonVSmall" onClick="javascript:fnAction(1);">Approve</div>
											</td>
											<td >
											<div class="buttonVSmall" id="rejectButton" onClick="javascript:fnAction(4);" style="display: none;">
												Reject</div>
											</td>
											<td >
											<div class="buttonSmall" onClick="javascript:fnAction(2);">RFI</div>
											</td>
											<td >
											<div class="buttonSmall" onClick="javascript:fnAction(3);">View
											WorkFlow</div>
											</td>
										</logic:notEmpty>
									</tr>
								</table>
	</html:form>
</div>
	</body>
	</html:html>
