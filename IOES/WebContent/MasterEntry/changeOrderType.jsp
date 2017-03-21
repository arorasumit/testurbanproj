<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<html>
<head>
<title>Change Order Type</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

var counter = 1;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}

function fnCheckAll()
{
	ids=document.forms[0].chkbox;
		if(ids==null)
		{	
			alert('No Request Present.');
			document.getElementById('chkHead').checked=false;
			return false;
		}

	var i;
	if(document.getElementById("chkHead").checked==true)
	{	
		if(document.forms[0].chkbox.length!=null)
		{	for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				document.forms[0].chkbox[i].checked=true;
				typeId=document.forms[0].chkbox[i].value;
				document.forms[0].status[i].disabled=false;
			}
		}
		else
		{
			document.forms[0].chkbox.checked=true;
			typeId=document.forms[0].chkbox.value;
			document.forms[0].status.disabled=false;
			
		}
	}
	else
	{
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				document.forms[0].chkbox[i].checked=false;
				typeId=document.forms[0].chkbox[i].value;
				document.forms[0].status[i].disabled=true;
			}
		}
		else
		{
			document.forms[0].chkbox.checked=false;
			typeId=document.forms[0].chkbox.value;
			document.forms[0].status.disabled=true;
		}	
		
	}

}


function removeCheckHead()
{
	javascript:document.getElementById('chkHead').checked=false;
	var temp_chk=0;
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				if(document.forms[0].chkbox[i].checked==true)
				{
					typeId=document.forms[0].chkbox[i].value;
					document.forms[0].status[i].disabled=false;
					temp_chk=temp_chk+1;
				}else if(document.forms[0].chkbox[i].checked==false){
				
				
					typeId=document.forms[0].chkbox[i].value;
					document.forms[0].status[i].disabled=true;
					temp_chk=temp_chk+1;
				
				}
			}
		}
		else 
		{	if(document.forms[0].chkbox.checked==true) {
					typeId=document.forms[0].chkbox.value;
					document.forms[0].status.disabled=false;
					temp_chk=temp_chk+1;
				} else if(document.forms[0].chkbox.checked==false)
		{
		
					typeId=document.forms[0].chkbox.value;
					document.forms[0].status.disabled=true;
					temp_chk=temp_chk+1;
			
		}
		}
		
}
function fnAddNew(){

		document.forms[0].editChangeTypeName.value="";
		document.forms[0].action="<%=request.getContextPath()%>/changeOrder.do?method=loadAddNew";
		document.forms[0].submit();
}
function fnSave(){
			
		if(trim(document.getElementById("editName1").value)==""){
		
		alert("Please Enter Change Type");
		document.getElementById("editName1").value=trim(document.getElementById("editName1").value);
		document.getElementById("editName1").focus();
		return;
		}
		if(document.getElementById("editStatus1").value=="-1"){
		
		alert("Please Select Status");
		return;
		}
		document.forms[0].editChangeTypeName.value=trim(document.getElementById("editName1").value);
		document.forms[0].action="<%=request.getContextPath()%>/changeOrder.do?method=addNewChangeOrder";
		document.forms[0].submit();


}
function fnCancel(){
			
		
		document.forms[0].action="<%=request.getContextPath()%>/changeOrder.do?method=getChangeOrderType";
		document.forms[0].submit();


}
function fnUpdate(){
	
	    var csvVal1="";
		var csvVal2="";
		var csvVal3="";
		var csvVal4="";
		var csvVal5="";
		

		var objCnt="";
		if(document.forms[0].chkbox==null)
		{
		
		return;
		
		}
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++)
			{
				if(document.forms[0].chkbox[i].checked==true)
				{
					
					typeId=document.forms[0].chkbox[i].value;
					
					
					objCnt=document.getElementById('changeTypeId'+typeId);
					val1=objCnt.innerText;
					csvVal1=csvVal1+","+val1;
			
					objCnt=document.getElementById('status'+typeId);
					val2=document.forms[0].status[i].value;
					if (val2=="-1")
					{
					alert("Please select Status");
					return;
					
					}else{
					csvVal2=csvVal2+","+val2;
					}
					
		
					
					
				}
			}
		
		document.forms[0].changeTypeIdList.value=csvVal1;
		document.forms[0].statusList.value=csvVal2;

		}
		else
		{
			if(document.forms[0].chkbox.checked==true)
				{
					
					typeId=document.forms[0].chkbox.value;
					
					
					objCnt=document.getElementById('changeTypeId'+typeId);
					val1=objCnt.innerText;
					csvVal1=csvVal1+","+val1;
			
					objCnt=document.getElementById('status'+typeId);
					val2=document.forms[0].status.value;
					if (val2=="-1")
					{
					alert("Please select Status");
					return;
					
					}else{
					csvVal2=csvVal2+","+val2;
					}
					
		
			
				}
				document.forms[0].changeTypeIdList.value=csvVal1;
				document.forms[0].statusList.value=csvVal2;
			
				
				
		}
		if(document.forms[0].changeTypeIdList.value==0){
		
		alert("Please select an Attribute");
		return false;
		
		}
	
		document.forms[0].action="<%=request.getContextPath()%>/changeOrder.do?method=updateChangeOrderType";
		document.forms[0].submit();
}
function displaySection(flag)
{
	
	
	if(flag=='1') // Type List
	{
		
		tblTypeList.style.display='block';
		tblAddNewType.style.display='none';
		
	}
	if(flag=='2')   // Add Type List
	{
		tblTypeList.style.display='none';
		tblAddNewType.style.display='block';
		
	}
	
}
function bodyLoad()
{
	//check flag here
	var myForm=document.getElementById('searchForm');
	var status=myForm.hiddenFlag.value;
	if(status=='1'){
	
		displaySection(1);
		return;
	
	}else if(status=='2'){
	
		displaySection(2);
		return;
	
	}

	<logic:present name="message">
		alert('<bean:write name="message"/>');
	</logic:present>
	
	<logic:present name="displayListTab">
		displaySection(1);
	</logic:present>
	<logic:present name="displayAddTab">
		displaySection(2);
	</logic:present>
}
path='<%=request.getContextPath()%>';

