package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;

public class DocumentMatrixReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private String toCOPCDate;
	private String fromCOPCDate;
	private String accountNo;
	private String accountName;
	private String ordType;
	private String ordSubType;
	private String accntMgr;
	private String salesCo;
	private String region;
	private String custSegment;
	private String custSegmentID;
	private String orderNo;
	private String lsiNo;
	private String stage;
	private String serviceNo;
	private String copcApprovr;
	private String copcApprovrID;
	private String copcApprovrRemarks;
	private String docCAF;
	private String docOFS;
	private String docISP;
	private String docTnC;
	private String docPO;
	private String docRFP;
	private String docNtwrk;
	private String docCustAgreemnt;
	private String docPCN;
	private String docSOW;
	private String docOther;
	private String docPCD;
	private String docFeasibility;
	private String docThirdParty;
	private String docLOU;
	private String copcAprDate;
	private String docAnyDeviation;
	private String docNonIndiaTouchLink;
	private String docPMTA; //priya
	
	
	public String getCopcAprDate() {
		return copcAprDate;
	}
	public void setCopcAprDate(String copcAprDate) {
		this.copcAprDate = copcAprDate;
	}
	public String getDocAnyDeviation() {
		return docAnyDeviation;
	}
	public void setDocAnyDeviation(String docAnyDeviation) {
		this.docAnyDeviation = docAnyDeviation;
	}
	public String getDocNonIndiaTouchLink() {
		return docNonIndiaTouchLink;
	}
	public void setDocNonIndiaTouchLink(String docNonIndiaTouchLink) {
		this.docNonIndiaTouchLink = docNonIndiaTouchLink;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getToCOPCDate() {
		return toCOPCDate;
	}
	public void setToCOPCDate(String toCOPCDate) {
		this.toCOPCDate = toCOPCDate;
	}
	public String getFromCOPCDate() {
		return fromCOPCDate;
	}
	public void setFromCOPCDate(String fromCOPCDate) {
		this.fromCOPCDate = fromCOPCDate;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getOrdType() {
		return ordType;
	}
	public void setOrdType(String ordType) {
		this.ordType = ordType;
	}
	public String getOrdSubType() {
		return ordSubType;
	}
	public void setOrdSubType(String ordSubType) {
		this.ordSubType = ordSubType;
	}
	public String getAccntMgr() {
		return accntMgr;
	}
	public void setAccntMgr(String accntMgr) {
		this.accntMgr = accntMgr;
	}
	public String getSalesCo() {
		return salesCo;
	}
	public void setSalesCo(String salesCo) {
		this.salesCo = salesCo;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCustSegment() {
		return custSegment;
	}
	public void setCustSegment(String custSegment) {
		this.custSegment = custSegment;
	}
	public String getCustSegmentID() {
		return custSegmentID;
	}
	public void setCustSegmentID(String custSegmentID) {
		this.custSegmentID = custSegmentID;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getLsiNo() {
		return lsiNo;
	}
	public void setLsiNo(String lsiNo) {
		this.lsiNo = lsiNo;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getCopcApprovr() {
		return copcApprovr;
	}
	public void setCopcApprovr(String copcApprovr) {
		this.copcApprovr = copcApprovr;
	}
	public String getCopcApprovrID() {
		return copcApprovrID;
	}
	public void setCopcApprovrID(String copcApprovrID) {
		this.copcApprovrID = copcApprovrID;
	}
	public String getCopcApprovrRemarks() {
		return copcApprovrRemarks;
	}
	public void setCopcApprovrRemarks(String copcApprovrRemarks) {
		this.copcApprovrRemarks = copcApprovrRemarks;
	}
	public String getDocCAF() {
		return docCAF;
	}
	public void setDocCAF(String docCAF) {
		this.docCAF = docCAF;
	}
	public String getDocOFS() {
		return docOFS;
	}
	public void setDocOFS(String docOFS) {
		this.docOFS = docOFS;
	}
	public String getDocISP() {
		return docISP;
	}
	public void setDocISP(String docISP) {
		this.docISP = docISP;
	}
	public String getDocTnC() {
		return docTnC;
	}
	public void setDocTnC(String docTnC) {
		this.docTnC = docTnC;
	}
	public String getDocPO() {
		return docPO;
	}
	public void setDocPO(String docPO) {
		this.docPO = docPO;
	}
	public String getDocRFP() {
		return docRFP;
	}
	public void setDocRFP(String docRFP) {
		this.docRFP = docRFP;
	}
	public String getDocNtwrk() {
		return docNtwrk;
	}
	public void setDocNtwrk(String docNtwrk) {
		this.docNtwrk = docNtwrk;
	}
	public String getDocCustAgreemnt() {
		return docCustAgreemnt;
	}
	public void setDocCustAgreemnt(String docCustAgreemnt) {
		this.docCustAgreemnt = docCustAgreemnt;
	}
	public String getDocPCN() {
		return docPCN;
	}
	public void setDocPCN(String docPCN) {
		this.docPCN = docPCN;
	}
	public String getDocSOW() {
		return docSOW;
	}
	public void setDocSOW(String docSOW) {
		this.docSOW = docSOW;
	}
	public String getDocOther() {
		return docOther;
	}
	public void setDocOther(String docOther) {
		this.docOther = docOther;
	}
	public String getDocPCD() {
		return docPCD;
	}
	public void setDocPCD(String docPCD) {
		this.docPCD = docPCD;
	}
	public String getDocFeasibility() {
		return docFeasibility;
	}
	public void setDocFeasibility(String docFeasibility) {
		this.docFeasibility = docFeasibility;
	}
	public String getDocThirdParty() {
		return docThirdParty;
	}
	public void setDocThirdParty(String docThirdParty) {
		this.docThirdParty = docThirdParty;
	}
	public String getDocLOU() {
		return docLOU;
	}
	public void setDocLOU(String docLOU) {
		this.docLOU = docLOU;
	}	
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	//priya start
	public String getDocPMTA() {
		return docPMTA;
	}
	public void setDocPMTA(String docPMTA) {
		this.docPMTA = docPMTA;
	}
	//priya end
	
}
