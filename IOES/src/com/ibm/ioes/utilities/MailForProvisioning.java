package com.ibm.ioes.utilities;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString; 
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.ioes.forms.ACSMailTemplateDto;

public class MailForProvisioning {
	
	
	public static String sqlGetFxAccountsForProvisiongMailACS="{call IOE.SP_GET_FXACCOUNT_FOR_PROVISIONING()}";
	public static String sqlupdateStatusForACSProvisioningMail="{call IOE.SP_UPDATE_STATUS_FOR_ACSPROVISIONING(?,?)}";
	
	//Start lawkush VCS
	
	public static String sqlGetFxAccountsForProvisiongMailMultiple="{call IOE.SP_GET_FXACCOUNT_FOR_PROVISIONING_MULTIPLE(?)}";
	public static String sqlupdateStatusForMultipleProvisioningMail="{call IOE.SP_UPDATE_STATUS_FOR_MULTIPLEPROVISIONING(?,?,?)}";
	public static String sqlGetForMultipleServices_PackageName="{call IOE.SP_GET_VCS_PACKAGENAME(?)}";
	private static final String sqlGetForMultipleServices_DispatchAddress = "{call IOE.SP_GET_VCS_DISPATCH_ADDRESS_NAME(?)}";
	
	//End lawkush 
	
	ArrayBlockingQueue<Thread> ACS_ThreadQueue=null; 
	ArrayBlockingQueue<Thread> OVCC_ThreadQueue=null; 
 
