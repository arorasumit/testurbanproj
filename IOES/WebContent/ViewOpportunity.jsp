<!--[001]	 LAWKUSH 		06-MAY-11	00-05422		Setting Paging ans sorting -->
<!--[002]    NANCY          01-JUL-16   20160301-XX-022139  Displaying ePCN NO. On Opportunity Dialog Box-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>View Opportunity</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript" type="text/javascript">



//[002] start
function callDisplayEpcn()
{
	var orderNo = <%=request.getParameter("orderNo")%>;
	var ePCNTextBox = document.getElementById('ePCNId');
	//ePCNTextBox.value='';

	var getEpcn=  jsonrpc.processes.getEpcnSaved(orderNo);
	if((getEpcn==null))
		{
	ePCNTextBox.value= '' ;
		}
	else
		ePCNTextBox.value= getEpcn ;
}
//[002] end




function callDisplayOppRows(){
	var orderNo = <%=request.getParameter("orderNo")%>;
	var mytable = document.getElementById('taboppDetails');	
	var iCountRows = mytable.rows.length;
	
	for(var i=1; i<iCountRows; i++){		
		mytable.deleteRow(1);
		
	}
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstSavedOpportunity = jsonrpc.processes.getOpportunitySaved(orderNo);
	var v_iTotalLength=lstSavedOpportunity.list.length;	
	for (var si = 0; si <  v_iTotalLength; si++)
	{		 	 
	    var oppIdSaved=lstSavedOpportunity.list[si].opportunityId;
	    var quoteNoSaved=lstSavedOpportunity.list[si].quoteNo;
	    var salesForceopportunityNoSaved=lstSavedOpportunity.list[si].salesForceopportunityNo;	    
	    if(salesForceopportunityNoSaved==null)
	    	salesForceopportunityNoSaved=' -';
	    	
		displayOppRows(oppIdSaved,quoteNoSaved,salesForceopportunityNoSaved,si)
	}
}

function displayOppRows(oppId,quoteNo,salesForceopportunityNoSaved,index){
	
		var colorValue=parseInt(index)%2;
		var colors=new Array("normal","lightBg");
		var txtColorArray = new Array("#FEFEFE","#F7F7E7");
		
		var str;	  	 		 
		var mytable = document.getElementById('taboppDetails');	
	 	var iCountRows = mytable.rows.length;		 	
			var tblRow=document.getElementById('taboppDetails').insertRow();			
			tblRow.className=colors[colorValue];	
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=index+1;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=oppId;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=quoteNo;
			CellContents = str;
			tblcol.innerHTML = CellContents;	
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str = "<input type='text' tabindex='-1' style='border: 0px solid;background:'"+ txtColorArray[colorValue] +"' value ='"+ salesForceopportunityNoSaved +"' readonly />";

			CellContents = str;
			tblcol.innerHTML = CellContents;						
			
}

function closeSelected(){
	window.close();
}
var path='<%=request.getContextPath()%>';

	function setValue()   //called this function on onload 
{
	
	var callerWindowObj = dialogArguments;
	
}
//
</script>
</head>
<body>
<center>
<html:form action="/NewOrderAction" styleId="opportunityTypes" method="post">
<!-- [002] start -->
<br/>
<table width="100%" border="1"  id="tabePCNNo." align="center" cellpadding="0" cellspacing="0" class="tab2" >		
		<tr class="head">
			<th colspan="2"> VIEW EPCN NO.</th>
		</tr>
</table>
<br/>
<fieldset class="border1">
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
		<tr class="normal"> 
			<td align="center" colspan="6">
				<div style="float:left;"><strong>ePCN No : </strong><input type="text" maxlength="50" id= "ePCNId" name="ePCNId" readonly="true"  />
				</div></td>
		</tr>
	</table>
</fieldset>
<br/>
<table width="100%" border="1"  id="tabePCNNo." align="center" cellpadding="0" cellspacing="0" class="tab2" >		
		<tr class="head">
			<th colspan="2">VIEW OPPORTUNITY</th>
		</tr>
</table>
<br/>
<!-- [002] end -->
<fieldset class="border1">	
			<table width="100%"  border="1" id="tabopportunityType" align="center" cellpadding="0" cellspacing="0" class="tab2" >			   				
			</table>	
</fieldset>
<br>
<fieldset class="border1">
	
		
			<table width="100%"  border="1" id="taboppDetails" align="center" cellpadding="0" cellspacing="0" class="tab2" >			   
				<tr class="grayBg">
				    <td width="10%" align="center">S No.</td>
					<td width="45%" align="left">
						Opportunity No			
					</td>
					<td width="45%" align="left">
						Quote No				
					</td>	
					<td width="45%" align="left">
						SF Opportunity No				
					</td>													
				</tr>
			</table>	
			
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">			
			<td width="20%" align="center"><input name="closeRows"
				id="closeRows" onClick="closeSelected();" type="button" value="Close"></td>
		</tr>
	</table>
</fieldset>		
</html:form>
</center>
</body>
<script type="text/javascript">	
	callDisplayOppRows();
	//[002]
    callDisplayEpcn(); 
</script>
</html>


	
