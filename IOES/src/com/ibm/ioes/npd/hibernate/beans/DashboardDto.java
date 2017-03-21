package com.ibm.ioes.npd.hibernate.beans;

public class DashboardDto {

	private int taskpending_mytodoList;

	private int taskpending_rfi;

	private int taskpending_plrUploading;
	
	private int total_taskPending;

	public int getTaskpending_mytodoList() {
		return taskpending_mytodoList;
	}

	public void setTaskpending_mytodoList(int taskpending_mytodoList) {
		this.taskpending_mytodoList = taskpending_mytodoList;
	}


	public int getTaskpending_rfi() {
		return taskpending_rfi;
	}

	public void setTaskpending_rfi(int taskpending_rfi) {
		this.taskpending_rfi = taskpending_rfi;
	}

	public int getTaskpending_plrUploading() {
		return taskpending_plrUploading;
	}

	public void setTaskpending_plrUploading(int taskpending_plrUploading) {
		this.taskpending_plrUploading = taskpending_plrUploading;
	}

	public int getTotal_taskPending() {
		return total_taskPending;
	}

	public void setTotal_taskPending(int total_taskPending) {
		this.total_taskPending = total_taskPending;
	}



}
