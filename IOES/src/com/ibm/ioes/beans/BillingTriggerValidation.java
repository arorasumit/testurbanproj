//Tag Name Resource Name  Date		CSR No			Description
//[0015]	 Raveendra 	25-jun-2015			Billing Efficiency -Auto Billing trigger for Zero value and PD
package com.ibm.ioes.beans;

import java.util.ArrayList;
import java.util.HashSet;

import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.utilities.Messages;

public class BillingTriggerValidation {

	private String chkBoxEnable=null;
	private String chkBoxColorCode=null;
	private String ori_colorcode=null;
	private String locNoForEdit=null;
	private String locDateForEdit=null;
	private String btdForEdit=null;
	private String helperRemarks=null;
	private String billingTriggerActionStatus=null;//required,not_required,done
	private String locdataforhwSale=null;//notfilled,filled,na
	private ViewOrderDto lineItemInfo=null;
	private OrderHeaderDTO orderInfo=null;
	private ArrayList<ViewOrderDto> eventids=null;
	private String locRecDateForEdit=null; 
	private final String editableControlClass="inputborder1";
	private final String nonEditableControlClass="inputborder2";
	private String isHide=null;
	private String locrecdataforhwSale=null;//notfilled,filled,na
	/*public int isAnyUserActionPendingForBillingTrigger()
	{
		
	}*/
	
	public String getChkBoxEnable_HTMLAttribute() {
		if("allow".equalsIgnoreCase(chkBoxEnable))
		{
			return "";	
		}
		else
		{
			return "disabled";
		}
	}
	
	public String getLocNoForEdit_HTMLAttribute() {
		if("allow".equalsIgnoreCase(locNoForEdit))
		{
			return "";	
		}
		else
		{
			return "disabled";
		}
	}
	
	public String getLocDateForEdit_HTMLAttribute() {
		if("allow".equalsIgnoreCase(locDateForEdit))
		{
			return "";	
		}
		else
		{
			return "disabled";
		}
	}
	
	public String getChkBoxEnable_HTMLClass() {
		if("allow".equalsIgnoreCase(chkBoxEnable))
		{
			return editableControlClass;	
		}
		else
		{
			return nonEditableControlClass;
		}
	}
	
	public String getLocNoForEdit_HTMLClass() {
		if("allow".equalsIgnoreCase(locNoForEdit))
		{
			return editableControlClass;	
		}
		else
		{
			return nonEditableControlClass;
		}
	}
	public String getLocDateForEdit_HTMLClass() {
		if("allow".equalsIgnoreCase(locDateForEdit))
		{
			return editableControlClass;	
		}
		else
		{
			return nonEditableControlClass;
		}
	}
	public String getBtdForEdit_HTMLClass() {
		if("allow".equalsIgnoreCase(btdForEdit))
		{
			return editableControlClass;	
		}
		else
		{
			return nonEditableControlClass;
		}
	}

	public String getChkBoxColorCode() {
		return chkBoxColorCode;
	}

	public BillingTriggerValidation() {
		initialiseState();
	}
	
	public void resetStateToNew()
	{
		initialiseState();
	}
	
	private void initialiseState()
	{
		chkBoxEnable="allow";
		chkBoxColorCode="white";
		ori_colorcode=null;
		locNoForEdit="allow";
		locDateForEdit="allow";
		btdForEdit="allow";
		helperRemarks="-";
		billingTriggerActionStatus="required";
		
		lineItemInfo=null;
		orderInfo=null;
		locRecDateForEdit = "allow";
		isHide=null;
	}
	
