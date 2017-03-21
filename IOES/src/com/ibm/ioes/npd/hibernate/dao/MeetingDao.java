package com.ibm.ioes.npd.hibernate.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.MeetingBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.LocationDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmMeetings;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingattendies;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingmoms;
import com.ibm.ioes.npd.hibernate.beans.TtrnMeetingschedules;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.utilities.AppConstants;
import com.ibm.ioes.npd.utilities.AppUtility;
import com.ibm.ioes.npd.utilities.HibernateBeansConstants;
import com.ibm.ioes.npd.utilities.Messages;
import com.ibm.ioes.npd.utilities.PagingSorting;
import com.ibm.ioes.npd.utilities.SMSUtil;
import com.ibm.ioes.npd.utilities.SendMailUtility;
import com.ibm.ioes.npd.utilities.Utility;
import com.ibm.ioes.utilities.DbConnection;

/**
 * @author Disha
 * @version 1.0
 */

public class MeetingDao extends CommonBaseDao {

	// This method saves the Meeting date,time,subject and attendies into
	// database
	
	private static final String baseDirPath = Messages.getMessageValue("TEMPORARY_DIR_PATH");
	public static final String ICS_TIME_FORMAT = "HHmmss";

	public MeetingBean saveMeetingSchedule(MeetingBean meetingBean,
			Session hibernateSession,TmEmployee tmEmployee) throws Exception {

		boolean insert = true;
		TtrnMeetingschedules ttrnMeetingschedules = new TtrnMeetingschedules();
		TtrnMeetingattendies ttrnMeetingattendies1 = null;
		TmMeetings tmMeetings = new TmMeetings();
		ArrayList meetingTypeList = new ArrayList();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);
		SimpleDateFormat simpleDateFormat_time = new SimpleDateFormat(
				AppConstants.TIME_FORMAT);

		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		Blob b = null;

