package com.ibm.ioes.npd.hibernate.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.ProductCreationBean;
import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.beans.RoleEmployeeBean;
import com.ibm.ioes.npd.beans.TaskInstanceBean;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.MyToDoListDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingattendies;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow;
import com.ibm.ioes.npd.hibernate.dao.ProjectPlanGraphDao;
import com.ibm.ioes.npd.hibernate.dao.BaseDaoFactory;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;
import com.ibm.ioes.npd.hibernate.dao.DaoConstants;
import com.ibm.ioes.npd.hibernate.dao.WorkFlowDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.NpdConnection;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.SendMailUtility;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;
//import com.ibm.ws.wim.bridge.assembler.datagraph.SearchDataGraphAssembler;

public class ProjectPlanGraphDao {
	
	public ArrayList<TtrnProjecthierarchy> getProductPlanGraph(
			Connection conn, ProjectPlanInstanceBean bean)
			throws NpdException {


		ArrayList<TtrnProjecthierarchy> list=new ArrayList<TtrnProjecthierarchy>();;
		try {

			String sql = "SELECT * FROM NPD.V_PRODUCTPLANGRAPH WHERE PROJECTID="+bean.getProjectId();

			

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TtrnProjecthierarchy dto = new TtrnProjecthierarchy();

				dto.setProjectid(rs.getLong("PROJECTID"));
				dto.setCurrenttaskid(rs.getLong("CURRENTTASKID"));
				dto.setPrevtaskid(rs.getLong("PARENTID"));
				dto.setTaskname(rs.getString("TASKNAME"));
				dto.setTaskdesc(rs.getString("TASKDESC"));


				list.add(dto);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getProductPlanGraph method of "
					+ this.getClass().getSimpleName()
					+ AppUtility.getStackTrace(ex));
			throw new NpdException(
					"Exception occured in getProductPlanGraph method of "
							+ this.getClass().getSimpleName());
		} finally {
			try {
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(list.size()==0)
		{
			list=null;
		}
		return list;
	}
}
