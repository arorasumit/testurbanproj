package com.ibm.ioes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ibm.ioes.demodays.dto.DemoDaysMailAlertDto;

import com.ibm.ioes.model.DemoDaysMailAlertModel;
import com.ibm.ioes.newdesign.dto.PMWelcomeMailDto;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Utility;

public class PMWelcomeMailDao {
	
	/*
	 * Account Manager Details 
	 */
	public static String sqlGetPrjctMgrMailids = "{call IOE.SP_GET_PM_WELCOMELETTER_MAILS(?)}";
	public static String sqlPMWelcomeMailDetails=  "{call IOE.GET_PM_WELCOMEMAIL_PM_DETAILS(?)}";
	/*
	 * Approver Manager Details 
	 */
	//public static String sqlGetPrjctMgrMailids = "{call IOE.SP_GET_PM_WELCOMEMAIL(?)}";
	//public static String sqlPMWelcomeMailDetails="{call IOE.GET_PM_WELCOMEMAIL_DETAILS(?)}";
	
	public ArrayList<PMWelcomeMailDto> getPrjctmgrDetailsforWelcomemail(Connection connection,long flag) 
	{
		CallableStatement getpmwelcomemailDetails =null;
		ResultSet rspmmailalert = null;
		ArrayList<PMWelcomeMailDto> demodaysList = new ArrayList<PMWelcomeMailDto>();
		PMWelcomeMailDto objpmmailDTO = null;
		//Utility utility=new Utility();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			getpmwelcomemailDetails= connection.prepareCall(sqlGetPrjctMgrMailids);
			getpmwelcomemailDetails.setLong(1,flag);
			rspmmailalert = getpmwelcomemailDetails.executeQuery();
			while(rspmmailalert.next())
			{
				
				objpmmailDTO =  new PMWelcomeMailDto();
				
				objpmmailDTO.setPm_emailid(rspmmailalert.getString("EMAIL_PM"));
				//objpmmailDTO.setActmgrid(rspmmailalert.getString("PROJECTMGRID"));
				objpmmailDTO.setContactname(rspmmailalert.getString("CONTACTFNAME")+" "+rspmmailalert.getString("CONTACTLNAME"));
				objpmmailDTO.setPmname(rspmmailalert.getString("PM_FNAME")+rspmmailalert.getString("PM_LNAME"));
				objpmmailDTO.setRefNo(rspmmailalert.getString("REFNO"));
				objpmmailDTO.setOrderdate(rspmmailalert.getString("ORDER_CREATEDDATE"));
				if (rspmmailalert.getString("ORDER_CREATEDDATE") != null && !"".equals(rspmmailalert.getString("ORDER_CREATEDDATE"))) 
				{
					//objpmmailDTO.setOrderdate((Utility.showDate_Report2(rspmmailalert.getString("ORDER_CREATEDDATE").toString())).toUpperCase());
					Date date=df.parse(objpmmailDTO.getOrderdate());
					objpmmailDTO.setOrderdate(sdf.format(date.getTime()).toUpperCase());
					
				}
				
				demodaysList.add(objpmmailDTO);
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
				DbConnection.closeResultset(rspmmailalert);
				DbConnection.closeCallableStatement(getpmwelcomemailDetails);
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
	
	

		public ArrayList<PMWelcomeMailDto> getPMWelcomeMailAlertDetails(Connection connection,String actmgrid,long flag) 
		{
			CallableStatement getmailDet =null;
			ResultSet rsmail = null;
			ArrayList<PMWelcomeMailDto> demodaysdetList = new ArrayList<PMWelcomeMailDto>();
			PMWelcomeMailDto objpmwelcomemailDTO = null;
			
			try
			{
				getmailDet= connection.prepareCall(sqlPMWelcomeMailDetails);
				getmailDet.setLong(1,flag);
				
				
				rsmail = getmailDet.executeQuery();
				while(rsmail.next()){
					objpmwelcomemailDTO =  new PMWelcomeMailDto();	
					if(rsmail.getString("PM_FNAME")==null && rsmail.getString("PM_LNAME")==null)
					{
						objpmwelcomemailDTO.setName(" ");
					}else{
					objpmwelcomemailDTO.setName(rsmail.getString("PM_FNAME")+rsmail.getString("PM_LNAME"));
					}
					objpmwelcomemailDTO.setAddress("NA");
					if (rsmail.getString("EMAIL_PM")!= null){
					objpmwelcomemailDTO.setEmail(rsmail.getString("EMAIL_PM"));
					}else{objpmwelcomemailDTO.setEmail(" ");}
					
					if (rsmail.getString("PHONE_NO")!= null){
					objpmwelcomemailDTO.setContactno(rsmail.getString("PHONE_NO"));
					}else
					{
						objpmwelcomemailDTO.setContactno("");
					}
					objpmwelcomemailDTO.setFaxno("00");
					
					if(rsmail.getString("L1_FNAME") ==null && rsmail.getString("L1_LNAME")==null){
						objpmwelcomemailDTO.setL1name(" ");
					}else{
					objpmwelcomemailDTO.setL1name(rsmail.getString("L1_FNAME")+rsmail.getString("L1_LNAME"));
					}
					if (rsmail.getString("L1_PHONENO") != null ){
						objpmwelcomemailDTO.setL1contactno(rsmail.getString("L1_PHONENO"));
					}else{objpmwelcomemailDTO.setL1contactno(" ");}
					
					if(rsmail.getString("L1_EMAILID")==null){
						objpmwelcomemailDTO.setL1email(" ");
					}else{
					objpmwelcomemailDTO.setL1email(rsmail.getString("L1_EMAILID"));
					}
					
					if(rsmail.getString("L2_FNAME") ==null && rsmail.getString("L2_LNAME")==null){
						objpmwelcomemailDTO.setL2name(" ");
					}else{
					objpmwelcomemailDTO.setL2name(rsmail.getString("L2_FNAME")+rsmail.getString("L2_LNAME"));
					}
					
					if (rsmail.getString("L2_PHONENO") != null ){
					objpmwelcomemailDTO.setL2contactno(rsmail.getString("L2_PHONENO"));
					}else{objpmwelcomemailDTO.setL2contactno(" ");}
					
					
					if(rsmail.getString("L2_EMAILID")==null){
						objpmwelcomemailDTO.setL2email(" ");
					}else{
					objpmwelcomemailDTO.setL2email(rsmail.getString("L2_EMAILID"));
					}
					
					if(rsmail.getString("L3_FNAME") ==null && rsmail.getString("L3_LNAME")==null){
						objpmwelcomemailDTO.setL3name(" ");
					}else{
					objpmwelcomemailDTO.setL3name(rsmail.getString("L3_FNAME")+rsmail.getString("L3_LNAME"));
					}
					if (rsmail.getString("L3_PHONENO") != null ){
					objpmwelcomemailDTO.setL3contactno(rsmail.getString("L3_PHONENO"));
					}else{
						objpmwelcomemailDTO.setL3contactno(" ");
					}
					if(rsmail.getString("L3_EMAILID")==null){
						objpmwelcomemailDTO.setL3email(" ");
					}else{
					objpmwelcomemailDTO.setL3email(rsmail.getString("L3_EMAILID"));
					}
					
					demodaysdetList.add(objpmwelcomemailDTO);
					
					System.out.println("Name  :"+objpmwelcomemailDTO.getName());
					
					System.out.println("Address   :"+objpmwelcomemailDTO.getAddress());
					System.out.println("Email id  :"+objpmwelcomemailDTO.getEmail());
					System.out.println("Contact No:"+objpmwelcomemailDTO.getContactno());
					System.out.println("faxno :"+objpmwelcomemailDTO.getFaxno());
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
					DbConnection.closeResultset(rsmail);
					DbConnection.closeCallableStatement(getmailDet);
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
