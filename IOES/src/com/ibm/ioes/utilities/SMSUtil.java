package com.ibm.ioes.utilities;

import java.util.ArrayList;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import com.ibm.appsecure.util.Encryption;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.jdom.xpath.XPath;
import sun.misc.BASE64Encoder;

import com.ibm.appsecure.util.Encryption;

/**
 * @author Vivek jadohn
 * 
 */
public class SMSUtil {

	private static final Logger logger;
	static {
		logger = Logger.getLogger(SMSUtil.class);
	}
	static PostMethod postMethod = null;

	static HttpClient client = null;
	private static final String SEND_SMS = "Y";

	// ArrayList mobileNumberList=null;
	//	
	// String messege=null;

	private static final Logger ExosureLOGGER_SendSMSUtility;

	static {
		ExosureLOGGER_SendSMSUtility = Logger.getLogger(SMSUtil.class);
	}

	public SMSUtil() {
		try {
			HttpClient client1 = new HttpClient();
		
			client = new HttpClient();
			client.setHttpConnectionManager(new MultiThreadedHttpConnectionManager());
			client.getHostConfiguration();
			Encryption encryption = new Encryption();
			String smsc_ip = Messages.getMessageValue("smsc.ip");
			String smsc_port = Messages.getMessageValue("smsc.port");
			//String userId = encryption.decrypt(GetPropertiesUtility.getProperty("smsc.loginId"), AppConstants.ENCRYPTION_KEY);
			//String password = encryption.decrypt(GetPropertiesUtility.getProperty("smsc.password"), AppConstants.ENCRYPTION_KEY);
			
			
			
			String userId = Messages.getMessageValue("smsc.loginId");
			String password = Messages.getMessageValue("smsc.password");

			String url = "http://" + smsc_ip + ":" + smsc_port;
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "text/xml");
			postMethod.setRequestHeader("Authorization", "Basic " + encode(userId + ":" + password));

		} catch (Exception e) {
			ExosureLOGGER_SendSMSUtility.info("Error in connection");
			ExosureLOGGER_SendSMSUtility.error("Send Email Fails : " + AppUtility.getStackTrace(e));
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to encode the UserName and Password String.
	 * 
	 * @param source
	 * @return
	 */
	private static String encode(String source) {
		BASE64Encoder enc = new sun.misc.BASE64Encoder();
		return (enc.encode(source.getBytes()));
	}

	/**
	 * Method for inserting the message and mobile no.
	 * 
	 * @param doc
	 * @param mobileNo
	 * @param message
	 * @return
	 */

	private static Document useXPath(Document doc, String mobileNo, String message) {
		// Using XPath
		try {
			Element mobileElementObj = (Element) XPath.selectSingleNode(doc, "//number");
			//System.out.println("Before mobile");
			//set the Mobile no into the Document object at the number Node
			//mobileElementObj.setText(mobileNo);
			//System.out.println("After mobile");
			//Element UdElementObj = (Element) XPath.selectSingleNode(doc, "//ud");
			// set the Message into the Document object at the ud Node
			//UdElementObj.setText(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	
	public int  sendingSMS(String mobileNumber, String messege) {
		try {
			logger.info("SMSUtil's sendingSMS method....");
			logger.info("SEND_SMS :"+SEND_SMS);
			// CreateXML createxml = new CreateXML();
			String responseString = ""; 
			if(SEND_SMS.equalsIgnoreCase("Y"))
			{
			Document doc = createXML("IB2B",mobileNumber,messege);
			System.out.println("doc "+doc.getContent().toString());
			// Calling Xpath method for inserting the message and mobile no.
			//doc = useXPath(doc, mobileNumber, messege);
			XMLOutputter serializer = new XMLOutputter();

			// Setting the XML String for SMS.
			String placeHolderString = serializer.outputString(doc);

			StringRequestEntity requestEntity = new StringRequestEntity(placeHolderString);
			postMethod.setRequestEntity(requestEntity);
			
				
				logger.info("Sending SMS.......");
				client.executeMethod(postMethod);
				logger.info("SMS Sent.......");
				
			// Print Response Body
				responseString = postMethod.getResponseBodyAsString();
				logger.info("Reponse String:"+responseString);
		}
			if(responseString.equalsIgnoreCase("OK")){
				System.err.println("Sending messege success to mobile number: " + mobileNumber);
				logger.info("Sending messege success to mobile number: " + mobileNumber);
				return 1;
			}else{
				System.err.println("Sending messege fail to mobile number: " + mobileNumber);
				logger.info("Sending messege success to mobile number: " + mobileNumber);
				return -1;
			}
			
		} catch (Exception e) {
			logger.info("Sending messege failed to mobile number: " + mobileNumber);
			logger.info("Send SMS Fails : " + AppUtility.getStackTrace(e));
			return -1;
		}

	}

	public void sendSMS(ArrayList mobileNumberList, String messege) {
		if (mobileNumberList != null && messege != null)
		{
			//if (messege.length() <= 300) 
			//{
				for (int i = 0; i < mobileNumberList.size(); i++) {
					try
					{
					String mobileNumber = (String) mobileNumberList.get(i);
					if (mobileNumber!=null && mobileNumber.length() == 10)
					{
						sendingSMS(mobileNumber, messege);
					} else if (mobileNumber!=null && mobileNumber.length() > 10) 
					{
						sendingSMS(mobileNumber, messege);
					} else 
					{
						ExosureLOGGER_SendSMSUtility.info("Incorret mobile number");
					}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						System.err.println("Cannot send messge to no:"+mobileNumberList.get(i));
					}

				//}
			} 
				//else {
				//ExosureLOGGER_SendSMSUtility.info("Messege can't be send as it is too big i.e. more than 256 character");
			//}

		} else {
			ExosureLOGGER_SendSMSUtility.info("No messege to send");
		}
	}
	
	/*private  Document createXML(String Source) {
		logger.info("SMSUtil's createXML method....");
		Document doc=null;
		//Creating the root element.
		Element root = new Element("message");
		Element mobile = new Element("sms");
		root.addContent(mobile);
		 
		mobile.setAttribute("type", "mt");
		Element destination = new Element("destination");
		//adding destination to sms tag
		mobile.addContent(destination);
		destination.setAttribute("messageid","PS0");
		Element address = new Element("address");
		//adding Address to the Destination tag.
		destination.addContent(address);
		Element numberType = new Element("number");
		//adding number to the Address tag.
		address.addContent(numberType);
		
		numberType.setAttribute("type","international");
		numberType.setText("9560193929");
		Element source = new Element("source");
		//adding source to sms tag
		mobile.addContent(source);
		Element sourceAddress= new Element("address");
		//adding SourceAddress to the source tag.
		source.addContent(sourceAddress);
		Element alphanumeric = new Element("alphanumeric");
		sourceAddress.addContent(alphanumeric);
		
		alphanumeric.setText("Airtel");
		Element rsrType= new Element("rsr");
		rsrType.setAttribute("type", "SUCCESS_FAILURE");
		//adding it to sms tag
		mobile.addContent(rsrType);
		Element Ud= new Element("ud");
		Ud.setAttribute("type", "text");
		Ud.setText("Message for IP Change testing");
        //adding it to sms tag
		mobile.addContent(Ud);
    	//prepare the DOM document
		doc = new Document(root);
		return doc;
     }*/
	
	private  Document createXML(String Source,String mobileNumber, String message) {
		logger.info("SMSUtil's createXML method....");
		Document doc=null;
		//Creating the root element.
		Element root = new Element("message");
		Element mobile = new Element("sms");
		root.addContent(mobile);
		 
		mobile.setAttribute("type", "mt");
		Element destination = new Element("destination");
		//adding destination to sms tag
		mobile.addContent(destination);
		destination.setAttribute("messageid","PS0");
		Element address = new Element("address");
		//adding Address to the Destination tag.
		destination.addContent(address);
		Element numberType = new Element("number");
		//adding number to the Address tag.
		address.addContent(numberType);
		
		numberType.setAttribute("type","international");
		numberType.setText(mobileNumber);
		Element source = new Element("source");
		//adding source to sms tag
		mobile.addContent(source);
		Element sourceAddress= new Element("address");
		//adding SourceAddress to the source tag.
		source.addContent(sourceAddress);
		Element alphanumeric = new Element("alphanumeric");
		sourceAddress.addContent(alphanumeric);
		
		alphanumeric.setText("Airtel");
		Element rsrType= new Element("rsr");
		rsrType.setAttribute("type", "SUCCESS_FAILURE");
		//adding it to sms tag
		mobile.addContent(rsrType);
		Element Ud= new Element("ud");
		Ud.setAttribute("type", "text");
		Ud.setText(message);
        //adding it to sms tag
		mobile.addContent(Ud);
    	//prepare the DOM document
		doc = new Document(root);
		return doc;
     }
}