	public void setSourceData(ViewOrderDto lineItemInfo,OrderHeaderDTO orderInfo,ArrayList eventids)
	{
		this.lineItemInfo=lineItemInfo;
		this.orderInfo=orderInfo;
		this.eventids=eventids;
	}
	public void computeProperties()
	{
		// 1st Filter , if any action is taken /not taken on charges/line in Order 
		if("NEW".equals(lineItemInfo.getLineOldOrNew())) {
				//if new Line Item
				chkBoxEnable="allow";
				chkBoxColorCode="white";
				locNoForEdit="allow";
				locDateForEdit="allow";
				btdForEdit="allow";
				helperRemarks+="NEW-";
				billingTriggerActionStatus="required";
				locRecDateForEdit = "allow";
				
					if("1".equals(lineItemInfo.getDuplicate_cktid())) 
					{
				
						chkBoxEnable="deny";
						locNoForEdit="deny";
						locDateForEdit="deny";
						btdForEdit="deny";
						locRecDateForEdit = "deny";
				
					}
					
					
					if(("Service".equalsIgnoreCase(lineItemInfo.getServiceType())) || ("Bandwidth".equalsIgnoreCase(lineItemInfo.getServiceType())))
					{
						if(lineItemInfo.getCktId()==null || lineItemInfo.getPmProvisioningDate()==null) 
						{
						   isHide="Yes";
						}
						
					}else if("Hardware".equalsIgnoreCase(lineItemInfo.getServiceType()))
					{
						if(lineItemInfo.getCktId()==null || lineItemInfo.getPmProvisioningDate()==null || lineItemInfo.getChallen_No()==null || lineItemInfo.getChallen_date()==null ) 
						{
						   isHide="Yes";
						}
					}
		}
		else if("OLD".equals(lineItemInfo.getLineOldOrNew()) && !"1".equals(lineItemInfo.getIsLineDisconnected())) {
					
					chkBoxEnable="allow";
					chkBoxColorCode="white";
					locNoForEdit="allow";
					locDateForEdit="allow";
					btdForEdit="allow";
					billingTriggerActionStatus="required";
					locRecDateForEdit="allow";
				
		}else if("OLD".equals(lineItemInfo.getLineOldOrNew()) && "1".equals(lineItemInfo.getIsLineDisconnected())) {
			//if Old Disconnected Line Item
			chkBoxEnable="allow";
			chkBoxColorCode="white";
			locNoForEdit="deny";
			locDateForEdit="deny";
			btdForEdit="allow";
			billingTriggerActionStatus="required";
			helperRemarks="OLDDISCONNECTED-";
			locRecDateForEdit="allow";
			}
		
		// Now 2nd Filter , if Account is not created , disable BT of this line item 
		if("".equals(lineItemInfo.getFxAccNoStatus()) || lineItemInfo.getFxAccNoStatus()==null) {
			chkBoxEnable="deny";
			ori_colorcode=chkBoxColorCode;
			chkBoxColorCode="red";
			locNoForEdit="deny";
			locDateForEdit="deny";
			btdForEdit="deny";
			helperRemarks+="ACCOUNT.NOT.CREATED-";
			locRecDateForEdit="deny";
		}
		 
		// Now 3rd Filter-New Order/Change Order Wise  
		
		if(orderInfo.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderInfo.getOrderInfo_OrderType())
				&& orderInfo.CHANGE_TYPE_RATERENEWAL==orderInfo.getOrderInfo_ChangeType()){
				//chkBoxEnable=let previous filter value;
				//chkBoxColorCode=let previous filter value;
				if(lineItemInfo.getLine_status().equalsIgnoreCase("New"))
				{
					locNoForEdit="allow";
					locDateForEdit="allow";
				}else
				{
				locNoForEdit="deny";
				locDateForEdit="deny";
				}
				//btdForEdit=let previous filter value;
				helperRemarks+="RATE.RENEWAL-";
		}
		 
		 // Now 4th Filter-Billing Trigger Done or Not , If Done Disable and make blue  
		 
