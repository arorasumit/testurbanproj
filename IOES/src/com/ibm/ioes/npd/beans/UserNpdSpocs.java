package com.ibm.ioes.npd.beans;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.utilities.PagingSorting;

public class UserNpdSpocs extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7069912811901631712L;

	private String employeeId;

	private ArrayList employeeList;

	private String[] roleId;

	private String previousRoleId;

	private String userReplacedRoleIds=null;

	private ArrayList roleList;

	private String level1Id;
	
	private ArrayList level1List;

	private String level2Id;
	
	private ArrayList level2List;

	private String buttonClicked = "S";

	private String fromDate;

	private String toDate;

	private String employeeName;

	private String oldRoles;

	private String newRoles;

	private String oldLevel1;

	private String newLevel1;

	private String oldLevel2;

	private String newLevel2;

	private String createdDate;

	private ArrayList roleHistoryList;

	private PagingSorting pagingSorting;

	private String originalLevel1Id;

	private String originalLevel2Id;

	private String level1EmployeeId=null;
	
	private String level2EmployeeId=null;
	
	private String originalLevel2EmployeeId=null;
	
	private String originalLevel1EmployeeId=null;
	
	private ArrayList level1EmployeeList=null;
	
	private ArrayList level2EmployeeList=null;
	
	ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks=null;
	
	ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks=null;
	
	String[] localRemovedRoles=null;
	
	String[] localReplacedUsers=null;
	
	private String reportRoleId=null;
	
	private String typeOfEmployee=null;
	
	private String[] unselectedRoleId=null;
	
	private String[] selectedRoleId=null;
	
	private ArrayList unselectedRoles=null;
	
	private ArrayList selectedRoles=null;
	
	private long npdempid;
	
	private String escalation=null;
	
	public String getOriginalLevel1Id() {
		return originalLevel1Id;
	}

	public void setOriginalLevel1Id(String originalLevel1Id) {
		this.originalLevel1Id = originalLevel1Id;
	}

	public UserNpdSpocs() {
		pagingSorting = new PagingSorting();
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	/**
	 * @return the employeeList
	 */
	public ArrayList getEmployeeList() {
		return employeeList;
	}

	/**
	 * @param employeeList
	 *            the employeeList to set
	 */
	public void setEmployeeList(ArrayList employeeList) {
		this.employeeList = employeeList;
	}

	/**
	 * @return the level1Id
	 */
	public String getLevel1Id() {
		return level1Id;
	}

	/**
	 * @param level1Id
	 *            the level1Id to set
	 */
	public void setLevel1Id(String level1Id) {
		this.level1Id = level1Id;
	}

	/**
	 * @return the level1List
	 */
	public ArrayList getLevel1List() {
		return level1List;
	}

	/**
	 * @param level1List
	 *            the level1List to set
	 */
	public void setLevel1List(ArrayList level1List) {
		this.level1List = level1List;
	}

	/**
	 * @return the level2Id
	 */
	public String getLevel2Id() {
		return level2Id;
	}

	/**
	 * @param level2Id
	 *            the level2Id to set
	 */
	public void setLevel2Id(String level2Id) {
		this.level2Id = level2Id;
	}

	/**
	 * @return the level2List
	 */
	public ArrayList getLevel2List() {
		return level2List;
	}

	/**
	 * @param level2List
	 *            the level2List to set
	 */
	public void setLevel2List(ArrayList level2List) {
		this.level2List = level2List;
	}

	/**
	 * @return the roleId
	 */
	public String[] getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String[] roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleList
	 */
	public ArrayList getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList
	 *            the roleList to set
	 */
	public void setRoleList(ArrayList roleList) {
		this.roleList = roleList;
	}

	public String getPreviousRoleId() {
		return previousRoleId;
	}

	public void setPreviousRoleId(String previousRoleId) {
		this.previousRoleId = previousRoleId;
	}

	public String getUserReplacedRoleIds() {
		return userReplacedRoleIds;
	}

	public void setUserReplacedRoleIds(String userReplacedRoleIds) {
		this.userReplacedRoleIds = userReplacedRoleIds;
	}

	public String getButtonClicked() {
		return buttonClicked;
	}

	public void setButtonClicked(String buttonClicked) {
		this.buttonClicked = buttonClicked;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		if (employeeName != null) {
			this.employeeName = employeeName.trim();
		}
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getNewLevel1() {
		return newLevel1;
	}

	public void setNewLevel1(String newLevel1) {
		this.newLevel1 = newLevel1;
	}

	public String getNewLevel2() {
		return newLevel2;
	}

	public void setNewLevel2(String newLevel2) {
		this.newLevel2 = newLevel2;
	}

	public String getNewRoles() {
		return newRoles;
	}

	public void setNewRoles(String newRoles) {
		this.newRoles = newRoles;
	}

	public String getOldLevel1() {
		return oldLevel1;
	}

	public void setOldLevel1(String oldLevel1) {
		this.oldLevel1 = oldLevel1;
	}

	public String getOldLevel2() {
		return oldLevel2;
	}

	public void setOldLevel2(String oldLevel2) {
		this.oldLevel2 = oldLevel2;
	}

	public String getOldRoles() {
		return oldRoles;
	}

	public void setOldRoles(String oldRoles) {
		this.oldRoles = oldRoles;
	}

	public ArrayList getRoleHistoryList() {
		return roleHistoryList;
	}

	public void setRoleHistoryList(ArrayList roleHistoryList) {
		this.roleHistoryList = roleHistoryList;
	}

	public String getOriginalLevel2Id() {
		return originalLevel2Id;
	}

	public void setOriginalLevel2Id(String originalLevel2Id) {
		this.originalLevel2Id = originalLevel2Id;
	}

	public String getReportRoleId() {
		return reportRoleId;
	}

	public void setReportRoleId(String reportRoleId) {
		this.reportRoleId = reportRoleId;
	}

	public String getLevel1EmployeeId() {
		return level1EmployeeId;
	}

	public void setLevel1EmployeeId(String level1EmployeeId) {
		this.level1EmployeeId = level1EmployeeId;
	}

	public String getLevel2EmployeeId() {
		return level2EmployeeId;
	}

	public void setLevel2EmployeeId(String level2EmployeeId) {
		this.level2EmployeeId = level2EmployeeId;
	}

	public String getOriginalLevel1EmployeeId() {
		return originalLevel1EmployeeId;
	}

	public void setOriginalLevel1EmployeeId(String originalLevel1EmployeeId) {
		this.originalLevel1EmployeeId = originalLevel1EmployeeId;
	}

	public String getOriginalLevel2EmployeeId() {
		return originalLevel2EmployeeId;
	}

	public void setOriginalLevel2EmployeeId(String originalLevel2EmployeeId) {
		this.originalLevel2EmployeeId = originalLevel2EmployeeId;
	}

	public ArrayList getLevel1EmployeeList() {
		return level1EmployeeList;
	}

	public void setLevel1EmployeeList(ArrayList level1EmployeeList) {
		this.level1EmployeeList = level1EmployeeList;
	}

	public ArrayList getLevel2EmployeeList() {
		return level2EmployeeList;
	}

	public void setLevel2EmployeeList(ArrayList level2EmployeeList) {
		this.level2EmployeeList = level2EmployeeList;
	}

	public String[] getLocalRemovedRoles() {
		return localRemovedRoles;
	}

	public void setLocalRemovedRoles(String[] localRemovedRoles) {
		this.localRemovedRoles = localRemovedRoles;
	}

	public String[] getLocalReplacedUsers() {
		return localReplacedUsers;
	}

	public void setLocalReplacedUsers(String[] localReplacedUsers) {
		this.localReplacedUsers = localReplacedUsers;
	}

	public ArrayList<ArrayList<TtrnProject>> getReplacedPendingRFITasks() {
		return replacedPendingRFITasks;
	}

	public void setReplacedPendingRFITasks(
			ArrayList<ArrayList<TtrnProject>> replacedPendingRFITasks) {
		this.replacedPendingRFITasks = replacedPendingRFITasks;
	}

	public ArrayList<ArrayList<TtrnProjecthierarchy>> getReplacedToDoListTasks() {
		return replacedToDoListTasks;
	}

	public void setReplacedToDoListTasks(
			ArrayList<ArrayList<TtrnProjecthierarchy>> replacedToDoListTasks) {
		this.replacedToDoListTasks = replacedToDoListTasks;
	}

	public String getTypeOfEmployee() {
		return typeOfEmployee;
	}

	public void setTypeOfEmployee(String typeOfEmployee) {
		this.typeOfEmployee = typeOfEmployee;
	}

	public String[] getSelectedRoleId() {
		return selectedRoleId;
	}

	public void setSelectedRoleId(String[] selectedRoleId) {
		this.selectedRoleId = selectedRoleId;
	}

	public String[] getUnselectedRoleId() {
		return unselectedRoleId;
	}

	public void setUnselectedRoleId(String[] unselectedRoleId) {
		this.unselectedRoleId = unselectedRoleId;
	}

	public ArrayList getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(ArrayList selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public ArrayList getUnselectedRoles() {
		return unselectedRoles;
	}

	public void setUnselectedRoles(ArrayList unselectedRoles) {
		this.unselectedRoles = unselectedRoles;
	}

	public long getNpdempid() {
		return npdempid;
	}

	public void setNpdempid(long npdempid) {
		this.npdempid = npdempid;
	}

	public String getEscalation() {
		return escalation;
	}

	public void setEscalation(String escalation) {
		this.escalation = escalation;
	}

}
