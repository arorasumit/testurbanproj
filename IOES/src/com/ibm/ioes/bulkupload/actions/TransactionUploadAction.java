//=================================================================================================================
//Tag Name		Resource Name		Date		CSR No			Description
//[001]			ANIL KUMAR	   28-JULY-2011		00-05422		to get transaction type list and open Bulkupload Page
//[002]			ANIL KUMAR	   28-JULY-2011		00-05422		to get template details of the transaction selected	
//[003]			SUMIT ARORA	   28-JULY-2011		00-05422		to save the uploaded file physically on the server 
//[004]			SUMIT ARORA	   28-JULY-2011		00-05422		to download template 	
//[005]			SUMIT ARORA	   28-JULY-2011		00-05422		to perform download 
//[006]			SUMIT ARORA	   28-JULY-2011		00-05422		to download reference master data in file	
//[007]			SUMIT ARORA	   28-JULY-2011		00-05422		to validating file for expected value for fields	
//[008]			SUMIT ARORA	   28-JULY-2011		00-05422		to processing file after validate
//[009]			ANIL KUMAR	   28-JULY-2011		00-05422		to download error file
//[010]			ANIL KUMAR	   28-JULY-2011		00-05422		to download filled template based on parameter	
//[011]			SAURABH SINGH  28-JULY-2011		00-05422		to display Search parameter list popup window
//=================================================================================================================
package com.ibm.ioes.bulkupload.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.ioes.beans.CurrencyChangeBean;
import com.ibm.ioes.beans.ReportsBean;
import com.ibm.ioes.beans.UserInfoDto;
import com.ibm.ioes.bulkupload.dao.ErrorFileDaoImpl;
import com.ibm.ioes.bulkupload.formbean.TransactionUploadFormBean;
import com.ibm.ioes.bulkupload.formbean.TransactionValidationFormBean;
import com.ibm.ioes.bulkupload.utilities.ErrorLogServiceImpl;
import com.ibm.ioes.bulkupload.utilities.TransactionProcessServicesImpl;
import com.ibm.ioes.bulkupload.utilities.TransactionUploadServiceImpl;
import com.ibm.ioes.bulkupload.utilities.TransactionValidationServicesImpl;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Utility;

/**
 * @version 	1.0
 * @author		Sumit Arora and Anil Kumar
 */

public class TransactionUploadAction extends DispatchAction
{
	/**
	 * Logger for the class.
	**/
	private static final Logger logger;
	
	static {

		logger = Logger.getLogger(TransactionUploadAction.class);
	}
	
	public ArrayList transactionTypeList = new ArrayList();
	public ArrayList referenceMasterList = new ArrayList();
	public String filePath;

