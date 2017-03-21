<!-- ============================================================================================================ -->
<!-- Tag Name	Resources						Date				CSR No					Description -->
<!-- [001]		Sumit Arora & Anil Kumar		28-july-2011		00-05422				Created Bulkupload Screen-->
<!-- ============================================================================================================ -->
<!-- [003]		Rohit Verma						6-Feb-14			Rate Revision Bulk Upload-BCP Search -->
<!-- [004]    Priya Gupta -->
<!-- start [001]-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html:html>
<head>
<title>iB2B Bulk Upload</title>
<link href="<%=request.getContextPath()%>/gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
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
var parameterList=new Array();
function window.confirm(str)
{
    execScript('n = msgbox("'+str+'","4132","Confirm Action")', "vbscript");
    return(n == 6);
}
function goToHome()
{
	var myForm=document.getElementById('searchForm');	
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
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


function downloadDump(fileName)
{
	document.forms[0].transactionType.style.visibility="hidden";
	var myForm=document.getElementById('searchForm');	
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=getDumpFile&fileName="+fileName;
	myForm.submit();
	
}	


function doSubmitTransactionType()
{
	var w = document.forms[0].transactionType.selectedIndex;
    var selected_text = document.forms[0].transactionType.options[w].text;    
    if(document.forms[0].transactionType.value!=0)
	{
		document.bulkUploadBean.lsiList.value="";	
		document.bulkUploadBean.methodName.value='getSelectedTransactionDetails';
		document.bulkUploadBean.transactionName.value = selected_text;
		document.bulkUploadBean.submit();
	}
	else
	{
		document.bulkUploadBean.methodName.value='goToBulkUpload';
		document.bulkUploadBean.submit();
	}	
	
}

function downloadTemplate()
{
	
	if(document.forms[0].transactionType.value==0)
	{
		alert("Please select Transaction Type !!");
		return false;
	}
	else
	{
		//xlsfilename=filename;
		document.forms[0].transactionType.style.visibility="hidden";
		showLayer();
		document.bulkUploadBean.methodName.value='getTemplate';
		document.bulkUploadBean.fileToDownload.value='';
		document.bulkUploadBean.submit();
	}
}

function getErrorFile()
{
	document.forms[0].transactionType.style.visibility="hidden";
	document.bulkUploadBean.methodName.value='getErrorLog';
	document.bulkUploadBean.submit();
}

function doUploadFile()
{
	if(document.forms[0].transactionType.value==0)
	{
		alert("Please Select Subchange Type !!");
		return false;
	}
	if(document.bulkUploadBean.newFile.value=="")
	{
		alert("Please Select the file");
		return false;
	}
	else if(document.bulkUploadBean.newFile.value.length>255)
	{
		alert("File name cannot be longer than 255 characters");
		return false;
	}
	else if(!(/^.*\.xls$/.test(document.bulkUploadBean.newFile.value))) 
	{
		//Extension not proper.
		alert("Please select a .xls file only.");
		return false;
	}
	else if(checkFilenameWithQuotes(document.bulkUploadBean.newFile.value)){
		alert("Invlid filename! please remove single quote from filename.");
		return false;
	}
	else
	{
	
		document.bulkUploadBean.checkStatus.value=1;
		
		document.getElementById('uploadWhite').style.display='none';
		document.getElementById('uploadYellow').style.display='';
		document.getElementById('uploadGreen').style.display='none';
		
		document.getElementById('validateWhite').style.display='';
		document.getElementById('validateYellow').style.display='none';
		document.getElementById('validateGreen').style.display='none';
		
		document.getElementById('processWhite').style.display='';
		document.getElementById('processYellow').style.display='none';
		document.getElementById('processGreen').style.display='none';
		
		document.forms[0].transactionType.style.visibility="hidden";
	
		showLayer();
		//document.forms[0].filePath.value=document.bulkUploadBean.newFile.value;
 		var myform=document.getElementById("searchForm");
 		myform.action="<%=request.getContextPath()%>/transactionUploadAction.do?methodName=uploadFile";
		myform.submit();		
	}	
}


function getReferenceMasterData()
{
	var i=1;
	document.bulkUploadBean.selectedReferenceId.value="";
	
	while(eval("document.bulkUploadBean.masterId"+i))
	{
		if(eval("document.bulkUploadBean.masterId"+i))
		{
		
			if(eval("document.bulkUploadBean.masterId"+i).checked)
			{
				if(document.bulkUploadBean.selectedReferenceId.value=="")
				{
					document.bulkUploadBean.selectedReferenceId.value = '&&&'+eval("document.bulkUploadBean.masterId"+i).value;
				}
				else
				{
					document.bulkUploadBean.selectedReferenceId.value = document.bulkUploadBean.selectedReferenceId.value+'&&&'+eval("document.bulkUploadBean.masterId"+i).value;
				}
			}
		}
		i=i+1;
	}
	if(document.bulkUploadBean.selectedReferenceId.value=="")
	{
		alert("Please select atleast one master to get data");
		return false;
	}
	showLayer();
	document.forms[0].transactionType.style.visibility="hidden";
	document.bulkUploadBean.methodName.value='getReferenceMasterData';
	document.bulkUploadBean.submit();	
	
}

function downloadCompleteReference()
{
	if(document.getElementById('hdnViewLinkValue').value==1)
	{
		document.getElementById('viewSelParameterId').style.display='block';
		document.getElementById('cancelParameterListId').style.display='block';
		document.getElementById('searchParameterId').style.display='none';
	}
	document.forms[0].transactionType.style.visibility="hidden";
	showLayer();
	document.bulkUploadBean.methodName.value='getReferenceMasterData';
	document.bulkUploadBean.submit();
	if(document.getElementById('hdnViewLinkValue').value==1)
	{
		document.getElementById('viewSelParameterId').style.display='block';
		document.getElementById('cancelParameterListId').style.display='block';
		document.getElementById('searchParameterId').style.display='none';
	}
}

function doLoad()
{
	
		
	if(document.bulkUploadBean.methodName.value=="uploadFile"&&document.bulkUploadBean.processStatus.value=="1")
	{
		document.getElementById('uploadWhite').style.display='none';
		document.getElementById('uploadGreen').style.display='';
		if(document.bulkUploadBean.processStatus.value=="1")
		{
			if(confirm("File uploaded successfully!! Do you want to proceed with validating the data?"))
			{
				showLayer();
				document.getElementById('validateWhite').style.display='none';
				document.getElementById('validateYellow').style.display='block';
				document.forms[0].transactionType.style.visibility="hidden";
				document.bulkUploadBean.methodName.value='validateData';
				document.bulkUploadBean.submit();				
			}
		}		
	}
	else if(document.bulkUploadBean.methodName.value=="uploadFile"&&document.bulkUploadBean.processStatus.value=="0")
	{
			alert('File could not be uploaded.');
	}
	
	if(document.bulkUploadBean.methodName.value=="validateData")
	{
		document.getElementById('uploadWhite').style.display='none';
		document.getElementById('uploadGreen').style.display='';
		if(document.bulkUploadBean.processStatus.value=="3")
		{	
			document.getElementById('validateWhite').style.display='none';
			document.getElementById('validateGreen').style.display='';
			alert("File validated successfully!! Scheduler will run to proceed Data");
		}
		else if(document.bulkUploadBean.processStatus.value=="2")
		{
			alert('File contains invalid data');
		}	
	}
	
	if(document.bulkUploadBean.methodName.value=="getCompleteReferenceMasterData"||document.bulkUploadBean.methodName.value=="getReferenceMasterData"||document.bulkUploadBean.methodName.value=="getErrorLog")
	{
		document.bulkUploadBean.methodName.value='doDownLoadFile';
		document.bulkUploadBean.submit();
	}
	if(document.bulkUploadBean.methodName.value=="downloadTemplate" &&document.bulkUploadBean.processStatus.value=="1")
	{
		document.bulkUploadBean.methodName.value='doDownLoadFile';
		document.bulkUploadBean.submit();		
	}
	else if(document.bulkUploadBean.methodName.value=="downloadTemplate" &&document.bulkUploadBean.processStatus.value=="0")
	{
		alert('Template of this subchange type is not available.');
	}
	else if(document.bulkUploadBean.methodName.value=="getTemplate")
	{		
		document.bulkUploadBean.methodName.value='doDownLoadFile';
		document.bulkUploadBean.submit();
	}
	
}


function CheckAll(chk)
{
var i=1;
while(eval("document.bulkUploadBean.masterId"+i))
{
	eval("document.bulkUploadBean.masterId"+i).checked=chk;
	i=i+1;
}
}


function SearchCriteria()
{
	if(document.forms[0].transactionType.value==0)
	{
		alert("Please Select Subchange Type !!");
		return false;
	}
	var subChangeTypeId=document.forms[0].transactionType.value;
	var path = "<%=request.getContextPath()%>/transactionUploadAction.do?methodName=searchFor&subChangeTypeId="+subChangeTypeId;		
	window.showModalDialog(path,window,"status:false;dialogWidth:1500px;dialogHeight:1300px");
	
	if(document.getElementById('hdnViewLinkValue').value==1)
	{
		document.getElementById('viewSelParameterId').style.display='block';
		document.getElementById('cancelParameterListId').style.display='block';
		document.getElementById('searchParameterId').style.display='none';
		//document.bulkUploadBean.lsiList.value="";
		for(i=0;i<parameterList.length;i++)
		{
			document.bulkUploadBean.lsiList.value=document.bulkUploadBean.lsiList.value+trim(parameterList[i])+",";
		}
		//document.getElementById('viewSelListId').value=document.bulkUploadBean.lsiList.value;
	}
	else
	{
		 document.getElementById('viewSelParameterId').style.display='none';
		 document.bulkUploadBean.lsiList.value="";
	}	
}
function CancelParameterList()
{
	document.bulkUploadBean.lsiList.value="";
	parameterList.length=0;
	document.getElementById('hdnViewLinkValue').value=0;
	document.getElementById('viewSelParameterId').style.display='none';
	document.getElementById('searchParameterId').style.display='block';
	document.getElementById('cancelParameterListId').style.display='none';		
}
function popupViewValue()
{
	var path = "<%=request.getContextPath()%>/transactionUploadAction.do?methodName=viewSelectedValue";		
	window.showModalDialog(path,window,"status:false;dialogWidth:400px;dialogHeight:300px");
}
window.onresize = setLayerPosition;

//[002] Start
//[004]
//function downloadLineItemDump()
function downloadLineItemDump(actiontype)
{
	var myForm=document.getElementById('searchForm');
	<%--myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=fetchTBTLineItemData; --%>
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=fetchTBTLineItemData&actiontype="+actiontype; 
	showLayerExcel();
	myForm.submit();
}
//[003] Start
function FetchBCP()
{
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchAccount&source=bulkupload";
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:500px");
}
//[003] End
//[below utility method check wheather file name have single quote::By:Anil Kumar::Date:18-June-2014]
function checkFilenameWithQuotes(filename){
	var index=0,flag=0;
	var foundChar;
	for(index=0;index <(filename.length);index++){
		foundChar=(filename).charAt(index);
		if(foundChar == "\'" || foundChar == "\""){
			flag=1;break;
		}
	}
	if(flag==1){		
		return true;
	}else{
		return false;
	}
}

window.onresize = setLayerPosition;
</script>
</head>
<body onload="javascript:doLoad();">
<div id="shadow" class="opaqueLayer"> 
		<br /><br /><br /><br /><br /><br /><br /><br />
	    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
	    <br /><br /><br />
	</div>
	<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">				
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
	</table>
	<div class="head"><span>Bulk Upload</span></div>
	<html:form action="/transactionUploadAction" styleId="searchForm"  enctype="multipart/form-data">
		<table border="0" align="center" width="80%" >
		
			<tr><td align="center">&nbsp;</td></tr>
			<logic:notEmpty name="bulkUploadBean" property="message">
				<tr class="successMsg">
					<td align="center"><font color="green"><bean:write name="bulkUploadBean" property="message" /></font></td>
				</tr>
			</logic:notEmpty>	
			<logic:notEmpty name="bulkUploadBean" property="errorMessage">
				<tr class="errMsg">
					<td align="center"><font color="red"><bean:write name="bulkUploadBean" property="errorMessage" /></font></td>
				</tr>
			</logic:notEmpty>
			<logic:notEmpty name="bulkUploadBean" property="fileLink">
				<logic:notEqual value="0" name="bulkUploadBean" property="processStatus">
				<tr class="errMsg">
					<td align="left">
							<a href="#" onclick="javascript:getErrorFile();" class="fileLink"><font color="blue"><bean:write name="bulkUploadBean" property="fileLink" /></font></a>
					</td>
				</tr>	
				<tr class="errMsg">	
					<td align="left"><font color="red">* Please Click on the filename to view file with errors</font></td>
				</tr>
				</logic:notEqual>
			</logic:notEmpty>
			<logic:notEmpty name="bulkUploadBean" property="msgList">
				<logic:iterate id="uploadErrors" name="bulkUploadBean" property="msgList">
					<tr class="errMsg">
						<td align="left" nowrap="nowrap">&nbsp;						
							<font color="red"><bean:write name="uploadErrors" property="errMsgOrder"/>.&nbsp;<bean:write name="uploadErrors" property="errMsgString"/></font>
						</td>	
					</tr>	
				</logic:iterate>
			</logic:notEmpty>
			<tr><td align="center">&nbsp;</td></tr>
			
		</table><br>
		<table width="80%" border="0" cellspacing="0" cellpadding="0" height="15" align="center" >
			<tr class="head">
				<td align="center">Progress Bar</td>
			</tr>
		</table>
		
		
		<table width="80%"  border="0" cellspacing="0" cellpadding="0" height="25" align="center" >
			<tr bgcolor="white">
				<td align="center" width="20%">
					<img src="<%=request.getContextPath()%>/images/whiteCircle.GIF" height="30" style="display:block;" id="uploadWhite">
					<img src="<%=request.getContextPath()%>/images/yellowCircle.GIF" height="30" style="display:none;" id="uploadYellow">
					<img src="<%=request.getContextPath()%>/images/greenCircle.GIF" height="30" style="display:none;" id="uploadGreen">
				</td>
				<td align="center" width="20%">
					<img src="<%=request.getContextPath()%>/images/next.gif" height="20" style="background-repeat: repeat-x" >
				</td>
				<td align="center" width="20%">
					<img src="<%=request.getContextPath()%>/images/whiteCircle.GIF" height="30" style="display:block;" id="validateWhite">
					<img src="<%=request.getContextPath()%>/images/yellowCircle.GIF" height="30" style="display:none;" id="validateYellow">
					<img src="<%=request.getContextPath()%>/images/greenCircle.GIF" height="30" style="display:none;" id="validateGreen">
				</td>
				<td align="center" width="20%">
					<img src="<%=request.getContextPath()%>/images/next.gif" height="20" style="background-repeat: repeat-x" >
				</td>
				<td align="center" width="20%">
					<img src="<%=request.getContextPath()%>/images/whiteCircle.GIF" height="30" style="display:block;" id="processWhite">
					<img src="<%=request.getContextPath()%>/images/yellowCircle.GIF" height="30" style="display:none;" id="processYellow">
					<img src="<%=request.getContextPath()%>/images/greenCircle.GIF" height="30" style="display:none;" id="processGreen">
				</td>	
			</tr>
			<tr class="head">
				<td align="center">
					UPLOADING
				</td>
				<td width="20%" >&nbsp;</td>
				<td align="center">
					VALIDATING
				</td>
				<td  width="20%">&nbsp;</td>
				<td align="center">
					PROCESSING
				</td>
			</tr>
		</table>							
		<br>
		<table border="0" align="center" width="80%" style="height: 50px" >
			<tr valign="middle">
				
				<td align="center">Select Subchange Type :
					<html:select property="transactionType" onchange="javascript:doSubmitTransactionType();" styleClass="inputBorder1">
						<logic:notEmpty name="bulkUploadBean" property="transactionList">				
							<html:option value="0">------------------- Subchange Type -------------------</html:option>
							<html:optionsCollection name="bulkUploadBean" property="transactionList" value="strTransactionID" label="strTransactionName" ></html:optionsCollection>
						</logic:notEmpty>
						<logic:empty name="bulkUploadBean" property="transactionList">				
							<option value="0">--------------No Subchange Type-------------</option>
						</logic:empty>
					</html:select>
				</td>	
			</tr>
		</table>
		<table border="0" align="center" width="80%" > 	
			<tr class="head">
				<td>&nbsp;</td>
			</tr>
		</table> 
		<table border="0" align="center" width="80%" > 	
			<tr>
				<td align="center" width="50%" style="border-right: thin;border-right-color: #610B0B;border-right-style: solid;border-right-width: thin" class="heading">
					Upload Excel
				</td>
				<td align="center" width="50%" class="heading">
					Master Reference
				</td>
			</tr>
			<tr valign="top">
				<td align="center" width="50%" style="border-right: thin;border-right-color: #610B0B;border-right-style: solid;border-right-width: thin">
						<table width="100%" valign="top" class="tableFont">
							<tr valign="top">
								<td>&nbsp;</td>
							</tr>
							
							<tr valign="top">	
								<td>
									Select File to Upload : <html:file property="newFile" styleClass="inputBorder1" onkeypress="javascript: return false;" onkeydown="javascript: return false;" onkeyup="javascript: return false;" onmousedown="javascript:return checkMouseEvent();" />
								</td>	
							</tr>
							<tr valign="top">
								<td align="right">
									<div class="buttonLarge" onClick="javascript:return doUploadFile();">Upload</div> 
									<!-- <div id="searchParameterId"><a href="#" onClick="javascript:SearchCriteria();"> Search Parameter List for Download Template</a></div> 
									
									<div id="cancelParameterListId" style="display:none"><a href="#" onClick="javascript:CancelParameterList();"> Cancel Parameter List</a></div> 
									<div id="viewSelParameterId" style="display:none"><a href="#" onclick="popupViewValue()">View Selected Parameter List</a></div> --> 
								</td>
							</tr>
							<tr valign="top">
								<td>&nbsp;</td>
							</tr>
							<!--  <tr valign="top">	
								<td align="left" nowrap="nowrap">* Please remove validation comments before uploading file</td>
							</tr> -->
							<tr valign="top">	
								<td align="left" nowrap="nowrap">* Maximum of 255 characters per cell will be read</td>
							</tr>
							<tr valign="top">
								<td>&nbsp;</td>
							</tr>
							<logic:notEmpty name ="bulkUploadBean" property="transactionType">
								<logic:notEqual value="0" name ="bulkUploadBean" property="transactionType">
									<tr valign="top">
										<td align="center"><a href="#" onclick="javascript:return downloadTemplate();" class="fileLink">Download Template</a></td>
									</tr>
									<tr valign="top">
										<td align="center">
											<table width="100%" class="tableFont" cellpadding="4" cellspacing="2">
											<tr>
												<td align="center" colspan="2"><b>Legends for excel sheet template</b></td>
											</tr>
											<tr>
												<td bgcolor="#A9F5D0" width="20%">&nbsp;&nbsp;&nbsp;</td>
												<td>: Mandatory fields</td>								
											</tr>
											<tr>
												<td bgcolor="#00FFFF" width="20%">&nbsp;&nbsp;&nbsp;</td>
												<td>: Non-Mandatory fields</td>												
											</tr>
											<tr>
												<td bgcolor="#FE9A2E" width="20%">&nbsp;&nbsp;&nbsp;</td>
												<td>: Non-Mandatory or Mandatory fields</td>										
											</tr>
											
											</table>
										</td>
									</tr>
									</logic:notEqual>
									<!-- [003] Start -->
									<logic:equal value="25" name ="bulkUploadBean" property="transactionType">
										<logic:present name="bulkUploadBean" property="isDumpAvailable">
											<logic:equal name="bulkUploadBean" property="isDumpAvailable" value="Y">
												<tr valign="top">
													<div id="searchParameterId" align="center"><a href="#" onClick="javascript:FetchBCP();">Download BCP/Dispatch for an Account</a></div>
												</tr>
											</logic:equal>
										</logic:present>
									</logic:equal>
									<!-- [003] End -->
									<!-- [004] Start -->
									<logic:equal  value="190" name ="bulkUploadBean" property="transactionType">										
										<tr valign="top">
											<div id="searchParameterId" align="center"><a href="#" onClick="javascript:FetchBCP();">Download BCP/Dispatch for an Account</a></div>
										</tr>											
									</logic:equal>
									<!-- [004] End -->
									<logic:equal value="201" name ="bulkUploadBean" property="transactionType">
										<logic:present name="bulkUploadBean" property="isDumpAvailable">
											<logic:equal name="bulkUploadBean" property="isDumpAvailable" value="Y">
									<!-- [002] Start -->
									<tr valign="top">
										<td align="center"><a href="#" onclick="javascript:return downloadDump('<bean:write name="bulkUploadBean" property="dumpFileName"/>');" title="Provides Day-1 Dump.Use when billing trigger is being done for 1st time in a day" class="fileLink">Download Dump(Day-1)</a></td>
									</tr>
									<!-- [004] start -->
									<tr valign="top">
										<td align="center"><a href="#" onclick="javascript:return downloadLineItemDump(1);" title="Generates Real time Dump.Use when billing trigger is being done for more than 2nd time in a day" class="fileLink">Generate Dump(Real Time)</a></td>
									</tr>
									
									<tr valign="top">
										<td align="center"><a href="#" onclick="javascript:return downloadLineItemDump(2);" title="Generates Real time Dump.Use when billing trigger is being done for more than 2nd time in a day" class="fileLink">Generate Automatic Dump(Real Time)</a></td>
									</tr>
									<!-- [004] end -->
									</logic:equal>
									</logic:present>
									<tr valign="top">
										<td align="center">
											<table width="100%" class="tableFont" cellpadding="4" cellspacing="2">
											<tr>
												<td align="center" colspan="2"><b>Legends for excel sheet template</b></td>
											</tr>
											<tr>
												<td bgcolor="#f0e68c" width="20%">&nbsp;&nbsp;&nbsp;</td>
												<td>: For Input Purpose</td>								
											</tr>
											<tr>
												<td bgcolor="#2e8b57" width="20%">&nbsp;&nbsp;&nbsp;</td>
												<td>: For Information Purpose</td>												
											</tr>
											
											</table>
										</td>
									</tr>
									</logic:equal>
								
							</logic:notEmpty>		
						</table>
				</td>
				<td align="center" width="50%">
				<%String []colors=new String[]{"lightBg","normal"}; %>
						<table width="100%" class="tab2">
							<tr>
								<td align="center" colspan="2" width="100%">&nbsp;</td>
							</tr>
							<logic:notEmpty name="bulkUploadBean" property="masterReferenceList">
							<tr>
								<td align="center" nowrap="nowrap" colspan="2">
									<a href="#" onclick="javascript:return getReferenceMasterData();" class="fileLink">Download Master Reference</a>
								</td>
							</tr>
							</logic:notEmpty>
							<tr>
								<td align="center" colspan="2" width="100%">&nbsp;</td>
							</tr>
							<logic:empty name="bulkUploadBean" property="masterReferenceList">
								<logic:empty name ="bulkUploadBean" property="transactionType">
								<tr>
									<td align="center" colspan="2" class="errMsg">No Transaction Selected</td>
								</tr>	
								</logic:empty>
								<logic:notEmpty name ="bulkUploadBean" property="transactionType">
									<logic:notEqual value="0" name ="bulkUploadBean" property="transactionType">
									<tr>
										<td align="center" colspan="2" class="errMsg">No Reference Master for Selected Transaction</td>
									</tr>	
									</logic:notEqual>
									<logic:equal value="0" name ="bulkUploadBean" property="transactionType">
									<tr>
										<td align="center" colspan="2" class="errMsg">No Transaction Selected</td>
									</tr>	
									</logic:equal>
								</logic:notEmpty>	
							</logic:empty>
							<logic:notEmpty name="bulkUploadBean" property="masterReferenceList">
								<tr>
								<td>&nbsp;</td>				
									<td colspan="4" align="left" nowrap="nowrap">
										<a href="#" onclick="javascript:CheckAll(true);" class="fileLink">Select All</a>&nbsp;|&nbsp;<a href="#" onclick="javascript:CheckAll(false);" class="fileLink">Deselect All</a>
									</td>
								</tr>
								
								<logic:iterate id="referenceMaster" name="bulkUploadBean" indexId="p" property="masterReferenceList">
									<tr class="<%=colors[p.intValue()%2]%>">									
										<td nowrap="nowrap" width="15%" align="right"><input type="checkbox" name="masterId<%=(p.intValue()+1)%>" value='<bean:write name="referenceMaster" property="referenceMasterID"/>#<bean:write name="referenceMaster" property="referenceMasterName"/>'></td>
										<td nowrap="nowrap" width="85%" align="left"><bean:write name="referenceMaster" property="referenceMasterName"/></td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
							
							<logic:notEmpty name="bulkUploadBean" property="masterReferenceList">
								<tr valign="top">
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
									
										<input type="submit" value=" Download Latest Master " class="buttonCSS" onClick="javascript:return getReferenceMasterData();">
									</td>	
								</tr>
								<tr valign="top">
									<td>&nbsp;</td>
								</tr>	
							</logic:notEmpty>
						</table>	
				</td>			
			</tr>
		</table>
		<table border="0" align="center" width="80%" >
			<tr class="head">
				<td>&nbsp;</td>
			</tr>
		</table> 
		<html:hidden property="checkStatus" value="0"/>
		<html:hidden property="processStatus"/>
		<html:hidden property="fileID"/>
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>	
		<html:hidden property="filePath"/>
		<html:hidden property="methodName"/>
		<html:hidden property="selectedReferenceId" value=""/>
		<html:hidden property="transactionName"/>
		<html:hidden property="transactionTemplate"/>
		<html:hidden property="templateId"/>	
		<html:hidden property="fileToDownload"/>
		<html:hidden property="lsiList"/>
		<html:hidden property="flagParameter"/>
		<input type="hidden" id="hdnSelectedParameterValueList"/>
		<input type="hidden" id="hdnOptionValue"/>		
		<html:hidden property="hdnViewLinkValue" styleId="hdnViewLinkValue"/>
	</html:form>	
</body>
</html:html>
<!-- end [001]-->