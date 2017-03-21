package com.ibm.ioes.dao;
//Tag Name Resource Name  Date		CSR No			Description 
//[001]	   ROHIT VERMA	  8-Feb-11	00-05422		Add Is Demo Column 
//[002]	   Vishwa	  7-Nov-11	00-05422		Adding New Columns 
//[003]	 Kalpana	    30-March-13	HYPR22032013003	COPC region wise change
//[004]  Gunjan Singla  10-Oct-14    IN327860       Change in date format
//[005]   Neha Maheshwari    added service segment    NORTH STAR ACCOUNT TAGGING
//[007] Anil Kumar 15-May-2015 20141219_R2_020936 Add New column COPC Approval date in Query Form
//[008] Priya Gupta	05-Aug-2015 20141219-R2-020936-Billing Efficiency Program_drop3 removed customer segment drop down and list out order no. according to resp id..
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.ibm.ioes.beans.UniversalWorkQueueDto;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.PagingDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.utilities.Utility;
import com.ibm.ioes.forms.UniversalWorkQueueFormBean;

public class UniversalWorkQueueDao {

	
	//public static String sqlGet_UniversalWorkQueueList = "{CALL IOE.SPGETUNIVERSALWORKQUEUELIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//To Fetch UNIVERSAL WORK QUEUE List from Database
	//[008]
	public static String sqlGet_UniversalWorkQueueList = "{CALL IOE.SPGETUNIVERSALWORKQUEUELIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlGetSourceName = "{call IOE.GETSOURCENAME()}";
	public static String sqlGetOrderTypes = "{call IOE.GETORDERTYPES()}";
	public static String sqlGetAllExistingOrderDetails = "{call IOE.SP_GET_EXISTINGORDERSDETAILS(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlCreateNewOrderWithExisting="{call IOE.SP_CREATE_CHANGE_ORDER(?,?,?,?,?,?,?,?,?,?)}";
	public static String sqlCreateNewOrderWithExisting_NewVersion="{call IOE.SP_CREATE_CHANGE_ORDER_NEW_VERSION(?,?,?,?,?,?,?)}";
	public static String sqlGetqueryOptionList="{call IOE.SP_GET_QUERYMASTERDATA()}";
	public static String sqlGetQueryFormValueList="{call IOE.QUERYFORM(?,?,?,?,?,?)}";
	public static String sqlGetSubChangeTypes = "{call IOE.GETSUBCHANGETYPES()}";
	//public static String sqlGetCurrencyName = "{call IOE.spGetCurrency(?)}";
	public static String sqlGetCustomerSegment = "{call IOE.GETCUSTOMERSEGMENT()}";
	public static String sqlGetRegionName = "{call IOE.GETREGIONLIST()}";
    //[005] Start
	public static String sqlGetServiceSegmentName = "{call IOE.GETSERVICESEGMENT()}";
    //[005] End
	public static String sqlGetIndustrySegmentName = "{call IOE.GETINDUSTRYSEGMENT()}";
	public static String sqlGetOrderStage = "{call IOE.GETORDERSTAGE()}";
	
	public String className=this.getClass().getSimpleName();
	
