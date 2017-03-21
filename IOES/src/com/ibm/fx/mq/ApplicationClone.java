package com.ibm.fx.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.ibm.ioes.service.UtilityService;
import com.ibm.ioes.utilities.Utility;
//import com.ibm.ioes.schedular.ApplicationPlugin;
//import com.ibm.ioes.utilities.Utility;
/**
  It will be a singleton class
 */

public class ApplicationClone {
		
			//public ExecutorService cloneLevelPool = null;
	
			public static enum TaskType
			{
				NRC_TO_FX ,M6_OSN_PROCESS, AUTO_BILLING_TRIGGER_LOC_LATER, AUTO_BILLING_TRIGGER_REST, EXTRA
			} 
			
			public static enum Approach
			{
				POOL_PER_TASK, SINGLE_POOL , MIN_POOL_PER_TASK
				
			}

			private static UtilityService utilityService; 
			
			//private int sum_minthread=0;
			
			private Approach approach;
			
			private Map<String,TaskResource> taskResources = null;
			
			ExecutorService singlePool = null;
			
			
			
			
			private ApplicationClone(UtilityService utilityService){								
				try{
					
					String configuredApproach = utilityService.getAppConfigValue("SCH_MULTITHREADING_APPROACH");
					
					if(Approach.POOL_PER_TASK==Approach.valueOf(configuredApproach)
							|| Approach.MIN_POOL_PER_TASK==Approach.valueOf(configuredApproach)){
						
						approach=Approach.valueOf(configuredApproach);//Approach.POOL_PER_TASK;
						
						ArrayList<PoolConfigData> configlist= utilityService.getPoolConfig( utilityService.getSchedulerName());// TODO verifiy correct scheduler name 
						
						ListIterator<PoolConfigData>itr= configlist.listIterator();
						int sum_minthread=0;
							while(itr.hasNext()){
							sum_minthread=sum_minthread+ itr.next().getMinThread();
						}					
						int maxPoolSize = Integer.parseInt(utilityService.getAppConfigValue("CLONE_LEVEL_POOL_SIZE"));
							if(sum_minthread>maxPoolSize){
							throw new RuntimeException("Cannot Create Pool");
						}					
						 // proceed to pool creation
						taskResources = new HashMap<String, TaskResource>();
						for(PoolConfigData pc : configlist){
							taskResources.put(pc.getTask_type(), new TaskResource(pc.getMinThread(),pc.getMaxThread())); //get minsize from parallel task_config_table 	
						}		
						if(maxPoolSize-sum_minthread>0){
							taskResources.put(TaskType.EXTRA.toString(), new TaskResource(maxPoolSize-sum_minthread,1000000));
						}
					
					}else if(Approach.SINGLE_POOL==Approach.valueOf(configuredApproach)){
						approach=Approach.SINGLE_POOL;
						int maxPoolSize = Integer.parseInt(utilityService.getAppConfigValue("CLONE_LEVEL_POOL_SIZE"));
						singlePool=Executors.newFixedThreadPool(maxPoolSize);
						
					}
					
					
			   }catch(Exception ex){
				   utilityService.LOG(ex);
				   throw new RuntimeException("Cannot Create Pool");					  
				   }
						   	 				   
			}
			

			private static class SingletonClonePoolHolder  
			{			
				private static final ApplicationClone INSTANCE = new ApplicationClone(ApplicationClone.utilityService);
			}

			public static ApplicationClone getInstance(UtilityService utilityService)
			{
				ApplicationClone.utilityService = utilityService;
				return SingletonClonePoolHolder.INSTANCE;
			}

	 
	   	
		   	public void  submitTaskToPool(List<Callable<Object>> taskList,TaskType taskType) 
		   			throws Exception{
		   		
		   		if(approach==Approach.POOL_PER_TASK){
		   			submitTaskToPoolWithPerTaskApproach(taskList,taskType);
		   		}else if(approach==Approach.SINGLE_POOL){
		   			submitTaskToPoolWithSinglePoolApproach(taskList,taskType);
		   		}else if(approach==Approach.MIN_POOL_PER_TASK){
		   			submitTaskToMinPoolWithPerTaskApproach(taskList,taskType);
		   		}
		   	}
		   	
		  	private void submitTaskToMinPoolWithPerTaskApproach(
					List<Callable<Object>> taskList, TaskType taskType) throws Exception{

		  		TaskResource primaryResource = taskResources.get(taskType.toString());
		  		primaryResource.getExecutor().invokeAll(taskList);
			}



			/****************************Shubhranshu, 7-1-16************************************************************/
		   public void submitTaskToPoolWithSinglePoolApproach(
				List<Callable<Object>> taskList, TaskType taskType) throws Exception{
			   
			  // System.out.println("doing something here");
			   
			   Stack<Future<Object>> futures = new Stack<Future<Object>>();
			   for (Callable<Object> task : taskList) {
				   futures.add(singlePool.submit(task));
			   }
				while(futures.size()>0){
					futures.pop().get();
				}
				
				//System.out.println("finally done!!");
		}
		   
