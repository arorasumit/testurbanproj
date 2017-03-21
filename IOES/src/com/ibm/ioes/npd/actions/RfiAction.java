/**
 * 
 */
package com.ibm.ioes.npd.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.upload.FormFile;
import org.hibernate.Session;

import com.ibm.ioes.npd.beans.RfiBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.model.CommonBaseModel;
import com.ibm.ioes.npd.model.RfiModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Utility;

/**
 * @author Sanjay
 * 
 */
public class RfiAction extends LookupDispatchAction {

	protected Map getKeyMethodMap() {

		Map map = new HashMap();
		map.put("link.viewrfi", "viewrfi");
		map.put("link.providerfi", "providerfi");
		map.put("link.saveclarification", "saveclarification");
		map.put("submit.downloadFile", "downloadFile");
		return map;
	}

	public ActionForward viewrfi(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		RfiBean rfiBean = (RfiBean) actionForm;
		RfiModel rfiModel = new RfiModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
				AppConstants.LOGINBEAN);

		try {
			rfiModel.rfiDetails(rfiBean,tmEmployee);
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		forward = mapping.findForward("initrfi");

		return forward;
	}

	public ActionForward providerfi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		RfiBean rfiBean = (RfiBean) form;
		RfiModel rfiModel = new RfiModel();
		ActionMessages messages = new ActionMessages();
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
		try 
		{
			if (Utility.decryptString(request.getParameter("rfiId")) != null
					&& !AppConstants.INI_VALUE.equalsIgnoreCase(Utility.decryptString(request
							.getParameter("rfiId")))) {
				rfiBean.setRfiId(Integer.parseInt(Utility.decryptString(request
						.getParameter("rfiId"))));
				rfiBean =rfiModel.getRfiCommentsAndBlob(rfiBean,tmEmployee);
				forward = mapping.findForward("provideclarification");

			} else 
			{
				rfiModel.rfiDetails(rfiBean,tmEmployee);
				forward = mapping.findForward("initrfi");
			}

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}
		return forward;
	}

	public ActionForward saveclarification(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{
		ActionForward forward = new ActionForward();
		ActionMessages messages = new ActionMessages();
		RfiModel rfiModel = new RfiModel();
		RfiBean rfiBean = (RfiBean) form;
		TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
		boolean insert = false;
		boolean errorsFound=false;
		try 
		{						
			//Server Side Security Start for Task Comments
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(rfiBean.getRfiClarification(),"RFI Clarification",500),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End	
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				forward = mapping.findForward("provideclarification");
				rfiBean =rfiModel.getRfiCommentsAndBlob(rfiBean,tmEmployee);
				return forward;
			}
			else
			{
				boolean validation_error=false;
				FormFile file=rfiBean.getAttachment();
				validation_error=Utility.validateAttachment(file, messages);
				if(validation_error)
				{
					//uploadPlr(mapping, form, request, response);
					saveMessages(request, messages);
					forward = mapping.findForward("provideclarification");
					rfiBean =rfiModel.getRfiCommentsAndBlob(rfiBean,tmEmployee);
					return forward;
				}
				
				
				
				
				insert = rfiModel.saveReferenceDoc(rfiBean,tmEmployee);	
				if(insert==true)
				{
					request.setAttribute("SAVED", "SAVED");
				}
			}
			
		} catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (insert) 
		{
			rfiBean.setRfiClarification(AppConstants.INI_VALUE);
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.RECORD_SAVE_SUCCESS));
		} 
		else 
		{
			messages.add(AppConstants.RECORD_SAVE_FAILURE, new ActionMessage(
					AppConstants.RECORD_SAVE_FAILURE));
		}
		if (!messages.isEmpty()) {
			this.saveMessages(request, messages);
		}

		forward = mapping.findForward("provideclarification");

		return forward;
	}


	// To download the already uploaded Reference Doc.
	
	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward();
	
		try {
			RfiBean rfiBean = (RfiBean) form;
			RfiModel rfiModel = new RfiModel();
			TmEmployee tmEmployee = (TmEmployee) request.getSession().getAttribute(
					AppConstants.LOGINBEAN);
			CommonBaseModel commonBaseModel = new CommonBaseModel();
			byte[] File = null;
			rfiBean =rfiModel.getRfiCommentsAndBlob(rfiBean,tmEmployee);

				File = commonBaseModel.blobToByteArray(rfiBean.getRfiDocument());
				String ContentType = commonBaseModel
						.setContentTypeForFile(rfiBean.getRfiDocumentName());
				response.setContentType(ContentType);
				response.setHeader("Content-Disposition",
						"attachment;filename=" + rfiBean.getRfiDocumentName());

				ServletOutputStream outs = response.getOutputStream();
				outs.write(File);
				outs.flush();
				outs.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward = null;// mapping.findForward("provideclarification");
		return forward;
	}

}
