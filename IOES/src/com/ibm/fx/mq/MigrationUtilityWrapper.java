package com.ibm.fx.mq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.forms.FileAttachmentDto;
import com.ibm.ioes.model.MigrationUtility;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

	 	public class MigrationUtilityWrapper {
	 		NewOrderDao objdao = new NewOrderDao();
	/**
	 * @param Written By Shubhranshu For Migration Of Docs of IB2B Orders using Multi-threaditng  
	 */
	 		
	 		static ExecutorService myThreadPool=null;
	final  static String  sqlFetchOrderNoForFileMigration ="select distinct(ORDERNO) from ioe.TFILEUPLOAD where IS_UPLOADED='EFS'";
		
	public ArrayList<FileAttachmentDto>getListOfEligibleOrderNo(int countToBeFetched)throws Exception
		{
			ArrayList<FileAttachmentDto> getFileAttributes = new ArrayList<FileAttachmentDto>();
			FileAttachmentDto fileDto = new FileAttachmentDto();
			Connection conn=null;
			CallableStatement cstmt=null;
			ResultSet rs=null;
			try
			{
				conn= DbConnection.getConnectionObject();
				cstmt=conn.prepareCall(sqlFetchOrderNoForFileMigration);
				rs=cstmt.executeQuery();
				rs.setFetchSize(countToBeFetched);
				while(rs.next())
				{
					fileDto = new FileAttachmentDto();
					fileDto.setHdnOrderNo((rs.getString("ORDERNO")));
					//fileDto.setSlno(rs.getInt("SLNO"));
					//fileDto.setFileName(rs.getString("FILENAME"));
					System.out.println("OrderNo is-------------------------"+rs.getString("ORDERNO"));
					getFileAttributes.add(fileDto);
				}
			}
			catch (SQLException sqe)
			{
				Utility.SPT_LOG(true, true, sqe, "");
			}
			finally
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(cstmt);
				DbConnection.freeConnection(conn);
			}
			return getFileAttributes;			
		}
		
	private void shutdownPool(ExecutorService threadPool)
		{
			System.out.println("...in method shutdownPool()....");
			if(!threadPool.isShutdown())
			{
				threadPool.shutdown();
			}
		}
	
	public void processMigartion()throws Exception
		{	
		ArrayList<FileAttachmentDto> list=null;
			ArrayList<Callable<Object>> listOfCallable=new ArrayList<Callable<Object>>();
		
			myThreadPool=Executors.newFixedThreadPool(Integer.parseInt(Utility.getAppConfigValue("SHAREPOINT_THREAD_COUNT")));
			long fileDownloadLotId = objdao.getNewFileDownloadLotId();
			do
				{
					list=getListOfEligibleOrderNo(Integer.parseInt(Utility.getAppConfigValue("SHAREPOINT_RECORD_COUNT")));
					listOfCallable.clear();
						if(list.size()>0)
						{
							
							Utility.SPT_LOG(true, true, "lot id is :"+fileDownloadLotId);
							for(FileAttachmentDto entry:list )
							{
								listOfCallable.add(new MyCallable(Long.parseLong(entry.getHdnOrderNo()), fileDownloadLotId));
								//myThreadPool.submit(new MyCallable(Long.parseLong(entry.getHdnOrderNo())));					
							}
						}
						else
						{
							Utility.SPT_LOG(true, true, "No more eligible attachments for one time Migration");
						}
					
						//Utility.SPT_LOG(true, true, "********************Migration of files started at ************************:" + new Date()+"\n");
						myThreadPool.invokeAll(listOfCallable);
						//Utility.SPT_LOG(true, true, "********************Migration of files ended at ************************:" + new Date()+"\n");
				}while(list.size()>0);		
				
				// when no record is remaining , pool is shutdown.
				shutdownPool(MigrationUtilityWrapper.myThreadPool);
		}
	
		static class MyCallable implements Callable<Object>
		{	
			Long orderNo=null;
			long filedownloadlotid=0;
			public MyCallable(Long OrderNo,long Filedownloadlotid)
			{
				this.orderNo=OrderNo;
				this.filedownloadlotid=Filedownloadlotid;
			}
			@Override
			public String call() {
				// TODO Auto-generated method stub
				// Call processing Method here 
				try
				{
					new MigrationUtility().downloadAttachmentsForOrder(this.orderNo,this.filedownloadlotid);
				}
				catch(Exception e)
				{
					Utility.SPT_LOG(true, true, e, "");
				}
				return null;
			}
			
		}
	
		
			public static void main(String[] args) {
				try {
					//Thread.sleep(10000);
					Utility.SPT_LOG(true, true, "********************Migration of files started at ************************:" + new Date()+"\n");
					new MigrationUtilityWrapper().processMigartion();
					Utility.SPT_LOG(true, true, "********************Migration of files ended at ************************:" + new Date()+"\n");
				} catch (Exception e) {

					e.printStackTrace();
					Utility.SPT_LOG(true, true, e, "");
				}
				
			}

}
