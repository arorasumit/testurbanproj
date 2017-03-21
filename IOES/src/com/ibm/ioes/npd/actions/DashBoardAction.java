package com.ibm.ioes.npd.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.hibernate.beans.DashboardDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.model.MyToDoListServicesImpl;
import com.ibm.ioes.npd.model.PlrUploadingServicesImpl;
import com.ibm.ioes.npd.model.RfiModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;

public class DashBoardAction extends DispatchAction {

	public ActionForward getDashBoard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward();

		ActionMessages messages = new ActionMessages();
		String forwardMapping = null;

		try {
			TmEmployee tmEmployee = (TmEmployee) request.getSession()
					.getAttribute(AppConstants.LOGINBEAN);

			String userId = String.valueOf(tmEmployee.getNpdempid());
			//tmEmployee.setCurrentRoleId(Long.parseLong(roleID));
			//String roleID = ""+tmEmployee.getOneRoleId();
			String roleID=  Long.toString(tmEmployee.getCurrentRoleId());
			MyToDoListServicesImpl myToDoListServicesImpl = new MyToDoListServicesImpl();
			RfiModel rfiModel = new RfiModel();
			PlrUploadingServicesImpl plrUploadingServicesImpl = new PlrUploadingServicesImpl();
			int taskPending_mytodoList = myToDoListServicesImpl
					.getMyToListCount(userId, roleID);
			int taskPending_rfi = rfiModel.getPendingRFICount(userId, roleID);
			int taskPending_plrUploading = plrUploadingServicesImpl
					.getPendingPLRUploadingCount(userId);

			DashboardDto dashboardDto = new DashboardDto();
			dashboardDto.setTaskpending_mytodoList(taskPending_mytodoList);
			dashboardDto.setTaskpending_rfi(taskPending_rfi);
			dashboardDto.setTaskpending_plrUploading(taskPending_plrUploading);
			dashboardDto.setTotal_taskPending(taskPending_mytodoList
					+ taskPending_rfi + taskPending_plrUploading);

			request.setAttribute("dashboardDto", dashboardDto);

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

}
