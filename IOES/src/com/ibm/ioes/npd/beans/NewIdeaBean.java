package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.model.NewIdeaDto;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.Utility;

public class NewIdeaBean extends ActionForm {

		public static String MODE_NEW = "MODE_NEW";
		public static String MODE_VIEW = "MODE_VIEW";
	
		private String mode=null;
	
		private String nameGenerator=null;
		private String mailId=null;
		private String phoneNo=null;
		private String function=null;
		private String location=null;
		private String briefDesc=null;
		
		
		private String benefit=null;
		
		//private String similarProductDetails=null;
		private String industryVertical=null;
		
		private String usp=null;
		
		
		private String isSimilarProductExist=null;
		private String country=null;
		private String organisation=null;
		private String prdDescription=null;
		
		private String marketSize=null;
		
		private String hrms_employeenumber=null;
		
		
		private String fromDate = null;
		private String toDate = null;
		private PagingSorting pagingSorting = new PagingSorting();
		
		private String exportExcel = null;
		
		ArrayList<NewIdeaDto> reportList=null;
		
		private String reportID=null;
		
		private long ideaId;
		
		private String searchName = null;
		private String searchOracleId = null;
		private String searchEmailId = null;
		
		
		
		
		
		public String getBenefit() {
			return benefit;
		}
		public void setBenefit(String benefit) {
			this.benefit = benefit;
		}
		public String getBriefDesc() {
			return briefDesc;
		}
		public void setBriefDesc(String briefDesc) {
			this.briefDesc = briefDesc;
		}
		public String getFunction() {
			return function;
		}
		public void setFunction(String function) {
			this.function = function;
		}
		public String getIndustryVertical() {
			return industryVertical;
		}
		public void setIndustryVertical(String industryVertical) {
			this.industryVertical = industryVertical;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getMailId() {
			return mailId;
		}
		public void setMailId(String mailId) {
			this.mailId = mailId;
		}
		public String getNameGenerator() {
			return nameGenerator;
		}
		public void setNameGenerator(String nameGenerator) {
			this.nameGenerator = nameGenerator;
		}
		public String getPhoneNo() {
			return phoneNo;
		}
		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}
		/*public String getSimilarProductDetails() {
			return similarProductDetails;
		}
		public void setSimilarProductDetails(String similarProductDetails) {
			this.similarProductDetails = similarProductDetails;
		}*/
		public String getUsp() {
			return usp;
		}
		public void setUsp(String usp) {
			this.usp = usp;
		}
		public String getMode() {
			return mode;
		}
		public void setMode(String mode) {
			this.mode = mode;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getIsSimilarProductExist() {
			return isSimilarProductExist;
		}
		public void setIsSimilarProductExist(String idSimilarProductExist) {
			this.isSimilarProductExist = idSimilarProductExist;
		}
		public String getMarketSize() {
			return marketSize;
		}
		public void setMarketSize(String marketSize) {
			this.marketSize = marketSize;
		}
		public String getOrganisation() {
			return organisation;
		}
		public void setOrganisation(String organisation) {
			this.organisation = organisation;
		}
		public String getPrdDescription() {
			return prdDescription;
		}
		public void setPrdDescription(String prdDescription) {
			this.prdDescription = prdDescription;
		}
		public String getHrms_employeenumber() {
			return hrms_employeenumber;
		}
		public void setHrms_employeenumber(String hrms_employeenumber) {
			this.hrms_employeenumber = hrms_employeenumber;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getToDate() {
			return toDate;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
		public PagingSorting getPagingSorting() {
			return pagingSorting;
		}
		public void setPagingSorting(PagingSorting pagingSorting) {
			this.pagingSorting = pagingSorting;
		}
		public String getExportExcel() {
			return exportExcel;
		}
		public void setExportExcel(String exportExcel) {
			this.exportExcel = exportExcel;
		}
		public ArrayList<NewIdeaDto> getReportList() {
			return reportList;
		}
		public void setReportList(ArrayList<NewIdeaDto> reportList) {
			this.reportList = reportList;
		}
		public String getReportID() {
			return reportID;
		}
		public void setReportID(String reportID) {
			this.reportID = reportID;
		}
		public long getIdeaId() {
			return ideaId;
		}
		public void setIdeaId(long ideaId) {
			this.ideaId = ideaId;
		}
		public String getSearchEmailId() {
			return searchEmailId;
		}
		public void setSearchEmailId(String searchEmailId) {
			this.searchEmailId = searchEmailId;
		}
		public String getSearchName() {
			return searchName;
		}
		public void setSearchName(String searchName) {
			this.searchName = searchName;
		}
		public String getSearchOracleId() {
			return searchOracleId;
		}
		public void setSearchOracleId(String searchOracleId) {
			this.searchOracleId = searchOracleId;
		}
		
		
	
		
		
		
		
}
