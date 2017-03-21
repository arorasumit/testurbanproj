package com.ibm.fx.mq;

public class PoolConfigData {
	
		String  taskType=null;
		String cloneName=null;
		int minThread=0;
		int maxThread=0;
		
		
		public PoolConfigData() {
			// TODO Auto-generated constructor stub
		}
		
		public PoolConfigData(String  taskType,String cloneName,int minThread,int maxThread) {
			this.taskType = taskType;
			this.cloneName = cloneName;
			this.minThread = minThread;
			this.maxThread = maxThread;
		}
		
		/**
		 * @return the task_type
		 */
		public String getTask_type() {
			return taskType;
		}
		
		/**
		 * @return the clone_name
		 */
		
		public String getClone_name() {
			return cloneName;
		}
		
		/**
		 * @return the minThread
		 */
		public int getMinThread() {
			return minThread;
		}
		/**
		 * @return the maxThread
		 */
		public int getMaxThread() {
			return maxThread;
		}
		/**
		 * @param task_type the task_type to set
		 */
		public void setTask_type(String task_type) {
			this.taskType = task_type;
		}
		/**
		 * @param clone_name the clone_name to set
		 */
		public void setCloneName(String clone_name) {
			this.cloneName = clone_name;
		}
		/**
		 * @param minThread the minThread to set
		 */
		public void setMinThread(int minThread) {
			this.minThread = minThread;
		}
		/**
		 * @param maxThread the maxThread to set
		 */
		public void setMaxThread(int maxThread) {
			this.maxThread = maxThread;
		}

}