		if("20".equalsIgnoreCase(lineItemInfo.getBillingTriggerStatus())){
				chkBoxEnable="deny";
				chkBoxColorCode="blue";
				locNoForEdit="deny";
				locDateForEdit="deny";
				btdForEdit="deny";
				billingTriggerActionStatus="done";
				helperRemarks+="BILLING.TRIG.DONE-";
				locRecDateForEdit="deny";
		}
		// for Auto Billing
		if(lineItemInfo.getIsAutoBilling()==1){
			chkBoxEnable="deny";
			btdForEdit="deny";
		
			
			if("".equalsIgnoreCase(lineItemInfo.getLocNo()))
			{
			        locNoForEdit="allow";
			        locdataforhwSale="notfilled";
			}
			
			else
				
			{
				locNoForEdit="deny";
				
			}
			
			if("".equalsIgnoreCase(lineItemInfo.getLocDate()))
			{
				locDateForEdit="allow";
				   locdataforhwSale="notfilled";
			}
			
			else
				
			{
				locDateForEdit="deny";
				
			}
			
			if("".equalsIgnoreCase(lineItemInfo.getLocRecDate()))
			{
				locRecDateForEdit="allow";
				locdataforhwSale="notfilled";
			}
			
			else
				
			{
				locRecDateForEdit="deny";
				
			}
			
			
			
			
		if(("deny".equals(locNoForEdit))&&("deny".equals(locDateForEdit))&&("deny".equals(locRecDateForEdit))){
			
			 locdataforhwSale="filled";
			
			
		}
		
			
	}
		
//001 start
		
else if(lineItemInfo.getIsAutoBilling()==2)
		{
			
			if("NEW".equals(lineItemInfo.getLineOldOrNew()))
			{
				chkBoxEnable="deny";
				btdForEdit="deny";
				locRecDateForEdit="deny";
				locNoForEdit="deny";
				locDateForEdit="deny";
				locdataforhwSale="na";
			}
			
			else
			{
				
				locRecDateForEdit="deny";
				locNoForEdit="deny";
				locDateForEdit="deny";
				locdataforhwSale="na";
			}
		}
//001 end		
//Start [0015]
		else if(lineItemInfo.getIsAutoBilling()==3 || lineItemInfo.getIsAutoBilling()==4 
					|| lineItemInfo.getIsAutoBilling()==105 
					|| lineItemInfo.getIsAutoBilling()==112 
					|| lineItemInfo.getIsAutoBilling()==104 
					|| lineItemInfo.getIsAutoBilling()==190
					|| lineItemInfo.getIsAutoBilling()==191){
			chkBoxEnable="deny";
			btdForEdit="deny";
			locRecDateForEdit="deny";
			locNoForEdit="deny";
			locDateForEdit="deny";
			locdataforhwSale="na";
		}
		//end [0015]
		else
			
