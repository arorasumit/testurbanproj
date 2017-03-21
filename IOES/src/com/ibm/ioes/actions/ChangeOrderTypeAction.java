package com.ibm.ioes.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.beans.ChangeOrderTypeBean;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.ChangeOrderTypeDto;
import com.ibm.ioes.model.ChangeOrderTypeModel;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class ChangeOrderTypeAction extends DispatchAction
{

	/**
	 * Method called when Main Attributes master is opened.
	 * Retrives the list of all main attributes from DB
	 * @param mapping 
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getChangeOrderType(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		
		ChangeOrderTypeBean formBean=(ChangeOrderTypeBean)form;
		ActionMessages messages = new ActionMessages();
		ArrayList<ChangeOrderTypeDto> changeTypeList = new ArrayList<ChangeOrderTypeDto>();
		ChangeOrderTypeModel objModel =  new ChangeOrderTypeModel();
		ChangeOrderTypeDto objDto =new ChangeOrderTypeDto();
		//ArrayList<String> dataTypes
		try
		{
			changeTypeList=objModel.viewChangeTypeList();
			request.setAttribute("changeTypeList", changeTypeList);
			if(changeTypeList.size()>0){
				objDto = (ChangeOrderTypeDto)changeTypeList.get(0);
				formBean.setHiddenFlag("1");
			forward = mapping.findForward("DisplayChangeOrderType");
			//formBean.setAttList(attList);
			
			//formBean.setDataTypes(getDataTypes());
		}
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * Method to take all the updated attributes from screen 
	 * and send to Dao for updation in table. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */ 
	 public ActionForward updateChangeOrderType(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	 {
		ActionForward forward = new ActionForward(); // return value
		ChangeOrderTypeBean formBean=(ChangeOrderTypeBean)form;
		ChangeOrderTypeModel objModel =  new ChangeOrderTypeModel();
		ChangeOrderTypeDto objDto =new ChangeOrderTypeDto();
		ArrayList<ChangeOrderTypeDto> typeList = new ArrayList<ChangeOrderTypeDto>();
		ActionMessages messages = new ActionMessages();
		ArrayList errors = new ArrayList();
		try {

			String typeId[] = formBean.getChangeTypeIdList().split(",");
			String statusList[] = formBean.getStatusList().split(",");
			
		
			for (int count = 1; count < typeId.length; count++) {
				objDto = new ChangeOrderTypeDto();
				objDto.setChangeTypeId(typeId[count]);
				objDto.setStatus(statusList[count]);
				
				typeList.add(objDto);
			}
			String status = objModel.updateChangeOrderTypeList(typeList);
			if (status != null && !status.trim().equalsIgnoreCase("")
					&& status.equalsIgnoreCase("success")) {
				messages.add("", new ActionMessage("recordUpdateSuccess"));

				//
				saveMessages(request, messages);
			} else {
				messages.add("", new ActionMessage("recordFailureSuccess"));
				saveMessages(request, messages);
			}
			formBean.setHiddenFlag("1");
			forward = getChangeOrderType(mapping, form, request, response);

		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return forward;

	}
	 
	 public ActionForward loadAddNew(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	 {
		ActionForward forward = new ActionForward(); // return value
		ChangeOrderTypeBean formBean=(ChangeOrderTypeBean)form;
		ChangeOrderTypeModel objModel =  new ChangeOrderTypeModel();
		ChangeOrderTypeDto objDto =new ChangeOrderTypeDto();
		ArrayList<ChangeOrderTypeDto> typeList = new ArrayList<ChangeOrderTypeDto>();
		ActionMessages messages = new ActionMessages();
		ArrayList errors = new ArrayList();
		try {

			
			request.setAttribute("changeTypeList", typeList);

			formBean.setHiddenFlag("2");
			forward = mapping.findForward("DisplayChangeOrderType");

		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return forward;

	}
	 public ActionForward addNewChangeOrder(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	 {
		ActionForward forward = new ActionForward(); // return value
		ChangeOrderTypeBean formBean=(ChangeOrderTypeBean)form;
		ChangeOrderTypeModel objModel =  new ChangeOrderTypeModel();
		ChangeOrderTypeDto objDto =new ChangeOrderTypeDto();
		ArrayList<ChangeOrderTypeDto> typeList = new ArrayList<ChangeOrderTypeDto>();
		ActionMessages messages = new ActionMessages();
		
		try {
			ArrayList errors= new ArrayList();
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Change Type", formBean.getEditChangeTypeName(), 100),
					Utility.generateCSV(Utility.CASE_MANDATORY,Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				formBean.setHiddenFlag("2");
				forward = mapping.findForward("DisplayChangeOrderType");
			
			}
			else{
				objDto.setChangeTypeName(formBean.getEditChangeTypeName());
			
				objDto.setStatus(formBean.getEditStatus());
			
				String status = objModel.addChangeOrderType(objDto);
				if (status != null && !status.trim().equalsIgnoreCase("")
						&& status.equalsIgnoreCase("success")) {
					messages.add("", new ActionMessage("recordAddedSuccess"));
	
					//
					saveMessages(request, messages);
				} else {
					messages.add("", new ActionMessage("recordFailureSuccess"));
					saveMessages(request, messages);
				}
				formBean.setHiddenFlag("1");
				//forward = getChangeOrderType(mapping, form, request, response);
				forward = mapping.findForward("DisplayChangeOrderTypeAfterAdd");
				
			}

		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return forward;

	} 
}
