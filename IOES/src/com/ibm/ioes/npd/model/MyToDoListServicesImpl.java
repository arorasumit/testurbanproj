package com.ibm.ioes.npd.model;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.PlrUploadingDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.dao.*;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.CommonUtilities;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.SendMailUtility;
import com.ibm.ioes.npd.utilities.TransactionDto;
import com.ibm.ioes.npd.utilities.TransactionServiceImpl;
import com.ibm.ioes.npd.utilities.Utility;
public class MyToDoListServicesImpl 
{
	private static final Logger logger;
	static {
		logger = Logger.getLogger(MyToDoListServicesImpl.class);
	}
	public ArrayList<MyToDoListDto> myToDoList(MyToDoListDto searchDto) throws NpdException 
	{
		ArrayList<MyToDoListDto> list = new ArrayList<MyToDoListDto>();
		
		MyToDOListDaoImpl objDao = new MyToDOListDaoImpl();
		try
		{
			list = objDao.myToDoList(searchDto);  // For MyToDoList Summary			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return list;
	}
	
	public ArrayList<MyToDoListDto> myToDoListNpdSpocMail(MyToDoListDto searchDto) throws NpdException 
	{
		ArrayList<MyToDoListDto> list = new ArrayList<MyToDoListDto>();
		
		MyToDOListDaoImpl objDao = new MyToDOListDaoImpl();
		try
		{
			list = objDao.myToDoListNpdSpocMail(searchDto);  // For MyToDoList Summary			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return list;
	}
	public void updateTask(MyToDoListDto objDto) throws NpdException
	{
		MyToDOListDaoImpl objDao = new MyToDOListDaoImpl();
		try
		{			
			objDao.updateTask(objDto);
		}
		catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured in outStandingSummaryList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
	}
	//
	public int rejectTask(MyToDoListDto objDto) throws NpdException
	{
		MyToDOListDaoImpl objDao = new MyToDOListDaoImpl();
		int status=-1;
		try
		{			
			status=objDao.rejectTask(objDto);
		}
		catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured in outStandingSummaryList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return status;
	}	
	public void updateRFI(MyToDoListDto objDto) throws NpdException
	{
		MyToDOListDaoImpl objDao = new MyToDOListDaoImpl();
		try
		{			
			objDao.updateRFI(objDto);
		}
		catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured in outStandingSummaryList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
	}
	
	public MyToDoListDto alreadyMapStakeHolder(MyToDoListDto searchDto) throws NpdException 
	{
		MyToDOListDaoImpl  objDao = new MyToDOListDaoImpl();
		MyToDoListDto objDto = new MyToDoListDto();
		try
		{
			objDto = objDao.alreadyMappedStakeHolder(searchDto);  // For MyToDoList Summary			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return objDto;
	}
	public byte[] DownloadFile(MyToDoListDto objdto)
	{
		byte[] fileBytes = null;
		MyToDOListDaoImpl objDao = new MyToDOListDaoImpl();
		try
		{
			fileBytes =  objDao.DownloadFile(objdto);
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		return fileBytes;
	}

	public ArrayList<TmRoles> getRoleMappedWithEmployee(String empid) throws NpdException
	{
		MyToDOListDaoImpl objDao = new MyToDOListDaoImpl();
		ArrayList<TmRoles> employeeList=new  ArrayList<TmRoles>();
		try
		{			
			employeeList = objDao.getRoleMappedWithEmployee(empid);
		}
		catch (Exception ex) {
			logger.error(ex.getMessage()
			+ " Exception occured in outStandingSummaryList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return employeeList;
	}

	public int getMyToListCount(String userId, String roleId)throws NpdException {
		MyToDOListDaoImpl  myToDOListDaoImpl = new MyToDOListDaoImpl();
		int taskPending_mytodoList = 0;
		try
		{
			taskPending_mytodoList = myToDOListDaoImpl.getMyToListCount(userId, roleId);  // For MyToDoList Summary			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage()
			+ " Exception occured in myToDoList method of "
			+ this.getClass().getSimpleName());
			throw new NpdException();
		}
		return taskPending_mytodoList;
	}
	public int sendEmailForReject(MyToDoListDto objDto) throws NpdException{
		int status=-1;
		Connection conn= null;
		Session hibernateSession=null;
		try
		{	
			conn=NpdConnection.getConnectionObject();
			conn.setAutoCommit(false);
			
			CommonBaseDao commonBaseDao = (CommonBaseDao) BaseDaoFactory
			.getInstance(DaoConstants.BASE_DAO_CLASS);
			hibernateSession = CommonBaseDao.beginTrans();
			
			SendMailUtility oSendMail = new SendMailUtility();
			ArrayList toList = new ArrayList();
			ArrayList ccList = new ArrayList();
			
			AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
			
			//get EmailId of employees
			ArrayList<TmEmployee> employeeList=new ArrayList<TmEmployee>();
			String projectWorkflowId=String.valueOf(objDto.getProjectId());
			employeeList=daoObj.getEmployeesOfProject(conn,projectWorkflowId);
			for (TmEmployee employee : employeeList) {
				toList.add(employee.getEmail());
			}
			
			
			
			
			
			StringBuffer eBody=new StringBuffer();
			
			String eSubject=null;
			//if("PLAN_ALTERED".equals(mailOption))
			//{
				eSubject="Product Plan's Task Rejected ,Product Closed";
			//}
			//else if("PLAN_NEW".equals(mailOption))
			//{
				//eSubject="New Product Created.";	
			//}
			
			
			
			eBody.append("<HTML><BODY>");
			eBody.append("<TABLE>");
			
			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD>");
							eBody.append(Messages.getMessageValue("Mail_Header")+"<BR>");
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
		
			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD bgcolor=\"#FF9255\">");
							//if("PLAN_ALTERED".equals(mailOption))
							//{
								eBody.append("Following Product has been Closed As its Plan's Task is Rejected . Details Are as Follows :");
							//}
							//else if("PLAN_NEW".equals(mailOption))
							//{
							//	eBody.append("A New Product has been Created . Details Are as Follows :");	
							//}
							
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
			//fetch project details and set
			StringBuffer productDetails=new StringBuffer();
			
			
			eBody.append("<TR><TD>");
			
			
			eBody.append("</TD></TR>");
			
			AttachEditProjectPlanModel aPPModel=new AttachEditProjectPlanModel();
			eBody.append(aPPModel.getProjectDetailString(new Long(objDto.getMainprojectid()),hibernateSession));
			
			eBody.append("</TD></TR>");
			//fetch plan details and set
			StringBuffer planDetails=new StringBuffer();
			//StringBuffer taskRow=new StringBuffer();
			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD>");
							eBody.append("Following Task is Rejected :");
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");			
			eBody.append("<TR><TD>");
			
			//eBody.append(aPPModel.getProjectPlanMailString(formBean.getProjectWorkflowId(),conn));
			eBody.append(getRejectedTaskString(objDto,conn));
			
			
		
			eBody.append("</TD></TR>");
			StringBuffer toMessage=new StringBuffer();
			StringBuffer fromMessage=new StringBuffer();
			
			eBody.append("<TR><TD>");
				eBody.append("<TABLE>");
					eBody.append("<TR>");
						eBody.append("<TD>");
							eBody.append("<BR>"+Messages.getMessageValue("Mail_Footer")+"<BR>");
						eBody.append("</TD>");
					eBody.append("</TR>");
				eBody.append("</TABLE>");
			eBody.append("</TD></TR>");
			
			eBody.append("</TABLE>");
			eBody.append("</BODY></HTML>");
			//eBody=toMessage.append(productDetails).append(planDetails).append(fromMessage).toString();
			 
			oSendMail.setOToList(toList);
			oSendMail.setOCcList(ccList);
			oSendMail.setStrSubject(eSubject);
			oSendMail.setStrMessage(eBody.toString());
			oSendMail.sendMessageWithAttachment(null);
			
		
			conn.commit();
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in sendEmailForReject method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in sendEmailForReject method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in sendEmailForReject method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in sendEmailForReject method of "
					+ this.getClass().getSimpleName()+"  inserting mapping ") ;
		}
		finally{
			try {
				//hibernateSession.close();
				NpdConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
				AppConstants.NPDLOGGER.error(e.getMessage()
						+ " Exception occured in sendEmailForReject method of "
						+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
				throw new NpdException("Exception occured in sendEmailForReject method of "
						+ this.getClass().getSimpleName()+"  inserting mapping ") ;
			}finally
			{
				try{hibernateSession.close();}
				catch(Exception e)
				{
					e.printStackTrace();
					AppConstants.NPDLOGGER.error(e.getMessage()
							+ " Exception occured in sendEmailForReject method of "
							+ this.getClass().getSimpleName()+AppUtility.getStackTrace(e));
					throw new NpdException("Exception occured in sendEmailForReject method of "
							+ this.getClass().getSimpleName()) ;

				}
			}
		}
		return status;
		
	}
	
	public StringBuffer getRejectedTaskString(MyToDoListDto objDto, Connection conn) throws NpdException
	{
		StringBuffer eBody=new StringBuffer();
		
		AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
		
		ArrayList<TtrnProjecthierarchy> taskList=new ArrayList<TtrnProjecthierarchy>();
		ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
		
		taskSearchDto.setProjectWorkflowId(String.valueOf(objDto.getProjectId()));
		taskSearchDto.setSearchTaskId(objDto.getTaskIdList());
		
		taskList=daoObj.getTaskString(conn,taskSearchDto);
		
		
		
		
		
		eBody.append("<TABLE  border=\"1\"  cellpadding=\"3\" cellspacing=\"1\" align=\"center\" >");
		eBody.append("<TR bgcolor=\"#FF9255\">");
		eBody.append("<TD nowrap >Stage Name</TD>");
		eBody.append("<TD nowrap >Task Name</TD>");
		eBody.append("<TD nowrap >Task Desc.</TD>");
		eBody.append("<TD nowrap >Mandatory</TD>");
		eBody.append("<TD nowrap >Rejected By</TD>");
		eBody.append("<TD nowrap >Role Name</TD>");
		eBody.append("<TD nowrap >Is First Task</TD>");
		eBody.append("<TD nowrap >Is Last Task</TD>");
		
		eBody.append("<TD nowrap >Planned Duration</TD>");
		eBody.append("<TD nowrap >Document To Upload</TD>");
		eBody.append("<TD nowrap >Remarks</TD>");
		eBody.append("<TD nowrap >Target Start</TD>");
		eBody.append("<TD nowrap >Target End</TD>");
		eBody.append("<TD nowrap >Rejection Comments</TD>");
		/*eBody.append("<TD>Actual Start</TD>");
		eBody.append("<TD>Actual End</TD>");
		eBody.append("<td>Action Taken</td>");*/
		eBody.append("</TR>");
		String[] colors={"\"#FFEBC6\"","\"#ffcf9f\""};
		int i=0;
		
		for (TtrnProjecthierarchy task: taskList) {
			eBody.append("<TR bgcolor="+colors[(i++)%2]+">");
			
			eBody.append("<TD nowrap >"+task.getStagename()+"</TD>");
			eBody.append("<TD nowrap >"+task.getTaskname()+"</TD>");
			eBody.append("<TD nowrap >"+task.getTaskdesc()+"</TD>");
			
			if(task.getTaskTasktype()!=null)
			{
				if(1==task.getTaskTasktype())
				{
					eBody.append("<TD nowrap >Y</TD>");
				}
				else if(0==task.getTaskTasktype())
				{
					eBody.append("<TD nowrap >No</TD>");
				}
			}
			eBody.append("<TD nowrap >"+task.getAssignedtouserName()+"</TD>");
			eBody.append("<TD nowrap >"+task.getTaskstakeholderName()+"</TD>");
			if(task.getIsfirsttask()!=null)
			{
				if(1==task.getIsfirsttask())
				{
					eBody.append("<TD nowrap >Y</TD>");
				}
				else if(0==task.getIsfirsttask())
				{
					eBody.append("<TD nowrap >No</TD>");
				}
			}
			if(task.getIslasttask()!=null)
			{
				if(1==task.getIslasttask())
				{
					eBody.append("<TD nowrap >Y</TD>");
				}
				else if(0==task.getIslasttask())
				{
					eBody.append("<TD nowrap >No</TD>");
				}
			}
			
			
			eBody.append("<TD nowrap >"+task.getTaskduration()+"</TD>");
			
			if(task.getTaskIsattachment()!=null)
			{
				if(1==task.getTaskIsattachment())
				{
					eBody.append("<TD nowrap >"+task.getTaskReferencedocname()+"</TD>");
				}
				else if(0==task.getTaskIsattachment())
				{
					eBody.append("<TD nowrap >None</TD>");
				}
			}
			
			eBody.append("<TD nowrap >"+task.getTaskTaskinstructionremarks()+"</TD>");
			//eBody.append("<TD nowrap >"+task.getTask+"</TD>");
			java.util.Date date=task.getTaskstartdate();
			if(date==null)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");	
			}
			else
			{
				eBody.append("<TD nowrap >"+Utility.showDate_Report(date)+"</TD>");	
			}
			
			date=task.getTaskenddate();
			if(date==null)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");	
			}
			else
			{
				eBody.append("<TD nowrap >"+Utility.showDate_Report(date)+"</TD>");	
			}
			eBody.append("<TD nowrap >"+objDto.getTaskrejectComments()+"</TD>");
			
			
			/*date=task.getActualtaskstartdate();
			if(date==null)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");	
			}
			else
			{
				eBody.append("<TD nowrap >"+Utility.showDate_Report(date)+"</TD>");	
			}
			
			date=task.getActualtaskenddate();
			if(date==null)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");	
			}
			else
			{
				eBody.append("<TD nowrap >"+Utility.showDate_Report(date)+"</TD>");	
			}
			
			
			long task_status=task.getTaskstatus();
			if(task_status==0)
			{
				eBody.append("<TD nowrap >&nbsp;</TD>");
			}else if(task_status==1)
			{
				eBody.append("<TD nowrap >Open</TD>");
			}else if(task_status==2)
			{
				eBody.append("<TD nowrap >Approved</TD>");
			}else if(task_status==3)
			{
				eBody.append("<TD nowrap >In RFI</TD>");	
			}*/
			eBody.append("</TR>");
		}
		eBody.append("</TABLE>");
		
		return eBody;
	}

}
