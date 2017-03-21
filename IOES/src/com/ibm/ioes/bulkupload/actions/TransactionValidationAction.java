//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		initialise the validate file page
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		get uploaded files for the selected transaction
//[003]			ANIL KUMAR	   28-JULY-2011		00-05422		send selected file for validation 
//[004]			ANIL KUMAR	   28-JULY-2011		00-05422		to send the validated file for being marked ready for processing 	
//[005]			ANIL KUMAR	   28-JULY-2011		00-05422		to download error file	
//[006]			ANIL KUMAR	   28-JULY-2011		00-05422		to download Result file	
//[007]			SUMIT ARORA	   28-JULY-2011		00-05422		to perform download 
//[008]	    GUNJAN SINGLA  29-SEPT-2014      CBR_20140704-XX-020224     Global Data Billing Efficiencies Phase1
//=================================================================================================================
package com.ibm.ioes.bulkupload.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.util.ResourceBundle;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.bulkupload.dto.TransactionValidationDto;
import com.ibm.ioes.bulkupload.formbean.TransactionUploadFormBean;
import com.ibm.ioes.bulkupload.formbean.TransactionValidationFormBean;
import com.ibm.ioes.bulkupload.utilities.ErrorLogServiceImpl;
import com.ibm.ioes.bulkupload.utilities.TransactionProcessServicesImpl;
import com.ibm.ioes.bulkupload.utilities.TransactionUploadServiceImpl;
import com.ibm.ioes.bulkupload.utilities.TransactionValidationServicesImpl;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.PagingSorting;


/**
 * @version 	1.0
 * @author		Sumit Arora and Anil Kumar
 */

