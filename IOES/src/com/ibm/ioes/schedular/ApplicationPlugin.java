//[001] SANTOSH SRIVASTAVA created this file on 01-12-2013 for running schedulrs on their specification bases
//[002]	 	VIPIN SAHARIA	13-Apr-15	CBR_20150202-R2-021036		Sending ePCD and SMS alerts to customer at every order life cycle stage
//[003]  Raveendra Billing efficiency
//[004]  NANCY SINGLA 21-OCT-2016 20160719-R2-022519                       ADDED SHAREPOINT SCHEDULER
package com.ibm.ioes.schedular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import com.ibm.appsecure.plugin.QueryPlugin;
import com.ibm.fx.mq.ApplicationClone;
import com.ibm.fx.mq.FXSchedulerPlugIn;
import com.ibm.ioes.clep.CLEPPlugIn;
import com.ibm.ioes.ei.m6.M6PlugIn;
import com.ibm.ioes.service.UtilityService;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.ECRM_PlugIn;
import com.ibm.ioes.utilities.ECRM_SingleView_PlugIn;
import com.ibm.ioes.utilities.Sharepoint_Plugin;
import com.ibm.ioes.utilities.UtilityPlugIn;
import org.apache.log4j.Logger;
import com.ibm.ioes.utilities.Utility;
public class ApplicationPlugin implements PlugIn 
{

