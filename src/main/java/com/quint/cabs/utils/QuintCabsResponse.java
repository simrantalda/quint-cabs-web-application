/**
 * 
 */
package com.quint.cabs.utils;

/**
 * @author LENOVO
 *
 */
public class QuintCabsResponse<T> {

	private T response;
	
	private int status;

	/**
	 * @return the response
	 */
	public T getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(T response) {
		this.response = response;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
