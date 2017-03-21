//Tag Name Resource Name  Date		CSR No			Description
//[001]	 	Lawkush		4-March-11	00-05422		In order to make nom mandatory field white and mandatory yellow
//[077]    ASHUTOSH SINGH  01-DEC-11  00-05422        Changes in PO AMOUNT(Decimal validation)
//[078]    Priya Gupta     22-Jun-15  Added a new ldclause coulmn
package com.ibm.ioes.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ibm.ioes.beans.ValidationBean;
import com.ibm.ioes.forms.ContactDTO;
import com.ibm.ioes.forms.NewOrderDto;
import com.ibm.ioes.utilities.AppConstants;
import com.ibm.ioes.utilities.AppUtility;
import com.ibm.ioes.utilities.Utility;


public class NewOrderValidation {

	/**
	 * Method to perform validation of PO tab data. 
	 * @param poData
	 * @return
	 */
	public String validatePoDetails(NewOrderDto poData) {

		NewOrderDto dto = new NewOrderDto();;
		boolean errorsFound = false;
		ArrayList errors = new ArrayList();
		errors=null;
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);
		String returnVal = new String("");
		dto = poData;
		try {
			//start[001]
		if((!dto.getPoDate().equalsIgnoreCase(null) && !dto.getPoDate().equalsIgnoreCase(""))|| dto.getIsReqPODate() == 1 )
		//end[001]
		{
				if (!errorsFound) {
					Object obArray[] = { "" + ValidationBean.VN_DATE_VALID,
							dto.getPoDate(), "PO Date ", simpleDateFormat };
					errors = Utility.validateValue(
							new ValidationBean(obArray),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_VN_DATE_VALID)
							.getCompleteMessageStrings();
					if (errors != null) {

						errorsFound = true;
					}
				}
			}
		//start[001]
		if ((!dto.getPoReceiveDate().equalsIgnoreCase(null)&& !dto.getPoReceiveDate().equalsIgnoreCase("")) || dto.getIsReqPORcvDt() == 1)
		//end[001]
		{
				if (!errorsFound) {

					Object obArray[] = { "" + ValidationBean.VN_DATE_VALID,
							dto.getPoReceiveDate(), "PO Receive Date ",
							simpleDateFormat };
					errors = Utility.validateValue(
							new ValidationBean(obArray),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_VN_DATE_VALID)
							.getCompleteMessageStrings();
					if (errors != null) {

						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getContractStartDate().equalsIgnoreCase(null) && !dto.getContractStartDate().equalsIgnoreCase(""))|| dto.getIsReqcontractStartDate() == 1)
//			end[001]
		{
			if (!errorsFound) {

				Object obArray[] = { "" + ValidationBean.VN_DATE_VALID,
						dto.getContractStartDate(), "Contract Start Date ",
						simpleDateFormat };
				errors = Utility.validateValue(
						new ValidationBean(obArray),
						"" + Utility.CASE_MANDATORY + ","
								+ Utility.CASE_VN_DATE_VALID)
						.getCompleteMessageStrings();
				if (errors != null) {

					errorsFound = true;
				}
			}
		}
//		start[001]
			if ((!dto.getContractStartDate().equalsIgnoreCase(null) && !dto.getContractStartDate().equalsIgnoreCase(""))|| dto.getIsReqcontractStartDate() == 1)
//				end[001]		
			{
				if (!errorsFound) {

					errors = Utility.validateValue(
							new ValidationBean()
									.loadValidationBean_String_SingleSelect(
											"Contract Start Date ", dto
													.getContractStartDate(),
											"-1"), "" + Utility.CASE_MANDATORY)
							.getCompleteMessageStrings();
					if (errors != null) {

						errorsFound = true;
					}
				}
			}
//			start[001]
			if ((!dto.getContractEndDate().equalsIgnoreCase(null) && !dto.getContractEndDate().equalsIgnoreCase("")) || dto.getIsReqcontractEndDate() == 1)
//				end[001]		 
			{
				if (!errorsFound) {

					Object obArray[] = { "" + ValidationBean.VN_DATE_VALID,
							dto.getContractEndDate(), "Contract End Date ",
							simpleDateFormat };
					errors = Utility.validateValue(
							new ValidationBean(obArray),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_VN_DATE_VALID)
							.getCompleteMessageStrings();
					if (errors != null) {

						errorsFound = true;
					}
				}
			}
			// Server Side Security End
			// By Saurabh For Customer PO Date field validatin 
			if ((dto.getCustPoDate()!=null || dto.getCustPoDate()!="" || dto.getIsReqCustPoDate() == 1)
					||(dto.getCustPoDate()!=null && dto.getIsReqCustPoDate() == 1)) {
				if (!errorsFound) {

					Object obArray[] = { "" + ValidationBean.VN_DATE_VALID,
							dto.getCustPoDate(), "Customer PO Date ",
							simpleDateFormat };
					errors = Utility.validateValue(
							new ValidationBean(obArray),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_VN_DATE_VALID)
							.getCompleteMessageStrings();
					if (errors != null) {

						errorsFound = true;
					}
				}
			}
			// By Saurabh Ends here
			// Server Side Security Start for Account Manager
			//start[001]
			if ((!dto.getPeriodsInMonths().equalsIgnoreCase(null) && !dto.getPeriodsInMonths().equalsIgnoreCase("")) || dto.getIsReqPeriodsInMonths() == 1) 
		//		end[001]
			{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getPeriodsInMonths(),
									"Contract Period ", 5),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_DIGITS_ONLY)
							.getCompleteMessageStrings();
					if (errors != null) {

						errorsFound = true;
					}
				}
			}
			// Server Side Security End
			//Added For Cusomer Po Detail field validation
			if ((dto.getCustPoDetailNo()!=null || dto.getCustPoDetailNo()!="" || dto.getIsReqCustPoDetailNo() == 1)||(dto.getCustPoDetailNo()!=null && dto.getIsReqCustPoDetailNo() == 1)) {
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getCustPoDetailNo(),
									"Customer PO Detail No ", 50),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_MANDATORY)
							.getCompleteMessageStrings();
					if (errors != null) {

						errorsFound = true;
					}
				}
			}
			// Server Side Security Start for Account Manager
			//		start[001]
			if ((!dto.getTotalPoAmt().equalsIgnoreCase(null) && !dto.getTotalPoAmt().equalsIgnoreCase("")) || dto.getIsReqTotalPoAmt() == 1) 
				//		end[001]
			{
				if (!errorsFound) {
					/*errors = Utility.validateValue(
							new ValidationBean(dto.getTotalPoAmt(),
									"Total PO Amount", 10),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_DECIMALNUBER)
							.getCompleteMessageStrings();
					if (errors != null) {

						errorsFound = true;
					}*/
					String strChargeAmount=dto.getTotalPoAmt();
					errors=Utility.validateValue(new ValidationBean(strChargeAmount,"Total Charge Amount",15),
							""+Utility.CASE_MANDATORY+","+Utility.CASE_DECIMALNUBER+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
					if(errors!=null)
					{
						//listProductCatelog = errors;
						errorsFound = true;
					}
					else
					{
						int index=strChargeAmount.indexOf('.');
						if(index<0 && strChargeAmount.length()>12)
						{
							errorsFound=true;
							errors= new ArrayList();
							errors.add("Only 12 digits are allowed in "+"PO Amount (before decimal)");
							//listProductCatelog = errors;
						}
						else if(index>12 )
						{
							errorsFound=true;
							errors= new ArrayList();
							errors.add("Only 12 digits are allowed before decimal(.) in "+"PO Amount");
						}
						else if(index>=0 && (strChargeAmount.length()-index-1)>2)
						{
							errorsFound=true;
							errors= new ArrayList();
							errors.add("Only 2 digits are allowed after decimal(.) in "+"PO Amount");
							//listProductCatelog = errors;
						}
					}
				}
			}
			// Server Side Security End
			
			//[078] starts
			if (!errorsFound) {
				errors = Utility.validateValue(
						new ValidationBean(dto.getldClause(),
								"Ld Clause ", 255),
						(dto.getIsReqLdClause()==1?("" + Utility.CASE_MANDATORY + ","):"")
								+ Utility.CASE_MAXLENGTH)
						.getCompleteMessageStrings();
				if (errors != null) {

					errorsFound = true;
				}
			}
			// [078] ends 
			
		} catch (Exception ex) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(ex));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}

		if (errors != null && errors.get(0) != null) {
			return (errors.get(0).toString());
		} else {
			return returnVal;

		}
	}
	public String validateContactDetails(ContactDTO poData) {

		ContactDTO dto = new ContactDTO();;
		boolean errorsFound = false;
		ArrayList errors = new ArrayList();
		errors=null;
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				AppConstants.DATE_FORMAT_PROC);
		String returnVal = new String("");
		dto = poData;
		try {
//			start[001]
			/*
			 *  1. If field is Mandatory, and Data is present : Validate
			 * 2. If field is mandatory and data is not present : Validate
			 * 3. If field is not mandatory and data is present : Validate
			 * 4. If field is not mandatory and Data is not present : skip Validation
			 * 
			 * If Condition below is A|| B, then 
			 * 	A : Data is present
			 * 	B: Mandatory 
			 * 
			 */
		if ((!dto.getContactType().equals(null) && !dto.getContactType()
					.equals("")) || dto.getIsReqContactType() == 1)
//			end[001]
		{

				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getContactType(),
									"Contact Type  of Line No " + poData.getCount()
											+ " of Tab Contact!!", 50),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getSalutationName().equals(null) && !dto.getSalutationName().equals("") ) || dto.getIsReqSaluation() == 1)
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getSalutationName(),
									"Saluation of Line No " + poData.getCount()
											+ " of Tab Contact!!", 50),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getFirstName().equals(null) && !dto.getFirstName().equals("")) || dto.getIsReqFirstName() == 1)
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getFirstName(),
									"First Name of Line No " + poData.getCount()
											+ " of Tab Contact!!", 100),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getLastName().equals(null) && !dto.getLastName().equals("")) || dto.getIsReqLastName() == 1) 
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getLastName(),
									"Last Name of Line No " + poData.getCount()
											+ " of Tab Contact!!", 100),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getCntEmail().equals(null) && !dto.getCntEmail().equals("")) || dto.getIsReqCntEmail() == 1) 
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getCntEmail(),
									"Email of Line No " + poData.getCount()
											+ " of Tab Contact!!", 255),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getCntEmail().equals(null) && !dto.getCntEmail().equals("")) || dto.getIsReqCntEmail() == 1)
