package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;
//[01]  opportunityId added  by Lawkush in reports "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"
//[02]  Priya Gupta  27-Jan-2016  Added 3 new columns
//[03]  Gunjan Singla	5-May-15    20160418-R1-022266  Added columns
public class OrderDetailReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private String accountManager;
	private int crmAccountNo;
    private String orderType2;
	
	private String toDate;
	
	private String fromDate;
	private int fromAccountNo;
	private String StoreName;
	private int toAccountNo;
	private int fromOrderNo;
	private int toOrderNo;
	private String demo;
	private String fromOrderDate;
	private String toOrderDate;
	private String osp2;
	private String cus_segment;
	private String act_category;
	private String verticalDetails;
	private String billingPODate;
	private String serviceDetDescription;
	private String linename;
	private String storename;
	private int contractPeriod;
	private String companyName;
	private String orderDate;
	private String currencyCode;
	private String chargeName;
	private double poAmounts;
	private String challenno;
	private int orderNumber;
	private double chargeAmount;
	private String copcApproveDate;
	private String taxation;
	private String orderType;
	private int AccountID;
	private String CrmACcountNO;
	private String projectManager;
	private String regionName;
	private int orderLineNumber;
	private String cancelflag;
	private String provisionBandWidth;
	private String uom;
	private String billingBandWidth;
	private String billUom;
	private String LocDate;
	private String categoryOfOrder;
	private String customerRfsDate;
	private String customerServiceRfsDate;
	private String customerPoDate;
	private String customerPoNumber;
	private String cyclicNonCyclic;
	private String partyName;
	private String endDateLogic;
	private String remarks;
	private String osp;
	private String fromSite;
	private String toSite;
	private int itemQuantity;
	private String kmsDistance;
	private String lineItemDescription;
	private String amReceiveDate;
	private double orderTotal;
	private String orderEntryDate;
	private String licenceCoName;
	private String logicalCircuitNumber;
	private String paymentTerm;
	private String oldLineitemAmount;
	private String demoType;
	private String orderStageDescription;
	private String serviceStageDescription;
	private String chargeEndDate;
	private String newOrderRemark;
	private String serviceOrderType;
	// <!--GlobalDataBillingEfficiency BFR6  -->		
	private String taxExcemption_Reason;
//	[01] Start  	 
	 private String opportunityId;	
	// [02] Start
	 private String salesForceOpportunityNumber;
	 private String networkType;
	 private String channelPartner;
	 private String partnerId;
	 //[02] End
	//[03] start
	private String fieldEngineer;
	private String partnerCode;
	//[03] end
	 public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
