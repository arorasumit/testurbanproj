/**
 * 
 */
package com.ibm.ioes.npd.hibernate.beans;

import java.sql.Blob;
import java.util.Date;

/**
 * @author Sanjay
 *
 */
public class TmRfiClarification implements java.io.Serializable {

	private long refclarificationid;

	private String refclarificationdesc;

	private int isactive;

	private long createdby;

	private Date createddate;

	private long modifiedby;

	private Date modifieddate;

	private int projectId;

	/**
	 * @return the createdby
	 */
	public long getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the createddate
	 */
	public Date getCreateddate() {
		return createddate;
	}

	/**
	 * @param createddate the createddate to set
	 */
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	/**
	 * @return the isactive
	 */
	public int getIsactive() {
		return isactive;
	}

	/**
	 * @param isactive the isactive to set
	 */
	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	/**
	 * @return the modifiedby
	 */
	public long getModifiedby() {
		return modifiedby;
	}

	/**
	 * @param modifiedby the modifiedby to set
	 */
	public void setModifiedby(long modifiedby) {
		this.modifiedby = modifiedby;
	}

	/**
	 * @return the modifieddate
	 */
	public Date getModifieddate() {
		return modifieddate;
	}

	/**
	 * @param modifieddate the modifieddate to set
	 */
	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}


	public String getRefclarificationdesc() {
		return refclarificationdesc;
	}

	public void setRefclarificationdesc(String refclarificationdesc) {
		this.refclarificationdesc = refclarificationdesc;
	}

	public long getRefclarificationid() {
		return refclarificationid;
	}

	public void setRefclarificationid(long refclarificationid) {
		this.refclarificationid = refclarificationid;
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

}
