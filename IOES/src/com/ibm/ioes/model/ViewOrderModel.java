//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha Garg	22-jun-11	00-05422		To get products in BillingTrigger Page for New Order
//[002]	 Manisha Garg	22-jun-11	00-05422		To get products in BillingTrigger Page  for Change Order
//[003]	 Manisha Garg	01-oct-12	00-05422		To get Line Items for Billing Trigger BulkUpload
//[004]	 Manisha Garg	01-oct-12	00-05422		To get Charge Items for Billing Trigger BulkUpload
//[005]    Lawkush  30-MAR-13  BUG ID TRNG22032013005 fixed
package com.ibm.ioes.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.ibm.ioes.actions.UniversalWorkQueueAction;
import com.ibm.ioes.beans.BillingTriggerValidation;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.forms.ChargeSummaryChargeSection;
import com.ibm.ioes.forms.ChargeSummaryComponentSection;
import com.ibm.ioes.forms.ChargeSummaryProductCatelog;
import com.ibm.ioes.forms.ChargeSummaryServiceDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.ViewOrderFormBean;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class ViewOrderModel {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(UniversalWorkQueueAction.class);
	}
	public ArrayList<ViewOrderDto> getTaskListOfOrder(long orderNo)
	{
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		ArrayList<ViewOrderDto> alTaskListOfOrder=null;
		try 
		{
			Utility.SysOut(" getTaskListOfOrder of Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			alTaskListOfOrder=objViewOrderDao.getTaskListOfOrder(orderNo);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
		} 
		return alTaskListOfOrder;
	}
	public ArrayList getTaskListHistory(long orderNo)
	{
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		ArrayList alTaskListOfOrder=null;
		try 
		{
			Utility.SysOut(" getTaskListOfOrder of Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			alTaskListOfOrder=objViewOrderDao.getTaskListHistory(orderNo);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
		} 
		return alTaskListOfOrder;
	}
	/*public ArrayList getChargeSummaryList(long orderNo)
	{
		logger.info("View Order Interface and getChargeSummaryList method of ViewOrderModel class have been called");
		ArrayList alChargeSummaryList=null;
		try 
		{
			System.out.println(" getChargeSummaryList of Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			alChargeSummaryList=objViewOrderDao.getChargeSummaryList(orderNo);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in getChargeSummaryList method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
		} 
		return alChargeSummaryList;
	}*/
	
	public int getPublishResult(long orderNo,String publishPage, OrderHeaderDTO orderTypeDto,String serviceList,String publishList,String roleid,String owner)
	{
		logger.info("View Order Interface and getTaskListOfOrder method of ViewOrderModel class have been called");
		int publishResult=0;
		try 
		{
			Utility.SysOut(" getTaskListOfOrder of Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			publishResult=objViewOrderDao.getPublishResult(orderNo,publishPage,orderTypeDto,null,serviceList,publishList,roleid,owner);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
			publishResult=1;
		} 
		return publishResult;
	}
	
//	For Checking whether the order is published or not and also for check of Billing Ready
	public String fnIsOrderPublishedBillingTrigger(long orderNo,long roleId)
	{
		ViewOrderDao objViewOrderDao =  new ViewOrderDao();
		String IsOrderPublishedBillingTrigger= null;
		try
		{
			IsOrderPublishedBillingTrigger =  objViewOrderDao.fnIsOrderPublishedBillingTrigger(orderNo,roleId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return IsOrderPublishedBillingTrigger;
	}
	
	//For displaying the list of 
	/*public ViewOrderDto getSELECT_BT_PRODUCTS_NEWORDER(ViewOrderDto objdto)
	{
		logger.info("View Order Interface and getSelectServiceDetails method of ViewOrderModel class have been called");
		ArrayList<ViewOrderDto> alChargeSummaryList=null;
		ViewOrderDto objLSIdto=null;
//		001 start
		ArrayList<ViewOrderDto> alLSIList =new ArrayList();
		HashMap<String,LabelValueBean> hm=new HashMap<String, LabelValueBean>();
		HashMap<String,LabelValueBean> hm1=new HashMap<String, LabelValueBean>();
		
		String InputLSI=objdto.getSearchLSI();
		String InputCustLSI=objdto.getCustLogicalSino();
		ViewOrderDto LSIRESULT=new ViewOrderDto();
//		001 end
		try 
		{
			System.out.println(" getSelectServiceDetails of Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			alChargeSummaryList=objViewOrderDao.getSELECT_BT_PRODUCTS_NEWORDER(objdto);
//			001 start
			for(int i=0;i<alChargeSummaryList.size();i++)
				
			{
				boolean lsipass=false;
				boolean custlsipass=false;
				
				objLSIdto=alChargeSummaryList.get(i);
				String lsiresult=objLSIdto.getLogicalSino();
				hm.put(lsiresult,new LabelValueBean(lsiresult,lsiresult));
				String customerlsi=objLSIdto.getCustLogicalSino();
				hm1.put(customerlsi,new LabelValueBean(customerlsi,customerlsi));
				
				if( InputLSI==null || "".equals(InputLSI) || "-1".equals(InputLSI)|| lsiresult.equals(InputLSI) )
				{
					lsipass=true;
				
				}
				
				if( InputCustLSI==null || "".equals(InputCustLSI) || "-1".equals(InputCustLSI)|| customerlsi.equals(InputCustLSI) )
				{
					custlsipass=true;
					
				}
				
				if(lsipass==true&&custlsipass==true)
				{
			          alLSIList.add(objLSIdto);
				}
				
				
			
				
		}
			
		ArrayList<LabelValueBean> lsis=new ArrayList<LabelValueBean>(hm.values());
		ArrayList<LabelValueBean> customerlsis=new ArrayList<LabelValueBean>(hm1.values());	
		
	
		LSIRESULT.setLSIS(lsis);
		LSIRESULT.setProductList(alLSIList);
		LSIRESULT.setCusLSIResult(customerlsis);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in getSelectServiceDetails method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
		} 
		return LSIRESULT;
	}*/

	//001 end
	 /**
	  * f0LLOWING FUNCTION ADD BY MANISHA FOR CHANGE ORDER BILLING TRRIGER
	    Change Order Type : connection
	  */
	/*public ArrayList<ViewOrderDto> getSELECT_BT_PRODUCTS_DISCONNECTION(long orderNo)
	{
		logger.info("View Order Interface and getSelectServiceDetails method of ViewOrderModel class have been called");
		ArrayList<ViewOrderDto> alChargeSummaryList=null;
		try 
		{
			System.out.println(" getSelectServiceDetails of Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			alChargeSummaryList=objViewOrderDao.getSELECT_BT_PRODUCTS_DISCONNECTION(orderNo);
			
			
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in getSelectServiceDetails method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
		} 
		return alChargeSummaryList;
	}*/
	//FOR BILLING TRIGGER RATE RENEWAL
	
	
	//For Billing Trigger Service Product Solution Change
	
	class LongStringComaparator implements Comparator<String>{

		public int compare(String o1, String o2) {
			long l1=Long.parseLong(o1);
			long l2=Long.parseLong(o2);
			if(l1>l2)
			{
				return 1;
			}else if(l1==l2)
			{
				return 0;
			}else if(l1<l2)
			{
				return -1;
			} 
			return 0;
		}
		
	}
	
	public ViewOrderDto getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(ViewOrderDto objdto)
	{
		logger.info("getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS method of ViewOrderModel class have been called");
		ArrayList<ViewOrderDto> alChargeSummaryList=null;
//		002 start
		ViewOrderDto objLSIdto=null;
		ArrayList<ViewOrderDto> alLSIList =null;
		TreeMap<String,LabelValueBean> hm=new TreeMap<String, LabelValueBean>(new LongStringComaparator());
		TreeMap<String,LabelValueBean> hm1=new TreeMap<String, LabelValueBean>(new LongStringComaparator());
        Connection conn = null;

        
        if(objdto.getSearchLSI()==null || "".equals(objdto.getSearchLSI()) || "-1".equals(objdto.getSearchLSI()))
        {
        	objdto.setSearchLSI(null);
        }
        if(objdto.getCustLogicalSino()==null || "".equals(objdto.getCustLogicalSino()) || "-1".equals(objdto.getCustLogicalSino()))
        {
        	objdto.setCustLogicalSino(null);
        }
        
		ViewOrderDto LSIRESULT=new ViewOrderDto();
//		002 end
		try 
		{
			conn = DbConnection.getConnectionObject();
			
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			
			alChargeSummaryList=objViewOrderDao.getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS(objdto,conn);
			//Comment for prformance tunning
			/*ArrayList<ViewOrderDto> serviceList=objViewOrderDao.getServiceslist(objdto.getPonum(),conn);
			
			for (ViewOrderDto dto : serviceList) {
				String lsis=dto.getLogicalSino();
				hm.put(lsis,new LabelValueBean(lsis,lsis));
				String customerlsi=dto.getCustLogicalSino();
				hm1.put(customerlsi,new LabelValueBean(customerlsi,customerlsi));
			}*/
			
//			002 start
			BillingTriggerValidation billingTriggerValidation = null;
            for(int i=0;i<alChargeSummaryList.size();i++)
			{
            	objLSIdto=alChargeSummaryList.get(i);
            	//compute properites for this line item which is used for
            	//	a.communicating info to presentation for fields allowed for edit
            	billingTriggerValidation =new BillingTriggerValidation(); 
				billingTriggerValidation.setSourceData(objLSIdto,objdto.getOrderInfo(),null/*eventids*/);
				billingTriggerValidation.computeProperties();
				objLSIdto.setBillingTriggerAllowDenyLogic(billingTriggerValidation);
			}
            
            
			
			
		ArrayList<LabelValueBean> lsis=new ArrayList<LabelValueBean>(hm.values());	
		ArrayList<LabelValueBean> customerlsis=new ArrayList<LabelValueBean>(hm1.values());	
	
		LSIRESULT.setLSIS(lsis);
		LSIRESULT.setProductList(alChargeSummaryList);
		LSIRESULT.setCusLSIResult(customerlsis);

		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in getSELECT_BT_PRODUCTS_FOR_CHANGE_ORDERS method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return LSIRESULT;
	}
	//002 end
	
	
	
	
	/*public ArrayList getSelectChargeDetails(long orderNo)
	{
		logger.info("View Order Interface and getSelectChargeDetails method of ViewOrderModel class have been called");
		ArrayList alChargeSummaryList=null;
		try 
		{
			System.out.println(" getSelectChargeDetails of Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			alChargeSummaryList=objViewOrderDao.getSelectChargeDetails(orderNo);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in getSelectChargeDetails method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
		} 
		return alChargeSummaryList;
	}*/
	
	/**
	 * Save data in db
	 * Save data of charges in FX local tables
	 * Call API , store result in tables(fx local tables) and if reqd in other master tables
	 * display approriate page
	 * 
	 * @param orderNo
	 * @param strBillingTrigger
	 * @return
	 */
/*	public int getBillingTriggerResult(long orderNo,String strBillingTrigger,String dataChanged,ViewOrderDto dto) throws Exception
	{
		int billingTriggerResult=0;
		Connection conn = null;
		long longServiceProductId=0;
		int arrLen=0;
		try 
		{
			System.out.println(" getBillingTriggerResult of Inside Model of Billing Trigger Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			//save the data entered by user for checked lines
			billingTriggerResult=objViewOrderDao.getBillingTriggerResult(orderNo,strBillingTrigger,dataChanged,dto);
			
			//fetch ServiceproductId;
			 arrLen=strBillingTrigger.split("@@").length;
			 String arr[]=new String[arrLen];
			 for(int i=1;i<=arrLen;i++)
			 {
				 String str=strBillingTrigger.split("@@")[i-1];
				 String individual[]=str.split("~");
				 arr[i-1]=individual[0];
				 
			
				
			 }
			 
			ArrayList<ViewOrderDto> temp=objViewOrderDao.chargeBilling(arr);
			dto.setBillingTriggerResult(temp);
			
			
			objViewOrderDao.setBTEndIfPossible(orderNo);
					
			//publish all checked sp(srvice products) info with charges to fx
			
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		} 
		return billingTriggerResult;
	}*/
	private final int CHARGE_TYPE_RC = 1;
	private final int CHARGE_TYPE_NRC = 2;
	private final int FX_STATUS_TO_BE_PUSHED = 1;
    private final int FX_STATUS_ALREADY_PUSHED = 3;
    private final int DISCONNECTED_BEFORE_PUSHING_TO_FX = 5;
    private final int DISCONNECTED_AFTER_PUSHING_TO_FX = 6;
    private final int FAILED_TO_BE_DISCONNECTED = 7;
	private ServletRequest request;  
    
    
	
	//created for change billing trigger
/*public ViewOrderDto fnBillingTriggerSubmitForDisconnect(long orderNo,String strBillingTrigger,String dataChanged,ViewOrderDto dto) throws Exception
	{
		ArrayList billingTriggerResult=null;		
		ViewOrderDto objViewOrderDto1=new ViewOrderDto();
	String str1="";
	String csvServiceProductIds="";
	int arrLen=0;
	long longServiceProductId=0;
	Connection conn=null;
		try 
		{
			System.out.println(" getBillingTriggerResultForChange of Inside Model of Billing Trigger Interface");
			ViewOrderDto objViewOrderDto= null;
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			
			
			
			arrLen=strBillingTrigger.split("@@").length;
			String[] eachLine=strBillingTrigger.split("@@");
			for(int i=1;i<=arrLen;i++)
			{	 
				 String str=eachLine[i-1];
				 String individual[]=str.split("~");
				 longServiceProductId=Long.parseLong(individual[0]);
				 csvServiceProductIds=csvServiceProductIds + ',' + longServiceProductId ;
			}
			if(csvServiceProductIds.length()>0)
			{
			 csvServiceProductIds=csvServiceProductIds.substring(1);
			}
			System.out.print(str1);
			
			//update the status(BILLING_TRIGGER_DISCONNECT_STATUS to 20) of products which are selected by user
			// and transfer corresponding charges to temp table
			//commit connection if success , otherwise rollback
			conn=DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			try
			{
				objViewOrderDao.updateStatusAndMoveChargesDataToSecondaryTables(conn,orderNo,csvServiceProductIds);
				conn.commit();
			}
			catch(Exception ex)
			{
				conn.rollback();
				ex.printStackTrace();
				Utility.LOG(true, true, ex.toString());
			}
	
 
			ArrayList<ViewOrderDto> temp=objViewOrderDao.getBillingTiggerResult_Disconnection(conn,csvServiceProductIds);
			dto.setBillingTriggerResult(temp);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in fnBillingTriggerSubmitForDisconnect method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		} 
		finally
		{
			DbConnection.freeConnection(conn);
		}
		 
		return dto ;
	}*/
	
	//For Rate Renewal

/*public ViewOrderDto fnBillingTriggerSubmitForRateRenewal(long orderNo,String strBillingTrigger,String dataChanged,ViewOrderDto dto) throws Exception
{
	ArrayList billingTriggerResult=null;		
	ViewOrderDto objViewOrderDto1=new ViewOrderDto();
	String str1="";
	int arrLen=0;
	long longServiceProductId=0;
	String locDate=null;
	String billingTriggerDate=null;
	String locNo=null;
	Connection conn = null;
	String status = null;
	CallableStatement csUpdateBilling_Trigger_Status=null;

	try 
	{
		System.out.println(" getBillingTriggerResultForChange of Inside Model of Billing Trigger Interface");
		ViewOrderDto objViewOrderDto= null;
		ViewOrderDao objViewOrderDao =  new ViewOrderDao();
		if(conn == null) 
			conn= DbConnection.getConnectionObject();
			//conn.setAutoCommit(false);
		
		
		arrLen=strBillingTrigger.split("@@").length;
		String[] eachLine=strBillingTrigger.split("@@");
		
		for(int i=1;i<=arrLen;i++)
		{	 
			 try {
				 String str=eachLine[i-1];
				 String individual[]=str.split("~");
				 
				 
				 longServiceProductId=Long.parseLong(individual[0]);
				 locNo=individual[1];
				 locDate=individual[2];
				 billingTriggerDate=individual[3];
				 
				 
				 status = objViewOrderDao.pushChargesInFXForDisconnection(conn,longServiceProductId,locNo,locDate,billingTriggerDate);
				 
			
				if(null !=status  && "SUCCESS".equalsIgnoreCase(status))
				 {
					objViewOrderDto1.setSuccess("SUBMITTED");
				 }
						
				 else
				 {
					 objViewOrderDto1.setSuccess("FAILURE");
				
				 }
				 
				 if(null !=status  && "SUCCESS".equalsIgnoreCase(status))
				 {
					 // GET LIST OF CHARGES FOR DISCONNECTION
					 billingTriggerResult=objViewOrderDao.getListOfChargesForRateRenewalDisconnection(conn, longServiceProductId);
				
					if(billingTriggerResult!=null&&billingTriggerResult.size()>0) {
						for (int j=0;j<billingTriggerResult.size();j++){
							try {
							objViewOrderDto = (ViewOrderDto)billingTriggerResult.get(j);
								;
								 * 1:rcid
								 * 2:nrcid
								 
							
								if(objViewOrderDto!=null&&objViewOrderDto.getChargeTypeName()!=0&&objViewOrderDto.getChargeTypeName()==CHARGE_TYPE_RC)for RCID
								{
									if(objViewOrderDto.getFx_Status()==FX_STATUS_TO_BE_PUSHED)to be pushed in to fx
									{										
										ViewOrderDao.updateFX_Status(DISCONNECTED_BEFORE_PUSHING_TO_FX, objViewOrderDto.getCharge_info_id());
									
									}else if(objViewOrderDto.getFx_Status()==FX_STATUS_ALREADY_PUSHED)successfuly pushed in fx
									{
										if(disconnectCharges(String.valueOf(objViewOrderDto.getRcId()),objViewOrderDto.getChargeDisconnectDate())==PRODUCT_DISCONNECTED)
										{													// successfully disconnected in fx
											ViewOrderDao.updateFX_Status(DISCONNECTED_AFTER_PUSHING_TO_FX, objViewOrderDto.getCharge_info_id());
										}
										else
										{
											// failed to be disconnected in fx
											ViewOrderDao.updateFX_Status(FAILED_TO_BE_DISCONNECTED, objViewOrderDto.getCharge_info_id());
										}
									}
								}else if(objViewOrderDto!=null&&objViewOrderDto.getChargeTypeName()!=0&&objViewOrderDto.getChargeTypeName()==CHARGE_TYPE_NRC)for NRCID
								{
									 if(objViewOrderDto.getFx_Status()==FX_STATUS_TO_BE_PUSHED)to be pushed in to fx
									 {
										 ViewOrderDao.updateFX_Status(DISCONNECTED_BEFORE_PUSHING_TO_FX, objViewOrderDto.getCharge_info_id());
										 
									 }
									 else if(objViewOrderDto.getFx_Status()==FX_STATUS_ALREADY_PUSHED)
									 {	 										
										 ViewOrderDao.updateFX_Status(DISCONNECTED_BEFORE_PUSHING_TO_FX, objViewOrderDto.getCharge_info_id());
										 
									 }	
								
								}
						  }catch(Exception e) {
							e.printStackTrace();
						  	}// end of for loop
						}
					}
					
						  
						     ViewOrderDto info=objViewOrderDao.getServiceProductInfo(conn,longServiceProductId);
							 String productType=info.getProductType();
						    
							 //Substeps
							 //1.read data in bean from secondary tables
							 	//1.2 if success next substep->2 
							 	//1.4 if exception update Billing_Trigger_Status and contiue with next serviceproduct(iteration)
							 
							 //2.send bean to FX
							 	//2.2 if success,then commit order , and update Billing_Trigger_Status 
							 	//2.4 if failure or exception then cancel order and update Billing_Trigger_Status
							 
							 //3. continue with next 
							 
							 //Impl
							 //1.
							 ServiceDTO serDto = null;
							 ChargesDto chargesDto = null;
							 //rEADIN dto FROM LOCAL FX TABLES
							 try
							 {
								 if(AppConstants.ProductType_Hardware.equals(productType) )
								 {
									 chargesDto=objViewOrderDao.fetchNextAccountLevelChargesData(conn,longServiceProductId);
								 }
								 else if(AppConstants.ProductType_Bandwidth.equals(productType)|| AppConstants.ProductType_Service.equals(productType))
								 {
									 serDto=objViewOrderDao.fetchNextServiceNChargesData(conn,longServiceProductId);	 
								 }
								 
							 }
							 catch(Exception ex)
							 {
								 Utility.LOG(true, true, "Exception ins STEP-3 on Billing trigger :populating bean for longServiceProductId="+longServiceProductId);
								 //update billing_trigger_status to 8
								 csUpdateBilling_Trigger_Status.clearParameters();
								 csUpdateBilling_Trigger_Status.setLong(1, longServiceProductId);
								 csUpdateBilling_Trigger_Status.setLong(2, AppConstants.Billing_Trigger_Status_FX_FAILURE_LOCAL_BEAN_READ);
								 csUpdateBilling_Trigger_Status.execute();
								 continue;
							 }
							 
							 
							 if(AppConstants.ProductType_Hardware.equals(productType) )
							 {
								 if(chargesDto==null)
								 {
									 String msg="chargesDto==null for longServiceProductId="+longServiceProductId;
									 Utility.LOG(true, true,msg );
									 continue;
								 }
							 }
							 else if(AppConstants.ProductType_Bandwidth.equals(productType)|| AppConstants.ProductType_Service.equals(productType))
							 {
								 if(serDto==null)
								 {
									 String msg="serDto==null for longServiceProductId="+longServiceProductId;
									 Utility.LOG(true, true,msg );
									 continue;
								 } 
							 }
							 
							 
							 //2.PUSHING DTO INFO TO FX
							 if(AppConstants.ProductType_Hardware.equals(productType))
							 {
								 objViewOrderDao.sendToFx(chargesDto);//TODO
							 }
							 else if(AppConstants.ProductType_Bandwidth.equals(productType) || AppConstants.ProductType_Service.equals(productType))
							 {
								 objViewOrderDao.sendToFx(serDto);	 
							 }
							 
							 int fx_status=0;
							 
							 //SAVING THE RETURNED VALUES//TODO 
							 try
							 {
								 if(AppConstants.ProductType_Hardware.equals(productType) )
								 {
									 fx_status=chargesDto.getReturnStatus();
									 objViewOrderDao.setFxOperationStatusForAccountLevelCharges(conn, chargesDto, fx_status);
								 }
								 else if(AppConstants.ProductType_Bandwidth.equals(productType)|| AppConstants.ProductType_Service.equals(productType))
								 {
									 fx_status=serDto.getReturnStatus();
									 objViewOrderDao.setFxOperationStatusForServiceLevelCharges(conn, serDto, fx_status);	 
								 }
								 
							 }
							 catch(Exception ex)
							 {
								 Utility.onEx_LOG_RET_NEW_EX(ex, "", "","", true,true);
								 if(fx_status==1)
								 {
									 //log for unupdation of fx details in IOES , as a error and warning
									 String msg="FX services and charges created but not updated in IOES for serviceproductid:"+longServiceProductId;
									 Utility.LOG(true,true,msg);
								 }
								 continue;
							 }
					//	}
					//}// end of if loop
			}	
		}catch (Exception e){
			 e.printStackTrace();
		 }  
		
		}
		//conn.commit();
	} catch (Exception ex) {
		//conn.rollback();
		logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
		ex.printStackTrace();
		Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
	}finally{
		try {
			
			DbConnection.freeConnection(conn);
		} catch (Exception e) {
			
		}
	} 
 
	
	ViewOrderDao objViewOrderDao1 =  new ViewOrderDao();
	arrLen=strBillingTrigger.split("@@").length;
	
	 for(int i=1;i<=arrLen;i++)
	 {	 
		 
		 try {
			 String str=strBillingTrigger.split("@@")[i-1];
			 String individual[]=str.split("~");
			 longServiceProductId=Long.parseLong(individual[0]);
			 
			 					 
	        str1=str1 + ',' + longServiceProductId ;
		 }catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		 
	 }
	 if(str1.length()>0)
		{
			str1=str1.substring(1);
		}
	
	int count= objViewOrderDao1.getfx_status(str1);
	objViewOrderDto1.setAccountdisconnected(count);
	 
	
	return objViewOrderDto1 ;
}*/


	//For Billing Trigger Solution Change
	public ViewOrderDto fnBillingTriggerSubmitForChangeOrders(long orderNo,String strBillingTrigger,String dataChanged,ViewOrderDto dto) throws Exception
	{
		int arrLen=0;
		long longServiceProductId=0;
		String locDate=null;
		String billingTriggerDate=null;
		String LocRecDate = null;
		String locNo=null;
		Connection conn = null;
		String Status= dto.getCheckbox_status();
		ArrayList<ViewOrderDto> loclist=new ArrayList();
		loclist=dto.getLocArrayList();
		ViewOrderDto LOCDataInserted=new ViewOrderDto();
		NewOrderDto status=new NewOrderDto();
		long empId=1;
		try 
		{
			Utility.SysOut(" fnBillingTriggerSubmitForChangeOrders of Inside Model of Billing Trigger Interface");
			
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			ViewOrderDto objLSIdto=null;
			
			conn= DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
		
			String csvServiceProductIds="";
			
			arrLen=strBillingTrigger.split("@@").length;
			String[] eachLine=strBillingTrigger.split("@@");
			
			ArrayList<ViewOrderDto> results=new ArrayList<ViewOrderDto>();
			
			if(Status.equals("checked"))
			{
					for(int i=1;i<=arrLen;i++)
					{	 
					 	 String str=eachLine[i-1];
					 String individual[]=str.split("~");
					 
					 longServiceProductId=Long.parseLong(individual[0]);
				 locNo=individual[1];
				 locDate=individual[2];
				 billingTriggerDate=individual[3];
				 LocRecDate = individual[4];
				 ViewOrderDto info=objViewOrderDao.getServiceProductInfo(conn,longServiceProductId);
				 String productType=info.getProductType();
				 String  billType = info.getProductBillType();
				 csvServiceProductIds=csvServiceProductIds+","+longServiceProductId;
				 if("".equals(locNo))
				 {
					 locNo=null;
				 }
				 if("".equals(locDate))
				 {
					 locDate=null;
				 }
				 if("".equals(LocRecDate))
				 {
					 LocRecDate=null;
				 }
				 status=objViewOrderDao.pushChargesInSecondaryTablesForChangeOrders(conn,longServiceProductId,locNo,locDate,billingTriggerDate,LocRecDate,orderNo,"",dto.getBtDoneBy());
				 /*if(AppConstants.Product_Bill_Type_Flat.equalsIgnoreCase(billType))
				 {
					 //add below code return type neworderdto and extra argument by Anil for CLEP
					 status=objViewOrderDao.pushChargesInSecondaryTablesForChangeOrders(conn,longServiceProductId,locNo,locDate,billingTriggerDate,LocRecDate,orderNo,empId,"");
						// }
				 }
				 
				 else if(AppConstants.Product_Bill_Type_Usage.equalsIgnoreCase(billType))
				 {
					
					  status=objViewOrderDao.pushComponentsInSecondaryTablesForChangeOrders(conn,longServiceProductId,locNo,locDate,billingTriggerDate,LocRecDate,orderNo);
				 
				 }*/
			
				 ViewOrderDto resultDto=new ViewOrderDto();
				 resultDto.setServiceProductID(""+longServiceProductId);
				 if("SUCCESS".equals(status.getBillingTriggerStatus()))
				 {
					 resultDto.setBillingTriggerStatus("20");
				 }
				 results.add(resultDto);
			 }  
				      dto.setBillingTriggerResult(results);
			}
			
			
			//003 start
			
			if(loclist.size()>0)
				
			{
				
				LOCDataInserted=objViewOrderDao.saveLocData(loclist,dto.getBtDoneBy(),orderNo);
				dto.setSuccessfullyLOCDataInserted(LOCDataInserted.getSuccessfullyLOCDataInserted());
				dto.setFailuetoInsertLocData(LOCDataInserted.getFailuetoInsertLocData());
				
			}
//			003 end
			//objViewOrderDao.setBTEndIfPossible(orderNo,null);

		} catch (Exception ex) {
			
			logger.error(ex.getMessage()+ " Exception occured in fnBillingTriggerSubmitForChangeOrders method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
			Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		}finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	 	return dto ;
	}



public ViewOrderDto fnUpdateLocdetails(long orderNo,String strBillingTrigger,String dataChanged,ViewOrderDto dto) throws Exception
{
	int arrLen=0;
	long longServiceProductId=0;
	String locDate=null;
	String valid=null;
	String billingTriggerDate=null;
	String locRecDate=null;
	String locNo=null;
	Connection conn = null;
	String Status= dto.getCheckbox_status();
	ArrayList<ViewOrderDto> loclist=new ArrayList();
	loclist=dto.getLocArrayList();
	
	try 
	{
		Utility.SysOut(" fnBillingTriggerSubmitForChangeOrders of Inside Model of Billing Trigger Interface");
		
		ViewOrderDao objViewOrderDao =  new ViewOrderDao();
		
		
		conn= DbConnection.getConnectionObject();
		conn.setAutoCommit(false);
	
		String csvServiceProductIds="";
		
		arrLen=strBillingTrigger.split("@@").length;
		String[] eachLine=strBillingTrigger.split("@@");
		
		ArrayList<ViewOrderDto> results=new ArrayList<ViewOrderDto>();
		
		if(Status.equals("checked"))
		{
				for(int i=1;i<=arrLen;i++)
				{	 
				 	 String str=eachLine[i-1];
				 String individual[]=str.split("~");
				 
			longServiceProductId=Long.parseLong(individual[0]);
			 locNo=individual[1];
			 locDate=individual[2];
			 billingTriggerDate=individual[3];
			 locRecDate=individual[4];
		     csvServiceProductIds=csvServiceProductIds+","+longServiceProductId;
			//start[005]
		      objViewOrderDao.fnUpdateLocdetails(conn,longServiceProductId,locNo,locDate,billingTriggerDate,locRecDate,orderNo,dto.getBtDoneBy());
		     	//End[005]
		
			
		 }  
			     
		}
		
		
		//003 start
		
		if(loclist.size()>0)
			
		{
//			start[005]
			objViewOrderDao.fnUpdateLocdetails_AUTO(conn,loclist,dto.getBtDoneBy(),orderNo);
//			End[005]
			
		}
		
		conn.commit();
		 valid="success";
		dto.setBiling_status(valid);
		
//		003 end
		
		//added by Ravneet 
		//objViewOrderDao.setBTEndIfPossible(orderNo,null);

	} catch (Exception ex) {
		conn.rollback();
		 valid="failure";
		dto.setBiling_status(valid);
	
	}finally{
		try {
			DbConnection.freeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
 	return dto ;
}
	
	
	
	
	
	
	public void setChargeSummaryData(ViewOrderFormBean formBean, long orderNo) throws Exception {
		logger.info("View Order Interface and setChargeSummaryData method of ViewOrderModel class have been called");
		String methodName="setChargeSummaryData", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		
		//Connection conn = null;
		try 
		{
			Utility.SysOut(" setChargeSummaryData of Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			
			//alChargeSummaryList=objViewOrderDao.getChargeSummaryList(orderNo);
			//conn= DbConnection.getConnectionObject();
			ArrayList<ChargeSummaryServiceDto> services = null;
			ArrayList<ChargeSummaryProductCatelog> productCatelogs = null;
			ArrayList<ChargeSummaryChargeSection> charges = null;
			ArrayList<ChargeSummaryComponentSection> components = null;
			
			services = objViewOrderDao.getChargeSummaryServices(null,orderNo);
			productCatelogs = objViewOrderDao.getChargeProductCatelogs(null,orderNo); // to get product data
			charges = objViewOrderDao.getChargeSummaryChargesSection(null,orderNo); // to get charge data
			components = objViewOrderDao.getChargeSummaryComponentSection(null,orderNo);  // to get component data
			
			HashMap<String,ArrayList<ChargeSummaryProductCatelog>> map_serviceId_prdCatelogs = new HashMap<String, ArrayList<ChargeSummaryProductCatelog>>();
			HashMap<String,ArrayList<ChargeSummaryChargeSection>> map_serviceproductId_prdCatelogs_charges = new HashMap<String, ArrayList<ChargeSummaryChargeSection>>();
			HashMap<String,ArrayList<ChargeSummaryComponentSection>> map_serviceproductid_prdCatelogs_com = new HashMap<String, ArrayList<ChargeSummaryComponentSection>>();
			
			for (ChargeSummaryProductCatelog catelog : productCatelogs) {
				String serviceId=catelog.getServiceId();
				ArrayList<ChargeSummaryProductCatelog> valueList = map_serviceId_prdCatelogs.get(serviceId);
				if(valueList == null)
				{
					valueList=new ArrayList<ChargeSummaryProductCatelog>();
					map_serviceId_prdCatelogs.put(serviceId, valueList);
				}
				valueList.add(catelog);
			}
			
			
			for (ChargeSummaryChargeSection productcharges : charges) {
				String serviceproductid_charges=productcharges.getSpid();
				ArrayList<ChargeSummaryChargeSection> valueList_charges = map_serviceproductId_prdCatelogs_charges.get(serviceproductid_charges);
				if(valueList_charges == null)
				{
					valueList_charges=new ArrayList<ChargeSummaryChargeSection>();
					map_serviceproductId_prdCatelogs_charges.put(serviceproductid_charges, valueList_charges);
				}
				valueList_charges.add(productcharges);
			}
			
			for (ChargeSummaryComponentSection productcomponents : components) {
				String serviceproductid_com=productcomponents.getServiceproductid();
				ArrayList<ChargeSummaryComponentSection> valueList_components =map_serviceproductid_prdCatelogs_com.get(serviceproductid_com);
				if(valueList_components == null)
				{
					valueList_components=new ArrayList<ChargeSummaryComponentSection>();
					map_serviceproductid_prdCatelogs_com.put(serviceproductid_com, valueList_components);
				}
				valueList_components.add(productcomponents);
			}
			
			formBean.setServices(services);
			//formBean.setProductCatelogs(productCatelogs);
			formBean.setMap_serviceId_prdCaelogs(map_serviceId_prdCatelogs);
			formBean.setMap_serviceproductId_prdCatelogs_charges(map_serviceproductId_prdCatelogs_charges);
			formBean.setMap_serviceproductId_prdCatelogs_components(map_serviceproductid_prdCatelogs_com);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in setChargeSummaryData method of "+ this.getClass().getSimpleName());
			ex.printStackTrace();
		} 
		finally{
			try {
				//DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
			}
		}
	}
	//method is used for populate region for view order
	// created by anil
	public ArrayList<NewOrderDto> getRegionList() throws Exception
	{
		ArrayList<NewOrderDto> listRegion= new ArrayList<NewOrderDto>();
		ViewOrderDao objDao = new ViewOrderDao();
		try
		{
			listRegion = objDao.getRegionList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listRegion;
	}
	//method is used for update main details through view order
	//created by anil
	public long updateMainViewOrder(ViewOrderFormBean viewOrderBean,String[] attributeVal,String[] attributeID)
	{
		ViewOrderDao objDao = new ViewOrderDao();
		long status=0;
		try
		{
			status = objDao.updateMainViewOrder(viewOrderBean,attributeVal,attributeID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return status;
	}
	private final int PRODUCT_FOUND = 10;
	private final int PRODUCT_NOT_FOUND = 30;
	private final int PRODUCT_DISCONNECTED = 20;
	private final int PRODUCT_NOT_DISCONNECTED = 40;
	private final int PRODUCT_FOUND_BUT_NOT_DISCONNECTED = 50;
	
	/*public  int disconnectCharges(String viewid,Timestamp chargeDisactiveDate) throws IOESException{
		int  saveStatus=0;
		Date inActiveDate = new Date(chargeDisactiveDate.getTime());
		//Date  inActiveDate = new Date();
		IOESKenanManager API = null;
		try
		{
			API=new IOESKenanManager();
			API.loginKenan();
			try
			{
				RcDto rcDto  = API.findProduct(viewid);
				saveStatus=rcDto.getSaveStatus();
				
				if(saveStatus==PRODUCT_FOUND){
					//int operationValue=API.disconnectProduct(viewid,inActiveDate,rcDto,null);
					  if(operationValue==PRODUCT_DISCONNECTED)
						  saveStatus = PRODUCT_DISCONNECTED;
					  else if(operationValue==PRODUCT_NOT_DISCONNECTED)
						  saveStatus = PRODUCT_FOUND_BUT_NOT_DISCONNECTED;
				}else if(saveStatus == PRODUCT_NOT_FOUND)
					saveStatus = PRODUCT_NOT_FOUND;
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		catch (Exception ex) {
			
			Utility.onEx_LOG_RET_NEW_EX(ex, "disconnectCharges", "disconnectCharges", null, true, true);
		}finally
		{
			try {
				 API.close();
			} catch (FxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return saveStatus;
	}*/
	
//003 start	
	public ArrayList<ViewOrderDto> getFilledTemplateforBillingLineSectionBulkUpload()
	{
		logger.info("getFilledTemplateforBulkUpload method of ViewOrderModel class have been called");
		ArrayList<ViewOrderDto> alLineDetails=null;
		ViewOrderDto objdto=null;
		BillingTriggerValidation validate=new BillingTriggerValidation();
		try 
		{
			Utility.SysOut("getFilledTemplateforBulkUploadof Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			alLineDetails=objViewOrderDao.getLineDetailsforBTBulkUpload();
			 for(int i=0;i<alLineDetails.size();i++)
				{
				 objdto=alLineDetails.get(i);
	            	//compute properites for this line item which is used for
	            	//	a.communicating info to presentation for fields allowed for edit
	            	validate =new BillingTriggerValidation(); 
	            	validate.setSourceData(objdto,null,null/*eventids*/);
	            	validate.computeProperties_FORBULKUPLOAD();
	            	objdto.setBillingTriggerAllowDenyLogic(validate);
				}
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		
		return alLineDetails;
		
	}
	
//003 end	
	
//004 start	
	public ArrayList<ViewOrderDto> getFilledTemplateforBillingChargeSectionBulkUpload()
	{
		logger.info("getFilledTemplateforBulkUpload method of ViewOrderModel class have been called");
		ArrayList<ViewOrderDto> alChanrgeDetails=null;
		try 
		{
			Utility.SysOut(" getFilledTemplateforBulkUploadof Inside Model of View Order Interface");
			ViewOrderDao objViewOrderDao =  new ViewOrderDao();
			alChanrgeDetails=objViewOrderDao.getChargeDetailsforBulkUpload();
//			002 start
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		
		return alChanrgeDetails;
		
	}
	
	public Boolean isRolePassedEscalation(String orderNo, String role){
		return new NewOrderDaoExt().isRolePassedEscalation(orderNo, role);
	}
	public String getDelayReasonUsers(){
		return new NewOrderDaoExt().getDelayReasonUsers();
	}
	public String getPartialInitiatorRoles(){
		return new NewOrderDaoExt().getPartialInitiatorRoles();
	}
	public boolean isClepOrder(long orderNo){
		return new NewOrderDaoExt().isClepOrder(orderNo);
	}
//004 end	
}
