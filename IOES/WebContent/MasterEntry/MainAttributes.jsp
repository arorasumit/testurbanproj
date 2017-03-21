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
<title>Main Attributes</title>
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
				attributeId=document.forms[0].chkbox[i].value;
				document.getElementById('attDescription'+attributeId).disabled=false;
				document.forms[0].attDataType[i].disabled=false;
				document.forms[0].attExpectedValue[i].disabled=false;
				document.forms[0].attIsmandatory[i].disabled=false;
			}
		}
		else
		{
			document.forms[0].chkbox.checked=true;
			attributeId=document.forms[0].chkbox.value;
			document.getElementById('attDescription'+attributeId).disabled=false;
			document.forms[0].attDataType.disabled=false;
			document.forms[0].attExpectedValue.disabled=false;
			document.forms[0].attIsmandatory.disabled=false;
			
		}
	}
	else
	{
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				document.forms[0].chkbox[i].checked=false;
				attributeId=document.forms[0].chkbox[i].value;
				document.getElementById('attDescription'+attributeId).disabled=true;
				document.forms[0].attDataType[i].disabled=true;
				document.forms[0].attExpectedValue[i].disabled=true;
				document.forms[0].attIsmandatory[i].disabled=true;
			}
		}
		else
		{
			document.forms[0].chkbox.checked=false;
			attributeId=document.forms[0].chkbox.value;
			document.getElementById('attDescription'+attributeId).disabled=true;
			document.forms[0].attDataType.disabled=true;
			document.forms[0].attExpectedValue.disabled=true;
			document.forms[0].attIsmandatory.disabled=true;
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
					attributeId=document.forms[0].chkbox[i].value;
					document.getElementById('attDescription'+attributeId).disabled=false;
					document.forms[0].attDataType[i].disabled=false;
					document.forms[0].attExpectedValue[i].disabled=false;
					document.forms[0].attIsmandatory[i].disabled=false;
					temp_chk=temp_chk+1;
				}else if(document.forms[0].chkbox[i].checked==false){
				
				
					attributeId=document.forms[0].chkbox[i].value;
					document.getElementById('attDescription'+attributeId).disabled=true;
					document.forms[0].attDataType[i].disabled=true;
					document.forms[0].attExpectedValue[i].disabled=true;
					document.forms[0].attIsmandatory[i].disabled=true;
					temp_chk=temp_chk+1;
				
				}
			}
		}
		else 
		{	if(document.forms[0].chkbox.checked==true) {
					attributeId=document.forms[0].chkbox.value;
					document.getElementById('attDescription'+attributeId).disabled=false;
					document.forms[0].attDataType.disabled=false;
					document.forms[0].attExpectedValue.disabled=false;
					document.forms[0].attIsmandatory.disabled=false;
					temp_chk=temp_chk+1;
				} else if(document.forms[0].chkbox.checked==false)
		{
		
					attributeId=document.forms[0].chkbox.value;
					document.getElementById('attDescription'+attributeId).disabled=true;
					document.forms[0].attDataType.disabled=true;
					document.forms[0].attExpectedValue.disabled=true;
					document.forms[0].attIsmandatory.disabled=true;
					temp_chk=temp_chk+1;
			
		}
		}
		
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
					
					attributeId=document.forms[0].chkbox[i].value;
					
					
					objCnt=document.getElementById('attID'+attributeId);
					val1=objCnt.innerText;
					csvVal1=csvVal1+","+val1;
					
					objCnt=document.getElementById('attDescription'+attributeId);
					val2=objCnt.value;
					csvVal2=csvVal2+","+val2;
														
					val3=document.forms[0].attDataType[i].value;
					if (val3=="0")
					{
					alert("Please select DataType");
					return;
					
					}else{
					csvVal3=csvVal3+","+val3;
					}
					
					val4=document.forms[0].attExpectedValue[i].value;
					if (val4=="0")
					{
					alert("Please select Expected Value");
					return;
					
					}else{
					csvVal4=csvVal4+","+val4;
					}
			
				
					objCnt=document.getElementById('attIsmandatory'+attributeId);
					val5=document.forms[0].attIsmandatory[i].value;
					if (val5=="0")
					{
					alert("Please select Mandatory Value");
					return;
					
					}else{
					csvVal5=csvVal5+","+val5;
					}
					
		
					
					
				}
			}
		
		document.forms[0].attIdList.value=csvVal1;
		document.forms[0].attDescList.value=csvVal2;
		document.forms[0].attDataTypeList.value=csvVal3;
		document.forms[0].attExpValList.value=csvVal4;
		document.forms[0].attMandatoryList.value=csvVal5;

		}
		else
		{
			if(document.forms[0].chkbox.checked==true)
				{
					
					attributeId=document.forms[0].chkbox.value;
					
					
					objCnt=document.getElementById('attID'+attributeId);
					val1=objCnt.innerText;
					csvVal1=csvVal1+","+val1;
					
					objCnt=document.getElementById('attDescription'+attributeId);
					val2=objCnt.value;
					csvVal2=csvVal2+","+val2;
					
						
					val3=document.forms[0].attDataType.value;
					if (val3=="0")
					{
					alert("Please select DataType");
					return;
					
					}else{
					csvVal3=csvVal3+","+val3;
					}
					
					val4=document.forms[0].attExpectedValue.value;
					if (val4=="0")
					{
					alert("Please select Expected Value");
					return;
					
					}else{
					csvVal4=csvVal4+","+val4;
					}
			
				
					objCnt=document.getElementById('attIsmandatory'+attributeId);
					val5=document.forms[0].attIsmandatory.value;
					if (val5=="0")
					{
					alert("Please select Mandatory Value");
					return;
					
					}else{
					csvVal5=csvVal5+","+val5;
					}
					
		
			
				}
				document.forms[0].attIdList.value=csvVal1;
				document.forms[0].attDescList.value=csvVal2;
				document.forms[0].attDataTypeList.value=csvVal3;
				document.forms[0].attExpValList.value=csvVal4;
				document.forms[0].attMandatoryList.value=csvVal5;
				
				
		}
		if(document.forms[0].attIdList.value==0){
		
		alert("Please select an Attribute");
		return false;
		
		}
	
		document.forms[0].action="<%=request.getContextPath()%>/mainAttributesAction.do?method=updateAttributes";
		document.forms[0].submit();
}

