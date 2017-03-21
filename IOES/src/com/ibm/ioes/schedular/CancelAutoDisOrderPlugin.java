//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha Garg	3-Sep-12	00-05422		Created for Auto Disconnection Order
package com.ibm.ioes.schedular;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import com.ibm.ioes.utilities.Utility;

public class CancelAutoDisOrderPlugin implements PlugIn{

		public void destroy() {
			

		}

		public void init(ActionServlet arg0, ModuleConfig arg1)
				throws ServletException {
			System.err.println("starting Cancel of Auto Disconnection Order");
			java.util.Timer timer = new java.util.Timer();		
			CancelAutoDisOrderSchedular ordercancelcheduler = new CancelAutoDisOrderSchedular();
			
			try {
				
				long delay = Long.parseLong(Utility.getAppConfigValue("CANCEL_DIS_ORDER_DELAYED_START"));
				long period=Long.parseLong(Utility.getAppConfigValue("CANCEL_DIS_ORDER_INTERVAL"));
				timer.schedule(ordercancelcheduler, delay, period);
				
				}
			
			catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

