/**
 * ClassName: LoginStatusFilter.java
 * <p>
 * Version Information:
 * <p>
 * Date: 2018/06/06
 * <p>
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.leaihealth.cloud.entity.idem.HzUserInfo;
import com.leaihealth.cloud.util.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Login status check filter.
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/06
 */
@Component
public class LoginStatusFilter implements HandlerInterceptor {
    /**
     * The header key for session token.
     */
    public static final String SESSION_TOKEN_HEADER_KEY = "auth";

    private static final String URI_PREFIX = "/idemService";

    private final Logger logger = LoggerFactory.getLogger(LoginStatusFilter.class);

    private final List<String> urlWhiteList = Arrays.asList( "/console/login", "/v2/api-docs", "/upload/downUpload");

    private boolean checkIgnoreList(final String uri) {
        if (StringUtils.isEmpty(uri)) {
            this.logger.warn("Empty URI!");
            return false;
        }

        final String newUri = StringUtils.substringAfter(uri, URI_PREFIX);
        final Optional<String> result = this.urlWhiteList.stream().filter(u -> newUri.startsWith(u)).findFirst();
        return result.isPresent();
    }

    private boolean checkAuthList(final String uri, List<String> acctRoles) {
        if (StringUtils.isEmpty(uri)) {
            this.logger.warn("Empty URI!");
            return false;
        }

        final String newUri = StringUtils.substringAfter(uri, URI_PREFIX);

        final Optional<String> result = acctRoles.stream().filter(u -> newUri.startsWith(u)).findFirst();
        return result.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterCompletion(final HttpServletRequest req, final HttpServletResponse res, final Object handler, final Exception ex) throws Exception {
        // nothing
        final HzUserInfo user = SecurityUtil.getUser();
        this.logger.info("afterCompletion done: user={}, access={} {}", user != null ? user.getLoginName() : null, req.getMethod(), req.getRequestURI());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postHandle(final HttpServletRequest req, final HttpServletResponse res, final Object handler, final ModelAndView view) throws Exception {
        final HzUserInfo user = SecurityUtil.getUser();
        this.logger.info("postHandle done: user={}, access={} {}", user != null ? user.getLoginName() : null, req.getMethod(), req.getRequestURI());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(final HttpServletRequest req, final HttpServletResponse res, final Object handler) throws Exception {
        final String requestUri = req.getRequestURI();
        final boolean result = true;

        String method = req.getMethod();
        if ("GET".equals(method) || "get".equals(method)){
            return result;
        }
        //待项目上线记得开启此处代码
        if (!this.checkIgnoreList(requestUri)) {
            final String sessionToken = req.getHeader(SESSION_TOKEN_HEADER_KEY);
//            HttpSession session = req.getSession(true);
//            final String sessionToken = (String) session.getAttribute("sessionAuth");
            if (StringUtils.isEmpty(sessionToken)) {
                this.logger.warn("Empty session token!! requestUri={}", requestUri);
                outputError(res);
                return false;
            }

            try {
                final boolean isLogin = SecurityUtil.isLogin(sessionToken);
                if (!isLogin) {
                    this.logger.warn("Unauthorized access: requestUri={}, sessionToken={}", requestUri, sessionToken);
                    LoginStatusFilter.outputError(res);
                    return false;
                }
            } catch (final Exception e) {
                this.logger.error("Login check failure: requestUri={}, sessionToken={}", requestUri, sessionToken);
                this.logger.error("Login check failure.", e);
            }
        }

        final HzUserInfo user = SecurityUtil.getUser();

        this.logger.info("preHandle: user={}, access={} {}, result={}", user != null ? user.getLoginName() : null, req.getMethod(), requestUri, result);
        return result;
    }

    private static String toJson(final Object obj) {
        final ObjectMapper om = new ObjectMapper();
        om.registerModule(new Hibernate5Module().disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION));
        try {
            return om.writeValueAsString(obj);
        } catch (final JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private static void outputError(final HttpServletResponse res) throws Exception {
        res.setContentType("application/json");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(toJson(JsonResponse.failed().withCode("UNAUTHORIZED_ERROR")));
        res.flushBuffer();
    }

    private static void outputErrorNotAuth(final HttpServletResponse res) throws Exception {
        res.setContentType("application/json");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(toJson(JsonResponse.failed().withCode("您没有权限操作，请联系管理员")));
        res.flushBuffer();
    }

}
