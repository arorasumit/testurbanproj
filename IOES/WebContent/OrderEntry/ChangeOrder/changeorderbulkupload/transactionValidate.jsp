<!-- ============================================================================================================ -->
<!-- Tag Name	Resources						Date				CSR No					Description -->
<!-- [001]		Sumit Arora & Anil Kumar		28-july-2011		00-05422				Created Bulkupload Reporting Screen-->
<!-- [002]      Gunjan Singla                   29-Sept-2014     CBR_20140704-XX-020224     Global Data Billing Efficiencies Phase1      -->
<!-- ============================================================================================================ -->
<!-- start [001]-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.bulkupload.formbean.TransactionValidationFormBean;"%>
<html:html>
<head>
<title>BulkUpload Transaction Reporting</title>
<link href="<%=request.getContextPath()%>/gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>    
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
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
<script type="text/javascript">
    function goToHome()
	{
		var myForm=document.getElementById('searchForm');	
		myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
		myForm.submit();
	}
    
    /*function window.confirm(str)
	{
    	execScript('n = msgbox("'+str+'","4132","Confirm Action")', "vbscript");
    	return(n == 6);
	}*/
	
	function checkMouseEvent()
	{
		if(event.button==2)
		{
			alert("This action is not Allowed!");
			return false;
		}
		else
		{
			return true;
		}
	}
    
    function getErrorFile(var_fileid,var_filepath,var_filename)
	{
		document.forms[0].selectedTransactionId.style.visibility="hidden";
		showLayer();				
		document.transactionValidationFormBean.fileId.value=var_fileid;
		document.transactionValidationFormBean.filePath.value=var_filepath+"/"+var_filename;				
		document.transactionValidationFormBean.methodName.value='getErrorLog';
		document.forms[0].submit();
	}
	
	function getResultLogFile(var_fileid,var_filepath,var_filename)
	{
		document.forms[0].selectedTransactionId.style.visibility="hidden";
		showLayer();					
		document.transactionValidationFormBean.fileId.value=var_fileid;
		document.transactionValidationFormBean.filePath.value=var_filepath+"/"+var_filename;				
		document.transactionValidationFormBean.methodName.value='getResultLog';
		document.forms[0].submit();
	}
	
	function getResultErrorMixLogFile(var_fileid,var_filepath,var_filename)
	{
		document.forms[0].selectedTransactionId.style.visibility="hidden";
		showLayer();					
		document.transactionValidationFormBean.fileId.value=var_fileid;
		document.transactionValidationFormBean.filePath.value=var_filepath+"/"+var_filename;				
		document.transactionValidationFormBean.methodName.value='getResultErrorMixLog';
		document.forms[0].submit();
	}
       
    function getTransaction()
	{
		var w = document.forms[0].selectedTransactionId.selectedIndex;
    	var selected_value = document.forms[0].selectedTransactionId.options[w].value;      	
		document.forms[0].selectedTransactionId.style.visibility="hidden";
		showLayer();
		document.transactionValidationFormBean.methodName.value='getFilesForTransaction';
		document.transactionValidationFormBean.transactionIdSelected.value=selected_value;
		document.forms[0].submit();
	}
	function sendFileToValidate(rowIndex)
	{												
		document.transactionValidationFormBean.trRowID.value=rowIndex;						
		document.forms[0].selectedTransactionId.style.visibility="hidden";				
		showLayer();
		document.transactionValidationFormBean.methodName.value='validateSelectedFile';
		document.forms[0].submit();
	}
	
	
	window.onresize = setLayerPosition;
	
	
	function doLoad()
	{	
		if(document.transactionValidationFormBean.methodName.value=='getErrorLog'||document.transactionValidationFormBean.methodName.value=='getResultLog'||document.transactionValidationFormBean.methodName.value=='getResultErrorMixLog')
		{	
			if(document.transactionValidationFormBean.fileFoundStatus.value==1)
			{
				document.transactionValidationFormBean.methodName.value='doDownLoadFile';
				document.transactionValidationFormBean.submit();
			}
			else
			{
				alert('The system could not find the file specified.');
			}
		}		
		if(document.transactionValidationFormBean.methodName.value=='validateSelectedFile'&&document.transactionValidationFormBean.validationStatus.value==1)
		{						
			refreshTransaction();
			alert("File validated successfully!! Scheduler will run to proceed Data");
		}
		if(document.transactionValidationFormBean.methodName.value=='validateSelectedFile'&&document.transactionValidationFormBean.validationStatus.value==2)
		{
			alert('File contains invalid data');						
			var rowno=document.transactionValidationFormBean.trRowID.value;			
			var trRowID=document.getElementById('transactionRowId'+rowno);			
			trRowID.className='highlight';
			refreshTransaction();
		}
		
				
	}
