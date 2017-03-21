<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[0026]	 Ashutosh Singh	26-Apr-13	-------			Validation for not allowing to add Components when Currency is not INR -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Components</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="./css/chargeTableFreeze.css" type="text/css" rel="stylesheet">
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
		 		document.getElementById('chkComponents'+i).checked=true;
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
	//[0026] Start	
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
	//[0026] END	
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=getAllComponents&poNumber="+<%=request.getParameter("poNumber")%>;
   	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	
	}
//Onclick backgroud Color change for row : Done by Ashutosh
var upperSectionSelectedTR=null;
 function changeUpperBackground()
 {
 	var current = window.event.srcElement;
 	while ( (current = current.parentElement)  && current.tagName !="TR");
	current.style.backgroundColor = "blue";
	if(upperSectionSelectedTR!=null)
	{
		upperSectionSelectedTR.style.backgroundColor = "#F7F7E7";//body color
	}
	upperSectionSelectedTR=current;
 }
function drawComponentsTable (components,componentsName,packageID,packageName)
{
	var counter = parseInt(document.getElementById('hdnComponentsCount').value);
	//Start-[02112012]POC POINT : Changed By Ashutosh
	componentCheckCount=0;	
	//Start-[02112012]POC POINT : Changed By Ashutosh

	for (var i = 0; i <  components.length; i++ ) 
	{  
   		
		
	    var str;
	
		   
		var tblRow=document.getElementById('tableComponents').insertRow();
		tblRow.onclick = function(){changeUpperBackground();};//Added by Ashutosh for highlighting row
		var str="";
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=logicalSINo[i];
		tblcol.className="innerdata col1 toprow";
		str ="<input name='chkComponents' id='chkComponents"+counter+"' type='checkbox' value = '"+counter+"'/>";
		str=str+"<input type='hidden' name='component_infoID' id = 'component_infoID"+counter+"' value=''>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=logicalSINo[i];
		tblcol.className="innerdata secondColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='components' id='components"+counter+"' isRequired='0'  value = ' " + components[i] + " ' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata descColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='componentsName' style='width:250px' id='componentsName"+counter+"' isRequired='0'  value = ' " + componentsName[i] + " ' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata secondColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='packageID' isRequired='0' style='width:150px' id='packageID"+counter+"'  value = ' " + packageID[i] + " ' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata descColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='packageName' isRequired='0' style='width:250px' id='packageName"+counter+"'  value = ' " + packageName[i] + " ' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata secondColumn";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='status' isRequired='0' readonly='readonly' style='width:150px' id='status"+counter+"'  value = 'NEW' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col4";
		//str ="<input type='text' class='inputBorder1' name='startdatelogic' isRequired='0' readonly='readonly' style='width:200px' id='startdatelogic"+counter+"'  value = 'BILLING TRIGGER DATE' >";
		str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='txtCompStartDateLogic' id='txtCompStartDateLogic"+counter+"' isRequired='1' Disp='' class='dropdownMargin' style='width:125px'><option value='-1' title='Select Start Date Logic'>Select Start Date Logic</option></select></td> ";
		str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartDays'  id='txtStartDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td> ";
		str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'><input type='hidden' name='hdnstartdate' value=''></td></tr></table> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
	    tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col4";
		//str ="<input type='text' class='inputBorder1' name='enddatelogic' isRequired='0' readonly='readonly' style='width:200px' id='enddatelogic"+counter+"'  value = 'TILL DISCONNECTION' >";
		str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='txtCompEndDateLogic' id='txtCompEndDateLogic"+counter+"' isRequired='1' Disp='' class='dropdownMargin' onchange='changeCompEndDays("+counter+");' style='width:125px'><option value='-1' title='Select End Date Logic'>Select End Date Logic</option></select></td> ";
		str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndDays' id='txtEndDays"+counter+"'  onblur='if(this.value.length > 0){return checknumber(this)}'></td> ";
		str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td></tr></table> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		//---------------Start Date Logic and End Date Logic----------------------------------
			fillComponentStartEndDateLogic(counter);
			document.getElementById('txtCompStartDateLogic'+counter).value='BTD';
			document.getElementById('txtCompEndDateLogic'+counter).value='TD';
		//------------------------------------------------------------------------------------
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata dateField";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='startdate' isRequired='0' readonly='readonly' style='width:100px' id='startdate"+counter+"'  value = '' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
	    tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata dateField";
		str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' name='enddate' isRequired='0' readonly='readonly' style='width:100px' id='enddate"+counter+"'  value = '' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		// 001 start
		var ctrlArry = new Array();
		ctrlArry[0] =  document.getElementById("components"+counter);
		ctrlArry[1] =  document.getElementById("componentsName"+counter);
		ctrlArry[2] =  document.getElementById("packageID"+counter);
		ctrlArry[3] =  document.getElementById("packageName"+counter);
		ctrlArry[4] =  document.getElementById("status"+counter);		
		ctrlArry[5] =  document.getElementById("startdate"+counter);
		ctrlArry[6] =  document.getElementById("enddate"+counter);
		ctrlArry[7] =  document.getElementById("txtCompStartDateLogic"+counter);
		ctrlArry[8] =  document.getElementById("txtCompEndDateLogic"+counter);
		var callerWindowObj = dialogArguments;		
		fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry);
		
		// 001 end

		
		//var tblcol=tblRow.insertCell();
		//tblcol.align = "left";
		//tblcol.vAlign="top";
		//str=logicalSINo[i];
		//str ="<input type='text' class='inputBorder1' name='activeDate'  id='activeDate"+components[i]+"'  readonly='true'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar('forms[0].activeDate"+ components[i] +"');\" >";
		//str ="<input type='text' class='inputBorder1' isRequired='0' name='activeDate' id='activeDate"+i+"' readonly='true'> <font size='1'><a href=\"javascript:show_calendar('forms[0].activeDate"+ i +"');\" class=\"borderTabCal\"><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Select Date'></a></font>";
		//str ="<input type='text' class='inputBorder1' name='activeDate' id='activeDate"+i+"' readonly='true'><font size='1'><a href=\"javascript:show_calendar('forms[0].activeDate"+ i +"');\" class=\"borderTabCal\"><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...'></a></font>";
		//str ="<input type='text' class='inputBorder1' name='activeDate' id='activeDate"+components[i]+"'  value = '' >";
		//CellContents = str;
		//tblcol.innerHTML = CellContents;
		
		//var tblcol=tblRow.insertCell();
		//tblcol.align = "left";
		//tblcol.vAlign="top";
		//str=logicalSINo[i];
		
		//str ="<input type='text' class='inputBorder1' name='inactiveDate' id='inactiveDate"+i+"' readonly='true'><font size='1'><a href=\"javascript:show_calendar('forms[0].inactiveDate"+ i +"');\" class=\"borderTabCal\"><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...'></a></font>";
		//str ="<input type='text' class='inputBorder1' name='inactiveDate'  id='inactiveDate"+components[i]+"'  readonly='true'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar('forms[0].inactiveDate"+ components[i] +"');\" class=\"borderTabCal\" >";
		
		//str ="<input type='text' class='inputBorder1' name='inactiveDate' id='inactiveDate"+components[i]+"'  value = '' >";
		//CellContents = str;
		//tblcol.innerHTML = CellContents;
		
		counter++;
		
}

