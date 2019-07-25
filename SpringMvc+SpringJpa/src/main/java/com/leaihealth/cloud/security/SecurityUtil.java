/**
 * ClassName: SecurityUtil.java
 * <p>
 * Version Information:
 * <p>
 * Date: 2018/06/07
 * <p>
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.security;

import com.leaihealth.cloud.config.SystemConfig;
import com.leaihealth.cloud.entity.idem.HzUserInfo;
import com.leaihealth.cloud.util.BusException;
import com.leaihealth.cloud.util.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The utility tool for security.
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/07
 */
public class SecurityUtil {
    private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    /**
     * Logins the system with the given login ID and password.
     *
     * @param loginId the login ID
     * @param password the password
     * @return the user
     * @throws if any
     */
    public static String login(final String loginId, final String password) throws BusException {
        final Subject subject = (new Subject.Builder()).buildSubject();
        ThreadContext.bind(subject);

        final UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginId, password);

        try {
            subject.login(usernamePasswordToken);
            subject.getSession().setTimeout(SystemConfig.getInteger(SystemConfig.PROP_SESSION_TIMEOUT) * DateUtils.MILLIS_PER_MINUTE);
//            final HzUserInfo user = (HzUserInfo) subject.getPrincipal();
            final String sessionToken = subject.getSession().getId().toString();
//            user.setSessionToken(sessionToken);
            return sessionToken;
        } catch (final UnknownAccountException | IncorrectCredentialsException e) {
            logger.error("Login failure: id={}, message={}", loginId, e.getMessage());
            throw new BusException(ErrorCode.UNKNOWN_ACCOUNT_OR_INCORRECT_PASSWORD, "Login failure: loginName=" + loginId);
        } catch (final Exception e) {
            throw e;
        }
    }

    /**
     * Returns <code>true</code> if the given session token is logged in.
     *
     * @param sessionToken the session token
     * @return <code>true</code> if the given session token is logged in
     */
    public static boolean isLogin(final String sessionToken) {
        if (StringUtils.isBlank(sessionToken)) {
            return false;
        }

        final Subject subject = getSubject(sessionToken);
        boolean isLogin = false;
        if (subject != null) {
            isLogin = subject.isAuthenticated();
            if (isLogin) {
                subject.getSession().touch();
                ThreadContext.bind(subject);
            }
        }
        return isLogin;
    }

    /**
     * Logs out a session token
     *
     * @param sessionToken the session token
     */
    public static void logout(final String sessionToken) {
        final Subject subject = getSubject(sessionToken);
        if (subject != null) {
            subject.logout();
        }
    }

    private static Subject getSubject(final String sessionToken) {
        final Subject.Builder builder = new Subject.Builder(SecurityUtils.getSecurityManager());
        final Subject subject = builder.sessionId(sessionToken).buildSubject();
        return subject;
    }

    /**
     * Returns the user related to the given session token.
     *
     * @param sessionToken the session token
     * @return the user
     */
    public static HzUserInfo getUser(final String sessionToken) {
        final Subject subject = getSubject(sessionToken);
        if (subject != null) {
            return (HzUserInfo) subject.getPrincipal();
        }
        return null;
    }


   public static HzUserInfo getUser() {
        try {
            final Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                return (HzUserInfo) subject.getPrincipal();
            }
        } catch (final Exception e) {
            // do nothing
        }
        return null;
    }
}
