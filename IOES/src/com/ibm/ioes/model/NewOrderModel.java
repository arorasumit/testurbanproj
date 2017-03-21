package com.ibm.ioes.model;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Rohit verma	3-Mar-11	00-05422		Created By And Modified by for each Transaction
//[002]  Rakshika		7-Mar-11	00-05422		Attachement Download
//[003]	 Vishwa			17-Mar-11	00-05422		Populate Pincode based City and State
//[004]	 Manisha	    13-Feb-13	00-05422		Send Mail to Act Mgr which ORDERS ARE PENDING TO COMPLETE
//[HYPR22032013001]    Vijay    30-March-2013          Billing Work Queue Report.
//[005]	 Rohit Verma	1-Mar-13	00-08440		Process Line Item Cancelation request
//[006] Vipin Saharia 26-Dec-2013 Added getSCMProgressStatus method for SCM Repush Button
//[007] Vipin Saharia 25-Feb-2014 Added getThirdPartyState method for getting Third Party State (Active or InActive)
//[0010]  Gunjan Singla    24-june-14    CSR_20140526_R1_020159   Order Cancellation Post Publish
//[011]	VIPIN SAHARIA 17-07-2014 Added methods to get charge details for Service - Sales charges validation for DC hardware products.
//[012] Gunjan Singla  7-Jan-15     20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection
//[013]	 Raveendra Kumar	25-Mar-15	20150225-R1-021110     Order transfer rights required on GUI for AM Role
//[014]	 Anil Kumar			14-May-15	20141219_R2_020936     Fetch Standard reason, Insert Standard reason and Update Standard reason
//[015] VIPIN SAHARIA 12-May-2015 20150403-R2-021204 Project Satyapan Adding ISP tagging fields to header section (Moved transaction call from dao to model to make changes in same transaction)
//[016] Raveendra 06-july-2015 20141219-R2-020936-Billing Efficiency Program_drop2  Auto Billing Trigger
//[017] Priya Gupta 05-Aug-2015 Commented code for finding distinct orders and added condition for enable/disable the checkbox in draft disconnection work queue
//[018] RAHUL TANDON 3-Sept-2015 CBR_20150603-R2-021385-Charge End Date and Short Billing : Alert on order validate if order has charges which have charges which can be re-disconnect
//[019] Gunjan Singla	24-Nov-2015		M2M:validations on validate order button 
//[020] Priya Gupta	    31-MAR-2016	 Order approval twice in PD orders.
//[021] Gunjan Singla   5-Apr-2016		same tax rate for foreign legal entity
//[023] Priya Gupta  CSR-20160301-XX-022139 -Auto billing for RR orders & other Enhancements
//[024] Gunjan Singla 20160219-XX-022117   CBR-ANG bandwidth correction in iB2B and M6
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.fx.dto.ChargesDto;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.forms.ChannelPartnerDto;
import com.ibm.ioes.forms.ContactDTO;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.forms.Entity;
import com.ibm.ioes.forms.FieldAttibuteDTO;
import com.ibm.ioes.forms.FieldEnginnerDto;
import com.ibm.ioes.forms.FileAttachmentDto;
import com.ibm.ioes.forms.LSICancellationDto;
import com.ibm.ioes.forms.LineItemDTO;
import com.ibm.ioes.forms.MigrationOrdersDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OrderForPendingWithAMDto;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.forms.ParallelUpgradeValidationDto;
import com.ibm.ioes.forms.PoDetailsDTO;
import com.ibm.ioes.forms.ProductCatelogDTO;
import com.ibm.ioes.forms.ServiceLineDTO;
import com.ibm.ioes.model.NewOrderModel.BillingEfficiencyDto;
import com.ibm.ioes.newdesign.dto.OrderDto;
import com.ibm.ioes.newdesign.dto.ServiceDto;
import com.ibm.ioes.newdesign.dto.StandardReason;
import com.ibm.ioes.utilities.AjaxValidation;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.ExcelCreator;
import com.ibm.ioes.utilities.IB2BMail;
import com.ibm.ioes.utilities.IB2BMailDto;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.SendMail;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.forms.LineItemValueDTO;
import com.ibm.ioes.forms.PRDetailsDto;
import com.ibm.ioes.forms.SCMDto;



public class NewOrderModel 
{
	//Shubhranshu
	private static final String sqlvalidateAsParallelUpgrade = " SELECT count(1) as  countflag FROM ioe.TPOSERVICE_PARALLEL_UPGRADE pu "+
                                                                                                        " inner join ioe.TPOSERVICEMASTER sm on pu.SERVICEID=sm.SERVICEID "+ 		
                                                                                                        " WHERE  pu.PARALLEL_UPGRADE_LSI=? " +
                                                                                                        "  and sm.IS_SERVICE_INACTIVE=0 and " +
                                                                                                        "  coalesce (sm.M6_FX_PROGRESS_STATUS,' ')not like '%CANCELLED%' and sm.ISPUBLISHED=1 " ;
	//Shubhranshu

