package com.ibm.ioes.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ValidationBean {

	private String value=null;
	
	private String displayLabel=null;
	
	private String pattern=null;
	
	private String noSelectValue=null;
	
	
	private int maxlength;
	
	private String csv_shortMessageStrings=null;
	
	private ArrayList<String> completeMessageStrings=null;
	
	private ArrayList<String> shortMessageStrings=null;
	
	private String[] multipleSelect=null;
	
	public final static int VN_DATE_COMPARISION=1; 
	public final static int VN_DATE_VALID=2;
	public final static int VN_MULTIPLE_SELECT_STRING=3;
	public final static String GREATER=">";
	public final static String LESS="<";
	public final static String GREATER_EQUAL=">=";
	public final static String LESS_EQUAL="<=";
	public final static String EQUAL="=";
	
	private String datevalue1=null;
	private String displayDateLabel1=null;
	private String datevalue2=null;
	private String displayDateLabel2=null;
	private String operator=null;
	private SimpleDateFormat dateFormat_in=null;
	
	public ValidationBean()
	{
		
	}
	
	public ValidationBean(String value1,String displayLabel)
	{
		this.value=value1;
		this.displayLabel=displayLabel;
	}
	public ValidationBean(String value1,String displayLabel,int maxlength1)
	{
		this.value=value1;
		this.maxlength=maxlength1;
		this.displayLabel=displayLabel;
	}
	
		
	public ValidationBean(Object []validation)
	{
		if(validation==null || validation.length==0)
		{
			return ;
		}
		int validationName=Integer.parseInt((String)validation[0]);
		switch (validationName) 
		{
			case VN_DATE_COMPARISION: 
			{
				try
				{
					this.datevalue1=(String)validation[1];
					this.displayDateLabel1=(String)validation[2];
					this.datevalue2=(String)validation[3];
					this.displayDateLabel2=(String)validation[4];
					this.operator=(String)validation[5];
					this.dateFormat_in=(SimpleDateFormat)validation[6];
					break;
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					return ;
				}
			}
			
			case VN_DATE_VALID: 
			{
				try
				{
				this.datevalue1=(String)validation[1];
				this.displayDateLabel1=(String)validation[2];
				this.value=this.datevalue1;
				this.displayLabel=this.displayDateLabel1;
				this.dateFormat_in=(SimpleDateFormat)validation[3];
				break;
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					return ;
				}
			}
			default:
			 break;
		}
	}
	
	
	
	public ValidationBean loadValidationBean_String_MultipleSelect(String displayLabel,String[]values)
	{
		this.displayLabel=displayLabel;
		this.multipleSelect=values;
		return this;
	}
	
	public ValidationBean loadValidationBean_String_SingleSelect(String displayLabel,String value,String noSelectValue)
	{
		this.displayLabel=displayLabel;
		this.value=value;
		this.noSelectValue=noSelectValue;
		return this;
	}
	
	public ValidationBean loadValidationBean_Time_Pattern(String displayLabel, String value, String pattern) {

		this.displayLabel=displayLabel;
		this.value=value;
		this.pattern=pattern;
		return this;
	}
	
	public ValidationBean loadValidationBean(String displayLabel,String value1)
	{
		this.value=value1;
		this.displayLabel=displayLabel;
		return this;
	}
	public ValidationBean loadValidationBean_Maxlength(String displayLabel,String value1,int maxlength1)
	{
		this.value=value1;
		this.maxlength=maxlength1;
		this.displayLabel=displayLabel;
		return this;
	}
	
	public  ArrayList appendToAndRetNewErrs(ArrayList allErrors)
	{
		if(allErrors==null){
			return getCompleteMessageStrings();
		}
		
		if(getCompleteMessageStrings()!=null)
		{
			allErrors.addAll(getCompleteMessageStrings());
		}
		return getCompleteMessageStrings();
	}
	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public ValidationBean addToCompleteMessageStrings(String str)
	{
		if(completeMessageStrings==null) completeMessageStrings=new ArrayList<String>();
		
		
		this.completeMessageStrings.add(str);
		return this;
	}
	public ValidationBean addToShortMessageStrings(String str)
	{
		if(shortMessageStrings==null) shortMessageStrings=new ArrayList<String>();
		
		
		this.shortMessageStrings.add(str);
		return this;
	}
	public ValidationBean addToMessageStrings(String complete,String shortMessage)
	{
		if(completeMessageStrings==null) completeMessageStrings=new ArrayList<String>();
		if(shortMessageStrings==null) shortMessageStrings=new ArrayList<String>();
		
		this.completeMessageStrings.add(complete);
		this.shortMessageStrings.add(shortMessage);
		return this;
	}
	public ArrayList<String> getCompleteMessageStrings() {
		return completeMessageStrings;
	}
	public void setCompleteMessageStrings(ArrayList<String> completeMessageStrings) {
		this.completeMessageStrings = completeMessageStrings;
	}
	public String getCsv_shortMessageStrings() {
		return csv_shortMessageStrings;
	}
	public void setCsv_shortMessageStrings(String csv_shortMessageStrings) {
		this.csv_shortMessageStrings = csv_shortMessageStrings;
	}
	public String getDisplayLabel() {
		return displayLabel;
	}
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}
	public ArrayList<String> getShortMessageStrings() {
		return shortMessageStrings;
	}
	public void setShortMessageStrings(ArrayList<String> shortMessageStrings) {
		this.shortMessageStrings = shortMessageStrings;
	}
	public SimpleDateFormat getDateFormat_in() {
		return dateFormat_in;
	}
	public void setDateFormat_in(SimpleDateFormat dateFormat_in) {
		this.dateFormat_in = dateFormat_in;
	}
	public String getDatevalue1() {
		return datevalue1;
	}
	public void setDatevalue1(String datevalue1) {
		this.datevalue1 = datevalue1;
	}
	public String getDatevalue2() {
		return datevalue2;
	}
	public void setDatevalue2(String datevalue2) {
		this.datevalue2 = datevalue2;
	}
	public String getDisplayDateLabel1() {
		return displayDateLabel1;
	}
	public void setDisplayDateLabel1(String displayDateLabel1) {
		this.displayDateLabel1 = displayDateLabel1;
	}
	public String getDisplayDateLabel2() {
		return displayDateLabel2;
	}
	public void setDisplayDateLabel2(String displayDateLabel2) {
		this.displayDateLabel2 = displayDateLabel2;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String[] getMultipleSelect() {
		return multipleSelect;
	}

	public void setMultipleSelect(String[] multipleSelect) {
		this.multipleSelect = multipleSelect;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getNoSelectValue() {
		return noSelectValue;
	}

	public void setNoSelectValue(String noSelectValue) {
		this.noSelectValue = noSelectValue;
	}

	
	
	
	
	
	
}
