package com.ibm.ioes.model;

import java.util.ArrayList;

import com.ibm.ioes.dao.ChangeOrderTypeDao;
import com.ibm.ioes.dao.MasterAttributesDao;
import com.ibm.ioes.forms.ChangeOrderTypeDto;
import com.ibm.ioes.forms.MastersAttributesDto;

public class ChangeOrderTypeModel {

	public ArrayList<ChangeOrderTypeDto> viewChangeTypeList()
			throws Exception {
		ArrayList<ChangeOrderTypeDto> objList = new ArrayList<ChangeOrderTypeDto>();
		ChangeOrderTypeDao objDao = new ChangeOrderTypeDao();
		try {
			objList = objDao.viewChangeOrderTypeList();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return objList;
	}

	public String updateChangeOrderTypeList(
			ArrayList<ChangeOrderTypeDto> objDtoList) throws Exception {

		ChangeOrderTypeDao objDao = new ChangeOrderTypeDao();
		String result = "";
		try {
			result = objDao.updateChangeOrderTypeList(objDtoList);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return result;
	}
	public String addChangeOrderType(ChangeOrderTypeDto objDto) throws Exception {

		ChangeOrderTypeDao objDao = new ChangeOrderTypeDao();
		String result = "";
		try {
			result = objDao.addChangeOrderType(objDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SQL Exception : " + ex.getMessage(), ex);
		}
		return result;
	}
	
}