//			end[001]	
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getCntEmail(),
									"Email ID of Line No " + poData.getCount()
											+ " of Tab Contact!!", 100),
							"" + Utility.CASE_EMAIL + ","
									+ Utility.CASE_MANDATORY)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getContactCell().equals(null) && !dto.getContactCell().equals("")) || dto.getIsReqContactCell() == 1)
//			end[001]
		{
			if(!errorsFound)
			{
				 errors=Utility.validateValue(new ValidationBean(dto.getContactCell(),"Contact Cell of Line No " +poData.getCount()+ " of Tab Contact!!" ,100),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_DIGITS_ONLY+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					errorsFound=true;
				}
			}
		}
//		start[001]
		if ((!dto.getContactFax().equals(null) && !dto.getContactFax().equals("")) ||dto.getIsReqContactFax() == 1)
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getContactFax(),
									"Contact Fax of Line No " + poData.getCount()
											+ " of Tab Contact!!", 100),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_DIGITS_ONLY + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]		
		if ((!dto.getAddress1().equals(null) && !dto.getAddress1().equals("")) || dto.getIsReqAddress1() == 1) 
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getAddress1(),
									"Address1 of Line No " + poData.getCount()
											+ " of Tab Contact!!", 255),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getAddress2().equals(null) && !dto.getAddress2().equals("")) || dto.getIsReqAddress2() == 1)
