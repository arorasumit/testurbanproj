package com.ibm.ioes.newdesign.form;

import java.util.List;

import com.ibm.ioes.forms.PagingCommonDto;
import com.ibm.ioes.newdesign.dto.OpportunityDto;

public class SelectOpportunityPageForm {
	private List<OpportunityDto> opportunityDto;
	private PagingCommonDto pagingCommonDto;
	public List<OpportunityDto> getOpportunityDto() {
		return opportunityDto;
	}
	public void setOpportunityDto(List<OpportunityDto> opportunityDto) {
		this.opportunityDto = opportunityDto;
	}
	public PagingCommonDto getPagingCommonDto() {
		return pagingCommonDto;
	}
	public void setPagingCommonDto(PagingCommonDto pagingCommonDto) {
		this.pagingCommonDto = pagingCommonDto;
	}
}
