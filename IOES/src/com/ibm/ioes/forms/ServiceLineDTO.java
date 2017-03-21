/*Tag Name  Resource Name   Date		    CSR No			Description
 * [001]   Gunjan Singla   2-Jan-15    20141113-R1-020802		ParallelUpgrade-Multiple LSI Selection
 * [002]   Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Parallel Upgrade LSI
 * */
 

package com.ibm.ioes.forms;

import java.util.ArrayList;

import com.ibm.ioes.utilities.PagingSorting;

public class ServiceLineDTO extends PagingDto {

	private int serviceId;
	private String serviceTypeName;
	private int serviceTypeId;
	private int customer_logicalSINumber;
	private int logicalSINumber;
	private int isPublished;   // By Saurabh for Partial Publish
	private String serviceStatus;
	private int prdAttrEntered = 0;
	private int servAttrEntered=0;
	private int productCatelogEntered=0;
	private int isServiceActive;
	private int isServiceCreatedAfterCancelCopy;
	private String productName;
	private String serviceName;
	private String checkedServiceNumber;
	private int orderNumber;
	private String downtimeClause;
	private int roleID;
	private Integer mbic_ServiceId;
	
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getDowntimeClause() {
		return downtimeClause;
	}
	public void setDowntimeClause(String downtimeClause) {
		this.downtimeClause = downtimeClause;
	}
	private int maxPageNo;
	private ArrayList errors;
	private int poNumber;
	private String effStartDate;
	private String rfsDate;
	private String attRemarks;
	ArrayList customer_logicalSINumbers = new ArrayList() ;
	private int serviceSubtypeId;
	private String rfs_date;
	private String product_name_for_fx;
	private String serviceSubTypeName;
	private int serviceDetailID;
	private String serviceId1;
	private int serviceProductID;
	private String subLineItemLbl;
	private int dummyServiceId;
	private int isDummyServicePublished;
	private int dummyOrderNo;
	private int isDummy;
	private String isCCMapWithMBIC;
	private String mbicServiceId;
	private String mbicServiceProductId;
	private String isAttach;
	private String cc_M6_Progress_status;
	private String tgno_Number;
	private String isMbicLineDummy;
	private int initiatedTo;
	
	private String cancelledOrderReference;
	/*private int parallelUpgradeLSI;*/
	//Start by Deepak for third Party
	private String nfaNumber;
	private int prId;
	private String prNumber;
	private String circle;
	//end Deepak
	//[001] start
	private String parallelUpgradeLSINo1;
	private String parallelUpgradeLSINo2;
	private String parallelUpgradeLSINo3;
	private ArrayList<ServiceLineDTO> ServiceLineDtoList=null;
	private String statusMsg;
	private String statusFlag;
	//[001] end
	//[002] Start
	private int parallelUpgradeLSIRequire;
	private String remarksParallelUpgrade;
	//[002] End
	
	public String getCc_M6_Progress_status() {
		return cc_M6_Progress_status;
	}
	public void setCc_M6_Progress_status(String cc_M6_Progress_status) {
		this.cc_M6_Progress_status = cc_M6_Progress_status;
	}
	public String getTgno_Number() {
		return tgno_Number;
	}
	public void setTgno_Number(String tgno_Number) {
		this.tgno_Number = tgno_Number;
	}
	public String getIsAttach() {
		return isAttach;
	}
	public void setIsAttach(String isAttach) {
		this.isAttach = isAttach;
	}
	public String getMbicServiceId() {
		return mbicServiceId;
	}
	public void setMbicServiceId(String mbicServiceId) {
		this.mbicServiceId = mbicServiceId;
	}
	public String getMbicServiceProductId() {
		return mbicServiceProductId;
	}
	public void setMbicServiceProductId(String mbicServiceProductId) {
		this.mbicServiceProductId = mbicServiceProductId;
	}
	
	public ServiceLineDTO()
	{
		effStartDate="";
		attRemarks="";
	}
	public String getSubLineItemLbl() {
		return subLineItemLbl;
	}