function searchSubmit()
{
	var myForm=document.getElementById('searchForm');	
	var w = document.forms[0].cboFileStausSearch.selectedIndex;
    var selected_value = document.forms[0].cboFileStausSearch.options[w].value; 
    document.transactionValidationFormBean.searchFileStatus.value=selected_value;
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	var txtfileid=document.getElementById('txtFileID');
	var txtuserid=document.getElementById('txtUserID');
	
	if(!isBlankForm())
	{
		if((document.getElementById('txtFileID').value).length>0)
		{
			if(isDigit(txtfileid))
			{
				showLayer();
				getTransaction();
			}
			else
			{
				alert('Only Numeric and positive value allowed for fileid');
				return;
			}
		}
		else
		{
			showLayer();
			getTransaction();
		}
		
	} 	
	else 
	{	
		alert('Please enter atleast one search criteria');
		return;			
	}
}
function pagingSubmit(val)
{
	var myForm=document.getElementById('searchForm');		
	myForm.elements["pagingSorting.pageNumber"].value=val;	
	showLayer();
    getTransaction();
}
function sortSubmit(sortBy)
{
	myForm=document.getElementById('searchForm');	
	myForm.elements["pagingSorting.pageNumber"].value=1;
	if(myForm.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myForm.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myForm.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myForm.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myForm.elements["pagingSorting.sortByColumn"].value=sortBy;
		myForm.elements["pagingSorting.sortByOrder"].value="decrement";
	}	
	getTransaction();     	
}
function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var filestatus=document.getElementById('cboFileStausSearch').value;
	var fromDate=document.getElementById('dateFrom').value;
	var toDate=document.getElementById('dateTo').value;
	var fileID=document.getElementById('txtFileID').value;
	var userID=document.getElementById('txtUserID').value;
	
	if((filestatus==null && fileID==null && fromDate == null && toDate == null && fileID==0 ) 
		||trim(fileID)==0 && trim(userID).length==0 && trim(fromDate)=="" && trim(toDate)=="" && trim(filestatus)=="-1")		
		{				
			return true;
		} 
	else {				
			if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
			{
				alert("Please enter From Date");
				return true;
			}
			else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
			{
				alert("Please enter To Date");
				return true;
			}		
			else if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return true;
			}
			else			
				return false;
		}	
}
function clearFields()
{
	document.getElementById('cboFileStausSearch').selectedIndex=0;	
	document.getElementById('cboFileStausSearch').value="-1";
	document.getElementById('dateFrom').value="";
	document.getElementById('dateTo').value="";
	document.getElementById('txtFileID').value="0";
	/*[002]*/
	document.getElementById('txtUserID').value="";
	
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     	searchSubmit();
    }      
}

function refreshTransaction()
{
		//clearFields();
		var w = document.forms[0].selectedTransactionId.selectedIndex;
    	var selected_value = document.forms[0].selectedTransactionId.options[w].value;      	
		document.forms[0].selectedTransactionId.style.visibility="hidden";
		//showLayer();
		document.transactionValidationFormBean.methodName.value='getFilesForTransaction';
		document.transactionValidationFormBean.transactionIdSelected.value=selected_value;
		document.forms[0].submit();
}
function isDigit(element)
{
	if(element.value!=null && element.value!="")
	{
		var str=new String(element.value);
		var reg =/^([0-9]+)$/;
	    if(reg.test(element.value) == false)
	    {   		
   			element.focus();
    		return false;
    	}
    	else
    		return true;
	}			
}
</script>

