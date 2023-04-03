package com.gstsgy.base.model;

import org.springframework.http.HttpStatus;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2021/9/26 下午5:44
 */
public class APIException extends RuntimeException{

    private HttpStatus httpStatus;

    private String message;

    public APIException(HttpStatus httpStatus , String message){
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getApiExceptionType() {
        return httpStatus;
    }

    public void setApiExceptionType(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
