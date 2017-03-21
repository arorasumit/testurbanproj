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

import com.ibm.ioes.beans.MasterAttributesBean;
import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.MastersAttributesDto;
import com.ibm.ioes.model.MasterAttributesModel;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class ProductConfiguratorAction extends DispatchAction{

	//getProductConfigurator -- method will load list of services
	//getServiceAttributeList--
	public ActionForward getProductConfigurator(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		MasterAttributesBean objForm=(MasterAttributesBean)form;
		MasterAttributesModel objModel=new MasterAttributesModel();
		ArrayList<MastersAttributesDto> attributeList= new ArrayList<MastersAttributesDto>();
		try
		{

			objModel.loadServices(objForm);
			request.setAttribute("attributeList", attributeList);
			forward = mapping.findForward("DisplayProductConfigurator");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return forward;
	}
	public ActionForward getServiceAttributeList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		MasterAttributesBean objForm=(MasterAttributesBean)form;
		MastersAttributesDto objDto =new MastersAttributesDto();
		MasterAttributesModel objModel=new MasterAttributesModel();
		ArrayList<MastersAttributesDto> attributeList= new ArrayList<MastersAttributesDto>();
		try
		{

			objModel.loadServices(objForm);
			objDto.setServiceTypeId(objForm.getHiddenServiceTypeId());
			attributeList=objModel.getProductConfigAttList(objDto);
			request.setAttribute("attributeList", attributeList);
			forward = mapping.findForward("DisplayProductConfigurator");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return forward;
	}
	public ActionForward updateProductConfigAttributes(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
			ActionForward forward = new ActionForward(); 
			MasterAttributesBean objForm = (MasterAttributesBean) form;
			MastersAttributesDto objDto;
			MasterAttributesModel objModel = new MasterAttributesModel();
			ArrayList<MastersAttributesDto> attributeList = new ArrayList<MastersAttributesDto>();
			ActionMessages messages = new ActionMessages();
			ArrayList errors = new ArrayList();
			boolean errorsFound = false;
			try {

				String attid[] = objForm.getAttIdList().split(",");
				String attOEParentId[] = objForm.getAttOEParentIdList().split(",");
				String attM6ParentId[] = objForm.getAttM6ParentIdList().split(",");
				String attM6ChildId[] = objForm.getAttM6ChildIdList().split(",");
				String attSend2FX[] = objForm.getAttSend2FXList().split(",");
				String attSend2M6[] = objForm.getAttSend2M6List().split(",");
				String attServiceSummary[] = objForm.getAttServiceSummaryList().split(",");
				String attBillingInfo[] = objForm.getAttBillInfoList().split(",");
				String attChargeIfo[] = objForm.getAttChargeInfoList().split(",");
				String attHarwareInfo[] = objForm.getAttHarwareInfoList().split(",");
				String attServiceLocation[] = objForm.getAttServiceLocationList().split(",");
				
			
				for (int count = 1; count < attid.length; count++) {
					if(attid.length != attOEParentId.length){
						errors.add("Please enter values for OE Parent Id");
						errorsFound = true;
						break;
					}
					if(attOEParentId.length >0) {
						errors = Utility.validateValue(
								new ValidationBean(attOEParentId[count],
										"OE Parent Id ", 20),
										"" + Utility.CASE_MANDATORY + ","
										+ Utility.CASE_DIGITS_ONLY)
								.getCompleteMessageStrings();
						if(errors == null && attOEParentId[count].equals("0") ){
							errors=new ArrayList();
							errors.add("OE Parent Id Cannot be Zero");
							errorsFound = true;
							break;
						}	
					}
					
				}
				if (!errorsFound) {
					for (int count = 1; count < attid.length; count++) {
						if(attid.length != attM6ParentId.length){
							errors.add("Please enter values for M6 Parent Id");
							errorsFound = true;
							break;
						}
						if(attM6ParentId.length >0) {
							errors = Utility.validateValue(
									new ValidationBean(attM6ParentId[count],
											"M6 Parent Id ", 20),
											"" + Utility.CASE_MANDATORY + ","
											+ Utility.CASE_DIGITS_ONLY)
									.getCompleteMessageStrings();
							if(errors == null && attM6ParentId[count].equals("0")){
								errors=new ArrayList();
								errors.add("M6 Parent Id Cannot be Zero");
								errorsFound = true;
								break;
							}	
						}
						
					}
				}
				if (!errorsFound) {
					for (int count = 1; count < attid.length; count++) {
						if(attid.length != attM6ChildId.length){
							errors.add("Please enter values for M6 Child Id");
							errorsFound = true;
							break;
						}
						if(attM6ChildId.length >0) {
							errors = Utility.validateValue(
									new ValidationBean(attM6ChildId[count],
											"M6 Child Id ", 20),
											"" + Utility.CASE_MANDATORY + ","
											+ Utility.CASE_DIGITS_ONLY)
									.getCompleteMessageStrings();
							if(errors == null && attM6ChildId[count].equals("0")){
								errors=new ArrayList();
								errors.add("M6 Child Id Cannot be Zero");
								errorsFound = true;
								break;
							}	
						}
						
					}
				}
				
				if (errors != null && errors.size() > 0)// During Server Side
														// Validation
				{
					request.setAttribute("validation_errors", errors);
					saveMessages(request, messages);
					return getServiceAttributeList(mapping, form, request, response);
				}
			
				for (int count = 1; count < attid.length; count++) {
					objDto = new MastersAttributesDto();
					objDto.setAttID(attid[count]);
					objDto.setOeParentID(attOEParentId[count]);
					objDto.setM6ParentID(attM6ParentId[count]);
					objDto.setM6ChildID(attM6ChildId[count]);
					objDto.setSendToFX(attSend2FX[count]);
					objDto.setSendToM6(attSend2M6[count]);
					objDto.setServiceSummary(attServiceSummary[count]);
					objDto.setBillingInfo(attBillingInfo[count]);
					objDto.setChargeInfo(attChargeIfo[count]);
					objDto.setHardwareInfo(attHarwareInfo[count]);
					objDto.setServiceLocation(attServiceLocation[count]);

					attributeList.add(objDto);
				}
				String status = objModel.updateProductConfigAttributes(attributeList);
				if (status != null && !status.trim().equalsIgnoreCase("")
						&& status.equalsIgnoreCase("success")) {
					messages.add("", new ActionMessage("recordUpdateSuccess"));

					//
					saveMessages(request, messages);
				} else {
					messages.add("", new ActionMessage("recordFailureSuccess"));
					saveMessages(request, messages);
				}

				// getProductConfigurator(mapping, form, request, response);
				forward= getServiceAttributeList(mapping, form, request, response);
				

			} catch (Exception ex) {
				return mapping.findForward(Messages
						.getMessageValue("errorGlobalForward"));
			}
			return forward;

		}
}
