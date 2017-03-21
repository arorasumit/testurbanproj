//[0001]   Neha Maheshwari    added service segment    NORTH STAR ACCOUNT TAGGING
package com.ibm.ioes.model;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.ibm.ioes.actions.UniversalWorkQueueAction;
import com.ibm.ioes.beans.UniversalWorkQueueDto;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.dao.UniversalWorkQueueDao;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.utilities.Utility;

public class UniversalWorkQueueModel {
	private static final Logger logger;
	static {
		logger = Logger.getLogger(UniversalWorkQueueAction.class);
	}
	
	public ArrayList getUniversalWorkQueueList(UniversalWorkQueueDto objUniversalWorkQueueDto,UserInfoDto objUserDto)throws Exception{
		ArrayList alUniversalWorkQueueList=null;
		try 
		{
			Utility.SysOut(" Inside Model of Universal Work Queue Interface ");
			UniversalWorkQueueDao objUniversalWorkQueueDao =  new UniversalWorkQueueDao();
			alUniversalWorkQueueList=objUniversalWorkQueueDao.getUniversalWorkQueueList(objUniversalWorkQueueDto,objUserDto);
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			throw new Exception( "Service is not available." +ex);
		} 
			
		return alUniversalWorkQueueList;
	}
	//public ArrayList<UniversalWorkQueueDto> displayCurrencyDetails(UniversalWorkQueueDto objDto) throws Exception
	//{
	//	ArrayList<UniversalWorkQueueDto> listCurrencyDetails= new ArrayList<UniversalWorkQueueDto>();
	//	UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
	//	try
	//	{
	//		listCurrencyDetails = objDao.getCurrency();
		//}
		//catch(Exception ex)
		//{
		//	ex.printStackTrace();	
		//}
		//return listCurrencyDetails;
	//}
	public ArrayList<UniversalWorkQueueDto> displaySourceDetails(UniversalWorkQueueDto objDto) throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listSourceDetails= new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
		try
		{
			listSourceDetails = objDao.getSourceName();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSourceDetails;
	}
	
	public ArrayList<UniversalWorkQueueDto> displayOrderTypeDetails(UniversalWorkQueueDto objDto) throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listOrderTypeDetails= new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
		try
		{
			listOrderTypeDetails = objDao.getOrderType();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listOrderTypeDetails;
	}
	
	//start anil
	public ArrayList getAllExistingOrdersDetails(UniversalWorkQueueDto objUniversalWorkQueueDto,UserInfoDto objUserDto)throws Exception{
		ArrayList alUniversalWorkQueueList=null;
		try 
		{
			Utility.SysOut(" Inside Model of Universal Work Queue Interface ");
			UniversalWorkQueueDao objUniversalWorkQueueDao =  new UniversalWorkQueueDao();
			alUniversalWorkQueueList=objUniversalWorkQueueDao.getAllExistingOrdersDetails(objUniversalWorkQueueDto,objUserDto);
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ " Exception occured in viewUniversalWorkQueue method of "+ this.getClass().getSimpleName());
			throw new Exception( "Service is not available." +ex);
		} 
			
		return alUniversalWorkQueueList;
	}
	//end anil
	//	start anil
	public ArrayList<UniversalWorkQueueDto> fetchQueryOptionList() throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listQueryOptions= new ArrayList<UniversalWorkQueueDto>();	
		UniversalWorkQueueDao objUniversalWorkQueueDao =  new UniversalWorkQueueDao();
		try
		{
			listQueryOptions = objUniversalWorkQueueDao.fetchQueryOptionList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listQueryOptions;
	}	
	//end anil
	public ArrayList<UniversalWorkQueueDto> displaySubChangeType(UniversalWorkQueueDto objDto) throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listSubChangeType= new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
		try
		{
			listSubChangeType = objDao.getSubChangeType();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listSubChangeType;
	}
	public ArrayList<UniversalWorkQueueDto> displayCustomerSegment(UniversalWorkQueueDto objDto) throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listCustomerSegmentDetails= new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
		try
		{
			listCustomerSegmentDetails = objDao.getCustomerSegment();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listCustomerSegmentDetails;
	}
	public ArrayList<UniversalWorkQueueDto> displayRegion(UniversalWorkQueueDto objDto) throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listRegionDetails= new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
		try
		{
			listRegionDetails = objDao.getRegionName();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listRegionDetails;
	}
	public ArrayList<UniversalWorkQueueDto> displayIndustrySegment(UniversalWorkQueueDto objDto) throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listIndustrySegmentDetails= new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
		try
		{
			listIndustrySegmentDetails = objDao.getIndustrySegmentName();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listIndustrySegmentDetails;
	}
	public ArrayList<UniversalWorkQueueDto> displayOrderStage(UniversalWorkQueueDto objDto) throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listOrderStageDetails= new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
		try
		{
			listOrderStageDetails = objDao.getOrderStageName();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listOrderStageDetails;
	}
	//[0001] Start
	public ArrayList<UniversalWorkQueueDto> displayServiceSegment(UniversalWorkQueueDto objDto) throws Exception
	{
		ArrayList<UniversalWorkQueueDto> listServiceSegmentDetails= new ArrayList<UniversalWorkQueueDto>();
		UniversalWorkQueueDao objDao = new UniversalWorkQueueDao();
		try
		{
			listServiceSegmentDetails = objDao.getServiceSegmentName();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();	
		}
		return listServiceSegmentDetails;
	}
	//[0001] End
	
}