	public void init(ActionServlet actionServlet, ModuleConfig moduleConfig)throws ServletException
	{
	
	try
		{
				
				File  file = new File(System.getProperty("user.install.root").concat("/").concat("logs")+"/"+com.ibm.websphere.runtime.ServerName.getDisplayName()+"/SchedularDetails.txt" );
				AppConstants.IOES_LOGGER.info("--------------------------------------Schedulers are starting  ---------------------------------------");
				AppConstants.IOES_LOGGER.info("*******                               Server Name is: "+com.ibm.websphere.runtime.ServerName.getDisplayName()+"                              ***********");
				AppConstants.IOES_LOGGER.info("------------------------------------------------------------------------------------------------------");
				FileInputStream fstream = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				 String strLine;
				 String fileData = "";
				 while ((strLine = br.readLine()) != null)   {
					   
						   fileData = fileData + strLine;
				 }
				 fstream.close();  
							 
				 startSpecificSchedulers(fileData, actionServlet, moduleConfig);
		 
	} catch (Exception exception){

		AppConstants.IOES_LOGGER.info("=======================================================================");
		AppConstants.IOES_LOGGER.info("***                Shedulers are not applicable                     ***");
		AppConstants.IOES_LOGGER.info("=======================================================================");

	}
		AppConstants.IOES_LOGGER.info("--------------------------------------Schedulers end here--------------------------------------");
	}
		public void startSpecificSchedulers(String schedulerType,  ActionServlet actionServlet, ModuleConfig moduleConfig) {
			
			M6PlugIn m6PlugIn = new M6PlugIn();
			QueryPlugin queryPlugin = new QueryPlugin();
			ECRM_PlugIn eCRM_PlugIn = new ECRM_PlugIn();
			
			
			ECRM_SingleView_PlugIn eCRM_SingleView_PlugIn = new ECRM_SingleView_PlugIn();
			
			FXSchedulerPlugIn fXSchedulerPlugIn = new FXSchedulerPlugIn();
			UtilityPlugIn utilityPlugIn = new UtilityPlugIn();
			AutoRenewalMailPlugin autoRenewalMailPlugin = new AutoRenewalMailPlugin();
			CLEPPlugIn cLEPPlugIn = new CLEPPlugIn();
			AutoCreationDisOrderPlugin autoCreationDisOrderPlugin = new AutoCreationDisOrderPlugin();
			CancelAutoDisOrderPlugin cancelAutoDisOrderPlugin = new CancelAutoDisOrderPlugin();
			DraftOrderMailPlugin draftOrderMailPlugin = new DraftOrderMailPlugin();
			RMEscalationMailPlugin rMEscalationMailPlugin = new RMEscalationMailPlugin();
			DemoDaysDisconnectionPlugin demoDaysDisconnectionPlugin = new DemoDaysDisconnectionPlugin();
			
			BulkUploadPlugin bulkUploadPlugin = new BulkUploadPlugin();
			DemoDaysAlertPlugin demoDaysAlertPlugin = new DemoDaysAlertPlugin();
			LepmLocDataUpdatePlugin lepmLocDataUpdatePlugin = new LepmLocDataUpdatePlugin();
			//obvalueautomation by nagarjuna
			OBValueAutomationPlugin obvalueAutomationplugin = new OBValueAutomationPlugin();
			CustomerMailSMSAlertsPlugin ePCDcustAlertPlugin = new CustomerMailSMSAlertsPlugin();
			PluginClassForTaskRunningOnceInADay onceinadayrunningPlugin = new PluginClassForTaskRunningOnceInADay();
			//Start [003]
			ProvisionMailPlugin provisionMailPlugIn=new ProvisionMailPlugin();
			//End [003]
			
			//[004] START
			Sharepoint_Plugin shareptPlugin = new Sharepoint_Plugin();
			//[004] END
			
			try {
				
				String  cLEPPlugInSch = Utility.getAppConfigValue("CLEPPLUGIN_SCHEDULER");
				String  autoCreationDisOrderPluginSch = Utility.getAppConfigValue("AUTOCREATIONDISORDERPLUGIN_SCHEDULER");
				String  cancelAutoDisOrderPluginSch = Utility.getAppConfigValue("CANCELAUTODISORDERPLUGIN_SCHEDULER");
				String  draftOrderMailPluginSch = Utility.getAppConfigValue("DRAFTORDERMAILPLUGIN_SCHEDULER");
				String  rMEscalationMailPluginSch = Utility.getAppConfigValue("RMESCALATIONMAILPLUGIN_SCHEDULER");
				String  demoDaysDisconnectionPluginSch = Utility.getAppConfigValue("DEMODAYSDISCONNECTIONPLUGIN_SCHEDULER");
			
				String  m6PluginSch = Utility.getAppConfigValue("M6PLUGIN_SCHEDULER");
				String  queryPluginSch = Utility.getAppConfigValue("QUERYPLUGIN_SCHEDULER");
				String  eCRM_PlugInSch = Utility.getAppConfigValue("ECRMPLUGIN_SCHEDULER");
				
				String  eCRM_SingleViewPlugInSch = Utility.getAppConfigValue("ECRM_SINGLE_VIEW_PLUGIN_SCHEDULER");
				
				String  fXSchedulerPlugInSch = Utility.getAppConfigValue("FXSCHEDULERPLUGIN_SCHEDULER");
				String  utilityPlugInSch = Utility.getAppConfigValue("UTILITYPLUGIN_SCHEDULER");
				String  autoRenewalMailPluginSch = Utility.getAppConfigValue("AUTORENEWALMAILPLUGIN_SCHEDULER");
				
				String bulkUploadSch = Utility.getAppConfigValue("BULKUPLOADPLUGIN_SCHEDULER");
				String demoDaysAlertSch = Utility.getAppConfigValue("DEMODAYSALERT_SCHEDULER");
				String lemoLocalDataSch = Utility.getAppConfigValue("LEMOLOCALDATA_SCHEDULER");
				//OBValueAutomation by nagarjuna
				String obvalueAutomationScheduler = Utility.getAppConfigValue("OBVALUEAUTOMATIONPLUGIN_SCHEDULER");
				String fXCurrecySchedulerPlugInSch = Utility.getAppConfigValue("EXCHANGERATE_CURRENCY_SCHEDULER_PLUGIN");;
				String  fXNrcSchedulerPlugInSch = Utility.getAppConfigValue("FX_NRC_SCHEDULERPLUGIN_SCHEDULER");
				String  fXComponentSchedulerPlugInSch = Utility.getAppConfigValue("FX_COMPONENT_SCHEDULERPLUGIN_SCHEDULER");
				String ePCDCustAlertsScheduler = Utility.getAppConfigValue("ePCD_CUST_ALERTS_SCHEDULER");
				String runningonceinadaySch=Utility.getAppConfigValue("ONCE_IN_A_DAY_RUNNING_SCHEDULER");
				//Start[003]
				String provisionPlugInScheduler=Utility.getAppConfigValue("PROVISION_MAIL_SCHEDULER");
				//End[003]
				//[004] start
				String sharepointMigrationScheduler = Utility.getAppConfigValue("SHAREPOINT_SCHEDULER");
				
				if(schedulerType.equalsIgnoreCase(sharepointMigrationScheduler)) {
					try
					{
					
						shareptPlugin.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----         SHAREPOINT scheduler is running               ------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.SPT_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.SPT_LOGGER.info("----         SHAREPOINT scheduler is running               ------");
						AppConstants.SPT_LOGGER.info("-----------------------------------------------------------------------");
					
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############     Problem in starting SHAREPOINT  SCHEDULER        ########");
						AppConstants.SPT_LOGGER.info("############     Problem in starting SHAREPOINT  SCHEDULER        ########");
					}
				} 
				//[004] end
				
				
				
	         	if(schedulerType.equalsIgnoreCase(cLEPPlugInSch)) {
				try
				{
				
					cLEPPlugIn.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----         CLEPPlugIn scheduler is running               ------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
				
				}
				catch (Exception exception)
				{
				
					AppConstants.IOES_LOGGER.info("############     Problem in starting CLEPSheduler        ########");
				}
			}  if(schedulerType.equalsIgnoreCase(autoCreationDisOrderPluginSch)) {	
				try 
				{
				
					autoCreationDisOrderPlugin.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----     AtoCreationDisOrderPlugin scheduler is running         -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

			
				}
				catch (Exception exception)
				{
				
					AppConstants.IOES_LOGGER.info("############    Problem in starting AutocreationSheduler      ########");
				}
			}  if(schedulerType.equalsIgnoreCase(cancelAutoDisOrderPluginSch)) {	
				try
				{
				
					cancelAutoDisOrderPlugin.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----     CancelAutoDisorderPlugin scheduler is running          -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

				
				}
				catch (Exception exception)
				{
				
					AppConstants.IOES_LOGGER.info("############     Problem in starting CancelAutoDosorderSheduler        ########");
				}
			}  if(schedulerType.equalsIgnoreCase(draftOrderMailPluginSch)) {
				try
				{
				
					draftOrderMailPlugin.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----         DraftOrderMailPlugin scheduler is running          -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

				
				}
				catch (Exception exception)
				{
				
					AppConstants.IOES_LOGGER.info("############   Problem in starting  DraftOrderMailPluginSheduler       ########");
				}
			}  if(schedulerType.equalsIgnoreCase(rMEscalationMailPluginSch)) {
				try
				{
				
					rMEscalationMailPlugin.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----       RMEscalationMailPlugin scheduler is running          -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

	
				}
				catch (Exception exception)
				{
					
					AppConstants.IOES_LOGGER.info("############    Problem in starting RMEScalationMailPluginSheduler       ########");
				}
			}  if(schedulerType.equalsIgnoreCase(demoDaysDisconnectionPluginSch)) {			
				try
				{
				
					demoDaysDisconnectionPlugin.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----     DempDaysDisconnectionPlugin scheduler is running       -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

	
				} 
				catch (Exception exception)
				{
	
					AppConstants.IOES_LOGGER.info("############    Problem in stating DemodaysDisconnetionSheduler        ########");
				}
			} if(schedulerType.equalsIgnoreCase(m6PluginSch)) {
				try
				{
					m6PlugIn.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----                M6Plugin scheduler is running               -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

				
				}
				catch (Exception exception)
				{
					
					AppConstants.IOES_LOGGER.error("*************************************************************************");
					AppConstants.IOES_LOGGER.info("############   Problem in starting  M6Sheduler       ########");
				}
			}  if (schedulerType.equalsIgnoreCase(queryPluginSch)) {
				try
				{
				
					queryPlugin.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----           QueryPlugin scheduler is running                 -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

				
				}
				catch (Exception exception)
				{
					System.out.println("Error in QueryPlugin is: "+exception);
					AppConstants.IOES_LOGGER.info(exception +"############   Problem in starting  QueryPluginSheduler        ########");
				}
			}	
			 if (schedulerType.equalsIgnoreCase(eCRM_PlugInSch)) {
				try
				{
				
					eCRM_PlugIn.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----              ECRMPlugin scheduler is running               -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

				
				}
				catch (Exception exception)
				{
				
					AppConstants.IOES_LOGGER.info("############  Problem in starting   ECRMPluginSheduler         ########");
				}
			}
			 
			 
			//---------------------------------Single View Added 16-Mar-2016--------------------------------------
			 if (schedulerType.equalsIgnoreCase(eCRM_SingleViewPlugInSch)) {
					try
					{
					
						eCRM_SingleView_PlugIn.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----              ECRMPlugin SINGLE VIEW scheduler is running               -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############  Problem in starting   ECRM Single View Plugin Scheduler         ########");
					}
				}
			//---------------------------------Single View Added 16-Mar-2016--------------------------------------
			 
			 
			 if (schedulerType.equalsIgnoreCase(fXSchedulerPlugInSch)) {
				try
				{
					
					fXSchedulerPlugIn.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----             FXScheduler scheduler is running               -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

			
				}
				catch (Exception exception)
				{
				
					AppConstants.IOES_LOGGER.info("############     Problem in starting FXScheduler       ########");
				}
			}
			 if (schedulerType.equalsIgnoreCase(fXNrcSchedulerPlugInSch)) {
					try
					{
						
						fXSchedulerPlugIn.init_NRC(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----             FXScheduler NRC scheduler is running               -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

				
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############     Problem in starting FX NRC Scheduler       ########");
					}
				}

			 
			 if (schedulerType.equalsIgnoreCase(fXComponentSchedulerPlugInSch)) {
					try
					{
						
						fXSchedulerPlugIn.init_Component(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----             FX Component Scheduler is running               -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

				
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############     Problem in starting FX NRC Scheduler       ########");
					}
				}

			 
			 if (schedulerType.equalsIgnoreCase(fXCurrecySchedulerPlugInSch)) {
					try
					{
						
						fXSchedulerPlugIn.init_Currency(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----             FXScheduler Currency scheduler is running               -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

				
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############     Problem in starting FX NRC Scheduler       ########");
					}
				}
			 if (schedulerType.equalsIgnoreCase(utilityPlugInSch)) {
				try
				{
					
					utilityPlugIn.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----            UtilityPlugin scheduler is running              -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

			
				}
				catch (Exception exception)
				{
				System.out.println("Error in QueryPlugin is :"+exception);
					AppConstants.IOES_LOGGER.info(exception+"############  Problem in starting   UtilityPluginSheduler        ########");
				}
			}
			 if (schedulerType.equalsIgnoreCase(autoRenewalMailPluginSch)) {
				try
				{
				
					autoRenewalMailPlugin.init(actionServlet, moduleConfig);
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					AppConstants.IOES_LOGGER.info("----       AutoRenewalMailPlugin scheduler is running           -------");
					AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

		
				}
				catch (Exception exception)
				{
				
					AppConstants.IOES_LOGGER.info("############   Problem in starting  AutorenewalMailPluginSheduler       ########");
				}
		} 		
			 if (schedulerType.equalsIgnoreCase(bulkUploadSch)) {
					try
					{
					
						bulkUploadPlugin.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----       BulkUploadplugin scheduler is running           -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

			
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############   Problem in starting  BulkUploadpluginSheduler       ########");
					}
			} 	
			 
			 
			 if (schedulerType.equalsIgnoreCase(demoDaysAlertSch)) {
					try
					{
					
						demoDaysAlertPlugin.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----       DemoDaysAlertPlugin scheduler is running           -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

			
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############   Problem in starting  DemoDaysAlertPlugin       ########");
					}
			} 	
			 
			 if (schedulerType.equalsIgnoreCase(lemoLocalDataSch)) {
					try
					{
					
						lepmLocDataUpdatePlugin.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----       LepmLocDataUpdatePlugin scheduler is running           -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

			
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############   Problem in starting  LepmLocDataUpdatePlugin       ########");
					}
			} 	
			
			 
			 /*
			  * 
			  * Created by Nagarjuna For OBValueAutomation Scheduler.To calculate OB Value
			  */
			 
			 if (schedulerType.equalsIgnoreCase(obvalueAutomationScheduler)) {
					try
					{
					
						obvalueAutomationplugin.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----       OB Value Automation scheduler is running           -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

			
					}
					catch (Exception exception)
					{
					
						AppConstants.IOES_LOGGER.info("############   Problem in starting  OBValueAutomationpluginSheduler       ########");
					}
			} 
			 
			 /*
			  * End of OBValueAutomation Scheduler
			  */
			 //[002] START
			 if (schedulerType.equalsIgnoreCase(ePCDCustAlertsScheduler)) {
					try
					{
						ePCDcustAlertPlugin.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----        ePCD Customer Alerts scheduler is running           -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					}
					catch (Exception exception)
					{
						AppConstants.IOES_LOGGER.info("###******####   Problem in starting  ePCDCustAlertsScheduler       ###******####");
					}
			}
			 //[002] END
			 //[003] START
			 if (schedulerType.equalsIgnoreCase(provisionPlugInScheduler)) {
					try
					{
						provisionMailPlugIn.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----       Provision mail scheduler is running           -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					}
					catch (Exception exception)
					{
						AppConstants.IOES_LOGGER.info("###******####   Problem in starting  provsion mail scheduler       ###******####");
					}
			}
			 //[003 END
			 if (schedulerType.equalsIgnoreCase(runningonceinadaySch)) {
					try
					{
						onceinadayrunningPlugin.init(actionServlet, moduleConfig);
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
						AppConstants.IOES_LOGGER.info("----     Running_Once_In_A_Day scheduler is running    -------");
						AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
					}
					catch (Exception exception)
					{
						AppConstants.IOES_LOGGER.info("###******####   Problem in starting  runningonceinadaySch       ###******####");
					}
			}
			 
			} catch (Exception exception) {
				
				AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");
				AppConstants.IOES_LOGGER.info("   Problem in starting schedulers :"+AppUtility.getStackTrace(exception));
				AppConstants.IOES_LOGGER.info("-----------------------------------------------------------------------");

			}
		}

	public void destroy()
	{
		// Some code here
		// TODO call a executor shutdown here 
		Utility.LOG("Server Stop-destroy method Initiated.... !!!!");
		ApplicationClone.getInstance(new UtilityService()).shutdown();
		Utility.LOG("destroy method returned succesfully !!!!");
	}
}
