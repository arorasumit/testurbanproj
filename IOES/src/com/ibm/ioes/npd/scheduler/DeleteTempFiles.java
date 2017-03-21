package com.ibm.ioes.npd.scheduler;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import com.ibm.ioes.npd.hibernate.beans.SessionObjectsDto;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.Messages;

public class DeleteTempFiles extends TimerTask{

	@Override
	public void run() 	 {

	System.err.println("Deleting temporary files at "+new Date());
	
	try
	{
		String dir=Messages.getMessageValue("TEMPORARY_DIR_PATH");
		File directory = new File(dir);
		long time=1 * 60 * 60 * 1000;
		//long time=60 * 1000;
        if(directory.exists()){   
  
            File[] listFiles = directory.listFiles();               
            long purgeTime = System.currentTimeMillis() - time;   
            for(File listFile : listFiles) {   
                if(listFile.lastModified() < purgeTime) {   
                    if(!listFile.delete()) {   
                        System.err.println("Unable to delete file: " + listFile);   
                    }   
                }   
            }   
        } else {   
            AppConstants.NPDLOGGER.error("Files were not deleted, directory " + dir + " does'nt exist!");
        }
        
        
       

	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		AppConstants.NPDLOGGER.error(ex.getMessage()
				+ " Exception occured in run() method :delete temp files scheduler method: of "
				+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
	}
	System.err.println("Files Deleted.");
	
		try{
		 new DeleteTempFiles().deleteGanttChartFiles();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in run() method :delete deleteGanttChartFiles scheduler method: of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
		}
	
	}

	public void deleteGanttChartFiles() 	 {

		System.err.println("Deleting deleteGanttChartFiles at "+new Date());
		
		try
		{
			//getServlet().getServletContext().getRealPath("/Data");
			String ganttDirPath=SessionObjectsDto.getInstance().getGanttChartDirPath();
			System.err.println("ganttDirPath :"+ganttDirPath);
			
			if(ganttDirPath==null || "".equals(ganttDirPath.trim()))
			{
				System.err.println("Could not delete files as directory path not set , Value is :"+ganttDirPath);
				return;
			}
			
			
			String dir=ganttDirPath;
			File directory = new File(dir);
			long time=1 * 60 * 60 * 1000;
			//long time=60 * 1000;
	        if(directory.exists()){   
	  
	            File[] listFiles = directory.listFiles();               
	            long purgeTime = System.currentTimeMillis() - time;   
	            for(File listFile : listFiles) {   
	                if(listFile.lastModified() < purgeTime) {   
	                    if(!listFile.delete()) {   
	                        System.err.println("Unable to delete file: " + listFile);   
	                    }   
	                }   
	            }   
	        } else {   
	            AppConstants.NPDLOGGER.error("Files were not deleted, directory " + dir + " does'nt exist!");
	        }   

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in run() method :delete deleteGanttChartFiles scheduler method: of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
		}
		System.err.println("deleteGanttChartFiles Deleted.");
		
		}
	
	
}
