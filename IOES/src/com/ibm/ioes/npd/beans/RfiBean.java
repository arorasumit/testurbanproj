/**
 * 
 */
package com.ibm.ioes.npd.beans;

import java.sql.Blob;
import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.PagingSorting;

/**
 * @author Sanjay
 *
 */
public class RfiBean extends ActionForm {
	
	private static final long serialVersionUID = 7069912811901631712L;

	private int npdCategoryId;

	private ArrayList npdCategoryList;

	private String projectName;

	private int prdCategoryId;

	private ArrayList prdCategoryList;

	private String airtelPotential;

	private String capexRequirement;

	private String targetMarket;

	private String totalMktPotential;

	private String launchDate;

	private String prjStartDate;

	private String launchPriority;

	private String productBrief;

	private int projectId;
	
	private int rfiId;

	private ArrayList projectList;

	private ArrayList projectDetailList;
	
	private FormFile attachment;
	
	private String rfiClarification= null;
	

	private String rfiRemarks= null;
	
	private Blob rfiDocument= null;
	
	private String assignedToUser= null;
	
	private String currentstatus= null;
	
	private String currentTask= null;
	
	private String daysWithCurrentUser= null;
	
	private String stageId= null;
	
	private String taskId= null;
	
	private String productCatDesc= null;
	
	private String rfiDocumentName= null;
	

	private String projectworkflowid;
	
	private PagingSorting pagingSorting;
	
	private String isSaved = "0";

	public RfiBean() 
		{
			pagingSorting=new PagingSorting();
		}
	


	/**
	 * @return the airtelPotential
	 */
	public String getAirtelPotential() {
		return airtelPotential;
	}

	/**
	 * @param airtelPotential the airtelPotential to set
	 */
	public void setAirtelPotential(String airtelPotential) {
		this.airtelPotential = airtelPotential;
	}

	/**
	 * @return the capexRequirement
	 */
	public String getCapexRequirement() {
		return capexRequirement;
	}

	/**
	 * @param capexRequirement the capexRequirement to set
	 */
	public void setCapexRequirement(String capexRequirement) {
		this.capexRequirement = capexRequirement;
	}

	/**
	 * @return the launchDate
	 */
	public String getLaunchDate() {
		return launchDate;
	}