</head>
<body onload="javascript:doLoad();">
<div id="shadow" class="opaqueLayer"> 
		<br /><br /><br /><br /><br /><br /><br /><br />
	    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
	    <br /><br /><br />
	</div>
	<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">				
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
	</table>
	<div class="head"><span>Bulk Upload Reporting</span></div>
	<html:form action="/transactionValidate" styleId="searchForm">
	<html:hidden property="validationStatus"/>
	<html:hidden property="fileFoundStatus"/>
	<!-- paging sorting start -->
	<bean:define id="formBean" name="transactionValidationFormBean"></bean:define>
	<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>

	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>	
	<!-- paging sorting end -->
	<table border="0" align="center" width="80%">	
		<logic:notEmpty name="transactionValidationFormBean" property="errorMessage">
				<tr class="errMsg">
					<td align="center"><bean:write name="transactionValidationFormBean" property="errorMessage" /></td>
				</tr>
			</logic:notEmpty>	
		<logic:notEmpty name="transactionValidationFormBean" property="message">
			<tr>
				<td align="center">
				<font color="green"><b><bean:write name="transactionValidationFormBean" property="message" /></b></font></td>
			</tr>
		</logic:notEmpty>
	</table>						
		<br>	
		<table border="0" align="center" width="60%" class="tab2">
			<tr class=grayBg>
				<td align="center">Select Subchange Type :</td>
				<td align="center">
					<html:select property="selectedTransactionId" styleClass="inputBorder1" onchange="javascript:getTransaction();">
						<logic:empty name="transactionValidationFormBean" property="transactionList">
							<html:option value="0">----------No transaction----------</html:option>
						</logic:empty>
						<logic:notEmpty name="transactionValidationFormBean" property="transactionList">
							<html:option value="0">----------Select Subchange Type----------</html:option>
							<html:optionsCollection name="transactionValidationFormBean" 
								label="strTransactionName" value="strTransactionID" property="transactionList"/>
						</logic:notEmpty>
					</html:select>	
				</td>
			</tr>
		</table>
		<BR/><BR/>
		<!-- paging sorting start -->
		<logic:notEmpty name="transactionValidationFormBean" property="transFileList">
		<table width="80%" border="0" class="tab2" cellpadding="3" cellspacing="1" align="center">
		<tr class="grayBg">
			<td class="tab2" >
				<bean:define id="pagingBean" name="formBean"></bean:define>					
					<%String  currPageNumber =String.valueOf(((TransactionValidationFormBean)formBean).getPagingSorting().getPageNumber());
					  String  maxPageNumber =String.valueOf(((TransactionValidationFormBean)formBean).getPagingSorting().getMaxPageNumber());
					%>					
					<jsp:include flush="true" page="/paging.jsp">
						<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
						<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
					</jsp:include>
			</td>
		</tr>	
   	</table>
   	</logic:notEmpty>
   	<logic:notEmpty name="transactionValidationFormBean" property="transFileList">
   	<table width="80%" border="0" class="tab2" cellpadding="3" cellspacing="1" align="center">
   	<tr>
   		<td>
   			File Status:
   			<html:select property="searchFileStatus" styleId="cboFileStausSearch" onkeypress="onPressEnterSearch(event)" style="width:150px" styleClass="inputBorder1">
				<html:option value="-1">--Select--</html:option>
				<html:optionsCollection name="fileStatusList" label="searchStrFileStatus" value="searchFileStausId" />
			</html:select>	   			
   		</td>
   		<td>
   			<table border="0">
   				<tr>
   					<td>
				         From Date:<html:text property="searchfromDate" styleId="fromdate" size="12" onkeypress="onPressEnterSearch(event)" readonly="true" styleId="dateFrom" styleClass="inputBorder1"></html:text>
				         <font size="1">
				         <a href="javascript:show_calendar(searchForm.searchfromDate);" title="pick date here" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="pick date here"></a>
				         </font>
				    </td>
				</tr>
				<tr>
				    <td>
						To Date:&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="searchToDate" styleId="todate" onkeypress="onPressEnterSearch(event)" size="12" readonly="true" styleId="dateTo" styleClass="inputBorder1"></html:text>
						<font size="1">
						<a href="javascript:show_calendar(searchForm.searchToDate);" title="pick date here" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="pick date here"></a>
						</font>
					</td>		   						
				</tr>				
			</table>
   		</td>
   		<td>
   			File ID:<html:text property="searchFileID" size="8"  styleId="txtFileID" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1"></html:text>
   		</td>
   		<!-- [002] start -->
   		<td>
   			User ID:<html:text property="searchUserID" styleId="txtUserID" onkeypress="onPressEnterSearch(event)" styleClass="inputBorder1"></html:text>
   		</td>
   		<!-- [002] end -->
   		<td>
   			<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
			<a href="#"><img onclick="searchSubmit();"
					src="${pageContext.request.contextPath}/gifs/search_icon.gif"
					title="search" width="18" height="20"></a>&nbsp;
			<a href="#"><img border="0"
					src="${pageContext.request.contextPath}/gifs/refresh_icon2.gif"
					alt="Refresh" width="20" height="21" onclick="refreshTransaction();" /></a>
   		</td>
   	</tr>
   	</table>
   	</logic:notEmpty>
   	<!-- paging sorting end -->
		<table border="0" bordercolor="#000000" align="center" width="80%" class="tab2" id="tranTable">
			<logic:empty name="transactionValidationFormBean" property="transFileList">
			<logic:notEqual value="0" name="transactionValidationFormBean" property="selectedTransactionId">
				<tr>	
					<td colspan="2" align="center" class="errMsg"><font color="red">No Transaction Files Found</font>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#"><img border="0" src="${pageContext.request.contextPath}/gifs/refresh_icon2.gif"
					alt="Refresh" width="20" height="21" onclick="refreshTransaction();" /></a>
					</td>	
				</tr>
			</logic:notEqual>	
			<logic:equal value="0" name="transactionValidationFormBean" property="selectedTransactionId">
				<tr>	
					<td colspan="2" align="center" class="errMsg"><font color="red">No Subchange Selected</font></td>	
				</tr>
			</logic:equal>	
			</logic:empty>
			
			<!-- paging sorting start -->
			<logic:notEmpty name="transactionValidationFormBean" property="transFileList">
				<tr class="grayBg" height="20px">
					<th align="center" width="8%%" class="headingFont">S. No.</th>
					<th align="center" class="headingFont" width="37%">
						<a href="#" onclick="sortSubmit('FILENAME')">
						File Name
						</a>
					</th>
					<th align="center" class="headingFont" width="16%">
						<a href="#" onclick="sortSubmit('TRANSDATE')">
						Transaction Date
						</a>
					</th>
					<th align="center" class="headingFont" width="13%">File Status</th>
					<th align="center" class="headingFont" width="10%">
						<a href="#" onclick="sortSubmit('FILEID')">
						File id
						</a>
						</th>
						<!-- [002] start -->
						 <th align="center" class="headingFont" width="10%">User ID</th> 
						<!-- [002] end -->
			<!-- paging sorting end -->
					<th class="headingFont" width="10%">Validate</th>
				</tr>
				<%String []colors=new String[]{"lightBg","normal"}; %>
				<%int rowcount=1;%>
				<logic:iterate id="transFilesData" name="transactionValidationFormBean" 
					indexId="p" property="transFileList">
				<tr class="<%=colors[p.intValue()%2]%>" id="transactionRowId<%=(p.intValue()+1)%>">				
						<td width="8%" align="center"><%=(p.intValue()+1)%>.</td>						
						<td width="37%">						
						<logic:equal value="1" name="transFilesData" property="fileStatusId">							
								<a href="#" title="Clik to download file" onclick="javascript:getErrorFile(<bean:write name='transFilesData' property='fileId'/>,'<bean:write name='transFilesData' property='filePath' />','<bean:write name='transFilesData' property='strFileName' />')"><font color="blue">
									<bean:write name="transFilesData" property="strFileName" /></font>
								</a>											
						</logic:equal>
						<logic:equal value="3" name="transFilesData" property="fileStatusId">																
									<bean:write name="transFilesData" property="strFileName" />											
						</logic:equal>	
						<logic:equal value="7" name="transFilesData" property="fileStatusId">																
									<bean:write name="transFilesData" property="strFileName" />											
						</logic:equal>	
						<logic:equal value="4" name="transFilesData" property="fileStatusId">																
								<a href="#" title="Clik to download file" onclick="javascript:getErrorFile(<bean:write name='transFilesData' property='fileId' />,'<bean:write name='transFilesData' property='filePath' />','<bean:write name='transFilesData' property='strFileName' />')"><font color="blue">
									<font color="red">
									<bean:write name="transFilesData" property="strFileName" /></font>
								</a>											
						</logic:equal>	
						<logic:equal value="2" name="transFilesData" property="fileStatusId">																
									<bean:write name="transFilesData" property="strFileName" />											
						</logic:equal>	
						<logic:equal value="5" name="transFilesData" property="fileStatusId">							
								<a href="#" title="Clik to download file" onclick="javascript:getResultLogFile(<bean:write name='transFilesData' property='fileId' />,'<bean:write name='transFilesData' property='filePath' />','<bean:write name='transFilesData' property='strFileName' />')">
								<font color="green">
									<bean:write name="transFilesData" property="strFileName" /></font>
								</a>											
						</logic:equal>
						<logic:equal value="9" name="transFilesData" property="fileStatusId">																
								<a href="#" title="Clik to download file" onclick="javascript:getResultLogFile(<bean:write name='transFilesData' property='fileId' />,'<bean:write name='transFilesData' property='filePath' />','<bean:write name='transFilesData' property='strFileName' />')">
								<font color="green">
									<bean:write name="transFilesData" property="strFileName" /></font>
								</a>											
						</logic:equal>	
						
						<logic:equal value="10" name="transFilesData" property="fileStatusId">																
								<a href="#" title="Clik to download file" onclick="javascript:getResultLogFile(<bean:write name='transFilesData' property='fileId' />,'<bean:write name='transFilesData' property='filePath' />','<bean:write name='transFilesData' property='strFileName' />')">
								<font color="green">
									<bean:write name="transFilesData" property="strFileName" /></font>
								</a>											
						</logic:equal>
						<logic:equal value="12" name="transFilesData" property="fileStatusId">																
								<a href="#" title="Clik to download file" onclick="javascript:getResultErrorMixLogFile(<bean:write name='transFilesData' property='fileId' />,'<bean:write name='transFilesData' property='filePath' />','<bean:write name='transFilesData' property='strFileName' />')">
								<font color="green">
									<bean:write name="transFilesData" property="strFileName" /></font>
								</a>											
						</logic:equal>
						<logic:equal value="13" name="transFilesData" property="fileStatusId">																
								<a href="#" title="Clik to download file" onclick="javascript:getResultErrorMixLogFile(<bean:write name='transFilesData' property='fileId' />,'<bean:write name='transFilesData' property='filePath' />','<bean:write name='transFilesData' property='strFileName' />')">
								<font color="green">
									<bean:write name="transFilesData" property="strFileName" /></font>
								</a>											
						</logic:equal>
						<logic:equal value="11" name="transFilesData" property="fileStatusId">																
									<bean:write name="transFilesData" property="strFileName" />											
						</logic:equal>	
						
						</td>									
						<td width="16%"><bean:write name="transFilesData" property="strTransDate" /></td>	
						<td width="13%">
						<logic:equal value="1" name="transFilesData" property="fileStatusId">							
								<font color="red">
									<bean:write name="transFilesData" property="strFileStatus" /></font>																
						</logic:equal>	
						<logic:equal value="2" name="transFilesData" property="fileStatusId">							
								<font color="green">
									<bean:write name="transFilesData" property="strFileStatus" /></font>																
						</logic:equal>
						<logic:equal value="7" name="transFilesData" property="fileStatusId">							
								<font color="green">
									<bean:write name="transFilesData" property="strFileStatus" /></font>																
						</logic:equal>							
						<logic:equal value="3" name="transFilesData" property="fileStatusId">															
									<font color="green">
										<bean:write name="transFilesData" property="strFileStatus" />
									</font>															
						</logic:equal>	
						<logic:equal value="4" name="transFilesData" property="fileStatusId">	
								<font color="red">															
									<bean:write name="transFilesData" property="strFileStatus" />
								</font>																
						</logic:equal>		
						<logic:equal value="5" name="transFilesData" property="fileStatusId">	
								<font color="green">														
									<bean:write name="transFilesData" property="strFileStatus" />
								</font>																
						</logic:equal>
						<logic:equal value="9" name="transFilesData" property="fileStatusId">	
								<font color="green">														
									<bean:write name="transFilesData" property="strFileStatus" />
								</font>																
						</logic:equal>
						<logic:equal value="10" name="transFilesData" property="fileStatusId">	
								<font color="green">														
									<bean:write name="transFilesData" property="strFileStatus" />
								</font>																
						</logic:equal>
						<logic:equal value="11" name="transFilesData" property="fileStatusId">	
								<font color="red">														
									<bean:write name="transFilesData" property="strFileStatus" />
								</font>																
						</logic:equal>				
						<logic:equal value="12" name="transFilesData" property="fileStatusId">	
								<font color="green">														
									<bean:write name="transFilesData" property="strFileStatus" />
								</font>																
						</logic:equal>
						<logic:equal value="13" name="transFilesData" property="fileStatusId">	
								<font color="green">														
									<bean:write name="transFilesData" property="strFileStatus" />
								</font>																
						</logic:equal>					
						</td>
						<td width="10%"><bean:write name="transFilesData" property="fileId" /></td>
						<!-- [002] start -->
						<td width="10%"><bean:write name="transFilesData" property="searchUserID"/></td>
						<!-- [002] end -->
						<td align="center" width="10%">

							<logic:equal value="7" name="transFilesData" property="fileStatusId">
								<html:radio name="transactionValidationFormBean" title="Click to validate"
								property="fileToValidate" value="${transFilesData.fileId}&&${transFilesData.strFileName}" 								
								onclick='<%="sendFileToValidate("+ rowcount +");"%>'></html:radio>								
							</logic:equal>
							<!--<logic:equal value="1" name="transFilesData" property="fileStatusId">
								<html:radio name="transactionValidationFormBean" title="Clik to validate"
								property="fileToValidate" value="${transFilesData.fileId}&&${transFilesData.strFileName}" 
								onclick='<%="sendFileToValidate("+ rowcount +");"%>'></html:radio>							
							</logic:equal>-->
						</td>
					
				</tr>
				<%rowcount=rowcount+1;%>
				</logic:iterate>
			</logic:notEmpty>
		</table>	
		<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">			
			<td width="20%" colspan="4">&nbsp;</td>
		</tr>
	</table>
		<br/>
		<html:hidden property="fileId"/>
		<html:hidden property="methodName"/>		
		<html:hidden property="filePath"/>
		<html:hidden property="fileToDownload"/>
		<html:hidden property="transactionIdSelected"/>
		<html:hidden property="transactionName"/>
		<html:hidden property="transactionTemplate"/>
		<html:hidden property="templateId"/>	
		<html:hidden property="processStatus"/>
		<html:hidden property="trRowID"/>
		<html:hidden property="validationFlag"/>
		
	</html:form>
</body>
</html:html>
<!-- end [001]-->