		private void submitTaskToPoolWithPerTaskApproach(
				List<Callable<Object>> taskList, TaskType taskType)  throws Exception{
		 		TaskResource primaryResource=null;
		   		int currentIndex=0;
		   		

		   		if(taskResources.containsKey(taskType.toString()))  	// check if pool map contains pool for tasktype key 
		   		{
		   			primaryResource = taskResources.get(taskType.toString()); // return primary pool associated with tasktype
		   		}
		   		else{	
		   			throw new RuntimeException("Cannot Find Primary Pool");
		   		}
		   		
		   		try{
		   			primaryResource.getLock().lock();// todo understand	
		   			
		   			final int maxAllowed = primaryResource.getMaxThreads();
		   			final int totalTaskCount = taskList.size();
		   	
		   			Stack<Future<Object>> futures = new Stack<Future<Object>>();
		   			
			   		do{		   			
			   			int currentParallelTasks = 0;
			   			
			   			int nextPoolSize = primaryResource.getPoolSize() ;
			   			int countForTasksForNextSubmit = getCountForNextPool(currentParallelTasks, maxAllowed, nextPoolSize,totalTaskCount-currentIndex);
			   			
	   					List<Callable<Object>> sublist=getSubList(taskList,countForTasksForNextSubmit,currentIndex);
	   					currentIndex += countForTasksForNextSubmit;  // new position of currentindex 
	   					currentParallelTasks = countForTasksForNextSubmit;
	   					
	   					futures.addAll(submitToPool(primaryResource.getExecutor(), sublist));  // now submit to  primary pool and collect its future

	   					// check for secondary pool 		   		
	   					Set<Map.Entry<String, TaskResource>> pools  = taskResources.entrySet();
	   					
	   					outer:
	   					for(Map.Entry<String, TaskResource> entry:  pools){
				   			if( (!entry.getKey().equals(taskType.toString()) ) ){
				   				TaskResource secResource =  entry.getValue();
				   				Lock secLock = secResource.getLock();
				   				boolean isLockAcquired = false;
				   				
				   				try{
				   					isLockAcquired = secLock.tryLock();
				   					if(isLockAcquired){
				   						//submit task to pool	
				   						nextPoolSize = secResource.getPoolSize();
				   						countForTasksForNextSubmit = getCountForNextPool(currentParallelTasks, maxAllowed, nextPoolSize,totalTaskCount-currentIndex);
				   						if(countForTasksForNextSubmit<=0){
				   							break outer;
				   						}
				   						List<Callable<Object>> tasksForSubmit =getSubList(taskList,countForTasksForNextSubmit,currentIndex);
				   						currentIndex += countForTasksForNextSubmit;
				   						currentParallelTasks += countForTasksForNextSubmit;
				   						
				   						futures.addAll(submitToPool(secResource.getExecutor(),tasksForSubmit));		
				   					}
				   				}finally{
				   					if(isLockAcquired) secLock.unlock();
				   				}
				   			}					   			
				   		}
	   					while(futures.size()>0){
								futures.pop().get();
	   					}
			   		}while(currentIndex<taskList.size());
		   		
		   		}finally{
		   			primaryResource.getLock().unlock();	
		   		}
		   	}
		   	
		   private int getCountForNextPool(int currentParallelTask,
				int maxAllowed, int nextPoolSize, int leftTasks) {
			int stillPossible = maxAllowed - currentParallelTask;
			if(stillPossible <= 0){
				return 0;
			}else{
				int possibleAsPerPool;
				if(stillPossible < nextPoolSize){
					possibleAsPerPool = stillPossible;
				}else{
					possibleAsPerPool = nextPoolSize;
				}
				if(possibleAsPerPool<leftTasks){
					return possibleAsPerPool;
				}else{
					return leftTasks;
				}
			}
		}

		private ArrayList<Future<Object>> submitToPool(ExecutorService threadpool,List<Callable<Object>> tasklist)
		   {

			   ArrayList<Future<Object>> futurelist=new ArrayList<Future<Object>>();
			   for(Callable<Object> taskobject:tasklist)
			   { 
				   futurelist.add(threadpool.submit(taskobject));  // submit to pool and collect future in a list 
			   }
			   return futurelist;
		   }
		   
	/*	   private boolean submitToSecondaryPool(ExecutorService threadpool,List<Callable<Object>> tasklist,ReentrantLock l)
		   {
			   			List<Future<Object>> futurelist=Collections.emptyList();
			   			
					   if(l.tryLock())   //acquire a lock 
					   {							   				
										   for(Callable<Object> taskobject:tasklist)
										   { 
											   futurelist.add(threadpool.submit(taskobject));  // submit to pool and collect future in a list 
										   }
							   
										  try
											  {
												  waitForCompletionStatus(futurelist);
											  }
											  catch(Exception e)
											  {
												  e.printStackTrace();
											  }
											  finally
											  {
												if(l.isLocked())  
													{l.unlock();}   // release the lock if acquired 								  
											  }	
							  
				        }
					   else
					   {return false;}
					   
					return true;   
		   }
				   */
				   
