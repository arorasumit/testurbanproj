/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibm.ioes.npd.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;
import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.PlanExcelUploadDto;
import com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto;
import com.ibm.ioes.npd.hibernate.beans.TmEmployee;
import com.ibm.ioes.npd.hibernate.beans.TmReferencedocs;
import com.ibm.ioes.npd.hibernate.beans.TmRoles;
import com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow;
import com.ibm.ioes.npd.hibernate.beans.TtrnProject;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues;
import com.ibm.ioes.npd.hibernate.beans.TtrnProjectrisks;
import com.ibm.ioes.npd.hibernate.dao.AttachEditProjectPlanDao;
import com.ibm.ioes.npd.hibernate.dao.CommonBaseDao;

/**
 *
 * 
 */
public class ExcelCreator {

    public HSSFWorkbook createWorkbook(ArrayList issueList , ArrayList assumptionList, ArrayList riskList, ArrayList projectPlanList , ArrayList rfilist) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet projectplansheet = wb.createSheet("Project Plan");
        HSSFSheet issuesheet = wb.createSheet("Project Issues");
        HSSFSheet assumptionsheet = wb.createSheet("Project Assumptions");
        HSSFSheet riskSheet = wb.createSheet("Project Risks");
        HSSFSheet rfiSheet = wb.createSheet("RFI Details");
        
        

        /**
         * Setting the width of the columns For Project Plan
         */

        projectplansheet.setColumnWidth(0, 3500);
        projectplansheet.setColumnWidth(0, 3500);
        projectplansheet.setColumnWidth(1, 3500);
        projectplansheet.setColumnWidth(2, 3500);
        projectplansheet.setColumnWidth(3, 3500);
        projectplansheet.setColumnWidth(4, 3500);
        projectplansheet.setColumnWidth(5, 3500);
        projectplansheet.setColumnWidth(6, 3500);
        projectplansheet.setColumnWidth(7, 3500);
        
        /**
         * Setting the width of the first three columns.
         */
        
        issuesheet.setColumnWidth(0, 3500);
        issuesheet.setColumnWidth(1, 3500);
        issuesheet.setColumnWidth(2, 3500);
        issuesheet.setColumnWidth(3, 3500);


        //Assumption list
        assumptionsheet.setColumnWidth(0, 3500);
        assumptionsheet.setColumnWidth(1, 3500);
        assumptionsheet.setColumnWidth(2, 3500);
        assumptionsheet.setColumnWidth(3, 3500);
        //Assumption list
        
        riskSheet.setColumnWidth(0, 3500);
        riskSheet.setColumnWidth(1, 3500);
        riskSheet.setColumnWidth(2, 3500);
        riskSheet.setColumnWidth(3, 3500);
        
        
        
        /**
         * Style for the header cells.
         */
        HSSFCellStyle headerCellStyle = wb.createCellStyle();
        HSSFFont boldFont = wb.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerCellStyle.setFont(boldFont);
             
        
        //Setting Columns For Risk With Header And Values For Project Plan       
        HSSFRow projectplanrow = projectplansheet.createRow(0);
        
        HSSFCell projectplancell = projectplanrow.createCell(0);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Project Id"));

        projectplancell = projectplanrow.createCell(1);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Project Name"));

        
        projectplancell = projectplanrow.createCell(2);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Stage"));
        
        projectplancell = projectplanrow.createCell(3);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Task"));

        projectplancell = projectplanrow.createCell(4);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Planned Start Date"));

        projectplancell = projectplanrow.createCell(5);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Planned End Date"));


        projectplancell = projectplanrow.createCell(6);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Actual Start Date"));


        projectplancell = projectplanrow.createCell(7);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Actual End Date"));

