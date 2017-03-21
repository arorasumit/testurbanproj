package com.ibm.ioes.npd.actions;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.upload.FormFile;
import org.hibernate.Session;

import com.ibm.ioes.npd.beans.AssumptionCaptureBean;
import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingmoms;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.model.MeetingModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.SMSUtil;
import com.ibm.ioes.npd.utilities.SendMailUtility;
import com.ibm.ioes.npd.utilities.Utility;

public class ManageMeetingsAction extends LookupDispatchAction {

	private static final Logger log;
	
	static {
		log = Logger.getLogger(ManageMeetingsAction.class);
	}

	public ManageMeetingsAction() {

	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();

		map.put("link.viewScheduleMeetings", "viewScheduleMeetings");
		map.put("submit.save", "saveMeetingSchedule");
		map.put("link.viewMomHistory", "viewMomHistory");
		map.put("submit.saveMOM", "saveMeetingMOM");
		map.put("link.viewAddMom", "viewAddMom");
		map.put("submit.viewHistory", "submitViewMomHistory");
		map.put("submit.downloadFile", "downloadFile");
		map.put("initSendMeetingSMS", "initSendMeetingSMS");
		map.put("saveUpdate", "saveUpdate");
		
		

		return map;
	}

	// To initialize the page for Scheduling a Meeting for CFT & NPSC
	public ActionForward viewScheduleMeetings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MeetingBean meetingBean = (MeetingBean) form;
		MeetingModel meetingModel = new MeetingModel();
		ActionMessages messages = new ActionMessages();

		try {

			meetingBean = meetingModel.viewScheduleMeeting(meetingBean);

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("initMeetingSchedule");
	}