//	[01] end  
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getAct_category() {
		return act_category;
	}
	public void setAct_category(String act_category) {
		this.act_category = act_category;
	}
	public String getAmReceiveDate() {
		return amReceiveDate;
	}
	public void setAmReceiveDate(String amReceiveDate) {
		this.amReceiveDate = amReceiveDate;
	}
	public String getBillingBandWidth() {
		return billingBandWidth;
	}
	public void setBillingBandWidth(String billingBandWidth) {
		this.billingBandWidth = billingBandWidth;
	}
	public String getBillingPODate() {
		return billingPODate;
	}
	public void setBillingPODate(String billingPODate) {
		this.billingPODate = billingPODate;
	}
	public String getBillUom() {
		return billUom;
	}
	public void setBillUom(String billUom) {
		this.billUom = billUom;
	}
	public String getCancelflag() {
		return cancelflag;
	}
	public void setCancelflag(String cancelflag) {
		this.cancelflag = cancelflag;
	}
	public String getCategoryOfOrder() {
		return categoryOfOrder;
	}
	public void setCategoryOfOrder(String categoryOfOrder) {
		this.categoryOfOrder = categoryOfOrder;
	}
	public String getChallenno() {
		return challenno;
	}
	public void setChallenno(String challenno) {
		this.challenno = challenno;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getChargeEndDate() {
		return chargeEndDate;
	}
	public void setChargeEndDate(String chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getCopcApproveDate() {
		return copcApproveDate;
	}
	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}
	public int getCrmAccountNo() {
		return crmAccountNo;
	}
	public void setCrmAccountNo(int crmAccountNo) {
		this.crmAccountNo = crmAccountNo;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCus_segment() {
		return cus_segment;
	}
	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}
	public String getCustomerPoDate() {
		return customerPoDate;
	}
	public void setCustomerPoDate(String customerPoDate) {
		this.customerPoDate = customerPoDate;
	}
	public String getCustomerPoNumber() {
		return customerPoNumber;
	}
	public void setCustomerPoNumber(String customerPoNumber) {
		this.customerPoNumber = customerPoNumber;
	}
	public String getCustomerRfsDate() {
		return customerRfsDate;
	}
	public void setCustomerRfsDate(String customerRfsDate) {
		this.customerRfsDate = customerRfsDate;
	}
	public String getCustomerServiceRfsDate() {
		return customerServiceRfsDate;
	}
	public void setCustomerServiceRfsDate(String customerServiceRfsDate) {
		this.customerServiceRfsDate = customerServiceRfsDate;
	}
	public String getCyclicNonCyclic() {
		return cyclicNonCyclic;
	}
	public void setCyclicNonCyclic(String cyclicNonCyclic) {
		this.cyclicNonCyclic = cyclicNonCyclic;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getDemoType() {
		return demoType;
	}
	public void setDemoType(String demoType) {
		this.demoType = demoType;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public int getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getFromOrderDate() {
		return fromOrderDate;
	}
	public void setFromOrderDate(String fromOrderDate) {
		this.fromOrderDate = fromOrderDate;
	}
	public int getFromOrderNo() {
		return fromOrderNo;
	}
	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
	}
	public String getFromSite() {
		return fromSite;
	}
	public void setFromSite(String fromSite) {
		this.fromSite = fromSite;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public String getKmsDistance() {
		return kmsDistance;
	}
	public void setKmsDistance(String kmsDistance) {
		this.kmsDistance = kmsDistance;
	}
	public String getLicenceCoName() {
		return licenceCoName;
	}
	public void setLicenceCoName(String licenceCoName) {
		this.licenceCoName = licenceCoName;
	}
	public String getLineItemDescription() {
		return lineItemDescription;
	}
	public void setLineItemDescription(String lineItemDescription) {
		this.lineItemDescription = lineItemDescription;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getLogicalCircuitNumber() {
		return logicalCircuitNumber;
	}
	public void setLogicalCircuitNumber(String logicalCircuitNumber) {
		this.logicalCircuitNumber = logicalCircuitNumber;
	}
	public String getNewOrderRemark() {
		return newOrderRemark;
	}
	public void setNewOrderRemark(String newOrderRemark) {
		this.newOrderRemark = newOrderRemark;
	}
	public String getOldLineitemAmount() {
		return oldLineitemAmount;
	}
	public void setOldLineitemAmount(String oldLineitemAmount) {
		this.oldLineitemAmount = oldLineitemAmount;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderEntryDate() {
		return orderEntryDate;
	}
	public void setOrderEntryDate(String orderEntryDate) {
		this.orderEntryDate = orderEntryDate;
	}
	public int getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(int orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderStageDescription() {
		return orderStageDescription;
	}
	public void setOrderStageDescription(String orderStageDescription) {
		this.orderStageDescription = orderStageDescription;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderType2() {
		return orderType2;
	}
	public void setOrderType2(String orderType2) {
		this.orderType2 = orderType2;
	}
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
	}
	public String getOsp2() {
		return osp2;
	}
	public void setOsp2(String osp2) {
		this.osp2 = osp2;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public double getPoAmounts() {
		return poAmounts;
	}
	public void setPoAmounts(double poAmounts) {
		this.poAmounts = poAmounts;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getProvisionBandWidth() {
		return provisionBandWidth;
	}
	public void setProvisionBandWidth(String provisionBandWidth) {
		this.provisionBandWidth = provisionBandWidth;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getServiceDetDescription() {
		return serviceDetDescription;
	}
	public void setServiceDetDescription(String serviceDetDescription) {
		this.serviceDetDescription = serviceDetDescription;
	}
	public String getServiceOrderType() {
		return serviceOrderType;
	}
	public void setServiceOrderType(String serviceOrderType) {
		this.serviceOrderType = serviceOrderType;
	}
	public String getServiceStageDescription() {
		return serviceStageDescription;
	}
	public void setServiceStageDescription(String serviceStageDescription) {
		this.serviceStageDescription = serviceStageDescription;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public int getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(int toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getToOrderDate() {
		return toOrderDate;
	}
	public void setToOrderDate(String toOrderDate) {
		this.toOrderDate = toOrderDate;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
	public String getToSite() {
		return toSite;
	}
	public void setToSite(String toSite) {
		this.toSite = toSite;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getVerticalDetails() {
		return verticalDetails;
	}
	public void setVerticalDetails(String verticalDetails) {
		this.verticalDetails = verticalDetails;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public int getAccountID() {
		return AccountID;
	}
	public void setAccountID(int accountID) {
		AccountID = accountID;
	}
	public String getCrmACcountNO() {
		return CrmACcountNO;
	}
	public void setCrmACcountNO(String crmACcountNO) {
		CrmACcountNO = crmACcountNO;
	}
	public String getLocDate() {
		return LocDate;
	}
	public void setLocDate(String locDate) {
		LocDate = locDate;
	}
	public void setStoreName(String string) {
		// TODO Auto-generated method stub
		
	}
	public String getStoreName() {
		return StoreName;
	}
	public String getTaxExcemption_Reason() {
		return taxExcemption_Reason;
	}
	public void setTaxExcemption_Reason(String taxExcemption_Reason) {
		this.taxExcemption_Reason = taxExcemption_Reason;
	}
	
	
	//[02] Start
		public String getSalesForceOpportunityNumber() {
			return salesForceOpportunityNumber;
		}
		public void setSalesForceOpportunityNumber(String salesForceOpportunityNumber) {
			this.salesForceOpportunityNumber = salesForceOpportunityNumber;
		}
		public String getNetworkType() {
			return networkType;
		}
		public void setNetworkType(String networkType) {
			this.networkType = networkType;
		}
		public String getChannelPartner() {
			return channelPartner;
		}
		public void setChannelPartner(String channelPartner) {
			this.channelPartner = channelPartner;
		}
		public String getPartnerId() {
			return partnerId;
		}
		public void setPartnerId(String partnerId) {
			this.partnerId = partnerId;
		}
		
		//[02] End
		//[03] start
		public String getFieldEngineer() {
			return fieldEngineer;
		}
		public void setFieldEngineer(String fieldEngineer) {
			this.fieldEngineer = fieldEngineer;
		}
		public String getPartnerCode() {
			return partnerCode;
		}
		public void setPartnerCode(String partnerCode) {
			this.partnerCode = partnerCode;
		}
		//[03] end
}
