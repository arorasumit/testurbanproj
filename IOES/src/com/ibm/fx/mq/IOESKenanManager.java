package com.ibm.fx.mq;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import terrapin.tuxedo.TuxError;

import com.bharti.utils.BHUtils;
import com.csgsystems.aruba.bp.data.ServerData;
import com.csgsystems.aruba.bp.data.ServerDataList;
import com.csgsystems.aruba.bp.data.ServerFilter;
import com.csgsystems.aruba.bp.session.ServerBean;
import com.csgsystems.aruba.connection.BSDMResourceException;
import com.csgsystems.aruba.connection.BSDMSessionContext;
import com.csgsystems.aruba.connection.BSDMSettings;
import com.csgsystems.aruba.connection.ConnectionFactory;
import com.csgsystems.aruba.connection.ServiceException;
import com.csgsystems.aruba.filter.IntegerEquals;
import com.csgsystems.aruba.filter.IntegerFilter;
import com.csgsystems.aruba.filter.StringEquals;
import com.csgsystems.aruba.filter.StringFilter;
import com.csgsystems.bali.connection.ApiMappings;
import com.csgsystems.bp.data.AccountCreateOutputData;
import com.csgsystems.bp.data.AccountIdObjectData;
import com.csgsystems.bp.data.AccountLocateXIDObjectData;
import com.csgsystems.bp.data.AccountLocateXIDObjectDataList;
import com.csgsystems.bp.data.AccountLocateXIDObjectFilter;
import com.csgsystems.bp.data.AccountObjectData;
import com.csgsystems.bp.data.AccountObjectKeyData;
import com.csgsystems.bp.data.AccountXIDObjectData;
import com.csgsystems.bp.data.AccountXIDObjectFilter;
import com.csgsystems.bp.data.NoteObjectData;
import com.csgsystems.bp.data.OpenItemIdMapObjectData;
import com.csgsystems.bp.data.ProductRateOverrideObjectData;
import com.csgsystems.bp.session.AccountBean;
import com.csgsystems.bp.session.AccountIdBean;
import com.csgsystems.bp.session.AccountLocateBean;
import com.csgsystems.bp.session.NoteBean;
import com.csgsystems.bp.session.OpenItemIdMapBean;
import com.csgsystems.bp.session.ProductRateOverrideBean;
import com.csgsystems.fx.security.SecurityManager;
import com.csgsystems.fx.security.SecurityManagerFactory;
import com.csgsystems.fx.security.util.AuthenticationException;
import com.csgsystems.fx.security.util.FxException;
import com.csgsystems.om.data.ComponentObjectBaseKeyData;
import com.csgsystems.om.data.ComponentObjectData;
import com.csgsystems.om.data.CustomerIdEquipMapObjectData;
import com.csgsystems.om.data.NrcObjectBaseData;
import com.csgsystems.om.data.NrcObjectBaseKeyData;
import com.csgsystems.om.data.NrcObjectData;
import com.csgsystems.om.data.OrderLookupObjectData;
import com.csgsystems.om.data.OrderLookupObjectKeyData;
import com.csgsystems.om.data.OrderObjectData;
import com.csgsystems.om.data.OrderObjectKeyData;
import com.csgsystems.om.data.OrderedCiemCreateOutputData;
import com.csgsystems.om.data.OrderedComponentCreateOutputData;
import com.csgsystems.om.data.OrderedComponentDisconnectOutputData;
import com.csgsystems.om.data.OrderedNrcCreateOutputData;
import com.csgsystems.om.data.OrderedPackageCreateOutputData;
import com.csgsystems.om.data.OrderedProductCreateOutputData;
import com.csgsystems.om.data.OrderedProductDisconnectOutputData;
import com.csgsystems.om.data.OrderedServiceCreateOutputData;
import com.csgsystems.om.data.OrderedServiceDisconnectOutputData;
import com.csgsystems.om.data.ProductObjectBaseData;
import com.csgsystems.om.data.ProductObjectBaseKeyData;
import com.csgsystems.om.data.ProductObjectData;
import com.csgsystems.om.data.ProductPackageObjectData;
import com.csgsystems.om.data.ServiceObjectBaseData;
import com.csgsystems.om.data.ServiceObjectBaseKeyData;
import com.csgsystems.om.data.ServiceObjectData;
import com.csgsystems.om.session.CustomerIdEquipMapBean;
import com.csgsystems.om.session.NrcBean;
import com.csgsystems.om.session.OrderBean;
import com.csgsystems.om.session.OrderLookupBean;
import com.csgsystems.om.session.OrderedCiemBean;
import com.csgsystems.om.session.OrderedComponentBean;
import com.csgsystems.om.session.OrderedNrcBean;
import com.csgsystems.om.session.OrderedPackageBean;
import com.csgsystems.om.session.OrderedProductBean;
import com.csgsystems.om.session.OrderedServiceBean;
import com.csgsystems.om.session.ProductBean;
import com.csgsystems.om.session.ServiceBean;
import com.ibm.fx.dto.AcctDTO;
import com.ibm.fx.dto.BCPAddressChangeDTO;
import com.ibm.fx.dto.ChargeRedirectDTO;
import com.ibm.fx.dto.ChargesDto;
import com.ibm.fx.dto.ComponentDTO;
import com.ibm.fx.dto.DisconnectionDto;
import com.ibm.fx.dto.ErrorDTO;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.FxExternalIdDto;
import com.ibm.fx.dto.LOCDATADTO;
import com.ibm.fx.dto.NrcDto;
import com.ibm.fx.dto.PackageDTO;
import com.ibm.fx.dto.RcDto;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.fx.dto.ServiceDisconnectionDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.ApplicationFlags;
import com.ibm.ioes.utilities.Utility;
import com.ibm.fx.dto.UpdateAccountDto;


/**
 * This class is used to interact with Kenan FX using its API - TS calls and
 * Stored Procedure calls.
 * 
 * @author Kunal Kumar Sharma
 * @version 0.1
 * @Created Date 31/03/2008
 * 
 */

public class IOESKenanManager {

	int customUATDate=0;
	Date uatDate=new Date(2012-1900,9-1,1);
	/**
	 * This member Variable sets the context for the Kenan Session
	 */
	protected BSDMSessionContext context = null;

	/**
	 * This member Variable sets the Security Manager for the Kenan Session
	 */
	protected static SecurityManager sm = null;
	
	private static boolean isSmAuthenicated = false;

	/**
	 * This member Variable sets the BSDM Settings for the Kenan Session
	 */
	protected static BSDMSettings settings = null;

	/**
	 * This member Variable sets the Logger Object for logging the Debug / Error /
	 * Exception Messages
	 */
	private Logger logSuccess = null;

	/**
	 * This member Variable sets the BLOCK_SIZE for the size of Data Returned
	 * from each Kenan Call
	 */
	private int BLOCK_SIZE;

	/**
	 * This member Variable sets the Maximum no of Rows for the size of Data
	 * Returned
	 */
	private int MAX_COUNT_ROWS;

	/**
	 * This member Variable stores the FX User Realm
	 */

	private String FX_USER_REALM = null;

	/**
	 * This member Variable stores the FX User Id
	 */

	private String FX_USER_ID = null;

	/**
	 * This member Variable stores the FX Session Name
	 */

	private String FX_SESSION_NAME = null;

	/**
	 * This member Variable stores the FX User Password
	 */

	private String FX_USER_PASSWORD = null;
	
	private static HashMap request = new HashMap();
	
	
	private String callName = null;
	
	private static HashMap Order	= new HashMap();
	
	public static ErrorDTO errDTO = new ErrorDTO();
	
	private HashMap hmProduct = null;
	
	private HashMap hmKey     = null;
	
	private HashMap hmViewid  = null;
	
	private HashMap hmRequest = null;
	
	private HashMap hmResponse = null;
	
	
	private final String    PRODUCT  = "Product";
	
	private final String    PRODUCTFIND = "ProductFind";
	
	private final String  	EQUAL   = "Equal";
	 
	private final String  	VIEWID  = "ViewId";
	
	private final String  	FETCH   = "Fetch"; 

	private final String 	KEY 	= "Key";
	
	private final String    ORDEREDPRODUCT = "OrderedProduct";
	
	private final String    INACTIVEDATE =  "InactiveDt";
	
	private static ConnectionFactory connectionFactory = null;
	private static com.csgsystems.aruba.connection.Connection fxApiConnection = null;

	


	//public static Date date=new Date(2007-1900,2-1,1);
	/**
	 * This is the default constructor of class.
	 * 
	 * @throws FxException
	 * @throws NullPointerException
	 * 
	 */

