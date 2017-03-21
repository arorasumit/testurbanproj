package com.ibm.ioes.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.ioes.forms.ACSMailTemplateDto;



public class InThreadMails implements Runnable {

	public static String PROVISIONING_MAIL_ACS="PROVISIONING_MAIL_ACS";

	public static ArrayBlockingQueue<Thread> ACS_ThreadQueue=null; 
	public static String sqlupdateStatusForACSProvisioningMail="{call IOE.SP_UPDATE_STATUS_FOR_ACSPROVISIONING(?,?)}";
	
	//lawkush Start
	

	public static String PROVISIONING_MAIL_VCS="PROVISIONING_MAIL_VCS";
	public static String PROVISIONING_MAIL_OVCC="PROVISIONING_MAIL_OVCC";
	public static ArrayBlockingQueue<Thread> VCS_ThreadQueue=null; 
	public static ArrayBlockingQueue<Thread> OVCC_ThreadQueue=null; //Added by anoop
	public static String sqlupdateStatusForMultipleProvisioningMail="{call IOE.SP_UPDATE_STATUS_FOR_MULTIPLEPROVISIONING(?,?,?)}";
	
	//lawkush End
	
	public static String ACS_DISCONNECTION_MAIL="ACS_DISCONNECTION_MAIL";
	public static String VCS_DISCONNECTION_MAIL="VCS_DISCONNECTION_MAIL";
	public static String sqlupdateStatusForDisconnectACSProvisioningMail="{call IOE.SP_UPDATE_STATUS_FOR_DISACSPROVISIONING(?,?)}";
	public static String sqlupdateStatusForDisconnectVCSProvisioningMail="{call IOE.SP_UPDATE_STATUS_FOR_MULTIPLEPROVISIONING(?,?,?)}";
	

	public static ArrayBlockingQueue<Thread> ACS_Disc_ThreadQueue=null;
	public static ArrayBlockingQueue<Thread> VCS_Disc_ThreadQueue=null; 
	public static ArrayBlockingQueue<Thread> OVCC_Disc_ThreadQueue=null; //Added by anoop
	
	
	String mailId;
	
	String to[ ];
	String cc[ ];
	String bcc[ ];
	String subject;
	String message;
	String from;
	Object[] attachment;
	String[] fileNames ;
	ArrayList<ACSMailTemplateDto > spIdList;
	int uniqueId;
	