public class TransactionValidationAction extends DispatchAction
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(TransactionValidationAction.class);
	}
	
	public ArrayList transFileList = new ArrayList(); 
	public ArrayList transactionTypeList = new ArrayList();
	ArrayList<TransactionValidationDto> fileStatusList = new ArrayList<TransactionValidationDto>();
	/**
	 * @method	initTransactionValidate
	 * @purpose	initialise the validate file page
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[001]
	public ActionForward initTransactionValidate(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
		    {
				String forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionValidationFormBean formBean = (TransactionValidationFormBean) form;
				TransactionValidationServicesImpl objService = new TransactionValidationServicesImpl();
				TransactionUploadServiceImpl objModel=new TransactionUploadServiceImpl();
				logger.info(userBean.getUserName() + " requested transaction validation page.");

				try
				{
					transactionTypeList=objModel.fetchSubchangeType(userBean.getRoleName());
					formBean.setTransactionList(transactionTypeList);
					//formBean.setTransactionList(objService.getTransactionType(Integer.parseInt(userBean.getUserName())));
					//transFileList = formBean.getTransactionList();
					fileStatusList=objService.getFileStatusDetails();
					request.setAttribute("fileStatusList", fileStatusList);
					forwardMapping = "transactionValidate";
					logger.info("Transaction validation page opened successfully.");
					
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in opening transaction validation page for " + userBean.getUserName() + ".");
					forwardMapping = "transactionValidateError";		
				}
				return mapping.findForward(forwardMapping);
		    }
		 
	//END[001]
	/**
	 * @method	getFilesForTransaction
	 * @purpose	get uploaded files for the selected transaction
	 * @param	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[002]
	public ActionForward getFilesForTransaction(ActionMapping mapping, 
    		ActionForm form,
    	    HttpServletRequest request, 
    	    HttpServletResponse response)throws Exception
		    {
		String forwardMapping = null;
		String getReturnValue = null, strTemplateName[] = null;
		HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
		HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
		UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
		TransactionValidationFormBean formBean = (TransactionValidationFormBean)form;
		TransactionValidationServicesImpl objService = new TransactionValidationServicesImpl();	
		TransactionValidationDto objDto=new TransactionValidationDto();
		TransactionUploadServiceImpl objServiceUpload = new TransactionUploadServiceImpl();
		logger.info(userBean.getUserName() + " selected transaction from transaction type dropdown. Requested uploaded file list for selected transaction.");				
		try
		{			
			PagingSorting pagingSorting = formBean.getPagingSorting();
			if(null != formBean.getSearchFileStatus())
			{
				objDto.setSearchFileStausId(Integer.parseInt(formBean.getSearchFileStatus()));
			}					
			objDto.setSearchfromDate(formBean.getSearchfromDate());
			objDto.setSearchToDate(formBean.getSearchToDate());
			objDto.setSearchFileID(formBean.getSearchFileID());
			//[008] start
			objDto.setSearchUserID(formBean.getSearchUserID());
			//[008] end
			
			pagingSorting.setDefaultifNotPresent("FILEID",PagingSorting.increment, 1);
			objDto.setPagingSorting(pagingSorting);
			objDto.setSelectedTransactionID(formBean.getTransactionIdSelected());
			formBean.setTransactionList(transactionTypeList);
			transFileList=objService.getTransFileList(objDto);
			formBean.setTransFileList(transFileList);	
			getReturnValue = objServiceUpload.getTransactionTemplate(formBean.getTransactionIdSelected());
			strTemplateName = getReturnValue.split("&&&");			
			formBean.setTemplateId(strTemplateName[1]);
			request.setAttribute("fileStatusList", fileStatusList);
			forwardMapping = "transactionValidate";
			logger.info("Uploaded file list for selected transaction fetched successfully.");
		}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in fetching uploaded file list for " + userBean.getUserName() + ".");
					forwardMapping = "transactionValidateError";		
				}
				return mapping.findForward(forwardMapping);
		    }
	
	//END[002]
	/**
	 * @method	validateSelectedFile
	 * @purpose	send selected file for validation
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[003]
	public ActionForward validateSelectedFile(ActionMapping mapping, 
    		ActionForm form,
    	    HttpServletRequest request, 
    	    HttpServletResponse response)throws Exception
		    {
				String forwardMapping = null , filename , fileID ;
				TransactionValidationFormBean formBean = (TransactionValidationFormBean)form;
				TransactionValidationServicesImpl objService = new TransactionValidationServicesImpl();
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);

				logger.info(userBean.getUserName() + " requested validation of selected file");
				
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
					String fileInfo[] = formBean.getFileToValidate().split("&&");
					filename = fileInfo[1];
					fileID = fileInfo[0];
					
					int isvalid=objService.getValidation(fileID);
					formBean.setFileId(fileID);										
					if(isvalid==2)
					{
						formBean.setValidationStatus(1);						
						formBean.setMessage(bundle.getString("validate.success"));						
						formBean.setFileId(fileID);
						formBean.setFileLink(filename);	
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						logger.info(filename + " validated successfully.");
					}
					else
					{
						formBean.setValidationStatus(2);						
						formBean.setErrorMessage(bundle.getString("validate.error"));
						formBean.setFileId(fileID);
						formBean.setFilePath(bundle.getString("excel.Uploaded")+"/"+filename);
						formBean.setFileLink(filename);	
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						logger.info(filename + " has validation errors.");
					}
			
					forwardMapping = "transactionValidate";
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in validating file for " + userBean.getUserName() + ".");
					forwardMapping = "transactionValidateError";		
				}
				return mapping.findForward(forwardMapping);
		    }
	
	//END[003]
	/**
	 * @method	processSelectedFile
	 * @purpose	send the validated file for being marked ready for processing
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[004]
	public ActionForward processSelectedFile(ActionMapping mapping, 
    		ActionForm form,
    	    HttpServletRequest request, 
    	    HttpServletResponse response)throws Exception
		    {
				String forwardMapping = null , filename , fileID ;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionValidationFormBean formBean = (TransactionValidationFormBean)form;
				//TransactionProcessServices servObj = new TransactionProcessServicesImpl();
				TransactionProcessServicesImpl objService = new TransactionProcessServicesImpl();
				
				logger.info(userBean.getUserName() + " requested file be marked for processing");
				
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
					if(formBean.getValidationFlag()==1)
					{
						formBean.setFileToProcess(formBean.getFileToValidate());
					}
					String fileInfo[] = formBean.getFileToProcess().split("&&");
					filename = fileInfo[1];
					fileID = fileInfo[0];
					formBean.setFileId(fileID);	
					
					String isvalid = objService.processFiles(formBean.getFileId(), Integer.valueOf(formBean.getTemplateId()),userBean.getEmployeeId());
					
					if(isvalid.equalsIgnoreCase("5"))
					{
						formBean.setProcessStatus(5);
						formBean.setMessage(bundle.getString("process.success"));
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);	
						request.setAttribute("fileStatusList", fileStatusList);										
						logger.info("File " + filename + " Processed successfully.");
					}
					else if(isvalid.equalsIgnoreCase("0"))
					{
						formBean.setProcessStatus(2);
						formBean.setFilePath(bundle.getString("excel.Uploaded")+"/"+filename);
						formBean.setErrorMessage(bundle.getString("process.error"));
						formBean.setFileLink(filename);
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						logger.info(filename + " has validation errors.");
					}
					else
					{
						formBean.setProcessStatus(2);
						formBean.setErrorMessage(bundle.getString("process.exceptionError"));
						formBean.setFileLink(filename);
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						logger.info("File " + filename + " has Process errors due to exception.");
					}
										
					forwardMapping = "transactionValidate";
					
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " marking file for processing for " + userBean.getUserName() + ".");
					forwardMapping = "transactionValidateError";
				}
				
				return mapping.findForward(forwardMapping);
				
				
		    }
	//END[004]
	/**
	 * @method	getErrorLog
	 * @purpose	to download error file			
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[005]
	public ActionForward getErrorLog(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
		    {
				String filePath , fileName , forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionValidationFormBean formBean = (TransactionValidationFormBean)form;
				ErrorLogServiceImpl servObj = new ErrorLogServiceImpl();
				logger.info(userBean.getUserName() + " requested generating errorlog for file.");
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			
					filePath = servObj.getErrorExcel(formBean.getFilePath(),Integer.parseInt(formBean.getFileId()));
					if("NOTFOUND".equalsIgnoreCase(filePath))
					{
						formBean.setFileFoundStatus(2);
						formBean.setErrorMessage(formBean.getFilePath()+" "+bundle.getString("fileNotFound.error"));
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						forwardMapping="transactionValidate";
						logger.info(formBean.getFilePath()+"The system cannot find the file specified.");
					}
					else
					{
						formBean.setFileFoundStatus(1);
						fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());		
						formBean.setErrorMessage(bundle.getString("validate.error"));
						formBean.setFileLink(fileName);
						formBean.setFileToDownload(filePath);
						
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						forwardMapping="transactionValidate";
						logger.info("Error log successfully generated for file.");
					}
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in generating error log for " + userBean.getUserName() + ".");
					forwardMapping="transactionValidateError";
				}
				
				return mapping.findForward(forwardMapping);
		    }
	//END[005]
	/**
	 * @method	getResultLog
	 * @purpose	to download Result file			
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[006]
	public ActionForward getResultLog(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
		    {
				String filePath , fileName , forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionValidationFormBean formBean = (TransactionValidationFormBean)form;
				ErrorLogServiceImpl servObj = new ErrorLogServiceImpl();
				logger.info(userBean.getUserName() + " requested generating errorlog for file.");
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			
					filePath = servObj.getResultExcel(formBean.getFilePath(),Integer.parseInt(formBean.getFileId()));
					if("NOTFOUND".equalsIgnoreCase(filePath))
					{
						formBean.setFileFoundStatus(2);
						formBean.setErrorMessage(formBean.getFilePath()+" "+bundle.getString("fileNotFound.error"));
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						forwardMapping="transactionValidate";
						logger.info(formBean.getFilePath()+"The system cannot find the file specified.");
					}
					else
					{
						formBean.setFileFoundStatus(1);
						fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());							
						formBean.setFileToDownload(filePath);					
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						forwardMapping="transactionValidate";
						logger.info("Result log successfully generated for file.");
					}
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in generating result log for " + userBean.getUserName() + ".");
					forwardMapping="transactionValidateError";
				}
				
				return mapping.findForward(forwardMapping);
		    }
	//END[006]
	/**
	 * @method	doDownLoadFile
	 * @purpose	to perform download 			
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[007]
	public void doDownLoadFile(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
			{
				String filepath, filename;
				TransactionValidationFormBean formBean = (TransactionValidationFormBean)form;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				OutputStream outStream = null;
				FileInputStream inStream = null;
				
				logger.info(userBean.getUserName() + " requested downloading file.");
				
				try
				{
					filepath = formBean.getFileToDownload();
					
					filename = filepath.substring((filepath.lastIndexOf("/") + 1),filepath.length());
					
					File f = new File(filepath);
					response.setContentType("application/vnd.ms-excel");
					response.setContentLength((int) f.length());
					response.setHeader("Content-Disposition", "attachment; filename="+ filename);
					response.setHeader("Cache-Control", "public");
					
					outStream = response.getOutputStream();
					inStream = new FileInputStream(f);
					
					byte[] buf = new byte[8192];
					int sizeRead = 0;
					while ((sizeRead = inStream.read(buf, 0, buf.length)) > 0) 
					{
						log.debug("size:" + sizeRead);
						outStream.write(buf, 0, sizeRead);
					}
					
					inStream.close();
					outStream.close();
					logger.info(filename + " downloaded successfully.");
				}
				catch(IllegalStateException ignoredException)
				{
					if (outStream != null) 
					{
						outStream.close();
					}
					if (inStream != null) 
					{
						inStream.close();
					}
					logger.error(ignoredException.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in downloading file for " + userBean.getUserName() + ".");
				}
				catch(Exception ex)
				{
					if (outStream != null) 
					{
						outStream.close();
					}
					if (inStream != null) 
					{
						inStream.close();
					}
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in downloading file for " + userBean.getUserName() + ".");
				}
			}
	//END[007]
	public ActionForward getResultErrorMixLog(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
		    {
				String filePath , fileName , forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionValidationFormBean formBean = (TransactionValidationFormBean)form;
				ErrorLogServiceImpl servObj = new ErrorLogServiceImpl();
				logger.info(userBean.getUserName() + " requested generating errorlog for file.");
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
			
					filePath = servObj.getResultErrorMixLog(formBean.getFilePath(),Integer.parseInt(formBean.getFileId()));
					if("NOTFOUND".equalsIgnoreCase(filePath))
					{
						formBean.setFileFoundStatus(2);
						formBean.setErrorMessage(formBean.getFilePath()+" "+bundle.getString("fileNotFound.error"));
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						forwardMapping="transactionValidate";
						logger.info(formBean.getFilePath()+"The system cannot find the file specified.");
					}
					else
					{
						formBean.setFileFoundStatus(1);
						fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());							
						formBean.setFileToDownload(filePath);					
						formBean.setTransactionList(transactionTypeList);
						formBean.setTransFileList(transFileList);
						request.setAttribute("fileStatusList", fileStatusList);
						forwardMapping="transactionValidate";
						logger.info("Result log successfully generated for file.");
					}
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in generating result log for " + userBean.getUserName() + ".");
					forwardMapping="transactionValidateError";
				}
				
				return mapping.findForward(forwardMapping);
		    }
}
