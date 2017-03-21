package com.ibm.ioes.npd.beans;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.hibernate.beans.LocationDto;
import com.ibm.ioes.npd.utilities.PagingSorting;

public class MeetingBean extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7069912811901631712L;

	private String subject;

	private String date;

	private String startTime;

	private String endTime;

	private ArrayList mandatoryList;

	private String[] mandatoryId;

	private ArrayList optionalList;

	private String[] optionalId;

	private FormFile attachment;

	private FormFile attachment1;

	private FormFile attachment2;

	private String meetingType;

	private String meetingIdCreated;

	private String meetingId;

	private ArrayList meetingList;

	private ArrayList momList;

	private String fromDate;

	private String toDate;

	private String docSearch;

	private String meetingMsg = null;

	private PagingSorting pagingSorting;
	
	private String productId = null;
	
	private ArrayList productList=null;
	
	private ArrayList<LocationDto> locationList=null;
	
	private String locationId = null;
	
	private String searchProjectId=null;
	
	private String searchProjectName=null;
	
	private String smsMessage=null;
	
	private String[] smsContacts=null;
	
	private FormFile attachment4;
	
	private java.util.Date createDate;
	
	private String createdID;
	
	private ArrayList selectedMandatoryList;
	
	private ArrayList selectedOptionalList;
	
	private String[] selectedMandatoryId;
	
	private String[] selectedOptionalId;
	
	public String[] getSelectedMandatoryId() {
		return selectedMandatoryId;
	}

	public void setSelectedMandatoryId(String[] selectedMandatoryId) {
		this.selectedMandatoryId = selectedMandatoryId;
	}

	public ArrayList getSelectedMandatoryList() {
		return selectedMandatoryList;
	}

	public void setSelectedMandatoryList(ArrayList selectedMandatoryList) {
		this.selectedMandatoryList = selectedMandatoryList;
	}

	public String[] getSelectedOptionalId() {
		return selectedOptionalId;
	}

	public void setSelectedOptionalId(String[] selectedOptionalId) {
		this.selectedOptionalId = selectedOptionalId;
	}

	public ArrayList getSelectedOptionalList() {
		return selectedOptionalList;
	}

	public void setSelectedOptionalList(ArrayList selectedOptionalList) {
		this.selectedOptionalList = selectedOptionalList;
	}

	public MeetingBean() {
		pagingSorting = new PagingSorting();
	}

	/**
	 * @return the meetingMsg
	 */
	public String getMeetingMsg() {
		return meetingMsg;
	}

	/**
	 * @param meetingMsg
	 *            the meetingMsg to set
	 */
	public void setMeetingMsg(String meetingMsg) {
		this.meetingMsg = meetingMsg;
	}

	/**
	 * @return the meetingIdCreated
	 */
	public String getMeetingIdCreated() {
		return meetingIdCreated;
	}

	/**
	 * @param meetingIdCreated
	 *            the meetingIdCreated to set
	 */
	public void setMeetingIdCreated(String meetingIdCreated) {
		this.meetingIdCreated = meetingIdCreated;
	}

	/**
	 * @return the attachment
	 */
	public FormFile getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment
	 *            the attachment to set
	 */
	public void setAttachment(FormFile attachment) {
		this.attachment = attachment;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the mandatoryId
	 */
	public String[] getMandatoryId() {
		return mandatoryId;
	}

	/**
	 * @param mandatoryId
	 *            the mandatoryId to set
	 */
	public void setMandatoryId(String[] mandatoryId) {
		this.mandatoryId = mandatoryId;
	}

	/**
	 * @return the mandatoryList
	 */
	public ArrayList getMandatoryList() {
		return mandatoryList;
	}

	/**
	 * @param mandatoryList
	 *            the mandatoryList to set
	 */
	public void setMandatoryList(ArrayList mandatoryList) {
		this.mandatoryList = mandatoryList;
	}

	/**
	 * @return the optionalId
	 */
	public String[] getOptionalId() {
		return optionalId;
	}

	/**
	 * @param optionalId
	 *            the optionalId to set
	 */
	public void setOptionalId(String[] optionalId) {
		this.optionalId = optionalId;
	}

	/**
	 * @param meetingId
	 *            the meetingId to set
	 */
	public void setMeetingId(String meetingId) {
		if (meetingId != null) {
			this.meetingId = meetingId.trim();
		}
	}

	/**
	 * @return the optionalList
	 */
	public ArrayList getOptionalList() {
		return optionalList;
	}

	/**
	 * @param optionalList
	 *            the optionalList to set
	 */
	public void setOptionalList(ArrayList optionalList) {
		this.optionalList = optionalList;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		if (subject != null ) {
			this.subject = subject.trim();
		}

	}

	/**
	 * @return the meetingType
	 */
	public String getMeetingType() {
		return meetingType;
	}

	/**
	 * @param meetingType
	 *            the meetingType to set
	 */
	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	/**
	 * @return the momList
	 */
	public ArrayList getMeetingList() {
		return meetingList;
	}

	/**
	 * @param momList
	 *            the momList to set
	 */
	public void setMeetingList(ArrayList meetingList) {
		this.meetingList = meetingList;
	}

	/**
	 * @return the meetingId
	 */
	public String getMeetingId() {
		return meetingId;
	}

	/**
	 * @return the momList
	 */
	public ArrayList getMomList() {
		return momList;
	}

	/**
	 * @param momList
	 *            the momList to set
	 */
	public void setMomList(ArrayList momList) {
		this.momList = momList;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public FormFile getAttachment1() {
		return attachment1;
	}

	public void setAttachment1(FormFile attachment1) {
		this.attachment1 = attachment1;
	}

	public FormFile getAttachment2() {
		return attachment2;
	}

	public void setAttachment2(FormFile attachment2) {
		this.attachment2 = attachment2;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getDocSearch() {
		return docSearch;
	}

	public void setDocSearch(String docSearch) {
		this.docSearch = docSearch;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ArrayList getProductList() {
		return productList;
	}

	public void setProductList(ArrayList productList) {
		this.productList = productList;
	}

	public ArrayList<LocationDto> getLocationList() {
		return locationList;
	}

	public void setLocationList(ArrayList<LocationDto> locationList) {
		this.locationList = locationList;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getSearchProjectId() {
		return searchProjectId;
	}

	public void setSearchProjectId(String searchProjectId) {
		this.searchProjectId = searchProjectId;
	}

	public String getSearchProjectName() {
		return searchProjectName;
	}

	public void setSearchProjectName(String searchProjectName) {
		this.searchProjectName = searchProjectName;
	}

	public String getSmsMessage() {
		return smsMessage;
	}

	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}

	public String[] getSmsContacts() {
		return smsContacts;
	}

	public void setSmsContacts(String[] smsContacts) {
		this.smsContacts = smsContacts;
	}

	public FormFile getAttachment4() {
		return attachment4;
	}

	public void setAttachment4(FormFile attachment4) {
		this.attachment4 = attachment4;
	}

	

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatedID() {
		return createdID;
	}

	public void setCreatedID(String createdID) {
		this.createdID = createdID;
	}

	

}
