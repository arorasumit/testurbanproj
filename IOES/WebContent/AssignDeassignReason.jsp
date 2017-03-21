<!--Tag Name Resource Name  Date		CSR No					Description -->
<!--NANCY SINGLA	09-MAY-16        20160301-XX-022139       STANDARD_REASON_MAPPING MASTER PAGE          -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.utilities.Utility"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Assign/Deassign Standard Reasons</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="js/jquery-1.7.2.min.js"> </script>
<script language="javascript" type="text/javascript">

var path='<%=request.getContextPath()%>';
var sessionid ='<%=request.getSession().getId() %>';

jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

function goToHome()
{
	var myForm=document.getElementById('viewStandardReasonFormId');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}


function getAllCustomerSegment()
{
	//alert("getCustomersegment called");
	
	
	 var myForm=document.getElementById('viewStandardReasonFormId');
	 var custSeg = jsonrpc.processes.getAllCustomerSegmentList();
	 var combomain =myForm.custSegment;
	 var iCountRows = combomain.length;
	   for (var i = 1; i <  iCountRows; i++)
	   {
		   combomain.remove(i);
	   }
	   for(var j=0;j<custSeg.list.length;j++)
	   {
			var optionmain = document.createElement("option");
		        optionmain.text = custSeg.list[j].cus_segment;
		   		optionmain.title = custSeg.list[j].cus_segment;				
				optionmain.value = custSeg.list[j].customerSegmentId;
				//alert("options: "+optionmain.text);
				try 
				{
					combomain.add(optionmain, null); 	
				}
				catch(error) 
				{
					combomain.add(optionmain); 
					
				} 
				
	    }
}


function getChangeTypeList()
{
	//alert("getChangeTypeList called");
	var myForm=document.getElementById('viewStandardReasonFormId');
	var combo=myForm.changeType;
	var iCountRows = combo.length;
	   for (var i = 1; i < iCountRows; i++)
	    {
		   combo.remove(i);
	    }
	var chgType= jsonrpc.processes.getChangeType();
	   for(var j=0;j<chgType.list.length;j++)
    	{
	 		var option = document.createElement("option");
	  		option.text = chgType.list[j].changeTypeName;
	  		option.value = chgType.list[j].changeTypeId;
	  	
			try 
			{
			combo.add(option, null); //Standard
			}
			catch(error) 
			{
			combo.add(option); // IE only
			}
        }
} 


function getSubChangeType()
{
          
     var i, j ;
     var ChangeTypeList;
     var myForm=document.getElementById('viewStandardReasonFormId');
     var combo=myForm.subChangeTypeId;
	 var iCountRows = combo.length;
	 
	    for (i = 1; i <  iCountRows; i++)
	    {
	    	//alert("previous values removed");
	       combo.remove(1);
	    }	
	 	var changeTypeId=Number(document.getElementById('changeTypeId').value);
	 	//alert(changeTypeId);
	 	try
	 	{
	    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    ChangeTypeList = jsonrpc.processes.populateChangeType(changeTypeId); 
	    }
	    catch(e)
	    {
	    	alert("exception     " + e);
	    }
	    for(j=0;j<ChangeTypeList.list.length;j++)
	    {
	    	
		   	var option = document.createElement("option");
		  		option.text = ChangeTypeList.list[j].subChangeTypeName;
		  		option.value = ChangeTypeList.list[j].subChangeTypeId;
			try 
			{
				combo.add(option, null); 
			}
			catch(error) 
			{
				combo.add(option); 
			}
	    }
	    
}