		{
			
			locdataforhwSale="na";
			
			
		}
		
		
		
		
		/*if(lineItemInfo.getSendToM6()==1 && !(orderInfo.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderInfo.getOrderInfo_OrderType())
				&& orderInfo.CHANGE_TYPE_RATERENEWAL==orderInfo.getOrderInfo_ChangeType())){
			
			HashSet<String> mandatoryOsn=null;
			mandatoryOsn=getListOfOSN(lineItemInfo);
			HashSet<String> receivedOsn=null;
			receivedOsn=getCurrentOSNList(eventids,lineItemInfo);
			mandatoryOsn.removeAll(receivedOsn);
			if(mandatoryOsn.size()>0)
			{
				String csv="";
				for (String val : mandatoryOsn) {
					String valueofKey=Messages.getMessageValue("BillingTrigger.Osn."+val);
					csv=csv+","+valueofKey;
					
				}
				
				if(csv.length()>0)
				{
				
				csv=csv.substring(1);
				}
				chkBoxEnable="deny";
				locNoForEdit="deny";
				locDateForEdit="deny";
				btdForEdit="deny";
				lineItemInfo.setRemarks(csv+"NOT RECEIVED");
				
				
				
			}
			
			
			}*/
		
		 
	}
	public void computeProperties_old()
	{
		// 1st Filter , if any action is taken /not taken on charges/line in Order 
		if("NEW".equals(lineItemInfo.getLineOldOrNew())) {
				//if new Line Item
				chkBoxEnable="allow";
				chkBoxColorCode="white";
				locNoForEdit="allow";
				locDateForEdit="allow";
				btdForEdit="allow";
				helperRemarks+="NEW-";
				billingTriggerActionStatus="required";
				locRecDateForEdit = "allow";
				
					if("1".equals(lineItemInfo.getDuplicate_cktid())) 
					{
				
						chkBoxEnable="deny";
						locNoForEdit="deny";
						locDateForEdit="deny";
						btdForEdit="deny";
						locRecDateForEdit = "deny";
				
					}
					
					
					if(("Service".equalsIgnoreCase(lineItemInfo.getServiceType())) || ("Bandwidth".equalsIgnoreCase(lineItemInfo.getServiceType())))
					{
						if(lineItemInfo.getCktId()==null || lineItemInfo.getPmProvisioningDate()==null) 
						{
						   isHide="Yes";
						}
						
					}else if("Hardware".equalsIgnoreCase(lineItemInfo.getServiceType()))
					{
						if(lineItemInfo.getCktId()==null || lineItemInfo.getPmProvisioningDate()==null || lineItemInfo.getChallen_No()==null || lineItemInfo.getChallen_date()==null ) 
						{
						   isHide="Yes";
						}
					}
		}
		else if("OLD".equals(lineItemInfo.getLineOldOrNew()) && !"1".equals(lineItemInfo.getIsLineDisconnected())) {
				//if Old and NOn disconnected line item
			/*	if(lineItemInfo.getNoOfNewCharges()>0 && lineItemInfo.getNoOfDisconnectClose()==0 && lineItemInfo.getNoOfDisconnectInactive()==0) {
						//With Only New Charges and hence BT date , loc date and Loc no can be entered
						chkBoxEnable="allow";
						chkBoxColorCode="white";
						locNoForEdit="allow";
						locDateForEdit="allow";
						btdForEdit="allow";
						billingTriggerActionStatus="required";
						helperRemarks+="OLDNONDISCONNECTED-ONLY.NEW.CHARGES-";
						locRecDateForEdit="allow";
				}
				 else if(lineItemInfo.getNoOfNewCharges()==00 && lineItemInfo.getNoOfDisconnectClose()+lineItemInfo.getNoOfDisconnectInactive()>0) {						
				 	//With Only Disconnect Charges
					if(lineItemInfo.getNoOfDisconnectClose()==0) {
						//Disconnect Charges list has only Inactive request and hence "no" Disconnect Date for individual charges is reqd
						chkBoxEnable="allow";
						chkBoxColorCode="white";
						locNoForEdit="allow";
						locDateForEdit="allow";
						btdForEdit="allow";
						billingTriggerActionStatus="required";
						helperRemarks+="OLDNONDISCONNECTED-ONLY.DISCONNECT.INACTIVE.CHARGES-";
						locRecDateForEdit="allow";
					} else { 
						//Disconnect Charges list has some or all Close Request and hence Disconnect Date for individual charges is reqd
						chkBoxEnable="allow";
						chkBoxColorCode="white";
						locNoForEdit="allow";
						locDateForEdit="allow";
						btdForEdit="allow";
						billingTriggerActionStatus="required";
						helperRemarks+="OLDNONDISCONNECTED-SOMECLOSE.CHARGES-";
						locRecDateForEdit="allow";
					} 
					}else*/ //if(lineItemInfo.getNoOfNewCharges()>0 || lineItemInfo.getNoOfDisconnectClose()+lineItemInfo.getNoOfDisconnectInactive()>0){ 
					if((lineItemInfo.getNoOfChangedCharges()>0)|| "Y".equals(lineItemInfo.getIsNewOrDisconnectedInCurrentService())){ 
							//Mix of New Charges and Disconnect Charges
						chkBoxEnable="allow";
						chkBoxColorCode="white";
						locNoForEdit="allow";
						locDateForEdit="allow";
						btdForEdit="allow";
						billingTriggerActionStatus="required";
						helperRemarks+="OLDNONDISCONNECTED-MIX.CHARGES-";
						locRecDateForEdit="allow";
					}
				
					else if("CHANGED".equalsIgnoreCase(lineItemInfo.getM6_att_fxchanged())){ 
						
					chkBoxEnable="allow";
					chkBoxColorCode="white";
					locNoForEdit="allow";
					locDateForEdit="allow";
					btdForEdit="allow";
					billingTriggerActionStatus="required";
					helperRemarks+="OLDNONDISCONNECTED-MIX.CHARGES-";
					locRecDateForEdit="allow";
				}
					
					
					else {
						//No New Action
						chkBoxEnable="deny";
						chkBoxColorCode="black";
						locNoForEdit="deny";
						locDateForEdit="deny";
						btdForEdit="deny";
						billingTriggerActionStatus="not_required";
						helperRemarks+="OLDNONDISCONNECTED-NO.CHARGES.ACTION-";
						locRecDateForEdit="deny";
					}
		}else if("OLD".equals(lineItemInfo.getLineOldOrNew()) && "1".equals(lineItemInfo.getIsLineDisconnected())) {
			//if Old Disconnected Line Item
			chkBoxEnable="allow";
			chkBoxColorCode="white";
			locNoForEdit="deny";
			locDateForEdit="deny";
			btdForEdit="allow";
			billingTriggerActionStatus="required";
			helperRemarks="OLDDISCONNECTED-";
			locRecDateForEdit="allow";
			}
		
		// Now 2nd Filter , if Account is not created , disable BT of this line item 
		if("".equals(lineItemInfo.getFxAccNoStatus()) || lineItemInfo.getFxAccNoStatus()==null) {
			chkBoxEnable="deny";
			ori_colorcode=chkBoxColorCode;
			chkBoxColorCode="red";
			locNoForEdit="deny";
			locDateForEdit="deny";
			btdForEdit="deny";
			helperRemarks+="ACCOUNT.NOT.CREATED-";
			locRecDateForEdit="deny";
		}
		 
		// Now 3rd Filter-New Order/Change Order Wise  
		
		if(orderInfo.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderInfo.getOrderInfo_OrderType())
				&& orderInfo.CHANGE_TYPE_RATERENEWAL==orderInfo.getOrderInfo_ChangeType()){
				//chkBoxEnable=let previous filter value;
				//chkBoxColorCode=let previous filter value;
				if(lineItemInfo.getLine_status().equalsIgnoreCase("New"))
				{
					locNoForEdit="allow";
					locDateForEdit="allow";
				}else
				{
				locNoForEdit="deny";
				locDateForEdit="deny";
				}
				//btdForEdit=let previous filter value;
				helperRemarks+="RATE.RENEWAL-";
		}
		 
		 // Now 4th Filter-Billing Trigger Done or Not , If Done Disable and make blue  
		 
		if("20".equalsIgnoreCase(lineItemInfo.getBillingTriggerStatus())){
				chkBoxEnable="deny";
				chkBoxColorCode="blue";
				locNoForEdit="deny";
				locDateForEdit="deny";
				btdForEdit="deny";
				billingTriggerActionStatus="done";
				helperRemarks+="BILLING.TRIG.DONE-";
				locRecDateForEdit="deny";
		}
		// for Auto Billing
		if(lineItemInfo.getIsAutoBilling()==1){
			chkBoxEnable="deny";
			btdForEdit="deny";
		
			
			if("".equalsIgnoreCase(lineItemInfo.getLocNo()))
			{
			        locNoForEdit="allow";
			        locdataforhwSale="notfilled";
			}
			
			else
				
			{
				locNoForEdit="deny";
				
			}
			
			if("".equalsIgnoreCase(lineItemInfo.getLocDate()))
			{
				locDateForEdit="allow";
				   locdataforhwSale="notfilled";
			}
			
			else
				
			{
				locDateForEdit="deny";
				
			}
			
			if("".equalsIgnoreCase(lineItemInfo.getLocRecDate()))
			{
				locRecDateForEdit="allow";
				locdataforhwSale="notfilled";
			}
			
			else
				
			{
				locRecDateForEdit="deny";
				
			}
			
			
			
			
		if(("deny".equals(locNoForEdit))&&("deny".equals(locDateForEdit))&&("deny".equals(locRecDateForEdit))){
			
			 locdataforhwSale="filled";
			
			
		}
		
			
	}
		
