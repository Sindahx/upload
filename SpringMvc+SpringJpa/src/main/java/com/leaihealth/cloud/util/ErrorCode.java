/**
 * ClassName: ErrorCode.java
 *
 * Version Information:
 *
 * Date: Mar 9, 2016
 *
 * COPYRIGHT (C) 2016 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.util;

/**
 * The enumeration for error codes.
 *
 * @author kennyliao
 * @author Joseph S. Kuo
 */
public enum ErrorCode {
    /* general */
    /**
     * Error code "error.runtime" when a runtime error occurs.
     */
    RUNTIME_ERROR("error.runtime"),

    /**
     * Error code "error.json_processing" when it fails to process JSON payload.
     */
    JSON_PROCESSING_ERROR("error.json_processing"),

    /**
     * Error code "error.data_integrity_violation" when data in database
     * violates integrity.
     */
    DATA_INTEGRITY_VIOLATION_ERROR("error.data_integrity_violation"),

    /**
     * Error code "error.parameter_missing" when a request misses any required
     * parameter.
     */
    PARAMETER_MISSING("error.parameter_missing"),

    /**
     * Error code "error.illegal_parameters" if a request contains illegal
     * parameters.
     */
    ILLEGAL_PARAMETERS("error.illegal_parameters"),

    /**
     * Error code "error.format" if the given data format, file extension format
     * or document content format is illegal.
     */
    FORMAT_ERROR("error.format"),

    /* login */
    /**
     * Error code "error.unauthorized" if a request is unauthorized.
     */
    UNAUTHORIZED_ERROR("error.unauthorized"),

    /**
     * Error code "warn.unknown_account_or_incorrect_password" when the user
     * logins with unknown account or incorrect password.
     */
    UNKNOWN_ACCOUNT_OR_INCORRECT_PASSWORD("warn.unknown_account_or_incorrect_password");

    private String code;

    /**
     * Constructor with the given error code.
     *
     * @param theCode the error code to set
     */
    private ErrorCode(final String theCode) {
        this.code = theCode;
    }

    /**
     * Returns the error code.
     *
     * @return the error code
     */
    public String getCode() {
        return this.code;
    }
}
