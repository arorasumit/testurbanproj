package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Session;

import com.ibm.ioes.npd.beans.NewIdeaBean;
import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.dao.AttachEditProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.NewIdeaDao;
import com.ibm.ioes.npd.hibernate.dao.RoleSelectionDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.SendMail;
import com.ibm.ioes.npd.utilities.Utility;

public class NewIdeaModel {

	public long saveIdea(NewIdeaBean formBean) throws NpdException{
		
		String methodName="saveIdea";
		Connection conn=null;
		long ideaId;
		
		try
		{
			conn = NpdConnection.getConnectionObject();
			
			NewIdeaDto dto=new NewIdeaDto();
			
			String benefit = formBean.getBenefit();
			String briefDesc = formBean.getBriefDesc();
			String function = formBean.getFunction();
			String industryVertical = formBean.getIndustryVertical();
			String location = formBean.getLocation();
			String mailId = formBean.getMailId();
			String nameGenerator = formBean.getNameGenerator();
			String phoneNo = formBean.getPhoneNo();
			//String similarProductDetails = formBean.getSimilarProductDetails();
			String usp = formBean.getUsp();
			
			String isSimilarProductExist=formBean.getIsSimilarProductExist();
			String country=formBean.getCountry();
			String organisation=formBean.getOrganisation();
			String prdDescription=formBean.getPrdDescription();
			String marketSize=formBean.getMarketSize();
			
			String hrms_employeenumber=formBean.getHrms_employeenumber();
			
			dto.setBenefit(benefit);
			dto.setBriefDesc(briefDesc);
			dto.setFunction(function);
			dto.setIndustryVertical(industryVertical);
			dto.setLocation(location);
			dto.setMailId(mailId);
			dto.setNameGenerator(nameGenerator);
			dto.setPhoneNo(phoneNo);
			//dto.setSimilarProductDetails(similarProductDetails);
			dto.setUsp(usp);
			
			if("Y".equals(isSimilarProductExist))
			{
				dto.setIsSimilarProductExist("Y");
				dto.setCountry(country);
				dto.setOrganisation(organisation);
				dto.setPrdDescription(prdDescription);
				dto.setMarketSize(marketSize);
			}
			else
			{
				dto.setIsSimilarProductExist("N");
			}
			
			dto.setHrms_employeenumber(hrms_employeenumber);
			
			NewIdeaDao dao=new NewIdeaDao();
			ideaId=dao.saveIdea(conn,dto);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in "+methodName+" method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in "+methodName+" method of "
						+ this.getClass().getSimpleName()) ;
			}
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in "+methodName+" method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in "+methodName+" method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in "+methodName+" method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in "+methodName+" method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return ideaId;
	}

	public static int USER_NOT_FOUND=2;
	public static int USER_FOUND=1;
	
	public long loadHRMSDetails(String ssfId, NewIdeaBean formBean) throws NpdException{
		
		String methodName="loadHRMSDetails";
		Connection conn=null;
		long status=0;
		
		try
		{
			conn = NpdConnection.getConnectionObject();
			
			NewIdeaDto dto=null;
			
			NewIdeaDao dao=new NewIdeaDao();
			dto=dao.loadHRMSDetails(conn,ssfId);
			
			if(dto==null)
			{
				status=USER_NOT_FOUND;
			}
			else
			{
				status=USER_FOUND;
				formBean.setNameGenerator(dto.getNameGenerator());
				formBean.setMailId(dto.getMailId());
				formBean.setPhoneNo(dto.getPhoneNo());
				formBean.setFunction(dto.getFunction());
				formBean.setLocation(dto.getLocation());
				formBean.setHrms_employeenumber(dto.getHrms_employeenumber());
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in "+methodName+" method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in "+methodName+" method of "
						+ this.getClass().getSimpleName()) ;
			}
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in "+methodName+" method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in "+methodName+" method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in "+methodName+" method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in "+methodName+" method of "
						+ this.getClass().getSimpleName()) ;
			}
		}
		return status;
	}

	public void getIdeaList(NewIdeaBean formBean) throws NpdException {
		
		Connection connection=null;
		
		try
		{
			connection=NpdConnection.getConnectionObject();
			
			NewIdeaDto searchDto=new NewIdeaDto();
			
			searchDto.setFromDate(formBean.getFromDate());
			searchDto.setToDate(formBean.getToDate());
			
			searchDto.setSearchEmailId(formBean.getSearchEmailId());
			searchDto.setSearchName(formBean.getSearchName());
			searchDto.setSearchOracleId(formBean.getSearchOracleId());
			
			PagingSorting pagingSorting=formBean.getPagingSorting();
			if("exportExcel".equals(formBean.getExportExcel()))
			{
				pagingSorting.setPagingSorting(false,true);
			}
			else
			{
				pagingSorting.setPagingSorting(true,true);
			}
			
			pagingSorting.setDefaultifNotPresent("submittedDate", PagingSorting.decrement, 1);
			searchDto.setPagingSorting(pagingSorting);
		
			NewIdeaDao dao=new NewIdeaDao();
			ArrayList<NewIdeaDto> reportList=dao.getIdeaList(connection,searchDto);
			formBean.setReportList(reportList);
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getIdeaList method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getIdeaList method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally{
			try {
				NpdConnection.freeConnection(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in getIdeaList method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in getIdeaList method of "
						+ this.getClass().getSimpleName());
			}
		
		}
	}

	public int sendMailForIdea(NewIdeaBean formBean) throws NpdException{
		// TODO Auto-generated method stub
		int status=0;

		Connection conn=null;
		
		try
		{
			SendMail mail=new SendMail();
			String []to=null;
			String []cc=null;
			String []bcc=null;
			String subject=null;
			String from=null;
			String message=null;
			
			if(formBean.getMailId()==null || "".equals(formBean.getMailId()))
			{
				return -2;
			}
			
			to=new String[]{formBean.getMailId()};
			
			//fetch admin email id for cc
			conn=NpdConnection.getConnectionObject();
			cc=new NewIdeaDao().getAllAdminEmailIds(conn);
			
			subject="Confirmation Mail for Idea Submission.";
			
			StringBuffer sb=new StringBuffer();
			sb.append("<HTML>");
			sb.append("<HEAD>");
			sb.append("</HEAD>");
			sb.append("<BODY>");
			//sb.append("<TABLE>");
			sb.append(Messages.getMessageValue("Mail_Header"));
			sb.append("<br><br>");
			
			sb.append("This is a Confirmation Mail for Idea Submitted by you on NPD Portal.<BR>The Confirmation Id is "+formBean.getIdeaId());
			sb.append("<BR><BR>Submitted Idea Details are :");
			sb.append("<TABLE width=\"100%\" border=\"1\"  cellpadding=\"3\" cellspacing=\"1\" align=\"center\" >");
				sb.append("<TR>");
					sb.append("<TD nowrap width=\"23%\"  bgcolor=\"#ffcf9f\">");
						sb.append("Brief description of Idea :");
					sb.append("</TD>");
					sb.append("<TD bgcolor=\"#FFEBC6\">");
						sb.append(formBean.getBriefDesc());
					sb.append("</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
					sb.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
						sb.append("Benefit to Customer :");
					sb.append("</TD>");
					sb.append("<TD bgcolor=\"#FFEBC6\">");
						sb.append(formBean.getBenefit());
					sb.append("</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
					sb.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
						sb.append("Similar Product existing anywhere:");
					sb.append("</TD>");
					sb.append("<TD bgcolor=\"#FFEBC6\">");
						if("Y".equals(formBean.getIsSimilarProductExist()))
						{	
							sb.append("Yes");
						}
						else
						{
							sb.append("No");
						}
					sb.append("</TD>");
				sb.append("</TR>");
				if("Y".equals(formBean.getIsSimilarProductExist()))
				{
					sb.append("<TR>");
						sb.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
							sb.append("Organisation :");
						sb.append("</TD>");
						sb.append("<TD bgcolor=\"#FFEBC6\">");
							sb.append(formBean.getOrganisation());
						sb.append("</TD>");
					sb.append("</TR>");
					sb.append("<TR>");
						sb.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
							sb.append("Country :");
						sb.append("</TD>");
						sb.append("<TD bgcolor=\"#FFEBC6\">");
							sb.append(formBean.getCountry());
						sb.append("</TD>");
					sb.append("</TR>");
					sb.append("<TR>");
						sb.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
							sb.append("Product Description :");
						sb.append("</TD>");
						sb.append("<TD bgcolor=\"#FFEBC6\">");
							sb.append(formBean.getPrdDescription());
						sb.append("</TD>");
					sb.append("</TR>");
					sb.append("<TR>");
						sb.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
							sb.append("Market Size :");
						sb.append("</TD>");
						sb.append("<TD bgcolor=\"#FFEBC6\">");
							sb.append(formBean.getMarketSize());
						sb.append("</TD>");
					sb.append("</TR>");
				}
				sb.append("<TR>");
					sb.append("<TD   bgcolor=\"#ffcf9f\">");
						sb.append("Industry verticals best suited for :");
					sb.append("</TD>");
					sb.append("<TD bgcolor=\"#FFEBC6\">");
						sb.append(formBean.getIndustryVertical());
					sb.append("</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
					sb.append("<TD nowrap  bgcolor=\"#ffcf9f\">");
						sb.append("USP of idea :");
					sb.append("</TD>");
					sb.append("<TD bgcolor=\"#FFEBC6\">");
						sb.append(formBean.getUsp());
					sb.append("</TD>");
				sb.append("</TR>");
			sb.append("</TABLE>");
			
			sb.append("<br><br>");
			sb.append(Messages.getMessageValue("Mail_Footer"));
			//sb.append("<TABLE>");
			sb.append("</BODY>");
			sb.append("</HTML>");
			
			message=sb.toString();
				//System.err.println("To:"+to[0]+":"+message);
			mail.postMail(to, cc, bcc, subject, message, from, null);
			
			status=1;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			status=-1;
			
		}
		finally
		{
			try {
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return status;
	}

}
