package com.ibm.ioes.npd.model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;

public class CommonBaseModel {

	/**
	 * convert Blob into Byte Array
	 * 
	 * @param mntFiles
	 * @return Byte Array
	 * @throws Exception
	 */
	public byte[] blobToByteArray(Blob b) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] File = null;
		byte[] buf = new byte[1048576];
		InputStream is = b.getBinaryStream();
		for (;;) {
			int dataSize = is.read(buf);

			if (dataSize == -1) {
				break;
			}
			baos.write(buf, 0, dataSize);
		}
		File = baos.toByteArray();
		b = null;
		return File;
	}

	/**
	 * set Content type according to file Type
	 * 
	 * @param mntFiles
	 * @return String value of content Type
	 * @throws Exception
	 */
	public String setContentTypeForFile(String fileName) throws Exception {

		String ContentType = "";
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName
				.length());

		if (ext.equalsIgnoreCase("xls")) {

			ContentType = "application/vnd.ms-excel";
			return ContentType;
		} else if (ext.equalsIgnoreCase("ppt") || ext.equalsIgnoreCase("pps")) {

			ContentType = "application/vns.ms-powerpoint";
			return ContentType;
		} else if (ext.equalsIgnoreCase("doc")) {

			ContentType = "application/msword";
			return ContentType;
		} else if (ext.equalsIgnoreCase("xlsx")) {
			ContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			return ContentType;
		} else if (ext.equalsIgnoreCase("docx")) {
			ContentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			return ContentType;
		} else if (ext.equalsIgnoreCase("jpg")) {
			ContentType = "image/jpg";
			return ContentType;
		} else if (ext.equalsIgnoreCase("gif")) {
			ContentType = "image/gif";
			return ContentType;
		} else if (ext.equalsIgnoreCase("txt")) {
			ContentType = "text/plain";
			return ContentType;
		}

		return ContentType;

	}

}
