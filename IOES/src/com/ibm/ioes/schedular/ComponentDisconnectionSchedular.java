//tag		Name       Date			CSR No			Description 
//[001]   Raveendra    28-Nov-2014                  Set processing count 10 in case of data issue exception is generated when scheduler is in progress

package com.ibm.ioes.schedular;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.fx.dto.ComponentDTO;
import com.ibm.fx.dto.ExtendedData;
import com.ibm.fx.mq.IOESKenanManager;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class ComponentDisconnectionSchedular {

	private static String sqlGet_Component_For_Disconnection_Schedular = "{CALL IOE.FX_GET_COMPONENT_FOR_DISCONNECTON(?)}";

	private String FXComponentDisconnectionStatusUpdate = "UPDATE IOE.TFX_COMPONENT_DISCONNECTION TCD SET PROCESSING_STATUS = 1,LAST_UPDATED_DATE = CURRENT TIMESTAMP "
			+ "WHERE TCD.PROCESSING_STATUS in (4)";

	private String sqlFX_SCHEDULER_SUCCESS = "{CALL IOE.FX_SCHEDULER_SUCCESS_FOR_COMPONENT_DISCONNECTION(?,?,?)}";
    //Start [001] 
    private String sqlFX_SCHEDULER_FAILURE = "{CALL IOE.FX_SCHEDULER_FAILURE_FOR_COMPONENT_DISCONNECTION(?,?,?,?)}";
    //End [001]

	/***************************************************************************
	 * Function Name:- process_component_for_disconnection Created By:- Sandeep
	 * Aggarwal Created On:- 12-jan-2012 Purpose:- Process the component for
	 * disconnection
	 * ********************************************************************************
	 */
	public void process_component_for_disconnection(long orderno) {

		try {
			IOESKenanManager API = null;
			Utility.LOG(true, true, " Push.....");
			Connection connection = null;
			// arraySericeList=null;
			try {
				API = new IOESKenanManager();
				API.loginKenan();

				connection = DbConnection.getConnectionObject();
				try {
					statusUpdateForFailedComponentDisconnection(connection);
				} catch (Exception ex) {
					Utility.LOG(true, true, ex, null);
				}
				connection.setAutoCommit(false);
				//Start [001]
				ArrayList<String> dataIssueException=Utility.getdataIssueException(connection);
				//End [001]
				
				ComponentDTO component = null;
				do {

					try {
						component = get_component_for_disconnection(connection,orderno);
						connection.commit();
					} catch (Exception ex) {
						Utility.LOG(true, true, ex, null);
						connection.commit();
						// logExceptionForPackage(Package,ex);//--Later
						continue;
					}
					if (component != null) {

						 API.disconnectComponent(component,"");

						try {
					     //[001] Change method saveDisconnectionStatus to add new parameter dataIssueException object
							saveDisconnectionStatus(connection, component,dataIssueException);
							connection.commit();
						} catch (Exception ex) {
							ex.printStackTrace();
							/*
							 * String msg=logIdentifier+"Exception in saving FX
							 * New Order Package Schedular final " + "Result for
							 * PackageproductId :"+Package.getPackageid() +" \n
							 * Result was
							 * returnStatus="+Package.getReturnStatus() +" \n
							 * ackage.getPackage_inst_id()= , if any
							 * ,="+Package.getPackage_inst_id() +" \n
							 * Package.getPackage_inst_id_serv()= , if any
							 * ,="+Package.getPackage_inst_id_serv() +" \n
							 * Exception , if any, =
							 * "+((Package.getException()==null)?null:Utility.getStackTrace(Package.getException())) +"
							 * \n Exception Message , if any,=
							 * "+Package.getExceptionMessage();
							 * Utility.LOG(true,true,ex,msg);
							 */
							connection.rollback();
						}

					}// end of the for loop

				} while (component != null);// end of while loop
			} finally {
				try {
					if (connection != null) {
						connection.close();
						
					}
				} catch (Exception ex) {
					Utility.LOG(true, true, ex, null);
				}
				API.close();

			}

		} catch (Throwable th) {

			Utility.LOG(true, true, new Exception(th), null);

		}

	}

	/***************************************************************************
	 * Function Name:- get_charge_list_for_disconnection Created By:- Sandeep
	 * Aggarwal Created On:- 10-FEB-2011 Purpose:- To Get All The Charge List
	 * For Disconnection
	 * ********************************************************************************
	 */

	ComponentDTO get_component_for_disconnection(Connection connection,long orderno)
			throws Exception {

		Utility
				.LOG(
						true,
						true,
						" IN  get_component_for_disconnection() function of DisconnectionSchedular class");
		CallableStatement csComponent_For_Disconnection = null;
		// ArrayList alSelect_Service_Details_For_Disconnection=null;
		ResultSet rsComponent_Details_For_Disconnection = null;
		ComponentDTO objDisconnectionDto = null;

		try {

			csComponent_For_Disconnection = connection
					.prepareCall(sqlGet_Component_For_Disconnection_Schedular);
			
			csComponent_For_Disconnection.setLong(1,orderno);
			rsComponent_Details_For_Disconnection = csComponent_For_Disconnection
					.executeQuery();
			Utility.LOG(true, true, "Fetch component for disconnection ");
			if (rsComponent_Details_For_Disconnection.next())/*
																 * fetch rc from
																 * for
																 * disconnection
																 */{
				try {
					objDisconnectionDto = new ComponentDTO();
					objDisconnectionDto.setIndex_key(rsComponent_Details_For_Disconnection.getInt("INDEX_KEY"));
					objDisconnectionDto.setAccountExternalId(rsComponent_Details_For_Disconnection.getString("EXT_ACCOUNT_NO"));
					objDisconnectionDto.setComponent_disconnection_date(rsComponent_Details_For_Disconnection.getTimestamp("DISCONNECTION_DATE"));
					objDisconnectionDto.setComponent_inst_id(rsComponent_Details_For_Disconnection.getString("COMPONENT_INST_ID") );
					objDisconnectionDto.setComponent_inst_id_serv(rsComponent_Details_For_Disconnection.getString("COMPONENT_INST_ID_SERV"));

					ArrayList<ExtendedData> extendedData = Utility.getFxExtendedData(connection,objDisconnectionDto.getIndex_key(),ExtendedData.AssociatedWith_ORDER_FOR_COMPONENT_DISCONNECTION);
					objDisconnectionDto.setOrderExtendedData(extendedData);
					
					 //alSelect_Service_Details_For_Disconnection.add(objDisconnectionDto);

					String msg = "Component details to be disconnected are "
							+ "\n INDEX_KEY "
							+ rsComponent_Details_For_Disconnection
									.getInt("INDEX_KEY");
					Utility.LOG(true, true, msg);

				} catch (Exception ex) {

					// String msg="Exception in retrieving Component for
					// DISCONNECTION Schedular. for index value "
					// +objDisconnectionDto.getIndex_key()
					// +" \n Exception , if any, =
					// "+((objDisconnectionDto.getException()==null)?null:Utility.getStackTrace(objDisconnectionDto.getException()))
					// +" \n Exception Message , if any,=
					// "+objDisconnectionDto.getExceptionMessage();

					// Utility.LOG(true,true,ex,msg);
				}

			}// end of the while loop

		} catch (Exception e) {

			Utility.LOG(true, true, e, null);

		}
         finally
         {
        	 DbConnection.closeCallableStatement(csComponent_For_Disconnection);
        	 DbConnection.closeResultset(rsComponent_Details_For_Disconnection);
        	 	
		}
		return objDisconnectionDto;

	}// end of the function

	public void saveDisconnectionStatus(Connection connection,
			ComponentDTO component, ArrayList<String> dataIssueException) throws Exception {

		if (component.getReturnStatus() == 1)// SUCCESS
		{

			CallableStatement cstmt_ComponentDisconnection_Success = null;
			try {
				cstmt_ComponentDisconnection_Success = connection.prepareCall(sqlFX_SCHEDULER_SUCCESS);
				cstmt_ComponentDisconnection_Success.setLong(1, component.getIndex_key());
				cstmt_ComponentDisconnection_Success.setLong(2, 0);
				cstmt_ComponentDisconnection_Success.setString(3, component.getTokenid());
				cstmt_ComponentDisconnection_Success.executeUpdate();

			} finally {
				if (cstmt_ComponentDisconnection_Success != null)
					cstmt_ComponentDisconnection_Success.close();
			}
			// return serDto;
			// update FX_STATUS in TPOPackageDETIALS
			// UPDATE subscr no , reset in TFX_Package create
		} else {
			//Start [001]
			boolean isDataIssueException=Utility.isDataIssueException(dataIssueException,component.getException());
			component.setDataIssueException(isDataIssueException);
     		//End [001]
			CallableStatement cstmt_ComponentDisconnection_Failure = null;
			try {
				cstmt_ComponentDisconnection_Failure = connection.prepareCall(sqlFX_SCHEDULER_FAILURE);
				cstmt_ComponentDisconnection_Failure.setLong(1, component.getIndex_key());
				java.sql.Clob clobData = com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob(Utility.getStackTrace(component.getException()));
				cstmt_ComponentDisconnection_Failure.setClob(2, clobData );
				cstmt_ComponentDisconnection_Failure.setString(3, component.getExceptionMessage());
				//Start [001]
				if(component.isDataIssueException()){
					cstmt_ComponentDisconnection_Failure.setLong(4, 1);
				}else{
					cstmt_ComponentDisconnection_Failure.setLong(4, 0);
				}
				//End [001]
				cstmt_ComponentDisconnection_Failure.executeUpdate();

			} finally {
				if (cstmt_ComponentDisconnection_Failure != null)
					cstmt_ComponentDisconnection_Failure.close();
			}
			// update FX_STATUS in TPOPackageDETIALS
			// LOG exception and errmsg//-Later
			// update TFX_Package.EXCEPTION_HISTORY_ID//-Later
		}
	}

	public void statusUpdateForFailedComponentDisconnection(
			Connection connection) throws Exception {
		PreparedStatement pstmt=null;
		try {
			 pstmt = connection.prepareStatement(FXComponentDisconnectionStatusUpdate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			DbConnection.closePreparedStatement(pstmt);
		}

	}

}
