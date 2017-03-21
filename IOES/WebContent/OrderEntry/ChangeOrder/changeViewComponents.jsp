<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--  001 START    MANISHA GARG   08/FEB/2012                 DELETE COMPONENTS FROM DATABASE-->
<!--[002]	 MANISHA GARG	21-feb-12	00-05422		Check Mandatory nonmandatory field -->
<!--[004]	 ROHIT VERMA	1-Apr-12	00-07480		Validation for not allowing to add Components when Lic Company is not 95p BAL -->
<!--[0026]	 Ashutosh Singh	26-Apr-13	-------			Validation for not allowing to add Components when Currency is not INR -->
<!-- // [005] 	 Vipin Saharia	27-Aug-14	GDB Lic Comp. segregation changed if condition for new licence company -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Components</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script  type="text/javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsonrpc.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/utility.js"></script>
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" type="text/javascript">
path="<%=request.getContextPath()%>";
var callerWindowObj = dialogArguments;
//Start-[02112012]POC POINT : Changed By Ashutosh
var componentCheckCount=0;
function fnCheckAll()
{
	var myForm=document.getElementById('tableComponents');	
    if(componentCheckCount==0)
    {
    	//componentCheckCount=myForm.rows.length-2;
    	componentCheckCount= document.getElementById('hdnComponentsCount').value;
    }
    var rowlen= myForm.rows.length-2;
   if(rowlen==0)
	{
		alert("There is no Records");
		document.getElementById('SelectAllChck').checked=false;
		return false;
	}
	
	if(document.getElementById('SelectAllChck').checked==true)
	{
		
		 for (i =0; i<componentCheckCount; i++)
		 {
		 	if(document.getElementById('chkComponents'+i)!=null)
		 	{
		 		if(document.getElementById('chkComponents'+i).disabled!=true)
		 			document.getElementById('chkComponents'+i).checked=true;
		 	}
		 }
		
	}
	else
	{
		for (i =0; i<componentCheckCount; i++)
		 {
		 	if(document.getElementById('chkComponents'+i)!=null)
			 	document.getElementById('chkComponents'+i).checked=false;
		 }
	}	
}
//End-[02112012]POC POINT : Changed By Ashutosh
function AddComponents()
	{
			if(!(isView == null || isView == 'null'))
			{
				return;
			}else
			{
				if(document.getElementById('txtPODetailNo').value == 0 && document.getElementById('txtBillingLvl').value!=2 )
				{
					alert('Please Select PO first');
					return false;
				}
				else if(document.getElementById('txtBillingLvl').value==2 && editSolutionChangeOldProduct ==1)
				{
					alert('You cannot add Component at Billing level is PO');
					return false;
				}
				//[004] Start
				if(document.getElementById('hdnConfigValue').value==1)
				{	//[005] Start
					if(document.getElementById('txtLicenceCo').value!=282 && document.getElementById('txtLicenceCo').value!=382)
					{
						alert("Components Addition not allowed as License Company is not 402-BAL 95P or 402-BAL 95P_GB License Company");
						return false;
					}	//[005] End
				}
				//[004] End
				//[0026] start
				//Added by Ashutosh for validation component for currency
				var callerWindowObj1 = dialogArguments;				
				var varConfig=document.getElementById('hdnConfigValue').value;
				if(varConfig==1 || varConfig==2 || varConfig==3 || varConfig==4 || varConfig==5 || varConfig==6 || varConfig==101 || varConfig==102 || varConfig==103 || varConfig==104 || varConfig==105 || varConfig==106)
				{
					if(callerWindowObj1.document.getElementById('curShortCode').value!='INR')
					{
						alert("Components Addition not allowed for Currency other than INR");
						return false;
					}
				} 
				//[0026] End
				var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=getAllComponents";
		   		window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
				//resetComponentsRows();
				//getComponentAttribute();
			}
	
	}
// 001 start	
	