	// Modified by Ramana  start
	public ArrayList getUniversalWorkQueueList(UniversalWorkQueueDto objUniversalWorkQueueDto,UserInfoDto objUserDto) throws Exception{
		//Added by nagarjuna
		String methodName="getUniversalWorkQueueList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		ArrayList<UniversalWorkQueueDto> alUniversalWorkQueueList = new ArrayList<UniversalWorkQueueDto>();
		Connection conn = null;
		ResultSet rsUniversalWorkQueue = null;
		CallableStatement csGetUniversalWorkQueue =null;
		//java.util.Date date = df.parse(objUniversalWorkQueueDto.getSearchfromDate()); 
		//java.util.Date date1 = df.parse(objUniversalWorkQueueDto.getSearchToDate()); 
		//DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date date = df.parse(dto.get); 
		//String a=df1.format(date);
		//String a1=df1.format(date1);
		
		String RoleName="";
		try {			
			
			conn = DbConnection.getConnectionObject();
			csGetUniversalWorkQueue = conn.prepareCall(sqlGet_UniversalWorkQueueList);			
			
			String searchCRMOrder = objUniversalWorkQueueDto.getSearchCRMOrder();
			String searchAccountNo  = objUniversalWorkQueueDto.getSearchAccountNo();
			String searchAccountName=objUniversalWorkQueueDto.getSearchAccountName();
			String searchfromDate=objUniversalWorkQueueDto.getSearchfromDate();
			String searchToDate = objUniversalWorkQueueDto.getSearchToDate();
			String searchSource=objUniversalWorkQueueDto.getSearchSource();
			String searchQuoteNumber=objUniversalWorkQueueDto.getSearchQuoteNumber();
			String searchOrderType=objUniversalWorkQueueDto.getSearchOrderType();
			String searchSubChangeType=objUniversalWorkQueueDto.getSearchSubchangeType();
			String searchRegion= objUniversalWorkQueueDto.getSearchRegionName();
			String searchOrderStage= objUniversalWorkQueueDto.getSearchOrderStageName();
			String searchDemoType=objUniversalWorkQueueDto.getSearchDemoType();
			String searchPartyNumber=objUniversalWorkQueueDto.getSearchPartyNumber();
            //[005] Start
			String searchServiceSegment = objUniversalWorkQueueDto.getSearchServiceSegmentName();
            //[005] End
			String searchIndustrySegment=objUniversalWorkQueueDto.getSearchIndustrySegmentName();
			String searchAmFromDate=objUniversalWorkQueueDto.getSearchAmFromDate();
			String searchAmToDate=objUniversalWorkQueueDto.getSearchAmToDate();
			String searchPmFromDate=objUniversalWorkQueueDto.getSearchPmFromDate();
			String searchPmToDate=objUniversalWorkQueueDto.getSearchPmToDate();
			String searchCopcFromDate=objUniversalWorkQueueDto.getSearchCopcToDate();
			String searchCopcToDate=objUniversalWorkQueueDto.getSearchCopcToDate();
			String searchCustomerSegment=objUniversalWorkQueueDto.getSearchCustomerSegmentName();
			
			if (searchCRMOrder == null || searchCRMOrder.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(1, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(1, searchCRMOrder);
			}

			if (searchAccountNo == null || searchAccountNo.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(2, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(2, searchAccountNo);
			}

			if (searchAccountName == null || searchAccountName.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(3, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(3, searchAccountName);
			}
			
			if (searchfromDate == null || searchfromDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(4, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(4, searchfromDate);
			}
			
			if (searchToDate == null || searchToDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(5, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(5, searchToDate);
			}			
			if (searchSource == null || searchSource.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(6, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(6, searchSource);
			}
			
			if (searchQuoteNumber == null || searchQuoteNumber.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(7, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(7, searchQuoteNumber);
			}
			

			if (searchOrderType == null || searchOrderType.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(8, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(8, searchOrderType);
			}
			
			if (searchOrderStage == null || searchOrderStage.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(9, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(9, searchOrderStage);
			}
			
			if (searchRegion == null || searchRegion.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(10, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(10, searchRegion);
			}
			
			if (searchDemoType == null || searchDemoType.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(11, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(11, searchDemoType);
			}
			
			if (searchPartyNumber == null || searchPartyNumber.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(12, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(12, searchPartyNumber);
			}
			
			if (searchIndustrySegment == null || searchIndustrySegment.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(13, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(13, searchIndustrySegment);
			}
			
			if (searchAmFromDate == null || searchAmFromDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(14, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(14, searchAmFromDate);
			}
			
			if (searchAmToDate == null || searchAmToDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(15, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(15, searchAmToDate);
			}
			
			if (searchPmFromDate == null || searchPmFromDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(16, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(16, searchPmFromDate);
			}
			
			if (searchPmToDate == null || searchPmToDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(17, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(17, searchPmToDate);
			}
			
			if (searchCopcFromDate == null || searchCopcFromDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(18, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(18, searchCopcFromDate);
			}
			
			if (searchCopcToDate == null || searchCopcToDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(19, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(19, searchCopcToDate);
			}

			if (searchCustomerSegment == null || searchCustomerSegment.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(20, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(20, searchCustomerSegment);
			}
			
			if(objUniversalWorkQueueDto.getIsBillingOrder().equalsIgnoreCase("1"))
			{
			   csGetUniversalWorkQueue.setLong(21,0);
			   csGetUniversalWorkQueue.setLong(22,0);
			}
			else
			{
			   csGetUniversalWorkQueue.setLong(21,Long.parseLong(objUniversalWorkQueueDto.getCurrentRole()));
			   csGetUniversalWorkQueue.setLong(22,Long.parseLong(objUserDto.getEmployeeId()));
			}
			if (searchSubChangeType == null || searchSubChangeType.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(23, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(23, searchSubChangeType);
			}
			
			PagingSorting pagingSorting = objUniversalWorkQueueDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			csGetUniversalWorkQueue.setString(24, pagingSorting.getSortByColumn());// columnName
			csGetUniversalWorkQueue.setString(25, PagingSorting.DB_Asc_Desc1(pagingSorting
					.getSortByOrder()));// sort order
			csGetUniversalWorkQueue.setInt(26, pagingSorting.getStartRecordId());// start index
			csGetUniversalWorkQueue.setInt(27, pagingSorting.getEndRecordId());// end index
			csGetUniversalWorkQueue.setInt(28, (pagingSorting.isPagingToBeDone() ? 1 : 0));// end
			//by kalpana for copc region change HYPR22032013003
			if(objUniversalWorkQueueDto.getCurrentRole().equalsIgnoreCase("3")){
				if(objUserDto.getRegionId() !=null){
					csGetUniversalWorkQueue.setString(29, objUserDto.getRegionId());
				}else{
					csGetUniversalWorkQueue.setString(29, "0");
				}
				
			}else{
				csGetUniversalWorkQueue.setNull(29, java.sql.Types.VARCHAR);
			}
			
			//[005] Start
			if (searchServiceSegment == null || searchServiceSegment.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(30, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(30, searchServiceSegment);
			}
			//[008]
			csGetUniversalWorkQueue.setLong(31, objUserDto.getRespId());
			//[005] End
			//end
			// index			
			rsUniversalWorkQueue=csGetUniversalWorkQueue.executeQuery();
			int countFlag = 0;
			int recordCount = 0;			
			while (rsUniversalWorkQueue.next()!= false) {
				countFlag++;
				
				UniversalWorkQueueDto objUniversalWorkQueueListDto = new UniversalWorkQueueDto();
				objUniversalWorkQueueListDto.setSearchCRMOrder(Utility.fnCheckNull(rsUniversalWorkQueue
						.getInt("ORDERNO")));
				objUniversalWorkQueueListDto.setSearchOrderType(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("ORDERTYPE")));
				objUniversalWorkQueueListDto.setOrderStage(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("STAGE")));
				SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
				//[004] start

				objUniversalWorkQueueListDto.setSearchfromDate(Utility.fnCheckNull(Utility.showDate_Report(rsUniversalWorkQueue.getDate("ORDERDATE"))));
				//[004] end
				objUniversalWorkQueueListDto.setSearchAccountNo(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("ACCOUNTID")));
				objUniversalWorkQueueListDto.setRegionName(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("REGIONNAME")));	
				objUniversalWorkQueueListDto.setSearchAccountName(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("ACCOUNTNAME")));
				objUniversalWorkQueueListDto.setSearchSource(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("SOURCE")));
				objUniversalWorkQueueListDto.setSearchQuoteNumber(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("QUOTENO")));
				//[002]	Start
				objUniversalWorkQueueListDto.setDemoType(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("ISDEMO")));
				objUniversalWorkQueueListDto.setTaskNo(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("TASKID")));
				objUniversalWorkQueueListDto.setTaskStatus("Open");
				objUniversalWorkQueueListDto.setPartyNo(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("PARTY_ID")));
               //[005] Start
				objUniversalWorkQueueListDto.setServiceSegment(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("acd")));
               //[005] End
				objUniversalWorkQueueListDto.setIndustrySegment(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("VERTICAL_DETAILS")));
				if(null!=rsUniversalWorkQueue.getDate("AM_APPROVAL_DATE"))
				//[004] start
				objUniversalWorkQueueListDto.setAmApprovalDate(Utility.fnCheckNull(Utility.showDate_Report(rsUniversalWorkQueue.getDate("AM_APPROVAL_DATE"))));
				//[004] end
				if(null!=rsUniversalWorkQueue.getDate("PM_APPROVAL_DATE"))
				//[004] start
				objUniversalWorkQueueListDto.setPmApprovalDate(Utility.fnCheckNull(Utility.showDate_Report(rsUniversalWorkQueue.getDate("PM_APPROVAL_DATE"))));
				//[004] end
				if(null!=rsUniversalWorkQueue.getDate("COPC_APPROVAL_DATE"))
				//[004] start
				objUniversalWorkQueueListDto.setCopcApprovalDate(Utility.fnCheckNull(Utility.showDate_Report(rsUniversalWorkQueue.getDate("COPC_APPROVAL_DATE"))));
				//[004] end
				objUniversalWorkQueueListDto.setCustomerSegment(Utility.fnCheckNull(rsUniversalWorkQueue.getString("DESCRIPTION")));
				objUniversalWorkQueueListDto.setSubChangeType(Utility.fnCheckNull(rsUniversalWorkQueue.getString("NAME_SUBTYPE")));
				/*vijay start 
				 * added for fetching total po amount
				 */
				objUniversalWorkQueueListDto.setPoAmount(rsUniversalWorkQueue.getDouble(("tot_amount")));
				objUniversalWorkQueueListDto.setAccMangerName(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("actmngrname")));
				//Vijay end



				//lawkush Start				
				objUniversalWorkQueueListDto.setAgingDaysHours(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("AGING")));
				//lawkush End
//				[002]	End
				
				if (pagingSorting.isPagingToBeDone()) {
					recordCount = rsUniversalWorkQueue.getInt("FULL_REC_COUNT");
				}

				alUniversalWorkQueueList.add(objUniversalWorkQueueListDto);
			}
			pagingSorting.setRecordCount(recordCount);		
		} catch (Exception e) {
			 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rsUniversalWorkQueue);
				DbConnection.closeCallableStatement(csGetUniversalWorkQueue);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"Exception in Closing Connection    : "+e);//Added by Nagarjuna
				//e.printStackTrace();
				throw new Exception("No Record Found");
			}
}

		return alUniversalWorkQueueList;
}
	//Modified by Ramana end
	
	public ArrayList<UniversalWorkQueueDto> getSourceName()
	{
//		Added by nagarjuna
		String methodName="getSourceName",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		PreparedStatement getSourceName =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listSourceName = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			getSourceName= connection.prepareCall(sqlGetSourceName);
			rs = getSourceName.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setSearchSourceName(rs.getString("SOURCENAME"));
			 listSourceName.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(getSourceName);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true,true,"Exception at Closing Connection    : "+e);//Added by Nagarjuna
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listSourceName;
	}
	
	//Ramana
	public ArrayList<UniversalWorkQueueDto> getOrderType()
	{
//		Added by nagarjuna
		String methodName="getOrderType",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		PreparedStatement getOrderType =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listOrderType = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			getOrderType= connection.prepareCall(sqlGetOrderTypes);
			rs = getOrderType.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setOrderType(rs.getString("ORDERTYPE"));
			 listOrderType.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			 Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(getOrderType);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true,true,"Exception at Closing Connection    : "+e);//Added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listOrderType;
	}
	//Ramana
	/*public ArrayList<UniversalWorkQueueDto> getCurrency()
	{
		Connection connection =null;
		PreparedStatement getSourceName =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listCurrency = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			getSourceName= connection.prepareCall(sqlGetCurrencyName);
			rs = getSourceName.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setSearchCurrencyName(rs.getString("CURNAME"));
			 listCurrency.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listCurrency;
	}*/
	
//	created by:Anil Kumar
	public ArrayList getAllExistingOrdersDetails(UniversalWorkQueueDto objUniversalWorkQueueDto,UserInfoDto objUserDto) throws Exception{
//		Added by nagarjuna
		String methodName="getAllExistingOrdersDetails",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		ArrayList<UniversalWorkQueueDto> alUniversalWorkQueueList = new ArrayList<UniversalWorkQueueDto>();
		Connection conn = null;
		ResultSet rsUniversalWorkQueue = null;
		CallableStatement csGetUniversalWorkQueue =null;
						
		try {			
			
			conn = DbConnection.getConnectionObject();	
			
			String searchCRMOrder = objUniversalWorkQueueDto.getSearchCRMOrder();
			String searchAccountNo  = objUniversalWorkQueueDto.getSearchAccountNo();
			String searchAccountName=objUniversalWorkQueueDto.getSearchAccountName();
			String searchfromDate=objUniversalWorkQueueDto.getSearchfromDate();
			String searchToDate = objUniversalWorkQueueDto.getSearchToDate();
			String searchSource=objUniversalWorkQueueDto.getSearchSource();
			String searchQuoteNumber=objUniversalWorkQueueDto.getSearchQuoteNumber();
			String searchOrderType=objUniversalWorkQueueDto.getSearchOrderType();
			
			csGetUniversalWorkQueue = conn.prepareCall(sqlGetAllExistingOrderDetails);
			
			if (searchCRMOrder == null || searchCRMOrder.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(1, java.sql.Types.BIGINT);
			} else {
				csGetUniversalWorkQueue.setLong(1, Long.parseLong(searchCRMOrder));
			}

			if (searchAccountNo == null || searchAccountNo.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(2, java.sql.Types.BIGINT);
			} else {
				csGetUniversalWorkQueue.setLong(2, Long.parseLong(searchAccountNo));
			}

			if (searchAccountName == null || searchAccountName.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(3, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(3, searchAccountName);
			}
			
			if (searchfromDate == null || searchfromDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(4, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(4, searchfromDate);
			}
			
			if (searchToDate == null || searchToDate.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(5, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(5, searchToDate);
			}			
			if (searchSource == null || searchSource.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(6, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(6, searchSource);
			}
															
			if (searchQuoteNumber == null || searchQuoteNumber.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(7, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(7, searchQuoteNumber);
			}			

			if (searchOrderType == null || searchOrderType.trim().equals("")) {
				csGetUniversalWorkQueue.setNull(8, java.sql.Types.VARCHAR);
			} else {
				csGetUniversalWorkQueue.setString(8, searchOrderType);
			}
		
			PagingSorting pagingSorting = objUniversalWorkQueueDto.getPagingSorting();
			pagingSorting.sync();// To calculate start index and Enc Index

			csGetUniversalWorkQueue.setString(9, pagingSorting.getSortByColumn());// columnName
			csGetUniversalWorkQueue.setString(10, PagingSorting.DB_Asc_Desc(pagingSorting.getSortByOrder()));// sort order
			csGetUniversalWorkQueue.setInt(11, pagingSorting.getStartRecordId());// start index
			csGetUniversalWorkQueue.setInt(12, pagingSorting.getEndRecordId());// end index			
							
			rsUniversalWorkQueue=csGetUniversalWorkQueue.executeQuery();
			int countFlag = 0;
			int recordCount = 0;			
			while (rsUniversalWorkQueue.next()!= false) {
				countFlag++;
				
				UniversalWorkQueueDto objUniversalWorkQueueListDto = new UniversalWorkQueueDto();
				objUniversalWorkQueueListDto.setSearchCRMOrder(Utility.fnCheckNull(rsUniversalWorkQueue
						.getInt("ORDERNO")));
				objUniversalWorkQueueListDto.setSearchOrderType(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("ORDERTYPE")));
				SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
				objUniversalWorkQueueListDto.setSearchfromDate(Utility.fnCheckNull(sdf.format(rsUniversalWorkQueue.getDate("ORDERDATE"))));
				objUniversalWorkQueueListDto.setSearchAccountNo(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("ACCOUNTID")));
				objUniversalWorkQueueListDto.setSearchAccountName(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("ACCOUNTNAME")));
				objUniversalWorkQueueListDto.setSearchSource(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("SOURCE")));
				objUniversalWorkQueueListDto.setSearchQuoteNumber(Utility.fnCheckNull(rsUniversalWorkQueue
						.getString("QUOTENO")));								
								
				recordCount = rsUniversalWorkQueue.getInt("FULL_REC_COUNT");
				alUniversalWorkQueueList.add(objUniversalWorkQueueListDto);
				
			}
			pagingSorting.setRecordCount(recordCount);		
		} catch (Exception e) {
			 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Error At ORDERNO :"+objUniversalWorkQueueDto.getSearchCRMOrder(), logToFile, logToConsole);//added by nagarjuna 
			//e.printStackTrace();
			throw new Exception("No Record Found");
		} finally {
			try {
				DbConnection.closeResultset(rsUniversalWorkQueue);
				DbConnection.closePreparedStatement(csGetUniversalWorkQueue);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(true,true,"Exception at Closing Connection    : "+e);//Added by Nagarjuna
				//e.printStackTrace();
				throw new Exception("No Record Found");
			}
}
		return alUniversalWorkQueueList;
}
	//end Anil Kumar
	//start Anil Kumar
	public int createNewOrderWithExisting(String orderNo,String empID)throws Exception{
//		Added by nagarjuna
		String methodName="createNewOrderWithExisting",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		int status=0;
		int ORDERNOOUT=-1;
		Connection conn = null;
		ResultSet rsCreateNewOrder = null;
		CallableStatement csCreateNewOrder =null;
		
			try{
				conn = DbConnection.getConnectionObject();	
				if((!orderNo.equals(null))&& (!"".equalsIgnoreCase(orderNo)))
				{					
					conn.setAutoCommit(false);
					csCreateNewOrder=conn.prepareCall(sqlCreateNewOrderWithExisting);
					csCreateNewOrder.setLong(1, Long.valueOf(orderNo));
					csCreateNewOrder.setLong(2, 0);
					csCreateNewOrder.setLong(3, 0);
					csCreateNewOrder.setInt(4, 0);
					csCreateNewOrder.setString(5, "");
					csCreateNewOrder.setString(6, "");
					csCreateNewOrder.setString(7, "");
					csCreateNewOrder.setLong(8,0);
					csCreateNewOrder.setString(9, "New");
					csCreateNewOrder.setLong(10,Long.parseLong(empID) );
					csCreateNewOrder.execute();
					status=csCreateNewOrder.getInt(4);
					if(status!=0){
						conn.rollback();
						ORDERNOOUT=-1;
					}else{
						conn.commit();
						ORDERNOOUT=csCreateNewOrder.getInt(8);
					}
				}								
			}
			catch (Exception e) {
				 Utility.onEx_LOG_RET_NEW_EX(e, methodName, className, "Error At ORDERNO :"+status, logToFile, logToConsole);//added by nagarjuna 
				//e.printStackTrace();
				throw new Exception("No Record Found");
			} finally {
				try {
					DbConnection.closeResultset(rsCreateNewOrder);
					DbConnection.closeCallableStatement(csCreateNewOrder);
					DbConnection.freeConnection(conn);
				} catch (Exception e) {
					Utility.LOG(true,true,"Exception at Closing Connection    : "+e);//Added by Nagarjuna
					//e.printStackTrace();
					throw new Exception("No Record Found");
				}
			}
		return ORDERNOOUT;
	}
	//end Anil Kumar
	
	public int createNewOrderWithExisting_NewVersion(Connection conn, Long sourceOrderNo, Long targetOrderNo, String empID)throws Exception{
		int status=0;
		int ORDERNOOUT=-1;
		
		CallableStatement csCreateNewOrder =null;
		try{
			if((!sourceOrderNo.equals(null))&& (sourceOrderNo != -1))
			{					
				csCreateNewOrder=conn.prepareCall(sqlCreateNewOrderWithExisting_NewVersion);
				csCreateNewOrder.setLong(1, sourceOrderNo);
				csCreateNewOrder.setLong(2, targetOrderNo);
				csCreateNewOrder.setLong(3, Long.parseLong(empID));
				csCreateNewOrder.registerOutParameter(4, java.sql.Types.INTEGER);
				csCreateNewOrder.registerOutParameter(5, java.sql.Types.VARCHAR);
				csCreateNewOrder.registerOutParameter(6, java.sql.Types.VARCHAR);
				csCreateNewOrder.registerOutParameter(7, java.sql.Types.VARCHAR);				
				csCreateNewOrder.execute();
				status=csCreateNewOrder.getInt(4);
				if(status!=0 && status !=100){					
					ORDERNOOUT=-1;
					throw new Exception("status:"+status+"[Create order with existing order is failed due to no record found or get some error]");
				}else{					
					ORDERNOOUT=0;
				}
			}								
		}finally {
			try {
				DbConnection.closeCallableStatement(csCreateNewOrder);
			} catch (Exception e) {
				Utility.LOG(true,true,e,"Exception at Closing Connection.");//Added by Nagarjuna
				throw e;
			}
		}
		return ORDERNOOUT;
	}
	
	public ArrayList<UniversalWorkQueueDto> fetchQueryOptionList() 
	{
//		Added by nagarjuna
		String methodName="fetchQueryOptionList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		CallableStatement csQueryOptions =null;
		ResultSet rsQueryOptions = null;
		ArrayList<UniversalWorkQueueDto> listqueryOptions = new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDto objDto = null;	
		try
		{
			connection=DbConnection.getConnectionObject();
			csQueryOptions= connection.prepareCall(sqlGetqueryOptionList);
									
			rsQueryOptions = csQueryOptions.executeQuery();
			while(rsQueryOptions.next())
			{
				objDto =  new UniversalWorkQueueDto();
				objDto.setQueryNameID(rsQueryOptions.getInt("QUERYID"));
				objDto.setQueryName(rsQueryOptions.getString("QUERYNAME"));
				listqueryOptions.add(objDto);			
			}
			return listqueryOptions;
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsQueryOptions);
				DbConnection.closeCallableStatement(csQueryOptions);
				DbConnection.freeConnection(connection);
			} 
			catch (SQLException e) 
			{
				Utility.LOG(true,true,"SQL Exception     : "+e);//Added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			catch(Exception ex)
			{
				Utility.LOG(true,true,"Exception at Closing Connection    : "+ex);//Added by Nagarjuna
				//ex.printStackTrace();//ogger.error(ex.getMessage() + " Exception occured while closing database objects in getTransFileList method of " + this.getClass().getSimpleName());
			}
		}
		return listqueryOptions;
	}
	public ArrayList getQueryFormValueList(PagingDto objDto) throws Exception
	{
		//logger.info(" Entered into getTransFileList method of " + this.getClass().getSimpleName());
		//		Added by nagarjuna
		String methodName="getQueryFormValueList",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		ArrayList<NewOrderDto> valueList = new ArrayList<NewOrderDto>();
		Connection con = null;
		CallableStatement csGetValueList = null;
		ResultSet rsGetValueList = null;
		NewOrderDto objNewOrderDto=null;
		int recordCount=0;
		try
		{
			//DbConnection connectionClassObj = new DbConnection();	
			con = DbConnection.getConnectionObject();
			
			csGetValueList = con.prepareCall(sqlGetQueryFormValueList);
			
			csGetValueList.setInt(1, objDto.getQueryNameID());
			csGetValueList.setString(2, objDto.getQueryFormValue());									
			csGetValueList.setString(3,objDto.getSortBycolumn());
			csGetValueList.setString(4,objDto.getSortByOrder());
			csGetValueList.setInt(5,objDto.getStartIndex());
			csGetValueList.setInt(6,objDto.getEndIndex());
			
			rsGetValueList = csGetValueList.executeQuery();	
			
			while(rsGetValueList.next())
			{
				objNewOrderDto = new NewOrderDto();
				objNewOrderDto.setOrderNo(rsGetValueList.getString("ORDERNO"));
				objNewOrderDto.setAccountID(rsGetValueList.getInt("ACCOUNTID"));
				objNewOrderDto.setAccountName(rsGetValueList.getString("ACCOUNTNAME"));
				objNewOrderDto.setStageName(rsGetValueList.getString("STAGE"));
				//--Vijay--//
				/*change Stage value to UPPER case */
				objNewOrderDto.setOrderStageAnnotationName(rsGetValueList.getString("STAGE_ANNOTATION").toUpperCase());
				objNewOrderDto.setOrderType(rsGetValueList.getString("ORDERTYPE"));
				objNewOrderDto.setServiceIdString(rsGetValueList.getString("SERVICEID"));
				objNewOrderDto.setM6OrderNoString(rsGetValueList.getString("M6ORDERNO"));
				objNewOrderDto.setServiceTypeId(rsGetValueList.getInt("SERVICETYPEID"));
				objNewOrderDto.setRegionId(rsGetValueList.getInt("CRM_REGIONID"));//by kalpana for copc region change HYPR22032013003
				//start[007]
				if(rsGetValueList.getString("COPC_APPROVAL_DATE")==null){
					objNewOrderDto.setCopcApproveDate("");
				}else{
					objNewOrderDto.setCopcApproveDate(rsGetValueList.getString("COPC_APPROVAL_DATE"));
				}
				//end[007]
				recordCount=rsGetValueList.getInt("FULL_REC_COUNT");
				objNewOrderDto.getPagingSorting().setRecordCount(recordCount);	
				objNewOrderDto.setMaxPageNo(objNewOrderDto.getPagingSorting().getMaxPageNumber());
				valueList.add(objNewOrderDto);
			}			
		}
		catch(SQLException sqlEx)
		{
			Utility.onEx_LOG_RET_NEW_EX(sqlEx, methodName, className,"SQL Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//sqlEx.printStackTrace();
		}
		catch(Exception ex)
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rsGetValueList);
				DbConnection.closeCallableStatement(csGetValueList);
				DbConnection.freeConnection(con);
			}
			catch(SQLException sqlEx)
			{
				Utility.LOG(true,true,"SQL Exception Closing Connection    : "+sqlEx);//Added by Nagarjuna
				//sqlEx.printStackTrace();
			}
			catch(Exception ex)
			{
				Utility.LOG(true,true,"Exception Closing Connection    : "+ex);//Added by Nagarjuna
				//ex.printStackTrace();
			}
		}
		return valueList;
	}
	//end Anil Kumar
//	LAWKUSH
	public ArrayList<UniversalWorkQueueDto> getSubChangeType()
	{
		
//		Added by nagarjuna
		String methodName="getSubChangeType",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		PreparedStatement getSubChangeType =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listSubChangeType = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			getSubChangeType= connection.prepareCall(sqlGetSubChangeTypes);
			rs = getSubChangeType.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setSubChangeType(rs.getString("NAME_SUBTYPE"));
			 listSubChangeType.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(getSubChangeType);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true,true,"Exception at Closing Connection    : "+e);//Added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listSubChangeType;
	}
	public ArrayList<UniversalWorkQueueDto> getCustomerSegment()
	{
		//		Added by nagarjuna
		String methodName="getCustomerSegment",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listCustomerSegmentName = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			pstmt= connection.prepareCall(sqlGetCustomerSegment);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setSearchCustomerSegmentName(rs.getString("DESCRIPTION"));
			 listCustomerSegmentName.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true,true,"Exception at Closing Connection    : "+e);//Added by Nagarjuna
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listCustomerSegmentName;
	}
	
	public ArrayList<UniversalWorkQueueDto> getRegionName()
	{
		//Added by nagarjuna
		String methodName="getRegionName",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listRegionName = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			pstmt= connection.prepareCall(sqlGetRegionName);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setSearchRegionName(rs.getString("REGIONNAME"));
			 listRegionName.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true, true, "Exception "+e);
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listRegionName;
	}
	
	public ArrayList<UniversalWorkQueueDto> getIndustrySegmentName()
	{
		//Added by nagarjuna
		String methodName="getIndustrySegmentName",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listIndustrySegmentName = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			pstmt= connection.prepareCall(sqlGetIndustrySegmentName);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setSearchIndustrySegmentName(rs.getString("VERTICAL_DETAILS"));
			 listIndustrySegmentName.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true, true, "Exception "+e);
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listIndustrySegmentName;
	}
	public ArrayList<UniversalWorkQueueDto> getOrderStageName()
	{
//		Added by nagarjuna
		String methodName="getOrderStageName",  msg="";
		boolean logToFile=true, logToConsole=true;
		//	End nagarjuna
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listOrderStageName = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			pstmt= connection.prepareCall(sqlGetOrderStage);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setSearchOrderStageName(rs.getString("STAGE"));
			 listOrderStageName.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);//added by nagarjuna 
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true, true, "Exception "+e);
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listOrderStageName;
	}
		//[005] Start
	public ArrayList<UniversalWorkQueueDto> getServiceSegmentName()
	{
		String methodName="getServiceSegmentName",  msg="";
		boolean logToFile=true, logToConsole=true;
		Connection connection =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		UniversalWorkQueueDto objOrderDto = null;
		ArrayList<UniversalWorkQueueDto> listServiceSegmentName = new ArrayList<UniversalWorkQueueDto>();
		try
		{
			connection=DbConnection.getConnectionObject();
			
			pstmt= connection.prepareCall(sqlGetServiceSegmentName);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
			 objOrderDto =  new UniversalWorkQueueDto();
	
			 objOrderDto.setSearchServiceSegmentName(rs.getString("ACCOUNTCATEGORY_DETAILS"));
			 listServiceSegmentName.add(objOrderDto);
			}
			
		}
		catch(Exception ex )
		{
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className,"Exception"+msg, logToFile, logToConsole);
			//ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rs);
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e) 
			{
				Utility.LOG(true, true, "Exception "+e);
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return listServiceSegmentName;
	}
	//[005] Start
	
}
