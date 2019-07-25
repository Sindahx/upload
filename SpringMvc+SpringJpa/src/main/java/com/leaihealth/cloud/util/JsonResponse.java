/**
 * ClassName: JsonResponse.java
 *
 * Version Information:
 *
 * Date: 2018/06/01
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * JSON response class.
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
public class JsonResponse {
    private boolean success;

    private String code;

    private String message;

    private Object data;

    private JsonResponse() {
    }

    private JsonResponse(final boolean isSuccess, final String theCode, final String theMessage) {
        this.success = isSuccess;
        this.code = theCode;
        this.message = theMessage;
    }

    /**
     * Returns a new JSON response with OK message.
     *
     * @return a new JSON response with OK message
     */
    public static JsonResponse ok() {
        return new JsonResponse(true, StringUtils.EMPTY, "ok");
    }

    /**
     * Returns a new JSON response with isSuccess {@code false}.
     *
     * @return a new JSON response with isSuccess {@code false}
     */
    public static JsonResponse failed() {
        return new JsonResponse(false, StringUtils.EMPTY, StringUtils.EMPTY);
    }

    /**
     * Sets the code and returns this object.
     *
     * @param theCode the code
     * @return this object
     */
    public JsonResponse withCode(final String theCode) {
        this.code = theCode;
        return this;
    }

    /**
     * Sets the message and returns this object.
     *
     * @param theMessage the message
     * @return this object
     */
    public JsonResponse withMessage(final String theMessage) {
        this.message = theMessage;
        return this;
    }

    /**
     * Sets the data and returns this object.
     *
     * @param theData the data
     * @return this object
     */
    public JsonResponse withData(final Object theData) {
        this.data = theData;
        return this;
    }

    /**
     * Initializes the data map and returns this object.
     *
     * @return this object
     */
    @SuppressWarnings("rawtypes")
    public JsonResponse withMapData() {
        this.data = new HashMap();
        return this;
    }

    /**
     * Adds the given key and value into data map.
     *
     * @param key the key
     * @param value the value
     * @return this object
     */
    @SuppressWarnings({
            "unchecked", "rawtypes"
    })
    public JsonResponse add(final String key, final Object value) {
        if ((this.data == null) || !(this.data instanceof HashMap)) {
            this.data = new HashMap<>();
//            throw new IllegalArgumentException("pleae initial the data map first.");
        }
        ((HashMap) this.data).put(key, value);
        return this;
    }

    /**
     * Returns {@code true} if success.
     *
     * @return {@code true} if success
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * Returns the message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Returns the code.
     *
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns the data.
     *
     * @return the data
     */
    public Object getData() {
        return this.data;
    }
}