	public ArrayList<OrderHeaderDTO> displayAccountDetails(NewOrderDto objDto) throws Exception
	{
		ArrayList<OrderHeaderDTO> listAccountDetails= new ArrayList<OrderHeaderDTO>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listAccountDetails = objDao.getAccountDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAccountDetails;
	}
	public NewOrderDto DeleteDownLoadFile(String str,String empId)
	{
		NewOrderDto listAccountDetails= new NewOrderDto();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listAccountDetails = objDao.DeleteDownLoadFile(str,empId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAccountDetails;
	}
	
	
  public ArrayList<NewOrderDto> viewOrderList(NewOrderDto objDto, long empID)
	throws Exception {
		ArrayList<NewOrderDto> orderList = new ArrayList<NewOrderDto>();
		ArrayList<NewOrderDto> orderList_new = new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto dto = null;
		HashSet<String> hs = new HashSet<String>();
		try {
			orderList = objDao.viewOrderList(objDto,empID);

			for (int i = 0; i < orderList.size(); i++) {

				dto = orderList.get(i);
				String orderno = dto.getSearchCRMOrder();
				if ((hs.contains(orderno)) == false) {

					hs.add(orderno);
					orderList_new.add(dto);

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return orderList_new;
}	
	
// for change
  
 public ArrayList<NewOrderDto> viewOrderList_change(NewOrderDto objDto)
	throws Exception {
              ArrayList<NewOrderDto> orderList = new ArrayList<NewOrderDto>();
              NewOrderDao objDao = new NewOrderDao();
              NewOrderDto dto=null;
              HashSet<String> hs=new HashSet<String>();
              ArrayList<NewOrderDto> orderList_change = new ArrayList<NewOrderDto>();
                         try {
                      	   orderList = objDao.viewOrderList_change(objDto);
                      	   
                      	   for(int i=0;i<orderList.size();i++){
                         	  
                         	  dto=orderList.get(i); 
                         	  String orderno=dto.getSearchCRMOrder();
                         	  if((hs.contains(orderno))==false){
                         		   
                         		  hs.add(orderno);
                         		 orderList_change.add(dto);
                         		  
                         		  
                         	  }
                         	  
                         	  
                           }
                      	   
                      	   
                                        } catch (Exception ex) {
	                                                ex.printStackTrace();
	                                      throw new Exception("SQL Exception : " + ex.getMessage(), ex);
                                         }
                                             return orderList_change;
}	
  
	
	
	public ArrayList<FieldAttibuteDTO> displayMainDetails(NewOrderDto objDto) throws Exception
	{
		ArrayList<FieldAttibuteDTO> listMainDetails= null;
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listMainDetails = objDao.getMainDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listMainDetails;
	}
	
	public ArrayList<NewOrderDto> displayCurrencyDetails(PagingDto objDto) throws Exception
	{
		ArrayList<NewOrderDto> listCurrencyDetails= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listCurrencyDetails = objDao.getCurrencyDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listCurrencyDetails;
	}

	public int getMaxOrderNo(NewOrderDto objDto) throws Exception
	{
		int maxOrderNo=0;
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			maxOrderNo = objDao.getMaxOrderNo(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return maxOrderNo;
	}
	
	public ArrayList<NewOrderDto> displaySourceDetails(NewOrderDto objDto) throws Exception
	{
		ArrayList<NewOrderDto> listSourceDetails= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listSourceDetails = objDao.getSourceDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSourceDetails;
	}

	
	public ArrayList<NewOrderDto> displayLogicalSINo(NewOrderDto objDto) throws Exception
	{
		ArrayList<NewOrderDto> listLogicalSINo= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			//listLogicalSINo = objDao.getLogiSINo();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listLogicalSINo;
	}
	//[001] Start
	//public long insertUpdateMain(NewOrderBean newOrderBean,int updateType,String[] attributeVal,String[] attributeID)
	public long insertUpdateMain(NewOrderBean newOrderBean,int updateType,String[] attributeVal,String[] attributeID,long empID)
	//[001] End
	{
		NewOrderDao objDao = new NewOrderDao();
		long status=0;
		try
		{
			//[001] Start
			//status = objDao.insertUpdateMain(newOrderBean,updateType,attributeVal,attributeID);
			status = objDao.insertUpdateMain(newOrderBean,updateType,attributeVal,attributeID,empID);
			//[001] End
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return status;
	}
	public ArrayList<OrderHeaderDTO> getAccountDetails (NewOrderDto objDto,long orderNo) throws Exception
	{
		ArrayList<OrderHeaderDTO> listAccountDetails= new ArrayList<OrderHeaderDTO>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listAccountDetails = objDao.getAccountDetails(objDto,orderNo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAccountDetails;
	}
	
	/*public ArrayList<NewOrderDto> fetechZoneList (NewOrderDto objDto ) throws Exception
	{
		ArrayList<NewOrderDto> zonelist= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			zonelist = objDao.fetechZoneList(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return zonelist;
	}*/
	public ArrayList<FieldAttibuteDTO> getMainDetails (NewOrderDto objDto,long orderNo) throws Exception
	{
		ArrayList<FieldAttibuteDTO> listAccountDetails= new ArrayList<FieldAttibuteDTO>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listAccountDetails = objDao.getMainDetailsWithAttributes(objDto,orderNo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAccountDetails;
	}
	//[001] Start
	public int insertContact(NewOrderBean newOrderBean,Map<String,String[]> addressMap,Map<String,String[]> contactMap,int count,int updateFlag, long empID)
	//[001] End
	{
		NewOrderDao objDao = new NewOrderDao();
		Connection connection =null;
		System.out.println("========= insertcontact============");
		int status=0,ispFieldsUpdateStatus=0,channelPartnerFieldsUpdateStatus=0;
		try
		{
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			//[001] Start
			//status = objDao.insertContact(newOrderBean,addressMap,contactMap,count,updateFlag);
			status = objDao.insertContact(connection,newOrderBean,addressMap,contactMap,count,updateFlag,empID);
			if(status==1)
				ispFieldsUpdateStatus=objDao.updateIspTaggingFields(connection,newOrderBean);
				channelPartnerFieldsUpdateStatus=objDao.updateChannelPartnerTaggingFields(connection,newOrderBean);
			//[001] End
			if(status==1 && ispFieldsUpdateStatus>=0 && channelPartnerFieldsUpdateStatus>=0)
				connection.commit();
			else
				connection.rollback();
		}
		catch(Exception ex)
		{
			try {
				connection.rollback();
			} catch (Exception e) {
				Utility.LOG(true, true, ex, "in insertContact method of NewOrderModel");
				//e.printStackTrace();
			}
			ex.printStackTrace();	
		}finally{
			try{
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e){
				Utility.LOG(true, true,e,"in insertContact method of NewOrderModel finally block");
			}
		}
		return status;
	}
	public ArrayList<ContactDTO> getContactDetails (long orderNo) throws Exception
	{
		ArrayList<ContactDTO> listContactDetails= null;
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listContactDetails = objDao.getContactDetail(orderNo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listContactDetails;
	}
	public ArrayList<NewOrderDto> getAddressDetails (long orderNo) throws Exception
	{
		ArrayList<NewOrderDto> listAddressDetails= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listAddressDetails = objDao.getAddressDetail(orderNo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listAddressDetails;
	}
	
	/*public Hashtable fetchServiceType() throws Exception
	{
		Hashtable listServiceType = new Hashtable();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listServiceType = objDao.getServiceType();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listServiceType;
	}*/
	
	public ArrayList<ContactDTO> getContactTypeDetail () throws Exception
	{
		ArrayList<ContactDTO> listContactTypes= null;
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objDto = new NewOrderDto();
		try
		{
			listContactTypes = objDao.getContactTypeDetail(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listContactTypes;
	}
	public ArrayList<ContactDTO> getSalutationList () throws Exception
	{
		ArrayList<ContactDTO> listSalutation= null;
		NewOrderDao objDao = new NewOrderDao();
	//	NewOrderDto objDto = new NewOrderDto();
		ContactDTO objDto= new  ContactDTO();
		try
		{
			listSalutation = objDao.fetechSalutationList(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSalutation;
	}
	public ArrayList<NewOrderDto> getCountryList (NewOrderDto frmDto) throws Exception
	{
		ArrayList<NewOrderDto> listCountry= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listCountry = objDao.fetechCountryList(frmDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listCountry;
	}	
	public ArrayList<NewOrderDto> getStateList (NewOrderDto frmDto) throws Exception
	{
		ArrayList<NewOrderDto> listState= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listState = objDao.fetechStateList(frmDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listState;
	}	
	public ArrayList<NewOrderDto> getCityList () throws Exception
	{
		ArrayList<NewOrderDto> listCity= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listCity = objDao.fetechCityList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listCity;
	}	

	//[003] Start
	public ArrayList<NewOrderDto> getPinList (NewOrderDto frmDto) throws Exception
	{
		ArrayList<NewOrderDto> listPin= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listPin = objDao.fetchPinList(frmDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listPin;
	}	
	//[003] End
	
	public ArrayList<ProductCatelogDTO> getServiceType(ServiceLineDTO objDto) throws Exception
	{
		ArrayList<ProductCatelogDTO> listServiceType= null;
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listServiceType = objDao.getServiceTypeDetails(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listServiceType;
	}	
	
	/**
	 * Method to retreive the PO data from DB
	 * @param poNumber
	 * @return
	 * @throws Exception
	 */
	public ArrayList<PoDetailsDTO> getPODetails (long poNumber) throws Exception
	{
		ArrayList<PoDetailsDTO> listPODetails= null;
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listPODetails = objDao.getPODetail(poNumber);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listPODetails;
	}
	/**
	 * Method to insert the PO Detail tab data
	 * @param newOrderBean
	 * @param poMap
	 * @param count
	 * @param updateFlag
	 * @return
	 */
	//[001] Start
	public int insertPO(NewOrderBean newOrderBean,Map<String,String[]> poMap,int count,int updateFlag,long empID)
	//[001] End
	{
		NewOrderDao objDao = new NewOrderDao();
		Connection connection=null;
		int status=0,ispFieldsUpdateStatus=0,channelPartnerFieldsUpdateStatus=0;
		try
		{
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			//[001] Start
			//status = objDao.insertPO(newOrderBean,poMap,count,updateFlag);
			status = objDao.insertPO(connection,newOrderBean,poMap,count,updateFlag,empID);
			if(status==1){
				ispFieldsUpdateStatus=objDao.updateIspTaggingFields(connection,newOrderBean);
				channelPartnerFieldsUpdateStatus=objDao.updateChannelPartnerTaggingFields(connection,newOrderBean);
            }
			
			if(status==1 && (ispFieldsUpdateStatus>=0 || channelPartnerFieldsUpdateStatus>=0))
				connection.commit();
			else
				connection.rollback();
			//[001] End
		}
		catch(Exception ex)
		{
			try {
				connection.rollback();
			} catch (Exception e) {
				Utility.LOG(true, true, ex, "in insertPO method of NewOrderModel");
				//e.printStackTrace();
			}
			ex.printStackTrace();	
		}finally{
			try{
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e){
				Utility.LOG(true, true,e,"in insertPO method of NewOrderModel finally block");
			}
		}
		return status;
	}
	
	/**
	 * Method to retreive entity data from DB
	 * @return
	 */
	public ArrayList<Entity> getEntityList(Entity entityData){
		NewOrderDao objDao = new NewOrderDao();
		ArrayList list = new ArrayList();
		try
		{
			list =  objDao.getEntityList(entityData);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return list;
	}
	/**
	 * Method to retreive TProductAttDetail data from DB
	 * @return
	 */
	public ArrayList<ProductCatelogDTO> getTProductAttDetail(long serviceNo, long serviceTypeId){
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> tProductAttDetail= null;
		try
		{
			tProductAttDetail =  objDao.getTProductAttDetail(serviceNo,serviceTypeId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return tProductAttDetail;
	}
	/*commented by rohit to see if its required anywhere
	public int insertTproductATTDetail(NewOrderDto objDto){
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> tProductAttDetail= new ArrayList<NewOrderDto>();
		int status =0;
		try
		{
		  status =  objDao.insertTproductATTDetail(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return status;
	}*/
	
//	 This Method get LogicalSiNo from Database
	public int getLogiSINo(){
		NewOrderDao objDao = new NewOrderDao();
		int status =0;
		try
		{
		  status =  objDao.getLogiSINo();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return status;
	}	
public ArrayList getIncompleteOrderList(){
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> incompleteOrderList= new ArrayList<NewOrderDto>();
		try
		{
			incompleteOrderList =  objDao.getIncompleteOrderList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return incompleteOrderList;
	}	
public ArrayList getIncompleteChangeOrderList(){
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> incompleteOrderList= new ArrayList<NewOrderDto>();
	try
	{
		incompleteOrderList =  objDao.getIncompleteChangeOrderList();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return incompleteOrderList;
}	
/*public ArrayList<NewOrderDto> getLogicalSINumber(long accountID,String logicalSiNumber) throws Exception
{
	ArrayList<NewOrderDto> listLogicalSINumber= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listLogicalSINumber = objDao.getLogicalSINumber(accountID,logicalSiNumber);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listLogicalSINumber;
}*/
public void downloadTemplateExcelForPrdCatelog(NewOrderBean formBean) throws Exception {

	
	
	Connection connection =null;
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		connection=DbConnection.getConnectionObject();
		Set<String> allowedSectionsset = objDao.fetchAllowedSections(connection,formBean);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
        HSSFFont boldFont = wb.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerCellStyle.setFont(boldFont);

        ExcelCreator creator=new ExcelCreator();       
		if(allowedSectionsset.contains(AppConstants.prdCatelog_Service_Summary))
		{
			HSSFSheet sheet = wb.createSheet(Messages.getMessageValue("prdCatelog.excelDownload."+AppConstants.prdCatelog_Service_Summary));
			creator.makePrdCatlogTemplateServiceSummarySheet(connection,formBean.getChk_spId(),sheet,headerCellStyle,AppConstants.prdCatelog_Billing_Information_Address,formBean.getHdnSelectedServiceDetailId(),0);
			
		}
		if(allowedSectionsset.contains(AppConstants.prdCatelog_Billing_Information_Address))
		{
			HSSFSheet sheet = wb.createSheet(Messages.getMessageValue("prdCatelog.excelDownload."+AppConstants.prdCatelog_Billing_Information_Address));
			creator.makePrdCatlogTemplateSheet(connection,formBean.getChk_spId(),sheet,headerCellStyle,AppConstants.prdCatelog_Billing_Information_Address,"",1);
		}
		if(allowedSectionsset.contains(AppConstants.prdCatelog_Hardware_Info))
		{
			HSSFSheet sheet = wb.createSheet(Messages.getMessageValue("prdCatelog.excelDownload."+AppConstants.prdCatelog_Hardware_Info));
			creator.makePrdCatlogTemplateSheet(connection,formBean.getChk_spId(),sheet,headerCellStyle,AppConstants.prdCatelog_Hardware_Info,"",2);
		}
		if(allowedSectionsset.contains(AppConstants.prdCatelog_ServiceLocation_Details))
		{
			HSSFSheet sheet = wb.createSheet(Messages.getMessageValue("prdCatelog.excelDownload."+AppConstants.prdCatelog_ServiceLocation_Details));
			creator.makePrdCatlogTemplateSheet(connection,formBean.getChk_spId(),sheet,headerCellStyle,AppConstants.prdCatelog_ServiceLocation_Details,"",3);
		}
		if(allowedSectionsset.contains(AppConstants.prdCatelog_Charges_Details))
		{
			HSSFSheet sheet = wb.createSheet(Messages.getMessageValue("prdCatelog.excelDownload."+AppConstants.prdCatelog_Charges_Details));
			creator.makePrdCatlogTemplateSheet(connection,formBean.getChk_spId(),sheet,headerCellStyle,AppConstants.prdCatelog_Charges_Details,"",4);
		}
		HSSFSheet sheet = wb.createSheet(Messages.getMessageValue("prdCatelog.excelDownload.MataData_Sheet"));
		creator.makePrdCatlogTemplateSheet(connection,formBean.getChk_spId(),sheet,headerCellStyle,"MetaData_Sheet",formBean.getHdnSelectedServiceDetailId(),5);
       
		
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerCellStyle.setFont(boldFont);
        
        formBean.setProductCatelogTemplateWorkbook(wb);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		
		throw new Exception("Exception occured in getMasterExcel method of "
				+ this.getClass().getSimpleName());
	}
	finally{
		try {
			DbConnection.freeConnection(connection);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new Exception("Exception occured in getMasterExcel method of "
					+ this.getClass().getSimpleName());
		}
		
	}
}
/**
 * Method to get all data for Masters Download
 * @param productID
 * @return
 */
public HSSFWorkbook downloadMasters(long productID){
	
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	HSSFWorkbook wb = new HSSFWorkbook();
	try
	{
		wb = objDao.downloadMasters(productID);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return wb;
}


public int saveUploadedFileToTemporaryTable(ArrayList<Object[][]>excelData,int productID,String fileName){
	
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int retStatus=0;
	try
	{
		retStatus = objDao.saveUploadedFileToTemporaryTable(excelData,productID,fileName);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return retStatus;
}
public ResultSet fetchServiceSummaryHeaderForExcel (int productID){
	ResultSet set = null;
	NewOrderDaoExt dao = new NewOrderDaoExt();
	try {
		set = dao.fetchServiceSummaryHeaderForExcel(productID);
	}catch(Exception ex){
		ex.printStackTrace();	

	}
	return set;
}
public ArrayList<NewOrderDto> getChangeType() throws Exception
{
	ArrayList<NewOrderDto> listChangeType= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listChangeType = objDao.getChangeType();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listChangeType;
}

public ArrayList<NewOrderDto> getChangeTypeForParallelUpgradeReport() throws Exception
{
	ArrayList<NewOrderDto> listChangeType= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listChangeType = objDao.getChangeTypeForParallelUpgradeReport();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listChangeType;
}
/*public ArrayList<NewOrderDto> getNewType() throws Exception
public ArrayList<NewOrderDto> getSourceName()throws Exception
{
	ArrayList<NewOrderDto> listSourceName= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listSourceName = objDao.getSourceName();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listSourceName;
}


public ArrayList<NewOrderDto> getCurrency()throws Exception
{
	ArrayList<NewOrderDto> listCurrency= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listCurrency = objDao.getCurrency();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listCurrency;
}


	ArrayList<NewOrderDto> listNewType= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listNewType = objDao.getNewType();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listNewType;
}*/

public ArrayList<OrderHeaderDTO> getProjectManagerNameList(long accId) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<OrderHeaderDTO> listProjectManagerNameList= null;
	try 
	{
		
		listProjectManagerNameList = objDao.getProjectManagerNameList(accId);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listProjectManagerNameList;

}

public ArrayList<NewOrderDto> getProjectManagerNameList() throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> listProjectManagerNameList= new ArrayList<NewOrderDto>();
	try 
	{
		
		listProjectManagerNameList = objDao.getProjectManagerNameList();
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listProjectManagerNameList;

}

public ArrayList<OrderHeaderDTO> getRegionList() throws Exception
{
	ArrayList<OrderHeaderDTO> listRegion= null;
	NewOrderDao objDao = new NewOrderDao();
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

public ArrayList<NewOrderDto> getServiceData(NewOrderDto objDto) throws Exception
{
	ArrayList<NewOrderDto> serviceData= new ArrayList<NewOrderDto>();
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	try
	{
		serviceData = objDao.getServiceData(objDto);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return serviceData;
}	
public  UserInfoDto getLoggedUserDetails(String userid , int roleid) throws Exception
{
	//SessionObjectsDto objDto = new SessionObjectsDto();
	NewOrderDao objDao=new NewOrderDao();
	UserInfoDto objUserInfo = new UserInfoDto(); 
	try
	{
		objUserInfo = objDao.getLoggedUserDetails(userid,roleid);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return objUserInfo;
	//return objDto;
}
public  boolean isUserValid(String userid) throws Exception
{
	//SessionObjectsDto objDto = new SessionObjectsDto();
	NewOrderDao objDao=new NewOrderDao();
	boolean isValidUser = false;
	try
	{
		if(objDao.isUserValid(userid)>0)
			isValidUser = true ;
		else
			isValidUser = false ;
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return isValidUser;
	//return objDto;
}

public ArrayList<OrderHeaderDTO> getChangeOrderSubTypeAttached(String orderno) throws Exception
{
	ArrayList<OrderHeaderDTO> listSourceDetails= null;
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listSourceDetails = objDao.getChangeOrderSubTypeAttached(orderno,null);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listSourceDetails;
}
/*Function		:insertFileUpload
 * return type	:long
 * @Author		:Anil Kumar
 * Date			:17-feb-11
 * Purpose		:To inserting file data to the database..
 * */
public long insertFileUpload(FileAttachmentDto fileDto)
{
	long finalStatus=0;
	NewOrderDao objDao = new NewOrderDao();		
	try
	{
	  finalStatus =  objDao.insertFileUpload(fileDto);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return finalStatus;	
}
/*[002] Start*/
public ArrayList<FileAttachmentDto>  getUploadedFileName(FileAttachmentDto fileDto,int docType)
{
	ArrayList<FileAttachmentDto> listFileAttached= new ArrayList<FileAttachmentDto>();
	NewOrderDao objDao = new NewOrderDao();		
	try
	{
		listFileAttached =  objDao.getUploadedFileName(fileDto,docType);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listFileAttached;	
}

public FileAttachmentDto getDownloadedFile(FileAttachmentDto fileDto)
{
	FileAttachmentDto downloadedFile= new FileAttachmentDto();
	NewOrderDao objDao = new NewOrderDao();		
	try
	{
		downloadedFile =  objDao.getUploadedFile(fileDto);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return downloadedFile;	
}
//[002] End//

public ArrayList<OrderHeaderDTO> getSourceName()throws Exception
{
	ArrayList<OrderHeaderDTO> listSourceName= null;
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listSourceName = objDao.getSourceName();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listSourceName;
}


public ArrayList<OrderHeaderDTO> getCurrency()throws Exception
{
	ArrayList<OrderHeaderDTO> listCurrency= null;
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listCurrency = objDao.getCurrency();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listCurrency;
}


//Meenakhi : account from session start
public OrderHeaderDTO getAccountData (String accountId) throws Exception
{
	OrderHeaderDTO accountDetails= new OrderHeaderDTO();
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	try
	{
		accountDetails = objDao.getAccountData(accountId);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return accountDetails;
}
//Meenakhi : account from session end

//lawkush Start 
/*
public ArrayList<NewOrderDto> getGamList(PagingDto objDto) throws Exception
{
	ArrayList<NewOrderDto> listGam= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listGam = objDao.getGamList(objDto);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listGam;
}	
*/
//lawkush End

//lawkush Start 

public ArrayList<NewOrderDto> getGamOrderAttached() throws Exception
{
	ArrayList<NewOrderDto> listGam= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listGam = objDao.getGamOrderAttached();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listGam;
}	

//lawkush End

public ArrayList<MigrationOrdersDto> fetchMigrationOrders(long orderno,String serviceType) throws Exception
{
	ArrayList<MigrationOrdersDto> listMigrationOrders= new ArrayList<MigrationOrdersDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		if(serviceType .equalsIgnoreCase( "dccolo"))
		{
			listMigrationOrders = objDao.fetchMigrationOrders_DCColo(orderno);
		}
		else
		{
			listMigrationOrders = objDao.fetchMigrationOrders_Teleport(orderno);
		}
		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listMigrationOrders;
}

public int sendAutoRenewalMail()  throws Exception {
	int status=0;
	Connection conn= null;
	
	try
	{	
		conn = DbConnection.getConnectionObject();
		SendMail sendMail=new SendMail();
		String toList[]=null;
		String ccList[]=null;
		String bcc[]=null;
		String from=null;
		NewOrderDao daoObj=new NewOrderDao(); 
		ArrayList<NewOrderDto> actmgrdto=new ArrayList<NewOrderDto>();
		ArrayList<NewOrderDto> chargeDetailsdto=new ArrayList<NewOrderDto>();
		actmgrdto=daoObj.getActMgrIdForAutoRenewalMail(conn);
		for (NewOrderDto actmgridList : actmgrdto) {
			
			try{
				
					chargeDetailsdto=daoObj.getChargeDetailsforAutoRenewalMail(conn,(actmgridList.getActmgrid()));
				    toList=(actmgridList.getActmgremailid());	
					StringBuffer eBody=new StringBuffer();
					String eSubject=Messages.getMessageValue("autorenewal.mail.subject");
					eBody.append("<HTML><BODY>");
					eBody.append("Dear "+actmgridList.getActmgrname()+",<BR><BR>");
					eBody.append("Following charges of orders in IB2B are scheduled for auto-closing after 60 days in which you are assigned as Account Manager :<BR><BR>");
					eBody.append("<TABLE border=\"1\">");
					
							eBody.append("<TR font-size:16px bgcolor=#F7F7E7>");
			                eBody.append("<TH>");
			                eBody.append("Account No");
			                eBody.append("</TH>");		                    
			                eBody.append("<TH>");
			                eBody.append("Account Name");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Logical SI No");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Service No");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Service Name");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Line Item No");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Line Item Name");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Charge Id");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Charge Name");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Charge Start Date");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Charge End Date");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Charge Amount");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Cust PO No");
			                eBody.append("</TH>");	
			                eBody.append("<TH>");
			                eBody.append("Cust PO Date");
			                eBody.append("</TH>");	
			                eBody.append("</TR>");      
		        for (NewOrderDto chargeDetailsList : chargeDetailsdto) { 
			       			eBody.append("<TR font-size: 15px>");
			       			eBody.append("<TD>");
				        	eBody.append(chargeDetailsList.getAccountID());
				        	eBody.append("&nbsp");
					        eBody.append("</TD>");		                    
					        eBody.append("<TD>");
				        	eBody.append(com.ibm.ioes.utilities.Utility.IfNullToBlank(chargeDetailsList.getAccountName()));
				        	eBody.append("&nbsp");
					        eBody.append("</TD>");	
					        eBody.append("<TD>");
				        	eBody.append(chargeDetailsList.getLogicalSINumber());
				        	eBody.append("&nbsp");
					        eBody.append("</TD>");	
					        eBody.append("<TD>");
					        eBody.append(chargeDetailsList.getServiceId());
					        eBody.append("&nbsp");
					        eBody.append("</TD>");	
					        eBody.append("<TD>");
				        	eBody.append(com.ibm.ioes.utilities.Utility.IfNullToBlank(chargeDetailsList.getServiceName()));
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(chargeDetailsList.getLineItemId());
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(com.ibm.ioes.utilities.Utility.IfNullToBlank(chargeDetailsList.getLineItemName()));
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(chargeDetailsList.getChargeInfoID());
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(com.ibm.ioes.utilities.Utility.IfNullToBlank(chargeDetailsList.getChargeName()));
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(com.ibm.ioes.utilities.Utility.IfNullToBlank(chargeDetailsList.getStartDate()));
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(com.ibm.ioes.utilities.Utility.IfNullToBlank(chargeDetailsList.getEndDate()));
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(chargeDetailsList.getChargeAmount());
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(com.ibm.ioes.utilities.Utility.IfNullToBlank(chargeDetailsList.getCustPoDetailNo()));
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("<TD>");
				        	eBody.append(com.ibm.ioes.utilities.Utility.IfNullToBlank(chargeDetailsList.getCustPoDate()));
				        	eBody.append("&nbsp");
				        	eBody.append("</TD>");	
				        	eBody.append("</TR>");      
		        		} 
	                    eBody.append("</TABLE>");
						eBody.append("</BODY></HTML>");
						
						/*String mailDetails=" Going to Send AutoRenewal Mail for Account Manager : \n EmailId : "+ toList[0]
         					                   + "\n	EBODY :"+eBody;                                                            		
      					com.ibm.ioes.utilities.Utility.LOG(mailDetails);*/
						
 						int retStatus=sendMail.postMailWithAttachment(toList, ccList, bcc, eSubject, eBody.toString(), from,null);
				
					    if(retStatus==1)
						{
							String success= " AutoRenewal Mail Sent Successfully to Account Manager: "+ toList;
							com.ibm.ioes.utilities.Utility.LOG(success);
						}
					    else
					    {
							String failure= " AutoRenewal Mail Sending Failed  to Account Manager : \n EmailId : "+ toList[0]
							                   + "\n	EBODY :"+eBody;                                                            		
							com.ibm.ioes.utilities.Utility.LOG(failure);
						}
		}// try               
		catch (Exception ex) {
		
			String exc= " Exception occured in Generating Mail to Account Manager: "+ toList;
			com.ibm.ioes.utilities.Utility.LOG(exc);
			com.ibm.ioes.utilities.Utility.LOG(ex);
			
		}		

		}// for				

	} // try 

	catch (Exception ex) {
		String exc= " Exception occured in Sending Mail to Account Manager: ";
		com.ibm.ioes.utilities.Utility.LOG(exc);
		com.ibm.ioes.utilities.Utility.LOG(ex);
		
	}
	
	finally
	{
		try 
		{	DbConnection.freeConnection(conn);
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	return status;
	
}


//lawkush Start 

public ArrayList<NewOrderDto> fileAttachmentType(int docType) throws Exception
{
	ArrayList<NewOrderDto> fileAttachmentTypeList= new ArrayList<NewOrderDto>();
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	try
	{
		fileAttachmentTypeList = objDao.fileAttachmentType(docType);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return fileAttachmentTypeList;
}	

//lawkush End

public ArrayList<NewOrderDto> getOrderListForPendingWithPM(NewOrderDto dto) throws Exception
{
	ArrayList<NewOrderDto> pendingOrdersList= new ArrayList<NewOrderDto>();
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	try
	{
		pendingOrdersList = objDao.getOrderListForPendingWithPM(dto);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return pendingOrdersList;
}	


//007 start

//public ArrayList<DisconnectOrderDto> viewPDOrderList(DisconnectOrderDto objDto)throws Exception 
//[017]
public ArrayList<DisconnectOrderDto> viewPDOrderList(DisconnectOrderDto objDto,UserInfoDto objUserDto)throws Exception 
{
      ArrayList<DisconnectOrderDto> PDorderList = new ArrayList<DisconnectOrderDto>();
      NewOrderDao objDao = new NewOrderDao();
      DisconnectOrderDto dto=null;
      HashSet<String> hs=new HashSet<String>();
      ArrayList<DisconnectOrderDto> orderList_change = new ArrayList<DisconnectOrderDto>();
      try 
      {   
    	//[017] starts
    	  // PDorderList = objDao.viewPDOrderList(objDto);
           PDorderList = objDao.viewPDOrderList(objDto,objUserDto);
           /*for(int i=0;i<PDorderList.size();i++)
             {
                dto=PDorderList.get(i); 
                String orderno=dto.getSearchCRMOrder();
                if((hs.contains(orderno))==false)
                {
                 		  hs.add(orderno);
                 		 orderList_change.add(dto);
                 		
                }
                 	
             }*/
           for(int i=0;i<PDorderList.size();i++)
           {
               dto=PDorderList.get(i); 
               String orderno=dto.getSearchCRMOrder();
               if((hs.contains(orderno))==false)
               { 
            	   hs.add(orderno);
            	   dto.setCheckboxstatus("enable");
            	   
               }
               else if((hs.contains(orderno))==true)
               {   
            	   hs.add(orderno);
           		   dto.setCheckboxstatus("disable");
           	   }
            }	
           //[017] ends
      }		
      catch (Exception ex)
      {
        ex.printStackTrace();
        throw new Exception("SQL Exception : " + ex.getMessage(), ex);
      }
                                     
      return PDorderList;
}	
//// 007 end
/////004 start
//[020]
public DisconnectOrderDto cancelPDOrders(String Orders,int empid,String flag,ArrayList<String> modifiedDateList) throws Exception 
{
	NewOrderDao objDao = new NewOrderDao();
	DisconnectOrderDto statusdto=null;
	DisconnectOrderDto dto=new  DisconnectOrderDto();
	Connection conn=null;
	
	try{
		conn= DbConnection.getConnectionObject();
		conn.setAutoCommit(false);
		StringTokenizer strOrderNos  = new StringTokenizer(Orders,",");
		ArrayList<String> listponumber = new ArrayList<String>();
		ArrayList<DisconnectOrderDto> results=new ArrayList<DisconnectOrderDto>();
		for (int i =0; strOrderNos.hasMoreTokens();i++) 
		{
			listponumber.add(strOrderNos.nextToken().trim());
		}
		for(int i=0;i<listponumber.size();i++)
		{
			//[020]
			statusdto=objDao.cancelPDOrders(conn,(Integer.parseInt(listponumber.get(i).toString())),empid,flag,modifiedDateList.get(i).toString());
			results.add(statusdto);
		}
		dto.setCancelorderstatuslist(results);	
	}	
	catch (Exception ex) {
		
		//logger.error(ex.getMessage()+ " Exception occured in cancelPDOrders method of "+ this.getClass().getSimpleName());
		ex.printStackTrace();
		
	}finally{
		try {
			DbConnection.freeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
 	return dto ;
	
}
/////004 end 


//// 005 start
//[020]
public DisconnectOrderDto fnapprovePDOrders(String Orders,String roleid,String empid,ArrayList<String> modifiedDateList) throws Exception 
{
	NewOrderDao objDao = new NewOrderDao();
	Connection conn=null;
	DisconnectOrderDto statusdto=null;
	DisconnectOrderDto dto=new  DisconnectOrderDto();
	try{
		conn= DbConnection.getConnectionObject();
		conn.setAutoCommit(false);
		StringTokenizer strOrderNos  = new StringTokenizer(Orders,",");
		ArrayList<String> listponumber = new ArrayList<String>();
		ArrayList<DisconnectOrderDto> results=new ArrayList<DisconnectOrderDto>();
		for (int i =0; strOrderNos.hasMoreTokens();i++) 
		{
			listponumber.add(strOrderNos.nextToken().trim());
		}
		for(int i=0;i<listponumber.size();i++)
		{
			//[020]
		    statusdto=objDao.fnapprovePDOrders(conn,(Integer.parseInt(listponumber.get(i).toString())),roleid,empid,modifiedDateList.get(i).toString());
			results.add(statusdto);
		}
		dto.setApproveorderstatuslist(results);	
	}	
	catch (Exception ex) {
		
		//logger.error(ex.getMessage()+ " Exception occured in fnapprovePDOrders method of "+ this.getClass().getSimpleName());
		ex.printStackTrace();
		//Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
	}finally{
		try {
			DbConnection.freeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
 	return dto ;
	
}		

///// 005 end

public NewOrderDto validateServicesWithRedirectedLSIBeforeDelete(NewOrderDto objDto)
throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	NewOrderDto objRetDto = new NewOrderDto();	
	try {
		if(objDto.getRemarks().equals("Delete Product"))
		{
			objRetDto=objDao.validateProductsWithRedirectedLSIBeforeDelete(objDto);
		}
		else if(objDto.getRemarks().equals("Cancel Order"))
		{
			objRetDto=objDao.validateOrderWithRedirectedLSIBeforeCancel(objDto);
		}
		else
			objRetDto=objDao.validateServicesWithRedirectedLSIBeforeDelete(objDto);		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}


//lawkush Redirected LSI Start 


public NewOrderDto getArborLSIListWithSorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String status="";
	Connection connection =DbConnection.getConnectionObject();
	try
	{
		//lawkush Start
		ArrayList<NewOrderDto> arborLSIList= null;
		ArrayList<NewOrderDto> arborRedirectedLSIs= null;
		arborRedirectedLSIs=objDao.getArborRedirectedLSIs(objDto,connection);
		if(arborRedirectedLSIs.size()==0)
		{
			 status="CAN_SELECT";
			arborLSIList=objDao.getArborLSIListWithSorting(objDto,connection);
			objDto.setArborLSIList(arborLSIList);
		}
		else
		{
			 status="CAN_NOT_SELECT";
			 objDto.setArborLSIList(arborRedirectedLSIs);
		}
		objDto.setStateName(status);
		
		//lawkush End
		
		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	finally
	{
		try 
		{
			
		DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//return objDao.getArborLSIListWithSorting(objDto);
	return  objDto;
}	

//lawkush Redirected LSI Start 


public String validateTaxRate(long orderno)throws Exception 
{
      ArrayList<NewOrderDto> TaxRateList = new ArrayList<NewOrderDto>();
      ArrayList<NewOrderDto> oldTaxRateList = new ArrayList<NewOrderDto>();
      ArrayList<NewOrderDto> newTaxRateList = new ArrayList<NewOrderDto>();
      NewOrderDao objDao = new NewOrderDao();
      HashMap<Integer,NewOrderDto> hs=new HashMap<Integer,NewOrderDto>();
      NewOrderDto objvalidate_returndto=null;
      NewOrderDto dto=null;
      String msg="";
      try 
      {
    	    
    	  //[021] start
    	  oldTaxRateList=objDao.fetchTaxRateForOldLineOldChargeFle(orderno);
    	  newTaxRateList=objDao.fetchTaxRateOldLineNewChargeFle(orderno);
    	  msg=compareTaxRateforFLE(oldTaxRateList,newTaxRateList);
    	  
    	  if(!(msg.equals("")))
    		  return msg;
    	  //[021] end
    	  
    	  TaxRateList = objDao.validateTaxRate(orderno);
             for(int i=0;i<TaxRateList.size();i++)
             {
            	 dto=TaxRateList.get(i); 
            	 if(hs.containsKey(dto.getPodetailID())==true)
		 		 	{
            		 objvalidate_returndto=hs.get(dto.getPodetailID());
            		 if(objvalidate_returndto.getSimilartaxrate()==dto.getSimilartaxrate())
            		 {
            			 continue;
            			 
            		 }
            		 
            		 else
            		 {
            			 msg=msg+"Different Tax Rate for PoDetailNo " + dto.getPodetailID() + "of ServiceproductId " +dto.getServiceProductID() + "for SeviceId " + dto.getServiceId();
            			 break;
            			 
            		 }
            		 	
		 		 		
		 		 	}
            	 else
            	 {
            		 
            		 hs.put(dto.getPodetailID(),dto);
            	 }
                 
             }
        } 
      catch (Exception ex)
      {
        ex.printStackTrace();
        throw new Exception("SQL Exception : " + ex.getMessage(), ex);
      }
                                     
      return msg;
}
//[021] start
private String compareTaxRateforFLE(ArrayList<NewOrderDto> oldTaxRateList,
		ArrayList<NewOrderDto> newTaxRateList) {
	HashMap<String,NewOrderDto> hmapOldTaxRate=new HashMap<String,NewOrderDto>();
	HashMap<String,ArrayList<NewOrderDto>> hmapNewTaxRate=new HashMap<String, ArrayList<NewOrderDto>>();
	ArrayList<NewOrderDto>  newTaxRateDtoList=new ArrayList<NewOrderDto>();
	String msg="";
	NewOrderDto dtoItr=new NewOrderDto();
	ArrayList<NewOrderDto> dtoList=new ArrayList<NewOrderDto>();
	ArrayList<NewOrderDto> dtoList_hmap=new ArrayList<NewOrderDto>();
	boolean success=false;
	//creating hashmap for old tax rate list
	for(NewOrderDto dto:oldTaxRateList){
		if(hmapOldTaxRate.containsKey(dto.getFx_external_acc_id())==true)
			continue;
		else
			hmapOldTaxRate.put(dto.getFx_external_acc_id(), dto);
		
	}
	
	//creating hash map of new tax rates
	for(NewOrderDto dto:newTaxRateList){
		if(hmapNewTaxRate.containsKey(dto.getFx_external_acc_id())){
			dtoList_hmap=hmapNewTaxRate.get(dto.getFx_external_acc_id());
			dtoList_hmap.add(dto);
			hmapNewTaxRate.put(dto.getFx_external_acc_id(), dtoList_hmap);
						
		}else{
			dtoList=new ArrayList<NewOrderDto>();
			dtoList.add(dto);
			hmapNewTaxRate.put(dto.getFx_external_acc_id(),dtoList);
		}
	}
	
	
	for(Map.Entry<String,NewOrderDto> OldTaxEntry:hmapOldTaxRate.entrySet()){
		if(hmapNewTaxRate.containsKey(OldTaxEntry.getKey())==true){
			newTaxRateDtoList=hmapNewTaxRate.get(OldTaxEntry.getKey());
			Iterator<NewOrderDto> itrNewOrderDto=newTaxRateDtoList.iterator();
			
			while(itrNewOrderDto.hasNext()){
				dtoItr=itrNewOrderDto.next();
				
				if(dtoItr.getTaxRate().compareTo(OldTaxEntry.getValue().getTaxRate())==0){
					success=true;
					continue;
				}
					
				else{
					msg=msg+"Different Tax Rate for ServiceproductId " +dtoItr.getServiceProductID() + "for SeviceId " + dtoItr.getServiceId()+". (Sample charge selected under line's billable accout in previous orders is: "+OldTaxEntry.getValue().getChargeName()+").";
					success=false;
		   			 break;
				}
			}
			
			if(success==false)
				break;
		}else
			continue;
	}
	
	return msg;
}

//[021] end
public NewOrderDto validateVCSServicesBeforeDelete(NewOrderDto objDto)
throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	NewOrderDto objRetDto = new NewOrderDto();	
	try {
		objRetDto=objDao.validateVCSServicesBeforeDelete(objDto);		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
}
public String isMultipleDummyServices(int orderNo)
throws Exception
{
	NewOrderDao objDao = new NewOrderDao();	 
	String resultStr="";
	NewOrderDto newDto=null;
	int serviceDetailsId=0,serviceId=0,serviceproductId=0,serDetailId_Cunt=0;
	try {
		ArrayList<NewOrderDto> lstList=objDao.isMultipleDummyServices(orderNo);
		if(lstList.size()>1)
		{
			for(int i=0; i<lstList.size(); i++)
			{
				newDto=new NewOrderDto();
				newDto=lstList.get(i);
				if(serviceDetailsId==newDto.getServiceDetailID())
				{
					if(resultStr.equals(""))
					{
						resultStr=" * Line Item :"+serviceproductId+" under service No: "+serviceId;
						resultStr=resultStr+"\n * Line Item :"+newDto.getServiceproductid()+" under service No: "+newDto.getServiceId();
					}
					else
					{
						if(serDetailId_Cunt==1)
						{
							resultStr=resultStr+"\n * Line Item :"+serviceproductId+" under service No: "+serviceId;	
						}
						resultStr=resultStr+"\n * Line Item :"+newDto.getServiceproductid()+" under service No: "+newDto.getServiceId();
					}
					serDetailId_Cunt++;
				}
				else
				{
					serviceDetailsId=newDto.getServiceDetailID();
					serviceId=newDto.getServiceId();
					serviceproductId=newDto.getServiceproductid();
					serDetailId_Cunt=1;
				}
			}
			if(!resultStr.equals(""))
			{
				resultStr="There are Multiple Dummy Line Item in Order as :\n"+resultStr;
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return resultStr;
}

//004 start
public void sendPendingOrderMail()
{
	int isMailSend = 0;
	Connection connection =null;
	NewOrderDao objdao=new NewOrderDao();
	//NewOrderDto objNewOrderDto = null;
	try
	{
		connection=DbConnection.getConnectionObject();
		ArrayList<IB2BMailDto> actmgrdto=new ArrayList<IB2BMailDto>();
		actmgrdto=objdao.getActMgrIdForPendingMail(connection);
		for (IB2BMailDto actmgridList : actmgrdto) {
			try{
				
		
				actmgridList.setMailTemplateType(AppConstants.PENDINGORDERTEMPLATE);
				actmgridList.setTo(new String[]{actmgridList.getCreatorEmail() ,actmgridList.getAccountMgrEmail()});
				//objMailDto.setCc(new String[]{objMailDto.getCodinatorEmailId()});
				//ArrayList<String> mailflags = objdao.getFlagForTaskEmail();			
				
				//objMailDto.setTo(new String[]{objMailDto.getAccountMgrEmail()});
				//objMailDto.setCc(new String[]{objMailDto.getCreatorEmail()});
				
					//if(mailflags.get(0).toString().equalsIgnoreCase("Y")){
						//isMailSend = IB2BMail.sendiB2BMail(actmgridList, connection,true);
						if(isMailSend==1)
						{
							String success= " Pending Order Mail Sent Successfully to Account Manager: "+ actmgridList.getAccountMgrEmail();
							com.ibm.ioes.utilities.Utility.LOG(success);
						}
					    else
					    {
							String failure= " Pending Order Mail Sending Failed  to Account Manager : \n EmailId : "+ actmgridList.getAccountMgrEmail();
							com.ibm.ioes.utilities.Utility.LOG(failure);
						}
					//}
		
				}
			catch (Exception ex) {
				
				String exc= " Exception occured in Generating Mail to Account Manager: "+ actmgridList.getAccountMgrEmail();
				com.ibm.ioes.utilities.Utility.LOG(exc);
				com.ibm.ioes.utilities.Utility.LOG(ex);
			}
		}
	}	
	
	catch (Exception e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	finally
	{
		try
		{
			DbConnection.freeConnection(connection);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/*
 * [HYPR22032013001] - start
 * get distince Order stage list
 */
public ArrayList<NewOrderDto> getOrderStageList() throws Exception
{
	ArrayList<NewOrderDto> listOfOrderStage= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listOfOrderStage = objDao.getOrderStageList();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listOfOrderStage;
}
/*
 * 
 * get distince Service stage list
 */
public ArrayList<NewOrderDto> getServiceStageList() throws Exception
{
	ArrayList<NewOrderDto> listOfServiceStage= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listOfServiceStage = objDao.getServiceStageList();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listOfServiceStage;
}
//-[HYPR22032013001] - start
/*
 * 
 * For fetching serviceNames in 
 * Lawkush
 * 
 */



public ArrayList<NewOrderDto> getServiceNameType() throws Exception
{
	ArrayList<NewOrderDto> listOfServiceNames= new ArrayList<NewOrderDto>();
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		listOfServiceNames = objDao.getServiceNameType();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listOfServiceNames;
}

/*
 * 
 * Lawkush End
 * 
 */

//[005] Start
	public LineItemDTO fnProcessLineItems(String LineItems,String roleid,String empid) throws Exception 
	{
		NewOrderDao objDao = new NewOrderDao();
		Connection conn=null;
		LineItemDTO statusdto=null;
		LineItemDTO dto=new  LineItemDTO();
		try
		{
			conn= DbConnection.getConnectionObject();
			conn.setAutoCommit(false);
			StringTokenizer strLineItems  = new StringTokenizer(LineItems,",");
			ArrayList<String> listLineItems = new ArrayList<String>();
			ArrayList<LineItemDTO> results=new ArrayList<LineItemDTO>();
			for (int i =0; strLineItems.hasMoreTokens();i++) 
			{
				listLineItems.add(strLineItems.nextToken().trim());
			}
			for(int i=0;i<listLineItems.size();i++)
			{
				statusdto=objDao.fnProcessLineItems(conn,listLineItems.get(i),roleid,empid);
				results.add(statusdto);
			}
			dto.setLineItemStatusList(results);	
		}	
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			try 
			{
				DbConnection.freeConnection(conn);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		} 
	 	return dto ;
	}	
	//[005] End

	/*
	 * Vijay add a method of get the order stage list 
	 */
    // added by Megha to enable SendToCopc for other roles
	public int getPrevTaskRoleId(Long orderNo) throws Exception 
	{	
		int copcCount =0;
		NewOrderDao objDao = new NewOrderDao();
		
		try
		{
			copcCount = objDao.getPrevTaskRoleId(orderNo);
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return copcCount ;
	}
	public ArrayList<NewOrderDto> displayOrderStage(NewOrderDto objDto) throws Exception
	{
		ArrayList<NewOrderDto> listOrderStageDetails= new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try
		{
			listOrderStageDetails = objDao.getOrderStageName();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listOrderStageDetails;
	}
	/*Vijay end */

	//[006] Start
	public LineItemValueDTO getSCMProgressStatus(int SPId) throws Exception 
	{	
		String status="";
		LineItemValueDTO objLineDto=null;
		NewOrderDao objDao = new NewOrderDao();
		
		try
		{
			objLineDto = objDao.getSCMProgressStatus(SPId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return objLineDto ;
	}
	//[006] End

	
	//[007] Start
		public String getThirdPartyState() throws Exception 
		{	
			String status="";
			NewOrderDao objDao = new NewOrderDao();
			
			try
			{
				status = objDao.getThirdPartyState();
			} 
			catch (Exception e) {
				e.printStackTrace();
			
			}
			return status ;
		}
		//[007] End
		
		public boolean CheckServicePresent(String serviceID,int roleID)
		{
			boolean servicePresent=false;
			NewOrderDaoExt objDao = new NewOrderDaoExt();
			try
			{
				servicePresent = objDao.CheckServicePresent(serviceID,roleID);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();	
			}
			return servicePresent;
		}
		
		/**
		 * The method will return not return exception. Exception if any will be treated as 0 mins
		 * @param orderNo
		 * @return
		 */
		public static double getMinutesForCreateNewOrder(long orderNo) {
			double mins=0;
			
			Connection conn=null;
			try{
				conn=DbConnection.getConnectionObject();
				
				int lineCount = NewOrderDao.getLinesCount(conn,orderNo);
				
				String factor_str=Utility.getAppConfigValue(conn, "COPY_ORDER_PER_100_LINE_SECONDS");
				double factor=Double.parseDouble(factor_str);
				
				mins=Math.round(lineCount*((factor/100)/60));
				
			}catch(Exception ex){
				Utility.LOG(true, true, ex, "");
				mins=-1;
			}finally{
				try {
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					Utility.LOG(true, true, e, "");
				}
			}
			//System.out.println("******************************Create Existing Order Time :"+mins);
			return mins;
		}
		public static OrderDto getValidationDataForCopyOrder(long currentOrderNo,String serviceList,
				String noOfCopy) {
			OrderDto returnDto= new OrderDto();

			
			Connection conn=null;
			try{
				conn=DbConnection.getConnectionObject();
				
				OrderDto serviceCount=NewOrderDao.getCommittedUnCommittedReadServiceCount(conn,currentOrderNo);
				returnDto.setCommittedServiceCount(serviceCount.getCommittedServiceCount());
				returnDto.setUncommittedReadServiceCount(serviceCount.getUncommittedReadServiceCount());
				
				if(serviceCount.getCommittedServiceCount().intValue()!=serviceCount.getUncommittedReadServiceCount().intValue()){
					returnDto.setCopyOrderAlreadyInProgress(1);
				}else{
					returnDto.setCopyOrderAlreadyInProgress(0);
				}
				
				String[] string_serviceIds=serviceList.split(",");
				String[] string_copies=noOfCopy.split(",");		
				
				Long[] serviceIds = Utility.stringArrayToLongArray(string_serviceIds);
				Integer[] copies = Utility.stringArrayToIntegerArray(string_copies);
				
				int lineCount=getCountTotalLines(conn,serviceIds,copies);
				
				String factor_str=Utility.getAppConfigValue(conn, "COPY_ORDER_PER_100_LINE_SECONDS");
				double factor=Double.parseDouble(factor_str);
				
				double mins=Math.round(lineCount*((factor/100)/60));
				returnDto.setMinsForCopyOrder(mins);
				
				
			}catch(Exception ex){
				Utility.LOG(true, true, ex, "");
				returnDto.setAnyException(true);
			}finally{
				try {
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					Utility.LOG(true, true, e, "");
				}
			}
			
			//System.out.println("******************************Copy Order Time :"+returnDto.getMinsForCopyOrder());
			return returnDto;
		}
		public static int getCountTotalLines(Connection conn, Long[] serviceIds,
				Integer[] copies) throws Exception{
			
			
			HashMap<Long,Integer> hmap_serviceId_countLines = NewOrderDao.getServiceLineCount(conn,serviceIds);
			
			int totalLineCount=0;
			for(int i=0;i<serviceIds.length;i++){
				int lineCount=hmap_serviceId_countLines.get(serviceIds[i]);
				totalLineCount+=lineCount*copies[i];
			}
			
			return totalLineCount;
		}
		
		//[0010] start
		public ArrayList<LSICancellationDto> viewEligibleLSICancelList(LSICancellationDto objDto, int startIndex, int endIndex, int pagingRequired, int pageSize) {
		    //System.out.println("in method of newordermodel");
		    ArrayList<LSICancellationDto> eligibleLSIForCancelList=new ArrayList<LSICancellationDto>();
		    NewOrderDao daoObj=new NewOrderDao();
		    try {
				eligibleLSIForCancelList=daoObj.viewEligibleLSICancelList(objDto,  startIndex,  endIndex, pagingRequired,pageSize);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return eligibleLSIForCancelList;
		}
		//[0010] end
		//[011] Start VIPIN
		/**
		 * 
		 * @param orderNo
		 * @param roleId
		 * @return ChargesDto
		 * @author vipin
		 * @since jul 2014
		 */
		public ChargesDto processChargeDetailsForDCHWChargeValidationsAndUpdation(long orderNo,int roleId) {
			NewOrderDao daoObj=new NewOrderDao();
			String alert="Charges Validation:\n";
			ChargesDto cdto = new ChargesDto();
			cdto.setReturnStatus(1);
			Connection connection = null;
		    try {
		    	connection = DbConnection.getConnectionObject();
		    	Map<Long,ChargesDto> map =daoObj.processChargeDetailsForDCHWChargeValidationsAndUpdation(connection,orderNo, roleId);
		    	System.out.println("in model ******************************************* "+ map);
		    	Set<Long> serviceProductIdwithServCharges=new HashSet<Long>();
		    	Set<Long> newServiceProductIdList=new HashSet<Long>();
				for(Map.Entry<Long,ChargesDto> e:map.entrySet()){
					int servTaxCount=0;
					if(e.getValue().getServiceId() == e.getValue().getCreatedInServiceId()){
						//New Line Logic 1.If SERVICE_TAX present then charges list size should equal to servTax count else invalid
						if(e.getValue().getIsServTaxPresentHw()==1)
							newServiceProductIdList.add(e.getValue().getServiceProductId());
						for(String str:e.getValue().getChargesList())
							if(str.contains("SERVICE_TAX"))
								servTaxCount++;
						if(servTaxCount!=0){
							if(e.getValue().getChargesList().size()==servTaxCount)
								serviceProductIdwithServCharges.add(e.getValue().getServiceProductId());//valid.add(e.getValue());
							else{
								alert+="Sales & Service Tax Charges cannot be together in "+e.getValue().getServiceProductId()+"\n";
								cdto.setReturnStatus(-1);
							}
						}
					}else{
						//Existing line Logic
						if(e.getValue().getIsServTaxPresentHw()==1){
							//Only Service Tax charges should be present.
							for(String str:e.getValue().getChargesList())
								if(str.contains("SERVICE_TAX"))
									servTaxCount++;	
								if(e.getValue().getChargesList().size()==servTaxCount)
									;
								else{
									alert+="Ony Service Tax Charges allowed in "+e.getValue().getServiceProductId()+"\n";
									cdto.setReturnStatus(-1);
								}
						}else{
							//Only Sales Tax allowed
							for(String str:e.getValue().getChargesList())
								if(str.contains("SERVICE_TAX"))
									servTaxCount++;
							if(servTaxCount!=0){
								alert+="Only Sales Tax Charges allowed in "+e.getValue().getServiceProductId()+"\n";
								cdto.setReturnStatus(-1);
							}
						}
					}
				}
				cdto.setMessage(alert);
				//cdto.setServiceProductIdwithServCharges(serviceProductIdwithServCharges);
				//cdto.setNewServiceProductIdList(newServiceProductIdList);
				if(cdto.getReturnStatus()==1){
					if(newServiceProductIdList.isEmpty()==false){
						Utility.LOG(true, true, "Calling doUpdateNewServiceProductIdFlag Method of NewOrderDao");
						connection.setAutoCommit(false);
						daoObj.doUpdateNewServiceProductIdFlag(connection,newServiceProductIdList);
						connection.commit();
					}
					if(serviceProductIdwithServCharges.isEmpty()==false){
						Utility.LOG(true, true, "Calling doUpdateServTaxPresentFlag Method of NewOrderDao");
						connection.setAutoCommit(false);
						daoObj.doUpdateServTaxPresentFlag(connection,serviceProductIdwithServCharges);
						connection.commit();
					}
				}
			} catch (Exception e) {
				Utility.LOG(true, true, e,"Some Error Occured in processChargeDetailsForDCHWChargeValidationsAndUpdation method of NewOrderModel");
			}finally {
				try {
					DbConnection.freeConnection(connection);
				} catch (Exception e) {
					Utility.LOG(true, true, e,"Some Error Occured in processChargeDetailsForDCHWChargeValidationsAndUpdation method of NewOrderModel");
				}
			}	
			return cdto;
		}
		//[011] Start VIPIN
		//[012] start
		/**
		 * To validate parallel upgrade LSIs and after successful validation, data is saved in database 
		 * @author gunjan
		 * @param objDto
		 * @param empID
		 * @return Arraylist
		 */
		public ArrayList<ParallelUpgradeValidationDto> manageParallelUpgradeLSI(NewOrderDto objDto,long empID) {
			

			AjaxValidation objValidate = new AjaxValidation();
			ArrayList lstValidate = new ArrayList();
			NewOrderDto objRetDto=new NewOrderDto();
			NewOrderModel model=new NewOrderModel();
			NewOrderDao objDao = new NewOrderDao();
			ArrayList<ParallelUpgradeValidationDto> listValidatedLsis1=new ArrayList<ParallelUpgradeValidationDto>();
			ArrayList<ParallelUpgradeValidationDto> listValidatedLsis2=new ArrayList<ParallelUpgradeValidationDto>();
			ArrayList<ParallelUpgradeValidationDto> listValidatedLsis3=new ArrayList<ParallelUpgradeValidationDto>();
			ArrayList<ParallelUpgradeValidationDto> listMergedValidatedLsis=new ArrayList<ParallelUpgradeValidationDto>();
			ParallelUpgradeValidationDto obParallel=new ParallelUpgradeValidationDto();
			HashMap<String, String> mapDistinctLsi=new HashMap<String, String>();
			boolean isAllSuccess=false;
			String msgDuplicate="";
			int duplicateStatus=0;
			int executeFlag;
			try {
						//validate contents of fields and count of LSIs 
						lstValidate = objValidate.ValidateServiceAttributes(objDto);
						 //new ParallelUpgradeValidationDto().setCustomer_logicalSINumber(objDto.getCustomer_logicalSINumber());
						
						if(lstValidate.toString().equalsIgnoreCase("[]")){
							//validate for no duplicate LSIs
							
									
									//convert into arraylist and then merge
									StringTokenizer parallelLSIToken1=new StringTokenizer(objDto.getParallelUpgradeLSINo1(), ",");
									while(parallelLSIToken1.hasMoreTokens()){
										//check for duplicate LSIs
										String parallelUpgradeLSI=parallelLSIToken1.nextToken();
										if(null==mapDistinctLsi.get(parallelUpgradeLSI)){
											mapDistinctLsi.put(parallelUpgradeLSI, parallelUpgradeLSI);
										}
										else{
											msgDuplicate+="duplicate LSIs in Parallel Upgrade LSI No1 \n";
											duplicateStatus=1;
											break;
										}
										if(duplicateStatus==0)
										listValidatedLsis1.add(new ParallelUpgradeValidationDto(parallelUpgradeLSI));
									}
									
									StringTokenizer parallelLSIToken2=new StringTokenizer(objDto.getParallelUpgradeLSINo2(), ",");
									while(parallelLSIToken2.hasMoreTokens()){
										String parallelUpgradeLSI=parallelLSIToken2.nextToken();
										//check for duplicate LSIs
										if(null==mapDistinctLsi.get(parallelUpgradeLSI)){
											mapDistinctLsi.put(parallelUpgradeLSI, parallelUpgradeLSI);
										}
										else{
											msgDuplicate+="duplicate LSIs in Parallel Upgrade LSI No2 \n";
											duplicateStatus=1;
											break;
										}
										if(duplicateStatus==0)
										listValidatedLsis2.add(new ParallelUpgradeValidationDto(parallelUpgradeLSI));
									}
									
									StringTokenizer parallelLSIToken3=new StringTokenizer(objDto.getParallelUpgradeLSINo3(), ",");
									while(parallelLSIToken3.hasMoreTokens()){
										//check for duplicate LSIs
										String parallelUpgradeLSI=parallelLSIToken3.nextToken();
										if(null==mapDistinctLsi.get(parallelUpgradeLSI)){
											mapDistinctLsi.put(parallelUpgradeLSI, parallelUpgradeLSI);
										}
										else{
											msgDuplicate+="duplicate LSIs in Parallel Upgrade LSI No3 \n";
											duplicateStatus=1;
											break;
										}
										if(duplicateStatus==0)
										listValidatedLsis3.add(new ParallelUpgradeValidationDto(parallelUpgradeLSI));
									}
									
									//merge these 3 arraylists into 1
									listMergedValidatedLsis.addAll(listValidatedLsis1);
									listMergedValidatedLsis.addAll(listValidatedLsis2);
									listMergedValidatedLsis.addAll(listValidatedLsis3);
									
								if(duplicateStatus==1){
									obParallel.setValidationFlag(1);
									obParallel.setStatusMsg(msgDuplicate);
									listMergedValidatedLsis.add(obParallel);
								}
								else{
									isAllSuccess=model.validateParallelUpgradeLSI(listMergedValidatedLsis,objDto.getServiceId());
									
									if(isAllSuccess==true){
										
										listMergedValidatedLsis=new ArrayList<ParallelUpgradeValidationDto>();
										executeFlag=objDao.updateServiceAttributes(objDto,empID,listValidatedLsis1,listValidatedLsis2,listValidatedLsis3);
										
										if(executeFlag==1){
											obParallel.setExecuteFlag(1);
											listMergedValidatedLsis.add(obParallel);
										}
											
									}
								}
							}
							else{
									obParallel.setValidationFlag(1);
									obParallel.setStatusMsg(lstValidate.toString());
									listMergedValidatedLsis.add(obParallel);
								}
					} catch (Exception e) {
					e.printStackTrace();
				 }
				return listMergedValidatedLsis;
			}
			
		/**
		 * To check for service type validation,account no validation, Disconenction order validation, LSI validation
		 * @author gunjan
		 * @param listMergedValidatedLsis
		 * @param currentServLSI
		 * @return ArrayList
		 */
		private boolean validateParallelUpgradeLSI(
				ArrayList<ParallelUpgradeValidationDto> listMergedValidatedLsis, int currentServiceId) {
			
			//generate comma separated string
			String strLSI="";
			//System.out.println(listMergedValidatedLsis.size());
			Long[] lstLongLSI=new Long[listMergedValidatedLsis.size()];
			Map<Long,ParallelUpgradeValidationDto> mapParallel = new HashMap<Long,ParallelUpgradeValidationDto>();
			ParallelUpgradeValidationDto currentService = new ParallelUpgradeValidationDto();
			ParallelUpgradeValidationDto obParallel=new ParallelUpgradeValidationDto();
			//ArrayList<ParallelUpgradeValidationDto> result=new ArrayList<ParallelUpgradeValidationDto>();
			int index=0;
			String msg="";
			boolean isAllSuccess=true;
			NewOrderDao obDao=new NewOrderDao();
			try {
				
			for(ParallelUpgradeValidationDto obDto:listMergedValidatedLsis){
				lstLongLSI[index]=Long.valueOf(obDto.getLogicalSINumber());
				index++;
				
			}
			strLSI=Utility.getCsv(lstLongLSI);
			mapParallel=obDao.fetchParallelUpgradeLSIData(strLSI);
			currentService=obDao.fetchCurrentServiceLSIData(currentServiceId);
			for(ParallelUpgradeValidationDto obDto:listMergedValidatedLsis){
				
					
					obDto.setStatusFlag(1);
					//check whether LSI is valid or not
					if(mapParallel.containsKey(Long.valueOf(obDto.getLogicalSINumber()))){
						ParallelUpgradeValidationDto obMap=mapParallel.get(Long.valueOf(obDto.getLogicalSINumber()));
						
							//check whether service type is same or not
							if(obMap.getServiceTypeId()==currentService.getServiceTypeId()){
								//check whether account ids match or not
								/* Start Amit
								if((obMap.getCrmAccountNo()).equals(currentService.getCrmAccountNo())){
									 End Amit */
									//check whether order is permanent disconnection or not
									if(obMap.getSubChangeTypeId()==3){
											obDto.setStatusFlag(1);
									}
									//if order is not permanent disconnection
									else{
										msg="Latest order should be permanent disconnection ";
										obDto.setStatusFlag(0);
										obDto.setStatusMsg(msg);
										isAllSuccess=false;
									}
									
								/* Start Amit
								}
								//if account ids don't match
								else{
									msg="account no of current service doesn't match with parallel upgrade LSI account no ";
									obDto.setStatusFlag(0);
									obDto.setStatusMsg(msg);
									isAllSuccess=false;
								}
								End Amit*/
								
							}
							//if services type doesn't match
							else{
								msg="service type of current service doesn't match with parallel upgrade LSI service type ";
								obDto.setStatusFlag(0);
								obDto.setStatusMsg(msg);
								isAllSuccess=false;
							}
						
					}
					//if lsi is not valid
					else{
						msg=" LSI doesn't exist ";
						obDto.setStatusFlag(0);
						obDto.setStatusMsg(msg);
						isAllSuccess=false;
					}
					
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return isAllSuccess;
		}
		//[012] end
		
		//Start [013] To get getOrderListForPendingWithAM for reassign to another AM
		public ArrayList<OrderForPendingWithAMDto> getOrderListForPendingWithAM(
				OrderForPendingWithAMDto orderForPendingWithAMDto)  {
			// TODO Auto-generated method stub
			ArrayList<OrderForPendingWithAMDto> pendingOrdersList= new ArrayList<OrderForPendingWithAMDto>();
			NewOrderDaoExt objDao = new NewOrderDaoExt();
			try
			{
				pendingOrdersList = objDao.getOrderListForPendingWithAM(orderForPendingWithAMDto);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();	
			}
			return pendingOrdersList;
		}
		
		//End [013]
		/**
 * Sending mail and sms async utility on approval or rejection
 * Mail State 1=Approval,2=Rejection,3=PM Welcome Mail,4=Partial Initiate
 * @param objDto
 * @param action
 * @author Anil Kumar
 * @since 14-April-2015
 */
public static void sendIB2BApprovalRejectionMailAndSMS(ViewOrderDto objDto) throws Exception {
	NewOrderDao objDao=new NewOrderDao();
	PMWelcomeMailModel pmmodel= new PMWelcomeMailModel();
	Integer Status=null;
	try{
		if(objDto.getIsApprovalMail()==true){
			Status=objDao.sendSaveActionMail(objDto,objDto.getNewTaskId(),null);
			//System.err.println("Approval Mail Sent");
		}
		if(objDto.getIsRejectionMail()==true){
			Status=objDao.sendSaveActionMailOnRejection(objDto, null);
			//System.err.println("Rejection Mail Sent");
		}
		if(objDto.getIsPMWelcomeMail()==true){
			Status=pmmodel.sendPMWelcomeMailAlert(objDto.getOrderno());
			//System.err.println("PM Welcome Mail Sent");
		}
		if(objDto.getIsPartialInitiateMail()==1){
			objDao.sendPartialInitiatedMail(objDto, objDto.getServiceList());
			//System.err.println("Partial Initate Mail Sent");
		}
	}catch(Exception exp){
		throw exp;
	}
}
//Start[014]
/**
 * 
 * @author Anil Kumar
 * @param StandardReason
 * @return int
 * @since 14-May-2015
 */
		public static String insertNewStandardReason(StandardReason objDto) throws Exception {
			String status="-1";
			Connection conn=null;
			try{
				conn=DbConnection.getConnectionObject();
				//Validate standard reason name (duplicate standard reason name does not allow)
				StandardReason objStdReason=NewOrderDaoExt.getStandardReasonByName(objDto.getStdReasonName(),objDto.getInterfaceType(),conn);
				if(objStdReason==null){
					
					//fetch next value of standard reason id from datbase sequence
					Long stdReasonId=NewOrderDaoExt.getNextStandardReasonId(conn);
					//insert new standard reason
					objDto.setStdReasonId(stdReasonId);
					conn.setAutoCommit(false);
					status=NewOrderDaoExt.insertNewStandardReason(objDto,conn);	//This method rollbacks on exception	
					if("1".equalsIgnoreCase(status)){
						conn.commit();
					}else{
						conn.rollback();
					}
				}
				else{
					//duplicate standard reason name found
					status="-2";
				}
			}catch(Exception e){
				if(conn!=null && conn.getAutoCommit()==false){
					conn.rollback();
				}
				throw e;
			}finally{
				DbConnection.freeConnection(conn);
			}
			return status;
		}
public static ArrayList<StandardReason> fetchStandardReason(StandardReason objDto) throws Exception {
	
	ArrayList<StandardReason> listStandardReasonDetails=null;
	try{
		listStandardReasonDetails=NewOrderDaoExt.fetchStandardReason(objDto);
	}catch(Exception e){
		throw e;
	}
	return listStandardReasonDetails;
}
public static int editStandardReason(String strStdReasonIds,String strStdReasonStatus) throws Exception {
	int status=-1;
	try{
		status=NewOrderDaoExt.editStandardReason(strStdReasonIds,strStdReasonStatus);
	}catch(Exception e){
		throw e;
	}
	return status;
}
//End[014]
//Start [016]
	public static WorkFlowAttachDetails getChangeOrderSubTypeAttached(
			String orderno, Connection optionalConnection) throws Exception {
	
		boolean isPermanentDisconnectionSingleThenBulkApproval = isPermanentDisconnectionSingleThenBulkApproval(orderno);
		
		if(isPermanentDisconnectionSingleThenBulkApproval==true){
			WorkFlowAttachDetails attachDetails = new WorkFlowAttachDetails();
			attachDetails.setBehaviour(WorkFlowAttachDetails.behaviour_PermanentDisconnectionSingleThenBulkApproval);
			return attachDetails; 
		}else{
			WorkFlowAttachDetails attachDetails = new WorkFlowAttachDetails();
			attachDetails.setWorkFlowList(new NewOrderDao().getChangeOrderSubTypeAttached(orderno,
					optionalConnection));	
			return attachDetails; 
		}
	}
	
	public static class WorkFlowAttachDetails{
		
		static String behaviour_DEFAULT="DEFAULT";
		static String behaviour_PermanentDisconnectionSingleThenBulkApproval="PermanentDisconnectionSingleThenBulkApproval";
		
		String behaviour = behaviour_DEFAULT;
		ArrayList<OrderHeaderDTO> workFlowList = null;
		public String getBehaviour() {
			return behaviour;
		}
		public void setBehaviour(String behaviour) {
			this.behaviour = behaviour;
		}
		public ArrayList<OrderHeaderDTO> getWorkFlowList() {
			return workFlowList;
		}
		public void setWorkFlowList(ArrayList<OrderHeaderDTO> workFlowList) {
			this.workFlowList = workFlowList;
		}
		
		
	}
	
	public static class BillingEfficiencyDto{
		
		String module="";
		Long orderNo=0L;
		Integer spid=0;
		String auto_Type="";
		String result="";
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		public Long getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(Long orderNo) {
			this.orderNo = orderNo;
		}
		public Integer getSpid() {
			return spid;
		}
		public void setSpid(Integer spid) {
			this.spid = spid;
		}
		public String getAuto_Type() {
			return auto_Type;
		}
		public void setAuto_Type(String auto_Type) {
			this.auto_Type = auto_Type;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		
	}	
					
	
	public static boolean isPermanentDisconnectionSingleThenBulkApproval(String orderNo){
		// TODO Raveendra
		boolean isPermanentDisconnectionSingleThenBulkApproval=false;
		try {
			BillingEfficiencyDto billingEfficiencyDto=new BillingEfficiencyDto();
			billingEfficiencyDto.setModule("CHK_PD_SINGLE_THEN_BULK_APPROVAL");			
			billingEfficiencyDto.setOrderNo(Long.parseLong(orderNo));
			NewOrderDao newOrderDao=new NewOrderDao();
			newOrderDao.getBillingEfficiencyData(billingEfficiencyDto);
			if("YES".equals(billingEfficiencyDto.getResult())){
				isPermanentDisconnectionSingleThenBulkApproval=true;	
			}else{
				isPermanentDisconnectionSingleThenBulkApproval=false;
			}
			return isPermanentDisconnectionSingleThenBulkApproval;
			
		} catch (Exception e) {
			Utility.LOG(true,false,e,orderNo);
			return false;
		}
	}

	public boolean isViewModeForPDSingleApproval(long orderNo) {
		BillingEfficiencyDto billingEfficiencyDto = new BillingEfficiencyDto();

		try {
			billingEfficiencyDto.setModule("CHK_VIEWMODE_FOR_PD_SINGLE_BULK");
			billingEfficiencyDto.setOrderNo(orderNo);
			NewOrderDao newOrderDao = new NewOrderDao();
			newOrderDao.getBillingEfficiencyData(billingEfficiencyDto);

			if ("YES".equals(billingEfficiencyDto.getResult())) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			Utility.LOG(true, false, e, String.valueOf(orderNo));
			return false;
		}

	}
	
	public static class ValidateOrderAlertDTO { 
		private Integer isAnyReDisconnectionAlert;
		private String reDisconnectionAlertMsg;
		
		public Integer getIsAnyReDisconnectionAlert() {
			return isAnyReDisconnectionAlert;
		}
		public void setIsAnyReDisconnectionAlert(Integer isAnyReDisconnectionAlert) {
			this.isAnyReDisconnectionAlert = isAnyReDisconnectionAlert;
		}
		public String getReDisconnectionAlertMsg() {
			return reDisconnectionAlertMsg;
		}
		public void setReDisconnectionAlertMsg(String reDisconnectionAlertMsg) {
			this.reDisconnectionAlertMsg = reDisconnectionAlertMsg;
		}
		
		
	}
	
	//End [016]
	
	//[018] start
	public ValidateOrderAlertDTO fetchOrderAlerts(NewOrderDto objDto) throws Exception {
		NewOrderDao newOrderDao=new NewOrderDao();
		ValidateOrderAlertDTO validateOrderAlertDTO=new ValidateOrderAlertDTO();
		try {
			List<String> lListScenerio = Arrays.asList(objDto.getScenario());// list having all scenerios
			if(lListScenerio.contains("REDISCONNECTION_NOTIFY")){
				Integer isDisconectedCharge=newOrderDao.getDisconnectionFlagStatus(objDto.getOrderNo());  // isDisconectedCharge is 0 means no reidisconnected charges in order else do have 
				if(isDisconectedCharge>0){
					validateOrderAlertDTO.setIsAnyReDisconnectionAlert(1);
					validateOrderAlertDTO.setReDisconnectionAlertMsg("The order has re-disconnected charges.");
				}
			}
			// User can also add another scenerios like below
			/*if(lListScenerio.contains("jsasdasda")){
				//Integer isJsasdasda=neworderdao.getJsasdasda(objDto);   
				//if(isJsasdasda>0){
					//validateOrderAlertDTO.setIsisJsasdasdaAlert(2);
					//validateOrderAlertDTO.setReisJsasdasdaAlertMsg("This order has jsasdasda which can be re-jsasdasda");
				//}
			}*/  
		} catch (Exception exception) {
			Utility.LOG(true, true,exception,"exception caught in fetchOrderAlerts method of "+ this.getClass());
		}
		return validateOrderAlertDTO;
	}
	//[018] end
	//[019] start
	
	//static inner class
	public static class M2MClass{
		private  int code=-1;
		private  String msg="";
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		
		
	}
	
	public M2MClass validateM2M(int orderNo) {
		NewOrderDao dao=new NewOrderDao();
	
		String mismatchedServiceId="";
		ArrayList<String> lstServiceId=new ArrayList<String>();
		M2MClass objM2M=new M2MClass();
		try {
			
					lstServiceId = dao.validateAccountofService(orderNo);
					if(!(lstServiceId.isEmpty())){
						for(int i=0;i<lstServiceId.size();i++)
						mismatchedServiceId=mismatchedServiceId+","+lstServiceId.get(i);
					}
					
					if(!(mismatchedServiceId.equalsIgnoreCase(""))){
						objM2M.setCode(1);
						objM2M.setMsg(mismatchedServiceId.substring(1));
					}
					
		
						
		} catch (Exception e) {
			Utility.LOG(true, true,e,"exception caught in fetchOrderAlerts method of "+ this.getClass());
			e.printStackTrace();
		}
		
		return objM2M;
	}
	//[019] end

	public static ArrayList<NewOrderBean> fetchChannelPartner(Long respId, int startIndex,int endIndex,int pagingRequired,int pageRecords, String feId , String partnerName) throws Exception {
		
		ArrayList<NewOrderBean> listChannelPartnerDetails=null;
		try{
			listChannelPartnerDetails=NewOrderDaoExt.fetchChannelPartner(respId,startIndex,endIndex,pagingRequired,pageRecords,feId,partnerName);
		}catch(Exception e){
			throw e;
		}
		return listChannelPartnerDetails;
	}
	
	public static String insertNewChannelPartner(NewOrderBean objDto) throws Exception {
		String status="FEFailure";
		try{
			
			status=NewOrderDaoExt.insertNewChannelPartner(objDto);	//This method rollbacks on exception	
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	public static String saveChannelPartner( ChannelPartnerDto objDto) throws Exception {
		String result = null;
		//boolean errorsFound=false;
		try{
			//Server Side Security Start for channel partner name 
			    ArrayList errorsMandatforName=Utility.validateValue(new ValidationBean(objDto.getChannelPartnerName(),"Channel Partner Name",200),
					""+Utility.CASE_MANDATORY).getCompleteMessageStrings();
				if(errorsMandatforName!=null)
				{
					return "ChannelNameMandatory";
				}
				ArrayList errorsSpecCharforName=Utility.validateValue(new ValidationBean(objDto.getChannelPartnerName(),"Channel Partner Name",200),
						Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errorsSpecCharforName!=null)
				{
					return "ChannelNameSpecialCharacter";
				}
				ArrayList errorsMaxLengthforName=Utility.validateValue(new ValidationBean(objDto.getChannelPartnerName(),"Channel Partner Name",200),
						Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errorsMaxLengthforName!=null)
				{
					return "ChannelNameMaxLength";
				}
			//Server Side Security End for channel partner name 
			
				//Server Side Security Start for channel partner code 
			    ArrayList errorsMandatforCode=Utility.validateValue(new ValidationBean(objDto.getChannelpartnerCode(),"Channel Partner Code",200),
					""+Utility.CASE_MANDATORY).getCompleteMessageStrings();
				if(errorsMandatforCode!=null)
				{
					/*return "ChannelCodeMandatory";*/
				}
				ArrayList errorsSpecCharforCode=Utility.validateValue(new ValidationBean(objDto.getChannelpartnerCode(),"Channel Partner Code",200),
						Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errorsSpecCharforCode!=null)
				{
					return "ChannelCodeSpecialCharacter";
				}
				ArrayList errorsMaxLengthforCode=Utility.validateValue(new ValidationBean(objDto.getChannelpartnerCode(),"Channel Partner Code",200),
						Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errorsMaxLengthforCode!=null)
				{
					return "ChannelCodeMaxLength";
				}
			//Server Side Security End for channel partner code
				
			NewOrderDao newOrderDao= new NewOrderDao();
			ArrayList<String> listofExistingChannelPartnerCode = newOrderDao.getListofPartnerCode(objDto.getRespId());
			ArrayList<String> listofExistingFE = newOrderDao.getListofFE(objDto.getRespId());
			ArrayList<FieldEnginnerDto> addList=objDto.getAddList();
			ArrayList<FieldEnginnerDto> editList=objDto.getEditList();

			if(objDto.getChannelPartnerId()==null){//add
				 result =validateChannelPartner(addList,objDto.getChannelpartnerCode(),listofExistingChannelPartnerCode,listofExistingFE,1);//add  // 1 for add
			}else{
				 if(objDto.getChannelpartnerCodeHidden().equals(objDto.getChannelpartnerCode())){ // if user doesnt change partner code
					 result =validateChannelPartner(editList,listofExistingFE,2);//edit
					 if(result.equals("success") && objDto.getPartner_Is_Active()==1 && NewOrderDaoExt.isActivePartnerCode(objDto.getChannelPartnerId())==0 && listofExistingChannelPartnerCode.contains(objDto.getChannelpartnerCode())){
						// result =validateUniqueChannelPartnerCode(objDto.getChannelpartnerCode(),listofExistingChannelPartnerCode);
						 result= "channelPartnerCodeFailure";
					 }
				 }else{
					 result =validateChannelPartner(editList,objDto.getChannelpartnerCode(),listofExistingChannelPartnerCode,listofExistingFE,2);//edit  // 2 for edit
				 }
			}
			if(result.equals("success") ){
				if(objDto.getChannelPartnerId()==null){
					result=NewOrderDaoExt.addChannelPartner(objDto);	//This method rollbacks on exception	
				}else{
					result=NewOrderDaoExt.updateChannelPartner(objDto);	//This method rollbacks on exception	
				}
			}
		}catch(Exception e){
			result="otherFailure";
			e.printStackTrace();
		}
		return result;
	}
	
	private static String validateChannelPartner(ArrayList<FieldEnginnerDto> list, ArrayList<String> listofExistingFE , Integer flag) {
		String result=null;
		try{
			if(listofExistingFE.size()==0){
				return "success";
			} else{
				if(validateUniqueFEId(list,listofExistingFE,flag).equals("success")){
					result= "success";	
				}else{
					result= "FEFailure";
				}	
			}
		}catch(Exception e){
			result= "otherFailure";
			e.printStackTrace();
		}
		return result;
	}
	
	private static String validateChannelPartner(ArrayList<FieldEnginnerDto> list,
		 String channelPartnerCode, ArrayList<String> listofExistingChannelPartnerCode, ArrayList<String> listofExistingFE, Integer flag) {
		String result=null;
			try{
				
				String resultValidateUniqueChannelPartnerCode = validateUniqueChannelPartnerCode(channelPartnerCode,listofExistingChannelPartnerCode);
				String resultValidateUniqueFE = validateUniqueFEId(list,listofExistingFE,flag);
				if(listofExistingFE.size()==0){
					if(resultValidateUniqueChannelPartnerCode.equals("success")){
						result= "success";	
					}else{
						result= "channelPartnerCodeFailure";
					}
				}else{
					if(resultValidateUniqueChannelPartnerCode.equals("success") && resultValidateUniqueFE.equals("success")){
						result= "success";	
					}else{
						if(!resultValidateUniqueChannelPartnerCode.equals("success")){
							result= "channelPartnerCodeFailure";	
						}else{
							result= "FEFailure";
						}
						//result= validateUniqueChannelPartnerCode(channelPartnerCode,listofExistingChannelPartnerCode);
					}
				}
				
			}catch(Exception e){
				result= "otherFailure";	
				e.printStackTrace();
			}
			return result;
	}
	
	private static String validateUniqueFEId(ArrayList<FieldEnginnerDto> list,
			ArrayList<String> listofExistingFE , Integer flag) throws Exception {
		/*
		ArrayList<LSICancellationDto> list=dto.getLSICancellationDtolist();
		try {
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			if(list !=null){
					for(LSICancellationDto objDto:list)
					{*/
		
		if(list !=null){
				
				if(flag==2){ //edit channel partner
					for(int i=0;i<list.size();i++){
						if((!list.get(i).getFieldEngineer().equalsIgnoreCase(list.get(i).getFieldEngineerHidden())) ){
							// and list.get(i).getFieldEngineer().equals(list.get(i).getFieldEngineerHidden() && )
							if(listofExistingFE.contains(list.get(i).getFieldEngineer().toUpperCase()))
							{
								return "FEFailure";
							}
						}else{
							if ((list.get(i).getField_Engineer_Is_Active()==1 && NewOrderDaoExt.isActiveFEId(list.get(i).getSe_Id())==0 && (listofExistingFE.contains(list.get(i).getFieldEngineer().toUpperCase())))){
								/*if(listofExistingFE.contains(list.get(i).getFieldEngineer().toUpperCase()))
								{
									return "FEFailure";
								}*/
								return "FEFailure";
							}/*else{
								return "FEFailure";
							}*/
						}
					}
				}else{ //add channel partner
					for(int i=0;i<list.size();i++){
						if(listofExistingFE.contains(list.get(i))){
							return "FEFailure";
						}
					}
				}
		}	
		
		/*for(int i=0;i<list.size();i++){
			if(listofExistingFE.contains(list.)){
				return false;
			}
		}	*/
		return "success";
	}
	
	private static String validateUniqueChannelPartnerCode(
			String channelPartnerCode, ArrayList<String> listofExistingChannelPartnerCode) {
		if(listofExistingChannelPartnerCode.contains(channelPartnerCode.toUpperCase())){
			return "channelPartnerCodeFailure";
		}
		return "success";
	}
	
	//Shubhranshu, Parallel Upgrade Validation for Change Order.jsp
	public ArrayList<ServiceDto> validateParallelUpgradeForDisconnectionOrder(ArrayList<Integer> listSerivceIds) throws Exception 
	{
		 ArrayList<ServiceDto> validationFailedServiceIDs=null;		
			 ArrayList<ServiceDto> validLSiDto=null;			
			      NewOrderDao dao=new NewOrderDao();	
					  validLSiDto=dao.getServicesDetailsForToBeCheckedServices(listSerivceIds);						
				validationFailedServiceIDs = validateLSIAsParallelUpgrade(validLSiDto);  								
		   return validationFailedServiceIDs;		
	}	
	// validate if a LSI is marked as parallel upgrade or not .....
	
private ArrayList<ServiceDto> validateLSIAsParallelUpgrade(ArrayList<ServiceDto> listSerivceIds) throws Exception 
	{
		ArrayList<ServiceDto> resultDto=new ArrayList<ServiceDto>();
			Connection con=null;
				con=DbConnection.getConnectionObject();
					PreparedStatement pstmt=null;
						ResultSet rst=null;
							pstmt=con.prepareStatement(sqlvalidateAsParallelUpgrade);
								ListIterator<ServiceDto> iterator=listSerivceIds.listIterator();
									while(iterator.hasNext())
										{
											ServiceDto item=iterator.next();
											 	pstmt.setLong(1,item.getLogical_si_no());
											 		rst=pstmt.executeQuery();
											 			while(rst.next())
											 				{
											 					if(rst.getInt("COUNTFLAG")==0)											  
											 					{
											 						resultDto.add(item);
											 					}																									
											 				}
										 				 }
											        try
											     {
											  DbConnection.closeResultset(rst);
									     DbConnection.closePreparedStatement(pstmt);			
									  DbConnection.freeConnection(con); 
	           					}
							catch(SQLException sqe)
						{
				     Utility.LOG(sqe);
				  }
	           return resultDto;
		}

				//	Parallel Upgrade Validation for Draft PD order Gui//
	public ArrayList<ServiceDto> validatePDOrderForParallelUpgrade(Integer[] serviceIdsAsArray) throws Exception 
		{
			return validateParallelUpgradeForDisconnectionOrder(new ArrayList<Integer>(Arrays.asList(serviceIdsAsArray)));
		}
	//Shubhranshu
	//[023] start
	
	//static inner class
		public static class RRAutoTriggerClass{
			private  String docname="";
			private  String message="";
			public String getDocname() {
				return docname;
			}
			public void setDocname(String docname) {
				this.docname = docname;
			}
			public String getMessage() {
				return message;
			}
			public void setMessage(String message) {
				this.message = message;
			}
		}
	public RRAutoTriggerClass processForRRAutoTriggerValidation(long orderno,int subChangeTypeId){
		RRAutoTriggerClass objRRAutoTrigger = new RRAutoTriggerClass();
		int flag = 0;
		try
		{
			NewOrderDaoExt objDao = new NewOrderDaoExt();
			if(subChangeTypeId == 5){
				flag = findRateRenewalAutoTriggerServices(orderno);
				if(flag ==1 )
				{
					
					int countofservices = objDao.getCountOfRRAutoTriggerServicesForPreviousMonth(orderno);
					
					if(countofservices>0){
						int countoffileupload = objDao.getCountOfApprovalDocument(orderno,AppConstants.RR_AUTO_TRIGGER_DOCUMENT_ID);
						
						if(countoffileupload <=0){
							String message = "Failed";
							objRRAutoTrigger.setMessage(message);
							
							objRRAutoTrigger.setDocname(ApplicationFlags.RR_AUTO_TRIGGER_DOCUMENT_NAME);
						}
					}
				}
			}
		} 
		catch (Exception e) {
			Utility.LOG(e);
			objRRAutoTrigger.setMessage("INTERNAL_ERROR");
		}
		return objRRAutoTrigger;
	}
	 
	

	public int findRateRenewalAutoTriggerServices(long orderNo) throws Exception{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		int flag = 0;
		flag = objDao.findRateRenewalAutoTriggerServices(orderNo);
		return flag;
	}
	// [023] end
	//[024] start
	
	public ServiceDto validateDropAndCarry(long orderNo, String stage) {
		
		NewOrderDao daoObj=new NewOrderDao();
		ServiceDto msgDto=null;
		try{
			msgDto=daoObj.validateDropAndCarry(orderNo,stage);
		}catch(Exception e){
			Utility.LOG(true, true, e, "from method validateDropAndCarry() of NewOrderModel ");
		}
		return msgDto;
	}
	//[024] end
	public String getIsDifferentialEnable(String HdnChangeTypeID,String changeTypeId) throws Exception{
		String isDifferentialEnable="Disable";
		String differentialChangeTypeIds=Utility.getAppConfigValue("DIFFERENTIAL_CHANGE_TYPES");
		if(differentialChangeTypeIds!=null){
			String[] diffChangeTypeIds=differentialChangeTypeIds.split(",");
			for (String diffChangeTypeId : diffChangeTypeIds) {
				if(diffChangeTypeId.equals(HdnChangeTypeID) || diffChangeTypeId.equals(changeTypeId)){
					isDifferentialEnable="Enable";
					break;
				}
			}
		}
		return isDifferentialEnable;
	}
}
