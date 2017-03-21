package com.ibm.ioes.npd.utilities;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.struts.upload.FormFile;

import com.ibm.ioes.utilities.DbConnection;


public class TransactionDaoImpl {
	Connection connection =null;
	CallableStatement proc =null;

	public String saveTransaction(ArrayList <TransactionDto>  listTranDto) throws SQLException,Exception{
	String Msg="";	
		try{
			
	  connection=NpdConnection.getConnectionObject();
	  connection.setAutoCommit(false);
		if(listTranDto.size()>0){
			for(int counter=0;counter<listTranDto.size();counter++){
				//Converting String into SQL Date
				TransactionDto tranDto=listTranDto.get(counter);
				String stringDate=tranDto.getCurrentDate()+" 00:00:00";
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				java.util.Date parsedUtilDate = formater.parse(stringDate);
				java.sql.Date sqltDate= new java.sql.Date(parsedUtilDate.getTime());
				//Calling Stored Procedure For Dataaction
			
				proc=connection.prepareCall(" {CALL CWS.PCWS_INSERT_TRNPROJECTACTIONDETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)} ");
				proc.setLong(1,tranDto.getPoroject());
				proc.setLong(2,tranDto.getStage());
				proc.setLong(3,tranDto.getTask());
				proc.setString(4,tranDto.getActionEevent());
				proc.setLong(5,Integer.parseInt(tranDto.getTaskOwner()));	
				proc.setDate(6,sqltDate);
				proc.setString(7,tranDto.getRemarks());
				proc.setLong(8,tranDto.getIsDocAttached());
				proc.setLong(9,tranDto.getActionType());
				proc.setLong(10,tranDto.getNextStageId());
				proc.setLong(11,tranDto.getNextTaskId());
				proc.setLong(12,Integer.parseInt(tranDto.getCtreatedBy()));
				proc.setLong(13,Integer.parseInt(tranDto.getCtreatedBy()));
				proc.setLong(14,tranDto.getIsActive());
				//Uploaded document related setting
				java.sql.Blob blob=null;
				
				FormFile doc=tranDto.getDocument();
				if(doc!=null)
				{
				blob=new SerialBlob( new SerialBlob(tranDto.getDocument().getFileData())); 
					proc.setBlob(15,blob);
					proc.setLong(16,1);
					proc.setString(17,doc.getFileName());
					proc.setString(18,doc.getFileName().substring(doc.getFileName().lastIndexOf(".")+1));
				}
				else
				{
					Blob blob2 = null;
					proc.setBlob(15,blob2);
					proc.setLong(16,0);
					proc.setString(17,null);
					proc.setString(18,null);
				}
				if(tranDto.getRfiFrom()!=null)
				{
					proc.setLong(19,Integer.parseInt(tranDto.getRfiFrom()));
				}
				else
				{
					proc.setInt(19,0);
				}				
				//Email Related Setting
				proc.setLong(20,tranDto.getIsMail());
				proc.setString(21,tranDto.getToId());
				proc.setString(22,tranDto.getCcId());
				proc.setString(23,tranDto.getBccId());
				proc.setString(24,tranDto.getSubject());
				proc.setString(25,tranDto.getBody());
				proc.setString(26,tranDto.getHeader());
				proc.setString(27,tranDto.getFooter());
				//
				proc.setString(28,"0");
				proc.setString(29,"BLANCK");
				proc.execute();
				System.out.println(proc.getInt(28));
				System.out.println(proc.getString(29));
				
				Msg=proc.getString(28);
				//Verifiying that exception accured or not in Stord Proc
				if(proc.getString(28).equals("66666")){
				connection.rollback();	
				}else{
				connection.commit();	
				}
			}//End of For Loop
		
		} // End of If condition
		}catch(Exception ex){
			
			ex.printStackTrace();
			
			throw ex;
			
			
		}finally
		{
			DbConnection.closeCallableStatement(proc);
			NpdConnection.freeConnection(connection);
		}
		return Msg;
	
	}//End of method saveTransaction()
	
}//End of class
