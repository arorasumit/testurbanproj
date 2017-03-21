<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/gifs/style.css" type="text/css" rel="stylesheet">

<title>Drop&Carry</title>
</head>
<script>
	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	var cntxpath="<%=request.getContextPath()%>";
	
	var orderType="<%=request.getParameter("orderType")%>";
	var serviceId="<%=request.getParameter("serviceId")%>"; 
	//alert(serviceId);
	var viewMode="<%=request.getParameter("viewMode")%>";	
	var orderNo="<%=request.getParameter("ordNo")%>";
	var param="/NewOrderAction.do?method=selectHeadEndCode&orderNo="+orderNo;
	</script>
	<!-- // Method for HeadEndCode  Selection
	function selectHeadEndCode()
	{
		 $("#HeadEndCodeDialog").load(path+param);						
			$('#HeadEndCodeDialog').dialog('open');
			
		//alert(path+param);															
		/*  $("#HeadEndCodeDialog").dialog({
	    	 	//height: 400,
	    	 	width: 800,
	    	 	minHeight:400,
	         	autoOpen: false,
	     		modal:true,
	     		appendTo:window,
	     		closeOnEscape: true,
	     		resizable:false,
	     		title: "SelectHeadEndCode",	     		
	     		close: function() { 					
					$("#HeadEndCodeDialog").empty();									
				}
	 	});	 */	 	
				
	};	
	</script> -->
<script language="javascript" src="<%=request.getContextPath()%>/js/Drop&CarryUtility.js"></script>
<body>
	<center>
	<html:form action="/NewOrderAction" styleId="dropNcarryForm" method="post">
	<fieldset class="border1" >
				<legend> <b>Drop&Carry Options</b></legend>				
				<table id="tab_DnC" border="0" cellspacing="0" cellpadding="0" align="center" width="100%" class="tab2">
				<tr>
				<td>Service Flavour</td><td>
					<select  disabled="disabled" name="ServiceFlavour" id="ddServiceFlavour" class="dropdownMargin" isRequired="0" style="width:150px">
						<option value="PP" title="PointToPoint" selected>PointToPoint</option>
						<option value="DC" title="Drop&Carry">Drop&Carry</option>			
					</select>
				</td>
				</tr>
				<tr>
				<td>Circuit Type</td><td>
					<select  disabled="disabled" id="ddCircuitType" class="dropdownMargin" isRequired="0" style="width:150px">
						<option value="0" title="default">--PleaseSelect--</option>
						<option value="HE" title="HeadEnd">HeadEnd</option>
						<option value="DROP" title="Drop">Drop</option>			
					</select>
				</td>
				</tr>
				<tr>
				<td>HeadEnd Code</td>
				<td width="15%">
				<input type="text" id="txtHeadEndCode" class="inputDisabled" value=" " readOnly="true"/>
				</td>
				<td width="15%">
				<div class="searchBg1"id="hec_link" ><a disabled="disabled" href="#"  id="heclink" onClick="selectHeadEndCode()" >...</a></div>
				</td>
				</tr>
				<tr>
				<td>
				<input type="button" id="btn_DnC" value="Save" onclick="saveOptions()"/>
				</td>
				</tr>			
				</table>
	</fieldset>	
	<div id ="HeadEndCodeDialog"></div>
	</html:form>	
		&nbsp;
		&nbsp;
	<div id ="DropAndCarryAlert">
	<p style="color:red; visibility:hidden" id="alertText"><strong>Please select Primary Location as Network Pop Location !!</strong></p>
	</div>	
	</center>
</body>
</html>