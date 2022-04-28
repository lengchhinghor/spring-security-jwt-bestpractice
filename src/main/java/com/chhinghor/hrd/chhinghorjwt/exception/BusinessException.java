package com.chhinghor.hrd.chhinghorjwt.exception;

import com.chhinghor.hrd.chhinghorjwt.enums.ErrorCode;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    public BusinessException(ErrorCode errorCode, String message) {

        super(message);
        this.errorCode = errorCode;

    }

    public BusinessException(ErrorCode errorCode) {

        super(errorCode.getMessage());
        this.errorCode = errorCode;

    }

    public BusinessException(ErrorCode errorCode, Throwable e) {

        this(errorCode);
        System.out.println(e);
//        AppLogManager.error(e);

    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
