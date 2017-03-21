<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	4-Feb-11	00-05422		Sending ShortCode on New Order -->
<!--[002]	 ROHIT VERMA	18-Feb-11	00-05422		Fetching Region and Zone AGainst an Account -->
<!--[003]	 ANIL KUMAR 	23-msr-11	00-05422		Setting Paging ans sorting in select account popup window-->
<!--[004]	 LAWKUSH 		06-MAY-11	00-05422		Setting Paging ans sorting -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.util.ArrayList;"%>
<html>
<head>
<title>Select Component</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">


var flag=0;
var callerWindowObj = dialogArguments;
var frompackages=new Array();
var frompackagesName=new Array();
var poNumber=callerWindowObj.poNumber; 
//Start[02112012]POC POINT : Changed By Ashutosh
var componentCheckCount=0;
function fnCheckAll()
{

	var myForm=document.getElementById('tabComponentType');	
    if(componentCheckCount==0)
    {
    	componentCheckCount=myForm.rows.length-1;
    }
    var rowlen= myForm.rows.length-1;
 
   
    if(rowlen==0)
	{
		alert("There is no records");
		document.getElementById('SelectAllChck').checked=false;
		return false;
	}
	
	if(document.getElementById('SelectAllChck').checked==true)
	{
		 for (i =0; i<componentCheckCount; i++)
		 {
		 	if(document.getElementById('chk'+i)!=null)
		 		document.getElementById('chk'+i).checked=true;
		 		
		 }
	}
	else
	{
		for (i =0; i<componentCheckCount; i++)
		 {
		 	if(document.getElementById('chk'+i)!=null)
			 	document.getElementById('chk'+i).checked=false;
		 }
	}	
}
function fnUncheck()
{
	document.getElementById('SelectAllChck').checked=false;
}
//End[02112012]POC POINT : Changed By Ashutosh
function DrawComponentList()
{
	   var frm=document.getElementById('searchForm');
	   
	   var mytable = document.getElementById('tabComponentType');	
	   var iCountRows = mytable.rows.length;
		
	   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   }   	
	    
	   document.getElementById('txtPageNumber').value= pageNumber;
	   sync();	   
	      
       if(document.getElementById('txtComponentID').value == "")
       {
       	document.getElementById('txtComponentID').value = 0;
       }	 
	         
	         var packageList = '';
	         for(var count = 0 ;count < frompackages.length; count++) {
	         	if(packageList != '') {
	         	      	packageList = packageList + ','+ frompackages[count];
	         	} else {
	         		packageList =  frompackages[count];
	         	}
	         }	
	        
	   var jsData =
			{
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				componentName:trim(document.getElementById('txtComponentName').value),
				componentID:trim(document.getElementById('txtComponentID').value)
				,selectedPackageList:packageList,
				poNumber:poNumber
			};
			var lstAccount;
	try {		
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	lstAccount = jsonrpc.processes.getComponentsWithSorting(jsData);
	} catch (e) {
		alert('exception in calling getComponentsWithSorting ::::' + e);
	}
		
	iTotalLength=lstAccount.list.length;	
		if(iTotalLength !=0)
		{
		iNoOfPages = lstAccount.list[0].maxPageNo;
		}


        else
		{     
	        iNoOfPages=1;
		}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;				
	var colors=new Array("normal","lightBg");
	for (i = 0 ; i <iTotalLength; i++) 
	{		 		
	 	var colorValue=parseInt(i)%2;	 		  
		var tblRow=document.getElementById('tabComponentType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		//[001] Start
		str="<input name=chk id='chk"+i+"' type='checkbox' value = '"+lstAccount.list[i].componentID+"'/>";
		//[001] End
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].componentID;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].packageID;
		str = str+"<input type='hidden' name='packageID' id='packageID"+lstAccount.list[i].componentID+"' value='"+ lstAccount.list[i].packageID +"'>"
		str = str+"<input type='hidden' name='packageName' id='packageName"+lstAccount.list[i].componentID+"' value='"+ lstAccount.list[i].packageName +"'>"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].componentName;
		str = str+"<input type='hidden' name='componentName' id='componentName"+lstAccount.list[i].componentID+"' value='"+ lstAccount.list[i].componentName +"'>"
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
	var pagenumber=document.getElementById('txtPageNumber').value;
		var MaxPageNo=document.getElementById('txtMaxPageNo').value;
	if(pagenumber && MaxPageNo==1)
	 {
	
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;
	
	
	 }
	 
	 if(pagenumber==1 && MaxPageNo!=1)
	 {
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=false;
	     document.getElementById('next').disabled=false;
	 
	 }
	 
	  if(pagenumber==MaxPageNo && pagenumber!=1)
	  
	 {
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;
	     document.getElementById('first').disabled=false;
	     document.getElementById('prev').disabled=false;
	 
	 }   
		if(pagenumber!=MaxPageNo && pagenumber!=1)
	  
	 {
	     document.getElementById('last').disabled=false;
	     document.getElementById('next').disabled=false;
	     document.getElementById('first').disabled=false;
	     document.getElementById('prev').disabled=false;
	 
	 }
		 
   		if(iTotalLength==0)		
		{
   
    		var tblRow=document.getElementById('tabComponentType').insertRow();
	    	var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.colSpan = 4;
			tblcol.vAlign="top";
			tblcol.innerHTML = "No Records Found";
   		 }
   		 return false;
} 

