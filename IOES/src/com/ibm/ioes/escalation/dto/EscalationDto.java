
package com.ibm.ioes.escalation.dto;

import java.io.Serializable;
import java.util.Set;


public class EscalationDto {

	
	
	
	//private static final long serialVersionUID = 1L;
	private int ID;
	private String ROLE_ID;
	private String CUSTOMER_SEGMENT_ID;
	private String REGION_ID;
	private String BUSINESS_SEGMENT;
	private String RM1_AGING;
	private String RM1_MAILID;
	private String RM2_AGING;
	private String RM2_MAILID;
	private String RM3_AGING;
	private String RM3_MAILID;
	private String CUSTOMER_SEGMENT;
	private String CREATED_BY;
	private String bussSegmentSet;
	private int isUpdatedLevel1;
	private int isUpdatedLevel2;
	private int isUpdatedLevel3;
	
	 
	public int getID() {
			return ID;
		}
		public void setID(int ID) {
			this.ID = ID;
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
	
	
	public String getCUSTOMER_SEGMENT_ID() {
		return CUSTOMER_SEGMENT_ID;
	}
	public void setCUSTOMER_SEGMENT_ID(String customer_segment_id) {
		CUSTOMER_SEGMENT_ID = customer_segment_id;
	}
	public String getREGION_ID() {
		return REGION_ID;
	}
	public void setREGION_ID(String region_id) {
		REGION_ID = region_id;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String role_id) {
		ROLE_ID = role_id;
	}
	
	public String getCUSTOMER_SEGMENT() {
		return CUSTOMER_SEGMENT;
	}
	public void setCUSTOMER_SEGMENT(String customer_segment) {
		CUSTOMER_SEGMENT = customer_segment;
	}
	
	
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String created_by) {
		CREATED_BY = created_by;
	}
	public String getBussSegmentSet() {
		return bussSegmentSet;
	}
	public void setBussSegmentSet(String bussSegmentSet) {
		this.bussSegmentSet = bussSegmentSet;
	}
	public int getIsUpdatedLevel1() {
		return isUpdatedLevel1;
	}
	public void setIsUpdatedLevel1(int isUpdatedLevel1) {
		this.isUpdatedLevel1 = isUpdatedLevel1;
	}
	public int getIsUpdatedLevel2() {
		return isUpdatedLevel2;
	}
	public void setIsUpdatedLevel2(int isUpdatedLevel2) {
		this.isUpdatedLevel2 = isUpdatedLevel2;
	}
	public int getIsUpdatedLevel3() {
		return isUpdatedLevel3;
	}
	public void setIsUpdatedLevel3(int isUpdatedLevel3) {
		this.isUpdatedLevel3 = isUpdatedLevel3;
	}
	
		
	
}
	
	
