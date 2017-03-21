package com.ibm.ioes.npd.utilities;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.utilities.DbConnection;

public class ByPass {

	

	public boolean isValid(HttpServletRequest request, String roleid, Connection connection, TmEmployee tmEmployee) throws NpdException{
		// TODO Auto-generated method stub
		
		
		String requestedURI = request.getRequestURI();
		String requestMapping = requestedURI.substring(requestedURI
				.lastIndexOf("/") + 1);
		
		CallableStatement proc =null;
		ResultSet rs = null;
		CallableStatement cstmt =null;
		
		try
		{
		if("createProduct.do".equals(requestMapping))
		{
			String method=(String)request.getParameter("method");
			if(method==null)
			{
				return false;
			}
			
			
			 String businessPurpose="'000000'";
			  
			if("initProduct".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.CREATE_NEW_PRODUCT+"'";
			}
			else if("Save Project".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.CREATE_NEW_PRODUCT+"'";
			}
			else if("initSearchProduct".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.VIEW_PROJECTS+"'";
			}
			/*else if("searchProduct".equals(method))
			{
				//businessPurpose=BUSINESS_PURPOSE_CONSTANTS.;
			}*/
			else if("initUpdateProduct".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.PRODUCT_DETAILS_UPDATION+"'";
			}
			else if("initSearchProductFinalizeNewPlan".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.FINALISE_NEW_PLAN+"'";
			}
			else if("initSearchEditBaselinedPlan".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.EDIT_BASELINED_PLAN+"'";
			}
			else if("initSearchProductUpdation".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.PRODUCT_DETAILS_UPDATION+"'";
			}
			else if("saveUpdateProductDraft".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.PRODUCT_DETAILS_UPDATION+"'";
			}
			else if("viewUpdateProduct".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.ACTION_ON_PRD_UPDATION+"'";
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.PRODUCT_DETAILS_UPDATION+"'";
			}
			else if("initUpdateProductApproval".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.ACTION_ON_PRD_UPDATION+"'";
			}
			else if("approveRejectOverrideDraft".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.ACTION_ON_PRD_UPDATION+"'";
			}
			else if("initSearchUpdationAction".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.ACTION_ON_PRD_UPDATION+"'";
			}
			else if("viewProductPlanGraph".equals(method))
			{
				businessPurpose=businessPurpose+",'"+BUSINESS_PURPOSE_CONSTANTS.VIEW_PROJECTS+"'";
			}
			//
			
			
			String query="SELECT COUNT(1) AS isAuthorised FROM NPD.TM_ACCESSMATRIXMAPPING ";
			  query=query+ " WHERE INTERFACEID IN (SELECT INTERFACEID FROM ";
			  query=query+ " NPD.TM_INTERFACEMASTER WHERE BUSINESS_PURPOSE_ID IN ("+ businessPurpose +"))";
			  query=query+ " AND ACCESSFLAG=1 AND ROLEID = "+ roleid;

			  //System.err.println(query);
			  
			  proc=connection.prepareCall(query);
			  rs = proc.executeQuery();
			  while(rs.next())
			  {
				  if(rs.getInt("isAuthorised")>0)
				  {
					  return true; 
				  }
				  else
				  {
					  return false;
				  }
			  }
		}
		else if("attachEditProjectPlan.do".equals(requestMapping))
		{
			//fetch the projectId , then fetch its state 
			
			//if new and first dratf, only role with finalise new plan can access it and user can only be the one who has created it
			
			//if workflow is exist in finalize , then request with edit baseline can access 
				
			
				//not used :and if project's workflow is only one and that in finalized state  then one method:reopen
			
				//not used :and if project's workflow is draft then only that projectworkflow id
			
			
			String method=(String)request.getParameter("method");
			if(method==null)
			{
				return false;
			}
			if("viewProductPlanVersions".equals(method))
			{
				String query="SELECT COUNT(1) AS isAuthorised FROM NPD.TM_ACCESSMATRIXMAPPING ";
				  query=query+ " WHERE INTERFACEID IN (SELECT INTERFACEID FROM ";
				  query=query+ " NPD.TM_INTERFACEMASTER WHERE BUSINESS_PURPOSE_ID IN ('VIEW_PROJECTS','EDIT_BASELINED_PLAN','FINALISE_NEW_PLAN'))";
				  query=query+ " AND ACCESSFLAG=1 AND ROLEID = "+ roleid;

				//  System.err.println(query);
				  
				  proc=connection.prepareCall(query);
				  rs = proc.executeQuery();
				  while(rs.next())
				  {
					  if(rs.getInt("isAuthorised")>0)
					  {
						  return true; 
					  }
					  else
					  {
					  		System.err.println(query);
						  return false;
					  }
				  }
				
				return true;
			}
			String projectId=(String)request.getParameter("projectId");
			if(projectId==null)
			{
				return false;
			}
			
			String projectWorkflowId=(String)request.getParameter("projectWorkflowId");
			
			cstmt= connection.prepareCall(
						"{call NPD.VALIDATE_ACCESS_WORKFLOW(?,?,?,?,?,?,?,?)}");
			
			cstmt.setLong(1, Long.parseLong(projectId));
			cstmt.setLong(2, Long.parseLong(roleid));
			cstmt.setLong(3, tmEmployee.getNpdempid());
			cstmt.setString(4, "openFinalizedAsDraft");
			if(projectWorkflowId==null || "".equals(projectWorkflowId))
			{
				cstmt.setNull(5, java.sql.Types.BIGINT);	
			}
			else
			{
				cstmt.setLong(5, Long.parseLong(projectWorkflowId));	
			}
			
			cstmt.setLong(6, new Long(0));
			cstmt.setString(7, "");
			cstmt.setString(8, "");
			
			boolean b=cstmt.execute();
			
			Long status=cstmt.getLong(6);
			if(status==1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new NpdException(ex);
		}
		try
		{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(proc);
			DbConnection.closeCallableStatement(cstmt);
		}
		catch(Exception ex)
		{
			
		}
		return true;
	}
	
}
