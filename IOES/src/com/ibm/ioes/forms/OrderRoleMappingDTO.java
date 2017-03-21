package com.ibm.ioes.forms;

public class OrderRoleMappingDTO {
private Long orderNo;
private String ownerTypeID;



public String getOwnerTypeID() {
	return ownerTypeID;
}

public void setOwnerTypeID(String ownerTypeID) {
	this.ownerTypeID = ownerTypeID;
}

public Long getOrderNo() {
	return orderNo;
}

public void setOrderNo(Long orderNo) {
	this.orderNo = orderNo;
}

}
