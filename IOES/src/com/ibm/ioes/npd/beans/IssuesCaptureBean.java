/**
 * 
 */
package com.ibm.ioes.npd.beans;

import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.utilities.PagingSorting;

/**
 * @author Sanjay
 *
 */
public class IssuesCaptureBean extends ActionForm {
	
	private String method;//RT
	private String projectId;
	
	private ArrayList projectList;
	
	private String description;
	
	private String status;
	
	private String priority;
	
	private String  timeframe;
	
	private String raisedby;
	
	private String raisedon;
	
	private String resolutionOwner;
	
	private ArrayList resolutionOwnerList;
	
	private String plannedReslDate;
	
	private String actualReslDate;
	
	private String reslutionSteps;
	
	private ArrayList issueDetailList;
	
	private String issueID;
	
	private String fromDate;

	private String toDate;
	
	private String projectName;
	
	private PagingSorting pagingSorting;
	
	private ArrayList rOwnerList;
	
	private ArrayList searchProjectList;

	public IssuesCaptureBean() 
		{
			pagingSorting=new PagingSorting();
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

	/**
	 * @return the issueDetailList
	 */
	public ArrayList getIssueDetailList() {
		return issueDetailList;
	}

	/**
	 * @param issueDetailList the issueDetailList to set
	 */
	public void setIssueDetailList(ArrayList issueDetailList) {
		this.issueDetailList = issueDetailList;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		if(description!=null)
		this.description = description.trim();
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}



	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		if (projectId != null) {
			this.projectId = projectId.trim();
		}
	}

	/**
	 * @return the projectList
	 */
	public ArrayList getProjectList() {
		return projectList;
	}

	/**
	 * @param projectList the projectList to set
	 */
	public void setProjectList(ArrayList projectList) {
		this.projectList = projectList;
	}

	/**
	 * @return the raisedby
	 */
	public String getRaisedby() {
		return raisedby;
	}

	/**
	 * @param raisedby the raisedby to set
	 */
	public void setRaisedby(String raisedby) {
		this.raisedby = raisedby;
	}

	

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the timeframe
	 */
	public String getTimeframe() {
		return timeframe;
	}

	/**
	 * @param timeframe the timeframe to set
	 */
	public void setTimeframe(String timeframe) {
		this.timeframe = timeframe;
	}

	


	/**
	 * @return the actualReslDate
	 */
	public String getActualReslDate() {
		return actualReslDate;
	}

	/**
	 * @param actualReslDate the actualReslDate to set
	 */
	public void setActualReslDate(String actualReslDate) {
		this.actualReslDate = actualReslDate;
	}

	/**
	 * @return the plannedReslDate
	 */
	public String getPlannedReslDate() {
		return plannedReslDate;
	}

	/**
	 * @param plannedReslDate the plannedReslDate to set
	 */
	public void setPlannedReslDate(String plannedReslDate) {
		this.plannedReslDate = plannedReslDate;
	}

	/**
	 * @return the raisedon
	 */
	public String getRaisedon() {
		return raisedon;
	}

	/**
	 * @param raisedon the raisedon to set
	 */
	public void setRaisedon(String raisedon) {
		this.raisedon = raisedon;
	}

	/**
	 * @return the reslutionSteps
	 */
	public String getReslutionSteps() {
		return reslutionSteps;
	}

	/**
	 * @param reslutionSteps the reslutionSteps to set
	 */
	public void setReslutionSteps(String reslutionSteps) {
		if(reslutionSteps!=null)
		this.reslutionSteps = reslutionSteps.trim();
	}

	/**
	 * @return the resolutionOwner
	 */
	public String getResolutionOwner() {
		return resolutionOwner;
	}

	/**
	 * @param resolutionOwner the resolutionOwner to set
	 */
	public void setResolutionOwner(String resolutionOwner) {
		this.resolutionOwner = resolutionOwner;
	}

	/**
	 * @return the resolutionOwnerList
	 */
	public ArrayList getResolutionOwnerList() {
		return resolutionOwnerList;
	}

	/**
	 * @param resolutionOwnerList the resolutionOwnerList to set
	 */
	public void setResolutionOwnerList(ArrayList resolutionOwnerList) {
		this.resolutionOwnerList = resolutionOwnerList;
	}

	/**
	 * @return the issueID
	 */
	public String getIssueID() {
		return issueID;
	}

	/**
	 * @param issueID the issueID to set
	 */
	public void setIssueID(String issueID) {
		this.issueID = issueID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		if (projectName != null) {
			this.projectName = projectName.trim();
		}
	}


	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}


	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public ArrayList getROwnerList() {
		return rOwnerList;
	}


	public void setROwnerList(ArrayList ownerList) {
		rOwnerList = ownerList;
	}


	public ArrayList getSearchProjectList() {
		return searchProjectList;
	}


	public void setSearchProjectList(ArrayList searchProjectList) {
		this.searchProjectList = searchProjectList;
	}

}