</script>

<body onload="javascript:bodyLoad();" >
	<html:form action="/changeOrder" styleId="searchForm" method="post">
		<bean:define id="formBean" name="changeOrderTypeBean"></bean:define>
		<input type="hidden" name="method" />
		<html:hidden property="changeTypeIdList"/>
		<html:hidden property="statusList"/>
		<html:hidden property="hiddenFlag"/>
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>Change Order Type</span> </div>
		<div border="1" class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
				<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
		</div>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Add/Edit Change Order Type Details</b></legend>
			<br/>
			
			<table class="tab2" style="overflow:auto;" width="70%" border="1" align="center" id="tblTypeList">
			<tr class="normal">
					<td align="center" style="font-size:9px" width="5%">&nbsp;
						<input type="checkbox" id="chkHead" onclick="fnCheckAll();" class="none" />
					</td>
					<td align="center" style="font-size:9px"><b>Sno</b></td>
					<td align="center" style="font-size:9px"><b>Change Type Name</b></td>
					<td align="center" style="font-size:9px"><b>Status</b></td>
					
			</tr>
				<logic:present name="changeTypeList"  scope="request">
					<logic:notEmpty   name="changeTypeList" scope="request"> 					
						<logic:iterate id="row" name="changeTypeList"  indexId="recordId">
							<%
							String classType=null;
							if((recordId.intValue())%2==0) {
						 classType="lightBg" ;
						 }else{ 
						 classType="normal";
						 } %>
						 <tr class="<%=classType %>">
								<td  align="center" style="font-size:9px" width="5%">
				  					<input type="checkbox" id="chkbox" onclick="javascript:removeCheckHead();" class="none" value="<bean:write name="row" property="changeTypeId"/>"/>
				  				</td>	
								<td  width="10%">&nbsp;
									<bean:write name="row" property="changeTypeId"/>
								</td>
								<td  width="40%">&nbsp;
									<input type="text" width="20%" id="changeTypeName<bean:write name="row" property="changeTypeId"/>" style="width:80%;"  class="inputBorder1"  disabled="true" name="changeTypeName" value="<bean:write  name="row" property="changeTypeName"/>" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'ChangeTypeName')}else{alert('Please Enter ChangeTypeName'); this.focus();return;};"/>
								</td width="10%">
								<td align="center">&nbsp;
									<select name="status" class="inputBorder4" style="width:100px;"  disabled="disabled" id="status<bean:write  name="row" property="changeTypeId"/>">
										<option value="-1">--- Select ---</option>
										<option value="1" <logic:equal value="1" name="row"  property="status">selected="selected" </logic:equal> >Active</option>
										<option value="0" <logic:equal value="0" name="row"  property="status">selected="selected" </logic:equal> >InActive</option>
									</select> 
								</td>
								<div style="display: none" >
									<span id="changeTypeId<bean:write name="row" property="changeTypeId"/>"><bean:write name="row" property="changeTypeId"/></span>
									<span id="changeTypeName<bean:write name="row" property="changeTypeId"/>"> </span>
									<span id="status<bean:write name="row" property="changeTypeId"/>"><bean:write name="row" property="status"/></span>
								</div>
						</tr>
				</logic:iterate>
					<tr class="tableTd" >
							<td   colspan="4" align="center" nowrap="nowrap" >
								<input type = "button" value="Add New" onclick="javascript:fnAddNew();" /> 
								<input type = "button" value="Update" onclick="javascript:fnUpdate();" /> 
							</td>
							
					</tr>		
			</logic:notEmpty>
			<logic:empty  name="changeTypeList">
				   <tr align="center" >
					 <td colspan="4" align="center"><B><font color="red">No Record Found</font></B></td>
				   </tr>
			</logic:empty>	
		</logic:present>				
	</table>
	
	<table class="tab2" style="overflow:auto; display:none" width="70%" border="1" align="center" id="tblAddNewType" >
			<tr>
				<td  align="left" style="font-size:17px" nowrap="nowrap">Add New change Order Type</td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" align="center" cellspacing="1" width="100%" border="1">
				
						<tr height="20px">
						<td width="50%" align="right">&nbsp;Change Order Type</td>
						<td align="left" style="font-size:9px"><html:text  property="editChangeTypeName" styleId="editName1" styleClass="inputBorder1" style="width:100px;" maxlength="200" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Change Type')};"/></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right">&nbsp;Status</td>
						<td align="left" style="font-size:9px">
							<select name="editStatus" class="inputBorder4" style="width:100px;" id="editStatus1">
										<option value="-1">--- Select ---</option>
										<option value="1" >Active</option>
										<option value="0" >InActive</option>
									</select> 
									
								</td>
						<tr class="tableTd" >
							<td   colspan="2" align="center" nowrap="nowrap" >
								<input type = "button" value="Save" onclick="javascript:fnSave();" /> 
								<input type = "button" value="Cancel" onclick="javascript:fnCancel();" /> 
								
							</td>
						</tr>	
		
					</table>
				</td>
			</tr>
	</table>		
</fieldset>
</html:form>
</body>
</html>
