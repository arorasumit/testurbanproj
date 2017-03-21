<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
<script type="text/javascript" src="npdjs/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<script language="javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
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


function updateProduct()
{

	productForm = document.getElementById('editProductForm');
	setFormBean(productForm);
	var total=""
 	if(document.forms[0].updateStatus.length!=null)
  	{
	  for(var i=0; i < document.forms[0].updateStatus.length; i++)
	  {
	   if(document.forms[0].updateStatus[i].checked){
	     total +=document.forms[0].updateStatus[i].value + ","
	    }
	  }
  	}
  	else
  	{
  		if(document.forms[0].updateStatus.checked){
	     total +=document.forms[0].updateStatus.value + ","
	    }
  	}  
   	if(total==""){
   		alert("Please select atleast one checkBox for continue!.."); 
   		return false;
  	}
 
 
  	var des = 0; 
  
  	for(var j = 0; j < document.forms[0].updateStatus.length; j++){
  		
	  	if(document.forms[0].updateStatus[j].disabled == false){	
	  	
	  	prd=document.forms[0].updateStatus[j].value;
	  	var varmychangedactive=document.forms[0].elements["mychangedactive"+prd].value;
	  	var varmyisactive=document.forms[0].elements["myisactive"+prd].value;
	  	 	
	  		if((document.forms[0].descReplicaId[j].value != document.forms[0].descId[j].value))
  			{
  				des = des+1;
  			}
  			if((varmychangedactive != '')  ){
  				des = des+1;
  			}

  			if(ValidateTextField(document.forms[0].descId[j],'<%=request.getContextPath()%>',"Description")==false)
			{
				return false;
			}
	  	}
  		
  	}
  	if(des > 0){
		productForm.action="<%=request.getContextPath()%>/ProductCategory.do?method=updateProductCategory";
		showLayer();
		productForm.submit();
	}else {
		alert('Please Change Product Description or Status!');
	}
}

function setValue(status,prd){
	document.forms[0].elements["mychangedactive"+prd].value=status;
}

</script>
</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
</div>
<html:form action="/ProductCategory" styleId="editProductForm" method="post">
	<table width="80%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
				<tr>
					<td><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%" id="add">Edit Product Category</td>
					<td><img src="npd/Images/tabRight.gif"></td>
					<td width="70%" align="right"><img src="npd/Images/zero.gif" width="15" height="1"><a class="tabledata" href="<%=request.getContextPath()%>/AddProductCategory.jsp">Add Product Category</a></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="80%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td width="70%">
				<div style="overflow:scroll;height:300px;width:100%" class="scroll">
					<table width="100%" border="0" class="tabledata" cellpadding="0" cellspacing="0">
						<tr align="center">
							<table width="100%" border="1" class="tabledata" cellpadding="3" cellspacing="1" align="center">
								<tr bgcolor="#FF9255">
									<th background="#FF9255">S.No.</th>
									<th background="#FF9255">Product Category Description</th>
									<th background="#FF9255">Active</th>
									<th background="#FF9255">InActive</th>
									<th background="#FF9255">Update</th>
								</tr>
								<%String []colors=new String[]{"redBg","alterRedBg"}; %>
								<%int sno;
								sno=0;%> 
								<logic:iterate id="productList" name="productCategoryDtoList" indexId="index_task">
									<%sno=sno+1;%>
									<tr class="<%=colors[index_task.intValue()%2]%>">
									
										<td align="center"><%=sno%></td>
										<td align="center">
											<logic:equal name="productList" property="mappedStatus" value="1">
										 		<input type="text" id = "descId" name="description<bean:write name="productList" property="productId"/>" value='<bean:write name="productList" property="productDesc"/>' readonly="readonly" maxlength="100">
										 	</logic:equal>
										 	<logic:equal name="productList" property="mappedStatus" value="0">
										 		<input type="text" id = "descId" name="description<bean:write name="productList" property="productId"/>" value='<bean:write name="productList" property="productDesc"/>'  maxlength="100">
										 	</logic:equal>
										 	<input type="hidden" id = "descReplicaId" name="descReplica" value='<bean:write name="productList" property="productDesc"/>'>
										</td>
										<td align="center">
												<input type="hidden" name="myisactive<bean:write name='productList' property='productId'/>" 
														  value="<bean:write name='productList' property='isActive'/>" 	/>
												<input type="hidden" name="mychangedactive<bean:write name='productList' property='productId'/>"/>
												<input type="radio" id="activeId" onclick="setValue('Y',<bean:write name='productList' property='productId'/>)" name="status<bean:write name="productList" property="productId"/>" value='Y' <logic:equal name="productList" property="isActive" value="Y">checked="checked"</logic:equal> <logic:equal name="productList" property="mappedStatus" value="1">disabled="disabled"</logic:equal>>
										 </td>				 
										 <td align="center">
										 		<input type="radio" id="activeId" onclick="setValue('N',<bean:write name='productList' property='productId'/>)" name="status<bean:write name="productList" property="productId"/>" value='N' <logic:equal name="productList" property="isActive" value="N">checked="checked"</logic:equal> <logic:equal name="productList" property="mappedStatus" value="1">disabled="disabled"</logic:equal> >
										 </td>
										 <td align="center">
											<logic:equal name="productList" property="mappedStatus" value="1">
												<input type="checkbox"  name="updateStatus" value="<bean:write name="productList" property="productId"/>" disabled="disabled">
											</logic:equal>
											<logic:equal name="productList" property="mappedStatus" value="0" >
												<html:multibox property="updateStatus" styleId="chkAll" styleClass="none">
													<bean:write name="productList" property="productId"/>
												</html:multibox>							
											</logic:equal>
											<input type="hidden" id = "statusId" name="active" value='<bean:write name="productList" property="isActive"/>'>
										</td>
									 </tr>
								</logic:iterate>
								<logic:empty  name="productCategoryDtoList">
									<tr class="redBg" align="center">
										<td colspan="5">No Records Found</td>
									</tr>
								</logic:empty>
								<logic:notEmpty name="productCategoryDtoList">
									<tr>
										<td colspan="5" align="center">
											<div class="buttonVSmall" onClick="javascript:updateProduct();">Update</div>
										</td>
									</tr>
								</logic:notEmpty>
							</table>
						</table>
					</div>
				</td>
			</tr>
		</table>
</html:form>
</div>		
</body>
</html:html>