		try {

			Criteria ce = hibernateSession.createCriteria(TmMeetings.class);
			ce = ce.add(Restrictions.eq("meetingtype",
					meetingBean.getMeetingType()).ignoreCase());

			if (ce.list() != null) {
				meetingTypeList = (ArrayList) ce.list();
			}
			if (meetingTypeList.size() > 0) {
				tmMeetings = (TmMeetings) meetingTypeList.get(0);
			}
			ttrnMeetingschedules.setIsactive(AppConstants.ACTIVE_FLAG);
			ttrnMeetingschedules.setTmMeetings(tmMeetings);
			ttrnMeetingschedules.setMeetingdate(simpleDateFormat
					.parse(meetingBean.getDate()));
			ttrnMeetingschedules.setSubject(meetingBean.getSubject());
			ttrnMeetingschedules.setStarttime(simpleDateFormat_time
					.parse(meetingBean.getStartTime()));
			ttrnMeetingschedules.setEndtime(simpleDateFormat_time
					.parse(meetingBean.getEndTime()));
			if (meetingBean.getAttachment() != null) {
				b = null;
				byte[] FileData = meetingBean.getAttachment().getFileData();
				b= Hibernate.createBlob(FileData);
				ttrnMeetingschedules.setAttachment(b);
				ttrnMeetingschedules.setFilename(meetingBean.getAttachment().getFileName());
			}
			if (!"".equals(meetingBean.getAttachment1().getFileName())) {
				b = null;
				byte[] FileData = meetingBean.getAttachment1().getFileData();
				 b = Hibernate.createBlob(FileData);
				ttrnMeetingschedules.setAttachment1(b);
				ttrnMeetingschedules.setFilename1(meetingBean.getAttachment1().getFileName());
			}
			if (!"".equals(meetingBean.getAttachment2().getFileName())) {
				b = null;
				byte[] FileData = meetingBean.getAttachment2().getFileData();
				 b = Hibernate.createBlob(FileData);
				ttrnMeetingschedules.setAttachment2(b);
				ttrnMeetingschedules.setFilename2(meetingBean.getAttachment2().getFileName());
			}
			ttrnMeetingschedules.setTmMeetings(tmMeetings);
			if(tmEmployee!=null)
			ttrnMeetingschedules.setCretaedby(tmEmployee.getNpdempid());
			Date d=new Date();
			meetingBean.setCreateDate((d));
			meetingBean.setCreatedID(""+tmEmployee.getNpdempid());
			TtrnProject ttrnProject= (TtrnProject) commonBaseDao.findById(Long.parseLong(meetingBean.getProductId()),
						HibernateBeansConstants.HBM_PROJECT, hibernateSession);
			//ttrnMeetingschedules.setProductid(Long.parseLong(meetingBean.getProductId()));
			ttrnMeetingschedules.setTtrnProduct(ttrnProject);
			
			ttrnMeetingschedules.setLocationid(Long.parseLong(meetingBean.getLocationId()));
			ttrnMeetingschedules.setCretaeddate(new Date());
			commonBaseDao.attachDirty(ttrnMeetingschedules, hibernateSession);

			if (ttrnMeetingschedules.getMeetingid() > 0) {
				meetingBean.setMeetingIdCreated(new Long(ttrnMeetingschedules
						.getMeetingid()).toString());
			}

			if (meetingBean.getSelectedMandatoryId() != null
					&& meetingBean.getSelectedMandatoryId().length > 0) {
				for (int x = 0; x < meetingBean.getSelectedMandatoryId().length; x++) {
					TtrnMeetingattendies ttrnMeetingattendies = new TtrnMeetingattendies();
					ttrnMeetingattendies
							.setTtrnMeetingschedules(ttrnMeetingschedules);
					ttrnMeetingattendies.setNpdempid(new Long(meetingBean
							.getSelectedMandatoryId()[x]).longValue());
					ttrnMeetingattendies
							.setAttentype(AppConstants.MANDATORY_LIST_TYPE);
					ttrnMeetingattendies.setCreateddate(new Date());
					if(tmEmployee!=null)
						ttrnMeetingattendies.setCreatedby(tmEmployee.getNpdempid())	;
					commonBaseDao.attachDirty(ttrnMeetingattendies,
							hibernateSession);
				}
			}

			if (meetingBean.getSelectedOptionalId() != null
					&& meetingBean.getSelectedOptionalId().length > 0) {
				for (int x = 0; x < meetingBean.getSelectedOptionalId().length; x++) {
					TtrnMeetingattendies ttrnMeetingattendies = new TtrnMeetingattendies();
					ttrnMeetingattendies
							.setTtrnMeetingschedules(ttrnMeetingschedules);
					ttrnMeetingattendies.setNpdempid(new Long(meetingBean
							.getSelectedOptionalId()[x]).longValue());
					ttrnMeetingattendies
							.setAttentype(AppConstants.OPTIONAL_LIST_TYPE);
					ttrnMeetingattendies.setCreateddate(new Date());
					if(tmEmployee!=null)
						ttrnMeetingattendies.setCreatedby(tmEmployee.getNpdempid())	;
					commonBaseDao.attachDirty(ttrnMeetingattendies,
							hibernateSession);
				}
			}
			meetingBean.setMeetingId(new Long(ttrnMeetingschedules.getMeetingid()).toString());

		} catch (Exception e) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			commonBaseDao.closeTrans(hibernateSession);
		}

		return meetingBean;

	}

	// This method sends mail to attendies as a meeting invite.

	public void sendMailForSchedule(MeetingBean meetingBean,
			Session hibernateSession) throws Exception {

		SendMailUtility oSendMail = new SendMailUtility();
		ArrayList toList = new ArrayList();
		ArrayList ccList = new ArrayList();
		Object[] messageParameters = new Object[7];
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmEmployee tmEmployee = new TmEmployee();
		ArrayList phoneNos=new ArrayList();
		
		if (meetingBean.getSelectedMandatoryId() != null
				&& meetingBean.getSelectedMandatoryId().length > 0) {
			for (int x = 0; x < meetingBean.getSelectedMandatoryId().length; x++) {
				tmEmployee = (TmEmployee) commonBaseDao.findById(Long
						.parseLong(meetingBean.getSelectedMandatoryId()[x]),
						HibernateBeansConstants.HBM_EMPLOYEE, hibernateSession);
				toList.add(tmEmployee.getEmail());
				phoneNos.add(tmEmployee.getMobileNo());
			}
		}

		
		if (meetingBean.getSelectedOptionalId() != null
				&& meetingBean.getSelectedOptionalId().length > 0) 
		{
			for (int x = 0; x < meetingBean.getSelectedOptionalId().length; x++) {
				tmEmployee = (TmEmployee) commonBaseDao.findById(Long
						.parseLong(meetingBean.getSelectedOptionalId()[x]),
						HibernateBeansConstants.HBM_EMPLOYEE, hibernateSession);
				ccList.add(tmEmployee.getEmail());
				phoneNos.add(tmEmployee.getMobileNo());
			}
		}
		
		
		SimpleDateFormat inFormat=new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat outFormat=new SimpleDateFormat("dd/MM/yy");
		
		
		messageParameters[0] = outFormat.format(inFormat.parse(meetingBean.getDate()));
		messageParameters[1] = meetingBean.getStartTime();
		messageParameters[2] = meetingBean.getEndTime();
		messageParameters[3] = meetingBean.getSubject();
		TtrnProject ttrnProject= (TtrnProject) commonBaseDao.findById(Long.parseLong(meetingBean.getProductId()),
				HibernateBeansConstants.HBM_PROJECT, hibernateSession);
		
		Connection conn=hibernateSession.connection();
		LocationDto dto= getLocationById(conn,meetingBean.getLocationId());
		conn.close();
		String lName="";
		if(dto!=null)
		{
			lName=dto.getLocationName();
		}
		
		messageParameters[4] = ttrnProject.getProjectName();//product
		//=meetingBean.getProductId();
		messageParameters[5] = lName;//location
		messageParameters[6] = meetingBean.getProductId();

		oSendMail.setOToList(toList);
		oSendMail.setOCcList(ccList);
		oSendMail.setStrSubject(SendMailUtility.getMessage(messageParameters,
				AppConstants.PROP_FILE, AppConstants.SUBJECT_MEETINGSCHEDULE));
		oSendMail.setStrMessage(SendMailUtility.getMessage(messageParameters,
				AppConstants.PROP_FILE, AppConstants.MSG_MEETINGSCHEDULE));
		//oSendMail.sendMessageWithAttachment(meetingBean.getAttachment());
		
		//for ICS FILE START
		String txt=SendMailUtility.getMessage(messageParameters,AppConstants.PROP_FILE, AppConstants.SUBJECT_MEETINGSCHEDULE);
		String txt1=SendMailUtility.getMessage(messageParameters,AppConstants.PROP_FILE, AppConstants.MSG_MEETINGSCHEDULE1);
		String smstxt=SendMailUtility.getMessage(messageParameters,AppConstants.PROP_FILE, AppConstants.MSG_MEETINGSCHEDULE_SMS);
		SimpleDateFormat icsDateFormat=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat outfromtime=new SimpleDateFormat(ICS_TIME_FORMAT);
		SimpleDateFormat infromtime=new SimpleDateFormat("h:mm a");
		
		java.util.Date createdDate=meetingBean.getCreateDate();
		
		String createID=null;
		if (meetingBean.getCreatedID() != null) 
		{
			tmEmployee = (TmEmployee) commonBaseDao.findById(Long.parseLong(meetingBean.getCreatedID()),
						HibernateBeansConstants.HBM_EMPLOYEE, hibernateSession);
			createID=tmEmployee.getEmail();
		}
		SimpleDateFormat icsSendDateFormat=new SimpleDateFormat("yyyyMMdd");
		//for ICS FILE END
		
		File f=null;
		try
		{
			f=new File(baseDirPath+"meeting"+"~"+System.currentTimeMillis()+"~"+meetingBean.getCreatedID()+".ics");
			BufferedWriter out = new BufferedWriter(new FileWriter(f)); 
			out.write("BEGIN:VCALENDAR\n");
			out.write("METHOD:REQUEST\n");
			out.write("BEGIN:VTIMEZONE\n");
			out.write("TZID:India\n");
			out.write("BEGIN:STANDARD\n");
			out.write("DTSTART:19500101T020000\n");
			out.write("TZOFFSETFROM:+0530\n");
			out.write("TZOFFSETTO:+0530\n");
			out.write("END:STANDARD\n");
			out.write("END:VTIMEZONE\n");
			out.write("BEGIN:VEVENT\n");
			out.write("SEQUENCE:0\n");
			out.write("ORGANIZER:Mailto:"+createID+"\n");
			for(int i=0;i<toList.size();i++)
			{
				out.write("ATTENDEE;RSVP=TRUE;TYPE=INDIVIDUAL:Mailto:"+toList.get(i)+"\n");
			}
			for(int i=0;i<ccList.size();i++)
			{
				out.write("ATTENDEE;RSVP=FALSE;ROLE=OPT-PARTICIPANT:Mailto:"+ccList.get(i)+"\n");
			}
			out.write("DESCRIPTION:"+txt1+"\n");
			out.write("SUMMARY:"+txt+"\n");
			out.write("LOCATION:"+lName+"\n");
			out.write("DTSTART;"+"TZID=india:"+icsDateFormat.format(inFormat.parse(meetingBean.getDate()))+"T"+outfromtime.format(infromtime.parse(meetingBean.getStartTime()))+"Z"+"\n");
			out.write("DTEND;"+"TZID=india:"+icsDateFormat.format(inFormat.parse(meetingBean.getDate()))+"T"+outfromtime.format(infromtime.parse(meetingBean.getEndTime()))+"Z"+"\n");
			out.write("DTSTAMP:"+icsSendDateFormat.format(createdDate)+"T"+outfromtime.format(createdDate)+"\n");
			out.write("STATUS:CONFIRMED\n");
			out.write("END:VEVENT\n");
			out.write("END:VCALENDAR\n");
			out.close();
		}
		catch (IOException  e)
		{//Catch exception if any
	      System.err.println("Error: " + e.getMessage());
	    }
		
		
		FormFile [] fileAttachment=null;
		
		FormFile file1=null;
		FormFile file2=null;
		FormFile file3=null;
		//FormFile file4=null;//for ICS File for Meeting Scheduless
		
		int count=0;
		if(meetingBean.getAttachment()!=null && !"".equals(meetingBean.getAttachment()))
		{
			file1=meetingBean.getAttachment();
			count++;
		}
		if(meetingBean.getAttachment()!=null && !"".equals(meetingBean.getAttachment()))
		{
			file2=meetingBean.getAttachment1();
				count++;
		}
		if(meetingBean.getAttachment()!=null && !"".equals(meetingBean.getAttachment()))
		{
			file3=meetingBean.getAttachment2();
			count++;
		}
		/*if(meetingBean.getAttachment4()!=null && !"".equals(meetingBean.getAttachment4()))//ics file
		{
			file4=meetingBean.getAttachment4();
			count++;
		}*/
		
		fileAttachment=new FormFile[count];
		int index=0;
		if(file1!=null)
		{
			fileAttachment[index]=file1;
			index++;
		}
		if(file2!=null)
		{
			fileAttachment[index]=file2;
			index++;
		}
		if(file3!=null)
		{
			fileAttachment[index]=file3;
			index++;
		}
		/*if(file4!=null)
		{
			fileAttachment[index]=file4;
			index++;
		}*/
		File files[]=new File[1];
		files[0]=f;
		oSendMail.sendMessageWithFormFile(fileAttachment,files);
		
		
		try {
			SMSUtil sms=new SMSUtil();
			sms.sendSMS(phoneNos, smstxt);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	// This method saves the Meeting MOM for a Scheduled Meeting into
	// database

	public boolean saveMeetingMOM(MeetingBean meetingBean,
			Session hibernateSession,TmEmployee tmEmployee) throws Exception {

		boolean insert = true;
		TtrnMeetingschedules ttrnMeetingschedules = new TtrnMeetingschedules();
		TtrnMeetingmoms ttrnMeetingmoms = new TtrnMeetingmoms();
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);

		try {
			if (meetingBean.getMeetingIdCreated() != null
					&& !meetingBean.getMeetingIdCreated().equalsIgnoreCase(""))
				ttrnMeetingschedules = (TtrnMeetingschedules) commonBaseDao
						.findById(new Long(meetingBean.getMeetingIdCreated())
								.longValue(),
								HibernateBeansConstants.HBM_MEETINGSCHEDULE,
								hibernateSession);

			if (meetingBean.getAttachment() != null) {
				byte[] FileData = meetingBean.getAttachment().getFileData();
				Blob b = Hibernate.createBlob(FileData);
				ttrnMeetingmoms.setMom(b);
				ttrnMeetingmoms.setFilename(meetingBean.getAttachment()
						.getFileName());
			}
			if(tmEmployee!=null)
			ttrnMeetingmoms.setCreatedby(tmEmployee);
			ttrnMeetingmoms.setCreateddate(new Date());
			ttrnMeetingmoms.setTmMeetings(ttrnMeetingschedules);
			commonBaseDao.attachDirty(ttrnMeetingmoms, hibernateSession);

		} catch (Exception e) {
			insert = false;
			hibernateSession.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			commonBaseDao.closeTrans(hibernateSession);
		}

		return insert;

	}

	// This method sends mail to attendies as a meeting invite.

	public void sendMailForMOM(MeetingBean meetingBean, Session hibernateSession)
			throws Exception {

		SendMailUtility oSendMail = new SendMailUtility(); //Disha's Code
		ArrayList toList = new ArrayList();
		ArrayList ccList = new ArrayList();
		Object[] messageParameters = new Object[4];
		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		TmEmployee tmEmployee = new TmEmployee();
		TtrnMeetingschedules ttrnMeetingschedules = new TtrnMeetingschedules();
		TtrnMeetingattendies meetingattendies = new TtrnMeetingattendies();
		ArrayList attendiesList = new ArrayList();

		Criteria ce = hibernateSession
				.createCriteria(TtrnMeetingattendies.class);
		ttrnMeetingschedules = (TtrnMeetingschedules) commonBaseDao.findById(
				new Long(meetingBean.getMeetingIdCreated()).longValue(),
				HibernateBeansConstants.HBM_MEETINGSCHEDULE, hibernateSession);
		ce = ce.add(Restrictions.eq("ttrnMeetingschedules",
				ttrnMeetingschedules));

		if (ce.list() != null) {
			attendiesList = (ArrayList) ce.list();
		}

		if (attendiesList.size() > 0) {
			for (int x = 0; x < attendiesList.size(); x++) {
				meetingattendies = (TtrnMeetingattendies) attendiesList.get(x);
				tmEmployee = (TmEmployee) commonBaseDao.findById(
						meetingattendies.getNpdempid(),
						HibernateBeansConstants.HBM_EMPLOYEE, hibernateSession);
				if (meetingattendies.getAttentype().equalsIgnoreCase(
						AppConstants.MANDATORY_LIST_TYPE)) {
					toList.add(tmEmployee.getEmail());
				} else {
					ccList.add(tmEmployee.getEmail());
				}

			}
		}

		messageParameters[0] = meetingBean.getMeetingIdCreated();
		//SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy");
		messageParameters[1] = ttrnMeetingschedules.getMeetingdate();
		messageParameters[2] = ttrnMeetingschedules.getTmMeetings()
				.getMeetingtype();

		oSendMail.setOToList(toList);
		oSendMail.setOCcList(ccList);
		oSendMail.setStrSubject(SendMailUtility.getMessage(messageParameters,
				AppConstants.PROP_FILE, AppConstants.SUBJECT_MEETINGMOM));
		oSendMail.setStrMessage(SendMailUtility.getMessage(messageParameters,
				AppConstants.PROP_FILE, AppConstants.MSG_MEETINGMOM));
		//oSendMail.sendMessageWithAttachment(meetingBean.getAttachment());
		oSendMail.sendMessageWithFormFile(meetingBean.getAttachment());

	}

	// This method get MOM List for a meeting from database

	public ArrayList getMomListForMeeting(MeetingBean meetingBean,
			Session hibernateSession) throws Exception {

		CommonBaseDao commonBaseDao = BaseDaoFactory
				.getInstance(DaoConstants.BASE_DAO_CLASS);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		ArrayList momList = null;
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		if (!meetingBean.getFromDate().equalsIgnoreCase("")) {

			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.setTime(sdf.parse(meetingBean.getFromDate()));
		}
		if (!meetingBean.getToDate().equalsIgnoreCase("")) {

			calendar1.set(Calendar.HOUR_OF_DAY, 23);
			calendar1.set(Calendar.MINUTE, 59);
			calendar1.set(Calendar.SECOND, 59);
			calendar1.setTime(sdf.parse(meetingBean.getToDate()));
			calendar1.add(Calendar.DATE, 1);
		}

		// Applying criteria as per the filter element at jsp for searching the
		// request.
		PagingSorting pagingSorting = meetingBean.getPagingSorting();
		if (pagingSorting == null) {
			pagingSorting = new PagingSorting();
			meetingBean.setPagingSorting(pagingSorting);
		}
		pagingSorting.setPagingSorting(true, true);

		pagingSorting.setDefaultifNotPresent("tmMeetings",
				PagingSorting.increment, 1);

		pagingSorting.setMode("hibernate");
		
		Criteria ce = hibernateSession.createCriteria(TtrnMeetingmoms.class);
		ce.createAlias("tmMeetings", "meeting");

		if (meetingBean.getMeetingId() != null
				&& !meetingBean.getMeetingId().equalsIgnoreCase(
						AppConstants.INI_VALUE)) {
			ce.add(Restrictions.eq("meeting.meetingid", new Long(meetingBean
					.getMeetingId())));
		}
		if (meetingBean.getSubject() != null
				&& !meetingBean.getSubject().equals(AppConstants.INI_VALUE)) {
			ce.add(Restrictions.ilike("meeting.subject", meetingBean.getSubject(),MatchMode.ANYWHERE));
		}
		if (meetingBean.getToDate() != null
				&& meetingBean.getFromDate() != null
				&& !(meetingBean.getToDate().equalsIgnoreCase("") && meetingBean
						.getFromDate().equalsIgnoreCase(""))) {

			ce.add(Restrictions.between("createddate", new Date(calendar
					.getTime().getTime()), new Date(calendar1.getTime()
					.getTime())));

		}
		ce.addOrder(Order.asc("meeting.meetingid"));

		momList = (ArrayList) ce.list();
		
		if(momList!=null&&momList.size()>0)
		{
			long meetingId=0;
			int counter =0;
			
				for(int i=0;i<momList.size();i++)
				{
					TtrnMeetingmoms meetingmoms = (TtrnMeetingmoms)momList.get(i);
					if(meetingmoms.getMeetingmomid()!=meetingId)
					{
						counter++;
						meetingId = meetingmoms.getMeetingmomid();
					}
					
				}
			pagingSorting.setRecordCount(counter);	
		}
		ce.setFirstResult(pagingSorting.getStartRecordId());
		ce.setMaxResults(pagingSorting.getPageRecords());
		momList = (ArrayList) ce.list();
		return momList;

	}

	public ArrayList<LocationDto> getLocationList(Connection conn) throws NpdException{
		
		ArrayList<LocationDto> list = new ArrayList<LocationDto>();
		
		try{
			
			String sql=" SELECT LOCATIONID, LOCATIONNAME, ISACTIVE " +
					" FROM NPD.TM_LOCATION_MSTR WHERE ISACTIVE=1 ORDER BY LOCATIONNAME";
	
			
			
			
			
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				LocationDto dto=new LocationDto();
				
				dto.setLocationId(rs.getLong("LOCATIONID"));
				dto.setLocationName(rs.getString("LOCATIONNAME"));
								
				list.add(dto);
			}
			  
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getLocationList method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getLocationList method of "
					+ this.getClass().getSimpleName()) ;
		}
		return list;
	}
