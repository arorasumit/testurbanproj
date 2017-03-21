<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Package</title>
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
var serdetid ="<%= request.getAttribute("serdeid") %>";


function DrawPackageList()
{
	   var str;
	   var frm=document.getElementById('searchForm');
	  
	   var mytable = document.getElementById('tabPackageType');	
	   var iCountRows = mytable.rows.length;
		
	   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   }   	
	    
	   document.getElementById('txtPageNumber').value= pageNumber;
	   sync();	   
	      
       if(document.getElementById('txtPackageID').value == "")
       {
       	document.getElementById('txtPackageID').value = 0;
       }	 
	       	   	   
	   var jsData =
			{
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				packageName:trim(document.getElementById('txtPackageName').value),
				packageID:trim(document.getElementById('txtPackageID').value),
				serviceDetailID:serdetid
			};
			
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var listPackage = jsonrpc.processes.getPackagesWithSorting(jsData);				
	iTotalLength=listPackage.list.length;	
		if(iTotalLength !=0)
		{
		iNoOfPages = listPackage.list[0].maxPageNo;
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
		var tblRow=document.getElementById('tabPackageType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		//[001] Start
		str="<input name=chk id='chk' type='checkbox' value = '"+listPackage.list[i].packageID+"'/>";
		//[001] End
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listPackage.list[i].packageID;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listPackage.list[i].packageName;
		str = str+"<input type='hidden' name='packageName' id='packageName"+listPackage.list[i].packageID+"' value='"+ listPackage.list[i].packageName +"'>"
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
		var tblRow=document.getElementById('tabPackageType').insertRow();
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

function clearFields()
{
	document.getElementById('txtPackageName').value="";
	document.getElementById('txtPackageID').value="";
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var PackageName=document.getElementById('txtPackageName').value;
	var PackageID=document.getElementById('txtPackageID').value;
	
	if(trim(PackageName) =="" && (trim(PackageID)=="" || trim(PackageID)=="0") )
	{
		alert('Please enter atleast one search creteria');		
	}
	else
	{
		if( trim(document.getElementById('txtPackageName').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtPackageName'),path,'Package Name')=='undefined')
			{
			return true;
			}
		}
		if( trim(document.getElementById('txtPackageID').value).length>0 && trim(document.getElementById('txtPackageID').value)!="0")
		{
			if(accountValidation(document.getElementById('txtPackageID'),'Package ID')==false)
			{
				return false;
			}
		}						
		searchFromList('PACKAGE_ID','SELECTPACKAGE');
		
		
	}
}


function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}

function SelectMultiplePackage()
{
var frm=document.getElementById('searchForm');
	var flag;
	var packages=new Array();
	var packagesName=new Array();
			
	if(frm.chk==undefined)
	{
		alert("There is no Records!!")
		return false;
	}
	if(frm.chk.length==undefined)
	{
	
		if(frm.chk.checked==true)
		{		
			
			packages[0]=frm.chk.value;
			packagesName[0]=document.getElementById('packageName'+frm.chk.value).value;
			flag=true;
		}
	}
	else {
	for(i=0,j=0;i<frm.chk.length;i++)
	{
		
			if(frm.chk[i].checked==true)
			{	
								
					packages[j]=frm.chk[i].value;
					packagesName[j]=document.getElementById('packageName'+ frm.chk[i].value).value;
					
					
					j++;
					flag=true;
								
				
				
			}	
	}
	}
	if(flag!=true)
		{
			alert("Please select Package");
			return false;
				
		}
	else
		{
			
			callerWindowObj.drawPackagesTable(packages,packagesName);	
			callerWindowObj.frompackages=packages;
			callerWindowObj.frompackagesName=packagesName;
			window.close();	
			
		}
}
</script>
</head>
<body>
<div class="head"> <span>Package List </span> </div>
<center>
<html:form action="/NewOrderAction" styleId="searchForm" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('PACKAGE_ID','SELECTPACKAGE')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('PACKAGE_ID','SELECTPACKAGE')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('PACKAGE_ID','SELECTPACKAGE')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('PACKAGE_ID','SELECTPACKAGE')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Package ID:</strong><input type="text" id="txtPackageID" name="PackageID" class="inputBorder1" onKeyPress="onPressEnterSearch(event)" ></div>
					</td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Package Name:</strong><input type="text" id="txtPackageName" name="PackageName" class="inputBorder1"  onKeyPress="onPressEnterSearch(event)"  >
					</div></td>
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<td>
					<div class="searchBg" onclick="SelectMultiplePackage()" ><a href="#">OK</a></div>
					</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	<table width="100%" border="1"  id="tabPackageType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('PACKAGE_ID','SELECTPACKAGE')">Package ID</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('PACKAGE_NAME','SELECTPACKAGE')">Package Name</a>
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
	DrawTable('PACKAGE_ID','SELECTPACKAGE')
	
	// <!-- end [003] -->
</script>
</html>