package com.ibm.ioes.clep;

import javax.jms.QueueConnection;
import javax.jms.QueueSession;

public class CLEPXmlDto {
	
String jmsMessageID=null;
String clepQueue_host=null;
String clepQueue_channel=null;
int clepQueue_port=0;
String clepQueue_qmgrName=null;
String clepQueue_reqQName=null;
String clepQueue_respQName=null;
String xmlData=null;
int send_status=0;
String sendMessageID=null;
QueueSession session=null;
long processId=0l;
QueueConnection JMSConnection=null;
long projectManagerId=0l;
String userId="";
String password="";

//Vijat start
String stage="";
String workFlowId;
//Vijay end

//start rave
long xmlfileid;
public long getXmlfileid() {
	return xmlfileid;
}

public void setXmlfileid(long xmlfileid) {
	this.xmlfileid = xmlfileid;
}
//end ravi

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public long getProjectManagerId() {
	return projectManagerId;
}

public void setProjectManagerId(long projectManagerId) {
	this.projectManagerId = projectManagerId;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public long getProcessId() {
	return processId;
}

public void setProcessId(long processId) {
	this.processId = processId;
}

public int getSend_status() {
	return send_status;
}

public void setSend_status(int send_status) {
	this.send_status = send_status;
}

public String getSendMessageID() {
	return sendMessageID;
}

public void setSendMessageID(String sendMessageID) {
	this.sendMessageID = sendMessageID;
}

public String getXmlData() {
	return xmlData;
}

public void setXmlData(String xmlData) {
	this.xmlData = xmlData;
}

public String getClepQueue_channel() {
	return clepQueue_channel;
}

public void setClepQueue_channel(String clepQueue_channel) {
	this.clepQueue_channel = clepQueue_channel;
}

public String getClepQueue_host() {
	return clepQueue_host;
}

public void setClepQueue_host(String clepQueue_host) {
	this.clepQueue_host = clepQueue_host;
}

public String getClepQueue_qmgrName() {
	return clepQueue_qmgrName;
}

public void setClepQueue_qmgrName(String clepQueue_qmgrName) {
	this.clepQueue_qmgrName = clepQueue_qmgrName;
}

public String getClepQueue_reqQName() {
	return clepQueue_reqQName;
}

public void setClepQueue_reqQName(String clepQueue_reqQName) {
	this.clepQueue_reqQName = clepQueue_reqQName;
}

public String getClepQueue_respQName() {
	return clepQueue_respQName;
}

public void setClepQueue_respQName(String clepQueue_respQName) {
	this.clepQueue_respQName = clepQueue_respQName;
}

public String getJmsMessageID() {
	return jmsMessageID;
}

public void setJmsMessageID(String jmsMessageID) {
	this.jmsMessageID = jmsMessageID;
}

public int getClepQueue_port() {
	return clepQueue_port;
}

public void setClepQueue_port(int clepQueue_port) {
	this.clepQueue_port = clepQueue_port;
}

public QueueSession getSession() {
	return session;
}

public void setSession(QueueSession session) {
	this.session = session;
}

public QueueConnection getJMSConnection() {
	return JMSConnection;
}

public void setJMSConnection(QueueConnection connection) {
	JMSConnection = connection;
}

//vijay start
public String getStage() {
	return stage;
}

public void setStage(String stage) {
	this.stage = stage;
}

public String getWorkFlowId() {
	return workFlowId;
}

public void setWorkFlowId(String workFlowId) {
	this.workFlowId = workFlowId;
}
//vijay end

}
