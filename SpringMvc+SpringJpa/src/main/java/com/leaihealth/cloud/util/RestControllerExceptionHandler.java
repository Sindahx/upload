/**
 * ClassName: RestControllerExceptionHandler.java
 *
 * Version Information:
 *
 * Date: Mar 11, 2016
 *
 * COPYRIGHT (C) 2016 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class handles exceptions thrown by rest controllers.
 */
@ControllerAdvice
public class RestControllerExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    /**
     * Handles application exceptions.
     *
     * @param request the HTTP request
     * @param e the application exception
     * @return the JSON response
     */
    @SuppressWarnings("static-method")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(BusException.class)
    @ResponseBody
    public JsonResponse handleAppException(final HttpServletRequest request, final BusException e) {
        logger.error("Message: '{}'", e.getMessage());
        logger.debug("Exception stacktrace: ", e);

        if (e.getData() != null) {
            return buildErrorResponse(e.getErrorCode(), e.getLocalizedMessage(), e.getData());
        }
        return buildErrorResponse(e.getErrorCode(), e.getLocalizedMessage());
    }

    /**
     * Handles authorization exceptions.
     *
     * @param request the HTTP request
     * @param e the authorization exception
     * @return the JSON response
     */
    @SuppressWarnings("static-method")
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public JsonResponse handleAuthorizationException(final HttpServletRequest request, final AuthorizationException e) {
        final String exceptionStrackTrace = ExceptionUtils.getStackTrace(e);
        logger.error(exceptionStrackTrace);
        return buildErrorResponse(ErrorCode.UNAUTHORIZED_ERROR, StringUtils.EMPTY);
    }

    /**
     * Handles all exceptions.
     *
     * @param request the HTTP request
     * @param e the all exception
     * @return the JSON response
     */
    @SuppressWarnings("static-method")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResponse handleUncaughtException(final HttpServletRequest request, final Exception e) {
        final String exceptionStrackTrace = ExceptionUtils.getStackTrace(e);
        logger.error(exceptionStrackTrace);
        return buildErrorResponse(ErrorCode.RUNTIME_ERROR, exceptionStrackTrace);
    }

    private static JsonResponse buildErrorResponse(final ErrorCode errorCode, final String errorMessage) {
        return JsonResponse.failed().withCode(errorCode.getCode()).withMessage(errorMessage);
    }

    private static JsonResponse buildErrorResponse(final ErrorCode errorCode, final String errorMessage, final List<String> data) {
        return JsonResponse.failed().withCode(errorCode.getCode()).withMessage(errorMessage).withData(data);
    }
}
