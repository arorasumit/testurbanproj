package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ibm.ioes.demodays.dto.DemoDaysMailAlertDto;

import com.ibm.ioes.model.DemoDaysMailAlertModel;
import com.ibm.ioes.utilities.DbConnection;

public class DemoDaysMailAlertDao {
	//public static String sqlGetPOEntity1= "{call IOE.SP_GET_ENTITY1()}";
	//SP_MAIL_ALERT_INFO
	//static String sqlGetSaleNature="call IOE.SP_GET_SALENATURE()";
	public static String sqlGetActMgrDetailsDemoOrder = "{call IOE.GET_ACTMAGRID_FOR_DEMOORDERDS(?)}";
	public static String sqlDemoDaysMailAlertDetails="{call IOE.GET_DEMODAYSMAILALERT(?,?)}";
	public ArrayList<DemoDaysMailAlertDto> getActmgrDetailsforDemoOrder(Connection connection,String flag) 
	{
		CallableStatement getdemodaysDetails =null;
		ResultSet rsdemoalert = null;
		ArrayList<DemoDaysMailAlertDto> demodaysList = new ArrayList<DemoDaysMailAlertDto>();
		DemoDaysMailAlertDto objdemodaysDTO = null;
		
		try
		{
			getdemodaysDetails= connection.prepareCall(sqlGetActMgrDetailsDemoOrder);
			getdemodaysDetails.setString(1,flag);
			rsdemoalert = getdemodaysDetails.executeQuery();
			while(rsdemoalert.next())
			{
				
				objdemodaysDTO =  new DemoDaysMailAlertDto();
				objdemodaysDTO.setAm_emailid(rsdemoalert.getString("EMAIL_AM"));
				objdemodaysDTO.setPm_emailid(rsdemoalert.getString("EMAIL_PM"));
				objdemodaysDTO.setColMgr_emailid(rsdemoalert.getString("COLMGR_EMAILID"));
				objdemodaysDTO.setActmgrid(rsdemoalert.getString("ACCOUNTMGRID"));
				demodaysList.add(objdemodaysDTO);
			}
		}
		catch(Exception ex )
		{
			ex.printStackTrace();	
		}
		finally
		{
			try 
			{
				DbConnection.closeResultset(rsdemoalert);
				DbConnection.closeCallableStatement(getdemodaysDetails);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return demodaysList;
	}	

	/*public static void main(String args[]){
		DemoDaysMailAlertDao ddam=new DemoDaysMailAlertDao();
		Connection conn=null;
		try{
			
			ddam.getDemoDaysMailAlert(conn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}*/
	
	

		public ArrayList<DemoDaysMailAlertDto> getDemoDaysMailAlertDetails(Connection connection,String actmgrid,String flag) 
		{
			CallableStatement getdemodaysDet =null;
			ResultSet rsdemo = null;
			ArrayList<DemoDaysMailAlertDto> demodaysdetList = new ArrayList<DemoDaysMailAlertDto>();
			DemoDaysMailAlertDto objdemodaysDTO = null;
			
			try
			{
				getdemodaysDet= connection.prepareCall(sqlDemoDaysMailAlertDetails);
				getdemodaysDet.setString(1,actmgrid);
				getdemodaysDet.setString(2,flag);
				
				rsdemo = getdemodaysDet.executeQuery();
				while(rsdemo.next()){
					objdemodaysDTO =  new DemoDaysMailAlertDto();	
					objdemodaysDTO.setDemo_end_date(rsdemo.getString("DEMO_END_DATE"));
					objdemodaysDTO.setLogical_si_no(rsdemo.getString("LOGICAL_SI_NO"));
					objdemodaysDTO.setOrderno(rsdemo.getString("ORDERNO"));
					objdemodaysDTO.setAccountname(rsdemo.getString("ACCOUNTNAME"));
					objdemodaysDTO.setAccountno(rsdemo.getString("ACCOUNTNO"));
					demodaysdetList.add(objdemodaysDTO);
					
					System.out.println("DEMO_END_DATE  :"+objdemodaysDTO.getDemo_end_date());
					
					System.out.println("ACCOUNTNAME   :"+objdemodaysDTO.getAccountname());
					System.out.println("ACCOUNTNO  :"+objdemodaysDTO.getAccountno());
					System.out.println("lsi:"+objdemodaysDTO.getLogical_si_no());
					System.out.println("orderno :"+objdemodaysDTO.getOrderno());
				}

			}
			catch(Exception ex )
			{
				ex.printStackTrace();	
			}
			finally
			{
				try 
				{
					DbConnection.closeResultset(rsdemo);
					DbConnection.closeCallableStatement(getdemodaysDet);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return demodaysdetList;
		}	
		
}
