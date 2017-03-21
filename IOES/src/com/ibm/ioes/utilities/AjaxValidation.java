
//tag		Name       Date			CSR No			Description 

//[001]	 Lawkush	11-April-2011 	CSR00-05422     for charge section validation
//[002]	 Vijay Pathak	07-Feb-2013    				indicative value should contain decimal no also with numeric value  
//[003]	 Kalpana		13-JUN-13	CBR-CSR-20130404-XX-08714     For M6 Editable fields ,changed maxlength from 100 and 200 in validateServicePrdAttValues function of type VARCHAR
//[004]  Gunjan Singla  7-Jan-15         20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection
//[005]  Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Parallel Upgrade LSI
package com.ibm.ioes.utilities;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.text.DateFormat;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.forms.ChargesDetailDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.newdesign.dto.StandardReason;

public class AjaxValidation 
{
	
	public ViewOrderDto validateActiveDate(ViewOrderDto objDto,Connection connection,String clepFlag,String csvSPOrderIDS,int bulkflag) throws Exception
	{
		
		
		int arrLen=0;
		long longServiceProductId=0;
		AjaxValidation objAjaxValidation=new AjaxValidation();
		String billingTriggerDate=null;
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		
		
		try {
				long orderNo=0;
				
				String strBillingTrigger=null;
				if(bulkflag!=1)
				{
				orderNo=Long.parseLong(objDto.getOrderNo());
				}
				strBillingTrigger=objDto.getBillingTriggerString();
				
				String csvServiceProductIds="";
				String csvBillingTriggerDate = "";
				String csvLineType="";
				arrLen=strBillingTrigger.split("@@").length;
				String[] eachLine=strBillingTrigger.split("@@");
				//String lineType[]=objDto.getLine_prop();
				ViewOrderDao objViewOrderDao =  new ViewOrderDao();
				
				ArrayList<ViewOrderDto> results=new ArrayList<ViewOrderDto>();
				HashMap<String, String> hmap_SPID_BTDate =new HashMap<String, String>();
				HashMap<String, String> hmap_SPID_LineType =new HashMap<String, String>();
				
				for(int i=1;i<=arrLen;i++)
				{	 
				 	 String str=eachLine[i-1];
					 String individual[]=str.split("~");
					 
					 longServiceProductId=Long.parseLong(individual[0]);
					 
					 billingTriggerDate=individual[3];
					 if(bulkflag==1)
					 {
					 if(!(billingTriggerDate==null || "".equals(billingTriggerDate)))
						{
						 billingTriggerDate=Utility.showDate_Report3(df.parse(billingTriggerDate));
						}
					 }
					 csvServiceProductIds=csvServiceProductIds+","+longServiceProductId;
					 
					 
					 //csvBillingTriggerDate=csvBillingTriggerDate+","+billingTriggerDate;
					 //csvLineType=csvLineType+","+lineType[i-1];
					 
					 hmap_SPID_BTDate.put(String.valueOf(longServiceProductId),billingTriggerDate );
					 //hmap_SPID_LineType.put(String.valueOf(longServiceProductId),lineType[i-1] );
					 
				 }  
				
				if(csvServiceProductIds!=null && csvServiceProductIds.length()>0)
				{
					csvServiceProductIds=csvServiceProductIds.substring(1);
				}
				
				
				
				objDto = null;
				//[004] start
				objDto=objAjaxValidation.validateParallelUpgradeLSI(csvServiceProductIds,connection);
				if("FAILURE".equals(objDto.getIfAnyFailValidation())){
					objDto.setBillingTriggerProcess("validation");
					return objDto;
				}
				else{
					//[004] end
				objDto = objViewOrderDao.validateProductBillingTriggerDate(connection,csvServiceProductIds,hmap_SPID_BTDate,orderNo,clepFlag,csvSPOrderIDS,bulkflag);
				}
			
			//
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return objDto;
		
	}
	//[004] start
	
	public ViewOrderDto validateParallelUpgradeLSI(String csvServiceProductIds,Connection con) {
		
		ViewOrderDao objViewOrderDao =  new ViewOrderDao();
		ArrayList<String> validationMsgs=new ArrayList<String>();
		ArrayList<ViewOrderDto> listViewOrderDto=new ArrayList<ViewOrderDto>();
		ViewOrderDto objRetViewOrderDto=new ViewOrderDto();
		String strValidationMsgs="";
		try {
			listViewOrderDto=objViewOrderDao.fetchNonValidLines(csvServiceProductIds,con);
		
		
		for(ViewOrderDto obDto:listViewOrderDto){
			strValidationMsgs="";
			
			
			if(obDto.getSubChangeTypeId()!=3){
				strValidationMsgs="Latest Order is not permanent disconnection order for parallel upgrade LSI "+obDto.getParallelUpgradeLSI()+" with line no "+obDto.getServiceLineNo();
				validationMsgs.add(strValidationMsgs);
				
			}
			else if(obDto.getM6FxProgressStatus()==null || !(obDto.getM6FxProgressStatus().equals("FX_BT_END"))){
				strValidationMsgs="Disconnection order of Parallel Upgrade Lsi "+obDto.getParallelUpgradeLSI() +" with line no "+obDto.getServiceLineNo()+" is not completed";
				validationMsgs.add(strValidationMsgs);
			}
			
		}
		if(strValidationMsgs!=""){
			objRetViewOrderDto.setIfAnyFailValidation("FAILURE");
			objRetViewOrderDto.setBillingTriggerValidationErrors(validationMsgs);
		}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return objRetViewOrderDto;
	}
	//[004] end

	public ViewOrderDto validateActiveDate_forbulkupload(ViewOrderDto objDto,Connection connection,String clepFlag,String csvSPOrderIDS,int bulkflag,long fileid) throws Exception
	{
		
		
		int arrLen=0;
		long longServiceProductId=0;
		
		String billingTriggerDate=null;
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		
		
		try {
				long orderNo=0;
				
				String strBillingTrigger=null;
				if(bulkflag!=1)
				{
				orderNo=Long.parseLong(objDto.getOrderNo());
				}
				strBillingTrigger=objDto.getBillingTriggerString();
				
				String csvServiceProductIds="";
				String csvBillingTriggerDate = "";
				String csvLineType="";
				arrLen=strBillingTrigger.split("@@").length;
				String[] eachLine=strBillingTrigger.split("@@");
				//String lineType[]=objDto.getLine_prop();
				ViewOrderDao objViewOrderDao =  new ViewOrderDao();
				
				ArrayList<ViewOrderDto> results=new ArrayList<ViewOrderDto>();
				HashMap<String, String> hmap_SPID_BTDate =new HashMap<String, String>();
				HashMap<String, String> hmap_SPID_LineType =new HashMap<String, String>();
				
				for(int i=1;i<=arrLen;i++)
				{	 
				 	 String str=eachLine[i-1];
					 String individual[]=str.split("~");
					 
					 longServiceProductId=Long.parseLong(individual[0]);
					 
					 billingTriggerDate=individual[3];
					 if(bulkflag==1)
					 {
					 if(!(billingTriggerDate==null || "".equals(billingTriggerDate)))
						{
						 billingTriggerDate=Utility.showDate_Report3(df.parse(billingTriggerDate));
						}
					 }
					 csvServiceProductIds=csvServiceProductIds+","+longServiceProductId;
					 
					 
					 //csvBillingTriggerDate=csvBillingTriggerDate+","+billingTriggerDate;
					 //csvLineType=csvLineType+","+lineType[i-1];
					 
					 hmap_SPID_BTDate.put(String.valueOf(longServiceProductId),billingTriggerDate );
					 //hmap_SPID_LineType.put(String.valueOf(longServiceProductId),lineType[i-1] );
					 
				 }  
				
				if(csvServiceProductIds!=null && csvServiceProductIds.length()>0)
				{
					csvServiceProductIds=csvServiceProductIds.substring(1);
				}
				
				
				Utility.LOG("Step5.3.1 BT_BULK Started to update partial Validation Flag for fileID : "+fileid);
				objDto = null;
				objDto = objViewOrderDao.validateProductBillingTriggerDate_bulkupload(connection,csvServiceProductIds,hmap_SPID_BTDate,orderNo,clepFlag,csvSPOrderIDS,bulkflag,fileid);
				Utility.LOG("Step5.3.2 BT_BULK Started to update partial Validation Flag for fileID : "+fileid);
				if(!"FAILURE".equals(objDto.getIfAnyFailValidation()))
				{
					//added by manisha  cust bill exp bfr 14 start
					objDto = objViewOrderDao.validateDisconnectionDate_forBulkUpload(fileid,connection,hmap_SPID_BTDate);
					
				}	
			
			//
			
			
		}catch(Exception e) {
			Utility.LOG_ITER(true, false,e," Billing Trigger Exception Occured at LineItem No : "+longServiceProductId+" in fileid : "+fileid);
//			throw e;
			//e.printStackTrace();
		}
		
		return objDto;
		
	}
	
	public ArrayList ValidateNotes(ViewOrderDto objDto) throws Exception
	{
		ArrayList listAccountDetails= new ArrayList();
		
		boolean errorsFound=false;
		try
		{
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objDto.getNotesType(),"Notes Type",200),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					listAccountDetails = errors;
					errorsFound=true;
				}
			}
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objDto.getNotesMeaning(),"Notes Meaning",1000),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					listAccountDetails = errors;
					errorsFound=true;
				}
			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAccountDetails;
	}
	
	public static ArrayList ValidateServiceSearch(NewOrderDto objDto) throws Exception
	{
		ArrayList listAccountDetails= new ArrayList();
		
		boolean errorsFound=false;
		try
		{
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objDto.getServiceTypeName(),"Service Type",50),
						""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					listAccountDetails = errors;
					errorsFound=true;
				}
			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAccountDetails;
	}
	public ArrayList ValidateProductCatelog(NewOrderDto objDto) throws Exception
	{
		ArrayList listProductCatelog= new ArrayList();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);
		
		boolean errorsFound=false;
		try
		{
			if(NewOrderDto.MODE_INSERT.equals(objDto.getMode()))
			{
				if (((objDto.getParentServiceProductId()!= null || objDto.getParentServiceProductId()!= ""
						|| objDto.getIsReqParentServiceProductId() == 1))||(objDto.getParentServiceProductId()!= null
						&& objDto.getIsReqParentServiceProductId() == 1)) {
					if (!errorsFound)// Parent Service Product Id
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getParentServiceProductId()),
										"Parent Product Id", 18),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
			}
			if(objDto.getServiceInfoValue()==1)//Service Summary Starts
			{
				String[] attributeVal=new String [objDto.getHdnSeriveAttCounter()];
				String[] attributeID=new String [objDto.getHdnSeriveAttCounter()];
				String[] dataType=new String [objDto.getHdnSeriveAttCounter()];
				String[] attributeName=new String [objDto.getHdnSeriveAttCounter()];
				String[] attributeExpectedValue=new String [objDto.getHdnSeriveAttCounter()];
				String[] attributeMandatory=new String [objDto.getHdnSeriveAttCounter()];
				String[] serviceSummMand=new String [objDto.getHdnSeriveAttCounter()];
				Integer[] attributeMaxLength=new Integer[objDto.getHdnSeriveAttCounter()];
				for(int j=0;j<=(objDto.getHdnSeriveAttCounter())-1;j++)
				{
					System.out.println("objDto.getNewProdAttValue()[j] :"+objDto.getNewProdAttValue()[0]);
					if(objDto.getNewProdAttValue()[0] .equalsIgnoreCase("ram") )
					{
						attributeVal[j] =objDto.getProdAttValue()[j];
					}
					else
					{
						attributeVal[j] =objDto.getNewProdAttValue()[j];
					}
					attributeID[j]=objDto.getProdAttID()[j];
					attributeExpectedValue[j]=objDto.getProdAttExptdValue()[j];
					attributeName[j]=objDto.getProdAttName()[j];
					attributeMandatory[j]=objDto.getProdAttMandatory()[j];
					serviceSummMand[j]=objDto.getServiceSummaryMandatory()[j];
					attributeMaxLength[j]=objDto.getProdAttriMaxLength()[j];
					if(!errorsFound)
					{
						if("N".equalsIgnoreCase(attributeMandatory[j]) && (attributeVal[j]==null || "".equals(attributeVal[j].trim())))
						{
							continue;
						}
						
						if(attributeExpectedValue[j].equalsIgnoreCase("numeric"))
						{
							if(serviceSummMand[j]=="1" && !attributeVal[j].equalsIgnoreCase(""))
							{
								ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
										""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
									//break;
								}
							}
							else
							if(!attributeVal[j].equalsIgnoreCase(""))
							{
								ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
										""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
									//break;
								}

							}
						}
						
						//--[002]--start//
						if(attributeExpectedValue[j].equalsIgnoreCase("decimal"))
						{
							
							if(serviceSummMand[j]=="1" && !attributeVal[j].equalsIgnoreCase(""))
							{
								ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
										""+Utility.CASE_DECIMALNUBER+","+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
									//break;
								}
							}
							else
							if(!attributeVal[j].equalsIgnoreCase(""))
							{
								ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
										""+Utility.CASE_DECIMALNUBER+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
									//break;
								}

							}
						}
                       //--[002]--end//
						
						if(attributeExpectedValue[j].equalsIgnoreCase("email"))
						{
							if(serviceSummMand[j]=="1" && !attributeVal[j].equalsIgnoreCase(""))
							{
								ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
										""+Utility.CASE_EMAIL+","+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
									//break;
								}
							}
							else
							if(!attributeVal[j].equalsIgnoreCase(""))
							{
								
								ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],30),
										""+Utility.CASE_EMAIL+","+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
									//break;
								}
							}

								
						}
						
						if(attributeExpectedValue[j].equalsIgnoreCase("varchar"))
						{
							if(serviceSummMand[j]=="1" && !attributeVal[j].equalsIgnoreCase(""))
							{
								ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],attributeMaxLength[j]),
										""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
									//break;
								}
							}
							else
								if(!attributeVal[j].equalsIgnoreCase(""))
								{
									ArrayList errors=Utility.validateValue(new ValidationBean(attributeVal[j],attributeName[j],attributeMaxLength[j]),
											""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
									if(errors!=null)
									{
										listProductCatelog = errors;
										errorsFound=true;
										//break;
									}
								}
						}
					}
				}
			}
			//Service Summary Ends
			
			if(objDto.getBillingInfoValue()==1)//For Billing Info Section Starts
			{	
				if ((String.valueOf(objDto.getPodetailID()) != null ||String.valueOf(objDto.getPodetailID()) != ""
						|| objDto.getIsReqTxtPODetailNo() == 1)||(String.valueOf(objDto.getPodetailID()) != null
						&& objDto.getIsReqTxtPODetailNo() == 1)) {
					if (!errorsFound)// PO DETAILS ID
					{
						ArrayList errors = Utility
								.validateValue(
										new ValidationBean(
												String.valueOf(objDto
														.getPodetailID()),
												"PO Detail ID", 200),
										"" + Utility.CASE_MANDATORY + ","
												+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				
				if(!errorsFound)//Account ID
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getAccountID()),"Account ID",200),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						listProductCatelog = errors;
						errorsFound=true;
					}
				}
				if (((String.valueOf(objDto.getCreditPeriod()) != null || String.valueOf(objDto.getCreditPeriod()) != ""
						|| objDto.getIsReqTxtBillingCP() == 1))||(String.valueOf(objDto.getCreditPeriod()) != null
						&& objDto.getIsReqTxtBillingCP() == 1)) {
					if (!errorsFound)// CREDIT PERIOD
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getCreditPeriod()), "Credit Period",
										200),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				
				if(!errorsFound)//LEGAL ENTITY
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getEntityID()),"Entity",200),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						listProductCatelog = errors;
						errorsFound=true;
					}
				}
				if ((objDto.getBillingMode() != null
						&& objDto.getIsReqTxtBillingMode() == 1)) {
					if (!errorsFound)// Billing Mode
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getBillingMode(),
										"Billing Mode", 50),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				//satyapan OSP Tagging
				if ((objDto.getIsOSPTagging() != null  && objDto.getIsReqOSPTagging() == 1) ) {
					if (!errorsFound)
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getIsOSPTagging(),
										"OSP Tagging", 3),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((objDto.getIsReqOSPRegNo()==1 && objDto.getTxtOSPRegNo() != null)) {
					if (!errorsFound)
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getTxtOSPRegNo(),
										"OSP RegNo", 500),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((objDto.getIsReqOSPRegDate()==1 && objDto.getTxtOSPRegDate() !=null) ) {
					if (!errorsFound)// Billing Mode
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getTxtOSPRegDate(),
										"OSP RegDate", 25),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				//END OSP Tagging satyapan
				if ((String.valueOf(objDto.getBillingformat()) != null
						&& objDto.getIsReqTxtBillingformat() == 1)) {
					if (!errorsFound)// BILLING FORMAT
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getBillingformat()), "Billing Format",
										200),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((String.valueOf(objDto.getLicenceCoID()) != null
						&& objDto.getIsReqTxtLicenceCo() == 1)) {
					if (!errorsFound)// LICENCE COMPANY
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getLicenceCoID()), "Licence Company",
										200),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((String.valueOf(objDto.getTaxation()) != null
						&& objDto.getIsReqTxtTaxation() == 1)) {
					if (!errorsFound)// Taxation
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getTaxation()), "Taxation", 200),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((String.valueOf(objDto.getTaxation()) != null
						&& objDto.getIsReqTxtCmtPeriod() == 1)) {
					if (!errorsFound)// Commitment Period
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getCommitmentPeriod()),
										"Commitment Period", 3),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((String.valueOf(objDto.getBillingLevel()) != null
						&& objDto.getIsReqTxtBillingLvl() == 1)) {
					if (!errorsFound)// Billing Level
					{
						ArrayList errors = Utility
								.validateValue(
										new ValidationBean(String
												.valueOf(objDto
														.getBillingLevel()),
												"Billing Level", 3),
										"" + Utility.CASE_MANDATORY + ","
												+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((objDto.getBillingLevelNo()!=null ||objDto.getIsReqTxtBillingLvlNo() == 1)||(objDto.getBillingLevelNo()!=null && objDto.getIsReqTxtBillingLvlNo() == 1)) {
			
					if (!errorsFound)// Billing Level
					{
						ArrayList errors = Utility
								.validateValue(
										new ValidationBean(String
												.valueOf(objDto
														.getBillingLevelNo()),
												"Billing Level NO", 50),
										"" + Utility.CASE_MANDATORY + ","
												+ Utility.CASE_DIGITS_ONLY + ","
												+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				
				if ((objDto.getPenaltyClause() != null
						&& objDto.getIsReqTxtPenaltyClause() == 1)){
					if (!errorsFound)// Penalty Clause
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getPenaltyClause(),
										"Penalty Clause", 1000),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				//WARRANTY CLAUSE ADDED BY MANISHA START
				if ((objDto.getWarrantyClause() != null
						&& objDto.getIsReqTxtWarrantyClause() == 1)){
					if (!errorsFound)// Penalty Clause
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getWarrantyClause(),
										"Warranty Clause", 1000),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				
				//WARRANTY CLAUSE ADDED BY MANISHA end
				
			if((String.valueOf(objDto.getBillingBCPId()) != null
						&& objDto.getIsReqBbPrimaryBCPId() == 1)){
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getBillingBCPId()),"Billing BCP ID",5),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						listProductCatelog = errors;
						errorsFound=true;
					}
				}
			}
		//  bcp details for services ADDED BY MANISHA START
			if((String.valueOf(objDto.getBillingBCPIdService()) != null
					&& objDto.getIsReqBillingBCPIdService() == 1)){
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getIsReqBillingBCPIdService()),"Billing BCP ID For Service",5),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					listProductCatelog = errors;
					errorsFound=true;
				}
			}
		}
		//  bcp details for services ADDED BY MANISHA end	
			}//For Billing Info Section Ends
			
			//For Service or Bandwidth Starts
			if(objDto.getLocationInfoValue()==1)
			{
				if ((String.valueOf(objDto.getSelectedPriLocType()) != null
						&& objDto.getIsReqDdPrimaryLocType() == 1)) {
				if(!errorsFound)//Selected Primary Location Type 
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getSelectedPriLocType()),"Primary Location Type",3),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						listProductCatelog = errors;
						errorsFound=true;
					}
				}
				
				
				if(objDto.getSelectedPriLocType()==1)//If Customer Location is Selected
				{
					
						if (!errorsFound)// Selected Primary BCP ID
						{
							ArrayList errors = Utility.validateValue(
									new ValidationBean(String.valueOf(objDto
											.getSelectedPrimaryBCP()),
											"Primary BCP ID", 3),
									"" + Utility.CASE_MANDATORY + ","
											+ Utility.CASE_DIGITS_ONLY)
									.getCompleteMessageStrings();
							if (errors != null) {
								listProductCatelog = errors;
								errorsFound = true;
							}
						}
					}
				
				
				if(objDto.getSelectedPriLocType()==2)//If Network Location is Selected
				{
					if(!errorsFound)//Selected Primary Network Location Type 
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getSelectedPNLocation()),"Primary Network Location",3),
								""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
						if(errors!=null)
						{
							listProductCatelog = errors;
							errorsFound=true;
						}
					}
				}
				
				if(!errorsFound)//Selected Secondary Location Type 
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getSelectedSecLocType()),"Secondary Location Type",3),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						listProductCatelog = errors;
						errorsFound=true;
					}
				}
			}
				
				if ((String.valueOf(objDto.getSelectedSecBCP()) != null
						&& objDto.getIsReqDdSecondaryLocType() == 1)) {
				if(objDto.getSelectedSecLocType()==1)//If Customer Location is Selected
				{
					if(!errorsFound)//Selected Secondary BCP ID
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getSelectedSecBCP()),"Secondary BCP ID",3),
								""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
						if(errors!=null)
						{
							listProductCatelog = errors;
							errorsFound=true;
						}
					}
				}
				
				if(objDto.getSelectedPriLocType()==2)//If Network Location is Selected
				{
					if(!errorsFound)//Selected Secondary Network Location Type 
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getSelectedSNLocation()),"Secondary Network Location",3),
								""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
						if(errors!=null)
						{
							listProductCatelog = errors;
							errorsFound=true;
						}
					}
				}
				}
