package com.ibm.ioes.forms;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 MANISHA GARG	24-FEB-12	00-05422		This dto using for ChargeSection of Charge Summary 
public class ChargeSummaryChargeSection {
	
	private String charges_type=null;
	private String charge_name=null;
	private String chargeperiod=null;
	private String chargeamount=null;
	private String chargestatus=null;
	private String spid=null;
	private String is_line_dis=null;
	private String disconnetion_type=null;
	private String billing_trigger_status=null;
	private String bill_period=null;
	private String billperiod=null;
	private String chargefrequency=null;
	private String frequencyamt=null;
	private String startdatelogic=null;
	private String start_date_days=null;
	private String start_date_month=null;
	private String charge_start_date=null;
	private String enddatelogic=null;
	private String end_date_days=null;
	private String end_date_month=null;
	private String charge_end_date=null;
	private String annual_rate=null;
	private String annotation=null;
	private String charge_id=null;
	private String fx_view_id=null;
	private String charge_start_status=null;
	private String start_fx_no=null;
	private String start_fx_status=null;
	private String start_token_no=null;
	private String charge_end_status=null;
	private String end_fx_no=null;
	private String end_fx_status=null;
	private String end_token_no=null;
	private String tax_Rate;
	//lawkush Start
	private String chargeRemarks;
	
	public String getChargeRemarks() {
		return chargeRemarks;
	}
	public void setChargeRemarks(String chargeRemarks) {
		this.chargeRemarks = chargeRemarks;
	}
	//lawkush End
	public String getTax_Rate() {
		return tax_Rate;
	}
	public void setTax_Rate(String tax_Rate) {
		this.tax_Rate = tax_Rate;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	public String getAnnual_rate() {
		return annual_rate;
	}
	public void setAnnual_rate(String annual_rate) {
		this.annual_rate = annual_rate;
	}
	public String getBill_period() {
		return bill_period;
	}
	public void setBill_period(String bill_period) {
		this.bill_period = bill_period;
	}
	public String getBilling_trigger_status() {
		return billing_trigger_status;
	}
	public void setBilling_trigger_status(String billing_trigger_status) {
		this.billing_trigger_status = billing_trigger_status;
	}
	public String getBillperiod() {
		return billperiod;
	}
	public void setBillperiod(String billperiod) {
		this.billperiod = billperiod;
	}
	public String getCharge_end_date() {
		return charge_end_date;
	}
	public void setCharge_end_date(String charge_end_date) {
		this.charge_end_date = charge_end_date;
	}
	public String getCharge_end_status() {
		return charge_end_status;
	}
	public void setCharge_end_status(String charge_end_status) {
		this.charge_end_status = charge_end_status;
	}
	public String getCharge_id() {
		return charge_id;
	}
	public void setCharge_id(String charge_id) {
		this.charge_id = charge_id;
	}
	public String getCharge_name() {
		return charge_name;
	}
	public void setCharge_name(String charge_name) {
		this.charge_name = charge_name;
	}
	public String getCharge_start_date() {
		return charge_start_date;
	}
	public void setCharge_start_date(String charge_start_date) {
		this.charge_start_date = charge_start_date;
	}
	public String getCharge_start_status() {
		return charge_start_status;
	}
	public void setCharge_start_status(String charge_start_status) {
		this.charge_start_status = charge_start_status;
	}
	public String getChargeamount() {
		return chargeamount;
	}
	public void setChargeamount(String chargeamount) {
		this.chargeamount = chargeamount;
	}
	public String getChargefrequency() {
		return chargefrequency;
	}
	public void setChargefrequency(String chargefrequency) {
		this.chargefrequency = chargefrequency;
	}
	public String getChargeperiod() {
		return chargeperiod;
	}
	public void setChargeperiod(String chargeperiod) {
		this.chargeperiod = chargeperiod;
	}
	public String getCharges_type() {
		return charges_type;
	}
	public void setCharges_type(String charges_type) {
		this.charges_type = charges_type;
	}
	public String getChargestatus() {
		return chargestatus;
	}
	public void setChargestatus(String chargestatus) {
		this.chargestatus = chargestatus;
	}
	public String getDisconnetion_type() {
		return disconnetion_type;
	}
	public void setDisconnetion_type(String disconnetion_type) {
		this.disconnetion_type = disconnetion_type;
	}
	public String getEnd_date_days() {
		return end_date_days;
	}
	public void setEnd_date_days(String end_date_days) {
		this.end_date_days = end_date_days;
	}
	public String getEnd_date_month() {
		return end_date_month;
	}
	public void setEnd_date_month(String end_date_month) {
		this.end_date_month = end_date_month;
	}
	public String getEnd_fx_no() {
		return end_fx_no;
	}
	public void setEnd_fx_no(String end_fx_no) {
		this.end_fx_no = end_fx_no;
	}
	public String getEnd_fx_status() {
		return end_fx_status;
	}
	public void setEnd_fx_status(String end_fx_status) {
		this.end_fx_status = end_fx_status;
	}
	public String getEnd_token_no() {
		return end_token_no;
	}
	public void setEnd_token_no(String end_token_no) {
		this.end_token_no = end_token_no;
	}
	public String getEnddatelogic() {
		return enddatelogic;
	}
	public void setEnddatelogic(String enddatelogic) {
		this.enddatelogic = enddatelogic;
	}
	public String getFrequencyamt() {
		return frequencyamt;
	}
	public void setFrequencyamt(String frequencyamt) {
		this.frequencyamt = frequencyamt;
	}
	public String getFx_view_id() {
		return fx_view_id;
	}
	public void setFx_view_id(String fx_view_id) {
		this.fx_view_id = fx_view_id;
	}
	public String getIs_line_dis() {
		return is_line_dis;
	}
	public void setIs_line_dis(String is_line_dis) {
		this.is_line_dis = is_line_dis;
	}
	public String getStart_date_days() {
		return start_date_days;
	}
	public void setStart_date_days(String start_date_days) {
		this.start_date_days = start_date_days;
	}
	public String getStart_date_month() {
		return start_date_month;
	}
	public void setStart_date_month(String start_date_month) {
		this.start_date_month = start_date_month;
	}
	public String getStart_fx_no() {
		return start_fx_no;
	}
	public void setStart_fx_no(String start_fx_no) {
		this.start_fx_no = start_fx_no;
	}
	public String getStart_fx_status() {
		return start_fx_status;
	}
	public void setStart_fx_status(String start_fx_status) {
		this.start_fx_status = start_fx_status;
	}
	public String getStart_token_no() {
		return start_token_no;
	}
	public void setStart_token_no(String start_token_no) {
		this.start_token_no = start_token_no;
	}
	public String getStartdatelogic() {
		return startdatelogic;
	}
	public void setStartdatelogic(String startdatelogic) {
		this.startdatelogic = startdatelogic;
	}
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	
	
	
	
	

}