	public void run() {
		
		if(PROVISIONING_MAIL_ACS.equals(mailId))
		{
			try{
			process_PROVISIONING_MAIL_ACS();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			ACS_ThreadQueue.remove(Thread.currentThread());
		}else if(PROVISIONING_MAIL_VCS.equals(mailId))
		{
			try{
			process_PROVISIONING_MAIL_MULTIPLE();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			VCS_ThreadQueue.remove(Thread.currentThread());
		}
		else if(PROVISIONING_MAIL_OVCC.equals(mailId))
		{
			try{
			process_PROVISIONING_MAIL_MULTIPLE();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			OVCC_ThreadQueue.remove(Thread.currentThread());
		}
		else if(ACS_DISCONNECTION_MAIL.equals(mailId))
		{
			try{
				process_PROVISIONING_MAIL_DISC_ACS();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			ACS_Disc_ThreadQueue.remove(Thread.currentThread());
		}else if(VCS_DISCONNECTION_MAIL.equals(mailId))
		{
			try{
		        process_PROVISIONING_MAIL_DISC_VCS();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			VCS_Disc_ThreadQueue.remove(Thread.currentThread());
		}
		
	}

	private void process_PROVISIONING_MAIL_MULTIPLE() throws Exception {
		//lawkush Start
		

	    int returnStatus=0;
		try
		{  String Multiple_Provisioning_Mail=null;
	       if(mailId.equals(PROVISIONING_MAIL_VCS))
		{
	    	   Multiple_Provisioning_Mail="VCS_Provisioning_Mail";
	       }
	       if(mailId.equals(PROVISIONING_MAIL_OVCC))
	       {
	    	   Multiple_Provisioning_Mail="OVCC_Provisioning_Mail";
	       }
			String filepath = AppConstants.BaseDirPathForSavingMail+Multiple_Provisioning_Mail+uniqueId+"_"+System.currentTimeMillis()+".xls";
			FileOutputStream fileOut = new FileOutputStream(filepath);
			HSSFWorkbook workbook=(HSSFWorkbook)attachment[0];
			workbook.write(fileOut);
			fileOut.close(); 
			File file =null;
			file = new File(filepath);
			
	        SendMail sendMail = new SendMail();
	        if(mailId.equals(PROVISIONING_MAIL_VCS))
		       {
			returnStatus=sendMail.postMailWithAttachmentPath(to, cc, bcc, subject, message, from, new String[]{file.getAbsolutePath()},new String[]{"VCS.xls"});
		       }
		       if(mailId.equals(PROVISIONING_MAIL_OVCC))
		       {
		    	   returnStatus=sendMail.postMailWithAttachmentPath(to, cc, bcc, subject, message, from, new String[]{file.getAbsolutePath()},new String[]{"OVCC.xls"});
		       }
			//file.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			updateMailResultForMultiple(returnStatus,spIdList,mailId);
		}

		
		
		//lawkush End
		
	}

	private void process_PROVISIONING_MAIL_ACS() throws Exception {

        int returnStatus=0;
		try
		{
			
			String filepath = AppConstants.BaseDirPathForSavingMail+"ACS_Provisioning_Mail_"+uniqueId+"_"+System.currentTimeMillis()+".xls";
			FileOutputStream fileOut = new FileOutputStream(filepath);
			HSSFWorkbook workbook=(HSSFWorkbook)attachment[0];
			workbook.write(fileOut);
			fileOut.close(); 
			File file =null;
			file = new File(filepath);
			
	        SendMail sendMail = new SendMail();
	        
			returnStatus=sendMail.postMailWithAttachmentPath(to, cc, bcc, subject, message, from, new String[]{file.getAbsolutePath()},new String[]{"ACS.xls"});
			//file.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			updateMailResultForACS(returnStatus,spIdList);
		}
			
	}
	
	synchronized static void updateMailResultForACS(int returnStatus,ArrayList<ACSMailTemplateDto > list)
	{
		
		Connection connection =null;
		CallableStatement pstmt =null;
		try
		{
			String spIds=null;
			for (int i=0; i<list.size(); i++)
			{
				if(spIds==null)
				{
					spIds=String.valueOf(list.get(i).getServiceProductId());
				}else{
					spIds=spIds+ ","+String.valueOf(list.get(i).getServiceProductId());
				}
			}
			
			connection= DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			
			if(returnStatus==1)
			{
				//update success
				pstmt= connection.prepareCall(sqlupdateStatusForACSProvisioningMail);
				pstmt.setInt(1, 3);	//3 for Success
				pstmt.setString(2, spIds);
				pstmt.execute();
			}
			else
			{
				//update failure
				pstmt= connection.prepareCall(sqlupdateStatusForACSProvisioningMail);
				pstmt.setInt(1, 4);	//4 For Failure
				pstmt.setString(2, spIds);
				pstmt.execute();
			}
			connection.commit();
		}
		catch(Exception e)
		{
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally{
			try{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	//lawkush Start
	
	synchronized static void updateMailResultForMultiple(int returnStatus,ArrayList<ACSMailTemplateDto > list,String provisioningMail)
	{
		
		Connection connection =null;
		CallableStatement pstmt =null;
		try
		{
			String spIds=null;
			for (int i=0; i<list.size(); i++)
			{
				if(spIds==null)
				{
					spIds=String.valueOf(list.get(i).getServiceProductId());
				}else{
					spIds=spIds+ ","+String.valueOf(list.get(i).getServiceProductId());
				}
			}
			
			connection= DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			
			if(returnStatus==1)
			{
				//update success
				pstmt= connection.prepareCall(sqlupdateStatusForMultipleProvisioningMail);
			    if(provisioningMail.equals("PROVISIONING_MAIL_OVCC"))
			    {
				pstmt.setInt(1, 3);	//3 for Success
				pstmt.setString(2, spIds);
			    	pstmt.setString(3,"PROVISIONING_MAIL_OVCC");
				pstmt.execute();
				}
			    else 
			    {
			    	pstmt.setInt(1, 3);	//3 for Success
			      	pstmt.setString(2, spIds);
				  	pstmt.setString(3,"PROVISIONING_MAIL_VCS");
				  	pstmt.execute();
			    }      
			}
			else
			{
				//update failure
				//added by anoop
				   if(provisioningMail.equals("PROVISIONING_MAIL_OVCC"))
				   {
					   pstmt.setInt(1, 3);	//3 for Success
				pstmt.setString(2, spIds);
					   pstmt.setString(3,"PROVISIONING_MAIL_OVCC");
				pstmt.execute();
				   }
				   else 
				   {
					   pstmt.setInt(1, 3);	//3 for Success
					   pstmt.setString(2, spIds);
					   pstmt.setString(3,"PROVISIONING_MAIL_VCS");
					   pstmt.execute();
				   }   
			}
			connection.commit();
		}
		catch(Exception e)
		{
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally{
			try{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	
	
	//lawkush End
	

	private void process_PROVISIONING_MAIL_DISC_VCS() {


        int returnStatus=0;
		try
		{
			
			String filepath = AppConstants.BaseDirPathForSavingMail+"DISC_ACS_Provisioning_Mail_"+uniqueId+"_"+System.currentTimeMillis()+".xls";
			FileOutputStream fileOut = new FileOutputStream(filepath);
			HSSFWorkbook workbook=(HSSFWorkbook)attachment[0];
			workbook.write(fileOut);
			fileOut.close(); 
			File file =null;
			file = new File(filepath);
			
	        SendMail sendMail = new SendMail();
	        
			returnStatus=sendMail.postMailWithAttachmentPath(to, cc, bcc, subject, message, from, new String[]{file.getAbsolutePath()},new String[]{"DISCVCS.xls"});
			//file.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			updateMailResultForDISCVCS(returnStatus,spIdList);
		}
			
	
		
	}

	synchronized public void updateMailResultForDISCVCS(int returnStatus,
			ArrayList<ACSMailTemplateDto> list) {

		
		Connection connection =null;
		CallableStatement pstmt =null;
		try
		{
			String spIds=null;
			for (int i=0; i<list.size(); i++)
			{
				if(spIds==null)
				{
					spIds=String.valueOf(list.get(i).getServiceProductId());
				}else{
					spIds=spIds+ ","+String.valueOf(list.get(i).getServiceProductId());
				}
			}
			
			connection= DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			
			if(returnStatus==1)
			{
				//update success
				pstmt= connection.prepareCall(sqlupdateStatusForDisconnectVCSProvisioningMail);
				pstmt.setInt(1, 3);	//3 for Success
				pstmt.setString(2, spIds);
				pstmt.setString(3, "VCS_DISCONNECTION_MAIL");
				pstmt.execute();
			}
			else
			{
				//update failure
				pstmt= connection.prepareCall(sqlupdateStatusForDisconnectVCSProvisioningMail);
				pstmt.setInt(1, 4);	//4 For Failure
				pstmt.setString(2, spIds);
				pstmt.setString(3, "VCS_DISCONNECTION_MAIL");
				pstmt.execute();
			}
			connection.commit();
		}
		catch(Exception e)
		{
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally{
			try{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
		
	}

	private void process_PROVISIONING_MAIL_DISC_ACS() throws Exception {
		// TODO Auto-generated method stub


        int returnStatus=0;
		try
		{
			
			String filepath = AppConstants.BaseDirPathForSavingMail+"DISC_ACS_Provisioning_Mail_"+uniqueId+"_"+System.currentTimeMillis()+".xls";
			FileOutputStream fileOut = new FileOutputStream(filepath);
			HSSFWorkbook workbook=(HSSFWorkbook)attachment[0];
			workbook.write(fileOut);
			fileOut.close(); 
			File file =null;
			file = new File(filepath);
			
	        SendMail sendMail = new SendMail();
	        
			returnStatus=sendMail.postMailWithAttachmentPath(to, cc, bcc, subject, message, from, new String[]{file.getAbsolutePath()},new String[]{"DISCACS.xls"});
			//file.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			updateMailResultForDISCACS(returnStatus,spIdList);
		}
			
	
		
	}
	
	synchronized public void updateMailResultForDISCACS(int returnStatus,
			ArrayList<ACSMailTemplateDto> list) {

		
		Connection connection =null;
		CallableStatement pstmt =null;
		try
		{
			String spIds=null;
			for (int i=0; i<list.size(); i++)
			{
				if(spIds==null)
				{
					spIds=String.valueOf(list.get(i).getServiceProductId());
				}else{
					spIds=spIds+ ","+String.valueOf(list.get(i).getServiceProductId());
				}
			}
			
			connection= DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			
			if(returnStatus==1)
			{
				//update success
				pstmt= connection.prepareCall(sqlupdateStatusForDisconnectACSProvisioningMail);
				pstmt.setInt(1, 3);	//3 for Success
				pstmt.setString(2, spIds);
				pstmt.execute();
			}
			else
			{
				//update failure
				pstmt= connection.prepareCall(sqlupdateStatusForDisconnectACSProvisioningMail);
				pstmt.setInt(1, 4);	//4 For Failure
				pstmt.setString(2, spIds);
				pstmt.execute();
			}
			connection.commit();
		}
		catch(Exception e)
		{
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally{
			try{
				DbConnection.closePreparedStatement(pstmt);
				DbConnection.freeConnection(connection);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
		
	}

	public Object[] getAttachment() {
		return attachment;
	}

	public void setAttachment(Object[] attachment) {
		this.attachment = attachment;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public ArrayList<ACSMailTemplateDto> getSpIdList() {
		return spIdList;
	}

	public void setSpIdList(ArrayList<ACSMailTemplateDto> spIdList) {
		this.spIdList = spIdList;
	}

}
