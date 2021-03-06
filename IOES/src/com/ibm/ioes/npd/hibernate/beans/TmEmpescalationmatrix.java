package com.ibm.ioes.npd.hibernate.beans;
// Generated Dec 9, 2009 11:12:39 AM by Hibernate Tools 3.1.0.beta4

import java.util.Date;


/**
 * TmEmpescalationmatrix generated by hbm2java
 */

public class TmEmpescalationmatrix  implements java.io.Serializable {


    // Fields    

     private long empescmatrixid;
     private TmEmployee tmEmployee;
     private long level1;
     private Long level2;
     private int isactive;
     private long createdby;
     private Date createddate;
     private Long modifiedby;
     private Date modifieddate;


    // Constructors

    /** default constructor */
    public TmEmpescalationmatrix() {
    }

	/** minimal constructor */
    public TmEmpescalationmatrix(long empescmatrixid, TmEmployee tmEmployee, long level1, int isactive, long createdby, Date createddate) {
        this.empescmatrixid = empescmatrixid;
        this.tmEmployee = tmEmployee;
        this.level1 = level1;
        this.isactive = isactive;
        this.createdby = createdby;
        this.createddate = createddate;
    }
    
    /** full constructor */
    public TmEmpescalationmatrix(long empescmatrixid, TmEmployee tmEmployee, long level1, Long level2, int isactive, long createdby, Date createddate, Long modifiedby, Date modifieddate) {
        this.empescmatrixid = empescmatrixid;
        this.tmEmployee = tmEmployee;
        this.level1 = level1;
        this.level2 = level2;
        this.isactive = isactive;
        this.createdby = createdby;
        this.createddate = createddate;
        this.modifiedby = modifiedby;
        this.modifieddate = modifieddate;
    }
    

   
    // Property accessors

    public long getEmpescmatrixid() {
        return this.empescmatrixid;
    }
    
    public void setEmpescmatrixid(long empescmatrixid) {
        this.empescmatrixid = empescmatrixid;
    }

    public TmEmployee getTmEmployee() {
        return this.tmEmployee;
    }
    
    public void setTmEmployee(TmEmployee tmEmployee) {
        this.tmEmployee = tmEmployee;
    }

    public long getLevel1() {
        return this.level1;
    }
    
    public void setLevel1(long level1) {
        this.level1 = level1;
    }

    public Long getLevel2() {
        return this.level2;
    }
    
    public void setLevel2(Long level2) {
        this.level2 = level2;
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
   








}
