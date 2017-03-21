package com.ibm.ioes.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;



public class EscalationBean extends ActionForm{
	
	 public int ID;
	 public String ROLE_NAME;
	 public String BUSINESS_SEGMENT;
	 public String RM1_AGING;
	 public String RM1_MAILID;
	 public String RM2_AGING;
	 public String RM2_MAILID;
	 public String RM3_AGING;
	 public String RM3_MAILID;
	 public String CREATED_BY;
	 public String REGION;
	// public List<EscalationDto> escalation;
	 
	 public int getID() {
			return ID;
		}
		public void setID(int ID) {
			this.ID = ID;
		}
	 
	 public String getROLE_NAME() {
			return ROLE_NAME;
		}
		public void setROLE_NAME(String role_name) {
			ROLE_NAME = role_name;
		}
	public String getBUSINESS_SEGMENT() {
		return BUSINESS_SEGMENT;
	}
	public void setBUSINESS_SEGMENT(String business_segment) {
		BUSINESS_SEGMENT = business_segment;
	}
	
	public String getRM1_AGING() {
		return RM1_AGING;
	}
	public void setRM1_AGING(String rm1_aging) {
		RM1_AGING = rm1_aging;
	}
	public String getRM1_MAILID() {
		return RM1_MAILID;
	}
	public void setRM1_MAILID(String rm1_mailid) {
		RM1_MAILID = rm1_mailid;
	}
	public String getRM2_AGING() {
		return RM2_AGING;
	}
	public void setRM2_AGING(String rm2_aging) {
		RM2_AGING = rm2_aging;
	}
	public String getRM2_MAILID() {
		return RM2_MAILID;
	}
	public void setRM2_MAILID(String rm2_mailid) {
		RM2_MAILID = rm2_mailid;
	}
	public String getRM3_AGING() {
		return RM3_AGING;
	}
	public void setRM3_AGING(String rm3_aging) {
		RM3_AGING = rm3_aging;
	}
	public String getRM3_MAILID() {
		return RM3_MAILID;
	}
	public void setRM3_MAILID(String rm3_mailid) {
		RM3_MAILID = rm3_mailid;
	}
	
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String created_by) {
		CREATED_BY = created_by;
	}
	public String getREGION() {
		return REGION;
	}
	public void setREGION(String region) {
		REGION = region;
	}
	public void setEscalation(List viewEscalations) {
		// TODO Auto-generated method stub
		
	}
	
	
	


}