function populateReasonForChange()
{
	
	var myForm=document.getElementById('viewStandardReasonFormId');
    var customerSegment= myForm.custSegment;
	var changeType= myForm.changeType;
	var subChangeType= myForm.subChangeTypeId;
	if(customerSegment.value==""||customerSegment.value==null)
	{
		alert("Please select customer segment");
		return false;
	}
	if (changeType.value==""||changeType.value==null)
	{
		alert("Please select change type");
		return false;
	} 
	if (subChangeType.value==""||subChangeType.value==null)
	{
		alert("Please select sub change type");
		return false;
	}
	var jsData =
	{
		customerSegmentId:customerSegment.value,
		changeTypeId:changeType.value,
		subChangeTypeId:subChangeType.value
	}; 
	
	$("#tabforNewStandardReason").show();
	$("#updateButtonDiv").show();
	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC"); 
	var getAllReasons= jsonrpc.processes.fillMappedReasonForChange(jsData);
	//alert("enter");
	//alert(getAllReasons.unassignedChangeReasons.list.length);
	//alert(getAllReasons.assignedChangeReasons.list.length);
	var getUnAssignedReasons= getAllReasons.unassignedChangeReasons;
	var getAssignedReasons=  getAllReasons.assignedChangeReasons;
	var combomain = document.getElementById("changeReason");
	var iCountRows = combomain.length;
	
	   for (var i = 1; i <  iCountRows; i++)
	   {
	       combomain.remove(i);
	   }
	   if ( getUnAssignedReasons!=null)
	   {
		   for(var j=0;j<getUnAssignedReasons.list.length;j++)
		   {
				   var option = document.createElement("option");	    	
			   		option.text = getUnAssignedReasons.list[j].stdReasonName;
			   		option.title = getUnAssignedReasons.list[j].stdReasonName;
					option.value = getUnAssignedReasons.list[j].stdReasonId;
				    try 
					{
						combomain.add(option, null); //Standard
					}
					catch(error) 
					{
					    combomain.add(option); // IE only
					}
		   }
	   }
	var combo = document.getElementById("changeReasonassign");
	var iCountRowsnew = combo.length;
	for (var i = 1; i <  iCountRowsnew; i++)
	   {
	       combo.remove(i);
	   }
	if ( getAssignedReasons!=null)
	{
	   	for(j=0;j<getAssignedReasons.list.length;j++)
		 {
		  	var optionnew = document.createElement("option");	    	
	   		optionnew.text = getAssignedReasons.list[j].stdReasonName;
	   		optionnew.title = getAssignedReasons.list[j].stdReasonName;
			optionnew.value = getAssignedReasons.list[j].stdReasonId;
		    try 
			{
			combo.add(optionnew, null); 
			}
			catch(error) 
			{
			combo.add(optionnew); 
			}
		 }
    }
	
}


function saveReasonMapping()
{
	
	var myForm=document.getElementById('viewStandardReasonFormId');
	var customerSegment= myForm.custSegment;
	var changeType= myForm.changeType;
	var subChangeType= myForm.subChangeTypeId;
	var assignedReason = document.getElementById('changeReasonassign');
	var myArray= [];
	for (var i = 0; i < assignedReason.length; i++)
	{
		myArray.push(assignedReason[i].value);
			
	}
	var jsData =
	{
	  customerSegmentId:customerSegment.value,
	  changeTypeId:changeType.value,
	  subChangeTypeId:subChangeType.value
	}; 
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC"); 
    var updateReasons= jsonrpc.processes.updateStandardReasonMapping(jsData,myArray);
		if (updateReasons == "success")
		{
	   		alert("Data Updated Succesfully");
		}	
		else
		{
	   		alert("error updating in data");
		}
}

$(function () 
		{     
	function moveItems(origin, dest) {
  $(origin).find(':selected').appendTo(dest);
    }
$('#leftButton').click(function(){
	
	moveItems('#changeReason','#changeReasonassign');
	
});

$('#rightButton').on('click', function () {
    moveItems('#changeReasonassign', '#changeReason');
});
	
});


function enableLeftToRightbutton()
{
	document.getElementById("leftButton").disabled=false;	
}
function enableRightToLeftbutton()
{
	document.getElementById("rightButton").disabled=false;
}

