/**
 * 
 */
package com.ibm.ioes.ei.m6;

/**
 * @author IBM
 * 
 */
public class XMLTags {

	public enum TAGS {
		OFEvent("OFEvent"),
		crmorderId("crmorderId"),
		ServiceListId("ServiceListId"),
		PreOrderNo("PreOrderNo"),
		M6OrderId("M6OrderId"),
		orderStatus("orderStatus"),
		eventId("eventId"),
		eventTypeId("eventTypeId"),
		creationDate("creationDate"),
		reason("reason"),
		
		// raghu
		OFEventParams("OFEventParams"),
		verb("verb"),
		orderLineNo("orderLineNo"),
		paramKey("paramKey"),
		value("value");

		String tag;

		private TAGS(String tag) {
			this.tag = tag;
		}

	}
	/*
	public enum RESPONSE_TAGS {
		
		SBASERVICEREQUEST("SBAServiceRequest"),SBAREQUESTID("sbaRequestID"),MOBILENUMBER("mobileNumber"),CRMSTATUS("crmStatus");

		String responseTag;

		private RESPONSE_TAGS(String tag) {
			this.responseTag = tag;
		}

	}*/

}