//001 start
		
else if(lineItemInfo.getIsAutoBilling()==2)
		{
			
			if("NEW".equals(lineItemInfo.getLineOldOrNew()))
			{
				chkBoxEnable="deny";
				btdForEdit="deny";
				locRecDateForEdit="deny";
				locNoForEdit="deny";
				locDateForEdit="deny";
				locdataforhwSale="na";
			}
			
			else
			{
				
				locRecDateForEdit="deny";
				locNoForEdit="deny";
				locDateForEdit="deny";
				locdataforhwSale="na";
			}
		}
//001 end		
		else
			
		{
			
			locdataforhwSale="na";
			
			
		}
		
		
		
		
		/*if(lineItemInfo.getSendToM6()==1 && !(orderInfo.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderInfo.getOrderInfo_OrderType())
				&& orderInfo.CHANGE_TYPE_RATERENEWAL==orderInfo.getOrderInfo_ChangeType())){
			
			HashSet<String> mandatoryOsn=null;
			mandatoryOsn=getListOfOSN(lineItemInfo);
			HashSet<String> receivedOsn=null;
			receivedOsn=getCurrentOSNList(eventids,lineItemInfo);
			mandatoryOsn.removeAll(receivedOsn);
			if(mandatoryOsn.size()>0)
			{
				String csv="";
				for (String val : mandatoryOsn) {
					String valueofKey=Messages.getMessageValue("BillingTrigger.Osn."+val);
					csv=csv+","+valueofKey;
					
				}
				
				if(csv.length()>0)
				{
				
				csv=csv.substring(1);
				}
				chkBoxEnable="deny";
				locNoForEdit="deny";
				locDateForEdit="deny";
				btdForEdit="deny";
				lineItemInfo.setRemarks(csv+"NOT RECEIVED");
				
				
				
			}
			
			
			}*/
		
		 
	}
	
	
	public void computeProperties_FORBULKUPLOAD()
	{
		// 1st Filter , if any action is taken /not taken on charges/line in Order 
		if("NEW".equals(lineItemInfo.getLineOldOrNew())) 
		{
			locNoForEdit="allow";
			locDateForEdit="allow";
			btdForEdit="allow";
			locRecDateForEdit="allow";		
		}
		
		else if("OLD".equals(lineItemInfo.getLineOldOrNew()) && !"1".equals(lineItemInfo.getIsLineDisconnected())) 
			{
					locNoForEdit="allow";
					locDateForEdit="allow";
					btdForEdit="allow";
					locRecDateForEdit="allow";
			}
		
		
		else if("OLD".equals(lineItemInfo.getLineOldOrNew()) && "1".equals(lineItemInfo.getIsLineDisconnected())) 
			{
			//if Old Disconnected Line Item
			locNoForEdit="deny";
			locDateForEdit="deny";
			btdForEdit="allow";
			locRecDateForEdit="allow";
			}

//	 Now 3rd Filter-New Order/Change Order Wise  

		if("Rate Revision".equalsIgnoreCase(lineItemInfo.getOrder_subtype()))
			{
				if(lineItemInfo.getLine_status().equalsIgnoreCase("New"))
				{
					locNoForEdit="allow";
					locDateForEdit="allow";
				}else
				{
				locNoForEdit="deny";
				locDateForEdit="deny";
				}
			//btdForEdit=let previous filter value;
		
			}
//	 for Auto Billing
	if(lineItemInfo.getIsAutoBilling()==1){
		
		btdForEdit="deny";

		
		if("".equalsIgnoreCase(lineItemInfo.getLocNo()) || lineItemInfo.getLocNo()==null)
		{
		        locNoForEdit="allow";
		        locdataforhwSale="notfilled";
		}
		
		else
			
		{
			locNoForEdit="deny";
			
		}
		
		if("".equalsIgnoreCase(lineItemInfo.getLocDate()) || lineItemInfo.getLocDate()==null)
		{
				locDateForEdit="allow";
			   locdataforhwSale="notfilled";
		}
		
		else
			
		{
			locDateForEdit="deny";
			
		}
		
		if("".equalsIgnoreCase(lineItemInfo.getLocRecDate()) || lineItemInfo.getLocRecDate()==null)
		{
			locRecDateForEdit="allow";
			locdataforhwSale="notfilled";
		}
		
		else
			
		{
			locRecDateForEdit="deny";
			
		}
		if(("deny".equals(locNoForEdit))&&("deny".equals(locDateForEdit))&&("deny".equals(locRecDateForEdit))){
			
			 locdataforhwSale="filled";
			
		}
	}

		else
			
		{
			locdataforhwSale="na";
		}

	}
	
	
