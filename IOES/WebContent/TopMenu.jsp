<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
  <tr height="25">
  	<td background="gifs/bg-header-main.jpg" width="50%" align="left"><img src="gifs/logo-inner.jpg">&nbsp;</td>
	<td background="gifs/bg-header-main.jpg" width="50%" align="right">&nbsp;<font face="verdana" size="4" color="white"><b><i>iB2B-Integrated Order Entry Management System</i></b></font></td>
</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="4%" align="left" ><img src="gifs/top/home.gif" onClick="goToHome()" alt="Home" title="Home"></img></td>
		
		<td width="4%" align="left"><img src="gifs/top/4.gif" onClick="validate()" style="display: block" id="saveIcon"  alt="Save" title="Save Information"></td>
		
		<td width="4%" align="left" ><img src="gifs/top/7.gif" alt="Print" title="Print" onclick="javascript:window.print();"></td>
		
		<td width="4%" align="left"> <img src="gifs/top/30.gif" onClick="cancelOrder()" style="display: none" id="cancelIcon"  alt="Cancel Order" title="Cancel Order"></td>
				
		<td width="4%" align="left" ><img src="gifs/top/11.gif" title="Attach Document" alt="Attach Document" id="attachIcon" onClick="return popitup('FileAttachment')"></td>		
		<td width="4%" align="left" ><img src="gifs/top/dd4.gif" title="View Document for Download" alt="View Document for Download" id="attachIcon" onClick="return popitup('UploadedFile')"></td>		
		<td width="4%" align="left" ><img src="gifs/top/9.gif" title="Close Order" alt="Close Order" id="attachIcon" onClick="return popitup('closeorder')"></td>
		<td width="4%" align="left" ><img src="gifs/top/24.gif" title="View Cancel And Copy Report" alt="View Cancel And Copy Report" id="attachIcon" onClick="fnViewCancelAndCopyReport(1,10)"></td>		

		<td width="4%" align="left" ><img src="gifs/top/6.gif" title="Add Global Account Manager" alt="Add Global Account Manager" id="addGam" onClick="return popitup('selectGAM')"></td>		
		<td width="4%" align="left"><img src="gifs/icon-order-history1.png" title="Approval Order History" width="25" height="25" alt="Approval Order History" id="apporderhistory" onclick="return popitup('approvalHistory')"></td>	
		
		<td width="4%" align="left" ><img src="gifs/top/N.jpg" title="New Order" alt="New Order" id="newOrderId" onClick="if(document.getElementById('orderType').value=='New'){fnOpenNewOrder();} else if(document.getElementById('orderType').value=='Change'){fnOpenChangeOrder();} "></td>				
		<td width="4%" align="left" ><a href="#" onclick="refreshTransaction('ACCOUNT');"><img border="0"
					src="${pageContext.request.contextPath}/gifs/refresh_icon2.gif"
					alt="Sync Account with CRM" id="refreshAccountNo"  width="20" height="21"  title="Sync Account with CRM" /></a>	</td>
		<td width="4%" align="left" ><a href="#"><img border="0"
					src="${pageContext.request.contextPath}/gifs/sync.jpg"
					alt="Sync 2D Product Values" width="20" height="21" onclick="insertUpdate2DProductValues();" title="Sync 2D Product Values" /></a>	</td>
		<td width="4%" align="left" ><img src="gifs/top/remove.JPG" title="Deleted Hardware Line Item Details" alt="Deleted Hardware Line Item Details" id="attachIcon" onClick="fnViewCancelledHardwareLineDetails()" width="20" height="21"></td>
		<td width="4%" align="left" ><img src="gifs/top/opportunity.jpg" title="Sync Opportunity ID" alt="Sync Opportunity ID" id="attachOpportunity"  style="display: none"  onClick="fnUpdateOpportunity()" width="20" height="21"></td>
		<td width="70%" align="right" ></td>

	</tr>
</table>