	/**
	 * @method	goToBulkUpload
	 * @purpose	to get transaction type list and open Bulkupload Page
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[001]
	public ActionForward goToBulkUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse respons) throws Exception
	{
		ActionForward forward=new ActionForward();
		TransactionUploadFormBean formBean=(TransactionUploadFormBean)form;		
		TransactionUploadServiceImpl objModel=new TransactionUploadServiceImpl();
		ActionMessages messages = new ActionMessages();		
		try
		{
			//added by mansha bt dump start
			Utility.setFileDumpParamsforBT(request,formBean);
			//added by mansha bt dump end
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
			
			transactionTypeList = objModel.fetchSubchangeType(userBean.getRoleName());
			
			formBean.setTransactionList(transactionTypeList);
			forward=mapping.findForward("BulkUploadPage");
		}
		catch(Exception e)
		{
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(e));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(AppConstants.MESSAGE_ID));
		}
		
		return forward;
	}
 	//END[001]
	/**
	 * @method	getSelectedTransactionDetails
	 * @purpose	to get template details of the transaction selected	
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[002]
	public ActionForward getSelectedTransactionDetails(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
			{
				Integer transactionId;
				String getReturnValue = null, strTemplateName[] = null, forwardMapping = null;
				
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				TransactionUploadFormBean formBean = (TransactionUploadFormBean) form;
				TransactionUploadServiceImpl objService = new TransactionUploadServiceImpl();
					
				logger.info(userBean.getUserName() + " selected transaction type with ID = " + formBean.getTransactionType() + ". Requesting transaction template and reference master list for selected transaction.");

				try
				{
					transactionId = Integer.parseInt(formBean.getTransactionType());
					getReturnValue = objService.getTransactionTemplate((int)transactionId);
					strTemplateName = getReturnValue.split("&&&");
					
					formBean.setTransactionTemplate(strTemplateName[0]);
					formBean.setTemplateId(strTemplateName[1]);
					formBean.setMasterReferenceList(objService.getTransactionMasterReference(formBean.getTemplateId()));
					
					referenceMasterList = formBean.getMasterReferenceList();
					formBean.setTransactionList(transactionTypeList);
					
					forwardMapping="BulkUploadPage";
					logger.info("Template and reference master list fetched sucessfully for selected transaction.");
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in fetching template and reference master list for " + userBean.getUserName());
					forwardMapping = "ErrorPageAction";
				}
				return mapping.findForward(forwardMapping);
			}
	//END[002]
	/**
	 * @method	uploadFile
	 * @purpose	to save the uploaded file physically on the server 			
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[003]
	public ActionForward uploadFile(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
			{
				String strFile = null, ext = null,fileName = null, forwardMapping = null;
				ArrayList retList = new ArrayList();
				RandomAccessFile raf = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionUploadFormBean formBean = (TransactionUploadFormBean) form;
				TransactionUploadServiceImpl objService = new TransactionUploadServiceImpl();
				int resultStatus=0;
				
				logger.info(userBean.getUserName() + " requested uploading file for selected transaction.");

				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
					if(formBean.getTemplateId().trim().equals(""))
					{
						formBean.setErrorMessage(" Please select a transaction type before uploading file");
						formBean.setProcessStatus(0);
					}
					else if(formBean.getNewFile().getFileName().trim().equals(""))
					{
						formBean.setErrorMessage(" Please select a file to upload");
						formBean.setProcessStatus(0);
					}
					else 
					{
						FormFile f = formBean.getNewFile();
						File file = null;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
						String date = sdf.format(new java.util.Date());
						date = date.substring(0,19);
						
						strFile = f.getFileName();
						
						//if((strFile.indexOf(".")!=strFile.lastIndexOf("."))||(!(strFile.toLowerCase().endsWith(".xls"))))
						if(!(strFile.toLowerCase().endsWith(".xls")))
						{
							formBean.setFilePath("");
							formBean.setProcessStatus(0);
							formBean.setErrorMessage(bundle.getString("invalid.excel"));
							logger.info(userBean.getUserName() + " uploaded an invalid xls file. Request could not be carried out.");
						}
						else if(strFile.substring(0,strFile.lastIndexOf(".")).length()>255)
						{
							formBean.setFilePath("");
							formBean.setProcessStatus(0);
							formBean.setErrorMessage(bundle.getString("filename.toolarge"));
							logger.info(userBean.getUserName() + " uploaded a xls file with too large name. Request could not be carried out.");
						}
						else	
						{	
							ext = strFile.substring(strFile.lastIndexOf("."));						
							fileName = strFile.substring(0,strFile.lastIndexOf("."))+date+ext;
							formBean.setFilePath(bundle.getString("excel.Uploaded")+fileName);					
							
							if(f.getFileSize()>4194304)
							{
								formBean.setFilePath("");
								formBean.setErrorMessage(bundle.getString("upload.toolarge"));
								formBean.setProcessStatus(0);
								logger.info(userBean.getUserName() + " uploaded a xls file greater than 4Mb. Request could not be carried out.");
							}
							else
							{	
								file = new File(formBean.getFilePath());
								byte[] b = f.getFileData();
								raf = new RandomAccessFile(file,"rw");
								raf.write(b);
								raf.close();
								request.setAttribute("FILE_OBJECT", file.getPath());
		
								retList = objService.validateUploadedExcel(formBean.getFilePath(),formBean.getTransactionTemplate());
								if(retList.isEmpty())
								{
								
										 resultStatus = objService.saveUploadedFileInfo(formBean.getFilePath(),formBean.getTemplateId(), Integer.parseInt(userBean.getEmployeeId()),userBean.getRespId());
									
									if(resultStatus==-1)
									{
										formBean.setFileID("-1");
										formBean.setProcessStatus(0);
										formBean.setErrorMessage(bundle.getString("sheet.blank"));
										logger.info(userBean.getUserName() + " uploaded a xls file with blank sheet. Request could not be carried out.");
									}
									else if(resultStatus==0)
									{
										formBean.setFileID("-1");
										formBean.setProcessStatus(0);
										formBean.setErrorMessage(bundle.getString("upload.error"));
										logger.info("There was some error in uploading file. Request could not be carried out for " + userBean.getUserName() + ".");
									}	
									else
									{
										formBean.setFileID(((Integer)resultStatus).toString());	
										formBean.setProcessStatus(1);
										formBean.setMessage(bundle.getString("upload.success")+" "+fileName+" with FILE ID "+resultStatus);
										logger.info("File uploaded successfully as " + fileName + " for " + userBean.getUserName() + ".");
									}
										
								}
								else
								{
									formBean.setProcessStatus(0);
									formBean.setErrorMessage(bundle.getString("upload.error"));
									logger.info("There was some error in uploading file. Request could not be carried out for " + userBean.getUserName() + ".");
									formBean.setMsgList(retList);
								}
							}
						}
					}
					formBean.setTransactionList(transactionTypeList);
					formBean.setMasterReferenceList(referenceMasterList);
					
					forwardMapping = "BulkUploadPage";
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in uploading file for " + userBean.getUserName() + ".");
					ex.printStackTrace();
					forwardMapping = "ErrorPageAction";
				}
				
				return mapping.findForward(forwardMapping);
			}
	//END[003]
	/**
	 * @method	downloadTemplate
	 * @purpose	to download template 			
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[004]
	public ActionForward downloadTemplate(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
			{
				String filePath = null, forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionUploadFormBean formBean = (TransactionUploadFormBean)form;
				
				logger.info(userBean.getUserName() + " requested downloading tempalte for selected transaction.");
				
				try
				{
					filePath = formBean.getTransactionTemplate();
					if("null/null".equalsIgnoreCase(filePath))
					{
						formBean.setProcessStatus(0);
						formBean.setErrorMessage("Template of this subchange type is not available");
						formBean.setTransactionList(transactionTypeList);
						formBean.setMasterReferenceList(referenceMasterList);
						logger.info("transaction template not found.");
					}
					else
					{
						formBean.setProcessStatus(1);
						formBean.setFileToDownload(filePath);
						formBean.setTransactionList(transactionTypeList);
						formBean.setMasterReferenceList(referenceMasterList);						
						logger.info("transaction template downloaded successfully.");
					}
					
					forwardMapping = "BulkUploadPage";
					
				}
				
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in downloading transaction template for " + userBean.getUserId());
					forwardMapping = "ErrorPageAction";
				}
				return mapping.findForward(forwardMapping);
			}
	//END[004]
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
	//START[005]
	public void doDownLoadFile(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
			{
				String filepath, filename;
				TransactionUploadFormBean formBean = (TransactionUploadFormBean)form;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				OutputStream outStream = null;
				FileInputStream inStream = null;
				
				logger.info(userBean.getUserName() + " requested downloading file " + formBean.getFileToDownload().substring((formBean.getFileToDownload().lastIndexOf("/") + 1),(formBean.getFileToDownload().length())) + ".");
				
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
	//END[005]
	/**
	 * @method	getReferenceMasterData
	 * @purpose	to download reference master data in file			
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[006]
	public ActionForward getReferenceMasterData(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
		    {
				String filepath = null, forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionUploadFormBean formBean = (TransactionUploadFormBean)form;
				TransactionUploadServiceImpl objService = new TransactionUploadServiceImpl();
				
				logger.info(userBean.getUserName() + " requested selected master reference data.");
				
				try
				{
					String selectedMasterStr = formBean.getSelectedReferenceId();
					if(selectedMasterStr!="")
					{
						String[] selectedMasterList = selectedMasterStr.split("&&&");
						int[] masterIdList = new int[selectedMasterList.length-1];
						String[] masterNameList = new String[selectedMasterList.length-1];
						
						for(int i=1;i<selectedMasterList.length;i++)
						{
							String[] tempMasterData = selectedMasterList[i].split("#");
							masterIdList[i-1] = (int)Integer.parseInt(tempMasterData[0]);
							masterNameList[i-1] = tempMasterData[1];
						}
						
						formBean.setSelectedReferenceId("");
						filepath = objService.getReferenceMasterData(masterIdList,masterNameList,formBean.getTransactionName());
						formBean.setFileToDownload(filepath);
						formBean.setTransactionList(transactionTypeList);
						formBean.setMasterReferenceList(referenceMasterList);
					}	
					forwardMapping="BulkUploadPage";
					logger.info("Selected master reference data fetched successfully.");
				}
				
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in fetching selected master reference data for " + userBean.getUserId());
					forwardMapping = "ErrorPageAction";
				}
				return mapping.findForward(forwardMapping);
		    }
	//END[006]
	/**
	 * @method	validateData
	 * @purpose	to validating file for expected value for fields			
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[007]
	public ActionForward validateData(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
			{
				int fileID;
				String forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionUploadFormBean formBean = (TransactionUploadFormBean) form;
				TransactionValidationServicesImpl servObj = new TransactionValidationServicesImpl();
				
				logger.info(userBean.getUserName() + " requested validating the file " );
				
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
					
					fileID = Integer.parseInt(formBean.getFileID());
					if(fileID>-1)
					{
						int isvalid = servObj.getValidation(formBean.getFileID());
						String filePath = formBean.getFilePath();
						String filename = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
						
						if(isvalid==2)
						{
							formBean.setProcessStatus(3);
							formBean.setMessage(bundle.getString("validate.success"));
							logger.info("File " + filename + " validated successfully.");
						}
						else 
						{
							formBean.setProcessStatus(2);
							formBean.setErrorMessage(bundle.getString("validate.error"));
							formBean.setFileLink(filename);
							logger.info("File " + filename + " has validation errors.");
						}
					}
					formBean.setTransactionList(transactionTypeList);
					formBean.setMasterReferenceList(referenceMasterList);
					
					forwardMapping="BulkUploadPage";
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in validating file for " + userBean.getUserName() + ".");
					forwardMapping = "ErrorPageAction";
				}
				return mapping.findForward(forwardMapping);
			}
	//END[007]
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
	//START[009]
	public ActionForward getErrorLog(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
		    {
				String filePath , fileName , forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionUploadFormBean formBean = (TransactionUploadFormBean) form;
				ErrorLogServiceImpl servObj = new ErrorLogServiceImpl();
				
				logger.info(userBean.getUserName() + " requested generating errorlog for file.");
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
					
					filePath = servObj.getErrorExcel(formBean.getFilePath(),Integer.parseInt(formBean.getFileID()));
					fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
					
					formBean.setErrorMessage(bundle.getString("validate.error"));
					formBean.setFileLink(fileName);
					formBean.setFileToDownload(filePath);
					
					formBean.setTransactionList(transactionTypeList);
					formBean.setMasterReferenceList(referenceMasterList);
					
					forwardMapping="BulkUploadPage";
					logger.info("Error log successfully generated for file.");
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " generating error log for " + userBean.getUserName() + ".");
					forwardMapping="ErrorPageAction";
				}
				
				return mapping.findForward(forwardMapping);
		    }
	//END[009]
	/**
	 * @method	processData
	 * @purpose	to processing file after validate	
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[008]
	public ActionForward processData(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
			{

				String forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				
				TransactionUploadFormBean formBean = (TransactionUploadFormBean) form;
				TransactionProcessServicesImpl servObj = new TransactionProcessServicesImpl();
				logger.info(userBean.getUserName() + " requested marking the file ready for processing.");
				
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
					String isvalid = servObj.processFiles(formBean.getFileID(), Integer.valueOf(formBean.getTemplateId()),userBean.getEmployeeId());
					String filePath = formBean.getFilePath();
					String filename = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
					
					if(isvalid.equalsIgnoreCase("5"))
					{
						formBean.setProcessStatus(5);
						formBean.setMessage(bundle.getString("process.success"));
						logger.info("File " + filename + " Processed successfully.");
					}
					else if(isvalid.equalsIgnoreCase("0"))
					{
						formBean.setProcessStatus(2);
						formBean.setErrorMessage(bundle.getString("process.error"));
						formBean.setFileLink(filename);
						logger.info("File " + filename + " has Process errors.");
					}
					else
					{
						formBean.setProcessStatus(0);
						formBean.setErrorMessage(bundle.getString("process.exceptionError"));
						formBean.setFileLink(filename);
						logger.info("File " + filename + " has Process errors due to exception.");
					}
					
					formBean.setTransactionList(transactionTypeList);
					formBean.setMasterReferenceList(referenceMasterList);
					
					forwardMapping="BulkUploadPage";
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " in marking file for processing for " + userBean.getUserName() + ".");
					forwardMapping="BulkUploadPage";
				}
				
				return mapping.findForward(forwardMapping);
			}
		//END[008]
	/**
	 * @method	getFilledTemplate
	 * @purpose	to download filled template based on parameter	
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[010]
	public ActionForward getFilledTemplate(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception 
		    {
				String filePath , fileName , forwardMapping = null;
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				TransactionUploadFormBean formBean = (TransactionUploadFormBean) form;
				ErrorLogServiceImpl servObj = new ErrorLogServiceImpl();
				logger.info(userBean.getUserName() + " requested generating errorlog for file.");
				try
				{
					ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");					
					filePath = servObj.getFilledTemplate(formBean.getTransactionTemplate(),Integer.parseInt(formBean.getTemplateId()),formBean.getFlagParameter(),formBean.getLsiList());
					fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
					
					//formBean.setErrorMessage(bundle.getString("validate.error"));
					//formBean.setFileLink(fileName);
					
					formBean.setFileToDownload(filePath);
					
					formBean.setTransactionList(transactionTypeList);
					formBean.setMasterReferenceList(referenceMasterList);
					
					forwardMapping="BulkUploadPage";
					logger.info("Filled template successfully generated for file.");
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + " generating filled template " + userBean.getUserName() + ".");
					forwardMapping="ErrorPageAction";
				}
				
				return mapping.findForward(forwardMapping);
		    }
	//END[010]
	/**
	 * @method	searchFor
	 * @purpose	to display Search parameter list popup window
	 * @param 	mapping
	 * @param 	form
	 * @param 	request
	 * @param 	response
	 * @return 	forward action to jsp page
	 * @throws 	Exception
	 */
	//START[011]
			public ActionForward searchFor(ActionMapping mapping, 
			ActionForm form,
		    HttpServletRequest request, 
		    HttpServletResponse response)throws Exception
			{

				String forwardMapping = null;				
				HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
				HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
				UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
				try
				{					
					TransactionUploadFormBean formBean=(TransactionUploadFormBean)form;
					formBean.setSubChangeTypeId(request.getParameter("subChangeTypeId"));	
					forwardMapping="SearchForPage";
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + "for display search parameter values window" + userBean.getUserName() + ".");
					forwardMapping="BulkUploadPage";
				}
				
