<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="com.ibm.ioes.dao.EscalationDao" %>
<%@ page import="java.util.*" %>
<%@page import="com.ibm.ioes.forms.FieldAttibuteDTO"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Level Escalation</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>




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
    

        table.main
        {
            width: 100%;
            height: 221px;
            table-layout: fixed;
        }
        table.root
        {
            table-layout: fixed;
        }
        table.content1
        {
            table-layout: fixed;
            width: 100%;
        }
        table.head1
        {
            table-layout: fixed;
            width: 100%;
        }
        table.frozen
        {
            table-layout: fixed;
        }
        
        div.horizontal-scroll
        {
            width: 100%;
            height: 22px;
            overflow: hidden;
            overflow-x: scroll;
            border: solid 1px #666;
        }
        div.horizontal-scroll div
        {
            width: 2400px;
            height: 1px;
        }
        
         div.horizontal-scrollService
        {
            width: 100%;
            height: 22px;
            overflow: hidden;
            overflow-x: scroll;
            border: solid 1px #666;
        }
        div.horizontal-scrollService div
        {
            width: 1000px;
            height: 1px;
        }
        
        div.vertical-scroll
        {
            height: 227px;
            width: 100%;
            overflow: hidden;
            overflow-y: scroll;
            border: solid 1px #666;
        }
        
        div.vertical-scroll div
        {
            height:1700px;
            width: 1px;
        }
        
          div.vertical-scrollService
        {
            height: 290px;
            width: 100%;
            overflow: hidden;
            overflow-y: scroll;
            border: solid 1px #666;
        }
        
        div.vertical-scrollService div
        {
            height:1700px;
            width: 1px;
        }
        
        
        td.inner
        {
            border-left: 1px solid #666;
            border-bottom: 1px solid #666;
            padding: 0px;
            height: 30px;
        }
        td.innerHead
        {
            border-left: 1px solid #666;
            border-bottom: 1px solid #666;
            padding: 0px;
            height: 90px;
        }
        
        td.frozencol
        {
            border-right: 1px double #666;
            width: 1px;
        }
        td.col1
        {           
            width: 50px;
        }
        .col4
        {            
            width:175px;
        }
        .col3
        {            
            width:130px;
        }
        .col5
        {            
            width:250px;
        }
        td.bottomcol
        {
            /*border-bottom: 1px solid #666;*/
        }
        .col2, .col6, .col7, .col8, .col9, .col10
        {
            width: 100px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
        }
        
          .colSrNo
          {
            width: 40px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           .colRadio
          {
            width: 52px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           .colCheckBox
          {
            width: 48px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
         .colPublished
          {
            width: 59px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
          .colServiceNo
          {
            width: 90px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
           
           .colServiceType
          {
            width: 150px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
            .colLogicalSI
          {
            width: 90px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
          .colAttribute
          {
            width: 70px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
           }
           
           .colServiceStatus
          {
            width: 120px;
            overflow: hidden;
            text-overflow: ellipses;
            white-space: nowrap;
            text-align:center
           }
        
        td.head1
        {
            /*border-bottom: 1px solid #666;*/
            background-color: #CCCC99;
            border-top: 1px solid #666;
        }
        .rightcol
        {
            border-right: 1px solid #666;
        }
        .toprow
        {
            border-top: 1px;
        }
        div.root
        {
            margin-left: 0px;
            overflow: hidden;
            width: 100%;
            height: 26px;
            border-bottom: 1px solid #666;
        }
        div.frozen
        {
            overflow: hidden;
            width: 100%; /*border-bottom: 1px solid #666;*/
            height: 200px;
        }
        div.divhead
        {
            overflow: hidden;            
            width: 100%;
            border-left: 1px solid #666;
            border-right: 1px solid #666; /*border-bottom: 0px solid #666;*/
            border-bottom: 1px solid #666;
        }
        
        div.content1
        {
            overflow: hidden;
            width: 100%;
            height: 200px;
            border-left: 1px solid #666;
            border-right: 1px solid #666; /*border-bottom: 1px solid #666;*/
        }
        td.tablefrozencolumn
        {
            width: 1px;
            border-right: 3px solid #666;
        }
        td.tablecontent
        {
            width: 100%;
        }
        td.tableverticalscroll
        {
            width: 24px;
        }
        div.ff-fill
        {
            height: 23px;
            width: 23px;
            background-color: #ccc;
            border-right: 1px solid #666;
            border-bottom: 1px solid #666;
        }
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript">
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);


%>	
	
 	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
 	var mailDetails;
 	var roleid;
 	var levelAging;
 	var checkstatus;
 	var userId = "<%=objUserDto.getUserId() %>";	
 	path='<%=request.getContextPath()%>';
	
	function getTip_DD(obj,value,objName)
	{	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('searchForm');
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
	}
	
	
	
	
	
	function goToHome()
	{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	//showLayer();
	document.forms[0].submit();
	}
	
	
	//Gettting Role Name
	function fetchBusinessSegment(roleId)
	{
		roleid=roleId;
		   var combo = document.getElementById("businessSegmentId");	
   			var iCountRows = combo.length;
   			for (i = 1; i <  iCountRows; i++)
   			{
   		   combo.remove(1);
   			}
     	 var jsData =			
   			 {
				ROLE_ID:roleId
			};
	
		var businessSegment = jsonrpc.processes.fetchBusinessSegment(jsData);
		for(j=0;j<businessSegment.list.length;j++)
	    {
	    	var option = document.createElement("option");	    	
	   		option.text = businessSegment.list[j].BUSINESS_SEGMENT;
	   		option.title = businessSegment.list[j].BUSINESS_SEGMENT;//customer segment
			option.value = businessSegment.list[j].ID;
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
	
	
	// Agings of all the levels
	function fetchLevelDetails(Id)
	{  
			var jsData =			
    		{
				ID:Id
			};
			levelAging = jsonrpc.processes.fetchLevelDetails(jsData);
			
		document.getElementById("age1Id").value= levelAging.list[0].RM1_AGING;
		
		document.getElementById("age2Id").value = levelAging.list[0].RM2_AGING;
		
	   	document.getElementById("age3Id").value = levelAging.list[0].RM3_AGING;
	   
		document.getElementById("rowId2").value= levelAging.list[0].ID;
	}
 

 //cancelling 
 function cancelLevelDetails()
 {
 		document.getElementById("searchForm").reset();
 		window.history.back();
 	 
 }
 
 //mailid enabling,disabling
 function editMailbox(bEnable, textBoxID ,mail2textbox ,mail3textbox)
  {
  if(document.getElementById("roleId").value > 2)
  {
    		document.getElementById(textBoxID).disabled = !bEnable
   			document.getElementById(mail2textbox).disabled = !bEnable
   			document.getElementById(mail3textbox).disabled = !bEnable
   }
  }
  
  
  function editMailtext()
  {
  if(document.getElementById("roleId").value > 2)
  {
  document.getElementById('mail1Id').disabled = false;
  document.getElementById('mail2Id').disabled = false;
  document.getElementById('mail3Id').disabled = false;
  }else
  {
  document.getElementById('mail1Id').disabled = true;
  document.getElementById('mail2Id').disabled = true;
  document.getElementById('mail3Id').disabled = true;
  }
  }
  
  //email validation
  function validateEmail(emailField){
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (reg.test(emailField.value) == false) 
        {
            alert('Email Address Must be Like    : abc@in.airtel.com');
          
            return false;
        }

        return true;
	}


function checkState()
{
		
		if(document.getElementById('roleId').value < 1 )
 		{
 		alert("Please Select Stage");
 		}
 		 		
	if(document.getElementById('businessSegmentId').value < 1 )
 	{
 		alert("Please Select BusinessSegment");
 		
 	}
}


	var age1;
	function validateAge()
	{
	 
    re = /^[0-9]+$/;
    age1=document.getElementById('age1Id').value;
    age2=document.getElementById('age2Id').value;
    if(re.test(document.getElementById("age1Id").value )  )
    {
    
 	
    }
    else
    {
 	 alert('Aging Must Be Numeric Value for Level1');
  	
   }
   if( parseInt(age1)>parseInt(age2) )
 	{
 	
 	alert('Level1 Aging'+age1+'must be less than Level2 Aging:'+age2);
 	
 	}
 	
      

}

	
	
	var age2;		
	function validateAge2()
	{
	 	
    	re = /^[0-9]+$/;
    	 
    if(re.test(document.getElementById("age2Id").value) )
    {
      
    }
    else
    {
 	alert('Aging Must Be Numeric Value for Level2');
   }
	age1=document.getElementById('age1Id').value;
    age2=document.getElementById('age2Id').value;
    age3=document.getElementById('age3Id').value;
   
   if( parseInt(age2) == parseInt(age1) )
   	{
 	
 	alert('Level2 Aging must be greater than Level1 Aging:'+age1);
 	
 	}
 	
 	 if( parseInt(age2) == parseInt(age3) )
   	{
 	
 	alert('Level2 Aging must be greater than Level3 Aging:'+age3);
 	
 	}
    if( parseInt(age2) < parseInt(age1) )
   	{
 	
 	alert('Level2 Aging must be greater than Level1 Aging:'+age1);
 	
 	} 
 	if( parseInt(age2)> parseInt(age3) )
  
 	{
 	
 	alert('Level2 Aging:'+age2+' must be less than Level3 Aging:'+age3);
 	
 	}
 	 
}

	
	
	var age3;		
	function validateAge3()
	{
	 	
    	re = /^[0-9]+$/;
    	 
    if(re.test(document.getElementById("age3Id").value) )
    {
      
    }
    else
    {
 	alert('Aging Must Be Numeric Value for Level3');
   }
	age1=document.getElementById('age1Id').value;
	age2=document.getElementById('age2Id').value;
    age3=document.getElementById('age3Id').value;
    
    if( parseInt(age3) == parseInt(age2) )
   	{
 	
 	alert("Level3 Aging must be greater than Level2 Aging:"+age2);
 	
 	}
    
    if( parseInt(age3) < parseInt(age2) )
   	{
 	
 	alert("Level3 Aging must be greater than Level2 Aging:"+age2);
 	
 	} 
 	if( parseInt(age3) < parseInt(age1) )
   	{
 	
 	alert("Level3 Aging must be greater than Level1 Aging:"+age1);
 	
 	} 
}



//fetching mail details
 function fetchmailDetails()
 {

	if(document.getElementById("roleId").value > 2)
	{
	
 	document.getElementById("mail1Id").value = levelAging.list[0].RM1_MAILID;
 	document.getElementById("mail2Id").value = levelAging.list[0].RM2_MAILID;
 	document.getElementById("mail3Id").value = levelAging.list[0].RM3_MAILID;
  if(document.getElementById("mail1Id").value == 'default')
  {
    document.getElementById("mail1Id").value= '';
    
  }
  if(document.getElementById("mail2Id").value == 'default')
  {
  document.getElementById("mail2Id").value= '';
  }
  if(document.getElementById("mail3Id").value == 'default')
  {
  document.getElementById("mail3Id").value= '';
  }
 
 } else  {
 	document.getElementById("mail1Id").value = 'default';
 	document.getElementById("mail2Id").value = 'default';
 	document.getElementById("mail3Id").value = 'default';
  

  }
 }
 
 //setting changed mail details of level 1
 var mail1;
 var level1update;
 function mail1change()
 {
	
 	var mailData=document.getElementById("mail1Id").value;
 	var age1Data=document.getElementById("age1Id").value;	
 	
    if(levelAging.list[0].RM1_MAILID != mailData || levelAging.list[0].RM1_AGING != age1Data)
    {
	
 	level1update = 1;
	
 	
 	}else{
 	level1update= 0 ;
 	}
 
 
 }
 var mail2;
 var level2update;
 //setting changed mail details of level 2
 function mail2change()
 {
 	
 	var mail2Data=document.getElementById("mail2Id").value;
 	var age2Data=document.getElementById("age2Id").value;	
   if(levelAging.list[0].RM2_MAILID != mail2Data || levelAging.list[0].RM2_AGING != age2Data)	
  {

 	 level2update = 1 ;

 	
 }else{
 level2update= 0 ;
 	}
 }
 
 //setting changed mail details of level 3 
 var level3update;
function mail3change()
 {
 	
 	var mail3Data=document.getElementById("mail3Id").value;
 	var age3Data=document.getElementById("age3Id").value;	
   	if(levelAging.list[0].RM3_MAILID != mail3Data || levelAging.list[0].RM3_AGING != age3Data)	
 
 	{
 	
 	level3update = 1 ;
 
 	
 	
 	}else{
	level3update= 0 ;
 	}
 } 
 
 
 //submitting changed mailid data to db
 	var mail3;
 function submittingMailDetails()
 {
 
 if(document.getElementById("mail1Id").value !='' && document.getElementById("age1Id").value !='')
 {
 mail1change();
 }else{alert("Enter value for aging or mailid of level1 ");}
if(document.getElementById("mail2Id").value !='' && document.getElementById("age2Id").value !='')
 {
 mail2change();
 }else{alert("Enter value for aging or mailid of level2 ");}
if(document.getElementById("mail3Id").value !='' && document.getElementById("age3Id").value !='')
 {
 mail3change();
 }else{
 		alert("Enter value for aging or mailid of level3 ");
 }
 
 if( level1update == 1 || level2update == 1 || level3update == 1 ){
 
 	var jsData =
 	{
 	CREATED_BY:userId,
 	ID:document.getElementById("rowId2").value,
 	RM1_MAILID:document.getElementById("mail1Id").value,
 	RM1_AGING:document.getElementById("age1Id").value,
 	RM2_MAILID:document.getElementById("mail2Id").value,
 	RM2_AGING:document.getElementById("age2Id").value,
 	RM3_MAILID:document.getElementById("mail3Id").value,
 	RM3_AGING:document.getElementById("age3Id").value,
 	isUpdatedLevel1:level1update,
 	isUpdatedLevel2:level2update,
 	isUpdatedLevel3:level3update
 	};
 	
 	 mail3 = jsonrpc.processes.setchangedMailId3(jsData);
 	 alert("data updation status:"+mail3);
 }
 
 
 
 }
 

function enableFieldset()
{
document.getElementById('agingfieldset').disabled = false;

} 
function submitData()
{
var  age11=document.getElementById("age1Id").value;
var  age12=document.getElementById("age2Id").value;
var  age13=document.getElementById("age3Id").value;
 
if(parseInt(age11) > parseInt(age12))
{
alert("Level1 aging Must be less than Level2");
}else if(parseInt(age12) > parseInt(age13))
{
alert("Level2 aging Must be less than Level3");
}
else

{
	submittingMailDetails();
}


}
function disableFieldset()
{
document.getElementById('agingfieldset').disabled = true;

} 
 function clearfields()
 {
 document.getElementById("mail1Id").value='default';
 document.getElementById("mail2Id").value='default';
 document.getElementById("mail3Id").value='default';
 document.getElementById("age1Id").value='';
 document.getElementById("age2Id").value='';
 document.getElementById("age3Id").value='';
 }
 
</script>


</head>
<body  bgcolor="#F7F7E7" >
		
<html:form action="/EscalationAction" styleId="searchForm" method="post">
	<bean:define id="formBean" name="EscalationBean"></bean:define>
	<div><table width="100%"  border="0" cellspacing="0" cellpadding="0" >
  		<tr height="25">
  		<td background="gifs/bg-header-main.jpg" width="50%" align="left"><img src="gifs/logo-inner.jpg">&nbsp;</td>
		<td background="gifs/bg-header-main.jpg" width="50%" align="right">&nbsp;<font face="verdana" size="4" color="white"><b><i>iB2B-Integrated Order Entry Management System</i></b></font></td>
		</tr>
		</table></div>
	
	<div><img src="./gifs/top/home.gif" onClick="goToHome();"></img></div>
	<div class="head"> <span><b>Level Escalation </b></span> </div>
	
<div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="40%" valign="top">

			<fieldset class="border1"><legend> <b>Select Stage</b> </legend>


			<table border="0" cellspacing="0" cellpadding="1"
				style="margin-top:7px ">
				<tr width="100%">
					<td width="6%">Stage</td>
					<td width="39%"><select
						onmouseover='getTip_DD(this,this.selectedIndex,this.name)'
						name="roleId" style="width:150px" id="roleId"
						class="dropdownMargin"
						onchange="fetchBusinessSegment(this.value); clearfields(); disableFieldset(this);">
						<option value="0">Select Role Name</option>
						<option value="1">AM</option>
						<option value="2">PM</option>
						<option value="3">COPC</option>
						<option value="4">SED</option>
					</select></td>

					<td width="16%">Business Segment</td>
					<td width="30%"><select
						onmouseover='getTip_DD(this,this.selectedIndex,this.name)'
						name="businessSegmentId" style="width:150px"
						id="businessSegmentId" class="dropdownMargin"
						onchange="fetchLevelDetails(this.value);  fetchmailDetails(); enableFieldset(this); editMailtext(this)"  >
						<option value="0">Select Business Segment</option>
						
					</select></td>
					
				</tr>

			</table>
			</fieldset>
</td>
</tr>
			
<tr>
			<td width="40%" valign="top">
			<fieldset disabled class="border1" id="agingfieldset"><legend> <b>Level Details</b> </legend>


			<table border="0" cellspacing="0" cellpadding="1"
				style="margin-top:7px " width="50%">
				<th width="21%">Level</th>
				<th width="12%">Aging(inHours)</th>
				<th width="5%" align="right"><!--Enable--></th>
				<th width="12%">MailId</th>



				<tr>
					<td height="29" width="21%">Level 1</td>
					<td align="center" height="29" width="12%"><input type="text" id="age1Id" name="age1Id" onblur="checkState(); validateAge(this);"  /></td>
					<td width="5%" height="29" align="right"><!--<input	type="checkbox" id="check1"	onclick=" editMailbox(this.checked, 'mail1Id','mail2Id','mail3Id'); fetchmailDetails(); " />--></td>
					<td align="center" height="29" width="12%"><input type="text" value="default" id="mail1Id" name="mail1Id" disabled="disabled" onblur="validateEmail(this);" /></td>

				</tr>
				<tr>
					<td height="29" width="21%">Level 2</td>
					<td align="center" height="29" width="12%"><input type="text" id="age2Id" name="age2Id" onblur="checkState(); validateAge2(this);" /></td>
					<td width="5%" height="29" align="right"><!--<input type="checkbox" id="check2" onchange="editMailbox(this.checked, );" onclick="fetchmailDetails2();"  >--></td>
					<td align="center" height="29" width="12%"><input type="text" id="mail2Id" value="default" name="mail2Id" disabled="disabled" onblur="validateEmail(this);" /></td>

				</tr>
				<tr>
					<td height="29" width="21%">Level 3</td>
					<td align="center" height="29" width="12%"><input type="text" id="age3Id" name="age3Id" onblur="checkState(); validateAge3(this);"  /></td>
					<td width="5%" height="29" align="right"><!--<input type="checkbox" id="check3" onchange="editMailbox(this.checked, );" onclick="fetchmailDetails3();" >--></td>
					<td align="center" height="29" width="12%"><input type="text" id="mail3Id" value="default" name="mail3Id" disabled="disabled" onblur="validateEmail(this);" /></td>

				</tr>
				<tr>
					<td width="10%"><input type="hidden" id="rowId2" /></td>
					<td width="25%" align="right"><input type="button" value="Submit" onclick="submitData();" /></td>
					<td width="30%" align="center">
					
					<input type="button" value="Cancel"	onclick="cancelLevelDetails();" /></td>

				</tr>

			</table>
			</fieldset>
			
			</td>
		</tr>
	</table>
</div>
</html:form>
</body>
</html>