document.getElementById('hdnComponentsCount').value=counter;

}
//------------------[ Start:ComponentStartDateLogic and ComponentEndDateLogic ]-----------------------------------------------------------------
function fillComponentStartEndDateLogic(count){
	try
	{										
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var startDateLogicList = jsonrpc.processes.fillComponentStartDateLogic();
		var cboStartDateLogic=document.getElementById('txtCompStartDateLogic'+count);
		
	    for (i = 1; i <  startDateLogicList.list.length; i++)
	    {
	       cboStartDateLogic.remove(1);
	    } 	    
	    for(i=0;i<startDateLogicList.list.length;i++)
	    {	    	
	    	var option = document.createElement("option");
	   		option.text = startDateLogicList.list[i].componentStartDateLogicDesc;
			option.value = startDateLogicList.list[i].componentStartDateLogicId;
			option.title = startDateLogicList.list[i].componentStartDateLogicDesc;			
			try 
			{
				cboStartDateLogic.add(option, null); //Standard
			}
			catch(error) 
			{
				cboStartDateLogic.add(option); // IE only
			}
	    }
	    
	    var endDateLogicList = jsonrpc.processes.fillComponentEndDateLogic();
		var cboEndDateLogic=document.getElementById('txtCompEndDateLogic'+count);
		
	    for (i = 1; i <  endDateLogicList.list.length; i++)
	    {
	       cboEndDateLogic.remove(1);
	    } 	    
	    for(i=0;i<endDateLogicList.list.length;i++)
	    {	    	
	    	var option = document.createElement("option");
	   		option.text = endDateLogicList.list[i].componentEndDateLogicDesc;
			option.value = endDateLogicList.list[i].componentEndDateLogicId;
			option.title = endDateLogicList.list[i].componentEndDateLogicDesc;			
			try 
			{
				cboEndDateLogic.add(option, null); //Standard
			}
			catch(error) 
			{
				cboEndDateLogic.add(option); // IE only
			}
	    }
    }
    catch(e)
	{
		alert("Error occurred !!");		
	}
}
function changeCompEndDays(index)
{
	if(document.getElementById('txtCompEndDateLogic'+index).value=='TD')
	{
	  document.getElementById('txtEndDays'+index).value=0;
	  document.getElementById('txtEndMonth'+index).value=0;		  		
	}
}
//------------------[ End:ComponentStartDateLogic and ComponentEndDateLogic ]-----------------------------------------------------------------
function DeleteComponents()
{

	//var myForm=document.getElementById('searchForm');

	var chkComponentLine=document.getElementsByName('chkComponents');
	if(chkComponentLine==null)
	{
		alert('No Component Present');
		return;
	}

	var len=chkComponentLine.length;
	if(len==null)
	{	//only one chk box present
		if(chkComponentLine.checked==true)
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
			deleteComponentLine(chkComponentLine);			
		}
		else
		{
			alert('No Component Selected.');
			return;
		}
	}
	else
	{//multiple checkboxex present
		var count=0;
		var answer = confirm("Do You Want To Continue Deleting Component Line");
		for(i=len-1;i>=0;i--)
		{
			if(chkComponentLine[i].checked==true)
			{
				//var answer = confirm("Do You Want To Continue Deleting Charge Line")
				if(answer)
				{
					//continue deleting
				}
				else
				{
					return false;
				}
				deleteComponentLine(chkComponentLine[i]);
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
function deleteComponentLine(current) {

            //here we will delete the line
            while ( (current = current.parentElement)  && current.tagName !="TR");
            current.parentElement.removeChild(current);
      }  
</script>
<script language='javascript' type='text/javascript'>
         function reposHeadComponent(e) {
            var h = document.getElementById('headscroll2');
            h.scrollLeft = e.scrollLeft;
            var f = document.getElementById('divfrozen2');
            f.scrollTop = e.scrollTop;
        }
        function reposHorizontalComponent(e) {
            var h = document.getElementById('headscroll2');
            var c = document.getElementById('contentscroll2');
            h.scrollLeft = e.scrollLeft;
            c.scrollLeft = e.scrollLeft;
            
        }
        function reposVerticalComponent(e) {
            var h = document.getElementById('divfrozen2');
            var c = document.getElementById('contentscroll2');
            h.scrollTop = e.scrollTop;
            c.scrollTop = e.scrollTop;
           
        }
</script>
<body>
	<fieldset class="border1">
	<legend> 
		<input name="btn1" id="btnComponentsDetails"
				onClick="show('tblComponentsDetails',this)" type="button" value="+"
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
					    <input type="button" name="btnAddComponents" value="Add Components" onClick="AddComponents()">
					    <input type="button" name="btnDeleteComponents" value="Delete Components" onClick="DeleteComponents()">
				</td>
			</tr>
	</table>
</fieldset>
</body>
</html>