package com.ibm.ioes.npd.hibernate.beans;
// Generated Dec 9, 2009 11:12:38 AM by Hibernate Tools 3.1.0.beta4

import java.sql.Blob;
import java.util.Date;


/**
 * TmReferencedocs generated by hbm2java
 */

public class TmReferencedocs  implements java.io.Serializable {


    // Fields    

     private long refdocid;
     private String refdocname;
     private Blob refdoc;
     private int isactive;
 	private TmEmployee createdby;
     private Date createddate;
     private Long modifiedby;
     private Date modifieddate;
     private long prev_doc_id;
     private String actualRefDocName;


    // Constructors

    /** default constructor */
    public TmReferencedocs() {
    }

	/** minimal constructor */
    public TmReferencedocs(long refdocid, String refdocname, int isactive, TmEmployee createdby, Date createddate,long prev_doc_id, String actualRefDocName) {
        this.refdocid = refdocid;
        this.refdocname = refdocname;
        this.isactive = isactive;
        this.createdby = createdby;
        this.createddate = createddate;
        this.prev_doc_id=prev_doc_id;
        this.actualRefDocName=actualRefDocName;
    }
    
    /** full constructor */
    public TmReferencedocs(long refdocid, String refdocname, Blob refdoc, int isactive, TmEmployee createdby, Date createddate, Long modifiedby, Date modifieddate,long prev_doc_id, String actualRefDocName) {
        this.refdocid = refdocid;
        this.refdocname = refdocname;
        this.refdoc = refdoc;
        this.isactive = isactive;
        this.createdby = createdby;
        this.createddate = createddate;
        this.modifiedby = modifiedby;
        this.modifieddate = modifieddate;
        this.prev_doc_id=prev_doc_id;
        this.actualRefDocName=actualRefDocName;
    }
    

   
    // Property accessors

    public long getRefdocid() {
        return this.refdocid;
    }
    
    public void setRefdocid(long refdocid) {
        this.refdocid = refdocid;
    }

    public String getRefdocname() {
        return this.refdocname;
    }
    
    public void setRefdocname(String refdocname) {
        this.refdocname = refdocname;
    }

    public Blob getRefdoc() {
        return this.refdoc;
    }
    
    public void setRefdoc(Blob refdoc) {
        this.refdoc = refdoc;
    }

    public int getIsactive() {
        return this.isactive;
    }
    
 

    public TmEmployee getCreatedby() {
		return createdby;
	}

	public void setCreatedby(TmEmployee createdby) {
		this.createdby = createdby;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
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

	/**
	 * @return the prev_doc_id
	 */
	public long getPrev_doc_id() {
		return prev_doc_id;
	}

	/**
	 * @param prev_doc_id the prev_doc_id to set
	 */
	public void setPrev_doc_id(long prev_doc_id) {
		this.prev_doc_id = prev_doc_id;
	}

	public String getActualRefDocName() {
		return actualRefDocName;
	}

	public void setActualRefDocName(String actualRefDocName) {
		this.actualRefDocName = actualRefDocName;
	}
   








}
