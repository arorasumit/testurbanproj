package com.ibm.ioes.ecrm;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.DbConnection;
import com.ibm.ioes.utilities.Messages;
import com.ibm.ioes.utilities.Utility;

public class ProcessECRMResponseXML {
	
	private static final int Fetch_Status_Found=1;
	
	final static String sql_UpdateStatusInResponse="{call IOE.spUpdateStatusM6Response(?,?)}";//"UPDATE [dbo].[tM6_NewOrder_Response] SET [status]=? ,modifiedDate=getdate() WHERE ID=?";
	final static String sql_UpdateBaseTables="{call IOE.SP_UPDATE_BASE_TABLE_M6_RESPONSE(?,?,?,?,?,?,?,?,?	)}";//"UPDATE [dbo].[tPOServiceMaster] SET [m6OrderNo]=?, [preOrderNo]=? WHERE serviceID=? and orderNo=? ";
	final static String sqlGetNextResponseXml="{call IOE.spGetM6ResponseNextXml()}";//" SELECT top 1 [ID], [JMS_MESSAGEID], [FILENAME] FROM [dbo].[tM6_NewOrder_Response] WHERE status=0 order by ID asc";
	public static String spInsertECRMtoIOMS = "{call IOE.SP_INSERT_ECRM_ACCOUNT_EAI(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public void processResponseJob()
	{
		System.err.println("in processResponseJob"); 
		
		int fetchXMLStatus=0;
		FetchCRMXMLDto crmxmldto =new FetchCRMXMLDto();
		Connection conn=null;
		int iStatus = 0;
		Connection iomsConn = null;
		CallableStatement csIOMS = null;

		try
		{
			//conn=DbConnection.getConnectionObject();
			iStatus = fetchNextXMLData(conn,crmxmldto);
			
			if(iStatus==1)
			{
				boolean isInserted = false;
				iomsConn = getConnectionObject();
				System.err.println("Inside Function....");
				csIOMS = iomsConn.prepareCall(spInsertECRMtoIOMS);
				int i=0;
				csIOMS.setString(++i , crmxmldto.getAccountNo());
				csIOMS.setString(++i, crmxmldto.getAccountName());
				csIOMS.setString(++i , "1");
				csIOMS.setLong(++i, Long.parseLong(crmxmldto.getAccAccountMangerId()));
				csIOMS.setLong(++i , Long.parseLong(crmxmldto.getAccProjectMangerId()));
				csIOMS.setString(++i , crmxmldto.getM6ShortName());
				csIOMS.setLong(++i , Long.parseLong(crmxmldto.getPartyId()));
				csIOMS.setLong(++i , Long.parseLong(crmxmldto.getVerticalId()));
				csIOMS.setLong(++i , Long.parseLong(crmxmldto.getAccountCategoryId()));
				csIOMS.setString(++i , crmxmldto.getAccountLob());
				csIOMS.setInt(++i, 0);
				csIOMS.setInt(++i, 0);
				csIOMS.setString(++i, "");
				csIOMS.execute();

	 
				if (csIOMS.getInt(12) == 0) 
				{
					iomsConn.commit();
				}
			}
			
		}
		catch(Exception ex)
		{
			/*try {
				conn.rollback();
			} catch (SQLException e) {
				Utility.onEx_LOG_RET_NEW_EX(ex, "processResponseJob", "processResponseXML", null, true, true);  
			}*/
			    Utility.onEx_LOG_RET_NEW_EX(ex, "processResponseJob", "processResponseXML", null, true, true);  
		}
		finally{
			try {
				DbConnection.closeCallableStatement(csIOMS);
				DbConnection.freeConnection(iomsConn);
				DbConnection.freeConnection(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Utility.onEx_LOG_RET_NEW_EX(e, "processResponseJob", "processResponseXML", null, true, true);  
			}
		}
		
		
	}


	public int fetchNextXMLData(Connection conn, FetchCRMXMLDto crmdto) throws Exception {
		int returnStatus=0;
		Statement stmt=null;
		  try {
					
			  		/*String readResponseXMLId="1001";//rs.getString("ID");
					//String fileName="accountinsert.xml";//rs.getString("FILENAME");
					
					String updateStatus=sql_UpdateStatusInResponse;//"UPDATE [dbo].[tM6_NewOrder_Response] SET [status]=1 ,modifiedDate=getdate() WHERE ID="+readResponseXMLId;
					PreparedStatement pstmt=conn.prepareStatement(updateStatus);
					pstmt.setShort(1, (short)1);
					pstmt.setString(2, readResponseXMLId);
					pstmt.execute(); */
					String absFileName=Messages.getMessageValue("CRM_RESPONSE_PATH")+"accountinsert.xml";
					//String absFileName="c:\\1863_LastTask_OSN.xml";
					//File file = new File("c:\\1863_LastTask_OSN.xml");
					File file = new File(absFileName);
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse(file);
					doc.getDocumentElement().normalize();
		  
					NodeList nodeLst = doc.getElementsByTagName("accountId");
					Node fstNode = nodeLst.item(0);
					Element Elmnt = (Element) fstNode;
					crmdto.setAcountId(Elmnt.getFirstChild().getNodeValue().trim());
					
					NodeList nodeLstaccountNumber = doc.getElementsByTagName("accountNumber");
					Node fstNodeaccountNumber = nodeLstaccountNumber.item(0);
					Element ElmntaccountNumber = (Element) fstNodeaccountNumber;
					crmdto.setAccountNo(ElmntaccountNumber.getFirstChild().getNodeValue().trim());

					NodeList nodeLstpartyName = doc.getElementsByTagName("partyName");
					Node fstNodepartyName = nodeLstpartyName.item(0);
					Element ElmntpartyName = (Element) fstNodepartyName;
					crmdto.setAccountName(ElmntpartyName.getFirstChild().getNodeValue().trim());
					
					NodeList nodeLstresourcemembers = doc.getElementsByTagName("resourcemembers");
					  for (int s = 0; s < nodeLstresourcemembers.getLength(); s++) 
					  {
						    NodeList nodeLstroleCode = doc.getElementsByTagName("roleCode");
							Node fstNoderoleCode = nodeLstroleCode.item(s);
							Element ElmntroleCode = (Element) fstNoderoleCode;
							
							if(ElmntroleCode!=null)
							{
								if(ElmntroleCode.getFirstChild().getNodeValue().trim().equalsIgnoreCase(AppConstants.ACCOUNTMANAGER))
								{
									crmdto.setAccAccountMangerId(doc.getElementsByTagName("resourceNumber").item(s).getFirstChild().getNodeValue().trim());
									crmdto.setAccAccountMangerName(doc.getElementsByTagName("resourceName").item(s).getFirstChild().getNodeValue().trim());
									
								}
								if(ElmntroleCode.getFirstChild().getNodeValue().trim().equalsIgnoreCase(AppConstants.PROJECTMANAGER))
								{
									crmdto.setAccProjectMangerId(doc.getElementsByTagName("resourceNumber").item(s).getFirstChild().getNodeValue().trim());
									crmdto.setAccProjectMangerName(	doc.getElementsByTagName("resourceName").item(s).getFirstChild().getNodeValue().trim());
									
								}
								
							}
					  }
					  crmdto.setRegionId(doc.getElementsByTagName("regionID").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setRegionName(doc.getElementsByTagName("regionCode").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setAccountLob(doc.getElementsByTagName("rrccCodeDescription").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setM6ShortName(doc.getElementsByTagName("accShortName").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setAccountStatus(doc.getElementsByTagName("accountStatus").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setAccountCategoryId(doc.getElementsByTagName("serviceSegmentId").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setAccountCategoryCode(doc.getElementsByTagName("serviceSegmentCode").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setVerticalId(doc.getElementsByTagName("industrySegmentId").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setVerticalCode(doc.getElementsByTagName("industrySegmentCode").item(0).getFirstChild().getNodeValue().trim());
					  crmdto.setPartyId(doc.getElementsByTagName("partyID").item(0).getFirstChild().getNodeValue().trim());
					 
					
					
				returnStatus=Fetch_Status_Found;
			
		  } 
			catch (Exception e) {
		    e.printStackTrace();
		  }
		 
		  return returnStatus;
	
	
	}

	public static void main(String[] args) {
		new ProcessECRMResponseXML().processResponseJob();
	}
	
	public static Connection getConnectionObject() throws Exception {
		Connection conn = null;
		try {
			DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
			conn = DriverManager.getConnection("jdbc:db2://10.24.62.159:50000/IOES_SEP", "db2admin","password");
			//conn = getURL();
			
			// System.err.println("conn2"+conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw (Exception) e;
		}
		return conn;
	}
}