	/**
	 * @param launchDate the launchDate to set
	 */
	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}

	/**
	 * @return the launchPriority
	 */
	public String getLaunchPriority() {
		return launchPriority;
	}

	/**
	 * @param launchPriority the launchPriority to set
	 */
	public void setLaunchPriority(String launchPriority) {
		this.launchPriority = launchPriority;
	}

	/**
	 * @return the npdCategoryId
	 */
	public int getNpdCategoryId() {
		return npdCategoryId;
	}

	/**
	 * @param npdCategoryId the npdCategoryId to set
	 */
	public void setNpdCategoryId(int npdCategoryId) {
		this.npdCategoryId = npdCategoryId;
	}

	/**
	 * @return the npdCategoryList
	 */
	public ArrayList getNpdCategoryList() {
		return npdCategoryList;
	}

	/**
	 * @param npdCategoryList the npdCategoryList to set
	 */
	public void setNpdCategoryList(ArrayList npdCategoryList) {
		this.npdCategoryList = npdCategoryList;
	}

	/**
	 * @return the prdCategoryId
	 */
	public int getPrdCategoryId() {
		return prdCategoryId;
	}

	/**
	 * @param prdCategoryId the prdCategoryId to set
	 */
	public void setPrdCategoryId(int prdCategoryId) {
		this.prdCategoryId = prdCategoryId;
	}

	/**
	 * @return the prdCategoryList
	 */
	public ArrayList getPrdCategoryList() {
		return prdCategoryList;
	}

	/**
	 * @param prdCategoryList the prdCategoryList to set
	 */
	public void setPrdCategoryList(ArrayList prdCategoryList) {
		this.prdCategoryList = prdCategoryList;
	}

	/**
	 * @return the prjStartDate
	 */
	public String getPrjStartDate() {
		return prjStartDate;
	}

	/**
	 * @param prjStartDate the prjStartDate to set
	 */
	public void setPrjStartDate(String prjStartDate) {
		this.prjStartDate = prjStartDate;
	}

	/**
	 * @return the productBrief
	 */
	public String getProductBrief() {
		return productBrief;
	}

	/**
	 * @param productBrief the productBrief to set
	 */
	public void setProductBrief(String productBrief) {
		this.productBrief = productBrief;
	}

	/**
	 * @return the projectDetailList
	 */
	public ArrayList getProjectDetailList() {
		return projectDetailList;
	}

	/**
	 * @param projectDetailList the projectDetailList to set
	 */
	public void setProjectDetailList(ArrayList projectDetailList) {
		this.projectDetailList = projectDetailList;
	}

	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
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
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the targetMarket
	 */
	public String getTargetMarket() {
		return targetMarket;
	}

	/**
	 * @param targetMarket the targetMarket to set
	 */
	public void setTargetMarket(String targetMarket) {
		this.targetMarket = targetMarket;
	}

	/**
	 * @return the totalMktPotential
	 */
	public String getTotalMktPotential() {
		return totalMktPotential;
	}

	/**
	 * @param totalMktPotential the totalMktPotential to set
	 */
	public void setTotalMktPotential(String totalMktPotential) {
		this.totalMktPotential = totalMktPotential;
	}

	/**
	 * @return the rfiClarification
	 */
	public String getRfiClarification() {
		return rfiClarification;
	}

	/**
	 * @param rfiClarification the rfiClarification to set
	 */
	public void setRfiClarification(String rfiClarification) {
		if(rfiClarification!=null&&!rfiClarification.equalsIgnoreCase(AppConstants.INI_VALUE))
		this.rfiClarification = rfiClarification.trim();
	}

	
	/**
	 * @return the attachment
	 */
	public FormFile getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(FormFile attachment) {
		this.attachment = attachment;
	}



	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}



	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}



	public Blob getRfiDocument() {
		return rfiDocument;
	}



	public void setRfiDocument(Blob rfiDocument) {
		this.rfiDocument = rfiDocument;
	}



	public String getRfiRemarks() {
		return rfiRemarks;
	}



	public void setRfiRemarks(String rfiRemarks) {
		this.rfiRemarks = rfiRemarks;
	}



	public int getRfiId() {
		return rfiId;
	}



	public void setRfiId(int rfiId) {
		this.rfiId = rfiId;
	}



	public String getAssignedToUser() {
		return assignedToUser;
	}



	public void setAssignedToUser(String assignedToUser) {
		this.assignedToUser = assignedToUser;
	}



	public String getCurrentstatus() {
		return currentstatus;
	}



	public void setCurrentstatus(String currentstatus) {
		this.currentstatus = currentstatus;
	}



	public String getCurrentTask() {
		return currentTask;
	}



	public void setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
	}



	public String getDaysWithCurrentUser() {
		return daysWithCurrentUser;
	}



	public void setDaysWithCurrentUser(String daysWithCurrentUser) {
		this.daysWithCurrentUser = daysWithCurrentUser;
	}



	public String getProductCatDesc() {
		return productCatDesc;
	}



	public void setProductCatDesc(String productCatDesc) {
		this.productCatDesc = productCatDesc;
	}



	public String getStageId() {
		return stageId;
	}



	public void setStageId(String stageId) {
		this.stageId = stageId;
	}



	public String getTaskId() {
		return taskId;
	}



	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}



	public String getRfiDocumentName() {
		return rfiDocumentName;
	}



	public void setRfiDocumentName(String rfiDocumentName) {
		this.rfiDocumentName = rfiDocumentName;
	}



	public String getProjectworkflowid() {
		return projectworkflowid;
	}



	public void setProjectworkflowid(String projectworkflowid) {
		this.projectworkflowid = projectworkflowid;
	}



	public String getIsSaved() {
		return isSaved;
	}



	public void setIsSaved(String isSaved) {
		this.isSaved = isSaved;
	}

}
