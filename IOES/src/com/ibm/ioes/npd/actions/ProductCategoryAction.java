package com.ibm.ioes.npd.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.beans.ProductCategoryBean;
import com.ibm.ioes.npd.hibernate.beans.ProductCategoryDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.model.ProductCategoryModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;

public class ProductCategoryAction extends DispatchAction {

	public ActionForward viewProductCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		ActionMessages messages = new ActionMessages();
		ProductCategoryModel productCategoryModel = new ProductCategoryModel();
		String forwardMapping = null;
		ArrayList<ProductCategoryDto> productCategoryDtoList = null;

		try {

			productCategoryDtoList = productCategoryModel
					.getProductCategoryList();
			request.setAttribute("productCategoryDtoList",
					productCategoryDtoList);
			forwardMapping = "success";
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}
		if (!messages.isEmpty()) {
			// saveErrors(request, messages);
		}

		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward updateProductCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		ProductCategoryBean productCategoryBean = (ProductCategoryBean) form;
		ActionMessages messages = new ActionMessages();
		ProductCategoryModel productCategoryModel = new ProductCategoryModel();
		String forwardMapping = null;
		int updateStatus = 0;

		try {
			String[] productIdArr = productCategoryBean.getUpdateStatus();
			String[] statusArr = new String[productIdArr.length];
			String[] descriptionArr = new String[productIdArr.length];
			for (int id = 0; id < productIdArr.length; id++) {
				statusArr[id] = (String) request.getParameter("status"
						+ productIdArr[id]);
				descriptionArr[id] = (String) request
						.getParameter("description" + productIdArr[id]);
			}
			TmEmployee employee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
			updateStatus = productCategoryModel.updateProductCategory(
					productIdArr, statusArr, descriptionArr, Integer.parseInt(""+employee.getCreatedby()));
			productCategoryBean.setUpdateStatus(null);

			if (updateStatus == 1) {
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
						AppConstants.RECORD_SAVE_SUCCESS));
			} else {
				messages.add(AppConstants.RECORD_SAVE_FAILURE,
						new ActionMessage(AppConstants.RECORD_SAVE_FAILURE));
			}
			if (!messages.isEmpty()) {
				this.saveMessages(request, messages);
			}
			viewProductCategory(mapping, form, request, response);
			forwardMapping = "productUpdated";
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}

		forward = mapping.findForward(forwardMapping);

		return forward;
	}

	public ActionForward addProductCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		ProductCategoryBean productCategoryBean = (ProductCategoryBean) form;
		ActionMessages messages = new ActionMessages();
		ProductCategoryModel productCategoryModel = new ProductCategoryModel();
		String forwardMapping = null;
		ProductCategoryDto productCategoryDto = new ProductCategoryDto();
		int addStatus = 0;

		try {
			productCategoryDto.setProductDesc(productCategoryBean
					.getDescription()[0]);
			productCategoryDto.setIsActive(productCategoryBean.getIsActive());
			
			TmEmployee employee = (TmEmployee) request.getSession().getAttribute(AppConstants.LOGINBEAN);
			addStatus = productCategoryModel
					.addProductCategory(productCategoryDto, Integer.parseInt(""+employee.getCreatedby()));
			productCategoryBean.setDescription(null);
			productCategoryBean.setIsActive(null);

			if (addStatus == 1) {
				messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
						AppConstants.RECORD_SAVE_SUCCESS));
			} else {
				messages.add(AppConstants.RECORD_SAVE_FAILURE,
						new ActionMessage(AppConstants.RECORD_SAVE_FAILURE));
			}
			if (!messages.isEmpty()) {
				this.saveMessages(request, messages);
			}

			forwardMapping = "productAdded";
		} catch (Exception e) {
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}

		forward = mapping.findForward(forwardMapping);

		return forward;
	}

}