function DeleteComponentdetails()
  {
  	var result;
	componentTab = document.getElementById('tableComponents');
	var rowlen= (componentTab.rows.length)-2;
	var rowCounter = 1;
	var myform=document.getElementById("productCatelog");
	var componentID="";
	var countDelete=1;
	
	
	if(rowlen==0)
	{
		alert("please add one row to delete");
		return false;
	}
	if(rowlen==1)
	{
		
		if(document.forms[0].chkComponents.checked==true)
		{ 
	 		   countDelete++;
	 	      
	 	  	
        		if(componentID=="")
	    		{
	    			componentID = document.forms[0].component_infoIDs.value;
	    			
	    		}	
	    		else
	    		{
	    			componentID = componentID + "," + document.forms[0].component_infoIDs.value;
	    			
	    		}
	    		
	    		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		  		var jsData =			
					    {
					    	componentIDList:componentID
					       
					    };
		    			var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
					     result = jsonrpc.processes.deleteComponents(jsData);
					     alert(result);
					     resetComponentsRows();
						 getComponentAttribute();
		    		  
		    		   
		   		    }       
		    		else
							{
								alert('No Component Selected.');
								return;
							}            
	    		}
	    		
	
	else
	{
		for(var j=1;j<=rowlen;j++)
		{
		    
		  
			if(myform.chkComponents[(j-1)].checked==true)
	     	{
	     		countDelete++;
	   			if(componentID=="")
	    		{
	    			componentID = myform.component_infoIDs[(j-1)].value;
	    			
	    		}	
	    		else
	    		{
	    			componentID = componentID + "," + myform.component_infoIDs[(j-1)].value;
	    			
	    		}	
	      	}
		}
	   	var answer = confirm("Do You Want To Continue Deleting Components Line")
		if(answer)
		{
			//continue deleting
		}
		else
		{
			return false;
		}
	   	for(var j=rowlen;j>0;j--)
		{
			 if(document.forms[0].chkComponents)
	    		{
		    if(myform.chkComponents[(j-1)]==undefined)
			{
		 	  	if(myform.chkComponents.checked==true)
		     	{
		     		countDelete++;
		     	      var jsData =			
					     	{
					        	componentIDList:componentID
					          	
					    	};
						  var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
					      result = jsonrpc.processes.deleteComponents(jsData);
					      alert(result);
					      resetComponentsRows();
						  getComponentAttribute();
		    		  
							
	   		            
	   		          	}
	   		          
	   		         
	   		            
				  	}//if(document.forms[0].contactUpdateType.value == 2 )
				 
		        else
				 	{   	
			        
				 if(myform.chkComponents[(j-1)].checked==true)
		     	  {
		     		
		     		countDelete++;
				    var jsData =			
					     	{
					        	componentIDList:componentID
					          
					     	};	
	    					var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
							result=  jsonrpc.processes.deleteComponents(jsData);
							alert(result);
						  	resetComponentsRows();
							getComponentAttribute();
		   		         
	   		       		
		    }		    	    	
}

			}		
	} // for

} // else

		if(countDelete==1 && rowlen>0)
		
		 {
		     alert("please select one row to delete");
		 }
         
           
}
      
//001 end     
            
