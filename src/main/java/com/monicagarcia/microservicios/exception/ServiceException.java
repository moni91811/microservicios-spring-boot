package com.monicagarcia.microservicios.exception;

public class ServiceException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4879468333448366121L;
	private Integer httpStatusCode;
  
    public ServiceException(String message, Integer httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
 
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
 
    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}