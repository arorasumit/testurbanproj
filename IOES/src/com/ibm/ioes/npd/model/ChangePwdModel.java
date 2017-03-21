package com.ibm.ioes.npd.model;

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.ioes.npd.beans.ChangePwdBean;
import com.ibm.ioes.npd.hibernate.dao.ChangePwdDao;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.SendMail;

public class ChangePwdModel extends CommonBaseModel
{
	public void validateLoginId(ChangePwdBean changePasswordbean)throws ValidationException, Exception 
	{
		GSDService gsd = new GSDService();
		gsd.validateChangePwd(changePasswordbean.getEmail(), changePasswordbean.getNewpassword(),
				changePasswordbean.getOldpassword());
	}
	
	public boolean changePassword(ChangePwdBean changePasswordbean) throws Exception 
	{
		ChangePwdDao dao=new ChangePwdDao();
		Encryption encryption = new Encryption();
		boolean insert=dao.updatePassword(changePasswordbean.getLoginId(),encryption.generateDigest(changePasswordbean.getNewpassword()),encryption.generateDigest(changePasswordbean.getOldpassword()));
		//		Sending Email
		if(changePasswordbean.getEmailConfirmation()==1)
		{
			SendMail objSendMail = new SendMail();
			String[] mailid = { changePasswordbean.getEmail() };
			StringBuffer sb=new StringBuffer();
			sb.append("<HTML><BODY>");
			sb.append(Messages.getMessageValue("Mail_Header")+"<BR>");
			sb.append("<BR>Your Password for NPD Portal has been Changed by You :");
			sb.append("<BR>New Password:"+changePasswordbean.getNewpassword());
			sb.append("<BR><BR>"+Messages.getMessageValue("Mail_Footer")+"<BR>");
			sb.append("</BODY></HTML>");
				
			objSendMail.postMail(mailid, null, null, "Password Changed", sb.toString(), null, null);	
		}
		return insert;
	}
}
