package com.vipe.service.exception;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class VipeException extends RuntimeException {


    private int errorCode;

    public VipeException() {
        super();
    }

    public VipeException(String message) {
        super(message);
    }

    public VipeException(int code, String message) {
        super(message);
        this.errorCode = code;
    }

    public VipeException(Throwable throwable) {
        super(throwable);
    }


    public VipeException(int code, Throwable throwable) {
        super(throwable);
        this.errorCode = code;
    }

    public VipeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public VipeException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