	/*	   private void waitForCompletionStatus(List<Future<Object>> futurelist) throws Exception
		   {
			   for(Future<Object> fut:futurelist)
			   {
				   fut.get();
			   }
			
		   }*/

		private List<Callable<Object>> getSubList(List<Callable<Object>> mainlist,int sublistsize,int curpos)
		   {
			   int toIndex=curpos+sublistsize;
			   return mainlist.subList(curpos, toIndex);
		   }


			public void shutdown() {
				if(approach==Approach.POOL_PER_TASK || approach==Approach.MIN_POOL_PER_TASK){
					
		   			for(Map.Entry<String,TaskResource> entry:taskResources.entrySet())
		   			{
		   				if(! (entry.getValue().getExecutor().isShutdown())){
		   					// get each executor and shutdown
		   					entry.getValue().getExecutor().shutdownNow();
		   				}
		   			}
		   			for(Map.Entry<String,TaskResource> entry:taskResources.entrySet())
		   			{
	   					// get each executor and shutdown
	   					try {
							entry.getValue().getExecutor().awaitTermination(60, TimeUnit.SECONDS);
						} catch (InterruptedException e) {
							Utility.LOG(e);
						}
		   			}
		   		}else{
		   			singlePool.shutdownNow();
		   			try {
						singlePool.awaitTermination(60, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						Utility.LOG(e);
					}
		   		}
			}

	

}

	//Shubhranshu	
	/*private String getPendencycount_NRC=
			" SELECT count(ID) total_ids " +
			" FROM IOE.TFX_NRC_CREATE TFX_NRC  _CREATE " +
			" WHERE TFX_NRC_CREATE.FX_SCHEDULAR_CREATE_STATUS=1 and TFX_NRC_CREATE.EFFECTIVE_DATE<=(Current date)"+
			" AND (PROCESSING_COUNT) < (SELECT BIGINT(KEYVALUE) FROM IOE.TM_APPCONFIG " +
			"																WHERE KEYNAME='PROCESSING_COUNT') with ur ";

	private String getPendencycount_RC=
			"SELECT count(ID) total_ids " +
			"FROM IOE.TFX_RC_CREATE TFX_RC_CREATE"+
			"  WHERE TFX_RC_CREATE.FX_SCHEDULAR_CREATE_STATUS=1 and TFX_RC_CREATE.CREATEDDATE<=(Current date)"+
			"  AND (PROCESSING_COUNT) < (SELECT BIGINT(KEYVALUE)"+
			"  FROM IOE.TM_APPCONFIG WHERE KEYNAME='PROCESSING_COUNT') with ur";*/
	//Shubhranshu	

	/*public static enum SchedulerName {
		NRC , M6, RC
	}*/

	
	//

	
	/*public int getPendency(SchedulerName sName) 
	{
				int countValue=0;
				Connection con = null;
				Statement stmt = null;
				ResultSet rst = null;
				try
				{
							con=DbConnection.getConnectionObject();
							
						
							stmt = con.createStatement();
							
							if(sName.equals(SchedulerName.NRC))
							rst=stmt.executeQuery(getPendencycount_NRC);
							
							else if(sName.equals(SchedulerName.RC))
							rst=stmt.executeQuery(getPendencycount_RC);
							
							if(rst.next())
								countValue= rst.getInt("total_ids");
							else
							System.out.println("NO data recieved!!");
								
							
		       }
				catch (Exception ex)
				{
				//Utility.LOG(ex); 
					ex.printStackTrace();
				}
				
				finally
				{
						
							try {
							DbConnection.closeResultset(rst);  DbConnection.closeStatement(stmt);
							
							DbConnection.freeConnection(con);
							//con.close(); 

							} 
							catch (Exception e) 
							{
							e.printStackTrace();
						    }
		         }
		return countValue;
	}*/

	/*public ExecutorService getPossiblePool(SchedulerName sName, int poolSizeReqd) {
		if(ApplicationClone.SchedulerName.NRC.equals(sName)){
			return Executors.newFixedThreadPool(poolSizeReqd);	
		}
		return null;
	}

	public int getTaskQueueCount(SchedulerName sName, int maximumPoolSize) {
		if(ApplicationClone.SchedulerName.NRC.equals(sName)){
			return 500;	
		}
		return 0;
	}

	public int getReqdPoolSize(SchedulerName sName, int pendency) {
		if(ApplicationClone.SchedulerName.NRC.equals(sName)){
			return pendency;
		}
		return 0;
	}*/

