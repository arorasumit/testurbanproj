package com.ibm.ioes.forms;

public class RoleSectionDetailDTO {
	private int role;
	private String section;
	private boolean commercialAllowed;
	private boolean nonCommercialAllowed;
	private boolean subSectionAttributeCheck;
	private boolean sectionCommercial;
	public boolean isSectionCommercial() {
		return sectionCommercial;
	}
	public void setSectionCommercial(boolean sectionCommercial) {
		this.sectionCommercial = sectionCommercial;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public boolean isCommercialAllowed() {
		return commercialAllowed;
	}
	public void setCommercialAllowed(boolean commercialAllowed) {
		this.commercialAllowed = commercialAllowed;
	}
	public boolean isNonCommercialAllowed() {
		return nonCommercialAllowed;
	}
	public void setNonCommercialAllowed(boolean nonCommercialAllowed) {
		this.nonCommercialAllowed = nonCommercialAllowed;
	}
	public boolean isSubSectionAttributeCheck() {
		return subSectionAttributeCheck;
	}
	public void setSubSectionAttributeCheck(boolean subSectionAttributeCheck) {
		this.subSectionAttributeCheck = subSectionAttributeCheck;
	}
	
}
