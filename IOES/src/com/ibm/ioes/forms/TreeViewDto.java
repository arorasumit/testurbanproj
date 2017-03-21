package com.ibm.ioes.forms;

import java.util.ArrayList;
import com.ibm.ioes.forms.ServiceSubTypeDto;

public class TreeViewDto 
{
	private String treeViewText;
	
	private String treeViewURL;
	
	private String serviceParentId;
	private String serviceChildId;
	private int countLevel;
	
	private String serviceProductParentId;
	private String serviceProductChildId;
	private int isChildDisconnected;
	private int changeSubTypeID;
	private int billingFormat;
	private int isAdditionalNode;
	private int isChargePresent;	
		//LAWKUSH
	private int serviceId;
	private String billFormat;
	private int changeServiceId;
	private int isCommercial;
	
	public int getIsCommercial() {
		return isCommercial;
	}
	public void setIsCommercial(int isCommercial) {
		this.isCommercial = isCommercial;
	}

	public int getIsAdditionalNode() {
		return isAdditionalNode;
	}

	public void setIsAdditionalNode(int isAdditionalNode) {
		this.isAdditionalNode = isAdditionalNode;
	}

	public int getBillingFormat() {
		return billingFormat;
	}

	public void setBillingFormat(int billingFormat) {
		this.billingFormat = billingFormat;
	}

	public int getChangeSubTypeID() {
		return changeSubTypeID;
	}

	public void setChangeSubTypeID(int changeSubTypeID) {
		this.changeSubTypeID = changeSubTypeID;
	}

	public int getIsChildDisconnected() {
		return isChildDisconnected;
	}

	public void setIsChildDisconnected(int isChildDisconnected) {
		this.isChildDisconnected = isChildDisconnected;
	}

	public String getTreeViewText() {
		return treeViewText;
	}

	public void setTreeViewText(String treeViewText) {
		this.treeViewText = treeViewText;
	}

	public String getTreeViewURL() {
		return treeViewURL;
	}

	public void setTreeViewURL(String treeViewURL) {
		this.treeViewURL = treeViewURL;
	}

	public String getServiceChildId() {
		return serviceChildId;
	}

	public void setServiceChildId(String serviceChildId) {
		this.serviceChildId = serviceChildId;
	}

	public String getServiceParentId() {
		return serviceParentId;
	}

	public void setServiceParentId(String serviceParentId) {
		this.serviceParentId = serviceParentId;
	}

	public int getCountLevel() {
		return countLevel;
	}

	public void setCountLevel(int countLevel) {
		this.countLevel = countLevel;
	}

	public String getServiceProductChildId() {
		return serviceProductChildId;
	}

	public void setServiceProductChildId(String serviceProductChildId) {
		this.serviceProductChildId = serviceProductChildId;
	}

	public String getServiceProductParentId() {
		return serviceProductParentId;
	}

	public void setServiceProductParentId(String serviceProductParentId) {
		this.serviceProductParentId = serviceProductParentId;
	}

	public int getIsChargePresent() {
		return isChargePresent;
	}

	public void setIsChargePresent(int isChargePresent) {
		this.isChargePresent = isChargePresent;
	}
		public String getBillFormat() {
		return billFormat;
	}

	public void setBillFormat(String billFormat) {
		this.billFormat = billFormat;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getChangeServiceId() {
		return changeServiceId;
	}

	public void setChangeServiceId(int changeServiceId) {
		this.changeServiceId = changeServiceId;
	}
	
}
