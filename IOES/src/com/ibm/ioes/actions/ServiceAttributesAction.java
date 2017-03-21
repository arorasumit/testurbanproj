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

public class ServiceAttributesAction extends DispatchAction
{
	
	public ActionForward getServiceTypeList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		ActionForward forward = new ActionForward(); // return value
		MasterAttributesBean objForm=(MasterAttributesBean)form;
		//MastersAttributesDto objDto =new MastersAttributesDto();
		MasterAttributesModel objModel=new MasterAttributesModel();
		ArrayList<MastersAttributesDto> attributeList= new ArrayList<MastersAttributesDto>();
		try
		{
			objForm.setHiddenFlag("2");
			request.setAttribute("attributeList", attributeList);
			objForm.setAttributeList(attributeList);
			objModel.loadServices(objForm);
			forward = mapping.findForward("DisplayServiceAttributes");
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
		
		ActionMessages messages = new ActionMessages();
		try
		{
			
			objForm.getHiddenServiceTypeId();
			System.err.println("Service Id:"+objForm.getHiddenServiceTypeId());
			attributeList=objModel.getServiceAttributeList(objForm);
			request.setAttribute("attributeList", attributeList);
			objModel.loadServices(objForm);
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
		      objForm.setExpectedValuesList(expectedVal.split(","));
		      objForm.setDataTypesList(dataTypes.split(","));
			}
			objForm.setHiddenFlag("2");
			forward = mapping.findForward("DisplayServiceAttributes");
		}
		catch (Exception e) 
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		return forward;
	}
	

	public ActionForward updateServiceAttributes(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{			
		//logger.info("-------------updateServiceAttributes-------------");
		ActionForward forward = new ActionForward(); // return value
		MasterAttributesBean objForm=(MasterAttributesBean)form;
		MastersAttributesDto objDto ;//=new MastersAttributesDto();
		MasterAttributesModel objModel=new MasterAttributesModel();
		ArrayList<MastersAttributesDto> attributeList= new ArrayList<MastersAttributesDto>();
		ActionMessages messages = new ActionMessages();
	
		try
		{
			ArrayList errors= new ArrayList();
			
			String attid[]=objForm.getAttIdList().split(",");
			String attdesc[]=objForm.getAttDescList().split(",");
			String attdatatype[]=objForm.getAttDataTypeList().split(",");
			String attexpval[]=objForm.getAttExpValList().split(",");
			String attisman[]=objForm.getAttMandatoryList().split(",");
			
//			******* Validation Ends Here *************************************
			for (int count =1;count <attdesc.length ; count++)
			{
			Utility.validateValue(new ValidationBean().loadValidationBean_Maxlength
					("Attribute Description", attdesc[count], 200),
					Utility.generateCSV(Utility.CASE_SPECIALCHARACTERS,
										Utility.CASE_MAXLENGTH))
					.appendToAndRetNewErrs(errors);
			}
			if(errors!=null && errors.size()>0)//During Server Side Validation
			{
				request.setAttribute("validation_errors", errors);
				saveMessages(request, messages);
				return mapping.findForward("DisplaySearch");
			}
			//******* Validation Ends Here *************************************
			for (int count =1;count <attid.length ; count++) {
				objDto= new MastersAttributesDto();
				objDto.setAttID(attid[count]);
				objDto.setAttDataType(attdatatype[count]);
				objDto.setAttDescription(attdesc[count]);
				objDto.setAttExpectedValue(attexpval[count]);
				objDto.setAttIsmandatory(attisman[count]);
				
				attributeList.add(objDto);
			}
			String status = objModel.updateServiceAttributes(attributeList);
			if(status!=null && !status.trim().equalsIgnoreCase("") && status.equalsIgnoreCase("success"))
			{
				messages.add("", new ActionMessage("recordUpdateSuccess"));
				
				//
				saveMessages(request , messages);
			} else {
				messages.add("", new ActionMessage("recordFailureSuccess"));
				saveMessages(request , messages);
			}
				
	   //********** Validate form fields ************************
				
			forward = getServiceAttributeList(mapping, form, request, response);
				
		}
		catch(Exception ex)
		{
			return mapping.findForward(Messages.getMessageValue("errorGlobalForward"));
		}
		return forward;
	}
	

}
