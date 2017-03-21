/**
 * 
 */
package com.ibm.fx.dto;

/**
 * @author Anshuli
 *
 */
public class ErrorDTO {
	
	private String errorReason ;
	private int errorCode;
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return this.errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorReason
	 */
	public String getErrorReason() {
		return this.errorReason;
	}
	/**
	 * @param errorReason the errorReason to set
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

}