	public void setSubLineItemLbl(String subLineItemLbl) {
		this.subLineItemLbl = subLineItemLbl;
	}
	public int getServiceProductID() {
		return serviceProductID;
	}

	public void setServiceProductID(int serviceProductID) {
		this.serviceProductID = serviceProductID;
	}
	public String getServiceId1() {
		return serviceId1;
	}


	public void setServiceId1(String serviceId1) {
		this.serviceId1 = serviceId1;
	}
	public int getServiceDetailID() {
		return serviceDetailID;
	}

	public void setServiceDetailID(int serviceDetailID) {
		this.serviceDetailID = serviceDetailID;
	}
	public String getServiceSubTypeName() {
		return serviceSubTypeName;
	}

	public void setServiceSubTypeName(String serviceSubTypeName) {
		this.serviceSubTypeName = serviceSubTypeName;
	}
	public String getProduct_name_for_fx() {
		return product_name_for_fx;
	}


	public void setProduct_name_for_fx(String product_name_for_fx) {
		this.product_name_for_fx = product_name_for_fx;
	}

	public String getRfs_date() {
		return rfs_date;
	}


	public void setRfs_date(String rfs_date) {
		this.rfs_date = rfs_date;
	}

	public int getServiceSubtypeId() {
		return serviceSubtypeId;
	}

	public void setServiceSubtypeId(int serviceSubtypeId) {
		this.serviceSubtypeId = serviceSubtypeId;
	}

	public ArrayList getCustomer_logicalSINumbers() {
		return customer_logicalSINumbers;
	}


	public void setCustomer_logicalSINumbers(ArrayList customer_logicalSINumbers) {
		this.customer_logicalSINumbers = customer_logicalSINumbers;
	}

	public String getAttRemarks() {
		return attRemarks;
	}

	public void setAttRemarks(String attRemarks) {
		this.attRemarks = attRemarks;
	}

	public String getRfsDate() {
		return rfsDate;
	}


