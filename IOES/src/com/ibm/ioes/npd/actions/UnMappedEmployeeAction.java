package com.ibm.ioes.npd.actions;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.beans.AssumptionCaptureBean;
import com.ibm.ioes.npd.beans.UnMappedEmployeeBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.UnMappedEmployeeDto;
import com.ibm.ioes.npd.model.UnMappedEmployeeModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class UnMappedEmployeeAction extends DispatchAction
{
	public ActionForward getUnMappedEmployee(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
   {
		ActionForward forward = new ActionForward();
		String forwardMapping = null;
	 	UnMappedEmployeeModel model=new UnMappedEmployeeModel();
		ActionMessages messages = new ActionMessages();
		UnMappedEmployeeBean formbean =(UnMappedEmployeeBean)form;
		UnMappedEmployeeDto objUnMappedEmployee= new UnMappedEmployeeDto();
		ArrayList<UnMappedEmployeeDto> listUnMappedEmployee = new ArrayList<UnMappedEmployeeDto>();
		boolean errorsFound=false;
		try 
		{
			//Searching
			String searchEmpName=formbean.getEmployeeName();
			String empID=formbean.getEmployeeId();
			
			if((searchEmpName!=null) && (!searchEmpName.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(searchEmpName,"Employee Name",30),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}

			if((empID!=null) && (!empID.equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(empID,"Employee ID",30),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				forward = mapping.findForward("success");
				return forward;
			}
			else
			{
				objUnMappedEmployee.setEmployeeName(searchEmpName);
				
				
				int employeeID;
				if (empID==null || empID.equalsIgnoreCase(""))
				{
					employeeID=0;
				}
				else
				{
					employeeID=Integer.parseInt(formbean.getEmployeeId());
				}
				objUnMappedEmployee.setEmployeeId(employeeID);
						
				PagingSorting pagingSorting=formbean.getPagingSorting();
				if(pagingSorting==null )
				{
					pagingSorting=new PagingSorting();
					formbean.setPagingSorting(pagingSorting);
				}
				pagingSorting.setPagingSorting(true,true);
				
				String sortByColumn=pagingSorting.getSortByColumn();
				String sortByOrder=pagingSorting.getSortByOrder();
				int pageNumber=pagingSorting.getPageNumber();
				
				if(sortByColumn==null || "".equals(sortByColumn))
				{
					sortByColumn="empName";//default flag
				}
				if(sortByOrder==null || "".equals(sortByOrder))
				{
					sortByOrder=PagingSorting.increment;
				}
				if(pageNumber==0)
				{
					pageNumber=1;
				}
				
				pagingSorting.setSortByColumn(sortByColumn);
				pagingSorting.setSortByOrder(sortByOrder);
				pagingSorting.setPageNumber(pageNumber);
				
				objUnMappedEmployee.setPagingSorting(pagingSorting);
		
				listUnMappedEmployee = model.viewEmpResourceReport(objUnMappedEmployee);
				formbean.setEmpList(listUnMappedEmployee);
				request.setAttribute("listEmpResource", listUnMappedEmployee);
				if (listUnMappedEmployee.size()!=0)
				{
					formbean.setCheckRptData(1);
				}
				else
				{
					formbean.setCheckRptData(0);
				}
				forwardMapping = "success";
			}
		}
		catch (Exception e) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) 
		{
			saveErrors(request, messages);
		}
		forward = mapping.findForward(forwardMapping);
		return forward;
   }
	
	public ActionForward viewExportToExcel(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	   {
			ActionForward forward = new ActionForward();
			String forwardMapping = null;
		 	UnMappedEmployeeModel model=new UnMappedEmployeeModel();
			ActionMessages messages = new ActionMessages();
			UnMappedEmployeeBean formbean =(UnMappedEmployeeBean)form;
			UnMappedEmployeeDto objUnMappedEmployee= new UnMappedEmployeeDto();
			ArrayList<UnMappedEmployeeDto> listUnMappedEmployee = new ArrayList<UnMappedEmployeeDto>();
			boolean errorsFound=false;
			try 
			{
				//Searching
				String searchEmpName=formbean.getEmployeeName();		
				String empID=formbean.getEmployeeId();
				
				if((searchEmpName!=null) && (!searchEmpName.equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(searchEmpName,"Employee Name",30),
								""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}

				if((empID!=null) && (!empID.equalsIgnoreCase("")))
				{
					if(!errorsFound)
					{
						ArrayList errors=Utility.validateValue(new ValidationBean(empID,"Employee ID",30),
								""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
						if(errors!=null)
						{
							request.setAttribute("validation_errors", errors);
							errorsFound=true;
						}
					}
				}
				
				if(errorsFound)
				{
					saveMessages(request, messages);
					forward = mapping.findForward("success");
					return forward;
				}
				else
				{
					objUnMappedEmployee.setEmployeeName(searchEmpName);
					int employeeID;
					if (empID==null || empID.equalsIgnoreCase(""))
					{
						employeeID=0;
					}
					else
					{
						employeeID=Integer.parseInt(formbean.getEmployeeId());
					}
					objUnMappedEmployee.setEmployeeId(employeeID);
							
					PagingSorting pagingSorting=formbean.getPagingSorting();
					if(pagingSorting==null )
					{
						pagingSorting=new PagingSorting();
						formbean.setPagingSorting(pagingSorting);
					}
					pagingSorting.setPagingSorting(true,true);
					
					String sortByColumn=pagingSorting.getSortByColumn();
					String sortByOrder=pagingSorting.getSortByOrder();
					int pageNumber=pagingSorting.getPageNumber();
					
					if(sortByColumn==null || "".equals(sortByColumn))
					{
						sortByColumn="empName";//default flag
					}
					if(sortByOrder==null || "".equals(sortByOrder))
					{
						sortByOrder=PagingSorting.increment;
					}
					if(pageNumber==0)
					{
						pageNumber=1;
					}
					
					pagingSorting.setSortByColumn(sortByColumn);
					pagingSorting.setSortByOrder(sortByOrder);
					pagingSorting.setPageNumber(pageNumber);
					
					objUnMappedEmployee.setPagingSorting(pagingSorting);
			
					listUnMappedEmployee = model.viewEmpResourceExport(objUnMappedEmployee);
					formbean.setEmpList(listUnMappedEmployee);
					request.setAttribute("listEmpResource", listUnMappedEmployee);
					if (listUnMappedEmployee.size()!=0)
					{
						formbean.setCheckRptData(1);
					}
					else
					{
						formbean.setCheckRptData(0);
					}
					forwardMapping = "export";
				} 
			}
			catch (Exception e) 
			{
				AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
			}
			if (!messages.isEmpty()) 
			{
				saveErrors(request, messages);
			}
			forward = mapping.findForward(forwardMapping);
			
			return forward;

	   }
}
