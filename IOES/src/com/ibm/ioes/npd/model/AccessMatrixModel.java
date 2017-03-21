package com.ibm.ioes.npd.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.forms.UserAccessMatrixDto;
import com.ibm.ioes.npd.hibernate.beans.AccessMatrixDto;
import com.ibm.ioes.npd.hibernate.beans.RoleSelectionDto;
import com.ibm.ioes.npd.hibernate.dao.AccessMatrixDao;
import com.ibm.ioes.npd.hibernate.dao.RoleSelectionDao;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class AccessMatrixModel extends CommonBaseModel
{
	
	public UserAccessMatrixDto getFinalRoleList(UserAccessMatrixDto objDto) throws Exception  
	{
		ArrayList<UserAccessMatrixDto> activeDeactivatedRoles = new ArrayList<UserAccessMatrixDto>();
		AccessMatrixDao objDao = new AccessMatrixDao();
		try
		{
			activeDeactivatedRoles = objDao.getActiveDeactivatedRoles(objDto);// having list of obj having all data against the employe id
			UserAccessMatrixDto obj21=activeDeactivatedRoles.get(0);
			objDto.setUserId(obj21.getUserId());
			objDto.setUserName(obj21.getFIRSTNAME()+" "+obj21.getLASTNAME());
			List<String> lListAssignedRole = Arrays.asList(objDto.getAssignedList());// list having assigned roles id
			//HashSet<String> lListAssignedRole = new HashSet<String>(lListAssignedRoleArrays);
			List<String> lListAssignedRoleName = Arrays.asList(objDto.getAssignedListRoleName());// list having assigned roles name
			//	List<String> lListNonAssignedRole =Arrays.asList(objDto.getNonAssignedList());//list having non assigned roles --no need to check we have to do update by default
			ArrayList<List<String>> listOfInsert = new ArrayList<List<String>>();
			ArrayList<List<String>> listOfUpdate = new ArrayList<List<String>>();
			List<String> tempListRoleID=new ArrayList<String>();
			HashSet<String> tempListRoleIDSet = new HashSet<String>(tempListRoleID);
			for(int i=0;i<activeDeactivatedRoles.size();i++){
				UserAccessMatrixDto obj=activeDeactivatedRoles.get(i);
				tempListRoleIDSet.add(""+obj.getRoleId());
			}
			
				for(int j=0;j<lListAssignedRole.size();j++){
					if (tempListRoleIDSet.contains(lListAssignedRole.get(j))) {	
						List<String> l=new ArrayList<String>();
						l.add(""+lListAssignedRole.get(j));
						l.add(""+lListAssignedRoleName.get(j));
						listOfUpdate.add(l); // update
					}else {
						List<String> l=new ArrayList<String>();
						l.add(""+lListAssignedRole.get(j));
						l.add(""+lListAssignedRoleName.get(j));
						listOfInsert.add(l); // insert
					}
				}
			
			objDto.setListOfInsert(listOfInsert);
			objDto.setListOfUpdate(listOfUpdate);
		}
		catch(Exception ex)
		{
			Utility.LOG(true, true, ex, "error in getFinalRoleList method");
		}
		return objDto;
	}
	
	
	public ArrayList<AccessMatrixDto> getRoleList(AccessMatrixDto objDto) throws Exception  
	{
		ArrayList<AccessMatrixDto> listRole = new ArrayList<AccessMatrixDto>();
		AccessMatrixDao objDao = new AccessMatrixDao();
		try
		{
			listRole = objDao.getRoleList(objDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listRole;
	}
	
	public ArrayList<AccessMatrixDto> getInterfaceList(String roleID,String moduleID) throws Exception  
	{
		ArrayList<AccessMatrixDto> listIntefrace = new ArrayList<AccessMatrixDto>();
		AccessMatrixDao objDao = new AccessMatrixDao();
		try
		{
			listIntefrace = objDao.getinterfaceList(roleID,moduleID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listIntefrace;
	}
	
	public int setUserMappedInterfaces(ArrayList allowedInterfaceId, ArrayList prohibitedInterfaceId,String roleID) throws Exception  
	{
		int listIntefrace = 0;
		AccessMatrixDao objDao = new AccessMatrixDao();
		try
		{
			listIntefrace = objDao.setUserMappedInterfaces(allowedInterfaceId,prohibitedInterfaceId,roleID);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listIntefrace;
	}

	public ArrayList<AccessMatrixDto> viewModuleList() throws Exception  
	{
		ArrayList<AccessMatrixDto> listModule = new ArrayList<AccessMatrixDto>();
		AccessMatrixDao objDao = new AccessMatrixDao();
		try
		{
			listModule = objDao.getModuleList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listModule;
	}



	public String updateRolesOnSubmit(UserAccessMatrixDto objDto) throws Exception
	{
		String result = null;
		String resultForCustSeg = null;
		NewOrderDao objDao = new NewOrderDao();
		Connection conn = DbConnection.getConnectionObject();
		conn.setAutoCommit(false);
		try {
			AccessMatrixModel getList=new AccessMatrixModel();
			if(objDto.getAssignedList().length>0 || objDto.getNonAssignedList().length>0){
				
				UserAccessMatrixDto objFinalList=getList.getFinalRoleList(objDto);
				result= objDao.updateUserRolesOnSubmit(objFinalList,conn);

			}
			if(objDto.getCustomerSegmentId()!=-1){
			    resultForCustSeg=objDao.updateOnlyCustomerSegmentOnSubmit(objDto.getOldCustSegmentName(),objDto.getCus_segment(),""+objDto.getCustomerSegmentId(),
						objDto.getEmpId(),objDto.getModifiedByUserId(),objDto.getModifiedByUserName(),conn);
				
			}
			if(("success".equals(result) || result==null ) && ("success".equals(resultForCustSeg) || resultForCustSeg==null)){
				conn.commit();
				result="success";
			}
			
			if("failure".equals(result) || "failure".equals(resultForCustSeg)){
				conn.rollback();
				result="failure";
			}
			
			
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			Utility.LOG(true, true,"Some Error Occured in updateRolesOnSubmit method of AccessMatrixModel "+e);
			result="failure";
		}
		

		finally
			{
				try
				{
					DbConnection.freeConnection(conn);
				} 
				catch (Exception e) 
				{
					Utility.LOG(true, true,"Some Error Occured in updateRolesOnSubmit method of AccessMatrixModel "+e);
				}
			}
		
		return result;
	}
	
}
