package com.ibm.ioes.npd.hibernate.beans;
// Generated Dec 9, 2009 11:12:39 AM by Hibernate Tools 3.1.0.beta4

import java.util.Date;


/**
 * TmWorkflowstage generated by hbm2java
 */

public class TmWorkflowstage  implements java.io.Serializable {


    // Fields    

     private int stageid;
     private TmWorkflow workflow;
     private String stagename;
     private String stagedesc;
     private int createdby;
     private Date createddate;
     private Integer modifiedby;
     private Date modifieddate;
     private int isactive;


    // Constructors

    /** default constructor */
    public TmWorkflowstage() {
    }

	/** minimal constructor */
    public TmWorkflowstage(int stageid, TmWorkflow workflow, int createdby, Date createddate, int isactive) {
        this.stageid = stageid;
        this.workflow = workflow;
        this.createdby = createdby;
        this.createddate = createddate;
        this.isactive = isactive;
    }
    
    /** full constructor */
    public TmWorkflowstage(int stageid,TmWorkflow workflow, String stagename, String stagedesc, int createdby, Date createddate, Integer modifiedby, Date modifieddate, int isactive) {
        this.stageid = stageid;
        this.workflow = workflow;
        this.stagename = stagename;
        this.stagedesc = stagedesc;
        this.createdby = createdby;
        this.createddate = createddate;
        this.modifiedby = modifiedby;
        this.modifieddate = modifieddate;
        this.isactive = isactive;
    }
    

   
    // Property accessors

    public int getStageid() {
        return this.stageid;
    }
    
    public void setStageid(int stageid) {
        this.stageid = stageid;
    }

     /**
	 * @return the workflow
	 */
	public TmWorkflow getWorkflow() {
		return workflow;
	}

	/**
	 * @param workflow the workflow to set
	 */
	public void setWorkflow(TmWorkflow workflow) {
		this.workflow = workflow;
	}

	public String getStagename() {
        return this.stagename;
    }
    
    public void setStagename(String stagename) {
        this.stagename = stagename;
    }

    public String getStagedesc() {
        return this.stagedesc;
    }
    
    public void setStagedesc(String stagedesc) {
        this.stagedesc = stagedesc;
    }

    public int getCreatedby() {
        return this.createdby;
    }
    
    public void setCreatedby(int createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return this.createddate;
    }
    
    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Integer getModifiedby() {
        return this.modifiedby;
    }
    
    public void setModifiedby(Integer modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Date getModifieddate() {
        return this.modifieddate;
    }
    
    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }

    public int getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }
   








}