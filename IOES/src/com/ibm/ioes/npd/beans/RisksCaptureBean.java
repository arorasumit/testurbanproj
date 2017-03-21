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
public class RisksCaptureBean extends ActionForm {

	private String projectId;

	private ArrayList projectList;

	private String description;

	private String status;

	private String priority;

	private String timeframe;

	private String raisedby;

	private String raisedon;

	private String mitigationOwner;

	private ArrayList mitigationOwnerList;

	private String riskOwner;

	private ArrayList riskOwnerList;

	private String plannedReslDate;

	private String actualReslDate;

	private String source;

	private ArrayList riskDetailList;

	private String riskID;

	private String mitigationPlan;

	private String impact;

	private String impactOfRisk;

	private String probability;

	private String fromDate;

	private String toDate;

	private String projectName;

	private PagingSorting pagingSorting;

	private Long[] projectidlist;
	
	private ArrayList searchProjectList;

	public RisksCaptureBean() {
		pagingSorting = new PagingSorting();
	}

	public String getActualReslDate() {
		return actualReslDate;
	}

	public void setActualReslDate(String actualReslDate) {
		this.actualReslDate = actualReslDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description != null)
			this.description = description.trim();
	}

	public String getImpactOfRisk() {
		return impactOfRisk;
	}

	public void setImpactOfRisk(String impactOfRisk) {
		if (impactOfRisk != null)
			this.impactOfRisk = impactOfRisk.trim();
	}

	public String getMitigationOwner() {
		return mitigationOwner;
	}

	public void setMitigationOwner(String mitigationOwner) {
		this.mitigationOwner = mitigationOwner;
	}

	public ArrayList getMitigationOwnerList() {
		return mitigationOwnerList;
	}

	public void setMitigationOwnerList(ArrayList mitigationOwnerList) {
		this.mitigationOwnerList = mitigationOwnerList;
	}

	public String getMitigationPlan() {
		return mitigationPlan;
	}

	public void setMitigationPlan(String mitigationPlan) {
		if (mitigationPlan != null)
			this.mitigationPlan = mitigationPlan.trim();
	}

	public String getPlannedReslDate() {
		return plannedReslDate;
	}

	public void setPlannedReslDate(String plannedReslDate) {
		this.plannedReslDate = plannedReslDate;
	}

	public String getPriority() {
		return priority;
	}

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

	public ArrayList getProjectList() {
		return projectList;
	}

	public void setProjectList(ArrayList projectList) {
		this.projectList = projectList;
	}

	public String getRaisedby() {
		return raisedby;
	}

	public void setRaisedby(String raisedby) {
		this.raisedby = raisedby;
	}

	public String getRaisedon() {
		return raisedon;
	}

	public void setRaisedon(String raisedon) {
		this.raisedon = raisedon;
	}

	public ArrayList getRiskDetailList() {
		return riskDetailList;
	}

	public void setRiskDetailList(ArrayList riskDetailList) {
		this.riskDetailList = riskDetailList;
	}

	public String getRiskID() {
		return riskID;
	}

	public void setRiskID(String riskID) {
		this.riskID = riskID;
	}

	public String getRiskOwner() {
		return riskOwner;
	}

	public void setRiskOwner(String riskOwner) {
		this.riskOwner = riskOwner;
	}

	public ArrayList getRiskOwnerList() {
		return riskOwnerList;
	}

	public void setRiskOwnerList(ArrayList riskOwnerList) {
		this.riskOwnerList = riskOwnerList;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		if (source != null)
			this.source = source.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeframe() {
		return timeframe;
	}

	public void setTimeframe(String timeframe) {
		this.timeframe = timeframe;
	}

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
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

	public Long[] getProjectidlist() {
		return projectidlist;
	}

	public void setProjectidlist(Long[] projectidlist) {
		this.projectidlist = projectidlist;
	}

	public ArrayList getSearchProjectList() {
		return searchProjectList;
	}

	public void setSearchProjectList(ArrayList searchProjectList) {
		this.searchProjectList = searchProjectList;
	}

}