//				 BY Saurabh for From location validation
				if ((objDto.getFromLocation()!=null || objDto.getFromLocation()!="" ||objDto.getIsReqTxtFromLocation() == 1)||(objDto.getFromLocation()!=null && objDto.getIsReqTxtFromLocation() == 1)) {
					if (!errorsFound)// Sale Type
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getFromLocation(),
										"From Address", 50),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
							
						}
					}
				} 
//				 BY Saurabh for to location validation
				if ((objDto.getToLocation()!=null || objDto.getToLocation()!="" ||objDto.getIsReqTxtToLocation() == 1)||(objDto.getToLocation()!=null && objDto.getIsReqTxtToLocation() == 1)) {
					if (!errorsFound)// Sale Type
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getToLocation(),
										"To Address", 50),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				} 
			}
			//For Service or Bandwidth End
			
			//For Hardware Starts
			if(objDto.getHardwareInfoValue()==1)
			{
				if(!errorsFound)//Selected Dispatch Address ID
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getSelectedDispatchID()),"Dispatch Address Code",3),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						listProductCatelog = errors;
						errorsFound=true;
					}
				}
				if ((String.valueOf(objDto.getSelectedStoreID()) != null
						&& objDto.getIsReqTxtStore() == 1)) {
					if (!errorsFound)// Selected Store ID
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getSelectedStoreID()), "Store", 3),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				///
				
				//if ((String.valueOf(objDto.getPrincipalAmount()) != null
				//		&& objDto.getIsReqPrincipalAmount() == 1)) {
					
					if (( objDto.getIsReqPrincipalAmount() == 1)) {
					if (!errorsFound)// Selected Store ID
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getPrincipalAmount()), "Principle Amount", 3),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				
				
				//if ((String.valueOf(objDto.getInterestRate()) != null
				//		&& objDto.getIsReqInterestRate() == 1)) {
					if(objDto.getIsReqInterestRate() == 1) {
					if (!errorsFound)// Selected Store ID
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getInterestRate()), "Interest Rate", 3),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				///
				if ((objDto.getHardwareType()!=null || objDto.getHardwareType()!= "" ||objDto.getIsReqTxtHtype() == 1)|| (objDto.getHardwareType()!=null && objDto.getIsReqTxtHtype() == 1)) {
					if (!errorsFound)// Hardware Type
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getHardwareType(),
										"Hardware Type", 100),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((objDto.getSaleNature()!=null || objDto.getSaleNature()!="" || objDto.getIsReqTxtNSale() == 1)||(objDto.getSaleNature()!=null && objDto.getIsReqTxtNSale() == 1)) {
					if (!errorsFound)// Sale Nature
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getSaleNature(),
										"Sale Nature", 50),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if ((objDto.getSaleType()!=null || objDto.getSaleType()!="" ||objDto.getIsReqTxtTSale() == 1)||(objDto.getSaleType()!=null && objDto.getIsReqTxtTSale() == 1)) {
					if (!errorsFound)// Sale Type
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(objDto.getSaleType(),
										"Sale Type", 50),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				
				if(!errorsFound)//Form Available
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(objDto.getFormAvailable(),"Form Available",10),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						listProductCatelog = errors;
						errorsFound=true;
					}
				}
	if ((String.valueOf(objDto.getStartHWDateLogic()) != null || String.valueOf(objDto.getStartHWDateLogic()) != ""
					|| objDto.getIsReqStartHWDateLogic() == 1)||(String.valueOf(objDto.getStartHWDateLogic()) != null
					&& objDto
								.getIsReqStartHWDateLogic() == 1)) {
					if (!errorsFound)// Selected Store ID
					{
					        ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getStartHWDateLogic()),
										"Hardware Start Date Logic", 25),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						
					
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
			if ((String.valueOf(objDto.getEndHWDateLogic()) != null || String.valueOf(objDto.getEndHWDateLogic()) != ""
						|| objDto.getIsReqEndHWDateLogic() == 1)||(String.valueOf(objDto.getEndHWDateLogic()) != null
						&& objDto
									.getIsReqEndHWDateLogic() == 1)) {
				
					if (!errorsFound)// Selected Store ID
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getEndHWDateLogic()),
										"Hardware End Date Logic", 25),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_SPECIALCHARACTERS + ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if(	objDto.getStartDate().equalsIgnoreCase("00/00/0000"))
					objDto.setStartDate("");
				if ((String.valueOf(objDto.getStartDate()) != null
					&& objDto.getIsReqStartDate() == 1)) {
					if (!errorsFound)// Selected Store ID
					{
						Object obArray[] = { "" + ValidationBean.VN_DATE_VALID,
								objDto.getStartDate(), "Start Date ", simpleDateFormat };
						ArrayList errors = Utility.validateValue(
								new ValidationBean(obArray),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_VN_DATE_VALID)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				if(	objDto.getEndDate().equalsIgnoreCase("00/00/0000"))
					objDto.setEndDate("");

				if ((String.valueOf(objDto.getEndDate()) != null
					&& objDto.getIsReqEndDate() == 1)) {
					if (!errorsFound)// Selected Store ID
					{
						Object obArray[] = { "" + ValidationBean.VN_DATE_VALID,
								objDto.getEndDate(), "End Date ", simpleDateFormat };
						ArrayList errors = Utility.validateValue(
								new ValidationBean(obArray),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_VN_DATE_VALID)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				
				
				if(	objDto.getTxtExtDate().equalsIgnoreCase("00/00/0000") || objDto.getTxtExtDate().equalsIgnoreCase("0") )
					objDto.setTxtExtDate("");

		/*		
			if ((String.valueOf(objDto.getWarrentyMonths()) != null || String.valueOf(objDto.getWarrentyMonths()) != ""
						|| objDto.getIsReqWarrentyMonths() == 1)||(String.valueOf(objDto.getWarrentyMonths()) != null
						&& objDto
									.getIsReqWarrentyMonths() == 1)) {
				
					if (!errorsFound)// Selected Store ID
					{
						ArrayList errors = Utility.validateValue(
								new ValidationBean(String.valueOf(objDto
										.getWarrentyMonths()),
										"Warranty Months", 3),
								"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY+ ","
										+ Utility.CASE_MAXLENGTH)
								.getCompleteMessageStrings();
						if (errors != null) {
							listProductCatelog = errors;
							errorsFound = true;
						}
					}
				}
				*/
			}
			//For Hardware Starts
			
			
				//lawkush
			//Start[001]	
				
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if ((String.valueOf(chargeDto.getChargeType()) != null && chargeDto.getIsReqDdCType() == 1))
						{
							if(!errorsFound)//Charge Type
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto
												.getChargeType()), "Charge Type", 3),
										"" + Utility.CASE_MANDATORY1 )
										.getCompleteMessageStrings();
								if (errors != null) {
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				
				//lawkush charge name adding one more mandatory2 validation by vijay
				
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(String.valueOf(chargeDto.getChargeName())!= null && chargeDto.getIsReqTxtCName()==1)
						{
							if(!errorsFound)//Charge Name
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getChargeName()),
												"Charge Name",100),
										"" + Utility.CASE_MANDATORY1 + ","
										   + Utility.CASE_MANDATORY2)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				//add by anil for validate charge Annotation
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(chargeDto.getChargeAnnotation()!=null && chargeDto.getIsReqTxtCAnnotation()==1)
						{
							if(!errorsFound)//Charge Annotation
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(chargeDto.getChargeAnnotation(),
												"Charge Annotation", 500),
										"" + Utility.CASE_MANDATORY + ","
												+ Utility.CASE_SPECIALCHARACTERS + ","
												+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				
				//lawkush charge Period 
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(String.valueOf(chargeDto.getChargePeriod())!=null && chargeDto.getIsReqTxtCPeriod()==1)
						{
							if(!errorsFound)//Charge Period
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getChargePeriod()),
												"Charge Period", 100),
										"" + Utility.CASE_MANDATORY2 + ","
												+ Utility.CASE_SPECIALCHARACTERS + ","
												+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				
				//lawkush total charge amount
				
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(chargeDto.getChargeAmount_String()!=null && chargeDto.getIsReqTxtCTAmt()==1)
						{
							if(!errorsFound)//total Charge Amount
							{
								String strChargeAmount=chargeDto.getChargeAmount_String();
								ArrayList errors=Utility.validateValue(new ValidationBean(strChargeAmount,"Total Charge Amount",15),
										""+Utility.CASE_MANDATORY+","+Utility.CASE_DECIMALNUBER+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
								else
								{
									int index=strChargeAmount.indexOf('.');
									if(index<0 && strChargeAmount.length()>12)
									{
										errorsFound=true;
										errors= new ArrayList();
										errors.add("Only 12 digits are allowed in "+"Charge Amount (before decimal)");
										listProductCatelog = errors;
									}
									else if(index>12 )
									{
										errorsFound=true;
										errors= new ArrayList();
										errors.add("Only 12 digits are allowed before decimal(.) in "+"Charge Amount");
										listProductCatelog = errors;
									}
									else if(index>=0 && (strChargeAmount.length()-index-1)>2)
									{
										errorsFound=true;
										errors= new ArrayList();
										errors.add("Only 2 digits are allowed after decimal(.) in "+"Charge Amount");
										listProductCatelog = errors;
									}
								}
							}
							
						}
					}					
				}
				//lawkush frequency
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(chargeDto.getChargeFrequency()!= null && chargeDto.getIsReqTxtCFrequency()==1)
						{
							if(!("2".equalsIgnoreCase(String.valueOf(chargeDto.getChargeType()))))
						  {
							if(!errorsFound)//frequency 
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getChargeFrequency()),
												"Frequency ", 100),
										"" + Utility.CASE_MANDATORY1 + ","
												+ Utility.CASE_SPECIALCHARACTERS + ","
												+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						 }
					 }
							
					}					
				}
				
				//lawkush frequency amount
				
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(chargeDto.getChargeFrequencyAmt()!=0 && chargeDto.getIsReqTxtCFreqAmt()==1)
						{
							if(!errorsFound)//frequency amount
							{
								String strChargeFrequencyAmt=String.valueOf(chargeDto.getChargeFrequencyAmt());
								ArrayList errors=Utility.validateValue(new ValidationBean(strChargeFrequencyAmt,"Charge Frequency Amount",15),
										""+Utility.CASE_MANDATORY+","+Utility.CASE_DECIMALNUBER+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
								else
								{
									int index=strChargeFrequencyAmt.indexOf('.');
									if(index<0 && strChargeFrequencyAmt.length()>12)
									{
										errorsFound=true;
										errors= new ArrayList();
										errors.add("Only 12 digits are allowed in "+"Charge Frequency Amount (before decimal)");
										listProductCatelog = errors;
									}
									else if(index>12 )
									{
										errorsFound=true;
										errors= new ArrayList();
										errors.add("Only 12 digits are allowed before decimal(.) in "+"Charge Frequency Amount");
										listProductCatelog = errors;
									}
									else if(index>=0 && (strChargeFrequencyAmt.length()-index-1)>2)
									{
										errorsFound=true;
										errors= new ArrayList();
										errors.add("Only 2 digits are allowed after decimal(.) in "+"Charge Frequency Amount");
										listProductCatelog = errors;
									}
								}
							}
							
						}
					}					
				}
				//lawkush start date logic
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(chargeDto.getStartDateLogic()!= null && chargeDto.getIsReqTxtCStartDate()==1)
						{
							if(!errorsFound)//frequency 
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean((chargeDto.getStartDateLogic()),
												"Start Date Logic ", 100),
										"" + Utility.CASE_MANDATORY1
										 + ","
											+ Utility.CASE_SPECIALCHARACTERS + ","
											+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				//lawkush start days
				
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if((chargeDto.getStartDateDays())==-1 && chargeDto.getIsReqTxtCStartDays()==1)
						{
							if(!errorsFound)//Charge Period
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getStartDateDays()),
												"Start Days", 100),
										"" + Utility.CASE_MANDATORY1 + ","
												+ Utility.CASE_SPECIALCHARACTERS + ","
												+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				//lawkush Start Month
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(String.valueOf(chargeDto.getStartDateMonth())!=null && chargeDto.getIsReqTxtCStartMonths()==1)
						{
							if(!errorsFound)//Start Month
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getStartDateMonth()),
												"Start Month", 100),
										"" + Utility.CASE_MANDATORY2 + ","
												+ Utility.CASE_SPECIALCHARACTERS + ","
												+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				
				//lawkush End date logic
				
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(chargeDto.getEndDateLogic()!= null && chargeDto.getIsReqTxtCEndDate()==1)
						{
							if(!errorsFound)//End date logic 
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean((chargeDto.getEndDateLogic()),
												"End Date Logic ", 100),
										"" + Utility.CASE_MANDATORY1
										 + ","
											+ Utility.CASE_SPECIALCHARACTERS + ","
											+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				
				//lawkush End days
				
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(String.valueOf(chargeDto.getEndDateDays())!=null && chargeDto.getIsReqTxtCEndDays()==1)
						{
							if(!errorsFound)//End days
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getEndDateDays()),
												"End days", 100),
										"" + Utility.CASE_MANDATORY2 + ","
												+ Utility.CASE_SPECIALCHARACTERS + ","
												+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				//lawkush End Month
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(String.valueOf(chargeDto.getEndDateMonth())!=null && chargeDto.getIsReqTxtCEndMonths()==1)
						{
							if(!errorsFound)//End Month
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getEndDateMonth()),
												"End Month", 100),
										"" + Utility.CASE_MANDATORY2 + ","
												+ Utility.CASE_SPECIALCHARACTERS + ","
												+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				
