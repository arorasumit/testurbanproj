package com.ibm.ioes.npd.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class ReferenceDocBean extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7069912811901631712L;

	private FormFile attachment;

	private TreeMap referenceDocList;

	private String[] referenceDocId;
	
	private String refDocName;
	
	private String rfDocSearch;
	
	private String fromDate;

	private String toDate;

	
	private PagingSorting pagingSorting;

	public ReferenceDocBean() 
		{
			pagingSorting=new PagingSorting();
		}
	

	public String getRefDocName() {
		return refDocName;
	}

	public void setRefDocName(String refDocName) {
		this.refDocName = refDocName;
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
	 * @return the referenceDocId
	 */
	public String[] getReferenceDocId() {
		return referenceDocId;
	}

	/**
	 * @param referenceDocId
	 *            the referenceDocId to set
	 */
	public void setReferenceDocId(String[] referenceDocId) {
		this.referenceDocId = referenceDocId;
	}

	public TreeMap getReferenceDocList() {
		return referenceDocList;
	}

	public void setReferenceDocList(TreeMap referenceDocList) {
		this.referenceDocList = referenceDocList;
	}

	public String getRfDocSearch() {
		return rfDocSearch;
	}

	public void setRfDocSearch(String rfDocSearch) {
		if (rfDocSearch != null) {
			this.rfDocSearch = rfDocSearch.trim();
		}
	}


	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}


	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
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
}