function clearFields()
{
	document.getElementById('txtComponentName').value="";
	document.getElementById('txtComponentID').value="";
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var componentName=document.getElementById('txtComponentName').value;
	var componentID=document.getElementById('txtComponentID').value;
	
	if(trim(componentName) =="" && (trim(componentID)=="" || trim(componentID)=="0" ))
	{
		alert('Please enter atleast one search creteria');		
	}
	else
	{
		if( trim(document.getElementById('txtComponentName').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtComponentName'),path,'Component Name')=='undefined')
			{
			return true;
			}
		}	
		if( trim(document.getElementById('txtComponentID').value).length>0 && trim(document.getElementById('txtComponentID').value)!="0")
		{
			if(accountValidation(document.getElementById('txtComponentID'),'Component ID')==false)
			{
				return false;
			}
		}					
		searchFromList('COMPONENT_ID','SELECTCOMPONENT');
		
		
	}
}


function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}

function SelectMultipleComponents()
{
var frm=document.getElementById('searchForm');
	var flag;
	var components=new Array();
	var componentsName=new Array();
	var packageID=new Array();
	var packageName=new Array();	
	//var pagename=callerWindowObj.document.getElementById('hdnpagename').value;
	var result;	
	
	if(frm.chk==undefined)
	{
		alert("There is no Records!!");
		return false;
	}
	if(frm.chk.length==undefined)
	{
	
		if(frm.chk.checked==true)
		{		
			
			components[0]=frm.chk.value;
			componentsName[0]=document.getElementById('componentName'+frm.chk.value).value;
			packageID[0]=document.getElementById('packageID'+frm.chk.value).value;
			packageName[0]=document.getElementById('packageName'+frm.chk.value).value;
			
			flag=true;
		}
	}
	else {
	for(i=0,j=0;i<frm.chk.length;i++)
	{
		
			if(frm.chk[i].checked==true)
			{	
						
				
					components[j]=frm.chk[i].value;
					componentsName[j]=document.getElementById('componentName'+ frm.chk[i].value).value;
					packageID[j]=document.getElementById('packageID'+ frm.chk[i].value).value;
					packageName[j]=document.getElementById('packageName'+frm.chk[i].value).value;
					j++;
					flag=true;
								
				
				
			}	
	}
	}
	if(flag!=true)
		{
			alert("Please select Component(s)");
			return false;
				
		}
	else
		{
			
			callerWindowObj.drawComponentsTable(components,componentsName,packageID,packageName);			
			//start[02112012]POC POINT : Changed By Ashutosh
			var myForm=document.getElementById('tabComponentType');	
		    if(componentCheckCount==0)
		    {
		    	componentCheckCount=myForm.rows.length-1;
		    }
			for (i =0; i<componentCheckCount; i++)
			{
			 	if(document.getElementById('chk'+i)!=null)
				 	document.getElementById('chk'+i).checked=false;
			}
			if(confirm("Selected Components are Added Successfully.\n Do You want Close Window"))
			{
				window.close();
			}
			document.getElementById('SelectAllChck').checked=false;
			//End [02112012]POC POINT : Changed By Ashutosh
				
			
		}
}
function SelectPackage() {
		var serdeid=callerWindowObj.document.getElementById("hdnServiceDetailID").value;
		
		var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=getPackages&serdeid="+serdeid;		
		window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:600px");
}
function drawPackagesTable(packages,packagesName) {
		
		var frm=document.getElementById('searchForm');
	  
	   var mytable = document.getElementById('tabComponentType');	
	   var iCountRows = mytable.rows.length;
		
	   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   }   	
	    
	   document.getElementById('txtPageNumber').value= pageNumber;
	   sync();	   
	      
       if(document.getElementById('txtComponentID').value == "")
       {
       	document.getElementById('txtComponentID').value = 0;
       }	 
	         
	         var packageList = '';
	         for(var count = 0 ;count < packages.length; count++) {
	         	if(packageList != '') {
	         	      	packageList = packageList + ','+ packages[count];
	         	} else {
	         		packageList =  packages[count];
	         	}
	         }	
	        
	   var jsData =
			{
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				componentName:document.getElementById('txtComponentName').value,
				componentID:document.getElementById('txtComponentID').value
				,selectedPackageList:packageList,
				poNumber:poNumber
			};
			var lstAccount;
	try {		
	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	lstAccount = jsonrpc.processes.getComponentsWithSorting(jsData);
	} catch (e) {
		alert('exception in calling getComponentsWithSorting ::::' + e);
	}
		
	iTotalLength=lstAccount.list.length;	
		if(iTotalLength !=0)
		{
		iNoOfPages = lstAccount.list[0].maxPageNo;
		}


        else
		{     
	        iNoOfPages=1;
		}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;				
	var colors=new Array("normal","lightBg");
	for (i = 0 ; i <iTotalLength; i++) 
	{		 		
	 	var colorValue=parseInt(i)%2;	 		  
		var tblRow=document.getElementById('tabComponentType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		//[001] Start
		str="<input name=chk id='chk"+i+"' type='checkbox'  value = '"+lstAccount.list[i].componentID+"'/>";
		//[001] End
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].componentID;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].packageID;
		str = str+"<input type='hidden' name='packageID' id='packageID"+lstAccount.list[i].componentID+"' value='"+ lstAccount.list[i].packageID +"'>"
		str = str+"<input type='hidden' name='packageName' id='packageName"+lstAccount.list[i].componentID+"' value='"+ lstAccount.list[i].packageName +"'>"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].componentName;
		str = str+"<input type='hidden' name='componentName' id='componentName"+lstAccount.list[i].componentID+"' value='"+ lstAccount.list[i].componentName +"'>"
		CellContents = str;
		tblcol.innerHTML = CellContents;
					
	}