//				Saurabh Charge Remarks
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if((String.valueOf(chargeDto.getChargeRemarks())!=null || String.valueOf(chargeDto.getChargeRemarks()) != "")
									&& chargeDto.getIsReqTxtRemarks()==1)
						{
							if(!errorsFound)//End Month
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getChargeRemarks()),
												"Remarks", 300),
										"" + Utility.CASE_MANDATORY + ","
												+ Utility.CASE_SPECIALCHARACTERS + ","
												+ Utility.CASE_MAXLENGTH)
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				/*
		//End[001]		
				//[000777]Start
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(String.valueOf(chargeDto.getLineItemName())!= null && chargeDto.getIsReqLineItem()==1)
						{
							if(!errorsFound)//Charge Name
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getLineItemName()),
												"Line Item",100),
										"" + Utility.CASE_MANDATORY1 )
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				
				if(objDto.getChargesDetails()!= null )
				{
					for (int count = 0; count< objDto.getChargesDetails().size();count++)
					{
						ChargesDetailDto chargeDto = objDto.getChargesDetails().get(count);
						if(String.valueOf(chargeDto.getSubLineItemName())!= null && chargeDto.getIsReqSubLineItem()==1)
						{
							if(!errorsFound)//Charge Name
							{
								ArrayList errors = Utility.validateValue(
										new ValidationBean(String.valueOf(chargeDto.getSubLineItemName()),
												"Sub Line Item",100),
										"" + Utility.CASE_MANDATORY1 )
										.getCompleteMessageStrings();
								if(errors!=null)
								{
									listProductCatelog = errors;
									errorsFound=true;
								}
							}
							
						}
					}					
				}
				*/
				//[000777]End
			//For Charge Details Section Ends
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listProductCatelog;
	}
	
	public ArrayList ValidateServiceAttributes(NewOrderDto objDto) throws Exception
	{
		ArrayList listServiceAttributes= new ArrayList();
		boolean errorsFound=false;
		try
		{
			if(!errorsFound)//For Service ID
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getServiceId()),"Service ID",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					listServiceAttributes = errors;
					errorsFound=true;
				}
			}
			
			if (objDto.getAttRemarks()!=null || objDto.getAttRemarks()!="") {
				if (!errorsFound)// Sale Type
				{
					ArrayList errors = Utility.validateValue(
							new ValidationBean(objDto.getAttRemarks(),
									"Attribute Remarks", 1000),
							"" + Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						listServiceAttributes = errors;
						errorsFound = true;
						
					}
				}
			} 
			
			
			if(!errorsFound)//For Cust Logical ID
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getCustomer_logicalSINumber()),"Cust Logical SI Number",50),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					listServiceAttributes = errors;
					errorsFound=true;
				}
			}
			if(!errorsFound)//For Effective Start Date
			{
				if(!errorsFound)
				{
					Object obArray[]={""+ValidationBean.VN_DATE_VALID,objDto.getEffStartDate(),"Effective Start Date",
							new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
					ArrayList errors=Utility.validateValue(new ValidationBean(obArray),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();
					if(errors!=null)
					{
						listServiceAttributes = errors;
						errorsFound=true;
					}
				}
			}
			/*if(!errorsFound)//For Effective Start Date
			{
				if(!errorsFound)
				{
					Object obArray[]={""+ValidationBean.VN_DATE_VALID,objDto.getEffEndDate(),"Effective End Date",
							new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
					ArrayList errors=Utility.validateValue(new ValidationBean(obArray),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_VN_DATE_VALID).getCompleteMessageStrings();
					if(errors!=null)
					{
						listServiceAttributes = errors;
						errorsFound=true;
					}
				}
			}*/
			//[004] start
			if(!errorsFound)//For parallel upgrade LSI no1
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getParallelUpgradeLSINo1()),"Parallel Upgrade LSI No1",250,21),
						""+Utility.CASE_CSV_LONG+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_MAXCSV).getCompleteMessageStrings();
				if(errors!=null)
				{
					listServiceAttributes = errors;
					errorsFound=true;
				}
			}
			
			if(!errorsFound)//For parallel upgrade LSI no2
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getParallelUpgradeLSINo2()),"Parallel Upgrade LSI No2",250,21),
						""+Utility.CASE_CSV_LONG+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_MAXCSV).getCompleteMessageStrings();
				if(errors!=null)
				{
					listServiceAttributes = errors;
					errorsFound=true;
				}
			}
			if(!errorsFound)//For parallel upgrade LSI no3
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(objDto.getParallelUpgradeLSINo3()),"Parallel Upgrade LSI No3",250,21),
						""+Utility.CASE_CSV_LONG+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_MAXCSV).getCompleteMessageStrings();
				if(errors!=null)
				{
					listServiceAttributes = errors;
					errorsFound=true;
				}
			}
			//[004] end
			//[005] Start
			if (objDto.getRemarksParallelUpgrade()!=null || objDto.getRemarksParallelUpgrade()!="") {
				if (!errorsFound)
				{
					ArrayList errors = Utility.validateValue(
							new ValidationBean(objDto.getRemarksParallelUpgrade(),
									"Parallel Upgrade Remarks", 150),
							"" + Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						listServiceAttributes = errors;
						errorsFound = true;
						
					}
				}
			}
			//[005] End
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listServiceAttributes;
	}

	public static ArrayList<String> validateServicePrdAttValues(NewOrderDto objDto) throws Exception {
		
		ArrayList<String> errors=new ArrayList<String>();
		 String[] prodAttributeLabelArray=objDto.getProdAttributeLabelArray();
		 
		 String[] prodAttributeDataTypeArray=objDto.getProdAttributeDataTypeArray();
		 String[] prodAttributeDisplayLabelArray=objDto.getProdAttributeDisplayLabelArray();
		 String[] prodAttributeIsMandatory=objDto.getProdAttributeIsMandatory();
		 Integer[] prodAttributeMaxLength=objDto.getProdServiceAttMaxLength();
		 if(prodAttributeDisplayLabelArray!=null)
		 {
			 for (int i=0;i<prodAttributeDisplayLabelArray.length;i++) {
				
				String value=prodAttributeLabelArray[i];
				String dataType=prodAttributeDataTypeArray[i];
				String displayLabel=prodAttributeDisplayLabelArray[i];
				String isMandatory=prodAttributeIsMandatory[i];
				int attributeMaxLength=prodAttributeMaxLength[i];
				if("N".equals(isMandatory) && (value==null || "".equals(value.trim())))
				{
					continue;
				}
				
				if("VARCHAR".equalsIgnoreCase(dataType))
				{
					//--modified by vijay--//
					//here just increase the length of varchar from 25 to 100. 
					errors=Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
							(displayLabel, value, attributeMaxLength /*25*/),
							Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
												Utility.CASE_MAXLENGTH)).getCompleteMessageStrings();
				}
				else if("YN".equalsIgnoreCase(dataType))
				{
					errors=Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
							(displayLabel, value, 25),
							Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
												Utility.CASE_MAXLENGTH,Utility.CASE_YN)).getCompleteMessageStrings();
					/*if(errors==null)
					{
						if(!("n".equalsIgnoreCase(value) || "y".equalsIgnoreCase(value)))
						{
							errors=new ArrayList<String>();
							errors.add("Only Y/N allowed in "+displayLabel);
						}
					}*/
				}
				else if("NUMERIC".equalsIgnoreCase(dataType))
				{
					errors=Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
							(displayLabel, value, 25),
							Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_DIGITS_ONLY,
												Utility.CASE_MAXLENGTH)).getCompleteMessageStrings();
				}
				else if("DATETIME".equalsIgnoreCase(dataType))
				{
					errors=Utility.validateValue(new ValidationBean().loadValidationBean_Time_Pattern
							(displayLabel, value, Messages.getMessageValue("calendar_entry_format")),
							Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_TIME_PATTERN)).getCompleteMessageStrings();
				}
				else if("EMAIL".equalsIgnoreCase(dataType))
				{
					errors=Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
							(displayLabel, value, 50),
							Utility.generateCSV(Utility.CASE_MANDATORY,
												Utility.CASE_MAXLENGTH,Utility.CASE_EMAIL))
												.getCompleteMessageStrings();
				}
				else if("DROPDOWN".equalsIgnoreCase(dataType))
				{
					errors=Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
							(displayLabel, value, 25),
							Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
												Utility.CASE_MAXLENGTH)).getCompleteMessageStrings();
				}
				if(errors!= null && errors.size()>0)
				{
					return errors;
				}
				
			}
		 }
		 
		return null;
	}

	// added by manisha cust bill exp bfr 14 start 
	public ViewOrderDto validateDisconnectionDate(ViewOrderDto objDto,Connection connection) throws Exception
	{
		
		
		int arrLen=0;
		long longServiceProductId=0;
		
		String billingTriggerDate=null;
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		
		
		try {
				long orderNo=Long.parseLong(objDto.getOrderNo());
				String strBillingTrigger=null;
				strBillingTrigger=objDto.getBillingTriggerString();
				String csvServiceProductIds="";
				String csvBillingTriggerDate = "";
				String csvLineType="";
				arrLen=strBillingTrigger.split("@@").length;
				String[] eachLine=strBillingTrigger.split("@@");
				//String lineType[]=objDto.getLine_prop();
				ViewOrderDao objViewOrderDao =  new ViewOrderDao();
				
				ArrayList<ViewOrderDto> results=new ArrayList<ViewOrderDto>();
				HashMap<String, String> hmap_SPID_BTDate =new HashMap<String, String>();
				for(int i=1;i<=arrLen;i++)
				{	 
				 	 String str=eachLine[i-1];
					 String individual[]=str.split("~");
					 
					 longServiceProductId=Long.parseLong(individual[0]);
					 
					 billingTriggerDate=individual[3];
					 csvServiceProductIds=csvServiceProductIds+","+longServiceProductId;
					 hmap_SPID_BTDate.put(String.valueOf(longServiceProductId),billingTriggerDate );
					 //hmap_SPID_LineType.put(String.valueOf(longServiceProductId),lineType[i-1] );
					 
				 }  
				objDto = null;
				objDto = objViewOrderDao.validateDisconnectionDate(orderNo,connection,hmap_SPID_BTDate);

			
			//
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return objDto;
		
	}
	// added by manisha cust bill exp bfr 14 end
	
	public static ArrayList ValidateStandardReasonName(StandardReason objDto) throws Exception
	{
		ArrayList listErrors= new ArrayList();
		
		boolean errorsFound=false;
		try
		{
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(objDto.getStdReasonName(),"Standard Reason Name",255),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS_V2+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					listErrors = errors;
					errorsFound=true;
				}
			}			
		}
		catch(Exception ex)
		{
			Utility.LOG(false, true, ex, "error occurred in ValidateStandardReasonName method of AjaxValidation class");
		}
		return listErrors;
	}
}
