package com.ibm.ioes.utilities;
//Tag Name Resource Name  Date		CSR No			Description
//[008]	 MANISHA GARG	23-Feb-11	00-05422		To Check PONUMBER and PODATE already exists inDatabase for NewOrder
//[009]	 MANISHA GARG	23-Feb-11	00-05422		To Check PONUMBER and PODATE already exists inDatabase for ChangeOrder 
//[001]	 ROHIT VERMA	4-MAR-11    00-05422		CREATED BY AND MODIFIED BY
//[002]  ROHIT VERMA	10-MAR-11	00-05422		ADD CHILD NODE FOR CHNAGE ORDER
//[012]	 SAURABH SINGH	10-MAR-11	00-05422		To view workflow during change order validation 
//[013]	 Vishwa			17-MAR-11	00-05422		Populate Pincode Based on State and City
//[020]	 SAURABH SINGH	14-MAR-11	00-05422		To Copy services from one Order into Another
//[021]  Vishwa			25-Mar-11	00-05422		Prepopulate Service Location Details based on the existing Service product id of Service Type
//[022]    MANISHA GARG   25-MAR-11   00-05422        Deletion Of Products 
//[025]	 SAURABH SINGH	25-MAR-11	00-05422		To Create Partial Publish
//[026] 	 Lawkush 		11-April-2011				In order to validate fields according to database driven mandatory or nonmandatory in all section
//[027] 	 Lawkush 		06-05-2011				Setting Paging sorting 
//[0020] 	 Sumit Arora 	27-05-2011		00-05422		For Fetching BCP With Disptach Address
//[0021] 	ANIL KUMAR		23-06-11		00-05422		for display BCP details from Account popup
// [00044]	 Ashutosh Singh		23-June-11	CSR00-05422     Creating Change Order From Existing order in Billing Queue
//[00055]	 Ashutosh Singh		05-July-11	CSR00-05422     Document tracking for Diffrent Role
//[030]	 LAWKUSH		25-July-11	CSR00-05422     fetching product specific po's and po's 
//[031]	 LAWKUSH		18-Aug-11	CSR00-05422     For Copy Charge Validation
//[032]	 MANISHA		18-Aug-11	CSR00-05422     Delete Services in Change Order
//[033]	 ROHIT VERMA	07-NOV-11	CSR00-05422		Report for Copy & Cancal 
//[035]  SAURABH SINGH  04-Nov-11   CSR00-05422		Creating Cancel&Copy Order
//[036]	 SAURABH SINGH	10-Nov-11	CSR00-05422		Check For Hardware LineItem , Not Allow More Than 1 per service
//[037]	 SAURABH SINGH	27-Dec-2011	CSR00-05422		Restricting User from Selecting Network Location For Non M6 Line Item
//[038]	 SAURABH SINGH	28-Dec-2011	CSR00-05422		Showing ServiceId and ServiceName at the top of Product Catelog
//[041]	 SAURABH SINGH	20-Feb-2012	CSR00-05422		Closing Order Already Opened during approving order and Closing page
//[042]  ASHUTOSH       11-Jun-12	CSR00-05422		Copy Service Product in New and Change Order Using Common Function
//[043]	 ASHUTOSH SINGH	19-JUN-2012	CSR00-05422		Validation for Child node 17 parameter should be different Than Existing Child Node
//[044]	 Vijay Pathak	30-Jan-2013			        getting Change reason on the behalf of serviceId
//[045]	 Manisha Garg	07-FEB-13	CSR00-05422		tO REVERT THE DISCONNECT cHARGES
//[046]	 Rohit Verma	19-Feb-2013	CSR00-07480		New Interface to show Mapping & Redirection Between LSI
//[047]	 Rohit Verma	26-Feb-2013	CSR00-07480		Check for duplicay of Dail Comid, Toll Free No, IRN No and TGNO No
//[048]  Anil Kumar		07-Mar-2013 ArborMigration	Service Level Component does not allow on NO FX SI Lineitem 
//[15032013017] Rampratap 14-03-13 added for count tolal line items selected.
//[049]    Lawkush  30-MAR-13  BUG ID TRNG22032013005 fixed
//[050] Rohit Verma		1-Mar-2013	CSR00-08440		Adding  New Method for Cancel Hardware Line item GUI
//[051] Saurabh Singh	22-Mar-2013	CSR00-08440		Getting data for Cancelled Hardware Line item to display
//[052]		Neelesh		6-June-13	TRNG22032013037     For Mulitple Opportunity
//[053]	Rohit Verma					CSRIT-09112		Method for ParallelUpgrade LSI No
//[055] Santosh.S	    29-Nov-13   CSR-IT-09463   	2 New Methods added for Displaying and Savinf Advance Payment against Line item
//[056]	Rohit Verma		26-Nov-13	CSR-IT-09463		Validation to input Advance Payment details at line item level 

//[054]                Gunjan Singla                    added method populatereportusagedetails                           
//[055] 			   Gunjan Singla                neworderdto is passed as argument instead of orderno only in functions cancelOrderForClep and cancelOrder 
//[056]  Gunjan Singla                   added method checkCopiedNode for displaying alert while validating order without saving line
//[059]  Gunjan Singla                   added method PopulateReasonForCancel() method
//[060] VIPIN SAHARIA 04-06-2014 Added logic for Fx_ChargeID required for extra logic DC_COLO_SERVICE_TAX Charge (Hardware DC)
//[0099] Nagarjuna      10-March-2014    Pm Welcome Mail .
//[0100] Ravneet      14-Jul-2014    Copy Orders and Create Existing Order Performace Checks
//[110]   Gunjan         03-Jul-2014      Order Cancellation Post Publish
//[111]	VIPIN SAHARIA 17-07-2014 Added methods to get charge details for Service - Sales charges validation for DC hardware products.
//[112] VIPIN SAHARIA 05-09-2014	Added logic to show alert while clicking SendToCOPC after SED Approval
//[113] Vipin Saharia	Code to chk if PO present while copying lines in change order (for PM n SED)
//[114] Pankaj Thakur    26-nov-14        added a method   getSpecialCharValue()
//[215] Raveendra      23-Jan-2015    validation for selected services 
//[116] Gunjan Singla    06-Jan-15     20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection
//[117]	 Raveendra Kumar	25-Mar-15	20150225-R1-021110     Order transfer rights required on GUI for AM Role

//[119] Pankaj Thakur        3-jun-2015  calling a method getOrderCreationSource() to validate  the  OrderCreationSource 
//[118]	 Anil Kumar			14-May-15	20141219_R2_020936     Fetch Standard reason, Insert Standard reason and Update Standard reason
//[119] Raveendra 06-july-2015 20141219-R2-020936-Billing Efficiency Program_drop2  Auto Billing Trigger
//[122]  Rahul Tandon      20150225-R1-021112-Modification in Access Matrix in IB2B
//[121] VIPIN SAHARIA 11th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Reverting MBIC CC mapping as per L3-VCS mapping
//[123] RAHUL TANDON 3-Sept-2015 CBR_20150603-R2-021385-Charge End Date and Short Billing : Alert on order validate if order has charges which have charges which can be re-disconnect
//[124]	Gunjan Singla	24-Nov-2015		M2M:validations on validate order button 
//[125] Priya Gupta	    31-MAR-2016	   Order Approves Twice in PD orders.
//[127] Priya Gupta    CSR-20160301-XX-022139 -Auto billing for RR orders & other Enhancements
//[128] Nancy Singla  16-May-2016  20160301-xx-022139  New functions added for retrieving/updating  Assigned/Deassigned Standard Reasons
//[129] Nancy Singla  1-JUNE-2016  20160301-xx-022139  New function added to get the saved ePcn No.import java.io.IOException;
//[130] Gunjan Singla 19-Oct-2016  20160219-XX-022117  ANG Bandwidth Correction in iB2B and M6
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.myfaces.config.impl.digester.elements.MapEntries.Entry;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.fx.dto.AcctDTO;
import com.ibm.fx.dto.ChargesDto;
import com.ibm.fx.dto.ComponentDTO;
import com.ibm.fx.mq.MigrationUtilityWrapper;
import com.ibm.ioes.actions.NewOrderValidation;
import com.ibm.ioes.actions.ViewOrderAction;
import com.ibm.ioes.beans.ArchivalReportBean;
import com.ibm.ioes.beans.DefaultBean;
import com.ibm.ioes.beans.NewOrderBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.bulkupload.dao.ErrorFileDaoImpl;
import com.ibm.ioes.bulkupload.dto.TransactionTemplateDto;
import com.ibm.ioes.clep.CLEPUtility;
import com.ibm.ioes.clep.CLEPXmlDto;
import com.ibm.ioes.clep.SendAndRecieveMessage;
import com.ibm.ioes.dao.ArchiveIb2bOrdersDao;
import com.ibm.ioes.dao.BCPAddressDao;
import com.ibm.ioes.dao.CommonBaseDao;
import com.ibm.ioes.dao.DispatchAddressDao;
import com.ibm.ioes.dao.EscalationDao;
import com.ibm.ioes.dao.FeildValidationUtilityDAO;
import com.ibm.ioes.dao.LocationDetailDao;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.dao.ReportsDao;
import com.ibm.ioes.dao.SCMDao;
import com.ibm.ioes.dao.UniversalWorkQueueDao;
import com.ibm.ioes.dao.ViewOrderDao;
import com.ibm.ioes.dbhelper.LabelValue;
import com.ibm.ioes.ecrm.FetchAccountFromCRM;
import com.ibm.ioes.escalation.dto.EscalationDto;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.forms.AdvancePaymentDTO;
import com.ibm.ioes.forms.BCPAddressDto;
import com.ibm.ioes.forms.ChannelPartnerDto;
import com.ibm.ioes.forms.ChargeComponentDTO;
import com.ibm.ioes.forms.ChargeDetailsSCM;
import com.ibm.ioes.forms.ChargesDetailDto;
import com.ibm.ioes.forms.CityDto;
import com.ibm.ioes.forms.CommonDTO;
import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.forms.ContactDTO;
import com.ibm.ioes.forms.CountryDto;
import com.ibm.ioes.forms.CurrencyChangeDto;
import com.ibm.ioes.forms.DelayReasonDTO;
import com.ibm.ioes.forms.DisconnectOrderDto;
import com.ibm.ioes.forms.DispatchAddressDto;
import com.ibm.ioes.forms.Entity;
import com.ibm.ioes.forms.FieldAttibuteDTO;
import com.ibm.ioes.forms.FieldEnginnerDto;
import com.ibm.ioes.forms.LSICancellationDto;
import com.ibm.ioes.forms.LineItemDTO;
import com.ibm.ioes.forms.LineItemValueDTO;
import com.ibm.ioes.forms.LocationDetailDto;
import com.ibm.ioes.forms.M6OrderStatusDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.OpportunityDTO;
import com.ibm.ioes.forms.OrderHeaderDTO;
import com.ibm.ioes.forms.PagingCommonDto;
import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.forms.ParallelUpgradeValidationDto;
import com.ibm.ioes.forms.PoDetailsDTO;
import com.ibm.ioes.forms.ProductCatelogDTO;
import com.ibm.ioes.forms.ReportsDto;
import com.ibm.ioes.forms.RoleSectionDetailDTO;
import com.ibm.ioes.forms.SCMDto;
import com.ibm.ioes.forms.SITransferDto;
import com.ibm.ioes.forms.ServiceLineDTO;
import com.ibm.ioes.forms.StateDto;
import com.ibm.ioes.forms.TreeViewDto;
import com.ibm.ioes.forms.UserAccessMatrixDto;
import com.ibm.ioes.model.ArchivalModel;
import com.ibm.ioes.model.BCPAddressModel;
import com.ibm.ioes.model.CopyOrderModel;
import com.ibm.ioes.model.LocationDetailModel;
import com.ibm.ioes.model.MigrationUtility;
import com.ibm.ioes.model.NewOrderModel;
import com.ibm.ioes.model.NewOrderModel.M2MClass;
import com.ibm.ioes.model.NewOrderModel.RRAutoTriggerClass;
import com.ibm.ioes.model.NewOrderModel.ValidateOrderAlertDTO;
import com.ibm.ioes.model.NewOrderModel.WorkFlowAttachDetails;
import com.ibm.ioes.model.SegmentReasonMappingService;
import com.ibm.ioes.newdesign.dto.DisconnectChangeOrdeReportDTO;
import com.ibm.ioes.newdesign.dto.OpportunityDto;
import com.ibm.ioes.newdesign.dto.OrderDto;
import com.ibm.ioes.newdesign.dto.RateRenewalReportDTO;
import com.ibm.ioes.newdesign.dto.ServiceDto;
import com.ibm.ioes.newdesign.dto.ChangeReasonMapping;
import com.ibm.ioes.newdesign.dto.StandardReason;
import com.ibm.ioes.newdesign.form.SelectOpportunityPageForm;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.IoesProductDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflow;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage;
import com.ibm.ioes.npd.hibernate.beans.TmWorkflowtasks;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.dao.AttachEditProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.HolidayMasterDao;
import com.ibm.ioes.npd.hibernate.dao.IssuesCaptureDao;
import com.ibm.ioes.npd.hibernate.dao.MasterProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.MyToDOListDaoImpl;
import com.ibm.ioes.npd.hibernate.dao.NpdUserDao;
import com.ibm.ioes.npd.hibernate.dao.ReferenceDocDao;
import com.ibm.ioes.npd.hibernate.dao.RisksCaptureDao;
import com.ibm.ioes.npd.model.AccessMatrixModel;
import com.ibm.ioes.npd.model.AttachEditProjectPlanModel;
import com.ibm.ioes.npd.model.MasterProjectPlanModel;
import com.ibm.ioes.npd.model.MeetingModel;
import com.ibm.ioes.npd.utilities.CommonUtilities;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.service.LSICancellationService;
import com.ibm.ioes.service.LSICancellationService.LsiCancellationTableResult;
import com.ibm.ioes.service.UtilityService;

public class AjaxHelper {
	private static Logger logger = Logger.getLogger(AjaxHelper.class);

	ServletContext context;

	public AjaxHelper(ServletContext context) {
		this.context = context;
		context.setAttribute(AppConstants.APP_SESSION, new HashMap<String, HttpSession>());
	}
	
	//	[001] Start
	//  public NewOrderDto insertService(NewOrderDto objDto) throws Exception
	
