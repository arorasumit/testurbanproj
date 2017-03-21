package com.ibm.ioes.npd.hibernate.beans;
// Generated Feb 8, 2010 10:30:27 AM by Hibernate Tools 3.1.0.beta4

import java.util.Date;


/**
 * TtrnProjectworkflowdet generated by hbm2java
 */

public class TtrnProjectworkflowdet  implements java.io.Serializable {


    // Fields    

     private long projectwfid;
     private int projecttype;
     private Long projectid;
     private Date createddate;
     private long createdby;
     private Date modifieddate;
     private Long modifiedby;
     private int isactive;
     private Long projectworkflowid;


    // Constructors

    /** default constructor */
    public TtrnProjectworkflowdet() {
    }

	/** minimal constructor */
    public TtrnProjectworkflowdet(long projectwfid, int projecttype, Date createddate, long createdby, int isactive) {
        this.projectwfid = projectwfid;
        this.projecttype = projecttype;
        this.createddate = createddate;
        this.createdby = createdby;
        this.isactive = isactive;
    }
    
    /** full constructor */
    public TtrnProjectworkflowdet(long projectwfid, int projecttype, Long projectid, Date createddate, long createdby, Date modifieddate, Long modifiedby, int isactive, Long projectworkflowid) {
        this.projectwfid = projectwfid;
        this.projecttype = projecttype;
        this.projectid = projectid;
        this.createddate = createddate;
        this.createdby = createdby;
        this.modifieddate = modifieddate;
        this.modifiedby = modifiedby;
        this.isactive = isactive;
        this.projectworkflowid = projectworkflowid;
    }
    

   
    // Property accessors

    public long getProjectwfid() {
        return this.projectwfid;
    }
    
    public void setProjectwfid(long projectwfid) {
        this.projectwfid = projectwfid;
    }

    public int getProjecttype() {
        return this.projecttype;
    }
    
    public void setProjecttype(int projecttype) {
        this.projecttype = projecttype;
    }

    public Long getProjectid() {
        return this.projectid;
    }
    
    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public Date getCreateddate() {
        return this.createddate;
    }
    
    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public long getCreatedby() {
        return this.createdby;
    }
    
    public void setCreatedby(long createdby) {
        this.createdby = createdby;
    }

    public Date getModifieddate() {
        return this.modifieddate;
    }
    
    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }

    public Long getModifiedby() {
        return this.modifiedby;
    }
    
    public void setModifiedby(Long modifiedby) {
        this.modifiedby = modifiedby;
    }

    public int getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    public Long getProjectworkflowid() {
        return this.projectworkflowid;
    }
    
    public void setProjectworkflowid(Long projectworkflowid) {
        this.projectworkflowid = projectworkflowid;
    }
   








}