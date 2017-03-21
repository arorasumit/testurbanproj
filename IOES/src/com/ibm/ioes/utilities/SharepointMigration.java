package com.ibm.ioes.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Date;

import com.ibm.ioes.dao.NewOrderDao;
import com.ibm.ioes.forms.FileAttachmentDto;
import com.ibm.ioes.forms.SharepointDto;
import com.ibm.ioes.model.MigrationUtility;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import test.HttpsClient;


public class SharepointMigration {
	NewOrderDao objdao=new NewOrderDao();

	public void uploadAttachmentToDocLib() throws Exception {

		String methodName="uploadAttachmentToDocLib" , className= this.getClass().getName() ,msg="" ;
		boolean logToFile=true, logToConsole=true;
		String urlForFileUpload = Utility.getAppConfigValue("SHAREPOINT_URL_FORFILEUPLOAD");
		//ArrayList<FileAttachmentDto> getFileAttributes = new ArrayList<FileAttachmentDto>() ;	
		ArrayList<SharepointDto> getFileAttributes = new ArrayList<SharepointDto>() ;
		getFileAttributes=objdao.getEligibleFileAttachmentForShareptMigration();
		if(getFileAttributes.size()==0)
		{
			Utility.SPT_LOG(true, true, "No pending attachments for migration through scheduler");
		}
		else
		{
			String generatedXml = null;
			String	NewgeneratedXml=null;
			SharepointDto objDto =  new SharepointDto();
			for(int size=0;size<getFileAttributes.size();size++)
			{
				int SlNo = getFileAttributes.get(size).getSlno();
				objDto.setSlno(SlNo);
				Utility.SPT_LOG(true, true, "******************************"+SlNo+"***********************************");
				System.out.println(SlNo);                                                               //for testing
				java.sql.Blob blob = objdao.getFileDataForMigration(SlNo);
				//java.sql.Blob blob = getFileAttributes.get(size).getFile();
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				String base64data=DatatypeConverter.printBase64Binary(blobAsBytes);
				//System.out.println("printbase64binary function base64data is----------->"+base64data);
				
				generatedXml= objdao.getXMLAttributesForFile(SlNo);
				System.out.println("old xml is ------------------>"+ generatedXml);                     // for testing
				
				Utility.SPT_LOG(true, true, " XML without body IS-->"+generatedXml);
				
				if(generatedXml.contains("<Body>"))
				{
					NewgeneratedXml = generatedXml.replace("<Body>", "<Body>"+base64data);
				}
				String responseFromSharepoint=	HttpsClient.postXmlToSharepoint(NewgeneratedXml,urlForFileUpload);
				
				Utility.SPT_LOG(true, true, "RESPONSE XML IS-->"+""+SlNo+""+ responseFromSharepoint); 
				
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(responseFromSharepoint));
				Document doc = db.parse(is);
				if(doc.getElementsByTagName("TransactionId").item(0)!=null)
				{
					NodeList TransactionId = doc.getElementsByTagName("TransactionId").item(0).getChildNodes();					
					Node nValueTransiD = (Node) TransactionId.item(0);
					if(nValueTransiD!=null){
						String TransId= nValueTransiD.getNodeValue();
						objDto.setTransactionId(TransId);
						//System.out.println("tRANSACTION id is: " + TransId);                               //for testing
						Utility.SPT_LOG(true, true, "tRANSACTION id FROM RESPONSE is: " + TransId); 
					}

				}
				if(doc.getElementsByTagName("Status").item(0)!=null)
				{
					NodeList status = doc.getElementsByTagName("Status").item(0).getChildNodes();					
					Node nValueofStatus = (Node) status.item(0);
					if(nValueofStatus!=null){
						String statusOfXml= nValueofStatus.getNodeValue();
						objDto.setStatus(statusOfXml);
						//System.out.println("status of xml is: " + statusOfXml);
						Utility.SPT_LOG(true, true, "status of xml is: " + statusOfXml);
						
					}

				}
				if(doc.getElementsByTagName("CurrentVersionUrl").item(0)!=null)
				{
					NodeList urlReceived = doc.getElementsByTagName("CurrentVersionUrl").item(0).getChildNodes();					
					Node nValueofUrl = (Node) urlReceived.item(0);
					if(nValueofUrl!=null){
						String url= nValueofUrl.getNodeValue();
						objDto.setSharepointUrl(url);
						System.out.println(" url  is: " + url);
						Utility.SPT_LOG(true, false, "url is: " + url);
						//String domainName= Utility.getAppConfigValue("SHAREPOINT_URL_DOMAIN");
						//String completeUrl= domainName+url;
						//Utility.SPT_LOG(true, false, "Complete url is: " + completeUrl);
						//objDto.setSharepointUrl(completeUrl);
						
					}
				}
				if(doc.getElementsByTagName("ExMessage").item(0)!=null)
				{
					NodeList exMsg = doc.getElementsByTagName("ExMessage").item(0).getChildNodes();					
					Node nexMsg = (Node) exMsg.item(0);
					if(nexMsg!=null){
						String exception= nexMsg.getNodeValue();
						objDto.setException(exception);
						System.out.println(" exception  is: " + exception);
						Utility.SPT_LOG(true, true, "exception is: " + exception);
					}
				}

				if(objDto.getStatus().equals("SUCCESS"))
				{
					String message=updateTablewithSharepointUrl(objDto);
					//open a transaction delete the filedata corresponding to the slno from table not as of now
					// dao query to update the url and status as US in db
					System.out.println("result is--------->"+ message);
					Utility.SPT_LOG(true, true, "result is--------->"+ message);
					
				}
				else 
				{
					String message=updateTableWithErrorStatus(objDto);
					// mark the status as ERR in db and increment the retry_count
					System.out.println("result is--------->"+ message);
					Utility.SPT_LOG(true, true, "result is--------->"+ message);
					
				}

			}
		}
	}		

	private String updateTableWithErrorStatus(SharepointDto objDto) throws Exception{
		String message;
		int rowsUpdatedwithErrorStatus= objdao.updateRetryCountandErrorStatus(objDto);
		if(rowsUpdatedwithErrorStatus>0)
		{
			message="retry count and error status updated successfully";
		}
		else
		{
			message="error in updating retry_count";
		}
		return message;
	}

	private String updateTablewithSharepointUrl(SharepointDto objDto) throws Exception{
		int deleteCountResult;
		int insertStatus;
		String finalresult=null;
		Connection connection=null;
		try
		{
			connection = DbConnection.getConnectionObject();
			connection.setAutoCommit(false);

			insertStatus= objdao.updateSharepointUrlInTable(objDto,connection);
			//connection.commit();
			deleteCountResult =objdao.deleteFileAttachmentFromTable(objDto,connection);
			if(insertStatus>0 && deleteCountResult>0)
			{
				finalresult="successfully updated";
				connection.commit();
			}
			else
			{
				finalresult="error in updating Sharepoint Url";
				connection.rollback();
			}
			 
			/*if((insertStatus>0 ))
			{
				finalresult="successfully updated";
				connection.commit();
			}
			else
			{
				finalresult="error in updating Sharepoint Url";
				connection.rollback();
			}*/

		}
		catch(Exception ex)
		{
			try {
				connection.rollback();
				finalresult="error in updating Sharepoint Url";
			} catch (Exception e) {
				//Utility.LOG(true, true, ex, "in updateTablewithSharepointUrl method of SharepointMigration");
				Utility.SPT_LOG(true, true, ex, "in updateTablewithSharepointUrl method of SharepointMigration");
				
			}
			ex.printStackTrace();	
		}
		finally{

			try{
				DbConnection.freeConnection(connection);
			} 
			catch (Exception e){
				finalresult="error in updating Sharepoint Url";
				//Utility.LOG(true, true,e,"in updateTablewithSharepointUrl method of SharepointMigration");
				Utility.SPT_LOG(true, true, e, "in updateTablewithSharepointUrl method of SharepointMigration");
				
			}
		}
		return finalresult;
	}

	public static void main (String args[]) throws Exception
	{
		System.out.println("main started");
		try{

			SharepointMigration mig = new SharepointMigration();
			System.out.println("started at ------------->"+ new Date());
			mig.uploadAttachmentToDocLib();
			System.out.println("ended at-------------->"+ new Date());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}



}