function drawComponentsTable(components,componentsName,packageID,packageName)
{
var count = document.getElementById('hdnComponentsCount').value;

/*for (var i=0 ,j=count; i < = components.length,j<count+components.length; i++,j++ ) 
{

} */
//Start-[02112012]POC POINT : Changed By Ashutosh
componentCheckCount=0;	
//Start-[02112012]POC POINT : Changed By Ashutosh

for(var i = 0;i<components.length;i++)
{
		var str;
		var tblRow=document.getElementById('tableComponents').insertRow();
		tblRow.onclick = function(){changeUpperBackground();};//Added by Ashutosh for highlighting row
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=logicalSINo[i];
		tblcol.className="innerdata col1 toprow";
		str ="<input name='chkComponents' id='chkComponents"+count+"' type='checkbox' value = '"+count+"'/>";	
		str=str+"<input type='hidden' name='component_infoID' id = 'component_infoID"+count+"' value=''>";	
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata secondColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='components' isRequired='0' id='components"+count+"'  value = ' " + components[i] + " ' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata descColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()'  class='inputDisabled' name='componentsName' isRequired='0' style='width:250px' id='componentsName"+count+"'  value = ' " + componentsName[i] + " ' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata secondColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='packageID' isRequired='0' style='width:100px' id='packageID"+count+"'  value = ' " + packageID[i] + " ' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata descColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='packageName' isRequired='0' style='width:250px' id='packageName"+count+"'  value = ' " + packageName[i] + " ' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata secondColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='status' isRequired='0' readonly='readonly' style='width:100px' id='status"+count+"'  value = 'NEW' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col4";
		//str ="<input type='text' class='inputBorder1' name='startdatelogic' isRequired='0' readonly='readonly' style='width:200px' id='startdatelogic"+count+"'  value = 'BILLING TRIGGER DATE' >";
		str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='txtCompStartDateLogic' id='txtCompStartDateLogic"+count+"' isRequired='1' Disp='' class='dropdownMargin' style='width:125px'><option value='-1' title='Select Start Date Logic'>Select Start Date Logic</option></select></td> ";
		str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartDays'  id='txtStartDays"+count+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td> ";
		str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+count+"' onblur='if(this.value.length > 0){return checknumber(this)}'><input type='hidden' name='hdnstartdate' value=''></td></tr></table> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
	    tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col4";
		//str ="<input type='text' class='inputBorder1' name='enddatelogic' isRequired='0' readonly='readonly' style='width:200px' id='enddatelogic"+count+"'  value = 'TILL DISCONNECTION' >";
		str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='txtCompEndDateLogic' id='txtCompEndDateLogic"+count+"' isRequired='1' Disp='' class='dropdownMargin' onchange='changeCompEndDays("+count+");' style='width:125px'><option value='-1' title='Select End Date Logic'>Select End Date Logic</option></select></td> ";
		str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndDays' id='txtEndDays"+count+"'  onblur='if(this.value.length > 0){return checknumber(this)}'></td> ";
		str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+count+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td></tr></table> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		//---------------[Start:Date Logic and End Date Logic]----------------------------------
			fillComponentStartEndDateLogic(count);			
			document.getElementById('txtCompStartDateLogic'+count).value="BTD";
			document.getElementById('txtCompEndDateLogic'+count).value="TD";			
		//---------------[End: Date Logic and End Date Logic]----------------------------------
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata dateField";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='startdate' isRequired='0' readonly='readonly' style='width:100px' id='startdate"+count+"'  value = '' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata dateField";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='enddate' isRequired='0' readonly='readonly' style='width:100px' id='enddate"+count+"'  value = '' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col1";
		str ="&nbsp;&nbsp;";
		CellContents = str;		
		tblcol.innerHTML = CellContents;
		
		
		//002 start
		var ctrlArry = new Array();
		ctrlArry[0] =  document.getElementById("components"+count);
		ctrlArry[1] =  document.getElementById("componentsName"+count);
		ctrlArry[2] =  document.getElementById("packageID"+count);
		ctrlArry[3] =  document.getElementById("packageName"+count);
		ctrlArry[4] =  document.getElementById("status"+count);		
		ctrlArry[5] =  document.getElementById("startdate"+count);
		ctrlArry[6] =  document.getElementById("enddate"+count);
		ctrlArry[7] =  document.getElementById("txtCompStartDateLogic"+count);
		ctrlArry[8] =  document.getElementById("txtCompEndDateLogic"+count);
		var callerWindowObj = dialogArguments;		
		fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry);
		count++;
		//002 end
		
		
		/*var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str ="<input type='text' class='inputBorder1' name='activeDate'  id='activeDate"+components[i]+"'  readonly='true'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar('forms[0].activeDate"+ components[i] +"');\" >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str ="<input type='text' class='inputBorder1' name='inactiveDate'  id='inactiveDate"+components[i]+"'  readonly='true'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar('forms[0].inactiveDate"+ components[i] +"');\" class=\"borderTabCal\" >";
		CellContents = str;
		tblcol.innerHTML = CellContents;*/
		
			
}

document.getElementById('hdnComponentsCount').value=count;
}

//--------------------------[ Add for Delete Components on click on Save Button ]-------------------
function DeleteComponent()
{	
	var chkComponentsLine=document.getElementsByName('chkComponents');
	if(chkComponentsLine==null)
	{
		alert('No Component Present');
		return;
	}
	
	var len=chkComponentsLine.length;
	if(len==null)
	{//only one chk box present

		if(chkComponentsLine.checked==true)
		{
			var answer = confirm("Do You Want To Continue Deleting Component Line")
			if(answer)
			{
				//continue deleting
			}
			else
			{
				return false;
			}
			deleteChargeLine(chkComponentsLine);
		}
		else
		{
			alert('No Component Selected.');
			return;
		}
	}
	else
	{
		var count=0;
		var answer = confirm("Do You Want To Continue Deleting Component Line");
		for(i=len-1;i>=0;i--)
		{
			if(chkComponentsLine[i].checked==true)
			{				
				if(answer)
				{
					//continue deleting
				}
				else
				{
					return false;
				}
				deleteChargeLine(chkComponentsLine[i]);
				count=count+1;
			}
		}
		if(count==0)
		{
			alert('No Component Selected.');
			return;
		}
	}
	//Start-[02112012]POC POINT : Changed By Ashutosh
	document.getElementById('SelectAllChck').checked=false;
	//Start-[02112012]POC POINT : Changed By Ashutosh	
}
function deleteComponentLine(current){
            //here we will delete the line
            while ( (current = current.parentElement)  && current.tagName !="TR");
            current.parentElement.removeChild(current);
}  
//--------------------------[ Add for Delete Components on click on Save Button ]-------------------
</script>

