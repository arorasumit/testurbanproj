<!-- ============================================================================================================ -->
<!-- Tag Name	Resources									Date				CSR No					Description -->
<!-- [001]		Sumit Arora,Anil Kumar & Saurabh Singh		28-july-2011		00-05422				Created Search Parameter list Screen-->
<!-- ============================================================================================================ -->
<!-- start [001]-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.bulkupload.formbean.TransactionUploadFormBean;"%>
<html:html>
<head>
<title>Search Parameter for Download Template</title>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>

<link href="<%=request.getContextPath()%>/gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<script language="javascript" type="text/javascript">
var callerWindowObj = dialogArguments;
var serviceList='';
var addList=new Array();
var filterFlag=0;
var countTotalRow=0;
var countTotalRowSelected=0;
var counter=0;
var selectedAccounts="";
var subChangeTypeId="";
var selectedLSIList="";

function submitLSI()
{			
	var cboSelectedList=document.getElementById('cboParameterListId');		
			serviceList=' ';
			var flag=0;			
			for (i = 0 ; i <addList.length; i++)
    		{       	flag=0;	
    			
						if(cboSelectedList.length>0)
						{							
							for(k=0;k<cboSelectedList.length;k++)
							{								
								if(trim(cboSelectedList.options[k].value)==trim(addList[i]))
								{									
									flag=1;break;
								}								
							}
							if(!flag)
							{
								var option = document.createElement("option");				
								option.text=addList[i];
								option.value=addList[i];
								try 
								{
									cboSelectedList.add(option, null); //Standard
								}
								catch(error) 
								{
									cboSelectedList.add(option); // IE only
								}
							}							
						}
						else
						{
							var option = document.createElement("option");				
							option.text=addList[i];
							option.value=addList[i];
							try 
							{
								cboSelectedList.add(option, null); //Standard
							}
							catch(error) 
							{
								cboSelectedList.add(option); // IE only
							}
						}																						
						counter++;					
					}
				}							

function popitup(url) 
{
	
	if(url=="SelectParty")
		{			
		var accountIDsList=selectedAccounts;
			var cboSelectedList=document.getElementById('cboParameterListId');			
		if(accountIDsList.length>0)
			{
			document.getElementById('txtAreaAccountNo').value="";
			accountIDsList=new Array();
			if(cboSelectedList.length>0 || cboSelectedList.length!=null)
							{
				fnClearParameterList();
							}						
					}										
			
		var path = "<%=request.getContextPath()%>/currencyChange.do?method=fetchParty";		
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:400px");
			}
	if(url=="SelectAccount")
{
		if(document.getElementById('sourcePartyNo').value=="")
		{
			alert("Please Select Party First!!");
			return false;
		}   	
		var partyNo=document.getElementById('sourcePartyNo').value;	
		var path = "<%=request.getContextPath()%>/currencyChange.do?method=fetchAccountWithParty&partyId="+partyNo;		
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:400px");
		} 
	if(url=="SelectLSI")
{
		if(document.getElementById('txtAreaAccountNo').value=="")
		{
			alert("Please Select Account First!!");
			return false;
		}   	
		var accountIDsList=selectedAccounts;
		subChangeTypeId=document.getElementById('hdnSubChangeTypeId').value;		
		if(accountIDsList != ""){
			var path = "<%=request.getContextPath()%>/currencyChange.do?method=fetchLSIWithAccounts&accountIDsList="+accountIDsList;		
			window.showModalDialog(path,window,"status:false;dialogWidth:650px;dialogHeight:550px");
		}else{
			//default LSI
			//var path = "<%=request.getContextPath()%>/currencyChange.do?method=fetchAccountWithParty&partyId="+partyNo;		
			//window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:400px");
		}	
}
}