	public void setRfsDate(String rfsDate) {
		this.rfsDate = rfsDate;
	}
	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}
	public int getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public ArrayList getErrors() {
		return errors;
	}

	public void setErrors(ArrayList errors) {
		this.errors = errors;
	}
	PagingSorting pagingSorting = new PagingSorting();
	
	public int getMaxPageNo() {
		return maxPageNo;
	}


	public void setMaxPageNo(int maxPageNo) {
		this.maxPageNo = maxPageNo;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCheckedServiceNumber() {
		return checkedServiceNumber;
	}


	public void setCheckedServiceNumber(String checkedServiceNumber) {
		this.checkedServiceNumber = checkedServiceNumber;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getIsServiceCreatedAfterCancelCopy() {
		return isServiceCreatedAfterCancelCopy;
	}


	public void setIsServiceCreatedAfterCancelCopy(
			int isServiceCreatedAfterCancelCopy) {
		this.isServiceCreatedAfterCancelCopy = isServiceCreatedAfterCancelCopy;
	}
	public int getIsServiceActive() {
		return isServiceActive;
	}


	public void setIsServiceActive(int isServiceActive) {
		this.isServiceActive = isServiceActive;
	}
	public int getProductCatelogEntered() {
		return productCatelogEntered;
	}

	public void setProductCatelogEntered(int productCatelogEntered) {
		this.productCatelogEntered = productCatelogEntered;
	}
	public int getServAttrEntered() {
		return servAttrEntered;
	}

	public void setServAttrEntered(int servAttrEntered) {
		this.servAttrEntered = servAttrEntered;
	}
	public int getPrdAttrEntered() {
		return prdAttrEntered;
	}

	public void setPrdAttrEntered(int prdAttrEntered) {
		this.prdAttrEntered = prdAttrEntered;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}


	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public int getIsPublished() {
		return isPublished;
	}


	public void setIsPublished(int isPublished) {
		this.isPublished = isPublished;
	}
	public int getLogicalSINumber() {
		return logicalSINumber;
	}


	public void setLogicalSINumber(int logicalSINumber) {
		this.logicalSINumber = logicalSINumber;
	}
	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public int getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public int getCustomer_logicalSINumber() {
		return customer_logicalSINumber;
	}


	public void setCustomer_logicalSINumber(int customer_logicalSINumber) {
		this.customer_logicalSINumber = customer_logicalSINumber;
	}
	public int getDummyOrderNo() {
		return dummyOrderNo;
	}
	public void setDummyOrderNo(int dummyOrderNo) {
		this.dummyOrderNo = dummyOrderNo;
	}
	public int getDummyServiceId() {
		return dummyServiceId;
	}
	public void setDummyServiceId(int dummyServiceId) {
		this.dummyServiceId = dummyServiceId;
	}
	public int getIsDummyServicePublished() {
		return isDummyServicePublished;
	}
	public void setIsDummyServicePublished(int isDummyServicePublished) {
		this.isDummyServicePublished = isDummyServicePublished;
	}
	public int getIsDummy() {
		return isDummy;
	}
	public void setIsDummy(int isDummy) {
		this.isDummy = isDummy;
	}
	public String getIsMbicLineDummy() {
		return isMbicLineDummy;
	}
	public void setIsMbicLineDummy(String isMbicLineDummy) {
		this.isMbicLineDummy = isMbicLineDummy;
	}
	public String getCancelledOrderReference() {
		return cancelledOrderReference;
	}
	public void setCancelledOrderReference(String cancelledOrderReference) {
		this.cancelledOrderReference = cancelledOrderReference;
	}
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	public String getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public int getInitiatedTo() {
		return initiatedTo;
	}
	public void setInitiatedTo(int initiatedTo) {
		this.initiatedTo = initiatedTo;
	}
	public String getNfaNumber() {
		return nfaNumber;
	}
	public void setNfaNumber(String nfaNumber) {
		this.nfaNumber = nfaNumber;
	}
	
	public Integer getMbic_ServiceId() {
		return mbic_ServiceId;
	}
	public void setMbic_ServiceId(Integer mbic_ServiceId) {
		this.mbic_ServiceId = mbic_ServiceId;
	}
	//[001] start
	public String getParallelUpgradeLSINo1() {
		return parallelUpgradeLSINo1;
	}
	public void setParallelUpgradeLSINo1(String parallelUpgradeLSINo1) {
		this.parallelUpgradeLSINo1 = parallelUpgradeLSINo1;
	}
	public String getParallelUpgradeLSINo2() {
		return parallelUpgradeLSINo2;
	}
	public void setParallelUpgradeLSINo2(String parallelUpgradeLSINo2) {
		this.parallelUpgradeLSINo2 = parallelUpgradeLSINo2;
	}
	public String getParallelUpgradeLSINo3() {
		return parallelUpgradeLSINo3;
	}
	public void setParallelUpgradeLSINo3(String parallelUpgradeLSINo3) {
		this.parallelUpgradeLSINo3 = parallelUpgradeLSINo3;
	}
	public ArrayList<ServiceLineDTO> getServiceLineDtoList() {
		return ServiceLineDtoList;
	}
	public void setServiceLineDtoList(ArrayList<ServiceLineDTO> serviceLineDtoList) {
		ServiceLineDtoList = serviceLineDtoList;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	
	
	//[001] end
	//[002] Start
	public int getParallelUpgradeLSIRequire() {
		return parallelUpgradeLSIRequire;
	}
	public void setParallelUpgradeLSIRequire(int parallelUpgradeLSIRequire) {
		this.parallelUpgradeLSIRequire = parallelUpgradeLSIRequire;
	}
	public String getRemarksParallelUpgrade() {
		return remarksParallelUpgrade;
	}
	public void setRemarksParallelUpgrade(String remarksParallelUpgrade) {
		this.remarksParallelUpgrade = remarksParallelUpgrade;
	}
	
	
	
	//[002] End
	
}