var pagenumber=document.getElementById('txtPageNumber').value;
	var MaxPageNo=document.getElementById('txtMaxPageNo').value;
	if(pagenumber && MaxPageNo==1)
	 {
	
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;
	
	
	 }
	 
	 if(pagenumber==1 && MaxPageNo!=1)
	 {
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=false;
	     document.getElementById('next').disabled=false;
	 
	 }
	 
	  if(pagenumber==MaxPageNo && pagenumber!=1)
	  
	 {
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;
	     document.getElementById('first').disabled=false;
	     document.getElementById('prev').disabled=false;
	 
	 }
			
	if(iTotalLength==0)		
	{
		var tblRow=document.getElementById('tabComponentType').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
	return false;		


}
</script>
</head>
<body>
<div class="head"> <span>Components List </span> </div>
<center>
<html:form action="/NewOrderAction" styleId="searchForm" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('COMPONENT_ID','SELECTCOMPONENT')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('COMPONENT_ID','SELECTCOMPONENT')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('COMPONENT_ID','SELECTCOMPONENT')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('COMPONENT_ID','SELECTCOMPONENT')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
  <tr class="normal">
   <td align="left" class="columnFontSmall" colspan="1" width="30%" >
									Select Package :
	</td>
  	<td align="center" colspan="10">
  		 <!-- <input type="text"  class="inputBorder1" name="txtPackagesSelected" id="txtPackagesSelected" value="" readonly="true"  style="width:190px;float:left;">-->
		 <div class="searchBg1" ><a href="#" onClick="SelectPackage()">Select Package here</a></div>
  			
  	</td>
  </tr>
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Component ID:</strong><input type="text" id="txtComponentID" name="componentID" class="inputBorder1" onKeyPress="onPressEnterSearch(event)" ></div>
					</td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Component Name:</strong><input type="text" id="txtComponentName" name="componentName" class="inputBorder1"  onKeyPress="onPressEnterSearch(event)"  >
					</div></td>
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Fields" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<td>
					<div class="searchBg" onclick="SelectMultipleComponents()" ><a href="#">OK</a></div>
					</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	<table width="100%" border="1"  id="tabComponentType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select<input type="checkbox" name="SelectAllChck" id="SelectAllChck" onclick="javascript:fnCheckAll();"/></td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('COMPONENT_ID','SELECTCOMPONENT')">Component ID</a>
				</td>
				<td width="8%" align="center">
					<a href="#">Package ID</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('COMPONENT_NAME','SELECTCOMPONENT')">Component Name</a>
				</td>
			</tr>								
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" >&nbsp;</td>
			<td width="20%" colspan="3">&nbsp;</td>
		</tr>
	</table>	
</html:form>
</center>
</body>
<script type="text/javascript">
	//DrawAccountType()
	//DrawTable('COMPONENT_ID','SELECTCOMPONENT')
	
	// <!-- end [003] -->
</script>
</html>