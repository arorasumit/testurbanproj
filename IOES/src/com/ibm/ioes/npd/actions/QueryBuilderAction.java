package com.ibm.ioes.npd.actions;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.ioes.npd.beans.QueryBuilderBean;
import com.ibm.ioes.npd.hibernate.beans.QueryBuilderDto;
import com.ibm.ioes.npd.hibernate.dao.QueryBuilderDao;
import com.ibm.ioes.npd.model.QueryBuilderModel;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;

public class QueryBuilderAction extends DispatchAction {
	
	public ActionForward ExecuteQueryBuilder(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		String forwardMapping = null;
		QueryBuilderDao objDao = new QueryBuilderDao();
		QueryBuilderBean objform = (QueryBuilderBean) actionForm;
		QueryBuilderDto objDto = new QueryBuilderDto();
		HashMap htValue = new HashMap();
		ArrayList lstColms = new ArrayList();
		ArrayList lstData = new ArrayList();
		try {
		    

			forwardMapping = "success";
			if(objform.getQuery()!=null && !objform.getQuery().equalsIgnoreCase(""))
			{
				
				if(objform.getQuery().substring(0, 3).toUpperCase().equalsIgnoreCase("SEL"))
				{
					objDto.setQuery(objform.getQuery());
					htValue = objDao.ExecuteQueryBuilder(objDto);
					lstColms = (ArrayList) htValue.get("Columns");
					lstData = (ArrayList) htValue.get("Data");
					request.setAttribute("lstColms", lstColms);
					request.setAttribute("lstData", lstData);
				}
				if(objform.getQuery().substring(0, 3).toUpperCase().equalsIgnoreCase("UPD")
				   ||objform.getQuery().substring(0, 3).toUpperCase().equalsIgnoreCase("DEL")
				   ||objform.getQuery().substring(0, 3).toUpperCase().equalsIgnoreCase("INS"))
				{
					objDto.setQuery(objform.getQuery());
					htValue = objDao.ExecuteQueryBuilderUpdate(objDto);
					String irecCount = htValue.get("iCountUpdate").toString();
					request.setAttribute("irecCount", irecCount);
				}
				if(objform.getQuery().substring(0, 3).toUpperCase().equalsIgnoreCase("ALT")
					|| objform.getQuery().substring(0, 3).toUpperCase().equalsIgnoreCase("CRE")
					|| objform.getQuery().substring(0, 3).toUpperCase().equalsIgnoreCase("DRO"))
						{
							objDto.setQuery(objform.getQuery());
							htValue = objDao.ExecuteQueryBuilderDDL(objDto);
							String isddlexecuted = htValue.get("iCountUpdate").toString();
							if(isddlexecuted.equalsIgnoreCase("0"))
								isddlexecuted = "Table Altered/Created/Dropped Successfully";
							request.setAttribute("isddlexecuted", isddlexecuted);
						}
				
				
			}
			
		} catch (Exception e) {
			request.setAttribute("ErrorMessage", e.getMessage());
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e));
		}
		forward = mapping.findForward(forwardMapping);

		return forward;
	}
}
