package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

public class SubMenuBean {
	
	private String Id=null;
	private String menuId=null;
	private String label=null;
	private String url=null;
	private ArrayList subSubMenuList=null;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public ArrayList getSubSubMenuList() {
		return subSubMenuList;
	}
	public void setSubSubMenuList(ArrayList subSubMenuList) {
		this.subSubMenuList = subSubMenuList;
	}
	

}