public LocationDto getLocationById(Connection conn,String id) throws NpdException{
		
		LocationDto dto = null;
		
		try{
			
			String sql=" SELECT LOCATIONID, LOCATIONNAME, ISACTIVE " +
					" FROM NPD.TM_LOCATION_MSTR WHERE LOCATIONID="+id;
	
			
			
			
			
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
		
			if(rs.next())
			{
			dto=new LocationDto();
			
			dto.setLocationId(rs.getLong("LOCATIONID"));
			dto.setLocationName(rs.getString("LOCATIONNAME"));
			
			}
			
								
			 
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getLocationById method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getLocationById method of "
					+ this.getClass().getSimpleName()) ;
		}
		return dto;
	}

public ArrayList<TtrnProject> fetchProjects(Connection conn)throws NpdException {
	
	ArrayList<TtrnProject> projectList=new ArrayList<TtrnProject>();
	try{
		
		
		String sql="SELECT PROJECTID,PROJECT_NAME,PROJECTSTATUS FROM NPD.TTRN_PROJECT" +
		" WHERE PROJECTSTATUS IN(1,0)";
		ResultSet rs=conn.createStatement().executeQuery(sql);
		while(rs.next())
		{
			TtrnProject dto=new TtrnProject();
			dto.setProjectid(rs.getLong("PROJECTID"));
			dto.setProjectName(rs.getString("PROJECT_NAME"));
			dto.setCSV_id_name(dto.getProjectid()+" ("+dto.getProjectName()+")");
			
			projectList.add(dto);
		}
		  
		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		AppConstants.NPDLOGGER.error(ex.getMessage()
				+ " Exception occured in fetchProjects method of "
				+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
		throw new NpdException("Exception occured in fetchProjects method of "
				+ this.getClass().getSimpleName()) ;
	}
	return projectList;
}

	public ArrayList getMeetingsOfProduct(Connection conn, String productId)throws NpdException {
		ArrayList<TtrnMeetingschedules> meetingList = new ArrayList<TtrnMeetingschedules>();
		ResultSet rs=null;
		try {
			
			String sql="SELECT MEETINGID, MEETINGTYPEID, SUBJECT, MEETINGDATE, STARTTIME, ENDTIME, ATTACHMENT, " +
					"ISACTIVE, CRETAEDBY, CRETAEDDATE, MODIFIEDBY, MODIFIEDDATE, ATTACHMENT1, ATTACHMENT2, " +
					"FILENAME, FILENAME1, FILENAME2, PRODUCTID, LOCATIONID  " +
					"FROM NPD.TTRN_MEETINGSCHEDULES WHERE PRODUCTID="+productId+"  ORDER BY SUBJECT";
			rs=conn.createStatement().executeQuery(sql);
			while(rs.next())
			{
				TtrnMeetingschedules dto=new TtrnMeetingschedules();
				
				dto.setMeetingid(rs.getLong("MEETINGID"));
				dto.setSubject(rs.getString("SUBJECT"));
				
				//dto.setLblSubject_Short(rs.getString("SUBJECT"));
				
				meetingList.add(dto);
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getMeetingsOfProduct method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getMeetingsOfProduct method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return meetingList;
	}

	public TtrnMeetingschedules getMeetingDetailById(Connection conn, String meetId) throws NpdException {
		TtrnMeetingschedules ttrnMeetingschedules = null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		try {
			
			 cstmt= conn.prepareCall(
						"{call NPD.NPD_MEETINGBYID(?)}");
			cstmt.setLong(1, Long.parseLong(meetId));
			
			rs=cstmt.executeQuery();
			
			
			
			if(rs.next())
			{
				ttrnMeetingschedules=new TtrnMeetingschedules();
				
				ttrnMeetingschedules.setMeetingid(rs.getLong("MEETINGID"));
				ttrnMeetingschedules.setSubject(rs.getString("SUBJECT"));
				
				ttrnMeetingschedules.setTmMeetings(new TmMeetings());
				ttrnMeetingschedules.getTmMeetings().setMeetingtypeid(rs.getInt("MEETINGTYPEID"));
				
				ttrnMeetingschedules.setLocation(new LocationDto());
				ttrnMeetingschedules.getLocation().setLocationName(rs.getString("LOCATIONNAME"));
				
				ttrnMeetingschedules.setLocationid(rs.getLong("LOCATIONID"));
				ttrnMeetingschedules.setMeetingdate(rs.getDate("MEETINGDATE"));
				//
				ttrnMeetingschedules.setMeetingdateString(Utility.showDate_Report2(rs.getDate("MEETINGDATE")));
				
				//ttrnMeetingschedules.setStarttime(rs.getTime("STARTTIME"));  
				//remove this comment ie setting starttime and endtime starts giving error in send sms for meeting 
				//because of use of ajax
				//ttrnMeetingschedules.setEndtime(rs.getTime("ENDTIME"));
				
				ttrnMeetingschedules.setStarttimeString(rs.getString("STARTTIME"));
				ttrnMeetingschedules.setEndtimeString(rs.getString("ENDTIME"));
				ttrnMeetingschedules.setTmMeetings(new TmMeetings());
				ttrnMeetingschedules.getTmMeetings().setMeetingtype(rs.getString("MEETINGTYPE"));
				
				CallableStatement empcstmt = conn.prepareCall(
								"{call NPD.NPD_EMPLOYEE_OF_MEETING(?)}");
				empcstmt.setLong(1, Long.parseLong(meetId));
				
				ResultSet empRs=empcstmt.executeQuery();
				
				
				ArrayList<TtrnMeetingattendies> attendies=new ArrayList<TtrnMeetingattendies>();
				
				TtrnMeetingattendies attendee=null;
				while(empRs.next())
				{
					attendee=new TtrnMeetingattendies();
					TmEmployee employee=new TmEmployee();
					
					employee.setEmpid(empRs.getLong("NPDEMPID"));
					employee.setEmpname(empRs.getString("EMPNAME"));
					employee.setMobileNo(empRs.getString("MOBILE_NO"));
					
					attendee.setEmployee(employee);
					attendies.add(attendee);
				}
				ttrnMeetingschedules.setAttendies(attendies);
				
			}
			
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			AppConstants.NPDLOGGER.error(ex.getMessage()
					+ " Exception occured in getMeetingDetailById method of "
					+ this.getClass().getSimpleName()+AppUtility.getStackTrace(ex));
			throw new NpdException("Exception occured in getMeetingDetailById method of "
					+ this.getClass().getSimpleName()) ;
		}
		finally
		{
			try
			{
			DbConnection.closeResultset(rs);
			DbConnection.closeCallableStatement(cstmt);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return ttrnMeetingschedules;
	}
}
