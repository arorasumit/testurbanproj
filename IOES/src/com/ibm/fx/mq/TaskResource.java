package com.ibm.fx.mq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskResource {
	
		private ExecutorService executor=null;
		private Lock lock=null;
		private int poolSize;
		private int maxThreads;
		
		public TaskResource(int minThread, int maxThread) 
		{
			this.poolSize=minThread;
			executor = Executors.newFixedThreadPool(minThread);
			lock = new ReentrantLock();
			this.maxThreads=maxThread;
		}
		public int getMaxThreads() {
			return maxThreads;
		}
		public ExecutorService getExecutor() {
			return executor;
		}
		public Lock getLock() {
			return lock;
		}
		public int getPoolSize() {
			return poolSize;
		}
}
