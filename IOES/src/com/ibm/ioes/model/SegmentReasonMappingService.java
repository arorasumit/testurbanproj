package com.ibm.ioes.model;
//Tag Name     Resource Name  Date		 CSR No					  Description -->
//          NANCY SINGLA	09-MAY-16   20160301-XX-022139       New Service Layer For Assign/Deassign Standard Reasons  -->
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.newdesign.dto.ChangeReasonMapping;
import com.ibm.ioes.newdesign.dto.StandardReason;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class SegmentReasonMappingService {
	NewOrderDao objdao = new NewOrderDao();
	public static class SegmentReasonMappingDto
	{

		private ArrayList<StandardReason> assignedChangeReasons=new ArrayList<StandardReason>();
		private ArrayList<StandardReason> unassignedChangeReasons = new ArrayList<StandardReason>();
		public ArrayList<StandardReason> getAssignedChangeReasons() 
		{
			return assignedChangeReasons;
		}
		public void setAssignedChangeReasons(ArrayList<StandardReason> assignedChangeReasons) 
		{
			this.assignedChangeReasons = assignedChangeReasons;
		}
		public ArrayList<StandardReason> getUnassignedChangeReasons() 
		{
			return unassignedChangeReasons;
		}
		public void setUnassignedChangeReasons(ArrayList<StandardReason> unassignedChangeReasons)
		{
			this.unassignedChangeReasons = unassignedChangeReasons;
		}


	}

	public SegmentReasonMappingDto getAssignedUnassignedReasons(long custSegment, long subChangeType) throws Exception
	{

		ArrayList<StandardReason> getAssignedReasons = null;
		ArrayList<StandardReason> getAllReasons =null;
		ArrayList<StandardReason> getUnAssignedReasons=new ArrayList<StandardReason>();
		StandardReason stdrsn = null;
		SegmentReasonMappingDto mappingDto=new SegmentReasonMappingDto();
		getAssignedReasons=objdao.getAssignedReasons(custSegment,subChangeType );
		getAllReasons= objdao.getAllReasons();

		if (getAssignedReasons.isEmpty()|| getAssignedReasons.size()<=0)
		{
			mappingDto.setUnassignedChangeReasons(getAllReasons);
			return mappingDto;	
		}
		else
		{

			mappingDto.setAssignedChangeReasons(getAssignedReasons);
			HashMap<Long,StandardReason> hshMap= new HashMap<Long,StandardReason>();
			for(int k=0;k<getAssignedReasons.size();k++)
			{
				stdrsn= new StandardReason();
				stdrsn.setStdReasonId(getAssignedReasons.get(k).getStdReasonId());
				stdrsn.setStdReasonName(getAssignedReasons.get(k).getStdReasonName());
				hshMap.put(getAssignedReasons.get(k).getStdReasonId(),stdrsn );
			}

			for(int i=0; i<getAllReasons.size() ; i++)
			{
				Long getAllReasonId=getAllReasons.get(i).getStdReasonId();
				stdrsn= new StandardReason();

				if(!(hshMap.containsKey(getAllReasonId)))
				{

					stdrsn.setStdReasonId(getAllReasons.get(i).getStdReasonId());
					stdrsn.setStdReasonName(getAllReasons.get(i).getStdReasonName());
					getUnAssignedReasons.add(stdrsn);
				}

			}
			if  (!(getUnAssignedReasons.isEmpty()) || !(getUnAssignedReasons.size() <=0))
			{

				mappingDto.setUnassignedChangeReasons(getUnAssignedReasons);	

			}
			return mappingDto;
		}
	}


	public String updateStandardReasonMapping(ChangeReasonMapping objDto,long[] assignedReasonsIds)throws Exception
	{
		Connection connection =null;
		String finalResult=null;
		int delCountResult=0;
		int insertStatusResult=0;
		long[] assignedids = assignedReasonsIds;
		long custsegm= objDto.getCustomerSegmentId();
		long subchgtype= objDto.getSubChangeTypeId();
		try
		{
			connection=DbConnection.getConnectionObject();
			connection.setAutoCommit(false);

			delCountResult=objdao.deleteAssignedReasons(custsegm,subchgtype,connection);

			if (delCountResult>=0)
			{
				if(!(assignedids.length ==0))
				{
					insertStatusResult=objdao.insertAssignedReasons(custsegm,subchgtype,assignedids,connection);
				}
			}

			if(delCountResult>=0 && (assignedids.length ==0 || insertStatusResult==1)){
				finalResult= "success";
				connection.commit();
			}else{
				finalResult="failure";
				connection.rollback();
			}



			/*if (!(insertStatusResult==-1))
			{
				if (delCountResult>0 || insertStatusResult==1)
				{
					finalResult= "success";
					connection.commit();

				}
				else
				{
					finalResult="failure";
					connection.rollback();
				}
			}
			else
			{
				finalResult="failure";
				connection.rollback();
			}*/

		}
		catch(Exception ex)
		{
			try {
				connection.rollback();
				finalResult="failure";
			} catch (Exception e) {
				Utility.LOG(true, true, ex, "in updateReason method of SegmentReasonMappingService");
				//e.printStackTrace();
			}
			ex.printStackTrace();	
		}finally{
			try{
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e){
				Utility.LOG(true, true,e,"in updateReason method of SegmentReasonMappingService");
			}
		}
		return finalResult;
	}

}
