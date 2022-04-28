package com.chhinghor.hrd.chhinghorjwt.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"404 Not Found"),
    POST_SUCCESS(HttpStatus.OK, "Resource has been posted successfully"),
    DELETED_SUCCESS(HttpStatus.OK, "Resource has been deleted successfully"),
    UPDATED_SUCCESS(HttpStatus.OK, "Resource has been updated successfully"),
    EXCEPTION_ACCORS(HttpStatus.OK, "Authentication Error. please login to get token!")
    ;

    private String message;
    private HttpStatus status;

    ErrorCode(final HttpStatus status, final String message) {

        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getStatus() {

        return status;

    }
}