<body>
	<fieldset class="border1">
	<legend> 
		<input name="btn1" id="btnComponentsDetails"
				onClick="if(this.value == '+')getComponentAttribute();show('tblComponentsDetails',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
	<b>Components</b> </legend>
	<table border="1" cellpadding="0" cellspacing="0" class='main' id="tblComponentsDetails" style="display: none;">   
		<tr>
                <td class='tablefrozencolumn'>
                    <div id='divroot' class='root'>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='root'>
                            <tr>
                                <td class='inner frozencol colwidth head1'>
                                    
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div id='divfrozen2' class='frozen'>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='frozen'>
                            <tr>
                                <td class='inner frozencol toprow'>
                                    
                                </td>
                            </tr>                            
                        </table>
                    </div>
                </td>
                <td class='tablecontent' valign="top">
                    <div id='headscroll2' class='divhead' style="vertical-align:top">                               	
                        <table border="0" cellpadding="0" cellspacing="0" class='head1' height="5%">
						 <input type="hidden" name="hdnComponentsCount" value="0"/>    
                              <tr>
                                <td class='inner col1 head1'>
                                    Select <input type="checkbox" name="SelectAllChck" id="SelectAllChck" onclick="javascript:fnCheckAll();"/>
                                </td>
                                <td class='inner secondColumn head1'>
                                    Component
                                </td>
                                <td class='inner descColumn head1'>
                                    Component Name
                                </td>
                                <td class='inner secondColumn head1'>
                                    Package ID
                                </td>
                                <td class='inner descColumn head1'>
                                    Package Name
                                </td>
                                <td class='inner secondColumn head1'>
                                    Status
                                </td>                                
                                <td class='inner col4 head1' valign="top">
                                    <table border="1" width="100%">
											<tr>
												<td align="center" colspan="3">Start Date Logic</td>
											</tr>
											<tr>
												<!-- Start   [003] -->
												<td  align="center" width="150px">Date Logic</td>
												<!-- End   [003] -->
												<td  align="center" width="150px">Days</td>
												<td  align="center" width="150px">Month</td>
											</tr>
								  </table>
                                </td>
                                <td class='inner col4 head1' valign="top">
                                    <table border="1" width="100%">
											<tr>
												<td align="center" colspan="3">End Date Logic</td>
											</tr>
											<tr>
												<!-- Start   [003] -->
												<td  align="center" width="150px">Date Logic</td>
												<!-- End   [003] -->
												<td  align="center" width="150px">Days</td>
												<td  align="center" width="150px">Month</td>
											</tr>
									</table>
								</td>													
                                <td class='inner dateField head1'>
                                    Component Start Date
                                </td>
                                <td class='inner dateField head1'>
                                    Component End Date
                                </td>  
                                <td class='inner col1 head1'>
                                    Disconnect
                                </td>                                                               
                            </tr>                            
                        </table>
                    </div>
                    <div id='contentscroll2' class='content' onscroll='reposHeadComponent(this);'>
                        <table border="0" cellpadding="0" cellspacing="0" class='content' id='tableComponents'>                        	
                                                                            
                        </table>
                    </div>
                </td>
                <td class='tableverticalscroll' rowspan="2">
                    <div class='vertical-scroll-component' onscroll='reposVerticalComponent(this);'>
                        <div>
                        </div>
                    </div>
                    <div class='ff-fill'>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <div class='horizontal-scroll-component' onscroll='reposHorizontalComponent(this);'>
                        <div>
                        </div>
                    </div>
                </td>
            </tr>            
			<tr>
				<td colspan="9" align="center">					    
					    <input type="button" name="btnAddComponents" id="btnAddComponents" value="Add Components" onClick="AddComponents()">
					    <input type="button" name="btnDeleteComponents" id="btnDeleteComponents" value="Delete Components" onClick="DeleteComponent()">
				</td>
			</tr>
	</table>
</fieldset>
</body>
</html>