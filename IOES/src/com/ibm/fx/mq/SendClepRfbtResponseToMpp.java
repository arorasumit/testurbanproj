package com.ibm.fx.mq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.ibm.ioes.clep.CLEPUtility;
import com.ibm.ioes.clep.CLEPXmlDto;
import com.ibm.ioes.clep.ParseXMLforCLEP;
import com.ibm.ioes.exception.IOESException;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;



public class SendClepRfbtResponseToMpp {

	private static final int Fetch_Acc_Status_DataFound=1;
	private static final int Fetch_Acc_Status_DataNotFound=2;
	
	
	
	
	/**
	 * 
	 * @param conn
	 * @param orderNo
	 * @throws IOESException
	 */
	public void fxSendClepResponseToMpp(Long orderNo) throws IOESException
	{
		CLEPUtility.SysErr("--------------- calling fxSendClepResponseToMpp()  >>>>>>>>>>>>>");
		ArrayList<CLEPXmlDto> clepXmlDtoList =null;
		int fetchAccStatus=0;
		Connection conn=null;
		
		try{
			conn=DbConnection.getConnectionObject();
			do
			{
					clepXmlDtoList=new ArrayList<CLEPXmlDto>();
					fetchAccStatus=fetchNextClepAccountData(conn,clepXmlDtoList,orderNo);
					if(fetchAccStatus==Fetch_Acc_Status_DataFound)
					{	
						for (CLEPXmlDto clepXmlDto : clepXmlDtoList) {
							sendRFBTResponseMsgToMpp(conn,clepXmlDto);
						}
					}
			}while(fetchAccStatus==Fetch_Acc_Status_DataFound);
		}catch(Throwable ex)
		{
			if(orderNo == null || orderNo== 0 )
			{
			 Utility.onEx_LOG_RET_NEW_EX(ex, "fxSendClepResponseToMpp", "FxSendClepResponseToMpp", "Checking account created in fx_accountcreate  , order no:", true, true);
			}
			else
			{
			 Utility.onEx_LOG_RET_NEW_EX(ex, "fxSendClepResponseToMpp", "FxSendClepResponseToMpp", "Checking account created in fx_accountcreate , order no:"+orderNo, true, true);
			}
		}finally{
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				Utility.LOG(e);
			}
		}
		
		
		
	
	}

	private void sendRFBTResponseMsgToMpp(Connection conn, CLEPXmlDto clepXmlDto) throws IOESException {
		
		

		
		String methodName="sendRFBTResponseMsgToMpp", className=this.getClass().getName(), msg="";
		boolean logToFile=true, logToConsole=true;
		try{
			 
			//CLEPListener.setClepQueueConfig(clepXmlDto);
			ParseXMLforCLEP.sendXMLtoMPP(clepXmlDto, clepXmlDto.getXmlfileid(), "N", "BT", conn);

			CLEPUtility.SysErr("--------------- sendRFBTResponseMsgToMpp  >>>>>>>>>>>>>");
		}catch(Exception ex){	
			Utility.onEx_LOG_RET_NEW_EX(ex, methodName, className, msg, logToFile, logToConsole);//Nagarjuna
		}
	
		
		
	}

	private int fetchNextClepAccountData(Connection conn, ArrayList<CLEPXmlDto> clepXmlDtoList, Long orderNo) throws IOESException{
		int fetchAccStatus=0;
		
		ResultSet rs=null;
		Statement statement=null;
		try
		{
			
			String sql=null;
			
				if(orderNo == null || orderNo== 0)
				{
				 sql="SELECT TM_MPPNEW_TTEMPLATEFILE_PROCESS.FILEID as fileId, TM_MPPNEW_TTEMPLATEFILE_PROCESS.XMLSUCCESSDATA as xmlData, " +
				 		" TM_MPPNEW_TTEMPLATEFILE_PROCESS.MSGREQUESTID as msgReqID " +
				 		" FROM IOE.TM_MPPNEW_TTEMPLATEFILE_PROCESS TM_MPPNEW_TTEMPLATEFILE_PROCESS " +
				 		" INNER JOIN IOE.TFX_ACCOUNTCREATE TFX_ACCOUNTCREATE ON TM_MPPNEW_TTEMPLATEFILE_PROCESS.ORDERNO=TFX_ACCOUNTCREATE.ORDERNO " +
				 		" WHERE ISCOPCSENTMSG=0 AND ORDERTYPE='RFBT' AND PROCESSINGSTATUS=3 " +
				 		" order by FILEID asc fetch first 5000 row only";
				   
				}
				else
				{
					sql="SELECT TM_MPPNEW_TTEMPLATEFILE_PROCESS.FILEID as fileId, TM_MPPNEW_TTEMPLATEFILE_PROCESS.XMLSUCCESSDATA as xmlData, " +
							" TM_MPPNEW_TTEMPLATEFILE_PROCESS.MSGREQUESTID as msgReqID " +
							" FROM IOE.TM_MPPNEW_TTEMPLATEFILE_PROCESS TM_MPPNEW_TTEMPLATEFILE_PROCESS " +
							" INNER JOIN IOE.TFX_ACCOUNTCREATE TFX_ACCOUNTCREATE ON TM_MPPNEW_TTEMPLATEFILE_PROCESS.ORDERNO=TFX_ACCOUNTCREATE.ORDERNO " +
							" WHERE ISCOPCSENTMSG=0 AND ORDERTYPE='RFBT' AND PROCESSINGSTATUS=3 AND TFX_ACCOUNTCREATE.ORDERNO="+orderNo+" " +
							" order by FILEID asc fetch first 5000 row only";
			   	}
				 statement=conn.createStatement();
				 rs=statement.executeQuery(sql);
				while(rs.next())
				{
					fetchAccStatus=Fetch_Acc_Status_DataFound;
					CLEPXmlDto clepXmlDto=new CLEPXmlDto();
					clepXmlDto.setXmlfileid(rs.getLong("fileId"));
     				clepXmlDto.setXmlData(rs.getString("xmlData"));
     				clepXmlDto.setJmsMessageID(rs.getString("msgReqID"));
					
     				clepXmlDtoList.add(clepXmlDto);
				}
				
				if(clepXmlDtoList.size()==0)
				{
					fetchAccStatus=Fetch_Acc_Status_DataNotFound;	
				}
			
			
		}catch (Exception ex) {
			throw Utility.onEx_LOG_RET_NEW_EX(ex, "fetchNextClepAccountData", getClass().getName(), null, true, true);
		}
		finally
		{
			try
			{
			if(statement!=null)DbConnection.closeStatement(statement);
			}
			catch(Exception ex){ex.printStackTrace();}
			try
			{
			if(rs!=null)DbConnection.closeResultset(rs);
			}
			catch(Exception ex){ex.printStackTrace();}
		}
		
		
		return fetchAccStatus;
	}

	
	/*public static void test() {
		SendClepRfbtResponseToMpp fxSendClepResponseToMpp=new SendClepRfbtResponseToMpp();
		try {
			Connection conn=null;
			conn=DbConnection.getConnectionObject();
			
			fxSendClepResponseToMpp.fxSendClepResponseToMpp(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
}

