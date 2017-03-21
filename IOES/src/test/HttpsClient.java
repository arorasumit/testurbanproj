package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class HttpsClient{

  public static String postXmlToSharepoint(String xml,String urlForFileUpload) throws Exception
  {
	  String xmlToPost=xml;
	  String responseStringFromSharepoint ;
        new HttpsClient().testIt(urlForFileUpload);     
        DefaultHttpClient httpClient = new DefaultHttpClient();
		try
	    {
	        //Define a postRequest request
			//String urlForFileUpload = Messages.getMessageValue("SHAREPOINT_URL_FORFILEUPLOAD");
			//String urlForFileUpload = Utility.getAppConfigValue("SHAREPOINT_URL_FORFILEUPLOAD");
			HttpPost postRequest = new HttpPost(urlForFileUpload);
	       // HttpPost postRequest = new HttpPost("https://dmstestapi.airtel.com/dev/api/docs"); ///Configurable	         
	        postRequest.addHeader("content-type", "application/xml");
	        postRequest.addHeader("accept", "application/xml");
	       // System.out.println("xml being posted------>"+xmlToPost);
	         
	        /// To be sent through Parameter/class object
	        // StringEntity userEntity = new StringEntity("<RequestDocumentUpload><DocumentType>CID</DocumentType><UserName>AB KAM</UserName><UserNumber>ABKAM1</UserNumber><CustomerNumber>ab1-0000040355</CustomerNumber><CustomerName>Testing Services Limited UAT</CustomerName><OpportunityNumber /><OpportunityName /><OrderNumber /><OrderName /><OpportunityCreateDate /><LOB>EGB</LOB><Role /><TransactionId>747493749</TransactionId><Level>Customer</Level><Document><DocumentName>CID_ab1-0000040351.txt</DocumentName><ExtensionType /><Body>6664736dfdsfsdfsadf7666468646668</Body></Document></RequestDocumentUpload>");
	        StringEntity xmlEntity = new StringEntity(xmlToPost);
	       // StringEntity userEntity = new StringEntity ("  <RequestDocumentUpload><DocumentType>OTHERS</DocumentType><UserName>Pramod</UserName><UserNumber>B0074427</UserNumber><CustomerNumber>2285375</CustomerNumber><CustomerName>Test AES Company</CustomerName><OpportunityNumber>5370607</OpportunityNumber><OpportunityName>5370607</OpportunityName><OpportunityCreateDate></OpportunityCreateDate><OrderNumber>3027948</OrderNumber><OrderName>3027948</OrderName><OrderCreateDate>11/20/2014</OrderCreateDate><LOB>AES CORP</LOB><Role></Role><TransactionId>IB2B_5001871</TransactionId><Level>Customer</Level><Document><DocumentName>network.txt</DocumentName><ExtensionType>txt</ExtensionType><Body>NzA3MjZmNzg3OTBkMGEzMTMwMmUzMTM0MmUzOTM3MmUzMjM0MjAyMDM0MzEzNDM1MGQwYTBkMGEwZDBhMGQwYTcwNzQ2ODYxNmI3NTcyMzA0MDY5NmUyZTY5NjI2ZDJlNjM2ZjZk</Body></Document></RequestDocumentUpload>");
	        postRequest.setEntity(xmlEntity);
	          
	        HttpResponse response = httpClient.execute(postRequest);
	        
	        responseStringFromSharepoint  = EntityUtils.toString(response.getEntity(), "UTF-8");
	        System.out.println("Response String is--------------->"+responseStringFromSharepoint);
	        
	        //verify the valid error code first
	        int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != 200) 
	        {
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	    }
	    finally
	    {
	        //Important: Close the connect
	        httpClient.getConnectionManager().shutdown();
	    }
         return responseStringFromSharepoint;
  }

  private TrustManager[ ] get_trust_mgr() {
     TrustManager[ ] certs = new TrustManager[ ] {
        new X509TrustManager() {
           public X509Certificate[ ] getAcceptedIssuers() { return null; }
           public void checkClientTrusted(X509Certificate[ ] certs, String t) { }
           public void checkServerTrusted(X509Certificate[ ] certs, String t) { }
         }
      };
      return certs;
  }

  private void testIt(String https_url){
    // String https_url = Messages.getMessageValue("SHAREPOINT_URL_FORFILEUPLOAD");
     URL url;
     try {

	    // Create a context that doesn't check certificates.
            SSLContext ssl_ctx = SSLContext.getInstance("TLS");
            TrustManager[ ] trust_mgr = get_trust_mgr();
            ssl_ctx.init(null,                // key manager
                         trust_mgr,           // trust manager
                         new SecureRandom()); // random number generator
            HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());

	    url = new URL(https_url);
	    HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

	    // Guard against "bad hostname" errors during handshake.
            con.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String host, SSLSession sess)
                {
                	return false;
                }
            });

	    //dumpl all cert info
	    print_https_cert(con);

	    //dump all the content
	    print_content(con);

	 } catch (MalformedURLException e) {
		 
		 Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(e, "testIt", "HttpsClient", "", true, true);
		e.printStackTrace();
	 } catch (IOException e) {
		e.printStackTrace();
		 Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(e, "testIt", "HttpsClient", "", true, true);
	 }catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
		 Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(e, "testIt", "HttpsClient", "", true, true);
	 }catch (KeyManagementException e) {
		e.printStackTrace();
		 Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(e, "testIt", "HttpsClient", "", true, true);
      }
   }

  private void print_https_cert(HttpsURLConnection con){
     if(con!=null){

     try {

	System.out.println("Response Code : " + con.getResponseCode());
	System.out.println("Cipher Suite : " + con.getCipherSuite());
	System.out.println("\n");

	Certificate[] certs = con.getServerCertificates();
	/*for(Certificate cert : certs){
	  System.out.println("Cert Type : " + cert.getType());
	  System.out.println("Cert Hash Code : " + cert.hashCode());
	  System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
	  System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
	  System.out.println("\n");
	}*/


     } catch (SSLPeerUnverifiedException e) {
	  e.printStackTrace();
	  Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(e, "testIt", "HttpsClient", "", true, true);
     } catch (IOException e){
	  e.printStackTrace(); Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(e, "testIt", "HttpsClient", "", true, true);
	  
     }
   }
  }

  private void print_content(HttpsURLConnection con){
    if(con!=null){

    try {

	System.out.println("****** Content of the URL ********");

	BufferedReader br =
		new BufferedReader(
			new InputStreamReader(con.getInputStream()));

	String input;

	while ((input = br.readLine()) != null){
	   System.out.println(input);
	}
	br.close();

     } catch (IOException e) {
	e.printStackTrace();
	 Utility.onEx_LOG_RET_NEW_EX_SHAREPOINT(e, "testIt", "HttpsClient", "", true, true);
     }
   }
  }
}