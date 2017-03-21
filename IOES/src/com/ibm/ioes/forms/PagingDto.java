/*tag		Name       Date			CSR No			Description 
[001]	ANIL KUMAR 	18-march-2011				Creating PagingDto for use paging sorting with Ajax json object 
 */
//start [001]
package com.ibm.ioes.forms;
import com.ibm.ioes.utilities.PagingSorting;
import com.ibm.ioes.beans.SessionObjectsDto;
/**
 * @author Administrator
 *
 */
public class PagingDto extends NewOrderDto {

	/**
	 * 
	 */
	public PagingDto() {
		// TODO Auto-generated constructor stub
	}
	
	private SessionObjectsDto sessionObjectsDto;
	{
		if(SessionObjectsDto.getInstance() != null){
		sessionObjectsDto = SessionObjectsDto.getInstance();
		}
	}
	
	PagingSorting pagingSorting=new PagingSorting();
	private int pageRecords=10;//sessionObjectsDto.getPageSize();
	private int startIndex;
	private int endIndex;
	private String sortBycolumn;
	private String sortByOrder;
	private int maxPageNumber;
	private int recordCount;
	private String bcpid;
//	Raghu
//	added for Pop Location PopUp on product catelog page
		private String searchAlphabet;
	
	public String getSearchAlphabet() {
			return searchAlphabet;
		}

		public void setSearchAlphabet(String searchAlphabet) {
			this.searchAlphabet = searchAlphabet;
		}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
		int maxpageNo=(int)Math.ceil((double)this.getRecordCount()/(double)this.getPageRecords());
		this.maxPageNumber = (1>maxpageNo)?1:maxpageNo;
	}

	public int getMaxPageNumber() {		
		return maxPageNumber;
	}
	
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getSortBycolumn() {
		return sortBycolumn;
	}

	public void setSortBycolumn(String sortBycolumn) {
		this.sortBycolumn = sortBycolumn;
	}

	public String getSortByOrder() {
		return sortByOrder;
	}

	public void setSortByOrder(String sortByOrder) {
		this.sortByOrder = sortByOrder;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageRecords() {
		return pageRecords;
	}

	public void setPageRecords(int pageRecords) {
		this.pageRecords = pageRecords;
	}

	public String getBcpid() {
		return bcpid;
	}

	public void setBcpid(String bcpid) {
		this.bcpid = bcpid;
	}
	
}
//end [001]