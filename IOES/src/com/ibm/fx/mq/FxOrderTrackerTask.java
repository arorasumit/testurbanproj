package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.db2.jcc.b.SqlException;
import com.ibm.fx.dto.ComponentDTO;
import com.ibm.fx.dto.NrcDto;
import com.ibm.fx.dto.OrderTrackerDto;
import com.ibm.fx.dto.RcDto;
import com.ibm.fx.dto.ServiceDTO;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;


public class FxOrderTrackerTask {
	
	private static String    INSERT_INTO_ORDER_TRACKER		=  "INSERT INTO IOE.TFX_ORDER_TRACKER(PARENT_ID,SCHEDULAR_TYPE,FX_ORDER_NO, STATUS)"+ 
																	"VALUES(?,?,?,?)";	
	private static String    UPDATE_ORDER_TRACKER				= "UPDATE   IOE.TFX_ORDER_TRACKER SET SUBSCR_NO = ?,SUBSCR_NO_RESETS = ?," +
																   "VIEW_ID=?,STATUS = ? WHERE SCHEDULAR_TYPE = ?	AND PARENT_ID = ? AND 	FX_ORDER_NO = ?";																
	private static String 	 UPDATE_IB2B_STATUS_SERVICE			= "UPDATE  IOE.TFX_SERVICECREATE SET TOKEN_ID = ? , FX_SCHEDULAR_CREATE_STATUS = 3 WHERE ID = ?"; 
	private static String 	 UPDATE_IB2B_STATUS_NRC				= "UPDATE  IOE.TFX_NRC_CREATE SET TOKEN_ID = ? , FX_SCHEDULAR_CREATE_STATUS = 3 WHERE ID = ?";
	private static String 	 UPDATE_IB2B_STATUS_PRODUCT			= "UPDATE  IOE.TFX_RC_CREATE SET TOKEN_ID = ? , FX_SCHEDULAR_CREATE_STATUS = 3 WHERE ID = ?";
	private static String 	 UPDATE_IB2B_STATUS_COMPONENT		= "UPDATE  IOE.TFX_COMPONENT_CREATE SET TOKEN_ID = ? , FX_SCHEDULAR_CREATE_STATUS = 3 WHERE ID = ?";
	private static String    GET_KNOCK_STATUS					= "SELECT COUNT(ID)AS COUNT FROM IOE.TRAISE_TO_KNOCK where PARENT_ID = ?";
	private static String    GET_IB2B_ORDER_TRACKER_STATUS =  "SELECT TOT.* FROM IOE.TFX_ORDER_TRACKER TOT  WHERE TOT.SCHEDULAR_TYPE = ? AND TOT.PARENT_ID =? order by TOT.ID desc FETCH FIRST ROWS ONLY";  	
	private static String    INSERT_KNOCK_STATUS			= "INSERT INTO IOE.TRAISE_TO_KNOCK (MODULE,SUB_MODULE,PARENT_ID,CREATED_DATE,TEXT_DESC)VALUES('FX',?,?,CURRENT DATE,?)";
	