function resetFetchedRecords()
{
	
	document.getElementById("changeReason").options.length = 0;
	document.getElementById("changeReasonassign").options.length = 0;
	document.getElementById("leftButton").disabled=true;
	document.getElementById("rightButton").disabled=true;
	document.getElementById("updateReasonButton").disabled=true;
}

function enableUpdateButton()
{
	document.getElementById("updateReasonButton").disabled=false;
}

function HideReasonTable()
{
$("#tabforNewStandardReason").hide();
$("#updateButtonDiv").hide();
}

</script>
</head>
<body onload="getAllCustomerSegment();getChangeTypeList();HideReasonTable();">
<bean:define id="formBean" name="newOrderBean"></bean:define> 
	<div class="head"> <span>Standard Reason Mapping </span></div> 
<html:form action="/defaultStandardReasonAction" styleId="viewStandardReasonFormId" method="post">

	<table width="100%"  border="0"  cellpadding="0" cellspacing="0">	
		<tr>	
			<td width="10%" align="right" vAlign="top">
				<img src="gifs/top/home.gif" title="Home" onClick="goToHome();"></img>
			</td>			
		</tr>
	</table>
	<table width="60%" border="1"  id="tabNewStandardReason" align="center" cellpadding="0" cellspacing="0" class="tab2" >		
		<tr class="head">
			<th colspan="2">Assign/Deassign Standard Reason</th>
		</tr>
								
		<tr>
			<td width="20%">Select Customer Segment :
			</td>
			<td>     
				<html:select property="custSegment"  style="width:250px;"  styleClass="dropdownMargin" styleId="customerSegment" onchange="resetFetchedRecords();HideReasonTable();">
				<html:option  value="">Select Customer Segment </html:option>
				</html:select>
			</td>	
		</tr>
		<tr>
			<td width="20%">Select Change Type:
			</td>
			<td width="70%">
			<!-- NANCY FOR CHANGE TYPE -->
			    <html:select  property="changeType" styleId="changeTypeId" style="width:250px;" styleClass="dropdownMargin" onchange="resetFetchedRecords();getSubChangeType();HideReasonTable();" >
				<html:option value=""> Select Change Type </html:option>
				</html:select>
			</td>
		</tr>
		<tr>
			<td width="20%">Select SubChange Type :
			</td>
			<td width="70%">
				<html:select  property="subChangeTypeId" styleId="subchangeTypeId" styleClass="dropdownMargin"  style="width:250px;" onchange="resetFetchedRecords();HideReasonTable();">
				<html:option value=""> Select Sub Change Type </html:option>
				</html:select>
			</td>
		</tr> 
   </table>	
   <br/>
	<div width="80%" align="center">
		<input type="button" id="fetchButton" value="FETCH" onclick="populateReasonForChange();"/>
	</div>
	<br/>
	<table width="60%" border="1"  id="tabforNewStandardReason" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr>
    		<td>UnAssigned Reasons:</td>
			<td></td>
			<td>Assigned Reasons:</td>
		</tr>
		<tr>
			<td>
				<select name="changeReason" id="changeReason" style="width:100% " size='10' multiple="multiple" onclick="enableLeftToRightbutton();">
				</select>
			</td>
			
			<td width="50px">
				<input type="button" size="50" id="leftButton" onclick="enableUpdateButton();" disabled value=">>"  /> 
        		<br/><br/>
				<input type="button" id="rightButton"  size="50" onclick="enableUpdateButton();" disabled value="<<" /> 
			</td>
			<td>
				<select name="changeReasonassign" id="changeReasonassign" style="width:100% " size='10' multiple="multiple" onclick="enableRightToLeftbutton();">
				</select>
			</td>
		</tr>
	</table>
	<br/>
	<br/>
	<div width="80%" align="center" id="updateButtonDiv">
		<input type = "button"  id="updateReasonButton" value="UPDATE" onclick= "saveReasonMapping();" disabled />
	</div>
	
			
</html:form>
	
	
	

</body>
</html>