package com.ibm.ioes.npd.hibernate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.Timestamp;

import com.ibm.ioes.npd.exception.NpdException;
import com.ibm.ioes.npd.hibernate.beans.ProductCategoryDto;

public class ProductCategoryDao extends CommonBaseDao {

	public ArrayList<ProductCategoryDto> getProductCategoryList(
			Connection connection) throws NpdException {
		ArrayList<ProductCategoryDto> productCategoryDtoList = new ArrayList<ProductCategoryDto>();
		ResultSet resultSet = null;
		ProductCategoryDto productCategoryDto = null;
		String sql = "SELECT CAT.*,COALESCE((SELECT 1 FROM SYSIBM.SYSDUMMY1"
				+ " WHERE EXISTS ( SELECT * FROM NPD.TTRN_PROJECT PRD WHERE PRD.PRODUCT_CATEGORY=CAT.PRODCATID)"
				+ "),0) AS MAPPED_STATUS FROM NPD.TM_PRODUCTCATEGORY AS CAT";

		try {

			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				productCategoryDto = new ProductCategoryDto();
				productCategoryDto.setProductId(resultSet
						.getString("PRODCATID"));
				productCategoryDto.setProductDesc(resultSet
						.getString("PRODCATDESC"));

				productCategoryDto.setCreatedDate(resultSet
						.getString("CREATEDDATE"));
				productCategoryDto.setCreatedBy(resultSet
						.getString("CREATEDBY"));
				productCategoryDto.setModyfiedDate(resultSet
						.getString("MODIFIEDDATE"));
				productCategoryDto.setModifiedBy(resultSet
						.getString("MODIFIEDBY"));
				productCategoryDto.setMappedStatus(resultSet
						.getString("MAPPED_STATUS"));
				
				if (resultSet.getString("ISACTIVE").equals("1")) {
					productCategoryDto.setIsActive("Y");
				} else {
					productCategoryDto.setIsActive("N");
				}
				productCategoryDtoList.add(productCategoryDto);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NpdException(e);
		}

		return productCategoryDtoList;
	}

	public int updateProductCategory(String[] productIdArr, String[] statusArr,
			String[] descriptionArr, int createdBy, Connection connection)
			throws NpdException {
		int updateStatus = 0;
		String sql = "UPDATE NPD.TM_PRODUCTCATEGORY"
				+ " SET PRODCATDESC=?, ISACTIVE=?, MODIFIEDDATE=?,CREATEDBY=?"
				+ "WHERE PRODCATID=?";
		PreparedStatement preparedStatement = null;

		try {
			int[] statusIntArr = new int[statusArr.length];
			for (int id = 0; id < statusArr.length; id++) {
				if (statusArr[id].equalsIgnoreCase("Y")) {
					statusIntArr[id] = 1;
				} else {
					statusIntArr[id] = 0;
				}
			}
			for (int i = 0; i < productIdArr.length; i++) {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, descriptionArr[i]);
				preparedStatement.setInt(2, statusIntArr[i]);
				preparedStatement.setTimestamp(3, new Timestamp(System
						.currentTimeMillis()));
				preparedStatement.setInt(4, createdBy);
				preparedStatement.setInt(5, Integer.parseInt(productIdArr[i]));
				updateStatus = preparedStatement.executeUpdate();
				connection.commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NpdException(e);
		}

		return updateStatus;
	}

	public int addProductCategory(ProductCategoryDto productCategoryDto,
			int createdBy, Connection connection) throws NpdException {
		int addStatus = 0;
		String sql = "INSERT INTO NPD.TM_PRODUCTCATEGORY(PRODCATDESC, ISACTIVE, CREATEDDATE, CREATEDBY) "
				+ " VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = null;

		try {
			int status = 0;
			if (productCategoryDto.getIsActive().equalsIgnoreCase("Y")) {
				status = 1;
			} else {
				status = 0;
			}

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, productCategoryDto.getProductDesc());
			preparedStatement.setInt(2, status);
			preparedStatement.setTimestamp(3, new Timestamp(System
					.currentTimeMillis()));
			preparedStatement.setInt(4, createdBy);
			addStatus = preparedStatement.executeUpdate();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw new NpdException(e);
		}

		return addStatus;
	}

}
