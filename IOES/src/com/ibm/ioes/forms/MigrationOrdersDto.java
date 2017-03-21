package com.ibm.ioes.forms;

public class MigrationOrdersDto {
	private String orderNo;
	private String documentNo;
	private String specGrpId;
	private String m6ChildSirKey;
	private String m6ParentSirKey;
	private String pon;
	private String rpon;
	private String orderLineId;
	private String ckt_id;
	private String priLoc;
	private String secLoc;
	private String locDate;
	private String productName;
	private String typeOfOrder;
	private String utp;
	private String opt;
	private String coaxial;
	private String roofSpace;
	private String workStnSeat;
	private String cabinHallMtngRoom;
	private String lockblstorearea;
	private String standardrack;
	private String racksreqd; 
	private String nonstddim; 
	private String nonstdquantity; 
	private String partialrack; 
	private String partialrackid; 
	private String dc_floor_space; 
	private String phac_mtr_perkwh; 
	private String phac_rate_kva_ann; 
	private String phac_rload_kva_ann;
	private String snglephac_mtr_kwhr;
	private String snglphac_rate_kva_an;
	private String snglphac_rload_kva_a;
	private String  min48v_dc_mtr;
	private String min48v_dc_rate;
	private String min48v_d_rld_kva_ann;
	private String rack_id_no;
	private String dcf_remarks;
	private String camera; 
	private String access_control_spec; 
	private String customization; 
	private String camera_specification; 
	private String access_control; 
	private String bas_smart_hand_srv_it; 
	private String prepaid_pack_mh_mont1; 
	private String postpaidpack_mh_mont1; 
	private String adv_smart_hand_srv_nt1; 
	private String prepaid_pack_mh_mont2; 
	private String postpaidpack_mh_mont2; 
	private String media_hndl_vault_serv; 
	private String vendor_management; 
	private String surveillance_camera; 
	private String surveillance_camera_spec; 
	private String dc_mpls_circuit_id; 
	private String anyotherproductsrvc; 
	private String dc_isp;
	private String dc_isp_circuit_id;
	private String dc_p_to_p_links;
	private String feasibility_id; 
	private String dc_mpls; 
	private String m6shortcode; 
	private String crm_acct_no; 
	private String order_status; 
	private String order_provision_status; 
	private String order_login_date;
	
	
	
	
	private String tb_satellite_type ;
	private String tb_ordbital_location ;
	private String tb_start_frequency ;
	private String tb_stop_frequency ;
	private String tb_centre_freq ;
	private String tb_bandwidth ;
	private String tcm_chng_reqster_name ;
	private String tcm_cr_request_date ;
	private String tcm_cr_request_end_time ;
	private String tcm_cr_reqst_start_time ;
	private String tcm_expected_downtime ;
	private String tcm_roll_back_plan ;
	private String tcm_severity ;
	private String tc_audio_bit_rate ;
	private String tc_video_bit_rate ;
	private String tc_router_port_id ;
	private String tc_mux_port_id ;
	private String tc_audio_pid ;
	private String tc_video_pid ;
	private String tu_info_rate_uplink ;
	private String tu_symbol_rate_uplink ;
	private String tu_dvb_standard_uplink ;
	private String tu_fec_uplink ;
	private String tu_modulation_uplink ;
	private String tu_type_of_carrier ;
	private String tpr_customer_type ;
	private String tpr_type_of_order ;
	private String tpr_teleport_location ;
	private String tpr_input_format ;
	private String tpr_playout_services ;
	private String tpr_type_of_service; 
	private String tpr_info_rate ;
	private String tpr_symbol_rate ;
	private String tpr_dvb_standard ;
	private String tpr_fec ;
	private String tpr_compression_tech ;
	private String tpr_modulation ;
	private String tpr_cust_multicast_ip ;
	private String tpr_cust_wan_ip ;
	private String tpr_audio_format ;
	private String tpr_no_of_audios ;
	private String tpr_type_of_audios ;
	private String tpr_video_format ;
	private String tc_compression_tech ;
	private String tcm_communication ;
	
	
	
	
	
	
	
	
	public String getAccess_control() {
		return access_control;
	}
	public void setAccess_control(String access_control) {
		this.access_control = access_control;
	}
	public String getAccess_control_spec() {
		return access_control_spec;
	}
	public void setAccess_control_spec(String access_control_spec) {
		this.access_control_spec = access_control_spec;
	}
	public String getAdv_smart_hand_srv_nt1() {
		return adv_smart_hand_srv_nt1;
	}
	public void setAdv_smart_hand_srv_nt1(String adv_smart_hand_srv_nt1) {
		this.adv_smart_hand_srv_nt1 = adv_smart_hand_srv_nt1;
	}
	public String getAnyotherproductsrvc() {
		return anyotherproductsrvc;
	}
	public void setAnyotherproductsrvc(String anyotherproductsrvc) {
		this.anyotherproductsrvc = anyotherproductsrvc;
	}
	public String getBas_smart_hand_srv_it() {
		return bas_smart_hand_srv_it;
	}
	public void setBas_smart_hand_srv_it(String bas_smart_hand_srv_it) {
		this.bas_smart_hand_srv_it = bas_smart_hand_srv_it;
	}
	public String getCabinHallMtngRoom() {
		return cabinHallMtngRoom;
	}
	public void setCabinHallMtngRoom(String cabinHallMtngRoom) {
		this.cabinHallMtngRoom = cabinHallMtngRoom;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}
	public String getCamera_specification() {
		return camera_specification;
	}
	public void setCamera_specification(String camera_specification) {
		this.camera_specification = camera_specification;
	}
	public String getCkt_id() {
		return ckt_id;
	}
	public void setCkt_id(String ckt_id) {
		this.ckt_id = ckt_id;
	}
	public String getCoaxial() {
		return coaxial;
	}
	public void setCoaxial(String coaxial) {
		this.coaxial = coaxial;
	}
	public String getCrm_acct_no() {
		return crm_acct_no;
	}
	public void setCrm_acct_no(String crm_acct_no) {
		this.crm_acct_no = crm_acct_no;
	}
	public String getCustomization() {
		return customization;
	}
	public void setCustomization(String customization) {
		this.customization = customization;
	}
	public String getDc_floor_space() {
		return dc_floor_space;
	}
	public void setDc_floor_space(String dc_floor_space) {
		this.dc_floor_space = dc_floor_space;
	}
	public String getDc_isp() {
		return dc_isp;
	}
	public void setDc_isp(String dc_isp) {
		this.dc_isp = dc_isp;
	}
	public String getDc_isp_circuit_id() {
		return dc_isp_circuit_id;
	}
	public void setDc_isp_circuit_id(String dc_isp_circuit_id) {
		this.dc_isp_circuit_id = dc_isp_circuit_id;
	}
	public String getDc_mpls() {
		return dc_mpls;
	}
	public void setDc_mpls(String dc_mpls) {
		this.dc_mpls = dc_mpls;
	}
	public String getDc_mpls_circuit_id() {
		return dc_mpls_circuit_id;
	}
	public void setDc_mpls_circuit_id(String dc_mpls_circuit_id) {
		this.dc_mpls_circuit_id = dc_mpls_circuit_id;
	}
	public String getDc_p_to_p_links() {
		return dc_p_to_p_links;
	}
	public void setDc_p_to_p_links(String dc_p_to_p_links) {
		this.dc_p_to_p_links = dc_p_to_p_links;
	}
	public String getDcf_remarks() {
		return dcf_remarks;
	}
	public void setDcf_remarks(String dcf_remarks) {
		this.dcf_remarks = dcf_remarks;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getFeasibility_id() {
		return feasibility_id;
	}
	public void setFeasibility_id(String feasibility_id) {
		this.feasibility_id = feasibility_id;
	}
	public String getLocDate() {
		return locDate;
	}
	public void setLocDate(String locDate) {
		this.locDate = locDate;
	}
	public String getLockblstorearea() {
		return lockblstorearea;
	}
	public void setLockblstorearea(String lockblstorearea) {
		this.lockblstorearea = lockblstorearea;
	}
	public String getM6ChildSirKey() {
		return m6ChildSirKey;
	}
	public void setM6ChildSirKey(String childSirKey) {
		m6ChildSirKey = childSirKey;
	}
	public String getM6ParentSirKey() {
		return m6ParentSirKey;
	}
	public void setM6ParentSirKey(String parentSirKey) {
		m6ParentSirKey = parentSirKey;
	}
	public String getM6shortcode() {
		return m6shortcode;
	}
	public void setM6shortcode(String m6shortcode) {
		this.m6shortcode = m6shortcode;
	}
	public String getMedia_hndl_vault_serv() {
		return media_hndl_vault_serv;
	}
	public void setMedia_hndl_vault_serv(String media_hndl_vault_serv) {
		this.media_hndl_vault_serv = media_hndl_vault_serv;
	}
	public String getMin48v_d_rld_kva_ann() {
		return min48v_d_rld_kva_ann;
	}
	public void setMin48v_d_rld_kva_ann(String min48v_d_rld_kva_ann) {
		this.min48v_d_rld_kva_ann = min48v_d_rld_kva_ann;
	}
	public String getMin48v_dc_mtr() {
		return min48v_dc_mtr;
	}
	public void setMin48v_dc_mtr(String min48v_dc_mtr) {
		this.min48v_dc_mtr = min48v_dc_mtr;
	}
	public String getMin48v_dc_rate() {
		return min48v_dc_rate;
	}
	public void setMin48v_dc_rate(String min48v_dc_rate) {
		this.min48v_dc_rate = min48v_dc_rate;
	}
	public String getNonstddim() {
		return nonstddim;
	}
	public void setNonstddim(String nonstddim) {
		this.nonstddim = nonstddim;
	}
	public String getNonstdquantity() {
		return nonstdquantity;
	}
	public void setNonstdquantity(String nonstdquantity) {
		this.nonstdquantity = nonstdquantity;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getOrder_login_date() {
		return order_login_date;
	}
	public void setOrder_login_date(String order_login_date) {
		this.order_login_date = order_login_date;
	}
	public String getOrder_provision_status() {
		return order_provision_status;
	}
	public void setOrder_provision_status(String order_provision_status) {
		this.order_provision_status = order_provision_status;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getOrderLineId() {
		return orderLineId;
	}
	public void setOrderLineId(String orderLineId) {
		this.orderLineId = orderLineId;
	}
	public String getPartialrack() {
		return partialrack;
	}
	public void setPartialrack(String partialrack) {
		this.partialrack = partialrack;
	}
	public String getPartialrackid() {
		return partialrackid;
	}
	public void setPartialrackid(String partialrackid) {
		this.partialrackid = partialrackid;
	}
	public String getPhac_mtr_perkwh() {
		return phac_mtr_perkwh;
	}
	public void setPhac_mtr_perkwh(String phac_mtr_perkwh) {
		this.phac_mtr_perkwh = phac_mtr_perkwh;
	}
	public String getPhac_rate_kva_ann() {
		return phac_rate_kva_ann;
	}
	public void setPhac_rate_kva_ann(String phac_rate_kva_ann) {
		this.phac_rate_kva_ann = phac_rate_kva_ann;
	}
	public String getPhac_rload_kva_ann() {
		return phac_rload_kva_ann;
	}
	public void setPhac_rload_kva_ann(String phac_rload_kva_ann) {
		this.phac_rload_kva_ann = phac_rload_kva_ann;
	}
	public String getPon() {
		return pon;
	}
	public void setPon(String pon) {
		this.pon = pon;
	}
	public String getPostpaidpack_mh_mont1() {
		return postpaidpack_mh_mont1;
	}
	public void setPostpaidpack_mh_mont1(String postpaidpack_mh_mont1) {
		this.postpaidpack_mh_mont1 = postpaidpack_mh_mont1;
	}
	public String getPostpaidpack_mh_mont2() {
		return postpaidpack_mh_mont2;
	}
	public void setPostpaidpack_mh_mont2(String postpaidpack_mh_mont2) {
		this.postpaidpack_mh_mont2 = postpaidpack_mh_mont2;
	}
	public String getPrepaid_pack_mh_mont1() {
		return prepaid_pack_mh_mont1;
	}
	public void setPrepaid_pack_mh_mont1(String prepaid_pack_mh_mont1) {
		this.prepaid_pack_mh_mont1 = prepaid_pack_mh_mont1;
	}
	public String getPrepaid_pack_mh_mont2() {
		return prepaid_pack_mh_mont2;
	}
	public void setPrepaid_pack_mh_mont2(String prepaid_pack_mh_mont2) {
		this.prepaid_pack_mh_mont2 = prepaid_pack_mh_mont2;
	}
	public String getPriLoc() {
		return priLoc;
	}
	public void setPriLoc(String priLoc) {
		this.priLoc = priLoc;
	}
	public String getRack_id_no() {
		return rack_id_no;
	}
	public void setRack_id_no(String rack_id_no) {
		this.rack_id_no = rack_id_no;
	}
	public String getRacksreqd() {
		return racksreqd;
	}
	public void setRacksreqd(String racksreqd) {
		this.racksreqd = racksreqd;
	}
	public String getRoofSpace() {
		return roofSpace;
	}
	public void setRoofSpace(String roofSpace) {
		this.roofSpace = roofSpace;
	}
	public String getRpon() {
		return rpon;
	}
	public void setRpon(String rpon) {
		this.rpon = rpon;
	}
	public String getSecLoc() {
		return secLoc;
	}
	public void setSecLoc(String secLoc) {
		this.secLoc = secLoc;
	}
	public String getSnglephac_mtr_kwhr() {
		return snglephac_mtr_kwhr;
	}
	public void setSnglephac_mtr_kwhr(String snglephac_mtr_kwhr) {
		this.snglephac_mtr_kwhr = snglephac_mtr_kwhr;
	}
	public String getSnglphac_rate_kva_an() {
		return snglphac_rate_kva_an;
	}
	public void setSnglphac_rate_kva_an(String snglphac_rate_kva_an) {
		this.snglphac_rate_kva_an = snglphac_rate_kva_an;
	}
	public String getSnglphac_rload_kva_a() {
		return snglphac_rload_kva_a;
	}
	public void setSnglphac_rload_kva_a(String snglphac_rload_kva_a) {
		this.snglphac_rload_kva_a = snglphac_rload_kva_a;
	}
	public String getSpecGrpId() {
		return specGrpId;
	}
	public void setSpecGrpId(String specGrpId) {
		this.specGrpId = specGrpId;
	}
	public String getStandardrack() {
		return standardrack;
	}
	public void setStandardrack(String standardrack) {
		this.standardrack = standardrack;
	}
	public String getSurveillance_camera() {
		return surveillance_camera;
	}
	public void setSurveillance_camera(String surveillance_camera) {
		this.surveillance_camera = surveillance_camera;
	}
	public String getSurveillance_camera_spec() {
		return surveillance_camera_spec;
	}
	public void setSurveillance_camera_spec(String surveillance_camera_spec) {
		this.surveillance_camera_spec = surveillance_camera_spec;
	}
	public String getTypeOfOrder() {
		return typeOfOrder;
	}
	public void setTypeOfOrder(String typeOfOrder) {
		this.typeOfOrder = typeOfOrder;
	}
	public String getUtp() {
		return utp;
	}
	public void setUtp(String utp) {
		this.utp = utp;
	}
	public String getVendor_management() {
		return vendor_management;
	}
	public void setVendor_management(String vendor_management) {
		this.vendor_management = vendor_management;
	}
	public String getWorkStnSeat() {
		return workStnSeat;
	}
	public void setWorkStnSeat(String workStnSeat) {
		this.workStnSeat = workStnSeat;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTb_bandwidth() {
		return tb_bandwidth;
	}
	public void setTb_bandwidth(String tb_bandwidth) {
		this.tb_bandwidth = tb_bandwidth;
	}
	public String getTb_centre_freq() {
		return tb_centre_freq;
	}
	public void setTb_centre_freq(String tb_centre_freq) {
		this.tb_centre_freq = tb_centre_freq;
	}
	public String getTb_ordbital_location() {
		return tb_ordbital_location;
	}
	public void setTb_ordbital_location(String tb_ordbital_location) {
		this.tb_ordbital_location = tb_ordbital_location;
	}
	public String getTb_satellite_type() {
		return tb_satellite_type;
	}
	public void setTb_satellite_type(String tb_satellite_type) {
		this.tb_satellite_type = tb_satellite_type;
	}
	public String getTb_start_frequency() {
		return tb_start_frequency;
	}
	public void setTb_start_frequency(String tb_start_frequency) {
		this.tb_start_frequency = tb_start_frequency;
	}
	public String getTb_stop_frequency() {
		return tb_stop_frequency;
	}
	public void setTb_stop_frequency(String tb_stop_frequency) {
		this.tb_stop_frequency = tb_stop_frequency;
	}
	public String getTc_audio_bit_rate() {
		return tc_audio_bit_rate;
	}
	public void setTc_audio_bit_rate(String tc_audio_bit_rate) {
		this.tc_audio_bit_rate = tc_audio_bit_rate;
	}
	public String getTc_audio_pid() {
		return tc_audio_pid;
	}
	public void setTc_audio_pid(String tc_audio_pid) {
		this.tc_audio_pid = tc_audio_pid;
	}
	public String getTc_compression_tech() {
		return tc_compression_tech;
	}
	public void setTc_compression_tech(String tc_compression_tech) {
		this.tc_compression_tech = tc_compression_tech;
	}
	public String getTc_mux_port_id() {
		return tc_mux_port_id;
	}
	public void setTc_mux_port_id(String tc_mux_port_id) {
		this.tc_mux_port_id = tc_mux_port_id;
	}
	public String getTc_router_port_id() {
		return tc_router_port_id;
	}
	public void setTc_router_port_id(String tc_router_port_id) {
		this.tc_router_port_id = tc_router_port_id;
	}
	public String getTc_video_bit_rate() {
		return tc_video_bit_rate;
	}
	public void setTc_video_bit_rate(String tc_video_bit_rate) {
		this.tc_video_bit_rate = tc_video_bit_rate;
	}
	public String getTc_video_pid() {
		return tc_video_pid;
	}
	public void setTc_video_pid(String tc_video_pid) {
		this.tc_video_pid = tc_video_pid;
	}
	public String getTcm_chng_reqster_name() {
		return tcm_chng_reqster_name;
	}
	public void setTcm_chng_reqster_name(String tcm_chng_reqster_name) {
		this.tcm_chng_reqster_name = tcm_chng_reqster_name;
	}
	public String getTcm_communication() {
		return tcm_communication;
	}
	public void setTcm_communication(String tcm_communication) {
		this.tcm_communication = tcm_communication;
	}
	public String getTcm_cr_reqst_start_time() {
		return tcm_cr_reqst_start_time;
	}
	public void setTcm_cr_reqst_start_time(String tcm_cr_reqst_start_time) {
		this.tcm_cr_reqst_start_time = tcm_cr_reqst_start_time;
	}
	public String getTcm_cr_request_date() {
		return tcm_cr_request_date;
	}
	public void setTcm_cr_request_date(String tcm_cr_request_date) {
		this.tcm_cr_request_date = tcm_cr_request_date;
	}
	public String getTcm_cr_request_end_time() {
		return tcm_cr_request_end_time;
	}
	public void setTcm_cr_request_end_time(String tcm_cr_request_end_time) {
		this.tcm_cr_request_end_time = tcm_cr_request_end_time;
	}
	public String getTcm_expected_downtime() {
		return tcm_expected_downtime;
	}
	public void setTcm_expected_downtime(String tcm_expected_downtime) {
		this.tcm_expected_downtime = tcm_expected_downtime;
	}
	public String getTcm_roll_back_plan() {
		return tcm_roll_back_plan;
	}
	public void setTcm_roll_back_plan(String tcm_roll_back_plan) {
		this.tcm_roll_back_plan = tcm_roll_back_plan;
	}
	public String getTcm_severity() {
		return tcm_severity;
	}
	public void setTcm_severity(String tcm_severity) {
		this.tcm_severity = tcm_severity;
	}
	public String getTpr_audio_format() {
		return tpr_audio_format;
	}
	public void setTpr_audio_format(String tpr_audio_format) {
		this.tpr_audio_format = tpr_audio_format;
	}
	public String getTpr_compression_tech() {
		return tpr_compression_tech;
	}
	public void setTpr_compression_tech(String tpr_compression_tech) {
		this.tpr_compression_tech = tpr_compression_tech;
	}
	public String getTpr_cust_multicast_ip() {
		return tpr_cust_multicast_ip;
	}
	public void setTpr_cust_multicast_ip(String tpr_cust_multicast_ip) {
		this.tpr_cust_multicast_ip = tpr_cust_multicast_ip;
	}
	public String getTpr_cust_wan_ip() {
		return tpr_cust_wan_ip;
	}
	public void setTpr_cust_wan_ip(String tpr_cust_wan_ip) {
		this.tpr_cust_wan_ip = tpr_cust_wan_ip;
	}
	public String getTpr_customer_type() {
		return tpr_customer_type;
	}
	public void setTpr_customer_type(String tpr_customer_type) {
		this.tpr_customer_type = tpr_customer_type;
	}
	public String getTpr_dvb_standard() {
		return tpr_dvb_standard;
	}
	public void setTpr_dvb_standard(String tpr_dvb_standard) {
		this.tpr_dvb_standard = tpr_dvb_standard;
	}
	public String getTpr_fec() {
		return tpr_fec;
	}
	public void setTpr_fec(String tpr_fec) {
		this.tpr_fec = tpr_fec;
	}
	public String getTpr_info_rate() {
		return tpr_info_rate;
	}
	public void setTpr_info_rate(String tpr_info_rate) {
		this.tpr_info_rate = tpr_info_rate;
	}
	public String getTpr_input_format() {
		return tpr_input_format;
	}
	public void setTpr_input_format(String tpr_input_format) {
		this.tpr_input_format = tpr_input_format;
	}
	public String getTpr_modulation() {
		return tpr_modulation;
	}
	public void setTpr_modulation(String tpr_modulation) {
		this.tpr_modulation = tpr_modulation;
	}
	public String getTpr_no_of_audios() {
		return tpr_no_of_audios;
	}
	public void setTpr_no_of_audios(String tpr_no_of_audios) {
		this.tpr_no_of_audios = tpr_no_of_audios;
	}
	public String getTpr_playout_services() {
		return tpr_playout_services;
	}
	public void setTpr_playout_services(String tpr_playout_services) {
		this.tpr_playout_services = tpr_playout_services;
	}
	public String getTpr_symbol_rate() {
		return tpr_symbol_rate;
	}
	public void setTpr_symbol_rate(String tpr_symbol_rate) {
		this.tpr_symbol_rate = tpr_symbol_rate;
	}
	public String getTpr_teleport_location() {
		return tpr_teleport_location;
	}
	public void setTpr_teleport_location(String tpr_teleport_location) {
		this.tpr_teleport_location = tpr_teleport_location;
	}
	public String getTpr_type_of_audios() {
		return tpr_type_of_audios;
	}
	public void setTpr_type_of_audios(String tpr_type_of_audios) {
		this.tpr_type_of_audios = tpr_type_of_audios;
	}
	public String getTpr_type_of_order() {
		return tpr_type_of_order;
	}
	public void setTpr_type_of_order(String tpr_type_of_order) {
		this.tpr_type_of_order = tpr_type_of_order;
	}
	public String getTpr_type_of_service() {
		return tpr_type_of_service;
	}
	public void setTpr_type_of_service(String tpr_type_of_service) {
		this.tpr_type_of_service = tpr_type_of_service;
	}
	public String getTpr_video_format() {
		return tpr_video_format;
	}
	public void setTpr_video_format(String tpr_video_format) {
		this.tpr_video_format = tpr_video_format;
	}
	public String getTu_dvb_standard_uplink() {
		return tu_dvb_standard_uplink;
	}
	public void setTu_dvb_standard_uplink(String tu_dvb_standard_uplink) {
		this.tu_dvb_standard_uplink = tu_dvb_standard_uplink;
	}
	public String getTu_fec_uplink() {
		return tu_fec_uplink;
	}
	public void setTu_fec_uplink(String tu_fec_uplink) {
		this.tu_fec_uplink = tu_fec_uplink;
	}
	public String getTu_info_rate_uplink() {
		return tu_info_rate_uplink;
	}
	public void setTu_info_rate_uplink(String tu_info_rate_uplink) {
		this.tu_info_rate_uplink = tu_info_rate_uplink;
	}
	public String getTu_modulation_uplink() {
		return tu_modulation_uplink;
	}
	public void setTu_modulation_uplink(String tu_modulation_uplink) {
		this.tu_modulation_uplink = tu_modulation_uplink;
	}
	public String getTu_symbol_rate_uplink() {
		return tu_symbol_rate_uplink;
	}
	public void setTu_symbol_rate_uplink(String tu_symbol_rate_uplink) {
		this.tu_symbol_rate_uplink = tu_symbol_rate_uplink;
	}
	public String getTu_type_of_carrier() {
		return tu_type_of_carrier;
	}
	public void setTu_type_of_carrier(String tu_type_of_carrier) {
		this.tu_type_of_carrier = tu_type_of_carrier;
	}
	
	
	
	
}
