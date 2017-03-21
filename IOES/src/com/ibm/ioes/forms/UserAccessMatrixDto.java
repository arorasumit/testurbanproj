package com.ibm.ioes.forms;

import java.util.ArrayList;
import java.util.List;

import com.ibm.ioes.utilities.PagingSorting;

public class UserAccessMatrixDto {
	PagingSorting pagingSorting = new PagingSorting();
	private String userId;
	private String userName;
	private long roleId;
	private String roleName;
	private long empId;
	private int isRoleAssigned;
	private String toCOPCDate;
	private String modifiedByUserId;
	private String modifiedByUserName;	
	private String fromCOPCDate;
	private String cus_segment;
	private Integer customerSegmentId;
	private String oldCustSegmentName;
	private ArrayList<UserAccessMatrixDto> getUserAccessList;
	private ArrayList<UserAccessMatrixDto> getMappedCustomerSegmentWithUserId;
	private String accessOrDenied;
	private String[] assignedList;
	private String[] nonAssignedList;
	private String[] assignedListRoleName;
	private String[] nonAssignedListRoleName;
	private String FIRSTNAME;
	private String LASTNAME;
	private String PHONENO;
	private String EMAILID;
	private String PHONE_NO;
	private String LOGIN_IP;
	
	public ArrayList<UserAccessMatrixDto> getGetUserAccessList() {
		return getUserAccessList;
	}
	public void setGetUserAccessList(
			ArrayList<UserAccessMatrixDto> getUserAccessList) {
		this.getUserAccessList = getUserAccessList;
	}
	public ArrayList<UserAccessMatrixDto> getGetMappedCustomerSegmentWithUserId() {
		return getMappedCustomerSegmentWithUserId;
	}
	public void setGetMappedCustomerSegmentWithUserId(
			ArrayList<UserAccessMatrixDto> getMappedCustomerSegmentWithUserId) {
		this.getMappedCustomerSegmentWithUserId = getMappedCustomerSegmentWithUserId;
	}
	private long srno;
	
	
	public long getSrno() {
		return srno;
	}
	public void setSrno(long srno) {
		this.srno = srno;
	}
	private String dateOfmofification;
	public String[] getNonAssignedList() {
		return nonAssignedList;
	}
	public String[] getAssignedListRoleName() {
		return assignedListRoleName;
	}
	public void setAssignedListRoleName(String[] assignedListRoleName) {
		this.assignedListRoleName = assignedListRoleName;
	}
	public void setNonAssignedList(String[] nonAssignedList) {
		this.nonAssignedList = nonAssignedList;
	}
	
	public String[] getNonAssignedListRoleName() {
		return nonAssignedListRoleName;
	}
	public void setNonAssignedListRoleName(String[] nonAssignedListRoleName) {
		this.nonAssignedListRoleName = nonAssignedListRoleName;
	}
	
	public String getIS_ACTIVE() {
		return IS_ACTIVE;
	}
	public void setIS_ACTIVE(String iS_ACTIVE) {
		IS_ACTIVE = iS_ACTIVE;
	}
	private String LOGIN_STATUS;
	
	private String IS_ACTIVE;
	
