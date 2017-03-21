package com.ibm.ioes.npd.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.upload.FormFile;
import org.hibernate.Session;

import com.ibm.ioes.npd.beans.ReferenceDocBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.model.ReferenceDocModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;
////CHANGES FOR SYSTEM TESTING DEFECTS ROHIT VERMA	8-MARCH-10
public class ManageReferenceDocAction extends LookupDispatchAction {

	private static final Logger log;

	static {
		log = Logger.getLogger(ManageReferenceDocAction.class);
	}

	public ManageReferenceDocAction() {

	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();

		map.put("link.viewReferenceDoc", "viewReferenceDoc");
		map.put("submit.downloadFile", "downloadFile");
		map.put("submit.saveReferenceDoc", "saveReferenceDoc");
		map.put("link.viewReferenceDocHistory", "viewReferenceDocHistory");
		
		return map;
	}

	// To initialize the page of adding Reference Docs .
	public ActionForward viewReferenceDoc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ReferenceDocBean referenceDocBean = (ReferenceDocBean) form;
		ReferenceDocModel referenceDocModel = new ReferenceDocModel();
		ActionMessages messages = new ActionMessages();
		boolean errorsFound=false;
		try 
		{
			if((referenceDocBean.getRfDocSearch()!="") && (referenceDocBean.getRfDocSearch()!=null))
			{
				//Server Side Security Start
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(referenceDocBean.getRfDocSearch(),"Document Name",30),
							""+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
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
					//forward = mapping.findForward(AppConstants.FAILURE);
					referenceDocBean.setRfDocSearch(null);
					return mapping.findForward(AppConstants.FAILURE);
				}
				else
				{
					referenceDocBean = referenceDocModel.viewReferenceDoc(referenceDocBean);
				}
			}
			else
			{
				referenceDocBean = referenceDocModel.viewReferenceDoc(referenceDocBean);
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("initReferenceDoc");
	}

	
//	 To initialize the page of Refernce doc History.
	public ActionForward viewReferenceDocHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ReferenceDocBean referenceDocBean = (ReferenceDocBean) form;
		
		
		

		
		ReferenceDocModel referenceDocModel = new ReferenceDocModel();
		ActionMessages messages = new ActionMessages();
		try {
			
//			validation start
			boolean errorsFound=false;
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(referenceDocBean.getRfDocSearch(),"Document Name",30),
						""+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			if(!errorsFound)
			{
				Object arr[]=new Object[]{""+ValidationBean.VN_DATE_COMPARISION,
						referenceDocBean.getFromDate(),"From Date",
						referenceDocBean.getToDate(),"To Date",ValidationBean.LESS_EQUAL,
						new SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
						
				
				ArrayList errors=Utility.validateValue(new ValidationBean(arr),
						""+Utility.CASE_DATECOMPARISION_NONMANDATORY_v2).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			
			//Server Side Security End
			
			if(errorsFound)//During Server Side Validation
			{
				
				//forward = mapping.findForward(AppConstants.FAILURE);
				//referenceDocBean.setRfDocSearch(null);
				return mapping.findForward("initReferenceDocHistory");
			}
			
			
			referenceDocBean = referenceDocModel
					.viewReferenceDocHistory(referenceDocBean);
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("initReferenceDocHistory");
	}

	
	// To download the already uploaded Reference Doc.
	
	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward();
		TmReferencedocs referencedocs = new TmReferencedocs();
		Session hibernateSession = null;
		try {
			CommonBaseDao commonBaseDao = BaseDaoFactory
					.getInstance(DaoConstants.BASE_DAO_CLASS);
			
			hibernateSession = CommonBaseDao.beginTrans();
			CommonBaseModel commonBaseModel = new CommonBaseModel();
			byte[] File = null;
			if (request.getParameter("refDocId") != null) {
			    String refDocId = Utility.decryptString(request.getParameter("refDocId"));
				referencedocs = (TmReferencedocs) commonBaseDao.findById(
						new Long(refDocId).longValue(),
						HibernateBeansConstants.HBM_REFERENCE_DOC,
						hibernateSession);
				

				File = commonBaseModel.blobToByteArray(referencedocs.getRefdoc());
				String ContentType = commonBaseModel.setContentTypeForFile(referencedocs.getActualRefDocName());//CHANGES FOR SYSTEM TESTING DEFECTS
				response.setContentType(ContentType);
				response.setHeader("Content-Disposition","attachment;filename=" + referencedocs.getActualRefDocName());//CHANGES FOR SYSTEM TESTING DEFECTS

				ServletOutputStream outs = response.getOutputStream();
				outs.write(File);
				outs.flush();
				outs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try{CommonBaseDao.closeTrans(hibernateSession);}
			catch(Exception e){e.printStackTrace();}
		}
		forward = mapping.findForward("");
		return forward;
	}

	// To save the uploaded reference doc in database as a Blob.
	
	public ActionForward saveReferenceDoc(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		ReferenceDocBean referenceDocBean = (ReferenceDocBean) form;
		ReferenceDocModel referenceDocModel = new ReferenceDocModel();
		ActionMessages messages = new ActionMessages();
		boolean insert = false;
		TmEmployee tmEmployee =	(TmEmployee)request.getSession().getAttribute(AppConstants.LOGINBEAN);
		try 
		{
			String DocName=null;
			boolean errorsFound=false;
			DocName=referenceDocBean.getRefDocName();
			//Server Side Security Start
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(DocName,"Document Name",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_MAXLENGTH+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End	
			
			FormFile Docattachment=null;
			Docattachment=referenceDocBean.getAttachment();
			
			if(errorsFound)//During Server Side Validation
			{
				saveMessages(request, messages);
				//forward = mapping.findForward(AppConstants.FAILURE);
				referenceDocBean.setAttachment(null);
				referenceDocBean.setRefDocName(null);
				return mapping.findForward(AppConstants.FAILURE);
			}
			else
			{
				boolean validation_error=false; 
				FormFile file=Docattachment;
				validation_error=Utility.validateAttachment(file,messages);
				if(validation_error)
				{
					saveMessages(request, messages);
				}
				else
				{
					insert = referenceDocModel.saveReferenceDoc(referenceDocBean,tmEmployee);
				}
			}
		} 
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (insert) 
		{
			messages.add(AppConstants.RECORD_SAVE_SUCCESS, new ActionMessage(AppConstants.RECORD_SAVE_SUCCESS));
			referenceDocBean.setRefDocName(null);
		} 
		else 
		{
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(AppConstants.RECORD_SAVE_FAILURE));
		}
		if (!messages.isEmpty()) 
		{
			this.saveMessages(request, messages);
		}
		return mapping.findForward(AppConstants.SUCCESS);
	}
}