function doDone()
{
	var cboSelectedList=document.getElementById('cboParameterListId');
	if(cboSelectedList.length==0 || cboSelectedList.length==null)
	{
		alert('List is empty!!!');
	}
	else
	{
		for(i=0;i<cboSelectedList.length;i++)
		{
			callerWindowObj.parameterList[i]=trim(cboSelectedList.options[i].value);
		}			
		callerWindowObj.document.bulkUploadBean.flagParameter.value=1;
		callerWindowObj.document.bulkUploadBean.hdnViewLinkValue.value=1;			
		window.close();
	}
}

function fnClearParameterList()
{
	var cboSelectedList=document.getElementById('cboParameterListId');
	for(i=(cboSelectedList.length-1);i>=0;i--)
	{
		cboSelectedList.remove(i);		
	}
}
function removeFromList()
{
	var cboSelectedList=document.getElementById('cboParameterListId');
	var index=cboSelectedList.selectedIndex;
	if(index!=-1)
	{
		cboSelectedList.remove(index);		
	}
	else
	{
		alert("Please select value from list!!!");
	}	
}
</script>
</head>
<body>
	<div class="head"><span>Search Parameter for Download Template</span></div>
	<html:form action="/transactionUploadAction" styleId="searchForm"  enctype="multipart/form-data">
	<bean:define id="formBean" name="bulkUploadBean"></bean:define>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Details</b></legend>
				<table border="0" width="100%" align="center" style="margin-top:7px">				
					<tr>					
						<td align="left" style="font-size:9px" width="191">Party Number:</td>
						<td align="left" nowrap width="159">
							<html:text  property="sourcePartyNo" styleId="sourcePartyNo" styleClass="inputBorder1" value="" style="width:90px;float:left;" readonly="true" size="60"></html:text>
							<div class="searchBg1"><a href="#" onClick="popitup('SelectParty')">...</a></div> 
						</td>
						<td align="Left" style="font-size:9px" colspan="3" width="607"> Party Name:
		
							<html:text  property="sourcePartyName" value="" styleClass="inputBorder1" style="width:170px;" readonly="true"></html:text>
						</td>
				</tr>
					<tr>					
			
						<td align="left" style="font-size:9px" width="191" height="31">
						<table border="0">
						<tr>
						<td>Select Account Number:</td>
						<td width="37"><div class="searchBg1"><a href="#" onClick="popitup('SelectAccount')">...</a></div></td>
				</tr>
			</table>
			</td>
			
			<td align="left" nowrap colspan="4" height="31"><html:textarea
				rows="10" cols="10" property="accountNo" style="width:400px"
				styleClass="inputBorder1" value="" styleId="txtAreaAccountNo" readonly="true"></html:textarea>
			</td>
			</tr>
					<tr>					
						<td align="left" style="font-size:9px" width="191" height="174">
						<table border="0">
						<tr>
							<td height="26">Select Logical SI:</td>
							<td height="26" width="37"><div class="searchBg1"><a href="#" onClick="popitup('SelectLSI')">...</a></div> </td>
			</tr>
			<tr>
							<!--<td height="26"><input type="checkbox" id="chkDefaultLSI" align="top">Default LSI</td>-->							
				</tr>
						</table>
						</td>
			<td align="left" colspan="4" height="174"><select
				multiple="multiple" size="10" style="width:400px" class="inputBorder1"
				id="cboParameterListId">
			</select>
				<div class="buttonLarge" onClick="javascript:return removeFromList();">Remove From List</div>
				<div class="buttonLarge" onClick="javascript:return fnClearParameterList();">Clear List</div> 
				</td>
			</tr>
			
			<tr class="grayBg">
						<td align="center" colspan="5"><input type="button" onclick="javascript:doDone();" id="btnDoneId" value="Done" /></td>		
			</tr>
					
	</table>
		</fieldset>		
	<input type="hidden" name="hdnMaxLSILength" id="hdnMaxLSILength" value="0"/>
	<html:hidden property="subChangeTypeId" name="formBean"
			styleId="hdnSubChangeTypeId" />
</html:form>
</html:html>
<!-- end [001]-->