	// To save the page details for Meeting schedule.
	public ActionForward saveMeetingSchedule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MeetingBean meetingBean = (MeetingBean) form;
		MeetingModel meetingModel = new MeetingModel();
		ActionMessages messages = new ActionMessages();
		Object[] messageParameters = new Object[1];

		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);
		boolean errorsFound=false;
		try 
		{
			//if ((meetingBean.getAttachment() != null && meetingBean.getAttachment().getFileSize() > AppConstants.FILE_SIZE)) 
			if (meetingBean.getAttachment() != null)
			{
				FormFile Docattachment=null;
				Docattachment=meetingBean.getAttachment();
				boolean validation_error=false; 
				FormFile file=Docattachment;
				validation_error=Utility.validateAttachment(file,messages);
				if(validation_error)
				{
					saveMessages(request, messages);
				}
				 if (meetingBean.getAttachment1() != null) 
				{
					FormFile Docattachment1=null;
					Docattachment1=meetingBean.getAttachment1();
					boolean validation_error1=false; 
					FormFile file1=Docattachment1;
					validation_error1=Utility.validateAttachment(file1,messages);
					if(validation_error1)
					{
						saveMessages(request, messages);
					}
				}
				if (meetingBean.getAttachment2() != null) 
				{
					FormFile Docattachment2=null;
					Docattachment2=meetingBean.getAttachment2();
					boolean validation_error2=false; 
					FormFile file2=Docattachment2;
					validation_error2=Utility.validateAttachment(file2,messages);
					if(validation_error2)
					{
						saveMessages(request, messages);
					}
				}
				//Server Side Security Start for Meeting Type
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getMeetingType(),"Meeting Type",1000),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Product ID
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getProductId(),"Product ID",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Location ID
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getLocationId(),"Location ID",1000),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Subject
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getSubject(),"Subject",100),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Date of Meeting
				if(!errorsFound)
				{
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
					Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,meetingBean.getDate(),"Date of Meeting",
							simpleDateFormat.format(new Date()),"Current Date",ValidationBean.GREATER_EQUAL,
							new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
					ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
							""+Utility.CASE_DATECOMPARISION_MANDATORY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End for Date of Meeting
				
				//Server Side Security Start for Selected Mandatory Users
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean().loadValidationBean_String_MultipleSelect("Mandatory List", meetingBean.getSelectedMandatoryId()),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY_MULTIPLE_STRING).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Selected Optional Users
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean().loadValidationBean_String_MultipleSelect("Optional List", meetingBean.getSelectedOptionalId()),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for Start Time
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean().loadValidationBean_Time_Pattern("Start Time", meetingBean.getStartTime(),AppConstants.TIMEPICKER_FORMAT),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY+","+Utility.CASE_TIME_PATTERN).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				//Server Side Security Start for End Time
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean().loadValidationBean_Time_Pattern("End Time", meetingBean.getEndTime(),AppConstants.TIMEPICKER_FORMAT),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MANDATORY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
				//Server Side Security End
				
				if(errorsFound)//During Server Side Validation
				{
					saveMessages(request, messages);
					BeanUtils.copyProperties(new MeetingBean(), meetingBean);
					return mapping.findForward("failure");
				}
				else
				{
					meetingBean = meetingModel.saveScheduleMeeting(meetingBean,	tmEmployee);
					if (meetingBean.getMeetingId() != null && !meetingBean.getMeetingId().equalsIgnoreCase(AppConstants.INI_VALUE)) 
					{
						messageParameters[0] = meetingBean.getMeetingId();
						meetingBean.setMeetingMsg(SendMailUtility.getMessage(messageParameters, AppConstants.PROP_FILE,"MeetingGeneratedSuccess"));	
					} else 
					{
						meetingBean.setMeetingMsg(SendMailUtility.getMessage(messageParameters, AppConstants.PROP_FILE,"MeetingGeneratedFailure"));
					}
				}
			
			}

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}

		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward(AppConstants.SUCCESS);
	}

	// To initialize the page for Viewing the Add Mom Page.
	public ActionForward viewAddMom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MeetingBean meetingBean = (MeetingBean) form;
		MeetingModel meetingModel = new MeetingModel();
		ActionMessages messages = new ActionMessages();
		boolean errorsFound=false;
		try 
		{
			if((meetingBean.getDocSearch()!="") && (meetingBean.getDocSearch()!=null))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getDocSearch(),"Meeting Type",10),
							""+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			if((meetingBean.getSearchProjectId()!="") && (meetingBean.getSearchProjectId()!=null))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getSearchProjectId(),"Project ID",10),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			if((meetingBean.getSearchProjectName()!="") && (meetingBean.getSearchProjectName()!=null))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getSearchProjectName(),"Project Name",10),
							""+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			if((meetingBean.getMeetingIdCreated()!="") && (meetingBean.getMeetingIdCreated()!=null))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getMeetingIdCreated(),"Meeting ID",10),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			
			if((meetingBean.getFromDate()!="") && (meetingBean.getToDate()!="") && (meetingBean.getFromDate()!=null) && (meetingBean.getToDate()!=null))
			{
				if(!errorsFound)
				{
					Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,meetingBean.getFromDate(),"From Date",
							meetingBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
							new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
					ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
							""+Utility.CASE_DATECOMPARISION_NONMANDATORY_v2).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				meetingBean.setDocSearch(null);
				meetingBean.setSearchProjectId(null);
				meetingBean.setSearchProjectName(null);
				meetingBean.setMeetingIdCreated("1");
				meetingBean.setFromDate(null);
				meetingBean.setToDate(null);
				return mapping.findForward("initAddMom");
			}
			else
			{
				meetingBean = meetingModel.viewAddMom(meetingBean);
			}

		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("initAddMom");
	}
	// To save the page details for Meeting schedule.

	public ActionForward saveMeetingMOM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MeetingBean meetingBean = (MeetingBean) form;
		MeetingModel meetingModel = new MeetingModel();
		ActionMessages messages = new ActionMessages();
		boolean insert = false;
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
		try 
		{
			FormFile Docattachment=null;
			Docattachment=meetingBean.getAttachment();
			boolean validation_error=false; 
			FormFile file=Docattachment;
			validation_error=Utility.validateAttachment(file,messages);
			if(validation_error)
			{
				saveMessages(request, messages);
			}
			else
			{
				
				insert = meetingModel.saveMeetingMOM(meetingBean, tmEmployee);
				meetingBean.setMeetingIdCreated(null);
				meetingBean.setFromDate(null);
				meetingBean.setToDate(null);
				meetingBean.setDocSearch(null);
				meetingBean.setSearchProjectId(null);
				meetingBean.setSearchProjectName(null);
			}
			
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (insert) {
			messages.add(AppConstants.RECORD_SAVE_SUCCESS, new ActionMessage(
					AppConstants.RECORD_SAVE_SUCCESS));
		} else {
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
					AppConstants.RECORD_SAVE_FAILURE));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("addMomSuccess");
	}

	// To initialize the page for Viewing the Mom History Page.
	public ActionForward viewMomHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MeetingBean meetingBean = (MeetingBean) form;
		MeetingModel meetingModel = new MeetingModel();
		ActionMessages messages = new ActionMessages();

		try {

			meetingBean = meetingModel.viewAddMom(meetingBean);

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("initMomHistory");
	}

	// To get result as a list of MOM added to a meeting

	public ActionForward submitViewMomHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MeetingBean meetingBean = (MeetingBean) form;
		MeetingModel meetingModel = new MeetingModel();
		ActionMessages messages = new ActionMessages();

		try {
			boolean errorsFound=false;
//			Server Side Security Start for Project ID
			if((meetingBean.getSubject().equalsIgnoreCase("") || meetingBean.getSubject()==null)
					&& (meetingBean.getMeetingId().equalsIgnoreCase("") || meetingBean.getMeetingId()==null)
					&& ((meetingBean.getFromDate().equalsIgnoreCase("") || meetingBean.getFromDate()==null)
							|| (meetingBean.getToDate().equalsIgnoreCase("") || meetingBean.getToDate()==null)))
			{
				ArrayList errors=new ArrayList();
				errors.add("Please Enter Subject or Meeting ID or From and To Date to Search");
				request.setAttribute("validation_errors", errors);
				errorsFound=true;
			}
			else
			{
				if(meetingBean.getSubject()!=null && (!meetingBean.getSubject().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getSubject(),"Subject",100),
								""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				
				if(meetingBean.getMeetingId()!=null && (!meetingBean.getMeetingId().equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(meetingBean.getMeetingId(),"Meeting ID",10),
								""+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				
				if((!meetingBean.getFromDate().equalsIgnoreCase("") && meetingBean.getFromDate()!=null)
						|| (!meetingBean.getToDate().equalsIgnoreCase("") && meetingBean.getToDate()!=null))
				{
					if(!errorsFound)
					{
						Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,meetingBean.getFromDate(),"From Date",
								meetingBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
								new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
								""+Utility.CASE_DATECOMPARISION_NONMANDATORY).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
			}
			//Server Side Security End
			
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				BeanUtils.copyProperties(new MeetingBean(), meetingBean);
				viewMomHistory(mapping, form, request, response);
				return mapping.findForward("initMomHistory");
			}

			meetingBean = meetingModel.submitViewMomHistory(meetingBean);
			if (meetingBean.getMomList() == null
					|| meetingBean.getMomList().size() <= 0) {
				messages.add(AppConstants.NO_RECORD, new ActionMessage(
						AppConstants.NO_RECORD));
			}

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}

		return mapping.findForward("momHistorySuccess");
	}

	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();
		ActionForward forward = new ActionForward();
		TtrnMeetingmoms ttrnMeetingmoms = new TtrnMeetingmoms();
		TtrnMeetingschedules ttrnMeetingschedules = new TtrnMeetingschedules();
		Blob attachment = null;
		String filename = null;
		Session hibernateSession = null;
		try {
			CommonBaseDao commonBaseDao = BaseDaoFactory
					.getInstance(DaoConstants.BASE_DAO_CLASS);
			
			hibernateSession = CommonBaseDao.beginTrans();
			CommonBaseModel commonBaseModel = new CommonBaseModel();
			byte[] File = null;
			if (request.getParameter("momId") != null) {
				String momId = Utility.decryptString(request
					.getParameter("momId"));
				ttrnMeetingmoms = (TtrnMeetingmoms) commonBaseDao.findById(
						new Long(momId).longValue(),
						HibernateBeansConstants.HBM_MEETING_MOM,
						hibernateSession);

				File = commonBaseModel
						.blobToByteArray(ttrnMeetingmoms.getMom());
				String ContentType = commonBaseModel
						.setContentTypeForFile(ttrnMeetingmoms.getFilename());
				response.setContentType(ContentType);
				response.setHeader("Content-Disposition",
						"attachment;filename=" + ttrnMeetingmoms.getFilename());

				ServletOutputStream outs = response.getOutputStream();
				outs.write(File);
				outs.flush();
				outs.close();
			} else if (request.getParameter("meetingId") != null) {
			    String meetingId = Utility.decryptString(request
					.getParameter("meetingId"));
			    String attachmentId = Utility.decryptString(request
					.getParameter("attachmentId"));
			    ttrnMeetingschedules = (TtrnMeetingschedules) commonBaseDao
					.findById(new Long(meetingId).longValue(),
							HibernateBeansConstants.HBM_MEETINGSCHEDULE,
							hibernateSession);
				
				if (attachmentId != null
						&& !request.getParameter("attachmentId").toString()
								.equalsIgnoreCase(AppConstants.INI_VALUE)) {
					int attachmentno = new Integer(attachmentId).intValue();
					switch (attachmentno) {
					case 1:
						attachment = ttrnMeetingschedules.getAttachment();
						filename = ttrnMeetingschedules.getFilename();
						break;
					case 2:
						attachment = ttrnMeetingschedules.getAttachment1();
						filename = ttrnMeetingschedules.getFilename1();
						break;
					case 3:
						attachment = ttrnMeetingschedules.getAttachment2();
						filename = ttrnMeetingschedules.getFilename2();
						break;

					}
					File = commonBaseModel.blobToByteArray(attachment);
					String ContentType = commonBaseModel
							.setContentTypeForFile(filename);
					response.setContentType(ContentType);
					response.setHeader("Content-Disposition",
							"attachment;filename=" + filename);

					ServletOutputStream outs = response.getOutputStream();
					outs.write(File);
					outs.flush();
					outs.close();
				}

			}

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}finally
		{
			try{CommonBaseDao.closeTrans(hibernateSession);}
			catch(Exception e){e.printStackTrace();}
		}

		forward = null;
		return forward;
	}
	// To initialize the page for Scheduling a Meeting for CFT & NPSC
	public ActionForward initSendMeetingSMS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MeetingBean meetingBean = (MeetingBean) form;
		MeetingModel meetingModel = new MeetingModel();
		ActionMessages messages = new ActionMessages();

		try {

			meetingBean = meetingModel.viewMeetingSMS(meetingBean);

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}
		return mapping.findForward("success");
	}
	
	public ActionForward saveUpdate(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	MeetingBean meetingBean = (MeetingBean) form;
	MeetingModel meetingModel = new MeetingModel();
	ActionMessages messages = new ActionMessages();
	SMSUtil sms=new SMSUtil();
	try {

	    	String txtMsg=meetingBean.getSmsMessage();
	    	
		String[] MobNumber= new String[meetingBean.getSmsContacts().length];
		
		for(int i=0;i<meetingBean.getSmsContacts().length;i++)
		{
		    sms.sendingSMS(meetingBean.getSmsContacts()[i].toString(), txtMsg);
		}
		
		MeetingBean objBean = new MeetingBean();
		BeanUtils.copyProperties(meetingBean, objBean);
		initSendMeetingSMS(mapping,form,request,response);
		//forwardMapping = "success";
		//meetingBean = meetingModel.viewMeetingSMS(meetingBean);

	} catch (Exception e) {
		AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
				AppConstants.MESSAGE_ID));
	}
	if (!messages.isEmpty()) {
		this.saveMessages(request, messages);
	}
	return mapping.findForward("success");
}

}
