package com.ibm.ioes.model;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	MANISHA GARG	22-feb-11					To remove duplicate data

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.SITransferDto;

public class SITransferModel extends NewOrderModel {

	
	public ArrayList<SITransferDto> fetchParty() throws Exception
	{
		ArrayList<SITransferDto> listPartyDetails= new ArrayList<SITransferDto>();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try
		{
			listPartyDetails = objDao.fetchParty();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listPartyDetails;
	}
	public ArrayList<SITransferDto> fetchAccount(String arr) throws Exception
	{
		ArrayList<SITransferDto> listPartyDetails= new ArrayList<SITransferDto>();
		NewOrderDaoExt objDao = new NewOrderDaoExt();
		try
		{
			//listPartyDetails = objDao.fetchAccount(arr);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listPartyDetails;
	}
	
	public ArrayList<SITransferDto> ViewSITransferList(SITransferDto objDto)
	throws Exception {
                ArrayList<SITransferDto> SITransferList = new ArrayList<SITransferDto>();
                ArrayList<SITransferDto> SITransferList_new = new ArrayList<SITransferDto>();
                NewOrderDaoExt objDao = new NewOrderDaoExt();
                SITransferDto dto = null;
        		HashSet<String> hs = new HashSet<String>();
                           try {
                        	   SITransferList = objDao.ViewSITransferList(objDto);
                        	   
                        	   // 001 start
                        	   
                        	   for (int i = 0; i < SITransferList.size(); i++) {

                   				dto = SITransferList.get(i);
                   				String batchid = dto.getSearchBatchId();
                   				if ((hs.contains(batchid)) == false) {

                   					hs.add(batchid);
                   					SITransferList_new.add(dto);

                   				}

                        	   }   
                        	   
                        	   // 001 end
                        	   
                                          } catch (Exception ex) {
	                                                ex.printStackTrace();
	                                      throw new Exception("SQL Exception : " + ex.getMessage(), ex);
                                           }
                                               return SITransferList_new;
}	
	

	public HashMap<String,ArrayList>  getSITransferDetails(String arr)
	throws Exception {
		HashMap<String ,ArrayList> SITransferDetails=new HashMap<String,ArrayList> ();	
                NewOrderDaoExt objDao = new NewOrderDaoExt();
                           try {
                        	   SITransferDetails = objDao.getSITransferDetails(arr);
                                          } catch (Exception ex) {
	                                                ex.printStackTrace();
	                                      throw new Exception("SQL Exception : " + ex.getMessage(), ex);
                                           }
                                               return SITransferDetails;
}		
	
}
