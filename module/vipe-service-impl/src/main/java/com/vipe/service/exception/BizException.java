package com.vipe.service.exception;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class BizException extends RuntimeException {


    private int code;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Throwable throwable) {
        super(throwable);
    }


    public BizException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BizException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
