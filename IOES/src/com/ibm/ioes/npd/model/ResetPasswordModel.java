package com.ibm.ioes.npd.model;

import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.ioes.npd.beans.ResetPasswordBean;
import com.ibm.ioes.npd.hibernate.dao.ChangePwdDao;
import com.ibm.ioes.npd.hibernate.dao.ResetPasswordDao;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.SendMail;

public class ResetPasswordModel extends CommonBaseModel
{
	public void updateAndResetPassword(String loginId) throws Exception 
	{
		AppConstants.NPDLOGGER.info("ResetPassword is trigered ");
		IEncryption encryption = new Encryption();
		String oldPassword = null;
		ResetPasswordDao dao=new ResetPasswordDao();
		ResetPasswordBean bean=null;
		ChangePwdDao chgDao=new ChangePwdDao();
		String newpwd = null;
		String dycpNewpwd = null;
		GSDService gsd = new GSDService();
		try 
		{
			bean=dao.fetchUserDetails(loginId);
			oldPassword=bean.getExistingPwd();
			int empid=bean.getEmpNPDID();
			newpwd = dao.generatePassword();
			dycpNewpwd=encryption.generateDigest(newpwd);
			AppConstants.NPDLOGGER.info("Plain PWD : " + newpwd);
			AppConstants.NPDLOGGER.info("DYCPT PWD : " + dycpNewpwd);
			chgDao.updatePassword(String.valueOf(empid), dycpNewpwd, oldPassword);
			//	Sending Email
			SendMail objSendMail = new SendMail();
			String[] mailid = { loginId };
			StringBuffer sb=new StringBuffer();
			sb.append("<HTML><BODY>");
			sb.append(Messages.getMessageValue("Mail_Header")+"<BR>");
			sb.append("<BR>Your Password for NPD Portal has been Reset :");
			sb.append("<BR>New Password:"+newpwd);
			sb.append("<BR><BR>"+Messages.getMessageValue("Mail_Footer")+"<BR>");
			sb.append("</BODY></HTML>");
				
			objSendMail.postMail(mailid, null, null, "Password Reset", sb.toString(), null, null);	
			
		}
		catch (Exception e1) 
		{
			AppConstants.NPDLOGGER.error(AppUtility.getStackTrace(e1));
			throw e1;
		}
		
	}
}