	public IOESKenanManager() throws FxException, NullPointerException {

		try {

			BHUtils.getInstance();

/*			String strBlockSize = BHUtils.getProperty("BLOCK_SIZE").trim();
			BLOCK_SIZE = new Integer(strBlockSize.trim()).intValue();

			String strMaxRows = BHUtils.getProperty("MAX_COUNT_ROWS").trim();
			MAX_COUNT_ROWS = new Integer(strMaxRows.trim()).intValue();

			FX_USER_REALM = BHUtils.getProperty("FX_USER_REALM").trim();
			FX_USER_ID = BHUtils.getProperty("FX_USER_ID").trim();
			FX_SESSION_NAME = BHUtils.getProperty("FX_SESSION_NAME").trim();
			FX_USER_PASSWORD = BHUtils.getProperty("FX_USER_PASSWORD").trim();

			// Decode the encrypted password

			FX_USER_PASSWORD = BHUtils.decode(FX_USER_PASSWORD);
*/
			//String strBlockSize = BHUtils.getProperty("BLOCK_SIZE").trim();
			BLOCK_SIZE = 5;

			//String strMaxRows = BHUtils.getProperty("MAX_COUNT_ROWS").trim();
			MAX_COUNT_ROWS = 5;

			FX_USER_REALM = BHUtils.getProperty("FX_USER_REALM").trim();
			FX_USER_ID = BHUtils.getProperty("FX_USER_ID").trim();
			FX_SESSION_NAME = BHUtils.getProperty("FX_SESSION_NAME").trim();
			//FX_USER_PASSWORD = BHUtils.getProperty("FX_USER_PASSWORD").trim();
		    //String keyname = Messages.getMessageValue("PASSWORD_ENCRYPT_KEY");
			//FX_USER_PASSWORD=Utility.getDecryptedPassword(keyname,"FX_USER_PASSWORD");
			FX_USER_PASSWORD="csgfx";
			// Decode the encrypted password

			//FX_USER_PASSWORD = BHUtils.decode(FX_USER_PASSWORD);

			
			context = BSDMSessionContext.getDefaultContext();

			if (context != null) {

				System.out.println(IOESKenanManager.class.getName()
						+ " Operator: " + context.getOperatorName()
						+ ", ServerId: " + context.getServerId());

			} else {

				System.err.println(IOESKenanManager.class.getName()
						+ ":No default context available.");
				logSuccess.error(IOESKenanManager.class.getName()
						+ ":No default context available.");

			}

			if(sm==null){
				synchronized (IOESKenanManager.class) {
					if(sm==null){
						sm = SecurityManagerFactory.createSecurityManager(FX_USER_REALM,
								FX_USER_ID, FX_SESSION_NAME, FX_USER_PASSWORD);	
					}
				}
			}
			
		} catch (FxException e) {
			throw e;
		} catch (NullPointerException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// end of try catch block
	} // end of constructor : DTHKenanManager

	/**
	 * This method is used to Log in the Security Server before any method is
	 * called from the Kenan Manager Class.
	 * 
	 * @throws AuthenticationException
	 */

	public void loginKenan() throws AuthenticationException {

		// Authenticate the SecurityManager object
		try {
			
			if(isSmAuthenicated == false){
				synchronized (this.getClass()) {
					if(isSmAuthenicated == false){
						sm.authenticate();
						isSmAuthenicated=true;
					}
				}
				
			}
			
			
			
			if (fxApiConnection == null) {
				context = BSDMSessionContext.getDefaultContext();
				if(settings == null){
					synchronized (this.getClass()) {
						if(settings == null){
							settings = BSDMSettings.getDefault();			
						}
					}
				}
				
				connectionFactory = ConnectionFactory.instance();
				try {
					fxApiConnection = connectionFactory.createConnection(settings);
				} catch (TuxError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			context.setServerId(ApplicationFlags.FX_AES_CUSTOMER_SERVER_ID);
		} catch (AuthenticationException e1) {
			throw e1;
		}
		// Associate the SecurityManager object with BSDMSessionContext object
		context.setSecurityContext(sm);
		//context.setServerId(50);
		// Create a default BSDMSettings object.

		

	} // end of method : loginKenan()

	/**
	 * 
	 * This method is responsible to fetch the all customer server ID from
	 * catalog server.
	 * 
	 * @throws BSDMResourceException
	 * @throws ServiceException
	 * @return arrLstServerList
	 * 
	 */

	public ArrayList getServerID() throws BSDMResourceException,
			ServiceException {

		Integer iOldSerId = null;
		ArrayList arrLstServerList = null;

		iOldSerId = context.getServerId();
		context.setServerId(new Integer(1));

		ServerBean serverBean = new ServerBean(this.settings);
		ServerFilter sf = new ServerFilter();
		// ServerType 3 is for customer servers as per APITS Doc
		IntegerFilter ifilter = new IntegerEquals(new Integer(3));
		sf.setServerTypeFilter(ifilter);
		sf.setServerIdFetch(true);
		sf.setFetch(true);
		ServerDataList serverList = serverBean.find(context, sf);
		ServerData[] serverData = serverList.getArray();

		if (serverData.length > 0) {
			arrLstServerList = new ArrayList();
			logSuccess.debug(":Total Customer Servers Count is:"
					+ serverData.length);
		} else {
			logSuccess.debug(":There is No Customer Server Found:");
			return arrLstServerList;
		}

		for (int i = 0; i < serverData.length; i++) {
			arrLstServerList.add(serverData[i].getServerId());
		}

		logSuccess.debug(":Availble Customer Server Ids are:"
				+ arrLstServerList);

		context.setServerId(iOldSerId);

		return arrLstServerList;
	}

	/**
	 * Getter method for context
	 * 
	 * @return context
	 */

	public BSDMSessionContext getContext() {
		return this.context;
	} // end of method : getContext()

	/**
	 * Getter method for SecurityManager
	 * 
	 * @return sm
	 */

	public SecurityManager getSecurityManager() {
		return this.sm;
	} // end of method : getSecurityManager()

	/**
	 * Getter method for settings
	 * 
	 * @return settings
	 */

	public BSDMSettings getSettings() {
		return this.settings;
	} // end of method : getSettings()

	/**
	 * This method sets the Logger Object for logging the Debug / Error /
	 * Exception Messages
	 * 
	 * @param loggerDebug
	 */
	public void setLoggers(Logger loggerDebug) {
		this.logSuccess = loggerDebug;
	} // end of method : setLoggers

	/**
	 * This method will be used to logout the security server and close the
	 * APITS sessions.
	 * 
	 * @throws FxException
	 */
	public void close() throws FxException {
		BHUtils.getInstance();
		// Log out of Security Server
		/*try {
			this.sm.logout();
		} catch (FxException e) {
			throw e;
		}*/ // end of try catch block : FxException
	} // End of Method : close()

	

	public AccountXIDObjectData accountFind(String strAccountId) throws Exception
			{

		/*System.out.println("\n" + "In method accountFind" + new Date()
				+ "The Account id passed is" + strAccountId);*/
		AccountXIDObjectData accountXIDObjectData = null;

		try {

			// Set the External Id Type
			Integer iExternalId = new Integer("1");
			IntegerEquals integerEquals = new IntegerEquals(iExternalId);
			IntegerFilter iFilterExtId = integerEquals;
			IntegerFilter[] iExtIdType = new IntegerFilter[1];
			iExtIdType[0] = iFilterExtId;
			// Set the Account Number

			StringEquals stringEquals = new StringEquals(strAccountId);
			StringFilter[] stringFilterAccExtId = new StringFilter[1];
			stringFilterAccExtId[0] = stringEquals;

			// //////////Find the Particular Account in Database //////////////

			AccountLocateXIDObjectFilter acctLocFilter = new AccountLocateXIDObjectFilter();
			AccountLocateBean acctLocBean = new AccountLocateBean(this.settings);
			AccountLocateXIDObjectDataList callResponse = null;
			// acctLocBean.find(context, acctLocFilter);

			// Set the Filters for Account External Id Type and Account External
			// Id
			acctLocFilter.setAccountExternalIdFilter(stringFilterAccExtId);
			acctLocFilter.setAccountExternalIdTypeFilter(iExtIdType);
			acctLocFilter.setFetch(true);

			callResponse = acctLocBean.find(this.context, acctLocFilter);
			AccountLocateXIDObjectData[] accountsFound = callResponse
					.getArray();

			/*System.out.println("\n" + new Date() + "Accounts found "
					+ callResponse.getLength());*/
			accountsFound = callResponse.getArray();

			if (callResponse.getLength() > 0) {
				/*System.out.println("server id ::"
						+ accountsFound[0].getServerId());*/
				this.context.setServerId(accountsFound[0].getServerId());

				AccountXIDObjectFilter accountXIDObjectFilter = new AccountXIDObjectFilter();
				accountXIDObjectFilter
						.setAccountExternalIdFilter(stringFilterAccExtId);
				accountXIDObjectFilter
						.setAccountExternalIdTypeFilter(iExtIdType);
				accountXIDObjectFilter.setFetch(true);

				// Create an Account Bean object
				AccountBean accountBean = new AccountBean(this.settings);
				// Create an AccountXIDObject data
				accountXIDObjectData = new AccountXIDObjectData();
				/*System.out.println("*******length "
						+ accountBean
								.find(this.context, accountXIDObjectFilter)
								.getArray().length);*/
				accountXIDObjectData = (accountBean.find(this.context,
						accountXIDObjectFilter).getArray())[0];
			} else {
				accountXIDObjectData = null;
				/*System.out.println("\n" + new Date()
						+ "This account could not be located" + strAccountId);*/
			}
		} catch (Exception e) {
			System.err.println("\n" + new Date()
					+ IOESKenanManager.class.getName()
					+ ":Exception in Locating Account no" + strAccountId
					+ " Exception" + e);
			System.err.println("\n" + new Date()
					+ IOESKenanManager.class.getName() );

			throw new Exception(e);
		}

		return accountXIDObjectData;

	} // End of Method accountFind


/**
 * TODO: rollback in case of failure of externalid addition
 * @param acctDTO
 * @param errorLogMsg 
 * @return
 */
	public AcctDTO createAccount1(AcctDTO acctDTO, String errorLogMsg) {

		
		int acctInternalId = 0;
		int returnStatus = 0;
		errDTO.setErrorCode(0);
		errDTO.setErrorReason("");
		try {
			Integer parentAccountId=null;
			
			//first check whther acc external id exist if yes do not save and set its internal id in bean and set return code as 1
			
			AccountXIDObjectData aod =this.accountFind(acctDTO.getAcctExternalId()); 
			if(aod!=null)
			{
				aod.getAccountInternalId();
				Utility.LOG(true, true,errorLogMsg+" \nAccout Found Internal Id:"+aod.getAccountInternalId());
				acctDTO.setAcctInternalId(aod.getAccountInternalId().toString());
				returnStatus = 1;
				acctDTO.setReturnStatus(returnStatus);
				acctDTO.setFoundActiveDate(aod.getDateActive());
				return acctDTO;
			}
			if(acctDTO.getParentAccExternalId()==null || "".equals(acctDTO.getParentAccExternalId()))
			{
				returnStatus = -1;
				acctDTO.setReturnStatus(returnStatus);
				String problem=(errorLogMsg+" \nParent Account External Id is null for ib2b order no:"+acctDTO.getOrderNo()
									+"\n Row Id="+acctDTO.getId()+"for ib2b order no:"+acctDTO.getOrderNo());
				Exception e=new Exception(problem);
				Utility.LOG(true, true, e, "");
				acctDTO.setException(e);
				acctDTO.setException_message(problem);
				return acctDTO; 
			}
			else
			{
				AccountXIDObjectData aod2 =this.accountFind(acctDTO.getParentAccExternalId()); 
				if(aod2==null)
				{
					returnStatus = -1;
					acctDTO.setReturnStatus(returnStatus);
					String problem=(errorLogMsg+" \nParent Account External Id is not present in FX for ib2b order no:"+acctDTO.getOrderNo()
							+"\n Row Id="+acctDTO.getId()+"for ib2b order no:"+acctDTO.getOrderNo());
					Exception e=new Exception(problem);
					Utility.LOG(true, true, e, "");
					acctDTO.setException(e);
					acctDTO.setException_message(problem);
					return acctDTO; 
				}
				else
				{
					parentAccountId=aod2.getAccountInternalId();
				}
			}


			AccountXIDObjectData axd = new AccountXIDObjectData();
			AccountCreateOutputData acop = null;
			AccountBean ab = new AccountBean(settings);
			
			


			axd.setAccountExternalId(acctDTO.getAcctExternalId());
			
			axd.setBillFname(acctDTO.getFName());
			axd.setBillLname(acctDTO.getLName());
			axd.setBillAddress1(acctDTO.getAddress1());
			axd.setBillAddress2(acctDTO.getAddress2());
			axd.setAccountCategory(acctDTO.getAccountCategory());//41
			axd.setVipCode(new Integer(acctDTO.getVipCode()));
			axd.setAccountType(new Integer(1));
			
			axd.setBillAddress3(acctDTO.getAddress3());
			axd.setBillCity(acctDTO.getBillcity());
			axd.setBillCompany(acctDTO.getBillcompany());
			axd.setBillCountryCode(new Integer(acctDTO.getBillcountrycode()));
			axd.setStatementToFaxno(acctDTO.getStatmentfaxno());
			axd.setBillNamePre(acctDTO.getPrename());
			axd.setBillZip(acctDTO.getBillzip());
			axd.setBillState(acctDTO.getBillstate());
			axd.setParentId(parentAccountId);//70052957//REMOVE COMMENT
			
			axd.setMktCode(new Integer(acctDTO.getMktCode()));//99
			
			axd.setCustCountryCode(acctDTO.getCustCountryCode());
			axd.setBillFmtOpt(acctDTO.getBillFmtOpt());
			axd.setBillDispMeth(acctDTO.getBillDispMeth());
			axd.setDateActive(acctDTO.getDateActive());
			if(customUATDate==1)
			{
				axd.setDateActive(uatDate);
			}
			/*if Legal Entit] is Nxtra Data Limited (11) then  
			 * {acctDTO.getOwningCostCtr() is 115}

		    if 	Bill Format is Bandwidth and Service 	then  Bill Fomrat : NHW
		    	{acctDTO.getBillFmtOpt()  is 101}
		    if 	Bill Format is AES_HW_RENTAL 		then  Bill Fomrat : NHW
		    	{acctDTO.getBillFmtOpt()  is 102}
		    if 	Bill Format is AES_HW SALE 		then Bill Fomrat : NHD
				{acctDTO.getBillFmtOpt()  is 103}*/
			/*axd.setBillPeriod(acctDTO.getBillPeriod());
			if(acctDTO.getOwningCostCtr()==115){
				if(acctDTO.getBillFmtOpt()==101)
					acctDTO.setBillPeriod("NHW");
				else if(acctDTO.getBillFmtOpt()==102) 
					acctDTO.setBillPeriod("NHW");
				else if(acctDTO.getBillFmtOpt()==103) 
					acctDTO.setBillPeriod("NHD");
			}*/
			axd.setBillPeriod(acctDTO.getBillPeriod());
			if(acctDTO.getOwningCostCtr()==115){
				if(acctDTO.getBillFmtOpt()==101)
					axd.setBillPeriod("NHW");
				else if(acctDTO.getBillFmtOpt()==102) 
					axd.setBillPeriod("NHW");
				else if(acctDTO.getBillFmtOpt()==103) 
					axd.setBillPeriod("NHD");
			}
			
			axd.setCurrencyCode(acctDTO.getCurrencyCode());
			axd.setAcctSegId(acctDTO.getSegmentId());
			axd.setOwningCostCtr(acctDTO.getOwningCostCtr());
			
			axd.setContact1Name(acctDTO.getContact1_Name());
			axd.setContact1Phone(acctDTO.getContact1_Phone());
			axd.setStatementToEmail(acctDTO.getStatement_to_email_for_contact1());
			
			axd.setExrateClass((acctDTO.getExrateClass()));
			if(acctDTO.getRateClassDefault()!=null)
			{
				axd.setRateClassDefault((acctDTO.getRateClassDefault()));
			}
			
			if("ON".equals(ApplicationFlags.IB2B_FX_TEST_ACCOUNT_CREATION))
			{
				String csvOrders=Utility.getAppConfigValue(AppConstants.ORDERS_FOR_NO_BILL_ACCOUNTS);
				if(csvOrders!=null)
				{
					String orders[]=csvOrders.split(",");
					for (String string : orders) {
						if(string.equals(String.valueOf(acctDTO.getOrderNo())))
						{
							axd.setNoBill(true);		
							axd.setMktCode(AppConstants.FX_TEST_MARKET_CODE);
						}
					}
					
				}
			}
			
			System.out.println(axd.issetAccountExternalIdType());
			
			
			//Setting extended data
			/*Map apiExtendedData=new HashMap();
			
			ArrayList<ExtendedData> extendedData = acctDTO.getExtendedData();
			for (ExtendedData data : extendedData) {
				apiExtendedData.put(data.getParamId(), data.getParamValue());
			}
			
			axd.extendedData=apiExtendedData;*/
			
			axd.extendedData=Utility.getFxExtendedDataMap(acctDTO.getExtendedData());
			
			
			acop = ab.create(context, axd, null, new Integer(1),
					new Integer(1), new Integer(1), new Integer(1),
					new Integer(1), new Integer(Utility.getAppConfigValue("FX_AES_CUSTOMER_SERVER_CATEGORY")));

			 acctInternalId = acop.getAccount().getAccountInternalId().intValue();
			 acctDTO.setFoundActiveDate(acop.getAccount().getDateActive());
			System.out.println("*****account created"
					+ acctInternalId);
			
			if(acctDTO.getTypeOfOrder()!=null && !"".equals(acctDTO.getTypeOfOrder().trim()))
			{
				NoteObjectData data= new NoteObjectData();
				data.setAccountExternalId(acctDTO.getAcctExternalId());
				data.setAccountExternalIdType(1);
				data.setAccountInternalId(acctInternalId);
				data.setComments(acctDTO.getTypeOfOrder());
				data.setNoteCode("TORD");
				data.setNoteText("TYPE OF ORDER");
				data.setPermanentFlag(true);
				
				NoteBean noteBean =new NoteBean(settings);
				noteBean.create(context,data );
			}
			
//			adding oracle id of customer and other extended attributes
			
			/*String oracleCustomerNo=acctInternalId.toString();
			Integer accountExternalIdTypeForOracleId=4990;*/
			//fetch data in externalIds 
			
			/*ArrayList<AccountExternalIdDto> externalIds = acctDTO.getExternalIds();
			 if(externalIds!=null && externalIds.size()>0)
			{
				AccountIdBean accountIdBean = new AccountIdBean(settings);
				AccountIdObjectData accountIdObjectData = null;
				
				for (AccountExternalIdDto extIdDto : externalIds) {
					accountIdObjectData = new AccountIdObjectData();
					
					accountIdObjectData.setAccountExternalId(extIdDto.getAccountExternalId());
					accountIdObjectData.setAccountExternalIdType(extIdDto.getAccountExternalIdType().intValue());
					accountIdObjectData.setAccountInternalId(acctInternalId);
					accountIdObjectData.setIsCurrent(extIdDto.getIsCurrent());
					
					AccountIdObjectData accountIdObjectDataResponse = accountIdBean.create(context, accountIdObjectData);
				}
			
				
			}*/
			
			//////////////for oracle no as acc external id :START  //REMOVE 
			/*AccountIdBean accountIdBean = new AccountIdBean(settings);
			AccountIdObjectData accountIdObjectData = new AccountIdObjectData();
			
			String oracleCustomerNo=acctInternalId+"";
			Integer accountExternalIdTypeForOracleId=4990;
			
			accountIdObjectData.setAccountExternalId(oracleCustomerNo);
			accountIdObjectData.setAccountExternalIdType(accountExternalIdTypeForOracleId);
			accountIdObjectData.setAccountInternalId(acctInternalId);
			accountIdObjectData.setIsCurrent(true);
			
			AccountIdObjectData accountIdObjectDataResponse = accountIdBean.create(context, accountIdObjectData);*/
//////////////for oracle no as acc external id  : END
			
			//return acc internal id generated through the bean
			acctDTO.setAcctInternalId(acop.getAccount().getAccountInternalId().toString());
			acctDTO.setIsCreatedNow("YES");
			returnStatus = 1;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Utility.LOG(true,true,e,errorLogMsg);
			String message="Exception for Account ExternalIds:"+acctDTO.getExternalIds()+" and Row Id :"+acctDTO.getId();
			acctDTO.setException(e);
			acctDTO.setException_message(message);
			returnStatus = -1;
			errDTO.setErrorReason("Service Exception in method createAccount1");
			
		} 
		errDTO.setErrorReason("Exiting method createAccount1 normally");
		errDTO.setErrorCode(returnStatus);
		
		acctDTO.setReturnStatus(returnStatus);
		
		return acctDTO;
		

	}
	
	
	public boolean updateAccount(BCPAddressChangeDTO objBCPAddressChangeDto) throws Exception
	{
		boolean status = false;
		
		try {
			
			
				AccountXIDObjectData axd = null;		
				
				AccountObjectData aod = new AccountObjectData();
				AccountObjectKeyData aokd = new AccountObjectKeyData();
				aokd.setAccountInternalId(new Integer(objBCPAddressChangeDto.getInternal_id()));
				AccountBean ab = new AccountBean(settings);
				
				Map map = new HashMap();
				
				if(objBCPAddressChangeDto.getNew_designation() == null || objBCPAddressChangeDto.getNew_designation().trim().equalsIgnoreCase("")) {
					
					map.put(1018,"-");
				}else {
					map.put(1018,objBCPAddressChangeDto.getNew_designation());
				}
				
				if(objBCPAddressChangeDto.getNew_rev_circle() == null || objBCPAddressChangeDto.getNew_rev_circle().trim().equalsIgnoreCase("")) {

					
				}else {
					map.put(1135,objBCPAddressChangeDto.getNew_rev_circle());
				}
				
				
				aod.setBillNamePre(objBCPAddressChangeDto.getNew_title());
				
				aod.setBillFname(objBCPAddressChangeDto.getNew_fname());
				aod.setBillMinit(null);
				aod.setBillLname(objBCPAddressChangeDto.getNew_lname());		
				aod.setBillAddress1(objBCPAddressChangeDto.getNew_address1());
				aod.setBillAddress2(objBCPAddressChangeDto.getNew_address2());
				
				//aod.setBillAddress3(objBCPAddressChangeDto.getNew_address3()+""+ objBCPAddressChangeDto.getNew_address4());
				aod.setBillAddress3(objBCPAddressChangeDto.getNew_address3());
				aod.setBillState(objBCPAddressChangeDto.getNew_state_name());
				aod.setBillCountryCode(Integer.parseInt(objBCPAddressChangeDto.getNew_country_code()));
				// Anadi
				aod.setCustCountryCode(Integer.parseInt(objBCPAddressChangeDto.getNew_country_code()));
			// Anadi	
				aod.setBillCity(objBCPAddressChangeDto.getNew_city_name());
				
				
				aod.setBillZip(objBCPAddressChangeDto.getNew_postal_code());
				aod.setContact1Name(objBCPAddressChangeDto.getNew_fname()+" "+ objBCPAddressChangeDto.getNew_lname());
				aod.setContact1Phone(objBCPAddressChangeDto.getNew_telephone_no());
				aod.setStatementToEmail(objBCPAddressChangeDto.getNew_emailid());
				aod.setKey(aokd);
				
				aod.extendedData=map;
				
				axd = ab.update(context,aod);
				
				status = true;
			
	
		}catch (Exception e) {
			
			
			e.printStackTrace();
		}
		
		return status;
	}





public void sendToFxServiceWith_RCs_or_NRCs( ServiceDTO servDTO) {

	
	int returnStatus = 0;
	errDTO.setErrorCode(0);
	errDTO.setErrorReason("");
	try {
		//
		AccountXIDObjectData aod =this.accountFind(servDTO.getAcctExternalId());
		if(aod==null)
		{
			String msg="Cannot create service since account is not present in FX . " +
							"\n  -External Service Id was to be made :"+servDTO.getServExtId()+
							",\n  -Account Not Found was :"+servDTO.getAcctExternalId();
			AppConstants.IOES_LOGGER.info(msg);
			servDTO.setReturnStatus(-1);
			return;
		}
		
		Integer acctInternalId = aod.getAccountInternalId();
		
	
		
		//1.Create Service
		//2.for all RC , create them
		//3.for all NRC ,create them
		
		// **********************************Service Creation**********************************************//
		OrderedServiceBean ordServiceBean = new OrderedServiceBean(settings);
		
		/*******************START**********/
		ServiceObjectData servicedata_new = new ServiceObjectData();
		servicedata_new.setParentAccountInternalId(acctInternalId);
		
		servicedata_new.setEmfConfigId(servDTO.getEmfConfigId());
		servicedata_new.setViewStatus(new Integer(1));
		//servicedata_new.setIntendedViewEffectiveDt(servDTO.getServiceActiveDate());
		servicedata_new.setServiceActiveDt(servDTO.getServiceActiveDate());
		//servicedata_new.setDisplayExternalIdType(servDTO.getServiceExternalIdType());
		servicedata_new.setPrivacyLevel(servDTO.getPrivacyLevel());
		servicedata_new.setRevRcvCostCtr(servDTO.getRevRcvCostCtr());
		
		servicedata_new.setServiceCompany(servDTO.getA_serviceCompany());
		servicedata_new.setServiceFname(servDTO.getA_serviceFname());
		servicedata_new.setServiceMinit(servDTO.getA_serviceMname());
		servicedata_new.setServiceLname(servDTO.getA_serviceLname());
		servicedata_new.setServiceAddress1(servDTO.getA_serviceAddress1());
		servicedata_new.setServiceAddress2(servDTO.getA_serviceAddress2());
		servicedata_new.setServiceAddress3(servDTO.getA_serviceAddress3());
		servicedata_new.setServiceCity(servDTO.getA_serviceCity());
		servicedata_new.setServiceState(servDTO.getA_serviceState());
		servicedata_new.setServiceCountryCode(new Integer(servDTO.getA_serviceCountryCode()));
		servicedata_new.setServiceZip(servDTO.getA_serviceZip());
		
		servicedata_new.setBServiceCompany(servDTO.getB_serviceCompany());
		servicedata_new.setBServiceFname(servDTO.getB_serviceFname());
		servicedata_new.setBServiceMinit(servDTO.getB_serviceMname());
		servicedata_new.setBServiceLname(servDTO.getB_serviceLname());
		servicedata_new.setBServiceAddress1(servDTO.getB_serviceAddress1());
		servicedata_new.setBServiceAddress2(servDTO.getB_serviceAddress2());
		servicedata_new.setBServiceAddress3(servDTO.getB_serviceAddress3());
		servicedata_new.setBServiceCity(servDTO.getB_serviceCity());
		servicedata_new.setBServiceCountryCode(new Integer(servDTO.getB_serviceCountryCode()));
		servicedata_new.setBServiceZip(servDTO.getB_serviceZip());
		
		servicedata_new.extendedData=Utility.getFxExtendedDataMap(servDTO.getExtendedData());
		
		
		//servicedata_new.setCurrencyCode(servDTO.getCurrencyCode());	
		//servicedata_new.setRateClass(servDTO.getRateClass());	
		
		//setting other fileds
		/*servicedata_new.setTimezone(servDTO.getTimezone());
		servicedata_new.setServiceLname(servDTO.getServiceLName());
		servicedata_new.setServiceFname(servDTO.getServiceFName());
		servicedata_new.setServiceNamePre(servDTO.getServiceNamePre());
		servicedata_new.setExrateClass(servDTO.getExRateClass());*/
		
		
		/*servicedata_new.setServiceAddress1("serv Addr1");
		servicedata_new.setServiceAddress2("serv Addr2");
		servicedata_new.setServiceZip("110034");
		servicedata_new.setServiceState("Harayana");
		servicedata_new.setServiceCity("Gurugaon");
		servicedata_new.setServiceCompany("IBM-A addr");
		servicedata_new.setServiceCountryCode(356);
		servicedata_new.setServiceFranchiseTaxCode(1);*/
		
		
		/*servicedata_new.setBServiceAddress1("B addr");
		servicedata_new.setBServiceCountryCode(356);
		servicedata_new.setBServiceFranchiseTaxCode(1);
		servicedata_new.setBRevRcvCostCtr(servDTO.getRevRcvCostCtr_B());*/
		
		//servicedata_new.setServiceExternalId(ext_ServiceId);
		/*******************END**********/
		// create order for this si identifier

		OrderedServiceCreateOutputData orderedServiceCreateOutputData= ordServiceBean
											.create(context, servicedata_new,null,null,true);
		
		OrderObjectData order = orderedServiceCreateOutputData.getOrder();
		
		ServiceObjectBaseData serviceResponse=orderedServiceCreateOutputData.getService();
		
		
		int subscr_no = serviceResponse.getServiceInternalId().intValue();
		int subscr_no_resets = serviceResponse.getServiceInternalIdResets().intValue();
		
		System.out.println("*****subscr_no" + subscr_no);
		System.out.println("*****subscr_no_resets" + subscr_no_resets);
		
		
//		START:creating externalId********************************************************
//		 Initialize and configure a Service-level external ID
		//CustomerIdEquipMapBean mapBean=new CustomerIdEquipMapBean(api.getSettings());
		/*OrderedCiemBean ordCiemBean =new OrderedCiemBean(settings);
		CustomerIdEquipMapObjectData serviceIdData =new CustomerIdEquipMapObjectData();
		
		serviceIdData.setServiceInternalId(serviceResponse.getServiceInternalId());
		serviceIdData.setServiceInternalIdResets(serviceResponse.getServiceInternalIdResets());
		ServiceObjectBaseKeyData serviceKey = serviceResponse.getKey();
		serviceIdData.setViewId(serviceKey.getViewId());
		serviceIdData.setActiveDate(servDTO.getServiceActiveDate());
//			 Use current date & time as external ID (not what you would do
//			 in the real world, but produces a unique value)
		serviceIdData.setServiceExternalId(servDTO.getServExtId()+System.currentTimeMillis()/1000);
		serviceIdData.setServiceExternalIdType(servDTO.getServiceExternalIdType());
		serviceIdData.setIsCurrent(true);
		serviceIdData.setViewStatus(1);
//			 Crate the Service-level external ID in the database
		
		OrderedCiemCreateOutputData serviceIdResponse =ordCiemBean.create(
									context, // BSDMSessionContext
									serviceIdData, // CustomerIdEquipMap to be created
									null, // Don't specify an Order
									null, // Don't specify a ServiceOrder
									new Boolean(true), // Look for an existing ServiceOrder
									new Boolean(true)); // Return order objects?
		
		AppConstants.IOES_LOGGER.info(
				"Created a CustomerIdEquipMap with ServiceExternalId: "
				+ serviceIdResponse.getCustomerIdEquipMap().getServiceExternalId());*/
		
		
//		END:creating externalId********************************************************
		
		//*****************Adding External Ids
		ArrayList<FxExternalIdDto> externalIds = servDTO.getExternalIds();
		 if(externalIds!=null && externalIds.size()>0)
		{
			AccountIdBean accountIdBean = new AccountIdBean(settings);
			AccountIdObjectData accountIdObjectData = null;
			
			OrderedCiemBean ordCiemBean =new OrderedCiemBean(settings);
			
			for (FxExternalIdDto extIdDto : externalIds) {
				
				CustomerIdEquipMapObjectData serviceIdData =new CustomerIdEquipMapObjectData();
				
				serviceIdData.setServiceInternalId(serviceResponse.getServiceInternalId());
				serviceIdData.setServiceInternalIdResets(serviceResponse.getServiceInternalIdResets());
				ServiceObjectBaseKeyData serviceKey = serviceResponse.getKey();
				serviceIdData.setViewId(serviceKey.getViewId());
				serviceIdData.setActiveDate(servDTO.getServiceActiveDate());
//					 Use current date & time as external ID (not what you would do
//					 in the real world, but produces a unique value)
				serviceIdData.setServiceExternalId(extIdDto.getExternalId());
				serviceIdData.setServiceExternalIdType(new Integer(extIdDto.getExternalIdType()));
				serviceIdData.setIsCurrent(true);
				serviceIdData.setViewStatus(1);
//					 Crate the Service-level external ID in the database
				
				OrderedCiemCreateOutputData serviceIdResponse =ordCiemBean.create(
											context, // BSDMSessionContext
											serviceIdData, // CustomerIdEquipMap to be created
											null, // Don't specify an Order
											null, // Don't specify a ServiceOrder
											new Boolean(true), // Look for an existing ServiceOrder
											new Boolean(true)); // Return order objects?
				
				AppConstants.IOES_LOGGER.info(
						"Created a CustomerIdEquipMap with ServiceExternalId: "
						+ serviceIdResponse.getCustomerIdEquipMap().getServiceExternalId());
			}
		
			
		}
		
		//******************End : Added External Ids
		
		OrderObjectKeyData orderKey = order.getKey();
		OrderBean orderBean = new OrderBean(settings);
		orderBean.commit(context, orderKey);
		
		servDTO.setSubscrNo(subscr_no);
		servDTO.setSubscrNoReset(subscr_no_resets);

		//Create rcs 
		ArrayList<RcDto> rcs=servDTO.getRcs();
		if(rcs!=null && rcs.size()>0)
		{
			for (RcDto rc : rcs) {
				try
				{
					ProductObjectData productObjectData = sendRcToFx(serviceResponse,rc,2,null,aod);
					rc.setViewId(productObjectData.getViewId().toString());
					//rc.setTrackingId(productObjectData.getTrackingId());
					//rc.setTrackingIdServ(productObjectData.getTrackingIdServ());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		
		//Create nrcs
		ArrayList<NrcDto> nrcs=servDTO.getNrcs();
		if(nrcs!=null && nrcs.size()>0)
		{
			for (NrcDto nrc : nrcs) {
				try
				{
					NrcObjectData nrcObjectData = sendNrcToFx(serviceResponse,nrc,2,null,aod);
					nrc.setViewId(nrcObjectData.getViewId().toString());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		
		/*order.setGenerateWorkflow(false);
		OrderObjectKeyData orderKey = order.getKey();
		System.out.println("Order ID FOR the service Id:"+servDTO.getServiceExternalId()+" and charges is :"+orderKey.getOrderId());*/
		
		
//		 Commit the Order
		
		/*objOrderBean.commit(context, order.getKey());
		AppConstants.IOES_LOGGER.info("order commited");*/
		returnStatus = 1;

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		returnStatus = -1;

		/*if(order!=null && objOrderBean!=null)
		{
			//OrderObjectKeyData orderKey = order.getKey();
			//OrderBean orderBean = new OrderBean(settings);
			try {
				
				//objOrderBean.cancel(context, order.getKey());
				AppConstants.IOES_LOGGER.info("Order Cancelled for Service Id:"+servDTO.getServExtId()+" at :"+new Date());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}*/
		
	}
	servDTO.setReturnStatus(returnStatus);
	errDTO.setErrorCode(returnStatus);
	

}

public void sh_sendToFxServiceWith_RCs_or_NRCs(Connection conn, ServiceDTO servDTO) {

}

/**
 * 
 * @param serviceResponse
 * @param nrcDto
 * @param order
 * @param level : 1 for account level product and 2 for service level product 
 * @param accountXIDObjectData
 * @return
 * @throws Exception
 */
	private NrcObjectData sendNrcToFx(ServiceObjectBaseData serviceResponse, NrcDto nrcDto, int level, AccountXIDObjectData accountXIDObjectData, AccountXIDObjectData aod) 
																								throws Exception{
		
		
		try
		{
		OrderObjectData order=null;
		
		OrderBean objOrderBean=null;
		
		objOrderBean = new OrderBean(settings);
		// create order
		order = new OrderObjectData();
		
		Integer forOrder_accountInternalId =null;
		if(level==1)
		{
			forOrder_accountInternalId=accountXIDObjectData.getAccountInternalId();
		}
		else if(level==2)
		{
			forOrder_accountInternalId=serviceResponse.getParentAccountInternalId();
		}
		order.setAccountInternalId(forOrder_accountInternalId);
		
		 
		
		order.setOrderStatusId(new Integer("10"));
		order.setGenerateWorkflow(new Boolean(false));
		
		order = objOrderBean.create(context,order);
		order.setAccountInternalId(forOrder_accountInternalId);
		
		
		NrcObjectData nrcFx = new NrcObjectData();
		boolean skipRecord = false;
		
				  
		nrcFx.setTypeIdNrc(nrcDto.getTypeidNrc());
//		Set default values
		nrcFx.setCurrencyCode(aod.getCurrencyCode());
		nrcFx.setEffectiveDate(nrcDto.getEffectiveDate());
		//nrcFx.setAnnotation(nrcDto.getAnnotation());
		nrcFx.setRate(new BigInteger(nrcDto.getNrcAmount()));
		nrcFx.setRateDt(nrcDto.getEffectiveDate());
		nrcFx.setNoBill(new Boolean(false));
		
		//nrcFx.setOrderNumber("AES-Order");
		//nrcFx.setAnnotation("Annotation : AES-NRC");
		
		if(level==1)
		{
			nrcFx.setBillingAccountInternalId(accountXIDObjectData.getAccountInternalId());
		}
		else if(level==2)
		{
			nrcFx.setBillingAccountInternalId(serviceResponse.getParentAccountInternalId());
			nrcFx.setParentServiceInternalId(serviceResponse.getServiceInternalId());
			nrcFx.setParentServiceInternalIdResets(serviceResponse.getServiceInternalIdResets());	
		} 
		
		
		//set extended attr
		nrcFx.extendedData=Utility.getFxExtendedDataMap(nrcDto.getExtendedData());
		//Map extendedData = new HashMap();
		//extendedData.put(4993001, "123");//RA Number//String
		//extendedData.put(4993002, new Date(System.currentTimeMillis()));//PO Date//date/time, in the format YYYY-MM-DD HHMMSS 
		//nrcFx.extendedData=extendedData;
		OrderedNrcBean ordNrcBean = new OrderedNrcBean(settings);

		// Create the Nrc in the database and store the returned
		// NRC object in nrcResponse
		
		OrderedNrcCreateOutputData nrcResponse = null;
		if(level==1)
		{
			
			/*Map extendedData = new HashMap();
			extendedData.put(1082, "0");//RA Number//String
			extendedData.put(1083, new Date());//Annual Rate//4 byte integer
			extendedData.put(1084, "Part Serial Number");//Distance//4 byte integer
			extendedData.put(1085, "Logical Ckt ID");//Distance//4 byte integer
			extendedData.put(1086, "Part Description");//Distance//4 byte integer
			extendedData.put(1087, "Part No (Oracle");//Distance//4 byte integer
			extendedData.put(1088, "Delivery Challan No");//Distance//4 byte integer
			extendedData.put(1089, new Date());//Distance//4 byte integer
			extendedData.put(1090, "Dispatch Address To");//Distance//4 byte integer
			extendedData.put(1091, "Dispatch_Address_Line1");//Distance//4 byte integer
			extendedData.put(1092, "Dispatch_Address_Line2");//Distance//4 byte integer
			extendedData.put(1093, "Dispatch_Address_Line3");//Distance//4 byte integer
			extendedData.put(1094, "Dispatch_Address_City");//Distance//4 byte integer
			extendedData.put(1095, "Dispatch_Address_State");//Distance//4 byte integer
			extendedData.put(1096, "Dispatch_Address_Pin");//Distance//4 byte integer
			nrcFx.extendedData=extendedData;*/
			nrcResponse =ordNrcBean.create(this.context, nrcFx, // Nrc to be created
					order, // Don't specify an Order
					null, // Don't specify a ServiceOrder
					new Boolean(true), // Look for an existing ServiceOrder
					new Boolean(true)); // Return order objects?
		}
		else if(level==2)
		{
			nrcResponse =ordNrcBean.create(this.context, nrcFx, // Nrc to be created
					order, // Don't specify an Order
					null, // Don't specify a ServiceOrder
					new Boolean(true), // Look for an existing ServiceOrder
					new Boolean(true)); // Return order objects?	
		}
		
		
		
		OrderObjectKeyData orderKey = order.getKey();
		System.out.println("Order ID FOR THE NRC"+orderKey.getOrderId());
//		 Commit the Order
		OrderBean orderBean = new OrderBean(settings);
		orderBean.commit(context, orderKey);
		
		
		
		return nrcResponse.getNrc()[0];
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		return new NrcObjectData();
				
	}
/**
 * 
 * @param service
 * @param rc
 * @param order
 * @param level :1 for Account level , 2 for service level 
 * @param accountXIDObjectData
 * @return
 * @throws Exception
 */
	private ProductObjectData sendRcToFx(ServiceObjectBaseData service, RcDto rc, int level, AccountXIDObjectData accountXIDObjectData,AccountXIDObjectData aod) throws Exception
		{
try
{
		
			OrderObjectData order=null;
			
			OrderBean objOrderBean=null;
			
			objOrderBean = new OrderBean(settings);
			// create order
			order = new OrderObjectData();
			
			Integer forOrder_accountInternalId =null;
			if(level==1)
			{
				forOrder_accountInternalId=accountXIDObjectData.getAccountInternalId();
			}
			else if(level==2)
			{
				forOrder_accountInternalId=service.getParentAccountInternalId();
			}
			order.setAccountInternalId(forOrder_accountInternalId);
			order.setOrderStatusId(new Integer("10"));
			order.setGenerateWorkflow(new Boolean(false));
			order = objOrderBean.create(context,order);
			order.setAccountInternalId(forOrder_accountInternalId);
		
//			 Initialize an OrderedProductBean
			OrderedProductBean ordProductBean = new OrderedProductBean(settings);
			
//			 Initialize a ProductObjectData object
//			 to contain the Product's info
			ProductObjectData product = new ProductObjectData();
			
			/*
			* associate the product with the Service.
			*/
			
			OrderedProductCreateOutputData productResponse = null;
			
			if(level==1)
			{
				product.setBillingAccountInternalId(accountXIDObjectData.getAccountInternalId());
				product.setParentAccountInternalId(accountXIDObjectData.getAccountInternalId());
			}
			else if(level==2)
			{
				product.setParentServiceInternalId(service.getServiceInternalId());
				product.setParentServiceInternalIdResets(service.getServiceInternalIdResets());
				product.setBillingAccountInternalId(service.getParentAccountInternalId());
				product.setParentAccountInternalId(service.getParentAccountInternalId());
			}
			
				
				//Integer elementId=new Integer("50107014");
				product.setElementId(rc.getElementId());
				product.setViewStatus(new Integer(1));
				//product.setOpenItemId(rc.getOpenItemId());//1
				//product.setIntendedViewEffectiveDt(rc.getBillingActiveDate());
				product.setProductActiveDt(rc.getBillingActiveDate());
				product.setProductInactiveDt(rc.getBillingEndDate());
				
				product.setBillingActiveDt(rc.getBillingActiveDate());
				product.setBillingInactiveDt(rc.getBillingEndDate());
				product.setIsPartOfComponent(false);
				product.setInArrearsOverride(rc.getInArrearsOverride());
				product.setBillPeriod(rc.getBillPeriod());
				
				//product.setOrderNumber(rc.getOrderNumber());
				product.setOrderNumber("AES-Order");
				
				
				product.extendedData=Utility.getFxExtendedDataMap(rc.getExtendedData());
				
				//START : REMOVE   : eatended param for 10.13.41.90
				//Map extendedData = new HashMap();
				//extendedData.put(4992002, "123");//RA Number//String
				//extendedData.put(4992001, new Integer(890));//Annual Rate//4 byte integer
				//extendedData.put(4992003, new Integer(300));//Distance//4 byte integer
				//extendedData.put(4992004, new Date(System.currentTimeMillis())/*"2010-10-25 000000"*/);//PO Date//date/time, in the format YYYY-MM-DD HHMMSS 
				//product.extendedData=extendedData;
				//END
				
				
//				 Insert the Product into the database and store
//				 the server's response in productResponse
				
				if(level==1)
				{
					/*Map extendedData = new HashMap();
					extendedData.put(1082, "0");//RA Number//String
					extendedData.put(1083, new Date());//Annual Rate//4 byte integer
					extendedData.put(1084, "Part Serial Number");//Distance//4 byte integer
					extendedData.put(1085, "Logical Ckt ID");//Distance//4 byte integer
					extendedData.put(1086, "Part Description");//Distance//4 byte integer
					extendedData.put(1087, "Part No (Oracle");//Distance//4 byte integer
					extendedData.put(1088, "Delivery Challan No");//Distance//4 byte integer
					extendedData.put(1089, new Date());//Distance//4 byte integer
					extendedData.put(1090, "Dispatch Address To");//Distance//4 byte integer
					extendedData.put(1091, "Dispatch_Address_Line1");//Distance//4 byte integer
					extendedData.put(1092, "Dispatch_Address_Line2");//Distance//4 byte integer
					extendedData.put(1093, "Dispatch_Address_Line3");//Distance//4 byte integer
					extendedData.put(1094, "Dispatch_Address_City");//Distance//4 byte integer
					extendedData.put(1095, "Dispatch_Address_State");//Distance//4 byte integer
					extendedData.put(1096, "Dispatch_Address_Pin");//Distance//4 byte integer
					 
					product.extendedData=extendedData;*/
					productResponse = ordProductBean.create(
							context, // BSDMSessionContext
							product, // Product to be created
							order, //  an Order
							null, // Don't specify a ServiceOrder
							new Boolean(true), // Look for an existing ServiceOrder
							new Boolean(true)); // Return order objects?
				}
				else if(level==2)
				{
					productResponse = ordProductBean.create(
							context, // BSDMSessionContext
							product, // Product to be created
							order, //  an Order
							null, // Don't specify a ServiceOrder
							new Boolean(true), // Look for an existing ServiceOrder
							new Boolean(true)); // Return order objects?
				}
				
				System.out.println("Created Product with ViewId: "+ productResponse.getProduct().getViewId());
				
				
				
//				 No need to create an Item for the Product;
//				 it's created by the OrderedProductCreate method
//				 Return the newly created Product
				
				
				ProductRateOverrideBean productRateOverrideBean=new ProductRateOverrideBean(settings);
				ProductRateOverrideObjectData productRateOverrideObjectData= new ProductRateOverrideObjectData();
				
				BigInteger iRate = new BigInteger(rc.getOverrideRate());
				
				
				productRateOverrideObjectData.setCurrencyCode(aod.getCurrencyCode());
				productRateOverrideObjectData.setActiveDt(rc.getBillingActiveDate());
				productRateOverrideObjectData.setInactiveDt(rc.getBillingEndDate());
				productRateOverrideObjectData.setTrackingId(productResponse.getProduct().getTrackingId());
				productRateOverrideObjectData.setTrackingIdServ(productResponse.getProduct().getTrackingIdServ());
				productRateOverrideObjectData.setOverrideRate(iRate);
				ProductRateOverrideObjectData overrideBean= productRateOverrideBean.create (context, productRateOverrideObjectData);
				System.out.println("Product Rate Overrided");
				
				
				
				OrderObjectKeyData orderKey = order.getKey();
				System.out.println("Order ID FOR THE PRODUCT"+orderKey.getOrderId());
//				 Commit the Order
				OrderBean orderBean = new OrderBean(settings);
				orderBean.commit(context, orderKey);
				
				
				
				
				return productResponse.getProduct();
				
}
catch(Exception ex)
{
	ex.printStackTrace();
}
return new ProductObjectData();
			
		}

	public void sendToFxAccount_Level_RCs_or_NRCs(ChargesDto chargesDto) {

		
		int returnStatus = 0;
		errDTO.setErrorCode(0);
		errDTO.setErrorReason("");
		OrderObjectData order = null;
		OrderBean objOrderBean=null;
		try {
			//
			AccountXIDObjectData aod =this.accountFind(chargesDto.getAccountExternalId());
			if(aod==null)
			{
				String msg="Cannot create service since account is not present in FX . " +
								"\n  -External Service Id was to be made :"+chargesDto.getAccountExternalId()+
								",\n  -Account Not Found was :"+chargesDto.getAccountExternalId();
				Utility.LOG(true, true, msg);
				chargesDto.setReturnStatus(-1);
				return;
			}
			
			Integer acctInternalId = aod.getAccountInternalId();
			
			objOrderBean = new OrderBean(settings);
			// create order
			order = new OrderObjectData();
			
			order.setAccountInternalId(acctInternalId);
			order.setOrderStatusId(new Integer("10"));
			order.setGenerateWorkflow(new Boolean(false));
			
			order = objOrderBean.create(context,order);
			order.setAccountInternalId(order.getAccountInternalId());
			Utility.LOG(true, true, ("orderid "+order.getOrderId()));
			//order=null;
			Utility.LOG(true, true, ("ORDER CREATED : Now adding objects in order"));
			
			
			//1.for all RC , create them
			//2.for all NRC ,create them
			
			//Create rcs 
			AccountXIDObjectData accountXIDObjectData=new AccountXIDObjectData();
			accountXIDObjectData.setAccountInternalId(acctInternalId);			
			
			//**********************************************
			// code added by sandeep only for UAT purpose on 27 nov 2010 
			// step 1. first create service and then rc and nrc with that
			//*********************************************
			
			/*Date d=null;
			ArrayList<RcDto> rcs2=chargesDto.getRcs();
			if(rcs2!=null && rcs2.size()>0)
			{
				for (RcDto rc : rcs2) {
					d=rc.getProductActiveDate();
				}
			}
			

			ArrayList<NrcDto> nrcs2=chargesDto.getNrcs();
			if(nrcs2!=null && nrcs2.size()>0)
			{
				for (NrcDto nrc : nrcs2) {
					if(d==null)
					{
						d=nrc.getEffectiveDate();
					}
				}
			}*/
			
			
			
//			 **********************************Service
			// Creation**********************************************//

			
			/*OrderedServiceBean ordServiceBean = new OrderedServiceBean(settings);*/
			
			
			/*******************START**********/
			/*ServiceObjectData servicedata_new = new ServiceObjectData();  // dto
			
			servicedata_new.setEmfConfigId(new Integer(5011));
			servicedata_new.setViewStatus(new Integer(1));
			servicedata_new.setIntendedViewEffectiveDt(d);
			servicedata_new.setServiceActiveDt(d);
			servicedata_new.setDisplayExternalIdType(new Integer(4994));
			servicedata_new.setPrivacyLevel(new Integer(0));
			servicedata_new.setCurrencyCode(1);	
			servicedata_new.setRateClass(1);	
			servicedata_new.setParentAccountInternalId(acctInternalId);
			//setting other fileds
			servicedata_new.setTimezone(1);
			servicedata_new.setServiceLname("s lname");
			servicedata_new.setServiceFname("s fname");
			servicedata_new.setServiceNamePre("pre");
			servicedata_new.setExrateClass(1);
			
			

			
			servicedata_new.setServiceAddress1("serv Addr1");
			servicedata_new.setServiceAddress2("serv Addr2");
			servicedata_new.setServiceZip("110034");
			servicedata_new.setServiceState("Harayana");
			servicedata_new.setServiceCity("Gurugaon");
			servicedata_new.setServiceCompany("IBM-A addr");
			servicedata_new.setServiceCountryCode(356);
			servicedata_new.setServiceFranchiseTaxCode(1);
			servicedata_new.setRevRcvCostCtr(new Integer(4990));
			
			servicedata_new.setBServiceAddress1("B addr");
			servicedata_new.setBServiceCountryCode(356);
			servicedata_new.setBServiceFranchiseTaxCode(1);
			servicedata_new.setBRevRcvCostCtr(new Integer(4990));*/
			
			
			/*******************END**********/

			/*OrderedServiceCreateOutputData orderedServiceCreateOutputData= ordServiceBean
			.create(context, servicedata_new,null,order,true);
			
			order = orderedServiceCreateOutputData.getOrder();
			
			ServiceObjectBaseData serviceResponse=orderedServiceCreateOutputData.getService();
			
			int subscr_no = serviceResponse.getServiceInternalId().intValue();
			int subscr_no_resets = serviceResponse.getServiceInternalIdResets().intValue();
			
			System.out.println("*****subscr_no" + subscr_no);
			System.out.println("*****subscr_no_resets" + subscr_no_resets);*/
			
			
//			START:creating externalId********************************************************
//			 Initialize and configure a Service-level external ID
			//CustomerIdEquipMapBean mapBean=new CustomerIdEquipMapBean(api.getSettings());
			/*OrderedCiemBean ordCiemBean =new OrderedCiemBean(settings);
			CustomerIdEquipMapObjectData serviceIdData =new CustomerIdEquipMapObjectData();
			
			serviceIdData.setServiceInternalId(serviceResponse.getServiceInternalId());
			serviceIdData.setServiceInternalIdResets(serviceResponse.getServiceInternalIdResets());
			ServiceObjectBaseKeyData serviceKey = serviceResponse.getKey();
			serviceIdData.setViewId(serviceKey.getViewId());
			serviceIdData.setActiveDate(d);
//				 Use current date & time as external ID (not what you would do
//				 in the real world, but produces a unique value)
			serviceIdData.setServiceExternalId(""+serviceResponse.getServiceInternalId());
			serviceIdData.setServiceExternalIdType(new Integer(4994));
			serviceIdData.setIsCurrent(true);
			serviceIdData.setViewStatus(1);
//				 Crate the Service-level external ID in the database
			
			OrderedCiemCreateOutputData serviceIdResponse =ordCiemBean.create(
										context, // BSDMSessionContext
										serviceIdData, // CustomerIdEquipMap to be created
										null, // Don't specify an Order
										null, // Don't specify a ServiceOrder
										new Boolean(true), // Look for an existing ServiceOrder
										new Boolean(true)); // Return order objects
			
			AppConstants.IOES_LOGGER.info(
					"Created a CustomerIdEquipMap with ServiceExternalId: "
					+ serviceIdResponse.getCustomerIdEquipMap().getServiceExternalId());*/
			
			
//			END:creating externalId********************************************************

			
			
			
			//*****************End of the code************* 
			ArrayList<RcDto> rcs=chargesDto.getRcs();
			if(rcs!=null && rcs.size()>0)
			{
				for (RcDto rc : rcs) {
					try
					{
						ProductObjectData productObjectData = sendRcToFx(null,rc,1,accountXIDObjectData,aod);
						rc.setViewId(productObjectData.getViewId().toString());
						//rc.setTrackingIdServ(productObjectData.getTrackingIdServ());
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			
			//Create nrcs
			ArrayList<NrcDto> nrcs=chargesDto.getNrcs();
			if(nrcs!=null && nrcs.size()>0)
			{
				for (NrcDto nrc : nrcs) {
					try
					{
						NrcObjectData nrcObjectData = sendNrcToFx(null,nrc,1,accountXIDObjectData,aod);//REMOVE
						nrc.setViewId(nrcObjectData.getViewId().toString());//REMOVE
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			
			/*order.setGenerateWorkflow(false);
			OrderObjectKeyData orderKey = order.getKey();
			System.out.println("Order ID FOR the service Id:"+servDTO.getServiceExternalId()+" and charges is :"+orderKey.getOrderId());*/
			
			
//			 Commit the Order
			
			objOrderBean.commit(context, order.getKey());
			Utility.LOG(true, true, ("ORDER Committed , "+"orderid "+order.getOrderId()));
			
			returnStatus = 1;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnStatus = -1;

			if(order!=null && objOrderBean!=null)
			{
				//OrderObjectKeyData orderKey = order.getKey();
				//OrderBean orderBean = new OrderBean(settings);
				try {
					
					objOrderBean.cancel(context, order.getKey());
					Utility.LOG(true, true, "Order("+"orderid "+order.getOrderId()+") Cancelled for Service Product Id:"+chargesDto.getServiceProductId()+" at :"+new Date());
				} catch (BSDMResourceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		chargesDto.setReturnStatus(returnStatus);
		errDTO.setErrorCode(returnStatus);
		

	}
	
	/*
	 * status 
	 *  10 = product found	 *  
	 *  30 = product not found
	 *  40 = product not disconnected
	 *  20 = product disconnected
	 *  
	 *  	 */
	
	private final int PRODUCT_FOUND = 10;
	private final int PRODUCT_NOT_FOUND = 30;
	
	
	public RcDto findProduct(String viewID) {
		
			System.out.println("IOESKenanManager.findProduct()" +"\n"+
					"RC View Id to be disconnected  is :: "+viewID);
			RcDto rcDto=new RcDto();
			int status = 0;
			hmKey			=	new HashMap();
			hmProduct		=	new HashMap();
			hmResponse		=	new HashMap();
			hmViewid 		= 	new HashMap();
			hmRequest       =   new HashMap();  
			try{
				
				// set view id to search
				hmViewid.put(EQUAL,new BigInteger(viewID));
				
				// set key
				hmKey.put(FETCH, new Boolean(true));
				hmKey.put(VIEWID,hmViewid);			
				
				hmProduct.put("Fetch", new Boolean(true));
				hmProduct.put(KEY,hmKey);			
							
				callName = ApiMappings.getCallName(PRODUCTFIND);			
				//hmRequest.clear();
				
				hmRequest.put("VerboseResponse", new Boolean(true));
				hmRequest.put(PRODUCT,hmProduct);
				
				
				BufferedWriter bw = new BufferedWriter(new
				PrintWriter(System.out));
				  
				ServiceException.printMap(bw, hmRequest, 0);
				bw.flush();
				
				hmResponse = fxApiConnection.call(context, callName, hmRequest); 
				
				System.out.println("ApI call exuted");
				
				 System.out.println("Total no of products find"+ hmResponse.get("Count"));
				if("1".equals(hmResponse.get("Count").toString()))
				{
					 status = PRODUCT_FOUND;/*product foud*/
					//status = disconnectProduct(hmResponse);
					/*if(status == 20)product disconnected{
						System.out.println("product successfully disconnected");
						 
					}*/
					 Set s=hmResponse.keySet();
					 Object[] rr=(Object[])hmResponse.get("ProductList");
					 Integer accountInternalId=(Integer)((Map)(rr[0])).get("BillingAccountInternalId");
					 //ProductObjectBaseDataList dataList=(ProductObjectBaseDataList)hmResponse.get("ProductList");
					 //Integer accountInternalId=dataList.getArray()[0].getBillingAccountInternalId();
					 //Integer accountInternalId=null;
					 rcDto.setAccountInternalId(accountInternalId);
					 
					
				}else 
				{
					status = PRODUCT_NOT_FOUND;/*product not found*/
				}
				ServiceException.printMap(bw,hmResponse, 0);
				 bw.flush();
				 
				
			}catch (Exception e) {
				status = PRODUCT_NOT_FOUND;/*product not found*/
			e.printStackTrace();		
		}	
			rcDto.setSaveStatus(status);
		 return rcDto;
	}
	
	/*
	 * status 
	 * 20 = product disconencted
	 * 40 = product failed to disconnect
	 * 
	 */
	/*public int  disconnectProduct(String viewId,Date inactiveDate) {
		
		int status = 0;
		System.out.println("IOESKenanManager.disconnectProduct()");
		
		
		hmResponse		=	new HashMap();
		hmViewid 		= 	new HashMap();
		
		try{
			
								
			callName = ApiMappings.getCallName("OrderedProductDisconnect");			
			hmRequest.clear();
			
			hmRequest.put("VerboseResponse", new Boolean(true));
			hmRequest.put(PRODUCT,hmProduct);			
			hmRequest.put(INACTIVEDATE,new Date());
			
			BufferedWriter bw = new BufferedWriter(new
			PrintWriter(System.out));
			  
			ServiceException.printMap(bw, hmRequest, 0);
			bw.flush();
			
			hmResponse = fxApiConnection.call(context, callName, hmRequest); 
			
			System.out.println("API call executed");
						
			ServiceException.printMap(bw,hmResponse, 0);
			bw.flush();
			status  = 20;
			
		}catch (Exception e) {
			status = 40;
			e.printStackTrace();		
	}	
	 return status;
	}*/
	
	/*
	 * status 
	 * 20 = product disconencted
	 * 40 = product failed to disconnect
	 * 
	 */
	
	private final int PRODUCT_DISCONNECTED = 20;
	private final int PRODUCT_NOT_DISCONNECTED = 40;
	
public int disconnectProduct(String viewId,Date inactiveDate,RcDto rcDto,DisconnectionDto objDisconnectionDto ,ArrayList<ExtendedData> orderExtendedData) {
		
		OrderedProductDisconnectOutputData  productResponse = null;
		ProductObjectData productDisconnect = new ProductObjectData();
		int status = 0;
		OrderObjectData order=null;
		OrderBean objOrderBean=null;
		String message;
		
		
		
		objOrderBean = new OrderBean(settings);
		// create order
		order = new OrderObjectData();
		//Integer accountInternalId=70149378;
		
		try {
			order.setAccountInternalId(rcDto.getAccountInternalId());
			order.setOrderStatusId(new Integer("10"));
			order.setGenerateWorkflow(new Boolean(false));
			order.extendedData=Utility.getFxExtendedDataMap(orderExtendedData);
			order = objOrderBean.create(context,order);
			order.setAccountInternalId(rcDto.getAccountInternalId());
			
			
			
			OrderedProductBean ordProductBean = new OrderedProductBean(settings);		
			productDisconnect.setViewId(new BigInteger(viewId));		 
			productResponse = ordProductBean.disconnect(
				context, // BSDMSessionContext
				productDisconnect, // Product to be created
			order, // Don't specify an Order
			null,
			inactiveDate ,// Don't specify a ServiceOrder
			new Boolean(false), // Look for an existing ServiceOrder
			new Boolean(false)); // Return order objects?
			if(productResponse !=null) {
				
				status  = PRODUCT_DISCONNECTED;
				System.out.println(
						"Disconnect  Product with TrackingId: "
						+ productResponse.getProduct().getTrackingId());
				System.out.println(
						"Disconnect Product with View Id:  "
						+ productResponse.getProduct().getViewId());
				System.out.println(
						"Inactivate Date:  "
						+ productResponse.getProduct().getProductInactiveDt());
				
			}
		

			//OrderObjectData order = productResponse.getOrder();
			//order.setGenerateWorkflow(false);
			OrderObjectKeyData orderKey = order.getKey();
			rcDto.setToken_id(orderKey.getOrderId().toString());
			System.out.println("Order ID FOR THE PRODUCT"+orderKey.getOrderId());
	//							 Commit the Order
			//OrderBean orderBean = new OrderBean(settings);
			objOrderBean.commit(context, orderKey);
		
	}catch (Exception e)
	{
		try {
			objOrderBean.cancel(order.getKey());
		} catch (BSDMResourceException e1) {
			// TODO Auto-generated catch block
			objDisconnectionDto.setException(e);
			message="Exception for Index_key :"+objDisconnectionDto.getIndex_key()+"and For Row ID  :"+ objDisconnectionDto.getView_id();
			objDisconnectionDto.setExceptionMessage(message);
			e1.printStackTrace();
		}
		status = PRODUCT_NOT_DISCONNECTED;
		objDisconnectionDto.setException(e);
		 message="Exception for Index_key :"+objDisconnectionDto.getIndex_key()+"and For Row ID  :"+ objDisconnectionDto.getView_id();
		objDisconnectionDto.setExceptionMessage(message);
		e.printStackTrace();
		
	}
	
	return status;		
}// end of the function

public static void main (String[] args) throws Exception{
	IOESKenanManager kenanManager=new IOESKenanManager();
	try
	{
	kenanManager.loginKenan();
	kenanManager.findProduct("142591051");
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
	kenanManager.close();
	}
	
}
/**
*	pushes a service to FX
*		returnStatus=1 for success
		returnStatus=-1 for failure
			in case of failure exception is stored in service.exception and text msg, if any , 
				is stored in service.exceptionMessage
 * @param logIdentifier 
*/
public void sendServiceToFx( ServiceDTO servDTO, String logIdentifier) {

	
	int returnStatus = 0;
	OrderObjectData order = null;
	OrderBean orderBean=null;
	try {
		//
		AccountXIDObjectData aod =this.accountFind(servDTO.getAcctExternalId());
		if(aod==null)
		{
			String msg=logIdentifier+"Cannot create service since account is not present in FX . " +
							"\n  -For ServiceProductId :"+servDTO.getServiceProductId()+
							",\n  -Account Not Found was :"+servDTO.getAcctExternalId();
			AppConstants.IOES_LOGGER.info(msg);
			servDTO.setReturnStatus(-1);
			servDTO.setException(new Exception(msg));
			return;
		}
		
		Integer acctInternalId=aod.getAccountInternalId();
		
		orderBean = new OrderBean(settings);
		// create order
		order = new OrderObjectData();
		order.setAccountInternalId(acctInternalId);
		order.setOrderStatusId(new Integer("10"));
		order.setGenerateWorkflow(new Boolean(false));
		order.extendedData=Utility.getFxExtendedDataMap(servDTO.getOrderExtendedData());
		order = orderBean.create(context,order);
		order.setAccountInternalId(acctInternalId);
		
			
		FxOrderTrackerTask.insertOrderTrackerStatus(FxOrderTrackerConst.SERVICE,order.getOrderId().toString(),servDTO.getRowId(),"INIT");
		
		// **********************************Service Creation**********************************************//
		OrderedServiceBean ordServiceBean = new OrderedServiceBean(settings);
		
		/*******************START**********/
		ServiceObjectData servicedata_new = new ServiceObjectData();
		servicedata_new.setParentAccountInternalId(acctInternalId);
		
		servicedata_new.setEmfConfigId(servDTO.getEmfConfigId());
		servicedata_new.setViewStatus(new Integer(1));
		//servicedata_new.setIntendedViewEffectiveDt(servDTO.getServiceActiveDate());
		servicedata_new.setServiceActiveDt(servDTO.getServiceActiveDate());
		if(customUATDate==1)
		{
			servicedata_new.setServiceActiveDt(uatDate);
		}
		//servicedata_new.setDisplayExternalIdType(servDTO.getServiceDisplayExternalIdType());
		servicedata_new.setPrivacyLevel(servDTO.getPrivacyLevel());
		servicedata_new.setRevRcvCostCtr(servDTO.getRevRcvCostCtr());
		
		servicedata_new.setServiceCompany(servDTO.getA_serviceCompany());
		servicedata_new.setServiceFname(servDTO.getA_serviceFname());
		servicedata_new.setServiceMinit(servDTO.getA_serviceMname());
		servicedata_new.setServiceLname(servDTO.getA_serviceLname());
		servicedata_new.setServiceAddress1(servDTO.getA_serviceAddress1());
		servicedata_new.setServiceAddress2(servDTO.getA_serviceAddress2());
		servicedata_new.setServiceAddress3(servDTO.getA_serviceAddress3());
		servicedata_new.setServiceCity(servDTO.getA_serviceCity());
		servicedata_new.setServiceState(servDTO.getA_serviceState());

		if(servDTO.getA_serviceCountryCode()!=null)
		{
			servicedata_new.setServiceCountryCode(new Integer(servDTO.getA_serviceCountryCode()));
		}
		servicedata_new.setServiceZip(servDTO.getA_serviceZip());
		
		servicedata_new.setBServiceCompany(servDTO.getB_serviceCompany());
		servicedata_new.setBServiceFname(servDTO.getB_serviceFname());
		servicedata_new.setBServiceMinit(servDTO.getB_serviceMname());
		servicedata_new.setBServiceLname(servDTO.getB_serviceLname());
		servicedata_new.setBServiceAddress1(servDTO.getB_serviceAddress1());
		servicedata_new.setBServiceAddress2(servDTO.getB_serviceAddress2());
		servicedata_new.setBServiceAddress3(servDTO.getB_serviceAddress3());
		servicedata_new.setBServiceCity(servDTO.getB_serviceCity());
		
		servicedata_new.setRateClass(servDTO.getRateClass());
		servicedata_new.setCurrencyCode(servDTO.getCurrencyCode());
		
		if(servDTO.getB_serviceCountryCode()!=null)
		{
			servicedata_new.setBServiceCountryCode(new Integer(servDTO.getB_serviceCountryCode()));
		}
		servicedata_new.setBServiceZip(servDTO.getB_serviceZip());
		
		servicedata_new.extendedData=Utility.getFxExtendedDataMap(servDTO.getExtendedData());
		
	
		/*******************END**********/
		// create order for this si identifier

		OrderedServiceCreateOutputData orderedServiceCreateOutputData= ordServiceBean
											.create(context, servicedata_new,null,order,true);
		
		//order = orderedServiceCreateOutputData.getOrder();
		
		ServiceObjectBaseData serviceResponse=orderedServiceCreateOutputData.getService();
		
		
		int subscr_no = serviceResponse.getServiceInternalId().intValue();
		int subscr_no_resets = serviceResponse.getServiceInternalIdResets().intValue();
		
		//AppConstants.IOES_LOGGER.info("*****subscr_no" + subscr_no);
		/*System.out.println("*****subscr_no_resets" + subscr_no_resets);*/
		
		
		//*****************Adding External Ids
		ArrayList<FxExternalIdDto> externalIds = servDTO.getExternalIds();
		 if(externalIds!=null && externalIds.size()>0)
		{
			OrderedCiemBean ordCiemBean =new OrderedCiemBean(settings);
			
			for (FxExternalIdDto extIdDto : externalIds) {
				
				CustomerIdEquipMapObjectData serviceIdData =new CustomerIdEquipMapObjectData();
				
				serviceIdData.setServiceInternalId(serviceResponse.getServiceInternalId());
				serviceIdData.setServiceInternalIdResets(serviceResponse.getServiceInternalIdResets());
				ServiceObjectBaseKeyData serviceKey = serviceResponse.getKey();
				serviceIdData.setViewId(serviceKey.getViewId());
				serviceIdData.setActiveDate(servDTO.getServiceActiveDate());
				if(customUATDate==1)
				{
					serviceIdData.setActiveDate(uatDate);
				}
//					 Use current date & time as external ID (not what you would do
//					 in the real world, but produces a unique value)
				if("<SUBSCRNO>".equals(extIdDto.getExternalId()))
				{
					serviceIdData.setServiceExternalId(""+subscr_no);
				}else
				{
					serviceIdData.setServiceExternalId(extIdDto.getExternalId());
				}
				serviceIdData.setServiceExternalIdType(new Integer(extIdDto.getExternalIdType()));
				serviceIdData.setIsCurrent(true);
				serviceIdData.setViewStatus(1);
//					 Crate the Service-level external ID in the database
				
				OrderedCiemCreateOutputData serviceIdResponse =ordCiemBean.create(
											context, // BSDMSessionContext
											serviceIdData, // CustomerIdEquipMap to be created
											null, // Don't specify an Order
											null, // Don't specify a ServiceOrder
											new Boolean(true), // Look for an existing ServiceOrder
											new Boolean(true)); // Return order objects?
				
				/*AppConstants.IOES_LOGGER.info(
						"Created a CustomerIdEquipMap with ServiceExternalId: "
						+ serviceIdResponse.getCustomerIdEquipMap().getServiceExternalId());*/
			}
		
			
		}
		
			 
			FxOrderTrackerTask.updateTfxOrderTracker(FxOrderTrackerConst.SERVICE,
					order.getOrderId().toString(),
					servDTO.getRowId(),
					serviceResponse.getServiceInternalId().toString() ,
					serviceResponse.getServiceInternalIdResets().toString(),
					serviceResponse.getKey().getViewId().toString(),
					"FORCOMMIT");	
			
	 
		 
		//******************End : Added External Ids
		
		//order = orderedServiceCreateOutputData.getOrder();
		//order.setGenerateWorkflow(false);
		OrderObjectKeyData orderKey = order.getKey();
		servDTO.setToken_id(order.getKey().getOrderId().toString());
		servDTO.setViewId(serviceResponse.getKey().getViewId().toString());
		orderBean = new OrderBean(settings);
		orderBean.commit(context, orderKey);
		
		servDTO.setSubscrNo(subscr_no);
		servDTO.setSubscrNoReset(subscr_no_resets);

	
		returnStatus = 1;

	} catch (Exception e) {
		Utility.LOG(true,true,e,null);
		returnStatus = -1;
		servDTO.setException(e);
		String message=logIdentifier+"Exception for serviceproductid :"+servDTO.getServiceProductId()+" and Row Id :"+servDTO.getRowId();
		Utility.LOG(true,true,message);
		servDTO.setExceptionMessage(message);
		
		if(order!=null && orderBean!=null)
		{
			//OrderObjectKeyData orderKey = order.getKey();
			//OrderBean orderBean = new OrderBean(settings);
			try {
				
				orderBean.cancel(context, order.getKey());
				message=logIdentifier+"Order Cancelled for Service Product Id:"+servDTO.getServiceProductId()+" at :"+new Date();
				AppConstants.IOES_LOGGER.info(message);
				servDTO.setExceptionMessage(message);
			} catch (Exception e1) {
				Utility.LOG(true,true,e1,null);
			}
		}
		
	}
	servDTO.setReturnStatus(returnStatus);
	

}

/**
*	pushes a service to FX
*		returnStatus=1 for success
		returnStatus=-1 for failure
			in case of failure exception is stored in RcDto rccharge.exception and text msg, if any , 
				is stored in RcDto rccharge.exceptionMessage
 * @param logIdentifier 
*/
public void sendRcToFX(RcDto rccharge, String logIdentifier) {

	OrderObjectData order=null;
	
	OrderBean objOrderBean=null;

	try
	{
			
				
				objOrderBean = new OrderBean(settings);
				// create order
				order = new OrderObjectData();
				
				AccountXIDObjectData aod =this.accountFind(rccharge.getFx_Ext_Account_No());
				if(aod==null)
				{
					String msg=logIdentifier+"Cannot create chage since account is not present in FX . " +
									"\n  -For Chargeinfoid :"+rccharge.getChargeinfoid()+
									",\n  -Account Not Found was :"+rccharge.getFx_Ext_Account_No()+
									",\n and Row Id"+rccharge.getRowId();
					AppConstants.IOES_LOGGER.info(msg);
					rccharge.setReturnStatus(-1);
					rccharge.setException(new Exception(msg));
					return;
				}
				
				//int a=1;if(a==1)throw new Exception("custom exc");//REMOVE 
				/***************Checking if this is Differential case and old charges needs to be disconnected**********************/
				
				boolean isDifferentialHitInFX=false;
				boolean addToExtData=false;
				if(new Integer(1).equals(rccharge.getIsDifferentialForFX())){
					
					//if Charge is already Disconnected
						//then log in raise to knock table 
						//add tracking Id and Serv to Ext Data for new charge push
						//proceed
					//if charge is not disconnected , then
						//if old charge is created,
							//set isDifferentialCase flag to true
							//add tracking Id and Serv to Ext Data for new charge push
							//then proceed
						//if old charge is not created , then return exception
					
					DisconnectionDto linkedDisconnectionCharge = rccharge.getLinkedDisconnectionCharge();
					
					if(linkedDisconnectionCharge.getProcessing_status()==3){
						String msg=logIdentifier+"Cannot create chage since linked old Charge already disconnected in FX . " +
								"\n  -For Chargeinfoid :"+rccharge.getChargeinfoid()+
								//",\n  -Account Not Found was :"+rccharge.getFx_Ext_Account_No()+
								",\n and Row Id"+rccharge.getRowId();
						AppConstants.IOES_LOGGER.info(msg);
						rccharge.setReturnStatus(-1);
						rccharge.setException(new Exception(msg));
						return;						
					}else{
						if(linkedDisconnectionCharge.getView_id()==null || linkedDisconnectionCharge.getSchedular_status()!=3
								|| linkedDisconnectionCharge.getTrackingId()==null || linkedDisconnectionCharge.getTrackingId().equals(0)
								|| linkedDisconnectionCharge.getTrackingIdServ()==null || linkedDisconnectionCharge.getTrackingIdServ().equals(0)){
							//if old charge is not created , then return exception
							String msg=logIdentifier+"Cannot create chage since linked old Charge is not created in FX . " +
									"\n  -For Chargeinfoid :"+rccharge.getChargeinfoid()+
									//",\n  -Account Not Found was :"+rccharge.getFx_Ext_Account_No()+
									",\n and Row Id"+rccharge.getRowId();
							AppConstants.IOES_LOGGER.info(msg);
							rccharge.setReturnStatus(-1);
							rccharge.setException(new Exception(msg));
							return;
						}else{
							isDifferentialHitInFX=true;
							addToExtData=true;
						}
					}
					
					
					if(addToExtData==true){
						ArrayList<ExtendedData> extendedData = rccharge.getExtendedData();
						
						ExtendedData extendedDto=new ExtendedData();
						extendedDto.setParamId(5990001);
						String dataType="INTEGER";
						extendedDto.setParamValue(Utility.getFXExtendedDataObject(dataType,String.valueOf(linkedDisconnectionCharge.getTrackingId())));
						extendedData.add(extendedDto);
						
						extendedDto=new ExtendedData();
						extendedDto.setParamId(5990002);
						dataType="INTEGER";
						extendedDto.setParamValue(Utility.getFXExtendedDataObject(dataType,String.valueOf(linkedDisconnectionCharge.getTrackingIdServ())));
						
						extendedData.add(extendedDto);
					}
				}
				
				/*******************************************************************************************************************/
				
				Integer forOrder_accountInternalId =aod.getAccountInternalId();
				order.setAccountInternalId(forOrder_accountInternalId);
				order.setOrderStatusId(new Integer("10"));
				order.setGenerateWorkflow(new Boolean(false));
				order.extendedData=Utility.getFxExtendedDataMap(rccharge.getOrderExtendedData());
				order = objOrderBean.create(context,order);
				order.setAccountInternalId(forOrder_accountInternalId);
			
				FxOrderTrackerTask.insertOrderTrackerStatus(FxOrderTrackerConst.PRODUCT,order.getOrderId().toString(),rccharge.getRowId(),"INIT");
				
//				 Initialize an OrderedProductBean
				OrderedProductBean ordProductBean = new OrderedProductBean(settings);
				
//				 Initialize a ProductObjectData object
//				 to contain the Product's info
				ProductObjectData product = new ProductObjectData();
				
				/*
				* associate the product with the Service.
				*/
				
				OrderedProductCreateOutputData productResponse = null;
				
				if(FxSchedulerTasksforCharges.CHARGE_LEVEL_ACCOUNT.equals(rccharge.getFx_Level()))
				{
					product.setBillingAccountInternalId(forOrder_accountInternalId);
					product.setParentAccountInternalId(forOrder_accountInternalId);
				}
				else if(FxSchedulerTasksforCharges.CHARGE_LEVEL_SERVICE.equals(rccharge.getFx_Level()))
				{
					product.setParentServiceInternalId(new Integer(rccharge.getSubScrNo()));
					product.setParentServiceInternalIdResets(0);
					product.setBillingAccountInternalId(forOrder_accountInternalId);
					product.setParentAccountInternalId(forOrder_accountInternalId);
				}
				
					
					//Integer elementId=new Integer("50107014");
					product.setElementId(rccharge.getElementId());
					product.setViewStatus(new Integer(1));
					//product.setOpenItemId(rc.getOpenItemId());//1
					//product.setIntendedViewEffectiveDt(rc.getBillingActiveDate());
					product.setProductActiveDt(rccharge.getBillingActiveDate());
					//product.setProductInactiveDt(rccharge.getBillingEndDate());
					
					product.setBillingActiveDt(rccharge.getBillingActiveDate());
					product.setBillingInactiveDt(rccharge.getBillingEndDate());
					product.setIsPartOfComponent(false);
					product.setInArrearsOverride(rccharge.getInArrearsOverride());
					product.setBillPeriod(rccharge.getBillPeriod());
					
					//product.setOrderNumber(rc.getOrderNumber());
					//product.setOrderNumber("AES-Order");
					
					
					product.extendedData=Utility.getFxExtendedDataMap(rccharge.getExtendedData());
					
					//START : REMOVE   : eatended param for 10.13.41.90
					//Map extendedData = new HashMap();
					//extendedData.put(4992002, "123");//RA Number//String
					//extendedData.put(4992001, new Integer(890));//Annual Rate//4 byte integer
					//extendedData.put(4992003, new Integer(300));//Distance//4 byte integer
					//extendedData.put(4992004, new Date(System.currentTimeMillis())/*"2010-10-25 000000"*/);//PO Date//date/time, in the format YYYY-MM-DD HHMMSS 
					//product.extendedData=extendedData;
					//END
					
					
//					 Insert the Product into the database and store
//					 the server's response in productResponse
					
					if(FxSchedulerTasksforCharges.CHARGE_LEVEL_ACCOUNT.equals(rccharge.getFx_Level()))
					{
						productResponse = ordProductBean.create(
								context, // BSDMSessionContext
								product, // Product to be created
								order, //  an Order
								null, // Don't specify a ServiceOrder
								new Boolean(true), // Look for an existing ServiceOrder
								new Boolean(true)); // Return order objects?
					}
					else if(FxSchedulerTasksforCharges.CHARGE_LEVEL_SERVICE.equals(rccharge.getFx_Level()))
					{
						productResponse = ordProductBean.create(
								context, // BSDMSessionContext
								product, // Product to be created
								order, //  an Order
								null, // Don't specify a ServiceOrder
								new Boolean(true), // Look for an existing ServiceOrder
								new Boolean(true)); // Return order objects?
					}
					
					System.out.println("Created Product with ViewId: "+ productResponse.getProduct().getViewId());
					
					
					
//					 No need to create an Item for the Product;
//					 it's created by the OrderedProductCreate method
//					 Return the newly created Product
					
					
					ProductRateOverrideBean productRateOverrideBean=new ProductRateOverrideBean(settings);
					ProductRateOverrideObjectData productRateOverrideObjectData= new ProductRateOverrideObjectData();
					
					BigInteger iRate = new BigInteger(rccharge.getOverrideRate());
					
					
					productRateOverrideObjectData.setCurrencyCode(aod.getCurrencyCode());
					productRateOverrideObjectData.setActiveDt(rccharge.getBillingActiveDate());
					//productRateOverrideObjectData.setInactiveDt(rccharge.getBillingEndDate());
					productRateOverrideObjectData.setTrackingId(productResponse.getProduct().getTrackingId());
					productRateOverrideObjectData.setTrackingIdServ(productResponse.getProduct().getTrackingIdServ());
					productRateOverrideObjectData.setOverrideRate(iRate);
					ProductRateOverrideObjectData overrideBean= productRateOverrideBean.create (context, productRateOverrideObjectData);
					System.out.println("Product Rate Overrided");
					
					
					/***************Checking if this is Differential case and old charges needs to be disconnected**********************/
					
					if(isDifferentialHitInFX==true){
						//Disconnect Charge in fx
						OrderedProductBean ordProductBeanForDisconnect = new OrderedProductBean(settings);	
						ProductObjectData productDisconnect = new ProductObjectData();
						productDisconnect.setViewId(new BigInteger(rccharge.getLinkedDisconnectionCharge().getView_id()));		 
						OrderedProductDisconnectOutputData productResponseForDisconnect = ordProductBeanForDisconnect.disconnect(
							context, // BSDMSessionContext
							productDisconnect, // Product to be created
							order, // Don't specify an Order
						null,
						new java.util.Date(rccharge.getLinkedDisconnectionCharge().getCharge_disconnection_date().getTime()) ,// Don't specify a ServiceOrder
						new Boolean(true), // Look for an existing ServiceOrder
						new Boolean(true)); 
						
					}
					
					/*******************************************************************************************************************/
					
					
					
					
					
					FxOrderTrackerTask.updateTfxOrderTracker(FxOrderTrackerConst.PRODUCT,
							order.getOrderId().toString(),
							rccharge.getRowId(),
							productResponse.getProduct().getTrackingId().toString() ,
							productResponse.getProduct().getTrackingIdServ().toString(),
							productResponse.getProduct().getViewId().toString(),
							"FORCOMMIT");
					
					OrderObjectKeyData orderKey = order.getKey();
					rccharge.setToken_id(orderKey.getOrderId().toString());
					System.out.println("Order ID FOR THE PRODUCT"+orderKey.getOrderId());
					
//					 Commit the Order
					//OrderBean orderBean = new OrderBean(settings);
					objOrderBean.commit(context, orderKey);
					
					//int a=1;if(a==1)throw new Exception("custom exc");//REMOVE 
					
					rccharge.setReturnStatus(1);
					rccharge.setViewId(productResponse.getProduct().getViewId().toString());
					rccharge.setTrackingId(productResponse.getProduct().getTrackingId());
					rccharge.setTrackingIdServ(productResponse.getProduct().getTrackingIdServ());
					rccharge.setDifferentialHitInFX(isDifferentialHitInFX);
					/*CustomerIdEquipMapObjectData serviceIdData =new CustomerIdEquipMapObjectData();
					
					//	Use current date & time as external ID (not what you would do
					//	in the real world, but produces a unique value)
					serviceIdData.setServiceExternalId(rccharge.getExtId()+System.currentTimeMillis()/1000);
					serviceIdData.setServiceExternalIdType(105);
					serviceIdData.setIsCurrent(true);
					serviceIdData.setViewStatus(1);*/
	}
	catch(Exception ex)
	{

		Utility.LOG(true,true,ex,null);
		rccharge.setReturnStatus(-1); 
		rccharge.setException(ex);
		String message=logIdentifier+"Exception for Chargeinfoid:"+rccharge.getChargeinfoid()+" and Row Id :"+rccharge.getRowId();
		rccharge.setExceptionMessage(message);
		
		if(order!=null && objOrderBean!=null)
		{
			//OrderObjectKeyData orderKey = order.getKey();
			//OrderBean orderBean = new OrderBean(settings);
			try {
				
				objOrderBean.cancel(context, order.getKey());
				message=logIdentifier+"Order Cancelled for Chargeinfoid:"+rccharge.getChargeinfoid()+" at :"+new Date();
				AppConstants.IOES_LOGGER.info(message);
				rccharge.setExceptionMessage(message);
			} catch (Exception e1) {
				Utility.LOG(true,true,e1,null);
			}
		}
	}
}

public void sendNRcToFX(NrcDto nrccharge, String logIdentifier) {
	

	OrderObjectData order=null;
	
	OrderBean objOrderBean=null;
	try
	{

	
		objOrderBean = new OrderBean(settings);
		// create order
		order = new OrderObjectData();
		
		AccountXIDObjectData aod =this.accountFind(nrccharge.getFx_Ext_Account_No());
		if(aod==null)
		{
			String msg=logIdentifier+"Cannot create chage since account is not present in FX . " +
							"\n  -For Chargeinfoid :"+nrccharge.getChargeinfoid()+
							",\n  -Account Not Found was :"+nrccharge.getFx_Ext_Account_No()+
							",\n and Row Id"+nrccharge.getRowId();
			AppConstants.IOES_LOGGER.info(msg);
			nrccharge.setReturnStatus(-1);
			nrccharge.setException(new Exception(msg));
			return;
		}
		
		Integer forOrder_accountInternalId =aod.getAccountInternalId();
		order.setAccountInternalId(forOrder_accountInternalId);
		
		 
		
		order.setOrderStatusId(new Integer("10"));
		order.setGenerateWorkflow(new Boolean(false));
		
		order.extendedData=Utility.getFxExtendedDataMap(nrccharge.getOrderExtendedData());
		order = objOrderBean.create(context,order);
		order.setAccountInternalId(forOrder_accountInternalId);
		
		FxOrderTrackerTask.insertOrderTrackerStatus(FxOrderTrackerConst.NRC,order.getOrderId().toString(),nrccharge.getRowId(),"INIT");
		
		NrcObjectData nrcFx = new NrcObjectData();
		boolean skipRecord = false;
		
				  
		nrcFx.setTypeIdNrc(nrccharge.getTypeidNrc());
	//	Set default values
		nrcFx.setCurrencyCode(aod.getCurrencyCode());
		nrcFx.setEffectiveDate(nrccharge.getEffectiveDate());
		//nrcFx.setAnnotation(nrcDto.getAnnotation());
		nrcFx.setRate(new BigInteger(nrccharge.getNrcAmount()));
		nrcFx.setRateDt(nrccharge.getEffectiveDate());
		nrcFx.setNoBill(new Boolean(false));
		
		//nrcFx.setOrderNumber("AES-Order");
		//nrcFx.setAnnotation("Annotation : AES-NRC");
		
		if(FxSchedulerTasksforCharges.CHARGE_LEVEL_ACCOUNT.equals(nrccharge.getFx_Level()))
		{
			nrcFx.setBillingAccountInternalId(forOrder_accountInternalId);
		}
		else if(FxSchedulerTasksforCharges.CHARGE_LEVEL_SERVICE.equals(nrccharge.getFx_Level()))
		{
			nrcFx.setBillingAccountInternalId(forOrder_accountInternalId);
			nrcFx.setParentServiceInternalId(new Integer(nrccharge.getSubScrNo()));
			nrcFx.setParentServiceInternalIdResets(0);	
		} 
		
		
		//set extended attr
		nrcFx.extendedData=Utility.getFxExtendedDataMap(nrccharge.getExtendedData());
		//Map extendedData = new HashMap();
		//extendedData.put(4993001, "123");//RA Number//String
		//extendedData.put(4993002, new Date(System.currentTimeMillis()));//PO Date//date/time, in the format YYYY-MM-DD HHMMSS 
		//nrcFx.extendedData=extendedData;
		OrderedNrcBean ordNrcBean = new OrderedNrcBean(settings);
	
		// Create the Nrc in the database and store the returned
		// NRC object in nrcResponse
		
		OrderedNrcCreateOutputData nrcResponse = null;
		if(FxSchedulerTasksforCharges.CHARGE_LEVEL_ACCOUNT.equals(nrccharge.getFx_Level()))
		{
			nrcResponse =ordNrcBean.create(this.context, nrcFx, // Nrc to be created
					order, // Don't specify an Order
					null, // Don't specify a ServiceOrder
					new Boolean(true), // Look for an existing ServiceOrder
					new Boolean(true)); // Return order objects?
		}
		else if(FxSchedulerTasksforCharges.CHARGE_LEVEL_SERVICE.equals(nrccharge.getFx_Level()))
		{
			nrcResponse =ordNrcBean.create(this.context, nrcFx, // Nrc to be created
					order, // Don't specify an Order
					null, // Don't specify a ServiceOrder
					new Boolean(true), // Look for an existing ServiceOrder
					new Boolean(true)); // Return order objects?	
		}
		
		
		FxOrderTrackerTask.updateTfxOrderTracker(FxOrderTrackerConst.NRC,
				order.getOrderId().toString(),
				nrccharge.getRowId(),
				nrcResponse.getNrc()[0].getTrackingId().toString() ,
				nrcResponse.getNrc()[0].getTrackingIdServ().toString(),
				nrcResponse.getNrc()[0].getViewId().toString(),
				"FORCOMMIT");
		
		OrderObjectKeyData orderKey = order.getKey();
		nrccharge.setToken_id(orderKey.getOrderId().toString());
		System.out.println("Order ID FOR THE NRC"+orderKey.getOrderId());
	//	 Commit the Order
		//OrderBean orderBean = new OrderBean(settings);
		objOrderBean.commit(context, orderKey);
		
		nrccharge.setReturnStatus(1);
		
		nrccharge.setViewId(nrcResponse.getNrc()[0].getViewId().toString());
				
		/*CustomerIdEquipMapObjectData serviceIdData =new CustomerIdEquipMapObjectData();
		
		//	Use current date & time as external ID (not what you would do
		//	in the real world, but produces a unique value)
		serviceIdData.setServiceExternalId(nrccharge.getExtId()+System.currentTimeMillis()/1000);
		serviceIdData.setServiceExternalIdType(105);
		serviceIdData.setIsCurrent(true);
		serviceIdData.setViewStatus(1);*/
		
		
	}
	catch(Exception ex)
	{
		Utility.LOG(true,true,ex,null);
		nrccharge.setReturnStatus(-1); 
		nrccharge.setException(ex);
		String message=logIdentifier+"Exception for Chargeinfoid:"+nrccharge.getChargeinfoid()+" and Row Id :"+nrccharge.getRowId();
		nrccharge.setExceptionMessage(message);
		
		if(order!=null && objOrderBean!=null)
		{
			//OrderObjectKeyData orderKey = order.getKey();
			//OrderBean orderBean = new OrderBean(settings);
			try {
				
				objOrderBean.cancel(context, order.getKey());
				message=logIdentifier+"Order Cancelled for Chargeinfoid:"+nrccharge.getChargeinfoid()+" at :"+new Date();
				AppConstants.IOES_LOGGER.info(message);
				nrccharge.setExceptionMessage(message);
			} catch (Exception e1) {
				Utility.LOG(true,true,e1,null);
			}
		}	
	}
	
 }// end of the function

 	

	
		public boolean updateProduct(String viewId,Date inactiveDate) throws Exception
		{
			boolean status = false;

			try {

					ProductObjectBaseData ProductUpdateIn=new ProductObjectBaseData(); 
					ProductObjectBaseKeyData pobkd = new ProductObjectBaseKeyData();
					pobkd.setViewId(new BigInteger(viewId));
					ProductBean pb = new ProductBean(settings);




					ProductUpdateIn.setBillingInactiveDt(inactiveDate);
					ProductUpdateIn.setKey(pobkd);


					pb.update(context,ProductUpdateIn);

					status = true;


			}catch (Exception e) {


				e.printStackTrace();
			}

			return status;
		}
		
		public void updateNRCLocData(LOCDATADTO nrcdto,LOCDATADTO objdto,String logIdentifier) {

	
		
		NrcBean nrcbean = new NrcBean(this.settings);
		NrcObjectBaseKeyData key = new NrcObjectBaseKeyData();
		NrcObjectData objectdata = new  NrcObjectData();
		NrcObjectBaseData response = null;  
		
		try {
			
			Map extendedData = new HashMap();
			extendedData.put(1122, objdto.getLocno());//RA Number//String
			if(objdto.getServiceType().toUpperCase().contains("HARD"))
			{
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date date = df.parse(objdto.getLocdate()); 
				extendedData.put(1083, date);//RA Number//String
			}else if("Y".equals(Utility.getAppConfigValue("SET_NULL_FOR_SERVICE_RC")))
				{
					extendedData.put(1083, null);//RA Number//String
				}
			key.setViewId(new BigInteger(nrcdto.getNrc_viewid()));			
			objectdata.setKey(key);
			objectdata.extendedData = extendedData;
			
			response = nrcbean.update(this.context,
									 objectdata
									);
			
			nrcdto.setNrc_Status("success");
		}catch (Exception e) {
			
			e.printStackTrace();
			nrcdto.setNrc_Status("failure");
			String message=logIdentifier+"error at the time to update Nrc Loc Data in FX()";
			objdto.setExceptionMessage(message);
		}
	}
		
		public void updateRCLocData(LOCDATADTO Rcdto,LOCDATADTO objdto,String logIdentifier) {

			ProductBean productbean = new ProductBean(this.settings);
			ProductObjectBaseKeyData key = new ProductObjectBaseKeyData();
			ProductObjectData objectdata = new  ProductObjectData();
			ProductObjectBaseData response = null;  
			
			try {
				Map extendedData = new HashMap();
				extendedData.put(1122, objdto.getLocno());//RA Number//String
				if(objdto.getServiceType().toUpperCase().contains("HARD"))
				{
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Date date = df.parse(objdto.getLocdate()); 
					extendedData.put(1083, date);//RA Number//String
				}else if("Y".equals(Utility.getAppConfigValue("SET_NULL_FOR_SERVICE_RC")))
				{
					extendedData.put(1083, null);//RA Number//String
				}
				key.setViewId(new BigInteger(Rcdto.getRc_viewid()));			
				objectdata.setKey(key);
				objectdata.extendedData = extendedData;
				
				response = productbean.update(this.context,
										 objectdata
										);
				
				Rcdto.setRc_Status("success");
			
			}catch (Exception e) {
				
				e.printStackTrace();
				Rcdto.setRc_Status("failure");
				String message=logIdentifier+"error at the time to update Rc Loc Data in FX()";
				objdto.setExceptionMessage(message);
			}
			
		}
		
		
		
		public void updateServiceLocData(LOCDATADTO ServiceDto,LOCDATADTO objdto,String logIdentifier) {

			ServiceBean servicebean = new ServiceBean(this.settings);
			ServiceObjectBaseKeyData key = new ServiceObjectBaseKeyData();
			ServiceObjectData objectdata = new  ServiceObjectData();
			ServiceObjectBaseData response = null;  
			
			try {
				
				Map extendedData = new HashMap();
				extendedData.put(1122, objdto.getLocno());//RA Number//String
				//extendedData.put(1083, date);//RA Number//String
				key.setViewId(new BigInteger(ServiceDto.getService_viewid()));			
				objectdata.setKey(key);
				objectdata.extendedData = extendedData;
				response = servicebean.update(this.context,
										 objectdata
										);
				if(ServiceDto.getService_parentid()==null)
				{
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat queryFormat=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
					Date date = df.parse(objdto.getLocdate()); 
					String locdate = queryFormat.format(date); 
				
				createService_ExternalId_LOCDATE(ServiceDto.getSubcno(),new BigInteger(ServiceDto.getService_viewid()),ServiceDto.getServiceactdate(),locdate);
				}
				
				ServiceDto.setService_Status("success");
			
				
			}catch (Exception e) {
				
				e.printStackTrace();
				ServiceDto.setService_Status("failure");
				String message=logIdentifier+"error at the time to update Service Loc Data in FX()";
				objdto.setExceptionMessage(message);
			}
		}
		
		
		public void disconnectService(ServiceDisconnectionDto objDisconnectionDto, String logIdentifier) 
		{ 
			int returnStatus = 0; // status 0 represents failure by default
			OrderBean objOrderBean = null;		
			OrderObjectData order = null;	
			OrderedServiceBean osb = new OrderedServiceBean(settings);
			
			ServiceObjectData sod = new  ServiceObjectData();
			

			OrderedServiceDisconnectOutputData osdo = new OrderedServiceDisconnectOutputData();
			
			
			
			try
			{
					
				AccountXIDObjectData aod =this.accountFind(objDisconnectionDto.getAcctExternalId());
				if(aod==null)
				{
					String msg=logIdentifier+"Cannot Disconnect service since account is not present in FX . " +
									"\n  -For Index_key :"+objDisconnectionDto.getIndex_key()+
									",\n  -Account Not Found was :"+objDisconnectionDto.getAcctExternalId();
					//AppConstants.IOES_LOGGER.info(msg);
					objDisconnectionDto.setReturnStatus(-1);
					objDisconnectionDto.setException(new Exception(msg));
					objDisconnectionDto.setExceptionMessage(msg);
					
					return;
				}
				Integer acctInternalId=aod.getAccountInternalId();
				
				objOrderBean = new OrderBean(settings);
				// create order
				order = new OrderObjectData();
				
				
				order.setAccountInternalId(acctInternalId);
				order.setOrderStatusId(new Integer("10"));
				order.setGenerateWorkflow(new Boolean(false));
				order.extendedData=Utility.getFxExtendedDataMap(objDisconnectionDto.getOrderExtendedData());
				order = objOrderBean.create(this.context,order);
				order.setAccountInternalId(acctInternalId);				
				
				
				
				sod.setViewId(new BigInteger(objDisconnectionDto.getView_id()));				
				osdo = osb.disconnect(context,
									  sod,
									  order,
									  1,
									  new Date(objDisconnectionDto.getService_disconnection_date().getTime()),
									  null,
									  null,
									  null,
									  null,
									  null,
									  null,
									  true);
				//order = osdo.getOrder();			
				
				
				
				returnStatus = 1;// status 3 represents success
				OrderObjectKeyData orderKey = order.getKey();
				objDisconnectionDto.setTokenid(order.getOrderId().toString());
				System.out.println("Order ID FOR THE Service Disconnection"+orderKey.getOrderId());
//				 Commit the Order
				
				objOrderBean.commit(context, orderKey);
			}
			catch(Exception e) {

				
				Utility.LOG(true,true,e,null);
				returnStatus = -1;
				objDisconnectionDto.setException(e);
				String message=logIdentifier+"Exception for Index_key :"+objDisconnectionDto.getIndex_key();
				Utility.LOG(true,true,message);
				objDisconnectionDto.setExceptionMessage(message);
				
				if(order!=null && objOrderBean!=null)
				{
					//OrderObjectKeyData orderKey = order.getKey();
					//OrderBean orderBean = new OrderBean(settings);
					try {
						
						objOrderBean.cancel(context, order.getKey());
						message=logIdentifier+"Exception for Index_key :"+objDisconnectionDto.getIndex_key();
						AppConstants.IOES_LOGGER.info(message);
						objDisconnectionDto.setExceptionMessage(message);
					} catch (Exception e1) {
						Utility.LOG(true,true,e1,null);
						objDisconnectionDto.setExceptionInCancel(true);
					}
				}
				
			
				
			}
			objDisconnectionDto.setReturnStatus(returnStatus);
		}



		public void updateService( ServiceDTO servDTO, String logIdentifier) {

			
			int returnStatus = 0;
			int subProcessingStatus = 0;
			int status_1=0;
			int status_2=0;
			boolean addToException=true;
			ServiceBean sb = new ServiceBean(settings);
			ServiceObjectBaseData sobd = new ServiceObjectBaseData();
			
			try {
					if("ALL".equalsIgnoreCase(servDTO.getServiceUpdateFlag())) {
						
					
						sobd.setServiceCompany(servDTO.getA_serviceCompany());
						sobd.setServiceFname(servDTO.getA_serviceFname());
						sobd.setServiceMinit(servDTO.getA_serviceMname());
						sobd.setServiceLname(servDTO.getA_serviceLname());
						sobd.setServiceAddress1(servDTO.getA_serviceAddress1());
						sobd.setServiceAddress2(servDTO.getA_serviceAddress2());
						sobd.setServiceAddress3(servDTO.getA_serviceAddress3());
						sobd.setServiceCity(servDTO.getA_serviceCity());
						sobd.setServiceState(servDTO.getA_serviceState());
						if(servDTO.getA_serviceCountryCode()!=null)
						{
							sobd.setServiceCountryCode(new Integer(servDTO.getA_serviceCountryCode()));
						}
						//sobd.setServiceCountryCode(new Integer(servDTO.getA_serviceCountryCode()));
						sobd.setServiceZip(servDTO.getA_serviceZip());
						
						sobd.setBServiceCompany(servDTO.getB_serviceCompany());
						sobd.setBServiceFname(servDTO.getB_serviceFname());
						sobd.setBServiceMinit(servDTO.getB_serviceMname());
						sobd.setBServiceLname(servDTO.getB_serviceLname());
						sobd.setBServiceAddress1(servDTO.getB_serviceAddress1());
						sobd.setBServiceAddress2(servDTO.getB_serviceAddress2());
						sobd.setBServiceAddress3(servDTO.getB_serviceAddress3());
						sobd.setBServiceCity(servDTO.getB_serviceCity());
						if(servDTO.getB_serviceCountryCode()!=null)
						{
							sobd.setBServiceCountryCode(new Integer(servDTO.getB_serviceCountryCode()));
						}
						sobd.setBServiceZip(servDTO.getB_serviceZip());
						//sobd.setActiveDt(servDTO.getServiceActiveDate());
						sobd.extendedData=Utility.getFxExtendedDataMap(servDTO.getExtendedData());
						
						sobd.setViewId(new BigInteger(servDTO.getViewId()));
						sobd = sb.update(context, sobd);
					    
						String message=logIdentifier+" ServiceProductId :"+servDTO.getServiceProductId()+" for ROW  ID:"+servDTO.getRowId()+"Successfuly Updated";
						System.out.println(message);
						Utility.LOG(true,true,message);
						
						servDTO.setViewId(sobd.getKey().getViewId().toString());						
						returnStatus = 1;
						
					}
					else if("ONLY_ACTIVE_DATE".equalsIgnoreCase(servDTO.getServiceUpdateFlag())) 
					{
							
						sobd.setActiveDt(servDTO.getServiceActiveDate());
						
						sobd.setViewId(new BigInteger(servDTO.getViewId()));
						sobd = sb.update(context, sobd);
					    
						String message=logIdentifier+" ServiceProductId :"+servDTO.getServiceProductId()+" for ROW  ID:"+servDTO.getRowId()+"Successfuly Updated";
						System.out.println(message);
						Utility.LOG(true,true,message);
						
						servDTO.setViewId(sobd.getKey().getViewId().toString());						
						returnStatus = 1;
					
					}
					else if("LOC_TRACKING_SI".equalsIgnoreCase(servDTO.getServiceUpdateFlag())) 
					{
						if(servDTO.getSubFxSchdUpdateStatus()==0)
						{
							status_1=updateActiveDateAndExtDataForLOCTracking(sb,sobd,servDTO,logIdentifier);
							status_2=insertExternalIdForLOCTracking(servDTO,logIdentifier,addToException);
							returnStatus=-1;
							if(status_1==1 && status_2==1)
							{
								subProcessingStatus=3;
								returnStatus=1;
							}
							else if(status_1==1 && status_2==-1)
							{
								subProcessingStatus=1;
							}
							else if(status_1==-1 && status_2==1)
							{
								subProcessingStatus=2;
							}
							else
							{
								subProcessingStatus=0;
							}
						}
						else if(servDTO.getSubFxSchdUpdateStatus()==1)
						{
							status_2=insertExternalIdForLOCTracking(servDTO,logIdentifier,addToException);
							if(status_2==1)
							{
								subProcessingStatus=3;
								returnStatus=1;
							}
							else
							{
								subProcessingStatus=1;
								returnStatus=-1;
							}
						}
						else if(servDTO.getSubFxSchdUpdateStatus()==2)
						{
							status_1=updateActiveDateAndExtDataForLOCTracking(sb,sobd,servDTO,logIdentifier);;
							if(status_1==1)
							{
								subProcessingStatus=3;
								returnStatus=1;
							}
							else
							{
								subProcessingStatus=2;
								returnStatus=-1;
							}
						}
					}else if("EXTENDED_DATA".equalsIgnoreCase(servDTO.getServiceUpdateFlag())) {
						
						
						sobd.extendedData=Utility.getFxExtendedDataMap(servDTO.getExtendedData());
						
						sobd.setViewId(new BigInteger(servDTO.getViewId()));
						sobd = sb.update(context, sobd);
					    
						String message=logIdentifier+" ServiceProductId :"+servDTO.getServiceProductId()+" for ROW  ID:"+servDTO.getRowId()+"Successfuly Updated";
						System.out.println(message);
						Utility.LOG(true,true,message);
						
						servDTO.setViewId(sobd.getKey().getViewId().toString());						
						returnStatus = 1;
						
					}

				}catch (Exception e) {
				Utility.LOG(true,true,e,null);
				returnStatus = -1;
				servDTO.setException(e);
				String message=logIdentifier+"Exception for ServiceProductId :"+servDTO.getServiceProductId()+" and ROW Id :"+servDTO.getRowId();
				System.out.println(message);
				Utility.LOG(true,true,message);
				servDTO.setExceptionMessage(message);
			}
			servDTO.setReturnStatus(returnStatus);
			servDTO.setSubProcessingStatus(subProcessingStatus);

		}

private int insertExternalIdForLOCTracking(ServiceDTO servDTO, String logIdentifier,boolean addToException) {
			try
			{
				ArrayList<FxExternalIdDto> arryList= servDTO.getExternalIds();
				//throw new Exception("exception in status_2");
				FxExternalIdDto dto=(FxExternalIdDto)arryList.get(0);
				createService_ExternalId_LOCDATE(servDTO.getSubscrNo(),new BigInteger(servDTO.getViewId()),servDTO.getServiceActiveDate(),dto.getExternalId());
				return 1;
			}
			catch(Exception e)
			{
				
				String message=logIdentifier+"Exception for ServiceProductId :"+servDTO.getServiceProductId()+" and ROW Id :"+servDTO.getRowId();
				System.out.println(message);
				Utility.LOG(true,true,e,message);
				
				if(addToException==true)
				{
					//servDTO.setException(servDTO.getException().);
					servDTO.setExceptionMessage(message+servDTO.getExceptionMessage());
				}else
				{
					servDTO.setException(e);
					servDTO.setExceptionMessage(message);
				}
				return -1;
			}
		}

private int updateActiveDateAndExtDataForLOCTracking(ServiceBean sb, ServiceObjectBaseData sobd, ServiceDTO servDTO, String logIdentifier) {
	try
	{
		sobd.setActiveDt(servDTO.getServiceActiveDate());
		if(customUATDate==1)
		{
			sobd.setActiveDt(uatDate);
		}
		sobd.extendedData=Utility.getFxExtendedDataMap(servDTO.getExtendedData());
		sobd.setViewId(new BigInteger(servDTO.getViewId()));
		//throw new Exception("exception in status_1");
		sobd = sb.update(context, sobd);
		return 1;
	}
	catch(Exception e)
	{
		servDTO.setException(e);
		String message=logIdentifier+"Exception for ServiceProductId :"+servDTO.getServiceProductId()+" and ROW Id :"+servDTO.getRowId();
		System.out.println(message);
		Utility.LOG(true,true,message);
		servDTO.setExceptionMessage(message);
		return -1;
	}
		}
public void sendPackageToFx( PackageDTO Package, String logIdentifier) {

			
			int returnStatus = 0;
			OrderObjectData order = null;
			OrderBean orderBean=null;
			try {
				//
					AccountXIDObjectData aod =this.accountFind(Package.getAcctExternalId());
					if(aod==null)
					{
						String msg=logIdentifier+"Cannot create package since account is not present in FX . " +
										"\n  -For Package Row Id :"+Package.getRowId()+
										",\n  -Account Not Found was :"+Package.getAcctExternalId();
						//AppConstants.IOES_LOGGER.info(msg);
						Package.setReturnStatus(-1);
						Package.setException(new Exception(msg));
						Package.setExceptionMessage(msg);
						return;
					}
				
				Integer acctInternalId=aod.getAccountInternalId();
				
				OrderBean objOrderBean = new OrderBean(settings);
				// create order
				order = new OrderObjectData();
				
				
				order.setAccountInternalId(acctInternalId);
				order.setOrderStatusId(new Integer("10"));
				order.setGenerateWorkflow(new Boolean(false));
				order = objOrderBean.create(this.context,order);
				order.setAccountInternalId(acctInternalId);


				
				
				OrderedPackageBean orderedPackageBean=new OrderedPackageBean(settings); 		
				ProductPackageObjectData productPackageObjectData=new ProductPackageObjectData();
				
				productPackageObjectData.setActiveDt(Package.getPackageActiveDate());
				if(customUATDate==1)
				{
					productPackageObjectData.setActiveDt(uatDate);
				}
				productPackageObjectData.setPackageId(Package.getPackageid()==null?null:new Integer(Package.getPackageid()));		
				productPackageObjectData.setParentAccountInternalId(acctInternalId);
				 
				OrderedPackageCreateOutputData packageCreateOutputData=orderedPackageBean.create(this.context, productPackageObjectData, order, null, true, true);
					
				returnStatus = 1;
				Package.setPackage_inst_id(new Long(packageCreateOutputData.getProductPackage().getPackageInstId()));
				Package.setPackage_inst_id_serv((new Long(packageCreateOutputData.getProductPackage().getPackageInstIdServ())));
				
				OrderObjectKeyData orderKey = order.getKey();
				Package.setTokenid(order.getOrderId().toString());
				
//				 Commit the Order
				
				objOrderBean.commit(context, orderKey);
				
				
			}catch (Exception e ){
				
				Utility.LOG(true,true,e,null);
				returnStatus = -1;
				Package.setException(e);
				String message=logIdentifier+"Exception for packageid :"+Package.getPackageid()+" and Row Id :"+Package.getRowId();
				Utility.LOG(true,true,message);
				Package.setExceptionMessage(message);
				
				if(order!=null && orderBean!=null)
				{
					//OrderObjectKeyData orderKey = order.getKey();
					//OrderBean orderBean = new OrderBean(settings);
					try {
						
						orderBean.cancel(context, order.getKey());
						message=logIdentifier+"Order Cancelled for Service Product Id:"+Package.getPackageid()+" at :"+new Date();
						AppConstants.IOES_LOGGER.info(message);
						Package.setExceptionMessage(message);
					} catch (Exception e1) {
						Utility.LOG(true,true,e1,null);
					}
				}
				
			}
			Package.setReturnStatus(returnStatus);

			}
		
		public void sendComponentToFx(ComponentDTO componentDTO, String logIdentifier){		

			int returnStatus = 0;
			OrderObjectData order=null;		
			OrderBean objOrderBean=null;
			try
			{
					
				AccountXIDObjectData aod =this.accountFind(componentDTO.getAccountExternalId());
				if(aod==null)
				{
					String msg=logIdentifier+"Cannot create component since account is not present in FX . " +
									"\n  -For RowId :"+componentDTO.getRowId()+
									",\n  -Account Not Found was :"+componentDTO.getAccountExternalId();
					//AppConstants.IOES_LOGGER.info(msg);
					componentDTO.setReturnStatus(-1);
					componentDTO.setException(new Exception(msg));
					componentDTO.setExceptionMessage(msg);
					return;
				}
				Integer acctInternalId=aod.getAccountInternalId();
				
				objOrderBean = new OrderBean(settings);
				// create order
				order = new OrderObjectData();
				
				
				order.setAccountInternalId(acctInternalId);
				order.setOrderStatusId(new Integer("10"));
				order.setGenerateWorkflow(new Boolean(false));
				order.extendedData=Utility.getFxExtendedDataMap(componentDTO.getOrderExtendedData());
				order = objOrderBean.create(this.context,order);
				order.setAccountInternalId(acctInternalId);		
				
				FxOrderTrackerTask.insertOrderTrackerStatus(FxOrderTrackerConst.COMPONENT,order.getOrderId().toString(),componentDTO.getRowId(),"INIT");
				
				OrderedComponentBean orderedComponentBean=new OrderedComponentBean(settings);
				ComponentObjectData componentObjectData=new ComponentObjectData();
				
				componentObjectData.setActiveDt(componentDTO.getBillingActiveDate());
				if(customUATDate==1)
				{
					componentObjectData.setActiveDt(uatDate);
				}
				componentObjectData.setComponentId(new Integer(componentDTO.getComponentid()));
				
				componentObjectData.setPackageId(new Integer(componentDTO.getPackageid()));
				componentObjectData.setPackageInstId(new Integer(componentDTO.getPackage_inst_id()));
				componentObjectData.setPackageInstIdServ(new Integer(componentDTO.getPackage_inst_id_serv()));
				
				if(FxSchedulerTasksForComponent.COMPONENT_LEVEL_ACCOUNT.equals(componentDTO.getFx_Level()))
				{
					componentObjectData.setAccountExternalId(componentDTO.getAccountExternalId());
				}
				else
				{
					componentObjectData.setParentServiceInternalId(new Integer(componentDTO.getSubScrNo()));
					componentObjectData.setParentServiceInternalIdResets(componentDTO.getSubScrNoReset());
				}
				
				OrderedComponentCreateOutputData orderedComponentCreateOutputData= orderedComponentBean.create(this.context, componentObjectData, order, null, null, null, true, true);
				
					
				
				
				returnStatus = 1;
				
				componentDTO.setComponent_inst_id(orderedComponentCreateOutputData.getComponent().getComponentInstId().toString());
				componentDTO.setComponent_inst_id_serv((orderedComponentCreateOutputData.getComponent().getComponentInstIdServ().toString()));
				
				FxOrderTrackerTask.updateTfxOrderTracker(FxOrderTrackerConst.COMPONENT,
						order.getOrderId().toString(),
						componentDTO.getRowId(),
						componentDTO.getComponent_inst_id().toString() ,
						componentDTO.getComponent_inst_id_serv().toString(),
						null,
						"FORCOMMIT");
				
				OrderObjectKeyData orderKey = order.getKey();
				componentDTO.setTokenid(order.getOrderId().toString());
				
//				 Commit the Order
				
				objOrderBean.commit(context, orderKey);
			}
			catch(Exception e)
			{

				
				Utility.LOG(true,true,e,null);
				returnStatus = -1;
				componentDTO.setException(e);
				String message=logIdentifier+"Exception for componentId :"+componentDTO.getComponentid()+" and Row Id :"+componentDTO.getRowId();
				Utility.LOG(true,true,message);
				componentDTO.setExceptionMessage(message);
				
				if(order!=null && objOrderBean!=null)
				{
					//OrderObjectKeyData orderKey = order.getKey();
					//OrderBean orderBean = new OrderBean(settings);
					try {
						
						objOrderBean.cancel(context, order.getKey());
						message=logIdentifier+"Order Cancelled for componentId:"+componentDTO.getComponentid()+" at :"+new Date();
						AppConstants.IOES_LOGGER.info(message);
						componentDTO.setExceptionMessage(message);
					} catch (Exception e1) {
						Utility.LOG(true,true,e1,null);
					}
				}
				
			
				
			}
			componentDTO.setReturnStatus(returnStatus);					
					
			
		}

		public int AccountModifyForTestPage(String accountInternalId) throws Exception{
			int returnStatus=0;
			
			try {
				
				
				AccountXIDObjectData axd = null;		
				
				AccountObjectData aod = new AccountObjectData();
				AccountObjectKeyData aokd = new AccountObjectKeyData();
				aokd.setAccountInternalId(new Integer(accountInternalId));
				
				AccountBean ab = new AccountBean(settings);
				
				aod.setMktCode(999);
				aod.setNoBill(true);
				
				aod.setKey(aokd);
				
				
				axd = ab.update(context,aod);
				
				returnStatus = 1;
	
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
				
			return returnStatus;
		}
		
		public void disconnectComponent(ComponentDTO component, String logIdentifier) 
		{ 
			int returnStatus = 0; // status 0 represents failure by default
			OrderBean objOrderBean = null;		
			OrderObjectData order = null;	
			ComponentObjectData componentdata  = new ComponentObjectData();
			OrderedComponentDisconnectOutputData disconnectComponentResponse = null;
			ComponentObjectBaseKeyData componentkey = new  ComponentObjectBaseKeyData();
			OrderedComponentBean ocb = new OrderedComponentBean(settings);
			
			
			
			try
			{
					
				AccountXIDObjectData aod =this.accountFind(component.getAccountExternalId());
				if(aod==null)
				{
					String msg=logIdentifier+"Cannot DISCONNECT since account is not present in FX . " +
									"\n  -For Index_key :"+component.getIndex_key()+
									",\n  -Account Not Found was :"+component.getAccountExternalId();
					//AppConstants.IOES_LOGGER.info(msg);
					component.setReturnStatus(-1);
					component.setException(new Exception(msg));
					component.setExceptionMessage(msg);
					return;
				}
				Integer acctInternalId=aod.getAccountInternalId();
				
				objOrderBean = new OrderBean(settings);
				// create order
				order = new OrderObjectData();		
				
				order.setAccountInternalId(acctInternalId);
				order.setOrderStatusId(new Integer("10"));
				order.setGenerateWorkflow(new Boolean(false));
				order.extendedData=Utility.getFxExtendedDataMap(component.getOrderExtendedData());
				order = objOrderBean.create(context,order);
				order.setAccountInternalId(acctInternalId);				
				
				
				
				componentkey.setComponentInstId(new Integer(component.getComponent_inst_id()));
				componentkey.setComponentInstIdServ(new Integer(component.getComponent_inst_id_serv()));			
				componentdata.setKey(componentkey);
				
				disconnectComponentResponse=ocb.disconnect(context,
								componentdata,
								order,
								null,
								null,
								null,
								new Date(component.getComponent_disconnection_date().getTime()),
								null,
								null);
				
				
				
				returnStatus = 1;// status 3 represents success
				OrderObjectKeyData orderKey = order.getKey();
				System.out.println("Order ID FOR THE DISCONNECTED COMPONENT"+orderKey.getOrderId());	
				component.setTokenid(order.getOrderId().toString());
				

				
				objOrderBean.commit(context, orderKey);
			}
			catch(Exception e) {

				
				Utility.LOG(true,true,e,null);
				returnStatus = -1;
				String message=logIdentifier+"Exception for componentId :"+component.getComponentid()+" and Row Id :"+component.getRowId();
				component.setException(e);
				Utility.LOG(true,true,message);
				component.setExceptionMessage(message);
				
				if(order!=null && objOrderBean!=null)
				{
					//OrderObjectKeyData orderKey = order.getKey();
					//OrderBean orderBean = new OrderBean(settings);
					try {
						
						objOrderBean.cancel(context, order.getKey());
						message=logIdentifier+"Exception for componentId :"+component.getComponentid()+" and Row Id :"+component.getRowId();
						AppConstants.IOES_LOGGER.info(message);
						component.setExceptionMessage(message);
					} catch (Exception e1) {
						Utility.LOG(true,true,e1,null);
					}
				}
				
			
				
			}
			component.setReturnStatus(returnStatus);
		}
		
		
		
public void createService_ExternalId_LOCDATE(Integer serviceInternalId, BigInteger viewId,Date date,String value) throws Exception{
			
			CustomerIdEquipMapBean customerIdEquipMapBean=new CustomerIdEquipMapBean(this.settings);
			CustomerIdEquipMapObjectData serviceIdData_un =new CustomerIdEquipMapObjectData();
			
			serviceIdData_un.setServiceInternalId(serviceInternalId);
			serviceIdData_un.setServiceInternalIdResets(0);
			serviceIdData_un.setViewId(viewId);
			serviceIdData_un.setActiveDate(date);
			if(customUATDate==1)
			{
				serviceIdData_un.setActiveDate(uatDate);
			}
			serviceIdData_un.setServiceExternalId(value);
			serviceIdData_un.setServiceExternalIdType(4986);//4994//105
			//serviceIdData.setIsCurrent(true);
			serviceIdData_un.setViewStatus(2);
			
			CustomerIdEquipMapObjectData responseCustomerIdEquipMapObjectData= customerIdEquipMapBean.create(
					context, // BSDMSessionContext
					serviceIdData_un // CustomerIdEquipMap to be created
					); // Return order objects?
		}

		public void updateAccount(AcctDTO account, String logIdentifier) {
			
			
			
			try {
				
				AccountXIDObjectData aod2 =this.accountFind(account.getAcctExternalId());
				if(aod2==null)
				{
					String msg=logIdentifier+"Cannot update Account since account is not present in FX . " ;
					AppConstants.IOES_LOGGER.info(msg);
					account.setReturnStatus(-1);
					account.setException(new Exception(msg));
					return;
				}
				
				Integer acctInternalId=aod2.getAccountInternalId();
				
					AccountXIDObjectData axd = null;		
					
					AccountObjectData aod = new AccountObjectData();
					AccountObjectKeyData aokd = new AccountObjectKeyData();
					aokd.setAccountInternalId(new Integer(acctInternalId));
					AccountBean ab = new AccountBean(settings);
					Map map = new HashMap();
					
					map=Utility.getFxExtendedDataMap(account.getExtendedData());
					
					aod.setKey(aokd);
					aod.extendedData=map;
					axd = ab.update(context,aod);
			
					account.setReturnStatus(1);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void  redirectCharges(ChargeRedirectDTO chargeRedirectdto,String logIdentifier)	
		{
				
			int returnStatus = 0;
			
			//bean creation
				OpenItemIdMapBean oiimb = new OpenItemIdMapBean(this.settings);				
				// create data object
				OpenItemIdMapObjectData oiimod = new OpenItemIdMapObjectData();
				
				try {
					AccountXIDObjectData aod =this.accountFind(chargeRedirectdto.getAccountExternalid());
					if(aod==null)
					{
						String msg=logIdentifier+"Cannot redirect charges since account is not present in FX . " +
										"\n  -For RowId :"+chargeRedirectdto.getRowId()+
										",\n  -Account Not Found was :"+chargeRedirectdto.getAccountExternalid();
						//AppConstants.IOES_LOGGER.info(msg);
						chargeRedirectdto.setReturnStatus(-1);
						chargeRedirectdto.setException(new Exception(msg));
						chargeRedirectdto.setExceptionMessage(msg);
						return;
					}
											
						oiimod.setAccountInternalId(chargeRedirectdto.getAccountno());
						oiimod.setActiveDt(chargeRedirectdto.getRedirectionActiveDate());
						if(customUATDate==1)
						{
							oiimod.setActiveDt(uatDate);
						}
						oiimod.setBalanceAccountExternalId(chargeRedirectdto.getBalanceAccountExternalid());
						//oiimod.setBalanceAccountExternalIdType(1);
						oiimod.setBalanceAccountInternalId(chargeRedirectdto.getBalanceAccountNo());
						oiimod.setChargeElementType(0);
						oiimod.setChargeElementValue(0);
						oiimod.setServiceInternalId(chargeRedirectdto.getSubscrNo());
						oiimod.setServiceInternalIdResets(chargeRedirectdto.getSubscrNoReset());
						//oiimod.setOpenItemId(1);
						oiimod=oiimb.create(this.context, oiimod);
						
						returnStatus=1;
						
						chargeRedirectdto.setTrackingid(oiimod.getTrackingId());
						chargeRedirectdto.setTrackingIdReset(oiimod.getTrackingIdServ());
						
						//System.out.println(oiimod.getTrackingId());				
					
				} catch (Exception e) {
					Utility.LOG(true,true,e,null);
					returnStatus = -1;
					chargeRedirectdto.setException(e);
					String message=logIdentifier+"Exception for Account NO :"+chargeRedirectdto.getAccountno()+" and Row Id :"+chargeRedirectdto.getRowId();
					Utility.LOG(true,true,message);
					chargeRedirectdto.setExceptionMessage(message);
					
					
				}
			
				chargeRedirectdto.setReturnStatus(returnStatus);
				
			
		}
		
		
		
		public int getFxOrderStatus(String orderno) throws Exception{
			
			OrderLookupBean 		 osb 	= new OrderLookupBean(this.settings);		
			OrderLookupObjectKeyData osokd 	= new OrderLookupObjectKeyData();
			int status = 0;
			try{
				
				osokd.setOrderId(new BigInteger(orderno));
				this.context.setServerId(ApplicationFlags.FX_AES_CUSTOMER_SERVER_ID);
				OrderLookupObjectData olod = osb.get(this.context,osokd);			
				status	=	olod.getOrderStatusId();
				//System.out.println(olod.getOrderId()+"with order status"+olod.getOrderStatusId());
				
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
			
			return status;
		}
		
		
		public int cancelOrder(String orderno)throws Exception{
			
			OrderObjectKeyData ookd	= 	new OrderObjectKeyData();		
			OrderBean objOrderBean	=	new OrderBean(this.settings);
			int status = 0; 
			try{
				ookd.setOrderId(new BigInteger(orderno));
				this.context.setServerId(ApplicationFlags.FX_AES_CUSTOMER_SERVER_ID);
				objOrderBean.cancel(this.context, ookd);
				status = getFxOrderStatus(orderno);
				if (status != FxOrderTrackerConst.CANELLED){
					throw new Exception("Order Failed to Cancel");
				}
			}catch(Exception e) {
				Utility.LOG(true, true, e, "");
				throw e;
			}
			
			return status;
		}
public void updateAccountData(UpdateAccountDto updateAccount, String logIdentifier) {			
			
			try {
				
				AccountXIDObjectData aod2 =this.accountFind(updateAccount.getAcctExternalId());
				if(aod2==null)
				{
					String msg=logIdentifier+"Cannot update Account since account is not present in FX . " ;
					AppConstants.IOES_LOGGER.info(msg);
					updateAccount.setReturnStatus(-1);
					updateAccount.setException(new Exception(msg));
					updateAccount.setException_message(logIdentifier);
					return;
				}
				
					Integer acctInternalId=aod2.getAccountInternalId();
				
					AccountXIDObjectData axd = null;					
					AccountObjectData aod = new AccountObjectData();
					AccountObjectKeyData aokd = new AccountObjectKeyData();
					
					aokd.setAccountInternalId(new Integer(acctInternalId));
					AccountBean ab = new AccountBean(settings);
					
					
					aod.setKey(aokd);
					aod.setBillCompany(updateAccount.getPartyName());
					aod.setAcctSegId(new Integer(updateAccount.getRegionId()));
					aod.setAccountCategory(new Integer(updateAccount.getAccountCaegory()));
					aod.extendedData=updateAccount.getExtendedData();
					axd = ab.update(context,aod);
			
					updateAccount.setReturnStatus(1);
					
			}catch (Exception e) {
				Utility.LOG(true, false, e, logIdentifier);
				updateAccount.setException(e);
				updateAccount.setException_message(logIdentifier);
				updateAccount.setReturnStatus(-1);
			}
		}
}