public HashSet<String> getListOfOSN(ViewOrderDto objdto)
	
       {
		   HashSet<String> s= new HashSet<String>() ;
		   
		   
if(orderInfo.ORDER_TYPE_CHANGE.equalsIgnoreCase(orderInfo.getOrderInfo_OrderType()))
		 
    {
	
	
	if("Service".equalsIgnoreCase(objdto.getServiceType()))
	{
		
		String osn=Messages.getMessageValue("OSNLIST.CHANGE.SERVICE");
		String serviceosnlist[]=osn.split(",");
		for (String val : serviceosnlist) {
			s.add(val);
		}
		
	}
	
	
			else
				
			{
				String osn=Messages.getMessageValue("OSNLIST.CHANGE.HARDWARE");
				String Hardwareosnlist[]=osn.split(",");
				for (String val : Hardwareosnlist) {
					s.add(val);
				}
		   
		
           }

    }
	
	
	
	else
	{
		
		
		if("Service".equalsIgnoreCase(objdto.getServiceType()))
		{
			
			String osn=Messages.getMessageValue("OSNLIST.NEW.SERVICE");
			String serviceosnlist[]=osn.split(",");
			for (String val : serviceosnlist) {
				s.add(val);
			}
			
		}
		
		
		else
			
		{
			
			String osn=Messages.getMessageValue("OSNLIST.NEW.HARDWARE");
			String Hardwareosnlist[]=osn.split(",");
			for (String val : Hardwareosnlist) {
				s.add(val);
			}
		}
		
	}
	




		
		return s;
		
	}
	
	
	
	public  HashSet<String> getCurrentOSNList(ArrayList<ViewOrderDto> eventids,ViewOrderDto objdto)
	{
		ViewOrderDto objLSIdto=null;
		HashSet<String> hm=new HashSet<String>();
		for(int i=0;i<eventids.size();i++)
			
		{
        	
			objLSIdto=eventids.get(i);
			String eventid=objLSIdto.getEventids();
			String serviceno=objLSIdto.getServiceId();
			
			if(serviceno.equals(objdto.getServiceId()))
				
			{
				hm.add(eventid);
			}
			
			
		
		
		
		
	}
		
		return hm;
		
		
	}
	
	public String getLRDForEdit_HTMLClass() {
		if("allow".equalsIgnoreCase(locRecDateForEdit))
		{
			return editableControlClass;	
		}
		else
		{
			return nonEditableControlClass;
		}
	}
	
	public String getBtdForEdit() {
		return btdForEdit;
	}

	public String getLocDateForEdit() {
		return locDateForEdit;
	}

	public String getHelperRemarks() {
		return helperRemarks;
	}

	public String getLocNoForEdit() {
		return locNoForEdit;
	}

	public String getBillingTriggerActionStatus() {
		return billingTriggerActionStatus;
	}

	public String getLocdataforhwSale() {
		return locdataforhwSale;
	}

	public void setLocdataforhwSale(String locdataforhwSale) {
		this.locdataforhwSale = locdataforhwSale;
	}

	public String getLocRecDateForEdit() {
		return locRecDateForEdit;
	}

	public void setLocRecDateForEdit(String locRecDateForEdit) {
		this.locRecDateForEdit = locRecDateForEdit;
	}

	public String getLocrecdataforhwSale() {
		return locrecdataforhwSale;
	}

	public void setLocrecdataforhwSale(String locrecdataforhwSale) {
		this.locrecdataforhwSale = locrecdataforhwSale;
	}
	
	
	public String getCheckBoxMessage()
	{
		 String result=null;
		 
		 if("red".equalsIgnoreCase(chkBoxColorCode)){
			 
			 result="Accounts Creation Pending";
		 }

		 if("blue".equalsIgnoreCase(chkBoxColorCode)){
	  
			 result= "Billing Trigger Done";     
		 }
		 if("white".equalsIgnoreCase(chkBoxColorCode)){
			 
			 result= "Billing Trigger Required";
		 }
		 if("1".equals(lineItemInfo.getDuplicate_cktid())) {
			 
			 
			 result= "Duplicate Circuit ID Generated";
			 
			 
		 }
		
		   return result;
		
	}

	public String getOri_colorcode() {
		return ori_colorcode;
	}

	public String getIsHide() {
		return isHide;
	}

	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	
}
