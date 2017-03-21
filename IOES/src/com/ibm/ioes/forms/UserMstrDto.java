/*****************************************************************************\
 **
 ** EDR Processor.
 **
 ** Licensed Material - Property of IBM
 ** (C) Copyright IBM Corp. 2008 - All Rights Reserved.
 **
 **
 \****************************************************************************/
package com.ibm.ioes.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public class UserMstrDto implements Serializable {

	private static final long serialVersionUID = 8627758272118844378L;
	
	private int userId;
	private String userType;
	private String password;
	private String loginId;
	private String circleId;
	private String userFName;
	private String userMName;
	private String userLName;
	private long userMobileNumber;
	private String userEMailId;
	private Date updatedDt = null;
	private String status;
	private String isLogin;
	private int loginAttempted;
	private Date createdDt = null;
	private int roleId ;
	private String isPassowrdReset ;
	private SessionObjectsDto sessiondto;
	private String isConfBlocked="0";
	private String acsID="";
	
	private String newPassword;

	private String oldPassword;
	
	private ArrayList ModuleList;
	
	private HashMap CategoryList;
	private HashMap ModuleData;
	private ArrayList userModuleUrl;
	private String global_path;
	
	private String companyName ;
	private String Name ;
	
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getGlobal_path() {
		return global_path;
	}
	public void setGlobal_path(String global_path) {
		this.global_path = global_path;
	}
	public HashMap getCategoryList() {
		return CategoryList;
	}
	public void setCategoryList(HashMap categoryList) {
		CategoryList = categoryList;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public Date getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}
	public String getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}
	public int getLoginAttempted() {
		return loginAttempted;
	}
	public void setLoginAttempted(int loginAttempted) {
		this.loginAttempted = loginAttempted;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public HashMap getModuleData() {
		return ModuleData;
	}
	public void setModuleData(HashMap moduleData) {
		ModuleData = moduleData;
	}
	public ArrayList getModuleList() {
		return ModuleList;
	}
	public void setModuleList(ArrayList moduleList) {
		ModuleList = moduleList;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}
	public String getUserEMailId() {
		return userEMailId;
	}
	public void setUserEMailId(String userEMailId) {
		this.userEMailId = userEMailId;
	}
	public String getUserFName() {
		return userFName;
	}
	public void setUserFName(String userFName) {
		this.userFName = userFName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserLName() {
		return userLName;
	}
	public void setUserLName(String userLName) {
		this.userLName = userLName;
	}
	public String getUserMName() {
		return userMName;
	}
	public void setUserMName(String userMName) {
		this.userMName = userMName;
	}
	public long getUserMobileNumber() {
		return userMobileNumber;
	}
	public void setUserMobileNumber(long userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}
	public ArrayList getUserModuleUrl() {
		return userModuleUrl;
	}
	public void setUserModuleUrl(ArrayList userModuleUrl) {
		this.userModuleUrl = userModuleUrl;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getIsPassowrdReset() {
		return isPassowrdReset;
	}
	public void setIsPassowrdReset(String isPassowrdReset) {
		this.isPassowrdReset = isPassowrdReset;
	}
	public SessionObjectsDto getSessiondto() {
		return sessiondto;
	}
	public void setSessiondto(SessionObjectsDto sessiondto) {
		this.sessiondto = sessiondto;
	}
	public String getIsConfBlocked() {
		return isConfBlocked;
	}
	public void setIsConfBlocked(String isConfBlocked) {
		this.isConfBlocked = isConfBlocked;
	}
	public String getAcsID() {
		return acsID;
	}
	public void setAcsID(String acsID) {
		this.acsID = acsID;
	}

	
	
	
	

	
	
	

}
