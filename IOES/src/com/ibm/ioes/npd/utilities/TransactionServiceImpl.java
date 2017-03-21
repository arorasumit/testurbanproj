package com.ibm.ioes.npd.utilities;



import java.util.ArrayList;

import com.ibm.ioes.npd.exception.NpdException;

public class TransactionServiceImpl {


	public String saveTransaction(ArrayList  listTranDto) throws Exception
	{
		String msg=null;
		TransactionDaoImpl trnsactionDao = new TransactionDaoImpl();
		try
		{
			msg = trnsactionDao.saveTransaction(listTranDto);
			
		}
		catch(Exception ex)
		{
			throw new NpdException("Exception :"+ ex.getMessage(), ex);
		}
		
		return msg;
	}
}
