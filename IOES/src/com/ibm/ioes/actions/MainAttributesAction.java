package com.ibm.ioes.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class MainAttributesAction extends DispatchAction
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
	public ActionForward getMainAttributes(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		
		MasterAttributesBean formBean=(MasterAttributesBean)form;
		ActionMessages messages = new ActionMessages();
		ArrayList<MastersAttributesDto> attributeList = new ArrayList<MastersAttributesDto>();
		MasterAttributesModel objService =  new MasterAttributesModel();
		MastersAttributesDto objDto =new MastersAttributesDto();
		//ArrayList<String> dataTypes
		try
		{
			attributeList=objService.viewAttributesList();
			request.setAttribute("attributeList", attributeList);
			if(attributeList.size()>0){
				objDto = (MastersAttributesDto)attributeList.get(0);
				
				Map map= objDto.getApplicatlionPropertiesMap();
				Set entries = map.entrySet();
				String expectedVal="";
				String dataTypes="";
			      Iterator it = entries.iterator();
			     
			      while (it.hasNext()) {
			         Map.Entry entry = (Map.Entry)it.next();
			         Object key = entry.getKey();  // Get the key from the entry.
			         Object value = entry.getValue();  // Get the value.
			 		Utility.SysOut( "   (" + key + "," + value + ")" );
			         if(key.toString().equals("EXPECTED_VALUES")){
			        	 expectedVal= (String)value;
			         }else {
			        	 dataTypes = (String)value;
			         }
			      }
			      formBean.setExpectedValuesList(expectedVal.split(","));
			      formBean.setDataTypesList(dataTypes.split(","));
				}
			forward = mapping.findForward("DisplayMainAttributes");
			//formBean.setAttList(attList);
			
			//formBean.setDataTypes(getDataTypes());
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
	 public ActionForward updateAttributes(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception
	 {
		ActionForward forward = new ActionForward(); // return value
		MasterAttributesBean objForm = (MasterAttributesBean) form;
		MastersAttributesDto objDto;// =new MastersAttributesDto();
		MasterAttributesModel objModel = new MasterAttributesModel();
		ArrayList<MastersAttributesDto> attributeList = new ArrayList<MastersAttributesDto>();
		ActionMessages messages = new ActionMessages();
		ArrayList errors = new ArrayList();
		try {

			String attid[] = objForm.getAttIdList().split(",");
			String attdesc[] = objForm.getAttDescList().split(",");
			String attdatatype[] = objForm.getAttDataTypeList().split(",");
			String attexpval[] = objForm.getAttExpValList().split(",");
			String attisman[] = objForm.getAttMandatoryList().split(",");

		
			for (int count = 1; count < attdesc.length; count++) {
				Utility.validateValue(
						new ValidationBean().loadValidationBean_Maxlength(
								"Attribute Description", attdesc[count], 200),
						Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
								Utility.CASE_MAXLENGTH)).appendToAndRetNewErrs(
						errors);
			}
			if (errors != null && errors.size() > 0)// During Server Side
													// Validation
			{
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				return mapping.findForward("DisplayMainAttributes");
			}
		
			for (int count = 1; count < attid.length; count++) {
				objDto = new MastersAttributesDto();
				objDto.setAttID(attid[count]);
				objDto.setAttDataType(attdatatype[count]);
				objDto.setAttDescription(attdesc[count]);
				objDto.setAttExpectedValue(attexpval[count]);
				objDto.setAttIsmandatory(attisman[count]);

				attributeList.add(objDto);
			}
			String status = objModel.updateMainAttributes(attributeList);
			if (status != null && !status.trim().equalsIgnoreCase("")
					&& status.equalsIgnoreCase("success")) {
				messages.add("", new ActionMessage("recordUpdateSuccess"));

				//
				saveMessages(request, messages);
			} else {
				messages.add("", new ActionMessage("recordFailureSuccess"));
				saveMessages(request, messages);
			}

			forward = getMainAttributes(mapping, form, request, response);

		} catch (Exception ex) {
			return mapping.findForward(Messages
					.getMessageValue("errorGlobalForward"));
		}
		return forward;

	}
}