path='<%=request.getContextPath()%>';
</script>

<body >
	<html:form action="/mainAttributesAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="masterAttributesBean"></bean:define>
		<input type="hidden" name="method" />
		<html:hidden property="attIdList"/>
	<html:hidden property="attDescList"/>
	<html:hidden property="attDataTypeList"/>
	<html:hidden property="attExpValList"/>
	<html:hidden property="attMandatoryList"/>
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>MAIN ATTRIBUTES</span> </div>
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
			<legend> <b>Main Attribute Details</b></legend>
			<br/>
			
			<table class="tab2" style="overflow:auto;" width="99%"  border="1" align="center">
			<tr class="normal">
					<td align="center" style="font-size:9px" width="5%">&nbsp;
						<input type="checkbox" id="chkHead" onclick="fnCheckAll();" class="none" />
					</td>
					<td align="center" style="font-size:9px"><b>Label</b></td>
					<td align="center" style="font-size:9px"><b>Data Type</b></td>
					<td align="center" style="font-size:9px"><b>Alias Name</b></td>
					<td align="center" style="font-size:9px"><b>Expected Value</b></td>
					<td align="center" style="font-size:9px"><b>Max Length</b></td>
					<td align="center" style="font-size:9px"><b>Is Mandatory</b></td>
					
			</tr>
				<logic:present name="attributeList"  scope="request">
					<logic:notEmpty   name="attributeList" scope="request"> 					
						<logic:iterate id="row" name="attributeList"  indexId="recordId">
							<%
							String classType=null;
							if((recordId.intValue())%2==0) {
						 classType="lightBg" ;
						 }else{ 
						 classType="normal";
						 } %>
						 <tr class="<%=classType %>">
								<td  align="center" style="font-size:9px" width="5%">
				  					<input type="checkbox" id="chkbox" onclick="javascript:removeCheckHead();" class="none" value="<bean:write name="row" property="attID"/>"/>
				  				</td>	
								<td  width="20%">&nbsp;
									<input type="text" id="attDescription<bean:write name="row" property="attID"/>" style="width:80%;"  class="inputBorder1"  disabled="true" name="attDescription" value="<bean:write  name="row" property="attDescription"/>" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Description')}else{alert('Please Enter Description'); this.focus();return;};"/>
								</td>
								<td>&nbsp;
									 <html:select name="row" property="attDataType" disabled="true" styleId="attDataType_1" styleClass="inputBorder1" style="width:110px;" >
									 	<html:option value="0">--- Select ---</html:option>
										<html:options name="masterAttributesBean" property="dataTypesList"  ></html:options>
									</html:select>
								</td>
								<td width="20%" >&nbsp;
									<bean:write  name="row"  property="attAliasName"/>
								</td>
								<td>&nbsp;
									<html:select name="row" property="attExpectedValue" disabled="true" styleId="attExpectedValue_1" styleClass="inputBorder1" style="width:110px;" >
										<html:option value="0">--- Select ---</html:option>
										<html:options name="masterAttributesBean" property="expectedValuesList"  ></html:options>
									</html:select>
								</td>
								<td>&nbsp;
									<bean:write  name="row" property="attMaxLenghth"/>
								</td>
								<td>&nbsp;
									<select name="attIsmandatory" class="inputBorder4" style="width:55px;"  disabled="disabled" id="attIsmandatory<bean:write  name="row" property="attID"/>">
										<option value="0">--- Select ---</option>
										<option value="Y" <logic:equal value="Y" name="row"  property="attIsmandatory">selected="selected" </logic:equal> >YES</option>
										<option value="N" <logic:equal value="N" name="row"  property="attIsmandatory">selected="selected" </logic:equal> >NO</option>
									</select> 
								</td>
								<div style="display: none" >
									<span id="attID<bean:write name="row" property="attID"/>"><bean:write name="row" property="attID"/></span>
									<span id="attDescription<bean:write name="row" property="attID"/>"> </span>
									<span id="attIsmandatory<bean:write name="row" property="attID"/>"><bean:write name="row" property="attIsmandatory"/></span>
								</div>
						</tr>
				</logic:iterate>
					<tr class="tableTd">
							<td   colspan="7" align="center" nowrap="nowrap" >
								<input type = "button" value="Update" onclick="javascript:fnUpdate();" /> 
							</td>
					</tr>		
			</logic:notEmpty>
			<logic:empty  name="attributeList">
				   <tr align="center" >
					 <td colspan="19" align="center"><B><font color="red">No Record Found</font></B></td>
				   </tr>
			</logic:empty>	
		</logic:present>				
	</table>
</fieldset>
</html:form>
</body>
</html>