	private String LAST_ROLE_ACCESSED;
	private String LAST_LOGGED_IN_TIME;
	private String LAST_LOGGED_OUT_TIME;
	private String EMPLOYEENO;
	private String CREATED_BY;
	private String CREATED_DATE;
	private String UPDATED_DATE;
	private String ORDER_NO;
	private String GAM_START_DATE;
	private String GAM_END_DATE;
	private ArrayList<List<String>> listOfInsert;
	private ArrayList<List<String>> listOfUpdate;
	
	
	public ArrayList<List<String>> getListOfInsert() {
		return listOfInsert;
	}
	public void setListOfInsert(ArrayList<List<String>> listOfInsert) {
		this.listOfInsert = listOfInsert;
	}
	public ArrayList<List<String>> getListOfUpdate() {
		return listOfUpdate;
	}
	public void setListOfUpdate(ArrayList<List<String>> listOfUpdate) {
		this.listOfUpdate = listOfUpdate;
	}
	public String getFIRSTNAME() {
		return FIRSTNAME;
	}
	public void setFIRSTNAME(String fIRSTNAME) {
		FIRSTNAME = fIRSTNAME;
	}
	public String getLASTNAME() {
		return LASTNAME;
	}
	public void setLASTNAME(String lASTNAME) {
		LASTNAME = lASTNAME;
	}
	public String getPHONENO() {
		return PHONENO;
	}
	public void setPHONENO(String pHONENO) {
		PHONENO = pHONENO;
	}
	public String getEMAILID() {
		return EMAILID;
	}
	public void setEMAILID(String eMAILID) {
		EMAILID = eMAILID;
	}
	public String getPHONE_NO() {
		return PHONE_NO;
	}
	public void setPHONE_NO(String pHONE_NO) {
		PHONE_NO = pHONE_NO;
	}
	public String getLOGIN_IP() {
		return LOGIN_IP;
	}
	public void setLOGIN_IP(String lOGIN_IP) {
		LOGIN_IP = lOGIN_IP;
	}
	public String getLOGIN_STATUS() {
		return LOGIN_STATUS;
	}
	public void setLOGIN_STATUS(String lOGIN_STATUS) {
		LOGIN_STATUS = lOGIN_STATUS;
	}
	public String getLAST_ROLE_ACCESSED() {
		return LAST_ROLE_ACCESSED;
	}
	public void setLAST_ROLE_ACCESSED(String lAST_ROLE_ACCESSED) {
		LAST_ROLE_ACCESSED = lAST_ROLE_ACCESSED;
	}
	public String getLAST_LOGGED_IN_TIME() {
		return LAST_LOGGED_IN_TIME;
	}
	public void setLAST_LOGGED_IN_TIME(String lAST_LOGGED_IN_TIME) {
		LAST_LOGGED_IN_TIME = lAST_LOGGED_IN_TIME;
	}
	public String getLAST_LOGGED_OUT_TIME() {
		return LAST_LOGGED_OUT_TIME;
	}
	public void setLAST_LOGGED_OUT_TIME(String lAST_LOGGED_OUT_TIME) {
		LAST_LOGGED_OUT_TIME = lAST_LOGGED_OUT_TIME;
	}
	public String getEMPLOYEENO() {
		return EMPLOYEENO;
	}
	public void setEMPLOYEENO(String eMPLOYEENO) {
		EMPLOYEENO = eMPLOYEENO;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public String getUPDATED_DATE() {
		return UPDATED_DATE;
	}
	public void setUPDATED_DATE(String uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}
	public String getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(String oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}
	public String getGAM_START_DATE() {
		return GAM_START_DATE;
	}
	public void setGAM_START_DATE(String gAM_START_DATE) {
		GAM_START_DATE = gAM_START_DATE;
	}
	public String getGAM_END_DATE() {
		return GAM_END_DATE;
	}
	public void setGAM_END_DATE(String gAM_END_DATE) {
		GAM_END_DATE = gAM_END_DATE;
	}
	public String getL1_EMPLOYEEID() {
		return L1_EMPLOYEEID;
	}
	public void setL1_EMPLOYEEID(String l1_EMPLOYEEID) {
		L1_EMPLOYEEID = l1_EMPLOYEEID;
	}
	public String getL2_EMPLOYEEID() {
		return L2_EMPLOYEEID;
	}
	public void setL2_EMPLOYEEID(String l2_EMPLOYEEID) {
		L2_EMPLOYEEID = l2_EMPLOYEEID;
	}
	public String getL3_EMPLOYEEID() {
		return L3_EMPLOYEEID;
	}
	public void setL3_EMPLOYEEID(String l3_EMPLOYEEID) {
		L3_EMPLOYEEID = l3_EMPLOYEEID;
	}
	private String L1_EMPLOYEEID;
	private String L2_EMPLOYEEID;
	private String L3_EMPLOYEEID;
	
	public String getModifiedByUserId() {
		return modifiedByUserId;
	}
	public void setModifiedByUserId(String modifiedByUserId) {
		this.modifiedByUserId = modifiedByUserId;
	}
	public String getModifiedByUserName() {
		return modifiedByUserName;
	}
	public void setModifiedByUserName(String modifiedByUserName) {
		this.modifiedByUserName = modifiedByUserName;
	}
	public String getAccessOrDenied() {
		return accessOrDenied;
	}
	public void setAccessOrDenied(String accessOrDenied) {
		this.accessOrDenied = accessOrDenied;
	}
	public String getDateOfmofification() {
		return dateOfmofification;
	}
	public void setDateOfmofification(String dateOfmofification) {
		this.dateOfmofification = dateOfmofification;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getToCOPCDate() {
		return toCOPCDate;
	}
	public void setToCOPCDate(String toCOPCDate) {
		this.toCOPCDate = toCOPCDate;
	}
	public String getFromCOPCDate() {
		return fromCOPCDate;
	}
	public void setFromCOPCDate(String fromCOPCDate) {
		this.fromCOPCDate = fromCOPCDate;
	}
	public int getIsRoleAssigned() {
		return isRoleAssigned;
	}
	public void setIsRoleAssigned(int isRoleAssigned) {
		this.isRoleAssigned = isRoleAssigned;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String[] getAssignedList() {
		return assignedList;
	}
	public void setAssignedList(String[] assignedList) {
		this.assignedList = assignedList;
	}
	public String getCus_segment() {
		return cus_segment;
	}
	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}
	public Integer getCustomerSegmentId() {
		return customerSegmentId;
	}
	public void setCustomerSegmentId(Integer customerSegmentId) {
		this.customerSegmentId = customerSegmentId;
	}
	public String getOldCustSegmentName() {
		return oldCustSegmentName;
	}
	public void setOldCustSegmentName(String oldCustSegmentName) {
		this.oldCustSegmentName = oldCustSegmentName;
	}
	
	

}
