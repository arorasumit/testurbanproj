package com.ibm.ioes.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.ibm.ioes.exception.IOESException;

public class IB2BMailDaoImpl {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(IB2BMailDaoImpl.class);
	}
	
	public  IB2BMailDto fetchEmailTemplate(Connection conn,IB2BMailDto mailDto) throws IOESException{
		logger.info("ConferenceMailDaoImpl's fetchEmailTemplate mehtod");
		StringBuffer mailTemplate=new StringBuffer();
		StringBuffer mailTemplateSubject=new StringBuffer();
		StringBuffer mailTemplateHeader=new StringBuffer();
		StringBuffer mailTemplateFooter=new StringBuffer();
		StringBuffer smsBufferTemplate=new StringBuffer();
		IB2BMailDto values=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			String mailTemplateType=mailDto.getMailTemplateType();
			String sql="select COALESCE((SELECT TEMPLATE_VALUE AS SMSTEMPLATE FROM IOE.TEMPLATE_MSTR WHERE TEMPLATE_KEY=TMPLATEMASTER.TEMPLATE_KEY || '_SMS' ),'-') AS SMS_TEMPLATE , TEMPLATE_VALUE,SUBJECT,HEADER,FOOTER from IOE.TEMPLATE_MSTR AS TMPLATEMASTER WHERE TEMPLATE_KEY=? ORDER BY APPEND_ORDER ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mailTemplateType);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				mailTemplate.append(rs.getString("TEMPLATE_VALUE"));
				String sub=rs.getString("SUBJECT");
				if(sub!=null && !"".equals(sub.trim()))
				{
					mailTemplateSubject.append(rs.getString("SUBJECT"));	
				}
				
				String header=rs.getString("HEADER");
				if(header!=null && !"".equals(header.trim()))
				{
					mailTemplateHeader.append(rs.getString("HEADER"));	
				}
				
				String footer=rs.getString("FOOTER");
				if(footer!=null && !"".equals(footer.trim()))
				{
					mailTemplateFooter.append(rs.getString("FOOTER"));	
				}
				String smsTemplate = rs.getString("SMS_TEMPLATE");
				if(smsTemplate!=null && !"".equals(smsTemplate.trim()))
				{
					smsBufferTemplate.append(rs.getString("SMS_TEMPLATE"));
				}
			}
			
			if(mailTemplate.toString().trim().equals(""))
			{
				return null;
			}
			
			values=new IB2BMailDto();
			values.setMailTemplateBody(mailTemplate.toString());
			values.setMailTemplateSubject(mailTemplateSubject.toString());
			values.setMailTemplateHeader(mailTemplateHeader.toString());
			values.setMailTemplateFooter(mailTemplateFooter.toString());
			values.setSmsText(smsBufferTemplate.toString());
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String str=ex.getMessage()
						+ " Exception occured in fetchEmailTemplate method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex);
			throw new IOESException(str) ;
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closePreparedStatement(pstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return values;
	}

	

}
