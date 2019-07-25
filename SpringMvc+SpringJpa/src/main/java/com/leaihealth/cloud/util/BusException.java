package com.leaihealth.cloud.util;

import java.util.List;

/**
 * *系统service抛出的异常)
 *
 * @author zhuxiange 2013-1-31 下午03:02:23
 */
public class BusException extends Exception {
    /**
     * 用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private String errorMessage;

    private List<String> data;

    /**
     * Constructor with error code and message.
     *
     * @param theErrorCode the error code
     * @param theErrorMessage the error message
     * @param theData more data details
     */
    public BusException(final ErrorCode theErrorCode, final String theErrorMessage, final List<String> theData) {
        super(theErrorMessage);
        this.errorCode = theErrorCode;
        this.errorMessage = theErrorMessage;
        this.data = theData;
    }

    /**
     * Constructor with error code and message.
     *
     * @param theErrorCode the error code
     * @param theErrorMessage the error message
     */
    public BusException(final ErrorCode theErrorCode, final String theErrorMessage) {
        super(theErrorMessage);
        this.errorCode = theErrorCode;
        this.errorMessage = theErrorMessage;
    }

    /**
     * Constructor with error code.
     *
     * @param theErrorCode the error code
     */
    public BusException(final ErrorCode theErrorCode) {
        this.errorCode = theErrorCode;
    }

    /**
     * Returns the error code.
     *
     * @return the error code
     */
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    /**
     * Returns more data details.
     *
     * @return more data details
     */
    public List<String> getData() {
        return this.data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
