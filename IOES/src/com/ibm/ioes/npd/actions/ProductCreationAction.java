/**
 * 
 */
package com.ibm.ioes.npd.actions;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.npd.beans.ProductCreationBean;
import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.RisksCaptureBean;
import com.ibm.ioes.npd.beans.ValidationBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.model.ProductCreationModel;
import com.ibm.ioes.npd.model.ProjectPlanGraphModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.Utility;

/**
 * @author Sanjay
 * 
 */
public class ProductCreationAction extends LookupDispatchAction {

	private static final Logger log;
	static {
		log = Logger.getLogger(ManageNpdSpocsAction.class);
	}

	public ProductCreationAction() {

	}

	protected Map getKeyMethodMap() {

		Map map = new HashMap();

		map.put("link.initProduct", "initProduct");
		map.put("submit.saveProject", "saveProduct");
		map.put("link.initSearchProduct", "initSearchProduct");
		map.put("submit.searchProduct", "searchProduct");
		map.put("initUpdateProduct", "initUpdateProduct");
		map.put("initSearchProductFinalizeNewPlan", "initSearchProductFinalizeNewPlan");
		map.put("initSearchEditBaselinedPlan", "initSearchEditBaselinedPlan");
		map.put("initSearchProductUpdation", "initSearchProductUpdation");
		map.put("initSearchUpdationAction", "initSearchUpdationAction");
		map.put("saveUpdateProductDraft", "saveUpdateProductDraft");
		map.put("viewUpdateProduct", "viewUpdateProduct");
		map.put("initUpdateProductApproval", "initUpdateProductApproval");
		map.put("approveRejectOverrideDraft", "approveRejectOverrideDraft");
		map.put("viewProductPlanGraph", "viewProductPlanGraph");
	
		return map;
	}

	public ActionForward initProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		try 
		{
			creationModel.viewProduct(productCreationBean);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initProduct method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		return mapping.findForward("viewProduct");
	}