        projectplancell = projectplanrow.createCell(8);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("StakeHolder Role"));

        projectplancell = projectplanrow.createCell(9);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("StakeHolder Name"));

        for (int index = 0; index < projectPlanList.size(); index++) {
        	projectplanrow = projectplansheet.createRow(index+1);
        	

            ProjectPlanReportDto userData = (ProjectPlanReportDto) projectPlanList.get(index);
            
            projectplancell = projectplanrow.createCell(0);
            HSSFRichTextString projectId = new HSSFRichTextString(String.valueOf(userData.getProject().getProjectid()));
            projectplancell.setCellValue(projectId);

            projectplancell = projectplanrow.createCell(1);
            HSSFRichTextString projectname = new HSSFRichTextString(userData.getProject().getProjectName());
            projectplancell.setCellValue(projectname);

            
            projectplancell = projectplanrow.createCell(2);
            HSSFRichTextString stageName = new HSSFRichTextString(userData.getStagename());
            projectplancell.setCellValue(stageName);

            projectplancell = projectplanrow.createCell(3);
            HSSFRichTextString taskName = new HSSFRichTextString(userData.getTaskname());
            projectplancell.setCellValue(taskName);

            projectplancell = projectplanrow.createCell(4);
            HSSFRichTextString plannedStartDate = new HSSFRichTextString(String.valueOf(userData.getPlannedstartdate()));
            projectplancell.setCellValue(plannedStartDate);

            if(userData.getPlannedenddate()!=null)
            {
	        	projectplancell = projectplanrow.createCell(5);
	            projectplancell.setCellValue(new HSSFRichTextString(String.valueOf(userData.getPlannedenddate())));
            }

            if(userData.getActualstartdate()!=null)
            {
	        	projectplancell = projectplanrow.createCell(6);
	            projectplancell.setCellValue(new HSSFRichTextString(String.valueOf(userData.getActualstartdate())));
            }
            
            if(userData.getActualenddate()!=null)
            {
	        	projectplancell = projectplanrow.createCell(7);
	            projectplancell.setCellValue(new HSSFRichTextString(String.valueOf(userData.getActualenddate())));
            }
            
            if(userData.getStakeholderrole()!=null)
            {
	            projectplancell = projectplanrow.createCell(8);
	            projectplancell.setCellValue(new HSSFRichTextString(userData.getStakeholderrole()));
            }

        	projectplancell = projectplanrow.createCell(9);
            projectplancell.setCellValue(new HSSFRichTextString(userData.getStakeholdername()));

        }

        
        
        	//	Creating Header and Filling Values For Issues
        HSSFRow issuerow = issuesheet.createRow(0);

        HSSFCell issuecell = issuerow.createCell(0);
        issuecell.setCellStyle(headerCellStyle);
        issuecell.setCellValue(new HSSFRichTextString("Project Id"));

        issuecell = issuerow.createCell(1);
        issuecell.setCellStyle(headerCellStyle);
        issuecell.setCellValue(new HSSFRichTextString("Project Name"));

        issuecell = issuerow.createCell(2);
        issuecell.setCellStyle(headerCellStyle);
        issuecell.setCellValue(new HSSFRichTextString("Project Issue"));

        issuecell = issuerow.createCell(3);
        issuecell.setCellStyle(headerCellStyle);
        issuecell.setCellValue(new HSSFRichTextString("Priority"));

        for (int index = 0; index < issueList.size(); index++) {
            issuerow = issuesheet.createRow(index+1);
            TtrnProjectisssues userData = (TtrnProjectisssues) issueList.get(index);
            
            issuecell = issuerow.createCell(0);
            HSSFRichTextString projectid = new HSSFRichTextString(String.valueOf(userData.getTtrnProject().getProjectid()));
            issuecell.setCellValue(projectid);

            issuecell = issuerow.createCell(1);
            HSSFRichTextString projectname = new HSSFRichTextString(userData.getTtrnProject().getProjectName());
            issuecell.setCellValue(projectname);

            issuecell = issuerow.createCell(2);
            HSSFRichTextString issueDesc = new HSSFRichTextString(userData.getIssuedesc());
            issuecell.setCellValue(issueDesc);
            
            issuecell = issuerow.createCell(3);
            HSSFRichTextString issuePriority = new HSSFRichTextString(userData.getPriority());
            issuecell.setCellValue(issuePriority);
              
        }

        //Setting Assumption Column And Thier Header & Values
        HSSFRow assumptionrow = assumptionsheet.createRow(0);
        HSSFCell assumptioncell = assumptionrow.createCell(0);
        assumptioncell.setCellStyle(headerCellStyle);
        assumptioncell.setCellValue(new HSSFRichTextString("Project Id"));
 
        assumptioncell = assumptionrow.createCell(1);
        assumptioncell.setCellStyle(headerCellStyle);
        assumptioncell.setCellValue(new HSSFRichTextString("Project Name"));
        
        assumptioncell = assumptionrow.createCell(2);
        assumptioncell.setCellStyle(headerCellStyle);
        assumptioncell.setCellValue(new HSSFRichTextString("Description"));

        assumptioncell = assumptionrow.createCell(3);
        assumptioncell.setCellStyle(headerCellStyle);
        assumptioncell.setCellValue(new HSSFRichTextString("Impact"));

        assumptioncell = assumptionrow.createCell(4);
        assumptioncell.setCellStyle(headerCellStyle);
        assumptioncell.setCellValue(new HSSFRichTextString("Raised Date"));

        String impact = null;
        for (int index = 0; index < assumptionList.size(); index++) {
        	assumptionrow = assumptionsheet.createRow(index+1);
            TtrnProjectassumptions userData = (TtrnProjectassumptions)assumptionList.get(index);

            assumptioncell = assumptionrow.createCell(0);
            HSSFRichTextString assumptionProjectId = new HSSFRichTextString(String.valueOf(userData.getTtrnProject().getProjectid()));
            assumptioncell.setCellValue(assumptionProjectId);

            assumptioncell = assumptionrow.createCell(1);
            HSSFRichTextString assumptionProjectname = new HSSFRichTextString(userData.getTtrnProject().getProjectName());
            assumptioncell.setCellValue(assumptionProjectname);

            assumptioncell = assumptionrow.createCell(2);
            HSSFRichTextString assumptionDesc = new HSSFRichTextString(userData.getDescription());
            assumptioncell.setCellValue(assumptionDesc);

            if(userData.getImpact().equalsIgnoreCase("0"))
            {
            	impact = "High";
            }
            if(userData.getImpact().equalsIgnoreCase("1"))
                {
                	impact = "Medium";
                }
            if(userData.getImpact().equalsIgnoreCase("2"))
            {
            	impact = "Low";
            }
            	
            assumptioncell = assumptionrow.createCell(3);
            HSSFRichTextString assumptionImpact = new HSSFRichTextString(impact);
            assumptioncell.setCellValue(assumptionImpact);

            assumptioncell = assumptionrow.createCell(4);
            HSSFRichTextString assumptionRaisedDate = new HSSFRichTextString( userData.getRaiseddate().toString());
            assumptioncell.setCellValue(assumptionRaisedDate);

        }


 //Setting Columns For Risk With Header And Values       
        HSSFRow riskrow = riskSheet.createRow(0);
        
        HSSFCell riskcell = riskrow.createCell(0);
        riskcell.setCellStyle(headerCellStyle);
        riskcell.setCellValue(new HSSFRichTextString("Project Id"));

        riskcell = riskrow.createCell(1);
        riskcell.setCellStyle(headerCellStyle);
        riskcell.setCellValue(new HSSFRichTextString("Project Name"));

        
        riskcell = riskrow.createCell(2);
        riskcell.setCellStyle(headerCellStyle);
        riskcell.setCellValue(new HSSFRichTextString("Desc"));
        
        riskcell = riskrow.createCell(3);
        riskcell.setCellStyle(headerCellStyle);
        riskcell.setCellValue(new HSSFRichTextString("Status"));

        riskcell = riskrow.createCell(4);
        riskcell.setCellStyle(headerCellStyle);
        riskcell.setCellValue(new HSSFRichTextString("Source"));

        riskcell = riskrow.createCell(5);
        riskcell.setCellStyle(headerCellStyle);
        riskcell.setCellValue(new HSSFRichTextString("Priority"));

        for (int index = 0; index < riskList.size(); index++) {
        	riskrow = riskSheet.createRow(index+1);
            TtrnProjectrisks userData = (TtrnProjectrisks) riskList.get(index);

            riskcell = riskrow.createCell(0);
            HSSFRichTextString projectid = new HSSFRichTextString(String.valueOf(userData.getTtrnProject().getProjectid()));
            riskcell.setCellValue(projectid);

            riskcell = riskrow.createCell(1);
            HSSFRichTextString projectname = new HSSFRichTextString(userData.getTtrnProject().getProjectName());
            riskcell.setCellValue(projectname);

            
             riskcell = riskrow.createCell(2);
            HSSFRichTextString riskDesc = new HSSFRichTextString(userData.getDescription());
            riskcell.setCellValue(riskDesc);

            riskcell = riskrow.createCell(3);
            String status = null; 
            if(userData.getStatus()==1)
            	status = "Open";
            else
            	status = "Close";
            
            HSSFRichTextString riskStatus = new HSSFRichTextString(status);
            riskcell.setCellValue(riskStatus);

        	riskcell = riskrow.createCell(4);
            HSSFRichTextString riskSource = new HSSFRichTextString(userData.getRisksource());
            riskcell.setCellValue(riskSource);

        	riskcell = riskrow.createCell(5);
            HSSFRichTextString riskPriority = new HSSFRichTextString(userData.getPriority());
            riskcell.setCellValue(riskPriority);
            
        }


        
        //Setting Columns For Risk With Header And Values       
        HSSFRow rfiRow = rfiSheet.createRow(0);
        
        HSSFCell rfiCell = rfiRow.createCell(0);
        rfiCell.setCellStyle(headerCellStyle);
        rfiCell.setCellValue(new HSSFRichTextString("Project Id"));

        rfiCell = rfiRow.createCell(1);
        rfiCell.setCellStyle(headerCellStyle);
        rfiCell.setCellValue(new HSSFRichTextString("Project Name"));
        
        rfiCell = rfiRow.createCell(2);
        rfiCell.setCellStyle(headerCellStyle);
        rfiCell.setCellValue(new HSSFRichTextString("Task Name"));
        
        rfiCell = rfiRow.createCell(3);
        rfiCell.setCellStyle(headerCellStyle);
        rfiCell.setCellValue(new HSSFRichTextString("RFI Raised Against"));

        rfiCell = rfiRow.createCell(4);
        rfiCell.setCellStyle(headerCellStyle);
        rfiCell.setCellValue(new HSSFRichTextString("Role"));

        rfiCell = rfiRow.createCell(5);
        rfiCell.setCellStyle(headerCellStyle);
        rfiCell.setCellValue(new HSSFRichTextString("RFI Action "));
        

        rfiCell = rfiRow.createCell(6);
        rfiCell.setCellStyle(headerCellStyle);
        rfiCell.setCellValue(new HSSFRichTextString("Remarks"));

        rfiCell = rfiRow.createCell(7);
        rfiCell.setCellStyle(headerCellStyle);
        rfiCell.setCellValue(new HSSFRichTextString("RFI Action Date"));

        for (int index = 0; index < rfilist.size(); index++) {
        	rfiRow = rfiSheet.createRow(index+1);
            ProjectPlanReportDto userData = (ProjectPlanReportDto) rfilist.get(index);

            rfiCell = rfiRow.createCell(0);
            HSSFRichTextString projectid = new HSSFRichTextString(String.valueOf(userData.getProject().getProjectid()));
            rfiCell.setCellValue(projectid);

            rfiCell = rfiRow.createCell(1);
            HSSFRichTextString projectname = new HSSFRichTextString(userData.getProject().getProjectName());
            rfiCell.setCellValue(projectname);

            rfiCell = rfiRow.createCell(2);
            HSSFRichTextString taskname = new HSSFRichTextString(userData.getRfitaskname());
            rfiCell.setCellValue(taskname);

            rfiCell = rfiRow.createCell(3);
            HSSFRichTextString rfitaskname = new HSSFRichTextString(userData.getAssignedtoname());
            rfiCell.setCellValue(rfitaskname);

            rfiCell = rfiRow.createCell(4);
            HSSFRichTextString role = new HSSFRichTextString(userData.getAssignedtorolename());
            rfiCell.setCellValue(role);

            rfiCell = rfiRow.createCell(5);
            HSSFRichTextString rfiaction = new HSSFRichTextString(userData.getActiontaken());
            rfiCell.setCellValue(rfiaction);

            rfiCell = rfiRow.createCell(6);
            HSSFRichTextString remarks = new HSSFRichTextString(userData.getActionremarks());
            rfiCell.setCellValue(remarks);

            rfiCell = rfiRow.createCell(7);
            HSSFRichTextString actiondate = new HSSFRichTextString(userData.getActiondate().toString());
            rfiCell.setCellValue(actiondate);

        }

        
        
        
        return wb;
    }
    public HSSFWorkbook createExcelForProjectEdit(ProjectPlanInstanceBean formBean, Session hibernateSession,Connection conn) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet projectplansheet = wb.createSheet("Attach Project");
        
        HSSFCellStyle headerCellStyle = wb.createCellStyle();
        HSSFFont boldFont = wb.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerCellStyle.setFont(boldFont);
        
        TtrnProject ttrnProject = new TtrnProject();
	CommonBaseDao commonBaseDao=new CommonBaseDao();
	ttrnProject = (TtrnProject) commonBaseDao.findById((Long.parseLong(formBean.getProjectId())),
	HibernateBeansConstants.HBM_PROJECT, hibernateSession);
        

        HSSFRow projectplanrow = projectplansheet.createRow(0);
        
        HSSFCell projectplancell = projectplanrow.createCell(0);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Project Id"));

        projectplancell = projectplanrow.createCell(1);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Project Name"));

        projectplanrow = projectplansheet.createRow(1);
        
        projectplancell = projectplanrow.createCell(0);
        HSSFRichTextString projectid = new HSSFRichTextString(formBean.getProjectId());
        projectplancell.setCellValue(projectid);
        
        projectplancell = projectplanrow.createCell(1);
        HSSFRichTextString projectname = new HSSFRichTextString(ttrnProject.getProjectName());
        projectplancell.setCellValue(projectname);
        
        
        projectplanrow = projectplansheet.createRow(2);
        
        projectplancell = projectplanrow.createCell(0);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Stage Name"));
        

        projectplancell = projectplanrow.createCell(1);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Task Name"));


        projectplancell = projectplanrow.createCell(2);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Task Desc"));

        projectplancell = projectplanrow.createCell(3);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Mandatory"));

        projectplancell = projectplanrow.createCell(4);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Assigned To"));

        projectplancell = projectplanrow.createCell(5);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Role Name"));

        projectplancell = projectplanrow.createCell(6);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Is First Task"));

        projectplancell = projectplanrow.createCell(7);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Is Last Task"));

        projectplancell = projectplanrow.createCell(8);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Next Tasks"));

        projectplancell = projectplanrow.createCell(9);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Planned Duration"));

        projectplancell = projectplanrow.createCell(10);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Document To Upload"));

        projectplancell = projectplanrow.createCell(11);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Remarks"));

        projectplancell = projectplanrow.createCell(12);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Target Start"));

        projectplancell = projectplanrow.createCell(13);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Target End"));

        projectplancell = projectplanrow.createCell(14);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Actual Start Date"));

        projectplancell = projectplanrow.createCell(15);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Actual End Date"));
        
        
        StringBuffer eBody=new StringBuffer();
	
	AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao(); 
	
	ArrayList<TtrnProjecthierarchy> taskList=new ArrayList<TtrnProjecthierarchy>();
	ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
	taskSearchDto.setProjectWorkflowId(formBean.getProjectWorkflowId());
	taskSearchDto.setTaskOption("ALL");
	PagingSorting tasksPS=new PagingSorting(false,true);
	tasksPS.setDefaultifNotPresent("special", PagingSorting.increment, 1);
	taskSearchDto.setTasksPS(tasksPS);		
	taskList=daoObj.getTasks(conn,taskSearchDto);
	
	
	HashMap<Long, TtrnProjecthierarchy> mapProject=new HashMap<Long, TtrnProjecthierarchy>();
	for (TtrnProjecthierarchy projecthierarchy : taskList) {
		mapProject.put(projecthierarchy.getCurrenttaskid(), projecthierarchy);
	}
	
	ArrayList<TtrnProjecthierarchy> taskListForNext=taskList;
	HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>> map=new HashMap<Long, ArrayList<TmstProjecthierarchytasksflow>>(); 
	for (TtrnProjecthierarchy projecthierarchy : taskListForNext) {
		map.put(projecthierarchy.getCurrenttaskid(),projecthierarchy.getNextTaskList());
	}
	
	int iCount = 3;
	for (TtrnProjecthierarchy task: taskList) 
	{
	    
	    	projectplanrow = projectplansheet.createRow(iCount);
	        
	        projectplancell = projectplanrow.createCell(0);
	        HSSFRichTextString stagename = new HSSFRichTextString(task.getStagename());
	        projectplancell.setCellValue(stagename);
	        
	        projectplancell = projectplanrow.createCell(1);
	        HSSFRichTextString taskname = new HSSFRichTextString(task.getTaskname());
	        projectplancell.setCellValue(taskname);

	        projectplancell = projectplanrow.createCell(2);
	        HSSFRichTextString taskdesc = new HSSFRichTextString(task.getTaskdesc());
	        projectplancell.setCellValue(taskdesc);

	        
	        projectplancell = projectplanrow.createCell(3);
	        String isMand = null;
	        if(1==task.getTaskTasktype())
		{
			isMand ="Y";
		}
	        else if(0==task.getTaskTasktype())
		{
	            isMand ="N";
		}
	        HSSFRichTextString mand = new HSSFRichTextString(isMand);
	        projectplancell.setCellValue(isMand);

	        projectplancell = projectplanrow.createCell(4);
	        HSSFRichTextString assignedto = new HSSFRichTextString(task.getAssignedtouserName());
	        projectplancell.setCellValue(assignedto);

	        projectplancell = projectplanrow.createCell(5);
	        HSSFRichTextString rolename = new HSSFRichTextString(task.getTaskstakeholderName());
	        projectplancell.setCellValue(rolename);
	        
	        String isFirstTask = null;
	        if(1==task.getIsfirsttask())
		{
	            isFirstTask = "Y";
		}
		else if(0==task.getIsfirsttask())
		{
		    isFirstTask = "N";
		}
	        
	        projectplancell = projectplanrow.createCell(6);
	        HSSFRichTextString firsttask = new HSSFRichTextString(isFirstTask);
	        projectplancell.setCellValue(firsttask);

	        String isLastTask = null;
	        if(1==task.getIslasttask())
		{
	        	isLastTask = "Y";
		}
		else if(0==task.getIslasttask())
		{
			isLastTask = "N";
		}
	        projectplancell = projectplanrow.createCell(7);
	        HSSFRichTextString lasttask = new HSSFRichTextString(isLastTask);
	        projectplancell.setCellValue(lasttask);

	        String csvList="";
		ArrayList<TmstProjecthierarchytasksflow> next=map.get(task.getCurrenttaskid());
		if(next!=null)
		{
			for (TmstProjecthierarchytasksflow row : next) {
				Long nextTaskId=row.getTaskid();
				TtrnProjecthierarchy ttrnProjecthierarchy=mapProject.get(nextTaskId);
				csvList=csvList+","+ttrnProjecthierarchy.getTaskname();
			}
			if(csvList.length()>0)
			{
				csvList=csvList.substring(1);
			}
		}
		
	        projectplancell = projectplanrow.createCell(8);
	        HSSFRichTextString csvList1 = new HSSFRichTextString(csvList);
	        projectplancell.setCellValue(csvList1);
		
	        projectplancell = projectplanrow.createCell(9);
	        HSSFRichTextString duration = new HSSFRichTextString(String.valueOf(task.getTaskduration()));
	        projectplancell.setCellValue(duration);

	        String attachment = null;
	        if(1==task.getTaskIsattachment())
		{
	            attachment = task.getTaskReferencedocname();
		}
		else if(0==task.getTaskIsattachment())
		{
		    attachment = "";
		}
	        projectplancell = projectplanrow.createCell(10);
	        HSSFRichTextString document = new HSSFRichTextString(attachment);
	        projectplancell.setCellValue(document);

	        projectplancell = projectplanrow.createCell(11);
	        HSSFRichTextString remark = new HSSFRichTextString(task.getTaskTaskinstructionremarks());
	        projectplancell.setCellValue(remark);

	        java.util.Date date=task.getTaskstartdate();
	        projectplancell = projectplanrow.createCell(12);
	        HSSFRichTextString targetstartdate = new HSSFRichTextString(Utility.showDate_Report(date));
	        projectplancell.setCellValue(targetstartdate);
	        
	        date=task.getTaskenddate();
	        projectplancell = projectplanrow.createCell(13);
	        HSSFRichTextString targetenddate = new HSSFRichTextString(Utility.showDate_Report(date));
	        projectplancell.setCellValue(targetenddate);
	        
	        date=task.getActualtaskstartdate();
	        projectplancell = projectplanrow.createCell(14);
	        HSSFRichTextString actualstart = new HSSFRichTextString(Utility.showDate_Report(date));
	        projectplancell.setCellValue(actualstart);

	        date=task.getActualtaskenddate();
	        projectplancell = projectplanrow.createCell(15);
	        HSSFRichTextString actualenddate = new HSSFRichTextString(Utility.showDate_Report(date));
	        projectplancell.setCellValue(actualenddate);
	        
	        iCount++;
	    
	}

        
        return wb;
    }
    
    public void makeRoleSheet(Session hibernateSession, HSSFSheet roleSheet, HSSFCellStyle headerCellStyle) throws NpdException{
//    	fetch role data
    	AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
        ArrayList<TmRoles> taskstakeholderList=daoObj.getRoles(hibernateSession);
        
    	HSSFRow excelRow = null;
        HSSFCell excelCell = null;
    	excelRow = roleSheet.createRow(0);

        /*excelCell = excelRow.createCell(0);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Role List"));*/
        

        excelRow = roleSheet.createRow(0);
        
        excelCell = excelRow.createCell(0);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Role Id"));
        excelCell = excelRow.createCell(1);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Role Name"));
        
        int iCount = 1;
        for (TmRoles role : taskstakeholderList) {
        	excelRow = roleSheet.createRow(iCount++);
            
        	excelCell = excelRow.createCell(0);
            excelCell.setCellValue( new HSSFRichTextString(""+role.getRoleid()));
            
            excelCell = excelRow.createCell(1);
            excelCell.setCellValue( new HSSFRichTextString(""+role.getRolename()));
    	}
        
    	
    	
    }

    public void makeStageSheet(Connection connection, String projectWorkflowId, HSSFSheet stageSheet, HSSFCellStyle headerCellStyle) throws NpdException{
//        fecthing stages
    	ProjectPlanInstanceBean stageSearchDto=new ProjectPlanInstanceBean();
    	stageSearchDto.setProjectWorkflowId(projectWorkflowId);
    	PagingSorting stagesPS=new PagingSorting(false,true);//since paging not required hence false
    	
    	stagesPS.setDefaultifNotPresent("stageName", PagingSorting.increment, 1);
    	stageSearchDto.setStagesPS(stagesPS);
    	AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
    	ArrayList<TtrnProjecthierarchy> stageList=daoObj.getStages(connection,stageSearchDto);
    	
    	
    	HSSFRow excelRow = null;
        HSSFCell excelCell = null;
        
        
            
        excelRow = stageSheet.createRow(0);
        
    /*		        excelCell = excelRow.createCell(0);
            excelCell.setCellStyle(headerCellStyle);
            excelCell.setCellValue(new HSSFRichTextString("Stage List"));*/
        

        excelRow = stageSheet.createRow(0);
        
        excelCell = excelRow.createCell(0);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Stage Id"));
        excelCell = excelRow.createCell(1);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Stage Name"));
        
        int iCount = 1;
        for (TtrnProjecthierarchy stage : stageList) {
        	excelRow = stageSheet.createRow(iCount++);
            
        	excelCell = excelRow.createCell(0);
            excelCell.setCellValue( new HSSFRichTextString(""+stage.getCurrentstageid()));
            
            excelCell = excelRow.createCell(1);
            excelCell.setCellValue( new HSSFRichTextString(""+stage.getStagename()));
    	}
       
    	
    }

   /* public void makeProjectPlanSheet(Connection connection, String projectWorkflowId, HSSFSheet projectplansheet, HSSFCellStyle headerCellStyle) throws NpdException{
    	
    	 //header of project plan sheet
        HSSFRow projectplanrow = projectplansheet.createRow(0);
        
        
        int count_cell=-1;
        
        HSSFCell projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Stage Id"));
        //projectplansheet.setColumnHidden(count_cell, true);
        
        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Stage Name"));
        

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Task Id"));
        
        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Task Name"));


        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Task Desc"));

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Mandatory(Y/N)"));
        
        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Rejection Allowed(Y/N)"));

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Stake Holder-Role Id"));
        
        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Stake Holder-Role Name"));
        
        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Employee Id"));
        
        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Employee Name"));

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Is First Task(Y/N)"));

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Is Last Task(Y/N)"));

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Previous Tasks Ids"));
        
        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Previous Tasks"));

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Planned Duration"));

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Document To Upload Id"));
        
        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Document To Upload"));

        projectplancell = projectplanrow.createCell(++count_cell);
        projectplancell.setCellStyle(headerCellStyle);
        projectplancell.setCellValue(new HSSFRichTextString("Remarks"));

        AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
        ArrayList<TtrnProjecthierarchy> taskList=new ArrayList<TtrnProjecthierarchy>();
    	ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
    	taskSearchDto.setProjectWorkflowId(projectWorkflowId);
    	taskSearchDto.setTaskOption("ALL");
    	PagingSorting tasksPS=new PagingSorting(false,true);
    	tasksPS.setDefaultifNotPresent(AttachEditProjectPlanDao.EXCEL_DOWNLOAD_EDIT, PagingSorting.increment, 1);
    	taskSearchDto.setTasksPS(tasksPS);		
    	taskList=daoObj.getTasks(connection,taskSearchDto);
    	
    	HashMap<Long, TtrnProjecthierarchy> mapProject=new HashMap<Long, TtrnProjecthierarchy>();
    	for (TtrnProjecthierarchy projecthierarchy : taskList) {
    		mapProject.put(projecthierarchy.getCurrenttaskid(), projecthierarchy);
    	}
    	
    	int iCount = 1;
    	for (TtrnProjecthierarchy task : taskList) {
    		
    		projectplanrow = projectplansheet.createRow(iCount++);
            
    		count_cell=-1;
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue(new HSSFRichTextString(""+task.getCurrentstageid()));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

            
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue(new HSSFRichTextString(task.getStagename()));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue( new HSSFRichTextString(""+task.getCurrenttaskid()));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue( new HSSFRichTextString(task.getTaskname()));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue( new HSSFRichTextString(task.getTaskdesc()));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
            projectplancell = projectplanrow.createCell(++count_cell);
            Integer isMandatory=task.getTaskTasktype();
            if(isMandatory==null || isMandatory==0)
            {
            	projectplancell.setCellValue( new HSSFRichTextString("N"));	
                projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            }
            else if(isMandatory==1)
            {
            	projectplancell.setCellValue( new HSSFRichTextString("Y"));
                projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            }
            
            projectplancell = projectplanrow.createCell(++count_cell);
            Integer isRejectionAllowed=task.getRejectionAllowed();
            if(isRejectionAllowed==null || isRejectionAllowed==0)
            {
            	projectplancell.setCellValue( new HSSFRichTextString("N"));
                projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );	
            }
            else if(isRejectionAllowed==1)
            {
            	projectplancell.setCellValue( new HSSFRichTextString("Y"));
                projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            }
            
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue( new HSSFRichTextString(""+task.getTaskstakeholder()));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue( new HSSFRichTextString(task.getTaskstakeholderName()));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
            String assignedId="";
            if(task.getAssignedtouserid()!=0)
            {
            	assignedId=""+task.getAssignedtouserid();
            }
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue( new HSSFRichTextString(assignedId));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue( new HSSFRichTextString(task.getAssignedtouserName()));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
            
            String isFirstTask = null;
            if(1==task.getIsfirsttask())
    		{
    	            isFirstTask = "Y";
    		}
    		else 
    		{
    		    isFirstTask = "N";
    		}
            
            projectplancell = projectplanrow.createCell(++count_cell);
            HSSFRichTextString firsttask = new HSSFRichTextString(isFirstTask);
            projectplancell.setCellValue(firsttask);
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

            String isLastTask = null;
            if(1==task.getIslasttask())
    		{
    	        	isLastTask = "Y";
    		}
    		else 
    		{
    			isLastTask = "N";
    		}
            projectplancell = projectplanrow.createCell(++count_cell);
            HSSFRichTextString lasttask = new HSSFRichTextString(isLastTask);
            projectplancell.setCellValue(lasttask);
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

            String csvList="";
            String csvList_Ids="";
    		ArrayList<TmstProjecthierarchytasksflow> prev=task.getPrevTaskList();
    		if(prev!=null)
    		{
    			for (TmstProjecthierarchytasksflow row : prev) {
    				Long prevTaskId=row.getPrevtaskid();
    				TtrnProjecthierarchy ttrnProjecthierarchy=mapProject.get(prevTaskId);
    				csvList=csvList+","+ttrnProjecthierarchy.getTaskname();
    				csvList_Ids=csvList_Ids+","+ttrnProjecthierarchy.getCurrenttaskid();
    			}
    			if(csvList.length()>0)
    			{
    				csvList=csvList.substring(1);
    				csvList_Ids=csvList_Ids.substring(1);
    			}
    		}
    	
    		projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue(new HSSFRichTextString(csvList_Ids));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
    		
    		projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue(new HSSFRichTextString(csvList));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
    	
            projectplancell = projectplanrow.createCell(++count_cell);
            HSSFRichTextString duration = new HSSFRichTextString(String.valueOf(task.getTaskduration()));
            projectplancell.setCellValue(duration);
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

            String attachmentId=null;
            String attachment = null;
            if(1==task.getTaskIsattachment())
    		{
    	            attachment = task.getTaskReferencedocname();
    	            attachmentId=String.valueOf(task.getTaskReferencedocid());
    		}
    		else 
    		{
    		    attachment = "";
    		    attachmentId="";
    		}
            projectplancell = projectplanrow.createCell(++count_cell);
            projectplancell.setCellValue(new HSSFRichTextString(attachmentId));
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
            projectplancell = projectplanrow.createCell(++count_cell);
            HSSFRichTextString document = new HSSFRichTextString(attachment);
            projectplancell.setCellValue(document);
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

            projectplancell = projectplanrow.createCell(++count_cell);
            HSSFRichTextString remark = new HSSFRichTextString(task.getTaskTaskinstructionremarks());
            projectplancell.setCellValue(remark);
            projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
            
    	}
        

    	
    }*/
    
    
    public void makeProjectPlanSheetFromTemplate(Connection connection, String projectWorkflowId, HSSFSheet projectplansheet, HSSFCellStyle headerCellStyle) throws Exception{
    	
//    	header of project plan sheet
        HSSFRow projectplanrow = projectplansheet.createRow(0);
        
        FileInputStream templateStream;
        HSSFRow templateRow = null;
 	
 		templateStream = new FileInputStream(AppConstants.UploadExcelPlanTemplateFilePath);
 	
        HSSFWorkbook templateWorkbook = new HSSFWorkbook(templateStream);
        HSSFSheet templateFileSheet=templateWorkbook.getSheetAt(0);
         templateRow = templateFileSheet.getRow(0);
         //projectplansheet.setColumnHidden(count_cell, true);	 
         HSSFPatriarch patr = projectplansheet.createDrawingPatriarch();
        int count_cell=-1;
        HSSFClientAnchor anchor=new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5);
        HSSFCell projectplancell = null;
        for(int i=0;i<templateRow.getLastCellNum();i++)
        {
     	   projectplancell = projectplanrow.createCell(++count_cell);
 	       createTemplateCell(projectplancell,headerCellStyle,count_cell,templateFileSheet,projectplansheet,patr,anchor);
        }
       
 	 

       AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
       ArrayList<TtrnProjecthierarchy> taskList=new ArrayList<TtrnProjecthierarchy>();
   	ProjectPlanInstanceBean taskSearchDto=new ProjectPlanInstanceBean();
   	taskSearchDto.setProjectWorkflowId(projectWorkflowId);
   	taskSearchDto.setTaskOption("ALL");
   	PagingSorting tasksPS=new PagingSorting(false,true);
   	tasksPS.setDefaultifNotPresent(AttachEditProjectPlanDao.EXCEL_DOWNLOAD_EDIT, PagingSorting.increment, 1);
   	taskSearchDto.setTasksPS(tasksPS);		
   	taskList=daoObj.getTasks(connection,taskSearchDto);
   	
   	HashMap<Long, TtrnProjecthierarchy> mapProject=new HashMap<Long, TtrnProjecthierarchy>();
   	for (TtrnProjecthierarchy projecthierarchy : taskList) {
   		mapProject.put(projecthierarchy.getCurrenttaskid(), projecthierarchy);
   	}
   	
   	int iCount = 1;
   	for (TtrnProjecthierarchy task : taskList) {
   		
   		projectplanrow = projectplansheet.createRow(iCount++);
           
   		count_cell=-1;
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(""+task.getCurrentstageid()));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getStagename()));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(""+task.getCurrenttaskid()));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getTaskname()));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getTaskdesc()));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
           projectplancell = projectplanrow.createCell(++count_cell);
           Integer isMandatory=task.getTaskTasktype();
           if(isMandatory==null || isMandatory==0)
           {
           	projectplancell.setCellValue( new HSSFRichTextString("N"));	
               projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           }
           else if(isMandatory==1)
           {
           	projectplancell.setCellValue( new HSSFRichTextString("Y"));
               projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           }
           
           projectplancell = projectplanrow.createCell(++count_cell);
           Integer isRejectionAllowed=task.getRejectionAllowed();
           if(isRejectionAllowed==null || isRejectionAllowed==0)
           {
           	projectplancell.setCellValue( new HSSFRichTextString("N"));
               projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );	
           }
           else if(isRejectionAllowed==1)
           {
           	projectplancell.setCellValue( new HSSFRichTextString("Y"));
               projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           }
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(""+task.getTaskstakeholder()));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getTaskstakeholderName()));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
           String assignedId="";
           if(task.getAssignedtouserid()!=0)
           {
           	assignedId=""+task.getAssignedtouserid();
           }
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(assignedId));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getAssignedtouserName()));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
           
           String isFirstTask = null;
           if(1==task.getIsfirsttask())
   		{
   	            isFirstTask = "Y";
   		}
   		else 
   		{
   		    isFirstTask = "N";
   		}
           
           projectplancell = projectplanrow.createCell(++count_cell);
           HSSFRichTextString firsttask = new HSSFRichTextString(isFirstTask);
           projectplancell.setCellValue(firsttask);
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

           String isLastTask = null;
           if(1==task.getIslasttask())
   		{
   	        	isLastTask = "Y";
   		}
   		else 
   		{
   			isLastTask = "N";
   		}
           projectplancell = projectplanrow.createCell(++count_cell);
           HSSFRichTextString lasttask = new HSSFRichTextString(isLastTask);
           projectplancell.setCellValue(lasttask);
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

           String csvList="";
           String csvList_Ids="";
   		ArrayList<TmstProjecthierarchytasksflow> prev=task.getPrevTaskList();
   		if(prev!=null)
   		{
   			for (TmstProjecthierarchytasksflow row : prev) {
   				Long prevTaskId=row.getPrevtaskid();
   				TtrnProjecthierarchy ttrnProjecthierarchy=mapProject.get(prevTaskId);
   				csvList=csvList+","+ttrnProjecthierarchy.getTaskname();
   				csvList_Ids=csvList_Ids+","+ttrnProjecthierarchy.getCurrenttaskid();
   			}
   			if(csvList.length()>0)
   			{
   				csvList=csvList.substring(1);
   				csvList_Ids=csvList_Ids.substring(1);
   			}
   		}
   	
   		projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(csvList_Ids));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
   		
   		projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(csvList));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
   	
           projectplancell = projectplanrow.createCell(++count_cell);
           HSSFRichTextString duration = new HSSFRichTextString(String.valueOf(task.getTaskduration()));
           projectplancell.setCellValue(duration);
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

           String attachmentId=null;
           String attachment = null;
           if(1==task.getTaskIsattachment())
   		{
   	            attachment = task.getTaskReferencedocname();
   	            attachmentId=String.valueOf(task.getTaskReferencedocid());
   		}
   		else 
   		{
   		    attachment = "";
   		    attachmentId="";
   		}
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(attachmentId));
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
           projectplancell = projectplanrow.createCell(++count_cell);
           HSSFRichTextString document = new HSSFRichTextString(attachment);
           projectplancell.setCellValue(document);
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );

           projectplancell = projectplanrow.createCell(++count_cell);
           HSSFRichTextString remark = new HSSFRichTextString(task.getTaskTaskinstructionremarks());
           projectplancell.setCellValue(remark);
           projectplancell.setCellType( HSSFCell.CELL_TYPE_STRING );
           
   	}
       

   	
   }
    

	
	private void createTemplateCell(HSSFCell projectplancell, HSSFCellStyle headerCellStyle, int count_cell, HSSFSheet templateFileSheet, HSSFSheet projectplansheet, HSSFPatriarch patr, HSSFClientAnchor anchor) {
		
		HSSFRow templateRow=templateFileSheet.getRow(0);
		
		projectplancell.setCellStyle(headerCellStyle);
		
		//setting cell value from template
	       projectplancell.setCellValue(templateRow.getCell(count_cell).getRichStringCellValue());
	       
	       //setting column width from template
	       projectplansheet.setColumnWidth(count_cell,templateFileSheet.getColumnWidth(count_cell));
	       //projectplancell.setCellComment(templateRow.getCell(count_cell).getCellComment());
	       
	       //setting cell comment from template
	       if(templateRow.getCell(count_cell).getCellComment()!=null)
		    {	
	    	   HSSFComment comment=patr.createComment(anchor);
	    	   	comment.setString(templateRow.getCell(count_cell).getCellComment().getString());
		       	projectplancell.setCellComment(comment);
	        }
	}
	/**
	 * @method validateUploadedExcel
	 * @purpose validate uploaded excel agaists the template before saving to
	 *          the database
	 * @param FormFile,
	 * @param templatePath
	 * @param messages 
	 * @param uploadedFilePath
	 * @return status
	 * @throws throws
	 *             NpdException
	 */
	public int validateUploadedExcel(FormFile file, String templateFilePath, ActionMessages messages) throws NpdException{

		AppConstants.NPDLOGGER.info("UploadServiceImpl's validateUploadedExcel() started");
		int status = 0;
		String fileName = null;
		
		try {

			fileName = file.getFileName();
			
			if (fileName != null) {

				status = checkSheet(file, templateFilePath);
				if (status != 1 && status != 2 && status != 7 && status != 8)
					status = checkColumns(file, templateFilePath);
				if (status != 1 && status != 2 && status != 7 && status != 8
						&& status != 4 && status != 5)
					status = checkBlankSheetAndDataLength(file,templateFilePath,messages);
			}
		} catch (Exception e) {
			throw new NpdException(e);
		}
		AppConstants.NPDLOGGER.info("UploadServiceImpl's validateUploadedExcel() completed");
		return status;
	}
	
	/**
	 * @param templateFilePath 
	 * @method checkSheet
	 * @purpose checks the number of sheet and sheetname of file uploaded
	 *          against that of template
	 * @param FormFile
	 * @param templateName
	 * @param uploadedFilePath
	 * @return
	 * @throws throws
	 *             NpdException
	 */

	public int checkSheet(FormFile file, String templateFilePath ) throws NpdException {
		AppConstants.NPDLOGGER.info("UploadServiceImpl's checkSheet() completed");
		int sheetsInFile, sheetsInTemplate, retCode = 0;
		HSSFWorkbook fileWorkBook, templateWorkbook;

		try {
			FileInputStream templateStream=new FileInputStream(templateFilePath);
			fileWorkBook = new HSSFWorkbook(file.getInputStream());
			templateWorkbook = new HSSFWorkbook(templateStream);

			sheetsInFile = fileWorkBook.getNumberOfSheets();
			sheetsInTemplate = templateWorkbook.getNumberOfSheets();
			if (fileWorkBook.getAllEmbeddedObjects().size() > 0) {
				retCode = 8;
			}
			if (sheetsInFile != sheetsInTemplate) {
				retCode = 1;
			} /*
				 * else { for (int sheetCount = 0; sheetCount < sheetsInFile;
				 * sheetCount++) { String fileSheetName = fileWorkBook
				 * .getSheetName(sheetCount); String templateSheetName =
				 * templateWorkbook .getSheetName(sheetCount); if
				 * (!fileSheetName.equals(templateSheetName)) { retCode = 2;
				 * break; } } }
				 */

		} catch (Exception e) {
			if (retCode == 0) {
				retCode = 7;

			} else {

				AppConstants.NPDLOGGER
						.error("Error while getting checkSheet "
								+ e.getMessage());
				throw new NpdException(e);
			}
		}

		AppConstants.NPDLOGGER.info("UploadServiceImpl's checkSheet() completed");
		return retCode;

	}

	/**
	 * @method checkBlankSheetAndDataLength
	 * @purpose check if file uploaded has any blank sheet
	 * @param FormFile,
	 *            uploadedFilePath
	 * @return
	 * @throws NpdException
	 */
	public int checkBlankSheetAndDataLength(FormFile file,String templateFilePath,ActionMessages messages)
			throws NpdException {
 		AppConstants.NPDLOGGER.info("UploadServiceImpl's checkBlankSheet() completed");
		int sheetsInFile, retCode = 0, rowCount, columnCount,templateColumnCount;
		HSSFWorkbook fileWorkBook,templateWorkbook;
		HSSFSheet fileSheet,templateFileSheet;
		HSSFRow fileRow,templateMaxLengthRow;
		HSSFCell fileCell,templateMaxLengthCell;
		try {
			int errorsFound=0;
			FileInputStream templateStream=new FileInputStream(templateFilePath);
			fileWorkBook = new HSSFWorkbook(file.getInputStream());
			sheetsInFile = fileWorkBook.getNumberOfSheets();
			
			templateWorkbook = new HSSFWorkbook(templateStream);
			int rowMaxLength=1;
			for (int sheetCount = 0; sheetCount < sheetsInFile ; sheetCount++) {
				retCode = 0;
				fileSheet = fileWorkBook.getSheetAt(sheetCount);
				templateFileSheet = templateWorkbook.getSheetAt(sheetCount);
				rowCount = fileSheet.getLastRowNum();
				
				if(rowCount==0)
				{
					return 3;
				}
				
				Integer [] colAllowedLengths=null;
				String [] colNames=null; 
				templateMaxLengthRow = templateFileSheet.getRow(rowMaxLength);
				if (templateMaxLengthRow != null) {
					templateColumnCount = templateMaxLengthRow.getLastCellNum();
					colAllowedLengths=new Integer[templateColumnCount];
					for(int col=0;col<templateColumnCount;col++)
					{
						templateMaxLengthCell = templateMaxLengthRow.getCell(col);
						if (templateMaxLengthCell!=null && !"".equals(templateMaxLengthCell.toString().trim())) {
							Float f=Float.parseFloat(templateMaxLengthCell.toString().trim());
							colAllowedLengths[col]=f.intValue();
						}
						else
						{
							colAllowedLengths[col]=0;
						}
					}
				}
				
				
				
				
				for (int row = 1; row <= rowCount ; row++) {
					fileRow = fileSheet.getRow(row);
					if (fileRow != null) {
						columnCount = fileRow.getLastCellNum();
						for (int col = 0; col < columnCount ; col++) {
							fileCell = fileRow.getCell(col);
							if (fileCell != null) {
//								check length
								
								//String s=fileCell.getStringCellValue();
								String str=fileCell.toString().trim();
								if(fileCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC)
								{
									double val=fileCell.getNumericCellValue();
									String valstr=String.valueOf(val);
									str=Utility.convertWithOutE_WithOutDotZero(valstr);
								}
								if (colAllowedLengths[col]==0 || str.length()<=colAllowedLengths[col]) {
									retCode = 0;
								}else
								{
									String columnName=templateFileSheet.getRow(0).getCell(col).toString();
									messages.add("colLen_overflow",new ActionMessage("errors.excel.column.length",columnName,colAllowedLengths[col],row+1));
									errorsFound=1;
									//AppConstants.NPDLOGGER.info("UploadServiceImpl's checkBlankSheet() completed");
									//retCode=9;
									//return retCode;
								}
							}
						}
					}
				}
				/*if (retCode == 3) {
					break;
				}*/

			}
			if(errorsFound==1)
			{
				retCode=9;
			}

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error("Error while getting checkBlankSheet "
					+ e.getMessage());
			throw new NpdException(e);
		}

		
		AppConstants.NPDLOGGER.info("UploadServiceImpl's checkBlankSheet() completed");
		return retCode;
	}

	/**
	 * @method checkColumns
	 * @purpose checks the number and names of columns of file uploaded against
	 *          the template
	 * @param FormFile
	 * @param templateName,
	 *            uploadedFilePath
	 * @return
	 * @throws NpdException
	 */
	public int checkColumns(FormFile file, String templateFilePath) throws NpdException {
		AppConstants.NPDLOGGER.info("UploadServiceImpl's checkColumns() completed");
		int numberOfSheet, sheetCol, templateCol, retCode = 0;
		HSSFWorkbook fileWorkBook, templateWorkbook;
		HSSFSheet fileSheet, templateSheet;
		HSSFRow fileRow, templateRow;
		HSSFCell cellInSheet, cellInTemplate;

		try {
			FileInputStream templateStream=new FileInputStream(templateFilePath);
			fileWorkBook = new HSSFWorkbook(file.getInputStream());
			templateWorkbook = new HSSFWorkbook(templateStream);

			numberOfSheet = fileWorkBook.getNumberOfSheets();

			for (int sheetCount = 0; sheetCount < numberOfSheet && retCode == 0; sheetCount++) {
				fileSheet = fileWorkBook.getSheetAt(sheetCount);
				templateSheet = templateWorkbook.getSheetAt(sheetCount);

				fileRow = fileSheet.getRow(0);
				templateRow = templateSheet.getRow(0);

				if (fileRow != null && templateRow != null) {
					sheetCol = fileRow.getLastCellNum();
					templateCol = templateRow.getLastCellNum();
					if (templateCol != sheetCol) {
						retCode = 4;
						break;
					} else {
						for (int col = 1; col < sheetCol && retCode == 0; col++) {
							cellInSheet = fileRow.getCell(col);
							cellInTemplate = templateRow.getCell(col);
							if (cellInSheet != null && cellInTemplate != null) {
								if (!(cellInSheet.toString().trim()
										.equals(cellInTemplate.toString()
												.trim()))) {
									retCode = 5;
									break;
								}
							} else if (!(cellInSheet == null && cellInTemplate == null)) {
								retCode = 5;
								break;
							}

						}
					}
				} else if (fileRow == null) {
					retCode = 5;
					break;
				}

			}

		} catch (Exception e) {
			AppConstants.NPDLOGGER.error("Error while getting checkColumns " + e.getMessage());
			throw new NpdException(e);
		}

		AppConstants.NPDLOGGER.info("UploadServiceImpl's checkColumns() completed");
		return retCode;
	}
	public void makeErrorLogProjectPlanSheet(Connection connection, ProjectPlanInstanceBean formBean, HSSFSheet projectplansheet, HSSFCellStyle headerCellStyle) throws Exception{
    	
//    	header of project plan sheet
        HSSFRow projectplanrow = projectplansheet.createRow(0);
        
        FileInputStream templateStream;
        HSSFRow templateRow = null;
 	
 		templateStream = new FileInputStream(AppConstants.UploadExcelPlanTemplateFilePath);
 	
        HSSFWorkbook templateWorkbook = new HSSFWorkbook(templateStream);
        HSSFSheet templateFileSheet=templateWorkbook.getSheetAt(0);
         templateRow = templateFileSheet.getRow(0);
         //projectplansheet.setColumnHidden(count_cell, true);	 
         HSSFPatriarch patr = projectplansheet.createDrawingPatriarch();
        int count_cell=-1;
        HSSFClientAnchor anchor=new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5);
        HSSFCell projectplancell = null;
        for(int i=0;i<templateRow.getLastCellNum();i++)
        {
     	   projectplancell = projectplanrow.createCell(++count_cell);
 	       createTemplateCell(projectplancell,headerCellStyle,count_cell,templateFileSheet,projectplansheet,patr,anchor);
        }
        
       
       projectplancell = projectplanrow.createCell(++count_cell);
       projectplancell.setCellStyle(headerCellStyle);
       projectplancell.setCellValue(new HSSFRichTextString("Error Log"));
       

       AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
       ArrayList<PlanExcelUploadDto> taskList=new ArrayList<PlanExcelUploadDto>();
       
   	
   	taskList=daoObj.getErrorLogTasks(connection,formBean.getProjectId());
   	
   	TreeMap<Integer, PlanExcelUploadDto> tasksOfCycle=new TreeMap<Integer, PlanExcelUploadDto>();
   	
   	int cycle_FirstTask_RowNo=-1;
   	
   	int iCount = 1;
   	for (PlanExcelUploadDto task : taskList) {
   		
   		projectplanrow = projectplansheet.createRow(iCount++);
           
   		count_cell=-1;
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(""+task.getStageid()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getStagename()));

           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(""+task.getTaskid()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getTaskname()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getTaskdesc()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getMandatory()));	
           
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getReject_allowed()));	
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(""+task.getStakeholder_roleid()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getStakeholder_rolename()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(""+task.getEmployeeId()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue( new HSSFRichTextString(task.getEmployeeName()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getIsfirst()));

           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getIslast()));

           
   			projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getPrev_task_ids()));
   		
   		projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getPrev_tasks()));
   	
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getPlanned_duration()));

           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getDocument_uploaded_id()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           projectplancell.setCellValue(new HSSFRichTextString(task.getDocument_uploaded()));
           
           projectplancell = projectplanrow.createCell(++count_cell);
           HSSFRichTextString remark = new HSSFRichTextString(task.getRemarks());
           projectplancell.setCellValue(remark);
           
           projectplancell = projectplanrow.createCell(++count_cell);
           HSSFRichTextString errorLog = new HSSFRichTextString(task.getErrorlog());
           projectplancell.setCellValue(errorLog);
           
           if(task.getCycleSequenceNo()!=null && !task.getCycleSequenceNo().trim().equals(""))
           {
        	   tasksOfCycle.put(Integer.parseInt(task.getCycleSequenceNo().trim()), task);
        	   if(task.getCycleSequenceNo().trim().equals("1"))
        	   {
        		   cycle_FirstTask_RowNo=iCount-1;
        	   }
           }
           
   	}
       
   	
   	if(cycle_FirstTask_RowNo!=-1) //cycle found
   	{
	   	String cycleSequence="";
	   	for (Integer seqNo : tasksOfCycle.keySet()) {
	   		cycleSequence=cycleSequence+","+tasksOfCycle.get(seqNo).getTaskid();
		}
	   	if(cycleSequence.length()>0)
	   	{
	   		cycleSequence=cycleSequence.substring(1);
	   		String cycleMessage="Cycle Found - Following Task Ids are forming a cycle : "+cycleSequence;
	   		//put cycleSequence in excel first row
	   		int errorLogCellNo=20-1;
	   		HSSFCell cell=projectplansheet.getRow(cycle_FirstTask_RowNo).getCell(errorLogCellNo);
	   		if(cell!=null)
	   		{
	   			cell.setCellValue(new HSSFRichTextString(cycleMessage));
	   		}
	   		/*else
	   		{
	   			cell=projectplansheet.getRow(cycle_FirstTask_RowNo).createCell(errorLogCellNo);
	   			cell.setCellValue(new HSSFRichTextString(cycleMessage));
	   		}*/
	   	}
   	}
   	
   	

   	
   }
	public void makeReferenceDocSheet(Session hibernateSession, HSSFSheet referenceDocSheet, HSSFCellStyle headerCellStyle) throws Exception{
//    	
    	AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
    	ArrayList<TmReferencedocs> templateList=daoObj.getTemplateDocList(hibernateSession);
        
        
    	HSSFRow excelRow = null;
        HSSFCell excelCell = null;
    	excelRow = referenceDocSheet.createRow(0);

        /*excelCell = excelRow.createCell(0);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Role List"));*/
        

        excelRow = referenceDocSheet.createRow(0);
        
        excelCell = excelRow.createCell(0);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Document Id"));
        excelCell = excelRow.createCell(1);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Document Name"));
        
        int iCount = 1;
        for (TmReferencedocs doc : templateList) {
        	excelRow = referenceDocSheet.createRow(iCount++);
            
        	excelCell = excelRow.createCell(0);
            excelCell.setCellValue( new HSSFRichTextString(""+doc.getRefdocid()));
            
            excelCell = excelRow.createCell(1);
            excelCell.setCellValue( new HSSFRichTextString(""+doc.getRefdocname()));
    	}
        
    	
    	
	}
	public void makeEmployeeSheet(Connection conn, HSSFSheet employeeSheet, HSSFCellStyle headerCellStyle) {
//    	fetch employee data
    	AttachEditProjectPlanDao daoObj=new AttachEditProjectPlanDao();
    	
    	
    	ArrayList<TmEmployee> employeeList=new ArrayList<TmEmployee>();
    	
    	String sql=" SELECT NPDEMPID, EMPNAME, ROLEID, ROLENAME " +
    				" FROM NPD.V_EXCEL_ROLE_EMP_MSTR ORDER BY ROLENAME,EMPNAME";
    	ResultSet rs;
		try {
			rs = conn.createStatement().executeQuery(sql);
			TmEmployee employee=null;
			 while(rs.next())
	        {
				 employee=new TmEmployee();
				 employee.setNpdempid(rs.getLong("NPDEMPID"));
				 employee.setEmpname(rs.getString("EMPNAME"));
				 employee.setCurrentRoleId(rs.getLong("ROLEID"));
				 employee.setCurrentRoleName(rs.getString("ROLENAME"));
				 employeeList.add(employee);
	        }
			 
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
       
        
    	HSSFRow excelRow = null;
        HSSFCell excelCell = null;
    	excelRow = employeeSheet.createRow(0);

        /*excelCell = excelRow.createCell(0);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Role List"));*/
        

        excelRow = employeeSheet.createRow(0);
        
        excelCell = excelRow.createCell(0);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Role Id"));
        excelCell = excelRow.createCell(1);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Role Name"));
        excelCell = excelRow.createCell(2);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Employee Id"));
        excelCell = excelRow.createCell(3);
        excelCell.setCellStyle(headerCellStyle);
        excelCell.setCellValue(new HSSFRichTextString("Employee Name"));
        
        int iCount = 1;
        for (TmEmployee emp : employeeList) {
        	excelRow = employeeSheet.createRow(iCount++);
        	
        	excelCell = excelRow.createCell(0);
            excelCell.setCellValue( new HSSFRichTextString(""+emp.getCurrentRoleId()));
            
            excelCell = excelRow.createCell(1);
            excelCell.setCellValue( new HSSFRichTextString(""+emp.getCurrentRoleName()));
            
        	excelCell = excelRow.createCell(2);
            excelCell.setCellValue( new HSSFRichTextString(""+emp.getNpdempid()));
            
            excelCell = excelRow.createCell(3);
            excelCell.setCellValue( new HSSFRichTextString(""+emp.getEmpname()));
    	}
		
	}
	
}
