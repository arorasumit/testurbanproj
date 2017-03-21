package com.ibm.ioes.npd.utilities;

import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import sun.misc.BASE64Encoder;

import com.ibm.appsecure.util.Encryption;

/**
 * @author Vivek jadohn
 * 
 */
public class SMSUtil {
	
	static PostMethod postMethod = null;

	static HttpClient client = null;

	// ArrayList mobileNumberList=null;
	//	
	// String messege=null;

	private static final Logger ExosureLOGGER_SendSMSUtility;

	static {
		ExosureLOGGER_SendSMSUtility = Logger.getLogger(SMSUtil.class);
	}

	public SMSUtil() {
		try {
			client = new HttpClient();
			//client.setHttpConnectionManager(new MultiThreadedHttpConnectionManager()); comment by anil for testing
			client.getHostConfiguration();
			Encryption encryption = new Encryption();
			String smsc_ip = GetPropertiesUtility.getProperty("smsc.ip");
			String smsc_port = GetPropertiesUtility.getProperty("smsc.port");
			//String userId = encryption.decrypt(GetPropertiesUtility.getProperty("smsc.loginId"), AppConstants.ENCRYPTION_KEY);
			//String password = encryption.decrypt(GetPropertiesUtility.getProperty("smsc.password"), AppConstants.ENCRYPTION_KEY);
			
			String userId =GetPropertiesUtility.getProperty("smsc.loginId");
			String password = GetPropertiesUtility.getProperty("smsc.password");

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
			// set the Mobile no into the Document object at the number Node
			mobileElementObj.setText(mobileNo);
			Element UdElementObj = (Element) XPath.selectSingleNode(doc, "//ud");
			// set the Message into the Document object at the ud Node
			UdElementObj.setText(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	
	public void sendingSMS(String mobileNumber, String messege) {
		try {
//			CreateXML createxml = new CreateXML();
			Document doc = createXML("NPD");
			// Calling Xpath method for inserting the message and mobile no.
			doc = useXPath(doc, mobileNumber, messege);
			XMLOutputter serializer = new XMLOutputter();

			// Setting the XML String for SMS.
			String placeHolderString = serializer.outputString(doc);

			StringRequestEntity requestEntity = new StringRequestEntity(placeHolderString);
			//postMethod.setRequestEntity(requestEntity); comment by anil for testing
			client.executeMethod(postMethod);
			// Print Response Body
			String responseString = postMethod.getResponseBodyAsString();
			//System.out.println("Reponse String:"+responseString);
			if(responseString.equalsIgnoreCase("OK")){
				ExosureLOGGER_SendSMSUtility.info("Sending messege success to mobile number: " + mobileNumber);
			}else{
				ExosureLOGGER_SendSMSUtility.error("Sending messege fail to mobile number->:" + mobileNumber +":<- ,responseString:"+responseString);
			}
			
		} catch (Exception e) {
			ExosureLOGGER_SendSMSUtility.error("Sending messege failed to mobile number: " + mobileNumber);
			ExosureLOGGER_SendSMSUtility.error("Send SMS Fails : " + AppUtility.getStackTrace(e));
		}

	}

	public void sendSMS(ArrayList mobileNumberList, String messege) {
		if (mobileNumberList != null && messege != null) {
			if (messege.length() <= 256) {
				for (int i = 0; i < mobileNumberList.size(); i++) {
					String mobileNumber = (String) mobileNumberList.get(i);
					if (mobileNumber!=null && mobileNumber.length() == 10) {
						sendingSMS(mobileNumber, messege);
					} else if (mobileNumber!=null && mobileNumber.length() > 10) {
						sendingSMS(mobileNumber, messege);
					} else {
						ExosureLOGGER_SendSMSUtility.info("Incorret mobile number");
					}

				}
			} else {
				ExosureLOGGER_SendSMSUtility.info("Messege can't be send as it is too big i.e. more than 256 character");
			}

		} else {
			ExosureLOGGER_SendSMSUtility.info("No messege to send");
		}
	}
	
	private  Document createXML(String Source) {
		
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
        //adding it to sms tag
		mobile.addContent(Ud);
    	//prepare the DOM document
		doc = new Document(root);
		return doc;
     }
}