	public ActionForward saveProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		String forwardMapping=null;
		ActionMessages messages=new ActionMessages();
		boolean errorsFound=false;
		try 
		{
/*			String npduserid=String.valueOf(((TmEmployee) request.getSession().getAttribute(
					AppConstants.LOGINBEAN)).getNpdempid());
*/
			productCreationBean.setCreatedBy("1");
			//Server Side Security Start for Created By
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getCreatedBy(),"Created By",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Name
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getProjectName(),"Project Name",100),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			
			//Server Side Security Start for NPD Category
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(productCreationBean.getNpdCategoryId()),"NPD Category",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			
			//Server Side Security Start for Product Brief
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getProductBrief(),"Product Brief",1000),
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
				creationModel.viewProduct(productCreationBean);
				return mapping.findForward("failure");
			}
			else
			{
				productCreationBean.setProductId(request.getParameter("parentProduct"));
				creationModel.insertProduct(productCreationBean);
				messages.add("detailsEntered",new ActionMessage("product.details.success",productCreationBean.getProjectId()));
				request.setAttribute("enterProjectPlan", "enterProjectPlan");
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveProduct method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		saveMessages(request, messages);
		return mapping.findForward("openFinalisePlan");

	}
	
	// calls the method to initialise the search page of PRoject
	public ActionForward initSearchProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{
		ActionMessages messages = new ActionMessages();
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project Name
			if((productCreationBean.getSearchProjectName()!=null) && (!productCreationBean.getSearchProjectName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProjectName(),"Project Name",20),
							""+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
				
			//Server Side Security Start for Project ID
			if((productCreationBean.getSearchProjectId()!=null) && (!productCreationBean.getSearchProjectId().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProjectId(),"Project ID",20),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Status
			if((String.valueOf(productCreationBean.getSearchProjectStatus())!=null) && (!String.valueOf(productCreationBean.getSearchProjectStatus()).equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(productCreationBean.getSearchProjectStatus()),"Project Status",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Priority
			if((productCreationBean.getSearchPriority()!=null) && (!productCreationBean.getSearchPriority().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchPriority(),"Project Priority",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for NPD Category
			if((productCreationBean.getSearchNpdCategoryId()!=null) && (!productCreationBean.getSearchNpdCategoryId().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchNpdCategoryId(),"NPD Category",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End	
			
			//Server Side Security Start for Product Category ID
			if((productCreationBean.getSearchProductCategoryId()!=null) && (!productCreationBean.getSearchProductCategoryId().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProductCategoryId(),"NPD Category",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;	
					}
				}
			}
			//Server Side Security End	
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				return mapping.findForward("viewSearchProduct");
			}
			else
			{
				creationModel.initSearchProduct(productCreationBean);
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initSearchProduct method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		return mapping.findForward("viewSearchProduct");
	}
	
	// calls the method to initialise the search page of PRoject
	public ActionForward initSearchProductFinalizeNewPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		try 
		{
			/*long npdempId = ((TmEmployee) request.getSession().getAttribute(
					AppConstants.LOGINBEAN)).getNpdempid();*/
			productCreationBean.setCreatedBy(String.valueOf("1")); //TO be changed Sumit 20-Jan-2011
			creationModel.initSearchProductFinalizeNewPlan(productCreationBean);
		} 
		catch (Exception ex) 
		{

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initSearchProductFinalizeNewPlan method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		return mapping.findForward("viewFinalizeNewProductsList");
	}
	
	// calls the method to initialise the search page of PRoject
	public ActionForward initSearchEditBaselinedPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		try {
			creationModel.initSearchEditBaselinedPlan(productCreationBean);
		} catch (Exception ex) {

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initSearchEditBaselinedPlan method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		return mapping.findForward("viewEditBaselinedPlanList");
	}	
	
	public ActionForward initSearchProductUpdation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	{
		ActionMessages messages=new ActionMessages();
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project ID
			if((productCreationBean.getSearchProjectId()!=null) && (!productCreationBean.getSearchProjectId().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProjectId(),"Project ID",8),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Name
			if((productCreationBean.getSearchProjectName()!=null) && (!productCreationBean.getSearchProjectName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProjectName(),"Project Name",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for NPD Category
			if((productCreationBean.getSearchNpdCategoryId()!=null) && (!productCreationBean.getSearchNpdCategoryId().equalsIgnoreCase("")) && (!productCreationBean.getSearchNpdCategoryId().equals("-1")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchNpdCategoryId(),"NPD Category",10),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Category
			if((productCreationBean.getSearchProductCategoryId()!=null) && (!productCreationBean.getSearchProductCategoryId().equalsIgnoreCase("")) && (!productCreationBean.getSearchProductCategoryId().equals("-1")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProductCategoryId(),"Product Category",10),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			//Server Side Security End
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				productCreationBean.setSearchProductCategoryId(null);
				productCreationBean.setSearchProjectName(null);
				productCreationBean.setSearchNpdCategoryId(null);
				productCreationBean.setSearchProjectId(null);
				creationModel.initSearchProductUpdation(productCreationBean);
				return mapping.findForward("viewProductUpdationList");
			}
			else
			{
				creationModel.initSearchProductUpdation(productCreationBean);
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initSearchProductUpdation method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		return mapping.findForward("viewProductUpdationList");
	}	
	
	public ActionForward initSearchUpdationAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		ActionMessages messages=new ActionMessages();
		boolean errorsFound=false;
		try 
		{
			//Server Side Security Start for Project ID
			if((productCreationBean.getSearchProjectId()!=null) && (!productCreationBean.getSearchProjectId().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProjectId(),"Project ID",8),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Name
			if((productCreationBean.getSearchProjectName()!=null) && (!productCreationBean.getSearchProjectName().equalsIgnoreCase("")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProjectName(),"Project Name",20),
							""+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for NPD Category
			if((productCreationBean.getSearchNpdCategoryId()!=null) && (!productCreationBean.getSearchNpdCategoryId().equalsIgnoreCase("")) && (!productCreationBean.getSearchNpdCategoryId().equals("-1")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchNpdCategoryId(),"NPD Category",10),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Category
			if((productCreationBean.getSearchProductCategoryId()!=null) && (!productCreationBean.getSearchProductCategoryId().equalsIgnoreCase("")) && (!productCreationBean.getSearchProductCategoryId().equals("-1")))
			{
				if(!errorsFound)
				{
					ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getSearchProductCategoryId(),"Product Category",10),
							""+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
					if(errors!=null)
					{
						request.setAttribute("validation_errors", errors);
						errorsFound=true;
					}
				}
			}
			//Server Side Security End
			
			if(errorsFound)
			{
				saveMessages(request, messages);
				productCreationBean.setSearchProductCategoryId(null);
				productCreationBean.setSearchProjectName(null);
				productCreationBean.setSearchNpdCategoryId(null);
				productCreationBean.setSearchProjectId(null);
				creationModel.initSearchProductUpdation(productCreationBean);
				return mapping.findForward("viewPrdUpdationActionList");
			}
			else
			{
				creationModel.initSearchUpdationAction(productCreationBean);
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initSearchUpdationAction method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		return mapping.findForward("viewPrdUpdationActionList");
	}		
	// calls the method to get the list of Projects which satisfy the filtered

	// calls the method to initialise the Update page of PRoject-
	
	public ActionForward initUpdateProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		try {
		    String projectId = Utility.decryptString(request.getParameter("projectId"));
		    productCreationBean.setProjectId(Long.parseLong(projectId));
			if (projectId != null
					&& !projectId.equalsIgnoreCase(
							AppConstants.INI_VALUE))
				productCreationBean = creationModel.initUpdateProject(new Long(
						projectId).longValue());
			
			
			TtrnProject ttrnProject=creationModel.getProduct(String.valueOf(productCreationBean.getProjectId()));
			request.setAttribute("projectBean", ttrnProject);
			productCreationBean.setUpdateMode(ProductCreationBean.PRD_UPDATE_ENTRYDRAFT);
			request.setAttribute("productCreationBean", productCreationBean);
			
		} catch (Exception ex) {

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initUpdateProduct method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		return mapping.findForward("viewUpdateProduct");
	}
	
	//
	public ActionForward viewUpdateProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		try {
			
		    String projectId = Utility.decryptString(request.getParameter("projectId"));
		    productCreationBean.setProjectId(Long.parseLong(projectId));
			if (projectId != null
					&& ! projectId.equalsIgnoreCase(
							AppConstants.INI_VALUE))
				productCreationBean = creationModel.initDraftProject(new Long(
						projectId).longValue());
		   
			
			TtrnProject ttrnProject=creationModel.getProduct(String.valueOf(productCreationBean.getProjectId()));
			request.setAttribute("projectBean", ttrnProject);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					AppConstants.DATE_FORMAT_PROC);
			productCreationBean.setPrjStartDate(simpleDateFormat
					.format(ttrnProject.getStartDate()));
			
			
			productCreationBean.setUpdateMode(ProductCreationBean.PRD_UPDATE_VIEWDRAFT);
			request.setAttribute("productCreationBean", productCreationBean);
			
		} catch (Exception ex) {

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewUpdateProduct method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		return mapping.findForward("viewUpdateProduct");
	}
	
	public ActionForward initUpdateProductApproval(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		try {
			 String projectId = Utility.decryptString(request.getParameter("projectId"));
			 productCreationBean.setProjectId(Long.parseLong(projectId));
				if (projectId != null
						&& ! projectId.equalsIgnoreCase(
								AppConstants.INI_VALUE))
					productCreationBean = creationModel.initDraftProject(new Long(projectId).longValue());
			
			TtrnProject ttrnProject=creationModel.getProduct(String.valueOf(productCreationBean.getProjectId()));
			request.setAttribute("projectBean", ttrnProject);
			productCreationBean.setUpdateMode(ProductCreationBean.PRD_UPDATE_ACTIONONDRAFT);
			
			SimpleDateFormat dte_in=new SimpleDateFormat("dd-MMM-yyyy");
			String projectStartDate=dte_in.format(ttrnProject.getStartDate());
			
			productCreationBean.setPrjStartDate(projectStartDate);
			request.setAttribute("productCreationBean", productCreationBean);
			creationModel.viewProduct(productCreationBean);
			
		} catch (Exception ex) {

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in initUpdateProductApproval method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		return mapping.findForward("viewUpdateProduct");
	}
	//	
	
	public ActionForward saveUpdateProductDraft(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		ActionMessages messages=new ActionMessages();
		boolean errorsFound=false;
		String forwardMapping;
		try 
		{
			long npdempId = ((TmEmployee) request.getSession().getAttribute(
					AppConstants.LOGINBEAN)).getNpdempid();
			String createdBy=String.valueOf(npdempId);
			productCreationBean.setCreatedBy(createdBy);
			//Server Side Validation Start for Created By 
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(createdBy,"Created By",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Validation End for Created By 
			
			//Server Side Security Start for Product Brief
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getProductBrief(),"Product Brief",1000),
				""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();				
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Airtel Potential 
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getAirtelPotential(),"Airtel Potential",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Capex Requirement 
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getCapexRequirement(),"Capex Requirement",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Total Market Potential 
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getTotalMktPotential(),"Total Market Potential",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Target Market
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getTargetMarket(),"Target Market",100),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//	Server Side Security Start for Project Launch Date 
			if(!errorsFound)
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
				Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,productCreationBean.getLaunchDate(),"Project Launch Date",
						productCreationBean.getPrjStartDate(),"Project Start Date",ValidationBean.GREATER,
						new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
				ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
						""+Utility.CASE_DATECOMPARISION_MANDATORY).getCompleteMessageStrings();
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
				TtrnProject ttrnProject=creationModel.getProduct(String.valueOf(productCreationBean.getProjectId()));
				request.setAttribute("projectBean", ttrnProject);
				productCreationBean.setUpdateMode(ProductCreationBean.PRD_UPDATE_ENTRYDRAFT);
				request.setAttribute("productCreationBean", productCreationBean);
				forwardMapping = "viewUpdateProductError";
				return mapping.findForward(forwardMapping);
			}
			else
			{
			
				int status=creationModel.updateProduct(productCreationBean);
				if(status==1)
				{
					//success
					messages.add("updateProductToDraft",new ActionMessage("product.updateToDraft.success"));
				}
				else
				{
					//failure
					messages.add("updateProductToDraft",new ActionMessage("product.updateToDraft.failure"));
				}
				TtrnProject ttrnProject=creationModel.getProduct(String.valueOf(productCreationBean.getProjectId()));
				request.setAttribute("projectBean", ttrnProject);
				productCreationBean.setUpdateMode(ProductCreationBean.PRD_UPDATE_VIEWDRAFT);
				request.setAttribute("productCreationBean", productCreationBean);
				request.setAttribute("refreshParent", "refreshParent");
			}		
		} catch (Exception ex) {

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in saveUpdateProductDraft method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		saveMessages(request, messages);
		return mapping.findForward("viewUpdateProduct");
	}
	
	public ActionForward approveRejectOverrideDraft(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProductCreationModel creationModel = new ProductCreationModel();
		ActionMessages messages=new ActionMessages();
		boolean errorsFound=false;
		try {
			
			long npdempId = ((TmEmployee) request.getSession().getAttribute(
					AppConstants.LOGINBEAN)).getNpdempid();
			String createdBy=String.valueOf(npdempId);
			productCreationBean.setCreatedBy(createdBy);
			//Server Side Security Start for Created By
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getCreatedBy(),"Created By",30),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Priority of Launch
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getLaunchPriority(),"Priority of Launch",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Target Market
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getTargetMarket(),"Target Market",100),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Product Category
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(String.valueOf(productCreationBean.getPrdCategoryId()),"Product Category",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Airtel Potential 
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getAirtelPotential(),"Airtel Potential",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Capex Requirement 
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getCapexRequirement(),"Capex Requirement",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Total Market Potential 
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getTotalMktPotential(),"Total Market Potential  ",10),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Project Launch Date 
			if(!errorsFound)
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT_PROC);
				Object obArray[]={""+ValidationBean.VN_DATE_COMPARISION,productCreationBean.getLaunchDate(),"Project Launch Date",
						productCreationBean.getPrjStartDate(),"Project Start Date",ValidationBean.GREATER,
						new java.text.SimpleDateFormat(Messages.getMessageValue("calendar_entry_format"))};
				ArrayList errors=Utility.validateValue(	new ValidationBean(obArray),
						""+Utility.CASE_DATECOMPARISION_MANDATORY).getCompleteMessageStrings();
				if(errors!=null)
				{
					request.setAttribute("validation_errors", errors);
					errorsFound=true;
				}
			}
			//Server Side Security End
			
			//Server Side Security Start for Product Brief
			if(!errorsFound)
			{
				ArrayList errors=Utility.validateValue(new ValidationBean(productCreationBean.getProductBrief(),"Product Brief",1000),
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
				productCreationBean=creationModel.initDraftProject(new Long(productCreationBean.getProjectId()).longValue());				
				TtrnProject ttrnProject=creationModel.getProduct(String.valueOf(productCreationBean.getProjectId()));
				request.setAttribute("projectBean", ttrnProject);
				productCreationBean.setUpdateMode(ProductCreationBean.PRD_UPDATE_ACTIONONDRAFT);
				SimpleDateFormat dte_in=new SimpleDateFormat("dd-MMM-yyyy");
				String projectStartDate=dte_in.format(ttrnProject.getStartDate());
				productCreationBean.setPrjStartDate(projectStartDate);
				request.setAttribute("productCreationBean", productCreationBean);
				creationModel.viewProduct(productCreationBean);
				return mapping.findForward("viewUpdateProduct");
			}
			else
			{
				int status=creationModel.actionForUpdateProduct(productCreationBean);
				
				if(status==1)
				{
					//success
					String updateActionType=productCreationBean.getUpdateActionType();
					
					if(ProductCreationBean.APPROVE_OVERRIDE.equals(updateActionType))
					{
						messages.add("updateProductAction",new ActionMessage("productUpdate.action.approveOverride.success"));
					}
					else if(ProductCreationBean.REJECT.equals(updateActionType))
					{
						messages.add("updateProductAction",new ActionMessage("productUpdate.action.reject.success"));
					}
					
					//send mail based on action
					try
					{
					creationModel.sendMailForProjectUpdation(productCreationBean);
					}
					catch(Exception ee)
					{
						ee.printStackTrace();
					}
					
				}
				else
				{
					//failure
					messages.add("updateProductAction",new ActionMessage("productUpdate.action.failure"));
				}
				TtrnProject ttrnProject=creationModel.getProduct(String.valueOf(productCreationBean.getProjectId()));
				request.setAttribute("projectBean", ttrnProject);
				productCreationBean.setUpdateMode(ProductCreationBean.PRD_ACTION_VIEW);
				request.setAttribute("productCreationBean", productCreationBean);
				request.setAttribute("refreshParent", "refreshParent");
			}
			
		} catch (Exception ex) {

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in approveRejectOverrideDraft method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}

		saveMessages(request, messages);
		return mapping.findForward("viewUpdateProduct");
	}	
		
	public ActionForward viewProductPlanGraph(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProductCreationBean productCreationBean = (ProductCreationBean) form;
		ProjectPlanGraphModel model = new ProjectPlanGraphModel();
		ActionMessages messages=new ActionMessages();
		try {
			if (request.getParameter("projectId") != null
					&& !request.getParameter("projectId").equalsIgnoreCase(
							AppConstants.INI_VALUE))
			{
				
				ArrayList<TtrnProjecthierarchy> list=null;
				ProjectPlanInstanceBean bean=new ProjectPlanInstanceBean();
				
				bean.setProjectId(String.valueOf(productCreationBean.getProjectId()));
				list=model.getProductPlanGraph(bean);
				if(list==null)
				{
					messages.add("asd",new ActionMessage("productPlanGraph.creation.failure"));
				}
				else
				{
					request.setAttribute("taskList", list);	
				}
				
				
			}
			
			//TtrnProject ttrnProject=creationModel.getProduct(String.valueOf(productCreationBean.getProjectId()));
			//request.setAttribute("projectBean", ttrnProject);
			//productCreationBean.setUpdateMode(ProductCreationBean.PRD_UPDATE_VIEWDRAFT);
			//request.setAttribute("productCreationBean", productCreationBean);
			
		} catch (Exception ex) {

			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in viewUpdateProduct method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			String forwardMapping = Messages.getMessageValue("errorGlobalForward");
			return mapping.findForward(forwardMapping);
		}
		saveMessages(request, messages);
		return mapping.findForward("viewPrdPlanGraph");
	}
	
}