				return mapping.findForward(forwardMapping);
			}
//END[011]
			public ActionForward viewSelectedValue(ActionMapping mapping, 
					ActionForm form,
				    HttpServletRequest request, 
				    HttpServletResponse response)throws Exception
					{

						String forwardMapping = null;				
						HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
						HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
						UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
						try
						{					
							forwardMapping="viewBulkSelectedValue";
						}
						catch(Exception ex)
						{
							logger.error(ex.getMessage() + " Exception occured in " + this.getClass().getSimpleName() + "for display selected parameter values window" + userBean.getUserName() + ".");
							forwardMapping="BulkUploadPage";
						}
						
						return mapping.findForward(forwardMapping);
					}
			
			
			public ActionForward getTemplate(ActionMapping mapping, 
					ActionForm form,
				    HttpServletRequest request, 
				    HttpServletResponse response)throws Exception 
				    {
						String filePath , fileName , forwardMapping = null;
						HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
						HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
						UserInfoDto userBean  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
						TransactionUploadFormBean formBean = (TransactionUploadFormBean) form;
						ErrorLogServiceImpl servObj = new ErrorLogServiceImpl();
						logger.info(userBean.getUserName() + " requested generating errorlog for file.");
						try
						{
							ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");					
							//filePath = servObj.getFilledTemplate(formBean.getTransactionTemplate(),Integer.parseInt(formBean.getTemplateId()));
							filePath=formBean.getTransactionTemplate();
							//filePath=bundle.getString("excel.Template") ;
							System.out.println("path in getTemplate:"+filePath);
							fileName = filePath.substring((filePath.lastIndexOf("/") + 1),filePath.length());
							
							//formBean.setErrorMessage(bundle.getString("validate.error"));
							//formBean.setFileLink(fileName);
							
							formBean.setFileToDownload(filePath);
							
							formBean.setTransactionList(transactionTypeList);
							formBean.setMasterReferenceList(referenceMasterList);
							
							forwardMapping="BulkUploadPage";
							logger.info("Filled template successfully generated for file.");
						}
						catch(Exception ex)
						{
							logger.error(ex.getMessage() + "::BULKUPLOAD_ERROR::Exception occured in " + this.getClass().getSimpleName() + " generating filled template " + userBean.getUserName() + ".");
							forwardMapping="ErrorPageAction";
						}
						
						return mapping.findForward(forwardMapping);
				    }
			
			
			
			
}
	