//			end[001]
		{
			if(!errorsFound)
			{
				 errors=Utility.validateValue(new ValidationBean(dto.getAddress2(),"Address2 of Line No " +poData.getCount()+ " of Tab Contact!!" ,255),
						""+Utility.CASE_MANDATORY+","+Utility.CASE_SPECIALCHARACTERS+","+Utility.CASE_MAXLENGTH).getCompleteMessageStrings();
				if(errors!=null)
				{
					errorsFound=true;
				}
			}
		}
//		start[001]
		if ((!dto.getAddress3().equals(null) && !dto.getAddress3().equals("")) || dto.getIsReqAddress3() == 1) 
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getAddress3(),
									"Address3 of Line No " + poData.getCount()
											+ " of Tab Contact!!", 255),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getCityName().equals(null) && !dto.getCityName().equals("")) || dto.getIsReqCityName() == 1) 
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getCityName(),
									"City Name of Line No of Line No " + poData.getCount()
											+ " of Tab Contact!!", 50),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getStateName().equals(null) && !dto.getStateName().equals("")) || dto.getIsReqStateName() == 1)
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getStateName(),
									"State Name of Line No " + poData.getCount()
											+ " of Tab Contact!!", 50),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((dto.getCountyName().equals(null) && dto.getCountyName().equals("")) || dto.getIsReqCountyName() == 1)
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getCountyName(),
									"Country Name of Line No " + poData.getCount()
											+ " of Tab Contact!!", 50),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
//		start[001]
		if ((!dto.getPinNo().equals(null) && !dto.getPinNo().equals("")) || dto.getIsReqPinNo() == 1)
//			end[001]
		{
				if (!errorsFound) {
					errors = Utility.validateValue(
							new ValidationBean(dto.getPinNo(),
									"Add Pin of Line No " + poData.getCount()
											+ " of Tab Contact!!", 50),
							"" + Utility.CASE_MANDATORY + ","
									+ Utility.CASE_SPECIALCHARACTERS + ","
									+ Utility.CASE_MAXLENGTH)
							.getCompleteMessageStrings();
					if (errors != null) {
						errorsFound = true;
					}
				}
			}
			// Server Side Security End

		} catch (Exception ex) {
			AppConstants.IOES_LOGGER.error(AppUtility.getStackTrace(ex));
			messages.add(AppConstants.MESSAGE_NAME, new ActionMessage(
					AppConstants.MESSAGE_ID));
		}

		if (errors != null && errors.get(0) != null) {
			return (errors.get(0).toString());
		} else {
			return returnVal;

		}
	}
}
