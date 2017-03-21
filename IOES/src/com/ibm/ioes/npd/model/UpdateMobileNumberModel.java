package com.ibm.ioes.npd.model;

import org.apache.struts.upload.FormFile;

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.ioes.npd.beans.UpdateMobileNumberBean;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.dao.RepositoryUploadDao;
import com.ibm.ioes.npd.hibernate.dao.UpdateMobileNumberDao;

public class UpdateMobileNumberModel extends CommonBaseModel
{
	public String fetchMobileNumber(TmEmployee employeebean,UpdateMobileNumberBean bean)throws ValidationException, Exception 
	{
		UpdateMobileNumberDao dao=new UpdateMobileNumberDao();
		String mobNumber=dao.fetchMobileNumber(employeebean,bean);
		return mobNumber;
	}
	
	public boolean updateMobileNumer(String mobileNumber,String empID) throws Exception
	{
		UpdateMobileNumberDao objDao = new UpdateMobileNumberDao();
		boolean insert=objDao.updateMobileNumber(mobileNumber, empID);
		return insert;
	}
}
