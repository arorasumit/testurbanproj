package com.ibm.ioes.npd.hibernate.beans;
// Generated Dec 9, 2009 11:12:39 AM by Hibernate Tools 3.1.0.beta4

import java.util.Date;


/**
 * TtrnProjectisssues generated by hbm2java
 */

public class TtrnProjectisssues  implements java.io.Serializable {


    // Fields    

     private long projissueid;
     private TtrnProject ttrnProject;
     private String issuedesc;
     private int status;
     private String priority;
     private int resolutiontime;
     private TmEmployee raisedby;
     private Date raiseddate;
     private TmEmployee resolutionowner;
     private Date resolutiondate;
     private Date actualresolutiondate;
     private String preposedresolutionsteps;
     private int isactive;
     private long createdby;
     private Date createddate;
     private Long modifiedby;
     private Date modifieddate;
     private int projectId;
     


    // Constructors

    /** default constructor */
    public TtrnProjectisssues() {
    }

	/** minimal constructor */
    public TtrnProjectisssues(long projissueid, TtrnProject ttrnProject, String issuedesc, int status, String priority, int resolutiontime, TmEmployee raisedby, Date raiseddate, int isactive, long createdby, Date createddate) {
        this.projissueid = projissueid;
        this.ttrnProject = ttrnProject;
        this.issuedesc = issuedesc;
        this.status = status;
        this.priority = priority;
        this.resolutiontime = resolutiontime;
        this.raisedby = raisedby;
        this.raiseddate = raiseddate;
        this.isactive = isactive;
        this.createdby = createdby;
        this.createddate = createddate;
    }
    
    /** full constructor */
    public TtrnProjectisssues(long projissueid, TtrnProject ttrnProject, String issuedesc, int status, String priority, int resolutiontime, TmEmployee raisedby, Date raiseddate, TmEmployee resolutionowner, Date resolutiondate, Date actualresolutiondate, String preposedresolutionsteps, int isactive, long createdby, Date createddate, Long modifiedby, Date modifieddate) {
        this.projissueid = projissueid;
        this.ttrnProject = ttrnProject;
        this.issuedesc = issuedesc;
        this.status = status;
        this.priority = priority;
        this.resolutiontime = resolutiontime;
        this.raisedby = raisedby;
        this.raiseddate = raiseddate;
        this.resolutionowner = resolutionowner;
        this.resolutiondate = resolutiondate;
        this.actualresolutiondate = actualresolutiondate;
        this.preposedresolutionsteps = preposedresolutionsteps;
        this.isactive = isactive;
        this.createdby = createdby;
        this.createddate = createddate;
        this.modifiedby = modifiedby;
        this.modifieddate = modifieddate;
    }
    

   
    // Property accessors

    public long getProjissueid() {
        return this.projissueid;
    }
    
    public void setProjissueid(long projissueid) {
        this.projissueid = projissueid;
    }

    public TtrnProject getTtrnProject() {
        return this.ttrnProject;
    }
    
    public void setTtrnProject(TtrnProject ttrnProject) {
        this.ttrnProject = ttrnProject;
    }

    public String getIssuedesc() {
        return this.issuedesc;
    }
    
    public void setIssuedesc(String issuedesc) {
        this.issuedesc = issuedesc;
    }

    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    public String getPriority() {
        return this.priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getResolutiontime() {
        return this.resolutiontime;
    }
    
    public void setResolutiontime(int resolutiontime) {
        this.resolutiontime = resolutiontime;
    }


    /**
	 * @return the raisedby
	 */
	public TmEmployee getRaisedby() {
		return raisedby;
	}

	/**
	 * @param raisedby the raisedby to set
	 */
	public void setRaisedby(TmEmployee raisedby) {
		this.raisedby = raisedby;
	}

	public Date getRaiseddate() {
        return this.raiseddate;
    }
    
    public void setRaiseddate(Date raiseddate) {
        this.raiseddate = raiseddate;
    }

  
    /**
	 * @return the resolutionowner
	 */
	public TmEmployee getResolutionowner() {
		return resolutionowner;
	}

	/**
	 * @param resolutionowner the resolutionowner to set
	 */
	public void setResolutionowner(TmEmployee resolutionowner) {
		this.resolutionowner = resolutionowner;
	}

	public Date getResolutiondate() {
        return this.resolutiondate;
    }
    
    public void setResolutiondate(Date resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    public Date getActualresolutiondate() {
        return this.actualresolutiondate;
    }
    
    public void setActualresolutiondate(Date actualresolutiondate) {
        this.actualresolutiondate = actualresolutiondate;
    }

    public String getPreposedresolutionsteps() {
        return this.preposedresolutionsteps;
    }
    
    public void setPreposedresolutionsteps(String preposedresolutionsteps) {
        this.preposedresolutionsteps = preposedresolutionsteps;
    }

    public int getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    public long getCreatedby() {
        return this.createdby;
    }
    
    public void setCreatedby(long createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return this.createddate;
    }
    
    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Long getModifiedby() {
        return this.modifiedby;
    }
    
    public void setModifiedby(Long modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Date getModifieddate() {
        return this.modifieddate;
    }
    
    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
   








}