	public ServiceLineDTO insertService(ServiceLineDTO objDto, String sessionID) throws Exception
	//[001] End
	{
		NewOrderDao objDao = new NewOrderDao();
		ServiceLineDTO objRetDto= null; 
		try {
			//[001] Start
			HashMap<String, HttpSession> hmap = (HashMap<String, HttpSession>) context
					.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionID);		
			UserInfoDto objUserDto = (UserInfoDto) session
					.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			//objRetDto = objDao.insertService(objDto);
			objRetDto = objDao.insertService(objDto, Long.valueOf(objUserDto
					.getEmployeeId()));
			//[001] End
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	
	//tO CHECK podetail no and date
	
	public long getSelectedProductId(long serviceID)
	{
		long productId = 0;
		NewOrderDao dao=new NewOrderDao();
		productId=dao.getSelectedProductId(serviceID);
		return productId;
	}
	public ArrayList<ViewOrderDto> fnGetDisconnectionDateData(ViewOrderDto dto) throws IOESException
	{
	
		
		ArrayList<ViewOrderDto> result=null;
		Connection conn = null;
		try 
		{
			conn=DbConnection.getConnectionObject();
		   NewOrderDao dao=new NewOrderDao();
			result=dao.fnGetDisconnectionDateData(dto);
			
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
		
	public ArrayList<ViewOrderDto> populateReasonForChange(
			int interfaceStdReason) throws IOESException {
	
		ArrayList<ViewOrderDto> result=null;
		Connection conn = null;
		try {
			conn=DbConnection.getConnectionObject();
		   NewOrderDao dao=new NewOrderDao();
			result=dao.populateReasonForChange(interfaceStdReason);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
		// Modified by Shubhranshu
	public ArrayList<ViewOrderDto> populateMappedChangeReason(int accNo,int subChangeTypeId,
			int interfaceStdReason) throws IOESException {
	
		ArrayList<ViewOrderDto> result=null;
		Connection conn = null;
		try {
			conn=DbConnection.getConnectionObject();
		   NewOrderDao dao=new NewOrderDao();
			result=dao.populateMappedChangeReason(accNo,subChangeTypeId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//[128] start
	public SegmentReasonMappingService.SegmentReasonMappingDto fillMappedReasonForChange( ChangeReasonMapping objDto ) throws Exception
	{
		SegmentReasonMappingService.SegmentReasonMappingDto assignedUnAssignedReasonsDto=null ;
		SegmentReasonMappingService getMappedReasonsData= new SegmentReasonMappingService();
		long custsegm= objDto.getCustomerSegmentId();
		long subchgtype= objDto.getSubChangeTypeId();
		try
		{
			assignedUnAssignedReasonsDto= getMappedReasonsData.getAssignedUnassignedReasons(custsegm, subchgtype); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return assignedUnAssignedReasonsDto ;
	}

	public String updateStandardReasonMapping (ChangeReasonMapping objDto, long[] assignedReasonsId )
	{
		String status=null;
		try
		{
			long[] assignedIds= assignedReasonsId;
			SegmentReasonMappingService getdata= new SegmentReasonMappingService();
			status= getdata.updateStandardReasonMapping(objDto,assignedIds);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return status;
	}
	//[128] end
	public ArrayList<LineItemDTO> populateLineItem() throws IOESException {
		ArrayList<LineItemDTO> result=null;
		Connection conn = null;
		try {
			conn=DbConnection.getConnectionObject();
		   NewOrderDaoExt dao=new NewOrderDaoExt();
			result=dao.populateLineItem();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public ArrayList<LineItemDTO> populateSubLineItem() throws IOESException
	{
		ArrayList<LineItemDTO> result=null;
		Connection conn = null;
		try 
		{
			conn=DbConnection.getConnectionObject();
		   NewOrderDaoExt dao=new NewOrderDaoExt();
			result=dao.populateSubLineItem();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//008 start
	public  ArrayList<PoDetailsDTO>  validatePoDetailNONDATE_FORNEW(PoDetailsDTO dto) throws IOESException
	{
	
		logger.info("In fnReTryAccountCreateInFx");
		ArrayList<PoDetailsDTO> list;
		Connection conn = null;
		try 
		{
			conn=DbConnection.getConnectionObject();
		   NewOrderDao dao=new NewOrderDao();
		   list=dao.validatePoDetailNONDATE_FORNEW(dto);
			
		
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage()+ " Exception occured in fnReTryAccountCreateInFx method of "+ this.getClass().getSimpleName());
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "", "", "", true, true);
			}
		}
		return list;
	}
	//008 end
	//009 start
			
	public  ArrayList<NewOrderDto>  validatePoDetailNONDATE_FORCHANGE(NewOrderDto dto) throws IOESException
	{
	
		logger.info("In fnReTryAccountCreateInFx");
		ArrayList<NewOrderDto> list;
		Connection conn = null;
		try 
		{
			conn=DbConnection.getConnectionObject();
		   NewOrderDao dao=new NewOrderDao();
		   list=dao.validatePoDetailNONDATE_FORCHANGE(dto);
			
		
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage()+ " Exception occured in fnReTryAccountCreateInFx method of "+ this.getClass().getSimpleName());
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "", "", "", true, true);
			}
		}
		return list;
	}
				
	
	//009 end
	
	
	public ArrayList<NewOrderDto> fnGetChargeTypeDetailsinFx(String dto) throws IOESException
	{
	
		logger.info("In fnReTryAccountCreateInFx");
		ArrayList<NewOrderDto> result;
		Connection conn = null;
		try 
		{
			conn=DbConnection.getConnectionObject();
		   NewOrderDao dao=new NewOrderDao();
			result=dao.getChargeTypeDetailsinFx(dto);
			
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage()+ " Exception occured in fnReTryAccountCreateInFx method of "+ this.getClass().getSimpleName());
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "", "", "", true, true);
			}
		}
		return result;
	}
		
	
	
	
	
	public ArrayList<StateDto>	fetchStates(CountryDto countryDto) throws IOESException
	{
		ArrayList<StateDto> states = null;
		Connection conn=null;
		try
		{
			ArrayList<StateDto> errorList = LocationDetailModel.validateCountrySeleted(countryDto);
			if(errorList!=null && errorList.size()>0)
			{
				return errorList;
			}
			conn = DbConnection.getConnectionObject();
			return CommonBaseDao.getAllStates(conn,countryDto);
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "getAllStates", "AjaxHelper", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "getAllStates", "AjaxHelper", "", true, true);
			}
		}
	}
	
	
	
	
	
	public ArrayList<CityDto> fetchCities(StateDto stateDto) throws IOESException
	{
		ArrayList<CityDto> cities = null;
		Connection conn=null;
		try
		{
			ArrayList<CityDto> errorList = LocationDetailModel.validateStateSeleted(stateDto);
			if(errorList!=null && errorList.size()>0)
			{
				return errorList;
			}
			conn = DbConnection.getConnectionObject();
			return CommonBaseDao.getAllCities(conn,stateDto);
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "getAllCities", "AjaxHelper", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "getAllCities", "AjaxHelper", "", true, true);
			}
		}
	}
//	get service product for modification
	public String isGetModify(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		String isModify="";
		try 
		{
			isModify = objDao.isGetModify(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isModify;
	}
	

	public int getChangeSubTypeID(NewOrderDto objDto) throws Exception
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		int changeSubTypeID=0;
		try 
		{
			changeSubTypeID = objDao.getChangeSubTypeID(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return changeSubTypeID;
	}
	//[00044] Start Added by Ashutosh 
	public ArrayList<NewOrderDto> getChangeType() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> lstChangeType = new ArrayList<NewOrderDto>();
		int changeSubTypeID=0;
		try 
		{
			lstChangeType = objDao.getChangeType();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstChangeType;
	}
	//Added by Ashutosh for creating Change Order from Existing Order Number
	public long getCreatedChangeOrderNumber(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		long cretadeOrderNO=0; 
		int changeSubTypeID=0;
		try 
		{
			cretadeOrderNO = objDao.genrateChangeOrderNumber(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cretadeOrderNO;
	}
	//[00044] End
	//Used to Populate Service List in a Pop up
	public ArrayList<ServiceLineDTO> poulateServiceList(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ServiceLineDTO> lstRetVal = null;
		try 
		{
			lstRetVal = objDao.fetechServiceList(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstRetVal;
	}
	
//	Used to Populate Zone List in a Combo
	//public ArrayList<NewOrderDto> populateZoneList(long accId) throws Exception
	public ArrayList<OrderHeaderDTO> populateZoneList(long regionId) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<OrderHeaderDTO> zoneList = null; 
		try 
		{
			zoneList = objDao.fetechZoneList(regionId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zoneList;
	}
	
	public ArrayList<OrderHeaderDTO> populateChannelPartnerList(Long orderNo) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		//ArrayList<OrderHeaderDTO> channelPartnerList = null; 
		ArrayList list = null;
		try 
		{
			/*channelPartnerList*/ 
			list= objDao.populateChannelPartnerList(orderNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
// Used to populate Quote No in combo
	public ArrayList<OrderHeaderDTO> populateQuoteNoList(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<OrderHeaderDTO> quoteList =null; 
		try 
		{
			quoteList = objDao.fetechQuoteNoList(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quoteList;
	}
	//Fill Salutation Combo
	public ArrayList<ContactDTO> populateSalutation(ContactDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ContactDTO> salutationList = new ArrayList<ContactDTO>(); 
		try 
		{
			salutationList = objDao.fetechSalutationList(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return salutationList;
	}
	
	//Used to Update Service Attribute for Service ID
	//[001] Start
	//[116] start
	//public NewOrderDto updateServiceAttribute(NewOrderDto objDto) throws Exception
	public ArrayList<ParallelUpgradeValidationDto> validateAndSaveServiceAttribute(NewOrderDto objDto, String sessionID) throws Exception
	//[001] End
	{	
		ArrayList<ParallelUpgradeValidationDto> objRetDto=new ArrayList<ParallelUpgradeValidationDto>();
		NewOrderModel model=new NewOrderModel();
	
		try {
			//[001] Start
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionID);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			objRetDto=model.manageParallelUpgradeLSI(objDto,Long.valueOf(objUserDto.getEmployeeId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	
	public ServiceLineDTO fetchServiceAttribute(ServiceLineDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ServiceLineDTO saDto=null;
		try 
		{
			saDto = objDao.fetchServiceAttribute(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}

	public ArrayList<FieldAttibuteDTO> populateServiceAttributeList(ServiceLineDTO objDto,String sessionid) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<FieldAttibuteDTO> saDto=new ArrayList<FieldAttibuteDTO>();
		
		try 
		{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			saDto = objDao.fetchServiceAttributeList(objDto,objUserDto.getUserRoleId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	
	public String validatePoDetails(NewOrderDto poData)
	{
		NewOrderValidation validation = new NewOrderValidation();
		String errors = validation.validatePoDetails(poData);
		return errors;
		
	}
	public String validateContactDetails(ContactDTO[] contactDatas){
		NewOrderValidation validation = new NewOrderValidation();
		String errors="";
		String error;
		for(ContactDTO contactDTO : contactDatas){
			error = validation.validateContactDetails(contactDTO);
			if(null != error && !error.trim().equals(""))
				if(errors.equals(""))
					errors= error;
				else
					errors= errors+"\n" + error;
		}
		return errors;		
	}
	public ArrayList<ContactDTO>  searchContactType(ContactDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ContactDTO> listSearchContactTypes= null;
		try 
		{
			listSearchContactTypes = objDao.searchContactTypes(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchContactTypes;
	}
	public ArrayList<ContactDTO>  searchSalutationName(ContactDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ContactDTO> listSalutationName= null;
		try 
		{
			listSalutationName = objDao.searchSalutationList(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSalutationName;
	}
	//Start[027]
	public ArrayList<NewOrderDto>  searchCountryName(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listSearchCountryName= new ArrayList<NewOrderDto>();
		try 
		{
			listSearchCountryName = objDao.searchCountryList(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchCountryName;
	}
	public ArrayList<NewOrderDto>  searchCountryName1(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listSearchCountryName= new ArrayList<NewOrderDto>();
		try 
		{
			listSearchCountryName = objDao.searchCountryList1(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchCountryName;
	}
	public ArrayList<NewOrderDto>  searchStateName(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listSearchStateName= new ArrayList<NewOrderDto>();
		try 
		{
			listSearchStateName = objDao.searchStateList(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchStateName;
	}
	public ArrayList<ContactDTO>  searchStateName1(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ContactDTO> listSearchStateName= null;
		try 
		{
			listSearchStateName = objDao.searchStateList1(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchStateName;
	}
	public ArrayList<NewOrderDto>  searchCityName(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listSearchCityName= new ArrayList<NewOrderDto>();
		try 
		{
			listSearchCityName = objDao.searchCityList(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchCityName;
	}
	//End[027]
	public ArrayList<ContactDTO>  searchCityName1(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ContactDTO> listSearchCityName= null;
		try 
		{
			listSearchCityName = objDao.searchCityList1(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchCityName;
	}
	public ArrayList<NewOrderDto>  searchCityName2(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listSearchCityName= new ArrayList<NewOrderDto>();
		try 
		{
			listSearchCityName = objDao.searchCityList2(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchCityName;
	}
	//[013] Start
	public ArrayList<NewOrderDto>  searchPincode(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listSearchPincode= new ArrayList<NewOrderDto>();
		try 
		{
			listSearchPincode = objDao.searchPincodeList(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listSearchPincode;
	}
	//[013]End
	public ArrayList<NewOrderDto>  searchOrderStatus(NewOrderDto objDto) throws Exception
	{
		ReportsDao objDao = new ReportsDao();
		ArrayList<NewOrderDto> listOrderStatues= new ArrayList<NewOrderDto>();
		try 
		{
			listOrderStatues = objDao.searchOrderStatus(objDto);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listOrderStatues;
	}
	// [052] Start
	public SelectOpportunityPageForm  opportunityType(OpportunityDto objDto, PagingCommonDto pagingDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		SelectOpportunityPageForm listOpportunity= new SelectOpportunityPageForm();
		try 
		{
			listOpportunity = objDao.getOpportunityList(objDto, pagingDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOpportunity;
	}
	

	public String saveMultipleOpportunity(String orderNo, String opportunityId , String salesforceOpportunityid, String ePCNNo,String func)throws Exception
	{
		
		NewOrderDao objDao = new NewOrderDao();
		String retStatus = "";
		try 
		{
			
			//retStatus = objDao.saveMultipleOpportunity(orderNo, opportunityId, salesforceOpportunityid);
			retStatus = objDao.saveMultipleOpportunity(orderNo, opportunityId, salesforceOpportunityid,ePCNNo,func);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStatus;
	}
	public ArrayList<OpportunityDto>  getOpportunitySaved(String orderNo) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<OpportunityDto> listOpportunity= new ArrayList<OpportunityDto>();
		try 
		{
			listOpportunity = objDao.getOpportunitySaved(orderNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOpportunity;
	}
	
	
	// [052]	End
	
	//[129] start
	public String getEpcnSaved(String orderNo) throws Exception
	{
		NewOrderDao objDao= new NewOrderDao();
		String getEpcn= "";
		try
		{
			getEpcn=objDao.getEpcnSaved(orderNo);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return getEpcn;
	}
	//[129] end
	
	public ArrayList<NewOrderDto>  currencyType(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listCurrencyTypes= new ArrayList<NewOrderDto>();
		try 
		{
			listCurrencyTypes = objDao.getCurrencyDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCurrencyTypes;
	}
	
	public ArrayList<NewOrderDto>  getFieldEngineer(NewOrderDto newOrderDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listChannelPartnerTypes= new ArrayList<NewOrderDto>();
		try 
		{
			listChannelPartnerTypes = objDao.getFieldEngineer(newOrderDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listChannelPartnerTypes;
	}
	//pankaj Start
	public ArrayList<FieldEnginnerDto>  getFieldEngineerList(Long partnerId) throws Exception
	{
		
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<FieldEnginnerDto> listFeDetails= new ArrayList<FieldEnginnerDto>();
		try 
		{
			listFeDetails = objDao.getFieldEngineerList(partnerId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listFeDetails;
	}
	
	public ArrayList<NewOrderDto> getChannelPartnerList(PagingDto objDto) throws Exception
	{
		System.out.println("getChannelPartnerList");
		NewOrderDao objDao = new NewOrderDao();
		//ArrayList<OrderHeaderDTO> channelPartnerList = null; 
		ArrayList list = null;
		try 
		{
			/*channelPartnerList*/ 
			list= objDao.getChannelPartnerList(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	//pankaj end
	
	public ArrayList<NewOrderDto>  getSourceDetails(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listSourceTypes= new ArrayList<NewOrderDto>();
		try 
		{
			listSourceTypes = objDao.getSourceDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listSourceTypes;
	}
	public ArrayList<OrderHeaderDTO>  getAccountDetails(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList errors=new ArrayList();
		objDto.setAccountName(Utility.trimIfNotNull(objDto.getAccountName()));
		
		String accountName=objDto.getAccountName();
		Utility.validateValue(new ValidationBean()
					.loadValidationBean_Maxlength("Account Name", accountName, 150),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
		
		
		if(errors!=null && errors.size()>0)//During Server Side Validation
		{
			NewOrderDto dto=new NewOrderDto();
			dto.setErrors_Validation(errors);
			ArrayList<NewOrderDto> errorList=new ArrayList<NewOrderDto>();
			errorList.add(dto);
			//return errorList;
		}
		ArrayList<OrderHeaderDTO> listAccountTypes= null;
		try 
		{
			listAccountTypes = objDao.getAccountDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listAccountTypes;
	}
	
	public ArrayList<PoDetailsDTO> populatePODetailsNo(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<PoDetailsDTO> listPODetails= null;
		try 
		{
			long orderNo=objDto.getPoNumber();
			listPODetails = objDao.getPODetail(orderNo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listPODetails;
	}
	
	public ArrayList<PoDetailsDTO> populateEntity(NewOrderDto objDto) throws Exception{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<PoDetailsDTO> listPOEntity= new ArrayList<PoDetailsDTO>();
		try{
			String poDetailNo=objDto.getPoDetailNo();
			listPOEntity = objDao.getPOEntity(poDetailNo);
		}catch (Exception e){
			e.printStackTrace();
		}
		return listPOEntity;
	}
	
	public ArrayList<ProductCatelogDTO> populateLicCompany(NewOrderDto objDto) throws Exception{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listPOEntity= new ArrayList<ProductCatelogDTO>();
		try{
			listPOEntity = objDao.populateLicCompany(objDto.getEntityID(),objDto.getServiceDetailID(),objDto.getProductId());
		}catch (Exception e){
			e.printStackTrace();
		}
		return listPOEntity;
	}
	
	public ArrayList<NewOrderDto> populateCustPoNo(NewOrderDto objDto) throws Exception //by Saurabh
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listPOEntity= new ArrayList<NewOrderDto>();
		try 
		{
			listPOEntity = objDao.populateCustPoDetailNo(objDto.getPodetailID());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listPOEntity;
	}
	
	//[021] Start
	public ArrayList<NewOrderDto> populateOldLocationDetails (NewOrderDto objDto) throws Exception 
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listOldLocationDetails= new ArrayList<NewOrderDto>();
		try 
		{
			listOldLocationDetails = objDao.populateOldLocationDetails(objDto.getServiceId());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listOldLocationDetails;
	}
	//[021] End
	
	public ArrayList<ProductCatelogDTO> populateStoreList(int licenseCo) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listStore= new ArrayList<ProductCatelogDTO>();
		try 
		{
				listStore = objDao.populateStoreList(licenseCo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listStore;
	}
	
	public ArrayList<NewOrderDto> populateDispatchCode(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listStore= new ArrayList<NewOrderDto>();
		try 
		{
			listStore = objDao.populateDispatchCode(objDto.getAccountID());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listStore;
	}
	
	public ArrayList<ProductCatelogDTO> populateDispatchAddress(ProductCatelogDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listStore= new ArrayList<ProductCatelogDTO>();
		try 
		{
			listStore = objDao.populateDispatchAddress(objDto.getDispatchAddressID());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listStore;
	}
	
	
	public int getCountContactNumber(NewOrderDto objDto)throws Exception 
	 {
		NewOrderDao objDao = new NewOrderDao();
		int poCount = 0;
		try {
			poCount = objDao.getCountContactNumber(objDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poCount;
	}
	
	public ArrayList<ProductCatelogDTO> populateBCP(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listStore= new ArrayList<ProductCatelogDTO>();
		try 
		{
			listStore = objDao.populateBCP(objDto.getAccountID());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listStore;
	}
	
	public ArrayList<ProductCatelogDTO> populateBCPDetails(ProductCatelogDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listBCPDetails= new ArrayList<ProductCatelogDTO>();
		try 
		{
			listBCPDetails = objDao.populateBCPDetails(objDto.getBcpID());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listBCPDetails;
	}
	
	public ArrayList<NewOrderDto> populateBCPSecDetails(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listBCPDetails= new ArrayList<NewOrderDto>();
		try 
		{
			listBCPDetails = objDao.populateBCPSecDetails(objDto.getBcpID());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listBCPDetails;
	}
	public ArrayList<ProductCatelogDTO> populateprmBCPDetails(ProductCatelogDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listBCPDetails= new ArrayList<ProductCatelogDTO>();
		try 
		{
			listBCPDetails = objDao.populateprmBCPDetails(objDto.getBcpID());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listBCPDetails;
	}

	public ArrayList<NewOrderDto> populatePNLocation() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listStore= new ArrayList<NewOrderDto>();
		try 
		{
			listStore = objDao.populatePNLocation();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listStore;
	}
	
	public ArrayList<NewOrderDto> populateSNLocation() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listStore= new ArrayList<NewOrderDto>();
		try 
		{
			listStore = objDao.populateSNLocation();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listStore;
	}
	
	public ArrayList<NewOrderDto> populateNPLocationAddress(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listNPLocationAddress= new ArrayList<NewOrderDto>();
		try 
		{
			listNPLocationAddress = objDao.populateNPLocationAddress(objDto.getPlocationCode());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listNPLocationAddress;
	}
	
	public ArrayList<NewOrderDto> populateNSLocationAddress(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listNPLocationAddress= new ArrayList<NewOrderDto>();
		try 
		{
			listNPLocationAddress = objDao.populateNSLocationAddress(objDto.getPlocationCode());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listNPLocationAddress;
	}
	//[001] Start
	//public NewOrderDto insertTProductAttDetail(NewOrderDto objDto)	throws Exception
	public NewOrderDto insertTProductAttDetail(NewOrderDto objDto,String sessionID)	throws Exception
	//[001] End
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto dto=new NewOrderDto();
		int status=0;
		try 
		{
				ArrayList<String> validationErrors=AjaxValidation.validateServicePrdAttValues(objDto);
				if(validationErrors!=null && validationErrors.size()>0)
				{
					dto.setErrors_Validation(validationErrors);
					return dto;
				}
				//[001] Start
				HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
				HttpSession session = hmap.get(sessionID);		
				UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				//status = objDao.insertTproductATTDetail(objDto);
				status = objDao.insertTproductATTDetail(objDto,Long.valueOf(objUserDto.getEmployeeId()));
				//[001] End
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		dto.setStatus(status);
		return dto;
	}
	//[001] Start
	//public NewOrderDto insertProductCatelog(NewOrderDto objDto) throws Exception
	public NewOrderDto insertProductCatelog(NewOrderDto objDto,String sessionID) throws Exception
	//[001] End
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto RetObjDto=new NewOrderDto(); 
		int completeStatus=0;
		AjaxValidation objValidate = new AjaxValidation();
		ArrayList lstValidate = new ArrayList();
		try 
		{
		if (objDto.getBillingBCPId().equalsIgnoreCase("")) 	objDto.setBillingBCPId("0");
	//	if (objDto.getChargeType()==-1) objDto.setChargeType(0);
		//if(objDto.getChargeFrequency().equalsIgnoreCase("")) objDto.setChargeFrequency("0");	
		if(objDto.getSaleNature().equalsIgnoreCase("")) objDto.setSaleNature("0");
		if(objDto.getSaleType().equalsIgnoreCase("")) objDto.setSaleType("0");
		if(objDto.getHardwareType().equalsIgnoreCase("")) objDto.setHardwareType("0");
		if(objDto.getFormAvailable().equalsIgnoreCase("")) objDto.setFormAvailable("0");
		if(objDto.getStartDate()==null || objDto.getStartDate().equalsIgnoreCase("")) objDto.setStartDate("00/00/0000");
		if(objDto.getEndDate()==null || objDto.getEndDate().equalsIgnoreCase("")) objDto.setEndDate("00/00/0000");
		if(String.valueOf(objDto.getCommitmentPeriod()).equalsIgnoreCase("")) objDto.setCommitmentPeriod(0);
		if(objDto.getPenaltyClause().equalsIgnoreCase("")) objDto.setPenaltyClause("0");
		if(objDto.getStartHWDateLogic().equalsIgnoreCase("")) objDto.setStartHWDateLogic("0");
		if(objDto.getEndHWDateLogic().equalsIgnoreCase("")) objDto.setEndHWDateLogic("0");
		if(String.valueOf(objDto.getWarrentyMonths()).equalsIgnoreCase("")) objDto.setWarrentyMonths(0);
	//	if(objDto.getPrincipalAmount()==null) objDto.setPrincipalAmount(0.0);
	//	if(objDto.getInterestRate()==null) objDto.setInterestRate(0.0);
		/*Start[026]
		if(objDto.getServiceTypeId()==4){
			objDto.setSelectedSNLocation(1);			
		}
		*/
		//End[026]
		//[001] Start
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionID);		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		//[001] End
		
			objDto.setMode(NewOrderDto.MODE_INSERT);
			lstValidate = objValidate.ValidateProductCatelog(objDto);
			if(lstValidate.toString().equalsIgnoreCase("[]"))
				//[001] Start
				//status = objDao.insertTproductATTDetail(objDto);
				RetObjDto = objDao.insertProductCatelog(objDto,Long.valueOf(objUserDto.getEmployeeId()));
				//[001] End
			else
				RetObjDto.setErrors(lstValidate);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return RetObjDto;
	}
	public ArrayList<String> validateProductNameLicCompMap(String orderNo)
	{
		NewOrderDao dao=new NewOrderDao();
		ArrayList<String> list=dao.validateProductNameLicCompMap(orderNo);
		return list;
	}
	
	public ArrayList<ServiceLineDTO> populateSearch(PagingDto objDto)
	throws Exception
	{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<ServiceLineDTO> lstRetVal = new ArrayList<ServiceLineDTO>();
	ArrayList lstError = new ArrayList();
	ServiceLineDTO objRetDto =new ServiceLineDTO();
		try {
			lstError = AjaxValidation.ValidateServiceSearch(objDto);
			if(lstError.size()==0)
				lstRetVal = objDao.getServiceTypeSearch(objDto);
			else
			{
				objRetDto.setErrors(lstError);
				lstRetVal.add(objRetDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstRetVal;
		
	}
	public ViewOrderDto SaveNotes(ViewOrderDto objDto)
	throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ViewOrderDto objRetDto = new ViewOrderDto();
		AjaxValidation objValidate = new AjaxValidation();
		ArrayList lstValidate = new ArrayList();
		try {
			
				lstValidate = objValidate.ValidateNotes(objDto);
				if(lstValidate.toString().equalsIgnoreCase("[]"))
					objRetDto = objDao.SaveNotes(objDto);
				else
					objRetDto.setErrors(lstValidate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
public ViewOrderDto SaveAction(ViewOrderDto objDto , String sessionid)
	throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ViewOrderDto objRetDto = new ViewOrderDto();
		String orderStage ="";
		

		try {
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			/*Vijay commenting some lines of code due to dynamic workflow implementation */
			
			/* if("2".equalsIgnoreCase(objDto.getOrder_creation_source())){
				//Order creation source is CLEP then workflow will be static for New Order				
				objRetDto = objDao.SaveAction(objDto,Long.valueOf(objUserDto.getEmployeeId()));
			}else{ */
			
				objDto.setUserid(objUserDto.getEmployeeId());
				objRetDto=objDao.SaveChangeOrderAction(objDto,null);
			/* }  */
				
			if(AppConstants.AM_ROLE.equalsIgnoreCase(objUserDto.getUserRoleId())){
					orderStage = AppConstants.AM;  //approved by AM
			}	
			else if(AppConstants.PM_ROLE.equalsIgnoreCase(objUserDto.getUserRoleId())){
				orderStage = AppConstants.PM;  //approved by PM
			}
			else if(AppConstants.COPC_ROLE.equalsIgnoreCase(objUserDto.getUserRoleId())){
				orderStage = AppConstants.COPC;  //approved by COPC	
			}
			else if(AppConstants.SED_ROLE.equalsIgnoreCase(objUserDto.getUserRoleId())){
				orderStage = AppConstants.SED;  //approved by SED
			}
			
			int actionId= 2;//it is for rejection
			if("1".equalsIgnoreCase(objDto.getActionId())){
				actionId=1;	//it is for approval
			}
			long orderNo = objDto.getOrderno();
			//below code added by Anil for Send CLEP Response to MPP after successfully Aproved by Last Aproval 
	  //	if(Utility.getAppConfigValue(AppConstants.LAST_ROLE_APROVAL_CLEP_SAAS_ERP).equalsIgnoreCase(objUserDto.getUserRoleId())){
				//if(objRetDto.getIsSuccessApproved()==1 && "1".equalsIgnoreCase(objDto.getActionId())){
				  if(objRetDto.getIsSuccessApproved()==1){
					
					ViewOrderDto objServiceTypeOrdSrcDto = new ViewOrderDto();
					if(objDto.getTaskID()!=null){
						long taskId=Long.valueOf(objDto.getTaskID());
						if(AppConstants.COPC_ROLE.equalsIgnoreCase(objUserDto.getUserRoleId()) && actionId==1){
							/*in case of copc approval, MSG template would be change and order id passed rather than task id */
							objServiceTypeOrdSrcDto=objDao.getServiceType_OrderSourceClepErp(orderNo, 4 /*1*/);	
						}
						else
						{
							objServiceTypeOrdSrcDto=objDao.getServiceType_OrderSourceClepErp(taskId, actionId /*1*/);
						}
						
						//if copc already sent message to mpp,it will not resent
					//     if(objServiceTypeOrdSrcDto.getIsCOPCSentMsgToMPP()!=1){
							if(objServiceTypeOrdSrcDto.getServiceTypeID()==2 && "2".equalsIgnoreCase(objServiceTypeOrdSrcDto.getOrder_creation_source())){
								if(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval()!=null){
									CLEPXmlDto clepXmlDto=new CLEPXmlDto();
									//Vijya setting stage
									clepXmlDto.setStage(orderStage);
									//vijay end
									
									CLEPUtility.SysErr("Message after approval:  "+ objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval() );
									clepXmlDto.setXmlData(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval());
									CLEPUtility.SysErr("Message Request ID Was:- "+objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
									clepXmlDto.setJmsMessageID(objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
									objDao.sendClepResponseToMpp(clepXmlDto,objServiceTypeOrdSrcDto.getClepFileId(),objServiceTypeOrdSrcDto.getOrderno(),"Y");							
								}	
							}
					//  }
					}else{
						CLEPUtility.SysErr("Error: Task Is Not Created!! MPP Response Sending Failed!!");
					}
				}
				  if(("2".equalsIgnoreCase(objUserDto.getUserRoleId()))&&(objRetDto.getIsSuccessApproved()==1))
				  {
					 System.out.println("In My code--For PM"); 
					 String attDet1 =Utility.getAppConfigValue(AppConstants.Deliver_To_Requester_Key);
					  Connection conn=DbConnection.getConnectionObject();
					  SCMDao dao=new SCMDao();
					  int orederNo=(int)objDto.getOrderno();
					  System.out.println("User id"+objUserDto.getUserId());
					  System.out.println("ordr No:  -----------" +orederNo);
					  ArrayList<LineItemValueDTO> listDto= dao.getLineItemDtls(conn,orederNo);
					  ArrayList<LineItemValueDTO> listDtoToUpdate=new ArrayList<LineItemValueDTO>(); 
					  for(LineItemValueDTO lineDto:listDto)
						{
						  System.out.println("IS_PR_REUSE"+ lineDto.getIsPrReuse());
						  if(lineDto.getIsPrReuse()==0){
						  	System.out.println("SERVICEPRODUCTID"+ lineDto.getSpId());
							System.out.println("SERVICEID"+ lineDto.getService_id());
							System.out.println("SERVICEID"+ lineDto.getServiceDtlId());
							lineDto.setAttValue(objUserDto.getUserId());
							listDtoToUpdate.add(lineDto);
						  }
					}
					  if(listDtoToUpdate!=null && listDtoToUpdate.size()>0){
							dao.updateAttVal(conn,listDtoToUpdate,attDet1);  
					  }

				}
				  else if(("4".equalsIgnoreCase(objUserDto.getUserRoleId()))&&(objRetDto.getIsSuccessApproved()==1))
				  {
					 System.out.println("In My code--For SED"); 
					  String attDet2 =Utility.getAppConfigValue(AppConstants.Preparer_Key);
					  Connection conn=DbConnection.getConnectionObject();
					  SCMDao dao=new SCMDao();
					  int orederNo=(int)objDto.getOrderno();
					  System.out.println("User id"+objUserDto.getUserId());
					  System.out.println("ordr No:  -----------" +orederNo);
					  ArrayList<LineItemValueDTO> listDto= dao.getLineItemDtls(conn,orederNo);
					  ArrayList<LineItemValueDTO> listDtoToUpdate=new ArrayList<LineItemValueDTO>(); 
					  for(LineItemValueDTO lineDto:listDto)
						{
						  System.out.println("IS_PR_REUSE"+ lineDto.getIsPrReuse());
						  if(lineDto.getIsPrReuse()==0){
						  	System.out.println("SERVICEPRODUCTID"+ lineDto.getSpId());
							System.out.println("SERVICEID"+ lineDto.getService_id());
							System.out.println("SERVICEID"+ lineDto.getServiceDtlId());
							lineDto.setAttValue(objUserDto.getUserId());
							listDtoToUpdate.add(lineDto);
							
						  }
					}
					  if(listDtoToUpdate!=null && listDtoToUpdate.size()>0){
							dao.updateAttVal(conn,listDtoToUpdate,attDet2);  
					  }

				}
		//	}
			//End CLEP
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}


public ArrayList<ViewOrderDto> ViewNotes(ViewOrderDto objDto , String sessionId)
throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<ViewOrderDto> objRetDto = new ArrayList<ViewOrderDto>();
	try {
			/*HashMap<String,HttpSession> hmap=(HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
 			HttpSession session=hmap.get(sessionId);
 			SessionObjectsDto userInfo = (SessionObjectsDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);*/

		objRetDto = objDao.ViewNotes(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}


public ArrayList<UserAccessMatrixDto> GetUserAccessList(String objDto)
throws Exception
{	
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<UserAccessMatrixDto> objRetDto = new ArrayList<UserAccessMatrixDto>();
	try {
		objRetDto = objDao.GetUserAccessMatrixData(objDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}


public UserAccessMatrixDto GetUserAccessListAndSegment(String objDto)
throws Exception
{
	try {
		ArrayList<UserAccessMatrixDto> getUserAccessList =GetUserAccessList(objDto);
		ArrayList<UserAccessMatrixDto> getMappedCustomerSegmentWithUserId=getMappedCustomerSegmentWithUserId(objDto);
		UserAccessMatrixDto userAccessMatrixDto=new UserAccessMatrixDto();
		userAccessMatrixDto.setGetUserAccessList(getUserAccessList);
		userAccessMatrixDto.setGetMappedCustomerSegmentWithUserId(getMappedCustomerSegmentWithUserId); 
		return userAccessMatrixDto;
	} catch (Exception e) {
		e.printStackTrace();
	}
		return null;
}

public ArrayList<FieldEnginnerDto> getFEByPartnerId(Integer partnerId)
throws Exception
{
	try {
		NewOrderDao newOrderDao = new NewOrderDao();
		ArrayList<FieldEnginnerDto> getFEByPartnerId =newOrderDao.getFEbyPartnerId(""+partnerId);
		return getFEByPartnerId;
	} catch (Exception e) {
		e.printStackTrace();
	}
		return null;
}


public ViewOrderDto DeleteNotes(ViewOrderDto objDto)
throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ViewOrderDto objRetDto = new ViewOrderDto();
	try {
		objRetDto = objDao.DeleteNotes(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}

public NewOrderDto DeletePODetailRows(NewOrderDto objDto)
throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	NewOrderDto objRetDto = new NewOrderDto();
	try {
		objRetDto = objDao.DeletePODetailRows(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}
	
	//[123] start
	public ValidateOrderAlertDTO fetchOrderAlerts(NewOrderDto objDto)
	throws Exception
	{
		NewOrderModel objModel = new NewOrderModel();
		ValidateOrderAlertDTO validateOrderAlertDTO=new ValidateOrderAlertDTO();
		try {
			validateOrderAlertDTO=objModel.fetchOrderAlerts(objDto);
		} catch (Exception e) {
			logger.error(e.getMessage()+ " Exception occured in fetchOrderAlerts method of "+ this.getClass());
		}
		return validateOrderAlertDTO;
	}
    //[123] end
	
	
//	getLocationDetails
	public ArrayList<LocationDetailDto>  getLocationDetails(LocationDetailDto objDto) throws Exception
	{
		LocationDetailDao objDao = new LocationDetailDao();
		
		
		ArrayList errors=new ArrayList();
		objDto.setSearchAccount(Utility.trimIfNotNull(objDto.getSearchAccount()));
		String accountNo=objDto.getSearchAccount();
		Utility.validateValue(new ValidationBean()
					.loadValidationBean_Maxlength("Account No", accountNo, 18),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_DIGITS_ONLY,Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
		
		objDto.setSearchLocation(Utility.trimIfNotNull(objDto.getSearchLocation()));
		Utility.validateValue(new ValidationBean()
					.loadValidationBean_Maxlength("Account No", objDto.getSearchLocation(), 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
		
		if(errors!=null && errors.size()>0)//During Server Side Validation
		{
			LocationDetailDto dto=new LocationDetailDto();
			dto.setErrors_Validation(errors);
			ArrayList<LocationDetailDto> errorList=new ArrayList<LocationDetailDto>();
			errorList.add(dto);
			return errorList;
		}
		
		
		ArrayList<LocationDetailDto> listLocation= new ArrayList<LocationDetailDto>();
		try 
		{
			listLocation = objDao.getLocationDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listLocation;
	}
//	getdispatch Address Details
	public ArrayList<DispatchAddressDto>  getDispatchAddressDetails(DispatchAddressDto objDto) throws Exception
	{
		DispatchAddressDao objDao = new DispatchAddressDao();
		
		
		ArrayList errors=new ArrayList();
		objDto.setSearchAccount(Utility.trimIfNotNull(objDto.getSearchAccount()));
		String accountNo=objDto.getSearchAccount();
		Utility.validateValue(new ValidationBean()
					.loadValidationBean_Maxlength("Account No", accountNo, 18),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_DIGITS_ONLY,Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
		
		objDto.setSearchDispatchAddress(Utility.trimIfNotNull(objDto.getSearchDispatchAddress()));
		Utility.validateValue(new ValidationBean()
					.loadValidationBean_Maxlength("Account No", objDto.getSearchDispatchAddress(), 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
		
		if(errors!=null && errors.size()>0)//During Server Side Validation
		{
			DispatchAddressDto dto=new DispatchAddressDto();
			dto.setErrors_Validation(errors);
			ArrayList<DispatchAddressDto> errorList=new ArrayList<DispatchAddressDto>();
			errorList.add(dto);
			return errorList;
		}
		
		
		ArrayList<DispatchAddressDto> listDispatch= new ArrayList<DispatchAddressDto>();
		try 
		{
			listDispatch = objDao.getDispatchAddressDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listDispatch;
	}

	

	
	
//	getBcpDetails
	public ArrayList<BCPAddressDto>  getBcpDetails(BCPAddressDto objDto) throws Exception
	{
		BCPAddressDao objDao = new BCPAddressDao();
		
		
		ArrayList errors=new ArrayList();
		objDto.setSearchAccount(Utility.trimIfNotNull(objDto.getSearchAccount()));
		String accountNo=objDto.getSearchAccount();
		Utility.validateValue(new ValidationBean()
					.loadValidationBean_Maxlength("Account No", accountNo, 18),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_DIGITS_ONLY,Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
		
		objDto.setSearchBCP(Utility.trimIfNotNull(objDto.getSearchBCP()));
		Utility.validateValue(new ValidationBean()
					.loadValidationBean_Maxlength("Account No", objDto.getSearchBCP(), 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
		
		if(errors!=null && errors.size()>0)//During Server Side Validation
		{
			BCPAddressDto dto=new BCPAddressDto();
			dto.setErrors_Validation(errors);
			ArrayList<BCPAddressDto> errorList=new ArrayList<BCPAddressDto>();
			errorList.add(dto);
			return errorList;
		}
		
		
		ArrayList<BCPAddressDto> listBCP= new ArrayList<BCPAddressDto>();
		try 
		{
			listBCP = objDao.getBCPDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listBCP;
	}
	
	public String getSpecialChar()
	{
		//SessionObjectsDto sessionObjectsDto =SessionObjectsDto.getInstance();
		//String iChar=sessionObjectsDto.getSpecialCharacter();
		//String iChar="33,35,36,94,42,43,61,91,93,39,59,123,125,124,34,60,62,64,38,40,63,60,62,34,92,41";
		String iChar="";
		return iChar;
	}
	
	public ArrayList<StateDto> getAllStates(CountryDto countryDto) throws IOESException
	{
		ArrayList<StateDto> states = null;
		Connection conn=null;
		try
		{
			ArrayList<StateDto> errorList = BCPAddressModel.validateCountrySeleted(countryDto);
			if(errorList!=null && errorList.size()>0)
			{
				return errorList;
			}
			conn = DbConnection.getConnectionObject();
			return CommonBaseDao.getAllStates(conn,countryDto);
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "getAllStates", "AjaxHelper", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "getAllStates", "AjaxHelper", "", true, true);
			}
		}
	}
	
	public ArrayList<CityDto> getAllCities(StateDto stateDto) throws IOESException
	{
		ArrayList<CityDto> cities = null;
		Connection conn=null;
		try
		{
			ArrayList<CityDto> errorList = BCPAddressModel.validateStateSeleted(stateDto);
			if(errorList!=null && errorList.size()>0)
			{
				return errorList;
			}
			conn = DbConnection.getConnectionObject();
			return CommonBaseDao.getAllCities(conn,stateDto);
			
		}
		catch(Exception ex)
		{
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "getAllCities", "AjaxHelper", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "getAllCities", "AjaxHelper", "", true, true);
			}
		}
	}
	
	public CommonDTO ValidateOrder(PoDetailsDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		CommonDTO objRetDto = new CommonDTO();
		try {
			//below code is commented,becoz new order workflow will be dynamic
			//objRetDto = objDao.ValidatePO(objDto);
			objRetDto.setMsgOut("PO Validated Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	
	
	public String ValidatePODetail(OrderHeaderDTO objDto) throws Exception
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		//NewOrderDto objRetDto = new NewOrderDto();
		String retVal = "";
		try {
			if(objDto.getChangeType() != null )
			{
				//Vijay. modify the condition on the behalf of demo-regularize 
				//if(objDto.getChangeType().equals("3") || (objDto.getChangeType().equals("4") && (objDto.getSubChangeTypeId() == 13 || objDto.getSubChangeTypeId() == 14  || objDto.getSubChangeTypeId() == 15 || objDto.getSubChangeTypeId() == 16))) {
				if(objDto.getChangeType().equals("3")) {
					retVal = objDao.ValidateDisconnectionPO(objDto,null);
					
				//}else if(objDto.getChangeType().equals("2") || objDto.getChangeType().equals("5") || (objDto.getChangeType().equals("4") && (objDto.getSubChangeTypeId() == 12 || objDto.getSubChangeTypeId() == 17 || objDto.getSubChangeTypeId() == 18  || objDto.getSubChangeTypeId() == 19))) {
				}else if(objDto.getChangeType().equals("2") || objDto.getChangeType().equals("5") || (objDto.getChangeType().equals("4") && (objDto.getSubChangeTypeId() == 12 )) ) {
					retVal = objDao.validateChargesPoLevelForChangeOrder(objDto,null);  
				} else if(objDto.getChangeType().equals("1")) {
					retVal = ""; 
				}
			}else {
				retVal = objDao.ValidatePODetail(objDto);
				if(retVal.equalsIgnoreCase(""))
				retVal = objDao.validateChargesPoLevel(objDto);  
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
		
	}
	
	

	public NewOrderDto ViewServiceTreeView(NewOrderDto objDto)	throws Exception
	{
		ArrayList<NewOrderDto> listChildMenu = null;
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objRetDto = new NewOrderDto();
		try {
			objRetDto = objDao.ViewServiceTreeView(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	
	
	
	public NewOrderDto ViewServiceTreeAfterDisconnection(NewOrderDto objDto)	throws Exception
	{
	
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objRetDto = new NewOrderDto();
		try {
			if(objDto.getChangeTypeId()==3  || (objDto.getChangeTypeId()==4 && (objDto.getSubChangeTypeId() == 13 || objDto.getSubChangeTypeId() == 14 || objDto.getSubChangeTypeId() == 15 || objDto.getSubChangeTypeId() == 16 ))) {
				objRetDto = objDao.ViewServiceTreeAfterDisconnection(objDto);
			}else
			{
				objRetDto = objDao.ViewServiceTreeForChangeOrder(objDto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	public ArrayList<FieldAttibuteDTO> populateServiceAttMasterValue(ProductCatelogDTO objDto,String sessionid) throws Exception
	{

		NewOrderDao objDao = new NewOrderDao();
		ArrayList<FieldAttibuteDTO> saDto=new ArrayList<FieldAttibuteDTO>();
		try 
		{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			saDto = objDao.fetchServiceAttributeMasterValue(objDto,Long.valueOf(objUserDto.getUserRoleId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	public ArrayList<ProductCatelogDTO> populateLinkageDetails(ServiceLineDTO objDto,String sessionid,String linakageUpdateFlag) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> saDto=null;
		try 
		{
			saDto = objDao.populateLinkageDetails(objDto,linakageUpdateFlag);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	public ArrayList<ChargeComponentDTO> populateChargeDetails(ServiceLineDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ChargeComponentDTO> saDto=new ArrayList<ChargeComponentDTO>();
		try 
		{
			saDto = objDao.fetchChargeDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	
	
	public ArrayList<ChargeComponentDTO> populateChargeDetailsForChangeOrders(ServiceLineDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ChargeComponentDTO> saDto=new ArrayList<ChargeComponentDTO>();
		try 
		{
			saDto = objDao.fetchChargeDetailsForChangeOrders(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	
	public ArrayList<ProductCatelogDTO> populateBillingDetails(ServiceLineDTO objDto) throws Exception{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> saDto=null;
		try{
			saDto = objDao.fetchBillingDetails(objDto);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	
	public ArrayList<ProductCatelogDTO> populateHardwareDetails(ServiceLineDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> saDto=null;
		try 
		{
			saDto = objDao.fetchHardwareDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	
	public ArrayList<NewOrderDto> populateLocationDetails(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> saDto=new ArrayList<NewOrderDto>();
		try 
		{
			saDto = objDao.fetchLocationDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	public NewOrderDto DeleteContactDetailRows(NewOrderDto objDto)
	throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objRetDto = new NewOrderDto();
		try {
			objRetDto = objDao.DeleteContactDetailRows(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	
	//[001] Start
	//public NewOrderDto updateProductCatelog(NewOrderDto objDto) throws Exception
	public NewOrderDto updateProductCatelog(NewOrderDto objDto,String sessionID) throws Exception
	//[001] End
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto RetObjDto=new NewOrderDto(); 
		int completeStatus=0;
		AjaxValidation objValidate = new AjaxValidation();
		ArrayList lstValidate = new ArrayList();
		try 
		{	
			if (objDto.getBillingBCPId().equalsIgnoreCase("")) 	objDto.setBillingBCPId("0");
			//	if (objDto.getChargeType()==-1) objDto.setChargeType(0);
				//if(objDto.getChargeFrequency().equalsIgnoreCase("")) objDto.setChargeFrequency("0");	
				/*if(objDto.getSaleNature().equalsIgnoreCase("")) objDto.setSaleNature("0");
				if(objDto.getSaleType().equalsIgnoreCase("")) objDto.setSaleType("0");
				if(objDto.getHardwareType().equalsIgnoreCase("")) objDto.setHardwareType("0");
				if(objDto.getFormAvailable().equalsIgnoreCase("")) objDto.setFormAvailable("0");
				if(objDto.getStartDate()==null || objDto.getStartDate().equalsIgnoreCase("")) objDto.setStartDate("00/00/0000");
				if(objDto.getEndDate()==null || objDto.getEndDate().equalsIgnoreCase("")) objDto.setEndDate("00/00/0000");
				if(String.valueOf(objDto.getCommitmentPeriod()).equalsIgnoreCase("")) objDto.setCommitmentPeriod(0);
				if(objDto.getPenaltyClause().equalsIgnoreCase("")) objDto.setPenaltyClause("0");
				if(objDto.getStartHWDateLogic().equalsIgnoreCase("")) objDto.setStartHWDateLogic("0");
				if(objDto.getEndHWDateLogic().equalsIgnoreCase("")) objDto.setEndHWDateLogic("0");
				if(String.valueOf(objDto.getWarrentyMonths()).equalsIgnoreCase("")) objDto.setWarrentyMonths(0);
				if(objDto.getPrincipalAmount()==null) objDto.setPrincipalAmount(0.0);
				if(objDto.getInterestRate()==null) objDto.setInterestRate(0.0); */
				objDto.setMode(NewOrderDto.MODE_UPDATE);

				//[001] Start
				HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
				HttpSession session = hmap.get(sessionID);		
				UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				//[001] End
				
			lstValidate = objValidate.ValidateProductCatelog(objDto);
			if(lstValidate.toString().equalsIgnoreCase("[]"))
				//[001] Start
				//RetObjDto = objDao.updateProductCatelog(objDto);
				RetObjDto = objDao.updateProductCatelog(objDto,Long.valueOf(objUserDto.getEmployeeId()));
				//[001] End
			else
				RetObjDto.setErrors(lstValidate);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return RetObjDto;
	}
	
	public ArrayList<ProductCatelogDTO> populateChargeType(Integer productid) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listChargeType= new ArrayList<ProductCatelogDTO>();
		try 
		{
			listChargeType = objDao.populateChargeType(productid);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			if("1".equals(Messages.getMessageValue("javascriptExceptionShow")))
			{
				throw e;
			}
			else
			{
				throw new Exception();
			}
		}
		return listChargeType;
	}
	/*Function		:populateChargeTypeForHardware
	 * return type	:ArrayList<NewOrderDto>
	 * @Author		:Anil Kumar
	 * Date			:25-feb-11
	 * Purpose		:To fetch charge type based on bill format for hardware
	 * */
	public ArrayList<ChargeComponentDTO> populateChargeTypeForHardware(Integer productid,Integer billformat) throws Exception{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ChargeComponentDTO> listChargeType= null;
		try{
			listChargeType = objDao.populateChargeTypeForHardware(productid,billformat);
		}catch (Exception e){
			e.printStackTrace();
			if("1".equals(Messages.getMessageValue("javascriptExceptionShow"))){
				throw e;
			}else{
				throw new Exception();
			}
		}
		return listChargeType;
	}
	public ArrayList<ProductCatelogDTO> populateChargeName(Integer chargeType ,Integer productid,int entityid) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listChargeType= new ArrayList<ProductCatelogDTO>();
		try 
		{
			listChargeType = objDao.populateChargeName(chargeType, productid,entityid);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			if("1".equals(Messages.getMessageValue("javascriptExceptionShow")))
			{
				throw e;
			}
			else
			{
				throw new Exception();
			}
		}
		return listChargeType;
	}
	
	public ArrayList<ProductCatelogDTO> populateFrequencyType() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listFrequencyType= new ArrayList<ProductCatelogDTO>();
		try 
		{
			listFrequencyType = objDao.populateFrequencyType(0);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			if("1".equals(Messages.getMessageValue("javascriptExceptionShow")))
			{
				throw e;
			}
			else
			{
				throw new Exception();
			}
		}
		return listFrequencyType;
	}
	public ArrayList<ProductCatelogDTO> populateFrequencyType(int contractPeriod) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listFrequencyType= new ArrayList<ProductCatelogDTO>();
		try 
		{
			listFrequencyType = objDao.populateFrequencyType(contractPeriod);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listFrequencyType;
	}
	
		//Filling Taxation Combo for Billing Info Of Product Catalog
	public ArrayList<ProductCatelogDTO> populateTaxationDetails() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listTaxation= new ArrayList<ProductCatelogDTO>();
		try 
		{
			
			listTaxation = objDao.getTaxationDetail();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listTaxation;
	}
//	Filling BillingLevel Combo for Billing Info Of Product Catalog
	public ArrayList<ProductCatelogDTO> populateBillingLevelDetails(int configValue,long currentSPID) throws Exception{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listBillingLevel= new ArrayList<ProductCatelogDTO>();
		try{
			listBillingLevel = objDao.populateBillingLevelDetails(configValue,currentSPID);
		}catch (Exception e){
			e.printStackTrace();
		}
		return listBillingLevel;
	}
//	Filling BillingFormat Combo for Billing Info Of Product Catalog
	public ArrayList<ProductCatelogDTO> populateBillingFormatDetails(String serviceName,int configValue) throws Exception{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listBillingFormat= new ArrayList<ProductCatelogDTO>();
		try{
			listBillingFormat = objDao.populateBillingFormatDetails(serviceName,configValue);
		}catch (Exception e){
			e.printStackTrace();
		}
		return listBillingFormat;
	}
//	Filling BillingType Combo for Billing Info Of Product Catalog
	public ArrayList<ProductCatelogDTO> populateBillingTypeDetails() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listBillingType= new ArrayList<ProductCatelogDTO>();
		try 
		{
			
			listBillingType = objDao.populateBillingTypeDetails();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listBillingType;
	}
//	Filling BillingType Combo for Billing Info Of Product Catalog
	public ArrayList<ProductCatelogDTO> getCreditPeriodDetails() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> listCreditPeriod= new ArrayList<ProductCatelogDTO>();
		try 
		{
			
			listCreditPeriod = objDao.getCreditPeriodDetails();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listCreditPeriod;
	}
//	 This Method get LogicalSiNo from Database
	public int getLogicalSiNo()
	{
		int maxLogicalSiNo=0;
		NewOrderModel objModel=new NewOrderModel();
		ActionMessages messages = new ActionMessages();
		try
		{
			maxLogicalSiNo = objModel.getLogiSINo();
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		//return forward;
		return maxLogicalSiNo;
	}
	
	public NewOrderDto deleteProductCatelog(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto RetObjDto=new NewOrderDto(); 
		int completeStatus=0;
		AjaxValidation objValidate = new AjaxValidation();
		ArrayList lstValidate = new ArrayList();
		try 
		{
			RetObjDto = objDao.deleteProductCatelog(objDto);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return RetObjDto;
	}
	public int cancelOrder(NewOrderDto objDto,String sessionid,String flag) throws Exception
	{
		int status=0;
		NewOrderDao objDao = new NewOrderDao();
		try 
		{
			HashMap<String, HttpSession> hmap = (HashMap<String, HttpSession>) context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto = (UserInfoDto) session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			status = objDao.cancelOrder(objDto,objUserDto.getEmployeeId(),flag);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return status;
	}
	
	/*Vijay
	 * This method is call when a clep order is going to cancel
	 */
	
	public int cancelOrderForClep(NewOrderDto objDto,String sessionid,String flag) throws Exception
	{
		System.out.println("This order is CLEP order and going to cancel.");
		int status=0;
		NewOrderDao objDao = new NewOrderDao();
		ViewOrderDto objRetDto = new ViewOrderDto();
	
		long poNumber=objDto.getOrderNumber();
		try 
		{
			HashMap<String, HttpSession> hmap = (HashMap<String, HttpSession>) context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto = (UserInfoDto) session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			status = objDao.cancelOrder(objDto,objUserDto.getEmployeeId(),flag);
			if(status ==1)
			{
				/*if status is 1 that means order has been cancel
				 * so, a milestone is going to send to clep
				 */
				ViewOrderDto objServiceTypeOrdSrcDto = new ViewOrderDto();
				System.out.println("Order  has been cancel and now going to send a milestone to clep.");
				objServiceTypeOrdSrcDto=objDao.cancelOrderForClep(poNumber);// no need this function use same as save action method  and pass cancel order flag as 3 or something
				
				if(objServiceTypeOrdSrcDto.getServiceTypeID()==2 && "2".equalsIgnoreCase(objServiceTypeOrdSrcDto.getOrder_creation_source())){
					if(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval()!=null){
						CLEPXmlDto clepXmlDto=new CLEPXmlDto();
						//Vijya setting stage
					//	System.out.println("Vijay Order Stage is "+objServiceTypeOrdSrcDto.getOrderStage());
						clepXmlDto.setStage("CANCELLED");
						//vijay end
						
						System.out.println("Response MSG "+ objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval() );
						clepXmlDto.setXmlData(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval());
						CLEPUtility.SysErr("Message Request ID Was:- "+objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
						clepXmlDto.setJmsMessageID(objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
						objDao.sendClepResponseToMppForCancel(clepXmlDto,objServiceTypeOrdSrcDto.getClepFileId(),objServiceTypeOrdSrcDto.getOrderno(),"Y");
						
					}	
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return status;
	}
	//Vijay end
	
	public int cancelLookUpServices(Long serviceNo) throws Exception
	{
		int status=0;
		NewOrderDao objDao = new NewOrderDao();
		try 
		{
			status = objDao.cancelLookUpServices(serviceNo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return status;
	}
	public NewOrderDto countProduct(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto RetObjDto=new NewOrderDto(); 
		int completeStatus=0;
		AjaxValidation objValidate = new AjaxValidation();
		ArrayList lstValidate = new ArrayList();
		try 
		{
			RetObjDto = objDao.countProduct(objDto);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return RetObjDto;
	}
	
	public NewOrderDto validateCharges(NewOrderDto objDto)
	{
		int maxLogicalSiNo=0;
		NewOrderDao objDao=new NewOrderDao();
		ActionMessages messages = new ActionMessages();
		NewOrderDto objRetDto = new NewOrderDto();
		try
		{
			objRetDto = objDao.validateCharges(objDto);
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		//return forward;
		return objRetDto;
	}
	
	public NewOrderDto DeleteServiceDetails(NewOrderDto objDto,String sessionid)
	throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objRetDto = new NewOrderDto();
		try {
			HashMap<String, HttpSession> hmap = (HashMap<String, HttpSession>) context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto = (UserInfoDto) session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			objRetDto = objDao.DeleteServiceDetails(objDto,objUserDto.getEmployeeId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	//013 start
	
	public CommonDTO DeleteProducts(ServiceLineDTO objDto)
	throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		CommonDTO objRetDto = new CommonDTO();
		try {
			objRetDto = objDao.DeleteProducts(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	
	// 013 end
	
	public NewOrderDto DeleteServices(NewOrderDto objDto)
	throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objRetDto = new NewOrderDto();
		NewOrderModel model=new NewOrderModel();
		try {
			objDto.setRemarks("Delete");
			objRetDto=model.validateVCSServicesBeforeDelete(objDto);
			if(objRetDto.getMsgOut()==null)
			{
				objRetDto=model.validateServicesWithRedirectedLSIBeforeDelete(objDto);
				if(objRetDto.getMsgOut()==null)
				{
					objRetDto = objDao.DeleteServices(objDto);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	
	public int getCountPODetailNumber(NewOrderDto objDto)throws Exception 
	 {
		NewOrderDao objDao = new NewOrderDao();
		int poCount = 0;
		try {
			poCount = objDao.getCountPODetailNumber(objDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poCount;
	}	
	public int getPoExistency(PoDetailsDTO objDto)throws Exception 
	 {
		NewOrderDao objDao = new NewOrderDao();
		int poCount = 0;
		try {
			poCount = objDao.getPoExistency(objDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poCount;
	}	
	
	public ArrayList<ProductCatelogDTO> fetchAccessDetails(ServiceLineDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		//NewOrderDto objRetDto = new NewOrderDto();
		ArrayList<ProductCatelogDTO> listAccessDetails= new ArrayList<ProductCatelogDTO>();
		try {
			listAccessDetails = objDao.populateProductAccess(objDto.getServiceDetailID(),objDto.getServiceProductID());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listAccessDetails;
		
	}
	
	public ArrayList<ProductCatelogDTO> populateHdnProductCatelogValue(ServiceLineDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> saDto=null;
		try 
		{
			saDto = objDao.populateHdnProductCatelogValue(objDto.getServiceDetailID(),objDto.getServiceProductID());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	
	public ProductCatelogDTO fetchParentServiceProduct(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ProductCatelogDTO objRetDto = null;
		ArrayList<ProductCatelogDTO> list= new ArrayList<ProductCatelogDTO>();
		try {
			objRetDto = objDao.populateParentServiceProduct(objDto.getServiceDetailID(),objDto.getServiceId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}
	
	/*
	public NewOrderDto copyServiceProduct(NewOrderDto objDto) throws Exception
	{
		String methodName="setChargeSummaryData", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		logger.info(methodName+" method of "+className+" class have been called");
		
		
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objRetDto = null;
		
		try {
			int numberOfCopy=Integer.parseInt(objDto.getCopyProductValues());
			objRetDto = objDao.copyServiceProduct(objDto.getServiceProductID(),numberOfCopy);
			
		} catch (Exception e) {
			throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
		return objRetDto;
		
	}*/
//[042] Start: Copy Service Product in new and Change Order
	public CommonDTO copyServiceProductForNewAndChangeOrder(ProductCatelogDTO objDto, String sessionID) throws Exception
	{
		String methodName="copyServiceProduct", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		logger.info(methodName+" method of "+className+" class have been called");
		
		
		NewOrderDao objDao = new NewOrderDao();
		CommonDTO objRetDto = null;
		
		try {
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionID);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);								
			objRetDto = objDao.copyServiceProductForNewAndChangeOrder(objDto, objUserDto.getEmployeeId());
			
		} catch (Exception e) {
			throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
		return objRetDto;
		
	}
	//[042 End]
	public ArrayList<NewOrderDto> getLogicalSINumber(PagingDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> saDto=new ArrayList<NewOrderDto>();
		String orderStage = objDto.getOrderStage();
		try 
		{
			if (objDto.getChangeTypeId() == 3){
				//#Disconnection Change Order
//				vijay. set order type 'D' if order_type is D other than 'N' in below method 
				saDto = objDao.getDisconnectedLogicalSINumber(objDto);
			}
			/*Vijay start
			 * commenting the code because now Demo order is handling other places   
			 */
			/*else if(objDto.getChangeTypeId() == 4){
				//#Demo Change Order
				//added by Ashutosh for Demo Disconnection
				if(objDto.getSubChangeTypeId()==13 ||objDto.getSubChangeTypeId()==14 ||objDto.getSubChangeTypeId()==15 ||objDto.getSubChangeTypeId()==16)
				{
					System.out.println("Vijay. getDemoLogicalSINumber(objDto) method is calling with setIssuspended & setIsdisconnected value.  ");
					saDto = objDao.getDemoLogicalSINumber(objDto);	
					
				}
				else if(objDto.getSubChangeTypeId()==12  || objDto.getSubChangeTypeId()==17 ||objDto.getSubChangeTypeId()==18 ||objDto.getSubChangeTypeId()==19)
				{
					objDto.setIssuspended(0);
					objDto.setIsdisconnected(0);
					System.out.println("Vijay. getDemoLogicalSINumber(objDto) method is calling without setIssuspended & setIsdisconnected value.  ");
						saDto = objDao.getDemoLogicalSINumber(objDto);
				} 
			}*/
					/*Vijay end */
				
			else {
				//#Raterenwal and Solution Change order  
				//vijay. use folling method for demo with regularize order but set order type 'D' rather than 'N'
				objDto.setIssuspended(0);
				objDto.setIsdisconnected(0);
				saDto = objDao.getLogicalSINumber(objDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
public ArrayList<NewOrderDto> populateChangeType(long changeTypeId) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<NewOrderDto> listChangeType= new ArrayList<NewOrderDto>();
		try 
		{
			
			listChangeType = objDao.getSubChangeType(changeTypeId);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listChangeType;
	}
public ArrayList<NewOrderDto> getServiceNProduct(NewOrderDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> listServiceNProduct=new ArrayList<NewOrderDto>();
	try 
	{
		if (objDto.getChangeTypeId() == 3){
			
			listServiceNProduct = objDao.getDisconnectedServiceNProduct(objDto);
		}
		else if (objDto.getChangeTypeId() == 4){
			//added by Ashutosh for Demo Disconnection
			if(objDto.getSubChangeTypeId()==13 ||objDto.getSubChangeTypeId()==14 ||objDto.getSubChangeTypeId()==15 ||objDto.getSubChangeTypeId()==16)
			{
				listServiceNProduct = objDao.getDisconnectedServiceNProduct(objDto);
			}//Added by Saurabh Demo Regularize
			else if(objDto.getSubChangeTypeId()==12 || objDto.getSubChangeTypeId()==17  || objDto.getSubChangeTypeId()==18 || objDto.getSubChangeTypeId()==19)
			{
				listServiceNProduct = objDao.getDemoServiceNProduct(objDto);
			}
			else if(objDto.getSubChangeTypeId()==17  || objDto.getSubChangeTypeId()==18 || objDto.getSubChangeTypeId()==19){
				listServiceNProduct = objDao.getServiceNProduct(objDto);
			}
		}
		else {
			listServiceNProduct = objDao.getServiceNProduct(objDto);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listServiceNProduct;
}

public NewOrderDto SaveServiceProductType(NewOrderDto objDto1)throws Exception
{
 	NewOrderDao objDao = new NewOrderDao();
	NewOrderDto objDt=new NewOrderDto();
	try {
		
			if (objDto1.getChangeTypeId() == 3){
				objDt = objDao.disconnectServiceProduct(objDto1);
				//Added by Raghu
				//objDt = DisplayServicesInLines(objDto1);
				
			}
			//Vijay. commented because demo regularize treated as rate renewal 
			/*else if(objDto1.getChangeTypeId() == 4){
				//Added by Ashutosh for Demo Disconnection
				if(objDto1.getSubChangeTypeId()==13 || objDto1.getSubChangeTypeId()==14 || objDto1.getSubChangeTypeId()==15 || objDto1.getSubChangeTypeId()==16 ){
					objDt = objDao.disconnectServiceProduct(objDto1);
				}
					
				//else if(objDto1.getSubChangeTypeId()==12) {
				//	objDt = objDao.demoServiceProduct(objDto1);
				//}
				else if(objDto1.getSubChangeTypeId()==12 || objDto1.getSubChangeTypeId()==17 || objDto1.getSubChangeTypeId()==18 || objDto1.getSubChangeTypeId()==19) {
					objDt = objDao.solutionChangeServiceProduct(objDto1);
				}
					
					
				
			}*/
			//add a condition for demo-regularize 
			else if(objDto1.getChangeTypeId() == 2 || objDto1.getChangeTypeId() == 5 || objDto1.getChangeTypeId() ==1 ||( objDto1.getChangeTypeId() == 4 && objDto1.getSubChangeTypeId()==12) )
			{
				objDt = objDao.solutionChangeServiceProduct(objDto1);
			}
			//else {
			//	objDt = objDao.SaveServiceProduct(objDto1);
			//}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objDt;
	
}
public NewOrderDto DisplayServicesInLines(NewOrderDto objDto1)throws Exception
{
 	NewOrderDao objDao = new NewOrderDao();
	NewOrderDto objDto=new NewOrderDto();
	try {
		objDto=objDao.displayServicesInLines(objDto1);
		
			
		} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return objDto;
	
}

public NewOrderDto SaveSolutionChange(NewOrderDto objDto1)throws Exception
{
 	NewOrderDao objDao = new NewOrderDao();
	NewOrderDto objDt=new NewOrderDto();
	try {
		
			if (objDto1.getChangeTypeId() == 2){
				objDt = objDao.disconnectServiceProduct(objDto1);
				
			}
			else {
				objDt = objDao.SaveServiceProduct(objDto1);
			}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objDt;
	
}

//====================New Order Changes ===========================
//			Changes Made For Making Role Wise Fields Validation
//			Getting The List Of Fields With Below Properties
//			1. is Mandatory
//			2. is Readonly
//====================New Order Changes ===========================

public ArrayList<FieldAttibuteDTO> getFieldValidation(String sessionid,String tabName) throws Exception {
	NewOrderDao objDao = new NewOrderDao();
	//NewOrderDto objDto=new NewOrderDto();
	ArrayList<FieldAttibuteDTO> lstDto = new ArrayList<FieldAttibuteDTO>();

	try {
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionid);		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		lstDto = objDao.getRoleMappingValidation(Integer.parseInt(objUserDto.getUserRoleId().toString()),tabName);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return lstDto;
	
}

//Filling StdReason Combo for Billing Info Of Product Catalog
public ArrayList<NewOrderDto> getStdReasonCombo(NewOrderDto dto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> listStdReasonCombo= new ArrayList<NewOrderDto>();
	try 
	{
		
		listStdReasonCombo = objDao.getStdReasonCombo(dto.getChangeTypeId());
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listStdReasonCombo;
}
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
public ArrayList<Entity>  getEntity(Entity dto) throws Exception
{
	ArrayList<Entity> listEntity= new ArrayList<Entity>();
	NewOrderDao objDao = new NewOrderDao();
	try 
	{
		listEntity = objDao.getEntityList(dto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listEntity;
}

public ArrayList<Entity>  getEntity1() throws Exception
{
	ArrayList<Entity> listEntity= new ArrayList<Entity>();
	NewOrderDao objDao = new NewOrderDao();
	try 
	{
		listEntity = objDao.getEntityList1();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listEntity;
}

//Used to Populate Service List in a Pop up
public ArrayList<NewOrderDto> poulateServiceListAfterDisconnection(PagingDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> lstRetVal = new ArrayList<NewOrderDto>(); 
	try 
	{
		lstRetVal = objDao.fetechServiceListForChangeOrder(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return lstRetVal;
}

public ViewOrderDto fnReTryAccountCreateInFx(Long orderNo)
	throws Exception
	{
		
		return (new ViewOrderAction().fnReTryAccountCreateInFx(orderNo));
	}
	//created by manisha for change BILLING TRIGGER 
	/*public ViewOrderDto fnTriggerBillingForDisConnect(ViewOrderDto dto)
	throws Exception
	{
		
		return (new ViewOrderAction().fnBillingTriggerSubmitForDisConnect(dto));
	}*/
	
	//FOR BILLING TRIGGER RATE RENEWAL
	
	/*public ViewOrderDto fnTriggerBillingForRateRenewal(ViewOrderDto dto)
	throws Exception
	{
		
		return (new ViewOrderAction().fnBillingTriggerSubmitForRateRenewal(dto));
	}*/
	
//For Billing Trigger Solution Change
	public ViewOrderDto fnBillingTriggerSubmitForChangeOrders(ViewOrderDto dto)
	throws Exception
	{
		boolean status	=	false;
		ViewOrderDto objViewOrderDto = new ViewOrderDto();
		AjaxValidation objValidate = new AjaxValidation();
		String check_status=dto.getCheckbox_status();
		Long btDoneBy=0l;
		//[049]  start
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(dto.getSessionid());		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		
		//[049]  end
		if(!(objUserDto.getEmployeeId()==null || objUserDto.getEmployeeId()==""))
			btDoneBy=Long.valueOf(objUserDto.getEmployeeId());
			dto.setBtDoneBy(btDoneBy);
		
		
		//030 start
		if(check_status.equals("checked"))
		{
			objViewOrderDto = objValidate.validateActiveDate(dto,null,"N",null,0);
			//objViewOrderDto.setIfAnyFailValidation("SUCCESS");
			if("FAILURE".equals(objViewOrderDto.getIfAnyFailValidation()))
			{
				objViewOrderDto.setBillingTriggerProcess("validation");
				return objViewOrderDto;
			}else {
			//added by manisha  cust bill exp bfr 14 start
			objViewOrderDto = objValidate.validateDisconnectionDate(dto,null);
			if("FAILURE".equals(objViewOrderDto.getIfAnyFailValidation()))
			{
				objViewOrderDto.setBillingTriggerProcess("validation");
				return objViewOrderDto;
			}else { //added by manisha  cust bill exp bfr 14 end
			objViewOrderDto.setIfAnyBillingTriggerConfirmationRequired("0");
			if("1".equals(objViewOrderDto.getIfAnyBillingTriggerConfirmationRequired()))
				{
					if(!"1".equals(dto.getBillingTriggerConfirmed()))
					{
						objViewOrderDto.setBillingTriggerProcess("validation");
						return objViewOrderDto;//Ask for Confirmation
					}
				}
				objViewOrderDto.setBillingTriggerProcess(null);
				return (new ViewOrderAction().fnBillingTriggerSubmitForChangeOrders(dto));	
		}
	}
}	
	else
		
	{
		return (new ViewOrderAction().fnBillingTriggerSubmitForChangeOrders(dto));	
		
	}
	//030 end		
	
}
	
	
	public ViewOrderDto fnUpdateLocdetails(ViewOrderDto dto)
	throws Exception
	{
		
	
		Long btDoneBy=-1l;
		
		dto.setBtDoneBy(btDoneBy);
		
	   return (new ViewOrderAction().fnUpdateLocdetails(dto));	
	
	}
	
	
/*	public ViewOrderDto fnTriggerBilling(ViewOrderDto dto,String sessionid)
	throws Exception
	{
		boolean status	=	false;
		ViewOrderDto objViewOrderDto = new ViewOrderDto();
		AjaxValidation objValidate = new AjaxValidation();
		objViewOrderDto = objValidate.validateActiveDate(dto,null);
		String check_status=dto.getCheckbox_status();
		if(check_status=="checked"){
		
		if(isSessionValid(sessionid)!=null){
		if(objViewOrderDto.getIfAnyFailValidation().equalsIgnoreCase("FAILURE"))
		{
			return objViewOrderDto;
		}else 
			
		return (new ViewOrderAction().fnBillingTriggerSubmit(dto));
		}
		else
		{
			return objViewOrderDto=null;
		}
		
	}
	
		else
		{
			return objViewOrderDto=null;
		}
		
	}*/

//Ramana

public ArrayList<NewOrderDto> poulateSolutionChangeResults(PagingDto objDto) throws Exception{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> lstRetVal = new ArrayList<NewOrderDto>(); 
	try{
		lstRetVal = objDao.fetechServiceListForChangeOrder(objDto);
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return lstRetVal;
}

//Ramana



	
	public int updateChargesForRenewal(NewOrderDto  objDtoList) throws Exception
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		int updationStatus = 0;
		try 
		{
			updationStatus = objDao.updateChargesForRenewal(objDtoList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updationStatus;
	}
	
//	Used to Populate Hardware Type
	public ArrayList<ProductCatelogDTO> populateHardwareType() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> hardwareTypeList = null; 
		//NewOrderDto objHDto = new NewOrderDto();
		try 
		{			
			hardwareTypeList=objDao.populateHardwareType();//add this line by anil on 25-feb-11	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hardwareTypeList;
	}
	// [00055] Start    (Added by Ashutosh)
	public ArrayList<OrderHeaderDTO> getUploadedFileList(Long orderNo,Long roleId) throws Exception
	{
		NewOrderDaoExt objDaoExt = new NewOrderDaoExt();
		ArrayList<OrderHeaderDTO> fileList = null; 
		//NewOrderDto objHDto = new NewOrderDto();
		try 
		{					
			fileList=objDaoExt.getUploadedFileList(orderNo,roleId);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}
	
	//saveUploadedFileCHKList 
	public String saveUploadedFileCHKList(OrderHeaderDTO objDtoList) throws Exception
	{
		NewOrderDaoExt objDaoExt = new NewOrderDaoExt();
		String status=""; 
		//NewOrderDto objHDto = new NewOrderDto();
		try 
		{					
			status=objDaoExt.saveUploadedFileCHKList(objDtoList);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public ArrayList<OrderHeaderDTO> getALLSelectedFileCHKList(Long orderNo) throws Exception
	{
		NewOrderDaoExt objDaoExt = new NewOrderDaoExt();
		ArrayList<OrderHeaderDTO> fileList =null; 
		//NewOrderDto objHDto = new NewOrderDto();
		try 
		{					
			fileList=objDaoExt.getALLSelectedFileCHKList(orderNo);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}
	
	public ArrayList<OrderHeaderDTO> getListOfRoleFromWorkflow(Long orderNo) throws Exception
	{
		NewOrderDaoExt objDaoExt = new NewOrderDaoExt();
		ArrayList<OrderHeaderDTO> roleList = null; 
		//NewOrderDto objHDto = new NewOrderDto();
		try 
		{					
			roleList=objDaoExt.getListOfRoleFromWorkflow(orderNo);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleList;
	}
	
	
	// [00055]End
	public ArrayList<ProductCatelogDTO> formAvailable() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> formAvailList = null; 
		//NewOrderDto objHDto = new NewOrderDto();
		try 
		{					
			formAvailList=objDao.populateFormAvailable();				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formAvailList;
	}
	
	public ArrayList<ProductCatelogDTO> natureOfSale() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> saleNatureList = null; 
		//NewOrderDto objHDto = new NewOrderDto();
		try 
		{			
			saleNatureList=objDao.populateNatureOfSale();//add by anil on 25-feb-11
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saleNatureList;
	}
	public ArrayList<ProductCatelogDTO> typeOfSale(String billFormat) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ProductCatelogDTO> saleTypeList = null; 
		//NewOrderDto objHDto = new NewOrderDto();
		try 
		{ 						
			saleTypeList=objDao.populateSaleType(billFormat);//add by anil on 25-feb-11			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saleTypeList;
	}
	
	//	Charge Details For All Orders
	 public ArrayList<ViewOrderDto> fnGetChargesAllOrders(PagingDto objPagingDto,ViewOrderDto objViewOrderdto,int pagingRequired)
	throws Exception
{
	logger.info("In fnGetChargesAllOrders");
	ArrayList<ViewOrderDto> result;
	Connection conn = null;
	try 
	{
		conn=DbConnection.getConnectionObject();
	
		ViewOrderDao dao=new ViewOrderDao();
		long l=Long.parseLong(objViewOrderdto.getServiceProductID());
		
		int startIndex=objPagingDto.getStartIndex();
		int endIndex=objPagingDto.getEndIndex();
			result=dao.fnGetChargesAllOrders(l,conn,Long.parseLong(objViewOrderdto.getServiceId()),startIndex,endIndex,pagingRequired,objPagingDto.getPageRecords());
			
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage()+ " Exception occured in fnGetChargesAllOrders method of "+ this.getClass().getSimpleName());
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
		}
		finally
		{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				throw Utility.onEx_LOG_RET_NEW_EX(e, "", "", "", true, true);
			}
		}
		return result;
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	public ArrayList getFieldValidationForChangeOrder(String sessionid ,String subChangeTypeID)throws Exception
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList lstDto = new ArrayList();

		try {
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			lstDto = objDao.getFieldValidationForChangeOrder(Integer.parseInt(objUserDto.getUserRoleId().toString()), Integer.parseInt(subChangeTypeID));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstDto;
		
	}
	
	public String checkM6Status(int orderNo)throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		String eventTypeId1="0";
		//int eventTypeId=null;
		try {
			
			int eventTypeId= objDao.checkM6Status(orderNo);
			if(eventTypeId > 0)
			{
				eventTypeId1="1";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventTypeId1;
		
	}
	
	public ArrayList<CommonDTO> populateRoleList(NewOrderDto objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<CommonDTO> roleList = null; 
		try 
		{
			roleList = objDao.fetechRoleList(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleList;
	}
	//add by anil for check publish status in table TM6_NEWORDER_STATUS
	public Long checkPublishStatus(int orderNo)throws Exception
	{
		ViewOrderDao objDao = new ViewOrderDao();
		Long TM6OrderNo=null;
		try {
			
			TM6OrderNo= objDao.checkPublishStatus(orderNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TM6OrderNo;
		
	}
	public Long checkPublishServiceStatus(int orderNo , String userid)throws Exception
	{
		ViewOrderDao objDao = new ViewOrderDao();
		Long TM6OrderNo=null;
		try {
			
			TM6OrderNo= objDao.checkPublishServiceStatus(orderNo,userid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TM6OrderNo;
		
	}
	
	
	public ArrayList getRolesForEscalation(String[] roleIds, String roleIdLevel1)
	throws Exception {

	ArrayList roleList = new ArrayList();
	
	NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
			.getInstance(DaoConstants.NPD_USER_DAO);
	Session hibernateSession = null;
	hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
	try {
		roleList = npdUserDao.getRolesForEscalationLevel(roleIds,
				roleIdLevel1, hibernateSession);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);
	}	
	return roleList;
	}	
	//Start [119]
	public int attachworkflowForChangeOrder(OrderHeaderDTO objDto)throws Exception{
		NewOrderDao objDao = new NewOrderDao();
		int workflowattached=0;
		try {
			if("PermanentDisconnectionSingleThenBulkApproval".equals(objDto.getPermanentDisconnectionSingleThenBulkApproval())){
				objDto.setProjectWorkflowId("-2");
			}
			workflowattached = objDao.attachworkflowForChangeOrder(objDto,null);
		}catch (Exception e){
			e.printStackTrace();
		}
		return workflowattached;
	}
	
	public WorkFlowAttachDetails getChangeOrderSubTypeAttached(String orderNo) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		
		WorkFlowAttachDetails attachDetails = null;
		try {
			
			//lstNewOrder = objDao.getChangeOrderSubTypeAttached(orderNo,null);
			attachDetails = NewOrderModel.getChangeOrderSubTypeAttached(orderNo,null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attachDetails;
		
	}
	//end [119]
	public ViewOrderDto SaveChangeOrderAction(ViewOrderDto objDto)
	throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ViewOrderDto objRetDto = new ViewOrderDto();
		
		String orderStage ="";
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(objDto.getSessionid());		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		
		try {
			objDto.setUserid(objUserDto.getEmployeeId());
			objRetDto = objDao.SaveChangeOrderAction(objDto,null);
			
		/*Vijay start */	
			
			if("1".equalsIgnoreCase(objUserDto.getUserRoleId())){
					orderStage = "AM";  //approved by AM
			}	
			else if("2".equalsIgnoreCase(objUserDto.getUserRoleId())){
				orderStage = "PM";  //approved by PM
			}
			else if("3".equalsIgnoreCase(objUserDto.getUserRoleId())){
				orderStage = "COPC";  //approved by COPC	
			}
			else if("4".equalsIgnoreCase(objUserDto.getUserRoleId())){
				orderStage = "SED";  //approved by SED
			}
			
			int actionId= 2;//it is for rejection, 
			if("1".equalsIgnoreCase(objDto.getActionId())){
				actionId=1;	//it is for approval
			}
			long orderNo = objDto.getOrderno();
			//below code added by Anil for Send CLEP Response to MPP after successfully Aproved by Last Aproval			
		//	if(Utility.getAppConfigValue(AppConstants.LAST_ROLE_APROVAL_CLEP_SAAS_ERP).equalsIgnoreCase(objUserDto.getUserRoleId())){				
		//	if(objRetDto.getIsSuccessApproved()==1 && "1".equalsIgnoreCase(objDto.getActionId())){
			  if(objRetDto.getIsSuccessApproved()==1){	
				  
				CLEPUtility.SysErr("---------- Finding Response to Send >>>>>>>>>>>");
				ViewOrderDto objServiceTypeOrdSrcDto = new ViewOrderDto();
				if(objDto.getTaskID()!=null){
					long taskId=Long.valueOf(objDto.getTaskID());
					
					if("3".equalsIgnoreCase(objUserDto.getUserRoleId()) && actionId==1){
						/*in case of copc approval, MSG template would be change and order id passed rather than task id */
						objServiceTypeOrdSrcDto=objDao.getServiceType_OrderSourceClepErp(orderNo, 4 /*1*/);	
					}
					else
					{
						objServiceTypeOrdSrcDto=objDao.getServiceType_OrderSourceClepErp(taskId, actionId /*1*/);
					}
					//if copc already sent message to mpp,it will not resent
					//if(objServiceTypeOrdSrcDto.getIsCOPCSentMsgToMPP()!=1){
					
						if(objServiceTypeOrdSrcDto.getServiceTypeID()==2 && "2".equalsIgnoreCase(objServiceTypeOrdSrcDto.getOrder_creation_source())){
							if(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval()!=null){
								CLEPXmlDto clepXmlDto=new CLEPXmlDto();
								
//								Vijya setting stage
								CLEPUtility.SysErr("Order Stage is "+orderStage);
								clepXmlDto.setStage(orderStage);
								//vijay end
								
								CLEPUtility.SysErr("Response MSG "+ objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval() );
								clepXmlDto.setXmlData(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval());
								CLEPUtility.SysErr("Message Request ID Was:- "+objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
								clepXmlDto.setJmsMessageID(objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
								objDao.sendClepResponseToMpp(clepXmlDto,objServiceTypeOrdSrcDto.getClepFileId(),objServiceTypeOrdSrcDto.getOrderno(),"Y");							
							}	
						}
					//}else{
					//	CLEPUtility.SysErr("------- Response already sent to MPP -------------");
					//}
				}else{
					CLEPUtility.SysErr("Error: Task Is Not Created!! MPP Response Sending Failed!!");
				}
			}
		//}
		//End CLEP
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;
		
	}	

public ArrayList getUsersForOptionalList(String[] mandatoryIds,String productId)
	throws Exception {

ArrayList optionalList = new ArrayList();

NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
		.getInstance(DaoConstants.NPD_USER_DAO);

try {
	optionalList = npdUserDao.getUsersForOptionalList(mandatoryIds,productId);
} catch (Exception e) {
	e.printStackTrace();
}
return optionalList;

}
//getMeetingsOfProduct
public ArrayList getMeetingsOfProduct(String productId)
throws Exception {

ArrayList meetingList = new ArrayList();

MeetingModel model=new MeetingModel(); 

try {
	meetingList = model.getMeetingsOfProduct(productId);
} catch (Exception e) {
	e.printStackTrace();
}
return meetingList;

}
//
public TtrnMeetingschedules getMeetingDetailById(String meetId)
throws Exception {

TtrnMeetingschedules ttrnMeetingschedules = null;

MeetingModel model=new MeetingModel(); 

try {
	ttrnMeetingschedules = model.getMeetingDetailById(meetId);
} catch (Exception e) {
	e.printStackTrace();
}
return ttrnMeetingschedules;

}

public ArrayList getUsersForMandatoryList(String[] productIds)
throws Exception {

ArrayList mandatoryList = new ArrayList();

NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
		.getInstance(DaoConstants.NPD_USER_DAO);

try {
	mandatoryList = npdUserDao.getUsersForMandatoryList(productIds);
} catch (Exception e) {
	e.printStackTrace();
}
return mandatoryList;
}

public ArrayList getUsersForARoleID(String roleId) throws Exception {

ArrayList optionalList = new ArrayList();

NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
		.getInstance(DaoConstants.NPD_USER_DAO);
Session hibernateSession = null;
hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();

try {
	optionalList = npdUserDao.getEmployeeRolesBasedOnRoleID(roleId,
			hibernateSession);

} catch (Exception e) {
	e.printStackTrace();
}
finally
{
	try{com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);}
	catch(Exception ex){ex.printStackTrace();}
}
return optionalList;

}

public ArrayList getUsersForARoleID_NpdSpocs(String roleId,String minusNpdEmpiId) throws Exception {

ArrayList optionalList = new ArrayList();

NpdUserDao npdUserDao = (NpdUserDao) BaseDaoFactory
		.getInstance(DaoConstants.NPD_USER_DAO);


Connection conn=null;
try {
conn=NpdConnection.getConnectionObject();


TmEmployee searchDto=new TmEmployee();
searchDto.setCurrentRoleId(Long.parseLong(roleId));
long val=0;
if(minusNpdEmpiId!=null && !minusNpdEmpiId.equals(""))
{
	val=Long.parseLong(minusNpdEmpiId);
}
return (npdUserDao.fetchEmployeesOfRoleNpdSpoc(conn,searchDto,val));


	

} catch (Exception e) {
	e.printStackTrace();
}finally
{
	NpdConnection.freeConnection(conn);
}
return optionalList;

}

public TmWorkflowstage checkDuplicateStage(String stage, String categoryId)
	throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
TmWorkflowstage tmWorkflowstage= null;

try {
	tmWorkflowstage = masterProjectPlanDao.checkDuplicateStage(stage,
			categoryId);

} catch (Exception e) {
	e.printStackTrace();
}
return tmWorkflowstage;

}

public TmWorkflowtasks getTaskDetails(String taskid) throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();
com.ibm.ioes.npd.hibernate.dao.CommonBaseDao commonBaseDao = (com.ibm.ioes.npd.hibernate.dao.CommonBaseDao) BaseDaoFactory
		.getInstance(DaoConstants.BASE_DAO_CLASS);
Session hibernateSession = null;
hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();

try {
	if (taskid != null
			&& !taskid.equalsIgnoreCase(AppConstants.INI_VALUE)) {
		tmWorkflowtasks = (TmWorkflowtasks) commonBaseDao.findById(Long
				.parseLong(taskid),
				HibernateBeansConstants.HBM_WORKFLOW_TASK,
				hibernateSession);

	}
} catch (Exception e) {
	e.printStackTrace();
}finally
{
	try{com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);}
	catch(Exception ex){ex.printStackTrace();}
}
return tmWorkflowtasks;

}

public TmWorkflowtasks getDuplicateTask(String task, String stageId)
	throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();

try {
	tmWorkflowtasks = masterProjectPlanDao.checkDuplicateTask(task,
			stageId);

} catch (Exception e) {
	e.printStackTrace();
}
return tmWorkflowtasks;

}

public boolean deleteTask(String taskId) throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
boolean delete = false;

try {
	delete = masterProjectPlanDao.deleteTask(taskId);

} catch (Exception e) {
	e.printStackTrace();
}
return delete;

}
public String[] getPreviousTasks(String taskId) throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
String[] previousTask=null;

try {
	previousTask = masterProjectPlanDao.getPreviousTasks(taskId);

} catch (Exception e) {
	e.printStackTrace();
}
return previousTask;

}
public boolean updateTask(String taskID, String remarks, String desc,
	String stakeholderId, String plannedDays, String taskName,boolean first,boolean last,String[] prevTaskId)
	throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
boolean update = false;

try {
	update = masterProjectPlanDao.updateTask(taskID, remarks, desc,
			stakeholderId, plannedDays, taskName,first,last,prevTaskId);

} catch (Exception e) {
	e.printStackTrace();
}
return update;

}

public TmWorkflowtasks checkForDuplicateFirstAndLastTask(boolean first,
	boolean last, String stageId) throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);

TmWorkflowtasks tmWorkflowtasks = new TmWorkflowtasks();

try {
	tmWorkflowtasks = masterProjectPlanDao.checkForDuplicateFirstAndLastTask(
			first, last, stageId);

} catch (Exception e) {
	e.printStackTrace();
}
return tmWorkflowtasks;

}
public ArrayList<TmWorkflowtasks> fetchFirstAndLastTask(String wkId) throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);

ArrayList<TmWorkflowtasks> tasks=null;

try {
	tasks = masterProjectPlanDao.fetchFirstAndLastTask(wkId);

} catch (Exception e) {
	e.printStackTrace();
}
return tasks;

}

public ArrayList getTemplateDocList() throws Exception {

ArrayList templateDocList = new ArrayList();
HashMap hashMap = new HashMap();

ReferenceDocDao referenceDocDao = (ReferenceDocDao) BaseDaoFactory
		.getInstance(DaoConstants.REFERENCE_DOC_DAO);
Session hibernateSession = null;
hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
try {
	AttachEditProjectPlanDao dao=new AttachEditProjectPlanDao();
	templateDocList=dao.getTemplateDocList(hibernateSession);
	/*hashMap = referenceDocDao.getActiveRefDoc(null,hibernateSession);
	if(hashMap!=null&&hashMap.size()>0)
	{
		Set s = hashMap.keySet();
		Object a[] = s.toArray();
		for(int i=0;i<a.length;i++)
		{
			templateDocList.add((TmReferencedocs)a[i]);
		}
	}*/

			
} catch (Exception e) {
	e.printStackTrace();
}finally{
	com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);
}
return templateDocList;

}

public ArrayList getTaskListForACategory(String stageId, String workflowId)
	throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
ArrayList previousTaskList = new ArrayList();
Session hibernateSession = null;
try {
	hibernateSession=com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
	previousTaskList = masterProjectPlanDao.getTaskListForACategory(
			stageId, workflowId,hibernateSession);
} catch (Exception e) {
	e.printStackTrace();
}finally
{
	try{com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);}
	catch(Exception ex){ex.printStackTrace();}
}
return previousTaskList;

}


public ArrayList getPreviousTaskList(String workflowId,String option,String taskId)
	throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
ArrayList previousTaskList = new ArrayList();
Session hibernateSession = null;
try {
	hibernateSession=com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
	if("ALL".equals(option))
	{
		previousTaskList = masterProjectPlanDao.getTaskListForACategory(null, workflowId,hibernateSession);	
	}
	else if("VALID_FOR_TASK".equals(option))
	{
		MasterProjectPlanModel model=new MasterProjectPlanModel();
		previousTaskList =model.fetchValidPrevTasks(workflowId, taskId);
	}

} catch (Exception e) {
	e.printStackTrace();
}finally
{
	try{com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);}
	catch(Exception ex){ex.printStackTrace();}
}
return previousTaskList;

}

/*public boolean updateProject(String projectId, String productBrief,
	String airtelPotential, String capexRequirement,
	String targetMarket, String totalMktPotential, String launchDate)
	throws Exception {

	ProductCreationDao productCreationDao = (ProductCreationDao) BaseDaoFactory
		.getInstance(DaoConstants.PRODUCT_CREATION_DAO);
boolean update = false;

try {
	if(projectId!=null&&!projectId.equalsIgnoreCase(AppConstants.INI_VALUE))
	update = productCreationDao.updateProject(new Long(projectId).longValue(),productBrief,airtelPotential,capexRequirement,targetMarket,totalMktPotential,launchDate);

} catch (Exception e) {
	e.printStackTrace();
}
return update;

}

*/	public ArrayList getUsersOfRole(String roleId) throws Exception 
{
ArrayList employeeList=new  ArrayList();
Session hibernateSession = null;

try{
	hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();			
	String sql="SELECT	TM_EMPLOYEE.NPDEMPID,TM_EMPLOYEE.EMPNAME FROM	NPD.TM_ROLEEMPMAPPING TM_ROLEEMPMAPPING " +
			"INNER JOIN NPD.TM_ROLES TM_ROLES ON TM_ROLEEMPMAPPING.ROLEID = TM_ROLES.ROLEID" +
			"	INNER JOIN NPD.TM_EMPLOYEE TM_EMPLOYEE " +
			"ON TM_ROLEEMPMAPPING.NPDEMPID = TM_EMPLOYEE.NPDEMPID WHERE TM_ROLES.ROLEID=:roleId";
	SQLQuery query=hibernateSession.createSQLQuery(sql);
	
	query.setLong("roleId", Long.valueOf(roleId));
	
	query.addScalar("npdempid",Hibernate.LONG)
			.addScalar("empname",Hibernate.STRING)
			.setResultTransformer( Transformers.aliasToBean(TmEmployee.class));
	
	
	employeeList=(ArrayList)query.list();
	
	
}
catch(Exception ex)
{
	ex.printStackTrace();
	String msg=ex.getMessage()
	+ " Exception occured in getUsersOfRole method of "
	+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
	AppConstants.NPDLOGGER.error(msg);
	throw new NpdException(msg) ;
}
finally
{
	hibernateSession.close();
}
return employeeList;
}

/*
*  Test whether a first is present in hierarchy other than input  currentTaskIdString
*/
public ArrayList getInfoProjectInstanceFirstTask(String projectWorkflowId,String currentTaskIdString ) throws Exception 
{
ArrayList messages=new  ArrayList();
Session hibernateSession = null;

try{
	hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();		
	Criteria ce=hibernateSession.createCriteria(TtrnProjecthierarchy.class);
	ce.add(Restrictions.eq("projectworkflowid", Long.parseLong(projectWorkflowId)));
	//ce.add(Restrictions.ne("currenttaskid", Long.parseLong(currentTaskIdString)));
	ce.add(Restrictions.eq("isfirsttask", 1));
	ce.add(Restrictions.eq("isactive", 1));
	
	List list=ce.list();
	
	if(list.size()==0)
	{
		messages.add("noFirstPresent");
	}else 
	if(list.size()==1)
	{
		TtrnProjecthierarchy ttrnProjecthierarchy=(TtrnProjecthierarchy)list.get(0);
		messages.add("firstAlreadyPresent");
		messages.add(ttrnProjecthierarchy);
		//messages.add("Task Name :"+ttrnProjecthierarchy.getTaskname()+"  and Stage Name :"+ttrnProjecthierarchy.getStagename());
		
	}
}
catch(Exception ex)
{
	ex.printStackTrace();
	String msg=ex.getMessage()
	+ " Exception occured in getInfoProjectInstanceFirstTask method of "
	+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
	AppConstants.NPDLOGGER.error(msg);
	throw new NpdException(msg) ;
}
finally
{
	hibernateSession.close();
}
return messages;
}

public ArrayList getInfoProjectInstanceLastTask(String projectWorkflowId,String currentTaskIdString ) throws Exception 
{
ArrayList messages=new  ArrayList();
Session hibernateSession = null;

try{
	hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();		
	Criteria ce=hibernateSession.createCriteria(TtrnProjecthierarchy.class);
	ce.add(Restrictions.eq("projectworkflowid", Long.parseLong(projectWorkflowId)));
	//ce.add(Restrictions.ne("currenttaskid", Long.parseLong(currentTaskIdString)));
	ce.add(Restrictions.eq("islasttask", 1));
	ce.add(Restrictions.eq("isactive", 1));
	
	List list=ce.list();
	
	if(list.size()==0)
	{
		messages.add("noLastPresent");
	}else 
	if(list.size()==1)
	{
		TtrnProjecthierarchy ttrnProjecthierarchy=(TtrnProjecthierarchy)list.get(0);
		messages.add("lastAlreadyPresent");
		messages.add(ttrnProjecthierarchy);
		//messages.add("Task Name :"+ttrnProjecthierarchy.getTaskname()+"  and Stage Name :"+ttrnProjecthierarchy.getStagename());
		
	}
}
catch(Exception ex)
{
	ex.printStackTrace();
	String msg=ex.getMessage()
	+ " Exception occured in getInfoProjectInstanceLastTask method of "
	+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
	AppConstants.NPDLOGGER.error(msg);
	throw new NpdException(msg) ;
}
finally
{
	hibernateSession.close();
}
return messages;
}

//
public TmWorkflow checkAnotherDraftWK(String npdCategoryId)
throws Exception {

MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);
TmWorkflow tmWorkflow= null;

try {
	tmWorkflow = masterProjectPlanDao.checkAnotherDraftWK(npdCategoryId);

} catch (Exception e) {
	e.printStackTrace();
}
return tmWorkflow;

}

/*public String getSpecialChar()
{
Connection connObj=null;
String iChar="";
String str="SELECT CHARACTERS FROM NPD.VW_SPECIALCHARACTERS";
Statement stmt = null;
ResultSet rs=null;
try 
{
	connObj = NpdConnection.getConnectionObject();
	stmt = connObj.createStatement();
	rs=stmt.executeQuery(str);
	rs.next();
	iChar=rs.getString(1);			
} 
catch (Exception ex) 
{
	ex.printStackTrace();
}
finally
{
	try {
		stmt.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return iChar;
}*/



public ArrayList getUsersOfResolution(String projectId) throws Exception 
{
ArrayList employeeList=new  ArrayList();
Session hibernateSession = null;
hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
IssuesCaptureDao dao=new IssuesCaptureDao(); 
employeeList=dao.getUsersOfResolution(projectId,hibernateSession);
com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);

return employeeList;
}
//TODO Change Made BY Sumit on 09-Mar-2010 (Start) Pending RFI Should Be According to role
//Start
public ArrayList getRoleMappedWithEmployee(String empid) throws Exception 
{
ArrayList<TmRoles> employeeList=new  ArrayList<TmRoles>();
MyToDOListDaoImpl dao=new MyToDOListDaoImpl(); 
employeeList=dao.getRoleMappedWithEmployee(empid);
return employeeList;
}
//End
public ArrayList getUsersOfMitigation(String projectId) throws Exception 
{
ArrayList employeeList=new  ArrayList();
Session hibernateSession = null;
hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
RisksCaptureDao dao=new RisksCaptureDao(); 
employeeList=dao.getUsersOfMitigation(projectId,hibernateSession);
com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);

return employeeList;
}

public boolean dashboardAuthorization(String roleID,String request, String empID) throws Exception 
{
Boolean ABC=false;
Session hibernateSession = null;
try{
hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
CommonUtilities cmn=new CommonUtilities();
ABC=cmn.isAuthorised(roleID,request,empID);
}finally{
com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);
}

return ABC;
}

public String encryptString(String var1)
throws Exception {

	String encode = ""; 
	IEncryption objen = new Encryption();

try {
	encode = URLEncoder.encode(objen.encrypt(var1));
	
} catch (Exception e) {
	e.printStackTrace();
}
return encode;

}

public int checkDuplicateHolidayDate(String holidayDate,int holidayID) throws Exception 
{
int cnt=0;
Session hibernateSession = null;
try
{
hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
HolidayMasterDao dao=new HolidayMasterDao();
cnt=dao.checkDuplicateHolidayDate(holidayDate,holidayID);
}finally{
com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);
}
return cnt;
}

public int checkDuplicateHolidayName(String holidayName,int holidayID) throws Exception 
{
int cnt=0;
Session hibernateSession = null;
try
{
hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
HolidayMasterDao dao=new HolidayMasterDao();
cnt=dao.checkDuplicateHolidayName(holidayName,holidayID);
}finally{
com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);
}
return cnt;
}


//rohit verma new cr
public ArrayList<TtrnProjecthierarchy> getPrevTaskList(String projectId,String projectWorkflowId,String[] taskIds)
throws Exception {

/*MasterProjectPlanDao masterProjectPlanDao = (MasterProjectPlanDao) BaseDaoFactory
		.getInstance(DaoConstants.MASTER_PROJECT_PLAN_DAO);*/
ArrayList<TtrnProjecthierarchy> previousTaskList = null;
Session hibernateSession = null;
AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
AttachEditProjectPlanModel model=new AttachEditProjectPlanModel();
try {
	hibernateSession = com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.beginTrans();
	ArrayList<TtrnProjecthierarchy> list = daoObj.getAllTasks(hibernateSession, projectWorkflowId);
	
	Set<TtrnProjecthierarchy> Set_PrevList=null;
	for (String taskId : taskIds) {
		ArrayList<TtrnProjecthierarchy> possPrevTasks=model.getPossiblePreviousTasks(taskId,list);
		if(Set_PrevList==null)
		{
			Set_PrevList=new HashSet<TtrnProjecthierarchy>(possPrevTasks);
		}
		Set_PrevList.retainAll(possPrevTasks);
	}
	if(Set_PrevList==null)
	{
		previousTaskList=new ArrayList<TtrnProjecthierarchy>();
	}
	else
	{
		previousTaskList=new ArrayList<TtrnProjecthierarchy>(Set_PrevList);
	}
	/*if("ALL".equals(option))
	{
		previousTaskList = masterProjectPlanDao.getTaskListForACategory(null, workflowId);	
	}
	else if("VALID_FOR_TASK".equals(option))
	{
		MasterProjectPlanModel model=new MasterProjectPlanModel();
		previousTaskList =model.fetchValidPrevTasks(workflowId, taskId);
	}*/

} catch (Exception e) {
	e.printStackTrace();
}finally
{
	com.ibm.ioes.npd.hibernate.dao.CommonBaseDao.closeTrans(hibernateSession);
}
return previousTaskList;

}
/*Function		:getPreviousTask
 * return type	:Long
 * @Author		:Anil Kumar
 * Date			:08-02-2011
 * Purpose		:To fetch previous task id against task id for validation
 * */
public Long getPreviousTask(int taskId)throws Exception
{
	Long prevTaskId=null;
	MasterProjectPlanModel objModel=new MasterProjectPlanModel();
	try
	{
		prevTaskId=objModel.getPreviousTask(taskId);
	}
	catch(Exception ex)
	{
		System.err.println(ex.getMessage());
	}
	return prevTaskId;
}
/*Function		:isWorkflowValid
 * return type	:int
 * @Author		:Anil Kumar
 * Date			:09-02-2011
 * Purpose		:To validation previous task status
 * */
public int isWorkflowValid(Long workflowid,String flag)throws Exception
{
	MasterProjectPlanDao objDao=new MasterProjectPlanDao();
	int isValid=0;	
	try
	{
		if("master".equalsIgnoreCase(flag))
		{
			isValid=objDao.isWorkflowid(workflowid,flag);
		}
		else
		{
			isValid=objDao.isWorkflowid(workflowid,flag);
		}
	}
	catch(Exception ex)
	{
		System.err.println(ex.getMessage());
	}
	return isValid;
}

//--------------------------------------IOES Functions---------------------------------------------
public ArrayList<IoesProductDto> addIOESProduct(String parentproductId) throws Exception 
{
ArrayList<IoesProductDto> lstioesproduct = new ArrayList<IoesProductDto>();
AttachEditProjectPlanDao objDao = new AttachEditProjectPlanDao();
try
{
	lstioesproduct = objDao.addIOESProduct(parentproductId);
} 
catch (Exception e) {
	e.printStackTrace();

}
return lstioesproduct;
}
public ArrayList<IoesProductDto> populateProductHeader() throws Exception 
{
ArrayList<IoesProductDto> lstioesproduct = new ArrayList<IoesProductDto>();
AttachEditProjectPlanDao objDao = new AttachEditProjectPlanDao();
try
{
	lstioesproduct = objDao.populateProductHeader();
} 
catch (Exception e) {
	e.printStackTrace();

}
return lstioesproduct;
}

/*function		:populateTaxRate
 * return type	:String
 * Author		:Anil Kumar
 * Date			:09-march-11
 * Purpose		:To fecth Tax Rate in charge section for product catelog page. 
 * */	
public String populateTaxRate(Long poNumber,Long mappingID)throws Exception
{
	String taxrate=null;
	NewOrderDao objDao=new NewOrderDao();
	try
	{
		taxrate=objDao.populateTaxRate(poNumber,mappingID,null);		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return taxrate;
}

/*Function		:getProductNameAndID
 * return type	:List
 * @Author		:Saurabh Singh
 * Date			:16-02-2011
 * Purpose		:To get Product name and id for copying charges
 * */
public ArrayList<ProductCatelogDTO> populateProductAndId(NewOrderDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<ProductCatelogDTO> listStore= new ArrayList<ProductCatelogDTO>();
	try 
	{
		listStore = objDao.populateProductAndId(objDto.getServiceId(),objDto.getChargeType());
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listStore;
}	
/*Function		:copyCharges
 * return type	:String
 * @Author		:Saurabh Singh
 * Date			:16-02-2011
 * Purpose		:To copy charges from one line item to other
 * */
public String copyChargeFromOneToOther(ChargeComponentDTO objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	String isModify="";
	try 
	{
		isModify = objDao.copyChargeFromOneToOther(objDto.getSourceProductId(),objDto.getDestinationProductId());
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return isModify;
}

//[002] Start
	public NewOrderDto addChildProduct(NewOrderDto objDto,long orderNo,long serviceNo) throws Exception
	{
		String methodName="setChargeSummaryData", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		logger.info(methodName+" method of "+className+" class have been called");
		
		
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objRetDto = null;
		
		try 
		{
			objRetDto = objDao.addChildProduct(objDto.getServiceProductID(),orderNo,serviceNo);			
		} catch (Exception e) {
			throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
		return objRetDto;
		
	}
//[002] End
public int disconnectCharge(String chargeID, String changeOrderNo,String disconnectionType) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int status=-1;
	try 
	{
		status = objDao.disconnectCharge(chargeID,changeOrderNo,disconnectionType);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;

}
public int cancelWorkflow(NewOrderDto objDto)
{
	int isCancelled = 0;
	NewOrderDao objDao = new NewOrderDao();
	try
	{
		isCancelled = objDao.cancelworkflow(objDto);
	}
	catch(Exception e)
	{
		
	}
	return isCancelled;
}
public int disconnectProducts(String spidList,String orderno,String subChangeType,String serviceid)
{
	int isDisconnected = 0;
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	try
	{
		isDisconnected = objDao.disconnectProducts(spidList,orderno,subChangeType,serviceid);
	}
	catch(Exception e)
	{
		
	}
	return isDisconnected;
}

//[012] Start
/*function		:populateStoreCode
 * return type	:String
 * Author		:Anil Kumar
 * Date			:09-march-11
 * Purpose		:To fecth Tax Rate in charge section for product catelog page. 
 * */	
public String populateTaxRateForHardware(int storeid,int nSaleId,int tSaleId,int formAvailId,int hTypeId,Long ponumber,int fxChargeId)throws Exception
{
	String taxrate=null;
	NewOrderDao objDao=new NewOrderDao();
	try
	{
		taxrate=objDao.populateTaxRateForHardware(storeid,nSaleId,tSaleId,formAvailId,hTypeId,ponumber,fxChargeId);		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return taxrate;
}
	public int getNpdCategory(String ib2bWorkflowAttachedName) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		int lstNewOrder = 0;
	try 
	{
		
		lstNewOrder = objDao.getNpdCategory(ib2bWorkflowAttachedName);
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return lstNewOrder;

}

//[012] End
	
// [020]	Start
/*Function		:getOrderDetails
 * return type	:ArrayList
 * @Author		:Saurabh Singh
 * Date			:14-03-2011
 * Purpose		:To get order details for copying order
 * */
public ArrayList<OrderHeaderDTO> getOrderDetails(OrderHeaderDTO objDto) throws Exception 
{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<OrderHeaderDTO> orderList = null;
	try
	{
		orderList = objDao.getAllNewOrders(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return orderList;
}
/*Function		:getServicesForTheOrder
 * return type	:ArrayList
 * @Author		:Saurabh Singh
 * Date			:14-03-2011
 * Purpose		:To get services of related order
 * */
public ArrayList<ServiceLineDTO> getServicesForTheOrder(OrderHeaderDTO objDto) throws Exception 
{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<ServiceLineDTO> list = null;
	try
	{
		list = objDao.getServicesForTheOrder(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return list;
}
/*Function		:getOrderDetails
 * return type	:String
 * @Author		:Saurabh Singh
 * Date			:14-03-2011
 * Purpose		:To copy Services from one order to another
 * */
 
//Vijay
public String copyServicesToTheOrder(long orderNo, long enteredOrderNo, String serviceList, String serviceProductList, String poList, String licenseCompanyList, String storeList, String noOfCopy, Long roleID,String sessionID)
//public String copyServicesToTheOrder(long orderNo, long enteredOrderNo, String serviceList, String serviceProductList, String poList, String licenseCompanyList, String storeList)
throws Exception 
{
NewOrderDaoExt objDao = new NewOrderDaoExt();
CopyOrderModel objCopyModel=new CopyOrderModel(); 
String status=null;
try
{	
String copySource=Utility.getAppConfigValue("COPYORDERSOURCEKEY");
	if("NEWCODE".equalsIgnoreCase(copySource)){
		
		HashMap<String, HttpSession> hmap = (HashMap<String, HttpSession>) context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionID);		
		UserInfoDto objUserDto = (UserInfoDto) session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		
		status=objCopyModel.copyOrderUserInterfaceProcess(orderNo, enteredOrderNo, serviceList, serviceProductList, poList, licenseCompanyList, storeList, noOfCopy, roleID,objUserDto.getEmployeeId());
	}else{
		status = objDao.copyOrder(orderNo,enteredOrderNo,serviceList,serviceProductList,poList,licenseCompanyList,storeList,noOfCopy,roleID);
	}
} 
catch (Exception e) {
	Utility.LOG(true, false, e, "::from "+getClass().getName()+" method ->copyServicesToTheOrder()");
}
return status;
}
//[020]	End


//
public ArrayList<NewOrderDto>  getAccountDetailsWithSorting(PagingDto objDto) throws Exception{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList errors=new ArrayList();
	objDto.setAccountName(Utility.trimIfNotNull(objDto.getAccountName()));
	
	String accountName=objDto.getAccountName();
	Utility.validateValue(new ValidationBean()
				.loadValidationBean_Maxlength("Account Name", accountName, 150),
				Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
				.appendToAndRetNewErrs(errors);
	
	
	if(errors!=null && errors.size()>0)//During Server Side Validation
	{
		NewOrderDto dto=new NewOrderDto();
		dto.setErrors_Validation(errors);
		ArrayList<NewOrderDto> errorList=new ArrayList<NewOrderDto>();
		errorList.add(dto);
		//return errorList;
	}
	ArrayList<NewOrderDto> listAccountTypes= new ArrayList<NewOrderDto>();
	try 
	{
		listAccountTypes = objDao.getAccountDetailsWithSorting(objDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listAccountTypes;
}
//Added by Ashutosh As On Date : 03-Oct-2011 
public ArrayList<NewOrderDto>  getCustLogicalSIDetailsWithSorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();		
	objDto.setAccountName(Utility.trimIfNotNull(objDto.getCustSINo()));	
	
	
	ArrayList<NewOrderDto> listAccountTypes= new ArrayList<NewOrderDto>();
	try 
	{
		listAccountTypes = objDao.getCustLogicalSIDetailsWithSorting(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listAccountTypes;
}
//
/*Function		:populatePODetailsNoForChangeView
 * return type	:ArrayList
 * @Author		:Saurabh Singh
 * Date			:03-05-2011
 * Purpose		:To get POdetails in Billing Info Section for change view product catelog
 * */
public ArrayList<NewOrderDto> populatePODetailsNoForChangeView(NewOrderDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> listPODetails= new ArrayList<NewOrderDto>();
	try 
	{
		listPODetails = objDao.getPODetailForChangeView(objDto);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listPODetails;
}

//[0020]---STart
public ArrayList<ProductCatelogDTO> populateBCPWithDispatch(OrderHeaderDTO objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<ProductCatelogDTO> listStore= null;
	try 
	{
		listStore = objDao.populateBCPWithDispatch(objDto.getAccountID());
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listStore;
}
//[0020]---End
//Raghu
//added for Pop Location PopUp on product catelog page
public ArrayList<NewOrderDto>  populateNPLocationAddress1(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();
	objDto.setAccountName(Utility.trimIfNotNull(objDto.getPopNetLocName()));
	
	String accountName=objDto.getPopNetLocName();
	Utility.validateValue(new ValidationBean()
				.loadValidationBean_Maxlength("Location Name", accountName, 150),
				Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
				.appendToAndRetNewErrs(errors);
	
	
	if(errors!=null && errors.size()>0)//During Server Side Validation
	{
		NewOrderDto dto=new NewOrderDto();
		dto.setErrors_Validation(errors);
		ArrayList<NewOrderDto> errorList=new ArrayList<NewOrderDto>();
		errorList.add(dto);
		//return errorList;
	}
	ArrayList<NewOrderDto> listNPLocationAddress= new ArrayList<NewOrderDto>();
	try 
	{
		listNPLocationAddress = objDao.populateNPLocationAddress1(objDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listNPLocationAddress;
}

//[025]	Start
/*Function		:getProductNameAndID
 * return type	:List
 * @Author		:Saurabh Singh
 * Date			:16-02-2011
 * Purpose		:To get Product name and id for copying charges
 * */
public ArrayList<ServiceLineDTO> getProductAndId(ServiceLineDTO objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<ServiceLineDTO> listStore= null;
	try 
	{
		listStore = objDao.getProductAndId(objDto.getServiceId());
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listStore;
}	

public ArrayList<NewOrderDto> getAllActiveProductNameList() throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<NewOrderDto> listofActiveProductIdwithName= null;
	try 
	{
		listofActiveProductIdwithName = objDao.getProductAndId();
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listofActiveProductIdwithName;
}

/*Function		:getProductNameAndID
 * return type	:List
 * @Author		:Saurabh Singh
 * Date			:16-02-2011
 * Purpose		:To get Product name and id for copying charges
 * */
public ArrayList<ServiceLineDTO> getSubProductAndId(ServiceLineDTO objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<ServiceLineDTO> listStore= null;
	try 
	{
		listStore = objDao.getSubProductAndId(objDto.getServiceTypeId());
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listStore;
}	

/*Function		:savePublish
 * return type	:String
 * @Author		:Saurabh Singh
 * Date			:16-02-2011
 * Purpose		:To copy charges from one line item to other
 * */
public String savePublish(NewOrderDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String isModify="";
	try 
	{
		//start[028]
		if(isSessionValid(objDto.getSessionid())!=null){
		isModify = objDao.savePublish(objDto.getServiceId(),objDto.getServiceTypeId(),objDto.getServiceSubtypeId(),objDto.getIsPublished());
		}
		else{
			isModify=null;
		}
		//end[028]
	} catch (Exception e) {
		e.printStackTrace();
	}
	return isModify;
}
/*Function		:getServicesForTheOrder
 * return type	:ArrayList
 * @Author		:Saurabh Singh
 * Date			:14-03-2011
 * Purpose		:To get services of related order
 * */
public ArrayList<ServiceLineDTO> getUpdateData(ServiceLineDTO objDto) throws Exception 
{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<ServiceLineDTO> list = null;
	try
	{
		list = objDao.getDataForPartialPublish(objDto.getServiceId());
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return list;
}
public String getUpdateData1(NewOrderDto objDto) throws Exception 
{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		String  serviceId_str=null;
	try
	{
		serviceId_str= objDao.getDataForPartialPublish1(objDto.getPoNumber());
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return serviceId_str;
}
/*Function		:getServiceListForTheOrder
 * return type	:ArrayList
 * @Author		:Saurabh Singh
 * Date			:14-03-2011
 * Purpose		:To get services of related order
 * */
public ArrayList<ServiceLineDTO> getServiceListForTheOrder(OrderHeaderDTO objDto) throws Exception 
{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<ServiceLineDTO> list = new ArrayList<ServiceLineDTO>();
	try
	{
		list = objDao.getServiceListForTheOrder(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return list;
}

/*Function		:getServiceListForTheOrderWithoutPaging
* return type	:ArrayList
* @Author		:Vijay Singh
* Purpose		:To get services of related order
* */
public ArrayList<ServiceLineDTO> getServiceListForTheOrderWithoutPaging(OrderHeaderDTO objDto) throws Exception 
{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<ServiceLineDTO> list = new ArrayList<ServiceLineDTO>();
	try
	{
		list = objDao.getServiceListForTheOrderWithoutPaging(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return list;
}
//Vijay end

/*Function		:updateViewIsPublish
 * return type	:String
 * @Author		:Saurabh Singh
 * Date			:14-03-2011
 * Purpose		:To update isPublished after we view it on clicking publish button
 * */
public String updateViewIsPublish(NewOrderDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	NewOrderModel model = new NewOrderModel();
	NewOrderDto objRetDto=null;
	String status=null;
	try
	{
		objDto.setRemarks("Publish");
		objRetDto=model.validateVCSServicesBeforeDelete(objDto);
		//if(objRetDto.getMsgOut()==null)
		//status = objDao.updateViewIsPublish(objDto);
		//else
			status=objRetDto.getMsgOut();
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return status;
}
//[025]	End

/*Function		:updateViewIsPublishByPublishAll
 * return type	:ArrayList<String>
 * @Author		:Vijay Pathak
 * Date			:09-09-2013
 * Purpose		:This method is copied of updateViewIsPublish() and retrung a list.
 * 				 This method is call when user click it on clicking publish All button
 * */
public ArrayList<String> updateViewIsPublishByPublishAll(NewOrderDto objDto) throws Exception 
{
	NewOrderModel model = new NewOrderModel();
	NewOrderDto objRetDto=null;
	String status="";
	ArrayList<String> publishAndServiceList = new ArrayList<String>(); 
	String serviceList = objDto.getServiceIdString();
	String publishList = objDto.getIsPublishedString();
	
	StringBuffer returnServiceList = new StringBuffer("");
	StringBuffer returnIsPublishedList= new StringBuffer("");
	try
	{
		objDto.setRemarks("Publish");
		objRetDto=model.validateVCSServicesBeforeDelete(objDto);
		status=objRetDto.getMsgOut();
		//Vijay start
		/*
		 * If status is blank or null that means all serviceIds are eligibile for publish otherwise
		 * there are some service Ids need to be restrict to publish
		 * 
		 *  So we are going to here collect final list of services Ids. So there are two variable :
		 *   
		 */
		//[121] START
		if(status!=null && status!=""){
			String[] serviceIds = serviceList.split(",");
			String[] isPublishFlags =  publishList.split(",");
			HashMap<String, Integer> serviceIdsMap = objRetDto.getServiceIdMap();
			for(int i=0; i<serviceIds.length; i++){
				int inputPublishFlag=Integer.parseInt((isPublishFlags[i]));
				if(!(inputPublishFlag==0 || inputPublishFlag==1)){
					inputPublishFlag=0;
				}
				//TODO vipin merge status
				if(serviceIdsMap.containsKey(serviceIds[i])){
					returnServiceList= returnServiceList.append(serviceIds[i]).append(",");
					returnIsPublishedList.append(0*inputPublishFlag).append(",");
				}
				else{
					returnServiceList.append(serviceIds[i]).append(",");
					returnIsPublishedList.append(1*inputPublishFlag).append(",");
				}
			}
		}
		publishAndServiceList.add(status);  //at 1st position it is status
		publishAndServiceList.add(returnServiceList.toString()); //it is service list  
		publishAndServiceList.add(returnIsPublishedList.toString()); //it containt flag in the form of 1 or 0 respective to each service
		//vijay end
		
		//if(objRetDto.getMsgOut()==null)
		//status = objDao.updateViewIsPublish(objDto);
		//else
		//[121] END	
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return publishAndServiceList;
}
//Vijay end

/*Function		:updateViewIsPublish
 * return type	:String
 * @Author		:Saurabh Singh
 * Date			:14-03-2011
 * Purpose		:To update isPublished after we view it on clicking publish button
 * */
/*public String updateFullPublish(NewOrderDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String status=null;
	try
	{
		status = objDao.updateFullPublish(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return status;
}
*/

//start[0021]
public ArrayList<NewOrderDto>  getBCPDetailsWithSorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();	
	ArrayList<NewOrderDto> listBCPDetails= new ArrayList<NewOrderDto>();
	try 
	{						
			listBCPDetails = objDao.getBCPDetailsWithSorting(objDto);
			
	} catch (Exception e) {
		e.printStackTrace();
		listBCPDetails=null;
	}
	return listBCPDetails;
}
//end[0021]


public ArrayList<SITransferDto>  fetchPartywithSorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();	
	ArrayList<SITransferDto> listPartyDetails= new ArrayList<SITransferDto>();
	try 
	{						
		listPartyDetails = objDao.fetchPartywithSorting(objDto);
			
	} catch (Exception e) {
		e.printStackTrace();
		listPartyDetails=null;
	}
	return listPartyDetails;
}
//end[0021]

public ArrayList<SITransferDto> fetchParty() throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> partyList = new ArrayList<SITransferDto>();
	try
	{
		partyList = objDao.fetchParty();
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return partyList;
}


public ArrayList<SITransferDto> getLogicalSINumbers(SITransferDto objDto,String sessionid) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> partyList = new ArrayList<SITransferDto>();
	try
	{
		//start[028]
		if(isSessionValid(sessionid)!=null){
		partyList = objDao.getLogicalSINumber(objDto);
		}
		else
		{
			partyList=null;
		}
		//end[028]
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return partyList;
}

//021 start
public ArrayList<SITransferDto> fetchAccount(SITransferDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> AccountList = new ArrayList<SITransferDto>();
	try
	{
		AccountList = objDao.fetchAccount(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return AccountList;
}

//021 end




public ArrayList<SITransferDto> fetchProductList(String  serviceID) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> AccountList = new ArrayList<SITransferDto>();
	try
	{
		AccountList = objDao.fetchProductList(serviceID);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return AccountList;
}

public ArrayList<SITransferDto> fetchBCPDetails(SITransferDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> BCPDetailsList = new ArrayList<SITransferDto>();
	try
	{
		BCPDetailsList = objDao.fetchBCPDetails(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return BCPDetailsList;
}

//022 end

//023 start
public ArrayList<SITransferDto> fetchDispatchInfo(SITransferDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> DispatchDetailsList = new ArrayList<SITransferDto>();
	try
	{
		DispatchDetailsList = objDao.fetchDispatchInfo(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return DispatchDetailsList;
}

//023 end
public ArrayList<SITransferDto>  processSITransfer(SITransferDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> returnDto = new ArrayList<SITransferDto>();
	try
	{
		returnDto = objDao.processSITransfer(objDto).getGuiProcessResult();
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return returnDto;
}

public ArrayList<CurrencyChangeDto>  processCurrencyChange(CurrencyChangeDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<CurrencyChangeDto> returnDto = new ArrayList<CurrencyChangeDto>();
	try
	{
		returnDto = objDao.processCurrencyChange(objDto).getGuiProcessResult();
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return returnDto;
}
//Currency Change
public ArrayList<CurrencyChangeDto> getLogicalSINO(CurrencyChangeDto objDto,String sessionid) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<CurrencyChangeDto> partyList = new ArrayList<CurrencyChangeDto>();
	try
	{
		//start[028]
		if(isSessionValid(sessionid)!=null){
		partyList = objDao.getLogicalSINO(objDto);
		}
		else
		{
			partyList=null;	
		}
		//end[028]
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return partyList;
}
//024 start
public ArrayList<SITransferDto> InsertUpdateSIDetails(SITransferDto objdto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> AccountList = new ArrayList<SITransferDto>();
	try
	{
		 objDao.InsertUpdateSIDetails(objdto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return AccountList;
}
//024 end
/*Function		:newOrderStatus
 * return type	:Long
 * @Author		:Saurabh Singh
 * Date			:28-04-2011
 * Purpose		:To get published services in M6
 * */
public Long newOrderStatus(int spid)throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	Long TM6OrderNo=null;
	try {
		
		TM6OrderNo= objDao.newOrderStatus(spid);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return TM6OrderNo;
	
}
/*Function		:getServicesForTheOrderInM6
 * return type	:ArrayList
 * @Author		:Saurabh Singh
 * Date			:14-03-2011
 * Purpose		:To get services of related order in Tm6_NewOrder_Status
 * */
public ArrayList<NewOrderDto> getServicesForTheOrderInM6(NewOrderDto objDto) throws Exception 
{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<NewOrderDto> list = new ArrayList<NewOrderDto>();
	try
	{
		list = objDao.getServiceListForTheOrderInM6(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return list;
}
/*Function		:newOrderStatus
 * return type	:Long
 * @Author		:Saurabh Singh
 * Date			:28-04-2011
 * Purpose		:To get published services in M6
 * */
public Long newOrderStatusServiceId(int serviceId)throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	Long TM6OrderNo=null;
	try {
		
		TM6OrderNo= objDao.newOrderStatusServiceId(serviceId);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return TM6OrderNo;
	
}

//Ramana start
public Long serviceInactiveFlagCheck(int serviceId)throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	Long serviceInactiveFlagCheck=null;
	try {
		
		serviceInactiveFlagCheck= objDao.serviceInactiveFlagCheck(serviceId);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return serviceInactiveFlagCheck;
	
}
//Ramana end

//start[028]
public UserInfoDto isSessionValid(String sessionid ) throws IllegalStateException
{
	UserInfoDto objUserDto = null;
	try
	{
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionid);		
		objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		
	}
	catch(IllegalStateException ex)
	{
		
	}
	return objUserDto;
}


//start anil
public ArrayList<NewOrderDto> getQueryFormValueList(PagingDto objDto) throws Exception
{
	ArrayList<NewOrderDto> listQueryOptions= new ArrayList<NewOrderDto>();	
	UniversalWorkQueueDao objUniversalWorkQueueDao =  new UniversalWorkQueueDao();
	try
	{
		listQueryOptions = objUniversalWorkQueueDao.getQueryFormValueList(objDto);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();	
	}
	return listQueryOptions;	

}
//end[028]


/*Function		:getOrderDetailForSearching
 * return type	:ArrayList
 * @Author		:Saurabh Singh
 * Date			:21-06-2011
 * Purpose		:To get order details for performing searching using ctrl+F11
 * */
public ArrayList<OrderHeaderDTO> getOrderDetailForSearching(long orderNo,long userRoleId,String userId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<OrderHeaderDTO> orderList = new ArrayList<OrderHeaderDTO>();
	try
	{
		orderList = objDao.getOrderDetailForSearching(orderNo,userRoleId,userId);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return orderList;

}
//end Anil


/*Function		:getShortCode
 * return type	:String
 * @Author		:Saurabh Singh
 * Date			:22-06-2011
 * Purpose		:To update isPublished after we view it on clicking publish button
 * */
public String getShortCode(long userRoleId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String shortCode=null;
	try
	{
		shortCode = objDao.getShortCode(userRoleId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return shortCode;
}

//start Anil
public String getExpectedValueForQueryForm(int queryNameId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String expectedvalue=null;
	try{
		expectedvalue=objDao.getExpectedValueForQueryForm(queryNameId);
	}
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return expectedvalue;
}
//end Anil


/*Function		:SaveActionFromSEDToCOPC
 * return type	:ViewOrderDto
 * @Author		:Saurabh Singh
 * Date			:22-06-2011
 * Purpose		:To send approval back to COPC from SED
 * */

public ViewOrderDto SaveActionFromSEDToCOPC(ViewOrderDto objDto , String sessionid) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ViewOrderDto objRetDto = new ViewOrderDto();
	try 
	{
		if(isSessionValid(sessionid)!=null)
		{
			objRetDto = objDao.SaveActionFromSEDToCOPC(objDto,Long.valueOf(isSessionValid(sessionid).getEmployeeId()), 
					Integer.valueOf(objDto.getUserid()));
		}
		else
		{
			objRetDto=null;
		}
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return objRetDto;
}
public int reattachworkflowcheck(NewOrderDto ordDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int poNumber1 = 0;
	try{
		poNumber1=objDao.reattachworkflowcheck(ordDto);
	}
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return poNumber1;

}
//raghu
public String validateSassProduct(String orderno,String sessionid) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String ErrorMessege=null;
	try
	{
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionid);		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		ErrorMessege = objDao.validateSassProduct(Long.parseLong(orderno),Integer.parseInt(objUserDto.getUserRoleId()));
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return ErrorMessege;
}
//Raghu
public String validateFeild(String orderNo,String sessionid) throws Exception 
{
	FeildValidationUtilityDAO objDao = new FeildValidationUtilityDAO();
	String expectedvalue=null;
	try{
		long orderNumber=Long.parseLong(orderNo);
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionid);		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		expectedvalue=objDao.feildValidation(orderNumber,Integer.parseInt(objUserDto.getUserRoleId()));
	}
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return expectedvalue;
}
public ArrayList<FieldAttibuteDTO> getFieldValidationForService(String sessionid,String serviceid)throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<FieldAttibuteDTO> lstDto = null;

	try {
		
		
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionid);		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		lstDto = objDao.getFieldValidationForService(Integer.parseInt(objUserDto.getUserRoleId().toString()),Integer.parseInt(serviceid));

					
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return lstDto;
	
}
//Start[030]
public ArrayList<PoDetailsDTO> populatePODetailsNoForProduct(PoDetailsDTO objDto) throws Exception{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<PoDetailsDTO> listPODetails= new ArrayList<PoDetailsDTO>();
		try{
		//	long orderNo=objDto.getPoNumber();
			listPODetails = objDao.getPODetailForProduct(objDto);
		}catch (Exception e){
			e.printStackTrace();
		}
		return listPODetails;
	}

//End[030]

public PoDetailsDTO populatePODetailsForSelectedPO(String custPOID) throws Exception{
	NewOrderDao objDao = new NewOrderDao();
	PoDetailsDTO pODetails= new PoDetailsDTO();
	try{
	//	long orderNo=objDto.getPoNumber();
		pODetails = objDao.getPODetailForPO(custPOID);
	}catch (Exception e){
		e.printStackTrace();
	}
	return pODetails;
}

public ArrayList<ViewOrderDto> fnInsertDisconnectionDate(ViewOrderDto objdto) throws Exception 
{
	NewOrderDao objDao = new NewOrderDao();
	ViewOrderDto dto=new ViewOrderDto();
	ArrayList<ViewOrderDto> result =null;
	try
	{
		result=objDao.fnInsertDisconnectionDate(objdto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return result;
	
}
/*Function		:getAdditionalNode
 * return type	:Integer
 * @Author		:Saurabh Singh
 * Date			:19-07-2011
 * Purpose		:To check whether Product is additional node
 * */
/*[043] Start : commented for Extra Hit into DB
public int getAdditionalNode(long serviceProductId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int flagNode=0;
	try
	{
		flagNode= objDao.getAdditionalNode(serviceProductId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return flagNode;

}
[043]END*/

/*Function		:checkAdditionalNode
 * return type	:String
 * @Author		:Saurabh Singh
 * Date			:19-07-2011
 * Purpose		:To compare all 17 parameters of childNode and parentNode
 * */
public String checkAdditionalNode(long serviceProductId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String compareResult=null;
	try
	{
		compareResult = objDao.checkAdditionalNode(serviceProductId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return compareResult;

}

/*Function		:populateProductList
 * return type	:ArrrayList
 * @Author		:Saurabh Singh
 * Date			:21-07-2011
 * Purpose		:To get all product/line item for comparision of parent and child node
 * */
public ArrayList<ServiceLineDTO>populateProductList(long serviceProductId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<ServiceLineDTO> productList = null;
	try
	{
		productList = objDao.populateProductList(serviceProductId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return productList;

}

//Created By Saurabh for Search BulkUpload to get Parties
public ArrayList<TransactionTemplateDto> populateParty(int i) throws Exception 
{	
	ErrorFileDaoImpl trDao = new ErrorFileDaoImpl();
	ArrayList<TransactionTemplateDto> PartyList = new ArrayList<TransactionTemplateDto>();
	
	try
	{
		PartyList= trDao.fetchPartyDetailsForSearchBulkUpload();
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return PartyList;
}
//End
//Created By Saurabh for Search BulkUpload to get Accounts
public ArrayList<TransactionTemplateDto> populateAccount(PagingDto objDto) throws Exception 
{	
	ErrorFileDaoImpl trDao = new ErrorFileDaoImpl();
	ArrayList<TransactionTemplateDto> AccountList = new ArrayList<TransactionTemplateDto>();
	
	try
	{
		AccountList = trDao.fetchAccountDetailsForSearchBulkUpload(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return AccountList ;
}
//End
//Created By Saurabh for Search BulkUpload to get Accounts
public ArrayList<TransactionTemplateDto> populateLSI(PagingDto objdto) throws Exception 
{	
	ErrorFileDaoImpl trDao = new ErrorFileDaoImpl();
	ArrayList<TransactionTemplateDto> LSIList = new ArrayList<TransactionTemplateDto>();
	
	try
	{
		LSIList = trDao.fetchLSIDetailsForSearchBulkUpload(objdto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return LSIList ;
}

/*Function		:populatePODetailForCopyOrder
 * return type	:ArrrayList
 * @Author		:Saurabh Singh
 * Date			:28-07-2011
 * Purpose		:To get po's for specific product for copying order
 * */
public ArrayList<PoDetailsDTO>populatePODetailForCopyOrder(long orderNo,long enteredOrderNo, long serviceProductId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<PoDetailsDTO> lstPODetail = null;
	try
	{
		lstPODetail = objDao.populatePODetailForCopyOrder(orderNo,enteredOrderNo,serviceProductId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return lstPODetail;

}

/*Function		:populateLicenseCompanyForCopyOrder
 * return type	:ArrrayList
 * @Author		:Saurabh Singh
 * Date			:28-07-2011
 * Purpose		:To get license company's for specific product for copying order
 * */
public ArrayList<ProductCatelogDTO>populateLicenseCompanyForCopyOrder(long serviceProductId,long poId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<ProductCatelogDTO> lstLCDetail = new ArrayList<ProductCatelogDTO>();
	try
	{
		lstLCDetail = objDao.populateLicenseCompanyForCopyOrder(serviceProductId,poId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return lstLCDetail;

}
/*Function		:populatePODetailsNoForChangeView
 * return type	:ArrayList
 * @Author		:Saurabh Singh
 * Date			:03-05-2011
 * Purpose		:To get POdetails in Billing Info Section for change view product catelog
 * */
public ArrayList<NewOrderDto> populatePODetailsNoForChangeViewWithLE(NewOrderDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> listPODetails= new ArrayList<NewOrderDto>();
	try 
	{
		listPODetails = objDao.getPODetailForChangeView(objDto);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listPODetails;
}
/*Function		:populateStoreForCopyOrder
 * return type	:ArrrayList
 * @Author		:Saurabh Singh
 * Date			:28-07-2011
 * Purpose		:To get Store's for specific product for copying order
 * */
public ArrayList<ProductCatelogDTO>populateStoreForCopyOrder(long serviceProductId,long licComp) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<ProductCatelogDTO> lstStoreDetail =null;
	try
	{
		lstStoreDetail = objDao.populateStoreForCopyOrder(serviceProductId,licComp);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return lstStoreDetail;

}
//Start[031]
public ArrayList validateDestinationCharges(ChargeComponentDTO objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList outputvalue=new ArrayList();
	try 
	{
		outputvalue = objDao.validateDestinationCharges(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return outputvalue;
}

// End [031]
//Meenakshi : Added for CBR_20120806-XX-07984
public String dmxMandatoryValidation(String OrderNo) throws Exception 
{	
	NewOrderDao Dao = new NewOrderDao();
	String str=null;
	
	try
	{
		str = Dao.dmxMandatoryValidation(Long.parseLong(OrderNo));
		
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return str ;
}
public ArrayList fnDmxValidation(String OrderNo) throws Exception 
{	
	NewOrderDao Dao = new NewOrderDao();
	ArrayList LSIList = new ArrayList();
	String str=null;
	
	try
	{
		str = Dao.fnDmxValidation(Long.parseLong(OrderNo));
		
		String[] strArr=new String[1];
		strArr=str.split("@@");
		LSIList.add(0, strArr[0]);
		LSIList.add(1, strArr[1]);
		LSIList.add(2, strArr[2]);
		LSIList.add(3, strArr[3]);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return LSIList ;
}
public ArrayList fnDmxCountPlayerInService(String serviceNo) throws Exception 
{	
	NewOrderDao Dao = new NewOrderDao();
	ArrayList list = new ArrayList();
	String str=null;
	
	try
	{
		str = Dao.fnDmxCountPlayerInService(Long.parseLong(serviceNo));
		String[] strArr=new String[1];
		strArr=str.split("@@");
		list.add(0, strArr[0]);
		list.add(1, strArr[1]);
		list.add(2, strArr[2]);
		list.add(3, strArr[3]);
		//list.add(4, strArr[4]);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return list ;
}
//raghu
//Meenakshi: added for signage Child validation
public String validateDMXLogicalLSI(String orderno,String poNumber) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String ErrorMessege=null;
	try
	{
		ErrorMessege = objDao.validateDMXLogicalLSI(Long.parseLong(orderno), Long.parseLong(poNumber));
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return ErrorMessege;
}

public String checkProductServiceType(String serviceDetID)
{
	NewOrderDao objDao = new NewOrderDao();
	String serviceType=null;
	try
	{
		serviceType = objDao.checkProductServiceType(Long.parseLong(serviceDetID));
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return serviceType;
	
}
public ArrayList<NewOrderDto> populateEntityForChange(String serviceProductID) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> listPOEntity= new ArrayList<NewOrderDto>();
	try 
	{
		
		listPOEntity = objDao.populateEntityForChange(serviceProductID);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listPOEntity;
}
public ArrayList<NewOrderDto> populateLicCompanyForChange(String serviceProductID) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> listPOEntity= new ArrayList<NewOrderDto>();
	try 
	{
		listPOEntity = objDao.populateLicCompanyForChange(serviceProductID);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return listPOEntity;
}

public ArrayList<TreeViewDto> ViewDropDownTree(String serviceid)throws Exception
{
	ArrayList<TreeViewDto> listChildMenu = null;
	NewOrderDao objDao = new NewOrderDao();
	//NewOrderDto objRetDto = new NewOrderDto();
	long servicetypeid = Long.parseLong(serviceid);
	try {
		//listChildMenu = new ArrayList<TreeViewDto>();
		listChildMenu = objDao.ViewTreeForDropDown(servicetypeid);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listChildMenu;
}

/*Function		:populatePODetailForCopyOrder
 * return type	:ArrrayList
 * @Author		:Saurabh Singh
 * Date			:24-09-2011
 * Purpose		:for billing level check in product catelog
 * */
public ArrayList<NewOrderDto>checkBillingLevel(long serviceIdNo) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<NewOrderDto> lstBillingDetail = new ArrayList<NewOrderDto>();
	try
	{
		lstBillingDetail = objDao.checkBillingLevel(serviceIdNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return lstBillingDetail;

}
//lawkush start

//public ArrayList populateHDateLogic()(NewOrderDto objDto) throws Exception

 
public ArrayList<ProductCatelogDTO> populateHDateLogic() throws Exception 
{
	ArrayList<ProductCatelogDTO> listSubLineItem = null;
	NewOrderDao objDao = new NewOrderDao();
	//NewOrderDto objDto=new NewOrderDto();
	try
	{
		listSubLineItem = objDao.populateHDateLogic();
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return listSubLineItem;

}
/*Function		:updateStatus
 * return type	:String
 * @Author		:Saurabh Singh
 * Date			:27-09-2011
 * Purpose		:for valid invalid check against OrderNo
 * */
public String updateStatus(String orderno,int status) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String validated=null;
	try
	{
		validated = objDao.updateStatus(Long.parseLong(orderno),status);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return validated;
}

public ArrayList<ServiceLineDTO> getProductDetails(String orderno) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<ServiceLineDTO> lstNeworder = null;
	try
	{
		lstNeworder = objDao.getProductDetails(orderno);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return lstNeworder;
}


//032 start

public NewOrderDto DeleteService(NewOrderDto objDto)
throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	NewOrderDto objRetDto = new NewOrderDto();
	try {
		objRetDto = objDao.DeleteService(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}
//032 end

//[033] Start
	public ArrayList<NewOrderDto> fetchCancelCopyReport(NewOrderDto objDto) 
	throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto objRetDto = new NewOrderDto();
		ArrayList<NewOrderDto> lstNeworder = new ArrayList<NewOrderDto>();
		try 
		{
			lstNeworder = objDao.fetchCancelCopyReport(objDto);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return lstNeworder;
	}
//Start [035]
public NewOrderDto cancelAndCopy(NewOrderDto objDto)
throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	NewOrderDto objRetDto = new NewOrderDto();
	try {
			objRetDto = objDao.cancelAndCopy(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}
//End [035]
public ArrayList<NewOrderDto>  getComponentsWithSorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();
	objDto.setComponentName(Utility.trimIfNotNull(objDto.getComponentName()));
	
	String componentName=objDto.getComponentName();
	Utility.validateValue(new ValidationBean()
				.loadValidationBean_Maxlength("Component Name", componentName, 150),
				Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
				.appendToAndRetNewErrs(errors);
	
	
	if(errors!=null && errors.size()>0)//During Server Side Validation
	{
		NewOrderDto dto=new NewOrderDto();
		dto.setErrors_Validation(errors);
		ArrayList<NewOrderDto> errorList=new ArrayList<NewOrderDto>();
		errorList.add(dto);
		//return errorList;
	}
	ArrayList<NewOrderDto> components= new ArrayList<NewOrderDto>();
	try 
	{
		objDto.setSortBycolumn("COMPONENT_ID");
		components = objDao.getComponentsWithSorting(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return components;
}

public ArrayList<ComponentsDto> populateComponentsDetails(NewOrderDto objDto) throws Exception
{
	NewOrderDaoExt objDaoExt = new NewOrderDaoExt();
	ArrayList<ComponentsDto> saDto=new ArrayList<ComponentsDto>();
	try 
	{
		saDto = objDaoExt.populateComponentsDetails(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return saDto;
}
//Charge Details For All Orders
public ArrayList<ComponentsDto> fnGetComponentsDetails(ViewOrderDto dto)
	throws Exception
{
	logger.info("In fnGetComponentsDetails");
	ArrayList<ComponentsDto> result;
	Connection conn = null;
	try 
	{
		conn=DbConnection.getConnectionObject();
	
		ViewOrderDao dao=new ViewOrderDao();
		result=dao.fnGetComponentsDetails(dto,conn);
		
		
	}
	catch(Exception ex)
	{
		logger.error(ex.getMessage()+ " Exception occured in fnGetChargesAllOrders method of "+ this.getClass().getSimpleName());
		throw Utility.onEx_LOG_RET_NEW_EX(ex, "", "", "", true, true);
	}
	finally
	{
		try {
			DbConnection.freeConnection(conn);
		} catch (Exception e) {
			throw Utility.onEx_LOG_RET_NEW_EX(e, "", "", "", true, true);
		}
	}
	return result;
}
public ArrayList<NewOrderDto>  getPackagesWithSorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();
	objDto.setPackageName(Utility.trimIfNotNull(objDto.getPackageName()));
	
	String packageName=objDto.getPackageName();
	Utility.validateValue(new ValidationBean()
				.loadValidationBean_Maxlength("Package Name", packageName, 150),
				Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
				.appendToAndRetNewErrs(errors);
	
	
	if(errors!=null && errors.size()>0)//During Server Side Validation
	{
		NewOrderDto dto=new NewOrderDto();
		dto.setErrors_Validation(errors);
		ArrayList<NewOrderDto> errorList=new ArrayList<NewOrderDto>();
		errorList.add(dto);
		//return errorList;
	}
	ArrayList<NewOrderDto> packages= new ArrayList<NewOrderDto>();
	try 
	{
		packages = objDao.getPackagesWithSorting(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return packages;
}
//Start [036]
public int getHardwareCount(long serviceId,long serviceDetailId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int hardwareCount=0;
	try
	{
		hardwareCount= objDao.getHardwareCount(serviceId,serviceDetailId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return hardwareCount;

}
//End [036]
public int getDataForHardware(ServiceLineDTO objDto) throws Exception 
{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		int isHardwareValid =0 ;
	try
	{
		isHardwareValid = objDao.getDataForHardware(objDto.getServiceId());
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return isHardwareValid;
}

//Start[037]
public int getM6LineItemCheck(long serviceDetailId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int isM6LineItem=-1;
	try
	{
		isM6LineItem= objDao.getM6LineItemCheck(serviceDetailId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return isM6LineItem;
}
//End[037]
//Add by Anil for GAM related methods
/*Function:getGAMListWithQuotes
 * Purpose:To fetch already attached gam with order
 * Created by:Anil Kumar
 * Date:27-Dec-2011
 * */
public ArrayList<NewOrderDto> getGAMListWithQuotes(PagingDto objDto) throws Exception
{
		ArrayList<NewOrderDto> newOrdDtoAL=new ArrayList<NewOrderDto>();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{
			newOrdDtoAL=objDao.getGAMList(objDto);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return newOrdDtoAL;
	
}


//Add by Anil for GAM related methods
/*Function:getMaximumGAM
 * Purpose:to Get Maximum GAM for attach with a order
 * Created By:Anil Kumar
 * Date:28-Dec-2011
 * */
public String getMaximumGAM() throws Exception
{
		String maxGam="";		
		try{
			maxGam=Utility.getAppConfigValue("MAXGAMSELECTION");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return maxGam;
	
}
//Add by Anil for GAM related methods
/*Function:saveGamList
 *Purpose:To save GAM List with Order and quotes with transaction table
 * Created by:Anil Kumar
 * Date:29-Dec-2011
 */
public int saveGamList(String strGAMIds,int orderno,String quote,String sessionId) throws Exception
{
		int successMsg=-1;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionId);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			successMsg=objDao.saveGamList(strGAMIds,orderno,quote,sessionId);
			
		}catch(IllegalStateException ex)
		{
			successMsg=1;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return successMsg;
	
}
//Add by Anil for GAM related methods
/*Function:deleteGamList
 *Purpose:To delete GAM List with Order from  transaction table
 * Created by:Anil Kumar
 * Date:03-Jan-2012
 */
public int deleteGamList(String strGAMIds,int orderno,String sessionId) throws Exception
{
		int successMsg=-1;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionId);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			successMsg=objDao.deleteGamList(strGAMIds,orderno,sessionId);
			
		}catch(IllegalStateException ex)
		{
			successMsg=1;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return successMsg;
	
}
/*Function:validateGAMBeforeSave
 * Purpose:To validate GAM before save,is GAM already attached,maximum GAM reached
 * Created By:Anil Kumar
 * Date:28-Dec-2011
 * */
public NewOrderDto validateGAMBeforeSave(String strGAMIds,int orderno,String sessionId) throws Exception
{
		NewOrderDto msgList=new NewOrderDto();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionId);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			msgList=objDao.validateGAMBeforeSave(strGAMIds,orderno);
		}catch(IllegalStateException ex)
		{
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return msgList;
	
}
/*Function:getGAMListAll
 * Purpose:To get all GAM List
 * Created By:Anil Kumar
 * Date:30-Dec-2011
 * */
public ArrayList<NewOrderDto> getGAMListAll(PagingDto objDto) throws Exception
{
	ArrayList<NewOrderDto> allGAMList=new ArrayList<NewOrderDto>();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			allGAMList=objDao.getGAMListAll(objDto);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return allGAMList;
	
}
//End By Anil GAM related methods

//Start[038]
public String getServiceName(int serviceNo) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String serviceName=null;
	try
	{
		serviceName = objDao.getServiceName(serviceNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return serviceName;
}
//End[038]
/*Function:getGamOrderList
 * Purpose:To get all Order List according to GAM
 * Created By:LAWKUSH
 * Date:5-JAN-2012
 * */
public ArrayList<NewOrderDto> getGamOrderList(PagingDto objDto) throws Exception
{
	ArrayList<NewOrderDto> allGAMList=new ArrayList<NewOrderDto>();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			allGAMList=objDao.getGamOrderList(objDto);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return allGAMList;
	
}
//End By Anil GAM related methods

/*Function:getGAMFormulaList
 * Purpose:To get all GAM List
 * Created By:Anil Kumar
 * Date:4-Jan-2012
 * */
public ArrayList<NewOrderDto> getGAMFormulaList(int orderno) throws Exception
{
	ArrayList<NewOrderDto> allGAMFormulaList=new ArrayList<NewOrderDto>();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			allGAMFormulaList=objDao.getGAMFormulaList(orderno);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return allGAMFormulaList;
	
}

/**Function:getFormulaNameList
* Purpose:To fetch Formula Name for fill combo
* Created By:Anil Kumar
* Date:4-Jan-2012
* */
public ArrayList<NewOrderDto> getFormulaNameList() throws Exception
{
	ArrayList<NewOrderDto> allGAMFormulaNameList=new ArrayList<NewOrderDto>();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			allGAMFormulaNameList=objDao.getFormulaNameList();			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return allGAMFormulaNameList;
	
}

//Add by Anil for GAM related methods
/*Function:saveGamFormula
 *Purpose:To save GAM Formula with order
 * Created by:Anil Kumar
 * Date:4-Jan-2012
 */
public int saveGamFormula(int formulaId,int orderno,String sessionId) throws Exception
{
		int successMsg=-1;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionId);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			successMsg=objDao.saveGamFormula(formulaId,orderno,sessionId);
			
		}catch(IllegalStateException ex)
		{
			successMsg=-2;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return successMsg;
	
}

//Add by Anil for GAM related methods
/*Function:deleteGamFormula
 *Purpose:To delete GAM List with Order from  transaction table
 * Created by:Anil Kumar
 * Date:04-Jan-2012
 */
public int deleteGamFormula(int orderno,String sessionId) throws Exception
{
		int successMsg=-1;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionId);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			successMsg=objDao.deleteGamFormula(orderno,sessionId);
			
		}catch(IllegalStateException ex)
		{
			successMsg=-2;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return successMsg;	
}
//Add by Anil for GAM related methods
/*Function:getFormulaAttachStatus
 *Purpose:To get formula attached status, formula already attached with order not
 * Created by:Anil Kumar
 * Date:04-Jan-2012
 */
public NewOrderDto getFormulaAttachStatus(int orderno) throws Exception
{		
		NewOrderDto formulaStatus=new NewOrderDto();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			formulaStatus=objDao.getFormulaAttachStatus(orderno);
			
		}catch(IllegalStateException ex)
		{
			formulaStatus.setAttachFormulaStatus(-2);
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return formulaStatus;	
}

//Add by Anil for GAM related methods
/*Function:isValidOrderNoForGAM
 *Purpose:To get valid OrderNo status
 * Created by:Anil Kumar
 * Date:04-Jan-2012
 */
public int isValidOrderNoForGAM(int orderno) throws Exception
{		
		int validOrderStatus=0;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			validOrderStatus=objDao.isValidOrderNoForGAM(orderno);
			
		}catch(IllegalStateException ex)
		{
			validOrderStatus=-2;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return validOrderStatus;	
}
//Add by Lawkush for GAM related methods
/*Function:dissociateOrderGam
 *Purpose:To delete GAM List with Order from  transaction table
 * Created by:Lawkush 
 * Date:03-Jan-2012
 */
public int dissociateOrderGam(String strGAMIds) throws Exception
{
		int successMsg=-1;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{
			
			
			successMsg=objDao.dissociateOrderGam(strGAMIds);
			
		}catch(IllegalStateException ex)
		{
			successMsg=1;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return successMsg;
	
}
//Add by Lawkush for GAM related methods
/*Function:dissociateOrderGam
 *Purpose:To delete GAM List with Order from  transaction table
 * Created by:Lawkush 
 * Date:03-Jan-2012
 */
public int replaceOrderGam(String strGAMIds,int gam_id1) throws Exception
{
		int successMsg=-1;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{
			
			
			successMsg=objDao.replaceOrderGam(strGAMIds,gam_id1);
			
		}catch(IllegalStateException ex)
		{
			successMsg=1;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return successMsg;
	
}

//Used to populate Opportunity Id in combo
public ArrayList<OpportunityDTO> fetchOpportunityIdList(NewOrderDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<OpportunityDTO> OpportunityList =null; 
	try 
	{
		OpportunityList = objDao.fetchOpportunityIdList(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return OpportunityList;
}


public ArrayList<SITransferDto>  fetchAccountwithsorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();	
	ArrayList<SITransferDto> listaccountDetails= new ArrayList<SITransferDto>();
	try 
	{						
		listaccountDetails = objDao.fetchAccountwithsorting(objDto);
			
	} catch (Exception e) {
		e.printStackTrace();
		listaccountDetails=null;
	}
	return listaccountDetails;
}

public ArrayList<SITransferDto> getLogicalSINumberswithSorting(PagingDto objDto,String sessionid) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<SITransferDto> Listlogicalsi = new ArrayList<SITransferDto>();
	try
	{
		//start[028]
		if(isSessionValid(sessionid)!=null){
			Listlogicalsi = objDao.getLogicalSINumberswithSorting(objDto);
		}
		else
		{
			Listlogicalsi=null;
		}
		//end[028]
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return Listlogicalsi;
}

public ArrayList<SITransferDto>  fetchBCPDetailswithsorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();	
	ArrayList<SITransferDto> listbcpDetails= new ArrayList<SITransferDto>();
	try 
	{						
		listbcpDetails = objDao.fetchBCPDetailswithsorting(objDto);
			
	} catch (Exception e) {
		e.printStackTrace();
		listbcpDetails=null;
	}
	return listbcpDetails;
}

public ArrayList<SITransferDto>  fetchDispatchInfowithsorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();	
	ArrayList<SITransferDto> listbcpDetails= new ArrayList<SITransferDto>();
	try 
	{						
		listbcpDetails = objDao.fetchDispatchInfowithsorting(objDto);
			
	} catch (Exception e) {
		e.printStackTrace();
		listbcpDetails=null;
	}
	return listbcpDetails;
}

public ArrayList<FieldAttibuteDTO> fillDropDownDependentlabel(ProductCatelogDTO objDto) throws Exception{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<FieldAttibuteDTO> DropDownDependentlabelList = null; 
	try{
		DropDownDependentlabelList = objDao.fillDropDownDependentlabel(objDto);
	}catch (Exception e){
		e.printStackTrace();
	}
	return DropDownDependentlabelList;
}

public ArrayList<FieldAttibuteDTO> fillAllDropDownDependentlabel(String[] attMasterIDLabels) throws Exception{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	try{
		return objDao.fillAllDropDownDependentlabel(attMasterIDLabels);
	}catch (Exception e){
		e.printStackTrace();
	}
	return null;
}

/*Function:gamOrdersAlreadyAttachedWithTarget
 * Purpose:To fetch Gam orders That are already attached to the target GAM
 * Created by:Lawkush
 * Date:20-Jan-2012
 * */
public ArrayList<NewOrderDto> gamOrdersAlreadyAttachedWithTarget(String strGAMIds,int gam_id1) 
{
	ArrayList<NewOrderDto> orderNumberList=new ArrayList<NewOrderDto>();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			orderNumberList=objDao.gamOrdersAlreadyAttachedWithTarget(strGAMIds,gam_id1) ;			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return orderNumberList;
	
}
/*Function:isFormulaAttachedWithOrder
 *Purpose:To get whether Formula attached with order or not
 * Created by:Lawkush
 * Date:30-Jan-2012
 */
public int isFormulaAttachedWithOrder(int orderno) throws Exception
{		
		int isFormulaAttachedWithOrder=0;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			isFormulaAttachedWithOrder=objDao.isFormulaAttachedWithOrder(orderno);
			
		}catch(IllegalStateException ex)
		{
			isFormulaAttachedWithOrder=-2;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return isFormulaAttachedWithOrder;	
}
/*Function:isFormulaAttachedWithOrder
 *Purpose:To get whether Formula attached with order or not
 * Created by:Lawkush
 * Date:30-Jan-2012
  */

public LineItemDTO getMplsLineCount(NewOrderDto objDto)
throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	LineItemDTO objRetDto = new LineItemDTO();
	try {
		objRetDto = objDao.getMplsLineCount(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}
public ArrayList<String> fnGetValidationDataForComponents(String componentInfoId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<String> startDate=null;
	try
	{
		startDate = objDao.fnGetValidationDataForComponents(new Long(componentInfoId).longValue());
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return startDate;
}
public String fnInsertDisconnectionDateForComponent(String disconnectionDate, String componentInfoId) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String result =null;
	try
	{
		result=objDao.fnInsertDisconnectionDateForComponent(disconnectionDate,componentInfoId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return result;
	
}
public ComponentDTO fndisconnectComponent(String componentinfoid, String disconnectionType,String isRevertDisconnection) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();	
	ComponentDTO disconnstatus=null;
	try 
	{
		disconnstatus = objDao.disconnectComponent(componentinfoid,disconnectionType,isRevertDisconnection);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return disconnstatus;

}
//039 start
public String deleteComponents(NewOrderDto componentdto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String result =null;
	try
	{
		result=objDao.deleteComponents(componentdto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return result;
	
}

//039 end

//040 start
 public String savecompoenentdata(NewOrderDto componentdto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String result =null;
	try
	{
		result=objDao.savecompoenentdata(componentdto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	
	return result;
	
}



/*Function:isFormulaAttachedWithOrder
 *Purpose:To get whether Formula attached with order or not
 * Created by:Lawkush
 * Date:30-Jan-2012
 */
public int isGamNFormulaAttachedWithOrder(int orderno) throws Exception
{		
		int isGamNFormulaAttachedWithOrder=0;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try{						
			isGamNFormulaAttachedWithOrder=objDao.isGamNFormulaAttachedWithOrder(orderno);
			
		}catch(IllegalStateException ex)
		{
			isGamNFormulaAttachedWithOrder=-2;
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return isGamNFormulaAttachedWithOrder;	
}
/*Function:isFormulaAttachedWithOrder
 *Purpose:To get whether Formula attached with order or not
 * Created by:Lawkush
 * Date:30-Jan-2012
  */





//Start[041]
public String closeOrderAlreadyOpened(String orderno , String userid , long roleid) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String serviceName=null;
	try
	{
		serviceName = objDao.closeOrderAlreadyOpened(orderno , userid , roleid);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return serviceName;
}
//End[041]
/*Function:getGamOrderList
 * Purpose:To get all Order List according to GAM
 * Created By:LAWKUSH
 * Date:5-JAN-2012
 * */
public ArrayList<NewOrderDto> getGamList(PagingDto objDto) throws Exception
{
	ArrayList<NewOrderDto> gamList=new ArrayList<NewOrderDto>();
		NewOrderDao objDao = new NewOrderDao();
		try{						
			gamList=objDao.getGamList(objDto);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return gamList;
	
}
//End By Anil GAM related methods

/*Function:getGAMFormulaList
 * Purpose:To get all GAM List
 * Created By:Anil Kumar
 * Date:4-Jan-2012
 * */



public int lsiValidationForMediaExchangeAssociation(String lsi) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int flag = 0;
	try
	{
		flag = objDao.lsiValidationForMediaExchangeAssociation(lsi);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return flag;
}


public ArrayList<OrderHeaderDTO>  getAccountDetails(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();
	objDto.setAccountName(Utility.trimIfNotNull(objDto.getAccountName()));
	

	ArrayList<OrderHeaderDTO> listAccountTypes= new ArrayList<OrderHeaderDTO>();
	try 
	{
		listAccountTypes = objDao.getAccountDetails(objDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listAccountTypes;
}
//lawkush Start

public ArrayList<ContactDTO> getContactTypeDetail(NewOrderDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<ContactDTO> contactTypeList = null; 
	try 
	{
		contactTypeList = objDao.getContactTypeDetail(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return contactTypeList;
}

//lawkush End
public ArrayList<NewOrderDto>  getCurrency(PagingDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> listCurrencyTypes= new ArrayList<NewOrderDto>();
	try 
	{
		listCurrencyTypes = objDao.getCurrency(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listCurrencyTypes;
}

public ArrayList<LineItemDTO>  getLineNSublineItemLbl(LineItemDTO objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<LineItemDTO> lstLineNSublineItemLbl= new ArrayList<LineItemDTO>();
	try 
	{
		lstLineNSublineItemLbl = objDao.getLineNSublineItemLbl(objDto);
		
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
	return lstLineNSublineItemLbl;
}

public int AdditionalNodeFlagCheck(NewOrderDto objDto)
{
	NewOrderDao objDao = new NewOrderDao();
	int additionalNodeFlag=0;
	try 
	{
		additionalNodeFlag = objDao.AdditionalNodeFlagCheck(objDto);
		
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
	return additionalNodeFlag;
}

//Start CLEP
public int getOrderStateforClep(int orderNo) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int ordState=-1;
	try
	{
		ordState = objDao.getOrderStateforClep(orderNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return ordState;
}
//End CLEP


public ArrayList getm6servicenoList_all(long orderNo,PagingDto objDto) throws Exception 
{
	ViewOrderDao objDao = new ViewOrderDao();
	ArrayList<ViewOrderDto> alTaskListOfOrder = new ArrayList<ViewOrderDto>();
	try
	{
		alTaskListOfOrder = objDao.getTaskListHistory_all(orderNo,objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return alTaskListOfOrder;
}
public ArrayList getm6servicenoList_selected(long orderNo,long m6No,PagingDto objDto) throws Exception 
{
	ViewOrderDao objDao = new ViewOrderDao();
	ArrayList<ViewOrderDto> alTaskListOfOrder = new ArrayList<ViewOrderDto>();
	try
	{
		alTaskListOfOrder = objDao.getTaskListHistory_selected(orderNo,m6No,objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return alTaskListOfOrder;
}

public ArrayList getServiceList(long orderNo) throws Exception 
{
	ViewOrderDao objDao = new ViewOrderDao();
	ArrayList<ViewOrderDto> al = new ArrayList<ViewOrderDto>();
	try
	{
		al = objDao.getServiceList(orderNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return al;
}

public String approvalTabVisible(long orderNo) throws Exception 
{
	ViewOrderDao objDao = new ViewOrderDao();
	String approvalTabVisible = null;
	try
	{
		approvalTabVisible = objDao.approvalTabVisible(orderNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return approvalTabVisible;
}
public long signageGlobalCount(long orderNo) throws Exception 
{
	ViewOrderDao objDao = new ViewOrderDao();
	long signageGlobalCount = 0;
	try
	{
		signageGlobalCount = objDao.signageGlobalCount(orderNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return signageGlobalCount;
}
public long lsiCheckForSignageValidation(long serviceIDChild) throws Exception 
{
	ViewOrderDao objDao = new ViewOrderDao();
	long lsiCheckForSignageValidation = 0;
	try
	{
		lsiCheckForSignageValidation = objDao.lsiCheckForSignageValidation(serviceIDChild);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return lsiCheckForSignageValidation;
}

public String reassignPM(String reassignpmList,String reassignpmnameList,String orderNoList,int roleid,String lastpmidList,String lastpmemailidList,String actmgrList) throws Exception 
{
	NewOrderDaoExt objdao = new NewOrderDaoExt();
	String status=null;
	try
	{
		// parameter added by manisha orderNoList,roleid,lastpmidList,lastpmemailidList,actmgrList defect no 15032013010
		status = objdao.reassignPM(reassignpmList,reassignpmnameList,orderNoList,roleid,lastpmidList,lastpmemailidList,actmgrList);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return status;
}

public NewOrderDto getUsageBasedLineCount(NewOrderDto objDto)
throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	NewOrderDto objRetDto = new NewOrderDto();
	try {
		objRetDto = objDao.getUsageBasedLineCount(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto;
	
}


public ArrayList getPOListForOrder(long orderNo) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<PoDetailsDTO> poList = new ArrayList<PoDetailsDTO>();
	try
	{
		poList = objDao.getPOListForOrder(orderNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return poList;
}
public String checkBillingLevelForOrder(long orderNo) throws Exception 
{
	String billingLevelCheck_ServiceNos=null;
	try
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		billingLevelCheck_ServiceNos = objDao.checkBillingLevelForOrder(orderNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return billingLevelCheck_ServiceNos;

}
//PAGING-SERVICE-LINE-14-10-2012
public ArrayList<NewOrderDto> poulateServiceListWithPaging(PagingDto objDto) throws Exception
{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<NewOrderDto> lstRetVal = new ArrayList<NewOrderDto>(); 
	try 
	{
		lstRetVal = objDao.fetechServiceListWithPaging(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return lstRetVal;
}
//PAGING-SERVICE-LINE-14-10-2012

public String checkMplsValidationForOrder(long orderNo) throws Exception 
{
	String mplsValidationCheck_ServiceNos=null;
	try
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		mplsValidationCheck_ServiceNos = objDao.checkMplsValidationForOrder(orderNo);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return mplsValidationCheck_ServiceNos;

}
//044 start
//[125]
public DisconnectOrderDto cancelPDOrders(String Orders,String sessionID,String flag,ArrayList<String> modifiedDateList) throws Exception
{
	DisconnectOrderDto objdto=null;
	NewOrderModel objModel = new NewOrderModel();
	try 
	{
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionID);		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		//[125]
		objdto = objModel.cancelPDOrders(Orders,Integer.parseInt(objUserDto.getEmployeeId()),flag,modifiedDateList);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return objdto;
}
//// 044 end 

///045 start
//[125]
public DisconnectOrderDto fnapprovePDOrders(String Orders,String sessionID,ArrayList<String> modifiedDateList) throws Exception
{
	DisconnectOrderDto objdto=null;
	NewOrderModel objModel = new NewOrderModel();
	try 
	{
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionID);		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		//[125]
		objdto = objModel.fnapprovePDOrders(Orders,objUserDto.getUserRoleId(),objUserDto.getEmployeeId(),modifiedDateList);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return objdto;
}
//// 045 end

/*Function:fetchConfigValue
 * Purpose:To fetch config value based on serviceDetailId
 * Date:24-July-2012
 * By:Anil Kumar
 * */
public M6OrderStatusDto fetchConfigValue(M6OrderStatusDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	M6OrderStatusDto configListDto = new M6OrderStatusDto();
	try
	{
		configListDto = objDao.fetchConfigValue(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return configListDto;
}
/*Function:fillComponentStartDateLogic
 * Purpose:To fetch Component Start Date Logic
 * Date:20-Aug-2012
 * By:Anil Kumar
 * */
public ArrayList<M6OrderStatusDto> fillComponentStartDateLogic() throws Exception 
{	
	ArrayList<M6OrderStatusDto> startDateLogic = new ArrayList<M6OrderStatusDto>();
	NewOrderDaoExt objDao=new NewOrderDaoExt();
	try
	{
		startDateLogic=objDao.fillComponentStartDateLogic();
	} 
	catch (Exception e) {
		e.printStackTrace();	
	}
	return startDateLogic;
}
/*Function:fillComponentEndDateLogic
 * Purpose:To fetch Component Start Date Logic
 * Date:20-Aug-2012
 * By:Anil Kumar
 * */
public ArrayList<M6OrderStatusDto> fillComponentEndDateLogic() throws Exception 
{	
	ArrayList<M6OrderStatusDto> endDateLogic = new ArrayList<M6OrderStatusDto>();
	NewOrderDaoExt objDao=new NewOrderDaoExt();
	try
	{
		endDateLogic=objDao.fillComponentEndDateLogic();
	} 
	catch (Exception e) {
		e.printStackTrace();	
	}
	return endDateLogic;
}
/*Function:fetchBillingMode
 * Purpose:To fetch config value based on serviceDetailId
 * Date:24-July-2012
 * By:Anil Kumar
 * */
public ArrayList<M6OrderStatusDto> fetchBillingMode(int configValue) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<M6OrderStatusDto> billingMode = new ArrayList<M6OrderStatusDto>();
	try
	{
		billingMode = objDao.fetchBillingMode(configValue);
	} 
	catch (Exception e) {
		e.printStackTrace();	
	}
	return billingMode;
}
public String fetchAddSubNodeAllowStatus(NewOrderDto objDto)
throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String isAllow="";
	try {
		isAllow = objDao.fetchAddSubNodeAllowStatus(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return isAllow;
	
}

public NewOrderDto getArborLSIListWithSorting(PagingDto objDto) throws Exception
{
	/*NewOrderDaoExt objDao = new NewOrderDaoExt();
	try 
	{
		objDao.getArborLSIListWithSorting(objDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objDao.getArborLSIListWithSorting(objDto);*/
	
	NewOrderModel objModel = new NewOrderModel();
	/*try 
	{
		//objModel.getArborLSIListWithSorting(objDto);
	} catch (Exception e) {
		e.printStackTrace();
	}*/
	return objModel.getArborLSIListWithSorting(objDto);
	
	
	
	
	
}
public ArrayList<AcctDTO>  getUpdatedAccountList(Long orderNo) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<AcctDTO> objList = new ArrayList<AcctDTO>();
	try 
	{
		objList=objDao.getUpdatedAccountList(orderNo);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objList;
}
/*public String validateVCSServicesBeforeCancel(NewOrderDto objDto)throws Exception
{	
	NewOrderDto objRetDto =null;
	NewOrderModel model =new NewOrderModel();
	try {		
		objRetDto=model.validateServicesWithRedirectedLSIBeforeDelete(objDto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto.getMsgOut();
	
}*/
public String disconnectAllComponent(long serviceId,long spid) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String result="";
	try
	{
		result= objDao.disconnectAllComponent(serviceId,spid);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return result ;

}
public ArrayList<NewOrderDto>  getVCS_BridgeLSIListWithSorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<NewOrderDto> listArborLSIList=null;
	try 
	{
		listArborLSIList = objDao.getVCS_BridgeLSIListWithSorting(objDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listArborLSIList;
}
public ArrayList<NewOrderDto> fetchLSINoForMBIC(int attMasterId,int orderNo,String sessionid) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	ArrayList<NewOrderDto> Listlogicalsi =null;
	try
	{
		if(isSessionValid(sessionid)!=null){
			Listlogicalsi = objDao.fetchLSINoForMBIC(attMasterId,orderNo);
		}
		else
		{
			Listlogicalsi=null;
		}
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return Listlogicalsi;
}
public String validateTaxRate(long orderno) throws Exception 
{
	NewOrderModel objModel = new NewOrderModel();
	String result="";
	try
	{
		result= objModel.validateTaxRate(orderno);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return result ;

}

public int getCreatedinServiceid(long serviceproductid) throws Exception 
{
	NewOrderDao objDao = new NewOrderDao();
	int serviceid=0;
	try
	{
		serviceid= objDao.getCreatedinServiceid(serviceproductid);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return serviceid ;

}
public String dettachMBICLSI(NewOrderDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String isDettach="";
	try
	{
		isDettach = objDao.dettachMBICLSI(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return isDettach;
}
public String validateMBIC_To_CC(int orderno)throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String isValidMBIC_CC_MAP="";
	try
	{
		isValidMBIC_CC_MAP = objDao.validateMBIC_To_CC(orderno);
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return isValidMBIC_CC_MAP;
}
public String selectCC_MBIC_Maping(NewOrderDto objDto) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String ccMbicServiceID="";
	try
	{
		ccMbicServiceID = objDao.selectCC_MBIC_Maping(objDto);
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return ccMbicServiceID;
}
public String validateVCS_BridgeServiceBundled(int orderno)throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String isValidMBIC_CC_MAP="";
	try
	{
		isValidMBIC_CC_MAP = objDao.validateVCS_BridgeServiceBundled(orderno);
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return isValidMBIC_CC_MAP;
}
public String validateVCS_L3MPLSForDisconnectOrder(int orderno)throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String isValidMBIC_CC_MAP="";
	try
	{
		isValidMBIC_CC_MAP = objDao.validateVCS_L3MPLSForDisconnectOrder(orderno);
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return isValidMBIC_CC_MAP;
}
public String validateMBIC_ON_CC_Discoonnect(int orderno)throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String isValidMBIC_CC_MAP="";
	try
	{
		isValidMBIC_CC_MAP = objDao.validateMBIC_ON_CC_Discoonnect(orderno);
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return isValidMBIC_CC_MAP;
}
public String getL3MplsMappingCount(int serviceId)throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String l3MPLSListBundledWithVCS="";
	try
	{
		l3MPLSListBundledWithVCS = objDao.getL3MplsMappingCount(serviceId);
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return l3MPLSListBundledWithVCS;
}
public String validateVCSServicesBeforeCancel(NewOrderDto objDto)throws Exception
{	
	NewOrderDto objRetDto =null;
	NewOrderModel model =new NewOrderModel();
	try {		
		objRetDto=model.validateVCSServicesBeforeDelete(objDto);	
		if(objRetDto.getMsgOut()==null)
		{
			objRetDto=model.validateServicesWithRedirectedLSIBeforeDelete(objDto);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return objRetDto.getMsgOut();
}
public String isComponentAdded(int orderNo)throws Exception
{	
	NewOrderDaoExt objDao =new NewOrderDaoExt();
	String retString="";
	try {		
		retString=objDao.isComponentAdded(orderNo);		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return retString;
}
public String isMultipleDummyServices(int orderNo)throws Exception
{	
	NewOrderModel newModel=new NewOrderModel();
	String retString="";
	try {		
		retString=newModel.isMultipleDummyServices(orderNo);		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return retString;
}
//New Bug of VCS for Dummy Line Item

public String isVCS_LSIBundled(int serviceId,String calledFrom)throws Exception
{	
	
	NewOrderDaoExt objDao =new NewOrderDaoExt();
	String bunled="";
	try {		
		bunled=objDao.isVCS_LSIBundled(serviceId,calledFrom);		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return bunled;
	
}
public String justUnmappedVCSDeletionCheck(int l3mplsServiceId,int orderNo)
{
	NewOrderDaoExt objDao =new NewOrderDaoExt();
	String result="";
	try {		
		result=objDao.justUnmappedVCSDeletionCheck(l3mplsServiceId,orderNo);		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}
public String sendXmlToMpp(CLEPXmlDto objDto)
{
	String msgDetails="";
	
	try{		
		String userId="ib2bclep";
		String pwd="ib2bclep";
		if(objDto.getUserId().equals(userId)&& objDto.getPassword().equals(pwd)){
			System.out.println("------Manual Message Sending Start---->");
			msgDetails=SendAndRecieveMessage.messageProducer_Test(objDto);
			System.out.println("------Manual Message Sending End---->");
		}else{
			msgDetails="Authentication Failed!!";
		}
	}catch(Exception exp){
		msgDetails=exp.getMessage();
		exp.printStackTrace();
	}
	
	return msgDetails;
}

//--[044]-start
/*public String getChangeReason(ServiceLineDTO objDto) throws Exception 
{
		String changeReason="";
		NewOrderDao objDao = new NewOrderDao();
	try
	{
		changeReason = objDao.getChangeReason(objDto.getServiceId());
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return changeReason;
}*/
public ServiceDto getChangeReasonNeffectiveDate(ServiceLineDTO objDto) throws Exception 
	{
		ServiceDto datalist=null;
			NewOrderDao objDao = new NewOrderDao();
				try
					{
						datalist = objDao.getChangeReasonNeffectiveDate(objDto.getServiceId());
					} 
				catch (Exception e) {
			Utility.LOG(e);
		}
	return datalist;
}

//--[044]--end
	//[046] Start	
	public ArrayList<LineItemDTO> getMappingLSINumber(LineItemDTO objDto) throws Exception
	{
		ReportsDao objDao = new ReportsDao();
		ArrayList<LineItemDTO> saDto=new ArrayList<LineItemDTO>();
		try 
		{
			saDto = objDao.getLSIMappingDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	//[046] End
	//[047] Start
	public int checkDuplicateAttributes(String attributeVal, String attributeID, String lineNo) throws Exception //Method used to check duplicay of Dail Comid, Toll Free No, IRN No and TGNO No
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		int flag = 0;
		try
		{
			flag = objDao.checkDuplicateAttributes(attributeVal,attributeID,lineNo);
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return flag;
	}
	//[047] End

//Start[048]
		public String validateNOFXSIComponent(String OrderNo) throws Exception
		{
			String strResult="";
			long orderNumber=0l;
			ViewOrderDao objDao=new ViewOrderDao();
				try{
					if(!"".equalsIgnoreCase(OrderNo) && OrderNo !=null){
						orderNumber=Long.valueOf(OrderNo);
					}
					strResult=objDao.validateNOFXSIComponent(orderNumber);					
				}catch(Exception e){
					e.printStackTrace();
				}
			return strResult;
		}
	//End[048]
	
	//Start Saurabh for keeping 2D product Values of Ib2b in sync with CRM	
	public String insertUpdate2DProductValues() throws Exception //Method used to check duplicay of Dail Comid, Toll Free No, IRN No and TGNO No
	{
		FetchAccountFromCRM fetchAccountFromCRM = new FetchAccountFromCRM();
		String returnStatus = null;
		int status=0;
		try
		{
			status = fetchAccountFromCRM.InsertUpdateProductDDValuesInIOMS(1);//For Insertion
			if(status == 1)//if failed
			{
				returnStatus="Synchronization for 2D Product failed";
			}
			else 
			{
				status = fetchAccountFromCRM.InsertUpdateProductDDValuesInIOMS(2);//For Updation
				returnStatus="Synchronization for 2D Product Successful";
				if(status == 1)//if failed
				{
					returnStatus="Synchronization for 2D Product failed";
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return returnStatus;
	}
	//End

//048 start	
public ArrayList<NewOrderDto>  getAccountDetailsWithSortingforReassigmPm(PagingDto objDto) throws Exception
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
			ArrayList errors=new ArrayList();
		objDto.setAccountName(Utility.trimIfNotNull(objDto.getAccountName()));
		
		String accountName=objDto.getAccountName();
		Utility.validateValue(new ValidationBean()
					.loadValidationBean_Maxlength("Account Name", accountName, 150),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
		
		
		if(errors!=null && errors.size()>0)//During Server Side Validation
		{
			NewOrderDto dto=new NewOrderDto();
			dto.setErrors_Validation(errors);
			ArrayList<NewOrderDto> errorList=new ArrayList<NewOrderDto>();
			errorList.add(dto);
			//return errorList;
		}
		ArrayList<NewOrderDto> listAccountTypes= new ArrayList<NewOrderDto>();
		try 
		{
			listAccountTypes = objDao.getAccountDetailsWithSortingforReassigmPm(objDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listAccountTypes;
	}

//048 end
//[15032013017] START
public CommonDTO countSelectedLineItems(ProductCatelogDTO objDto, String sessionID) throws Exception
{
	String methodName="countSelectedLineItems", className=this.getClass().getName(), msg="";
	boolean logToFile=true, logToConsole=true;
	logger.info(methodName+" method of "+className+" class have been called");
	NewOrderDao objDao = new NewOrderDao();
	CommonDTO objRetDto = null;
	
	try {
		//HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		//HttpSession session = hmap.get(sessionID);		
		//UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);								
		objRetDto = objDao.countSelectedLineItems(objDto);
		
	} catch (Exception e) {
		throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
	}
	return objRetDto;
	
}
// [15032013017] END

//[050] Start	
	public ArrayList<LineItemDTO> getHardwareLineDetails(LineItemDTO objDto) throws Exception//Method to Display Hardware Line item for Cancelation
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<LineItemDTO> saDto=new ArrayList<LineItemDTO>();
		try 
		{
			saDto = objDao.getHardwareLineDetails(objDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saDto;
	}
	
	public LineItemDTO fnProcessLineItems(String LineItems,String sessionID) throws Exception//Method to Process Request of Line Item Cancelation
	{
		LineItemDTO objdto=null;
		NewOrderModel objModel = new NewOrderModel();
		try 
		{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionID);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			objdto = objModel.fnProcessLineItems(LineItems,objUserDto.getUserRoleId(),objUserDto.getEmployeeId());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return objdto;
	}
	
	public ArrayList<ReportsDto> getRequestHistory(ReportsDto objDto) throws Exception//Method to Display Hardware Line item for Cancelation
	{
		ReportsDao objDao = new ReportsDao();
		ArrayList<ReportsDto> saDto=new ArrayList<ReportsDto>();
		try 
		{
			saDto = objDao.getRequestHistory(objDto);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return saDto;
	}
	//[050] End
	
	//[051] Start
	public ArrayList<ProductCatelogDTO> getCancelledHardwareLineDetails(long orderNo) throws Exception//Method to Display Hardware Line item for Cancelation
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<ProductCatelogDTO> saDto=null;
		try 
		{
			saDto = objDao.getCancelledHardwareLineDetails(orderNo);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return saDto;
	}
	//[051] End
	
	//revert disconnect charge

	public int revertDisconnectCharge(String chargeID, String changeOrderNo,String disconnectionType) throws Exception
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		int status=-1;
		try 
		{
			status = objDao.revertDisconnectCharge(chargeID,changeOrderNo,disconnectionType);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}	
	public ArrayList<NewOrderDto>  getContactDetails(PagingDto objDto) throws Exception{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<NewOrderDto> listContactDetails= new ArrayList<NewOrderDto>();
		try 
		{
			listContactDetails = objDao.getContactDetails(objDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listContactDetails;
	}

//	Filling Rejection Reason  Combo of approval page
	public ArrayList<ViewOrderDto> populateRejectionReason() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ViewOrderDto> listRejectionReason= new ArrayList<ViewOrderDto>();
		try 
		{
			
			listRejectionReason = objDao.populateRejectionReason();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return listRejectionReason;
	}
//	Filling Rejection Reason  Combo of approval page
	
	//Escalation starts from here
	public ArrayList<EscalationDto> fetchBusinessSegment(EscalationDto objDto) throws Exception
	{
		EscalationDao objDao = new EscalationDao();
		ArrayList<EscalationDto> saDto=null;
		try 
		{
			saDto = objDao.fetchBusinessSegment(objDto);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return saDto;
	}

	
	
	public ArrayList<EscalationDto> fetchLevelDetails(EscalationDto objDto) throws Exception
	{
		EscalationDao objDao = new EscalationDao();
		ArrayList<EscalationDto> list=null;
		try 
		{
			list = objDao.fetchLevelDetails(objDto);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	

	public String setchangedMailId3(EscalationDto objDto) throws Exception
	{
		EscalationDao objDao = new EscalationDao();
		String value="";
		try 
		{
			value = objDao.setchangedMailId3(objDto);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return value;
	}
	// End
	//Start[TRNG22032013018]
	//Used to Populate Service List in a Array for Service level Navigation
	public ArrayList<ServiceLineDTO> poulateServiceListForArrayLoading(long orderNo) throws Exception
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<ServiceLineDTO> lstRetVal = null;
		try 
		{
			lstRetVal = objDao.poulateServiceListForArrayLoading(orderNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstRetVal;
	}
	
	public ArrayList<NewOrderDto> getServiceDetailsForSwitchingService(long serviceId)	throws Exception
	{
		ArrayList<NewOrderDto> listdetails = null;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try {
			listdetails = objDao.getServiceDetailsForSwitchingService(serviceId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listdetails;
	}
	//End[TRNG22032013018]
	
	//Start [TRNG22032013020]
	@SuppressWarnings("unchecked")
	public CommonDTO copyServiceProductForNewOrder(ProductCatelogDTO objDto, String sessionID) throws Exception
	{
		String methodName="copyServiceProduct", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		logger.info(methodName+" method of "+className+" class have been called");
		
		
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		CommonDTO objRetDto = null;
		
		try {
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionID);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);								
			objRetDto = objDao.copyServiceProductForNewOrder(objDto, objUserDto.getEmployeeId());
			
		} catch (Exception e) {
			throw Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);
		}
		return objRetDto;
		
	}
	//End [TRNG22032013020]
	/**
	 *@author vijay 
	 * @param chargeId
	 * @param serviceProductId
	 * @param serviceId
	 * @param orderNo
	 * @param frequency
	 * @return A List that contain all active charge which can be disconnected
	 * @throws Exception
	 */
	public ArrayList<ChargeComponentDTO> getChargesForDisconnectAndCopy(long chargeId, long serviceProductId, long serviceId, int frequency)	throws Exception
	{
		ArrayList<ChargeComponentDTO> listdetails = null;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try {
			listdetails = objDao.getChargesForDisconnectAndCopy(chargeId,serviceProductId,serviceId,frequency);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listdetails;
	}
	
	/**@author Vijay
	 * @param chargeId
	 * @return An object of ChargeComponentDto that contains charge related detals. This method is using 
	 * for "copy charge from Disconnection charge" funcationlity 
	 * @throws Exception
	 */
	public ChargesDetailDto populateChargeForCopyDisconnection(long chargeId) throws Exception
	{
		ChargesDetailDto objChargesDetailDto = null;
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try {
			objChargesDetailDto = objDao.populateChargeForCopyDisconnection(chargeId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return objChargesDetailDto;
		
	}
	
	public Object[] getDisconnectLinkedCharges(long serviceProductId, long serviceId) throws Exception
	{
		NewOrderDaoExt objDaoExt = new NewOrderDaoExt();
		Object disconnectChargeArray[] = null;
		ArrayList<ComponentsDto> saDto=new ArrayList<ComponentsDto>();
		try 
		{
			disconnectChargeArray = objDaoExt.getDisconnectLinkedCharges(serviceProductId, serviceId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return disconnectChargeArray;
	}
	//[053] Start
	public String parallelUpgradeValidation(int orderNo, String stage) throws Exception 
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		String  errorMsg=null;
		try
		{
			errorMsg= objDao.parallelUpgradeValidation(orderNo,stage);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return errorMsg;
	}
	//[053] End
//lawkush start
	
	
	public int refreshOpportunity(Long accountId) throws Exception //Method used to check duplicay of Dail Comid, Toll Free No, IRN No and TGNO No
	{
		FetchAccountFromCRM fetchAccountFromCRM = new FetchAccountFromCRM();		
		int status=0;
		try
		{
			status = fetchAccountFromCRM.refreshOpportunity(accountId);//For Insertion
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return status;
	}
	
	
	
	//lawkush end
	//To Check whether BCP Address is present agsinst the account by Vishwa on 10-Sep-13 START
	public int countBCPWithDispatch(int accountID) throws Exception
	{
		OrderHeaderDTO objDto=new OrderHeaderDTO();
		NewOrderDao objDao = new NewOrderDao();
		objDto.setAccountID(accountID);
		int countAddress= 0;
		try 
		{
			countAddress = objDao.countBCPWithDispatch(objDto.getAccountID());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return countAddress;
	}
	//To Check whether BCP Address is present agsinst the account by Vishwa on 10-Sep-13 END
	
	//Vijay start
	public String totalServiceCountCheck(long orderNo, int copyCount) throws Exception //Method used to check duplicay of Dail Comid, Toll Free No, IRN No and TGNO No
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		String message = "";
		try
		{
			message = objDao.totalServiceCountCheck(orderNo,copyCount);
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return message;
	} 
	//Vijay end
	
//shourya
	//SHOURYA
	public ArrayList<ServiceLineDTO> getDowntimeClause(ServiceLineDTO objDto) throws Exception
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList<ServiceLineDTO> list =null;
		try 
		{
			list = objDao.getDowntimeClause(objDto);
			System.out.println("ajax helper"+list);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	//SHOURYA
	
//	 OPS01042013001 added by manisha start 
	public String  getSpecialCharContact() throws Exception{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		String ichar="";
		try 
		{
			ichar = objDao.getSpecialCharContact();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ichar;
	}
	
//	 OPS01042013001 added by manisha end 	
	
	
	//[114] start
	
	public String  getSpecialCharValue() throws Exception{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		String ichar="";
		try 
		{
			ichar = objDao.getSpecialCharValue();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ichar;
	}
	
	
	
	
	//[114] end
	
	
	public String fnupdatedemoenddate(String logicalsilist,String noofdayslist,String demoendlist,String orderlist,String demodayslist) throws Exception 
	{
		NewOrderDaoExt objdao = new NewOrderDaoExt();
		String status=null;
		try
		{
			// parameter added by manisha orderNoList,roleid,lastpmidList,lastpmemailidList,actmgrList defect no 15032013010
			status = objdao.fnupdatedemoenddate(logicalsilist,noofdayslist,demoendlist,orderlist,demodayslist);
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return status;
	}
	// addeed by manisha cust bil exp bfr no 7 end
	
	
	public int validateDemoDays(int orderno) throws Exception 
	{
		NewOrderDaoExt objdao = new NewOrderDaoExt();
		int result=0;
		try
		{
			result= objdao.validateDemoDays(orderno);
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return result ;

	}
	// [127] start 
	public RRAutoTriggerClass  processForRRAutoTriggerValidation(long orderno,int subChangeTypeId) throws Exception 
	{
		NewOrderModel objmodel = new NewOrderModel();
		RRAutoTriggerClass objRRAutoTrgigger = new RRAutoTriggerClass();
		try
		{
			objRRAutoTrgigger= objmodel.processForRRAutoTriggerValidation(orderno,subChangeTypeId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return objRRAutoTrgigger ;

	}
	// [127] end
	//[055] Start
	public AdvancePaymentDTO setAdvancePaymentDetails(AdvancePaymentDTO advancePaymentDTO) {
		
		AdvancePaymentDTO paymentDTO = new AdvancePaymentDTO();
		
		try { 
			NewOrderDao newOrderDao = new NewOrderDao(); 
			paymentDTO = newOrderDao.saveAdvancePaymentDetails(advancePaymentDTO);
			
		} catch (Exception exception) {
			
			exception.printStackTrace();
		}
		return paymentDTO;
		
	}
	
	public AdvancePaymentDTO getAdvancePaymentDetails(AdvancePaymentDTO dto) {
		
		AdvancePaymentDTO advancePaymentDTO = new AdvancePaymentDTO();
	
		try {
				NewOrderDao newOrderDao = new NewOrderDao(); 
				advancePaymentDTO = newOrderDao.getAdvancePaymentInfo(dto);
			
		} catch (Exception exception) {
			
			exception.printStackTrace();
		}
		return advancePaymentDTO;
	}
	//[055] End
	//[056] Start
	public String validateAdvancePaymentDetails(int orderNo) throws Exception 
	{
		NewOrderDao objDao = new NewOrderDao();
		String  errorMsg=null;
		try
		{
			errorMsg= objDao.validateAdvancePaymentDetails(orderNo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return errorMsg;
	}
	//[056] End
	public List<RoleSectionDetailDTO> getRoleWiseSectionDetails(int role){
		return new NewOrderDaoExt().getRoleWiseSectionDetails(role) ;
	}
	
	public Boolean checkServiceSummaryAttributesCommercial(int serviceDetailId){
		List<FieldAttibuteDTO> attributeList = new NewOrderDaoExt().getServiceAttList(serviceDetailId);
		if(null != attributeList)
			for(FieldAttibuteDTO attribute : attributeList){
				if(attribute.getIsCommercial() == 1)
					return true;
			}
		return false;
	}
	
	public List<DelayReasonDTO> getDelayReason(){
		return new NewOrderDaoExt().getDelayReason() ;
	}
	//[054]	
	public void  populateReportUsageDetails(ReportsDto objrpt){
		
		System.out.println("in populate report usage details");
		ReportsDao rptdaoobj=new ReportsDao();
		try {
		rptdaoobj.viewReportUsageDetails(objrpt);
		} catch (Exception e) {
			Utility.LOG(true, true, e, "error in populateReportUsageDetails method");
		}
	}

	public String checkCopiedNode(long serviceProductId)
	{
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		String compareResult=null;
		try
		{
			 compareResult= objDao.checkCopiedNode(serviceProductId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		
		}
		return compareResult;
	}
	
	// Added By Deepak for Third Party
	public ArrayList<SCMDto> populateDeliveryLocation(int locId) throws Exception {
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<SCMDto>  listDeliveryLocation = new ArrayList<SCMDto>();
		
		try 
		{
			
			listDeliveryLocation = objDao.getDeliverylocation(locId);
			Utility.SysOut("In populateDeliveryLocation Method of Ajax Helper");
			 
			 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Utility.LOG(true, true, "Exception "+e);
			
		}
		return listDeliveryLocation;
	}
	// Added By Deepak for Third Party
	public ArrayList<SCMDto> populateSubInventory(int subId) throws Exception {
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<SCMDto>  listSubInventory = new ArrayList<SCMDto>();
		
		try 
		{
			listSubInventory=objDao.getSubInventory(subId);
			Utility.SysOut("In populateSubInventory Method of Ajax Helper");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Utility.LOG(true, true, "Exception "+e);
		}
		return listSubInventory;
	   }
	// Added By Deepak for Third Party
	public ArrayList<SCMDto> populateBudgetHeadAop1(int budgetId) throws Exception {
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<SCMDto>  listSubInventory = new ArrayList<SCMDto>();
		
		try 
		{
			listSubInventory = objDao.getBudgetHeadAop1(budgetId);
			Utility.SysOut("In populateBudgetHeadAop1 Method of Ajax Helper");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Utility.LOG(true, true, "Exception "+e);
		}
		return listSubInventory;
	}
//	 Added By Deepak for Third Party
	public ArrayList<SCMDto> populateBudgetHeadAop2(int aopId) throws Exception {
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<SCMDto>  listSubInventory = new ArrayList<SCMDto>();
		
		try 
		{
			listSubInventory = objDao.getBudgetHeadAOP2(aopId);
			Utility.SysOut("In populateBudgetHeadAop2 Method of Ajax Helper");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Utility.LOG(true, true, "Exception "+e);
		}
		return listSubInventory;
	}
	// Added By Deepak for Third Party 
	public ArrayList<SCMDto> populateItemCodeSCM(int itemId) throws Exception {
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<SCMDto>  listItemCode = new ArrayList<SCMDto>();
		
		try 
		{
			listItemCode = objDao.getItemCodeForScm(itemId);
			Utility.SysOut("In populateItemCodeSCM Method of Ajax Helper");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Utility.LOG(true, true, "Exception "+e);
		}
		return listItemCode;
	}
	
	/*
	 * This method is used to Populate Service Summery Value on ViewProductCataLogForSCM.jsp
	 */
	public ArrayList<FieldAttibuteDTO> populateServiceAttMasterValueForSCM(ProductCatelogDTO objDto,String sessionid) throws Exception
	{

		NewOrderDao objDao = new NewOrderDao();
		ArrayList<FieldAttibuteDTO> saDto=new ArrayList<FieldAttibuteDTO>();
		try 
		{
			
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			saDto = objDao.fetchServiceAttributeMasterValueFOrSCM(objDto,Long.valueOf(objUserDto.getUserRoleId()));
			Utility.SysOut("In populateServiceAttMasterValueForSCM Method ");
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true, "Some Exception in populateServiceAttMasterValueForSCM"+e);
		}
		return saDto;
	}
	//Added by Deepak kumar for third aprty Scm 
	public ArrayList<SCMDto> populateScmLineDetails(ServiceLineDTO objDto) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<SCMDto> saDto=new ArrayList<SCMDto>();
		try 
		{
			saDto = objDao.fetchScmLineDetails(objDto);
			Utility.SysOut("In populateScmLineDetails Method");
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true, "Exception "+e);
		}
		return saDto;
	}
	public NewOrderDto updateProductCatelogForScm(NewOrderDto objDto,String sessionID) throws Exception
	
	{
		NewOrderDao objDao = new NewOrderDao();
		NewOrderDto RetObjDto=new NewOrderDto(); 
		
	
		try 
		{	
				objDto.setMode(NewOrderDto.MODE_UPDATE);
				HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
				HttpSession session = hmap.get(sessionID);		
				UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		
		
				RetObjDto = objDao.updateProductCatelogForScm(objDto,Long.valueOf(objUserDto.getEmployeeId()));
		
			Utility.SysOut("In updateProductCatelogForScm method of Ajax helper");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return RetObjDto;
	}
	/*
	 * This method is used to populate SCM status on view mode
	 * Added by Deepak Kumar
	 */
	public ArrayList<SCMDto> populateScmPrStatus(ServiceLineDTO lineDTO) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<SCMDto> saDto=new ArrayList<SCMDto>();
		try 
		{
			saDto = objDao.fetchScmPrStatusDetails(lineDTO);
			Utility.SysOut("In populateScmPrStatus Method");
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true, "Exception "+e);
		}
		return saDto;
	}
	
	/*
	 * This method is used to fetch key value list   
	 * Added by Deepak kumar 
	 */
	public SCMDto getServiceDetailId(ServiceLineDTO lineDTO) throws Exception
	{
		
		NewOrderDao objDao=new NewOrderDao();
		NewOrderModel model=new NewOrderModel();
		List<String> keyValueList=null;
		String keyValue=null;
		SCMDto dto=new SCMDto();
		String productName=null;
		Integer thirdPartyFlag=0;
		try 
		{
			
			keyValue= objDao.getKeyValue();
			keyValueList=Arrays.asList(keyValue.split(","));
			for(String serviceDetId:keyValueList)
			{
				if(Integer.parseInt(serviceDetId)==(lineDTO.getServiceDetailID()))
				{
					dto.setSerDetIdAskeyValue(serviceDetId);
					productName=objDao.getThirdPartyProductName(Integer.parseInt(serviceDetId));
					thirdPartyFlag=Integer.parseInt(model.getThirdPartyState());
					dto.setProductName(productName);
					dto.setThirdPartyFlag(thirdPartyFlag);
				}
			}
			
			Utility.SysOut("In getServiceDetailId Method of Ajax Hepler");
		} catch (Exception e) {
			
			Utility.LOG(true, true, "Some Error Occured in getServiceDetailId Method of Ajax Hepler "+e);
		}
		return dto;
	}
	public int callCreateScmXmlRepush(String orderNo,String serviceId,String serviceProductID) throws Exception{
		int res=0;
		NewOrderDao objDao = new NewOrderDao();
		
		try{
			res=objDao.callCreateScmXmlRepush(orderNo,serviceId,serviceProductID);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	/*
	 * This method is used to getLOV label
	 * Added by Deepak Kumar
	 */
	
		public String getAttDescKey() throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		String attDescKey=null;
		
		try 
		{
			attDescKey = objDao.getAttDescKey();
			Utility.LOG(true, true, "In getAttDescKey Method of Ajax Helper");
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true, "Exception "+e);
		}
		return attDescKey;
	}
	/*
	 * This method is used to call getPRDetailsForPRResue of Dao
	 * Added by Deepak Kumar
	 */
	public ArrayList<SCMDto> getPRDetailsForPRResue(SCMDto objDto) throws Exception
	{
        
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<SCMDto> saDto=new ArrayList<SCMDto>();
		try 
		{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			saDto = objDao.getPRDetailsForPRResue(objDto.getPrId());
			Utility.LOG(true, true, "In getPRDetailsForPRResue Method of Ajax Helper");
			
		} catch (Exception e) {
			Utility.LOG(true, true, "Some Exception in getPRDetailsForPRResue"+e);
		}
		return saDto;
	}
	/*
	 *This method is used to getLabel Value for LOV in serviceSummary 
	 *Added by Deepak Kumar 
	 */
	
	public  SCMDto getDescrption() throws Exception
	{
        
		NewOrderDao objDao = new NewOrderDao();
		SCMDto saDto=new SCMDto();
		String descLabel[]=new String[4];
		try 
		{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			descLabel = objDao.getDetescrption();
			
			saDto.setDescLabel(descLabel[0]);
			saDto.setAttributeValue(descLabel[1]);
			saDto.setPreparer(descLabel[2]);
			saDto.setDeliverRequester(descLabel[3]);
			Utility.LOG(true, true, "In getDescrption Method of Ajax Helper");
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true, "Some Exception in getDescrption"+e);
		}
		return saDto;
	}
	
	/*
	 *This method is used to search prNumber for PrReuse 
	 *Added by Deepak Kumar 
	 */
	
	public ArrayList<ServiceLineDTO> populatePrSearch(PagingDto objDto)
	throws Exception
	{
	NewOrderDao objDao = new NewOrderDao();
	ArrayList<ServiceLineDTO> lstRetVal = new ArrayList<ServiceLineDTO>();
	
	
		try {
			
			
				lstRetVal = objDao.getPrReueSearch(objDto);
				
				Utility.LOG(true,true,"In populatePrSearch Method of Ajax helper:");
			
		}catch (Exception e) {
			Utility.LOG(true,true,"Exception in populatePrSearch of Ajax helper:"+e);
		}
		return lstRetVal;
		
	}
	
	/*
	 * This method is used to call disableViewScmAttributes of dao
	 * Added by Deepak Kumar
	 */
	public SCMDto disableViewScm(ServiceLineDTO lineDTO) throws Exception
	{
		NewOrderDao objDao = new NewOrderDao();
		SCMDto saDto=new SCMDto();
		try 
		{
			saDto = objDao.disableViewScmAttributes(lineDTO);
			Utility.LOG(true,true,"In disableViewScm Method of Ajax helper:");
			
		} catch (Exception e) {
			Utility.LOG(true,true,"Exception in disableViewScm of Ajax helper:"+e);
		}
		return saDto;
	}
	
	public ArrayList<ChargeDetailsSCM> findScmId(NewOrderDto dto){
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ChargeDetailsSCM> detailsSCMs=null;
		try
		{
			detailsSCMs =objDao.findScmId(dto);
			Utility.LOG(true,true,"In findScmId Method of Ajax helper");
		}
		catch(Exception ex)
		{
			Utility.LOG(true,true,"Exception in findScmId of Ajax helper:"+ex);
		}
		return detailsSCMs;
	}
	
	public void deleteScmLine(NewOrderDto dto){
		NewOrderDao objDao = new NewOrderDao();
		
		try
		{
			objDao.deleteScmLine(dto);
			dto.setDeletedChargesList(dto.getDeletedChargesList());
			Utility.LOG(true,true,"In deleteScmLine Method of Ajax helper");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			Utility.LOG(true,true,"Exception in deleteScmLine of Ajax helper:"+ex);
		}
		
	}
	/*
	 * This method is used to validate prNumber 
	 * Added by Deepak
	 */
	public  SCMDto validatePrId(SCMDto scmDto) throws Exception
	{
        
		NewOrderDao objDao = new NewOrderDao();
		
		SCMDto validList=null;
		
		try 
		{
			 validList=objDao.getPrValidation(scmDto);
			 if(validList!=null)
			 {
			validList.setPrValidationMsg("This PR No. is Already Used For "+validList.getNfaNumber()+" NFA No. So Please Select Another PR No.");
			 }
			 Utility.LOG(true,true,"In validatePrId Method of Ajax Helper");
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true,true,"Some Error in validatePrId of Ajax Helper:"+e);
		}
		return validList;
	}
		public int callGetIsPublished(String serviceId) throws Exception{
		int res=0;
		NewOrderDao objDao = new NewOrderDao();
		try{
			if(null!=serviceId)
				res=objDao.getIsPublished(Integer.parseInt(serviceId));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
		public ArrayList<FieldAttibuteDTO> populateServiceAttributeListSCM(ServiceLineDTO objDto,String sessionid) throws Exception
		{
			NewOrderDao objDao = new NewOrderDao();
			ArrayList<FieldAttibuteDTO> saDto=new ArrayList<FieldAttibuteDTO>();
			try 
			{
				
				
				HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
				HttpSession session = hmap.get(sessionid);		
				UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				saDto = objDao.fetchServiceAttributeForSCM(objDto,objUserDto.getUserRoleId());
				Utility.LOG(true,true,"In populateServiceAttributeListSCM Method of Ajax Helper");
			} catch (Exception e) {
				Utility.LOG(true,true,"Some Error in populateServiceAttributeListSCM of Ajax Helper:"+e);
			}
			return saDto;
		}
	/*Function		:partialInititaionTask
	 * return type	:ViewOrderDto
	 * @Author		:Anoop Tiwari
	 * Date			:19-02-2014
	 * Purpose		:for performing partial initiationtasks
	 * */

	public ViewOrderDto partialInititaionTask(ViewOrderDto objDto, String sessionid) throws Exception {	
		String msg="in  AjaxHelper's partialInititaionTask";
		NewOrderDao objDao = new NewOrderDao();
		ViewOrderDto objRetDto=null;
		try{
			HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionid);		
			UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			objDto.setUserid(objUserDto.getEmployeeId());
			objRetDto =objDao.partialInititaionTask(objDto, objUserDto);
			if(null!=objRetDto && objRetDto.getSaveActionCalled()){
				String orderStage ="";
				/*Vijay start */	
				if("1".equalsIgnoreCase(objUserDto.getUserRoleId())){
					orderStage = "AM";  //approved by AM
				}	
				else if("2".equalsIgnoreCase(objUserDto.getUserRoleId())){
					orderStage = "PM";  //approved by PM
				}
				else if("3".equalsIgnoreCase(objUserDto.getUserRoleId())){
					orderStage = "COPC";  //approved by COPC	
				}
				else if("4".equalsIgnoreCase(objUserDto.getUserRoleId())){
					orderStage = "SED";  //approved by SED
				}

				int actionId= 2;//it is for rejection, 
				if("1".equalsIgnoreCase(objDto.getActionId())){
					actionId=1;	//it is for approval
				}
				long orderNo = objDto.getOrderno();
				//below code added by Anil for Send CLEP Response to MPP after successfully Aproved by Last Aproval			
				//	if(Utility.getAppConfigValue(AppConstants.LAST_ROLE_APROVAL_CLEP_SAAS_ERP).equalsIgnoreCase(objUserDto.getUserRoleId())){				
				//	if(objRetDto.getIsSuccessApproved()==1 && "1".equalsIgnoreCase(objDto.getActionId())){
				if(objRetDto.getIsSuccessApproved()==1){	

					CLEPUtility.SysErr("---------- Finding Response to Send >>>>>>>>>>>");
					ViewOrderDto objServiceTypeOrdSrcDto = new ViewOrderDto();
					if(objDto.getTaskID()!=null){
						long taskId=Long.valueOf(objDto.getTaskID());
						
						String OrderCrSrc=Utility.getOrderCreationSource(orderNo);
					 if("2".equals(OrderCrSrc)){
						if("3".equalsIgnoreCase(objUserDto.getUserRoleId()) && actionId==1){
							/*in case of copc approval, MSG template would be change and order id passed rather than task id */
							objServiceTypeOrdSrcDto=objDao.getServiceType_OrderSourceClepErp(orderNo, 4 /*1*/);	
						}
						else
						{
							objServiceTypeOrdSrcDto=objDao.getServiceType_OrderSourceClepErp(taskId, actionId /*1*/);
						}
						//if copc already sent message to mpp,it will not resent
						//if(objServiceTypeOrdSrcDto.getIsCOPCSentMsgToMPP()!=1){

						if(objServiceTypeOrdSrcDto.getServiceTypeID()==2 && "2".equalsIgnoreCase(objServiceTypeOrdSrcDto.getOrder_creation_source())){
							if(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval()!=null){
								CLEPXmlDto clepXmlDto=new CLEPXmlDto();

								//								Vijya setting stage
								clepXmlDto.setStage(orderStage);
								//vijay end

								clepXmlDto.setXmlData(objServiceTypeOrdSrcDto.getResponseClepOrderMsgAfterAproval());
								CLEPUtility.SysErr("Message Request ID Was:- "+objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
								clepXmlDto.setJmsMessageID(objServiceTypeOrdSrcDto.getClepJMSReqMsgId());
								objDao.sendClepResponseToMpp(clepXmlDto,objServiceTypeOrdSrcDto.getClepFileId(),objServiceTypeOrdSrcDto.getOrderno(),"Y");							
							}	
						}
						//}else{
						//	CLEPUtility.SysErr("------- Response already sent to MPP -------------");
						//}
					}
				}	  
					  
					  else{
						CLEPUtility.SysErr("Error: Task Is Not Created!! MPP Response Sending Failed!!");
					}
					if(("2".equalsIgnoreCase(objUserDto.getUserRoleId()))&&(objRetDto.getIsSuccessApproved()==1))
					  {
						 System.out.println("In My code--For PM"); 
						 String attDet1 =Utility.getAppConfigValue(AppConstants.Deliver_To_Requester_Key);
						  Connection conn=null;
						  try{
							  conn = DbConnection.getConnectionObject();
							  SCMDao dao=new SCMDao();
							  int orederNo=(int)objDto.getOrderno();
							//  System.out.println("User id"+objUserDto.getUserId());
							 // System.out.println("ordr No:  -----------" +orederNo);
							  ArrayList<LineItemValueDTO> listDto= dao.getLineItemDtls(conn,orederNo);
							  ArrayList<LineItemValueDTO> listDtoToUpdate=new ArrayList<LineItemValueDTO>(); 
							  for(LineItemValueDTO lineDto:listDto)
							  {
								//  System.out.println("IS_PR_REUSE"+ lineDto.getIsPrReuse());
								  if(lineDto.getIsPrReuse()==0){
								  //	System.out.println("SERVICEPRODUCTID"+ lineDto.getSpId());
									//System.out.println("SERVICEID"+ lineDto.getService_id());
									//System.out.println("SERVICEID"+ lineDto.getServiceDtlId());
									lineDto.setAttValue(objUserDto.getUserId());
									listDtoToUpdate.add(lineDto);
								  }
							  }
							  if(listDtoToUpdate!=null && listDtoToUpdate.size()>0){
									dao.updateAttVal(conn,listDtoToUpdate,attDet1);  
							  }  
						  }finally{
							  DbConnection.freeConnection(conn);
						  }
					}
					  else if(("4".equalsIgnoreCase(objUserDto.getUserRoleId()))&&(objRetDto.getIsSuccessApproved()==1))
					  {
						 //System.out.println("In My code--For SED"); 
						  String attDet2 =Utility.getAppConfigValue(AppConstants.Preparer_Key);
						  Connection conn=null;
						  try{
							  conn=DbConnection.getConnectionObject();
							  SCMDao dao=new SCMDao();
							  int orederNo=(int)objDto.getOrderno();
							  //System.out.println("User id"+objUserDto.getUserId());
							  //System.out.println("ordr No:  -----------" +orederNo);
							  ArrayList<LineItemValueDTO> listDto= dao.getLineItemDtls(conn,orederNo);
							  ArrayList<LineItemValueDTO> listDtoToUpdate=new ArrayList<LineItemValueDTO>(); 
							  for(LineItemValueDTO lineDto:listDto)
							  {
								  //System.out.println("IS_PR_REUSE"+ lineDto.getIsPrReuse());
								  if(lineDto.getIsPrReuse()==0){
								  	//System.out.println("SERVICEPRODUCTID"+ lineDto.getSpId());
									//System.out.println("SERVICEID"+ lineDto.getService_id());
									//System.out.println("SERVICEID"+ lineDto.getServiceDtlId());
									lineDto.setAttValue(objUserDto.getUserId());
									listDtoToUpdate.add(lineDto);
									
								  }
							  }
							  if(listDtoToUpdate!=null && listDtoToUpdate.size()>0){
									dao.updateAttVal(conn,listDtoToUpdate,attDet2);  
							  } 
						  }finally{
							  DbConnection.freeConnection(conn);
						  }
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRetDto;

	}
	 	/*Function		:getServiceListForTheOrderAndRole
	 * return type	:ArrayList
	 * @Author		:Megha 
	 * Date			:24-02-2014
	 * Purpose		:To get services of order
	 * */
	public ArrayList<ServiceLineDTO> getServiceListForTheOrderAndRole(Long orderNo, int roleId) throws Exception {
		NewOrderDao objDao = new NewOrderDao();
		ArrayList<ServiceLineDTO> list = new ArrayList<ServiceLineDTO>();
		try{
			list = objDao.getServiceListForTheOrderAndRole(orderNo, roleId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/*Function		:getServiceListForTheOrderAndRoleWithPagination
	 * return type	:ArrayList
	 * @Author		:Megha 
	 * Date			:24-02-2014
	 * Purpose		:To get services of order
	 * */
	public ArrayList<ServiceLineDTO> getServiceListForTheOrderAndRoleWithPagination(OrderHeaderDTO objDto) throws Exception {
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		System.out.println("in getServiceListForTheOrderAndRoleWithPagination Ajax Helper");
		ArrayList<ServiceLineDTO> list = new ArrayList<ServiceLineDTO>();
		try{
			list = objDao.getServiceListForTheOrderAndRoleWithPagination(objDto);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public String checkServiceAttributePresent(Long orderno,Long roleId) throws Exception 
	{
		NewOrderDaoExt objDao = null;
		String ErrorMessege=null;
		try
		{
			objDao = new NewOrderDaoExt();
			ErrorMessege = objDao.checkServiceAttributePresent(orderno, roleId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return ErrorMessege;
	}
	/*
	 * 
	 * [0099]PM Welcome MailData Modification. 
	 * 
	 */
	public String updatePMWelcomeMail(ReportsDto dto) throws Exception 
	{
		NewOrderDao objDao = new NewOrderDao();
	String value=null;
	try 
	{
		value = objDao.updatePMWelcomeMail(dto);
		//System.out.println("value in pmajax"+value);
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return value;
	}
	

	public ArrayList<ReportsDto> getPMWelcomeMail() throws Exception 
	{
		NewOrderDao objDao = new NewOrderDao();
	ArrayList  list=null;
	try 
	{
		list=objDao.getPMWelcomeMail();
		//System.out.println("value in pmajax"+list);
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return list;
	}
	/*
	 * [0099]pm welcomemail end.
	 */
	public String getAutoSuggestServiceAttribute(
			String searchval, String attributefor, String sourceType, String param, String callType, String roleId) throws Exception{
		String reqVal = searchval;
		String reqAttID = attributefor;
		String accountId = param;
		NewOrderDao objDao = new NewOrderDao();
		
		return objDao.getAutoSuggestServiceAttribute(reqVal, roleId, reqAttID, null, accountId, callType);
	}
	//Add by Deepak Kumar
	public int findServiceStatus(int serviceId) 
	{
        
		NewOrderDao objDao = new NewOrderDao();
		int serviceStatus=0;
		try 
		{
			serviceStatus=objDao.getServiceStatus(serviceId);
			Utility.LOG(true, true, "In findServiceStatus Method of Ajax Helper");
			
		} catch (Exception e) {
			Utility.LOG(true, true, "Some Exception in findServiceStatus"+e);
		}
		return serviceStatus;
	}
//[059]
	public ArrayList<ViewOrderDto> populateReasonForCancel(String ordServFlag) throws IOESException {

		ArrayList<ViewOrderDto> result = null;
		Connection conn = null;
		try {
			
			conn = DbConnection.getConnectionObject();
			NewOrderDao dao = new NewOrderDao();
			result = dao.populateReasonForCancel(ordServFlag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
//	[0100] Start
	public double getMinutesForCreateNewOrder(long orderNo){
		return NewOrderModel.getMinutesForCreateNewOrder(orderNo);
	}
	
	public OrderDto getValidationDataForCopyOrder(long currentOrderNo,String serviceList,String noOfCopy){
		return NewOrderModel.getValidationDataForCopyOrder(currentOrderNo,serviceList,noOfCopy);
	}
//[0100] End
/**
	 * 
	 * @author Gunjan
	 * @param LSICancellationDto
	 * @return String
	 * @throws Exception
	 */
	//[110] start
	public String LSICancellation(LSICancellationDto dto,String sessionId) throws Exception {
		String customMsg=null,msg=":: from "+AjaxHelper.class.getName()+"::method LSICancellation";
		NewOrderDao objDao=new NewOrderDao();
		try{	
			
			HashMap<String, HttpSession> hmap = (HashMap<String, HttpSession>) context.getAttribute(AppConstants.APP_SESSION);
			HttpSession session = hmap.get(sessionId);		
			UserInfoDto objUserDto = (UserInfoDto) session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			customMsg = objDao.LSICancellation(dto, Long.valueOf(objUserDto.getEmployeeId()));
		} 
		catch (Exception e) {
			Utility.LOG(true,false,e,msg);
		}
		return customMsg;
	}
	//[110] end
	//[111] Start VIPIN
	public ChargesDto validateDCHWChargeAndUpdateFlag(long orderNo,int roleId) 
	{
        
		NewOrderModel objModel=new NewOrderModel();
		ChargesDto cdto=null;
		try 
		{
			cdto=objModel.processChargeDetailsForDCHWChargeValidationsAndUpdation(orderNo,roleId);
			Utility.LOG(true, true, "In validateDCHWChargeAndUpdateFlag Method of Ajax Helper");
			
		} catch (Exception e) {
			Utility.LOG(true, true, "Some Exception in validateDCHWChargeAndUpdateFlag"+e);
		}
		return cdto;
	}
	//[111] END VIPIN
	//[112] Start VIPIN
	public int getServicePublishReadyCount(long orderNo) 
	{
        
		NewOrderDao objDao = new NewOrderDao();
		int count=0;
		try 
		{
			count=objDao.getServicePublishReadyCount(orderNo);
			Utility.LOG(true, true, "In getServicePublishReadyCount Method of Ajax Helper");
			
		} catch (Exception e) {
			Utility.LOG(true, true, "Some Exception in getServicePublishReadyCount"+e);
		}
		return count;
	}
	//[112] End VIPIN
	//[113] Start
	public long chkIsPoDetailsPresent(Long spId) 
	{
        
		NewOrderDao objDao = new NewOrderDao();
		long poDetailId=-1;
		try 
		{
			poDetailId=objDao.chkIsPoDetailsPresent(spId);
			Utility.LOG(true, true, "In chkIsPoDetailsPresent Method of Ajax Helper");
			
		} catch (Exception e) {
			Utility.LOG(true, true, "Some Exception in chkIsPoDetailsPresent"+e);
		}
		return poDetailId;
	}
	//[113] End
	
	public String getOrderAttribute(int orderNo, int attributeId) 
	{
		String value = null;
		try 
		{
			value=NewOrderDao.getOrderAttributeValue(orderNo,attributeId);
			Utility.LOG(true, true, "In chkIsPoDetailsPresent Method of Ajax Helper");
			
		} catch (Exception e) {
			Utility.LOG(true, true, "Some Exception in chkIsPoDetailsPresent"+e);
		}
		return value;
	}
//[0sada]	
			public  ArrayList<RateRenewalReportDTO> populateCustomerSegmentDetail(){
				
				
				ArrayList<RateRenewalReportDTO> returncustomerSegment = null; 
				ReportsDao rptdaoobj=new ReportsDao();
				try {
					returncustomerSegment=rptdaoobj.facthCustomerSegmentDetails();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return returncustomerSegment;
			}
//[0sada]
		
		
//[0sada]	
				public  ArrayList<DisconnectChangeOrdeReportDTO> populateCustomerSegmentDetailForDiscon(){
					
					
					ArrayList<DisconnectChangeOrdeReportDTO> returncustomerSegmentForDiscon = null; 
					ReportsDao rptdaoobjdiscon=new ReportsDao();
					try {
						returncustomerSegmentForDiscon=rptdaoobjdiscon.facthCustomerSegmentForDisconChaneReportDetails();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return returncustomerSegmentForDiscon;
				}

				
				public String updateRolesOnSubmit(UserAccessMatrixDto objDto) throws Exception
				{
					return new AccessMatrixModel().updateRolesOnSubmit(objDto);
				}
				
			

				
//start [215]

public String getServiceForValidate(String orderNo,String sessionid,boolean allServicesSelected,String selectedServiceList) throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
	String validated=null;
	try
	{
		long orderNumber=Long.parseLong(orderNo);
		HashMap<String,HttpSession> hmap = (HashMap<String,HttpSession>)context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionid);		
		UserInfoDto objUserDto  = (UserInfoDto)session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		Integer[] roleIds={0,Integer.parseInt(objUserDto.getUserRoleId())};
		validated = objDao.getServiceForValidate(Long.parseLong(orderNo),roleIds,allServicesSelected,selectedServiceList);
		Utility.LOG(true, true, "In getServiceForValidate Method of Ajax Helper");
		
	} 
	catch (Exception e) {
		Utility.LOG(true, true, "Some exception In getServiceForValidate Method of Ajax Helper"+e);
		
	}
	return validated;
}

public String checkOldCodeActive() throws Exception 
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
    String checkStatus="0";
	try{
		checkStatus=Utility.getAppConfigValue("PARTIAL_VALIDATION_OLD_CODE");
		
		
	}
	catch (Exception e) {
		Utility.LOG(true, true, "Some exception In checkOldCodeActive Method of Ajax Helper"+e);
			
	}
	return checkStatus;
}


//End [215]

//Start [117]
public ArrayList<NewOrderDto>  getAccountDetailsWithSortingforReassignAm(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();
		ArrayList errors=new ArrayList();
	objDto.setAccountName(Utility.trimIfNotNull(objDto.getAccountName()));
	
	String accountName=objDto.getAccountName();
	Utility.validateValue(new ValidationBean()
				.loadValidationBean_Maxlength("Account Name", accountName, 150),
				Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,Utility.CASE_MAXLENGTH))
				.appendToAndRetNewErrs(errors);
	
	
	if(errors!=null && errors.size()>0)//During Server Side Validation
	{
		NewOrderDto dto=new NewOrderDto();
		dto.setErrors_Validation(errors);
		ArrayList<NewOrderDto> errorList=new ArrayList<NewOrderDto>();
		errorList.add(dto);
		//return errorList;
	}
	ArrayList<NewOrderDto> listAccountTypes= new ArrayList<NewOrderDto>();
	try 
	{
		listAccountTypes = objDao.getAccountDetailsWithSortingforReassignAm(objDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return listAccountTypes;
}

public String reassignAM(String reassignamList,String reassignamnameList,String orderNoList,int roleid,String lastamidList,String lastamemailidList,String actmgrList) throws Exception 
{
	NewOrderDaoExt objdao = new NewOrderDaoExt();
	String status=null;
	try
	{
		// parameter added by manisha orderNoList,roleid,lastpmidList,lastpmemailidList,actmgrList defect no 15032013010
		status = objdao.reassignAM(reassignamList,reassignamnameList,orderNoList,roleid,lastamidList,lastamemailidList,actmgrList);
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return status;
}

//End [117]
public ArrayList<UserAccessMatrixDto> getAllCustomerSegmentList() throws Exception
				{
					ArrayList<UserAccessMatrixDto> returncustomerSegment = null; 
					NewOrderDao daoobj=new NewOrderDao();
					try {
						returncustomerSegment=daoobj.getCustomerSegmentList();
						
					} catch (Exception e) {
						Utility.LOG(true, true,"Some Error Occured in getAllCustomerSegmentList method of AjaxHelper "+e);
					}
					return returncustomerSegment;
				}
				
				public ArrayList<UserAccessMatrixDto> getMappedCustomerSegmentWithUserId(String empid) throws Exception
				{
					try {
						ArrayList<UserAccessMatrixDto> returnMappedCustomerSegment = null; 
						NewOrderDao daoObj=new NewOrderDao();
						returnMappedCustomerSegment=daoObj.getMappedCustomerSegment(empid);
						return returnMappedCustomerSegment;
					} catch (Exception e) {
						Utility.LOG(true, true,"Some Error Occured in getMappedCustomerSegmentWithUserId method of AjaxHelper "+e);
						return null;
					}
				}
/**
 * The mail and sms sending async during order approval or rejection
 * @author Anil Kumar
 * @since 14-April-2015
 * @param objDto
 */
public void sendIB2BApprovalRejectionMailAndSMS(ViewOrderDto objDto){
	try{
		NewOrderModel.sendIB2BApprovalRejectionMailAndSMS(objDto);
	}catch(Exception exp){
		Utility.LOG(true, true,"Some Error Occured in sendIB2BApprovalRejectionMailAndSMS method of AjaxHelper "+exp);
	}
}
//Start[118]
/**
 * Inserting new standard reason
 * @param StandardReason
 * @return
 */
public String insertNewStandardReason(StandardReason objDto,String sessionid){
	String status="-1";
	try{
		HashMap<String, HttpSession> hmap = (HashMap<String, HttpSession>) context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionid);		
		UserInfoDto objUserDto = (UserInfoDto) session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		
		ArrayList<String> errors=AjaxValidation.ValidateStandardReasonName(objDto);
		status=errors.toString();		
		if(errors.size()==0){
			status=NewOrderModel.insertNewStandardReason(objDto);
		}else{
			return status;
		}
		
	}catch(Exception e){
		Utility.LOG(true, true,"Some Error Occured in insertNewStandardReason method of AjaxHelper "+e);
	}
	
	return status;
}
/**
 * fetch standard reason details
 * @param PagingDto
 * @return  ArrayList<StandardReason>
 * @throws Exception
 *
 */
public ArrayList<StandardReason>  fetchStandardReason(StandardReason objDto)
{	
	ArrayList<StandardReason> listStandardReasonDetails= new ArrayList<StandardReason>();
	try 
	{						
		listStandardReasonDetails = NewOrderModel.fetchStandardReason(objDto);
			
	} catch (Exception e) {
		e.printStackTrace();
		Utility.LOG(true, true,"Some Error Occured in fetchStandardReason method of AjaxHelper "+e);
	}
	return listStandardReasonDetails;
}
/**
 * 
 * @param strStdReasonIds
 * @param strStdReasonStatus
 * @param sessionid
 * @return
 */
public int editStandardReason(String strStdReasonIds,String strStdReasonStatus,String sessionid){
	int status=-1;
	try{
		HashMap<String, HttpSession> hmap = (HashMap<String, HttpSession>) context.getAttribute(AppConstants.APP_SESSION);
		HttpSession session = hmap.get(sessionid);		
		UserInfoDto objUserDto = (UserInfoDto) session.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		
		status=NewOrderModel.editStandardReason(strStdReasonIds,strStdReasonStatus);
		
	}catch(Exception e){
		Utility.LOG(true, true,"Some Error Occured in editStandardReason method of AjaxHelper "+e);
	}
	
	return status;
}
//End[118]


public ArrayList<NewOrderDto>  getDispatchDetailsWithSorting(PagingDto objDto) throws Exception
{
	NewOrderDaoExt objDao = new NewOrderDaoExt();		
	ArrayList<NewOrderDto> listDispatchDetails= new ArrayList<NewOrderDto>();
	try 
	{						
		listDispatchDetails = objDao.getDispatchDetailsWithSorting(objDto);
			
	} catch (Exception e) {
		listDispatchDetails=null;
		Utility.LOG(true, false, e, "Error: in method getDispatchDetailsWithSorting of AjaxHelper class.");
	}
	return listDispatchDetails;
}

public int loggTotalTimeTaken(int orderno,Date start_time,int total_elapsed_time,String actiontype) throws Exception{

	NewOrderDaoExt objDao = new NewOrderDaoExt();
	int status=objDao.loggTotalTimeTaken(orderno,start_time,total_elapsed_time,actiontype);
	return 1;
}
public String getMBICServicesWithTGNOBlank(Long orderNo) throws Exception 
{
		NewOrderDao objDao = new NewOrderDao();
		List<Long> list = new ArrayList<Long>();
		String strListMbicServices="";
	try
	{
		list = objDao.getMBICServicesWithTGNOBlank(orderNo);
		strListMbicServices=list.toString();
	} 
	catch (Exception e) {
		e.printStackTrace();
	
	}
	return strListMbicServices;
}
//[124] start
public M2MClass validateM2M(int orderNo) 
{
	NewOrderModel model=new NewOrderModel();
	
	M2MClass classM2m=new M2MClass();
	
	classM2m=model.validateM2M(orderNo);
	
	return classM2m;
	}
//[124] end


	public static class MyHttpServletResponseWrapper extends HttpServletResponseWrapper{
	
		ServletOutputStream outputStream =null;
		
		public MyHttpServletResponseWrapper(HttpServletResponse response) {
			super(response);
			
			outputStream = new ServletOutputStreamEx();
			
		}
	
		/*@Override
		public PrintWriter getWriter() throws IOException {
			return printWriter;
		}*/
		
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return outputStream;
		}
		
		@Override
	    public PrintWriter getWriter() throws IOException {
	        //return new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"));
			return new PrintWriter(sw);
	    }
		
		StringWriter sw = new StringWriter();
		
		public String getData(){
			return sw.getBuffer().toString();
		}
		
	}
	
	
	
	public static class ServletOutputStreamEx extends ServletOutputStream {
	
	    StringBuilder stringBuilder;
	
	    public ServletOutputStreamEx() {
	        this.stringBuilder = new StringBuilder();
	    }
	
	    @Override
	    public void write(int b) throws IOException {
	    	stringBuilder.append(b);
	    } 
	
	    @Override
	    public void write(byte b[], int off, int len) throws IOException {
	        stringBuilder.append(new String(b, "UTF-8"));
	    }
	
	    @Override
	    public String toString() {
	        return stringBuilder.toString();
	    }
	    
	    @Override
	    public void write(byte[] b) throws IOException {
	    	stringBuilder.append(new String(b, "UTF-8"));
	    }
	}
	    public LsiCancellationTableResult GetLsiCancellationTable(HttpServletRequest request,HttpSession session,LSICancellationDto lsiCancellationDto,PagingDto objPagingDto,int pagingRequired/*,String sessionId*/){
			//System.out.println("savdjhdvjhdvjasdvsahdvasjdvajh"+lsiCancellationDto);
			//Object form=request.getAttribute("formOb");
			int startIndex=objPagingDto.getStartIndex();
			int endIndex=objPagingDto.getEndIndex();
			//l,conn,Long.parseLong(objViewOrderdto.getServiceId()),startIndex,endIndex,pagingRequired,objPagingDto.getPageRecords()
			return new LSICancellationService().GetLsiCancellationTable(request,session,lsiCancellationDto,new UtilityService(),startIndex,endIndex,pagingRequired,objPagingDto.getPageRecords());
			
		}
	    
	    public int performPurging() throws Exception
		{
			NewOrderDaoExt objDao = new NewOrderDaoExt();
			int updationStatus = 0 ;
			try 
			{
				updationStatus = objDao.performPurging();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return updationStatus;
		} 
	public ArrayList<NewOrderBean>  fetchChannelPartner(Long respId,int startIndex,int endIndex,int pagingRequired,int pageRecords, String FeId , String partnerName)
	{	
		ArrayList<NewOrderBean> listChannelPartnerDetails= new ArrayList<NewOrderBean>();
		try 
		{						
			listChannelPartnerDetails = NewOrderModel.fetchChannelPartner(respId,startIndex,endIndex,pagingRequired,pageRecords,FeId,partnerName);
				
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true,"Some Error Occured in fetchChannelPartner method of AjaxHelper "+e);
		}
		return listChannelPartnerDetails;
	}
	
	public ArrayList<FieldEnginnerDto>  getAllFEList(long respId)
	{	
		ArrayList<FieldEnginnerDto> listAllFE= new ArrayList<FieldEnginnerDto>();
		try 
		{						
			listAllFE = NewOrderDao.fetchAllFE(respId);
				
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true,"Some Error Occured in getAllFEList method of AjaxHelper "+e);
		}
		return listAllFE;
	}

	public ArrayList<ChannelPartnerDto>  getAllPartnerNameList(long respId)
	{	
		ArrayList<ChannelPartnerDto> listAllPartnerName= new ArrayList<ChannelPartnerDto>();
		try 
		{						
			listAllPartnerName = NewOrderDao.getAllPartnerNameList(respId);
				
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true,"Some Error Occured in getAllFEList method of AjaxHelper "+e);
		}
		return listAllPartnerName;
	}	
	
	public ArrayList<ChannelPartnerDto>  getPartnerCode(long crmAccountNo)
	{	
		ArrayList<ChannelPartnerDto> listAllPartnerCode= new ArrayList<ChannelPartnerDto>();
		try 
		{						
			listAllPartnerCode = NewOrderDao.getPartnerCodeList(crmAccountNo);
				
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true,"Some Error Occured in getPartnerCode method of AjaxHelper "+e);
		}
		return listAllPartnerCode;
	}
	
	public ArrayList<ChannelPartnerDto>  getPartnerNameList(long crmAccountNo)
	{	
		ArrayList<ChannelPartnerDto> listAllPartnerName= new ArrayList<ChannelPartnerDto>();
		try 
		{						
			listAllPartnerName = NewOrderDao.getPartnerNameList(crmAccountNo);
				
		} catch (Exception e) {
			e.printStackTrace();
			Utility.LOG(true, true,"Some Error Occured in getPartnerCode method of AjaxHelper "+e);
		}
		return listAllPartnerName;
	}
	
	public String insertChannelPartner(NewOrderBean objDto){
		String status="-1";
		try{
			status=NewOrderModel.insertNewChannelPartner(objDto);
		}catch(Exception e){
			Utility.LOG(true, true,"Some Error Occured in insertChannelPartner method of AjaxHelper "+e);
		}
		return status;
	}
	
	public String saveChannelPartner(ChannelPartnerDto objDto){
		String status = null ;
		try{
			status=NewOrderModel.saveChannelPartner(objDto);
		}catch(Exception e){
			Utility.LOG(true, true,"Some Error Occured in saveChannelPartner method of AjaxHelper "+e);
		}
		return status;
	}
	
	public ArrayList<Integer> getEligibleCustSegmforFeId(){
		ArrayList<Integer> listofFeId = null;
		try{
			 listofFeId=NewOrderDao.getEligibleCustSegm();
		}catch(Exception e){
			Utility.LOG(true, true,"Some Error Occured in getEligibleCustSegmforFeId method of AjaxHelper "+e);
		}
		return listofFeId;
	}
	
	public ArrayList<Long> getCustSegbyRespId(Integer respId){
		ArrayList<Long> custSegId = null;
		try{
			custSegId=NewOrderDao.getCustSegIdByRespId(respId);
		}catch(Exception e){
			Utility.LOG(true, true,"Some Error Occured in getCustSegbyRespId method of AjaxHelper "+e);
		}
		return custSegId;
	}
	
	public String getCustSegNamebyRespId(Integer respId){
		String custSegNameList = null;
		try{
			custSegNameList=NewOrderDao.getCustSegNameByRespId(respId);
		}catch(Exception e){
			Utility.LOG(true, true,"Some Error Occured in getCustSegbyRespId method of AjaxHelper "+e);
		}
		return custSegNameList;
	}
	
	public String getCustLOBNamebyRespId(Integer respId){
		String lobNameList = null;
		try{
			lobNameList=NewOrderDao.getLOBNameByRespId(respId);
		}catch(Exception e){
			Utility.LOG(true, true,"Some Error Occured in getCustSegbyRespId method of AjaxHelper "+e);
		}
		return lobNameList;
	}
	// Shubhranshu,Last Modified on 23-06-2016
	// Called from Change Order.jsp on Publish of PD related orders //
	public ArrayList<ServiceDto> validateParallelUpgradeForDisconnectionOrder(Integer[] listForValidation) throws Exception
	{
			//System.out.println(listForValidation.toString()); 
			//System.out.println("i am here in validateParallelUpgradeForDisconnectionOrder !!");			
			return new NewOrderModel().validateParallelUpgradeForDisconnectionOrder(new ArrayList<Integer>(Arrays.asList(listForValidation)));	
	}
// Shubhranshu
	
	// For Draft PD order Gui 
public ArrayList<ServiceDto>validatePDOrderForParallelUpgrade(Integer[] orderNumberArray) throws Exception
	{
		return (new NewOrderModel().validatePDOrderForParallelUpgrade(new NewOrderDao().getServiceIdsFromOrderNumberArray(orderNumberArray)));
	}
//Start Amit Sharma
public ArrayList<String> newOrderKeyValues() throws Exception{
	ArrayList<String> ValuesList = null;
	try{
		ValuesList=NewOrderDao.newOrderKeyValues();
	}catch(Exception e){
		Utility.LOG(true, true,"Some Error Occured in newOrderKeyValues method of AjaxHelper "+e);
	}
	return ValuesList;
}
	//End Amit Sharma
///[130] start
public ServiceDto validateDropAndCarry(long orderNo,String stage){
	
	NewOrderModel model=new NewOrderModel();
	ServiceDto msgDto=null;
	try{
		msgDto=model.validateDropAndCarry(orderNo,stage);
	}catch(Exception e){}
	
	return msgDto;
}

public String checkDropAndCarryLSiForPD(String strLogicalSiNo,int subChangeTypeId){
	NewOrderDaoExt objDaoExt=new NewOrderDaoExt();
	String result=null;
	try {
		result=objDaoExt.checkDropAndCarryLSiForPD(strLogicalSiNo,subChangeTypeId);
	} catch (Exception e) {
		
		Utility.LOG(true, true, e, "in method checkDropAndCarryLSiForPD()");
		
	}
	return result;
}
//[130] end
	//Shubhranshu, 12-oct-2016, Drop&Carry
	public ArrayList<ServiceDto>getDropAndCarryDataForCC(Long serviceId)throws Exception 
	{
		return new NewOrderDao().getDropAndCarryDataForCC(serviceId);
	}
	// Shubhranshu, 
	public ArrayList<ServiceDto>getHeadEndLsiForDropAndCarryWithSorting(PagingDto objDto) throws Exception
	{
		return new NewOrderDao().getHeadEndLsiForDropAndCarryWithSorting(objDto);
	}
	
	public int saveDropNCarryData(ServiceDto sdto)throws Exception{
		
		return new NewOrderDao().saveDropNCarryData(sdto);
	}
	//end
	public String getIsDifferentialEnable(String HdnChangeTypeID,String changeTypeId) throws Exception{
		return new NewOrderModel().getIsDifferentialEnable(HdnChangeTypeID,changeTypeId);
	}
	//nancy for migration
public int countOfRecordsToMigrate() throws Exception
{
	int noOfrecords =0;
	MigrationUtility countAttachments = new MigrationUtility();
	noOfrecords= countAttachments.countNoOfAttachmentsToMigrate();
	return noOfrecords;

}
public String initiateMigration() throws Exception
{
	String message =null;
	/*MigrationUtility migrateAttachments = new MigrationUtility();*/
	MigrationUtilityWrapper migrateAttachments = new MigrationUtilityWrapper();
	Utility.SPT_LOG(true, true, "********************Migration of files started at ************************:" + new Date()+"\n");
	migrateAttachments.processMigartion();
	Utility.SPT_LOG(true, true, "********************Migration of files ended at ************************:" + new Date()+"\n");
	//message = migrateAttachments.processMigartion();
	return "Migration Completed Successfully...";
}

//Gunjan Start
public ArrayList<String> getLayerRateAttIds() throws Exception{
	ArrayList<String> ValuesList = null;
	try{
		ValuesList=NewOrderDao.getLayerRateAttIds();
	}catch(Exception e){
		Utility.LOG(true, true,"Some Error Occured in getLayerRateAttIds method of AjaxHelper "+e);
	}
	return ValuesList;
}
public ArrayList<String> getL2_L3_ISP_BW_AttIds() throws Exception{
	ArrayList<String> ValuesList = null;
	NewOrderDao daoObj=new NewOrderDao();
	try{
		ValuesList=daoObj.getL2_L3_ISP_BW_AttIds();
	}catch(Exception e){
		Utility.LOG(true, true,"Some Error Occured in getL2_L3_ISP_BW_AttIds method of AjaxHelper "+e);
	}
	return ValuesList;
}

//Gunjan End
/*satish Starts for Archival*/
public String checkArchivalPhase(String groupId) throws Exception {  
	ArchiveIb2bOrdersDao objDao=new ArchiveIb2bOrdersDao();
	ArrayList<ArchivalReportBean> archivalList=null;
	ArchivalReportBean archivalPhase=null;
	ArrayList<String> lsiList=null;
	String flag=null;
	try 
	{
		archivalPhase =objDao.checkArchivalPhase(groupId);
		flag=archivalPhase.getArchival_phase();	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return flag;
}

public boolean checkLsiGroupIdServiceList(String groupId,String excludingLSINo ) throws Exception {  
	ArchiveIb2bOrdersDao objDao=new ArchiveIb2bOrdersDao();
	ArchivalModel model=new ArchivalModel();
	//ArrayList<ArchivalReportBean> groupAndlsi = null;
	ArrayList<ArchivalReportBean> archivalList=null;
	//ArrayList<ArchivalReportBean> archivalList2=null;
	ArrayList<String> lsiList=null;
	boolean lsiisExistAndGroupId = false;
	try 
	{
		//groupAndlsi = objDao.checkLsiGroupIdServiceList(groupId);
		lsiList=model.listofalltheInputLsi(excludingLSINo);
		archivalList  = objDao.searchLsiGroupIdServiceList(groupId);//list whose iseligble=1
		//archivalList2 =objDao.checkLsiGroupIdServiceList(groupId);
		//String flag=archivalList2.get(0).getArchival_phase();
		
		lsiisExistAndGroupId=model.isLsiExistInDb(lsiList,archivalList);
		
		 
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return lsiisExistAndGroupId;
}



/*satish Starts for Archival*/

}