	public static void updateTfxOrderTracker(String  schedular_type,String orderno,Long parent_id,String tempVar1,String tempVar2,String view_id,String status)
	throws Exception{
		 PreparedStatement ps = null;
		Connection connection = null;
			
		
		try
		{
			connection = DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			ps=connection.prepareCall(UPDATE_ORDER_TRACKER);
			
			ps.setString(1, tempVar1);
			ps.setString(2, tempVar2);
			ps.setString(3,view_id);
			ps.setString(4, status);
			ps.setString(5, schedular_type);
			ps.setLong(6, parent_id);
			ps.setString(7, orderno);
			ps.executeUpdate();
			connection.commit();
		}
		catch(Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw e1;
			}
			throw e;
		}
		finally
		{
			try
			{
				if(connection!=null)
				{
					DbConnection.freeConnection(connection);
				}if (ps!=null)DbConnection.closePreparedStatement(ps);
				
			}
			catch(Exception ex)
			{
				Utility.LOG(true,true,ex,null);
			}
			
			
		}		
	}
	
	public static void insertOrderTrackerStatus(String schedularType,String orderNo,long parentid,String status) throws Exception{
		
		PreparedStatement ps = null;
		Connection connection = null;
			
		
		try
		{
			connection = DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			ps=connection.prepareStatement(INSERT_INTO_ORDER_TRACKER);
			ps.setLong(1, parentid);
			ps.setString(2, schedularType);
			ps.setString(3, orderNo);
			ps.setString(4, status);
			ps.executeUpdate();
			connection.commit();
		}
		catch(Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw e1;
			}
			throw e;
		}
		finally
		{ 
			try
			{
				if(connection!=null)
				{
					DbConnection.freeConnection(connection);
					
				}if (ps!=null)DbConnection.closePreparedStatement(ps);
			}
			catch(Exception ex)
			{
				Utility.LOG(true,true,ex,null);
			}
		}
		
	}//end of function
	
	public static int checkPreviousStatus(Connection conn,IOESKenanManager API,Object dto,String schedular_type){		
			
		int status=0;
		int fxstatus  = 0;
		String ib2bstatus = null;
		OrderTrackerDto orderDto		  = null;	
		int returnStatus = 0;
		String fxOrderNo = null;
		
		Long id = null ;
		Long parent_id = null;
		
		try{
			//getorder status
			orderDto = FxOrderTrackerTask.getOrderTrackerStatusIb2b(conn,dto,schedular_type);			
			if(orderDto !=null){ // get fx status
				
				//logic to split orderno and fxstatus				
				
				id 			= orderDto.getRowId();
				ib2bstatus  = orderDto.getStatus();
				fxOrderNo	= orderDto.getOrderno();
				parent_id	= orderDto.getParent_id();
				
				
				fxstatus = API.getFxOrderStatus(fxOrderNo);
				status  = fxstatus;
				if(fxstatus == FxOrderTrackerConst.PROGRESS){					
					if(ib2bstatus.equalsIgnoreCase("INIT") || ib2bstatus.equalsIgnoreCase("FORCOMMIT")){
						
						try{							
							status = API.cancelOrder(fxOrderNo);
							
							
						}catch (Exception e){
							
							returnStatus = -1;
							setFailureResponse(schedular_type,dto,returnStatus,
									schedular_type+" : "+"Order cannot Be Cancelled during reprocess of last FX order",e);
						}
					}					
				}else if(fxstatus == FxOrderTrackerConst.COMMITTED||fxstatus == FxOrderTrackerConst.COMPLETED){
					//				if (fx)status is progress  and ordertracker status is in for for commit /init
					if("FORCOMMIT".equalsIgnoreCase(ib2bstatus)){						
						
									
							returnStatus = 1;
							if("SERVICE".equalsIgnoreCase(schedular_type)){
								
								ServiceDTO sdto	= (ServiceDTO)dto;
								sdto.setReturnStatus(returnStatus);	
								sdto.setToken_id(orderDto.getOrderno());
								sdto.setSubscrNo(Integer.parseInt(orderDto.getSubscr_no().toString()));
								sdto.setSubscrNoReset(Integer.parseInt(orderDto.getSubscr_no_reset().toString()));
								
							}else if("NRC".equalsIgnoreCase(schedular_type)){
								
								NrcDto nrcDto	= (NrcDto)dto;
								nrcDto.setReturnStatus(returnStatus);
								nrcDto.setToken_id(orderDto.getOrderno());
								nrcDto.setViewId(orderDto.getView_id());
								
							}else if("PRODUCT".equalsIgnoreCase(schedular_type)){
								
								RcDto rc 	= (RcDto)dto;
								rc.setReturnStatus(returnStatus);
								rc.setToken_id(orderDto.getOrderno());
								rc.setViewId(orderDto.getView_id());
								rc.setTrackingId(orderDto.getTrackingId());
								rc.setTrackingIdServ(orderDto.getTrackingIdServ());
								
								if(new Integer(1).equals(rc.getIsDifferentialForFX())){
									rc.setDifferentialHitInFX(true);
								}
								
							}else if ("COMPONENT".equalsIgnoreCase(schedular_type)){
								
								ComponentDTO componentDTO = (ComponentDTO)dto;
								componentDTO.setReturnStatus(returnStatus);
								componentDTO.setTokenid(orderDto.getOrderno());
								componentDTO.setComponent_inst_id(orderDto.getSubscr_no().toString());
								componentDTO.setComponent_inst_id_serv(orderDto.getSubscr_no_reset().toString());
							}
							
				
						
					}else if("INIT".equalsIgnoreCase(ib2bstatus)){								
				
							
							raiseToKnockTeam(conn,schedular_type,id);
							throw new Exception("Raised  to Knock Team");
							
							
														
					}					
				}
			 
			}else
			{
				status = FxOrderTrackerConst.NoPreviousLOG;
			}
		}catch(Exception e){
			
			returnStatus = -1;
			setFailureResponse(schedular_type,dto,returnStatus,
					schedular_type+" : "+"Exception caught during reprocess of last FX order",e);
			
		}		
		return status;
		
	}// end of the function
	
	private static void setFailureResponse(String schedular_type,Object dto,int returnStatus,String excMsg,Exception e) {
		if("SERVICE".equalsIgnoreCase(schedular_type)){
			
			ServiceDTO sdto	= (ServiceDTO)dto;
			sdto.setReturnStatus(returnStatus);	
			sdto.setException(e);
			sdto.setExceptionMessage(excMsg);//"Order for Service cannot Be Cancelled during reprocess of last FX order");
			
		}else if("NRC".equalsIgnoreCase(schedular_type)){
			
			NrcDto nrcDto	= (NrcDto)dto;
			nrcDto.setReturnStatus(returnStatus);
			nrcDto.setException(e);
			nrcDto.setExceptionMessage(excMsg);//("Order for NRC cannot Be Cancelled during reprocess of last FX order");
			
		}else if("PRODUCT".equalsIgnoreCase(schedular_type)){
			
			RcDto rc 	= (RcDto)dto;
			rc.setReturnStatus(returnStatus);
			rc.setException(e);
			rc.setExceptionMessage(excMsg);//("Order for RC cannot Be Cancelled during reprocess of last FX order");
			
		}else if ("COMPONENT".equalsIgnoreCase(schedular_type)){
			
			ComponentDTO componentDTO = (ComponentDTO)dto;
			componentDTO.setReturnStatus(returnStatus);								
			componentDTO.setException(e);
			componentDTO.setExceptionMessage(excMsg);//("Order for Component cannot Be Cancelled during reprocess of last FX order");
		}
	}

	public static OrderTrackerDto getOrderTrackerStatusIb2b(Connection conn ,Object dto,String schedular_type) throws Exception {
		
		PreparedStatement  ps = null;
		ResultSet  rs         = null;
			
		Long parent_id    = null;
		
		OrderTrackerDto orderTrackerdto = null;
		
		try
		{
			
			if("SERVICE".equalsIgnoreCase(schedular_type)){
				
				ServiceDTO sdto	= (ServiceDTO)dto;
				parent_id		= sdto.getRowId();				
				
			}else if("NRC".equalsIgnoreCase(schedular_type)){
				
				NrcDto nrcDto	= (NrcDto)dto;
				parent_id		= nrcDto.getRowId();
				
			}else if("PRODUCT".equalsIgnoreCase(schedular_type)){
				
				RcDto rc 	= (RcDto)dto;
				parent_id	= rc.getRowId();
				
			}else if ("COMPONENT".equalsIgnoreCase(schedular_type)){
				
				ComponentDTO componentDTO = (ComponentDTO)dto;
				parent_id				  = componentDTO.getRowId();				
			}
			ps=conn.prepareStatement(GET_IB2B_ORDER_TRACKER_STATUS);			
			ps.setString(1, schedular_type);
			ps.setLong(2, parent_id);
			rs = ps.executeQuery();	
			
			while(rs.next()){
				
				orderTrackerdto = new OrderTrackerDto(); 
				orderTrackerdto.setRowId(Long.parseLong(rs.getString("ID")));
				orderTrackerdto.setStatus(rs.getString("STATUS"));
				orderTrackerdto.setOrderno(rs.getString("FX_ORDER_NO"));
				orderTrackerdto.setParent_id(Long.parseLong(rs.getString("PARENT_ID")));	
				
				if("PRODUCT".equalsIgnoreCase(schedular_type)){
					orderTrackerdto.setTrackingId(rs.getInt("SUBSCR_NO"));
					orderTrackerdto.setTrackingIdServ(rs.getInt("SUBSCR_NO_RESETS"));
				}else{
					orderTrackerdto.setSubscr_no(rs.getLong("SUBSCR_NO"));
					orderTrackerdto.setSubscr_no_reset(rs.getLong("SUBSCR_NO_RESETS"));		
				}

				orderTrackerdto.setView_id(rs.getString("VIEW_ID"));
			}
					
		}
		catch(Exception e) {
			Utility.LOG(true, true, e, "");
			throw e;
		}finally
		{
			if(rs!=null)DbConnection.closeResultset(rs);
			if(ps!=null)DbConnection.closePreparedStatement(ps);		
			
		}
		
		return orderTrackerdto;
	}
	
	public static void updateIb2bStatus(Connection conn ,String schedular_type,Long parent_id,String order_no){
		
		PreparedStatement  ps = null;		
		int result            = 0;
		try{
			
						
			if("SERVICE".equalsIgnoreCase(schedular_type)){
				
				ps=conn.prepareStatement(UPDATE_IB2B_STATUS_SERVICE);				
				ps.setString(1, order_no);
				ps.setLong(2, parent_id);				
				
			}else if("NRC".equalsIgnoreCase(schedular_type)){
				
				ps=conn.prepareStatement(UPDATE_IB2B_STATUS_NRC);				
				ps.setString(1, order_no);
				ps.setLong(2, parent_id);
				
			}else if("PRODUCT".equalsIgnoreCase(schedular_type)){
				
				ps=conn.prepareStatement(UPDATE_IB2B_STATUS_PRODUCT);				
				ps.setString(1, order_no);
				ps.setLong(2, parent_id);
				
			}else if ("COMPONENT".equalsIgnoreCase(schedular_type)){
				
				ps=conn.prepareStatement(UPDATE_IB2B_STATUS_COMPONENT);				
				ps.setString(1, order_no);
				ps.setLong(2, parent_id);	
				
			}
									
			result = ps.executeUpdate();
			
						
		}catch(SQLException e){			
			Utility.LOG(true,true,e,null);
			
		}finally {
			if(ps!=null)
				try {
					DbConnection.closePreparedStatement(ps);
				} catch (Exception e) {
					Utility.LOG(true,true,e,null);
					
				}
				
		}
	}
	
	
	public static void raiseToKnockTeam(Connection conn,String schedular_type,Long id) throws Exception{
		
		PreparedStatement  ps = null;
		ResultSet          rs = null;
		int count			  =0;	
		int result 			  =0;
		
		try{
			//check if it already exists
			
			ps	=	conn.prepareStatement(GET_KNOCK_STATUS);
			ps.setLong(1, id);
			rs	=	ps.executeQuery();
			rs.next();
			count = Integer.valueOf(rs.getString("COUNT"));
			if (count==0){
				conn.setAutoCommit(false);
				ps = conn.prepareStatement(INSERT_KNOCK_STATUS);
				ps.setString(1, schedular_type);
				ps.setLong(2, id);
				ps.setClob(3, com.ibm.db2.jcc.t2zos.DB2LobFactory.createClob("FX Object committed in FX but in ib2b processing was in INIT stage"));
				result	=	ps.executeUpdate();
				conn.commit();
				
			}			
		}catch(Exception e){
			Utility.LOG(true,true,e,null);
			
		}finally{
			if(ps!= null){
				DbConnection.closePreparedStatement(ps);
			}
			if(rs!=null){
				DbConnection.closeResultset(rs);
			}
		}
		
	
		
		
	}
	
}// end of class
