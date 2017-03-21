package com.ibm.ioes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.NewOrderDaoExt;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.forms.CurrencyChangeDto;
import com.ibm.ioes.forms.SITransferDto;

public class CurrencyChangeModel extends NewOrderModel {

	
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
	public ArrayList<CurrencyChangeDto> fetchAccount(String arr) throws Exception
	{
		ArrayList<CurrencyChangeDto> listPartyDetails= new ArrayList<CurrencyChangeDto>();
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
	
	public ArrayList<CurrencyChangeDto> ViewCurrencyChangeList(CurrencyChangeDto objDto)
	throws Exception {
                ArrayList<CurrencyChangeDto> CurrencyChangeList = new ArrayList<CurrencyChangeDto>();
                NewOrderDaoExt objDao = new NewOrderDaoExt();
                           try {
                        	   CurrencyChangeList = objDao.ViewCurrencyChangeList(objDto);
                                          } catch (Exception ex) {
	                                                ex.printStackTrace();
	                                      throw new Exception("SQL Exception : " + ex.getMessage(), ex);
                                           }
                                               return CurrencyChangeList;
}	
	

	public HashMap<String,ArrayList>  getCurrencyChangeDetails(String arr)
	throws Exception {
		HashMap<String ,ArrayList> CurrencyChangeDetails=new HashMap<String,ArrayList> ();	
                NewOrderDaoExt objDao = new NewOrderDaoExt();
                           try {
                        	   CurrencyChangeDetails = objDao.getCurrencyChangeDetails(arr);
                                          } catch (Exception ex) {
	                                                ex.printStackTrace();
	                                      throw new Exception("SQL Exception : " + ex.getMessage(), ex);
                                           }
                                               return CurrencyChangeDetails;
}		
	
}
