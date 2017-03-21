//tag		Name       Date			CSR No			Description 
//[003]   Raveendra    02-Dec-2014                  Set processing count 10 in case of data issue exception is generated when scheduler is in progress

package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ArrayList;

import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.dto.FxExternalIdDto;
import com.ibm.fx.dto.PackageDTO;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class FxSchedulerTasksForPackage {

//	Fetches for FX New Order Schedular , next back or current dated (not future )Package which are to be pushed to FX 
	private String sqlFX_NO_SCH_GETPACKAGEDATA = "call IOE.FX_NO_SCH_GETPACKAGEDATA(?)";
	
	private String sqlFX_SCHEDULER_SUCCESS="call IOE.FX_SCHEDULER_SUCCESS_FOR_PACKAGE(?,?,?,?)";
	//private String sqlFX_SCHEDULER_FAILURE="call IOE.FX_SCHEDULER_FAILURE_FOR_PACKAGE(?,?,?)";
	//change [003]
	private String sqlFX_SCHEDULER_FAILURE="call IOE.FX_SCHEDULER_FAILURE_FOR_PACKAGE(?,?,?,?)";
	
	
	private String FXPackageStatusUpdate="UPDATE IOE.TFX_PACKAGE_CREATE SET FX_SCHEDULAR_CREATE_STATUS=1 WHERE FX_SCHEDULAR_CREATE_STATUS=4 ";
	//[001] Start
	private String getNewCurrencyExchangeRate = "SELECT CURRENCY_CODE,EXCHANGE_RATE,EXCHANGE_CURRENCY_CODE,EXCHANGE_DATE,CREATE_DT FROM ARBOR.EXCHANGE_RATE WHERE CREATE_DT like  SYSDATE";
	private String fxCurrencyChangeInsertion = "INSERT INTO IOE.TM_EXCHANGE_RATE(FX_CURRENCY_CODE, EXCHANGE_RATE, EXCHANGE_CURRENCY_CODE, EXCHANGE_DATE, CREATE_DATE) VALUES(?, ?, ?, ?, ?)";
	private String updateCurrencyChangeDate = " UPDATE IOE.TTRACKINSERTDATE SET INSERTDATE = current date , UPDATEDATE= current date where TABLENAME='TM_EXCHANGE_RATE' ";
	//[001] End
	/**	
	*	This function pushes today's or backdated Packages to FX for New Order
	*/
	public void pushPackagesToFX(long orderno)
	{
		try
		{
			IOESKenanManager API= null ;
			Utility.LOG(true, true, " IN  pushPackagesToFX()");
			Connection connection = null;	
			try
			{			
				API=new IOESKenanManager();
				API.loginKenan();
				
				connection = DbConnection.getConnectionObject();
				
				try
				{
					statusUpdateforPackageFailed(connection);
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}				
				
				connection.setAutoCommit(false);
				//Start [003]
				ArrayList<String> dataIssueException=Utility.getdataIssueException(connection);
				//End [003]
				
				PackageDTO Package = null;
				do
				{
					//fetch one Package
					try
					{
						Package = fetchNextPackage(connection,orderno);
						connection.commit();
					}
					catch(Exception ex)
					{
						Utility.LOG(true,true,ex,null);
						connection.commit();
						//logExceptionForPackage(Package,ex);//--Later
						continue;
					}
					if(Package!=null) 
					{ 
					//push to FX
						String logIdentifier="Package SCHEDULAR ERRROR , PackageID:"+Package.getPackageid()+"\n";
//						
						API.sendPackageToFx(Package,"");
						//save results	
						try
						{	
							//[003] Change method savePackagePushResult to add new parameter dataIssueException object

							savePackagePushResult(connection,Package,dataIssueException);
							connection.commit();
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
							String msg=logIdentifier+"Exception in saving FX New Order Package Schedular final " +
									"Result for PackageproductId :"+Package.getPackageid()
								+" \n Result was returnStatus="+Package.getReturnStatus()
								+" \n ackage.getPackage_inst_id()= , if any ,="+Package.getPackage_inst_id()
								+" \n Package.getPackage_inst_id_serv()= , if any ,="+Package.getPackage_inst_id_serv()
								+" \n Exception , if any, = "+((Package.getException()==null)?null:Utility.getStackTrace(Package.getException()))
								+" \n Exception Message , if any,= "+Package.getExceptionMessage();
							Utility.LOG(true,true,ex,msg);
							connection.rollback();
						}
					}
				}while(Package!=null);

			}
			finally
			{
				try
				{
					if(connection!=null)
					{
						DbConnection.freeConnection(connection);
					}
				}
				catch(Exception ex)
				{
					Utility.LOG(true,true,ex,null);
				}
				API.close();
				
			}
		}
		catch(Throwable th)
		{
			Utility.LOG(true,true,new Exception(th),null);
		}

		
	}

	
	public void statusUpdateforPackageFailed(Connection connection)
	{
		PreparedStatement pstmt=null;
		 try {
			pstmt=connection.prepareStatement(FXPackageStatusUpdate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
			if(pstmt!=null)DbConnection.closePreparedStatement(pstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}
	
	PackageDTO fetchNextPackage(Connection connection,long orderno) throws Exception
	{
		PackageDTO pacDto=null;
		
		CallableStatement cstmt_Package = null;
		
		ResultSet rs=null;
		try
		{
			cstmt_Package=connection.prepareCall(sqlFX_NO_SCH_GETPACKAGEDATA);
			cstmt_Package.setLong(1,orderno);
			rs=cstmt_Package.executeQuery();
			if(rs.next())
			{
				pacDto=new PackageDTO();
				pacDto.setRowId(rs.getLong("ID"));
				pacDto.setPackageActiveDate(rs.getDate("BILLINGACTIVEDT"));
				pacDto.setPackageid(rs.getString("PACKAGE_ID"));
				pacDto.setAcctExternalId(rs.getString("FX_EXT_ACCOUNT_NO"));
				
			}

		}
		
		finally
		{
			if(rs!=null)DbConnection.closeResultset(rs);
			if(cstmt_Package!=null)DbConnection.closeCallableStatement(cstmt_Package);
			
		}
		return pacDto;
	}

	public void savePackagePushResult(Connection connection,PackageDTO Package, ArrayList<String> dataIssueException) throws Exception
	{
		
		if(Package.getReturnStatus()==1)//SUCCESS
		{
					
			CallableStatement cstmt_Package_Success = null;
			try
			{
				cstmt_Package_Success=connection.prepareCall(sqlFX_SCHEDULER_SUCCESS);
				cstmt_Package_Success.setLong(1, Package.getRowId());
				cstmt_Package_Success.setLong(2, Package.getPackage_inst_id());
				cstmt_Package_Success.setLong(3, Package.getPackage_inst_id_serv());
				cstmt_Package_Success.setString(4,Package.getTokenid());
				cstmt_Package_Success.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Package_Success!=null) DbConnection.closeCallableStatement(cstmt_Package_Success);
			}
			//return serDto;
			//update FX_STATUS in TPOPackageDETIALS
			//UPDATE subscr no , reset in TFX_Package create
		}
		else
		{
			//Start [003]
			boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,Package.getException());
			Package.setDataIssueException(isDataIssueException);
			//End [003]
			
			
			
			CallableStatement cstmt_Package_Failure = null;
			try
			{
				cstmt_Package_Failure=connection.prepareCall(sqlFX_SCHEDULER_FAILURE);
				cstmt_Package_Failure.setLong(1, Package.getRowId());
				java.sql.Clob clobData = 
					com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(Package.getException()));
				cstmt_Package_Failure.setClob(2, clobData );
				cstmt_Package_Failure.setString(3, Package.getExceptionMessage());
				
				//Start [003]
				
				if(Package.isDataIssueException()){
					cstmt_Package_Failure.setLong(4, 1);
				}else{
					cstmt_Package_Failure.setLong(4, 0);
				}
				//End [003]
				cstmt_Package_Failure.executeUpdate();
				

			}
			finally
			{
				if(cstmt_Package_Failure!=null) cstmt_Package_Failure.close();
			}
			//update FX_STATUS in TPOPackageDETIALS
			//LOG exception and errmsg//-Later
			//update TFX_Package.EXCEPTION_HISTORY_ID//-Later
		}
	}
	
	//[001] Start
		public void updateCurrencyChangeRate() {

			Connection fxConnection = null, ib2bConnection = null;	
			ResultSet resultSet = null;
			PreparedStatement  preparedStatement=null, ib2bStatement= null;
				
				try
				{
					
					fxConnection = FxDbConnectionRetriever.getFxDbConnection();
					ib2bConnection = DbConnection.getConnectionObject();
					 
					preparedStatement = fxConnection.prepareStatement(getNewCurrencyExchangeRate);
					//AppConstants.IOES_LOGGER.info("Date is : "+FxSchedulerTasksForPackage.getLatestDateForCurrencyChange().get(AppConstants.FX_IB2B_CURRENCY).toString());
					//preparedStatement.setString(1, FxSchedulerTasksForPackage.getLatestDateForCurrencyChange().get(AppConstants.FX_IB2B_CURRENCY).toString());
					resultSet = preparedStatement.executeQuery();
					while(resultSet.next()) {
						
								
								ib2bConnection.setAutoCommit(false);
		
								/*Fetching data from FX datbase */		
								long currentCode =resultSet.getLong("CURRENCY_CODE");
								double exchangeRate = resultSet.getDouble("EXCHANGE_RATE");
								AppConstants.IOES_LOGGER.info("Latest Currency rate is : "+exchangeRate);
								int exchCurrCode =	resultSet.getInt("EXCHANGE_CURRENCY_CODE");
								Timestamp exchangeDate =	resultSet.getTimestamp("EXCHANGE_DATE");
								Timestamp createDate =	resultSet.getTimestamp("CREATE_DT");
								
								java.util.Date date = new java.util.Date();
								System.out.println(new Timestamp(date.getTime()));
								/*Inserting data into IB2B  */
								ib2bStatement = ib2bConnection.prepareStatement(fxCurrencyChangeInsertion);
								ib2bStatement.setLong(1, currentCode);
								ib2bStatement.setDouble(2, exchangeRate);
								ib2bStatement.setInt(3, exchCurrCode);
								ib2bStatement.setTimestamp(4, exchangeDate);
								ib2bStatement.setTimestamp(5, new Timestamp(date.getTime()));
								ib2bStatement.execute();
								ib2bConnection.commit();
								AppConstants.IOES_LOGGER.info(currentCode +",  "+exchangeRate +", "+exchCurrCode+", "+exchangeDate+", "+createDate);
							}
					// Updating date in history table
					ib2bStatement = ib2bConnection.prepareStatement(updateCurrencyChangeDate);
					//Date d = new Date();
					//ib2bStatement.setTimestamp(1, new Timestamp(d.getTime()));
					ib2bStatement.execute();
					
					
		}catch (Exception exception){
			try {
				ib2bConnection.rollback();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			exception.printStackTrace();
		 }finally {
				try {
					resultSet.close();
					ib2bStatement.close();
					preparedStatement.close();
					DbConnection.freeConnection(ib2bConnection);
					fxConnection.close();
				} catch (Exception e) {
					AppConstants.IOES_LOGGER.info("exeption due to : " + e.getMessage());
				}
		 	}
		}
	
	//Santosh 25-03-2014
	public static HashMap<String, String> getLatestDateForCurrencyChange()
	{
		HashMap<String, String> lastUpdatedCurrentDate= new HashMap<String, String>();
		ResultSet rset = null;
		Connection connection = null;
		CallableStatement csIOMS = null;
		try {			
			AppConstants.IOES_LOGGER.info("Connect with IOES database ==>");
		
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			csIOMS=connection.prepareCall("SELECT INSERT_TIMESTAMP AS INSERTDATE, DETAILS FROM IOE.TM_TRACK_CER");
			rset=csIOMS.executeQuery();
			AppConstants.IOES_LOGGER.info("Fetching date from IB2B and putting into into hashmap");
			while(rset.next())
			{
				SimpleDateFormat noMilliSecondsFormatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
				lastUpdatedCurrentDate.put(rset.getString("DETAILS"), noMilliSecondsFormatter.format(rset.getTimestamp("INSERTDATE")));
			}
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AppConstants.IOES_LOGGER.info("Error in method getLatestDateForTable() "+ e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				DbConnection.closeResultset(rset);
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(connection);
			} catch (Exception e) {
				AppConstants.IOES_LOGGER.info("exeption due to : " + e.getMessage());
			}
		}
	
			return lastUpdatedCurrentDate;
	}
	//[001] End
	public static void main(String[] args) {
	//	FxSchedulerTasksForPackage forPackage = new FxSchedulerTasksForPackage();
	//	forPackage.updateCurrencyChangeRate();
		//forPackage.getLatestDateForCurrencyChange();
	}
	
}