	public int sendACSProvisiongMail()  throws Exception {
		int status=0;
		ArrayList<ACSMailTemplateDto> mailDataList = new ArrayList<ACSMailTemplateDto>();
		Connection connection =null;
		try
		{	
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			mailDataList=getFXAccountSuccessData_ACS(connection);
			connection.commit();
			/*Collections.sort(mailDataList, 
								new Comparator<ACSMailTemplateDto>()
									{
										public int compare(ACSMailTemplateDto o1, ACSMailTemplateDto o2) {
											if(o1.getOrderNo()>o2.getOrderNo())
											{
												return 1;
											}
											else if(o1.getOrderNo()==o2.getOrderNo())
											{
												return 0;
											}else if(o1.getOrderNo()<o2.getOrderNo())
											{
												return -1;
											}
											return 0;
										}
									}
									);*/
			HashMap<Long,ArrayList<ACSMailTemplateDto>> hmap=new HashMap<Long, ArrayList<ACSMailTemplateDto>>();
			HashMap<Long,ACSMailTemplateDto> hmap_OrderLevelData=new HashMap<Long, ACSMailTemplateDto>();
			HashMap<Long,String> hmap_TemplateId=new HashMap<Long,String >();
			
			for (ACSMailTemplateDto dto : mailDataList) {
				long key=dto.getOrderNo();
				ArrayList<ACSMailTemplateDto> savedValue=hmap.get(key);
				if(savedValue==null)
				{
					savedValue=new ArrayList<ACSMailTemplateDto>();
					hmap.put(key, savedValue);
										
					ACSMailTemplateDto orderLevelData =new ACSMailTemplateDto();
					orderLevelData.setAccMgrEmailId(dto.getAccMgrEmailId());
					hmap_OrderLevelData.put(key,orderLevelData);
					hmap_TemplateId.put(key,dto.getConfigAattibuteValue());
				}
					savedValue.add(dto);
			}
			
			Set<Long> orders=hmap.keySet();
			
			String bridgeTo=Utility.getAppConfigValue(connection,"ACS_PROVISIONING_MAIL_SEND_TO");;
			int uniqueId=0;
			for (Long orderNo : orders) {
				HSSFWorkbook workbook=generateExcel(hmap.get(orderNo));
				uniqueId++;
				
				
				String to[]=null;
				String cc[]=null;
				String bcc[]=null;
				to=new String[]{bridgeTo};
				cc=new String[]{hmap_OrderLevelData.get(orderNo).getAccMgrEmailId()};
				
				String from=null;
				
				IB2BMailDaoImpl conferenceMailDaoImpl=new IB2BMailDaoImpl();
				IB2BMailDto mailDto= new IB2BMailDto();
				mailDto.setMailTemplateType(hmap_TemplateId.get(orderNo));
				IB2BMailDto values=conferenceMailDaoImpl.fetchEmailTemplate(connection,mailDto);
				String message=values.getMailTemplateBody();
		        String subject=values.getMailTemplateSubject();
		        message=message.replaceAll("(?i)\\{\\{orderNo\\}\\}",orderNo.toString());
		        subject=subject.replaceAll("(?i)\\{\\{orderNo\\}\\}",orderNo.toString());
		        
		        
		        connection.commit();
				//sendMailInThread( "PROVISIONING_MAIL_ACS" ,to, cc, bcc, subject, message, from, new Object[]{workbook},new String[]{"ACS Provisioning.xls"});
				
				InThreadMails inThreadMails=new InThreadMails();
				inThreadMails.setMailId(InThreadMails.PROVISIONING_MAIL_ACS);
				inThreadMails.setTo(to);
				inThreadMails.setCc(cc);
				inThreadMails.setBcc(bcc);
				inThreadMails.setSubject(subject);
				inThreadMails.setMessage(message);
				inThreadMails.setFrom(from);
				inThreadMails.setAttachment(new Object[]{workbook});
				inThreadMails.setFileNames(new String[]{"ACS Provisioning.xls"});
				inThreadMails.setUniqueId(uniqueId);
				inThreadMails.setSpIdList(hmap.get(orderNo));
				
				startThreadWithMaxLimit(InThreadMails.PROVISIONING_MAIL_ACS,inThreadMails);
				
				/*String filepath = baseDirPath+"ACS_Provisioning_Mail"+System.currentTimeMillis()+".xls";
				FileOutputStream fileOut = new FileOutputStream(filepath);
				workbook.write(fileOut);
				fileOut.close(); 
				File file =null;
				file = new File(filepath);*/
				
		       /* int returnStatus=0;
				returnStatus=sendMail.postMailWithAttachmentPath(to, cc, bcc, subject, message, from, new String[]{file.getAbsolutePath()},new String[]{"Product Plan.xls"});
				file.delete();*/
				
				/*if(returnStatus==1)
				{
					//update success
					callstmt= connection.prepareCall(sqlupdateStatusForACSProvisioningMail);
					callstmt.setLong(1, orderNo);
					callstmt.setInt(2, 3);	//3 for Success
					callstmt.execute();
				}
				else
				{
					//update failure
					callstmt= connection.prepareCall(sqlupdateStatusForACSProvisioningMail);
					callstmt.setLong(1, orderNo);
					callstmt.setInt(2, 4);	//4 For Failure
					callstmt.execute();
				}*/
				
			}
			
		}
		catch (Exception ex) {
			connection.rollback();
			Utility.LOG(true,true,"Error while generating mail for provisiong : "+new Date());
		}
		finally{
			try{
				DbConnection.freeConnection(connection);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
			
		return status;
	}
	
	private void startThreadWithMaxLimit(String mailId, InThreadMails inThreadMails) {
		if(InThreadMails.PROVISIONING_MAIL_ACS.equals(mailId))
		{
			if(ACS_ThreadQueue==null)
			{
				int max;
				try {
					max = Integer.parseInt(Utility.getAppConfigValue("PROVISIONING_MAIL_ACS_MAX_THREAD"));
				} catch (Exception e) {
					max=1;
					e.printStackTrace();
				}
				ACS_ThreadQueue=new ArrayBlockingQueue<Thread>(max);
				InThreadMails.ACS_ThreadQueue=ACS_ThreadQueue;
			}
			Thread t=new Thread(inThreadMails);
			try {
				ACS_ThreadQueue.put(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t.start();
							
		}else if(InThreadMails.PROVISIONING_MAIL_VCS.equals(mailId))
		{
			if(VCS_ThreadQueue==null)
			{
				int max;
				try {
					max = Integer.parseInt(Utility.getAppConfigValue("PROVISIONING_MAIL_VCS_MAX_THREAD"));
				} catch (Exception e) {
					max=1;
					e.printStackTrace();
				}
				VCS_ThreadQueue=new ArrayBlockingQueue<Thread>(max);
				InThreadMails.VCS_ThreadQueue=VCS_ThreadQueue;
			}
			Thread t=new Thread(inThreadMails);
			try {
				VCS_ThreadQueue.put(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t.start();
							
		}
		else if(InThreadMails.PROVISIONING_MAIL_OVCC.equals(mailId))
		{
			if(OVCC_ThreadQueue==null)
			{
				int max;
				try {
					max = Integer.parseInt(Utility.getAppConfigValue("PROVISIONING_MAIL_VCS_MAX_THREAD"));
				} catch (Exception e) {
					max=1;
					e.printStackTrace();
				}
				OVCC_ThreadQueue=new ArrayBlockingQueue<Thread>(max);
				InThreadMails.OVCC_ThreadQueue=OVCC_ThreadQueue;
			}
			Thread t=new Thread(inThreadMails);
			try {
				OVCC_ThreadQueue.put(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t.start();
		}
	}

	
	public ArrayList<ACSMailTemplateDto> getFXAccountSuccessData_ACS(Connection connection) throws Exception
	{
		CallableStatement callstmt =null;
		CallableStatement callstmt1=null;
		ResultSet rs = null;
		ACSMailTemplateDto objDto =null;
		ArrayList<ACSMailTemplateDto> mailDataList = new ArrayList<ACSMailTemplateDto>();
		String spIds=null;
		try{
			callstmt= connection.prepareCall(sqlGetFxAccountsForProvisiongMailACS);	
			rs = callstmt.executeQuery();
			while(rs.next())
			{
				objDto= new ACSMailTemplateDto();
				objDto.setOrderNo(rs.getInt("ORDERNO"));
				objDto.setDateOfCreation(Utility.showDate_Report(rs.getString("DATEOFCREATION")));
				objDto.setServiceProductId(rs.getInt("SERVICEPRODUCTID"));
				
				objDto.setRegion(rs.getString("REGION"));
				objDto.setParentId(rs.getString("PARENT_ID"));
				objDto.setChildId(rs.getString("CHILD_ID"));
				
				objDto.setAcsId(rs.getString("ACSID"));
				objDto.setServiceType(rs.getString("SERVICETYPE"));
				objDto.setConfigAattibuteValue(rs.getString("CONFIGAATTIBUTEVALUE"));
				objDto.setTypeOfId(rs.getString("TYPE_OF_ID"));
				objDto.setCustomerName(rs.getString("CUSTOMERNAME"));
				objDto.setAddress(rs.getString("BCP_ADDRESS"));
				objDto.setCity(rs.getString("BCP_CITY"));
				objDto.setPinNo(rs.getString("PIN"));
				objDto.setChairPersonDesignation(rs.getString("CP_DESIGNATION"));
				objDto.setChairPersonName(rs.getString("CP_NAME"));
				objDto.setChairPersonEmailId(rs.getString("CP_EMAIL_ID"));
				objDto.setChairPersonPhoneNo(rs.getString("CP_PHONE"));
				objDto.setBcpName(rs.getString("BCP_NAME"));
				objDto.setBcpDesignation(rs.getString("BCP_DESIGNATION"));
				objDto.setBcpEmail(rs.getString("BCP_EMAIL_ID"));
				
				objDto.setBcpPhone(rs.getString("TELEPHONENO"));
				
				objDto.setAccMgrName(rs.getString("ACC_MGR_NAME"));
				objDto.setAccMgrDesignation("");
				objDto.setAccMgrEmailId(rs.getString("ACC_MGR_EMAIL_ID"));
				objDto.setAccMgrphone(rs.getString("ACC_MGR_PHONE"));
				objDto.setWebConf(rs.getString("WEBCONFERENCEREQUIRED"));
				objDto.setWebConfRental(rs.getString("NOOFLICENSE"));
				if(spIds == null)
				{
					spIds=String.valueOf(rs.getInt("SERVICEPRODUCTID"));
				}
				else
				{
					spIds=spIds +" ,"+ String.valueOf(rs.getInt("SERVICEPRODUCTID"));
				}
				mailDataList.add(objDto);
			}
			if(spIds != null)
			{
				callstmt1= connection.prepareCall(sqlupdateStatusForACSProvisioningMail);
				callstmt1.setInt(1, 2);
				callstmt1.setString(2, spIds);
				callstmt1.execute();
			}
		}	
		catch(Exception ex )
		{
			Utility.LOG(true, true, ex,"");
			throw ex;
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.closeCallableStatement(callstmt1);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return mailDataList;
	}
	
	public HSSFWorkbook generateExcel(ArrayList<ACSMailTemplateDto> mailDataList)
	{
		HSSFWorkbook wb = null;
		try{
			ACSMailTemplateDto mailObjDto= null;
			wb = new HSSFWorkbook();
			HSSFSheet mailProvisionSheet = wb.createSheet("Mail Provision");
			HSSFCellStyle headerCellStyle = wb.createCellStyle();
			HSSFFont boldFont = wb.createFont();
	        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerCellStyle.setFont(boldFont);
	        
	        
	        HSSFRow excelRow = null;
	        HSSFCell excelCell = null;
	        
	        excelRow = mailProvisionSheet.createRow(0);
	        excelRow = mailProvisionSheet.createRow(0);
	        
	        int i_cell=0;
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Date of Creation"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Quote No./ISS Order No."));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Region"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Parent A/C ID(Billed)"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Child ID (Unbilled)"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Premium ACS ID"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Type of ID"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Customer Name"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Address"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("City State"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Pin"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Moderator Name"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Designation"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Email"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Phone No."));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Contact Person"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Designation"));
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Email"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Phone No."));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Account Manager Name"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Designation"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Email ID/ Phone No"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Web Conf (Y/N)"));
	        
	        excelCell = excelRow.createCell(i_cell++);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Web Conf Rental"));
	        
	        int iCount = 1;
	        for (int i=0;i<mailDataList.size();i++) {
	        	mailObjDto=mailDataList.get(i);
	        	excelRow = mailProvisionSheet.createRow(iCount++);
	            
	        	i_cell=0;
	        	
	        	excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getDateOfCreation())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getOrderNo())));
	            
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getRegion())));
	            
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getParentId())));
	            
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChildId())));
	            
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getAcsId())));
	            /*excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getServiceType()));*/
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getTypeOfId())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getCustomerName())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getAddress())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getCity())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getPinNo())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChairPersonName())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChairPersonDesignation())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChairPersonEmailId())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChairPersonPhoneNo())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getBcpName())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getBcpDesignation())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getBcpEmail())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getBcpPhone())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getAccMgrName())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getAccMgrDesignation())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getAccMgrphone())+"/"+Utility.fnCheckNull(mailObjDto.getAccMgrEmailId())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getWebConf())));
	            excelCell = excelRow.createCell(i_cell++);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getWebConfRental())));
	    	}
		}
		catch (Exception ex)
		{
			Utility.LOG(true,true,"Error while fetching accounts for provisiong : "+new Date());
		}
		return wb;
	}
	
	//lawkush Start VCS Code Start
	
	
	ArrayBlockingQueue<Thread> VCS_ThreadQueue=null; 
	 
	public int sendMultipleProvisiongMail(String mailProvisiningFlag)  throws Exception {
		int status=0;
		ArrayList<ACSMailTemplateDto> mailDataList = new ArrayList<ACSMailTemplateDto>();
		Connection connection =null;
		
		try
		{	
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);
			mailDataList=getFXAccountSuccessData_Multiple(connection,mailProvisiningFlag);
			connection.commit();
			/*Collections.sort(mailDataList, 
								new Comparator<ACSMailTemplateDto>()
									{
										public int compare(ACSMailTemplateDto o1, ACSMailTemplateDto o2) {
											if(o1.getOrderNo()>o2.getOrderNo())
											{
												return 1;
											}
											else if(o1.getOrderNo()==o2.getOrderNo())
											{
												return 0;
											}else if(o1.getOrderNo()<o2.getOrderNo())
											{
												return -1;
											}
											return 0;
										}
									}
									);*/
			HashMap<Long,ArrayList<ACSMailTemplateDto>> hmap=new HashMap<Long, ArrayList<ACSMailTemplateDto>>();
			HashMap<Long,ACSMailTemplateDto> hmap_OrderLevelData=new HashMap<Long, ACSMailTemplateDto>();
			HashMap<Long,String> hmap_TemplateId=new HashMap<Long,String >();
			
			for (ACSMailTemplateDto dto : mailDataList) {
				long key=dto.getOrderNo();
				ArrayList<ACSMailTemplateDto> savedValue=hmap.get(key);
				if(savedValue==null)
				{
					savedValue=new ArrayList<ACSMailTemplateDto>();
					hmap.put(key, savedValue);
										
					ACSMailTemplateDto orderLevelData =new ACSMailTemplateDto();
					orderLevelData.setAccMgrEmailId(dto.getAccMgrEmailId());
					hmap_OrderLevelData.put(key,orderLevelData);
					hmap_TemplateId.put(key,dto.getConfigAattibuteValue());
				}
					savedValue.add(dto);
			}
			
			Set<Long> orders=hmap.keySet();
			String bridgeTo=null;
			if("PROVISIONING_MAIL_VCS".equals(mailProvisiningFlag))
			{
				bridgeTo=Utility.getAppConfigValue(connection,"VCS_PROVISIONING_MAIL_SEND_TO");
			}
			else
			{
				bridgeTo=Utility.getAppConfigValue(connection,"OVCC_PROVISIONING_MAIL_SEND_TO");
			}
			
	 
			int uniqueId=0;
			for (Long orderNo : orders) {
				HSSFWorkbook workbook=generateExcel_Multiple(hmap.get(orderNo),mailProvisiningFlag);
				uniqueId++;
				
				
				String to[]=null;
				String cc[]=null;
				String bcc[]=null;
				to=new String[]{bridgeTo};
				cc=new String[]{hmap_OrderLevelData.get(orderNo).getAccMgrEmailId()};
				
				String from=null;
				
				IB2BMailDaoImpl conferenceMailDaoImpl=new IB2BMailDaoImpl();
				IB2BMailDto mailDto= new IB2BMailDto();
				mailDto.setMailTemplateType(hmap_TemplateId.get(orderNo));
				IB2BMailDto values=conferenceMailDaoImpl.fetchEmailTemplate(connection,mailDto);
				String message=values.getMailTemplateBody();
		        String subject=values.getMailTemplateSubject();
		        message=message.replaceAll("(?i)\\{\\{orderNo\\}\\}",orderNo.toString());
		        subject=subject.replaceAll("(?i)\\{\\{orderNo\\}\\}",orderNo.toString());
		        
		        
		        connection.commit();
				//sendMailInThread( "PROVISIONING_MAIL_VCS" ,to, cc, bcc, subject, message, from, new Object[]{workbook},new String[]{"VCS Provisioning.xls"});
				
				InThreadMails inThreadMails=new InThreadMails();
				if("PROVISIONING_MAIL_VCS".equals(mailProvisiningFlag))
				{
				inThreadMails.setMailId(InThreadMails.PROVISIONING_MAIL_VCS);
				}
				else
				{
					inThreadMails.setMailId(InThreadMails.PROVISIONING_MAIL_OVCC);
				}
				inThreadMails.setTo(to);
				inThreadMails.setCc(cc);
				inThreadMails.setBcc(bcc);
				inThreadMails.setSubject(subject);
				inThreadMails.setMessage(message);
				inThreadMails.setFrom(from);
				inThreadMails.setAttachment(new Object[]{workbook});
				if("PROVISIONING_MAIL_VCS".equals(mailProvisiningFlag))
				{
				inThreadMails.setFileNames(new String[]{"VCS Provisioning.xls"});
				}
				else
				{
					inThreadMails.setFileNames(new String[]{"OVCC Provisioning.xls"});
				}
				inThreadMails.setUniqueId(uniqueId);
				inThreadMails.setSpIdList(hmap.get(orderNo));
				
				//lawkush testing start
				
				System.out.println("Sending Mail For Order No."+hmap.get(orderNo)+" VCS start...........");
						System.out.println("To  ..."+to);
						System.out.println("CC......"+cc);
						System.out.println("BCC...."+bcc);
						System.out.println("SUBJECT........"+subject);
						System.out.println("MESSAGE...."+message);
						System.out.println("FROM...."+from);
				System.out.println("Sending Mail For Order No."+hmap.get(orderNo)+" VCS end...........");
				
				//lawkush testing end
				
				if(mailProvisiningFlag.equals("PROVISIONING_MAIL_VCS"))
				{
				startThreadWithMaxLimit(InThreadMails.PROVISIONING_MAIL_VCS,inThreadMails);
				}
				else
				{
					startThreadWithMaxLimit(InThreadMails.PROVISIONING_MAIL_OVCC,inThreadMails);
				}
				
				/*String filepath = baseDirPath+"VCS_Provisioning_Mail"+System.currentTimeMillis()+".xls";
				FileOutputStream fileOut = new FileOutputStream(filepath);
				workbook.write(fileOut);
				fileOut.close(); 
				File file =null;
				file = new File(filepath);*/
				
		       /* int returnStatus=0;
				returnStatus=sendMail.postMailWithAttachmentPath(to, cc, bcc, subject, message, from, new String[]{file.getAbsolutePath()},new String[]{"Product Plan.xls"});
				file.delete();*/
				
				/*if(returnStatus==1)
				{
					//update success
					callstmt= connection.prepareCall(sqlupdateStatusForVCSProvisioningMail);
					callstmt.setLong(1, orderNo);
					callstmt.setInt(2, 3);	//3 for Success
					callstmt.execute();
				}
				else
				{
					//update failure
					callstmt= connection.prepareCall(sqlupdateStatusForVCSProvisioningMail);
					callstmt.setLong(1, orderNo);
					callstmt.setInt(2, 4);	//4 For Failure
					callstmt.execute();
				}*/
				
			}
			
		}
		catch (Exception ex) {
			connection.rollback();
			Utility.LOG(true,true,"Error while generating mail for provisiong : "+new Date());
		}
		finally{
			try{
				DbConnection.freeConnection(connection);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
			
		return status;
	}
	
	/*private void startThreadWithMaxLimit_VCS(String mailId, InThreadMails inThreadMails) {
		if(InThreadMails.PROVISIONING_MAIL_VCS.equals(mailId))
		{
			if(VCS_ThreadQueue==null)
			{
				int max;
				try {
					max = Integer.parseInt(Utility.getAppConfigValue("PROVISIONING_MAIL_VCS_MAX_THREAD"));
				} catch (Exception e) {
					max=1;
					e.printStackTrace();
				}
				VCS_ThreadQueue=new ArrayBlockingQueue<Thread>(max);
				InThreadMails.VCS_ThreadQueue=VCS_ThreadQueue;
			}
			Thread t=new Thread(inThreadMails);
			try {
				VCS_ThreadQueue.put(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t.start();
							
		}else if(InThreadMails.PROVISIONING_MAIL_VCS.equals(mailId))
		{
			
		}
	}*/

	
	public ArrayList<ACSMailTemplateDto> getFXAccountSuccessData_Multiple(Connection connection, String mailProvisiningFlag) throws Exception
	{
		CallableStatement callstmt =null;
		CallableStatement callstmt1=null;
		CallableStatement callstmt2=null;
		ResultSet rs = null;
		ACSMailTemplateDto objDto =null;
		ArrayList<ACSMailTemplateDto> mailDataList = new ArrayList<ACSMailTemplateDto>();
		
		
		String spIds=null;
		try{
			callstmt= connection.prepareCall(sqlGetFxAccountsForProvisiongMailMultiple);	
			callstmt.setString(1, mailProvisiningFlag);
			rs = callstmt.executeQuery();
			while(rs.next())
			{
				objDto= new ACSMailTemplateDto();
				objDto.setDateOfCreation(Utility.showDate_Report(rs.getString("DATEOFCREATION")));
				objDto.setAccountName(rs.getString("ACCOUNTNAME"));
				objDto.setOrderNo(rs.getInt("ORDERNO"));
				objDto.setProductName(rs.getString("PRODUCT"));
				objDto.setConfigAattibuteValue(rs.getString("CONFIGAATTIBUTEVALUE"));
				objDto.setParentId(rs.getString("PARENTID"));
				objDto.setChildId(rs.getString("CHILDID"));
				objDto.setVcsId(rs.getString("VCSID"));
			//	objDto.setBundledVcsPlan(rs.getString("BUNDLES_VCS_PLAN"));
				objDto.setChairPersonName(rs.getString("CP_NAME"));
				objDto.setBcpName(rs.getString("BCP_NAME"));
				//objDto.setBillingAddress(rs.getString("CUST_ADDRESS"));
				objDto.setBillingAddress(rs.getString("BCP_ADDRESS"));
			//	objDto.setAddress(rs.getString("ADDRESS"));
				objDto.setStatus(rs.getString("STATUS"));
				objDto.setAccMgrEmailId(rs.getString("ACC_MGR_EMAIL_ID"));
				objDto.setServiceProductId(rs.getInt("SERVICEPRODUCTID"));
				
				if(spIds == null)
				{
					spIds=String.valueOf(rs.getInt("SERVICEPRODUCTID"));
				}
				else
				{
					spIds=spIds +" ,"+ String.valueOf(rs.getInt("SERVICEPRODUCTID"));
				}

				mailDataList.add(objDto);
			}
			
			//lawkush Start Fetching Packagename List
			ArrayList<ACSMailTemplateDto> packageDataList = new ArrayList<ACSMailTemplateDto>();
			ACSMailTemplateDto objDto1= null;
			callstmt2= connection.prepareCall(sqlGetForMultipleServices_PackageName);	
			callstmt2.setString(1, spIds);
			rs = callstmt2.executeQuery();
			while(rs.next())
			{
				objDto1= new ACSMailTemplateDto();
				 objDto1.setServiceProductId(rs.getInt("SERVICEPRODUCTID"));
				 objDto1.setPackageName(rs.getString("PACKAGE_NAME"));
				 packageDataList.add(objDto1);
			}
			
			String oldSPiD=null,csv_package=null,currentSpId=null;
			HashMap<String,String> hmapNew=new HashMap<String, String>();
			for (ACSMailTemplateDto dto :packageDataList)
			{
				
				currentSpId=String.valueOf(dto.getServiceProductId());
				
				if(!currentSpId.equalsIgnoreCase(oldSPiD))
				{
					if(oldSPiD!=null )
					{
						hmapNew.put(oldSPiD, csv_package);
						csv_package=dto.getPackageName();
					}
					else 
					{
						csv_package=dto.getPackageName();
					}
				
				}
				else
				{
					csv_package=csv_package+','+dto.getPackageName();
				}
			
				oldSPiD=currentSpId;
			
			}
			if(packageDataList.size()!=0)
			{
				hmapNew.put(oldSPiD, csv_package);
			}
			
			for(ACSMailTemplateDto dto :mailDataList)
			{
				dto.setPackageName(hmapNew.get(String.valueOf(dto.getServiceProductId())));
			}
			
			//lawkush End Fetching Package Name List
//			lawkush Start Fetching Dispatch Address List
			
			ArrayList<ACSMailTemplateDto> dispatchDataList = new ArrayList<ACSMailTemplateDto>();
			ACSMailTemplateDto objDto2=null;
			callstmt2= connection.prepareCall(sqlGetForMultipleServices_DispatchAddress);	
			callstmt2.setString(1, spIds);
			rs = callstmt2.executeQuery();
			while(rs.next())
			{
				objDto2= new ACSMailTemplateDto();
				objDto2.setServiceProductId(rs.getInt("SERVICEPRODUCTID"));
				objDto2.setHwAddress(rs.getString("DISPATCHADDNAME"));
				 dispatchDataList.add(objDto2);
			}
			
			String oldSPiD_dispatch=null,csv_dispatch=null,currentSpId_dispatch=null;
			HashMap<String,String> hmapNew_dispatch=new HashMap<String, String>();
			for (ACSMailTemplateDto dto :dispatchDataList)
			{
				
				currentSpId_dispatch=String.valueOf(dto.getServiceProductId());
				
				if(!currentSpId_dispatch.equalsIgnoreCase(oldSPiD_dispatch))
				{
					if(oldSPiD_dispatch!=null)
					{
						hmapNew_dispatch.put(oldSPiD_dispatch, csv_dispatch);
						csv_dispatch=dto.getHwAddress();
					}
					else
					{
						csv_dispatch=dto.getHwAddress();
					}
				}
				else
				{
					csv_dispatch=csv_dispatch+','+dto.getHwAddress();
				}
				
				oldSPiD_dispatch=currentSpId_dispatch;
			
			}
			
			//lawkush Start
			if(dispatchDataList.size()!=0)
			{
				hmapNew_dispatch.put(oldSPiD, csv_dispatch);
			}
			//lawkush End
			
			for(ACSMailTemplateDto dto :mailDataList)
			{
				dto.setHwAddress(hmapNew_dispatch.get(String.valueOf(dto.getServiceProductId())));
			}
			
			//	lawkush Start Fetching Dispatch Address List
			
			if(spIds != null)
			{
				callstmt1= connection.prepareCall(sqlupdateStatusForMultipleProvisioningMail);
				callstmt1.setInt(1, 2);
				callstmt1.setString(2, spIds);
				callstmt1.setString(3, mailProvisiningFlag);
				callstmt1.execute();
			}
		}	
		catch(Exception ex )
		{
			Utility.LOG(true, true, ex,"");
			throw ex;
		}
		finally
		{
			try
			{
				DbConnection.closeResultset(rs);
				DbConnection.closeCallableStatement(callstmt);
				DbConnection.closeCallableStatement(callstmt1);
				DbConnection.closeCallableStatement(callstmt2);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return mailDataList;
	}
	
	public HSSFWorkbook generateExcel_Multiple(ArrayList<ACSMailTemplateDto> mailDataList,String mailProvisioningFlag)
	{
		HSSFWorkbook wb = null;
		try{
			ACSMailTemplateDto mailObjDto= null;
			wb = new HSSFWorkbook();
			HSSFSheet mailProvisionSheet = wb.createSheet("Mail Provision");
			HSSFCellStyle headerCellStyle = wb.createCellStyle();
			HSSFFont boldFont = wb.createFont();
	        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerCellStyle.setFont(boldFont);
	        
	        
	        HSSFRow excelRow = null;
	        HSSFCell excelCell = null;
	        
	        excelRow = mailProvisionSheet.createRow(0);
	        excelRow = mailProvisionSheet.createRow(0);
	        
	        if("PROVISIONING_MAIL_OVCC".equals(mailProvisioningFlag))
	        {   
	        	excelCell = excelRow.createCell(0);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Date of Creation"));
		        excelCell = excelRow.createCell(1);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Account name"));
		        excelCell = excelRow.createCell(2);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("ISS Order no"));
		        excelCell = excelRow.createCell(3);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Product"));
		        excelCell = excelRow.createCell(4);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Parent ID"));
		        excelCell = excelRow.createCell(5);
		        excelCell.setCellStyle(headerCellStyle);
	        	excelCell.setCellValue(new HSSFRichTextString("Child ID"));
	        	excelCell = excelRow.createCell(6);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("OVCC ID"));
		        excelCell = excelRow.createCell(7);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Bundled OVCC plan"));
		        excelCell = excelRow.createCell(8);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Chairperson Name"));
		        excelCell = excelRow.createCell(9);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Billing Person Name"));
		        excelCell = excelRow.createCell(10);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Billing Address"));
		        excelCell = excelRow.createCell(11);
		        excelCell.setCellStyle(headerCellStyle);
		        excelCell.setCellValue(new HSSFRichTextString("Status"));
	        }
	        else
	        {
	        excelCell = excelRow.createCell(0);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Date of Creation"));
	        excelCell = excelRow.createCell(1);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Account name"));
	        excelCell = excelRow.createCell(2);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("ISS Order no"));
	        excelCell = excelRow.createCell(3);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Product"));
	        excelCell = excelRow.createCell(4);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Parent ID"));
	        excelCell = excelRow.createCell(5);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Child ID"));
	        excelCell = excelRow.createCell(6);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("VCS ID"));
	        excelCell = excelRow.createCell(7);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Bundled VCS plan"));
	        excelCell = excelRow.createCell(8);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Chairperson Name"));
	        excelCell = excelRow.createCell(9);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Billing Person Name"));
	        excelCell = excelRow.createCell(10);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Billing Address"));
	        excelCell = excelRow.createCell(11);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("ID/HW address"));
	        excelCell = excelRow.createCell(12);
	        excelCell.setCellStyle(headerCellStyle);
	        excelCell.setCellValue(new HSSFRichTextString("Status"));
	        }
	        
	        
	        int iCount = 1;
	        for (int i=0;i<mailDataList.size();i++) {
	        	mailObjDto=mailDataList.get(i);
	        	excelRow = mailProvisionSheet.createRow(iCount++);
	        	if("PROVISIONING_MAIL_OVCC".equals(mailProvisioningFlag)) {
	        	excelCell = excelRow.createCell(0);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getDateOfCreation())));
	            excelCell = excelRow.createCell(1);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getAccountName())));
	            excelCell = excelRow.createCell(2);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getOrderNo())));	            
	            excelCell = excelRow.createCell(3);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getProductName())));
	            excelCell = excelRow.createCell(4);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getParentId())));
	            excelCell = excelRow.createCell(5);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChildId())));
	            excelCell = excelRow.createCell(6);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getMultipleId())));
		            excelCell = excelRow.createCell(7);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getPackageName())));
		            excelCell = excelRow.createCell(8);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChairPersonName())));
		            excelCell = excelRow.createCell(9);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getBcpName())));
		            excelCell = excelRow.createCell(10);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getBillingAddress())));
		            excelCell = excelRow.createCell(11);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getStatus())));
	        	}
	        	else {
	        		excelCell = excelRow.createCell(0);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getDateOfCreation())));
		            excelCell = excelRow.createCell(1);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getAccountName())));
		            excelCell = excelRow.createCell(2);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getOrderNo())));	            
		            excelCell = excelRow.createCell(3);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getProductName())));
		            excelCell = excelRow.createCell(4);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getParentId())));
		            excelCell = excelRow.createCell(5);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChildId())));
		            excelCell = excelRow.createCell(6);
		            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getMultipleId())));
	            excelCell = excelRow.createCell(7);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getPackageName())));
	            excelCell = excelRow.createCell(8);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getChairPersonName())));
	            excelCell = excelRow.createCell(9);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getBcpName())));
	            excelCell = excelRow.createCell(10);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getBillingAddress())));
	            excelCell = excelRow.createCell(11);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getHwAddress())));
	            excelCell = excelRow.createCell(12);
	            excelCell.setCellValue( new HSSFRichTextString(""+Utility.fnCheckNull(mailObjDto.getStatus())));
	        	}
	    	}
		}
		catch (Exception ex)
		{
			Utility.LOG(true,true,"Error while fetching accounts for provisiong : "+new Date());
		}
		return wb;
	}
	
	//lawkush End VCS code End
}
